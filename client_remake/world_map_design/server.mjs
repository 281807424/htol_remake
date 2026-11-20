import { createServer } from "node:http";
import { execFile } from "node:child_process";
import { readFile, readdir, writeFile } from "node:fs/promises";
import { fileURLToPath } from "node:url";
import { dirname, join, resolve, sep } from "node:path";
import { tmpdir } from "node:os";
import { promisify } from "node:util";

const directory = dirname(fileURLToPath(import.meta.url));
const exportDirectory = resolve(directory, "..", "htol_export");
const mapsDirectory = join(exportDirectory, "map", "preview");
const designFile = join(directory, "world_map.json");
const placementsFile = resolve(directory, "..", "map_placement_editor", "map_placements.json");
const thumbnailScript = join(directory, "thumbnail_cache.py");
const thumbnailDirectory = join(tmpdir(), "htol-world-map-design-thumbnails");
const runFile = promisify(execFile);
const staticFiles = new Map([
  ["/", ["index.html", "text/html; charset=utf-8"]],
  ["/index.html", ["index.html", "text/html; charset=utf-8"]],
  ["/app.js", ["app.js", "text/javascript; charset=utf-8"]],
  ["/layout.js", ["layout.js", "text/javascript; charset=utf-8"]],
  ["/styles.css", ["styles.css", "text/css; charset=utf-8"]],
]);
const mapFilenamePattern = /^map_\d+_palette_0_preview\.png$/;
const objectFilenamePattern = /^(?:map|unit|monster|player|effect|icon|pet|npc|other)\/(?:[^/]+\/)*sprite-\d+-.+\.png$/;
const maxDesignBytes = 8 * 1024 * 1024;
const maxTeleportLinkDistance = 220;
let thumbnailPromise;

function send(response, statusCode, contentType, body) {
  response.writeHead(statusCode, { "content-type": contentType });
  response.end(body);
}

function sendJson(response, statusCode, value) {
  send(response, statusCode, "application/json; charset=utf-8", JSON.stringify(value));
}

async function readJson(path) {
  return JSON.parse(await readFile(path, "utf8"));
}

async function readRequestJson(request) {
  const chunks = [];
  let size = 0;
  for await (const chunk of request) {
    size += chunk.length;
    if (size > maxDesignBytes) {
      const error = new Error("Design file is too large.");
      error.statusCode = 413;
      throw error;
    }
    chunks.push(chunk);
  }
  return JSON.parse(Buffer.concat(chunks).toString("utf8"));
}

async function readPlacedTeleports() {
  const placements = await readJson(placementsFile);
  if (placements?.schemaVersion !== 1 || !Array.isArray(placements.maps)) {
    throw new Error("地图布置文件格式无效。");
  }

  const teleportsById = new Map();
  for (const map of placements.maps) {
    if (!map || !Number.isInteger(map.mapId) || !Array.isArray(map.teleports)) {
      throw new Error("地图布置文件包含无效地图或传送点清单。");
    }
    for (const teleport of map.teleports) {
      if (
        !teleport ||
        typeof teleport.id !== "string" ||
        !teleport.id ||
        !["东", "东北", "北", "西北", "西", "西南", "南", "东南"].includes(teleport.direction) ||
        !Number.isFinite(teleport.x) ||
        !Number.isFinite(teleport.y) ||
        teleportsById.has(teleport.id)
      ) {
        throw new Error(`地图 ${map.mapId} 的传送点数据无效或 ID 重复。`);
      }
      teleportsById.set(teleport.id, { mapId: map.mapId, ...teleport });
    }
  }
  return teleportsById;
}

