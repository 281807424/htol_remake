"""Extract structural metadata for the client's bundled map resources."""

import argparse
import hashlib
import json
import struct
import zipfile
from pathlib import Path


ROOT = Path(__file__).resolve().parents[1]
DEFAULT_JAR = ROOT / "htol_local.jar"
DEFAULT_OUTPUT = ROOT / "analysis" / "map_catalog.json"
FIRST_MAP_RESOURCE = 7
LAST_MAP_RESOURCE = 161


def _sha256(data):
    return hashlib.sha256(data).hexdigest()


def _read_partition(archive, member_name, partition_index):
    partition_member = member_name if partition_index == 0 else (
        "{}.{}".format(member_name, partition_index)
    )
    try:
        return archive.read(partition_member)
    except KeyError as error:
        raise ValueError(
            "Missing resource partition {!r}".format(partition_member)
        ) from error


def read_map_sections(archive, member_name):
    data = _read_partition(archive, member_name, 0)
    if len(data) < 4:
        raise ValueError("Resource {!r} has a truncated header".format(member_name))

    entry_count, partition_count = struct.unpack_from("<HH", data)
    header_end = 4 + partition_count * 2
    if entry_count <= 0 or partition_count <= 0 or len(data) < header_end:
        raise ValueError("Resource {!r} has invalid partition metadata".format(member_name))

    partition_starts = struct.unpack_from(
        "<{}H".format(partition_count), data, 4
    )
    sections = []
    for partition_index, entry_start in enumerate(partition_starts):
        next_entry_start = (
            partition_starts[partition_index + 1]
            if partition_index + 1 < partition_count
            else entry_count
        )
        partition_entry_count = next_entry_start - entry_start
        if partition_entry_count <= 0:
            raise ValueError(
                "Resource {!r} has an empty or invalid partition".format(member_name)
            )

        partition_data = _read_partition(archive, member_name, partition_index)
        table_start = header_end if partition_index == 0 else 0
        table_end = table_start + (partition_entry_count + 1) * 4
        if table_end > len(partition_data):
            raise ValueError(
                "Resource {!r} has a truncated section-offset table".format(member_name)
            )
        offsets = struct.unpack_from(
            "<{}I".format(partition_entry_count + 1),
            partition_data,
            table_start,
        )

        for offset_index in range(partition_entry_count):
            section_start = offsets[offset_index]
            section_end = offsets[offset_index + 1]
            if section_start >= section_end or section_end > len(partition_data):
                raise ValueError(
                    "Resource {!r} has invalid section offsets".format(member_name)
                )
            sections.append(
                partition_data[section_start + 1:section_end]
            )

    if len(sections) != 4:
        raise ValueError(
            "Resource {!r} contains {} sections; expected 4".format(
                member_name, len(sections)
            )
        )
    return sections


def parse_map_resource(archive, member_name, resource_number):
    data = _read_partition(archive, member_name, 0)
    sections = read_map_sections(archive, member_name)
    if len(sections[0]) < 4:
        raise ValueError(
            "Resource {!r} has a truncated map header".format(member_name)
        )

    width, height = struct.unpack_from("<HH", sections[0])
    if width == 0 or height == 0:
        raise ValueError(
            "Resource {!r} has invalid dimensions {}x{}".format(
                member_name, width, height
            )
        )

    return {
        "resource_number": resource_number,
        "resource_member": member_name,
        "map_index": resource_number - FIRST_MAP_RESOURCE,
        "width_tiles": width,
        "height_tiles": height,
        "width_pixels": width * 24,
        "height_pixels": height * 24,
        "section_bytes": [len(section) for section in sections],
        "resource_sha256": _sha256(data),
        "section_sha256": [_sha256(section) for section in sections],
    }


def build_catalog(jar_path):
    with zipfile.ZipFile(jar_path) as archive:
        members = set(archive.namelist())
        maps = []
        for resource_number in range(FIRST_MAP_RESOURCE, LAST_MAP_RESOURCE + 1):
            member_name = str(resource_number)
            if member_name not in members:
                raise ValueError(
                    "The original JAR is missing map resource {!r}".format(
                        member_name
                    )
                )
            maps.append(
                parse_map_resource(archive, member_name, resource_number)
            )

    return {
        "source_jar": Path(jar_path).name,
        "resource_range": [
            FIRST_MAP_RESOURCE,
            LAST_MAP_RESOURCE,
        ],
        "map_count": len(maps),
        "map_index_rule": "map_index = resource_number - 7 (client bm.L)",
        "format_note": (
            "Each resource has four client-indexed sections. Width and height "
            "are the first two little-endian 16-bit values in section 0. "
            "Section hashes cover the payload after the one-byte prefix skipped "
            "by the client."
        ),
        "maps": maps,
    }


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--jar", type=Path, default=DEFAULT_JAR)
    parser.add_argument("--output", type=Path, default=DEFAULT_OUTPUT)
    arguments = parser.parse_args()

    catalog = build_catalog(arguments.jar)
    arguments.output.parent.mkdir(parents=True, exist_ok=True)
    arguments.output.write_text(
        json.dumps(catalog, ensure_ascii=False, indent=2) + "\n",
        encoding="utf-8",
    )
    print(
        "Parsed {} map resources into {}".format(
            catalog["map_count"], arguments.output
        )
    )


if __name__ == "__main__":
    main()
