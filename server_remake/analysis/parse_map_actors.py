"""Extract static actor records from the client's bundled map resources."""

import argparse
import json
import struct
import zipfile
from pathlib import Path

from parse_map_assets import (
    DEFAULT_JAR,
    FIRST_MAP_RESOURCE,
    LAST_MAP_RESOURCE,
    parse_map_resource,
    read_map_sections,
)


ROOT = Path(__file__).resolve().parents[1]
DEFAULT_OUTPUT = ROOT / "analysis" / "map_actor_catalog.json"
MAX_ACTOR_FIELDS = 20


def java_coordinate(raw_coordinate):
    scaled_coordinate = raw_coordinate * 2
    if scaled_coordinate < 0:
        return -((-scaled_coordinate) // 3)
    return scaled_coordinate // 3


def parse_actor_records(section):
    records = []
    offset = 0
    while offset < len(section):
        field_count = section[offset]
        offset += 1
        if field_count < 1 or field_count > MAX_ACTOR_FIELDS:
            raise ValueError(
                "Invalid map actor field count {} at byte {}".format(
                    field_count, offset - 1
                )
            )
        record_end = offset + field_count * 2
        if record_end > len(section):
            raise ValueError(
                "Truncated map actor record at byte {}".format(offset - 1)
            )
        fields = list(
            struct.unpack_from("<{}h".format(field_count), section, offset)
        )
        offset = record_end

        record = {
            "sprite_id": fields[0],
            "fields": fields,
            "kind": "static_actor" if fields[0] < 300 else "layer_asset",
        }
        if fields[0] < 300 and field_count >= 4:
            record["position_pixels"] = [
                java_coordinate(fields[2]),
                java_coordinate(fields[3]),
            ]
        records.append(record)
    return records


def build_catalog(jar_path):
    maps = []
    with zipfile.ZipFile(jar_path) as archive:
        for resource_number in range(FIRST_MAP_RESOURCE, LAST_MAP_RESOURCE + 1):
            member_name = str(resource_number)
            metadata = parse_map_resource(archive, member_name, resource_number)
            records = parse_actor_records(read_map_sections(archive, member_name)[3])
            maps.append({
                "resource_number": resource_number,
                "map_index": metadata["map_index"],
                "width_pixels": metadata["width_pixels"],
                "height_pixels": metadata["height_pixels"],
                "records": records,
            })

    return {
        "source_jar": Path(jar_path).name,
        "map_count": len(maps),
        "record_format": (
            "Section 3 consists of a one-byte field count followed by that "
            "many little-endian signed 16-bit values. For sprite IDs below "
            "300, values 2 and 3 are source coordinates transformed by "
            "Java's (value << 1) / 3 calculation."
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
    record_count = sum(len(map_data["records"]) for map_data in catalog["maps"])
    print(
        "Parsed {} map resources and {} section-3 records into {}".format(
            catalog["map_count"], record_count, arguments.output
        )
    )


if __name__ == "__main__":
    main()
