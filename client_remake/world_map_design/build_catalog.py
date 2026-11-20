import csv
import json
import re
from collections import Counter, defaultdict
from pathlib import Path
from urllib.parse import quote


PROJECT_ROOT = Path(__file__).resolve().parent.parent
EXPORT_ROOT = PROJECT_ROOT / "htol_export"
MAPS_ROOT = EXPORT_ROOT / "map" / "preview"
COLLISION_ROOT = EXPORT_ROOT / "map" / "collision"
OUTPUT_ROOT = Path(__file__).resolve().parent

MAP_FILE_PATTERN = re.compile(r"map_(\d+)_palette_0_preview\.png$")
REGION_CELLS = {
    "玄武国": [
        *((row, column) for row, start, stop in ((0, 4, 9), (1, 3, 10), (2, 2, 11), (3, 1, 12), (4, 0, 13))
          for column in range(start, stop)),
    ],
    "邪血国": [
        *((row, column) for row in range(5, 9) for column in range(5)
          if (row, column) not in {(5, 3), (5, 4), (8, 4)}),
        *((row, 8) for row in range(6, 9)),
        *((9, column) for column in range(2, 9)),
    ],
    "玄月国": [
        *((row, column) for row in range(5, 10) for column in range(9, 14)
          if (row, column) != (5, 9)),
        *((4, column) for column in range(13, 16)),
        (9, 14),
        *((10, column) for column in range(6, 14)),
    ],
    "皇城": [
        *((row, column) for row in range(5, 9) for column in range(5, 9)),
    ],
}
CITY_CELLS = {
    49: (1, 6),
    140: (4, 6),
    75: (7, 1),
    83: (5, 1),
    13: (9, 11),
    110: (6, 9),
}
GRID_ORIGIN = (80, 40)
GRID_PITCH = (100, 78)
MAP_CARD_SIZE = (88, 64)
SEA_MAP_IDS = {111, 112, 113, 127, 128, 129, 130, 131, 132, 139}
XUANWU_PINNED_MAPS = {
    (2, 4): 81,
    (2, 5): 86,
    (2, 6): 85,
    (3, 3): 121,
    (3, 4): 122,
    (3, 5): 123,
    (3, 7): 124,
    (3, 8): 155,
    (3, 9): 126,
    (3, 10): 125,
    (2, 7): 87,
}
EVIL_PINNED_MAPS = {(6, 2): 14, (6, 3): 97}
COASTAL_CELLS = {
    "玄月国": {
        (10, 6): 111,
        (10, 7): 112,
        (10, 8): 113,
        (10, 9): 127,
        (10, 10): 128,
        (10, 11): 129,
        (10, 12): 130,
        (10, 13): 131,
        (9, 13): 132,
        (8, 13): 139,
    },
}
BUILDING_SPRITES = {237, 239, 255, 260, 263, 264, 265, 266, 267, 268, 269, 271}
CITY_MOVES = {
    49: {"nation": "玄武国", "role": "主城", "name": "玄武国主城"},
    140: {"nation": "玄武国", "role": "主城", "name": "玄武国主城"},
    75: {"nation": "邪血国", "role": "主城", "name": "邪血国主城"},
    83: {"nation": "邪血国", "role": "主城", "name": "邪血国主城"},
    13: {"nation": "玄月国", "role": "主城", "name": "玄月国主城"},
    110: {"nation": "玄月国", "role": "主城", "name": "玄月国主城"},
}
FORMER_XUANWU_CITIES = {43, 48}

