#!/usr/bin/env python3
"""Flatten the existing HTOL sprite export and decode bundled map metadata."""

from __future__ import annotations

import argparse
import csv
import json
import re
import shutil
import struct
import zipfile
from pathlib import Path
from typing import Any

from PIL import Image


ROOT = Path(__file__).resolve().parent
IMAGE_ROOT = ROOT / "images"
LEGACY_SPRITES = IMAGE_ROOT / "sprites"
JAR_PATH = ROOT.parent.parent / "server_remake" / "htol_local.jar"
RAW_ROOT = ROOT / "raw_resources"
MAP_TILE_SIZE = 36
MAP_TILE_SOURCE_SIZE = 88
MAP_TILE_SOURCE_OFFSET = 8

CLASSIFICATIONS = {
    "ui_icons_candidate": ("界面图标（外观候选）", "visual-candidate"),
    "effects_candidate": ("技能/战斗特效（外观候选）", "visual-candidate"),
    "characters_candidate": ("角色与生物（外观候选）", "visual-candidate"),
    "items_candidate": ("道具与场景物件（外观候选）", "visual-candidate"),
    "monsters_candidate": ("怪物与坐骑（外观候选）", "visual-candidate"),
    "map_tiles": ("地图图块集", "source-confirmed"),
    "map_objects": ("地图对象", "source-confirmed"),
    "world_objects_candidate": ("场景/地形对象（外观候选）", "visual-candidate"),
    "equipment_candidate": ("装备/角色外观（外观候选）", "visual-candidate"),
    "character_palette_variants": ("角色外观映射", "source-confirmed"),
    "ui_character_resources": ("界面/角色系统引用资源", "source-reference"),
    "unclassified": ("未分类/未能绘制", "unresolved"),
}

DIRECT_SOURCE_REFERENCES = {
    307: "n.java:247-248",
    308: "ah.java:289-290; an.java:130; s.java:45,58; u.java:23; y.java:270",
    309: "bp.java:55; cb.java:19; co.java:117",
    310: "at.java:13",
    311: "ah.java:105",
}


def write_csv(path: Path, fields: list[str], rows: list[dict[str, Any]]) -> None:
    path.parent.mkdir(parents=True, exist_ok=True)
    with path.open("w", encoding="utf-8-sig", newline="") as stream:
        writer = csv.DictWriter(stream, fieldnames=fields, extrasaction="ignore")
        writer.writeheader()
        writer.writerows(rows)


def read_csv(path: Path) -> list[dict[str, str]]:
    with path.open("r", encoding="utf-8-sig", newline="") as stream:
        return list(csv.DictReader(stream))


def map_sprite_id(raw_id: int) -> int:
    return raw_id - 92 if raw_id >= 300 else raw_id + 217


def read_indexed_archive(blob: bytes) -> dict[int, bytes]:
    if len(blob) < 6:
        raise ValueError("resource archive is shorter than its header")
    total_records, shard_count = struct.unpack_from("<HH", blob, 0)
    if shard_count == 0:
        raise ValueError("resource archive has no shards")
    shard_starts = list(struct.unpack_from(f"<{shard_count}H", blob, 4))
    if shard_starts[0] != 0 or shard_starts != sorted(shard_starts):
        raise ValueError("resource archive has invalid shard boundaries")

    cursor = 4 + shard_count * 2
    records: dict[int, bytes] = {}
    for shard, shard_start in enumerate(shard_starts):
        shard_end = (
            shard_starts[shard + 1]
            if shard + 1 < shard_count
            else total_records
        )
        record_count = shard_end - shard_start
        if record_count < 0:
            raise ValueError("resource archive has a negative shard length")
        table_size = (record_count + 1) * 4
        if cursor + table_size > len(blob):
            raise ValueError("resource archive offset table is truncated")
        offsets = list(struct.unpack_from(f"<{record_count + 1}I", blob, cursor))
        cursor += table_size
        if offsets != sorted(offsets) or offsets[-1] > len(blob):
            raise ValueError("resource archive has invalid payload offsets")
        for local_id in range(record_count):
            start, end = offsets[local_id], offsets[local_id + 1]
            records[shard_start + local_id] = blob[start:end]
    return records


