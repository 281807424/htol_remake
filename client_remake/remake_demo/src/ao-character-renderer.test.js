import assert from 'node:assert/strict';
import { readFile } from 'node:fs/promises';
import test from 'node:test';

import {
  buildAOFrameCommands,
  cycleAppearanceSlot,
  getAppearanceSlotOrder,
  renderAOFrame,
} from './ao-character-renderer.js';

function createCharacter() {
  return {
    spriteId: 74,
    appearanceSlots: [
      { slot: 2, mapping: { 1: 2 } },
      { slot: 0, mapping: {} },
      { slot: 5, mapping: { 1: 3 } },
    ],
    componentSizes: [[0, 0], [3, 2], [4, 2], [2, 2]],
    componentRects: [null, { x: 0, y: 0, width: 3, height: 2 }, { x: 4, y: 0, width: 4, height: 2 }, { x: 9, y: 0, width: 2, height: 2 }],
    frames: [[1, 0]],
    drawItems: [[1, -2, 3, 0]],
    animations: [[0, 1, -4, 5, 0]],
    groups: [[1, 0]],
  };
}

test('appearance slots cycle in ascending order including the base slot', () => {
  const character = createCharacter();
  assert.deepEqual(getAppearanceSlotOrder(character), [0, 2, 5]);
  assert.equal(cycleAppearanceSlot(character, 0), 2);
  assert.equal(cycleAppearanceSlot(character, 2), 5);
  assert.equal(cycleAppearanceSlot(character, 5), 0);
});

test('appearance mapping replaces components without changing the AO root anchor', () => {
  const character = createCharacter();
  const base = buildAOFrameCommands(character, 0, 0, 0);
  const changed = buildAOFrameCommands(character, 0, 0, 2);

  assert.equal(base.commands[0].componentId, 1);
  assert.equal(changed.commands[0].componentId, 2);
  assert.deepEqual([base.commands[0].x, base.commands[0].y], [250, 264]);
  assert.deepEqual([changed.commands[0].x, changed.commands[0].y], [250, 264]);
});

test('mapped component dimensions are used for transformed draw bounds', () => {
  const character = createCharacter();
  character.drawItems[0][3] = 4;

  const mapped = buildAOFrameCommands(character, 0, 0, 2);

  assert.equal(mapped.commands[0].componentId, 2);
  assert.deepEqual(
    [mapped.commands[0].outputWidth, mapped.commands[0].outputHeight],
    [2, 4],
  );
  assert.deepEqual(
    [mapped.bounds.width, mapped.bounds.height],
    [2, 4],
  );
});

test('AO component rotations and reflections match the original transform table', () => {
  const character = createCharacter();
  const transforms = [];
  const context = {
    clearRect() {},
    save() {},
    translate() {},
    transform(...matrix) {
      transforms.push(matrix);
    },
    drawImage() {},
    restore() {},
  };
  const expected = [
    [0, 1, 1, 0, 0, 0],
    [0, -1, -1, 0, 0, 0],
    [0, 1, -1, 0, 0, 0],
    [0, -1, 1, 0, 0, 0],
  ];

  for (const flags of [6, 5, 4, 7]) {
    character.drawItems[0][3] = flags;
    renderAOFrame(context, character, {}, 0, 0, 0);
  }

  assert.deepEqual(transforms, expected);
});

test('all exported character frames render for every original appearance slot', async () => {
  const runtime = JSON.parse(
    await readFile(
      new URL('../../htol_export/player/ao_runtime.json', import.meta.url),
      'utf8',
    ),
  );
  let renderedFrameCount = 0;
  const atlasFiles = new Set();
  const appearanceChanges = new Map();

  for (const character of Object.values(runtime.characters)) {
    atlasFiles.add(character.componentAtlasFile);
    for (const { slot } of character.appearanceSlots) {
      appearanceChanges.set(`${character.spriteId}:${slot}`, 0);
    }
    for (let groupId = 0; groupId < character.groups.length; groupId += 1) {
      const [frameCount] = character.groups[groupId];
      for (let frameIndex = 0; frameIndex < frameCount; frameIndex += 1) {
        const base = buildAOFrameCommands(character, groupId, frameIndex, 0);
        assert.ok(base.bounds.x >= 0 && base.bounds.y >= 0);
        assert.ok(base.bounds.x + base.bounds.width <= 512);
        assert.ok(base.bounds.y + base.bounds.height <= 512);
        renderedFrameCount += 1;
        for (const { slot } of character.appearanceSlots.slice(1)) {
          const rendered = buildAOFrameCommands(
            character,
            groupId,
            frameIndex,
            slot,
          );
          assert.ok(rendered.commands.length > 0);
          assert.ok(rendered.bounds.x >= 0 && rendered.bounds.y >= 0);
          assert.ok(rendered.bounds.x + rendered.bounds.width <= 512);
          assert.ok(rendered.bounds.y + rendered.bounds.height <= 512);
          if (rendered.commands.some(
            ({ componentId }, index) => componentId !== base.commands[index]?.componentId,
          )) {
            const key = `${character.spriteId}:${slot}`;
            appearanceChanges.set(key, appearanceChanges.get(key) + 1);
          }
          renderedFrameCount += 1;
        }
      }
    }
  }

  assert.deepEqual([...atlasFiles].length, 3);
  assert.ok(renderedFrameCount > 10000);
  for (const [slot, changes] of appearanceChanges) {
    if (!slot.endsWith(':0')) assert.ok(changes > 0, `${slot} does not change any component`);
  }
});
