import json
import math
from collections import defaultdict
from pathlib import Path


ROOT = Path(__file__).resolve().parent
PROJECT_ROOT = ROOT.parent
WORLD_FILE = ROOT / "world_map.json"
CATALOG_FILE = ROOT / "map_catalog.json"
PLACEMENTS_FILE = PROJECT_ROOT / "map_placement_editor" / "map_placements.json"

CARD_WIDTH = 88
CARD_HEIGHT = 64
WIDTH = 2750
HEIGHT = 2000
MATCHING_INFINITY = 1_000_000
MAX_LINK_DISTANCE = 220
CITY_ROLES = {
    49: ("玄武国", "主城", "玄武国主城"),
    140: ("玄武国", "主城", "玄武国主城"),
    75: ("邪血国", "主城", "邪血国主城"),
    83: ("邪血国", "主城", "邪血国主城"),
    13: ("玄月国", "主城", "玄月国主城"),
    110: ("玄月国", "主城", "玄月国主城"),
}
FORMER_XUANWU_CITIES = {43, 48}
SEA_MAP_IDS = {111, 112, 113, 127, 128, 129, 130, 131, 132, 139}
DIRECTIONS = {
    "东": (1.0, 0.0),
    "东北": (math.sqrt(0.5), -math.sqrt(0.5)),
    "北": (0.0, -1.0),
    "西北": (-math.sqrt(0.5), -math.sqrt(0.5)),
    "西": (-1.0, 0.0),
    "西南": (-math.sqrt(0.5), math.sqrt(0.5)),
    "南": (0.0, 1.0),
    "东南": (math.sqrt(0.5), math.sqrt(0.5)),
}


def read_json(path):
    return json.loads(path.read_text(encoding="utf-8"))


def write_json(path, value):
    path.write_text(json.dumps(value, ensure_ascii=False, indent=2) + "\n", encoding="utf-8")


def normalized(x, y):
    length = math.hypot(x, y)
    if length < 1e-9:
        return 0.0, 0.0
    return x / length, y / length


def map_target(map_record, catalog_record, index, group_size, map_directions):
    map_id = map_record["mapId"]
    region = map_record["region"]
    category = catalog_record["sourceCategory"]
    angle = index * 2.399963229728653 + (map_id % 19) * 0.071
    radius = math.sqrt((index + 0.5) / max(group_size, 1))
    wobble = 0.88 + (map_id % 7) * 0.035

    if map_id in CITY_ROLES:
        centers = {
            49: (1320, 760),
            140: (900, 440),
            75: (850, 1050),
            83: (310, 1290),
            13: (1660, 960),
            110: (2310, 1640),
        }
        return centers[map_id]

    if map_id in FORMER_XUANWU_CITIES:
        return (1280 + (map_id - 43) * 32, 875 + (map_id - 43) * 24)

    if region == "皇城":
        center, spread = (1320, 930), (340, 250)
    elif region == "玄武国":
        if "熔岩" in category:
            center, spread = (790, 235), (430, 155)
        elif "粉树" in category:
            center, spread = (1590, 600), (340, 190)
        elif "绿树" in category:
            center, spread = (890, 575), (280, 220)
        else:
            center, spread = (1190, 360), (460, 250)
    elif region == "邪血国":
        if category == "海边":
            center, spread = (760, 1665), (410, 115)
        elif "黄树" in category:
            center, spread = (935, 820), (260, 185)
        elif "粉树" in category:
            center, spread = (1040, 1260), (250, 260)
        else:
            center, spread = (475, 1160), (390, 390)
    elif region == "玄月国":
        if category == "海边":
            center, spread = (2280, 1300), (175, 470)
        elif "粉树" in category:
            center, spread = (1810, 1010), (380, 385)
        else:
            center, spread = (1900, 1120), (340, 370)
    else:
        raise ValueError(f"Map {map_id} has an unknown region: {region}")

    target_x = center[0] + math.cos(angle) * radius * spread[0] * wobble
    target_y = center[1] + math.sin(angle) * radius * spread[1] * wobble
    missing_x = sum(vector[0] for direction, vector in DIRECTIONS.items() if direction not in map_directions)
    missing_y = sum(vector[1] for direction, vector in DIRECTIONS.items() if direction not in map_directions)
    missing_x, missing_y = normalized(missing_x, missing_y)
    return target_x + missing_x * 105, target_y + missing_y * 105


