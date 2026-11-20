#!/usr/bin/env python3
"""Index complete player sprite animations selected by the client."""

from __future__ import annotations

import argparse
import csv
import hashlib
import json
import struct
import zipfile
from pathlib import Path
from typing import Any


ROOT = Path(__file__).resolve().parent
IMAGE_ROOT = ROOT
OUTPUT_ROOT = IMAGE_ROOT / "player" / "weapon_appearance_export"
PLAYER_SPRITES = {0: 74, 1: 75, 2: 76}
PLAYER_PALETTE_RECORD_RANGES = {0: (0, 10), 1: (11, 19), 2: (20, 28)}


def sha256(data: bytes) -> str:
    return hashlib.sha256(data).hexdigest()


def read_indexed_record(
    archive: zipfile.ZipFile, resource_set: str, resource_id: int
) -> tuple[bytes, bytes]:
    """Return one indexed archive record and aq.c()'s payload."""
    root_blob = archive.read(resource_set)
    if len(root_blob) < 6:
        raise ValueError(f"JAR /{resource_set} archive has a truncated header")
    resource_count, shard_count = struct.unpack_from("<HH", root_blob)
    if not shard_count or resource_id < 0 or resource_id >= resource_count:
        raise ValueError(f"invalid /{resource_set} resource ID: {resource_id}")
    starts = list(
        struct.unpack_from(f"<{shard_count}H", root_blob, 4)
    )
    if starts[0] != 0 or starts != sorted(starts):
        raise ValueError("JAR /5 archive has invalid shard boundaries")

    shard = max(index for index, start in enumerate(starts) if start <= resource_id)
    shard_start = starts[shard]
    shard_end = (
        starts[shard + 1] if shard + 1 < shard_count else resource_count
    )
    blob = (
        root_blob
        if shard == 0
        else archive.read(f"{resource_set}.{shard}")
    )
    table_offset = 4 + shard_count * 2 if shard == 0 else 0
    record_count = shard_end - shard_start
    table_end = table_offset + (record_count + 1) * 4
    if table_end > len(blob):
        raise ValueError(
            f"/{resource_set} shard {shard} has a truncated offset table"
        )
    offsets = list(
        struct.unpack_from(f"<{record_count + 1}I", blob, table_offset)
    )
    if offsets != sorted(offsets) or offsets[0] < table_end:
        raise ValueError(
            f"/{resource_set} shard {shard} has invalid record offsets"
        )
    if offsets[-1] > len(blob):
        raise ValueError(
            f"/{resource_set} shard {shard} has an out-of-range record offset"
        )

    local_id = resource_id - shard_start
    record = blob[offsets[local_id] : offsets[local_id + 1]]
    runtime_payload = record[1:] if record else b""
    return record, runtime_payload


def animation_group_for_state(
    weapon_mode: int, state: int, visual_variant: int = 0
) -> int:
    """Return the normal-state base group plus the entity's 51-group offset."""
    if weapon_mode not in (0, 1, 2):
        raise ValueError(f"weapon mode is outside the client's 0..2 tables: {weapon_mode}")
    if visual_variant not in (0, 1):
        raise ValueError(
            f"visual variant is outside the client's 0..1 range: {visual_variant}"
        )

    standing_groups = (43, 0, 22)
    moving_groups = (46, 3, 25)
    if state in (0, 1):
        group = standing_groups[weapon_mode]
    elif state in (2, 3):
        group = moving_groups[weapon_mode]
    elif state == 4:
        group = (43, 6, 28)[weapon_mode]
    elif state in (5, 6):
        group = (16, 16, 38)[weapon_mode]
    elif state in (7, 8):
        group = (43, 20, 42)[weapon_mode]
    else:
        group = standing_groups[weapon_mode]

    return group + visual_variant * 51


def read_csv(path: Path) -> list[dict[str, str]]:
    with path.open("r", encoding="utf-8-sig", newline="") as stream:
        return list(csv.DictReader(stream))


def find_jar(requested: Path | None) -> Path:
    if requested is not None:
        if not requested.is_file():
            raise FileNotFoundError(f"client JAR not found: {requested}")
        return requested
    candidates = sorted(ROOT.parents[1].glob("*.jar"))
    if len(candidates) != 1:
        raise FileNotFoundError(
            "expected one client JAR in the repository root; pass --jar explicitly"
        )
    return candidates[0]


