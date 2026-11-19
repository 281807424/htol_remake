import { mkdir, readFile, readdir, writeFile } from 'node:fs/promises';
import { dirname, resolve } from 'node:path';
import { fileURLToPath } from 'node:url';

const projectRoot = resolve(dirname(fileURLToPath(import.meta.url)), '..');
const exportRoot = resolve(projectRoot, '..', 'htol_export', 'images', 'phaser');
const exportImageRoot = resolve(projectRoot, '..', 'htol_export', 'images');
const collisionOutputRoot = resolve(exportImageRoot, 'map_collision');
const runtimeRoot = resolve(exportImageRoot, 'demo_runtime');
const spriteLibraryRoot = resolve(exportImageRoot, 'sprite_library');
const spriteLibraryPaths = new Set();
const spriteLibraryPathsByName = new Map();
async function indexSpriteLibraryFiles(directory, relativeDirectory = '') {
  for (const entry of await readdir(directory, { withFileTypes: true })) {
    const relativePath = relativeDirectory
      ? `${relativeDirectory}/${entry.name}`
      : entry.name;
    if (entry.isDirectory()) {
      await indexSpriteLibraryFiles(resolve(directory, entry.name), relativePath);
    } else if (entry.isFile() && entry.name.toLowerCase().endsWith('.png')) {
      spriteLibraryPaths.add(relativePath);
      const matchingPaths = spriteLibraryPathsByName.get(entry.name) ?? [];
      matchingPaths.push(relativePath);
      spriteLibraryPathsByName.set(entry.name, matchingPaths);
    }
  }
}
await indexSpriteLibraryFiles(spriteLibraryRoot);

function resolveSpriteLibraryPath(file) {
  if (spriteLibraryPaths.has(file)) return file;
  const filename = file.split('/').pop();
  const matchingPaths = spriteLibraryPathsByName.get(filename) ?? [];
  if (matchingPaths.length !== 1) {
    throw new Error(
      `sprite_library 资源路径 ${file} ${matchingPaths.length ? '存在歧义' : '不存在'}`,
    );
  }
  return matchingPaths[0];
}

const worldMap = JSON.parse(
  await readFile(resolve(projectRoot, '..', 'world_map_design', 'world_map.json'), 'utf8'),
);
const placements = JSON.parse(
  await readFile(
    resolve(projectRoot, '..', 'map_placement_editor', 'map_placements.json'),
    'utf8',
  ),
);
const mapIds = worldMap.maps.map(({ mapId }) => mapId);
const spriteIds = new Set([31, 32, 38, 74, 75, 76]);
const spriteFileOverrides = new Map([
  ['30:0', '技能特效/dynamic/sprite-030-爆裂术-00.png'],
  ['31:0', '技能特效/dynamic/sprite-031-陨石术-00.png'],
  ['32:0', '技能特效/dynamic/sprite-032-落雷术-00.png'],
  ['32:1', '技能特效/dynamic/sprite-032-雷电束缚-01.png'],
  ['32:6', '技能特效/dynamic/sprite-032-冰冻之环-06.png'],
  ['32:7', '技能特效/dynamic/sprite-032-冰冻之环被控-07.png'],
  ['32:8', '技能特效/dynamic/sprite-032-光盾-08.png'],
  ['33:0', '技能特效/dynamic/sprite-033-天火术-00.png'],
]);
const collisionCellSize = 36;
const blockingAttribute = 23;

function parseCsv(content) {
  const [headerLine, ...lines] = content.replace(/^\uFEFF/, '').trimEnd().split(/\r?\n/);
  const headers = headerLine.split(',');
  return lines.filter(Boolean).map((line) => {
    const values = line.split(',');
    return Object.fromEntries(headers.map((header, index) => [header, values[index] ?? '']));
  });
}

const mapIndexRows = parseCsv(await readFile(resolve(exportImageRoot, 'map_index.csv'), 'utf8'));
const placementsByMapId = new Map();
for (const mapPlacement of placements.maps) {
  if (placementsByMapId.has(mapPlacement.mapId)) {
    throw new Error(`map_placements.json 中地图 ${mapPlacement.mapId} 重复`);
  }
  placementsByMapId.set(mapPlacement.mapId, mapPlacement);
  for (const monster of mapPlacement.monsters) spriteIds.add(monster.spriteId);
}
if (
  placements.schemaVersion !== 1 ||
  mapIds.some((mapId) => !placementsByMapId.has(mapId)) ||
  placements.maps.some(({ mapId }) => !mapIds.includes(mapId))
) {
  throw new Error('map_placements.json 格式无效或缺少地图布置');
}

