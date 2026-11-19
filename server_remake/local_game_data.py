import csv
from dataclasses import dataclass
from pathlib import Path


DATA_DIR = Path(__file__).with_name("交接资料") / "tables"
JOB_NAMES = ("先锋", "百步", "天灵")
SLOT_NAMES = ("衣", "护手", "护腿", "头盔", "项链", "戒指", "戒指", "靴子", "武器", "法器")
SLOT_BY_PART = {
    "衣": 0,
    "护手": 1,
    "护腿": 2,
    "头盔": 3,
    "项链": 4,
    "戒指": 5,
    "靴子": 7,
    "武器": 8,
    "法器": 9,
}
STAT_COLUMNS = (
    "物攻",
    "法攻",
    "物防",
    "法防",
    "暴击",
    "命中",
    "闪避",
    "格挡",
    "生命",
    "法力",
    "速度",
    "暴击倍数",
    "命中系数",
    "坚韧",
    "穿透",
)


@dataclass(frozen=True)
class MapData:
    index: int
    name: str
    zone_id: int
    map_res_id: int
    base_level: int
    kind: str
    resource_file: str

    @property
    def safe_zone(self):
        return "安全区" in self.kind

    @property
    def faction_bonus(self):
        return 2 if "妖族占领地" in self.kind else 0


@dataclass(frozen=True)
class NpcData:
    index: int
    character_id: int
    sprite_id: int
    x: int
    y: int
    name: str


def _read_csv(filename):
    with (DATA_DIR / filename).open("r", encoding="utf-8-sig", newline="") as source:
        return list(csv.DictReader(source))


def _load_maps():
    rows = _read_csv("地图表.csv")
    maps = []
    for row in rows:
        maps.append(MapData(
            index=int(row["序号"]),
            name=row["地图名"],
            zone_id=int(row["zoneId(分区)"]),
            map_res_id=int(row["地图资源号 mapResId"]),
            base_level=int(row["怪物基础等级"]),
            kind=row["类型"],
            resource_file=row["资源文件"],
        ))
    if not maps or [entry.index for entry in maps] != list(range(len(maps))):
        raise ValueError("Map table indexes must be contiguous and start at zero")
    if len({entry.zone_id for entry in maps}) != len(maps):
        raise ValueError("Map table contains duplicate zone IDs")
    return tuple(maps)


def _load_npcs():
    rows = _read_csv("NPC表.csv")
    npcs = []
    for row in rows:
        sprite = row["NPC_SPRITES精灵号"] or row["NPC_DEFS精灵号"]
        npcs.append(NpcData(
            index=int(row["序号"]),
            character_id=int(row["charId"]),
            sprite_id=int(sprite),
            x=int(row["x"]),
            y=int(row["y"]),
            name=row["名字"],
        ))
    if len({entry.character_id for entry in npcs}) != len(npcs):
        raise ValueError("NPC table contains duplicate character IDs")
    return tuple(npcs)


def _slot_index(part, ring_counts):
    slot = SLOT_BY_PART.get(part)
    if slot == 5:
        ring_index = ring_counts.get(part, 0)
        ring_counts[part] = ring_index + 1
        return 5 + ring_index
    if slot is None:
        raise ValueError("Unknown equipment slot: {}".format(part))
    return slot


def _load_equipment():
    names = {}
    ring_counts = {}
    for row in _read_csv("装备名称表.csv"):
        key = (JOB_NAMES.index(row["职业"]), int(row["档位idx"]))
        slot = _slot_index(row["部位"], ring_counts.setdefault(key, {}))
        names[key + (slot,)] = row["装备名"]

    stats = {}
    ring_counts = {}
    for row in _read_csv("装备属性表.csv"):
        key = (JOB_NAMES.index(row["职业"]), int(row["档位idx"]))
        slot = _slot_index(row["部位"], ring_counts.setdefault(key, {}))
        stats[key + (slot,)] = tuple(int(row[column]) for column in STAT_COLUMNS)

    artifacts = {}
    ring_counts = {}
    for row in _read_csv("神器名称表.csv"):
        job = JOB_NAMES.index(row["职业"])
        slot = _slot_index(row["部位"], ring_counts.setdefault(job, {}))
        artifacts[(job, slot)] = row["神器名"]

    if len(names) != 390 or len(stats) != 390 or len(artifacts) != 30:
        raise ValueError("Equipment tables have unexpected row counts")
    return names, stats, artifacts


MAPS = _load_maps()
MAPS_BY_ZONE = {entry.zone_id: entry for entry in MAPS}
NPCS = _load_npcs()
GEAR_NAMES, GEAR_STATS, ARTIFACT_NAMES = _load_equipment()