function teleportScenePosition(map, teleport, catalogById) {
  const catalogMap = catalogById.get(map.mapId);
  if (!catalogMap) return null;
  const insetX = map.kind === "野外地图" ? 3 : 4;
  const insetY = map.kind === "野外地图" ? 2 : 3;
  const previewWidth = map.width - insetX * 2;
  const previewHeight = 34;
  const imageWidth = catalogMap.widthCells * catalogMap.renderCellPx;
  const imageHeight = catalogMap.heightCells * catalogMap.renderCellPx;
  const scale = Math.min(previewWidth / imageWidth, previewHeight / imageHeight);
  const renderedWidth = imageWidth * scale;
  const renderedHeight = imageHeight * scale;
  return {
    x: map.x + insetX + (previewWidth - renderedWidth) / 2 + teleport.x * scale,
    y: map.y + insetY + (previewHeight - renderedHeight) / 2 + teleport.y * scale,
  };
}

function segmentIntersectsMap(start, end, map) {
  const deltaX = end.x - start.x;
  const deltaY = end.y - start.y;
  let minimum = 0;
  let maximum = 1;
  for (const [direction, distance] of [
    [-deltaX, start.x - map.x],
    [deltaX, map.x + map.width - start.x],
    [-deltaY, start.y - map.y],
    [deltaY, map.y + map.height - start.y],
  ]) {
    if (Math.abs(direction) < 1e-9) {
      if (distance < 0) return false;
      continue;
    }
    const intersection = distance / direction;
    if (direction < 0) minimum = Math.max(minimum, intersection);
    else maximum = Math.min(maximum, intersection);
  }
  return minimum <= maximum && maximum > 0.01 && minimum < 0.99;
}

function connectionConstraintError(connection, mapsById, teleportsById, catalogById) {
  const from = mapsById.get(connection.fromMapId);
  const to = mapsById.get(connection.toMapId);
  const fromTeleport = teleportsById.get(connection.fromTeleportId);
  const toTeleport = teleportsById.get(connection.toTeleportId);
  if (!from || !to || !fromTeleport || !toTeleport) return "连接地图或传送点无效。";

  const start = teleportScenePosition(from, fromTeleport, catalogById);
  const end = teleportScenePosition(to, toTeleport, catalogById);
  if (!start || !end) return "连接地图缺少图像尺寸档案。";
  if (Math.hypot(end.x - start.x, end.y - start.y) > maxTeleportLinkDistance) {
    return "传送点距离过远；请先调整地图位置。";
  }
  for (const map of mapsById.values()) {
    if (
      map.mapId !== from.mapId &&
      map.mapId !== to.mapId &&
      segmentIntersectsMap(start, end, map)
    ) {
      return "传送点连线不能穿过其他地图。";
    }
  }
  return null;
}

function validateDesign(candidate, baseline, teleportsById, catalogById) {
  if (
    !candidate ||
    !Array.isArray(candidate.maps) ||
    !Array.isArray(candidate.connections) ||
    candidate.maps.length !== baseline.maps.length ||
    candidate.connections.length > teleportsById.size / 2
  ) {
    return "设计文件结构或地图/连接数量无效。";
  }

  const expectedIds = new Set(baseline.maps.map((map) => map.mapId));
  const maps = new Map();
  for (const map of candidate.maps) {
    if (
      !map ||
      !Number.isInteger(map.mapId) ||
      !expectedIds.has(map.mapId) ||
      maps.has(map.mapId) ||
      !Number.isFinite(map.x) ||
      !Number.isFinite(map.y) ||
      !Array.isArray(map.teleports) ||
      map.teleports.length !== 0
    ) {
      return "地图节点缺失、重复、位置无效或包含旧传送点配置。";
    }
    maps.set(map.mapId, map);
  }
  if (maps.size !== expectedIds.size) return "设计必须包含全部可用地图。";

  const connectionIds = new Set();
  const connectedTeleports = new Set();
  const connectedMapPairs = new Set();
  for (const connection of candidate.connections) {
    const fromTeleport = connection && teleportsById.get(connection.fromTeleportId);
    const toTeleport = connection && teleportsById.get(connection.toTeleportId);
    if (
      !connection ||
      !expectedIds.has(connection.fromMapId) ||
      !expectedIds.has(connection.toMapId) ||
      connection.fromMapId === connection.toMapId ||
      typeof connection.id !== "string" ||
      !connection.id ||
      connectionIds.has(connection.id) ||
      !fromTeleport ||
      !toTeleport ||
      fromTeleport.mapId !== connection.fromMapId ||
      toTeleport.mapId !== connection.toMapId ||
      connection.fromTeleportId === connection.toTeleportId
    ) {
      return "地图连接包含无效地图、传送点或重复端点。";
    }
    const mapPairKey = [connection.fromMapId, connection.toMapId]
      .sort((left, right) => left - right)
      .join(":");
    if (connectedMapPairs.has(mapPairKey)) {
      return "同一对地图只能建立一条传送点连接。";
    }
    if (
      connectedTeleports.has(connection.fromTeleportId) ||
      connectedTeleports.has(connection.toTeleportId)
    ) {
      return "地图连接包含无效地图、传送点或重复端点。";
    }
    const constraintError = connectionConstraintError(connection, maps, teleportsById, catalogById);
    if (constraintError) return constraintError;
    connectionIds.add(connection.id);
    connectedTeleports.add(connection.fromTeleportId);
    connectedTeleports.add(connection.toTeleportId);
    connectedMapPairs.add(mapPairKey);
  }

  return null;
}

