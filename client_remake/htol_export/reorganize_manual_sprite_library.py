#!/usr/bin/env python3
"""Apply user-curated sprite names, categories, and duplicate cleanup."""

from __future__ import annotations

import csv
import hashlib
import json
import re
from pathlib import Path

from sprite_library_taxonomy import (
    DUPLICATE_SPRITE_IDS,
    MANUAL_CATEGORIES,
    ROLE_SPRITE_CONFIGS,
    component_filename,
    group_filename,
    is_role_unarmed_group,
    role_animation_details,
    sprite_category,
    sprite_name,
)


ROOT = Path(__file__).resolve().parent
IMAGE_ROOT = ROOT / "images"
LIBRARY_ROOT = IMAGE_ROOT / "sprite_library"
MANIFEST_PATH = LIBRARY_ROOT / "sprite_library.json"
FILE_INDEX_PATH = IMAGE_ROOT / "asset_file_index.csv"


def read_csv(path: Path) -> list[dict[str, str]]:
    with path.open("r", encoding="utf-8-sig", newline="") as stream:
        return list(csv.DictReader(stream))


def write_csv(path: Path, rows: list[dict[str, str]]) -> None:
    if not rows:
        return
    with path.open("w", encoding="utf-8-sig", newline="") as stream:
        writer = csv.DictWriter(stream, fieldnames=list(rows[0]), extrasaction="ignore")
        writer.writeheader()
        writer.writerows(rows)


def map_sprite_ids() -> set[int]:
    ids = {
        int(row["sprite_resource_id"])
        for row in read_csv(IMAGE_ROOT / "map_objects.csv")
        if row["sprite_resource_id"]
    }
    ids.update(
        int(row["tile_atlas_sprite_id"])
        for row in read_csv(IMAGE_ROOT / "map_index.csv")
        if row["tile_atlas_sprite_id"]
    )
    return ids


def index_existing_library(
    manifest: dict,
) -> tuple[dict[str, list[tuple]], dict[int, dict]]:
    paths: dict[str, list[tuple]] = {}
    sprites = {}
    for sprite in manifest.get("sprites", []):
        sprite_id = int(sprite["spriteId"])
        sprites[sprite_id] = sprite
        for group in sprite.get("groups", []):
            paths.setdefault(group["file"], []).append(("group", sprite, group))
        for component in sprite.get("components", []):
            paths.setdefault(component["file"], []).append(
                ("component", sprite, component)
            )
    return paths, sprites


def motion_for_file(file_path: Path, item: tuple) -> str:
    kind, _, metadata = item
    if kind == "component":
        return "static"
    return metadata["motion"]


def target_relative_path(
    sprite_id: int,
    old_relative: str,
    item: tuple,
    map_ids: set[int],
) -> str:
    kind, _, metadata = item
    source_path = Path(old_relative)
    if sprite_id in map_ids:
        category = "map_objects"
        file_name = source_path.name
    else:
        if kind == "group":
            group_id = int(metadata["animationGroup"])
            if is_role_unarmed_group(sprite_id, group_id):
                return (Path("角色") / "空手" / source_path.name).as_posix()
            category = sprite_category(sprite_id, group_id)
            action_count = len(item[1].get("groups", []))
            file_name = group_filename(sprite_id, group_id, action_count)
        else:
            category = sprite_category(sprite_id)
            component_id = int(metadata["componentId"])
            file_name = component_filename(sprite_id, component_id)
    relative_path = Path(category) / motion_for_file(source_path, item)
    if category == "怪物" and kind == "group" and metadata["motion"] == "dynamic":
        relative_path /= str(len(item[1].get("groups", [])))
    return (relative_path / file_name).as_posix()


