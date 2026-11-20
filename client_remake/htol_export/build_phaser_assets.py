#!/usr/bin/env python3
"""Build lossless Phaser animation atlases and Tiled-compatible map exports."""

from __future__ import annotations

import base64
import csv
import hashlib
import json
import math
import os
import re
from collections import defaultdict
from pathlib import Path
from typing import Any

from PIL import Image

from export_character_phaser_assets import export_character_phaser_assets
from generate_character_appearance_preview import (
    parse_ao_sprite,
    render_ao_frame_with_anchor,
)
from sprite_library_taxonomy import (
    DUPLICATE_SPRITE_IDS,
    asset_folder,
    component_filename,
    group_filename,
    is_role_unarmed_group,
    role_animation_details,
    sprite_category as manual_sprite_category,
    sprite_name as manual_sprite_name,
)


ROOT = Path(__file__).resolve().parent
IMAGE_ROOT = ROOT
PHASER_ROOT = ROOT / "map"
SPRITE_LIBRARY_ROOT = ROOT
MAP_ROOT = PHASER_ROOT / "tiled"
TILESET_ROOT = PHASER_ROOT / "tilesets"
PREVIEW_ROOT = PHASER_ROOT / "preview"
MAP_TILE_SIZE = 36
MAP_TILE_OFFSET = 8
MAP_OBJECT_TRANSFORMS = (
    None,
    Image.Transpose.FLIP_LEFT_RIGHT,
    Image.Transpose.FLIP_TOP_BOTTOM,
    Image.Transpose.ROTATE_180,
    Image.Transpose.ROTATE_270,
    Image.Transpose.ROTATE_90,
    Image.Transpose.TRANSPOSE,
    Image.Transpose.TRANSVERSE,
)
MAX_SPRITESHEET_SIDE = 2048
TILED_FLIP_H = 0x80000000
TILED_FLIP_V = 0x40000000
RAW_SPRITE_CACHE: dict[int, Any] = {}
def transform_map_object_frame(
    image: Image.Image,
    origin_x: int,
    origin_y: int,
    source_transform: int,
) -> tuple[Image.Image, int, int]:
    transform = source_transform & 7
    width, height = image.size
    transpose = MAP_OBJECT_TRANSFORMS[transform]
    if transpose is None:
        return image, origin_x, origin_y

    image = image.transpose(transpose)
    if transform == 1:
        origin_x = -origin_x - width
    elif transform == 2:
        origin_y = -origin_y - height
    elif transform == 3:
        origin_x, origin_y = -origin_x - width, -origin_y - height
    elif transform == 4:
        origin_x, origin_y = -origin_y - height, origin_x
    elif transform == 5:
        origin_x, origin_y = origin_y, -origin_x - width
    elif transform == 6:
        origin_x, origin_y = origin_y, origin_x
    else:
        origin_x, origin_y = -origin_y - height, -origin_x - width
    return image, origin_x, origin_y


def read_csv(path: Path) -> list[dict[str, str]]:
    with path.open("r", encoding="utf-8-sig", newline="") as stream:
        return list(csv.DictReader(stream))


def write_csv(path: Path, fields: list[str], rows: list[dict[str, Any]]) -> None:
    path.parent.mkdir(parents=True, exist_ok=True)
    with path.open("w", encoding="utf-8-sig", newline="") as stream:
        writer = csv.DictWriter(stream, fieldnames=fields, extrasaction="ignore")
        writer.writeheader()
        writer.writerows(rows)


def write_json(path: Path, data: Any) -> None:
    path.parent.mkdir(parents=True, exist_ok=True)
    path.write_text(
        json.dumps(data, ensure_ascii=False, indent=2) + "\n", encoding="utf-8"
    )


def group_name(sprite_id: int, animation_group: int, frame_count: int) -> str:
    if sprite_id == 76:
        return {
            43: "idle-up",
            44: "idle-down",
            45: "idle-left-right",
            46: "walk-left-right",
            47: "walk-up",
            48: "walk-down",
        }.get(animation_group, f"animation-group-{animation_group:03d}")
    if frame_count > 1:
        return f"animation-group-{animation_group:03d}"
    return f"variant-{animation_group:03d}"


def existing_library_sprites() -> dict[int, dict[str, Any]]:
    manifest_path = SPRITE_LIBRARY_ROOT / "sprite_library.json"
    if not manifest_path.is_file():
        return {}
    manifest = json.loads(manifest_path.read_text(encoding="utf-8"))
    return {int(sprite["spriteId"]): sprite for sprite in manifest.get("sprites", [])}


