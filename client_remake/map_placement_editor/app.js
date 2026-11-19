const directionList = document.querySelector("#direction-list");
const monsterList = document.querySelector("#monster-list");
const monsterSearch = document.querySelector("#monster-search");
const mapSelect = document.querySelector("#map-select");
const mapCounter = document.querySelector("#map-counter");
const mapTitle = document.querySelector("#map-title");
const mapDescription = document.querySelector("#map-description");
const mapViewport = document.querySelector("#map-viewport");
const mapCanvas = document.querySelector("#map-canvas");
const mapImage = document.querySelector("#map-image");
const placementsLayer = document.querySelector("#placements-layer");
const previousMapButton = document.querySelector("#previous-map");
const nextMapButton = document.querySelector("#next-map");
const zoomOutButton = document.querySelector("#zoom-out");
const zoomInButton = document.querySelector("#zoom-in");
const zoomLevel = document.querySelector("#zoom-level");
const saveButton = document.querySelector("#save-button");
const saveStatus = document.querySelector("#save-status");
const selectedInfo = document.querySelector("#selected-info");
const inspector = document.querySelector("#element-inspector");

const DIRECTIONS = [
  { name: "东", arrow: "→", row: 2, column: 3 },
  { name: "东北", arrow: "↗", row: 1, column: 3 },
  { name: "北", arrow: "↑", row: 1, column: 2 },
  { name: "西北", arrow: "↖", row: 1, column: 1 },
  { name: "西", arrow: "←", row: 2, column: 1 },
  { name: "西南", arrow: "↙", row: 3, column: 1 },
  { name: "南", arrow: "↓", row: 3, column: 2 },
  { name: "东南", arrow: "↘", row: 3, column: 3 },
];
const directionNames = new Set(DIRECTIONS.map((direction) => direction.name));

let maps = [];
let monsters = [];
let monstersByKey = new Map();
let placementsByMapId = new Map();
let currentMapIndex = 0;
let selectedElement = null;
let dragState = null;
let zoomMultiplier = 1;
let baseScale = 1;
let scale = 1;
let dirty = false;
let changeVersion = 0;

function escapeHtml(value) {
  return String(value).replace(/[&<>"']/g, (character) => ({
    "&": "&amp;",
    "<": "&lt;",
    ">": "&gt;",
    '"': "&quot;",
    "'": "&#39;",
  })[character]);
}

function monsterKey(monster) {
  return `${monster.actionCount}:${monster.spriteId}:${monster.name}`;
}

function selectedMap() {
  return maps[currentMapIndex];
}

function currentPlacements() {
  const map = selectedMap();
  if (!placementsByMapId.has(map.mapId)) {
    placementsByMapId.set(map.mapId, { mapId: map.mapId, teleports: [], monsters: [] });
  }
  return placementsByMapId.get(map.mapId);
}

function setDirty() {
  dirty = true;
  changeVersion += 1;
  saveStatus.textContent = "有未保存更改";
}

function setDragData(event, item) {
  const payload = JSON.stringify(item);
  event.dataTransfer.setData("application/json", payload);
  event.dataTransfer.setData("text/plain", payload);
}

function renderDirections() {
  directionList.replaceChildren();
  for (const direction of DIRECTIONS) {
    const card = document.createElement("button");
    card.type = "button";
    card.draggable = true;
    card.className = "direction-card";
    card.dataset.direction = direction.name;
    card.style.gridRow = String(direction.row);
    card.style.gridColumn = String(direction.column);
    card.innerHTML = `<span class="direction-arrow">${direction.arrow}</span><span class="direction-label">${direction.name}</span>`;
    card.addEventListener("dragstart", (event) => {
      setDragData(event, {
        type: "teleport",
        direction: direction.name,
      });
      event.dataTransfer.effectAllowed = "copy";
    });
    directionList.append(card);
  }
}

