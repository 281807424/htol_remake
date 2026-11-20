import { layoutMaps } from "./layout.js";

const board = document.querySelector("#map-board");
const scene = document.querySelector("#map-scene");
const mapNodes = document.querySelector("#map-nodes");
const regionLabels = document.querySelector("#region-labels");
const connectionLayer = document.querySelector("#connection-layer");
const regionLegend = document.querySelector("#region-legend");
const worldSummary = document.querySelector("#world-summary");
const interactionStatus = document.querySelector("#interaction-status");
const saveButton = document.querySelector("#save-button");
const autoLayoutButton = document.querySelector("#auto-layout-button");
const saveStatus = document.querySelector("#save-status");
const zoomOutButton = document.querySelector("#zoom-out");
const zoomInButton = document.querySelector("#zoom-in");
const zoomLevel = document.querySelector("#zoom-level");
const detailDialog = document.querySelector("#detail-dialog");
const dialogContent = document.querySelector("#dialog-content");

const MIN_ZOOM = 0.1;
const MAX_ZOOM = 3;
const ZOOM_STEP = 1.2;
const MAX_TELEPORT_LINK_DISTANCE = 220;
const DARK_REGION_IDS = new Set(["邪血国", "皇城"]);
const REGION_LABEL_POSITIONS = {
  玄武国: { x: 1080, y: 78 },
  邪血国: { x: 130, y: 1510 },
  玄月国: { x: 2180, y: 1510 },
  皇城: { x: 1310, y: 690 },
};

let maps = [];
let sprites = [];
let mapsById = new Map();
let spritesById = new Map();
let teleportsById = new Map();
let teleportsByMapId = new Map();
let design = null;
let zoom = 1;
let panX = 20;
let panY = 20;
let drag = null;
let dirty = false;
let suppressClickUntil = 0;
let pendingConnectionTeleportId = null;
let pendingPointerMove = null;
let pointerMoveFrame = 0;
let pendingPointerClient = null;
let editRevision = 0;

function escapeHtml(value) {
  return String(value).replace(/[&<>"']/g, (character) => ({
    "&": "&amp;",
    "<": "&lt;",
    ">": "&gt;",
    '"': "&quot;",
    "'": "&#39;",
  })[character]);
}

function isTeleportConnected(teleportId) {
  return design.connections.some((connection) =>
    connection.fromTeleportId === teleportId || connection.toTeleportId === teleportId,
  );
}

function mapPairKey(fromMapId, toMapId) {
  return [fromMapId, toMapId].sort((left, right) => left - right).join(":");
}

function setConnectionStatus(message) {
  interactionStatus.textContent = message;
}

function beginTeleportConnection(teleport, clientX, clientY) {
  if (isTeleportConnected(teleport.id)) {
    setConnectionStatus("该传送点已经连接；按住 Ctrl 并右键点击可断开");
    return;
  }
  pendingConnectionTeleportId = teleport.id;
  pendingPointerClient = { x: clientX, y: clientY };
  renderConnections();
  renderTeleportMarkers();
  updatePendingConnectionLine(clientX, clientY);
  setConnectionStatus(`已选中 ${teleportName(teleport)}，点击另一张地图的传送点完成连接；Esc 取消`);
}

function handleTeleportContextMenu(event) {
  if (event.button !== 2 || !event.ctrlKey) return;
  const marker = event.target.closest(".teleport-marker");
  if (!marker) return;
  event.preventDefault();
  event.stopPropagation();
  const teleport = teleportsById.get(marker.dataset.teleportId);
  if (!teleport) return;

  const existingConnection = design.connections.find((connection) =>
    connection.fromTeleportId === teleport.id || connection.toTeleportId === teleport.id,
  );
  if (existingConnection) {
    design.connections = design.connections.filter((connection) => connection !== existingConnection);
    pendingConnectionTeleportId = null;
    pendingPointerClient = null;
    renderConnections();
    renderTeleportMarkers();
    renderWorldSummary();
    setDirty();
    setConnectionStatus(`${teleportName(teleport)} 已断开连接`);
    return;
  }

  beginTeleportConnection(teleport, event.clientX, event.clientY);
}

