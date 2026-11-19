import unittest

from export_character_phaser_assets import _load_appearance_slots, _pack_component_atlas
from generate_character_appearance_preview import parse_ao_sprite
from test_character_appearance_preview import make_sprite


class CharacterPhaserAssetExportTests(unittest.TestCase):
    def test_component_atlas_packs_bitmap_components_once(self) -> None:
        sprite = parse_ao_sprite(make_sprite(), 74)

        atlas, component_rects = _pack_component_atlas(sprite)

        self.assertEqual(len([rect for rect in component_rects if rect]), 2)
        self.assertEqual(
            [atlas.getpixel((rect["x"], rect["y"])) for rect in component_rects if rect],
            [(255, 0, 0, 255), (0, 255, 0, 255)],
        )

    def test_original_mapping_slots_include_the_base_and_all_role_variants(self) -> None:
        expected_slot_counts = {74: 11, 75: 9, 76: 9}
        for sprite_id, expected_count in expected_slot_counts.items():
            with self.subTest(sprite_id=sprite_id):
                slots = _load_appearance_slots(sprite_id)

                self.assertEqual(len(slots), expected_count)
                self.assertEqual([slot["slot"] for slot in slots], list(range(expected_count)))
                self.assertEqual(slots[0]["mapping"], {})


if __name__ == "__main__":
    unittest.main()