def runtime_payload(raw_record: bytes) -> bytes:
    # aq.c() consumes the first byte of every non-empty indexed record.
    return raw_record[1:] if raw_record else b""


def parse_object_blocks(data: bytes, resource_id: int) -> list[list[int]]:
    blocks: list[list[int]] = []
    cursor = 0
    while cursor < len(data):
        count = data[cursor]
        cursor += 1
        byte_count = count * 2
        if cursor + byte_count > len(data):
            raise ValueError(
                f"/{resource_id}: truncated object block at byte {cursor - 1}"
            )
        if count == 0:
            blocks.append([])
            continue
        blocks.append(list(struct.unpack_from(f"<{count}h", data, cursor)))
        cursor += byte_count
    return blocks


def java_divide(value: int, divisor: int) -> int:
    return abs(value) // divisor * (-1 if value < 0 else 1)


def output_category(
    sprite_id: int, map_tile_ids: set[int], map_object_ids: set[int]
) -> tuple[str, str, str, str]:
    if sprite_id in map_tile_ids:
        return (
            "map_tiles",
            *CLASSIFICATIONS["map_tiles"],
            "bm.java map tile atlas reference; bm.i() remaps map IDs",
        )
    if sprite_id in map_object_ids:
        return (
            "map_objects",
            *CLASSIFICATIONS["map_objects"],
            "bm.java map object record -> bm.i() -> h.java sprite lookup",
        )
    if 71 <= sprite_id <= 76:
        return (
            "character_palette_variants",
            *CLASSIFICATIONS["character_palette_variants"],
            "ao.java /6 component-index mapping; bj.java ranges and sprite IDs 71-76",
        )
    if sprite_id in DIRECT_SOURCE_REFERENCES:
        return (
            "ui_character_resources",
            *CLASSIFICATIONS["ui_character_resources"],
            DIRECT_SOURCE_REFERENCES[sprite_id],
        )
    if 1 <= sprite_id <= 20:
        category = "ui_icons_candidate"
    elif 23 <= sprite_id <= 69:
        category = "effects_candidate"
    elif 70 <= sprite_id <= 156:
        category = "characters_candidate"
    elif 157 <= sprite_id <= 179:
        category = "items_candidate"
    elif 180 <= sprite_id <= 203:
        category = "monsters_candidate"
    elif 217 <= sprite_id <= 274:
        category = "world_objects_candidate"
    elif 277 <= sprite_id <= 306:
        category = "equipment_candidate"
    else:
        category = "unclassified"
    label, confidence = CLASSIFICATIONS[category]
    evidence = (
        "contact-sheet appearance only; not a source-defined category"
        if confidence == "visual-candidate"
        else "no drawable frame/component or no confirmed use"
    )
    return category, label, confidence, evidence


def prepare_map_tile(
    image: Image.Image, source: Path
) -> tuple[Image.Image, tuple[int, int]]:
    image = image.convert("RGBA")
    if image.size == (MAP_TILE_SOURCE_SIZE, MAP_TILE_SOURCE_SIZE):
        bounds = image.getchannel("A").getbbox()
        if bounds is None:
            return Image.new("RGBA", (MAP_TILE_SIZE, MAP_TILE_SIZE)), (0, 0)
        expected_bounds = (
            MAP_TILE_SOURCE_OFFSET,
            MAP_TILE_SOURCE_OFFSET,
            MAP_TILE_SOURCE_OFFSET + MAP_TILE_SIZE,
            MAP_TILE_SOURCE_OFFSET + MAP_TILE_SIZE,
        )
        if bounds != expected_bounds:
            raise ValueError(
                f"{source}: unexpected map tile bounds {bounds}; "
                f"expected {expected_bounds}"
            )
        return image.crop(bounds), (0, 0)
    if image.size == (MAP_TILE_SIZE, MAP_TILE_SIZE):
        return image, (0, 0)
    raise ValueError(f"{source}: unexpected map tile size {image.size}")