def move_library_images(
    manifest: dict, map_ids: set[int]
) -> tuple[dict[str, str], int]:
    item_by_path, _ = index_existing_library(manifest)
    item_by_filename = {
        Path(old_path).name: items for old_path, items in item_by_path.items()
    }
    path_mapping: dict[str, str] = {}
    moved = 0
    for image_path in sorted(LIBRARY_ROOT.rglob("*.png")):
        old_relative = image_path.relative_to(LIBRARY_ROOT).as_posix()
        items = item_by_path.get(old_relative) or item_by_filename.get(image_path.name)
        if not items:
            raise ValueError(f"Sprite library PNG is absent from manifest: {old_relative}")
        item = min(
            items,
            key=lambda candidate: (
                int(candidate[1]["spriteId"]),
                int(candidate[2].get("animationGroup", candidate[2].get("componentId", 0))),
            ),
        )
        sprite_id = int(item[1]["spriteId"])
        new_relative = target_relative_path(sprite_id, old_relative, item, map_ids)
        target = LIBRARY_ROOT / Path(new_relative)
        if target.exists() and target.resolve() != image_path.resolve():
            raise FileExistsError(f"Refusing to overwrite existing sprite image: {target}")
        target.parent.mkdir(parents=True, exist_ok=True)
        path_mapping[old_relative] = new_relative
        path_mapping[item[2]["file"]] = new_relative
        if image_path.resolve() != target.resolve():
            image_path.replace(target)
            moved += 1
    return path_mapping, moved


def update_manifest(manifest: dict, path_mapping: dict[str, str], map_ids: set[int]) -> None:
    manifest["sprites"] = [
        sprite
        for sprite in manifest.get("sprites", [])
        if int(sprite["spriteId"]) not in DUPLICATE_SPRITE_IDS
    ]
    for sprite in manifest.get("sprites", []):
        sprite_id = int(sprite["spriteId"])
        is_map_sprite = sprite_id in map_ids
        if is_map_sprite:
            for group in sprite.get("groups", []):
                group["file"] = path_mapping[group["file"]]
            for component in sprite.get("components", []):
                component["file"] = path_mapping[component["file"]]
            sprite["category"] = "map_objects"
            sprite["categoryLabel"] = "地图对象"
            sprite["manualCategory"] = "地图对象"
            continue

        group_categories = set()
        for group in sprite.get("groups", []):
            group_id = int(group["animationGroup"])
            action_count = len(sprite.get("groups", []))
            category = sprite_category(sprite_id, group_id)
            display_name = sprite_name(sprite_id, group_id, action_count)
            group["file"] = path_mapping[group["file"]]
            group["category"] = category
            group["categoryLabel"] = category
            group["displayName"] = display_name
            role_details = role_animation_details(sprite_id, group_id)
            if role_details:
                group["name"] = (
                    f"{role_details['action']}-{role_details['actionNumber']:02d}"
                )
                group["displayName"] = role_details["action"]
                group.update(role_details)
            group_categories.add(category)
        for component in sprite.get("components", []):
            category = sprite_category(sprite_id)
            component["file"] = path_mapping[component["file"]]
            component_categories = category
            group_categories.add(component_categories)

        if not group_categories:
            group_categories.add(sprite_category(sprite_id))
        category = next(iter(group_categories)) if len(group_categories) == 1 else "多分类"
        name = sprite_name(sprite_id)
        sprite["name"] = name
        sprite["manualName"] = name
        sprite["category"] = category
        sprite["categoryLabel"] = category
        sprite["manualCategory"] = category
        sprite["manualCategoryLabel"] = category
        sprite["classificationNote"] = (
            "人工分类和中文名称按用户清单整理；未列出的 sprite 放入未分类。"
        )
    manifest["classificationNote"] = (
        "人工分类和中文别名以用户清单为准；未列出的非地图 sprite 为未分类。"
        "地图对象资源保留原有 map_objects 分类。"
    )
    MANIFEST_PATH.write_text(
        json.dumps(manifest, ensure_ascii=False, indent=2) + "\n",
        encoding="utf-8",
    )