def direction_cost(left, right, positions):
    left_x, left_y = DIRECTIONS[left["direction"]]
    right_x, right_y = DIRECTIONS[right["direction"]]
    direction_penalty = 100 * (1 + left_x * right_x + left_y * right_y)
    left_position = positions[left["mapId"]]
    right_position = positions[right["mapId"]]
    dx, dy = normalized(right_position[0] - left_position[0], right_position[1] - left_position[1])
    alignment_penalty = 12 * (
        2 - dx * left_x - dy * left_y + dx * right_x + dy * right_y
    )
    distance = math.hypot(
        right_position[0] - left_position[0],
        right_position[1] - left_position[1],
    )
    return direction_penalty + alignment_penalty + distance * 0.5


def hungarian(costs):
    size = len(costs)
    potentials_left = [0.0] * (size + 1)
    potentials_right = [0.0] * (size + 1)
    matched_row = [0] * (size + 1)
    previous_column = [0] * (size + 1)

    for row in range(1, size + 1):
        matched_row[0] = row
        column0 = 0
        minimum = [float("inf")] * (size + 1)
        used = [False] * (size + 1)
        while True:
            used[column0] = True
            row0 = matched_row[column0]
            delta = float("inf")
            column1 = 0
            for column in range(1, size + 1):
                if used[column]:
                    continue
                reduced = (
                    costs[row0 - 1][column - 1]
                    - potentials_left[row0]
                    - potentials_right[column]
                )
                if reduced < minimum[column]:
                    minimum[column] = reduced
                    previous_column[column] = column0
                if minimum[column] < delta:
                    delta = minimum[column]
                    column1 = column
            if not math.isfinite(delta):
                raise ValueError("传送点无法组成完整的一对一匹配。")
            for column in range(size + 1):
                if used[column]:
                    potentials_left[matched_row[column]] += delta
                    potentials_right[column] -= delta
                else:
                    minimum[column] -= delta
            column0 = column1
            if matched_row[column0] == 0:
                break
        while True:
            column1 = previous_column[column0]
            matched_row[column0] = matched_row[column1]
            column0 = column1
            if column0 == 0:
                break

    assignment = [0] * size
    for column in range(1, size + 1):
        assignment[matched_row[column] - 1] = column - 1
    return assignment


def bipartite_pairs(left_items, right_items, positions):
    size = max(len(left_items), len(right_items))
    dummy_cost = MATCHING_INFINITY / 2
    costs = []
    for row in range(size):
        cost_row = []
        for column in range(size):
            if row >= len(left_items) or column >= len(right_items):
                cost_row.append(0 if row >= len(left_items) else dummy_cost)
                continue
            left = left_items[row]
            right = right_items[column]
            forbidden = (
                left["mapId"] == right["mapId"]
                or {left["mapId"], right["mapId"]} == {13, 110}
            )
            cost_row.append(
                MATCHING_INFINITY
                if forbidden
                else direction_cost(left, right, positions)
            )
        costs.append(cost_row)

    assignment = hungarian(costs)
    pairs = []
    for row, column in enumerate(assignment):
        if row >= len(left_items) or column >= len(right_items):
            continue
        if costs[row][column] >= MATCHING_INFINITY:
            raise ValueError("找不到满足跨地图且玄月国两张主城不直连的传送点匹配。")
        pairs.append((left_items[row], right_items[column]))
    if len(pairs) != min(len(left_items), len(right_items)):
        raise ValueError("传送点之间无法完成最大数量的一对一匹配。")
    return pairs