const teleportLocations = new Map();
for (const mapPlacement of placements.maps) {
  for (const teleport of mapPlacement.teleports) {
    if (teleportLocations.has(teleport.id)) {
      throw new Error(`map_placements.json 中传送点 ${teleport.id} 重复`);
    }
    teleportLocations.set(teleport.id, {
      mapId: mapPlacement.mapId,
      teleport,
    });
  }
}
const connectionsByTeleportId = new Map();
for (const connection of worldMap.connections) {
  const from = teleportLocations.get(connection.fromTeleportId);
  const to = teleportLocations.get(connection.toTeleportId);
  if (from?.mapId !== connection.fromMapId || to?.mapId !== connection.toMapId) {
    throw new Error(`世界地图连接 ${connection.id} 引用了无效传送点`);
  }
  if (
    connectionsByTeleportId.has(connection.fromTeleportId) ||
    connectionsByTeleportId.has(connection.toTeleportId)
  ) {
    throw new Error(`世界地图连接 ${connection.id} 重复使用传送点`);
  }
  connectionsByTeleportId.set(connection.fromTeleportId, {
    mapId: to.mapId,
    teleportId: connection.toTeleportId,
    connectionId: connection.id,
  });
  connectionsByTeleportId.set(connection.toTeleportId, {
    mapId: from.mapId,
    teleportId: connection.fromTeleportId,
    connectionId: connection.id,
  });
}

const collisionDataByMap = new Map();
for (const row of mapIndexRows) {
  const mapId = Number(row.map_resource_id);
  const width = Number(row.width_cells);
  const height = Number(row.height_cells);
  if (!Number.isInteger(mapId) || !Number.isInteger(width) || !Number.isInteger(height) || width <= 0 || height <= 0) {
    throw new Error(`map_index.csv 中有无效地图尺寸：${row.map_resource_id}`);
  }
  if (collisionDataByMap.has(mapId)) throw new Error(`map_index.csv 中地图 ${mapId} 重复`);
  collisionDataByMap.set(mapId, {
    mapResourceId: mapId,
    width,
    height,
    cellSize: collisionCellSize,
    blockingAttribute,
    attributes: Array(width * height),
  });
}

const tileCsv = (await readFile(resolve(exportImageRoot, 'map_tiles.csv'), 'utf8')).replace(/^\uFEFF/, '');
const tileRows = parseCsv(tileCsv);
for (const requiredColumn of ['map_resource_id', 'cell_x', 'cell_y', 'attribute_byte']) {
  if (!tileRows.length || !(requiredColumn in tileRows[0])) {
    throw new Error(`map_tiles.csv 缺少 ${requiredColumn} 字段`);
  }
}
for (const row of tileRows) {
  const mapId = Number(row.map_resource_id);
  const collisionData = collisionDataByMap.get(mapId);
  if (!collisionData) throw new Error(`map_tiles.csv 包含未知地图 ${mapId}`);
  const x = Number(row.cell_x);
  const y = Number(row.cell_y);
  const attribute = Number(row.attribute_byte);
  if (![x, y, attribute].every(Number.isInteger) || attribute < 0 || attribute > 255) {
    throw new Error(`map_tiles.csv 中地图 ${mapId} 有无效格子属性`);
  }
  if (x < 0 || x >= collisionData.width || y < 0 || y >= collisionData.height) {
    throw new Error(`地图 ${mapId} 的碰撞属性坐标越界：(${x}, ${y})`);
  }
  const index = y * collisionData.width + x;
  if (collisionData.attributes[index] !== undefined) {
    throw new Error(`地图 ${mapId} 的碰撞属性坐标重复：(${x}, ${y})`);
  }
  collisionData.attributes[index] = attribute;
}

await mkdir(collisionOutputRoot, { recursive: true });
for (const collisionData of collisionDataByMap.values()) {
  const { mapResourceId, width, height, attributes } = collisionData;
  if (attributes.length !== width * height || attributes.some((value) => value === undefined)) {
    throw new Error(
      `地图 ${mapResourceId} 碰撞数据不完整：${attributes.filter((value) => value !== undefined).length}/${width * height} 格`,
    );
  }
  const collisionJson = `${JSON.stringify(collisionData)}\n`;
  await writeFile(
    resolve(collisionOutputRoot, `map_${String(mapResourceId).padStart(3, '0')}.json`),
    collisionJson,
  );
}