def decode_maps(
    archive: zipfile.ZipFile,
) -> tuple[list[dict[str, Any]], list[dict[str, Any]], list[dict[str, Any]], set[int], set[int]]:
    map_index: list[dict[str, Any]] = []
    tiles: list[dict[str, Any]] = []
    objects: list[dict[str, Any]] = []
    map_tile_ids: set[int] = set()
    map_object_ids: set[int] = set()
    map_output = IMAGE_ROOT / "maps"
    raw_map_root = RAW_ROOT / "maps"
    map_output.mkdir(parents=True, exist_ok=True)
    raw_map_root.mkdir(parents=True, exist_ok=True)

    for resource_id in range(7, 162):
        member = str(resource_id)
        if member not in archive.namelist():
            raise ValueError(f"JAR is missing map resource archive /{resource_id}")
        raw_archive = archive.read(member)
        records = read_indexed_archive(raw_archive)
        if set(range(4)) - records.keys():
            raise ValueError(f"/{resource_id}: expected map records 0-3")
        runtime_records = {key: runtime_payload(value) for key, value in records.items()}
        for record_id, raw_record in records.items():
            raw_path = raw_map_root / f"map_{resource_id:03d}_record_{record_id:03d}.bin"
            raw_path.write_bytes(raw_record)

        header = runtime_records[0]
        if len(header) < 4:
            raise ValueError(f"/{resource_id}: map header is truncated")
        width, height = struct.unpack_from("<HH", header, 0)
        cell_count = width * height
        grid = header[4:]
        packed_palette = runtime_records[1]
        attributes = runtime_records[2]
        blocks = parse_object_blocks(runtime_records[3], resource_id)
        if len(grid) != cell_count:
            raise ValueError(
                f"/{resource_id}: expected {cell_count} grid bytes, found {len(grid)}"
            )
        if len(packed_palette) != (cell_count + 3) // 4:
            raise ValueError(f"/{resource_id}: invalid 2-bit tile palette length")
        if len(attributes) != cell_count:
            raise ValueError(f"/{resource_id}: invalid map attribute grid length")

        tile_sets = [
            values
            for values in blocks
            if values and values[0] >= 300
        ]
        if len(tile_sets) != 1 or len(tile_sets[0]) < 1:
            raise ValueError(
                f"/{resource_id}: expected one source-confirmed map tile atlas"
            )
        raw_tile_sprite_id = tile_sets[0][0]
        tile_sprite_id = map_sprite_id(raw_tile_sprite_id)
        map_tile_ids.add(tile_sprite_id)
        tile_index_path = (
            LEGACY_SPRITES
            / f"sprite_{tile_sprite_id:03d}"
            / "component_index.csv"
        )
        if not tile_index_path.exists():
            raise ValueError(
                f"/{resource_id}: missing tile components for sprite {tile_sprite_id}"
            )
        component_rows = read_csv(tile_index_path)
        available_components = {int(row["component"]) for row in component_rows}

        for values in blocks:
            if not values or values[0] >= 300 or values[0] < 0:
                continue
            map_object_ids.add(map_sprite_id(values[0]))

        map_path = map_output / f"map_{resource_id:03d}_base_preview.png"
        canvas = Image.new(
            "RGBA", (width * MAP_TILE_SIZE, height * MAP_TILE_SIZE), (0, 0, 0, 0)
        )
        component_cache: dict[
            int, tuple[Image.Image, tuple[int, int]]
        ] = {}
        max_component = max((tile for tile in grid if tile != 255), default=-1)
        if max_component >= 0 and max_component not in available_components:
            raise ValueError(
                f"/{resource_id}: tile index {max_component} is absent from sprite "
                f"{tile_sprite_id}"
            )
        for cell_index, tile_id in enumerate(grid):
            if tile_id not in component_cache:
                if tile_id != 255:
                    component_file = (
                        LEGACY_SPRITES
                        / f"sprite_{tile_sprite_id:03d}"
                        / "components"
                        / f"component_{tile_id:04d}.png"
                    )
                    if not component_file.exists():
                        raise ValueError(
                            f"/{resource_id}: missing tile component {tile_id} "
                            f"from sprite {tile_sprite_id}"
                        )
                    with Image.open(component_file) as image:
                        component_cache[tile_id] = prepare_map_tile(
                            image, component_file
                        )
            x, y = cell_index % width, cell_index // width
            transform_shift = (3 - (cell_index & 3)) * 2
            tile_transform = (
                packed_palette[cell_index >> 2] >> transform_shift
            ) & 0x03
            if tile_id != 255:
                tile, offset = component_cache[tile_id]
                if tile_transform & 1:
                    tile = tile.transpose(Image.Transpose.FLIP_LEFT_RIGHT)
                if tile_transform & 2:
                    tile = tile.transpose(Image.Transpose.FLIP_TOP_BOTTOM)
                canvas.alpha_composite(
                    tile,
                    (
                        x * MAP_TILE_SIZE + offset[0],
                        y * MAP_TILE_SIZE + offset[1],
                    ),
                )
            tiles.append(
                {
                    "map_resource_id": resource_id,
                    "cell_x": x,
                    "cell_y": y,
                    "world_x_units": x * 24,
                    "world_y_units": y * 24,
                    "render_x_px": x * 36,
                    "render_y_px": y * 36,
                    "tile_component": "" if tile_id == 255 else tile_id,
                    "tile_transform_2bit": tile_transform,
                    "attribute_byte": attributes[cell_index],
                    "is_empty_tile": "true" if tile_id == 255 else "false",
                    "tile_atlas_sprite_id": tile_sprite_id,
                }
            )
        canvas.save(map_path, format="PNG", optimize=True)

        object_count = 0
        tile_record_count = 0
        for block_index, values in enumerate(blocks):
            if not values:
                continue
            raw_id = values[0]
            is_tile_set = raw_id >= 300
            if is_tile_set:
                tile_record_count += 1
                continue
            object_count += 1
            sprite_id = map_sprite_id(raw_id) if raw_id >= 0 else None
            x_units = values[2] if len(values) > 2 else None
            y_units = values[3] if len(values) > 3 else None
            draw_x = (
                (java_divide(x_units * 2, 3) * 3) >> 1
                if x_units is not None
                else None
            )
            draw_y = (
                (java_divide(y_units * 2, 3) * 3) >> 1
                if y_units is not None
                else None
            )
            objects.append(
                {
                    "map_resource_id": resource_id,
                    "object_index": block_index,
                    "record_short_count": len(values),
                    "raw_sprite_id": raw_id,
                    "sprite_resource_id": sprite_id,
                    "source_x_units": x_units,
                    "source_y_units": y_units,
                    "draw_anchor_x_units": draw_x,
                    "draw_anchor_y_units": draw_y,
                    "map_cell_x_approx": round(x_units / 24, 4) if x_units is not None else None,
                    "map_cell_y_approx": round(y_units / 24, 4) if y_units is not None else None,
                    "animation_group": values[4] if len(values) > 4 else None,
                    "field_5": values[5] if len(values) > 5 else None,
                    "field_6": values[6] if len(values) > 6 else None,
                    "field_7": values[7] if len(values) > 7 else None,
                    "field_8": values[8] if len(values) > 8 else None,
                    "raw_fields_json": json.dumps(values, separators=(",", ":")),
                }
            )

        map_index.append(
            {
                "map_resource_id": resource_id,
                "jar_archive": f"/{resource_id}",
                "width_cells": width,
                "height_cells": height,
                "logical_cell_units": 24,
                "render_cell_px": 36,
                "tile_set_raw_id": raw_tile_sprite_id,
                "tile_atlas_sprite_id": tile_sprite_id,
                "tile_record_count": tile_record_count,
                "object_record_count": object_count,
                "empty_tile_count": sum(1 for value in grid if value == 255),
                "attribute_record_bytes": len(attributes),
                "base_preview": map_path.relative_to(IMAGE_ROOT).as_posix(),
                "full_preview": f"maps/map_{resource_id:03d}_full_preview.png",
                "raw_records_pattern": f"../raw_resources/maps/map_{resource_id:03d}_record_NNN.bin",
                "map_name": "",
                "map_name_status": "not present in client resource record",
            }
        )

    return map_index, tiles, objects, map_tile_ids, map_object_ids