SPRITE_DESCRIPTIONS = {
    218: ("木制亭阁", "有台阶的中式木亭，单体建筑。", "high"),
    219: ("莲花水景", "池中莲花与水波动画。", "high"),
    220: ("粉色花瓣", "飘落的粉色花瓣特效。", "high"),
    221: ("石砌喷泉", "圆形石砌水池/喷泉，带青色水面。", "high"),
    222: ("木栅栏", "交错木杆构成的围栏。", "high"),
    223: ("火焰特效", "橙红色火焰动画。", "high"),
    224: ("蓝色水纹特效", "蓝青色水波或魔法能量粒子。", "medium"),
    225: ("公鸡", "棕色公鸡，带有行走动画。", "high"),
    226: ("浅色小型生物/装饰", "白灰色小型生物状图形；具体语义无法仅凭图像确认。", "low"),
    227: ("喷泉水花", "水面涟漪与向上喷出的水柱。", "high"),
    228: ("跃出水面的鱼", "带水花的彩色鱼类动画。", "high"),
    229: ("石灯笼", "带屋檐的中式石灯。", "high"),
    230: ("绿叶树", "落叶乔木，浓密绿色树冠。", "high"),
    231: ("石狮雕像", "石质瑞兽/狮子雕像，置于方形底座。", "high"),
    232: ("地面阴影/铺地条", "细长斜向地面图块，外观更像阴影或铺地装饰。", "medium"),
    233: ("石板小桥", "短段石板桥，带低矮石栏。", "high"),
    234: ("铁栅栏", "竖向金属栏杆。", "high"),
    235: ("空白资源", "当前导出的预览帧不可见。", "low"),
    236: ("红色木门", "带门环的红褐色中式门扇。", "high"),
    237: ("中式民居", "红木墙体、青瓦屋顶的小型中式房屋。", "high"),
    238: ("临水石阶", "伸入水边的多级石阶。", "high"),
    239: ("马匹与马棚", "带马匹的木制马棚/拴马设施。", "high"),
    240: ("花纹石质地砖", "方形浅色雕花地面装饰。", "high"),
    241: ("陶罐与瓷器", "成组的陶罐、酒坛或小型容器。", "high"),
    242: ("骸骨", "浅色动物/人形骨骸装饰。", "medium"),
    243: ("中式牌楼", "双柱三门式中式牌楼。", "high"),
    244: ("深色灌木", "深绿叶片的低矮灌木或蕨类。", "high"),
    245: ("垂柳", "树冠下垂的绿色柳树。", "high"),
    246: ("幽暗洞窟入口", "蓝绿色岩洞/洞窟入口，内部有台阶。", "high"),
    247: ("空白资源", "当前导出的预览帧不可见。", "low"),
    248: ("橙色光效", "圆形橙黄色发光/爆裂特效。", "high"),
    249: ("木板栈道", "窄木板组成的直线栈道。", "high"),
    250: ("圆形石台", "带台阶与青色纹样的圆形祭坛/传送台。", "high"),
    251: ("蓝色传送门", "竖立的蓝色旋涡传送门。", "high"),
    252: ("绿色草丛", "小型绿色草木或灌木。", "high"),
    253: ("瀑布水柱", "白蓝色垂直瀑布或水流特效。", "high"),
    254: ("冰雾/云团", "淡蓝色横向雾气或云状粒子效果。", "medium"),
    255: ("布篷摊位", "黄色布篷下的货摊与桌台。", "high"),
    256: ("蓝色晶簇", "多簇蓝紫色水晶。", "high"),
    257: ("青色石雕", "带青蓝色底座的鸟/兽形石雕。", "medium"),
    258: ("火炬灯柱", "顶部燃烧的立式灯柱。", "high"),
    259: ("暗色圆形法阵", "带红色纹路的黑色圆形地面法阵。", "high"),
    260: ("金顶宫殿", "多层红墙金瓦宫殿。", "high"),
    261: ("熔岩岩柱", "裂开并发光的火山岩柱。", "high"),
    262: ("熔岩碎片", "橙红色熔岩碎屑/火星。", "high"),
    263: ("中式大屋", "红墙青瓦的多层中式房屋。", "high"),
    264: ("中式楼阁", "红墙青瓦的中式楼阁，带侧翼屋顶。", "high"),
    265: ("中式民居", "较宽的红木墙青瓦民居。", "high"),
    266: ("中式楼房", "带高层屋顶的红墙中式楼房。", "high"),
    267: ("灰墙民居", "灰墙褐瓦的小型中式民居。", "high"),
    268: ("中式房屋", "红木立面、青瓦屋顶的独立房屋。", "high"),
    269: ("中式商铺/宅邸", "带红色门窗与青瓦屋顶的中式建筑。", "high"),
    270: ("金色地面器物", "金橙色方形低矮器物，可能为祭台/宝箱类装饰。", "low"),
    271: ("茅草屋", "木结构茅草顶小屋。", "high"),
    272: ("岩石", "灰绿色大型岩石。", "high"),
    273: ("龙纹岩雕", "带龙形纹饰的深色岩石雕刻。", "high"),
    274: ("蓝色岩石", "深蓝色大型岩石或矿石。", "high"),
}

SPECIAL_MAP_NOTES = {
    13: "宽阔石铺街区与中心广场，分布中式屋宇、粉花树、绿树和水渠；适合作为玄月国主城。",
    43: "石铺大道、宫殿立面、栅栏与临水区域，具有北方宫城入口感；选作玄武国主城。",
    48: "多组中式屋宇沿交叉石路分布，夹有绿树和粉花树；适合作为玄武国主城。",
    75: "密集的中式建筑、货摊与宽阔铺石路，含中心铺地广场；选作邪血国主城。",
    83: "深色林地中的中式聚落，粉花树、绿树、石路与中心石台并存；选作邪血国主城。",
    110: "棕榈海岸村落，包含茅草屋、船只、沙滩与浅海；选作玄月国海边主城。",
    45: "石铺平台和水渠构成的开阔路网，未见建筑，适合作为皇城空地连接区。",
    69: "多层石铺平台与水道，未见房屋建筑，适合作为皇城过渡空地。",
    80: "浅色广场平台与水池构成的开阔区域，未见房屋建筑，适合作为皇城过渡空地。",
    142: "池水与石铺走道之间分布多座中式建筑；不是纯空地，留作皇城内部节点。",
    145: "水渠与浅色铺地平台构成的空旷路口，未见房屋建筑。",
    146: "矩形石铺台地和水道，属于 maps 根目录散图，暂不使用。",
    147: "多处平台、水道和建筑构成的皇城街区；不是纯空地。",
    159: "海水环绕的浅色平台与沙地，带少量建筑，留作皇城内部节点。",
    160: "水面间分布的浅色石铺平台，未见房屋建筑。",
}