for (const mapId of mapIds) {
  const mapName = `map_${String(mapId).padStart(3, '0')}`;
  const sourcePath = resolve(exportRoot, 'maps', `${mapName}.tmj`);
  const map = JSON.parse(await readFile(sourcePath, 'utf8'));
  const collisionData = collisionDataByMap.get(mapId);
  if (collisionData.width !== map.width || collisionData.height !== map.height) {
    throw new Error(`地图 ${mapId} 的碰撞数据尺寸与 Tiled 地图不一致`);
  }

  for (const layer of map.layers || []) {
    for (const object of layer.objects || []) {
      const resourceId = Number(
        object.properties?.find(({ name }) => name === 'spriteResourceId')?.value,
      );
      if (Number.isInteger(resourceId) && resourceId > 0) spriteIds.add(resourceId);
    }
  }
}

if (mapIds.length !== 124 || new Set(mapIds).size !== mapIds.length || !mapIds.includes(13)) {
  throw new Error('world_map.json 必须包含唯一的 124 张地图，并将 map_013 用作玄月国主城。');
}

const library = JSON.parse(
  await readFile(resolve(spriteLibraryRoot, 'sprite_library.json'), 'utf8'),
);
if (library.format !== 'htol-readable-sprite-library' || library.version !== 1) {
  throw new Error('sprite_library.json 格式或版本不受支持');
}
const aoRuntime = JSON.parse(
  await readFile(
    resolve(exportImageRoot, 'weapon_appearance_export', 'ao_runtime.json'),
    'utf8',
  ),
);
if (
  aoRuntime.format !== 1 ||
  !aoRuntime.characters ||
  [74, 75, 76].some((spriteId) => !aoRuntime.characters[String(spriteId)])
) {
  throw new Error('AO 角色运行时资源缺失或格式无效；请先运行 export_character_phaser_assets.py');
}
const aoCharacters = aoRuntime.characters;
const libraryById = new Map(library.sprites.map((sprite) => [sprite.spriteId, sprite]));
const entityCategories = new Set(['怪物', 'NPC']);
const ambientEntityGroupKeys = new Set();
let ambientEntityCatalog = library.sprites.flatMap((sprite) => {
  const categories = [...new Set(
    sprite.groups
      .filter((group) => entityCategories.has(group.category))
      .map((group) => group.category),
  )];
  return categories.map((category) => {
    const groups = sprite.groups
      .filter((group) => group.category === category)
      .sort((left, right) => left.animationGroup - right.animationGroup);
    if (groups.some((group) => (
      group.outputKind !== 'spritesheet' &&
      !(group.outputKind === 'image' && group.frameCount === 1)
    ))) {
      throw new Error(`${category} ${sprite.name} 包含不支持的动画格式`);
    }
    if (category === 'NPC' && groups.length !== 1) {
      throw new Error(`NPC ${sprite.name} 必须且只能有一个动画组`);
    }
    return {
      spriteId: sprite.spriteId,
      name: sprite.name,
      category,
      animationGroups: groups.map((group) => group.animationGroup),
    };
  });
});
const skillEffectSpriteIds = new Set(
  library.sprites
    .filter((sprite) => sprite.groups.some(
      (group) => group.category === '技能特效' && group.outputKind === 'spritesheet',
    ))
    .map((sprite) => sprite.spriteId),
);
const monsterGroupFileOverrides = new Map();
for (const mapPlacement of placements.maps) {
  for (const monster of mapPlacement.monsters) {
    const directory = `怪物/dynamic/${monster.actionCount}`;
    const files = await readdir(resolve(spriteLibraryRoot, directory));
    for (const animationGroup of [0, 3]) {
      const prefix = `sprite-${String(monster.spriteId).padStart(3, '0')}-${monster.name}-`;
      const filename = files.find((file) => {
        const actionIndex = file.match(/-(\d+)[^-]*\.png$/i)?.[1];
        return file.startsWith(prefix) &&
          file.toLowerCase().endsWith('.png') &&
          Number(actionIndex) === animationGroup;
      });
      if (!filename) {
        throw new Error(`怪物 ${monster.name} 缺少动作组 ${animationGroup} 的素材`);
      }
      const key = `${monster.spriteId}:${animationGroup}`;
      const path = `${directory}/${filename}`;
      const previousPath = monsterGroupFileOverrides.get(key);
      if (previousPath && previousPath !== path) {
        throw new Error(`sprite ${monster.spriteId} 的怪物动作组 ${animationGroup} 对应多个素材`);
      }
      monsterGroupFileOverrides.set(key, path);
    }
  }
}
const ambientEntityFileOverrides = new Map();
const availableAmbientEntities = [];
for (const entity of ambientEntityCatalog) {
  const sprite = libraryById.get(entity.spriteId);
  const groupsByDisplayName = new Map();
  let hasUnavailableFiles = false;
  for (const animationGroup of entity.animationGroups) {
    const group = sprite.groups.find(({ animationGroup: id }) => id === animationGroup);
    const groups = groupsByDisplayName.get(group.displayName) ?? [];
    groups.push(group);
    groupsByDisplayName.set(group.displayName, groups);
  }
  for (const [displayName, groups] of groupsByDisplayName) {
    const hasMissingFile = groups.some((group) => {
      if (spriteLibraryPaths.has(group.file)) return false;
      const filename = group.file.split('/').pop();
      return (spriteLibraryPathsByName.get(filename) ?? []).length !== 1;
    });
    if (!hasMissingFile) continue;

    const assetDirectory = groups[0].outputKind === 'image' ? '/static/' : '/dynamic/';
    const prefix = `sprite-${String(entity.spriteId).padStart(3, '0')}-${displayName}-`;
    const candidates = [...spriteLibraryPaths]
      .filter((file) => (
        file.includes(assetDirectory) &&
        file.split('/').pop().startsWith(prefix)
      ))
      .sort((left, right) => {
        const getActionIndex = (file) => Number(file.match(/-(\d+)[^-]*\.png$/i)?.[1]);
        return getActionIndex(left) - getActionIndex(right);
      });
    if (!candidates.length) {
      console.warn(
        `跳过缺少图像文件的${entity.category} ${entity.name}（sprite ${entity.spriteId}）`,
      );
      hasUnavailableFiles = true;
      continue;
    }
    if (candidates.length !== groups.length) {
      throw new Error(
        `${entity.category} ${displayName} 动画组与素材数量不匹配：${groups.length}/${candidates.length}`,
      );
    }
    groups.forEach((group, index) => {
      ambientEntityFileOverrides.set(
        `${entity.spriteId}:${group.animationGroup}`,
        candidates[index],
      );
    });
  }
  if (hasUnavailableFiles) continue;
  for (const animationGroup of entity.animationGroups) {
    ambientEntityGroupKeys.add(`${entity.spriteId}:${animationGroup}`);
  }
  availableAmbientEntities.push(entity);
}
ambientEntityCatalog = availableAmbientEntities;
const ambientEntitySpriteIds = new Set(ambientEntityCatalog.map(({ spriteId }) => spriteId));
const runtimeSpriteIds = new Set([
  ...spriteIds,
  ...skillEffectSpriteIds,
  ...ambientEntitySpriteIds,
]);
const runtimeSprites = [];
const tilesetSpriteIds = new Set();
for (const spriteId of [...runtimeSpriteIds].sort((a, b) => a - b)) {
  const sprite = libraryById.get(spriteId);
  if (!sprite) throw new Error(`sprite_library.json 缺少地图资源 sprite ${spriteId}`);
  const groupCountBeforeSprite = runtimeSprites.length;
  for (const group of sprite.groups) {
    if (
      [74, 75, 76].includes(spriteId) &&
      (
        !group.profession ||
        !(group.actionNumber <= 15 || [17, 20, 21].includes(group.actionNumber))
      )
    ) {
      continue;
    }
    if (
      !spriteIds.has(spriteId) &&
      !ambientEntityGroupKeys.has(`${spriteId}:${group.animationGroup}`) &&
      (group.category !== '技能特效' || group.outputKind !== 'spritesheet')
    ) {
      continue;
    }
    const monsterGroupFile = monsterGroupFileOverrides.get(
      `${spriteId}:${group.animationGroup}`,
    );
    const ambientEntityFile = ambientEntityFileOverrides.get(
      `${spriteId}:${group.animationGroup}`,
    );
    const textureKey = `sprite-${spriteId}-group-${group.animationGroup}`;
    const spriteLibraryFile = monsterGroupFile ??
      ambientEntityFile ??
      spriteFileOverrides.get(`${spriteId}:${group.animationGroup}`) ??
      group.file;
    runtimeSprites.push({
      spriteId,
      animationGroup: group.animationGroup,
      name: group.name,
      displayName: group.displayName,
      category: group.category,
      action: group.action,
      actionNumber: group.actionNumber,
      gender: group.gender,
      weapon: group.weapon,
      profession: group.profession,
      setStart: group.setStart,
      textureKey,
      textureKind: group.outputKind,
      file: `sprite_library/${resolveSpriteLibraryPath(spriteLibraryFile)}`,
      frameWidth: group.frameWidth,
      frameHeight: group.frameHeight,
      frameCount: group.frameCount,
      durationMs: group.durationMs,
      originX: group.originX,
      originY: group.originY,
      frames: group.frames.map((frame) => ({
        sourceFrameIndex: frame.sourceFrameIndex,
        sheetFrame: frame.sheetFrame,
        durationMs: frame.durationMs,
        durationTicks: frame.durationTicks,
        drawOffsetX: frame.drawOffsetX,
        drawOffsetY: frame.drawOffsetY,
        offsetKnown: frame.offsetKnown,
        width: frame.width,
        height: frame.height,
      })),
    });
  }
  if (runtimeSprites.length === groupCountBeforeSprite) {
    throw new Error(`地图资源 sprite ${spriteId} 没有可加载的帧或组件`);
  }
}