def decode_palette_maps(archive: zipfile.ZipFile) -> list[dict[str, Any]]:
    raw_root = RAW_ROOT / "palettes"
    raw_root.mkdir(parents=True, exist_ok=True)
    sprite_ranges = [
        (74, 0, 10),
        (75, 11, 19),
        (76, 20, 28),
        (71, 29, 31),
        (72, 32, 33),
        (73, 34, 35),
    ]
    by_record = {
        record_id: (sprite_id, record_id - start)
        for sprite_id, start, end in sprite_ranges
        for record_id in range(start, end + 1)
    }
    records = read_indexed_archive(archive.read("6"))
    rows: list[dict[str, Any]] = []
    for record_id, raw in sorted(records.items()):
        (raw_root / f"resource_{record_id:03d}.bin").write_bytes(raw)
        payload = runtime_payload(raw)
        assignment = by_record.get(record_id)
        sprite_id, palette_slot = assignment if assignment else (None, None)
        if len(payload) % 4:
            rows.append(
                {
                    "resource_record_id": record_id,
                    "sprite_id": sprite_id,
                    "palette_slot": palette_slot,
                    "mapping_pair_index": "",
                    "source_sprite_component_index": "",
                    "target_sprite_component_index": "",
                    "status": f"payload length {len(payload)} is not a whole pair",
                    "raw_record_file": f"../raw_resources/palettes/resource_{record_id:03d}.bin",
                }
            )
            continue
        if not payload:
            rows.append(
                {
                    "resource_record_id": record_id,
                    "sprite_id": sprite_id,
                    "palette_slot": palette_slot,
                    "mapping_pair_index": "",
                    "source_sprite_component_index": "",
                    "target_sprite_component_index": "",
                    "status": (
                        "empty mapping record referenced by source"
                        if assignment
                        else "empty/unreferenced record"
                    ),
                    "raw_record_file": f"../raw_resources/palettes/resource_{record_id:03d}.bin",
                }
            )
            continue
        for pair_index in range(0, len(payload), 4):
            source, target = struct.unpack_from("<HH", payload, pair_index)
            rows.append(
                {
                    "resource_record_id": record_id,
                    "sprite_id": sprite_id,
                    "palette_slot": palette_slot,
                    "mapping_pair_index": pair_index // 4,
                    "source_sprite_component_index": source,
                    "target_sprite_component_index": target,
                    "status": (
                        "source-mapped"
                        if assignment
                        else "present but no observed /6 source mapping"
                    ),
                    "raw_record_file": f"../raw_resources/palettes/resource_{record_id:03d}.bin",
                }
            )
    return rows