def teleport_assignment(teleports, positions):
    by_direction = defaultdict(list)
    for teleport in teleports:
        by_direction[teleport["direction"]].append(teleport)

    pairs = []
    used = set()
    for direction, opposite in (
        ("东", "西"),
        ("东北", "西南"),
        ("北", "南"),
        ("西北", "东南"),
    ):
        left_items = sorted(by_direction[direction], key=lambda item: (item["mapId"], item["id"]))
        right_items = sorted(by_direction[opposite], key=lambda item: (item["mapId"], item["id"]))
        directional_pairs = bipartite_pairs(left_items, right_items, positions)
        pairs.extend(directional_pairs)
        used.update(item["id"] for pair in directional_pairs for item in pair)

    remaining = [item for item in teleports if item["id"] not in used]
    if len(remaining) % 2:
        raise ValueError("传送点总数为奇数，不能将全部传送点两两配对。")
    remaining.sort(key=lambda item: (item["mapId"], item["direction"], item["id"]))
    remainder_pairs = bipartite_pairs(remaining[::2], remaining[1::2], positions)
    if len(remainder_pairs) * 2 != len(remaining):
        raise ValueError("剩余传送点未能全部配对。")
    pairs.extend(remainder_pairs)
    return pairs


def teleport_scene_position(map_record, catalog_record, teleport):
    preview_inset_x = 3 if map_record["kind"] == "野外地图" else 4
    preview_inset_y = 2 if map_record["kind"] == "野外地图" else 3
    preview_width = map_record["width"] - preview_inset_x * 2
    preview_height = 34
    image_width = catalog_record["widthCells"] * catalog_record["renderCellPx"]
    image_height = catalog_record["heightCells"] * catalog_record["renderCellPx"]
    scale = min(preview_width / image_width, preview_height / image_height)
    rendered_width = image_width * scale
    rendered_height = image_height * scale
    return (
        map_record["x"] + preview_inset_x
        + (preview_width - rendered_width) / 2 + teleport["x"] * scale,
        map_record["y"] + preview_inset_y
        + (preview_height - rendered_height) / 2 + teleport["y"] * scale,
    )


def segment_intersects_map(start, end, map_record):
    delta_x = end[0] - start[0]
    delta_y = end[1] - start[1]
    minimum = 0.0
    maximum = 1.0
    for direction, distance in (
        (-delta_x, start[0] - map_record["x"]),
        (delta_x, map_record["x"] + map_record["width"] - start[0]),
        (-delta_y, start[1] - map_record["y"]),
        (delta_y, map_record["y"] + map_record["height"] - start[1]),
    ):
        if abs(direction) < 1e-9:
            if distance < 0:
                return False
            continue
        intersection = distance / direction
        if direction < 0:
            minimum = max(minimum, intersection)
        else:
            maximum = min(maximum, intersection)
    return minimum <= maximum and maximum > 0.01 and minimum < 0.99


