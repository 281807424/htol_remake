import { createServer } from "node:http";
import { readFile, readdir, writeFile } from "node:fs/promises";
import { fileURLToPath } from "node:url";
import { dirname, join, resolve } from "node:path";

const directory = dirname(fileURLToPath(import.meta.url));
const projectDirectory = resolve(directory, "..");
const designDirectory = join(projectDirectory, "world_map_design");
const imagesDirectory = join(projectDirectory, "htol_export", "images");
const mapsDirectory = join(imagesDirectory, "maps");
const monstersDirectory = join(imagesDirectory, "sprite_library", "怪物", "dynamic");
const placementsFile = join(directory, "map_placements.json");
const maxRequestBytes = 8 * 1024 * 1024;
const directions = new Set(["东", "东北", "北", "西北", "西", "西南", "南", "东南"]);
const staticFiles = new Map([
  ["/", ["index.html", "text/html; charset=utf-8"]],
  ["/index.html", ["index.html", "text/html; charset=utf-8"]],
  ["/app.js", ["app.js", "text/javascript; charset=utf-8"]],
  ["/styles.css", ["styles.css", "text/css; charset=utf-8"]],
]);
const initialPlacements = { schemaVersion: 1, maps: [] };

let catalogPromise;

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
    if (size > maxRequestBytes) {
      const error = new Error("布置数据过大。");
      error.statusCode = 413;
      throw error;
    }
    chunks.push(chunk);
  }
  return JSON.parse(Buffer.concat(chunks).toString("utf8"));
}

function encodeAssetUrl(parts) {
  return parts.map((part) => encodeURIComponent(part)).join("/");
}

async function readMonsterCatalog() {
  const buckets = (await readdir(monstersDirectory, { withFileTypes: true }))
    .filter((entry) => entry.isDirectory() && /^\d+$/.test(entry.name))
    .sort((left, right) => Number(left.name) - Number(right.name));
  const monsters = [];
  const files = new Set();

  for (const bucket of buckets) {
    const entries = await readdir(join(monstersDirectory, bucket.name), { withFileTypes: true });
    const groups = new Map();
    for (const entry of entries) {
      if (!entry.isFile() || !entry.name.toLowerCase().endsWith(".png")) continue;
      const match = entry.name.match(/^sprite-(\d+)-(.+)-(\d+)[^-]*\.png$/);
      if (!match) continue;

      const [, rawSpriteId, name, rawActionIndex] = match;
      const key = `${rawSpriteId}:${name}`;
      if (!groups.has(key)) groups.set(key, { spriteId: Number(rawSpriteId), name, actions: [] });
      groups.get(key).actions.push({ filename: entry.name, index: Number(rawActionIndex) });
      files.add(`${bucket.name}/${entry.name}`);
    }

    for (const group of groups.values()) {
      group.actions.sort((left, right) => left.index - right.index || left.filename.localeCompare(right.filename));
      const preview = group.actions.find((action) => action.index === 0) ?? group.actions[0];
      monsters.push({
        spriteId: group.spriteId,
        name: group.name,
        actionCount: Number(bucket.name),
        bucket: bucket.name,
        previewUrl: `/assets/monsters/${encodeAssetUrl([bucket.name, preview.filename])}`,
        previewFile: preview.filename,
      });
    }
  }

  monsters.sort((left, right) =>
    left.actionCount - right.actionCount ||
    left.name.localeCompare(right.name, "zh-CN") ||
    left.spriteId - right.spriteId,
  );
  return { monsters, files };
}

async function loadCatalog() {
  const [world, mapCatalog, monsterCatalog] = await Promise.all([
    readJson(join(designDirectory, "world_map.json")),
    readJson(join(designDirectory, "map_catalog.json")),
    readMonsterCatalog(),
  ]);
  const mapsById = new Map(mapCatalog.maps.map((map) => [map.mapId, map]));
  const worldMaps = world.maps.map((map) => {
    const catalogMap = mapsById.get(map.mapId);
    if (!catalogMap) throw new Error(`地图 ${map.mapId} 缺少素材档案。`);
    return {
      mapId: map.mapId,
      key: map.key,
      name: map.name,
      region: map.region,
      kind: map.kind,
      category: map.category,
      width: catalogMap.widthCells * catalogMap.renderCellPx,
      height: catalogMap.heightCells * catalogMap.renderCellPx,
      imageUrl: `/assets/maps/${map.mapId}`,
    };
  }).sort((left, right) => left.mapId - right.mapId);
  return {
    maps: worldMaps,
    monsters: monsterCatalog.monsters,
    mapAssetsById: mapsById,
    monsterFiles: monsterCatalog.files,
    monsterKeys: new Set(monsterCatalog.monsters.map((monster) =>
      `${monster.actionCount}:${monster.spriteId}:${monster.name}`,
    )),
  };
}

function getCatalog() {
  catalogPromise ??= loadCatalog();
  return catalogPromise;
}

function validPlacementId(value) {
  return typeof value === "string" && /^[a-zA-Z0-9_-]{1,80}$/.test(value);
}

function validatePosition(element, map) {
  return Number.isFinite(element.x) &&
    Number.isFinite(element.y) &&
    element.x >= 0 &&
    element.y >= 0 &&
    element.x <= map.width &&
    element.y <= map.height;
}