function completeTeleportConnection(targetTeleportId) {
  if (!pendingConnectionTeleportId) return false;
  const fromTeleport = teleportsById.get(pendingConnectionTeleportId);
  const toTeleport = teleportsById.get(targetTeleportId);
  if (!fromTeleport || !toTeleport) return true;
  if (fromTeleport.id === toTeleport.id) {
    pendingConnectionTeleportId = null;
    pendingPointerClient = null;
    renderConnections();
    renderTeleportMarkers();
    setConnectionStatus("已取消传送点选择");
    return true;
  }
  if (fromTeleport.mapId === toTeleport.mapId) {
    setConnectionStatus("请选择另一张地图上的传送点");
    return true;
  }
  if (isTeleportConnected(toTeleport.id)) {
    setConnectionStatus("该传送点已经连接；按住 Ctrl 并右键点击可断开");
    return true;
  }

  const fromMap = mapsById.get(fromTeleport.mapId);
  const toMap = mapsById.get(toTeleport.mapId);
  if (!fromMap || !toMap) {
    setConnectionStatus("连接失败：找不到传送点所属地图");
    return true;
  }
  if (design.connections.some((connection) =>
    mapPairKey(connection.fromMapId, connection.toMapId) === mapPairKey(fromMap.mapId, toMap.mapId),
  )) {
    setConnectionStatus("这两张地图已经连接；每对地图只能建立一条传送点连线");
    return true;
  }
  const connection = {
    id: crypto.randomUUID(),
    fromMapId: fromMap.mapId,
    toMapId: toMap.mapId,
    fromTeleportId: fromTeleport.id,
    toTeleportId: toTeleport.id,
  };
  const constraintError = connectionConstraintError(connection);
  if (constraintError) {
    setConnectionStatus(`无法连接：${constraintError}`);
    return true;
  }
  design.connections.push(connection);
  pendingConnectionTeleportId = null;
  pendingPointerClient = null;
  renderConnections();
  renderTeleportMarkers();
  renderWorldSummary();
  setDirty();
  setConnectionStatus(`${fromMap.key} ${teleportName(fromTeleport)} ↔ ${toMap.key} ${teleportName(toTeleport)}`);
  return true;
}

function cancelPendingConnection() {
  if (!pendingConnectionTeleportId) return;
  pendingConnectionTeleportId = null;
  pendingPointerClient = null;
  renderConnections();
  renderTeleportMarkers();
  setConnectionStatus("已取消待连接传送点");
}

function assetImage(url) {
  return url.split("/").map((part, index) => index === 0 ? "" : encodeURIComponent(decodeURIComponent(part))).join("/");
}

function teleportScenePosition(map, teleport) {
  const previewInsetX = map.kind === "野外地图" ? 3 : 4;
  const previewInsetY = map.kind === "野外地图" ? 2 : 3;
  const previewWidth = map.width - previewInsetX * 2;
  const previewHeight = 34;
  const scale = Math.min(previewWidth / map.imageWidth, previewHeight / map.imageHeight);
  const renderedWidth = map.imageWidth * scale;
  const renderedHeight = map.imageHeight * scale;
  return {
    x: map.x + previewInsetX + (previewWidth - renderedWidth) / 2 + teleport.x * scale,
    y: map.y + previewInsetY + (previewHeight - renderedHeight) / 2 + teleport.y * scale,
  };
}

function setDirty() {
  dirty = true;
  editRevision += 1;
  saveStatus.textContent = "有未保存更改";
}

function setZoom(nextZoom, clientX, clientY) {
  const next = Math.min(MAX_ZOOM, Math.max(MIN_ZOOM, nextZoom));
  if (next === zoom) return;
  const rect = board.getBoundingClientRect();
  const pointX = clientX === undefined ? rect.width / 2 : clientX - rect.left;
  const pointY = clientY === undefined ? rect.height / 2 : clientY - rect.top;
  const worldX = (pointX - panX) / zoom;
  const worldY = (pointY - panY) / zoom;
  panX = pointX - worldX * next;
  panY = pointY - worldY * next;
  zoom = next;
  renderTransform();
  if (pendingConnectionTeleportId && pendingPointerClient) {
    updatePendingConnectionLine(pendingPointerClient.x, pendingPointerClient.y);
  }
}

function renderTransform() {
  scene.style.transform = `translate(${panX}px, ${panY}px) scale(${zoom})`;
  zoomLevel.textContent = `${Math.round(zoom * 100)}%`;
}

function fitWorld() {
  if (!design) return;
  const width = design.layout.width;
  const height = design.layout.height;
  zoom = Math.min(
    MAX_ZOOM,
    Math.max(MIN_ZOOM, Math.min((board.clientWidth - 48) / width, (board.clientHeight - 48) / height)),
  );
  panX = Math.max(12, (board.clientWidth - width * zoom) / 2);
  panY = Math.max(12, (board.clientHeight - height * zoom) / 2);
  renderTransform();
  if (pendingConnectionTeleportId && pendingPointerClient) {
    updatePendingConnectionLine(pendingPointerClient.x, pendingPointerClient.y);
  }
}

function renderRegionLegend() {
  regionLegend.replaceChildren();
  for (const region of design.regions) {
    const item = document.createElement("span");
    item.className = "legend-item";
    item.innerHTML = `<i style="--region-color:${escapeHtml(region.color)}"></i>${escapeHtml(region.label)}`;
    regionLegend.append(item);
  }
}

