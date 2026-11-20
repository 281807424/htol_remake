#!/usr/bin/env python3
"""Build a labeled preview of the client's baked character weapon animations."""

from __future__ import annotations

import argparse
import csv
import struct
from dataclasses import dataclass
from pathlib import Path

from PIL import Image, ImageDraw, ImageFont


ROOT = Path(__file__).resolve().parent
IMAGE_ROOT = ROOT
OUTPUT = IMAGE_ROOT / "player" / "weapon_appearance_export" / "character_weapon_preview.png"
MAPPING_OUTPUT = (
    IMAGE_ROOT
    / "player"
    / "weapon_appearance_export"
    / "character_appearance_mapping_preview.png"
)
SPRITES = (74, 75, 76)
MODES = (
    ("男 / 模式 1", 0),
    ("男 / 模式 2", 22),
    ("女 / 模式 1", 51),
    ("女 / 模式 2", 73),
)

BACKGROUND = (27, 31, 40, 255)
CARD_BACKGROUND = (40, 45, 56, 255)
TEXT_COLOR = (240, 242, 246, 255)
MUTED_COLOR = (174, 183, 197, 255)
ACCENT_COLOR = (112, 180, 255, 255)
AO_TO_MIDP_TRANSFORM = (0, 2, 1, 3, 5, 7, 4, 6)
HORIZONTAL_TRANSFORMS = (1, 0, 3, 2, 6, 7, 4, 5)
VERTICAL_TRANSFORMS = (2, 3, 0, 1, 5, 4, 7, 6)
ROTATED_TRANSFORMS = (4, 5, 6, 7, 3, 2, 1, 0)


@dataclass(frozen=True)
class AoSprite:
    component_sizes: list[tuple[int, int]]
    component_types: list[int]
    draw_items: list[tuple[int, int, int, int]]
    frames: list[tuple[int, int]]
    animations: list[tuple[int, int, int, int, int]]
    groups: list[tuple[int, int]]
    palette_count: int
    bitmaps: list[Image.Image | None]


def load_font(size: int) -> ImageFont.FreeTypeFont | ImageFont.ImageFont:
    for candidate in (
        Path("C:/Windows/Fonts/msyh.ttc"),
        Path("C:/Windows/Fonts/simhei.ttf"),
        Path("C:/Windows/Fonts/arial.ttf"),
    ):
        if candidate.is_file():
            return ImageFont.truetype(str(candidate), size)
    return ImageFont.load_default()


def load_animation_sheets() -> dict[tuple[int, int], Image.Image]:
    with (IMAGE_ROOT / "animation_index.csv").open(
        "r", encoding="utf-8-sig", newline=""
    ) as stream:
        rows = list(csv.DictReader(stream))

    sheets: dict[tuple[int, int], Image.Image] = {}
    for sprite_id in SPRITES:
        for label, group in MODES:
            row = next(
                (
                    candidate
                    for candidate in rows
                    if int(candidate["sprite_id"]) == sprite_id
                    and int(candidate["animation_group"]) == group
                ),
                None,
            )
            if row is None:
                raise ValueError(
                    f"sprite {sprite_id} is missing {label} animation group {group}"
                )
            path = IMAGE_ROOT / row["output_file"]
            if not path.is_file():
                raise FileNotFoundError(f"animation image is missing: {path}")
            with Image.open(path) as source:
                sheets[(sprite_id, group)] = source.convert("RGBA")
    return sheets


def decode_palette_color(color: int, palette_format: int) -> tuple[int, int, int, int]:
    if palette_format == 0x8888:
        alpha = (color >> 24) & 0xFF
        red = (color >> 16) & 0xFF
        green = (color >> 8) & 0xFF
        blue = color & 0xFF
    elif palette_format == 0x4444:
        alpha = ((color >> 12) & 0xF) * 17
        red = ((color >> 8) & 0xF) * 17
        green = ((color >> 4) & 0xF) * 17
        blue = (color & 0xF) * 17
    elif palette_format == 0x5515:
        alpha = 255 if color & 0x8000 else 0
        red = (color & 0x7C00) >> 7
        green = (color & 0x03E0) >> 2
        blue = (color & 0x001F) << 3
    elif palette_format == 0x6505:
        alpha = 0 if color == 63519 else 255
        red = (color & 0xF800) >> 8
        green = (color & 0x07E0) >> 3
        blue = (color & 0x001F) << 3
    else:
        raise ValueError(f"unsupported AO palette format: 0x{palette_format:04x}")

    if (red, green, blue) == (255, 0, 255):
        alpha = 0
    return red, green, blue, alpha