for (const mapId of mapIds) {
  const mapName = `map_${String(mapId).padStart(3, '0')}`;
  const map = JSON.parse(await readFile(resolve(exportRoot, 'maps', `${mapName}.tmj`), 'utf8'));
  for (const tilesetReference of map.tilesets || []) {
    const definition = JSON.parse(
      await readFile(resolve(exportRoot, 'maps', tilesetReference.source), 'utf8'),
    );
    const match = /^sprite_(\d+)_tiles$/.exec(definition.name);
    if (!match) throw new Error(`${mapName} 引用了未知地形图集：${definition.name}`);
    tilesetSpriteIds.add(Number(match[1]));
  }
}

const ambientEntityMapIds = [81, 12];
const ambientEntitiesByMap = new Map(
  ambientEntityMapIds.map((mapId) => [mapId, []]),
);
if (ambientEntityMapIds.some((mapId) => !mapIds.includes(mapId))) {
  throw new Error('怪物与 NPC 展示地图必须包含 map_081 和 map_012');
}
ambientEntityCatalog.forEach((entity, index) => {
  ambientEntitiesByMap.get(ambientEntityMapIds[index % ambientEntityMapIds.length]).push(entity);
});
for (const [mapId, entities] of ambientEntitiesByMap) {
  const collisionData = collisionDataByMap.get(mapId);
  if (!collisionData) throw new Error(`地图 ${mapId} 缺少碰撞数据，无法放置怪物与 NPC`);
  const openCells = [];
  for (let y = 0; y < collisionData.height; y += 1) {
    for (let x = 0; x < collisionData.width; x += 1) {
      if (collisionData.attributes[y * collisionData.width + x] !== blockingAttribute) {
        openCells.push({ x, y });
      }
    }
  }
  if (openCells.length < entities.length) {
    throw new Error(`地图 ${mapId} 的非碰撞格不足以放置所有怪物与 NPC`);
  }
  entities.forEach((entity, index) => {
    const cellIndex = Math.floor(((index + 0.5) * openCells.length) / entities.length);
    const cell = openCells[cellIndex];
    entity.x = (cell.x + 0.5) * collisionCellSize;
    entity.y = (cell.y + 0.5) * collisionCellSize;
  });
}

