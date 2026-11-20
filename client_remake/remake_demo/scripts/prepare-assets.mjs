import { mkdir, readFile, readdir, writeFile } from 'node:fs/promises';
import { dirname, resolve } from 'node:path';
import { fileURLToPath } from 'node:url';

const projectRoot = resolve(dirname(fileURLToPath(import.meta.url)), '..');
const exportRoot = resolve(projectRoot, '..', 'htol_export');
const mapRoot = resolve(exportRoot, 'map');
const tiledMapRoot = resolve(mapRoot, 'tiled');
const collisionOutputRoot = resolve(mapRoot, 'collision');
const runtimeRoot = resolve(exportRoot, 'other', 'demo_runtime');
const spriteLibraryRoot = exportRoot;
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
for (const category of ['map', 'unit', 'monster', 'player', 'effect', 'icon', 'pet', 'npc', 'other']) {
  await indexSpriteLibraryFiles(resolve(spriteLibraryRoot, category), category);
}

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

const mapIndexRows = parseCsv(await readFile(resolve(exportRoot, 'map_index.csv'), 'utf8'));
const placementsByMapId = new Map();
for (const mapPlacement of placements.maps) {
  if (placementsByMapId.has(mapPlacement.mapId)) {
    throw new Error(`map_placements.json 中地图 ${mapPlacement.mapId} 重复`);
  }
  placementsByMapId.set(mapPlacement.mapId, mapPlacement);
  for (const monster of mapPlacement.monsters) spriteIds.add(monster.spriteId);
}
const availableMapIds = new Set(mapIndexRows.map((row) => Number(row.map_resource_id)));
if (
  placements.schemaVersion !== 1 ||
  mapIds.some((mapId) => !placementsByMapId.has(mapId)) ||
  placements.maps.some(({ mapId }) => !availableMapIds.has(mapId))
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

const tileCsv = (await readFile(resolve(exportRoot, 'map_tiles.csv'), 'utf8')).replace(/^\uFEFF/, '');
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
  const sourcePath = resolve(tiledMapRoot, `${mapName}.tmj`);
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

if (mapIds.length !== 1 || mapIds[0] !== 13) {
  throw new Error('world_map.json 必须只包含 demo 出场地图 map_013。');
}

const library = JSON.parse(
  await readFile(resolve(spriteLibraryRoot, 'sprite_library.json'), 'utf8'),
);
if (library.format !== 'htol-readable-sprite-library' || library.version !== 1) {
  throw new Error('sprite_library.json 格式或版本不受支持');
}
const aoRuntime = JSON.parse(
  await readFile(
    resolve(exportRoot, 'player', 'ao_runtime.json'),
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
    const directory = `monster/dynamic/${monster.actionCount}`;
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
const runtimeSpriteIds = new Set([...spriteIds, ...skillEffectSpriteIds]);
const runtimeSprites = [];
const tilesetSpriteIds = new Set();
const tilesetFiles = new Map();
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
      (group.category !== '技能特效' || group.outputKind !== 'spritesheet')
    ) {
      continue;
    }
    const textureKey = `sprite-${spriteId}-group-${group.animationGroup}`;
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
      file: resolveSpriteLibraryPath(group.file),
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
  const map = JSON.parse(await readFile(resolve(tiledMapRoot, `${mapName}.tmj`), 'utf8'));
  for (const tilesetReference of map.tilesets || []) {
    const definition = JSON.parse(
      await readFile(resolve(tiledMapRoot, tilesetReference.source), 'utf8'),
    );
    const match = /^sprite_(\d+)_palette_0_tiles$/.exec(definition.name);
    if (!match) throw new Error(`${mapName} 引用了未知地形图集：${definition.name}`);
    const spriteId = Number(match[1]);
    const imageName = definition.image?.split(/[\\/]/).pop();
    if (!imageName || !/^sprite_\d+_palette_0_tiles\.png$/.test(imageName)) {
      throw new Error(`${mapName} 的地形图集图片路径无效：${definition.image}`);
    }
    const previousImage = tilesetFiles.get(spriteId);
    if (previousImage && previousImage !== imageName) {
      throw new Error(`sprite ${spriteId} 对应多个地形图集文件`);
    }
    tilesetSpriteIds.add(spriteId);
    tilesetFiles.set(spriteId, imageName);
  }
}

await writeFile(
  resolve(runtimeRoot, 'manifest.json'),
  `${JSON.stringify({
    mapIds,
    tilesetSpriteIds: [...tilesetSpriteIds].sort((left, right) => left - right),
    tilesetFiles: Object.fromEntries(tilesetFiles),
    mapSpriteIds: [...spriteIds].sort((a, b) => a - b),
    sprites: runtimeSprites,
    aoCharacters,
    startMapId: mapIds[0],
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
            targetMapId: connection?.mapId ??
              (mapIds.includes(teleport.targetMapId) ? teleport.targetMapId : null),
            targetTeleportId: connection?.teleportId ?? null,
            connectionId: connection?.connectionId ?? null,
          };
        }),
        monsters: mapPlacement.monsters,
        ambientEntities: [],
      };
    }),
  }, null, 2)}\n`,
);
console.log(`Prepared metadata for ${mapIds.length} maps and ${runtimeSprites.length} sprite groups.`);