def copy_asset(
    source: Path,
    category: str,
    destination_name: str,
    file_kind: str,
    sprite_id: int | str = "",
    animation_group: int | str = "",
    frame_index: int | str = "",
    metadata: dict[str, Any] | None = None,
) -> dict[str, Any]:
    destination_dir = IMAGE_ROOT / category
    destination_dir.mkdir(parents=True, exist_ok=True)
    destination = destination_dir / destination_name
    shutil.copy2(source, destination)
    if file_kind in {"undecoded_sprite_payload"}:
        width, height = "", ""
    else:
        with Image.open(destination) as image:
            width, height = image.size
    result: dict[str, Any] = {
        "file_kind": file_kind,
        "sprite_id": sprite_id,
        "category": category,
        "animation_group": animation_group,
        "frame_index": frame_index,
        "component_id": "",
        "delay_ticks": "",
        "delay_ms": "",
        "origin_x_px": "",
        "origin_y_px": "",
        "bound_min_x_px": "",
        "bound_min_y_px": "",
        "bound_max_x_px": "",
        "bound_max_y_px": "",
        "width_px": width,
        "height_px": height,
        "file": destination.relative_to(IMAGE_ROOT).as_posix(),
        "source_file": source.relative_to(ROOT).as_posix(),
    }
    if isinstance(sprite_id, int) and LEGACY_SPRITES in source.parents:
        result["source_file"] = (
            f"raw_resources/sprites/resource_{sprite_id:03d}.bin"
        )
    if metadata:
        result.update(metadata)
    return result