def deduplicate_role_animation_images(manifest: dict) -> int:
    groups_by_hash: dict[str, list[tuple[int, int, dict, str]]] = {}
    for sprite in manifest.get("sprites", []):
        sprite_id = int(sprite["spriteId"])
        if sprite_id not in ROLE_SPRITE_CONFIGS:
            continue
        for group in sprite.get("groups", []):
            group_id = int(group["animationGroup"])
            if is_role_unarmed_group(sprite_id, group_id):
                continue
            relative_path = group["file"]
            path = LIBRARY_ROOT / relative_path
            if not path.is_file():
                raise FileNotFoundError(f"Role animation image is missing: {path}")
            digest = hashlib.sha256(path.read_bytes()).hexdigest()
            groups_by_hash.setdefault(digest, []).append(
                (sprite_id, group_id, group, relative_path)
            )

    duplicate_paths = set()
    for matching_groups in groups_by_hash.values():
        matching_groups.sort(key=lambda item: (item[0], item[1]))
        canonical_path = matching_groups[0][3]
        for _, _, group, relative_path in matching_groups[1:]:
            group["file"] = canonical_path
            if relative_path != canonical_path:
                duplicate_paths.add(relative_path)

    referenced_paths = {
        resource["file"]
        for sprite in manifest.get("sprites", [])
        for resource in (
            *sprite.get("groups", []),
            *sprite.get("components", []),
        )
    }
    removed = 0
    for relative_path in duplicate_paths - referenced_paths:
        path = LIBRARY_ROOT / relative_path
        if path.is_file():
            path.unlink()
            removed += 1
    return removed


def update_file_index(map_ids: set[int]) -> None:
    rows = [
        row
        for row in read_csv(FILE_INDEX_PATH)
        if not (
            row["file_kind"] in {"animation_overview", "component_overview"}
            and row["sprite_id"]
            and int(row["sprite_id"]) not in map_ids
        )
        and not (
            row["sprite_id"]
            and int(row["sprite_id"]) in DUPLICATE_SPRITE_IDS
        )
    ]
    manifest = json.loads(MANIFEST_PATH.read_text(encoding="utf-8"))
    sprite_by_id = {int(item["spriteId"]): item for item in manifest["sprites"]}
    for row in rows:
        if not row["sprite_id"] or int(row["sprite_id"]) in map_ids:
            continue
        sprite_id = int(row["sprite_id"])
        sprite = sprite_by_id.get(sprite_id)
        if not sprite:
            continue
        if row["file_kind"] == "animation_frame":
            group_id = int(row["animation_group"])
            group = next(
                (
                    item
                    for item in sprite["groups"]
                    if int(item["animationGroup"]) == group_id
                ),
                None,
            )
            if group:
                row["file"] = f"sprite_library/{group['file']}"
        elif row["file_kind"] == "sprite_component":
            component_id = int(row["component_id"])
            component = next(
                (
                    item
                    for item in sprite["components"]
                    if int(item["componentId"]) == component_id
                ),
                None,
            )
            if component:
                row["file"] = f"sprite_library/{component['file']}"
    write_csv(FILE_INDEX_PATH, rows)
    catalog_path = IMAGE_ROOT / "asset_catalog.csv"
    catalog = read_csv(catalog_path)
    counts: dict[str, int] = {}
    for row in rows:
        if row["sprite_id"]:
            counts[row["sprite_id"]] = counts.get(row["sprite_id"], 0) + 1
    for row in catalog:
        row["flat_output_files"] = str(counts.get(row["sprite_id"], 0))
    write_csv(catalog_path, catalog)


def update_animation_indexes() -> None:
    manifest = json.loads(MANIFEST_PATH.read_text(encoding="utf-8"))
    groups = {
        (int(sprite["spriteId"]), int(group["animationGroup"])): group
        for sprite in manifest["sprites"]
        for group in sprite.get("groups", [])
    }
    fields = [
        "sprite_id",
        "animation_group",
        "frame_count",
        "source_total_ticks",
        "source_total_ms",
        "animation_key",
        "output_kind",
        "output_file",
    ]
    for name in ("animation_groups.csv", "animation_index.csv"):
        path = IMAGE_ROOT / name
        if not path.is_file():
            continue
        rows = read_csv(path)
        rows = [
            row
            for row in rows
            if int(row["sprite_id"]) not in DUPLICATE_SPRITE_IDS
        ]
        for row in rows:
            group = groups.get(
                (int(row["sprite_id"]), int(row["animation_group"]))
            )
            if group:
                row["output_kind"] = group["outputKind"]
                row["output_file"] = f"sprite_library/{group['file']}"
        with path.open("w", encoding="utf-8-sig", newline="") as stream:
            writer = csv.DictWriter(stream, fieldnames=fields, extrasaction="ignore")
            writer.writeheader()
            writer.writerows(rows)