GROUND_LABELS = {
    "黄地": "黄土/浅褐色地表",
    "绿地": "明亮草地",
    "深绿地": "深色林地地表",
    "海边": "沙滩与海水",
    "皇城": "石铺皇城街区",
    "三国主城3x2": "城市/聚落地表",
    "地府": "幽暗地府地表",
}

VEGETATION_LABELS = {
    "粉树": "粉色花树",
    "绿树": "绿色树木",
    "黄树": "黄色/秋色树木",
    "黄粉树": "黄叶与粉花树",
    "黄绿树": "黄叶与绿叶树",
    "黄地熔岩": "荒地与熔岩裂隙",
    "海边": "棕榈、海岸植被",
    "皇城": "园林树木与城内绿化",
    "三国主城3x2": "依地图呈现记录",
    "地府": "枯木与幽暗植被",
}


def read_csv(path):
    with path.open(encoding="utf-8-sig", newline="") as source:
        return list(csv.DictReader(source))


def number(value):
    if value in ("", None):
        return None
    parsed = float(value)
    return int(parsed) if parsed.is_integer() else parsed


def map_category(path):
    return path.parent.name if path.parent != MAPS_ROOT else "_未分类根目录"


def image_url(folder, filename):
    return f"/maps/{folder}/{filename}"


def object_url(filename):
    return "/objects/" + "/".join(quote(part, safe="") for part in Path(filename).parts)


def list_group_cells(rows, columns):
    cells = []
    for row in range(rows):
        row_columns = range(columns) if row % 2 == 0 else range(columns - 1, -1, -1)
        cells.extend((row, column) for column in row_columns)
    return cells


def fill_group(map_ids, rows, columns, pinned_cells, building_map_ids=None):
    cells = list_group_cells(rows, columns)
    by_cell = {tuple(cell): map_id for cell, map_id in pinned_cells.items()}
    used_ids = set(by_cell.values())
    if len(used_ids) != len(by_cell):
        raise ValueError("A pinned map ID is assigned to more than one cell.")
    remaining = [map_id for map_id in map_ids if map_id not in used_ids]
    free_cells = [cell for cell in cells if cell not in by_cell]
    if len(remaining) != len(free_cells):
        raise ValueError("The number of maps must match the number of open cells.")
    if building_map_ids is None:
        for cell, map_id in zip(free_cells, remaining):
            by_cell[cell] = map_id
        return by_cell, cells

    remaining_buildings = [map_id for map_id in remaining if map_id in building_map_ids]
    remaining_wilderness = [map_id for map_id in remaining if map_id not in building_map_ids]
    selected_cells = [
        cell for cell, map_id in by_cell.items() if map_id in building_map_ids
    ]
    available_cells = set(free_cells)
    while remaining_buildings:
        def spread_score(cell):
            row, column = cell
            if selected_cells:
                distance = min(
                    (row - selected_row) ** 2 + (column - selected_column) ** 2
                    for selected_row, selected_column in selected_cells
                )
            else:
                distance = 0
            center_distance = (row - (rows - 1) / 2) ** 2 + (column - (columns - 1) / 2) ** 2
            return distance, -center_distance, -row, -column

        cell = max(available_cells, key=spread_score)
        by_cell[cell] = remaining_buildings.pop(0)
        selected_cells.append(cell)
        available_cells.remove(cell)
    open_cells = [cell for cell in cells if cell not in by_cell]
    for cell, map_id in zip(open_cells, remaining_wilderness):
        by_cell[cell] = map_id
    return by_cell, cells


def load_portal_edge_data(map_ids):
    data_by_map = {}
    for map_id in map_ids:
        path = COLLISION_ROOT / f"map_{map_id:03d}.json"
        with path.open(encoding="utf-8") as source:
            collision = json.load(source)
        width = collision["width"]
        height = collision["height"]
        attributes = collision["attributes"]
        if len(attributes) != width * height or collision["blockingAttribute"] != 23:
            raise ValueError(f"Map {map_id} has invalid collision data.")
        data_by_map[map_id] = collision
    return data_by_map


def open_map_edges(collision):
    width = collision["width"]
    height = collision["height"]
    blocking = collision["blockingAttribute"]
    attributes = collision["attributes"]
    open_edges = set()
    for edge, cells in {
        "north": ((x, y) for y in range(min(3, height)) for x in range(width)),
        "south": ((x, y) for y in range(max(0, height - 3), height) for x in range(width)),
        "west": ((x, y) for x in range(min(3, width)) for y in range(height)),
        "east": ((x, y) for x in range(max(0, width - 3), width) for y in range(height)),
    }.items():
        if any(
            attributes[y * width + x] != blocking
            for x, y in cells
        ):
            open_edges.add(edge)
    return open_edges


def edge_port(collision, edge):
    width = collision["width"]
    height = collision["height"]
    attributes = collision["attributes"]
    blocking = collision["blockingAttribute"]
    candidates = []
    if edge in ("north", "south"):
        edge_rows = range(min(3, height)) if edge == "north" else range(max(0, height - 3), height)
        for y in edge_rows:
            for x in range(width):
                if attributes[y * width + x] != blocking:
                    candidates.append((x, y))
        dimension = width
        axis_index = 0
    else:
        edge_columns = range(min(3, width)) if edge == "west" else range(max(0, width - 3), width)
        for x in edge_columns:
            for y in range(height):
                if attributes[y * width + x] != blocking:
                    candidates.append((x, y))
        dimension = height
        axis_index = 1
    if not candidates:
        return {"edge": edge, "position": 0.5}
    midpoint = (dimension - 1) / 2
    coordinate = min(candidates, key=lambda cell: (abs(cell[axis_index] - midpoint), cell[axis_index]))
    position = max(0.15, min(0.85, (coordinate[axis_index] + 0.5) / dimension))
    return {"edge": edge, "position": round(position, 3)}


