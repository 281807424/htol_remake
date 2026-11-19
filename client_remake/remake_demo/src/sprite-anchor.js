export function placeSpriteAtLogicalAnchor(sprite, point, entry) {
  sprite
    .setOrigin(entry.originX, entry.originY)
    .setPosition(point.x, point.y);
}