def remove_duplicate_outputs(map_ids: set[int]) -> int:
    manifest = json.loads(MANIFEST_PATH.read_text(encoding="utf-8"))
    deletable_ids = {
        int(sprite["spriteId"])
        for sprite in manifest.get("sprites", [])
        if int(sprite["spriteId"]) not in map_ids
    }
    duplicate_roots = (
        "character_palette_variants",
        "characters_candidate",
        "effects_candidate",
        "equipment_candidate",
        "items_candidate",
        "monsters_candidate",
        "ui_character_resources",
        "ui_icons_candidate",
        "unclassified",
        "world_objects_candidate",
    )
    removed = 0
    for dirname in duplicate_roots:
        root = IMAGE_ROOT / dirname
        if not root.is_dir():
            continue
        for path in root.rglob("*.png"):
            match = re.search(r"sprite[_-](\d+)", path.name, flags=re.IGNORECASE)
            if match and int(match.group(1)) in deletable_ids:
                path.unlink()
                removed += 1

    atlas_root = IMAGE_ROOT / "phaser" / "atlases"
    if atlas_root.is_dir():
        for path in atlas_root.iterdir():
            match = re.search(r"sprite_(\d+)", path.name, flags=re.IGNORECASE)
            if match and int(match.group(1)) in deletable_ids:
                path.unlink()
                removed += 1
    for dirname in (*duplicate_roots, "phaser/atlases"):
        root = IMAGE_ROOT / dirname
        if not root.is_dir():
            continue
        for directory in sorted(
            (path for path in root.rglob("*") if path.is_dir()),
            key=lambda path: len(path.parts),
            reverse=True,
        ):
            if not any(directory.iterdir()):
                directory.rmdir()
        if not any(root.iterdir()):
            root.rmdir()
    return removed


def main() -> None:
    manifest = json.loads(MANIFEST_PATH.read_text(encoding="utf-8"))
    map_ids = map_sprite_ids()
    duplicate_files = {
        group["file"]
        for sprite in manifest.get("sprites", [])
        if int(sprite["spriteId"]) in DUPLICATE_SPRITE_IDS
        for group in sprite.get("groups", [])
    } | {
        component["file"]
        for sprite in manifest.get("sprites", [])
        if int(sprite["spriteId"]) in DUPLICATE_SPRITE_IDS
        for component in sprite.get("components", [])
    }
    for relative_path in duplicate_files:
        path = LIBRARY_ROOT / relative_path
        if path.is_file():
            path.unlink()
    for directory in sorted(
        (path for path in LIBRARY_ROOT.rglob("*") if path.is_dir()),
        key=lambda path: len(path.parts),
        reverse=True,
    ):
        if not any(directory.iterdir()):
            directory.rmdir()
    path_mapping, moved = move_library_images(manifest, map_ids)
    update_manifest(manifest, path_mapping, map_ids)
    duplicate_role_files_removed = deduplicate_role_animation_images(manifest)
    MANIFEST_PATH.write_text(
        json.dumps(manifest, ensure_ascii=False, indent=2) + "\n",
        encoding="utf-8",
    )
    update_file_index(map_ids)
    update_animation_indexes()
    removed = remove_duplicate_outputs(map_ids)
    print(
        json.dumps(
            {
                "renamed_or_relocated_library_images": moved,
                "manual_sprite_ids": len(MANUAL_CATEGORIES) + 1,
                "map_sprite_ids_preserved": len(map_ids),
                "duplicate_role_files_removed": duplicate_role_files_removed,
                "duplicate_files_removed": removed,
            },
            ensure_ascii=False,
            indent=2,
        )
    )


if __name__ == "__main__":
    main()
