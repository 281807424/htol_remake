"""User-curated names and categories for the readable sprite library."""

from __future__ import annotations


MANUAL_NAMES: dict[int, str] = {
    23: "道具", 24: "表情", 25: "技能", 26: "快捷道具", 27: "其他",
    30: "技能特效", 31: "技能特效", 32: "技能特效", 33: "技能特效",
    34: "技能特效", 35: "技能特效", 36: "技能特效", 37: "技能特效",
    38: "技能特效", 39: "技能特效", 40: "技能特效", 41: "技能特效",
    42: "技能特效", 43: "技能特效", 44: "技能特效", 45: "技能特效",
    46: "技能特效", 47: "技能特效", 48: "技能特效", 51: "技能特效",
    52: "技能特效", 54: "技能特效", 55: "技能特效", 56: "技能特效",
    57: "技能特效", 58: "技能特效", 59: "技能特效", 60: "技能特效",
    61: "技能特效", 62: "技能特效", 63: "技能特效", 64: "技能特效",
    65: "技能特效", 68: "技能特效", 69: "技能特效",
    71: "狂狮", 72: "圣龙", 73: "麒麟",
    74: "男女战士", 75: "男女弓手", 76: "男女法师",
    77: "霸王蜂", 78: "小鸟", 79: "吉祥兔", 80: "鬼火",
    81: "小花", 82: "兰羽蝶", 86: "小兔", 87: "小鹿",
    88: "花怪", 89: "桃怪", 90: "花妖", 91: "山猫", 92: "狐狸",
    93: "野猪", 94: "野狼", 97: "花粉怪", 98: "老鹰", 99: "花豹",
    101: "小孩怪", 102: "刀客", 103: "蓝元素怪", 104: "斗笠刀客",
    105: "不羁和尚", 106: "吹笛侍女", 108: "毒龟",
    109: "公告栏", 110: "钓鱼老翁", 111: "女孩", 112: "大臣",
    113: "药师", 114: "将军", 115: "守卫", 116: "铁匠",
    117: "青衣侍女", 118: "红衣女", 119: "大力士",
    122: "鼓音怪", 123: "僵尸怪", 129: "蓝光球", 133: "玄天侍女",
    134: "交易商", 135: "佛像", 136: "蓝衣女使者", 137: "杂货商",
    138: "紫衣女", 139: "持枪侍卫", 140: "黑衣商贩", 141: "带剑人",
    142: "老板娘", 143: "屠夫", 144: "带剑侍卫", 146: "药剂商",
    147: "马匹", 148: "熊猫", 149: "老和尚",
    150: "狂牛", 151: "霸气大剑士", 152: "树精怪", 153: "护国神兽",
    154: "鬼女子", 155: "吹冰女子", 156: "终极女怪", 157: "鼓音怪",
    158: "六臂妖魔", 159: "防御塔", 160: "界石", 161: "妖小兵",
    162: "传令官",
    163: "橙色矿石", 164: "宝箱", 165: "布匹", 166: "花草",
    167: "狗尾草", 168: "兰花草", 169: "红蘑菇", 170: "紫花",
    171: "绿团草", 172: "绿叶草", 173: "致幻菇", 174: "魔草",
    175: "紫色藻", 176: "散落金币", 177: "粮包", 178: "木材",
    179: "宝箱怪", 180: "粮车", 181: "火凤凰怪", 182: "树妖怪",
    183: "圣龙坐骑", 184: "麒麟坐骑", 185: "妖精怪", 186: "孩儿怪",
    187: "虎王怪", 188: "宠物兔", 189: "螃蟹怪", 190: "亡灵守卫",
    191: "鱼头怪", 192: "亡灵大剑士", 193: "骷髅亡灵", 194: "鬼剑士",
    195: "魔化蝎", 196: "蚱蜢刀客", 197: "泥人士兵", 198: "水元素",
    199: "八脚光头", 200: "蚌精", 201: "龟精",
    202: "冥渊守卫", 203: "副本使者",
    277: "原画", 278: "原画", 279: "原画", 280: "原画",
    281: "原画", 282: "原画", 284: "无用图片",
    286: "新坐姿女怪", 287: "新坐姿女怪带背环",
}


MANUAL_CATEGORIES: dict[int, str] = {}
DUPLICATE_SPRITE_IDS = {285: 83, 301: 299, 302: 300}
MAP_UNIT_SPRITE_IDS = {109, 159, 160}

ASSET_FOLDERS = {
    "图标": "icon",
    "UI相关": "icon",
    "技能特效": "effect",
    "角色": "player",
    "坐骑": "player",
    "怪物": "monster",
    "中立生物": "monster",
    "宠物": "pet",
    "NPC": "npc",
    "采集物": "unit",
    "其他": "other",
    "未分类": "other",
    "原画": "other",
    "多分类": "other",
    "无用图片": "other",
}

ROLE_SPRITE_CONFIGS = {
    74: ("先锋", "剑", "斧"),
    75: ("百步", "弓", "弩"),
    76: ("天灵", "棍", "杖"),
}
# The unarmed blocks between these ranges are stored separately from the 12 sets.
ROLE_SET_BLOCKS = (
    (0, 22, "男", 0),
    (22, 43, "男", 1),
    (51, 73, "女", 0),
    (73, 94, "女", 1),
)
ROLE_UNARMED_GROUP_RANGES = ((43, 51), (94, 102))
ROLE_ACTION_NAMES = {
    0: "左站立",
    1: "上站立",
    2: "下站立",
    3: "左奔跑",
    4: "上奔跑",
    5: "下奔跑",
    **dict.fromkeys(range(6, 12), "攻击"),
    12: "施法",
    13: "攻击",
    14: "攻击",
    15: "攻击",
    16: "死亡",
    17: "施法",
    18: "骑乘",
    19: "骑乘",
    20: "施法",
    21: "施法",
}