function validatePlacements(candidate, catalog) {
  if (!candidate || candidate.schemaVersion !== 1 || !Array.isArray(candidate.maps)) {
    return "布置数据结构无效。";
  }
  if (candidate.maps.length > catalog.maps.length) return "布置数据包含过多地图。";

  const mapsById = new Map(catalog.maps.map((map) => [map.mapId, map]));
  const seenMaps = new Set();
  for (const mapEntry of candidate.maps) {
    if (!mapEntry || !Number.isInteger(mapEntry.mapId) || seenMaps.has(mapEntry.mapId)) {
      return "地图 ID 无效或重复。";
    }
    const map = mapsById.get(mapEntry.mapId);
    if (!map || !Array.isArray(mapEntry.teleports) || !Array.isArray(mapEntry.monsters)) {
      return "地图布置引用了无效地图或元素列表。";
    }
    seenMaps.add(mapEntry.mapId);

    const elementIds = new Set();
    for (const teleport of mapEntry.teleports) {
      if (
        !teleport ||
        !validPlacementId(teleport.id) ||
        elementIds.has(teleport.id) ||
        !directions.has(teleport.direction) ||
        !validatePosition(teleport, map) ||
        (teleport.targetMapId !== null &&
          (!Number.isInteger(teleport.targetMapId) || !mapsById.has(teleport.targetMapId)))
      ) {
        return `地图 ${map.key} 中包含无效传送点。`;
      }
      elementIds.add(teleport.id);
    }

    for (const monster of mapEntry.monsters) {
      const monsterKey = monster && `${monster.actionCount}:${monster.spriteId}:${monster.name}`;
      if (
        !monster ||
        !validPlacementId(monster.id) ||
        elementIds.has(monster.id) ||
        !Number.isInteger(monster.spriteId) ||
        !Number.isInteger(monster.actionCount) ||
        !catalog.monsterKeys.has(monsterKey) ||
        !validatePosition(monster, map)
      ) {
        return `地图 ${map.key} 中包含无效怪物。`;
      }
      elementIds.add(monster.id);
    }
  }
  return null;
}

async function readPlacements() {
  try {
    return await readJson(placementsFile);
  } catch (error) {
    if (error.code === "ENOENT") return initialPlacements;
    throw error;
  }
}

async function serveMapImage(mapId, response) {
  const catalog = await getCatalog();
  const map = catalog.mapAssetsById.get(mapId);
  if (!map || !/^map_\d+_full_preview\.png$/.test(map.filename)) {
    send(response, 404, "text/plain; charset=utf-8", "Not found");
    return;
  }
  const categoryPath = join(mapsDirectory, map.sourceCategory);
  const imagePath = join(categoryPath, map.filename);
  try {
    send(response, 200, "image/png", await readFile(imagePath));
  } catch (error) {
    if (error.code === "ENOENT") {
      send(response, 404, "text/plain; charset=utf-8", "Not found");
      return;
    }
    throw error;
  }
}

async function serveMonsterImage(pathname, response) {
  const parts = pathname.split("/");
  if (parts.length !== 5) {
    send(response, 404, "text/plain; charset=utf-8", "Not found");
    return;
  }

  let bucket;
  let filename;
  try {
    bucket = decodeURIComponent(parts[3]);
    filename = decodeURIComponent(parts[4]);
  } catch {
    send(response, 400, "text/plain; charset=utf-8", "素材路径无效。");
    return;
  }

  const catalog = await getCatalog();
  if (!catalog.monsterFiles.has(`${bucket}/${filename}`)) {
    send(response, 404, "text/plain; charset=utf-8", "Not found");
    return;
  }
  const imagePath = join(monstersDirectory, bucket, filename);
  try {
    send(response, 200, "image/png", await readFile(imagePath));
  } catch (error) {
    if (error.code === "ENOENT") {
      send(response, 404, "text/plain; charset=utf-8", "Not found");
      return;
    }
    throw error;
  }
}

const server = createServer(async (request, response) => {
  try {
    const pathname = new URL(request.url, "http://localhost").pathname;
    if (request.method === "GET") {
      if (pathname === "/api/catalog") {
        const catalog = await getCatalog();
        sendJson(response, 200, { maps: catalog.maps, monsters: catalog.monsters });
        return;
      }
      if (pathname === "/api/placements") {
        sendJson(response, 200, await readPlacements());
        return;
      }
      if (/^\/assets\/maps\/\d+$/.test(pathname)) {
        await serveMapImage(Number(pathname.slice("/assets/maps/".length)), response);
        return;
      }
      if (pathname.startsWith("/assets/monsters/")) {
        await serveMonsterImage(pathname, response);
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

    if (request.method === "POST" && pathname === "/api/placements") {
      const candidate = await readRequestJson(request);
      const catalog = await getCatalog();
      const invalidReason = validatePlacements(candidate, catalog);
      if (invalidReason) {
        sendJson(response, 400, { error: invalidReason });
        return;
      }
      await writeFile(placementsFile, `${JSON.stringify(candidate, null, 2)}\n`, "utf8");
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
      send(response, 500, "text/plain; charset=utf-8", "Required editor data is missing.");
      return;
    }

    console.error("Placement editor request failed:", error);
    send(response, 500, "text/plain; charset=utf-8", "Internal server error");
  }
});

const port = Number(process.env.PORT ?? 4180);
server.listen(port, "127.0.0.1", () => {
  console.log(`Map placement editor: http://127.0.0.1:${port}`);
});
