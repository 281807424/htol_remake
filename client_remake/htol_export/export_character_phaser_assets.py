#!/usr/bin/env python3
"""Rebuild the demo character animation atlases directly from the raw AO resource."""

from __future__ import annotations

import csv
import json
import math
from pathlib import Path
from typing import Any

from PIL import Image

from generate_character_appearance_preview import (
    load_appearance_mappings,
    parse_ao_sprite,
    render_ao_frame_with_anchor,
)
from sprite_library_taxonomy import role_animation_details


ROOT = Path(__file__).resolve().parent
IMAGE_ROOT = ROOT / "images"
SPRITE_LIBRARY_ROOT = IMAGE_ROOT / "sprite_library"
ROLE_SPRITE_IDS = (74, 75, 76)
TICK_MS = 50
AO_RUNTIME_PATH = IMAGE_ROOT / "weapon_appearance_export" / "ao_runtime.json"
AO_COMPONENT_ROOT = SPRITE_LIBRARY_ROOT / "角色" / "ao_components"


def _read_csv(path: Path) -> list[dict[str, str]]:
    with path.open("r", encoding="utf-8-sig", newline="") as stream:
        return list(csv.DictReader(stream))


def _write_csv(path: Path, rows: list[dict[str, str]]) -> None:
    if not rows:
        return
    fields = list(rows[0])
    with path.open("w", encoding="utf-8-sig", newline="") as stream:
        writer = csv.DictWriter(stream, fieldnames=fields, lineterminator="\n")
        writer.writeheader()
        writer.writerows(rows)


def _pack_component_atlas(sprite: Any) -> tuple[Image.Image, list[dict[str, int] | None]]:
    components = [
        (component_id, bitmap)
        for component_id, bitmap in enumerate(sprite.bitmaps)
        if bitmap is not None and bitmap.width > 0 and bitmap.height > 0
    ]
    if not components:
        raise ValueError("AO sprite has no bitmap components")

    padding = 1
    area = sum((bitmap.width + padding) * (bitmap.height + padding) for _, bitmap in components)
    widest = max(bitmap.width + padding for _, bitmap in components)
    target_width = max(256, widest, math.ceil(math.sqrt(area)))
    atlas_width = 1 << (target_width - 1).bit_length()
    component_rects: list[dict[str, int] | None] = [None] * len(sprite.bitmaps)
    placements = []
    x = y = row_height = used_width = 0
    for component_id, bitmap in components:
        if x and x + bitmap.width + padding > atlas_width:
            x = 0
            y += row_height + padding
            row_height = 0
        placements.append((component_id, bitmap, x, y))
        component_rects[component_id] = {
            "x": x,
            "y": y,
            "width": bitmap.width,
            "height": bitmap.height,
        }
        x += bitmap.width + padding
        used_width = max(used_width, x)
        row_height = max(row_height, bitmap.height)

    atlas = Image.new("RGBA", (used_width, y + row_height), (0, 0, 0, 0))
    for _, bitmap, x, y in placements:
        atlas.alpha_composite(bitmap, (x, y))
    return atlas, component_rects


def _load_appearance_slots(sprite_id: int) -> list[dict[str, Any]]:
    manifest_path = IMAGE_ROOT / "weapon_appearance_export" / "manifest.json"
    appearance_manifest = json.loads(manifest_path.read_text(encoding="utf-8"))
    player = appearance_manifest.get("players", {}).get(str(sprite_id - 74))
    if player is None:
        raise ValueError(f"appearance manifest is missing sprite {sprite_id}")
    raw_slots = player.get("appearance_mapping_slots")
    if not isinstance(raw_slots, dict) or not raw_slots:
        raise ValueError(f"appearance manifest has no /6 slots for sprite {sprite_id}")

    csv_mappings = load_appearance_mappings()
    slots = []
    for slot_text, slot_record in sorted(raw_slots.items(), key=lambda item: int(item[0])):
        slot = int(slot_text)
        mapping = {
            str(int(pair["source_sprite_component_index"])): int(
                pair["target_sprite_component_index"]
            )
            for pair in slot_record["mapping_pairs"]
        }
        if mapping != {
            str(source): target
            for source, target in csv_mappings.get((sprite_id, slot), {}).items()
        }:
            raise ValueError(
                f"sprite {sprite_id} slot {slot} differs between /6 manifest and CSV"
            )
        slots.append({"slot": slot, "mapping": mapping})

    if slots[0]["slot"] != 0:
        raise ValueError(f"sprite {sprite_id} /6 slots do not include base slot 0")
    return slots