function renderMonsters(query = "") {
  const normalizedQuery = query.trim().toLocaleLowerCase();
  const filtered = monsters.filter((monster) =>
    monster.name.toLocaleLowerCase().includes(normalizedQuery) ||
    String(monster.spriteId).includes(normalizedQuery),
  );
  const grouped = new Map();
  for (const monster of filtered) {
    if (!grouped.has(monster.actionCount)) grouped.set(monster.actionCount, []);
    grouped.get(monster.actionCount).push(monster);
  }

  monsterList.replaceChildren();
  for (const [actionCount, group] of grouped) {
    const section = document.createElement("section");
    section.className = "monster-group";
    const heading = document.createElement("h3");
    heading.textContent = `${actionCount} 个动作 · ${group.length} 种`;
    const items = document.createElement("div");
    items.className = "monster-items";
    for (const monster of group) {
      const card = document.createElement("button");
      card.type = "button";
      card.draggable = true;
      card.className = "monster-card";
      card.innerHTML = `
        <img src="${escapeHtml(monster.previewUrl)}" alt="" draggable="false" />
        <span class="monster-card-copy">
          <strong>${escapeHtml(monster.name)}</strong>
          <small>sprite ${monster.spriteId}</small>
        </span>`;
      card.addEventListener("dragstart", (event) => {
          setDragData(event, {
          type: "monster",
          spriteId: monster.spriteId,
          name: monster.name,
          actionCount: monster.actionCount,
          });
        event.dataTransfer.effectAllowed = "copy";
      });
      items.append(card);
    }
    section.append(heading, items);
    monsterList.append(section);
  }
  if (filtered.length === 0) {
    monsterList.innerHTML = '<p class="empty-list">没有匹配的动态怪物。</p>';
  }
}

function populateMapSelect() {
  mapSelect.replaceChildren();
  for (const map of maps) {
    const option = document.createElement("option");
    option.value = String(map.mapId);
    option.textContent = `${map.key} · ${map.name}`;
    mapSelect.append(option);
  }
}

function getBaseScale(map) {
  const horizontalSpace = Math.max(100, mapViewport.clientWidth - 48);
  const verticalSpace = Math.max(100, mapViewport.clientHeight - 48);
  return Math.max(0.08, Math.min(1, horizontalSpace / map.width, verticalSpace / map.height));
}

function findSelectedElement() {
  if (!selectedElement) return null;
  const data = currentPlacements();
  const entries = selectedElement.type === "teleport" ? data.teleports : data.monsters;
  const element = entries.find((entry) => entry.id === selectedElement.id);
  return element ? { type: selectedElement.type, element } : null;
}

function renderInspector() {
  const selected = findSelectedElement();
  if (!selected) {
    selectedElement = null;
    inspector.hidden = true;
    selectedInfo.textContent = "选择地图上的元素可编辑或删除；Delete 键也可删除。";
    return;
  }
  inspector.hidden = false;

  if (selected.type === "teleport") {
    const targetOptions = maps.map((map) =>
      `<option value="${map.mapId}" ${map.mapId === selected.element.targetMapId ? "selected" : ""}>${escapeHtml(map.key)} · ${escapeHtml(map.name)}</option>`,
    ).join("");
    inspector.innerHTML = `
      <strong>传送点 · ${escapeHtml(selected.element.direction)}</strong>
      <label>目标地图
        <select id="teleport-target">
          <option value="">未指定</option>
          ${targetOptions}
        </select>
      </label>
      <button class="delete-element" type="button">删除元素</button>`;
    inspector.querySelector("#teleport-target").addEventListener("change", (event) => {
      selected.element.targetMapId = event.target.value === "" ? null : Number(event.target.value);
      setDirty();
    });
    inspector.querySelector(".delete-element").addEventListener("click", deleteSelectedElement);
    selectedInfo.textContent = `传送点坐标：${Math.round(selected.element.x)}, ${Math.round(selected.element.y)}`;
    return;
  }

  const monster = monstersByKey.get(monsterKey(selected.element));
  inspector.innerHTML = `
    <strong>怪物 · ${escapeHtml(selected.element.name)} (${selected.element.actionCount} 个动作)</strong>
    <span>sprite ${selected.element.spriteId}</span>
    <button class="delete-element" type="button">删除元素</button>`;
  inspector.querySelector(".delete-element").addEventListener("click", deleteSelectedElement);
  selectedInfo.textContent = monster
    ? `怪物坐标：${Math.round(selected.element.x)}, ${Math.round(selected.element.y)}`
    : "该怪物素材当前不可用。";
}

