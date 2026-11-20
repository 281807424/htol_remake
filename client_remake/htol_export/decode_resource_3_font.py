#!/usr/bin/env python3
"""Decode the /3 bitmap font and index its inline chat glyphs."""

from __future__ import annotations

import csv
import math
from pathlib import Path

from PIL import Image


ROOT = Path(__file__).resolve().parent
FONT_PATH = ROOT / "raw_resources" / "set_3" / "resource_000.bin"
INLINE_GLYPHS_PATH = ROOT / "raw_resources" / "set_3" / "resource_001.bin"
IMAGE_PATH = ROOT / "other" / "fonts" / "resource_3_font_atlas.png"
CSV_PATH = ROOT / "text" / "resource_3_font_glyphs.csv"
INLINE_CSV_PATH = ROOT / "text" / "resource_3_inline_glyphs.csv"


def decode_font(data: bytes) -> tuple[int, list[int], bytes]:
    if len(data) < 6:
        raise ValueError("font payload is shorter than its header")

    version = data[0]
    glyph_size = data[1]
    glyph_count = int.from_bytes(data[2:6], "big")
    if glyph_size != 16:
        raise ValueError(f"unexpected font glyph size: {glyph_size}")
    if glyph_count <= 0:
        raise ValueError(f"invalid font glyph count: {glyph_count}")

    character_table_end = 6 + glyph_count * 2
    bitmap_bytes = glyph_count * (glyph_size * glyph_size // 8)
    expected_size = character_table_end + bitmap_bytes
    if expected_size != len(data):
        raise ValueError(
            f"font payload size mismatch: expected {expected_size}, got {len(data)}"
        )

    characters = [
        int.from_bytes(data[offset : offset + 2], "big")
        for offset in range(6, character_table_end, 2)
    ]
    return version, characters, data[character_table_end:]


def main() -> None:
    if not FONT_PATH.is_file():
        raise FileNotFoundError(f"resource /3 record 0 not found: {FONT_PATH}")
    if not INLINE_GLYPHS_PATH.is_file():
        raise FileNotFoundError(
            f"resource /3 record 1 not found: {INLINE_GLYPHS_PATH}"
        )

    version, characters, bitmap_data = decode_font(FONT_PATH.read_bytes())
    inline_data = INLINE_GLYPHS_PATH.read_bytes()
    if len(inline_data) < 8:
        raise ValueError("resource /3 record 1 is shorter than its ao header")
    inline_glyph_count = int.from_bytes(inline_data[6:8], "little")
    if inline_glyph_count != 11:
        raise ValueError(
            f"unexpected /3 inline glyph count: {inline_glyph_count}"
        )

    glyph_size = 16
    columns = 64
    rows = math.ceil(len(characters) / columns)
    atlas = Image.new(
        "RGBA",
        (columns * glyph_size, rows * glyph_size),
        (0, 0, 0, 0),
    )
    pixels = atlas.load()
    bytes_per_glyph = glyph_size * glyph_size // 8

    CSV_PATH.parent.mkdir(parents=True, exist_ok=True)
    IMAGE_PATH.parent.mkdir(parents=True, exist_ok=True)
    with CSV_PATH.open("w", encoding="utf-8-sig", newline="") as stream:
        writer = csv.writer(stream)
        writer.writerow(
            ["glyph_index", "codepoint", "character", "atlas_column", "atlas_row"]
        )
        for glyph_index, codepoint in enumerate(characters):
            column = glyph_index % columns
            row = glyph_index // columns
            glyph_offset = glyph_index * bytes_per_glyph
            glyph = bitmap_data[glyph_offset : glyph_offset + bytes_per_glyph]
            for byte_index, value in enumerate(glyph):
                y, byte_column = divmod(byte_index, 2)
                for bit in range(8):
                    if value & (1 << bit):
                        pixels[
                            column * glyph_size + byte_column * 8 + bit,
                            row * glyph_size + y,
                        ] = (255, 0, 0, 255)
            writer.writerow(
                [
                    glyph_index,
                    f"U+{codepoint:04X}",
                    chr(codepoint),
                    column,
                    row,
                ]
            )

    atlas.save(IMAGE_PATH)
    with INLINE_CSV_PATH.open("w", encoding="utf-8-sig", newline="") as stream:
        writer = csv.writer(stream)
        writer.writerow(["glyph_index", "inline_token", "source_record"])
        for glyph_index in range(inline_glyph_count):
            writer.writerow(
                [glyph_index, f"%{chr(ord('a') + glyph_index)}", "3:1"]
            )

    print(
        f"Decoded /3 font: version={version}, glyphs={len(characters)}, "
        f"atlas={atlas.width}x{atlas.height}; "
        f"indexed {inline_glyph_count} inline glyphs"
    )


if __name__ == "__main__":
    main()