def nearby_teleport_pairs(teleports, maps_by_id, catalog_by_id):
    teleports_by_id = {teleport["id"]: teleport for teleport in teleports}
    points = {
        teleport["id"]: teleport_scene_position(
            maps_by_id[teleport["mapId"]],
            catalog_by_id[teleport["mapId"]],
            teleport,
        )
        for teleport in teleports
    }
    candidates = []
    for index, left in enumerate(teleports):
        left_map = maps_by_id[left["mapId"]]
        left_point = points[left["id"]]
        for right in teleports[index + 1:]:
            if left["mapId"] == right["mapId"]:
                continue
            if {left["mapId"], right["mapId"]} == {13, 110}:
                continue
            right_map = maps_by_id[right["mapId"]]
            right_point = points[right["id"]]
            distance = math.dist(left_point, right_point)
            if distance > MAX_LINK_DISTANCE:
                continue
            if any(
                segment_intersects_map(left_point, right_point, obstacle)
                for map_id, obstacle in maps_by_id.items()
                if map_id not in (left["mapId"], right["mapId"])
            ):
                continue
            dot = (
                DIRECTIONS[left["direction"]][0] * DIRECTIONS[right["direction"]][0]
                + DIRECTIONS[left["direction"]][1] * DIRECTIONS[right["direction"]][1]
            )
            score = (1 + dot, distance, left["id"], right["id"])
            map_pair = tuple(sorted((left["mapId"], right["mapId"])))
            candidates.append((*score, map_pair))

    candidates.sort()
    candidates_by_teleport = defaultdict(list)
    for candidate in candidates:
        candidates_by_teleport[candidate[2]].append(candidate)
        candidates_by_teleport[candidate[3]].append(candidate)

    matched_teleports = set()
    matched_map_pairs = set()
    selected = []
    for candidate in candidates:
        _, _, left_id, right_id, map_pair = candidate
        if (
            left_id in matched_teleports
            or right_id in matched_teleports
            or map_pair in matched_map_pairs
        ):
            continue
        matched_teleports.update((left_id, right_id))
        matched_map_pairs.add(map_pair)
        selected.append(candidate)

    while True:
        unconnected = {
            teleport["id"] for teleport in teleports
        } - matched_teleports
        replacement = None
        for index, current in enumerate(selected):
            left_id, right_id, old_map_pair = current[2], current[3], current[4]
            occupied_map_pairs = matched_map_pairs - {old_map_pair}
            left_options = [
                candidate
                for candidate in candidates_by_teleport[left_id]
                if (
                    candidate[3] if candidate[2] == left_id else candidate[2]
                ) in unconnected
                and candidate[4] not in occupied_map_pairs
            ]
            right_options = [
                candidate
                for candidate in candidates_by_teleport[right_id]
                if (
                    candidate[3] if candidate[2] == right_id else candidate[2]
                ) in unconnected
                and candidate[4] not in occupied_map_pairs
            ]
            for left_candidate in left_options:
                left_unconnected = (
                    left_candidate[3]
                    if left_candidate[2] == left_id
                    else left_candidate[2]
                )
                for right_candidate in right_options:
                    right_unconnected = (
                        right_candidate[3]
                        if right_candidate[2] == right_id
                        else right_candidate[2]
                    )
                    if (
                        left_unconnected != right_unconnected
                        and left_candidate[4] != right_candidate[4]
                    ):
                        replacement = (
                            index,
                            current,
                            left_candidate,
                            right_candidate,
                        )
                        break
                if replacement:
                    break
            if replacement:
                break

        if not replacement:
            break
        index, current, left_candidate, right_candidate = replacement
        matched_map_pairs.remove(current[4])
        matched_map_pairs.update((left_candidate[4], right_candidate[4]))
        matched_teleports.difference_update((current[2], current[3]))
        matched_teleports.update(
            (
                left_candidate[2],
                left_candidate[3],
                right_candidate[2],
                right_candidate[3],
            )
        )
        selected[index:index + 1] = [left_candidate, right_candidate]

    return [
        (teleports_by_id[candidate[2]], teleports_by_id[candidate[3]])
        for candidate in selected
    ]





def graph_components(map_ids, pairs):
    adjacency = {map_id: set() for map_id in map_ids}
    for left, right in pairs:
        adjacency[left["mapId"]].add(right["mapId"])
        adjacency[right["mapId"]].add(left["mapId"])

    components = []
    remaining = set(map_ids)
    while remaining:
        start = min(remaining)
        pending = [start]
        component = set()
        while pending:
            current = pending.pop()
            if current in component:
                continue
            component.add(current)
            pending.extend(adjacency[current] - component)
        components.append(component)
        remaining -= component
    return components


def connect_components(map_ids, pairs, positions):
    while True:
        components = graph_components(map_ids, pairs)
        if len(components) == 1:
            return
        component_by_map = {
            map_id: index
            for index, component in enumerate(components)
            for map_id in component
        }
        edge_groups = defaultdict(list)
        for index, pair in enumerate(pairs):
            edge_groups[component_by_map[pair[0]["mapId"]]].append(index)

        best = None
        for first_component in range(len(components)):
            for second_component in range(first_component + 1, len(components)):
                for first_index in edge_groups[first_component]:
                    first_left, first_right = pairs[first_index]
                    for second_index in edge_groups[second_component]:
                        second_left, second_right = pairs[second_index]
                        old_cost = (
                            direction_cost(first_left, first_right, positions)
                            + direction_cost(second_left, second_right, positions)
                        )
                        for new_first, new_second in (
                            ((first_left, second_left), (first_right, second_right)),
                            ((first_left, second_right), (first_right, second_left)),
                        ):
                            if any(
                                {left["mapId"], right["mapId"]} == {13, 110}
                                for left, right in (new_first, new_second)
                            ):
                                continue
                            new_cost = (
                                direction_cost(*new_first, positions)
                                + direction_cost(*new_second, positions)
                            )
                            candidate = (new_cost - old_cost, first_index, second_index, new_first, new_second)
                            if best is None or candidate[0] < best[0]:
                                best = candidate
        if best is None:
            raise ValueError("无法在保持传送点唯一占用的同时连接全部地图区域。")
        _, first_index, second_index, new_first, new_second = best
        pairs[first_index] = new_first
        pairs[second_index] = new_second