def decode_bitmap(
    data: bytes,
    width: int,
    height: int,
    palette: list[tuple[int, int, int, int]],
    compression: int,
) -> Image.Image:
    pixel_count = width * height
    pixels: list[int] = []
    cursor = 0

    def append_color(index: int, count: int = 1) -> None:
        if index >= len(palette):
            raise ValueError(f"AO bitmap palette index {index} is out of range")
        pixels.extend([index] * count)

    if compression == 25840:
        mask = 1
        shift = 0
        palette_size = len(palette) - 1
        while palette_size:
            palette_size >>= 1
            mask <<= 1
            shift += 1
        mask -= 1
        while cursor < len(data) and len(pixels) < pixel_count:
            value = data[cursor]
            cursor += 1
            append_color(value & mask, (value >> shift) + 1)
    elif compression == 10225:
        while cursor < len(data) and len(pixels) < pixel_count:
            value = data[cursor]
            cursor += 1
            if value > 127:
                count = value - 128
                if cursor >= len(data):
                    raise ValueError("truncated AO repeated-color run")
                append_color(data[cursor], count)
                cursor += 1
            else:
                append_color(value)
    elif compression == 22258:
        while cursor < len(data) and len(pixels) < pixel_count:
            count = data[cursor]
            cursor += 1
            if count > 127:
                count -= 128
                if cursor + count > len(data):
                    raise ValueError("truncated AO literal-color run")
                for index in data[cursor : cursor + count]:
                    append_color(index)
                cursor += count
            else:
                if cursor >= len(data):
                    raise ValueError("truncated AO repeated-color run")
                append_color(data[cursor], count)
                cursor += 1
    elif compression == 5632:
        for value in data:
            append_color(value >> 4)
            append_color(value & 0x0F)
            if len(pixels) >= pixel_count:
                break
    elif compression == 1024:
        for value in data:
            for shift in (6, 4, 2, 0):
                append_color((value >> shift) & 0x03)
                if len(pixels) >= pixel_count:
                    break
            if len(pixels) >= pixel_count:
                break
    elif compression == 512:
        for value in data:
            for shift in range(7, -1, -1):
                append_color((value >> shift) & 0x01)
                if len(pixels) >= pixel_count:
                    break
            if len(pixels) >= pixel_count:
                break
    elif compression == 22018:
        for index in data[:pixel_count]:
            append_color(index)
    else:
        raise ValueError(f"unsupported AO bitmap compression: 0x{compression:04x}")

    if len(pixels) < pixel_count:
        raise ValueError(
            f"AO bitmap decoded {len(pixels)} of {pixel_count} expected pixels"
        )
    image = Image.new("RGBA", (width, height))
    image.putdata([palette[index] for index in pixels[:pixel_count]])
    return image