await writeFile(
  resolve(runtimeRoot, 'manifest.json'),
  `${JSON.stringify({
    mapIds,
    tilesetSpriteIds: [...tilesetSpriteIds].sort((left, right) => left - right),
    mapSpriteIds: [...spriteIds].sort((a, b) => a - b),
    sprites: runtimeSprites,
    aoCharacters,
    startMapId: 13,
    maps: worldMap.maps.map((map) => {
      const mapPlacement = placementsByMapId.get(map.mapId);
      return {
        mapId: map.mapId,
        id: String(map.mapId),
        key: map.key,
        name: map.kind === '野外地图' ? `${map.nation}野外地图 ${map.key}` : map.name,
        region: map.region,
        nation: map.nation,
        kind: map.kind,
        category: map.category,
        file: `map_${String(map.mapId).padStart(3, '0')}`,
        spawn: { x: 0.5, y: 0.72 },
        teleports: mapPlacement.teleports.map((teleport) => {
          const connection = connectionsByTeleportId.get(teleport.id);
          return {
            ...teleport,
            targetMapId: connection?.mapId ?? teleport.targetMapId ?? null,
            targetTeleportId: connection?.teleportId ?? null,
            connectionId: connection?.connectionId ?? null,
          };
        }),
        monsters: mapPlacement.monsters,
        ambientEntities: ambientEntitiesByMap.get(map.mapId) ?? [],
      };
    }),
  }, null, 2)}\n`,
);
console.log(`Prepared metadata for ${mapIds.length} maps and ${runtimeSprites.length} sprite groups.`);
