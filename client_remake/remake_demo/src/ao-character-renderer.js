const AO_TO_MIDP_TRANSFORM = [0, 2, 1, 3, 5, 7, 4, 6];
const HORIZONTAL_TRANSFORMS = [1, 0, 3, 2, 6, 7, 4, 5];
const VERTICAL_TRANSFORMS = [2, 3, 0, 1, 5, 4, 7, 6];
const ROTATED_TRANSFORMS = [4, 5, 6, 7, 3, 2, 1, 0];
const TRANSFORM_MATRICES = [
  [1, 0, 0, 1],
  [1, 0, 0, -1],
  [-1, 0, 0, 1],
  [-1, 0, 0, -1],
  [0, 1, 1, 0],
  [0, 1, -1, 0],
  [0, -1, 1, 0],
  [0, -1, -1, 0],
];
const AO_CANVAS_SIZE = 512;
const AO_ROOT_COORDINATE = AO_CANVAS_SIZE / 2;

export function getAppearanceSlotOrder(character) {
  if (!Array.isArray(character.appearanceSlots)) {
    throw new Error(`sprite ${character.spriteId} has no appearance slots`);
  }
  const slots = character.appearanceSlots.map(({ slot }) => slot);
  if (
    !slots.length ||
    slots.some((slot) => !Number.isInteger(slot) || slot < 0) ||
    new Set(slots).size !== slots.length ||
    !slots.includes(0)
  ) {
    throw new Error(`sprite ${character.spriteId} has invalid appearance slots`);
  }
  return [...slots].sort((left, right) => left - right);
}

export function cycleAppearanceSlot(character, currentSlot) {
  const slots = getAppearanceSlotOrder(character);
  const currentIndex = slots.indexOf(currentSlot);
  if (currentIndex < 0) {
    throw new Error(
      `sprite ${character.spriteId} has no appearance slot ${currentSlot}`,
    );
  }
  return slots[(currentIndex + 1) % slots.length];
}

function getAppearanceMapping(character, slot) {
  const selectedSlot = character.appearanceSlots.find(
    (candidate) => candidate.slot === slot,
  );
  if (!selectedSlot) {
    throw new Error(`sprite ${character.spriteId} has no appearance slot ${slot}`);
  }
  return selectedSlot.mapping;
}

function getTransformedSize(width, height, flags) {
  const transform = AO_TO_MIDP_TRANSFORM[flags & 7];
  return transform >= 4 ? [height, width] : [width, height];
}

export function buildAOFrameCommands(
  character,
  groupId,
  frameIndex,
  appearanceSlot,
) {
  const group = character.groups[groupId];
  if (!group || !Number.isInteger(frameIndex) || frameIndex < 0 || frameIndex >= group[0]) {
    throw new Error(
      `sprite ${character.spriteId} group ${groupId} has no frame ${frameIndex}`,
    );
  }
  const mapping = getAppearanceMapping(character, appearanceSlot);
  const animation = character.animations[group[1] + frameIndex];
  const [componentId, , offsetX, offsetY, animationFlags] = animation;
  const rootComponentId = componentId | ((animationFlags & 0xc0) << 2);
  const rootFlags = animationFlags & 0x0f;
  const commands = [];
  const activePath = new Set();
  const bounds = {
    left: AO_CANVAS_SIZE,
    top: AO_CANVAS_SIZE,
    right: 0,
    bottom: 0,
  };

  const drawComposite = (compositeId, x, y, transform) => {
    if (activePath.has(compositeId)) {
      throw new Error(`sprite ${character.spriteId} has a cyclic AO component ${compositeId}`);
    }
    const composite = character.frames[compositeId];
    if (!composite) {
      throw new Error(`sprite ${character.spriteId} has invalid AO composite ${compositeId}`);
    }
    activePath.add(compositeId);
    const [itemCount, firstItem] = composite;
    for (let itemIndex = firstItem; itemIndex < firstItem + itemCount; itemIndex += 1) {
      const [imageId, sourceX, sourceY, sourceFlags] = character.drawItems[itemIndex];
      let mappedId = imageId;
      if (!(sourceFlags & 0x10)) {
        mappedId = mapping[imageId] ?? imageId;
      }
      const size = character.componentSizes[mappedId];
      if (!size) {
        throw new Error(`sprite ${character.spriteId} maps to invalid component ${mappedId}`);
      }
      let [width, height] = size;
      let flags = sourceFlags;
      let drawX = sourceX;
      let drawY = sourceY;
      if (flags & 0x10) {
        width = 0;
        height = 0;
      }
      if (flags & 4) [width, height] = [height, width];

      if (transform & 1) {
        flags = (flags & ~7) | HORIZONTAL_TRANSFORMS[flags & 7];
        drawX = -drawX - width;
      }
      if (transform & 2) {
        flags = (flags & ~7) | VERTICAL_TRANSFORMS[flags & 7];
        drawY = -drawY - height;
      }
      if (transform & 4) {
        flags = (flags & ~7) | ROTATED_TRANSFORMS[flags & 7];
        [drawX, drawY] = [-drawY - height, drawX];
      }
      drawX += x;
      drawY += y;

      if (flags & 0x10) {
        drawComposite(mappedId, drawX, drawY, flags & 0x0f);
        continue;
      }

      const source = character.componentRects[mappedId];
      if (!source || width === 0 || height === 0) {
        throw new Error(
          `sprite ${character.spriteId} component ${mappedId} has no bitmap`,
        );
      }
      const [outputWidth, outputHeight] = getTransformedSize(
        source.width,
        source.height,
        flags,
      );
      const command = {
        componentId: mappedId,
        source,
        x: drawX,
        y: drawY,
        width: source.width,
        height: source.height,
        outputWidth,
        outputHeight,
        transform: AO_TO_MIDP_TRANSFORM[flags & 7],
      };
      commands.push(command);
      bounds.left = Math.min(bounds.left, drawX);
      bounds.top = Math.min(bounds.top, drawY);
      bounds.right = Math.max(bounds.right, drawX + outputWidth);
      bounds.bottom = Math.max(bounds.bottom, drawY + outputHeight);
    }
    activePath.delete(compositeId);
  };

  drawComposite(
    rootComponentId,
    AO_ROOT_COORDINATE + offsetX,
    AO_ROOT_COORDINATE + offsetY,
    rootFlags,
  );
  if (!commands.length) {
    throw new Error(
      `sprite ${character.spriteId} group ${groupId} frame ${frameIndex} is empty`,
    );
  }
  return {
    commands,
    bounds: {
      x: bounds.left,
      y: bounds.top,
      width: bounds.right - bounds.left,
      height: bounds.bottom - bounds.top,
    },
  };
}

export function renderAOFrame(
  context,
  character,
  componentAtlas,
  groupId,
  frameIndex,
  appearanceSlot,
) {
  const { commands, bounds } = buildAOFrameCommands(
    character,
    groupId,
    frameIndex,
    appearanceSlot,
  );
  context.clearRect(0, 0, AO_CANVAS_SIZE, AO_CANVAS_SIZE);
  context.imageSmoothingEnabled = false;

  for (const command of commands) {
    const [a, b, c, d] = TRANSFORM_MATRICES[command.transform];
    context.save();
    context.translate(
      command.x + command.outputWidth / 2,
      command.y + command.outputHeight / 2,
    );
    context.transform(a, b, c, d, 0, 0);
    context.drawImage(
      componentAtlas,
      command.source.x,
      command.source.y,
      command.source.width,
      command.source.height,
      -command.width / 2,
      -command.height / 2,
      command.width,
      command.height,
    );
    context.restore();
  }
  return bounds;
}