function renderPlacements() {
  const map = selectedMap();
  const data = currentPlacements();
  placementsLayer.replaceChildren();

  for (const teleport of data.teleports) {
    const direction = DIRECTIONS.find((entry) => entry.name === teleport.direction);
    if (!direction) continue;
    const marker = document.createElement("div");
    marker.className = `placed-element teleport${selectedElement?.id === teleport.id ? " selected" : ""}`;
    marker.dataset.type = "teleport";
    marker.dataset.id = teleport.id;
    marker.title = `传送点 ${teleport.direction}`;
    marker.setAttribute("aria-label", marker.title);
    marker.style.left = `${teleport.x * scale}px`;
    marker.style.top = `${teleport.y * scale}px`;
    marker.innerHTML = `<span>${direction.arrow}</span><span>${direction.name}</span>`;
    placementsLayer.append(marker);
  }

  for (const placement of data.monsters) {
    const monster = monstersByKey.get(monsterKey(placement));
    const marker = document.createElement("div");
    marker.className = `placed-element monster${selectedElement?.id === placement.id ? " selected" : ""}`;
    marker.dataset.type = "monster";
    marker.dataset.id = placement.id;
    marker.title = `${placement.name} · ${placement.actionCount} 个动作`;
    marker.setAttribute("aria-label", marker.title);
    marker.style.left = `${placement.x * scale}px`;
    marker.style.top = `${placement.y * scale}px`;
    if (monster) {
      const image = document.createElement("img");
      image.src = monster.previewUrl;
      image.alt = "";
      image.draggable = false;
      marker.append(image);
    } else {
      marker.textContent = placement.name;
    }
    placementsLayer.append(marker);
  }
  renderInspector();
}

function renderMap() {
  const map = selectedMap();
  if (!map) return;
  baseScale = getBaseScale(map);
  scale = baseScale * zoomMultiplier;
  const displayWidth = map.width * scale;
  const displayHeight = map.height * scale;
  mapCanvas.style.width = `${displayWidth}px`;
  mapCanvas.style.height = `${displayHeight}px`;
  mapImage.src = map.imageUrl;
  mapImage.alt = `${map.key} 地图`;
  mapTitle.textContent = `${map.key} · ${map.name}`;
  mapDescription.textContent = `${map.region} · ${map.kind} · ${map.width} × ${map.height} px`;
  mapCounter.textContent = `${currentMapIndex + 1} / ${maps.length}`;
  mapSelect.value = String(map.mapId);
  previousMapButton.disabled = currentMapIndex === 0;
  nextMapButton.disabled = currentMapIndex === maps.length - 1;
  zoomLevel.textContent = `${Math.round(zoomMultiplier * 100)}%`;
  renderPlacements();
}

function changeMap(nextIndex) {
  if (nextIndex < 0 || nextIndex >= maps.length || nextIndex === currentMapIndex) return;
  currentMapIndex = nextIndex;
  selectedElement = null;
  zoomMultiplier = 1;
  renderMap();
  mapViewport.scrollTo(0, 0);
}

function createId() {
  return crypto.randomUUID().replaceAll("-", "");
}

function addPlacement(item, x, y) {
  const map = selectedMap();
  const data = currentPlacements();
  const position = {
    x: Math.round(Math.min(map.width, Math.max(0, x))),
    y: Math.round(Math.min(map.height, Math.max(0, y))),
  };
  if (item.type === "teleport" && directionNames.has(item.direction)) {
    const teleport = {
      id: createId(),
      direction: item.direction,
      targetMapId: null,
      ...position,
    };
    data.teleports.push(teleport);
    selectedElement = { type: "teleport", id: teleport.id };
  } else if (item.type === "monster") {
    const monster = monstersByKey.get(monsterKey(item));
    if (!monster) {
      saveStatus.textContent = "无法添加：怪物素材不在当前目录清单中。";
      return;
    }
    const placement = {
      id: createId(),
      spriteId: monster.spriteId,
      name: monster.name,
      actionCount: monster.actionCount,
      ...position,
    };
    data.monsters.push(placement);
    selectedElement = { type: "monster", id: placement.id };
  } else {
    saveStatus.textContent = "无法识别拖入的地图元素。";
    return;
  }
  setDirty();
  renderPlacements();
}

