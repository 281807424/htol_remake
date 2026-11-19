#!/usr/bin/env python3
"""Build lossless Phaser animation atlases and Tiled-compatible map exports."""

from __future__ import annotations

import base64
import csv
import json
import math
import re
from collections import defaultdict
from pathlib import Path
from typing import Any

from PIL import Image

from export_character_phaser_assets import export_character_phaser_assets
from sprite_library_taxonomy import (
    DUPLICATE_SPRITE_IDS,
    component_filename,
    group_filename,
    is_role_unarmed_group,
    role_animation_details,
    sprite_category as manual_sprite_category,
    sprite_name as manual_sprite_name,
)


ROOT = Path(__file__).resolve().parent
IMAGE_ROOT = ROOT / "images"
PHASER_ROOT = IMAGE_ROOT / "phaser"
SPRITE_LIBRARY_ROOT = IMAGE_ROOT / "sprite_library"
MAP_ROOT = PHASER_ROOT / "maps"
TILESET_ROOT = MAP_ROOT / "tilesets"
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
        raise FileNotFoundError(f"Missing existing sprite library image: {output_path}")
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
                Path(category) / "空手"
                if is_role_unarmed_group(sprite_id, animation_group)
                else Path(category) / ("dynamic" if is_dynamic else "static")
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
            category = "map_objects" if is_map_sprite else manual_sprite_category(sprite_id)
            source = IMAGE_ROOT / row["file"]
            if source.is_file() and row["file"].startswith("sprite_library/"):
                source = SPRITE_LIBRARY_ROOT / row["file"].removeprefix("sprite_library/")
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
            destination = SPRITE_LIBRARY_ROOT / category / "static" / component_name
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
    return manifest