def set_grid_position(node, cell):
    row, column = cell
    origin_x, origin_y = GRID_ORIGIN
    pitch_x, pitch_y = GRID_PITCH
    node["x"] = origin_x + column * pitch_x
    node["y"] = origin_y + row * pitch_y
    node["gridCell"] = {"row": row, "column": column}


def assign_region(map_ids, cells, pinned_cells, building_map_ids):
    by_cell = {tuple(cell): map_id for cell, map_id in pinned_cells.items()}
    if len(set(by_cell.values())) != len(by_cell):
        raise ValueError("A pinned map ID is assigned to more than one cell.")
    if len(map_ids) != len(cells) or len(set(cells)) != len(cells):
        raise ValueError("Region map count and unique cell count must match.")
    if not set(by_cell).issubset(cells) or not set(by_cell.values()).issubset(map_ids):
        raise ValueError("Pinned map assignment is outside its region.")

    remaining = [map_id for map_id in map_ids if map_id not in by_cell.values()]
    remaining_buildings = [map_id for map_id in remaining if map_id in building_map_ids]
    remaining_wilderness = [map_id for map_id in remaining if map_id not in building_map_ids]
    selected_cells = [cell for cell, map_id in by_cell.items() if map_id in building_map_ids]
    available_cells = set(cells) - set(by_cell)

    while remaining_buildings:
        def spread_score(cell):
            row, column = cell
            distance = min(
                ((row - selected_row) ** 2 + (column - selected_column) ** 2 for selected_row, selected_column in selected_cells),
                default=0,
            )
            center_distance = (row - 5.5) ** 2 + (column - 6.5) ** 2
            return distance, -center_distance, -row, -column

        cell = max(sorted(available_cells), key=spread_score)
        by_cell[cell] = remaining_buildings.pop(0)
        selected_cells.append(cell)
        available_cells.remove(cell)

    free_cells = sorted(available_cells)
    for cell, map_id in zip(free_cells, remaining_wilderness):
        by_cell[cell] = map_id
    return by_cell


def terrain_labels(category):
    if category == "_未分类根目录":
        return "未分类", "未分类"
    ground = next((label for prefix, label in GROUND_LABELS.items() if category.startswith(prefix)), category)
    vegetation = next((label for suffix, label in VEGETATION_LABELS.items() if category.endswith(suffix)), "未注明植被")
    if category == "黄地熔岩":
        vegetation = VEGETATION_LABELS[category]
    return ground, vegetation


