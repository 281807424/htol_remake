from __future__ import annotations

import struct
import unittest

from generate_character_appearance_preview import (
    parse_ao_sprite,
    render_ao_frame,
    render_ao_frame_with_anchor,
)


def make_sprite() -> bytes:
    parts = [
        b"\x00\x00",
        struct.pack("<I", 0x1000),
        struct.pack("<H", 2),
    ]
    parts.extend(
        struct.pack("<Bhhhh", 0, 0, 0, 1, 1)
        for _ in range(2)
    )
    parts.extend(
        (
            struct.pack("<H", 1),
            struct.pack("<BhhB", 0, 0, 0, 0),
            struct.pack("<H", 1),
            struct.pack("<BBH", 1, 0, 0),
            struct.pack("<H", 1),
            struct.pack("<BBhhB", 0, 0, 0, 0, 0),
            struct.pack("<H", 1),
            struct.pack("<BBH", 1, 0, 0),
            struct.pack("<HBB", 0x8888, 1, 3),
            struct.pack("<III", 0xFFFF00FF, 0xFFFF0000, 0xFF00FF00),
            struct.pack("<H", 22018),
            struct.pack("<HB", 1, 1),
            struct.pack("<HB", 1, 2),
        )
    )
    return b"".join(parts)


class CharacterAppearanceRendererTests(unittest.TestCase):
    def test_component_mapping_replaces_drawn_sprite_index(self) -> None:
        sprite = parse_ao_sprite(make_sprite(), 74)

        base = render_ao_frame(sprite, 0, 0, {})
        mapped = render_ao_frame(sprite, 0, 0, {0: 1})

        self.assertEqual(base.getpixel((0, 0)), (255, 0, 0, 255))
        self.assertEqual(mapped.getpixel((0, 0)), (0, 255, 0, 255))

    def test_rendered_crop_preserves_its_offset_from_the_ao_root(self) -> None:
        sprite_data = bytearray(make_sprite())
        animation_offset = sprite_data.index(struct.pack("<BBhhB", 0, 0, 0, 0, 0))
        struct.pack_into("<BBhhB", sprite_data, animation_offset, 0, 0, -2, 3, 0)
        sprite = parse_ao_sprite(bytes(sprite_data), 74)

        image, offset_x, offset_y = render_ao_frame_with_anchor(sprite, 0, 0, {})

        self.assertEqual(image.size, (1, 1))
        self.assertEqual((offset_x, offset_y), (-2, 3))
        self.assertEqual(image.getpixel((0, 0)), (255, 0, 0, 255))


if __name__ == "__main__":
    unittest.main()
