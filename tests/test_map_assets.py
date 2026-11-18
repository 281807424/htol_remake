import struct
import unittest
import zipfile
from pathlib import Path
from tempfile import TemporaryDirectory

from analysis.parse_map_assets import (
    DEFAULT_JAR,
    build_catalog,
    parse_map_resource,
)


def make_map_resource(sections):
    data_start = 6 + (len(sections) + 1) * 4
    offsets = []
    encoded_sections = []
    current_offset = data_start
    for section in sections:
        encoded = b"\x00" + section
        offsets.append(current_offset)
        encoded_sections.append(encoded)
        current_offset += len(encoded)
    offsets.append(current_offset)
    return (
        struct.pack("<HHH", len(sections), 1, 0)
        + struct.pack("<{}I".format(len(offsets)), *offsets)
        + b"".join(encoded_sections)
    )


class MapAssetTests(unittest.TestCase):
    def test_map_resource_reads_client_sections_and_dimensions(self):
        sections = [
            struct.pack("<HH", 2, 3) + b"map",
            b"layer-one",
            b"layer-two",
            b"layer-three",
        ]
        with TemporaryDirectory() as temporary_directory:
            archive_path = Path(temporary_directory) / "maps.zip"
            with zipfile.ZipFile(archive_path, "w") as archive:
                archive.writestr("7", make_map_resource(sections))

            with zipfile.ZipFile(archive_path) as archive:
                result = parse_map_resource(archive, "7", 7)

        self.assertEqual(result["map_index"], 0)
        self.assertEqual(result["width_tiles"], 2)
        self.assertEqual(result["height_tiles"], 3)
        self.assertEqual(result["section_bytes"], [7, 9, 9, 11])
        self.assertEqual(len(result["section_sha256"]), 4)

    def test_original_jar_contains_all_155_parsed_maps(self):
        catalog = build_catalog(DEFAULT_JAR)
        self.assertEqual(catalog["map_count"], 155)
        self.assertEqual(catalog["maps"][0]["resource_member"], "7")
        self.assertEqual(catalog["maps"][-1]["resource_member"], "161")

    def test_resource_13_is_a_58_by_44_candidate(self):
        catalog = build_catalog(DEFAULT_JAR)
        candidate = catalog["maps"][6]
        self.assertEqual(candidate["resource_member"], "13")
        self.assertEqual(candidate["map_index"], 6)
        self.assertEqual(
            (candidate["width_tiles"], candidate["height_tiles"]),
            (58, 44),
        )


if __name__ == "__main__":
    unittest.main()
