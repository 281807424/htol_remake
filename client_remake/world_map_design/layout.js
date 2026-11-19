const DIRECTION_VECTORS = {
  东: [1, 0],
  东北: [Math.SQRT1_2, -Math.SQRT1_2],
  北: [0, -1],
  西北: [-Math.SQRT1_2, -Math.SQRT1_2],
  西: [-1, 0],
  西南: [-Math.SQRT1_2, Math.SQRT1_2],
  南: [0, 1],
  东南: [Math.SQRT1_2, Math.SQRT1_2],
};
const CARD_GAP = 10;
const CONNECTION_LENGTH = 150;
const MAX_CONNECTION_DISTANCE = 220;
const ITERATIONS = 300;

function normalized(x, y) {
  const length = Math.hypot(x, y);
  return length > 1e-9 ? [x / length, y / length] : [0, 0];
}

function teleportOffset(map, teleport) {
  const insetX = map.kind === "野外地图" ? 3 : 4;
  const insetY = map.kind === "野外地图" ? 2 : 3;
  const previewWidth = map.width - insetX * 2;
  const previewHeight = 34;
  const scale = Math.min(previewWidth / map.imageWidth, previewHeight / map.imageHeight);
  const renderedWidth = map.imageWidth * scale;
  const renderedHeight = map.imageHeight * scale;
  return {
    x: insetX + (previewWidth - renderedWidth) / 2 + teleport.x * scale - map.width / 2,
    y: insetY + (previewHeight - renderedHeight) / 2 + teleport.y * scale - map.height / 2,
  };
}