def _export_ao_runtime_character(sprite: Any, sprite_id: int) -> dict[str, Any]:
    atlas, component_rects = _pack_component_atlas(sprite)
    atlas_path = AO_COMPONENT_ROOT / f"sprite_{sprite_id:03d}.png"
    atlas_path.parent.mkdir(parents=True, exist_ok=True)
    atlas.save(atlas_path, format="PNG", optimize=True)
    appearance_slots = _load_appearance_slots(sprite_id)
    for slot in appearance_slots:
        for source, target in slot["mapping"].items():
            target_id = int(target)
            if target_id >= len(sprite.bitmaps) or sprite.bitmaps[target_id] is None:
                raise ValueError(
                    f"sprite {sprite_id} slot {slot['slot']} maps component "
                    f"{source} to a non-bitmap component {target_id}"
                )

    return {
        "spriteId": sprite_id,
        "componentAtlasFile": atlas_path.relative_to(IMAGE_ROOT).as_posix(),
        "componentTextureKey": f"ao-components-{sprite_id}",
        "componentSizes": sprite.component_sizes,
        "componentTypes": sprite.component_types,
        "componentRects": component_rects,
        "drawItems": sprite.draw_items,
        "frames": sprite.frames,
        "animations": sprite.animations,
        "groups": sprite.groups,
        "appearanceSlots": appearance_slots,
    }


def _build_group(
    sprite: Any,
    group_id: int,
    group: dict[str, Any],
) -> dict[str, Any]:
    frame_count, start = sprite.groups[group_id]
    rendered_frames = []
    left = top = right = bottom = 0
    total_ticks = 0

    for frame_index in range(frame_count):
        image, offset_x, offset_y = render_ao_frame_with_anchor(
            sprite, group_id, frame_index, {}
        )
        duration_ticks = max(1, sprite.animations[start + frame_index][1])
        total_ticks += duration_ticks
        left = min(left, offset_x)
        top = min(top, offset_y)
        right = max(right, offset_x + image.width)
        bottom = max(bottom, offset_y + image.height)
        rendered_frames.append(
            {
                "image": image,
                "sourceFrameIndex": frame_index,
                "durationTicks": duration_ticks,
                "durationMs": duration_ticks * TICK_MS,
                "drawOffsetX": offset_x,
                "drawOffsetY": offset_y,
            }
        )

    frame_width = max(1, right - left)
    frame_height = max(1, bottom - top)
    columns = max(1, int(group.get("columns", math.ceil(math.sqrt(frame_count)))))
    rows = math.ceil(frame_count / columns)
    atlas = Image.new(
        "RGBA",
        (columns * frame_width, rows * frame_height),
        (0, 0, 0, 0),
    )
    frames = []
    for frame_index, frame in enumerate(rendered_frames):
        column = frame_index % columns
        row = frame_index // columns
        image = frame["image"]
        atlas.alpha_composite(
            image,
            (
                column * frame_width + frame["drawOffsetX"] - left,
                row * frame_height + frame["drawOffsetY"] - top,
            ),
        )
        frames.append(
            {
                "sourceFrameIndex": frame["sourceFrameIndex"],
                "durationMs": frame["durationMs"],
                "durationTicks": frame["durationTicks"],
                "drawOffsetX": frame["drawOffsetX"],
                "drawOffsetY": frame["drawOffsetY"],
                "offsetKnown": True,
                "width": image.width,
                "height": image.height,
                "sheetFrame": frame_index,
            }
        )

    output_path = SPRITE_LIBRARY_ROOT / group["file"]
    output_path.parent.mkdir(parents=True, exist_ok=True)
    atlas.save(output_path, format="PNG", optimize=True)
    group.update(
        {
            "frameCount": frame_count,
            "frameWidth": frame_width,
            "frameHeight": frame_height,
            "columns": columns,
            "rows": rows,
            "anchorX": -left,
            "anchorY": -top,
            "originX": -left / frame_width,
            "originY": -top / frame_height,
            "durationMs": total_ticks * TICK_MS,
            "frames": frames,
            "renderSource": "raw-ao-root-anchor",
        }
    )
    return group