async function ensureThumbnailCache() {
  if (!thumbnailPromise) {
    thumbnailPromise = runFile(
      process.env.PYTHON ?? "python",
      [
        thumbnailScript,
        designFile,
        join(directory, "map_catalog.json"),
        exportDirectory,
        thumbnailDirectory,
      ],
      { maxBuffer: 1024 * 1024, timeout: 120000 },
    ).catch((error) => {
      thumbnailPromise = null;
      throw new Error(`地图缩略图生成失败：${error.stderr?.trim() || error.message}`, { cause: error });
    });
  }
  await thumbnailPromise;
}

async function routeMapThumbnail(mapId, response) {
  const design = await readJson(designFile);
  if (!design.maps.some((map) => map.mapId === mapId)) {
    send(response, 404, "text/plain; charset=utf-8", "Not found");
    return;
  }
  const thumbnailPath = join(thumbnailDirectory, `map_${mapId}.png`);
  await ensureThumbnailCache();
  const thumbnail = await readFile(thumbnailPath);
  response.writeHead(200, {
    "content-type": "image/png",
    "cache-control": "public, max-age=86400",
  });
  response.end(thumbnail);
}

async function routeImage(pathname, response, imageDirectory) {
  const segments = pathname.split("/").slice(1);
  if (segments.length !== 3) return false;

  let folder;
  let filename;
  try {
    folder = decodeURIComponent(segments[1]);
    filename = decodeURIComponent(segments[2]);
  } catch {
    send(response, 400, "text/plain; charset=utf-8", "Invalid image path");
    return true;
  }
  if (!mapFilenamePattern.test(filename)) {
    send(response, 404, "text/plain; charset=utf-8", "Not found");
    return true;
  }

  const entries = await readdir(imageDirectory, { withFileTypes: true });
  let imagePath;
  if (folder === "_未分类根目录") {
    imagePath = join(imageDirectory, filename);
  } else if (entries.some((entry) => entry.isDirectory() && entry.name === folder)) {
    imagePath = join(imageDirectory, folder, filename);
  } else {
    send(response, 404, "text/plain; charset=utf-8", "Not found");
    return true;
  }

  try {
    send(response, 200, "image/png", await readFile(imagePath));
  } catch (error) {
    if (error.code === "ENOENT") {
      send(response, 404, "text/plain; charset=utf-8", "Not found");
      return true;
    }
    throw error;
  }
  return true;
}