function segmentIntersectsMap(start, end, map, center) {
  const deltaX = end.x - start.x;
  const deltaY = end.y - start.y;
  const left = center.x - map.width / 2;
  const right = center.x + map.width / 2;
  const top = center.y - map.height / 2;
  const bottom = center.y + map.height / 2;
  let minimum = 0;
  let maximum = 1;
  for (const [direction, distance] of [
    [-deltaX, start.x - left],
    [deltaX, right - start.x],
    [-deltaY, start.y - top],
    [deltaY, bottom - start.y],
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

function connectionVector(fromTeleport, toTeleport) {
  const from = DIRECTION_VECTORS[fromTeleport.direction];
  const to = DIRECTION_VECTORS[toTeleport.direction];
  if (!from || !to) throw new Error("连接中包含未知方向的传送点。");
  const [x, y] = normalized(from[0] - to[0], from[1] - to[1]);
  return x || y ? [x, y] : from;
}

function addDelta(deltas, mapId, x, y) {
  const delta = deltas.get(mapId);
  delta.x += x;
  delta.y += y;
}

function relaxConnectedMaps(connectedMaps, edges, positions) {
  for (let iteration = 0; iteration < ITERATIONS; iteration += 1) {
    const deltas = new Map(connectedMaps.map((map) => [map.mapId, { x: 0, y: 0 }]));

    for (const edge of edges) {
      const from = positions.get(edge.fromMapId);
      const to = positions.get(edge.toMapId);
      const errorX = to.x - from.x - edge.vector[0] * CONNECTION_LENGTH;
      const errorY = to.y - from.y - edge.vector[1] * CONNECTION_LENGTH;
      addDelta(deltas, edge.fromMapId, errorX * 0.08, errorY * 0.08);
      addDelta(deltas, edge.toMapId, -errorX * 0.08, -errorY * 0.08);
    }

    for (let leftIndex = 0; leftIndex < connectedMaps.length; leftIndex += 1) {
      const leftMap = connectedMaps[leftIndex];
      const left = positions.get(leftMap.mapId);
      for (let rightIndex = leftIndex + 1; rightIndex < connectedMaps.length; rightIndex += 1) {
        const rightMap = connectedMaps[rightIndex];
        const right = positions.get(rightMap.mapId);
        const overlapX = (leftMap.width + rightMap.width) / 2 + CARD_GAP - Math.abs(right.x - left.x);
        const overlapY = (leftMap.height + rightMap.height) / 2 + CARD_GAP - Math.abs(right.y - left.y);
        if (overlapX <= 0 || overlapY <= 0) continue;

        if (overlapX <= overlapY) {
          const sign = right.x === left.x
            ? (leftMap.mapId < rightMap.mapId ? 1 : -1)
            : Math.sign(right.x - left.x);
          const push = overlapX * 0.22 * sign;
          addDelta(deltas, leftMap.mapId, -push, 0);
          addDelta(deltas, rightMap.mapId, push, 0);
        } else {
          const sign = right.y === left.y
            ? (leftMap.mapId < rightMap.mapId ? 1 : -1)
            : Math.sign(right.y - left.y);
          const push = overlapY * 0.22 * sign;
          addDelta(deltas, leftMap.mapId, 0, -push);
          addDelta(deltas, rightMap.mapId, 0, push);
        }
      }
    }

    for (const edge of edges) {
      const from = positions.get(edge.fromMapId);
      const to = positions.get(edge.toMapId);
      const start = { x: from.x + edge.fromOffset.x, y: from.y + edge.fromOffset.y };
      const end = { x: to.x + edge.toOffset.x, y: to.y + edge.toOffset.y };
      const lineX = end.x - start.x;
      const lineY = end.y - start.y;
      const lineLength = Math.hypot(lineX, lineY) || 1;
      for (const obstacleMap of connectedMaps) {
        if (obstacleMap.mapId === edge.fromMapId || obstacleMap.mapId === edge.toMapId) continue;
        const obstacle = positions.get(obstacleMap.mapId);
        if (!segmentIntersectsMap(start, end, obstacleMap, obstacle)) continue;

        const projection = Math.max(0, Math.min(1, (
          (obstacle.x - start.x) * lineX + (obstacle.y - start.y) * lineY
        ) / (lineLength * lineLength)));
        const normalX = -lineY / lineLength;
        const normalY = lineX / lineLength;
        const signedDistance = (
          (obstacle.x - start.x - projection * lineX) * normalX
          + (obstacle.y - start.y - projection * lineY) * normalY
        );
        const side = Math.sign(signedDistance) || (obstacleMap.mapId % 2 ? 1 : -1);
        const clearance = (
          Math.abs(normalX) * obstacleMap.width / 2
          + Math.abs(normalY) * obstacleMap.height / 2
          + CARD_GAP
          - Math.abs(signedDistance)
        );
        const push = Math.min(12, Math.max(1, clearance * 0.24));
        const pushX = normalX * side * push;
        const pushY = normalY * side * push;
        addDelta(deltas, edge.fromMapId, -pushX, -pushY);
        addDelta(deltas, edge.toMapId, -pushX, -pushY);
      }
    }

    for (const map of connectedMaps) {
      const position = positions.get(map.mapId);
      const delta = deltas.get(map.mapId);
      const distance = Math.hypot(delta.x, delta.y);
      const scale = distance > 12 ? 12 / distance : 1;
      position.x += delta.x * scale;
      position.y += delta.y * scale;
    }
  }
}

function getBounds(maps, positions) {
  if (!maps.length) return null;
  return maps.reduce((bounds, map) => {
    const point = positions.get(map.mapId);
    return {
      minX: Math.min(bounds.minX, point.x - map.width / 2),
      minY: Math.min(bounds.minY, point.y - map.height / 2),
      maxX: Math.max(bounds.maxX, point.x + map.width / 2),
      maxY: Math.max(bounds.maxY, point.y + map.height / 2),
    };
  }, { minX: Infinity, minY: Infinity, maxX: -Infinity, maxY: -Infinity });
}

function placeUnconnectedMaps(maps, positions, regionOrder, layoutWidth, layoutHeight) {
  if (!maps.length) return;
  const connectedMaps = [...positions.keys()].map((mapId) => maps.find((map) => map.mapId === mapId));
  const bounds = getBounds(connectedMaps, positions) ?? {
    minX: layoutWidth / 2 - 50,
    minY: layoutHeight / 2 - 50,
    maxX: layoutWidth / 2 + 50,
    maxY: layoutHeight / 2 + 50,
  };
  const groups = new Map();
  for (const map of maps) {
    if (positions.has(map.mapId)) continue;
    if (!groups.has(map.region)) groups.set(map.region, []);
    groups.get(map.region).push(map);
  }

  let rowIndex = 0;
  const orderedGroups = [...regionOrder, ...[...groups.keys()].filter((region) => !regionOrder.includes(region))]
    .map((region) => groups.get(region))
    .filter(Boolean);
  for (const group of orderedGroups) {
    group.sort((left, right) => left.mapId - right.mapId);
    const span = Math.max(bounds.maxX - bounds.minX, group.length * 112);
    const rowY = bounds.minY - 128 - rowIndex * 96;
    rowIndex += 1;
    for (let index = 0; index < group.length; index += 1) {
      const along = (index + 1) / (group.length + 1);
      const map = group[index];
      positions.set(map.mapId, {
        x: (bounds.minX + bounds.maxX - span) / 2 + span * along,
        y: rowY,
      });
    }
  }
}

function checkConnectionConstraints(maps, positions, edges) {
  const violations = [];
  for (const edge of edges) {
    const from = positions.get(edge.fromMapId);
    const to = positions.get(edge.toMapId);
    const start = { x: from.x + edge.fromOffset.x, y: from.y + edge.fromOffset.y };
    const end = { x: to.x + edge.toOffset.x, y: to.y + edge.toOffset.y };
    const reasons = [];
    if (Math.hypot(end.x - start.x, end.y - start.y) > MAX_CONNECTION_DISTANCE) {
      reasons.push("传送点距离过远");
    }
    for (const obstacleMap of maps) {
      if (obstacleMap.mapId === edge.fromMapId || obstacleMap.mapId === edge.toMapId) continue;
      if (segmentIntersectsMap(start, end, obstacleMap, positions.get(obstacleMap.mapId))) {
        reasons.push(`连线穿过 map_${String(obstacleMap.mapId).padStart(3, "0")}`);
        break;
      }
    }
    if (reasons.length) violations.push({ id: edge.id, reasons });
  }
  return violations;
}

function countOverlaps(maps, positions) {
  let overlapCount = 0;
  for (let leftIndex = 0; leftIndex < maps.length; leftIndex += 1) {
    const leftMap = maps[leftIndex];
    const left = positions.get(leftMap.mapId);
    for (let rightIndex = leftIndex + 1; rightIndex < maps.length; rightIndex += 1) {
      const rightMap = maps[rightIndex];
      const right = positions.get(rightMap.mapId);
      if (
        Math.abs(right.x - left.x) < (leftMap.width + rightMap.width) / 2
        && Math.abs(right.y - left.y) < (leftMap.height + rightMap.height) / 2
      ) {
        overlapCount += 1;
      }
    }
  }
  return overlapCount;
}

function interpolatePositions(start, end, fraction) {
  return new Map([...start].map(([mapId, position]) => {
    const target = end.get(mapId);
    return [mapId, {
      x: position.x + (target.x - position.x) * fraction,
      y: position.y + (target.y - position.y) * fraction,
    }];
  }));
}

function keepValidImprovement(maps, startingPositions, arrangedPositions, edges) {
  const startingOverlapCount = countOverlaps(maps, startingPositions);
  const isSafe = (positions) =>
    checkConnectionConstraints(maps, positions, edges).length === 0
    && countOverlaps(maps, positions) <= startingOverlapCount;
  if (isSafe(arrangedPositions)) return { positions: arrangedPositions, fraction: 1 };

  let minimum = 0;
  let maximum = 1;
  for (let iteration = 0; iteration < 24; iteration += 1) {
    const middle = (minimum + maximum) / 2;
    if (isSafe(interpolatePositions(startingPositions, arrangedPositions, middle))) {
      minimum = middle;
    } else {
      maximum = middle;
    }
  }
  return {
    positions: interpolatePositions(startingPositions, arrangedPositions, minimum),
    fraction: minimum,
  };
}

function reduceOverlaps(maps, positions, edges) {
  for (let attempt = 0; attempt < maps.length * 4; attempt += 1) {
    const currentOverlapCount = countOverlaps(maps, positions);
    if (!currentOverlapCount) return;

    let bestPositions = null;
    let bestOverlapCount = currentOverlapCount;
    for (let leftIndex = 0; leftIndex < maps.length; leftIndex += 1) {
      const leftMap = maps[leftIndex];
      const left = positions.get(leftMap.mapId);
      for (let rightIndex = leftIndex + 1; rightIndex < maps.length; rightIndex += 1) {
        const rightMap = maps[rightIndex];
        const right = positions.get(rightMap.mapId);
        const overlapX = (leftMap.width + rightMap.width) / 2 - Math.abs(right.x - left.x);
        const overlapY = (leftMap.height + rightMap.height) / 2 - Math.abs(right.y - left.y);
        if (overlapX <= 0 || overlapY <= 0) continue;

        const directions = [
          ...[
            [true, overlapX, Math.sign(right.x - left.x || 1)],
            [false, overlapY, Math.sign(right.y - left.y || 1)],
          ].sort((first, second) => first[1] - second[1])
            .filter(([, overlap]) => overlap > 0)
            .flatMap(([horizontal, overlap, sign]) => [
              [leftMap.mapId, -sign, overlap, horizontal],
              [rightMap.mapId, sign, overlap, horizontal],
            ]),
        ];
        for (const [mapId, direction, overlap, horizontal] of directions) {
          const candidate = new Map([...positions].map(([id, position]) => [id, { ...position }]));
          const point = candidate.get(mapId);
          if (horizontal) point.x += direction * (overlap + CARD_GAP + 1);
          else point.y += direction * (overlap + CARD_GAP + 1);
          if (checkConnectionConstraints(maps, candidate, edges).length) continue;
          const overlapCount = countOverlaps(maps, candidate);
          if (overlapCount < bestOverlapCount) {
            bestPositions = candidate;
            bestOverlapCount = overlapCount;
          }
        }
        if (bestOverlapCount < currentOverlapCount) break;
      }
      if (bestOverlapCount < currentOverlapCount) break;
    }
    if (!bestPositions) return;
    positions.clear();
    for (const [mapId, position] of bestPositions) positions.set(mapId, position);
  }
}

export function layoutMaps(maps, connections, teleportsById, regionOrder, layoutWidth, layoutHeight) {
  const mapsById = new Map(maps.map((map) => [map.mapId, map]));
  const adjacency = new Map(maps.map((map) => [map.mapId, []]));
  const edges = connections.map((connection) => {
    const fromMap = mapsById.get(connection.fromMapId);
    const toMap = mapsById.get(connection.toMapId);
    const fromTeleport = teleportsById.get(connection.fromTeleportId);
    const toTeleport = teleportsById.get(connection.toTeleportId);
    if (!fromMap || !toMap || !fromTeleport || !toTeleport) {
      throw new Error(`连接 ${connection.id} 引用了不存在的地图或传送点。`);
    }
    const edge = {
      ...connection,
      vector: connectionVector(fromTeleport, toTeleport),
      fromOffset: teleportOffset(fromMap, fromTeleport),
      toOffset: teleportOffset(toMap, toTeleport),
    };
    adjacency.get(edge.fromMapId).push({ edge, targetMapId: edge.toMapId, sign: 1 });
    adjacency.get(edge.toMapId).push({ edge, targetMapId: edge.fromMapId, sign: -1 });
    return edge;
  });

  for (const adjacent of adjacency.values()) {
    adjacent.sort((left, right) => left.targetMapId - right.targetMapId);
  }

  const positions = new Map();
  for (const map of maps) {
    if (adjacency.get(map.mapId).length === 0) continue;
    positions.set(map.mapId, { x: map.x + map.width / 2, y: map.y + map.height / 2 });
  }

  const startingPositions = new Map([...positions].map(([mapId, position]) => [mapId, { ...position }]));
  const connectedMaps = [...positions.keys()].map((mapId) => mapsById.get(mapId));
  relaxConnectedMaps(connectedMaps, edges, positions);
  const validLayout = keepValidImprovement(connectedMaps, startingPositions, positions, edges);
  positions.clear();
  for (const [mapId, position] of validLayout.positions) positions.set(mapId, position);
  placeUnconnectedMaps(maps, positions, regionOrder, layoutWidth, layoutHeight);
  reduceOverlaps(maps, positions, edges);

  const violations = checkConnectionConstraints(maps, positions, edges);
  if (violations.length) {
    const sample = violations.slice(0, 3)
      .map((item) => `${item.id}: ${item.reasons.join("、")}`)
      .join("；");
    throw new Error(`排版后仍有 ${violations.length} 条连线不符合保存条件：${sample}`);
  }
  const overlapCount = countOverlaps(maps, positions);
  if (overlapCount) throw new Error(`排版后仍有 ${overlapCount} 处地图重叠，请手动调整后重试。`);

  const bounds = getBounds(maps, positions);
  const width = Math.max(layoutWidth, Math.ceil(bounds.maxX - bounds.minX + 96));
  const height = Math.max(layoutHeight, Math.ceil(bounds.maxY - bounds.minY + 96));
  const offsetX = (width - (bounds.maxX - bounds.minX)) / 2 - bounds.minX;
  const offsetY = (height - (bounds.maxY - bounds.minY)) / 2 - bounds.minY;
  const arrangedPositions = new Map();
  for (const map of maps) {
    const position = positions.get(map.mapId);
    arrangedPositions.set(map.mapId, {
      x: Math.round(position.x + offsetX - map.width / 2),
      y: Math.round(position.y + offsetY - map.height / 2),
    });
  }

  return {
    positions: arrangedPositions,
    width,
    height,
    unconnectedMapCount: maps.length - connectedMaps.length,
    optimizationFraction: validLayout.fraction,
    overlapCount,
  };
}