def remove_previous_outputs() -> None:
    manifest_path = OUTPUT_ROOT / "manifest.json"
    if not manifest_path.is_file():
        return

    previous = json.loads(manifest_path.read_text(encoding="utf-8"))
    files: list[str] = []
    for player in previous.get("players", {}).values():
        files.extend(
            value
            for value in (player.get("archive_record_file"), player.get("runtime_payload_file"))
            if value
        )
        mapping_slots = player.get(
            "appearance_mapping_slots", player.get("palette_slots", {})
        )
        for slot in mapping_slots.values():
            files.extend(
                value
                for value in (slot.get("archive_record_file"), slot.get("runtime_payload_file"))
                if value
            )
        for animation in player.get("animations", {}).values():
            files.extend(
                frame["file"]
                for frame in animation.get("frames", [])
                if frame.get("file")
            )

    output_root = OUTPUT_ROOT.resolve()
    for relative_path in files:
        target = (OUTPUT_ROOT / relative_path).resolve()
        if target.is_relative_to(output_root) and target.is_file():
            target.unlink()

    for path in sorted(
        (path for path in OUTPUT_ROOT.rglob("*") if path.is_dir()),
        key=lambda item: len(item.parts),
        reverse=True,
    ):
        try:
            path.rmdir()
        except OSError:
            pass


def index_sprite_animations(
    resource_id: int,
    animation_rows: dict[tuple[int, int], dict[str, str]],
    frame_rows: list[dict[str, str]],
) -> dict[int, dict[str, Any]]:
    rows_by_group: dict[int, list[dict[str, str]]] = {}
    for row in frame_rows:
        if row["sprite_id"] == str(resource_id) and row["animation_group"]:
            rows_by_group.setdefault(int(row["animation_group"]), []).append(row)

    resource_animations = {
        group: row
        for (sprite_id, group), row in animation_rows.items()
        if sprite_id == resource_id
    }
    if (
        not resource_animations
        or min(resource_animations) < 0
        or max(resource_animations) > 101
    ):
        raise ValueError(
            f"sprite {resource_id} has animation groups outside the client's 0..101 range"
        )

    indexed: dict[int, dict[str, Any]] = {}
    for group, animation in sorted(resource_animations.items()):
        rows = sorted(
            rows_by_group.get(group, []),
            key=lambda row: int(row["frame_index"]),
        )
        expected_count = int(animation["frame_count"])
        frame_indices = [int(row["frame_index"]) for row in rows]
        if frame_indices != list(range(expected_count)):
            raise ValueError(
                f"sprite {resource_id} group {group} has frames {frame_indices}; "
                f"expected 0..{expected_count - 1}"
            )
        sheet = animation["output_file"]
        if sheet and not (IMAGE_ROOT / sheet).is_file():
                raise FileNotFoundError(
                    f"decoded animation sheet is missing: {IMAGE_ROOT / sheet}"
                )

        frames = []
        for row in rows:
            if not (IMAGE_ROOT / row["file"]).is_file():
                raise FileNotFoundError(
                    f"decoded source frame is missing: {IMAGE_ROOT / row['file']}"
                )
            frames.append(
                {
                    "frame_index": int(row["frame_index"]),
                    "image": f"../{row['file']}",
                    "delay_ticks": int(row["delay_ticks"]),
                    "delay_ms": int(row["delay_ms"]),
                    "origin_x_px": int(row["origin_x_px"]),
                    "origin_y_px": int(row["origin_y_px"]),
                }
            )

        indexed[group] = {
            "visual_gender_label": "female" if 51 <= group <= 101 else "male_or_shared",
            "frame_count": expected_count,
            "source_total_ticks": int(animation["source_total_ticks"]),
            "source_total_ms": int(animation["source_total_ms"]),
            "animation_sheet": f"../{sheet}" if sheet else "",
            "frames": frames,
        }
    return indexed