def parse_ao_sprite(
    data: bytes, sprite_id: int, palette_index: int = 0
) -> AoSprite:
    if len(data) < 8:
        raise ValueError(f"sprite {sprite_id} payload is shorter than its AO header")

    flags = struct.unpack_from("<I", data, 2)[0]
    cursor = 6

    def read_u8() -> int:
        nonlocal cursor
        value = data[cursor]
        cursor += 1
        return value

    def read_u16() -> int:
        nonlocal cursor
        value = struct.unpack_from("<H", data, cursor)[0]
        cursor += 2
        return value

    def read_i16() -> int:
        nonlocal cursor
        value = struct.unpack_from("<h", data, cursor)[0]
        cursor += 2
        return value

    def skip(size: int) -> None:
        nonlocal cursor
        cursor += size
        if cursor > len(data):
            raise ValueError(f"sprite {sprite_id} AO payload ends unexpectedly")

    component_count = read_u16()
    component_sizes: list[tuple[int, int]] = []
    component_types: list[int] = []
    for component_id in range(component_count):
        kind = read_u8()
        component_types.append(kind)
        width = height = 0
        if kind == 0:
            skip(4)
            width, height = read_u16(), read_u16()
        elif kind in (255, 254):
            skip(4)
            width, height = read_u16(), read_u16()
        elif kind == 253:
            skip(4)
        elif kind in (252, 251):
            skip(4)
            width, height = read_u16(), read_u16()
            skip(4)
        elif kind in (250, 249):
            skip(12)
        else:
            raise ValueError(
                f"sprite {sprite_id} has unsupported AO component type {kind}"
            )
        component_sizes.append((width, height))

    draw_item_count = read_u16()
    draw_items = [
        (read_u8(), read_i16(), read_i16(), read_u8())
        for _ in range(draw_item_count)
    ]

    if flags & 0x8000:
        anchor_count = read_u16()
        skip(anchor_count * 4 * (2 if flags & 0x400 else 1))

    frame_count = read_u16()
    frames = []
    for _ in range(frame_count):
        item_count = read_u8()
        skip(1)
        start = read_u16()
        if flags & 0x8000:
            skip(1)
        frames.append((item_count, start))

    if not flags & 0x1000:
        skip(frame_count * 8)

    animation_count = read_u16()
    animations = [
        (read_u8(), read_u8(), read_i16(), read_i16(), read_u8())
        for _ in range(animation_count)
    ]

    group_count = read_u16()
    groups = []
    for _ in range(group_count):
        item_count = read_u8()
        skip(1)
        start = read_u16()
        groups.append((item_count, start))

    palette_format = read_u16()
    palette_count = read_u8()
    colors_per_palette = read_u8() or 256
    palettes: list[list[tuple[int, int, int, int]]] = []
    for _ in range(palette_count):
        palette = []
        for _ in range(colors_per_palette):
            if palette_format == 0x8888:
                color = struct.unpack_from("<I", data, cursor)[0]
                cursor += 4
            else:
                color = read_u16()
            palette.append(decode_palette_color(color, palette_format))
        palettes.append(palette)

    compression = read_u16()
    chunks = []
    for _ in range(component_count):
        if flags & 0x80:
            size = data[cursor] | (data[cursor + 1] << 8)
            size |= data[cursor + 2] << 16 | data[cursor + 3] << 24
            cursor += 4
        else:
            size = read_u16()
        chunks.append(data[cursor : cursor + size])
        cursor += size
        if cursor > len(data):
            raise ValueError(f"sprite {sprite_id} has a truncated bitmap")

    if cursor != len(data):
        raise ValueError(
            f"sprite {sprite_id} AO parser stopped at {cursor} of {len(data)} bytes"
        )
    if not palettes:
        raise ValueError(f"sprite {sprite_id} has no AO color palette")
    if not 0 <= palette_index < len(palettes):
        raise ValueError(
            f"sprite {sprite_id} palette index {palette_index} is out of range"
        )

    bitmaps: list[Image.Image | None] = []
    for component_id, (width, height) in enumerate(component_sizes):
        if component_types[component_id] != 0:
            bitmaps.append(None)
            continue
        bitmaps.append(
            decode_bitmap(
                chunks[component_id],
                width,
                height,
                palettes[palette_index],
                compression,
            )
        )

    for group_id, (item_count, start) in enumerate(groups):
        if start + item_count > len(animations):
            raise ValueError(
                f"sprite {sprite_id} group {group_id} points outside its frame table"
            )
    return AoSprite(
        component_sizes=component_sizes,
        component_types=component_types,
        draw_items=draw_items,
        frames=frames,
        animations=animations,
        groups=groups,
        palette_count=len(palettes),
        bitmaps=bitmaps,
    )


