export function snapFollowTargetToPixelGrid(x, y, camera) {
  const originX = camera.width * camera.originX;
  const originY = camera.height * camera.originY;
  return {
    x: originX + Math.round((x - originX) * camera.zoomX) / camera.zoomX,
    y: originY + Math.round((y - originY) * camera.zoomY) / camera.zoomY,
  };
}

export function getPixelAlignedCameraBounds(camera, x, y, width, height) {
  const horizontal = getAxisBounds(
    x,
    width,
    camera.width,
    camera.zoomX,
  );
  const vertical = getAxisBounds(
    y,
    height,
    camera.height,
    camera.zoomY,
  );
  return {
    x: horizontal.start,
    y: vertical.start,
    width: horizontal.size,
    height: vertical.size,
  };
}

function getAxisBounds(start, size, viewportSize, zoom) {
  const displaySize = viewportSize / zoom;
  const halfExpansion = (displaySize - viewportSize) / 2;
  const minScroll = start + halfExpansion;
  const maxScroll = Math.max(
    minScroll,
    start + size - (displaySize + viewportSize) / 2,
  );
  const minPixel = Math.ceil(minScroll * zoom - 1e-9);
  const maxPixel = Math.floor(maxScroll * zoom + 1e-9);
  const minAlignedScroll = minPixel <= maxPixel
    ? minPixel / zoom
    : Math.round(minScroll * zoom) / zoom;
  const maxAlignedScroll = Math.max(
    minAlignedScroll,
    maxPixel >= minPixel ? maxPixel / zoom : minAlignedScroll,
  );
  return {
    start: minAlignedScroll - halfExpansion,
    size: maxAlignedScroll - minAlignedScroll + displaySize,
  };
}