def main():
    map_rows = read_csv(EXPORT_ROOT / "map_index.csv")
    entity_rows = read_csv(EXPORT_ROOT / "map_entities.csv")
    preview_rows = read_csv(EXPORT_ROOT / "map_preview_index.csv")
    image_by_id = {
        int(row["map_resource_id"]): EXPORT_ROOT / row["file"]
        for row in preview_rows
        if int(row["palette_index"]) == 0
    }
    previous_catalog_path = OUTPUT_ROOT / "map_catalog.json"
    previous_maps = {}
    if previous_catalog_path.is_file():
        previous_catalog = json.loads(
            previous_catalog_path.read_text(encoding="utf-8")
        )
        previous_maps = {
            int(map_record["mapId"]): map_record
            for map_record in previous_catalog.get("maps", [])
        }
    design_path = OUTPUT_ROOT / "world_map.json"
    design_maps = {}
    if design_path.is_file():
        design = json.loads(design_path.read_text(encoding="utf-8"))
        design_maps = {
            int(map_record["mapId"]): map_record
            for map_record in design.get("maps", [])
        }
    sprite_manifest = json.loads(
        (EXPORT_ROOT / "sprite_library.json").read_text(encoding="utf-8")
    )
    library_by_id = {
        int(sprite["spriteId"]): sprite
        for sprite in sprite_manifest.get("sprites", [])
    }

    entities_by_map = defaultdict(list)
    for row in entity_rows:
        entities_by_map[int(row["map_resource_id"])].append(row)

    sprite_rows = defaultdict(list)
    for entity in entity_rows:
        sprite_id = int(entity["sprite_resource_id"])
        sprite_rows[sprite_id].append(entity)

    sprites = []
    for sprite_id in sorted(set(sprite_rows)):
        library_sprite = library_by_id.get(sprite_id, {})
        sprite_files = sorted(
            {
                item["file"]
                for item in (
                    *library_sprite.get("groups", []),
                    *library_sprite.get("components", []),
                )
                if item.get("file")
            }
        )
        label, description, confidence = SPRITE_DESCRIPTIONS.get(
            sprite_id,
            (f"精灵资源 {sprite_id}", "未添加人工视觉描述。", "low"),
        )
        used_rows = sprite_rows.get(sprite_id, [])
        sprites.append({
            "spriteResourceId": sprite_id,
            "label": label,
            "visualDescription": description,
            "confidence": confidence,
            "overviewImage": object_url(sprite_files[0]) if sprite_files else None,
            "files": [object_url(filename) for filename in sprite_files],
            "fileCount": len(sprite_files),
            "mapInstanceCount": len(used_rows),
            "mapCount": len({int(row["map_resource_id"]) for row in used_rows}),
        })
    sprite_by_id = {sprite["spriteResourceId"]: sprite for sprite in sprites}

    maps = []
    map_by_id = {}
    for row in map_rows:
        map_id = int(row["map_resource_id"])
        path = image_by_id.get(map_id)
        if path is None:
            raise FileNotFoundError(f"Missing full map preview for map_{map_id:03d}.")
        old_record = previous_maps.get(map_id, {})
        design_record = design_maps.get(map_id, {})
        category = old_record.get(
            "sourceCategory",
            design_record.get("category", map_category(path)),
        )
        excluded_reason = (
            "maps 根目录散图：按需求暂不用于世界地图"
            if category == "_未分类根目录"
            else "地府目录：按需求暂不用于世界地图"
            if category == "地府"
            else None
        )
        if old_record.get("includedInWorld") is not None:
            excluded_reason = old_record.get("exclusionReason")
        terrain, vegetation = terrain_labels(category)
        map_entities = sorted(
            entities_by_map.get(map_id, []),
            key=lambda entity: int(entity["object_index"]),
        )
        object_counts = Counter(int(entity["sprite_resource_id"]) for entity in map_entities)
        object_summary = [
            {
                "spriteResourceId": sprite_id,
                "label": sprite_by_id[sprite_id]["label"],
                "count": count,
            }
            for sprite_id, count in sorted(object_counts.items(), key=lambda item: (-item[1], item[0]))
        ]
        building_count = sum(object_counts[sprite_id] for sprite_id in BUILDING_SPRITES)
        map_note = SPECIAL_MAP_NOTES.get(map_id)
        if not map_note:
            prominent = "、".join(
                f'{item["label"]}×{item["count"]}'
                for item in object_summary[:5]
            )
            map_note = f"{terrain}，{vegetation}；地图实例包括{prominent or '少量未分类对象'}。"
        object_instances = []
        for entity in map_entities:
            sprite_id = int(entity["sprite_resource_id"])
            frame_file = entity["frame_file"]
            object_instances.append({
                "objectIndex": int(entity["object_index"]),
                "entityKind": entity["entity_kind"],
                "classificationStatus": entity["classification_status"],
                "rawSpriteId": number(entity["raw_sprite_id"]),
                "spriteResourceId": sprite_id,
                "label": sprite_by_id.get(sprite_id, {}).get("label", f"精灵资源 {sprite_id}"),
                "animationGroup": number(entity["animation_group"]),
                "sourceXUnits": number(entity["source_x_units"]),
                "sourceYUnits": number(entity["source_y_units"]),
                "renderXPx": number(entity["render_x_px"]),
                "renderYPx": number(entity["render_y_px"]),
                "frameFile": object_url(frame_file) if frame_file else None,
                "frameOriginXPx": number(entity["frame_origin_x_px"]),
                "frameOriginYPx": number(entity["frame_origin_y_px"]),
                "rawFields": json.loads(entity["raw_fields_json"]),
            })
        image_folder = (
            path.parent.name if path.parent != MAPS_ROOT else "_未分类根目录"
        )
        map_record = {
            "mapId": map_id,
            "key": f"map_{map_id:03d}",
            "filename": path.name,
            "image": image_url(image_folder, path.name),
            "previewFile": path.relative_to(EXPORT_ROOT).as_posix(),
            "sourceCategory": category,
            "includedInWorld": old_record.get(
                "includedInWorld", excluded_reason is None
            ),
            "exclusionReason": excluded_reason,
            "name": design_record.get("name", old_record.get("name", f"地图 {map_id}")),
            "region": design_record.get("region", old_record.get("region", category)),
            "nation": design_record.get("nation", old_record.get("nation", "")),
            "kind": design_record.get("kind", old_record.get("kind", terrain)),
            "category": design_record.get("category", old_record.get("category", vegetation)),
            "widthCells": int(row["width_cells"]),
            "heightCells": int(row["height_cells"]),
            "logicalCellUnits": int(row["logical_cell_units"]),
            "renderCellPx": int(row["render_cell_px"]),
            "tileSetRawId": int(row["tile_set_raw_id"]),
            "tileAtlasSpriteId": int(row["tile_atlas_sprite_id"]),
            "objectRecordCount": int(row["object_record_count"]),
            "entityCount": len(object_instances),
            "ground": terrain,
            "vegetation": vegetation,
            "hasBuildings": building_count > 0,
            "buildingObjectCount": building_count,
            "objectSummary": object_summary,
            "visualDescription": map_note,
            "objects": object_instances,
        }
        maps.append(map_record)
        map_by_id[map_id] = map_record

    if len(maps) != 155 or sum(len(item["objects"]) for item in maps) != 12200:
        raise ValueError("Unexpected map catalog size; verify map_index.csv and map_entities.csv.")
    if len(sprites) != 57:
        raise ValueError(f"Expected 57 unique map-object sprites, found {len(sprites)}.")

    OUTPUT_ROOT.joinpath("map_catalog.json").write_text(
        json.dumps({
            "schemaVersion": 1,
            "source": "htol_export/map_index.csv + map_entities.csv",
            "mapCount": len(maps),
            "mapObjectInstanceCount": sum(len(item["objects"]) for item in maps),
            "maps": maps,
        }, ensure_ascii=False, separators=(",", ":")),
        encoding="utf-8",
    )
    OUTPUT_ROOT.joinpath("object_catalog.json").write_text(
        json.dumps({
            "schemaVersion": 1,
            "source": "htol_export/map and sprite_library.json",
            "uniqueSpriteCount": len(sprites),
            "objectImageFileCount": sum(sprite["fileCount"] for sprite in sprites),
            "sprites": sprites,
        }, ensure_ascii=False, separators=(",", ":")),
        encoding="utf-8",
    )

    if len(design_maps) == 1:
        current_design = json.loads(design_path.read_text(encoding="utf-8"))
        map_node = current_design["maps"][0]
        catalog_map = map_by_id[int(map_node["mapId"])]
        for field in (
            "image",
            "name",
            "region",
            "nation",
            "kind",
            "category",
            "hasBuildings",
            "buildingObjectCount",
        ):
            map_node[field] = catalog_map[field]
        design_path.write_text(
            json.dumps(current_design, ensure_ascii=False, indent=2) + "\n",
            encoding="utf-8",
        )
        print(json.dumps({
            "maps": len(maps),
            "mapObjectInstances": sum(len(item["objects"]) for item in maps),
            "spriteResources": len(sprites),
            "objectImageFiles": sum(sprite["fileCount"] for sprite in sprites),
            "worldMapPreserved": True,
        }, ensure_ascii=False))
        return

    field_maps = [
        record for record in maps
        if record["includedInWorld"] and record["sourceCategory"] != "三国主城3x2"
    ]
    field_by_id = {record["mapId"]: record for record in field_maps}
    country_field_ids = {
        "玄武国": sorted(
            record["mapId"] for record in field_maps
            if record["sourceCategory"].startswith("黄地")
        ),
        "邪血国": sorted(
            record["mapId"] for record in field_maps
            if record["sourceCategory"].startswith("深绿地")
        ),
        "玄月国": sorted(
            [record["mapId"] for record in field_maps if record["sourceCategory"].startswith("绿地")]
            + sorted(SEA_MAP_IDS)
        ),
    }
    city_ids = {
        "玄武国": [49, 140],
        "邪血国": [75, 83],
        "玄月国": [13, 110],
    }
    expected_field_counts = {"玄武国": 43, "邪血国": 25, "玄月国": 34}
    if any(len(country_field_ids[nation]) != expected_field_counts[nation] for nation in country_field_ids):
        counts = {nation: len(ids) for nation, ids in country_field_ids.items()}
        raise ValueError(f"Unexpected field map distribution: {counts}.")
    if any(len(city_ids[nation]) != 2 for nation in city_ids):
        raise ValueError("Each country must have exactly two main-city maps.")

    # 皇城区位于 4x4 的中心版块内；必须使用该区域内的合法单元格，否则 assign_region 会拒绝
    # 这些固定点用于保留无建筑的皇城连接图，并让皇城保持在诸国中心附近。
    center_pins = {
        (5, 5): 45,
        (5, 6): 69,
        (6, 5): 80,
        (7, 6): 145,
        (7, 7): 160,
    }
    pinned_cells = {
        "玄武国": {
            **XUANWU_PINNED_MAPS,
            CITY_CELLS[49]: 49,
            CITY_CELLS[140]: 140,
        },
        "邪血国": {
            **EVIL_PINNED_MAPS,
            CITY_CELLS[75]: 75,
            CITY_CELLS[83]: 83,
        },
        "玄月国": {
            **COASTAL_CELLS["玄月国"],
            CITY_CELLS[13]: 13,
            CITY_CELLS[110]: 110,
        },
    }
    country_cells = {
        nation: assign_region(
            country_field_ids[nation] + city_ids[nation],
            REGION_CELLS[nation],
            pinned_cells[nation],
            {map_id for map_id in country_field_ids[nation] if map_by_id[map_id]["hasBuildings"]},
        )
        for nation in city_ids
    }
    imperial_ids = [
        record["mapId"] for record in maps
        if (
            record["includedInWorld"]
            and record["sourceCategory"] == "皇城"
            and record["mapId"] not in set(CITY_MOVES)
        )
    ]
    imperial_ids.extend(sorted(FORMER_XUANWU_CITIES))
    if len(imperial_ids) != 16:
        raise ValueError(f"Expected 16 central Imperial City maps; got {len(imperial_ids)}.")
    imperial_cells = assign_region(
        imperial_ids,
        REGION_CELLS["皇城"],
        center_pins,
        {map_id for map_id in imperial_ids if map_by_id[map_id]["hasBuildings"]},
    )
    cities_by_id = CITY_MOVES
    region_cells = {**country_cells, "皇城": imperial_cells}
    ordered_regions = {
        region: [
            map_id
            for _, map_id in sorted(cell_map.items(), key=lambda item: (item[0][0], item[0][1]))
        ]
        for region, cell_map in region_cells.items()
    }

    nodes = {}
    cell_by_map = {}
    for region, cell_map in region_cells.items():
        for cell, map_id in cell_map.items():
            map_record = map_by_id[map_id]
            city = cities_by_id.get(map_id)
            nodes[map_id] = {
                "mapId": map_id,
                "key": map_record["key"],
                "name": city["name"] if city else f"{region}野外地图",
                "region": region,
                "nation": city["nation"] if city else (None if region == "皇城" else region),
                "kind": city["role"] if city else "野外地图",
                "category": map_record["sourceCategory"],
                "image": map_record["image"],
                "x": 0,
                "y": 0,
                "width": MAP_CARD_SIZE[0],
                "height": MAP_CARD_SIZE[1],
                "hasBuildings": map_record["hasBuildings"] if not city else False,
                "buildingObjectCount": map_record["buildingObjectCount"] if not city else 0,
                "teleports": [],
            }
            set_grid_position(nodes[map_id], cell)
            cell_by_map[map_id] = cell

    connections = []

    def add_connection(left_id, right_id, kind, label, border_note=None):
        left = nodes[left_id]
        right = nodes[right_id]
        if len(left["teleports"]) >= 4 or len(right["teleports"]) >= 4:
            return
        connection_id = f"{min(left_id, right_id)}-{max(left_id, right_id)}"
        if any(item["id"] == connection_id for item in connections):
            raise ValueError(f"Duplicate connection: {connection_id}")
        left_center_x = left["x"] + left["width"] / 2
        left_center_y = left["y"] + left["height"] / 2
        right_center_x = right["x"] + right["width"] / 2
        right_center_y = right["y"] + right["height"] / 2
        delta_x = right_center_x - left_center_x
        delta_y = right_center_y - left_center_y
        if abs(delta_x) >= abs(delta_y):
            left_edge = "east" if delta_x >= 0 else "west"
            right_edge = "west" if delta_x >= 0 else "east"
            left_position = max(0.15, min(0.85, 0.5 + delta_y / 600))
            right_position = max(0.15, min(0.85, 0.5 - delta_y / 600))
        else:
            left_edge = "south" if delta_y >= 0 else "north"
            right_edge = "north" if delta_y >= 0 else "south"
            left_position = max(0.15, min(0.85, 0.5 + delta_x / 600))
            right_position = max(0.15, min(0.85, 0.5 - delta_x / 600))
        left_port = {"edge": left_edge, "position": round(left_position, 3)}
        right_port = {"edge": right_edge, "position": round(right_position, 3)}
        left["teleports"].append({
            "targetMapId": right_id,
            "label": label,
            "kind": kind,
            "borderNote": border_note,
            "port": left_port,
        })
        right["teleports"].append({
            "targetMapId": left_id,
            "label": label,
            "kind": kind,
            "borderNote": border_note,
            "port": right_port,
        })
        connections.append({
            "id": connection_id,
            "fromMapId": left_id,
            "toMapId": right_id,
            "kind": kind,
            "label": label,
            "borderNote": border_note,
            "fromPort": left_port,
            "toPort": right_port,
        })

    for region, ordered_ids in ordered_regions.items():
        for left_id, right_id in zip(ordered_ids, ordered_ids[1:]):
            add_connection(left_id, right_id, "local", "区域内道路")

    external_pairs = [
        ("玄武国", [(4, 5), (4, 6), (4, 7), (4, 8)], "邪血国", [(5, 2), (6, 2), (6, 3), (7, 3)],
         "国境", "玄武—邪血国界", "黄土地貌向深绿林地过渡；黄地绿树与深绿地绿树相接。"),
        ("玄武国", [(4, 9), (3, 9), (3, 8), (3, 7)], "玄月国", [(10, 9), (10, 10), (9, 10), (9, 13)],
         "国境", "玄武—玄月国界", "黄地粉/绿植被过渡到绿地粉树；保留南行通路。"),
        ("邪血国", [(7, 3), (7, 4), (8, 3), (6, 3)], "玄月国", [(10, 9), (10, 10), (9, 10), (9, 13)],
         "国境", "邪血—玄月国界", "深绿林地与粉树带过渡到明亮绿地粉树。"),
    ]
    for left_region, left_cells, right_region, right_cells, kind, label, note in external_pairs:
        left_region_map = country_cells[left_region]
        right_region_map = country_cells[right_region]
        for left_cell, right_cell in zip(left_cells, right_cells):
            add_connection(left_region_map[left_cell], right_region_map[right_cell], kind, label, note)

    capital_pairs = [
        ("玄武国", [(4, 5), (4, 6), (3, 7)], [(5, 5), (5, 6), (6, 5)], "玄武国—皇城"),
        ("邪血国", [(5, 2), (6, 2), (7, 3)], [(5, 5), (6, 5), (7, 6)], "邪血国—皇城"),
        ("玄月国", [(10, 9), (10, 10), (9, 10)], [(5, 6), (7, 6), (7, 7)], "玄月国—皇城"),
    ]
    for nation, country_cells_for_links, center_cells_for_links, label in capital_pairs:
        national_map_by_cell = country_cells[nation]
        for country_cell, center_cell in zip(country_cells_for_links, center_cells_for_links):
            imperial_map_id = imperial_cells[center_cell]
            if map_by_id[imperial_map_id]["hasBuildings"]:
                raise ValueError(f"Imperial City border map {imperial_map_id} contains buildings.")
            add_connection(
                national_map_by_cell[country_cell],
                imperial_map_id,
                "capital-border",
                label,
                "皇城连接地图为开阔石铺/水道区域，不布置房屋建筑。",
            )

    for nation, ids in city_ids.items():
        add_connection(ids[0], ids[1], "city-route", f"{nation}两张主城")

    for node in nodes.values():
        if len(node["teleports"]) > 4:
            raise ValueError(f'{node["key"]} has {len(node["teleports"])} teleport points.')
    if len(nodes) != 124:
        raise ValueError(f"Unexpected world graph size: {len(nodes)} maps.")
    if sum(node["region"] == "皇城" for node in nodes.values()) != 16:
        raise ValueError("The central Imperial City must contain exactly 16 maps.")
    adjacency = defaultdict(set)
    for connection in connections:
        adjacency[connection["fromMapId"]].add(connection["toMapId"])
        adjacency[connection["toMapId"]].add(connection["fromMapId"])
    reached = set()
    pending = [next(iter(nodes))]
    while pending:
        map_id = pending.pop()
        if map_id in reached:
            continue
        reached.add(map_id)
        pending.extend(adjacency[map_id] - reached)
    if len(reached) != len(nodes):
        raise ValueError(f"World graph is disconnected: {len(reached)} of {len(nodes)} maps are reachable.")

    for node in nodes.values():
        node["teleports"] = []
    connections = []

    OUTPUT_ROOT.joinpath("world_map.json").write_text(
        json.dumps({
            "schemaVersion": 1,
            "title": "三国与中央皇城世界地图",
            "layout": {
                "width": 1680,
                "height": 1320,
                "cardWidth": 88,
                "cardHeight": 64,
                "maxTeleportsPerMap": 4,
            },
            "regions": [
                {"id": "玄武国", "label": "玄武国", "description": "北部黄地与黄树/绿树荒野。", "color": "#c99a37"},
                {"id": "邪血国", "label": "邪血国", "description": "西南深绿林地、粉树过渡带及熔岩荒地。", "color": "#17365d"},
                {"id": "玄月国", "label": "玄月国", "description": "东南绿地粉树与海岸。", "color": "#4b9b55"},
                {"id": "皇城", "label": "中央皇城", "description": "由皇城目录的无房屋石铺/水道地图构成的中立区。", "color": "#b7a986"},
            ],
            "maps": list(nodes.values()),
            "connections": connections,
            "summary": {
                "includedMapCount": len(nodes),
                "countryMapCounts": {
                    nation: sum(node["region"] == nation for node in nodes.values())
                    for nation in ("玄武国", "邪血国", "玄月国")
                },
                "imperialCityMapCount": sum(node["region"] == "皇城" for node in nodes.values()),
                "connectionCount": len(connections),
                "interCountryBorderConnections": sum(
                    connection["kind"] == "国境" for connection in connections
                ),
                "capitalBorderConnections": sum(
                    connection["kind"] == "capital-border" for connection in connections
                ),
                "maximumTeleportsOnAnyMap": max(len(node["teleports"]) for node in nodes.values()),
                "regionBorderConnections": {
                    nation: sum(
                        connection["kind"] == "国境"
                        and (
                            nodes[connection["fromMapId"]]["region"] == nation
                            or nodes[connection["toMapId"]]["region"] == nation
                        )
                        for connection in connections
                    )
                    for nation in ("玄武国", "邪血国", "玄月国")
                },
                "buildingOutpostCounts": {
                    nation: sum(node["hasBuildings"] for node in nodes.values() if node["region"] == nation)
                    for nation in ("玄武国", "邪血国", "玄月国")
                },
            },
            "notes": [
                "maps 根目录散图与地府目录不进入世界地图；所有地图仍保留在 map_catalog.json 中并标明排除原因。",
                "三国地图数量按地貌分布自然分配，不强制完全相等；重点在于各国与相邻国和皇城保持足够连接。",
                "运行 build_catalog.py 后需运行 arrange_world.py，依据地图地貌与传送点数据生成最终布局和连接。",
            ],
        }, ensure_ascii=False, indent=2),
        encoding="utf-8",
    )

    print(json.dumps({
        "maps": len(maps),
        "includedMaps": len(nodes),
        "mapObjectInstances": sum(len(item["objects"]) for item in maps),
        "spriteResources": len(sprites),
        "objectImageFiles": sum(sprite["fileCount"] for sprite in sprites),
        "connections": len(connections),
        "countryCounts": {
            nation: sum(node["region"] == nation for node in nodes.values())
            for nation in ("玄武国", "邪血国", "玄月国")
        },
        "buildingOutposts": {
            nation: sum(node["hasBuildings"] for node in nodes.values() if node["region"] == nation)
            for nation in ("玄武国", "邪血国", "玄月国")
        },
        "maxTeleports": max(len(node["teleports"]) for node in nodes.values()),
    }, ensure_ascii=False))


if __name__ == "__main__":
    main()
