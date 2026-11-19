from __future__ import annotations

import json
import struct
import tempfile
import unittest
import zipfile
from io import BytesIO
from pathlib import Path
from unittest.mock import patch

from export_weapon_appearance import (
    animation_group_for_state,
    index_sprite_animations,
    read_indexed_record,
    remove_previous_outputs,
)


def make_archive() -> zipfile.ZipFile:
    archive_bytes = BytesIO()
    root_header = struct.pack("<HHHH", 3, 2, 0, 2)
    root_table = struct.pack("<3I", 20, 22, 25)
    root_payload = b"\xA0a\xB0bc"
    shard_table = struct.pack("<2I", 8, 12)
    shard_payload = b"\xC0xyz"
    palette_header = struct.pack("<HHH", 2, 1, 0)
    palette_table = struct.pack("<3I", 18, 19, 22)
    palette_payload = b"\x00\xA0\x01\x02"
    with zipfile.ZipFile(archive_bytes, "w") as archive:
        archive.writestr("5", root_header + root_table + root_payload)
        archive.writestr("5.1", shard_table + shard_payload)
        archive.writestr("6", palette_header + palette_table + palette_payload)
    archive_bytes.seek(0)
    return zipfile.ZipFile(archive_bytes)


class ReadSpriteRecordTests(unittest.TestCase):
    def test_reads_root_and_sharded_records_and_skips_runtime_prefix(self) -> None:
        with make_archive() as archive:
            self.assertEqual(
                read_indexed_record(archive, "5", 0), (b"\xA0a", b"a")
            )
            self.assertEqual(
                read_indexed_record(archive, "5", 1), (b"\xB0bc", b"bc")
            )
            self.assertEqual(
                read_indexed_record(archive, "5", 2), (b"\xC0xyz", b"xyz")
            )
            self.assertEqual(
                read_indexed_record(archive, "6", 0), (b"\x00", b"")
            )
            self.assertEqual(
                read_indexed_record(archive, "6", 1),
                (b"\xA0\x01\x02", b"\x01\x02"),
            )


class AnimationMappingTests(unittest.TestCase):
    def test_maps_weapon_modes_to_client_animation_groups(self) -> None:
        self.assertEqual(
            [animation_group_for_state(mode, 0) for mode in range(3)],
            [43, 0, 22],
        )
        self.assertEqual(
            [animation_group_for_state(mode, 2) for mode in range(3)],
            [46, 3, 25],
        )
        self.assertEqual(
            [animation_group_for_state(mode, 5) for mode in range(3)],
            [16, 16, 38],
        )
        self.assertEqual(
            [animation_group_for_state(mode, 7) for mode in range(3)],
            [43, 20, 42],
        )
        self.assertEqual(
            [animation_group_for_state(mode, 0, visual_variant=1) for mode in range(3)],
            [94, 51, 73],
        )

    def test_rejects_weapon_mode_outside_client_tables(self) -> None:
        with self.assertRaises(ValueError):
            animation_group_for_state(3, 0)

    def test_rejects_visual_variant_outside_client_offsets(self) -> None:
        with self.assertRaises(ValueError):
            animation_group_for_state(0, 0, visual_variant=2)


class AnimationIndexTests(unittest.TestCase):
    def test_indexes_all_groups_by_reference_without_copying_images(self) -> None:
        with tempfile.TemporaryDirectory() as temp_dir:
            image_root = Path(temp_dir)
            animation_rows = {}
            frame_rows = []
            groups = (0, 50, 51, 101)
            for group in groups:
                sheet = f"shared/sprite_074_group_{group:03d}.png"
                frame = f"shared/sprite_074_group_{group:03d}_frame_000.png"
                (image_root / "shared").mkdir(exist_ok=True)
                (image_root / sheet).write_bytes(b"sheet")
                (image_root / frame).write_bytes(b"frame")
                animation_rows[(74, group)] = {
                    "frame_count": "1",
                    "source_total_ticks": "1",
                    "source_total_ms": "50",
                    "output_file": sheet,
                }
                frame_rows.append(
                    {
                        "sprite_id": "74",
                        "animation_group": str(group),
                        "frame_index": "0",
                        "file": frame,
                        "delay_ticks": "1",
                        "delay_ms": "50",
                        "origin_x_px": "0",
                        "origin_y_px": "0",
                    }
                )

            with patch("export_weapon_appearance.IMAGE_ROOT", image_root):
                indexed = index_sprite_animations(74, animation_rows, frame_rows)

            self.assertEqual(set(indexed), set(groups))
            self.assertEqual(indexed[51]["visual_gender_label"], "female")
            self.assertEqual(indexed[50]["visual_gender_label"], "male_or_shared")
            self.assertEqual(
                indexed[51]["frames"][0]["image"],
                "../shared/sprite_074_group_051_frame_000.png",
            )
            self.assertEqual(len(list((image_root / "shared").iterdir())), 8)


class PreviousOutputCleanupTests(unittest.TestCase):
    def test_removes_only_files_listed_in_the_previous_manifest(self) -> None:
        with tempfile.TemporaryDirectory() as temp_dir:
            output_root = Path(temp_dir)
            generated_file = output_root / "frames" / "old.png"
            user_file = output_root / "frames" / "keep.png"
            generated_file.parent.mkdir()
            generated_file.write_bytes(b"generated")
            user_file.write_bytes(b"user")
            (output_root / "manifest.json").write_text(
                json.dumps(
                    {
                        "players": {
                            "0": {
                                "animations": {
                                    "0": {"frames": [{"file": "frames/old.png"}]}
                                }
                            }
                        }
                    }
                ),
                encoding="utf-8",
            )

            with patch("export_weapon_appearance.OUTPUT_ROOT", output_root):
                remove_previous_outputs()

            self.assertFalse(generated_file.exists())
            self.assertTrue(user_file.is_file())
            self.assertTrue((output_root / "manifest.json").is_file())


if __name__ == "__main__":
    unittest.main()