def copy_animation_frame(
    source: Path,
    category: str,
    destination_name: str,
    sprite_id: int,
    animation: int,
    frame: int,
    timing: dict[str, str],
) -> dict[str, Any]:
    destination_dir = IMAGE_ROOT / category
    destination_dir.mkdir(parents=True, exist_ok=True)
    destination = destination_dir / destination_name
    with Image.open(source) as opened:
        image = opened.convert("RGBA")
    alpha_bounds = image.getchannel("A").getbbox()
    try:
        min_x, min_y, max_x, max_y = (
            int(timing[key]) for key in ("min_x", "min_y", "max_x", "max_y")
        )
        valid_bounds = (
            max_x > min_x
            and max_y > min_y
            and abs(min_x) < 1_000_000
            and abs(min_y) < 1_000_000
            and abs(max_x) < 1_000_000
            and abs(max_y) < 1_000_000
        )
    except (KeyError, ValueError):
        valid_bounds = False
        min_x = min_y = max_x = max_y = 0

    if alpha_bounds and valid_bounds:
        width, height = max_x - min_x, max_y - min_y
        crop = (
            alpha_bounds[0],
            alpha_bounds[1],
            alpha_bounds[0] + width,
            alpha_bounds[1] + height,
        )
        if crop[2] > image.width or crop[3] > image.height:
            raise ValueError(
                f"{source}: recorded bounds do not fit the rendered canvas"
            )
        image = image.crop(crop)
        origin_x, origin_y = min_x, min_y
    elif alpha_bounds:
        image = image.crop(alpha_bounds)
        origin_x = origin_y = ""
    else:
        image = Image.new("RGBA", (1, 1), (0, 0, 0, 0))
        origin_x = origin_y = ""
    image.save(destination, format="PNG", optimize=True)
    return {
        "file_kind": "animation_frame",
        "sprite_id": sprite_id,
        "category": category,
        "animation_group": animation,
        "frame_index": frame,
        "component_id": "",
        "delay_ticks": timing.get("delay_ticks", ""),
        "delay_ms": timing.get("delay_ms", ""),
        "origin_x_px": origin_x,
        "origin_y_px": origin_y,
        "bound_min_x_px": min_x if valid_bounds else "",
        "bound_min_y_px": min_y if valid_bounds else "",
        "bound_max_x_px": max_x if valid_bounds else "",
        "bound_max_y_px": max_y if valid_bounds else "",
        "width_px": image.width,
        "height_px": image.height,
        "file": destination.relative_to(IMAGE_ROOT).as_posix(),
        "source_file": source.relative_to(ROOT).as_posix(),
    }