def transform_image(image: Image.Image, transform: int) -> Image.Image:
    transpose = Image.Transpose
    midp_transform = AO_TO_MIDP_TRANSFORM[transform & 0x07]
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


def render_ao_frame(
    sprite: AoSprite,
    group_id: int,
    frame_index: int,
    mapping: dict[int, int],
) -> Image.Image:
    image, _, _ = render_ao_frame_with_anchor(
        sprite, group_id, frame_index, mapping
    )
    return image


def render_ao_frame_with_anchor(
    sprite: AoSprite,
    group_id: int,
    frame_index: int,
    mapping: dict[int, int],
) -> tuple[Image.Image, int, int]:
    if group_id >= len(sprite.groups):
        raise ValueError(f"AO animation group {group_id} is out of range")
    frame_count, start = sprite.groups[group_id]
    if frame_index >= frame_count:
        raise ValueError(
            f"AO group {group_id} has {frame_count} frames, "
            f"not frame {frame_index}"
        )
    animation_index = start + frame_index
    component_id, _, offset_x, offset_y, animation_flags = sprite.animations[
        animation_index
    ]
    component_id |= (animation_flags & 0xC0) << 2
    component_flags = animation_flags & 0x0F
    canvas = Image.new("RGBA", (512, 512), (0, 0, 0, 0))
    origin_x = origin_y = 256
    active_path: set[int] = set()

    def draw_composite(
        composite_id: int,
        x: int,
        y: int,
        transform: int,
    ) -> None:
        if composite_id in active_path:
            raise ValueError(f"cyclic AO component reference at {composite_id}")
        if composite_id >= len(sprite.frames):
            raise ValueError(f"AO composite {composite_id} is out of range")
        active_path.add(composite_id)
        item_count, first_item = sprite.frames[composite_id]
        for item_index in range(first_item, first_item + item_count):
            image_id, dx, dy, flags = sprite.draw_items[item_index]
            mapped_id = image_id
            if not flags & 0x10:
                mapped_id = mapping.get(image_id, image_id)
            width, height = (
                sprite.component_sizes[mapped_id]
                if mapped_id < len(sprite.component_sizes)
                else (0, 0)
            )
            if flags & 0x10:
                width = height = 0
            if flags & 4:
                width, height = height, width

            if transform & 1:
                flags = (flags & ~7) | HORIZONTAL_TRANSFORMS[flags & 7]
                dx = -dx - width
            if transform & 2:
                flags = (flags & ~7) | VERTICAL_TRANSFORMS[flags & 7]
                dy = -dy - height
            if transform & 4:
                flags = (flags & ~7) | ROTATED_TRANSFORMS[flags & 7]
                dx, dy = -dy - height, dx

            draw_x, draw_y = x + dx, y + dy
            if flags & 0x10:
                draw_composite(mapped_id, draw_x, draw_y, flags & 0x0F)
                continue
            if mapped_id >= len(sprite.bitmaps):
                raise ValueError(f"AO bitmap {mapped_id} is out of range")
            bitmap = sprite.bitmaps[mapped_id]
            if bitmap is None:
                raise ValueError(
                    f"sprite renderer does not support AO vector component {mapped_id}"
                )
            transformed = transform_image(bitmap, flags)
            canvas.alpha_composite(
                transformed,
                (int(round(draw_x)), int(round(draw_y))),
            )
        active_path.remove(composite_id)

    draw_composite(
        component_id,
        origin_x + offset_x,
        origin_y + offset_y,
        component_flags,
    )
    bounds = canvas.getbbox()
    if bounds is None:
        raise ValueError(
            f"AO group {group_id} frame {frame_index} rendered no visible pixels"
        )
    return canvas.crop(bounds), bounds[0] - origin_x, bounds[1] - origin_y


def load_appearance_mappings() -> dict[tuple[int, int], dict[int, int]]:
    mappings: dict[tuple[int, int], dict[int, int]] = {}
    with (IMAGE_ROOT / "sprite_palette_maps.csv").open(
        "r", encoding="utf-8-sig", newline=""
    ) as stream:
        for row in csv.DictReader(stream):
            if not row["sprite_id"] or not row["palette_slot"]:
                continue
            if not row["source_sprite_component_index"]:
                continue
            key = (int(row["sprite_id"]), int(row["palette_slot"]))
            mappings.setdefault(key, {})[
                int(row["source_sprite_component_index"])
            ] = int(row["target_sprite_component_index"])
    return mappings