function renderRegionLabels() {
  regionLabels.replaceChildren();
  for (const [index, region] of design.regions.entries()) {
    const position = design.maps.length === 1
      ? { x: design.maps[0].x, y: design.maps[0].y }
      : REGION_LABEL_POSITIONS[region.id] ?? { x: 16, y: 16 + index * 34 };
    const label = document.createElement("div");
    label.className = `region-label region-${region.id === "皇城" ? "capital" : "country"}`;
    label.textContent = region.label;
    label.style.left = `${position.x}px`;
    label.style.top = `${position.y - 34}px`;
    label.style.setProperty("--region-color", region.color);
    regionLabels.append(label);
  }
}

function teleportName(teleport) {
  return teleport ? `${teleport.direction}传送点` : "传送点";
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

function connectionConstraintError(connection) {
  const from = mapsById.get(connection.fromMapId);
  const to = mapsById.get(connection.toMapId);
  const fromTeleport = teleportsById.get(connection.fromTeleportId);
  const toTeleport = teleportsById.get(connection.toTeleportId);
  if (!from || !to || !fromTeleport || !toTeleport) return "连接点或地图不存在";

  const start = teleportScenePosition(from, fromTeleport);
  const end = teleportScenePosition(to, toTeleport);
  if (Math.hypot(end.x - start.x, end.y - start.y) > MAX_TELEPORT_LINK_DISTANCE) {
    return "传送点距离过远，请先调整地图位置";
  }
  for (const map of mapsById.values()) {
    if (
      map.mapId !== from.mapId &&
      map.mapId !== to.mapId &&
      segmentIntersectsMap(start, end, map)
    ) {
      return "连线会穿过其他地图，请调整地图位置";
    }
  }
  return null;
}

function createConnectionLine(connection, className = "connection") {
  const from = mapsById.get(connection.fromMapId);
  const to = mapsById.get(connection.toMapId);
  const fromTeleport = teleportsById.get(connection.fromTeleportId);
  const toTeleport = teleportsById.get(connection.toTeleportId);
  if (!from || !to || !fromTeleport || !toTeleport) return null;
  if (connectionConstraintError(connection)) return null;
  const start = teleportScenePosition(from, fromTeleport);
  const end = teleportScenePosition(to, toTeleport);
  const line = document.createElementNS("http://www.w3.org/2000/svg", "line");
  line.setAttribute("x1", String(start.x));
  line.setAttribute("y1", String(start.y));
  line.setAttribute("x2", String(end.x));
  line.setAttribute("y2", String(end.y));
  line.setAttribute("class", className);
  return line;
}

function getPendingConnectionLine() {
  return connectionLayer.querySelector(".connection-pending");
}

function updatePendingConnectionLine(clientX, clientY) {
  const teleport = teleportsById.get(pendingConnectionTeleportId);
  if (!teleport) return;
  const map = mapsById.get(teleport.mapId);
  if (!map) return;
  const point = teleportScenePosition(map, teleport);
  let line = getPendingConnectionLine();
  if (!line) {
    line = document.createElementNS("http://www.w3.org/2000/svg", "line");
    line.setAttribute("class", "connection connection-pending");
    connectionLayer.append(line);
  }
  const rect = board.getBoundingClientRect();
  const targetX = (clientX - rect.left - panX) / zoom;
  const targetY = (clientY - rect.top - panY) / zoom;
  line.setAttribute("x1", String(point.x));
  line.setAttribute("y1", String(point.y));
  line.setAttribute("x2", String(targetX));
  line.setAttribute("y2", String(targetY));
}

function renderConnections() {
  connectionLayer.setAttribute("viewBox", `0 0 ${design.layout.width} ${design.layout.height}`);
  connectionLayer.setAttribute("width", String(design.layout.width));
  connectionLayer.setAttribute("height", String(design.layout.height));
  connectionLayer.replaceChildren();
  const defs = document.createElementNS("http://www.w3.org/2000/svg", "defs");
  const endpoint = document.createElementNS("http://www.w3.org/2000/svg", "marker");
  endpoint.id = "teleport-connection-endpoint";
  endpoint.setAttribute("markerWidth", "10");
  endpoint.setAttribute("markerHeight", "10");
  endpoint.setAttribute("markerUnits", "userSpaceOnUse");
  endpoint.setAttribute("refX", "5");
  endpoint.setAttribute("refY", "5");
  endpoint.setAttribute("orient", "auto");
  const endpointDot = document.createElementNS("http://www.w3.org/2000/svg", "circle");
  endpointDot.setAttribute("cx", "5");
  endpointDot.setAttribute("cy", "5");
  endpointDot.setAttribute("r", "4");
  endpointDot.setAttribute("class", "connection-endpoint");
  endpoint.append(endpointDot);
  defs.append(endpoint);
  connectionLayer.append(defs);
  for (const connection of design.connections) {
    const line = createConnectionLine(connection);
    if (!line) continue;
    line.dataset.connectionId = connection.id;
    line.dataset.fromMapId = String(connection.fromMapId);
    line.dataset.toMapId = String(connection.toMapId);
    line.setAttribute("aria-label", `${teleportName(teleportsById.get(connection.fromTeleportId))} → ${teleportName(teleportsById.get(connection.toTeleportId))}`);
    line.setAttribute("marker-start", "url(#teleport-connection-endpoint)");
    line.setAttribute("marker-end", "url(#teleport-connection-endpoint)");
    connectionLayer.append(line);
  }
}

function updateConnectionsForMap(mapId) {
  for (const line of connectionLayer.querySelectorAll(".connection[data-connection-id]")) {
    if (Number(line.dataset.fromMapId) !== mapId && Number(line.dataset.toMapId) !== mapId) continue;
    const connection = design.connections.find((item) => item.id === line.dataset.connectionId);
    const updatedLine = connection && createConnectionLine(connection);
    if (!updatedLine) {
      line.setAttribute("visibility", "hidden");
      continue;
    }
    line.removeAttribute("visibility");
    line.setAttribute("x1", updatedLine.getAttribute("x1"));
    line.setAttribute("y1", updatedLine.getAttribute("y1"));
    line.setAttribute("x2", updatedLine.getAttribute("x2"));
    line.setAttribute("y2", updatedLine.getAttribute("y2"));
  }
  if (pendingConnectionTeleportId && teleportsById.get(pendingConnectionTeleportId)?.mapId === mapId && pendingPointerClient) {
    updatePendingConnectionLine(pendingPointerClient.x, pendingPointerClient.y);
  }
}

function renderTeleportMarkers() {
  for (const node of mapNodes.querySelectorAll(".map-node")) {
    const mapId = Number(node.dataset.mapId);
    const map = mapsById.get(mapId);
    const layer = node.querySelector(".teleport-markers");
    layer.replaceChildren();
    for (const teleport of teleportsByMapId.get(mapId) ?? []) {
      const point = teleportScenePosition(map, teleport);
      const marker = document.createElement("span");
      marker.className = "teleport-marker";
      marker.dataset.teleportId = teleport.id;
      marker.dataset.mapId = String(mapId);
      marker.title = `${map.key} · ${teleportName(teleport)}`;
      marker.setAttribute("aria-label", marker.title);
      marker.style.left = `${point.x - map.x - (map.kind === "野外地图" ? 3 : 4)}px`;
      marker.style.top = `${point.y - map.y - (map.kind === "野外地图" ? 2 : 3)}px`;
      marker.textContent = ({
        东: "→",
        东北: "↗",
        北: "↑",
        西北: "↖",
        西: "←",
        西南: "↙",
        南: "↓",
        东南: "↘",
      })[teleport.direction];
      marker.classList.toggle("connected", design.connections.some((connection) =>
        connection.fromTeleportId === teleport.id || connection.toTeleportId === teleport.id,
      ));
      marker.classList.toggle("pending", teleport.id === pendingConnectionTeleportId);
      layer.append(marker);
    }
  }
}

function renderMapNodes() {
  mapNodes.replaceChildren();
  for (const map of design.maps) {
    const teleports = teleportsByMapId.get(map.mapId) ?? [];
    const node = document.createElement("div");
    node.className = `map-node ${map.kind === "野外地图" ? "field-node" : "city-node"} ${map.hasBuildings ? "outpost-node" : ""}`;
    node.setAttribute("role", "group");
    node.setAttribute("aria-label", `${map.key} · ${map.name}`);
    node.dataset.mapId = String(map.mapId);
    node.style.left = `${map.x}px`;
    node.style.top = `${map.y}px`;
    node.style.width = `${map.width}px`;
    node.style.height = `${map.height}px`;
    const region = design.regions.find((item) => item.id === map.region);
    node.style.setProperty("--node-color", region?.color ?? "#777");
    node.style.setProperty("--node-text-color", DARK_REGION_IDS.has(map.region) ? "#fff" : "#202720");
    node.title = `${map.key} · ${map.name} · ${map.category} · ${teleports.length} 个传送点`;
    const preview = document.createElement("div");
    preview.className = "map-preview";
    const image = document.createElement("img");
    image.src = `/thumbnails/${map.mapId}`;
    image.alt = map.key;
    image.draggable = false;
    image.decoding = "async";
    image.loading = "eager";
    const teleportLayer = document.createElement("div");
    teleportLayer.className = "teleport-markers";
    preview.append(image, teleportLayer);
    const label = document.createElement("span");
    label.className = "node-label";
    label.textContent = map.kind === "野外地图" ? map.key : map.name;
    const sublabel = document.createElement("span");
    sublabel.className = "node-sublabel";
    sublabel.textContent = map.kind === "野外地图"
      ? `${map.category}${map.hasBuildings ? " · 据点" : ""}`
      : `${map.kind} · ${teleports.length}点`;
    const imageButton = document.createElement("button");
    imageButton.className = "map-image-button";
    imageButton.type = "button";
    imageButton.textContent = "大图";
    imageButton.title = "查看地图大图";
    imageButton.setAttribute("aria-label", `查看 ${map.key} 地图大图`);
    node.append(preview, label, sublabel, imageButton);
    mapNodes.append(node);
  }
  renderTeleportMarkers();
  renderConnections();
}

function renderWorldSummary() {
  if (design.maps.length === 1) {
    const map = design.maps[0];
    const teleportCount = teleportsByMapId.get(map.mapId)?.length ?? 0;
    worldSummary.textContent = `Demo 首张地图 ${map.key} · ${teleportCount} 个传送点`;
    return;
  }
  const { countryMapCounts, imperialCityMapCount } = design.summary;
  const maximumTeleports = Math.max(0, ...[...teleportsByMapId.values()].map((items) => items.length));
  design.summary.connectionCount = design.connections.length;
  design.summary.maximumTeleportsOnAnyMap = maximumTeleports;
  const unconnectedTeleportCount = teleportsById.size - design.connections.length * 2;
  worldSummary.textContent = `${countryMapCounts["玄武国"]} / ${countryMapCounts["邪血国"]} / ${countryMapCounts["玄月国"]} 张国家地图 · 皇城 ${imperialCityMapCount} 张 · ${design.connections.length} 条连接 · 未连接传送点 ${unconnectedTeleportCount} 个 · 单图最多 ${maximumTeleports} 点`;
}

function renderObjectRows(map) {
  return map.objects.map((object) => `
    <tr>
      <td>${object.objectIndex}</td>
      <td><button class="inline-reference" data-kind="sprite" data-id="${object.spriteResourceId}">${escapeHtml(object.label)} (#${object.spriteResourceId})</button></td>
      <td>${object.sourceXUnits ?? "—"}, ${object.sourceYUnits ?? "—"}</td>
      <td>${object.renderXPx ?? "—"}, ${object.renderYPx ?? "—"}</td>
      <td>${escapeHtml(object.frameFile?.split("/").pop() ?? "—")}</td>
    </tr>`).join("");
}

function renderMapDetails(map, modal = false) {
  const node = mapsById.get(map.mapId);
  const connectionList = design.connections.filter((connection) =>
    connection.fromMapId === map.mapId || connection.toMapId === map.mapId,
  ).map((connection) => {
    const isFrom = connection.fromMapId === map.mapId;
    const ownTeleport = teleportsById.get(isFrom ? connection.fromTeleportId : connection.toTeleportId);
    const targetTeleport = teleportsById.get(isFrom ? connection.toTeleportId : connection.fromTeleportId);
    const targetMapId = isFrom ? connection.toMapId : connection.fromMapId;
    return `<li>${escapeHtml(teleportName(ownTeleport))} → <button class="inline-reference" data-kind="map" data-id="${targetMapId}">map_${String(targetMapId).padStart(3, "0")}</button>（${escapeHtml(teleportName(targetTeleport))}）</li>`;
  }).join("");
  const objectSummary = map.objectSummary.map((object) =>
    `<li><button class="inline-reference" data-kind="sprite" data-id="${object.spriteResourceId}">${escapeHtml(object.label)} (#${object.spriteResourceId})</button> ×${object.count}</li>`,
  ).join("");
  const objectsTable = modal
    ? `<div class="instance-table-wrap"><table class="instance-table">
        <thead><tr><th>序号</th><th>物件</th><th>源地图坐标（单位）</th><th>渲染坐标（px）</th><th>帧文件</th></tr></thead>
        <tbody>${renderObjectRows(map)}</tbody>
      </table></div>`
    : "";
  return `
    <article class="detail-card">
      <img class="detail-map-image" src="${escapeHtml(assetImage(map.image))}" alt="${escapeHtml(map.key)} 地图预览" />
      <h2>${escapeHtml(map.key)}${node?.kind && node.kind !== "野外地图" ? ` · ${escapeHtml(node.name)}` : ""}</h2>
      <p class="detail-category">${escapeHtml(map.sourceCategory)}${map.includedInWorld ? "" : " · 暂不用于世界地图"}</p>
      <p>${escapeHtml(map.visualDescription)}</p>
      <dl class="detail-facts">
        <div><dt>地图格数</dt><dd>${map.widthCells} × ${map.heightCells}</dd></div>
        <div><dt>地貌 / 植被</dt><dd>${escapeHtml(map.ground)} / ${escapeHtml(map.vegetation)}</dd></div>
        <div><dt>地图物件</dt><dd>${map.entityCount} 个实例，${map.objectSummary.length} 种精灵</dd></div>
        <div><dt>建筑</dt><dd>${map.hasBuildings ? `${map.buildingObjectCount} 个建筑类实例` : "未识别到建筑类精灵"}</dd></div>
        ${node ? `<div><dt>世界区域 / 传送点</dt><dd>${escapeHtml(node.region)} / ${teleportsByMapId.get(node.mapId)?.length ?? 0} 个</dd></div>` : ""}
      </dl>
      ${connectionList ? `<h3>世界连接与边缘位置</h3><ul class="detail-list">${connectionList}</ul>` : ""}
      <h3>物件类别与数量</h3><ul class="detail-list object-summary">${objectSummary}</ul>
      ${modal ? `<h3>逐实例位置（源坐标与渲染像素）</h3>${objectsTable}` : ""}
    </article>`;
}

function renderSpriteDetails(sprite, modal = false) {
  const files = modal
    ? `<details class="frame-files"><summary>全部 ${sprite.fileCount} 个帧/预览文件</summary>
        <ul>${sprite.files.map((file) => `<li><a href="${escapeHtml(assetImage(file))}" target="_blank" rel="noreferrer">${escapeHtml(file.split("/").pop())}</a></li>`).join("")}</ul>
      </details>`
    : "";
  return `
    <article class="detail-card">
      <img class="detail-sprite-image" src="${escapeHtml(assetImage(sprite.overviewImage))}" alt="${escapeHtml(sprite.label)} 物件预览" />
      <h2>sprite_${sprite.spriteResourceId} · ${escapeHtml(sprite.label)}</h2>
      <p>${escapeHtml(sprite.visualDescription)}</p>
      <dl class="detail-facts">
        <div><dt>识别置信度</dt><dd>${escapeHtml(sprite.confidence)}</dd></div>
        <div><dt>地图实例</dt><dd>${sprite.mapInstanceCount} 个，分布于 ${sprite.mapCount} 张地图</dd></div>
        <div><dt>帧与预览文件</dt><dd>${sprite.fileCount} 个</dd></div>
      </dl>
      ${files}
    </article>`;
}

function showMapDetails(mapId) {
  const map = mapsById.get(Number(mapId));
  if (!map) return;
  detailDialog.classList.remove("map-image-dialog");
  dialogContent.innerHTML = renderMapDetails(map, true);
  detailDialog.showModal();
}

function showMapImage(mapId) {
  const map = mapsById.get(Number(mapId));
  if (!map) return;
  detailDialog.classList.add("map-image-dialog");
  dialogContent.innerHTML = `
    <figure class="map-image-preview">
      <figcaption>${escapeHtml(map.key)}${map.kind === "野外地图" ? "" : ` · ${escapeHtml(map.name)}`}</figcaption>
      <img src="${escapeHtml(assetImage(map.image))}" alt="${escapeHtml(map.key)} 地图大图" />
    </figure>`;
  detailDialog.showModal();
}

function showSpriteDetails(spriteId) {
  const sprite = spritesById.get(Number(spriteId));
  if (!sprite) return;
  detailDialog.classList.remove("map-image-dialog");
  dialogContent.innerHTML = renderSpriteDetails(sprite, true);
  detailDialog.showModal();
}

function updateNodePosition(map, x, y) {
  map.x = Math.round(x);
  map.y = Math.round(y);
  const savedMap = design.maps.find((entry) => entry.mapId === map.mapId);
  if (savedMap) {
    savedMap.x = map.x;
    savedMap.y = map.y;
  }
  const node = mapNodes.querySelector(`[data-map-id="${map.mapId}"]`);
  if (node) {
    node.style.left = `${map.x}px`;
    node.style.top = `${map.y}px`;
  }
  updateConnectionsForMap(map.mapId);
}

function markSaved() {
  dirty = false;
  saveStatus.textContent = "已保存到 world_map.json";
}

async function applyConnectionLayout() {
  if (pendingConnectionTeleportId) {
    setConnectionStatus("请先完成或取消当前传送点连线，再进行排版");
    return;
  }

  autoLayoutButton.disabled = true;
  setConnectionStatus("正在根据传送点连接计算地图位置…");
  await new Promise((resolve) => setTimeout(resolve, 0));
  try {
    const result = layoutMaps(
      [...mapsById.values()],
      design.connections,
      teleportsById,
      design.regions.map((region) => region.id),
      design.layout.width,
      design.layout.height,
    );
    for (const map of mapsById.values()) {
      const position = result.positions.get(map.mapId);
      map.x = position.x;
      map.y = position.y;
      const savedMap = design.maps.find((entry) => entry.mapId === map.mapId);
      savedMap.x = position.x;
      savedMap.y = position.y;
    }
    design.layout.width = result.width;
    design.layout.height = result.height;
    scene.style.width = `${result.width}px`;
    scene.style.height = `${result.height}px`;
    renderMapNodes();
    renderRegionLabels();
    fitWorld();
    setDirty();
    const optimizationNote = result.optimizationFraction < 0.98
      ? "为保持连线有效，部分位置调整幅度已自动收敛"
      : "已按连接方向优化地图位置";
    const overlapNote = result.overlapCount
      ? `，仍有 ${result.overlapCount} 处地图重叠`
      : "，地图卡片无重叠";
    setConnectionStatus(
      `${optimizationNote}；未连接地图 ${result.unconnectedMapCount} 张置于区域外围${overlapNote}。检查后点击“保存配置”`,
    );
  } catch (error) {
    setConnectionStatus(`自动排版失败：${error.message}`);
    console.error("Could not arrange maps from teleport connections:", error);
  } finally {
    autoLayoutButton.disabled = false;
  }
}

async function saveDesign() {
  saveButton.disabled = true;
  saveStatus.textContent = "保存中…";
  const revision = editRevision;
  try {
    const response = await fetch("/api/design", {
      method: "POST",
      headers: { "content-type": "application/json" },
      body: JSON.stringify(design),
    });
    const result = await response.json();
    if (!response.ok) throw new Error(result.error ?? `保存请求失败：${response.status}`);
    if (revision === editRevision) markSaved();
    else {
      dirty = true;
      saveStatus.textContent = "当前快照已保存，仍有更新尚未保存";
    }
  } catch (error) {
    saveStatus.textContent = `保存失败：${error.message}`;
    console.error("Could not save the world map design:", error);
  } finally {
    saveButton.disabled = false;
  }
}

function startPointerAction(event) {
  if (event.button !== 0) return;
  if (event.target.closest(".teleport-marker, .map-image-button")) return;
  const mapElement = event.target.closest(".map-node");
  if (mapElement) {
    const map = mapsById.get(Number(mapElement.dataset.mapId));
    if (!map) return;
    drag = {
      kind: "map",
      map,
      pointerId: event.pointerId,
      startX: event.clientX,
      startY: event.clientY,
      originalX: map.x,
      originalY: map.y,
      moved: false,
    };
  } else if (board.contains(event.target)) {
    drag = {
      kind: "pan",
      pointerId: event.pointerId,
      startX: event.clientX,
      startY: event.clientY,
      originalX: panX,
      originalY: panY,
      moved: false,
    };
  }
  if (!drag) return;
  board.setPointerCapture(event.pointerId);
  event.preventDefault();
}

function movePointerAction(event) {
  if (pendingConnectionTeleportId) {
    pendingPointerClient = { x: event.clientX, y: event.clientY };
    updatePendingConnectionLine(event.clientX, event.clientY);
  }
  if (!drag || drag.pointerId !== event.pointerId) return;
  const deltaX = event.clientX - drag.startX;
  const deltaY = event.clientY - drag.startY;
  if (!drag.moved && Math.hypot(deltaX, deltaY) < 4) return;
  drag.moved = true;
  if (drag.kind === "pan") {
    panX = drag.originalX + deltaX;
    panY = drag.originalY + deltaY;
    renderTransform();
  } else {
    updateNodePosition(drag.map, drag.originalX + deltaX / zoom, drag.originalY + deltaY / zoom);
  }
  if (pendingConnectionTeleportId && pendingPointerClient) {
    updatePendingConnectionLine(pendingPointerClient.x, pendingPointerClient.y);
  }
}

function queuePointerMove(event) {
  pendingPointerMove = event;
  if (pointerMoveFrame) return;
  pointerMoveFrame = requestAnimationFrame(() => {
    pointerMoveFrame = 0;
    const latestEvent = pendingPointerMove;
    pendingPointerMove = null;
    if (latestEvent) movePointerAction(latestEvent);
  });
}

function endPointerAction(event) {
  if (!drag || drag.pointerId !== event.pointerId) return;
  if (pointerMoveFrame) {
    cancelAnimationFrame(pointerMoveFrame);
    pointerMoveFrame = 0;
    pendingPointerMove = null;
    movePointerAction(event);
  }
  if (drag.moved) {
    if (drag.kind === "map") setDirty();
    suppressClickUntil = Date.now() + 200;
  }
  drag = null;
}

function attachEvents() {
  dialogContent.addEventListener("click", (event) => {
    const item = event.target.closest(".inline-reference");
    if (!item) return;
    showRecordInDialog(item.dataset.kind, item.dataset.id);
  });
  mapNodes.addEventListener("click", (event) => {
    if (Date.now() < suppressClickUntil) return;
    const imageButton = event.target.closest(".map-image-button");
    if (imageButton) {
      event.preventDefault();
      event.stopPropagation();
      showMapImage(imageButton.closest(".map-node")?.dataset.mapId);
      return;
    }
    const marker = event.target.closest(".teleport-marker");
    if (marker) {
      if (pendingConnectionTeleportId) completeTeleportConnection(marker.dataset.teleportId);
      else {
        const teleport = teleportsById.get(marker.dataset.teleportId);
        if (teleport) beginTeleportConnection(teleport, event.clientX, event.clientY);
      }
      return;
    }
    const item = event.target.closest(".map-node");
    if (item) showMapDetails(item.dataset.mapId);
  });
  mapNodes.addEventListener("contextmenu", handleTeleportContextMenu);
  board.addEventListener("pointerdown", startPointerAction);
  window.addEventListener("pointermove", queuePointerMove);
  window.addEventListener("pointerup", endPointerAction);
  window.addEventListener("pointercancel", endPointerAction);
  board.addEventListener("wheel", (event) => {
    event.preventDefault();
    setZoom(zoom * (event.deltaY < 0 ? ZOOM_STEP : 1 / ZOOM_STEP), event.clientX, event.clientY);
  }, { passive: false });
  zoomInButton.addEventListener("click", () => setZoom(zoom * ZOOM_STEP));
  zoomOutButton.addEventListener("click", () => setZoom(zoom / ZOOM_STEP));
  autoLayoutButton.addEventListener("click", applyConnectionLayout);
  saveButton.addEventListener("click", saveDesign);
  window.addEventListener("resize", fitWorld);
  window.addEventListener("keydown", (event) => {
    if (event.key === "Escape" && pendingConnectionTeleportId) cancelPendingConnection();
  });
}

function showRecordInDialog(kind, id) {
  if (kind === "map") showMapDetails(id);
  else showSpriteDetails(id);
}

async function start() {
  try {
    const [catalogResponse, designResponse, teleportResponse] = await Promise.all([
      fetch("/api/catalog"),
      fetch("/api/design"),
      fetch("/api/teleports"),
    ]);
    if (!catalogResponse.ok) throw new Error(`素材档案加载失败：${catalogResponse.status}`);
    if (!designResponse.ok) throw new Error(`世界地图配置加载失败：${designResponse.status}`);
    if (!teleportResponse.ok) throw new Error(`传送点布置数据加载失败：${teleportResponse.status}`);
    const catalog = await catalogResponse.json();
    const placedTeleports = await teleportResponse.json();
    maps = catalog.maps.maps;
    sprites = catalog.objects.sprites;
    design = await designResponse.json();
    scene.style.width = `${design.layout.width}px`;
    scene.style.height = `${design.layout.height}px`;
    spritesById = new Map(sprites.map((sprite) => [sprite.spriteResourceId, sprite]));
    mapsById = new Map();
    for (const map of design.maps) {
      const catalogMap = maps.find((record) => record.mapId === map.mapId);
      if (!catalogMap) throw new Error(`地图 ${map.mapId} 缺少图像尺寸档案`);
      mapsById.set(map.mapId, {
        ...catalogMap,
        ...map,
        imageWidth: catalogMap.widthCells * catalogMap.renderCellPx,
        imageHeight: catalogMap.heightCells * catalogMap.renderCellPx,
      });
    }
    if (!Array.isArray(placedTeleports.maps)) throw new Error("传送点布置数据格式无效");
    teleportsById = new Map();
    teleportsByMapId = new Map();
    for (const entry of placedTeleports.maps) {
      const map = mapsById.get(entry.mapId);
      if (!Array.isArray(entry.teleports)) {
        throw new Error(`地图 ${entry.mapId} 的传送点数据无效`);
      }
      if (!map) continue;
      for (const teleport of entry.teleports) {
        if (
          !teleport ||
          typeof teleport.id !== "string" ||
          !teleport.id ||
          !["东", "东北", "北", "西北", "西", "西南", "南", "东南"].includes(teleport.direction) ||
          !Number.isFinite(teleport.x) ||
          !Number.isFinite(teleport.y) ||
          teleport.x < 0 ||
          teleport.y < 0 ||
          teleport.x > map.imageWidth ||
          teleport.y > map.imageHeight ||
          teleportsById.has(teleport.id)
        ) {
          throw new Error(`地图 ${map.key} 含有无效或重复的传送点`);
        }
        const placedTeleport = { ...teleport, mapId: map.mapId };
        teleportsById.set(teleport.id, placedTeleport);
        if (!teleportsByMapId.has(map.mapId)) teleportsByMapId.set(map.mapId, []);
        teleportsByMapId.get(map.mapId).push(placedTeleport);
      }
    }

    renderWorldSummary();
    renderRegionLegend();
    renderRegionLabels();
    renderMapNodes();
    attachEvents();
    requestAnimationFrame(fitWorld);
    markSaved();
  } catch (error) {
    saveStatus.textContent = `加载失败：${error.message}`;
    console.error("Could not load the world map workspace:", error);
  }
}

start();