def flatten_sprite_assets(
    resource_rows: dict[int, dict[str, str]],
    map_tile_ids: set[int],
    map_object_ids: set[int],
    palette_sprites: set[int],
) -> tuple[list[dict[str, Any]], list[dict[str, Any]]]:
    file_rows: list[dict[str, Any]] = []
    catalog_rows: list[dict[str, Any]] = []
    sprite_root = LEGACY_SPRITES
    if not sprite_root.is_dir():
        raise FileNotFoundError(
            f"Expected pre-existing sprite export at {sprite_root}; "
            "this migration script does not reimplement the J2ME sprite decoder."
        )

    for sprite_id in range(312):
        source_dir = sprite_root / f"sprite_{sprite_id:03d}"
        if not source_dir.is_dir():
            source_dir = None
        category, label, confidence, evidence = output_category(
            sprite_id, map_tile_ids, map_object_ids
        )
        if sprite_id in palette_sprites and sprite_id not in map_tile_ids:
            category = "character_palette_variants"
            label, confidence = CLASSIFICATIONS[category]
            evidence = "ao.java /6 component-index mapping; bj.java ranges and sprite IDs 71-76"
        raw = resource_rows.get(sprite_id, {})
        frame_rows: dict[str, dict[str, str]] = {}
        animation_rows: dict[int, dict[str, str]] = {}
        if source_dir:
            timing_path = source_dir / "frame_timing.csv"
            if timing_path.exists():
                frame_rows = {
                    row["png"]: row for row in read_csv(timing_path)
                }
        animation_index = IMAGE_ROOT / "animation_index.csv"
        if animation_index.exists():
            for row in read_csv(animation_index):
                if int(row["sprite_id"]) == sprite_id:
                    animation_rows[int(row["animation_group"])] = row

        current_files: list[dict[str, Any]] = []
        animation_count = 0
        frame_count = 0
        component_count = 0
        if source_dir:
            for path in sorted((source_dir / "frames").glob("*.png")) if (source_dir / "frames").exists() else []:
                timing = frame_rows.get(path.name, {})
                animation = int(timing.get("animation", -1))
                frame = int(timing.get("frame", -1))
                min_x = timing.get("min_x", "")
                min_y = timing.get("min_y", "")
                max_x = timing.get("max_x", "")
                max_y = timing.get("max_y", "")
                copied = copy_animation_frame(
                    path,
                    category,
                    f"sprite_{sprite_id:03d}_anim_{animation:03d}_frame_{frame:03d}.png",
                    sprite_id,
                    animation,
                    frame,
                    timing,
                )
                current_files.append(copied)
                frame_count += 1
            animation_count = len(animation_rows)
            components_dir = source_dir / "components"
            if components_dir.exists():
                for path in sorted(components_dir.glob("*.png")):
                    component = re.search(r"component_(\d+)", path.stem)
                    component_id = int(component.group(1)) if component else ""
                    current_files.append(
                        copy_asset(
                            path,
                            category,
                            f"sprite_{sprite_id:03d}_component_{component_id:04d}.png",
                            "sprite_component",
                            sprite_id,
                            metadata={
                                "frame_index": component_id,
                                "component_id": component_id,
                            },
                        )
                    )
                    component_count += 1
            for overview_name, kind in (
                ("overview.png", "animation_overview"),
                ("components_overview.png", "component_overview"),
            ):
                path = source_dir / overview_name
                if path.exists():
                    current_files.append(
                        copy_asset(
                            path,
                            category,
                            f"sprite_{sprite_id:03d}_{overview_name}",
                            kind,
                            sprite_id,
                        )
                    )

        payload_bytes = int(raw.get("payload_bytes", "0") or 0)
        render_status = (
            "rendered"
            if frame_count or component_count
            else "raw-only"
            if payload_bytes
            else "empty-record"
        )
        if render_status == "raw-only":
            raw_source = RAW_ROOT / "sprites" / f"resource_{sprite_id:03d}.bin"
            if raw_source.exists():
                copied = copy_asset(
                    raw_source,
                    "unclassified",
                    f"resource_{sprite_id:03d}.bin",
                    "undecoded_sprite_payload",
                    sprite_id,
                )
                current_files.append(copied)
                evidence += "; raw payload preserved because no drawable frames/components were recovered"
        file_rows.extend(current_files)
        catalog_rows.append(
            {
                "sprite_id": sprite_id,
                "category": category,
                "category_label": label,
                "classification_confidence": confidence,
                "classification_evidence": evidence,
                "payload_bytes": payload_bytes,
                "payload_sha256": raw.get("sha256", ""),
                "render_status": render_status,
                "animation_groups": animation_count,
                "animation_frames": frame_count,
                "components": component_count,
                "flat_output_files": len(current_files),
                "source_reference": DIRECT_SOURCE_REFERENCES.get(sprite_id, ""),
            }
        )
    return catalog_rows, file_rows


def add_standalone_files(file_rows: list[dict[str, Any]]) -> None:
    standalone = IMAGE_ROOT / "standalone"
    if not standalone.exists():
        return
    for path in sorted(standalone.iterdir()):
        if path.is_file():
            with Image.open(path) as image:
                width, height = image.size
            file_rows.append(
                {
                    "file_kind": "standalone_image",
                    "sprite_id": "",
                    "category": "standalone",
                    "animation_group": "",
                    "frame_index": "",
                    "delay_ticks": "",
                    "delay_ms": "",
                    "origin_x_px": "",
                    "origin_y_px": "",
                    "bound_min_x_px": "",
                    "bound_min_y_px": "",
                    "bound_max_x_px": "",
                    "bound_max_y_px": "",
                    "width_px": width,
                    "height_px": height,
                    "file": f"standalone/{path.name}",
                    "source_file": f"images/standalone/{path.name}",
                }
            )


def add_map_preview_files(
    file_rows: list[dict[str, Any]], map_index: list[dict[str, Any]]
) -> None:
    for row in map_index:
        path = IMAGE_ROOT / row["base_preview"]
        with Image.open(path) as image:
            width, height = image.size
        file_rows.append(
            {
                "file_kind": "map_base_preview_unpaletted",
                "sprite_id": row["tile_atlas_sprite_id"],
                "category": "maps",
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
                "width_px": width,
                "height_px": height,
                "file": row["base_preview"],
                "source_file": f"../../server_remake/htol_local.jar!/{row['map_resource_id']}",
            }
        )