def map_tile_crop(image_path: Path) -> Image.Image:
    image = Image.open(image_path).convert("RGBA")
    if image.size == (88, 88):
        bounds = image.getchannel("A").getbbox()
        if bounds is None:
            return Image.new("RGBA", (MAP_TILE_SIZE, MAP_TILE_SIZE))
        if bounds != (MAP_TILE_OFFSET, MAP_TILE_OFFSET, 44, 44):
            raise ValueError(f"Unexpected source map tile bounds in {image_path}: {bounds}")
        return image.crop(bounds)
    if image.size == (MAP_TILE_SIZE, MAP_TILE_SIZE):
        return image
    raise ValueError(f"Unexpected source map tile image dimensions in {image_path}: {image.size}")


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
    for sprite_id in atlas_ids:
        components = {}
        for path in (IMAGE_ROOT / "map_tiles").glob(
            f"sprite_{sprite_id:03d}_component_*.png"
        ):
            match = re.search(r"component_(\d+)", path.stem)
            if match:
                components[int(match.group(1))] = path
        if not components:
            raise ValueError(f"No tile components found for sprite {sprite_id}")
        tile_count = max(components) + 1
        columns = min(16, tile_count)
        rows = math.ceil(tile_count / columns)
        sheet = Image.new(
            "RGBA", (columns * MAP_TILE_SIZE, rows * MAP_TILE_SIZE), (0, 0, 0, 0)
        )
        for tile_id, path in components.items():
            tile = map_tile_crop(path)
            sheet.alpha_composite(
                tile, ((tile_id % columns) * MAP_TILE_SIZE, (tile_id // columns) * MAP_TILE_SIZE)
            )
        image_name = f"sprite_{sprite_id:03d}_tiles.png"
        sheet.save(TILESET_ROOT / image_name, format="PNG", optimize=True)
        write_json(
            TILESET_ROOT / f"sprite_{sprite_id:03d}.tsj",
            {
                "columns": columns,
                "image": image_name,
                "imageheight": rows * MAP_TILE_SIZE,
                "imagewidth": columns * MAP_TILE_SIZE,
                "margin": 0,
                "name": f"sprite_{sprite_id:03d}_tiles",
                "spacing": 0,
                "tilecount": tile_count,
                "tiledversion": "1.10.2",
                "tileheight": MAP_TILE_SIZE,
                "tileoffset": {"x": 0, "y": 0},
                "tilewidth": MAP_TILE_SIZE,
                "type": "tileset",
                "version": "1.10",
            },
        )


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
                    "source": f"tilesets/sprite_{sprite_id:03d}.tsj",
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

    entity_rows: list[dict[str, Any]] = []
    preview_index_rows: list[dict[str, Any]] = []
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

        canvas = Image.new(
            "RGBA", (width * MAP_TILE_SIZE, height * MAP_TILE_SIZE), (0, 0, 0, 0)
        )
        for expected_index, row in enumerate(cells):
            x = int(row["cell_x"])
            y = int(row["cell_y"])
            if (x, y) != (expected_index % width, expected_index // width):
                raise ValueError(f"Map {map_id}: tile coordinates are not row-major")
            component = row["tile_component"]
            if not component:
                continue
            component_id = int(component)
            component_path = (
                IMAGE_ROOT
                / "map_tiles"
                / f"sprite_{sprite_id:03d}_component_{component_id:04d}.png"
            )
            if not component_path.is_file():
                raise FileNotFoundError(
                    f"Map {map_id}: missing tile component {component_path}"
                )
            tile = map_tile_crop(component_path)
            transform = map_tile_transform(row)
            if transform & 1:
                tile = tile.transpose(Image.Transpose.FLIP_LEFT_RIGHT)
            if transform & 2:
                tile = tile.transpose(Image.Transpose.FLIP_TOP_BOTTOM)
            canvas.alpha_composite(tile, (x * MAP_TILE_SIZE, y * MAP_TILE_SIZE))

        map_entities = sorted(
            objects_by_map[map_id],
            key=lambda row: (
                int(row["draw_anchor_y_units"]),
                int(row["object_index"]),
            ),
        )
        for row in map_entities:
            sprite_resource_id = int(row["sprite_resource_id"])
            animation = int(row["animation_group"])
            frames = indexed_frames.get((sprite_resource_id, animation), [])
            if not frames:
                raise ValueError(
                    f"Map {map_id} object {row['object_index']} references missing "
                    f"sprite animation {sprite_resource_id}/{animation}"
                )
            frame = frames[0]
            frame_path = IMAGE_ROOT / frame["file"]
            if not frame_path.is_file():
                raise FileNotFoundError(
                    f"Map {map_id} object {row['object_index']}: missing sprite frame "
                    f"{frame_path}"
                )
            with Image.open(frame_path) as opened:
                image = opened.convert("RGBA")
            if image.getchannel("A").getbbox() is None:
                frame_origin_x = frame["origin_x_px"]
                frame_origin_y = frame["origin_y_px"]
            elif not frame["origin_x_px"] or not frame["origin_y_px"]:
                raise ValueError(
                    f"Sprite frame has visible pixels but unknown draw origin: {frame_path}"
                )
            else:
                frame_origin_x = frame["origin_x_px"]
                frame_origin_y = frame["origin_y_px"]

            anchor_x = int(row["draw_anchor_x_units"])
            anchor_y = int(row["draw_anchor_y_units"])
            origin_x = int(frame_origin_x or 0)
            origin_y = int(frame_origin_y or 0)
            image, origin_x, origin_y = transform_map_object_frame(
                image,
                origin_x,
                origin_y,
                int(row["field_5"] or 0),
            )
            canvas.alpha_composite(image, (anchor_x + origin_x, anchor_y + origin_y))
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
                    "frame_file": frame["file"],
                    "frame_origin_x_px": frame_origin_x,
                    "frame_origin_y_px": frame_origin_y,
                    "field5": row["field_5"],
                    "field6": row["field_6"],
                    "field7": row["field_7"],
                    "field8": row["field_8"],
                    "raw_fields_json": row["raw_fields_json"],
                }
            )

        output_path = IMAGE_ROOT / "maps" / f"map_{map_id:03d}_full_preview.png"
        output_path.parent.mkdir(parents=True, exist_ok=True)
        canvas.save(output_path, format="PNG", optimize=True)
        preview_index_rows.append(
            {
                "file_kind": "map_full_static_preview",
                "sprite_id": sprite_id,
                "category": "maps",
                "animation_group": "",
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
                "width_px": width * MAP_TILE_SIZE,
                "height_px": height * MAP_TILE_SIZE,
                "file": output_path.relative_to(IMAGE_ROOT).as_posix(),
                "source_file": f"../raw_resources/maps/map_{map_id:03d}_record_003.bin",
            }
        )

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

    map_tiles_path = IMAGE_ROOT / "map_tiles.csv"
    if tile_rows and "palette_id_2bit" in tile_rows[0]:
        for row in tile_rows:
            row["tile_transform_2bit"] = row.pop("palette_id_2bit")
        write_csv(
            map_tiles_path,
            [
                "map_resource_id",
                "cell_x",
                "cell_y",
                "world_x_units",
                "world_y_units",
                "render_x_px",
                "render_y_px",
                "tile_component",
                "tile_transform_2bit",
                "attribute_byte",
                "is_empty_tile",
                "tile_atlas_sprite_id",
            ],
            tile_rows,
        )

    map_index_path = IMAGE_ROOT / "map_index.csv"
    for row in map_index_rows:
        row["full_preview"] = f"maps/map_{int(row['map_resource_id']):03d}_full_preview.png"
    write_csv(
        map_index_path,
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

    asset_index_path = IMAGE_ROOT / "asset_file_index.csv"
    asset_rows = read_csv(asset_index_path)
    asset_rows = [
        row
        for row in asset_rows
        if row["file_kind"] != "map_full_static_preview"
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
    map_object_ids.update(
        int(row["tile_atlas_sprite_id"])
        for row in read_csv(IMAGE_ROOT / "map_index.csv")
        if row["tile_atlas_sprite_id"]
    )
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
        if not row["sprite_id"]:
            continue
        sprite_id = int(row["sprite_id"])
        if sprite_id in map_object_ids:
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
                row["file"] = f"sprite_library/{component['file']}"
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
                row["file"] = f"sprite_library/{group['file']}"
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
    protected_ids = {
        int(row["sprite_resource_id"])
        for row in read_csv(IMAGE_ROOT / "map_objects.csv")
        if row["sprite_resource_id"]
    }
    protected_ids.update(
        int(row["tile_atlas_sprite_id"])
        for row in read_csv(IMAGE_ROOT / "map_index.csv")
        if row["tile_atlas_sprite_id"]
    )
    library_ids = {
        int(sprite["spriteId"])
        for sprite in existing_library_sprites().values()
        if int(sprite["spriteId"]) not in protected_ids
    }
    duplicate_roots = [
        "character_palette_variants",
        "characters_candidate",
        "effects_candidate",
        "equipment_candidate",
        "items_candidate",
        "monsters_candidate",
        "ui_character_resources",
        "ui_icons_candidate",
        "unclassified",
        "world_objects_candidate",
    ]
    removed = 0
    for path in SPRITE_LIBRARY_ROOT.rglob("*.png"):
        match = re.match(r"sprite-(\d+)-", path.name)
        if match and int(match.group(1)) in DUPLICATE_SPRITE_IDS:
            path.unlink()
            removed += 1
    for directory in duplicate_roots:
        root = IMAGE_ROOT / directory
        if not root.exists():
            continue
        for path in root.rglob("*.png"):
            match = re.search(r"sprite[_-](\d+)", path.name, flags=re.IGNORECASE)
            if match and int(match.group(1)) in library_ids:
                path.unlink()
                removed += 1
    atlas_root = PHASER_ROOT / "atlases"
    if atlas_root.exists():
        for path in atlas_root.iterdir():
            match = re.search(r"sprite_(\d+)", path.name, flags=re.IGNORECASE)
            if match and int(match.group(1)) in library_ids:
                path.unlink()
                removed += 1
    for directory_name in (*duplicate_roots, "phaser/atlases"):
        root = IMAGE_ROOT / directory_name
        if not root.is_dir():
            continue
        for directory in sorted(
            (path for path in root.rglob("*") if path.is_dir()),
            key=lambda path: len(path.parts),
            reverse=True,
        ):
            if not any(directory.iterdir()):
                directory.rmdir()
        if not any(root.iterdir()):
            root.rmdir()
    return removed


def main() -> None:
    sprite_library = write_sprite_library()
    sprite_library = export_character_phaser_assets(sprite_library)
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
            "output_file": f"sprite_library/{group['file']}",
        }
        for sprite in sprite_library["sprites"]
        for group in sprite["groups"]
    ]
    map_index = read_csv(IMAGE_ROOT / "map_index.csv")
    map_tiles = read_csv(IMAGE_ROOT / "map_tiles.csv")
    map_objects = read_csv(IMAGE_ROOT / "map_objects.csv")
    write_tiled_tilesets(map_index)
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
    clean_gif_references()
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
