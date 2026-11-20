#!/usr/bin/env python3
"""Compose the two map illustrations encoded in sprite 20's AO groups."""

from __future__ import annotations

import json
import struct
from pathlib import Path
from typing import Any

from PIL import Image


ROOT = Path(__file__).resolve().parent
SOURCE = ROOT / "raw_resources" / "sprites" / "resource_020.bin"
SPRITE_LIBRARY = ROOT / "sprite_library.json"
OUTPUT = ROOT / "other" / "sprite_composites"
COMPONENT_CAPTURE_MARGIN = 8
AO_TO_MIDP_TRANSFORM = (0, 2, 1, 3, 5, 7, 4, 6)


def read_u16(data: bytes, offset: int) -> int:
    return struct.unpack_from("<H", data, offset)[0]


def read_i16(data: bytes, offset: int) -> int:
    return struct.unpack_from("<h", data, offset)[0]


def parse_sprite(
    data: bytes,
) -> tuple[
    list[tuple[int, int]],
    list[tuple[int, int, int, int]],
    list[tuple[int, int]],
    list[tuple[int, int, int, int]],
]:
    if len(data) < 8:
        raise ValueError("sprite 20 resource is shorter than the AO header")

    flags = struct.unpack_from("<I", data, 2)[0]
    component_count = read_u16(data, 6)
    cursor = 8
    component_sizes: list[tuple[int, int]] = []

    for component_id in range(component_count):
        kind = data[cursor]
        cursor += 1
        if kind == 0:
            _origin_x, _origin_y, width, height = struct.unpack_from(
                "<hhhh", data, cursor
            )
            cursor += 8
        else:
            raise ValueError(
                f"unsupported AO component type {kind} at component {component_id}"
            )
        if width <= 0 or height <= 0:
            raise ValueError(
                f"invalid dimensions for component {component_id}: {width}x{height}"
            )
        component_sizes.append((width, height))

    draw_count = read_u16(data, cursor)
    cursor += 2
    draw_items: list[tuple[int, int, int, int]] = []
    for _ in range(draw_count):
        component_or_group = data[cursor]
        x = read_i16(data, cursor + 1)
        y = read_i16(data, cursor + 3)
        draw_flags = data[cursor + 5]
        draw_items.append((component_or_group, x, y, draw_flags))
        cursor += 6

    group_count = read_u16(data, cursor)
    cursor += 2
    groups: list[tuple[int, int]] = []
    for _ in range(group_count):
        item_count = data[cursor]
        start = read_u16(data, cursor + 2)
        groups.append((item_count, start))
        cursor += 4

    for group_id, (item_count, start) in enumerate(groups):
        if start + item_count > len(draw_items):
            raise ValueError(f"group {group_id} points outside the draw-item table")

    if flags & 0x1000:
        raise ValueError("sprite 20 uses computed rather than serialized group bounds")
    group_bounds = []
    for _ in groups:
        if flags & 0x400:
            group_bounds.append(struct.unpack_from("<hhhh", data, cursor))
            cursor += 8
        else:
            x, y, width, height = data[cursor : cursor + 4]
            group_bounds.append((x, y, width, height))
            cursor += 4
    return component_sizes, draw_items, groups, group_bounds


def transform_image(image: Image.Image, transform: int) -> Image.Image:
    transpose = Image.Transpose
    midp_transform = AO_TO_MIDP_TRANSFORM[transform]
    transforms = {
        0: (),
        1: (transpose.FLIP_LEFT_RIGHT, transpose.ROTATE_180),
        2: (transpose.FLIP_LEFT_RIGHT,),
        3: (transpose.ROTATE_180,),
        4: (transpose.FLIP_LEFT_RIGHT, transpose.ROTATE_90),
        5: (transpose.ROTATE_270,),
        6: (transpose.ROTATE_90,),
        7: (transpose.FLIP_LEFT_RIGHT, transpose.ROTATE_270),
    }
    for operation in transforms[midp_transform]:
        image = image.transpose(operation)
    return image