def load_source_frame(
    row: dict[str, str],
    sprite_id: int,
    animation_group: int,
    existing_sprites: dict[int, dict[str, Any]],
) -> Image.Image:
    image_path = IMAGE_ROOT / row["file"]
    if image_path.is_file() and not row["file"].startswith("sprite_library/"):
        with Image.open(image_path) as opened:
            image = opened.convert("RGBA")
        if image.size == (int(row["width_px"]), int(row["height_px"])):
            return image

    sprite = existing_sprites.get(sprite_id)
    group = next(
        (
            entry
            for entry in sprite.get("groups", [])
            if int(entry["animationGroup"]) == animation_group
        ),
        None,
    ) if sprite else None
    if not group:
        raise FileNotFoundError(
            f"Missing source frame {image_path} and no existing library group to recover it"
        )
    output_path = SPRITE_LIBRARY_ROOT / group["file"]
    if not output_path.is_file():
        raw_path = ROOT / "raw_resources" / "sprites" / f"resource_{sprite_id:03d}.bin"
        if not raw_path.is_file():
            raise FileNotFoundError(
                f"Missing existing sprite library image {output_path} and raw sprite {raw_path}"
            )
        if sprite_id not in RAW_SPRITE_CACHE:
            RAW_SPRITE_CACHE[sprite_id] = parse_ao_sprite(
                raw_path.read_bytes(), sprite_id
            )
        try:
            image, origin_x, origin_y = render_ao_frame_with_anchor(
                RAW_SPRITE_CACHE[sprite_id],
                animation_group,
                int(row["frame_index"]),
                {},
            )
        except ValueError as error:
            if "rendered no visible pixels" not in str(error):
                raise
            return Image.new(
                "RGBA", (int(row["width_px"]), int(row["height_px"])), (0, 0, 0, 0)
            )
        if not row["origin_x_px"]:
            row["origin_x_px"] = str(origin_x)
        if not row["origin_y_px"]:
            row["origin_y_px"] = str(origin_y)
        indexed_origin_x = int(row["origin_x_px"])
        indexed_origin_y = int(row["origin_y_px"])
        expected_size = (int(row["width_px"]), int(row["height_px"]))
        offset_x = origin_x - indexed_origin_x
        offset_y = origin_y - indexed_origin_y
        if (
            offset_x < 0
            or offset_y < 0
            or offset_x + image.width > expected_size[0]
            or offset_y + image.height > expected_size[1]
        ):
            raise ValueError(
                f"Raw sprite {sprite_id} animation {animation_group} frame "
                f"{row['frame_index']} does not fit indexed frame bounds "
                f"{expected_size} at offset {(offset_x, offset_y)}"
            )
        reconstructed = Image.new("RGBA", expected_size, (0, 0, 0, 0))
        reconstructed.alpha_composite(image, (offset_x, offset_y))
        return reconstructed
    with Image.open(output_path) as opened:
        output_image = opened.convert("RGBA")

    frame_index = int(row["frame_index"])
    frame = next(
        (
            entry
            for entry in group["frames"]
            if int(entry["sourceFrameIndex"]) == frame_index
        ),
        None,
    )
    if frame is None:
        raise ValueError(
            f"Existing sprite {sprite_id} group {animation_group} "
            f"has no frame {frame_index}"
        )
    if group["outputKind"] == "image":
        return output_image

    sheet_frame = int(frame["sheetFrame"])
    cell_x = (sheet_frame % int(group["columns"])) * int(group["frameWidth"])
    cell_y = (sheet_frame // int(group["columns"])) * int(group["frameHeight"])
    crop_x = cell_x + int(group["anchorX"]) + int(frame["drawOffsetX"])
    crop_y = cell_y + int(group["anchorY"]) + int(frame["drawOffsetY"])
    crop_box = (
        crop_x,
        crop_y,
        crop_x + int(frame["width"]),
        crop_y + int(frame["height"]),
    )
    return output_image.crop(crop_box)


def write_sprite_library() -> dict[str, Any]:
    catalog_rows = read_csv(IMAGE_ROOT / "asset_catalog.csv")
    asset_rows = read_csv(IMAGE_ROOT / "asset_file_index.csv")
    catalog_by_id = {int(row["sprite_id"]): row for row in catalog_rows}
    frames_by_group: dict[tuple[int, int], list[dict[str, str]]] = defaultdict(list)
    components_by_sprite: dict[int, list[dict[str, str]]] = defaultdict(list)
    for row in asset_rows:
        if row["file_kind"] == "animation_frame":
            frames_by_group[
                (int(row["sprite_id"]), int(row["animation_group"]))
            ].append(row)
        elif row["file_kind"] == "sprite_component":
            components_by_sprite[int(row["sprite_id"])].append(row)

    for frames in frames_by_group.values():
        frames.sort(key=lambda row: int(row["frame_index"]))

    existing_sprites = existing_library_sprites()
    map_object_ids = {
        int(row["sprite_resource_id"])
        for row in read_csv(IMAGE_ROOT / "map_objects.csv")
        if row["sprite_resource_id"]
    }
    SPRITE_LIBRARY_ROOT.mkdir(parents=True, exist_ok=True)
    resources: list[dict[str, Any]] = []
    for sprite_id, catalog in sorted(catalog_by_id.items()):
        if catalog["category"] == "map_tiles" or sprite_id in DUPLICATE_SPRITE_IDS:
            continue
        is_map_sprite = sprite_id in map_object_ids
        resource_frames = {
            group: frames
            for (group_sprite, group), frames in frames_by_group.items()
            if group_sprite == sprite_id
        }
        groups: list[dict[str, Any]] = []
        has_dynamic = any(len(frames) > 1 for frames in resource_frames.values())
        has_static = any(len(frames) == 1 for frames in resource_frames.values()) or bool(
            components_by_sprite.get(sprite_id)
        )

        for animation_group, frame_rows in sorted(resource_frames.items()):
            indexes = [int(row["frame_index"]) for row in frame_rows]
            if indexes != list(range(len(frame_rows))):
                raise ValueError(
                    f"sprite {sprite_id} animation {animation_group}: "
                    f"non-contiguous frame indices {indexes}"
                )

            images: list[Image.Image] = []
            frame_metadata: list[dict[str, Any]] = []
            for row in frame_rows:
                image = load_source_frame(
                    row, sprite_id, animation_group, existing_sprites
                )
                if image.size != (int(row["width_px"]), int(row["height_px"])):
                    raise ValueError(
                        f"Frame dimensions disagree with asset index: sprite "
                        f"{sprite_id} group {animation_group} frame {row['frame_index']}"
                    )
                offset_x = int(row["origin_x_px"]) if row["origin_x_px"] else 0
                offset_y = int(row["origin_y_px"]) if row["origin_y_px"] else 0
                images.append(image)
                metadata = {
                    "sourceFrameIndex": int(row["frame_index"]),
                    "durationMs": int(row["delay_ms"] or 50),
                    "durationTicks": int(row["delay_ticks"] or 1),
                    "drawOffsetX": offset_x,
                    "drawOffsetY": offset_y,
                    "offsetKnown": (
                        row["origin_x_px"] != "" and row["origin_y_px"] != ""
                    ),
                    "width": image.width,
                    "height": image.height,
                }
                frame_metadata.append(metadata)

            role_details = role_animation_details(sprite_id, animation_group)
            group_label = (
                f"{role_details['action']}-{role_details['actionNumber']:02d}"
                if role_details
                else group_name(sprite_id, animation_group, len(frame_rows))
            )
            category = (
                "map_objects"
                if is_map_sprite
                else manual_sprite_category(sprite_id, animation_group)
            )
            asset_category = asset_folder(
                sprite_id,
                manual_sprite_category(sprite_id, animation_group),
                is_map_sprite,
            )
            display_name = (
                existing_sprites[sprite_id].get("name", f"地图对象 sprite {sprite_id:03d}")
                if is_map_sprite and sprite_id in existing_sprites
                else manual_sprite_name(sprite_id, animation_group, len(resource_frames))
            )
            is_dynamic = len(frame_rows) > 1
            group: dict[str, Any] = {
                "animationGroup": animation_group,
                "name": group_label,
                "displayName": display_name,
                "category": category,
                "categoryLabel": category,
                "motion": "dynamic" if is_dynamic else "static",
                "frameCount": len(frame_rows),
                "durationMs": sum(frame["durationMs"] for frame in frame_metadata),
                "frames": frame_metadata,
            }
            if role_details:
                group.update(role_details)
            relative_dir = (
                Path(asset_category) / "空手"
                if is_role_unarmed_group(sprite_id, animation_group)
                else Path(asset_category) / ("dynamic" if is_dynamic else "static")
            )
            if category == "怪物" and is_dynamic:
                relative_dir /= str(len(resource_frames))
            file_name = (
                f"sprite-{sprite_id:03d}-{group_label}.png"
                if is_map_sprite
                else group_filename(sprite_id, animation_group, len(resource_frames))
            )

            if is_dynamic:
                min_x = min(0, *(frame["drawOffsetX"] for frame in frame_metadata))
                min_y = min(0, *(frame["drawOffsetY"] for frame in frame_metadata))
                max_x = max(
                    0,
                    *(
                        frame["drawOffsetX"] + frame["width"]
                        for frame in frame_metadata
                    ),
                )
                max_y = max(
                    0,
                    *(
                        frame["drawOffsetY"] + frame["height"]
                        for frame in frame_metadata
                    ),
                )
                cell_width = max_x - min_x
                cell_height = max_y - min_y
                if cell_width <= 0 or cell_height <= 0:
                    raise ValueError(
                        f"sprite {sprite_id} animation {animation_group} has invalid frame bounds"
                    )
                max_columns = MAX_SPRITESHEET_SIDE // cell_width
                max_rows = MAX_SPRITESHEET_SIDE // cell_height
                if max_columns < 1 or max_rows < 1:
                    raise ValueError(
                        f"sprite {sprite_id} animation {animation_group} exceeds "
                        f"the {MAX_SPRITESHEET_SIDE}px spritesheet limit"
                    )
                columns = min(
                    len(images),
                    max_columns,
                    max(
                        1,
                        math.ceil(
                            math.sqrt(len(images) * cell_height / cell_width)
                        ),
                    ),
                )
                rows = math.ceil(len(images) / columns)
                if rows > max_rows:
                    columns = min(max_columns, math.ceil(len(images) / max_rows))
                    rows = math.ceil(len(images) / columns)
                if rows > max_rows:
                    raise ValueError(
                        f"sprite {sprite_id} animation {animation_group} cannot fit "
                        "within the configured spritesheet dimensions"
                    )
                sheet = Image.new(
                    "RGBA",
                    (columns * cell_width, rows * cell_height),
                    (0, 0, 0, 0),
                )
                anchor_x, anchor_y = -min_x, -min_y
                for index, (image, metadata) in enumerate(
                    zip(images, frame_metadata, strict=True)
                ):
                    cell_x = (index % columns) * cell_width
                    cell_y = (index // columns) * cell_height
                    draw_x = cell_x + metadata["drawOffsetX"] - min_x
                    draw_y = cell_y + metadata["drawOffsetY"] - min_y
                    sheet.alpha_composite(image, (draw_x, draw_y))
                    metadata["sheetFrame"] = index
                output_path = SPRITE_LIBRARY_ROOT / relative_dir / file_name
                output_path.parent.mkdir(parents=True, exist_ok=True)
                sheet.save(output_path, format="PNG", optimize=True)
                group.update(
                    {
                        "outputKind": "spritesheet",
                        "file": output_path.relative_to(SPRITE_LIBRARY_ROOT).as_posix(),
                        "frameWidth": cell_width,
                        "frameHeight": cell_height,
                        "columns": columns,
                        "rows": rows,
                        "originX": anchor_x / cell_width,
                        "originY": anchor_y / cell_height,
                        "anchorX": anchor_x,
                        "anchorY": anchor_y,
                    }
                )
            else:
                metadata = frame_metadata[0]
                output_path = SPRITE_LIBRARY_ROOT / relative_dir / file_name
                output_path.parent.mkdir(parents=True, exist_ok=True)
                images[0].save(output_path, format="PNG", optimize=True)
                group.update(
                    {
                        "outputKind": "image",
                        "file": output_path.relative_to(SPRITE_LIBRARY_ROOT).as_posix(),
                        "frameWidth": images[0].width,
                        "frameHeight": images[0].height,
                        "originX": (
                            -metadata["drawOffsetX"] / images[0].width
                            if metadata["offsetKnown"]
                            else 0.5
                        ),
                        "originY": (
                            -metadata["drawOffsetY"] / images[0].height
                            if metadata["offsetKnown"]
                            else 0.5
                        ),
                        "anchorX": (
                            -metadata["drawOffsetX"]
                            if metadata["offsetKnown"]
                            else images[0].width / 2
                        ),
                        "anchorY": (
                            -metadata["drawOffsetY"]
                            if metadata["offsetKnown"]
                            else images[0].height / 2
                        ),
                    }
                )
                metadata["file"] = group["file"]
                metadata["sheetFrame"] = 0
            groups.append(group)

        components = []
        for row in sorted(
            components_by_sprite.get(sprite_id, []),
            key=lambda item: int(item["component_id"]),
        ):
            source_category = manual_sprite_category(sprite_id)
            category = "map_objects" if is_map_sprite else source_category
            asset_category = asset_folder(
                sprite_id, source_category, is_map_sprite
            )
            source = IMAGE_ROOT / row["file"]
            if not source.is_file():
                previous = existing_sprites.get(sprite_id, {})
                old_component = next(
                    (
                        item
                        for item in previous.get("components", [])
                        if int(item["componentId"]) == int(row["component_id"])
                    ),
                    None,
                )
                if old_component:
                    source = SPRITE_LIBRARY_ROOT / old_component["file"]
            if not source.is_file():
                raise FileNotFoundError(f"Missing sprite component PNG: {source}")
            component_name = (
                f"sprite-{sprite_id:03d}-component-{int(row['component_id']):04d}.png"
                if is_map_sprite
                else component_filename(sprite_id, int(row["component_id"]))
            )
            destination = SPRITE_LIBRARY_ROOT / asset_category / "static" / component_name
            destination.parent.mkdir(parents=True, exist_ok=True)
            with Image.open(source) as opened:
                opened.convert("RGBA").save(destination, format="PNG", optimize=True)
            components.append(
                {
                    "componentId": int(row["component_id"]),
                    "file": destination.relative_to(SPRITE_LIBRARY_ROOT).as_posix(),
                    "width": int(row["width_px"]),
                    "height": int(row["height_px"]),
                    "motion": "static",
                }
            )

        if has_dynamic and has_static:
            motion_classification = "mixed"
        elif has_dynamic:
            motion_classification = "dynamic"
        elif has_static or components:
            motion_classification = "static"
        else:
            motion_classification = "unresolved"
        resource_categories = {group["category"] for group in groups}
        if components or not resource_categories:
            resource_categories.add(
                "map_objects" if is_map_sprite else manual_sprite_category(sprite_id)
            )
        resource_category = (
            next(iter(resource_categories))
            if len(resource_categories) == 1
            else "多分类"
        )
        resource_name = (
            existing_sprites[sprite_id].get("name", f"地图对象 sprite {sprite_id:03d}")
            if is_map_sprite and sprite_id in existing_sprites
            else manual_sprite_name(sprite_id)
        )
        resources.append(
            {
                "spriteId": sprite_id,
                "name": resource_name,
                "sourceCategory": catalog["category"],
                "category": resource_category,
                "categoryLabel": (
                    "地图对象" if resource_category == "map_objects" else resource_category
                ),
                "manualName": resource_name,
                "manualCategory": resource_category,
                "classificationConfidence": catalog["classification_confidence"],
                "classificationEvidence": catalog["classification_evidence"],
                "semanticStatus": (
                    "map-use-confirmed-role-unconfirmed"
                    if catalog["category"] == "map_objects"
                    else "candidate"
                    if catalog["classification_confidence"] == "visual-candidate"
                    else catalog["classification_confidence"]
                ),
                "renderStatus": catalog["render_status"],
                "motionClassification": motion_classification,
                "groups": groups,
                "components": components,
            }
        )

    manifest = {
        "format": "htol-readable-sprite-library",
        "version": 1,
        "sourceTickMs": 50,
        "frameTimingNote": "durationMs is the source frame duration; drawOffsetX/Y locate the cropped frame relative to its source draw anchor.",
        "classificationNote": "Visual categories remain candidates unless supported by source references; map-object semantics (including NPC identity) are not inferred from appearance alone.",
        "sprites": resources,
    }
    write_json(SPRITE_LIBRARY_ROOT / "sprite_library.json", manifest)
    groups_by_id = {
        (int(sprite["spriteId"]), int(group["animationGroup"])): group["file"]
        for sprite in resources
        for group in sprite["groups"]
    }
    components_by_id = {
        (int(sprite["spriteId"]), int(component["componentId"])): component["file"]
        for sprite in resources
        for component in sprite["components"]
    }
    for row in asset_rows:
        if not row["sprite_id"]:
            continue
        sprite_id = int(row["sprite_id"])
        if row["file_kind"] == "animation_frame":
            group_file = groups_by_id.get(
                (sprite_id, int(row["animation_group"]))
            )
            if group_file:
                row["file"] = group_file
        elif row["file_kind"] == "sprite_component":
            component_file = components_by_id.get(
                (sprite_id, int(row["component_id"]))
            )
            if component_file:
                row["file"] = component_file
    write_csv(
        IMAGE_ROOT / "asset_file_index.csv",
        list(asset_rows[0]),
        asset_rows,
    )
    return manifest


def map_tile_crop(image: Image.Image) -> Image.Image:
    image = image.convert("RGBA")
    if image.size == (88, 88):
        bounds = image.getchannel("A").getbbox()
        if bounds is None:
            return Image.new("RGBA", (MAP_TILE_SIZE, MAP_TILE_SIZE))
        if bounds != (MAP_TILE_OFFSET, MAP_TILE_OFFSET, 44, 44):
            raise ValueError(f"Unexpected source map tile bounds: {bounds}")
        return image.crop(bounds)
    if image.size == (MAP_TILE_SIZE, MAP_TILE_SIZE):
        return image
    raise ValueError(f"Unexpected source map tile image dimensions: {image.size}")


def load_map_tile_sprite(sprite_id: int, palette_index: int = 0):
    source = ROOT / "raw_resources" / "sprites" / f"resource_{sprite_id:03d}.bin"
    if not source.is_file():
        raise FileNotFoundError(f"Missing raw map tile sprite resource: {source}")
    return parse_ao_sprite(source.read_bytes(), sprite_id, palette_index)


def map_tile_transform(row: dict[str, str]) -> int:
    value = row.get("tile_transform_2bit") or row.get("palette_id_2bit")
    transform = int(value or 0)
    if not 0 <= transform <= 3:
        raise ValueError(f"Invalid map tile transform value: {transform}")
    return transform


def write_tiled_tilesets(map_index_rows: list[dict[str, str]]) -> None:
    atlas_ids = sorted(
        {int(row["tile_atlas_sprite_id"]) for row in map_index_rows}
    )
    TILESET_ROOT.mkdir(parents=True, exist_ok=True)
    atlas_index_rows: list[dict[str, Any]] = []
    for sprite_id in atlas_ids:
        first_palette = load_map_tile_sprite(sprite_id)
        tile_count = len(first_palette.component_sizes)
        if not tile_count:
            raise ValueError(f"Map tile sprite {sprite_id} has no components")
        columns = min(16, tile_count)
        row_count = math.ceil(tile_count / columns)

        for stale_path in (
            TILESET_ROOT / f"sprite_{sprite_id:03d}_tiles.png",
            TILESET_ROOT / f"sprite_{sprite_id:03d}.tsj",
            *TILESET_ROOT.glob(f"sprite_{sprite_id:03d}_palette_*_tiles.png"),
            *TILESET_ROOT.glob(f"sprite_{sprite_id:03d}_palette_*.tsj"),
        ):
            if stale_path.exists():
                stale_path.unlink()

        for palette_index in range(first_palette.palette_count):
            sprite = (
                first_palette
                if palette_index == 0
                else load_map_tile_sprite(sprite_id, palette_index)
            )
            sheet = Image.new(
                "RGBA",
                (columns * MAP_TILE_SIZE, row_count * MAP_TILE_SIZE),
                (0, 0, 0, 0),
            )
            for tile_id, bitmap in enumerate(sprite.bitmaps):
                if bitmap is None:
                    continue
                tile = map_tile_crop(bitmap)
                sheet.alpha_composite(
                    tile,
                    (
                        (tile_id % columns) * MAP_TILE_SIZE,
                        (tile_id // columns) * MAP_TILE_SIZE,
                    ),
                )

            stem = f"sprite_{sprite_id:03d}_palette_{palette_index}"
            image_name = f"{stem}_tiles.png"
            image_path = TILESET_ROOT / image_name
            sheet.save(image_path, format="PNG", optimize=True)
            write_json(
                TILESET_ROOT / f"{stem}.tsj",
                {
                    "columns": columns,
                    "image": image_name,
                    "imageheight": row_count * MAP_TILE_SIZE,
                    "imagewidth": columns * MAP_TILE_SIZE,
                    "margin": 0,
                    "name": f"{stem}_tiles",
                    "spacing": 0,
                    "tilecount": tile_count,
                    "tiledversion": "1.10.2",
                    "tileheight": MAP_TILE_SIZE,
                    "tileoffset": {"x": 0, "y": 0},
                    "tilewidth": MAP_TILE_SIZE,
                    "type": "tileset",
                    "version": "1.10",
                    "properties": [
                        {"name": "spriteId", "type": "int", "value": sprite_id},
                        {
                            "name": "paletteIndex",
                            "type": "int",
                            "value": palette_index,
                        },
                    ],
                },
            )
            atlas_index_rows.append(
                {
                    "file_kind": "map_tile_atlas",
                    "sprite_id": sprite_id,
                    "category": "map_tiles",
                    "animation_group": "",
                    "frame_index": "",
                    "component_id": "",
                    "delay_ticks": "",
                    "delay_ms": "",
                    "origin_x_px": "",
                    "origin_y_px": "",
                    "bound_min_x_px": "",
                    "bound_min_y_px": "",
                    "bound_max_x_px": "",
                    "bound_max_y_px": "",
                    "width_px": sheet.width,
                    "height_px": sheet.height,
                    "file": image_path.relative_to(IMAGE_ROOT).as_posix(),
                    "source_file": (
                        f"raw_resources/sprites/resource_{sprite_id:03d}.bin"
                    ),
                }
            )

    update_tile_asset_indexes(atlas_ids, atlas_index_rows)


def update_tile_asset_indexes(
    sprite_ids: list[int], atlas_rows: list[dict[str, Any]]
) -> None:
    index_path = IMAGE_ROOT / "asset_file_index.csv"
    rows = read_csv(index_path)
    atlas_ids = set(sprite_ids)
    rows = [
        row
        for row in rows
        if not (
            int(row["sprite_id"] or -1) in atlas_ids
            and (
                row["file_kind"] == "map_tile_atlas"
                or row["file_kind"] == "component_overview"
                or (
                    row["file_kind"] == "sprite_component"
                    and row["category"] == "map_tiles"
                )
            )
        )
    ]
    rows.extend(atlas_rows)
    write_csv(index_path, list(rows[0].keys()) if rows else list(atlas_rows[0]), rows)

    atlas_counts: dict[int, int] = defaultdict(int)
    for row in atlas_rows:
        atlas_counts[int(row["sprite_id"])] += 1
    catalog_path = IMAGE_ROOT / "asset_catalog.csv"
    catalog_rows = read_csv(catalog_path)
    for row in catalog_rows:
        sprite_id = int(row["sprite_id"])
        if sprite_id in atlas_ids:
            row["components"] = "0"
            row["flat_output_files"] = str(atlas_counts[sprite_id])
            row["render_status"] = "atlas-only"
    if catalog_rows:
        write_csv(catalog_path, list(catalog_rows[0].keys()), catalog_rows)


def tiled_properties(metadata: dict[str, Any]) -> list[dict[str, Any]]:
    return [
        {"name": key, "type": "string", "value": str(value)}
        for key, value in metadata.items()
    ]


def write_tiled_maps(
    map_index_rows: list[dict[str, str]],
    tile_rows: list[dict[str, str]],
    object_rows: list[dict[str, str]],
) -> None:
    tiles_by_map: dict[int, list[dict[str, str]]] = defaultdict(list)
    objects_by_map: dict[int, list[dict[str, str]]] = defaultdict(list)
    for row in tile_rows:
        tiles_by_map[int(row["map_resource_id"])].append(row)
    for row in object_rows:
        objects_by_map[int(row["map_resource_id"])].append(row)

    for map_row in map_index_rows:
        map_id = int(map_row["map_resource_id"])
        width = int(map_row["width_cells"])
        height = int(map_row["height_cells"])
        sprite_id = int(map_row["tile_atlas_sprite_id"])
        cells = sorted(
            tiles_by_map[map_id],
            key=lambda row: (int(row["cell_y"]), int(row["cell_x"])),
        )
        if len(cells) != width * height:
            raise ValueError(
                f"Map {map_id}: expected {width * height} tile records, found {len(cells)}"
            )
        gids = []
        transforms = []
        attributes = []
        for expected_index, row in enumerate(cells):
            x, y = int(row["cell_x"]), int(row["cell_y"])
            if (x, y) != (expected_index % width, expected_index // width):
                raise ValueError(f"Map {map_id}: tile coordinates are not row-major")
            component = row["tile_component"]
            transform = map_tile_transform(row)
            gid = 0 if not component else int(component) + 1
            if gid:
                if transform & 1:
                    gid |= TILED_FLIP_H
                if transform & 2:
                    gid |= TILED_FLIP_V
            gids.append(gid)
            transforms.append(transform)
            attributes.append(int(row["attribute_byte"]))
        packed_transforms = bytearray(math.ceil(len(transforms) / 4))
        for index, transform in enumerate(transforms):
            packed_transforms[index // 4] |= transform << (6 - (index % 4) * 2)

        tile_layer = {
            "data": gids,
            "height": height,
            "id": 1,
            "name": "ground",
            "opacity": 1,
            "type": "tilelayer",
            "visible": True,
            "width": width,
            "x": 0,
            "y": 0,
        }
        map_objects = []
        for object_index, row in enumerate(objects_by_map[map_id], start=1):
            raw_fields = json.loads(row["raw_fields_json"])
            source_x = int(row["source_x_units"])
            source_y = int(row["source_y_units"])
            draw_x = int(row["draw_anchor_x_units"])
            draw_y = int(row["draw_anchor_y_units"])
            sprite_resource_id = row["sprite_resource_id"]
            animation = int(row["animation_group"])
            properties: dict[str, Any] = {
                "objectIndex": int(row["object_index"]),
                "entityKind": "unclassified_map_sprite",
                "classificationStatus": "NPC/object semantics not confirmed",
                "rawSpriteId": int(row["raw_sprite_id"]),
                "spriteResourceId": sprite_resource_id,
                "animationGroup": animation,
                "sourceXUnits": source_x,
                "sourceYUnits": source_y,
                "renderXpx": draw_x,
                "renderYpx": draw_y,
                "field5": row["field_5"],
                "field6": row["field_6"],
                "field7": row["field_7"],
                "field8": row["field_8"],
                "rawFieldsJson": row["raw_fields_json"],
            }
            map_objects.append(
                {
                    "height": 0,
                    "id": object_index,
                    "name": f"sprite_{sprite_resource_id}_object_{object_index:04d}",
                    "point": True,
                    "properties": tiled_properties(properties),
                    "rotation": 0,
                    "type": "source_sprite_object",
                    "visible": True,
                    "width": 0,
                    "x": draw_x,
                    "y": draw_y,
                }
            )
        object_layer = {
            "draworder": "topdown",
            "id": 2,
            "name": "source_objects",
            "objects": map_objects,
            "opacity": 1,
            "type": "objectgroup",
            "visible": True,
            "x": 0,
            "y": 0,
        }
        metadata = {
            "sourceJarArchive": map_row["jar_archive"],
            "sourceRawRecordsPattern": map_row["raw_records_pattern"],
            "sourceTileSetRawId": map_row["tile_set_raw_id"],
            "sourceTileAtlasSpriteId": sprite_id,
            "tilePaletteIndex": 0,
            "sourceLogicalCellUnits": map_row["logical_cell_units"],
            "tileTransform2BitBase64": base64.b64encode(packed_transforms).decode("ascii"),
            "tileTransformSemantics": "bit 0 horizontal flip; bit 1 vertical flip (bm.i -> ao.b -> Graphics.drawRegion)",
            "attributeBytesBase64": base64.b64encode(bytes(attributes)).decode("ascii"),
            "attributeSemantics": "unknown; preserved from source",
            "coordinateOrder": "row-major; x increases right and y increases down (confirmed in bm.java)",
            "entityCoordinates": "source actor coordinates are converted by h constructor and rendered at draw_anchor_x/y from map_objects.csv; values are already render pixels",
        }
        tiled = {
            "compressionlevel": -1,
            "height": height,
            "infinite": False,
            "layers": [tile_layer, object_layer],
            "nextlayerid": 3,
            "nextobjectid": len(map_objects) + 1,
            "orientation": "orthogonal",
            "renderorder": "right-down",
            "tiledversion": "1.10.2",
            "tileheight": MAP_TILE_SIZE,
            "tilesets": [
                {
                    "firstgid": 1,
                    "source": f"../tilesets/sprite_{sprite_id:03d}_palette_0.tsj",
                }
            ],
            "tilewidth": MAP_TILE_SIZE,
            "type": "map",
            "version": "1.10",
            "width": width,
            "properties": tiled_properties(metadata),
        }
        write_json(MAP_ROOT / f"map_{map_id:03d}.tmj", tiled)


def write_map_previews(
    map_index_rows: list[dict[str, str]],
    tile_rows: list[dict[str, str]],
    object_rows: list[dict[str, str]],
) -> tuple[int, int]:
    tiles_by_map: dict[int, list[dict[str, str]]] = defaultdict(list)
    objects_by_map: dict[int, list[dict[str, str]]] = defaultdict(list)
    for row in tile_rows:
        tiles_by_map[int(row["map_resource_id"])].append(row)
    for row in object_rows:
        objects_by_map[int(row["map_resource_id"])].append(row)

    indexed_frames: dict[tuple[int, int], list[dict[str, str]]] = defaultdict(list)
    for row in read_csv(IMAGE_ROOT / "asset_file_index.csv"):
        if row["file_kind"] == "animation_frame":
            indexed_frames[(int(row["sprite_id"]), int(row["animation_group"]))].append(row)
    for frames in indexed_frames.values():
        frames.sort(key=lambda row: int(row["frame_index"]))

    sprite_library = json.loads(
        (SPRITE_LIBRARY_ROOT / "sprite_library.json").read_text(encoding="utf-8")
    )
    groups_by_key = {
        (int(sprite["spriteId"]), int(group["animationGroup"])): group
        for sprite in sprite_library.get("sprites", [])
        for group in sprite.get("groups", [])
    }
    entity_rows: list[dict[str, Any]] = []
    preview_index_rows: list[dict[str, Any]] = []
    tile_atlas_cache: dict[tuple[int, int], tuple[Image.Image, int, int]] = {}
    palette_counts: dict[int, int] = {}
    frame_images: dict[str, Image.Image] = {}
    preview_frame_cache: dict[
        tuple[int, int], tuple[Image.Image, int, int, str]
    ] = {}

    def load_preview_frame(
        sprite_id: int, animation_group: int
    ) -> tuple[Image.Image, int, int, str]:
        cache_key = (sprite_id, animation_group)
        if cache_key in preview_frame_cache:
            return preview_frame_cache[cache_key]
        group = groups_by_key.get(cache_key)
        if group is None:
            raise ValueError(
                f"Map object sprite {sprite_id} is missing animation group "
                f"{animation_group} from sprite_library.json"
            )
        frame_path = SPRITE_LIBRARY_ROOT / group["file"]
        if not frame_path.is_file():
            raise FileNotFoundError(f"Missing map object sprite image: {frame_path}")
        if group["file"] not in frame_images:
            with Image.open(frame_path) as opened:
                frame_images[group["file"]] = opened.convert("RGBA")
        atlas = frame_images[group["file"]]

        def crop_frame(frame: dict[str, Any]) -> Image.Image:
            if group["outputKind"] == "image":
                return atlas.copy()
            sheet_frame = int(frame["sheetFrame"])
            cell_x = (sheet_frame % int(group["columns"])) * int(group["frameWidth"])
            cell_y = (sheet_frame // int(group["columns"])) * int(group["frameHeight"])
            left = cell_x + int(group["anchorX"]) + int(frame["drawOffsetX"])
            top = cell_y + int(group["anchorY"]) + int(frame["drawOffsetY"])
            return atlas.crop(
                (
                    left,
                    top,
                    left + int(frame["width"]),
                    top + int(frame["height"]),
                )
            )

        frames = sorted(group["frames"], key=lambda item: int(item["sourceFrameIndex"]))
        selected = frames[0]
        image = crop_frame(selected)
        for candidate in frames:
            candidate_image = crop_frame(candidate)
            if (
                candidate.get("offsetKnown")
                and candidate_image.getchannel("A").getbbox() is not None
            ):
                selected = candidate
                image = candidate_image
                break
        origin_x = int(selected["drawOffsetX"]) if selected.get("offsetKnown") else 0
        origin_y = int(selected["drawOffsetY"]) if selected.get("offsetKnown") else 0
        result = image, origin_x, origin_y, group["file"]
        preview_frame_cache[cache_key] = result
        return result

    PREVIEW_ROOT.mkdir(parents=True, exist_ok=True)
    for stale_path in PREVIEW_ROOT.rglob("map_*_palette_*_preview.png"):
        stale_path.unlink()
    existing_catalog_path = ROOT.parent / "world_map_design" / "map_catalog.json"
    map_categories: dict[int, str] = {}
    if existing_catalog_path.is_file():
        existing_catalog = json.loads(existing_catalog_path.read_text(encoding="utf-8"))
        map_categories = {
            int(entry["mapId"]): entry.get("sourceCategory", "_未分类根目录")
            for entry in existing_catalog.get("maps", [])
        }

    for map_row in map_index_rows:
        map_id = int(map_row["map_resource_id"])
        width = int(map_row["width_cells"])
        height = int(map_row["height_cells"])
        sprite_id = int(map_row["tile_atlas_sprite_id"])
        cells = sorted(
            tiles_by_map[map_id],
            key=lambda row: (int(row["cell_y"]), int(row["cell_x"])),
        )
        if len(cells) != width * height:
            raise ValueError(
                f"Map {map_id}: expected {width * height} tile records, found {len(cells)}"
            )
        palette_counts.setdefault(
            sprite_id, load_map_tile_sprite(sprite_id).palette_count
        )
        map_entities = sorted(
            objects_by_map[map_id],
            key=lambda row: (
                int(row["draw_anchor_y_units"]),
                int(row["object_index"]),
            ),
        )

        for palette_index in range(palette_counts[sprite_id]):
            canvas = Image.new(
                "RGBA",
                (width * MAP_TILE_SIZE, height * MAP_TILE_SIZE),
                (0, 0, 0, 0),
            )
            atlas_key = (sprite_id, palette_index)
            if atlas_key not in tile_atlas_cache:
                tileset_path = (
                    TILESET_ROOT
                    / f"sprite_{sprite_id:03d}_palette_{palette_index}.tsj"
                )
                if not tileset_path.is_file():
                    raise FileNotFoundError(
                        f"Map {map_id}: missing palette {palette_index} tileset "
                        f"{tileset_path}"
                    )
                tileset = json.loads(tileset_path.read_text(encoding="utf-8"))
                atlas_path = TILESET_ROOT / tileset["image"]
                if not atlas_path.is_file():
                    raise FileNotFoundError(
                        f"Map {map_id}: missing tile atlas {atlas_path}"
                    )
                with Image.open(atlas_path) as opened:
                    tile_atlas_cache[atlas_key] = (
                        opened.convert("RGBA"),
                        int(tileset["columns"]),
                        int(tileset["tilecount"]),
                    )

            atlas, columns, tile_count = tile_atlas_cache[atlas_key]
            for expected_index, row in enumerate(cells):
                x = int(row["cell_x"])
                y = int(row["cell_y"])
                if (x, y) != (expected_index % width, expected_index // width):
                    raise ValueError(f"Map {map_id}: tile coordinates are not row-major")
                if not row["tile_component"]:
                    continue
                component_id = int(row["tile_component"])
                if not 0 <= component_id < tile_count:
                    raise ValueError(
                        f"Map {map_id}: component {component_id} is outside sprite "
                        f"{sprite_id} tile atlas ({tile_count} tiles)"
                    )
                left = (component_id % columns) * MAP_TILE_SIZE
                top = (component_id // columns) * MAP_TILE_SIZE
                tile = atlas.crop(
                    (left, top, left + MAP_TILE_SIZE, top + MAP_TILE_SIZE)
                )
                transform = map_tile_transform(row)
                if transform & 1:
                    tile = tile.transpose(Image.Transpose.FLIP_LEFT_RIGHT)
                if transform & 2:
                    tile = tile.transpose(Image.Transpose.FLIP_TOP_BOTTOM)
                canvas.alpha_composite(tile, (x * MAP_TILE_SIZE, y * MAP_TILE_SIZE))

            for row in map_entities:
                sprite_resource_id = int(row["sprite_resource_id"])
                animation = int(row["animation_group"])
                frames = indexed_frames.get((sprite_resource_id, animation), [])
                if not frames:
                    raise ValueError(
                        f"Map {map_id} object {row['object_index']} references missing "
                        f"sprite animation {sprite_resource_id}/{animation}"
                    )
                image, frame_origin_x, frame_origin_y, frame_file = load_preview_frame(
                    sprite_resource_id, animation
                )
                anchor_x = int(row["draw_anchor_x_units"])
                anchor_y = int(row["draw_anchor_y_units"])
                transformed_image, origin_x, origin_y = transform_map_object_frame(
                    image,
                    frame_origin_x,
                    frame_origin_y,
                    int(row["field_5"] or 0),
                )
                canvas.alpha_composite(
                    transformed_image, (anchor_x + origin_x, anchor_y + origin_y)
                )
                if palette_index == 0:
                    entity_rows.append(
                        {
                            "map_resource_id": map_id,
                            "object_index": row["object_index"],
                            "entity_kind": "unclassified_map_sprite",
                            "classification_status": "NPC/object semantics not confirmed",
                            "raw_sprite_id": row["raw_sprite_id"],
                            "sprite_resource_id": sprite_resource_id,
                            "animation_group": animation,
                            "source_x_units": row["source_x_units"],
                            "source_y_units": row["source_y_units"],
                            "render_x_px": anchor_x,
                            "render_y_px": anchor_y,
                            "frame_file": frame_file,
                            "frame_origin_x_px": frame_origin_x,
                            "frame_origin_y_px": frame_origin_y,
                            "field5": row["field_5"],
                            "field6": row["field_6"],
                            "field7": row["field_7"],
                            "field8": row["field_8"],
                            "raw_fields_json": row["raw_fields_json"],
                        }
                    )

            preview_size = (
                max(1, round(canvas.width * 0.25)),
                max(1, round(canvas.height * 0.25)),
            )
            preview = canvas.resize(preview_size, Image.Resampling.LANCZOS)
            category = map_categories.get(map_id, "_未分类根目录")
            preview_directory = (
                PREVIEW_ROOT if category == "_未分类根目录" else PREVIEW_ROOT / category
            )
            preview_directory.mkdir(parents=True, exist_ok=True)
            output_path = preview_directory / (
                f"map_{map_id:03d}_palette_{palette_index}_preview.png"
            )
            preview.save(output_path, format="PNG", optimize=True)
            preview_index_rows.append(
                {
                    "file_kind": "map_palette_preview",
                    "sprite_id": sprite_id,
                    "category": "map",
                    "animation_group": palette_index,
                    "frame_index": map_id,
                    "component_id": "",
                    "delay_ticks": "",
                    "delay_ms": "",
                    "origin_x_px": "",
                    "origin_y_px": "",
                    "bound_min_x_px": "",
                    "bound_min_y_px": "",
                    "bound_max_x_px": "",
                    "bound_max_y_px": "",
                    "width_px": preview.width,
                    "height_px": preview.height,
                    "file": output_path.relative_to(IMAGE_ROOT).as_posix(),
                    "source_file": (
                        f"raw_resources/maps/map_{map_id:03d}_record_003.bin"
                    ),
                }
            )
        map_row["base_preview"] = ""
        category = map_categories.get(map_id, "_未分类根目录")
        preview_relative_path = (
            Path("map")
            / "preview"
            / (
                Path(category)
                if category != "_未分类根目录"
                else Path()
            )
            / f"map_{map_id:03d}_palette_0_preview.png"
        )
        map_row["full_preview"] = preview_relative_path.as_posix()

    write_csv(
        IMAGE_ROOT / "map_entities.csv",
        [
            "map_resource_id",
            "object_index",
            "entity_kind",
            "classification_status",
            "raw_sprite_id",
            "sprite_resource_id",
            "animation_group",
            "source_x_units",
            "source_y_units",
            "render_x_px",
            "render_y_px",
            "frame_file",
            "frame_origin_x_px",
            "frame_origin_y_px",
            "field5",
            "field6",
            "field7",
            "field8",
            "raw_fields_json",
        ],
        entity_rows,
    )

    write_csv(
        IMAGE_ROOT / "map_index.csv",
        [
            "map_resource_id",
            "jar_archive",
            "width_cells",
            "height_cells",
            "logical_cell_units",
            "render_cell_px",
            "tile_set_raw_id",
            "tile_atlas_sprite_id",
            "tile_record_count",
            "object_record_count",
            "empty_tile_count",
            "attribute_record_bytes",
            "base_preview",
            "full_preview",
            "raw_records_pattern",
            "map_name",
            "map_name_status",
        ],
        map_index_rows,
    )
    write_csv(
        IMAGE_ROOT / "map_preview_index.csv",
        ["map_resource_id", "palette_index", "width_px", "height_px", "file"],
        [
            {
                "map_resource_id": row["frame_index"],
                "palette_index": row["animation_group"],
                "width_px": row["width_px"],
                "height_px": row["height_px"],
                "file": row["file"],
            }
            for row in preview_index_rows
        ],
    )

    asset_index_path = IMAGE_ROOT / "asset_file_index.csv"
    asset_rows = [
        row
        for row in read_csv(asset_index_path)
        if row["file_kind"]
        not in {
            "map_palette_preview",
            "map_full_static_preview",
            "map_base_preview_unpaletted",
        }
    ]
    asset_rows.extend(preview_index_rows)
    write_csv(
        asset_index_path,
        [
            "file_kind",
            "sprite_id",
            "category",
            "animation_group",
            "frame_index",
            "component_id",
            "delay_ticks",
            "delay_ms",
            "origin_x_px",
            "origin_y_px",
            "bound_min_x_px",
            "bound_min_y_px",
            "bound_max_x_px",
            "bound_max_y_px",
            "width_px",
            "height_px",
            "file",
            "source_file",
        ],
        asset_rows,
    )
    return len(preview_index_rows), len(entity_rows)


def clean_gif_references() -> None:
    file_index = IMAGE_ROOT / "asset_file_index.csv"
    library = existing_library_sprites()
    map_object_ids = {
        int(row["sprite_resource_id"])
        for row in read_csv(IMAGE_ROOT / "map_objects.csv")
        if row["sprite_resource_id"]
    }
    tile_sprite_ids = {
        int(row["tile_atlas_sprite_id"])
        for row in read_csv(IMAGE_ROOT / "map_index.csv")
        if row["tile_atlas_sprite_id"]
    }
    map_object_ids.update(tile_sprite_ids)
    rows = [
        row
        for row in read_csv(file_index)
        if row["file_kind"] != "animation_gif" and not row["file"].lower().endswith(".gif")
        and not (
            row["sprite_id"]
            and int(row["sprite_id"]) in DUPLICATE_SPRITE_IDS
        )
        and not (
            row["file_kind"] in {"animation_overview", "component_overview"}
            and row["sprite_id"]
            and int(row["sprite_id"]) not in map_object_ids
        )
    ]
    for row in rows:
        if row["file_kind"] == "animation_frame" and row["sprite_id"]:
            row["source_file"] = (
                f"raw_resources/sprites/resource_{int(row['sprite_id']):03d}.bin"
            )
        elif row["file_kind"] == "standalone_image":
            if not row["file"].startswith("other/"):
                row["file"] = (Path("other") / row["file"]).as_posix()
            row["source_file"] = row["file"]
        if not row["sprite_id"]:
            continue
        sprite_id = int(row["sprite_id"])
        if sprite_id in tile_sprite_ids:
            continue
        sprite = library.get(sprite_id)
        if not sprite:
            continue
        if row["file_kind"] == "sprite_component":
            component = next(
                (
                    item
                    for item in sprite["components"]
                    if int(item["componentId"]) == int(row["component_id"])
                ),
                None,
            )
            if component:
                row["file"] = component["file"]
                row["width_px"], row["height_px"] = (
                    str(component["width"]),
                    str(component["height"]),
                )
        elif row["file_kind"] == "animation_frame":
            group = next(
                (
                    item
                    for item in sprite["groups"]
                    if int(item["animationGroup"]) == int(row["animation_group"])
                ),
                None,
            )
            if group:
                row["file"] = group["file"]
    write_csv(
        file_index,
        [
            "file_kind",
            "sprite_id",
            "category",
            "animation_group",
            "frame_index",
            "component_id",
            "delay_ticks",
            "delay_ms",
            "origin_x_px",
            "origin_y_px",
            "bound_min_x_px",
            "bound_min_y_px",
            "bound_max_x_px",
            "bound_max_y_px",
            "width_px",
            "height_px",
            "file",
            "source_file",
        ],
        rows,
    )
    catalog_path = IMAGE_ROOT / "asset_catalog.csv"
    catalog = read_csv(catalog_path)
    counts: dict[str, int] = defaultdict(int)
    for row in rows:
        if row["sprite_id"]:
            counts[row["sprite_id"]] += 1
    for row in catalog:
        row["flat_output_files"] = counts[row["sprite_id"]]
    write_csv(
        catalog_path,
        [
            "sprite_id",
            "category",
            "category_label",
            "classification_confidence",
            "classification_evidence",
            "payload_bytes",
            "payload_sha256",
            "render_status",
            "animation_groups",
            "animation_frames",
            "components",
            "flat_output_files",
            "source_reference",
        ],
        catalog,
    )


def remove_duplicate_sprite_exports() -> int:
    removed = 0
    manifest = existing_library_sprites()
    referenced_files = {
        group["file"]
        for sprite in manifest.values()
        for group in sprite.get("groups", [])
    }
    referenced_files.update(
        component["file"]
        for sprite in manifest.values()
        for component in sprite.get("components", [])
    )
    for category in (
        "map",
        "unit",
        "monster",
        "player",
        "effect",
        "icon",
        "pet",
        "npc",
        "other",
    ):
        for path in (ROOT / category).rglob("sprite-*.png"):
            if path.relative_to(ROOT).as_posix() not in referenced_files:
                path.unlink()
                removed += 1
    return removed + deduplicate_png_assets()


def deduplicate_png_assets() -> int:
    asset_roots = {
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
    hashes: dict[str, list[Path]] = defaultdict(list)
    for path in ROOT.rglob("*.png"):
        relative = path.relative_to(ROOT)
        if relative.parts[0] in asset_roots:
            with Image.open(path) as opened:
                image = opened.convert("RGBA")
                digest = hashlib.sha256()
                digest.update(image.width.to_bytes(4, "big"))
                digest.update(image.height.to_bytes(4, "big"))
                digest.update(image.tobytes())
            hashes[digest.hexdigest()].append(path)

    def priority(path: Path) -> tuple[int, str]:
        relative = path.relative_to(ROOT)
        if relative.parts[:2] == ("map", "tilesets") and "_palette_" in path.name:
            rank = 0
        elif relative.parts[:2] == ("map", "preview"):
            rank = 1
        elif path.name.startswith("sprite-"):
            rank = 2
        else:
            rank = 3
        return rank, relative.as_posix()

    aliases: dict[str, str] = {}
    for paths in hashes.values():
        if len(paths) < 2:
            continue
        canonical = min(paths, key=priority)
        canonical_path = canonical.relative_to(ROOT).as_posix()
        for duplicate in paths:
            if duplicate != canonical:
                aliases[duplicate.relative_to(ROOT).as_posix()] = canonical_path

    if not aliases:
        return 0

    def rewrite_json(value: Any, metadata_path: Path) -> Any:
        if isinstance(value, str):
            normalized = value.replace("\\", "/")
            if normalized in aliases:
                return aliases[normalized]
            if "/" not in normalized:
                candidate = metadata_path.parent / normalized
                try:
                    relative = candidate.resolve().relative_to(ROOT).as_posix()
                except ValueError:
                    return value
                if relative in aliases:
                    return os.path.relpath(
                        ROOT / aliases[relative], metadata_path.parent
                    ).replace("\\", "/")
            return value
        if isinstance(value, list):
            return [rewrite_json(item, metadata_path) for item in value]
        if isinstance(value, dict):
            return {
                key: rewrite_json(item, metadata_path)
                for key, item in value.items()
            }
        return value

    for metadata_path in ROOT.rglob("*.csv"):
        with metadata_path.open("r", encoding="utf-8-sig", newline="") as stream:
            reader = csv.DictReader(stream)
            rows = list(reader)
            fields = reader.fieldnames or []
        changed = False
        for row in rows:
            for key, value in row.items():
                if value in aliases:
                    row[key] = aliases[value]
                    changed = True
        if changed:
            with metadata_path.open("w", encoding="utf-8-sig", newline="") as stream:
                writer = csv.DictWriter(stream, fieldnames=fields, lineterminator="\n")
                writer.writeheader()
                writer.writerows(rows)

    metadata_paths = [
        path
        for suffix in ("*.json", "*.tsj", "*.tmj")
        for path in ROOT.rglob(suffix)
    ]
    for metadata_path in metadata_paths:
        original = json.loads(metadata_path.read_text(encoding="utf-8"))
        rewritten = rewrite_json(original, metadata_path)
        if rewritten != original:
            write_json(metadata_path, rewritten)

    for relative in aliases:
        (ROOT / relative).unlink()
    return len(aliases)


def main() -> None:
    sprite_library = write_sprite_library()
    sprite_library = export_character_phaser_assets(sprite_library)
    clean_gif_references()
    group_rows = [
        {
            "sprite_id": sprite["spriteId"],
            "animation_group": group["animationGroup"],
            "frame_count": group["frameCount"],
            "source_total_ticks": group["durationMs"] // 50,
            "source_total_ms": group["durationMs"],
            "animation_key": (
                f"sprite_{sprite['spriteId']:03d}_anim_{group['animationGroup']:03d}"
            ),
            "output_kind": group["outputKind"],
            "output_file": group["file"],
        }
        for sprite in sprite_library["sprites"]
        for group in sprite["groups"]
    ]
    map_index = read_csv(IMAGE_ROOT / "map_index.csv")
    map_tiles = read_csv(IMAGE_ROOT / "map_tiles.csv")
    map_objects = read_csv(IMAGE_ROOT / "map_objects.csv")
    write_tiled_tilesets(map_index)
    asset_rows = read_csv(IMAGE_ROOT / "asset_file_index.csv")
    asset_rows = [
        row
        for row in asset_rows
        if row["file_kind"] != "animation_overview"
        and (
            not row["sprite_id"]
            or int(row["sprite_id"]) not in DUPLICATE_SPRITE_IDS
        )
    ]
    for row in asset_rows:
        if row["file_kind"] == "map_tile_atlas":
            palette_match = re.search(r"_palette_(\d+)_tiles\.png$", row["file"])
            if not palette_match:
                raise ValueError(f"Unrecognized map tile atlas path: {row['file']}")
            row["file"] = (
                f"map/tilesets/sprite_{int(row['sprite_id']):03d}_"
                f"palette_{int(palette_match.group(1))}_tiles.png"
            )
    write_csv(
        IMAGE_ROOT / "asset_file_index.csv",
        list(asset_rows[0]),
        asset_rows,
    )
    write_tiled_maps(map_index, map_tiles, map_objects)
    preview_count, entity_count = write_map_previews(map_index, map_tiles, map_objects)

    group_rows.sort(key=lambda row: (row["sprite_id"], row["animation_group"]))
    write_csv(
        IMAGE_ROOT / "animation_groups.csv",
        [
            "sprite_id",
            "animation_group",
            "frame_count",
            "source_total_ticks",
            "source_total_ms",
            "animation_key",
            "output_kind",
            "output_file",
        ],
        group_rows,
    )
    write_csv(
        IMAGE_ROOT / "animation_index.csv",
        [
            "sprite_id",
            "animation_group",
            "frame_count",
            "source_total_ticks",
            "source_total_ms",
            "animation_key",
            "output_kind",
            "output_file",
        ],
        group_rows,
    )
    removed_duplicates = remove_duplicate_sprite_exports()
    print(
        json.dumps(
            {
                "sprites_in_library": len(sprite_library["sprites"]),
                "dynamic_spritesheets": sum(
                    group["outputKind"] == "spritesheet"
                    for sprite in sprite_library["sprites"]
                    for group in sprite["groups"]
                ),
                "static_animation_groups": sum(
                    group["outputKind"] == "image"
                    for sprite in sprite_library["sprites"]
                    for group in sprite["groups"]
                ),
                "animation_groups": len(group_rows),
                "animation_frames": sum(row["frame_count"] for row in group_rows),
                "tiled_maps": len(map_index),
                "map_tileset_sprites": len(
                    {row["tile_atlas_sprite_id"] for row in map_index}
                ),
                "map_objects": len(map_objects),
                "full_map_previews": preview_count,
                "indexed_map_entities": entity_count,
                "removed_duplicate_sprite_files": removed_duplicates,
                "output": str(PHASER_ROOT),
            },
            ensure_ascii=False,
            indent=2,
        )
    )


if __name__ == "__main__":
    main()