def write_animation_groups(catalog_rows: list[dict[str, Any]]) -> None:
    source = IMAGE_ROOT / "animation_index.csv"
    if not source.exists():
        return
    rows = []
    animation_rows = []
    for row in read_csv(source):
        sprite_id = int(row["sprite_id"])
        group = int(row["animation_group"])
        animation_key = f"sprite_{sprite_id:03d}_anim_{group:03d}"
        rows.append(
            {
                "sprite_id": sprite_id,
                "animation_group": group,
                "frame_count": row["frame_count"],
                "source_total_ticks": row["source_total_ticks"],
                "source_total_ms": row["source_total_ms"],
                "animation_key": animation_key,
                "output_kind": "pending",
                "output_file": "",
            }
        )
        animation_rows.append(
            {
                "sprite_id": sprite_id,
                "animation_group": group,
                "frame_count": row["frame_count"],
                "source_total_ticks": row["source_total_ticks"],
                "source_total_ms": row["source_total_ms"],
                "animation_key": animation_key,
                "output_kind": "pending",
                "output_file": "",
            }
        )
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
        rows,
    )
    write_csv(
        source,
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
        animation_rows,
    )


def main() -> None:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument(
        "--remove-legacy",
        action="store_true",
        help="remove images/sprites after all flat copies and indexes are written",
    )
    args = parser.parse_args()

    if not JAR_PATH.exists():
        raise FileNotFoundError(
            "HTOL JAR not found: expected server_remake/htol_local.jar"
        )
    inventory_rows = read_csv(ROOT / "resource_inventory.csv")
    resource_rows = {
        int(row["resource_id"]): row
        for row in inventory_rows
        if row["resource_set"] == "5"
    }

    with zipfile.ZipFile(JAR_PATH) as archive:
        map_index, map_tiles, map_objects, map_tile_ids, map_object_ids = decode_maps(
            archive
        )
        palette_rows = decode_palette_maps(archive)
    palette_sprites = {71, 72, 73, 74, 75, 76}
    catalog_rows, file_rows = flatten_sprite_assets(
        resource_rows, map_tile_ids, map_object_ids, palette_sprites
    )
    add_map_preview_files(file_rows, map_index)
    add_standalone_files(file_rows)
    write_animation_groups(catalog_rows)

    write_csv(
        IMAGE_ROOT / "asset_catalog.csv",
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
        catalog_rows,
    )
    write_csv(
        IMAGE_ROOT / "asset_file_index.csv",
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
        file_rows,
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
        map_index,
    )
    write_csv(
        IMAGE_ROOT / "map_tiles.csv",
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
        map_tiles,
    )
    write_csv(
        IMAGE_ROOT / "map_objects.csv",
        [
            "map_resource_id",
            "object_index",
            "record_short_count",
            "raw_sprite_id",
            "sprite_resource_id",
            "source_x_units",
            "source_y_units",
            "draw_anchor_x_units",
            "draw_anchor_y_units",
            "map_cell_x_approx",
            "map_cell_y_approx",
            "animation_group",
            "field_5",
            "field_6",
            "field_7",
            "field_8",
            "raw_fields_json",
        ],
        map_objects,
    )
    write_csv(
        IMAGE_ROOT / "sprite_palette_maps.csv",
        [
            "resource_record_id",
            "sprite_id",
            "palette_slot",
            "mapping_pair_index",
            "source_sprite_component_index",
            "target_sprite_component_index",
            "status",
            "raw_record_file",
        ],
        palette_rows,
    )
    print(
        json.dumps(
            {
                "sprite_records": len(catalog_rows),
                "sprite_rendered": sum(
                    1 for row in catalog_rows if row["render_status"] == "rendered"
                ),
                "sprite_raw_only": sum(
                    1 for row in catalog_rows if row["render_status"] == "raw-only"
                ),
                "sprite_empty": sum(
                    1 for row in catalog_rows if row["render_status"] == "empty-record"
                ),
                "flat_image_files": len(file_rows),
                "map_archives": len(map_index),
                "map_cells": len(map_tiles),
                "map_objects": len(map_objects),
                "map_tile_sprites": sorted(map_tile_ids),
                "map_object_sprites": sorted(map_object_ids),
                "palette_mapping_rows": len(palette_rows),
            },
            ensure_ascii=False,
            indent=2,
        )
    )
    if args.remove_legacy:
        shutil.rmtree(LEGACY_SPRITES)


if __name__ == "__main__":
    main()