def relax_positions(map_ids, pairs, targets):
    positions = {map_id: [*targets[map_id]] for map_id in map_ids}
    for _ in range(900):
        forces = {map_id: [0.0, 0.0] for map_id in map_ids}
        for map_id in map_ids:
            forces[map_id][0] += (targets[map_id][0] - positions[map_id][0]) * 0.003
            forces[map_id][1] += (targets[map_id][1] - positions[map_id][1]) * 0.003

        for left, right in pairs:
            left_x, left_y = DIRECTIONS[left["direction"]]
            right_x, right_y = DIRECTIONS[right["direction"]]
            desired_x, desired_y = normalized(left_x - right_x, left_y - right_y)
            if desired_x == 0 and desired_y == 0:
                desired_x, desired_y = left_x, left_y
            left_position = positions[left["mapId"]]
            right_position = positions[right["mapId"]]
            error_x = right_position[0] - left_position[0] - desired_x * 138
            error_y = right_position[1] - left_position[1] - desired_y * 138
            strength = 0.035
            forces[left["mapId"]][0] += error_x * strength
            forces[left["mapId"]][1] += error_y * strength
            forces[right["mapId"]][0] -= error_x * strength
            forces[right["mapId"]][1] -= error_y * strength

        for index, first_id in enumerate(map_ids):
            first_x, first_y = positions[first_id]
            for second_id in map_ids[index + 1:]:
                second_x, second_y = positions[second_id]
                dx = second_x - first_x
                dy = second_y - first_y
                overlap_x = CARD_WIDTH + 18 - abs(dx)
                overlap_y = CARD_HEIGHT + 18 - abs(dy)
                if overlap_x <= 0 or overlap_y <= 0:
                    continue
                if overlap_x < overlap_y:
                    direction = 1 if dx >= 0 else -1
                    force = overlap_x * 0.055 * direction
                    forces[first_id][0] -= force
                    forces[second_id][0] += force
                else:
                    direction = 1 if dy >= 0 else -1
                    force = overlap_y * 0.055 * direction
                    forces[first_id][1] -= force
                    forces[second_id][1] += force

        for map_id in map_ids:
            positions[map_id][0] = min(WIDTH - CARD_WIDTH - 35, max(35, positions[map_id][0] + forces[map_id][0]))
            positions[map_id][1] = min(HEIGHT - CARD_HEIGHT - 35, max(35, positions[map_id][1] + forces[map_id][1]))
    return {
        map_id: [round(point[0]), round(point[1])]
        for map_id, point in positions.items()
    }