def generate_appearance_mapping_preview() -> Path:
    mappings = load_appearance_mappings()
    font = load_font(12)
    title_font = load_font(28)
    subtitle_font = load_font(15)

    margin = 24
    title_height = 76
    card_width = 170
    card_height = 178
    row_gap = 14
    col_gap = 10
    slot_count = 11
    width = margin * 2 + slot_count * card_width + (slot_count - 1) * col_gap
    mapping_rows = (
        (71, 0, "狂狮"),
        (72, 0, "圣龙"),
        (73, 0, "麒麟"),
        *((sprite_id, 0, "男性") for sprite_id in SPRITES),
        *((sprite_id, 51, "女性") for sprite_id in SPRITES),
    )
    height = (
        margin
        + title_height
        + len(mapping_rows) * card_height
        + (len(mapping_rows) - 1) * row_gap
        + margin
    )
    canvas = Image.new("RGBA", (width, height), BACKGROUND)
    draw = ImageDraw.Draw(canvas)
    draw.text(
        (margin, margin),
        "客户端角色与坐骑外观映射效果",
        font=title_font,
        fill=TEXT_COLOR,
    )
    draw.text(
        (margin, margin + 43),
        "/6 槽位按 AO 绘制路径替换组件索引；坐骑 sprite 71–73 展示组 0，角色 sprite 74–76 展示男组 0 / 女组 51",
        font=subtitle_font,
        fill=MUTED_COLOR,
    )

    sprites: dict[int, AoSprite] = {}
    for sprite_id in (71, 72, 73, *SPRITES):
        source = ROOT / "raw_resources" / "sprites" / f"resource_{sprite_id:03d}.bin"
        sprites[sprite_id] = parse_ao_sprite(source.read_bytes(), sprite_id)

    for row_index, (sprite_id, group_id, appearance_label) in enumerate(mapping_rows):
        sprite = sprites[sprite_id]
        y = margin + title_height + row_index * (card_height + row_gap)
        for slot in range(slot_count):
            x = margin + slot * (card_width + col_gap)
            draw.rounded_rectangle(
                (x, y, x + card_width, y + card_height),
                radius=8,
                fill=CARD_BACKGROUND,
            )
            draw.text(
                (x + 9, y + 8),
                f"sprite {sprite_id} · {appearance_label} · 槽 {slot}",
                font=font,
                fill=TEXT_COLOR,
            )
            if slot > 0 and (sprite_id, slot) not in mappings:
                draw.text(
                    (x + 18, y + 78),
                    "无映射记录",
                    font=subtitle_font,
                    fill=MUTED_COLOR,
                )
                continue
            mapping = mappings.get((sprite_id, slot), {})
            frame = render_ao_frame(sprite, group_id, 0, mapping)
            scale = min(
                (card_width - 16) / frame.width,
                (card_height - 40) / frame.height,
            )
            resized = frame.resize(
                (
                    max(1, round(frame.width * scale)),
                    max(1, round(frame.height * scale)),
                ),
                Image.Resampling.NEAREST,
            )
            position = (
                x + (card_width - resized.width) // 2,
                y + 32 + (card_height - 40 - resized.height) // 2,
            )
            canvas.alpha_composite(resized, position)

    MAPPING_OUTPUT.parent.mkdir(parents=True, exist_ok=True)
    canvas.convert("RGB").save(MAPPING_OUTPUT, format="PNG", optimize=True)
    return MAPPING_OUTPUT