def _assign(category: str, sprite_ids: list[int]) -> None:
    MANUAL_CATEGORIES.update({sprite_id: category for sprite_id in sprite_ids})


_assign("图标", [23, 24, 25, 26, 27])
_assign("技能特效", list(range(30, 49)) + [51, 52] + list(range(54, 66)) + [68, 69])
_assign("坐骑", [71, 72, 73, 183, 184])
_assign("角色", [74, 75, 76])
_assign("宠物", [77, 78, 79, 80, 81, 82, 86])
_assign("中立生物", [87])
_assign("其他", [109, 135, 159, 160, 180])
_assign("NPC", [110, 111, 112, 113, 114, 115, 116, 117, 118, 134, 136, 137,
                138, 139, 140, 141, 142, 143, 144, 146, 147, 148, 149, 162,
                202, 203])
_assign("采集物", list(range(163, 179)))
_assign(
    "怪物",
    [88, 89, 90, 91, 92, 93, 94, 97, 98, 99, 101, 102, 103, 104, 105, 106,
     108, 119, 122, 123, 129, 133, 150, 151, 152, 153, 154, 155, 156, 157,
     158, 161, 179, 181, 182, 185, 186, 187, 188, 189, 190, 191, 192, 193,
     194, 195, 196, 197, 198, 199, 200, 201, 286, 287],
)
_assign("原画", list(range(277, 283)))
_assign("无用图片", [284])


def sprite_category(sprite_id: int, animation_group: int | None = None) -> str:
    if sprite_id == 32 and animation_group == 2:
        return "UI相关"
    if sprite_id == 32:
        return "技能特效"
    if sprite_id == 38 and animation_group == 1:
        return "其他"
    return MANUAL_CATEGORIES.get(sprite_id, "未分类")


def asset_folder(
    sprite_id: int,
    category: str,
    is_map_sprite: bool = False,
) -> str:
    if category == "采集物" or sprite_id in MAP_UNIT_SPRITE_IDS:
        return "unit"
    if category in {"图标", "UI相关", "技能特效", "角色", "坐骑", "怪物", "中立生物", "宠物", "NPC"}:
        return ASSET_FOLDERS[category]
    if is_map_sprite:
        return "map"
    return ASSET_FOLDERS.get(category, "other")


def sprite_name(
    sprite_id: int,
    animation_group: int | None = None,
    action_count: int | None = None,
) -> str:
    if sprite_id == 32 and animation_group == 2:
        return "目标选中标记"
    if sprite_id == 38 and animation_group == 1:
        return "传送阵"
    if sprite_id == 108 and animation_group is not None and animation_group >= 5:
        return "熔岩龟"
    if sprite_id == 119 and animation_group is not None and animation_group >= 5:
        return "披甲大力士"
    if (
        sprite_category(sprite_id, animation_group) == "怪物"
        and action_count == 5
        and animation_group is not None
    ):
        return f"{animation_group}{('待机', '移动', '攻击', '受击', '死亡')[animation_group]}"
    return MANUAL_NAMES.get(sprite_id, "未分类")


def is_role_unarmed_group(sprite_id: int, animation_group: int) -> bool:
    return sprite_id in ROLE_SPRITE_CONFIGS and any(
        start <= animation_group < end
        for start, end in ROLE_UNARMED_GROUP_RANGES
    )


def role_animation_details(
    sprite_id: int,
    animation_group: int,
) -> dict[str, str | int] | None:
    config = ROLE_SPRITE_CONFIGS.get(sprite_id)
    if config is None:
        return None

    for start, end, gender, weapon_index in ROLE_SET_BLOCKS:
        if start <= animation_group < end:
            profession, first_weapon, second_weapon = config
            action_number = animation_group - start
            action = ROLE_ACTION_NAMES.get(action_number, "动作待确认")
            return {
                "profession": profession,
                "gender": gender,
                "weapon": (first_weapon, second_weapon)[weapon_index],
                "action": action,
                "actionNumber": action_number,
                "setStart": start,
                "configurationStatus": (
                    "按用户确认" if sprite_id == 76 else "按图像推断"
                ),
                "actionStatus": "按用户动作表",
                "classificationEvidence": (
                    f"user-confirmed weapon mapping and visual comparison of "
                    f"sprite {sprite_id} animation group {animation_group}; "
                    f"set starts at group {start}"
                    if sprite_id == 76
                    else (
                        f"visual comparison of sprite {sprite_id} animation "
                        f"group {animation_group}; set starts at group {start}"
                    )
                ),
            }
    return None


def group_filename(
    sprite_id: int,
    animation_group: int,
    action_count: int | None = None,
) -> str:
    role_details = role_animation_details(sprite_id, animation_group)
    if role_details:
        return (
            f"{role_details['profession']}-{role_details['gender']}-"
            f"{role_details['weapon']}-{role_details['action']}-"
            f"{role_details['actionNumber']:02d}.png"
        )
    if sprite_id in {108, 119} and animation_group >= 5:
        action_index = animation_group - 4
        return f"sprite-{sprite_id:03d}-{sprite_name(sprite_id, animation_group)}-{action_index:02d}.png"
    name = sprite_name(sprite_id, animation_group, action_count)
    if sprite_category(sprite_id, animation_group) == "怪物" and action_count == 5:
        return f"sprite-{sprite_id:03d}-{MANUAL_NAMES[sprite_id]}-{name}.png"
    return f"sprite-{sprite_id:03d}-{name}-{animation_group:02d}.png"


def component_filename(sprite_id: int, component_id: int) -> str:
    return f"sprite-{sprite_id:03d}-{sprite_name(sprite_id)}-组件-{component_id:04d}.png"