def separate_overlapping_nodes(map_ids, positions):
    points = {map_id: [float(value) for value in positions[map_id]] for map_id in map_ids}
    for _ in range(1600):
        collisions = 0
        for index, first_id in enumerate(map_ids):
            first_x, first_y = points[first_id]
            for second_id in map_ids[index + 1:]:
                second_x, second_y = points[second_id]
                dx = second_x - first_x
                dy = second_y - first_y
                overlap_x = CARD_WIDTH + 12 - abs(dx)
                overlap_y = CARD_HEIGHT + 12 - abs(dy)
                if overlap_x <= 0 or overlap_y <= 0:
                    continue
                collisions += 1
                if overlap_x < overlap_y:
                    sign = 1 if dx > 0 or (dx == 0 and first_id < second_id) else -1
                    shift = overlap_x / 2 + 0.5
                    points[first_id][0] -= sign * shift
                    points[second_id][0] += sign * shift
                else:
                    sign = 1 if dy > 0 or (dy == 0 and first_id < second_id) else -1
                    shift = overlap_y / 2 + 0.5
                    points[first_id][1] -= sign * shift
                    points[second_id][1] += sign * shift
                first_x, first_y = points[first_id]
        if collisions == 0:
            break

        for map_id in map_ids:
            points[map_id][0] = min(WIDTH - CARD_WIDTH - 35, max(35, points[map_id][0]))
            points[map_id][1] = min(HEIGHT - CARD_HEIGHT - 35, max(35, points[map_id][1]))

    result = {
        map_id: [round(point[0]), round(point[1])]
        for map_id, point in points.items()
    }
    for index, first_id in enumerate(map_ids):
        for second_id in map_ids[index + 1:]:
            dx = abs(result[first_id][0] - result[second_id][0])
            dy = abs(result[first_id][1] - result[second_id][1])
            if dx < CARD_WIDTH and dy < CARD_HEIGHT:
                raise ValueError(f"地图卡片仍有重叠：{first_id} 与 {second_id}")
    return result


def assign_map_identity(world, catalog_by_id):
    by_id = {map_record["mapId"]: map_record for map_record in world["maps"]}
    field_ids = {
        map_id
        for map_id, catalog_record in catalog_by_id.items()
        if catalog_record["includedInWorld"]
        and catalog_record["sourceCategory"] != "三国主城3x2"
    }
    regional_map_ids = {
        "玄武国": {
            map_id for map_id in field_ids
            if catalog_by_id[map_id]["sourceCategory"].startswith("黄地")
        } | {49, 140},
        "邪血国": {
            map_id for map_id in field_ids
            if catalog_by_id[map_id]["sourceCategory"].startswith("深绿地")
        } | {75, 83},
        "玄月国": {
            map_id for map_id in field_ids
            if catalog_by_id[map_id]["sourceCategory"].startswith("绿地")
        } | SEA_MAP_IDS | {13, 110},
        "皇城": {
            map_id for map_id, catalog_record in catalog_by_id.items()
            if catalog_record["includedInWorld"]
            and catalog_record["sourceCategory"] == "皇城"
            and map_id not in {49, 140}
        } | FORMER_XUANWU_CITIES,
    }
    region_by_map = {
        map_id: region
        for region, map_ids in regional_map_ids.items()
        for map_id in map_ids
    }
    if len(region_by_map) != len(by_id) or set(region_by_map) != set(by_id):
        raise ValueError("地图地貌分类未能完整覆盖世界地图或存在重复归属。")

    for map_id in FORMER_XUANWU_CITIES:
        map_record = by_id[map_id]
        map_record.update({
            "name": "皇城野外地图",
            "region": "皇城",
            "nation": None,
            "kind": "野外地图",
            "category": "皇城",
        })
    for map_id, (nation, role, name) in CITY_ROLES.items():
        map_record = by_id[map_id]
        map_record.update({
            "name": name,
            "region": nation,
            "nation": nation,
            "kind": role,
            "category": "三国主城3x2",
        })

    for map_record in world["maps"]:
        map_id = map_record["mapId"]
        catalog_record = catalog_by_id[map_id]
        region = region_by_map[map_id]
        is_city = map_id in CITY_ROLES
        map_record["region"] = region
        if map_id not in FORMER_XUANWU_CITIES and not is_city:
            map_record["nation"] = region if region != "皇城" else None
            map_record["kind"] = "野外地图"
            map_record["name"] = f"{region}野外地图"
            map_record["category"] = catalog_record["sourceCategory"]
        map_record["image"] = catalog_record["image"]
        map_record["hasBuildings"] = catalog_record["hasBuildings"] if not is_city else False
        map_record["buildingObjectCount"] = (
            catalog_record["buildingObjectCount"] if not is_city else 0
        )
        map_record.pop("gridCell", None)
        map_record["teleports"] = []

    counts = defaultdict(int)
    for map_record in world["maps"]:
        counts[map_record["region"]] += 1
    expected = {"玄武国": 45, "邪血国": 27, "玄月国": 36, "皇城": 16}
    if dict(counts) != expected:
        raise ValueError(f"区域地图数量不符合预期：{dict(counts)}")


