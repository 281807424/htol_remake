import assert from 'node:assert/strict';
import test from 'node:test';

import {
  getPixelAlignedCameraBounds,
  snapFollowTargetToPixelGrid,
} from './camera-pixel-snap.js';

test('camera follow aligns both axes to whole screen pixels', () => {
  const camera = {
    width: 1280,
    height: 720,
    originX: 0.5,
    originY: 0.5,
    zoomX: 0.75,
    zoomY: 0.75,
  };

  assert.deepEqual(
    snapFollowTargetToPixelGrid(1001, 361, camera),
    { x: 640 + 271 / 0.75, y: 360 + 1 / 0.75 },
  );
});

test('camera bounds keep the final scroll position on a screen-pixel boundary', () => {
  for (const viewport of [{ width: 1280, height: 720 }, { width: 544, height: 373 }]) {
    const camera = { ...viewport, zoomX: 0.75, zoomY: 0.75 };
    const bounds = getPixelAlignedCameraBounds(camera, 0, 0, 1080, 900);

    for (const [position, size, zoom, viewportSize] of [
      [bounds.x, bounds.width, camera.zoomX, camera.width],
      [bounds.y, bounds.height, camera.zoomY, camera.height],
    ]) {
      const displaySize = viewportSize / zoom;
      const minScroll = position + (displaySize - viewportSize) / 2;
      const maxScroll = position + size - (displaySize + viewportSize) / 2;
      assert.ok(Math.abs(minScroll * zoom - Math.round(minScroll * zoom)) < 1e-10);
      assert.ok(Math.abs(maxScroll * zoom - Math.round(maxScroll * zoom)) < 1e-10);
    }
  }
});
