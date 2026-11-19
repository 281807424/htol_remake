#!/usr/bin/env python3
"""Export and document the otherwise unclassified /0 and /4 JAR entries."""

from __future__ import annotations

import csv
import hashlib
import re
import zipfile
from pathlib import Path
from typing import Any

from export_classified_assets import read_indexed_archive, runtime_payload


ROOT = Path(__file__).resolve().parent
JAR_PATH = ROOT.parent.parent / "server_remake" / "htol_local.jar"
RAW_ROOT = ROOT / "raw_resources" / "misc"
TEXT_ROOT = ROOT / "text"
CJK = re.compile(r"[\u3400-\u9fff]")


def write_csv(path: Path, fields: list[str], rows: list[dict[str, Any]]) -> None:
    path.parent.mkdir(parents=True, exist_ok=True)
    with path.open("w", encoding="utf-8-sig", newline="") as stream:
        writer = csv.DictWriter(stream, fieldnames=fields, extrasaction="ignore")
        writer.writeheader()
        writer.writerows(rows)


def class_utf8_constants(blob: bytes) -> list[tuple[int, str]]:
    if blob[:4] != b"\xca\xfe\xba\xbe":
        return []

    constant_count = int.from_bytes(blob[8:10], "big")
    cursor = 10
    constants: list[tuple[int, str]] = []
    index = 1
    fixed_sizes = {
        3: 4,
        4: 4,
        5: 8,
        6: 8,
        7: 2,
        8: 2,
        9: 4,
        10: 4,
        11: 4,
        12: 4,
        15: 3,
        16: 2,
        17: 4,
        18: 4,
        19: 2,
        20: 2,
    }

    while index < constant_count:
        if cursor >= len(blob):
            raise ValueError("truncated class constant pool")
        tag = blob[cursor]
        cursor += 1
        if tag == 1:
            if cursor + 2 > len(blob):
                raise ValueError("truncated class UTF-8 constant length")
            size = int.from_bytes(blob[cursor : cursor + 2], "big")
            cursor += 2
            if cursor + size > len(blob):
                raise ValueError("truncated class UTF-8 constant")
            value = blob[cursor : cursor + size].decode("utf-8", errors="replace")
            constants.append((index, value))
            cursor += size
        elif tag in fixed_sizes:
            cursor += fixed_sizes[tag]
            if tag in (5, 6):
                index += 1
        else:
            raise ValueError(f"unsupported class constant-pool tag {tag}")
        index += 1

    return constants


def main() -> None:
    if not JAR_PATH.exists():
        raise FileNotFoundError(f"HTOL JAR not found: {JAR_PATH}")

    RAW_ROOT.mkdir(parents=True, exist_ok=True)
    TEXT_ROOT.mkdir(parents=True, exist_ok=True)
    analysis_rows: list[dict[str, Any]] = []
    class_string_rows: list[dict[str, Any]] = []

    with zipfile.ZipFile(JAR_PATH) as archive:
        entry_0 = archive.read("0")
        if entry_0 != b"\x00":
            raise ValueError(f"unexpected /0 contents: {entry_0.hex()}")
        (RAW_ROOT / "entry_0.bin").write_bytes(entry_0)
        analysis_rows.append(
            {
                "jar_entry": "/0",
                "entry_bytes": len(entry_0),
                "entry_sha256": hashlib.sha256(entry_0).hexdigest(),
                "container_format": "not an indexed archive",
                "shards": "",
                "record_id": "",
                "stored_record_bytes": "",
                "stored_record_hex": "",
                "runtime_payload_bytes": "",
                "runtime_payload_hex": "",
                "interpretation": "single 00 sentinel byte; no caller found in decompiled code",
            }
        )

        entry_4 = archive.read("4")
        records = read_indexed_archive(entry_4)
        if records != {0: b"\x00"}:
            raise ValueError(f"unexpected /4 archive records: {records!r}")
        (RAW_ROOT / "entry_4.bin").write_bytes(entry_4)
        for record_id, stored_record in sorted(records.items()):
            payload = runtime_payload(stored_record)
            (RAW_ROOT / f"entry_4_record_{record_id:03d}.bin").write_bytes(
                stored_record
            )
            analysis_rows.append(
                {
                    "jar_entry": "/4",
                    "entry_bytes": len(entry_4),
                    "entry_sha256": hashlib.sha256(entry_4).hexdigest(),
                    "container_format": "indexed archive: little-endian header/offsets",
                    "shards": 1,
                    "record_id": record_id,
                    "stored_record_bytes": len(stored_record),
                    "stored_record_hex": stored_record.hex(" "),
                    "runtime_payload_bytes": len(payload),
                    "runtime_payload_hex": payload.hex(" "),
                    "interpretation": (
                        "one stored 00 byte; aq.c() consumes it as the record marker, "
                        "leaving an empty payload; no /4 caller found"
                    ),
                }
            )

        for member in archive.namelist():
            if not member.endswith(".class"):
                continue
            for constant_index, value in class_utf8_constants(archive.read(member)):
                if CJK.search(value):
                    class_string_rows.append(
                        {
                            "class_file": member,
                            "constant_pool_index": constant_index,
                            "text": value,
                        }
                    )

    write_csv(
        ROOT / "resource_0_4_analysis.csv",
        [
            "jar_entry",
            "entry_bytes",
            "entry_sha256",
            "container_format",
            "shards",
            "record_id",
            "stored_record_bytes",
            "stored_record_hex",
            "runtime_payload_bytes",
            "runtime_payload_hex",
            "interpretation",
        ],
        analysis_rows,
    )
    write_csv(
        TEXT_ROOT / "class_strings.csv",
        ["class_file", "constant_pool_index", "text"],
        class_string_rows,
    )
    print(
        f"Analyzed /0 and /4; found {len(class_string_rows)} CJK class constants."
    )


if __name__ == "__main__":
    main()