def main():
    world = read_json(WORLD_FILE)
    catalog = read_json(CATALOG_FILE)
    placements = read_json(PLACEMENTS_FILE)
    catalog_by_id = {item["mapId"]: item for item in catalog["maps"]}
    placements_by_id = {item["mapId"]: item for item in placements["maps"]}
    map_by_id = {item["mapId"]: item for item in world["maps"]}
    if len(map_by_id) != 124 or len(placements_by_id) != len(map_by_id):
        raise ValueError("世界地图与传送点布置文件的地图数量不匹配。")

    assign_map_identity(world, catalog_by_id)
    world["title"] = "三国与中央皇城世界地图"
    world["regions"] = [
        {"id": "玄武国", "label": "玄武国", "description": "北部黄地与荒野。", "color": "#c99a37"},
        {"id": "邪血国", "label": "邪血国", "description": "西南深绿林地与沿海地带。", "color": "#17365d"},
        {"id": "玄月国", "label": "玄月国", "description": "东南绿地粉树与海岸。", "color": "#a8d5a2"},
        {"id": "皇城", "label": "中央皇城", "description": "位于三国之间的中央皇城。", "color": "#4682b4"},
    ]
    teleports = []
    directions_by_map = defaultdict(set)
    for map_id, placement in placements_by_id.items():
        if map_id not in map_by_id:
            raise ValueError(f"传送点布置文件含世界地图之外的地图：{map_id}")
        for teleport in placement["teleports"]:
            teleports.append({**teleport, "mapId": map_id})
            directions_by_map[map_id].add(teleport["direction"])
    teleports.sort(key=lambda item: (item["mapId"], item["id"]))

    groups = defaultdict(list)
    for map_record in world["maps"]:
        map_id = map_record["mapId"]
        groups[(map_record["region"], catalog_by_id[map_id]["sourceCategory"])].append(map_record)

    targets = {}
    for group in groups.values():
        ordered = sorted(group, key=lambda item: item["mapId"])
        for index, map_record in enumerate(ordered):
            targets[map_record["mapId"]] = map_target(
                map_record,
                catalog_by_id[map_record["mapId"]],
                index,
                len(ordered),
                directions_by_map[map_record["mapId"]],
            )

    if len(teleports) % 2:
        raise ValueError("传送点数量为奇数，无法让每个点都连到另一个点。")

    pairs = teleport_assignment(teleports, targets)
    connect_components(map_by_id, pairs, targets)
    map_ids = list(map_by_id)
    final_positions = relax_positions(map_ids, pairs, targets)
    final_positions = separate_overlapping_nodes(map_ids, final_positions)
    for map_id, map_record in map_by_id.items():
        map_record["x"], map_record["y"] = final_positions[map_id]

    pairs = nearby_teleport_pairs(teleports, map_by_id, catalog_by_id)
    unmatched_count = len(teleports) - len(pairs) * 2
    world["layout"].update({
        "width": WIDTH,
        "height": HEIGHT,
        "cardWidth": CARD_WIDTH,
        "cardHeight": CARD_HEIGHT,
        "maxTeleportsPerMap": max(len(placements_by_id[map_id]["teleports"]) for map_id in placements_by_id),
    })
    world["connections"] = [
        {
            "id": f"tp-{index + 1:03d}",
            "fromMapId": left["mapId"],
            "toMapId": right["mapId"],
            "fromTeleportId": left["id"],
            "toTeleportId": right["id"],
        }
        for index, (left, right) in enumerate(pairs)
    ]
    map_id_set = set(map_by_id)
    regions_by_id = {map_record["mapId"]: map_record["region"] for map_record in world["maps"]}
    world["summary"] = {
        "includedMapCount": len(world["maps"]),
        "countryMapCounts": {
            nation: sum(item["region"] == nation for item in world["maps"])
            for nation in ("玄武国", "邪血国", "玄月国")
        },
        "imperialCityMapCount": sum(item["region"] == "皇城" for item in world["maps"]),
        "connectionCount": len(world["connections"]),
        "unconnectedTeleportCount": unmatched_count,
        "connectedMapComponentCount": len(graph_components(map_id_set, pairs)),
        "maximumTeleportsOnAnyMap": max(
            len(placements_by_id[map_id]["teleports"]) for map_id in placements_by_id
        ),
        "interCountryBorderConnections": sum(
            regions_by_id[connection["fromMapId"]] in {"玄武国", "邪血国", "玄月国"}
            and regions_by_id[connection["toMapId"]] in {"玄武国", "邪血国", "玄月国"}
            and regions_by_id[connection["fromMapId"]] != regions_by_id[connection["toMapId"]]
            for connection in world["connections"]
        ),
        "buildingOutpostCounts": {
            nation: sum(
                map_record["region"] == nation and map_record["hasBuildings"]
                for map_record in world["maps"]
            )
            for nation in ("玄武国", "邪血国", "玄月国")
        },
    }
    world["notes"] = [
        "地图由地貌/植被分区、传送点方向与关系共同布置；坐标采用自由布局而非网格。",
        "传送点连线直接连接两个传送点；地图间距不限制，连线不得穿过其他地图；未连接传送点数量不设上限。",
        "玄月国海岛主城与另一张主城保持分离且不直连。",
        "传送点原始布置读取 map_placement_editor/map_placements.json；此工具不会修改该文件。",
    ]

    used_teleports = set()
    used_map_pairs = set()
    for connection in world["connections"]:
        if connection["fromMapId"] == connection["toMapId"]:
            raise ValueError(f"连接不能指向同一地图：{connection}")
        map_pair = tuple(sorted((connection["fromMapId"], connection["toMapId"])))
        if map_pair in used_map_pairs:
            raise ValueError(
                f"同一对地图不能重复连接：{connection['fromMapId']} 与 {connection['toMapId']}"
            )
        used_map_pairs.add(map_pair)
        for key in ("fromTeleportId", "toTeleportId"):
            teleport_id = connection[key]
            if teleport_id in used_teleports:
                raise ValueError(f"传送点重复连接：{teleport_id}")
            used_teleports.add(teleport_id)
    if len(teleports) - len(used_teleports) != unmatched_count:
        raise ValueError("生成的连接数量与未连接传送点数量不一致。")
    teleport_by_id = {teleport["id"]: teleport for teleport in teleports}
    for connection in world["connections"]:
        left = teleport_by_id[connection["fromTeleportId"]]
        right = teleport_by_id[connection["toTeleportId"]]
        left_map = map_by_id[left["mapId"]]
        right_map = map_by_id[right["mapId"]]
        start = teleport_scene_position(left_map, catalog_by_id[left_map["mapId"]], left)
        end = teleport_scene_position(right_map, catalog_by_id[right_map["mapId"]], right)
        if any(
            segment_intersects_map(start, end, obstacle)
            for map_id, obstacle in map_by_id.items()
            if map_id not in (left_map["mapId"], right_map["mapId"])
        ):
            raise ValueError(
                f"传送点连线穿过其他地图：{left_map['mapId']} 与 {right_map['mapId']}"
            )
    if {connection["fromMapId"] for connection in world["connections"]} - map_id_set:
        raise ValueError("连接引用了未知地图。")
    if any(
        {connection["fromMapId"], connection["toMapId"]} == {13, 110}
        for connection in world["connections"]
    ):
        raise ValueError("玄月国两张主城不能直接相连。")

    write_json(WORLD_FILE, world)
    print(json.dumps({
        "maps": len(world["maps"]),
        "regions": world["summary"]["countryMapCounts"],
        "imperialMaps": world["summary"]["imperialCityMapCount"],
        "teleports": len(teleports),
        "connections": len(world["connections"]),
        "unmatchedTeleports": len(teleports) - len(used_teleports),
        "connectedMapComponents": len(graph_components(map_id_set, pairs)),
    }, ensure_ascii=False))


if __name__ == "__main__":
    main()