def generate_preview() -> Path:
    sheets = load_animation_sheets()
    font = load_font(18)
    title_font = load_font(30)
    subtitle_font = load_font(15)

    margin = 24
    title_height = 78
    header_height = 42
    row_header_width = 140
    card_width = 330
    card_height = 292
    row_gap = 14
    col_gap = 12
    footer_height = 58
    content_width = row_header_width + len(MODES) * card_width + len(MODES) * col_gap
    width = margin * 2 + content_width
    height = (
        margin
        + title_height
        + header_height
        + len(SPRITES) * card_height
        + (len(SPRITES) - 1) * row_gap
        + footer_height
        + margin
    )
    canvas = Image.new("RGBA", (width, height), BACKGROUND)
    draw = ImageDraw.Draw(canvas)
    draw.text((margin, margin), "客户端内置角色武器外观对照", font=title_font, fill=TEXT_COLOR)
    draw.text(
        (margin, margin + 43),
        "原始整身动画组：同一职业的武器模式与男女动作；非装备模型叠层",
        font=subtitle_font,
        fill=MUTED_COLOR,
    )

    grid_top = margin + title_height
    draw.text(
        (margin + row_header_width, grid_top + 10),
        "持械模式 1",
        font=font,
        fill=ACCENT_COLOR,
    )
    draw.text(
        (margin + row_header_width + card_width + col_gap, grid_top + 10),
        "持械模式 2",
        font=font,
        fill=ACCENT_COLOR,
    )
    draw.text(
        (margin + row_header_width + (card_width + col_gap) * 2, grid_top + 10),
        "持械模式 1",
        font=font,
        fill=ACCENT_COLOR,
    )
    draw.text(
        (margin + row_header_width + (card_width + col_gap) * 3, grid_top + 10),
        "持械模式 2",
        font=font,
        fill=ACCENT_COLOR,
    )
    sheet_top = grid_top + header_height

    for row_index, sprite_id in enumerate(SPRITES):
        y = sheet_top + row_index * (card_height + row_gap)
        draw.text(
            (margin, y + 16),
            f"sprite {sprite_id}",
            font=font,
            fill=TEXT_COLOR,
        )
        draw.text(
            (margin, y + 43),
            "女性组号 +51",
            font=load_font(13),
            fill=MUTED_COLOR,
        )
        for column, (label, group) in enumerate(MODES):
            x = margin + row_header_width + column * (card_width + col_gap)
            draw.rounded_rectangle(
                (x, y, x + card_width, y + card_height),
                radius=8,
                fill=CARD_BACKGROUND,
            )
            mode_text = "男性" if label.startswith("男 ") else "女性"
            draw.text(
                (x + 12, y + 9),
                f"{mode_text} · 组 {group}",
                font=load_font(14),
                fill=MUTED_COLOR,
            )

            sheet = sheets[(sprite_id, group)]
            available_width = card_width - 20
            available_height = card_height - 47
            scale = min(
                available_width / sheet.width,
                available_height / sheet.height,
            )
            resized = sheet.resize(
                (
                    max(1, round(sheet.width * scale)),
                    max(1, round(sheet.height * scale)),
                ),
                Image.Resampling.NEAREST,
            )
            position = (
                x + (card_width - resized.width) // 2,
                y + 38 + (available_height - resized.height) // 2,
            )
            canvas.alpha_composite(resized, position)

    footer_y = sheet_top + len(SPRITES) * card_height + (len(SPRITES) - 1) * row_gap
    draw.text(
        (margin, footer_y + 3),
        "备注：角色帧以整身画面绘制；肩甲/背饰的装备外观映射尚未确认。",
        font=subtitle_font,
        fill=TEXT_COLOR,
    )
    draw.text(
        (margin, footer_y + 27),
        "当前图展示可直接确认的武器动作；/6 组件索引替换效果另见角色外观映射图，具体装备名尚未确认。",
        font=load_font(13),
        fill=MUTED_COLOR,
    )

    OUTPUT.parent.mkdir(parents=True, exist_ok=True)
    canvas.convert("RGB").save(OUTPUT, format="PNG", optimize=True)
    return OUTPUT


def main() -> None:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.parse_args()
    output = generate_preview()
    mapping_output = generate_appearance_mapping_preview()
    print(f"Generated {output}")
    print(f"Generated {mapping_output}")


if __name__ == "__main__":
    main()