function deleteSelectedElement() {
  const selected = findSelectedElement();
  if (!selected) return;
  const data = currentPlacements();
  const entries = selected.type === "teleport" ? data.teleports : data.monsters;
  const index = entries.findIndex((entry) => entry.id === selected.element.id);
  if (index < 0) return;
  entries.splice(index, 1);
  selectedElement = null;
  setDirty();
  renderPlacements();
}

function placementAtPointer(event) {
  const rect = mapCanvas.getBoundingClientRect();
  return {
    x: (event.clientX - rect.left) / scale,
    y: (event.clientY - rect.top) / scale,
  };
}

function handleDrop(event) {
  event.preventDefault();
  const raw = event.dataTransfer.getData("application/json") ||
    event.dataTransfer.getData("text/plain");
  if (!raw) {
    saveStatus.textContent = "无法读取拖入的素材数据，请重新拖动素材。";
    return;
  }
  let item;
  try {
    item = JSON.parse(raw);
  } catch (error) {
    console.error("Could not read dragged map element:", error);
    saveStatus.textContent = "拖入的素材数据无效。";
    return;
  }
  const position = placementAtPointer(event);
  addPlacement(item, position.x, position.y);
}

function findElement(type, id) {
  const data = currentPlacements();
  const entries = type === "teleport" ? data.teleports : data.monsters;
  return entries.find((entry) => entry.id === id);
}

function renderDraggedElement() {
  if (!dragState) return;
  const map = selectedMap();
  const element = findElement(dragState.type, dragState.id);
  if (!element) return;
  element.x = Math.round(Math.min(map.width, Math.max(
    0,
    dragState.originalX + (dragState.lastClientX - dragState.startClientX) / scale,
  )));
  element.y = Math.round(Math.min(map.height, Math.max(
    0,
    dragState.originalY + (dragState.lastClientY - dragState.startClientY) / scale,
  )));
  renderPlacements();
}

async function savePlacements() {
  if (saveButton.disabled) return;
  saveButton.disabled = true;
  saveStatus.textContent = "正在保存…";
  const saveVersion = changeVersion;
  const candidate = {
    schemaVersion: 1,
    maps: [...placementsByMapId.values()]
      .filter((entry) => entry.teleports.length > 0 || entry.monsters.length > 0)
      .sort((left, right) => left.mapId - right.mapId),
  };
  try {
    const response = await fetch("/api/placements", {
      method: "POST",
      headers: { "content-type": "application/json" },
      body: JSON.stringify(candidate),
    });
    const result = await response.json();
    if (!response.ok) throw new Error(result.error ?? `保存失败：HTTP ${response.status}`);
    dirty = changeVersion !== saveVersion;
    saveStatus.textContent = dirty
      ? "已保存当前版本；仍有更新尚未保存"
      : "已保存至 map_placements.json";
  } catch (error) {
    saveStatus.textContent = `保存失败：${error.message}`;
    console.error("Could not save map placements:", error);
  } finally {
    saveButton.disabled = false;
  }
}

async function loadEditor() {
  const [catalogResponse, placementResponse] = await Promise.all([
    fetch("/api/catalog"),
    fetch("/api/placements"),
  ]);
  if (!catalogResponse.ok) throw new Error(`素材清单加载失败：HTTP ${catalogResponse.status}`);
  if (!placementResponse.ok) throw new Error(`布置数据加载失败：HTTP ${placementResponse.status}`);
  const catalog = await catalogResponse.json();
  const saved = await placementResponse.json();
  if (!Array.isArray(catalog.maps) || catalog.maps.length === 0 || !Array.isArray(catalog.monsters)) {
    throw new Error("素材清单结构无效或没有可用地图。");
  }
  if (saved.schemaVersion !== 1 || !Array.isArray(saved.maps)) {
    throw new Error("布置数据结构无效，请检查 map_placements.json。");
  }

  maps = catalog.maps;
  monsters = catalog.monsters;
  monstersByKey = new Map(monsters.map((monster) => [monsterKey(monster), monster]));
  placementsByMapId = new Map(saved.maps.map((entry) => [entry.mapId, entry]));
  populateMapSelect();
  renderDirections();
  renderMonsters();
  renderMap();
  const elementCount = saved.maps.reduce((count, entry) =>
    count + entry.teleports.length + entry.monsters.length, 0,
  );
  saveStatus.textContent = elementCount
    ? `已加载 ${elementCount} 个已保存元素`
    : "尚无已保存布置";
}