const server = createServer(async (request, response) => {
  const pathname = new URL(request.url, "http://localhost").pathname;

  try {
    if (request.method === "GET") {
      if (pathname === "/api/catalog") {
        const [maps, objects] = await Promise.all([
          readJson(join(directory, "map_catalog.json")),
          readJson(join(directory, "object_catalog.json")),
        ]);
        sendJson(response, 200, { maps, objects });
        return;
      }
      if (pathname === "/api/design") {
        sendJson(response, 200, await readJson(designFile));
        return;
      }
      if (pathname === "/api/teleports") {
        const teleportsById = await readPlacedTeleports();
        const maps = new Map();
        for (const teleport of teleportsById.values()) {
          if (!maps.has(teleport.mapId)) maps.set(teleport.mapId, []);
          maps.get(teleport.mapId).push(teleport);
        }
        sendJson(response, 200, {
          maps: [...maps].map(([mapId, teleports]) => ({ mapId, teleports })),
        });
        return;
      }
      if (/^\/thumbnails\/\d+$/.test(pathname)) {
        await routeMapThumbnail(Number(pathname.slice("/thumbnails/".length)), response);
        return;
      }
      if (pathname.startsWith("/maps/")) {
        if (await routeImage(pathname, response, mapsDirectory)) return;
      } else if (pathname.startsWith("/objects/")) {
        let filename;
        try {
          filename = decodeURIComponent(pathname.slice("/objects/".length));
        } catch {
          send(response, 400, "text/plain; charset=utf-8", "Invalid image path");
          return;
        }
        if (!objectFilenamePattern.test(filename)) {
          send(response, 404, "text/plain; charset=utf-8", "Not found");
          return;
        }
        const objectPath = resolve(exportDirectory, ...filename.split("/"));
        if (!objectPath.startsWith(`${exportDirectory}${sep}`)) {
          send(response, 404, "text/plain; charset=utf-8", "Not found");
          return;
        }
        try {
          send(response, 200, "image/png", await readFile(objectPath));
        } catch (error) {
          if (error.code === "ENOENT") {
            send(response, 404, "text/plain; charset=utf-8", "Not found");
            return;
          }
          throw error;
        }
        return;
      }

      const staticFile = staticFiles.get(pathname);
      if (staticFile) {
        const [filename, contentType] = staticFile;
        send(response, 200, contentType, await readFile(join(directory, filename)));
        return;
      }

      send(response, 404, "text/plain; charset=utf-8", "Not found");
      return;
    }

    if (request.method === "POST" && pathname === "/api/design") {
      const candidate = await readRequestJson(request);
      const [baseline, teleportsById, catalog] = await Promise.all([
        readJson(designFile),
        readPlacedTeleports(),
        readJson(join(directory, "map_catalog.json")),
      ]);
      const catalogById = new Map(catalog.maps.map((map) => [map.mapId, map]));
      const invalidReason = validateDesign(candidate, baseline, teleportsById, catalogById);
      if (invalidReason) {
        sendJson(response, 400, { error: invalidReason });
        return;
      }
      await writeFile(designFile, `${JSON.stringify(candidate, null, 2)}\n`, "utf8");
      sendJson(response, 200, { saved: true });
      return;
    }

    send(response, 405, "text/plain; charset=utf-8", "Method not allowed");
  } catch (error) {
    if (error.statusCode === 413) {
      send(response, 413, "text/plain; charset=utf-8", error.message);
      return;
    }
    if (error instanceof SyntaxError) {
      sendJson(response, 400, { error: "JSON 请求内容无效。" });
      return;
    }
    if (error.code === "ENOENT") {
      send(response, 500, "text/plain; charset=utf-8", "Required design data is missing.");
      return;
    }

    console.error("Request failed:", error);
    send(response, 500, "text/plain; charset=utf-8", "Internal server error");
  }
});

const port = Number(process.env.PORT ?? 4179);
server.listen(port, "127.0.0.1", () => {
  console.log(`World map design tool: http://127.0.0.1:${port}`);
});
