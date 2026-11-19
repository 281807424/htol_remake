import json
import sys
from pathlib import Path

from PIL import Image


MAX_SIZE = (176, 68)


def main():
    if len(sys.argv) != 5:
        raise SystemExit("Usage: thumbnail_cache.py world-map.json map-catalog.json maps-dir cache-dir")

    world_path, catalog_path, maps_root, cache_root = map(Path, sys.argv[1:])
    world = json.loads(world_path.read_text(encoding="utf-8"))
    catalog = json.loads(catalog_path.read_text(encoding="utf-8"))
    maps_by_id = {record["mapId"]: record for record in catalog["maps"]}
    cache_root.mkdir(parents=True, exist_ok=True)
    generated = 0

    for world_map in world["maps"]:
        record = maps_by_id.get(world_map["mapId"])
        if record is None:
            raise ValueError(f"Missing map catalog record for map {world_map['mapId']}.")
        image_path = maps_root / record["sourceCategory"] / record["filename"]
        thumbnail_path = cache_root / f"map_{world_map['mapId']}.png"
        metadata_path = cache_root / f"map_{world_map['mapId']}.meta"
        image_stat = image_path.stat()
        signature = f"{image_stat.st_mtime_ns}:{image_stat.st_size}"
        if (
            thumbnail_path.exists()
            and metadata_path.exists()
            and metadata_path.read_text(encoding="ascii") == signature
        ):
            continue

        with Image.open(image_path) as image:
            image.thumbnail(MAX_SIZE, Image.Resampling.LANCZOS)
            image.save(thumbnail_path, format="PNG", optimize=True)
        metadata_path.write_text(signature, encoding="ascii")
        generated += 1

    print(json.dumps({"generated": generated, "total": len(world["maps"])}))


if __name__ == "__main__":
    main()