previousMapButton.addEventListener("click", () => changeMap(currentMapIndex - 1));
nextMapButton.addEventListener("click", () => changeMap(currentMapIndex + 1));
mapSelect.addEventListener("change", () => {
  const index = maps.findIndex((map) => map.mapId === Number(mapSelect.value));
  if (index >= 0) changeMap(index);
});
zoomOutButton.addEventListener("click", () => {
  zoomMultiplier = Math.max(0.5, zoomMultiplier / 1.2);
  renderMap();
});
zoomInButton.addEventListener("click", () => {
  zoomMultiplier = Math.min(6, zoomMultiplier * 1.2);
  renderMap();
});
saveButton.addEventListener("click", savePlacements);
monsterSearch.addEventListener("input", () => renderMonsters(monsterSearch.value));
mapImage.addEventListener("error", () => {
  saveStatus.textContent = `地图预览加载失败：${selectedMap()?.key ?? ""}`;
});

mapViewport.addEventListener("dragover", (event) => {
  event.preventDefault();
  event.dataTransfer.dropEffect = "copy";
});
mapViewport.addEventListener("drop", handleDrop);
mapViewport.addEventListener("wheel", (event) => {
  event.preventDefault();
  const nextMultiplier = event.deltaY < 0 ? zoomMultiplier * 1.1 : zoomMultiplier / 1.1;
  zoomMultiplier = Math.min(6, Math.max(0.5, nextMultiplier));
  renderMap();
}, { passive: false });

placementsLayer.addEventListener("pointerdown", (event) => {
  const marker = event.target.closest(".placed-element");
  if (!marker) return;
  const { type, id } = marker.dataset;
  const element = findElement(type, id);
  if (!element) return;
  event.preventDefault();
  selectedElement = { type, id };
  dragState = {
    pointerId: event.pointerId,
    type,
    id,
    startClientX: event.clientX,
    startClientY: event.clientY,
    lastClientX: event.clientX,
    lastClientY: event.clientY,
    originalX: element.x,
    originalY: element.y,
    moved: false,
  };
  placementsLayer.setPointerCapture(event.pointerId);
  renderPlacements();
});
placementsLayer.addEventListener("pointermove", (event) => {
  if (!dragState || dragState.pointerId !== event.pointerId) return;
  dragState.lastClientX = event.clientX;
  dragState.lastClientY = event.clientY;
  if (
    !dragState.moved &&
    Math.hypot(event.clientX - dragState.startClientX, event.clientY - dragState.startClientY) < 3
  ) return;
  dragState.moved = true;
  renderDraggedElement();
});
function finishPointerDrag(event) {
  if (!dragState || dragState.pointerId !== event.pointerId) return;
  if (dragState.moved) setDirty();
  dragState = null;
  renderInspector();
}
placementsLayer.addEventListener("pointerup", finishPointerDrag);
placementsLayer.addEventListener("pointercancel", finishPointerDrag);

window.addEventListener("resize", renderMap);
window.addEventListener("keydown", (event) => {
  const target = event.target;
  const editingField = target instanceof HTMLElement &&
    target.closest("input, select, textarea, [contenteditable='true']");
  if (editingField) return;
  if (event.key === "ArrowLeft") {
    event.preventDefault();
    changeMap(currentMapIndex - 1);
  } else if (event.key === "ArrowRight") {
    event.preventDefault();
    changeMap(currentMapIndex + 1);
  } else if (event.key === "Delete" && selectedElement) {
    event.preventDefault();
    deleteSelectedElement();
  } else if ((event.ctrlKey || event.metaKey) && event.key.toLowerCase() === "s") {
    event.preventDefault();
    savePlacements();
  }
});
window.addEventListener("beforeunload", (event) => {
  if (!dirty) return;
  event.preventDefault();
  event.returnValue = "";
});

loadEditor().catch((error) => {
  saveStatus.textContent = `加载失败：${error.message}`;
  console.error("Could not load the map placement editor:", error);
});