def export_character_phaser_assets(
    manifest: dict[str, Any] | None = None,
) -> dict[str, Any]:
    manifest_path = SPRITE_LIBRARY_ROOT / "sprite_library.json"
    if manifest is None:
        manifest = json.loads(manifest_path.read_text(encoding="utf-8"))
    groups_by_sprite: dict[int, dict[int, dict[str, Any]]] = {}
    ao_characters = {}
    sprites_by_id = {
        int(item["spriteId"]): item for item in manifest["sprites"]
    }
    for sprite_id in ROLE_SPRITE_IDS:
        character = sprites_by_id.get(sprite_id)
        if character is None:
            raise ValueError(f"sprite {sprite_id} is missing from sprite_library.json")

        raw_path = ROOT / "raw_resources" / "sprites" / f"resource_{sprite_id:03d}.bin"
        sprite = parse_ao_sprite(raw_path.read_bytes(), sprite_id)
        ao_characters[str(sprite_id)] = _export_ao_runtime_character(sprite, sprite_id)
        groups_by_id = {
            int(group["animationGroup"]): group for group in character["groups"]
        }
        groups_by_sprite[sprite_id] = groups_by_id
        for group_id, group in groups_by_id.items():
            if (
                role_animation_details(sprite_id, group_id) is None
                or group_id >= len(sprite.groups)
                or not sprite.groups[group_id][0]
            ):
                continue
            _build_group(sprite, group_id, group)

    AO_RUNTIME_PATH.write_text(
        json.dumps(
            {"format": 1, "source": "raw AO /5 sprites and /6 mappings", "characters": ao_characters},
            ensure_ascii=False,
            indent=2,
        )
        + "\n",
        encoding="utf-8",
    )
    manifest_path.write_text(
        json.dumps(manifest, ensure_ascii=False, indent=2) + "\n",
        encoding="utf-8",
    )
    _update_animation_indexes(groups_by_sprite)
    _update_asset_file_index(groups_by_sprite)
    return manifest


def _update_animation_indexes(
    groups_by_sprite: dict[int, dict[int, dict[str, Any]]],
) -> None:
    for filename in ("animation_groups.csv", "animation_index.csv"):
        path = IMAGE_ROOT / filename
        rows = _read_csv(path)
        for row in rows:
            sprite_id = int(row["sprite_id"])
            groups_by_id = groups_by_sprite.get(sprite_id)
            if groups_by_id is None:
                continue
            group = groups_by_id.get(int(row["animation_group"]))
            if group is None or group.get("renderSource") != "raw-ao-root-anchor":
                continue
            row["frame_count"] = str(group["frameCount"])
            row["source_total_ticks"] = str(group["durationMs"] // TICK_MS)
            row["source_total_ms"] = str(group["durationMs"])
            row["output_file"] = f"sprite_library/{group['file']}"
        _write_csv(path, rows)


def _update_asset_file_index(
    groups_by_sprite: dict[int, dict[int, dict[str, Any]]],
) -> None:
    path = IMAGE_ROOT / "asset_file_index.csv"
    rows = _read_csv(path)
    for row in rows:
        if (
            row["file_kind"] != "animation_frame"
        ):
            continue
        sprite_id = int(row["sprite_id"] or -1)
        groups_by_id = groups_by_sprite.get(sprite_id)
        if groups_by_id is None:
            continue
        group_id = int(row["animation_group"])
        group = groups_by_id.get(group_id)
        if group is None or group.get("renderSource") != "raw-ao-root-anchor":
            continue
        frame_index = int(row["frame_index"])
        if frame_index >= len(group["frames"]):
            raise ValueError(
                f"sprite {sprite_id} group {group_id} index has invalid frame {frame_index}"
            )
        frame = group["frames"][frame_index]
        offset_x = frame["drawOffsetX"]
        offset_y = frame["drawOffsetY"]
        row.update(
            {
                "delay_ticks": str(frame["durationTicks"]),
                "delay_ms": str(frame["durationMs"]),
                "origin_x_px": str(offset_x),
                "origin_y_px": str(offset_y),
                "bound_min_x_px": str(offset_x),
                "bound_min_y_px": str(offset_y),
                "bound_max_x_px": str(offset_x + frame["width"]),
                "bound_max_y_px": str(offset_y + frame["height"]),
                "width_px": str(frame["width"]),
                "height_px": str(frame["height"]),
                "file": f"sprite_library/{group['file']}",
            }
        )
    _write_csv(path, rows)


if __name__ == "__main__":
    updated = export_character_phaser_assets()
    print(
        json.dumps(
            {
                "sprites": {
                    str(sprite_id): [
                        group["animationGroup"]
                        for group in next(
                            item
                            for item in updated["sprites"]
                            if int(item["spriteId"]) == sprite_id
                        )["groups"]
                        if group.get("renderSource") == "raw-ao-root-anchor"
                    ]
                    for sprite_id in ROLE_SPRITE_IDS
                },
                "source": "raw_resources/sprites/resource_074.bin through resource_076.bin",
                "ao_runtime": AO_RUNTIME_PATH.relative_to(IMAGE_ROOT).as_posix(),
            },
            ensure_ascii=False,
            indent=2,
        )
    )