def export(jar_path: Path) -> dict[str, Any]:
    inventory_rows = read_csv(ROOT / "resource_inventory.csv")
    sprite_inventory = {
        int(row["resource_id"]): row
        for row in inventory_rows
        if row["resource_set"] == "5"
    }
    appearance_inventory = {
        int(row["resource_id"]): row
        for row in inventory_rows
        if row["resource_set"] == "6"
    }
    animation_rows = {
        (int(row["sprite_id"]), int(row["animation_group"])): row
        for row in read_csv(IMAGE_ROOT / "animation_index.csv")
    }
    frame_rows = read_csv(IMAGE_ROOT / "asset_file_index.csv")
    palette_map_rows = read_csv(IMAGE_ROOT / "sprite_palette_maps.csv")

    player_data: dict[str, Any] = {}
    with zipfile.ZipFile(jar_path) as archive:
        for player_type, resource_id in PLAYER_SPRITES.items():
            record, runtime_payload = read_indexed_record(
                archive, "5", resource_id
            )
            expected = sprite_inventory.get(resource_id)
            if expected is None:
                raise ValueError(f"resource inventory is missing sprite {resource_id}")
            digest = sha256(runtime_payload)
            if (
                int(expected["payload_bytes"]) != len(runtime_payload)
                or expected["sha256"] != digest
            ):
                raise ValueError(
                    f"/5 resource {resource_id} differs from resource_inventory.csv"
                )

            existing_raw = (
                ROOT
                / "raw_resources"
                / "sprites"
                / f"resource_{resource_id:03d}.bin"
            )
            if not existing_raw.is_file() or existing_raw.read_bytes() != runtime_payload:
                raise ValueError(
                    f"/5 resource {resource_id} differs from the existing decoded "
                    f"raw record: {existing_raw}"
                )

            mapping_start, mapping_end = PLAYER_PALETTE_RECORD_RANGES[player_type]
            appearance_mapping_slots: dict[str, Any] = {}
            for palette_record_id in range(mapping_start, mapping_end + 1):
                palette_record, palette_payload = read_indexed_record(
                    archive, "6", palette_record_id
                )
                appearance_expected = appearance_inventory.get(palette_record_id)
                if appearance_expected is None:
                    raise ValueError(
                        f"resource inventory is missing /6 record "
                        f"{palette_record_id}"
                    )
                palette_digest = sha256(palette_payload)
                if (
                    int(appearance_expected["payload_bytes"]) != len(palette_payload)
                    or (
                        appearance_expected["sha256"]
                        and appearance_expected["sha256"] != palette_digest
                    )
                ):
                    raise ValueError(
                        f"/6 resource {palette_record_id} differs from "
                        "resource_inventory.csv"
                    )

                palette_record_path = (
                    ROOT
                    / "raw_resources"
                    / "palettes"
                    / f"resource_{palette_record_id:03d}.bin"
                )
                palette_payload_source = (
                    ROOT
                    / "raw_resources"
                    / "set_6"
                    / f"resource_{palette_record_id:03d}.bin"
                )
                if (
                    not palette_record_path.is_file()
                    or palette_record_path.read_bytes() != palette_record
                ):
                    raise ValueError(
                        f"/6 record {palette_record_id} differs from the "
                        f"existing archive record: {palette_record_path}"
                    )
                if palette_payload_source.is_file():
                    if palette_payload_source.read_bytes() != palette_payload:
                        raise ValueError(
                            f"/6 record {palette_record_id} differs from its "
                            f"existing runtime payload: {palette_payload_source}"
                        )
                elif palette_payload:
                    raise FileNotFoundError(
                        f"existing /6 runtime payload is missing: "
                        f"{palette_payload_source}"
                    )

                slot = palette_record_id - mapping_start
                mapping_rows = [
                    row
                    for row in palette_map_rows
                    if row["resource_record_id"] == str(palette_record_id)
                    and row["sprite_id"] == str(resource_id)
                ]
                mapping_pairs = [
                    {
                        "source_sprite_component_index": int(
                            row["source_sprite_component_index"]
                        ),
                        "target_sprite_component_index": int(
                            row["target_sprite_component_index"]
                        ),
                    }
                    for row in mapping_rows
                    if row["source_sprite_component_index"]
                    and row["target_sprite_component_index"]
                ]
                if not mapping_rows:
                    raise ValueError(
                        f"sprite_palette_maps.csv is missing /6 record "
                        f"{palette_record_id} for sprite {resource_id}"
                    )

                appearance_mapping_slots[str(slot)] = {
                    "resource_record_id": palette_record_id,
                    "active_when_low_display_nibble_matches_slot": slot,
                    "archive_record_file": (
                        f"../../raw_resources/palettes/"
                        f"resource_{palette_record_id:03d}.bin"
                    ),
                    "runtime_payload_file": (
                        f"../../raw_resources/set_6/"
                        f"resource_{palette_record_id:03d}.bin"
                        if palette_payload
                        else None
                    ),
                    "runtime_payload_bytes": len(palette_payload),
                    "runtime_payload_sha256": palette_digest,
                    "mapping_pairs": mapping_pairs,
                }

            animations = index_sprite_animations(
                resource_id,
                animation_rows,
                frame_rows,
            )
            player_data[str(player_type)] = {
                "class_id": player_type,
                "sprite_resource_id": resource_id,
                "archive_entry": f"/5 record {resource_id}",
                "archive_record_bytes": len(record),
                "runtime_payload_file": (
                    f"../../raw_resources/sprites/resource_{resource_id:03d}.bin"
                ),
                "runtime_payload_bytes": len(runtime_payload),
                "runtime_payload_sha256": digest,
                "visual_variant_offset": {
                    "formula": "ai * 51",
                    "variants": {
                        "0": "animation groups 0..50",
                        "1": (
                            "animation groups 51..101 (female labels in the "
                            "existing visual index)"
                        ),
                    },
                    "protocol_bit_semantics": (
                        "The decompiled entity bit is retained as ai; associating "
                        "its 0/1 values with gender is supported by group labels "
                        "but not independently confirmed by a captured packet."
                    ),
                },
                "appearance_mapping_slots": appearance_mapping_slots,
                "weapon_modes": {
                    str(mode): {
                        "display_weapon_mode": mode,
                        "normal_state_to_animation_group": {
                            str(state): animation_group_for_state(mode, state)
                            for state in range(9)
                        },
                        "alternate_state_groups": {
                            "standing": (49, 18, 40)[mode],
                            "moving": (50, 19, 41)[mode],
                        },
                    }
                    for mode in range(3)
                },
                "animation_group_count": len(animations),
                "animations": {
                    str(group): animation for group, animation in animations.items()
                },
            }

    manifest = {
        "format": 1,
        "source_jar": jar_path.name,
        "source_archive": "/5",
        "resource_loading": (
            "2102 creates a cr entity; cr.a(name,id,class,...) loads ck.b(74 + "
            "class). ck reads the indexed /5 record through aq.c(), which "
            "consumes the leading record byte before ao parses the sprite."
        ),
        "weapon_selection": (
            "The 2102 packed entity fields set the current weapon mode; 1264 "
            "updates slot 8 as well. In the slot-8 value, bits 4..7 select one "
            "of three weapon animation modes, bits 8..11 are passed to a "
            "separate character animation state, and bits 0..3 are stored in "
            "cr.n. No use of cr.n to select a /6 mapping was found in the "
            "character render path. /6 component maps exist, but their "
            "selection for player characters is not established here. "
            "cr.D() has further G/X-dependent animation branches; the listed "
            "normal/alternate groups are base tables rather than a runtime "
            "trace for every actor state."
        ),
        "source_symbols": {
            "resource_selection": "decompiled/cr.java:306",
            "entity_creation": "decompiled/cc.java:99-188",
            "initial_appearance_slots": "decompiled/cc.java:170-174, 8976-8999",
            "display_update": "decompiled/cc.java:2851-2858; decompiled/cr.java:2112-2135",
            "action_to_group": (
                "decompiled/cr.java:1427-1494, 2258-2262; "
                "decompiled/h.java:130-139"
            ),
            "appearance_mapping_application": "decompiled/h.java:105-110; decompiled/ao.java:531-550, 764-770; decompiled/bm.java:4685-4715; decompiled/bj.java:10-14",
            "equipment_item_records": "decompiled/cc.java:4357-4383; decompiled/cn.java:571-583",
            "archive_read": "decompiled/ck.java:167-205; decompiled/aq.java:343-350, 365-402",
        },
        "scope": (
            "Every animation group present in the shared decoded index is listed "
            "for each of the three class sprites; available groups are within "
            "0..101 and include male/shared and female-labeled variants. Image "
            "and raw resource paths reference existing shared exports; no duplicate "
            "frames or binaries are copied here. Appearance mappings are "
            "described but not baked into the shared animation PNGs; equipment "
            "item IDs to display values and specific armor semantics remain "
            "unresolved."
        ),
        "players": player_data,
    }
    remove_previous_outputs()
    OUTPUT_ROOT.mkdir(parents=True, exist_ok=True)
    (OUTPUT_ROOT / "manifest.json").write_text(
        json.dumps(manifest, ensure_ascii=False, indent=2) + "\n",
        encoding="utf-8",
    )
    return manifest


def main() -> None:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument(
        "--jar",
        type=Path,
        help="client JAR containing /5 and /5.1.. /5.11 (default: sole root JAR)",
    )
    args = parser.parse_args()
    manifest = export(find_jar(args.jar))
    print(
        f"Exported {len(manifest['players'])} client sprite resources to "
        f"{OUTPUT_ROOT}"
    )
    print(
        f"Indexed "
        f"{sum(player['animation_group_count'] for player in manifest['players'].values())} "
        "animation groups without copying the shared images."
    )


if __name__ == "__main__":
    main()
