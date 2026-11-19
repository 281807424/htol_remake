import assert from 'node:assert/strict';
import test from 'node:test';

import { placeSpriteAtLogicalAnchor } from './sprite-anchor.js';

test('sprite frames keep their source draw anchor fixed at the logical point', () => {
  const calls = [];
  const sprite = {
    setOrigin(...origin) {
      calls.push(['origin', ...origin]);
      return this;
    },
    setPosition(...position) {
      calls.push(['position', ...position]);
      return this;
    },
  };
  const entries = [
    { originX: 0.5, originY: 0.9, frame: { drawOffsetX: -21, drawOffsetY: -31 } },
    { originX: 0.4, originY: 0.8, frame: { drawOffsetX: -39, drawOffsetY: -29 } },
  ];
  const point = { x: 200, y: 300 };

  for (const entry of entries) {
    placeSpriteAtLogicalAnchor(sprite, point, entry);
  }

  assert.deepEqual(calls, [
    ['origin', 0.5, 0.9],
    ['position', 200, 300],
    ['origin', 0.4, 0.8],
    ['position', 200, 300],
  ]);
});
