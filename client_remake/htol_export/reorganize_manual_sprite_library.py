#!/usr/bin/env python3
"""Validate the categorized, human-readable sprite library and its file indexes."""

from __future__ import annotations

import csv
import json
from pathlib import Path


ROOT = Path(__file__).resolve().parent
ASSET_ROOTS = {
    "map",
    "unit",
    "monster",
    "player",
    "effect",
    "icon",
    "pet",
    "npc",
    "other",
}


def read_csv(path: Path) -> list[dict[str, str]]:
    with path.open("r", encoding="utf-8-sig", newline="") as stream:
        return list(csv.DictReader(stream))


def validate_asset_path(relative_path: str, context: str) -> None:
    path = Path(relative_path)
    if path.is_absolute() or not path.parts or path.parts[0] not in ASSET_ROOTS:
        raise ValueError(f"{context} uses an invalid asset path: {relative_path}")
    resolved = (ROOT / path).resolve()
    if ROOT.resolve() not in resolved.parents or not resolved.is_file():
        raise FileNotFoundError(f"{context} references a missing asset: {relative_path}")


def main() -> None:
    manifest = json.loads((ROOT / "sprite_library.json").read_text(encoding="utf-8"))
    sprites = manifest.get("sprites")
    if manifest.get("format") != "htol-readable-sprite-library" or not isinstance(
        sprites, list
    ):
        raise ValueError("sprite_library.json format is invalid")

    image_count = 0
    for sprite in sprites:
        for group in sprite.get("groups", []):
            validate_asset_path(group["file"], f"sprite {sprite['spriteId']} group")
            image_count += 1
        for component in sprite.get("components", []):
            validate_asset_path(
                component["file"], f"sprite {sprite['spriteId']} component"
            )
            image_count += 1

    indexed_files = 0
    for row in read_csv(ROOT / "asset_file_index.csv"):
        validate_asset_path(row["file"], row["file_kind"])
        indexed_files += 1

    print(
        json.dumps(
            {
                "sprites": len(sprites),
                "spriteImages": image_count,
                "indexedFiles": indexed_files,
            },
            ensure_ascii=False,
            indent=2,
        )
    )


if __name__ == "__main__":
    main()