def flatten_group(
    group_id: int,
    position_x: int,
    position_y: int,
    parent_groups: tuple[int, ...],
    draw_items: list[tuple[int, int, int, int]],
    groups: list[tuple[int, int]],
) -> list[dict[str, Any]]:
    if group_id in parent_groups:
        raise ValueError(f"cyclic AO group reference: {parent_groups + (group_id,)}")

    item_count, start = groups[group_id]
    flattened: list[dict[str, Any]] = []
    for item_index in range(start, start + item_count):
        component_or_group, x, y, flags = draw_items[item_index]
        absolute_x = position_x + x
        absolute_y = position_y + y
        if flags & 0x10:
            if flags & 0x0F:
                raise ValueError("nested AO groups with transforms are not supported")
            flattened.extend(
                flatten_group(
                    component_or_group,
                    absolute_x,
                    absolute_y,
                    parent_groups + (group_id,),
                    draw_items,
                    groups,
                )
            )
        else:
            flattened.append(
                {
                    "component_id": component_or_group,
                    "x": absolute_x,
                    "y": absolute_y,
                    "transform": flags & 0x07,
                    "midp_transform": AO_TO_MIDP_TRANSFORM[flags & 0x07],
                    "group_path": list(parent_groups + (group_id,)),
                }
            )
    return flattened


def compose_group(
    group_id: int,
    component_sizes: list[tuple[int, int]],
    component_files: dict[int, Path],
    draw_items: list[tuple[int, int, int, int]],
    groups: list[tuple[int, int]],
    group_bounds: list[tuple[int, int, int, int]],
) -> dict[str, Any]:
    leaves = flatten_group(group_id, 0, 0, (), draw_items, groups)
    positioned: list[tuple[dict[str, Any], Image.Image]] = []
    for item in leaves:
        component_id = item["component_id"]
        if component_id >= len(component_sizes):
            raise ValueError(f"component {component_id} is outside the component table")
        width, height = component_sizes[component_id]
        path = component_files.get(component_id)
        if path is None:
            raise ValueError(f"component {component_id} is missing from sprite_library.json")
        with Image.open(path) as source:
            source = source.convert("RGBA")
            left = COMPONENT_CAPTURE_MARGIN
            top = COMPONENT_CAPTURE_MARGIN
            right = left + width
            bottom = top + height
            if right > source.width or bottom > source.height:
                raise ValueError(f"component {component_id} is smaller than AO dimensions")
            crop = source.crop((left, top, right, bottom))
        crop = transform_image(crop, item["transform"])
        positioned.append((item, crop))
        item["width"] = crop.width
        item["height"] = crop.height

    min_x = min(item["x"] for item, _ in positioned)
    min_y = min(item["y"] for item, _ in positioned)
    max_x = max(item["x"] + image.width for item, image in positioned)
    max_y = max(item["y"] + image.height for item, image in positioned)
    width = max_x - min_x
    height = max_y - min_y
    expected_bounds = group_bounds[group_id]
    if (min_x, min_y, width, height) != expected_bounds:
        raise ValueError(
            f"group {group_id} rendered bounds {(min_x, min_y, width, height)} "
            f"do not match AO bounds {expected_bounds}"
        )
    canvas = Image.new("RGBA", (width, height), (0, 0, 0, 0))
    for item, image in positioned:
        canvas.alpha_composite(image, (item["x"] - min_x, item["y"] - min_y))

    return {
        "group_id": group_id,
        "width": width,
        "height": height,
        "source_origin": {"x": min_x, "y": min_y},
        "ao_bounds": {
            "x": expected_bounds[0],
            "y": expected_bounds[1],
            "width": expected_bounds[2],
            "height": expected_bounds[3],
        },
        "draw_item_count": len(leaves),
        "draw_items": leaves,
        "image": canvas,
    }


def main() -> None:
    component_sizes, draw_items, groups, group_bounds = parse_sprite(
        SOURCE.read_bytes()
    )
    manifest = json.loads(SPRITE_LIBRARY.read_text(encoding="utf-8"))
    sprite = next(
        (item for item in manifest["sprites"] if int(item["spriteId"]) == 20),
        None,
    )
    if sprite is None:
        raise ValueError("sprite 20 is missing from sprite_library.json")
    component_files = {
        int(item["componentId"]): ROOT / item["file"]
        for item in sprite["components"]
    }
    OUTPUT.mkdir(parents=True, exist_ok=True)

    for group_id in (0, 5):
        result = compose_group(
            group_id,
            component_sizes,
            component_files,
            draw_items,
            groups,
            group_bounds,
        )
        image_path = OUTPUT / f"sprite_020_group_{group_id}.png"
        json_path = OUTPUT / f"sprite_020_group_{group_id}.json"
        result["image"].save(image_path)
        metadata = {key: value for key, value in result.items() if key != "image"}
        metadata["image"] = image_path.name
        json_path.write_text(
            json.dumps(metadata, ensure_ascii=False, indent=2) + "\n",
            encoding="utf-8",
        )
        print(
            f"group {group_id}: {result['width']}x{result['height']}, "
            f"{result['draw_item_count']} draw items -> {image_path}"
        )


if __name__ == "__main__":
    main()
