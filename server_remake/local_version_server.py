import hashlib
import hmac
import json
import os
import random
import socket
import struct
import threading
import time
from pathlib import Path

from local_game_data import (
    ARTIFACT_NAMES,
    GEAR_NAMES,
    GEAR_STATS,
    MAPS,
    MAPS_BY_ZONE,
    NPCS,
)


HOST = "127.0.0.1"
PORT = 28000
VERSION_CHECK_COMMAND = 265
LOGIN_REQUEST_COMMAND = 257
LOGIN_RESPONSE_COMMAND = 258
RECONNECT_COMMAND = 400
HEARTBEAT_COMMAND = 0xFFFF
ZONE_AUTH_COMMAND = 513
CHARACTER_LIST_COMMAND = 514
CHARACTER_SELECT_COMMAND = 515
CHARACTER_ENTER_COMMAND = 523
WORLD_LOGIN_RESPONSE_COMMAND = 401
SCENE_UPDATE_COMMAND = 521
NAVIGATION_MAP_REQUEST_COMMAND = 3006
MAP_ENTRY_COMMAND = 2112
MAP_ENTRY_ACK_COMMAND = 2116
PLAYER_STATUS_COMMAND = 622
PLAYER_ATTRIBUTES_COMMAND = 623
PLAYER_CONTAINER_COMMAND = 950
PLAYER_POSITION_UPDATE_COMMAND = 602
SPRITE_CACHE_COMMAND = 2702
ASSET_CACHE_COMMAND = 2706
MAP_LOADED_COMMAND = 525
WORLD_MAP_TELEPORT_COMMAND = 600
MAP_NAVIGATION_FLAG_COMMAND = 1401
PLAYER_PROPERTY_UPDATE_COMMAND = 624
LOGIN_FAILURE = 2
ZONE_AUTH_MAGIC = b"240W"
GAME_PROTOCOL_VERSION = 11400
ACCOUNT_NAME = "1"
ACCOUNT_PASSWORD_DIGEST = hashlib.md5(b"1").hexdigest().encode("ascii")
SESSION_ID = 1
LOGIN_SERVER_ID = 1
SESSION_KEY = b"local-session"
ZONE_ID = 1
ZONE_NAME = "本地测试区"
ZONE_HOST = "127.0.0.1"
ZONE_PORT = PORT
ZONE_STATUS = 0
CHARACTER_SLOT = 0
CHARACTER_NAME = "天灵男"
CHARACTER_ID = 1
CHARACTER_GENDER = 0
CHARACTER_CLASS = 2
CHARACTER_LEVEL = 10
CHARACTER_FACTION = 0
CHARACTER_MODEL = 0
CHARACTER_STATUS = 0
CHARACTER_ATTRIBUTES = (0,) * 20
CHARACTER_ATTRIBUTE_FLAGS = 0
PLAYER_BASE_ATTRIBUTES = (0, 0, 0, 0, 100, 100, 0, 0, 0, 0, 0, 0, 0, 0, 0)
PLAYER_CURRENT_HP = 100
PLAYER_CURRENT_MP = 100
PLAYER_BAG_CAPACITY = 30
PLAYER_EQUIPMENT_SLOT_COUNT = 22
PLAYER_MAX_LEVEL = 70
PLAYER_EXP_TABLE = (
    0, 0, 120, 280, 480, 720, 1000, 1320, 1680, 2080, 2520, 3100, 3720,
    4380, 5080, 5820, 6600, 7420, 8280, 9180, 10120, 11300, 12520, 13780,
    15080, 16420, 17800, 19220, 20680, 22180, 23720, 25600, 27520, 29480,
    31480, 33520, 35600, 37720, 39880, 42080, 44320, 47100, 49920, 52780,
    55680, 58620, 61600, 64620, 67680, 70780, 73920, 77900, 81920, 85980,
    90080, 94220, 98400, 102620, 106880, 111180, 115520, 120700, 125920,
    131180, 136480, 141820, 147200, 152620, 158080, 163580, 169120,
)
STARTING_SPAWN_X = 696
STARTING_SPAWN_Y = 528
PLAYER_STATE_LOCK = threading.RLock()
PLAYER_STATE = {
    "level": CHARACTER_LEVEL,
    "exp": 0,
    "hp": PLAYER_CURRENT_HP,
    "mp": PLAYER_CURRENT_MP,
    "gold": 100,
    "job": CHARACTER_CLASS,
    "name": CHARACTER_NAME,
    "x": STARTING_SPAWN_X,
    "y": STARTING_SPAWN_Y,
    "attack": 20,
    "defense": 10,
    "magic_attack": 20,
    "magic_defense": 10,
    "crit": 200,
    "hit": 200,
    "dodge": 100,
    "block": 100,
    "toughness": 100,
    "penetration": 100,
    "max_hp": PLAYER_CURRENT_HP,
    "max_mp": PLAYER_CURRENT_MP,
}
PLAYER_INVENTORY = []
PLAYER_EQUIPMENT = []
PLAYER_DEPOT = []
PLAYER_INITIALIZED = False
PLAYER_SKILL_COOLDOWNS = {}
PLAYER_BUFFS = {}
PLAYER_IN_GAME = False
PLAYER_AUTO_TARGET = None
CURRENT_MAP_INDEX = 5
MONSTERS = []
MONSTER_LOCK = threading.RLock()
SAVE_FILE = Path(os.environ.get(
    "HTOL_SAVE_FILE",
    str(Path(__file__).with_name("saves") / "htol_save.json"),
))
MONSTER_SPAWNS = (
    (7, 9), (9, 9), (11, 9), (13, 9), (15, 9), (12, 11),
    (5, 12), (7, 13), (10, 12), (14, 12), (16, 11), (18, 10),
    (4, 8), (18, 14), (3, 14), (19, 8),
)
MONSTER_SPRITES = (85, 86, 87, 88, 89, 90)
MONSTER_NAMES = ("小鸡", "野兔", "山贼", "狼", "鹿", "蝙蝠")
SHOP_STOCK = (
    {"def_id": 1, "name": "金创药", "view_id": 0, "price": 10},
    {"def_id": 2, "name": "回灵药", "view_id": 2, "price": 15},
    {"def_id": 3, "name": "解毒草", "view_id": 30, "price": 20},
    {"def_id": 4, "name": "回城符", "view_id": 17, "price": 50},
)
NPC_DIALOGS = (
    "欢迎来到定远城。城内的商店、铁匠与传送阵都可为你所用。",
    "金创药、回灵药，都是出城保命的东西。",
    "装备需要修理时，尽管来找我。",
    "多余的材料可以存在这里，出门在外轻装上路。",
    "想去其他地界，可以使用传送阵。",
    "在护城阵法的庇护下打坐，可以安心修炼。",
    "多余的装备可以出售换取金币。",
)
QUALITY_NAMES = ("", "蓝", "紫", "橙", "青", "神器")
QUALITY_MULTIPLIERS = (1.0, 1.25, 1.55, 1.9, 2.3, 3.2)
SLOT_UI_POSITIONS = (0, 1, 2, 3, 6, 5, 5, 6, 7, 8)
SLOT_KEY_POSITIONS = tuple(range(10))
SLOT_VIEW_BY_JOB = (
    (11, 12, 8, 10, 29, 37, 37, 9, 3, 39),
    (16, 17, 13, 15, 29, 37, 37, 14, 4, 39),
    (21, 22, 18, 20, 29, 37, 37, 19, 5, 39),
)
SKILL_LISTS = (
    (
        (1001, "英勇冲锋", 0, 15, 12000, 0, 3),
        (1002, "大力挥击", 1, 12, 6000, 0, 3),
        (1003, "利刃风暴", 2, 30, 18000, 0, 2),
        (1004, "奋力一击", 3, 20, 15000, 0, 3),
        (1005, "嗜血攻击", 4, 18, 12000, 0, 3),
        (1006, "狂暴之血", 5, 20, 45000, 0, 5),
        (1007, "大地守护", 6, 25, 10000, 0, 5),
        (1008, "嗜血杀戮", 7, 30, 30000, 0, 3),
        (1009, "战神之握", 8, 20, 20000, 0, 3),
    ),
    (
        (2001, "三连射", 0, 10, 6000, 0, 6),
        (2002, "毒箭", 1, 15, 15000, 0, 6),
        (2003, "爆裂箭", 2, 30, 20000, 800, 2),
        (2004, "会心术", 3, 20, 60000, 0, 5),
        (2005, "冰箭", 4, 22, 23000, 0, 6),
        (2006, "定神箭", 5, 15, 18000, 0, 6),
        (2007, "海纳百川", 6, 25, 45000, 0, 5),
        (2008, "隐遁", 7, 20, 60000, 0, 5),
    ),
    (
        (3001, "天火术", 0, 15, 6000, 0, 6),
        (3002, "落雷术", 1, 30, 15000, 0, 6),
        (3003, "冰暴术", 2, 22, 20000, 0, 6),
        (3004, "寒冰甲", 3, 20, 30000, 0, 5),
        (3005, "回天之术", 4, 30, 25000, 0, 6),
        (3006, "雷电束缚", 5, 28, 30000, 0, 6),
        (3007, "冰冻之环", 6, 30, 25000, 0, 2),
        (3008, "陨石术", 7, 40, 45000, 0, 6),
    ),
)
SKILL_EFFECTS = {
    1001: (4, 150, 0, 2000),
    1002: (0, 180, 0, 0),
    1003: (1, 120, 0, 0),
    1004: (0, 250, 0, 0),
    1005: (8, 160, 0, 0),
    1006: (2, 0, 30, 10000),
    1007: (2, 0, 20, 600000),
    1008: (8, 150, 0, 0),
    1009: (5, 120, 0, 2000),
    2001: (0, 120, 0, 0),
    2002: (6, 150, 0, 0),
    2003: (1, 110, 0, 0),
    2004: (2, 0, 60, 10000),
    2005: (4, 207, 0, 2500),
    2006: (5, 100, 0, 3000),
    2007: (3, 0, 0, 15000),
    2008: (2, 0, 60, 6000),
    3001: (0, 150, 0, 0),
    3002: (1, 120, 0, 0),
    3003: (4, 180, 0, 1500),
    3004: (2, 0, 25, 600000),
    3005: (3, 0, 0, 4000),
    3006: (7, 120, 0, 6000),
    3007: (1, 110, 0, 0),
    3008: (1, 200, 0, 0),
}
STARTING_WORLD_NODE_ID = 6
STARTING_ZONE_ID = 12
STARTING_MAP_RES_ID = 6
STARTING_SCENE_NAME = "定远城"
PLAYER_POSITION_LOCK = threading.Lock()
PLAYER_POSITION = {
    "zone_id": STARTING_ZONE_ID,
    "map_id": STARTING_ZONE_ID,
    "world_map_id": STARTING_WORLD_NODE_ID,
    "map_res_id": STARTING_MAP_RES_ID,
    "x": STARTING_SPAWN_X,
    "y": STARTING_SPAWN_Y,
}
MAX_FRAME_SIZE = 65535
MAX_FIELD_SIZE = 4096
CIPHER_KEY = bytes.fromhex(
    "3a74552b6144792e3e70194c1e0d481e66091a33022c25101d3f0f69435e6b52017a14272741065b280e2a627d634934563e1a7609783d245f3b4b6c785b3d4c5e6f5d3c7e4c3e3734122e0e373f371b1e42405e6d157e7b6b451e4b2f792d5c1e7d1d6c2b5038040f4a2e226b6f2a4a1a4d506d5f1e76393d4a695915014d395a576d513a7d6d283f2b724c716e5d1b2d3c29281955443c4d4a255d152a6c2a1b5f3f19285f283b112f675f514e62192c3e64327e46690810243f660a1d2f3e4d407f5b7e45445d081b2e47633e1b704d57715d085c6a64281d044812224b381b4c2b5d1e4b7b297e6f3c7c49291b382a677c4f273a3f4c2c2a7125611b591d"
)

with (Path(__file__).with_name("analysis") / "map_catalog.json").open("r", encoding="utf-8") as catalog_file:
    MAP_CATALOG = json.load(catalog_file)
MAPS_BY_INDEX = {entry["map_index"]: entry for entry in MAP_CATALOG["maps"]}
STARTING_MAP_WIDTH = MAPS_BY_INDEX[STARTING_MAP_RES_ID]["width_pixels"]
STARTING_MAP_HEIGHT = MAPS_BY_INDEX[STARTING_MAP_RES_ID]["height_pixels"]

with Path(__file__).with_name("local_text_tables.json").open("r", encoding="utf-8") as text_file:
    LOCAL_TEXT_TABLES = json.load(text_file)
WORLD_MAP_NAMES = next(
    table["entries"]
    for table in LOCAL_TEXT_TABLES["tables"]
    if table["table_id"] == 3
)
WORLD_NODE_TO_ZONE_ID = {
    1: 7,
    2: 8,
    3: 9,
    4: 10,
    5: 11,
    6: 12,
    7: 13,
    8: 14,
    9: 15,
    10: 16,
    11: 17,
    12: 18,
    13: 19,
    14: 74,
    15: 75,
    16: 76,
    17: 78,
    18: 79,
    19: 80,
    20: 83,
    21: 84,
    22: 85,
    23: 86,
    24: 87,
    25: 88,
    26: 89,
    27: 90,
    28: 91,
    29: 92,
    30: 93,
    31: 94,
    32: 95,
    33: 96,
    34: 97,
    35: 98,
    36: 107,
    37: 108,
    38: 109,
    39: 110,
    40: 111,
    41: 112,
    42: 113,
    43: 114,
    44: 115,
    45: 116,
    46: 117,
    47: 118,
    48: 119,
    49: 120,
}
ZONE_TO_MAP_RES_ID = {
    7: 6,
    8: 9,
    9: 20,
    10: 13,
    11: 12,
    12: 6,
    13: 29,
    14: 28,
    15: 10,
    16: 21,
    17: 19,
    18: 17,
    19: 22,
    20: 18,
    21: 9,
    22: 23,
    23: 10,
    24: 27,
    96: 28,
}
MAP_RES_ID_TO_DIMENSIONS = {
    map_res_id: (
        MAPS_BY_INDEX[map_res_id]["width_pixels"],
        MAPS_BY_INDEX[map_res_id]["height_pixels"],
    )
    for map_res_id in set(ZONE_TO_MAP_RES_ID.values())
}


def transform(data, operation):
    return bytes(
        (value + operation * CIPHER_KEY[index & 0xFF]) & 0xFF
        for index, value in enumerate(data)
    )


def receive_exact(connection, size):
    data = bytearray()
    while len(data) < size:
        chunk = connection.recv(size - len(data))
        if not chunk:
            return None
        data.extend(chunk)
    return bytes(data)


def receive_frame(connection):
    encrypted_header = receive_exact(connection, 4)
    if encrypted_header is None:
        return None

    header = transform(encrypted_header, -1)
    frame_size = struct.unpack_from("<H", header)[0]
    if frame_size < 4 or frame_size > MAX_FRAME_SIZE:
        raise ValueError("Invalid frame length: {}".format(frame_size))

    command = struct.unpack_from("<H", header, 2)[0]
    body_size = frame_size - 4
    if command == PLAYER_POSITION_UPDATE_COMMAND and frame_size == 14:
        body_size = 4

    encrypted_body = receive_exact(connection, body_size)
    if encrypted_body is None:
        return None
    return transform(encrypted_header + encrypted_body, -1)


def make_response(command, payload):
    frame = struct.pack("<HH", len(payload) + 4, command) + payload
    if len(frame) > MAX_FRAME_SIZE:
        raise ValueError("Response exceeds the client frame limit")
    return transform(frame, 1)


def latest_version_response():
    return make_response(VERSION_CHECK_COMMAND, bytes((0, 0, 0)))


def read_sized_field(frame, offset, length_size, limit=MAX_FIELD_SIZE):
    if offset + length_size > len(frame):
        raise ValueError("Truncated field length")
    if length_size == 1:
        field_size = frame[offset]
    elif length_size == 4:
        field_size = struct.unpack_from("<I", frame, offset)[0]
    else:
        raise ValueError("Unsupported field-length size")
    offset += length_size
    if field_size > limit or offset + field_size > len(frame):
        raise ValueError("Invalid field length")
    return frame[offset:offset + field_size], offset + field_size


def parse_login_request(frame):
    offset = 4
    username, offset = read_sized_field(frame, offset, 4)
    password_digest, offset = read_sized_field(frame, offset, 4)

    if offset + 10 > len(frame):
        raise ValueError("Truncated main-login metadata")
    client_version = struct.unpack_from("<I", frame, offset)[0]
    offset += 4
    login_mode = frame[offset]
    client_id = struct.unpack_from("<I", frame, offset + 1)[0]
    offset += 5
    device_id, offset = read_sized_field(frame, offset, 1, limit=255)
    if offset + 4 != len(frame):
        raise ValueError("Invalid main-login request trailer")
    client_type = struct.unpack_from("<I", frame, offset)[0]

    return {
        "username": username,
        "password_digest": password_digest,
        "client_version": client_version,
        "login_mode": login_mode,
        "client_id": client_id,
        "device_id": device_id,
        "client_type": client_type,
    }


def authenticate(frame):
    request = parse_login_request(frame)
    return (
        hmac.compare_digest(
            request["username"],
            ACCOUNT_NAME.encode("utf-8"),
        )
        and hmac.compare_digest(
            request["password_digest"],
            ACCOUNT_PASSWORD_DIGEST,
        )
    )


def parse_zone_auth_request(frame):
    if len(frame) < 8:
        raise ValueError("Truncated zone-auth request")
    offset = 4
    character_id = struct.unpack_from("<I", frame, offset)[0]
    offset += 4
    session_key, offset = read_sized_field(frame, offset, 4)
    magic, offset = read_sized_field(frame, offset, 4)
    if offset + 8 != len(frame):
        raise ValueError("Invalid zone-auth request length")

    login_server_id, protocol_version = struct.unpack_from("<II", frame, offset)
    if magic != ZONE_AUTH_MAGIC:
        raise ValueError("Invalid zone-auth marker")
    if protocol_version != GAME_PROTOCOL_VERSION:
        raise ValueError("Unsupported game protocol version")

    return {
        "character_id": character_id,
        "session_key": session_key,
        "login_server_id": login_server_id,
    }


def encode_server_entry():
    name = ZONE_NAME.encode("utf-8")
    host = ZONE_HOST.encode("utf-8")
    if len(name) > 255 or len(host) > 255:
        raise ValueError("Zone name or host exceeds the client string limit")
    if not 0 <= ZONE_PORT <= 32767:
        raise ValueError("Zone port must fit the client's signed short reader")
    if not 0 <= ZONE_STATUS <= 255:
        raise ValueError("Zone status must fit in one byte")

    return b"".join((
        struct.pack("<I", ZONE_ID),
        struct.pack("<B", len(name)),
        name,
        struct.pack("<B", len(host)),
        host,
        struct.pack("<H", ZONE_PORT),
        struct.pack("<B", len(host)),
        host,
        struct.pack("<H", ZONE_PORT),
        struct.pack("<B", ZONE_STATUS),
    ))


def login_success_response():
    if len(SESSION_KEY) > MAX_FIELD_SIZE:
        raise ValueError("Session key exceeds the client field limit")
    payload = b"".join((
        b"\x01",
        struct.pack("<I", SESSION_ID),
        struct.pack("<B", LOGIN_SERVER_ID),
        struct.pack("<I", len(SESSION_KEY)),
        SESSION_KEY,
        b"\x01",
        encode_server_entry(),
    ))
    return make_response(LOGIN_RESPONSE_COMMAND, payload)


def zone_auth_success_response():
    return make_response(ZONE_AUTH_COMMAND, bytes((1,)))


def encode_character_entry():
    name = CHARACTER_NAME.encode("utf-8")
    if len(name) > 33:
        raise ValueError("Character name exceeds the client field limit")
    if len(CHARACTER_ATTRIBUTES) != 20:
        raise ValueError("The client expects exactly 20 character attributes")
    if not 0 <= CHARACTER_SLOT <= 2:
        raise ValueError("Character slot must be in the client's 0-2 range")
    if CHARACTER_GENDER not in (0, 1):
        raise ValueError("Character gender must match the client's 0/1 selector")
    if not 0 <= CHARACTER_CLASS <= 2:
        raise ValueError("Character class must match the client's 0-2 selector")
    if not 0 <= CHARACTER_FACTION <= 2:
        raise ValueError("Character faction must match the client's 0-2 selector")

    return b"".join((
        struct.pack("<B", CHARACTER_SLOT),
        name.ljust(33, b"\x00"),
        struct.pack("<BB", CHARACTER_STATUS, CHARACTER_GENDER),
        struct.pack(
            "<IIIII",
            CHARACTER_ID,
            CHARACTER_CLASS,
            CHARACTER_LEVEL,
            CHARACTER_FACTION,
            CHARACTER_MODEL,
        ),
        struct.pack("<20hI", *CHARACTER_ATTRIBUTES, CHARACTER_ATTRIBUTE_FLAGS),
        struct.pack("<B", len(name)),
        name,
    ))


def character_list_response():
    payload = b"".join((
        b"\x01",
        struct.pack("<I", 0),
        encode_character_entry(),
    ))
    return make_response(CHARACTER_LIST_COMMAND, payload)


def parse_character_select_request(frame):
    if len(frame) != 8:
        raise ValueError("Invalid character-select request length")
    character_id = struct.unpack_from("<I", frame, 4)[0]
    if character_id != CHARACTER_ID:
        raise ValueError("Unknown character ID: {}".format(character_id))
    return character_id


def character_select_response():
    return make_response(CHARACTER_SELECT_COMMAND, bytes((1,)))


def character_enter_acceptance_response():
    return make_response(CHARACTER_ENTER_COMMAND, bytes((1,)))


def parse_character_enter_request(frame):
    if len(frame) != 16:
        raise ValueError("Invalid character-entry request length")
    character_id, selection_option, reserved = struct.unpack_from("<III", frame, 4)
    if character_id != CHARACTER_ID:
        raise ValueError("Unknown character ID: {}".format(character_id))
    if selection_option != 0xFFFFFFFF or reserved != 0:
        raise ValueError("Invalid character-entry request fields")
    return character_id


def parse_reconnect_request(frame):
    if len(frame) < 25:
        raise ValueError("Truncated reconnect request")
    session_id, character_id, zone_id = struct.unpack_from("<III", frame, 4)
    x, y = struct.unpack_from("<hh", frame, 16)
    reconnect_flag = frame[20]
    key_size = struct.unpack_from("<I", frame, 21)[0]
    if key_size > MAX_FIELD_SIZE or len(frame) != 25 + key_size:
        raise ValueError("Invalid reconnect key length")
    session_key = frame[25:]
    if session_id != SESSION_ID:
        raise ValueError("Invalid reconnect session ID")
    if character_id != CHARACTER_ID:
        raise ValueError("Unknown reconnect character ID")
    if session_key != SESSION_KEY:
        raise ValueError("Invalid reconnect session key")
    map_res_id = get_map_res_id(zone_id)
    validate_player_position(map_res_id, x, y)
    return {
        "character_id": character_id,
        "zone_id": zone_id,
        "map_res_id": map_res_id,
        "x": x,
        "y": y,
        "reconnect_flag": reconnect_flag,
    }


def get_map_res_id(zone_id):
    map_res_id = ZONE_TO_MAP_RES_ID.get(zone_id)
    if map_res_id is None:
        raise ValueError("Unknown map resource for zone ID: {}".format(zone_id))
    if map_res_id not in MAPS_BY_INDEX:
        raise ValueError("Unknown map resource ID: {}".format(map_res_id))
    return map_res_id


def get_map_dimensions(map_res_id):
    map_entry = MAPS_BY_INDEX.get(map_res_id)
    if map_entry is None:
        raise ValueError("Unknown map resource ID: {}".format(map_res_id))
    return map_entry["width_pixels"], map_entry["height_pixels"]


def get_world_map_name(map_id):
    if 0 <= map_id < len(MAPS):
        return MAPS[map_id].name
    if 0 <= map_id < len(WORLD_MAP_NAMES):
        return WORLD_MAP_NAMES[map_id]
    return "地图{}".format(map_id)


def get_map_index(zone_id):
    map_data = MAPS_BY_ZONE.get(zone_id)
    if map_data is None:
        return None
    return map_data.index


def validate_player_position(map_res_id, x, y):
    width, height = get_map_dimensions(map_res_id)
    if not 0 <= x < width or not 0 <= y < height:
        raise ValueError("Player position is outside the current map")


def set_player_position(zone_id, x, y, world_node_id=None, map_res_id=None):
    global CURRENT_MAP_INDEX
    if map_res_id is None:
        map_res_id = get_map_res_id(zone_id)
    validate_player_position(map_res_id, x, y)
    if world_node_id is None:
        world_node_id = next(
            (node_id for node_id, candidate_zone in WORLD_NODE_TO_ZONE_ID.items()
             if candidate_zone == zone_id),
            STARTING_WORLD_NODE_ID,
        )
    with PLAYER_POSITION_LOCK:
        PLAYER_POSITION.update({
            "zone_id": zone_id,
            "map_id": zone_id,
            "world_node_id": world_node_id,
            "world_map_id": world_node_id,
            "map_res_id": map_res_id,
            "x": x,
            "y": y,
        })
        map_index = get_map_index(zone_id)
        if map_index is not None:
            CURRENT_MAP_INDEX = map_index
        PLAYER_STATE.update({"x": x, "y": y})


def get_player_position():
    with PLAYER_POSITION_LOCK:
        return dict(PLAYER_POSITION)


def parse_player_position_update(frame):
    if len(frame) != 8:
        raise ValueError("Invalid player-position update length")
    x, y = struct.unpack_from("<hh", frame, 4)
    position = get_player_position()
    validate_player_position(position["map_res_id"], x, y)
    return {
        "zone_id": position["zone_id"],
        "map_res_id": position["map_res_id"],
        "world_node_id": position["world_node_id"],
        "x": x,
        "y": y,
    }


def parse_world_map_teleport_request(frame):
    if len(frame) != 8:
        raise ValueError("Invalid world-map teleport request length")
    world_node_id = struct.unpack_from("<I", frame, 4)[0]
    zone_id = WORLD_NODE_TO_ZONE_ID.get(world_node_id)
    if zone_id is None:
        raise ValueError("Unknown world-map node ID: {}".format(world_node_id))
    map_res_id = get_map_res_id(zone_id)
    width, height = get_map_dimensions(map_res_id)
    return {
        "zone_id": zone_id,
        "map_id": zone_id,
        "map_res_id": map_res_id,
        "world_node_id": world_node_id,
        "x": width // 2,
        "y": height // 2,
    }


def parse_navigation_map_request(frame):
    if len(frame) != 16:
        raise ValueError("Invalid navigation map request length")
    character_id = struct.unpack_from("<I", frame, 12)[0]
    if character_id not in (0, CHARACTER_ID):
        raise ValueError("Unknown character ID in navigation map request")
    return get_player_position()


def world_login_success_response():
    return make_response(WORLD_LOGIN_RESPONSE_COMMAND, bytes((3,)))


def scene_update_response(world_node_id=None):
    position = get_player_position()
    zone_id = position["zone_id"]
    map_index = get_map_index(zone_id)
    map_id = position["map_id"] if map_index is None else map_index
    world_node_id = position.get("world_node_id", STARTING_WORLD_NODE_ID) if world_node_id is None else world_node_id
    scene_name_text = MAPS[map_index].name if map_index is not None else get_world_map_name(world_node_id)
    scene_name = scene_name_text.encode("utf-8")
    if len(scene_name) > 127:
        raise ValueError("Scene name exceeds the client's signed-byte string limit")
    sprite_ids = tuple(MONSTER_SPRITES) + tuple(npc.sprite_id for npc in NPCS)
    if len(sprite_ids) > 40:
        raise ValueError("Scene sprite list exceeds the client's limit")
    payload = b"".join((
        struct.pack("<I", 0),
        struct.pack("<B", len(sprite_ids)),
        b"".join(struct.pack("<H", sprite_id) for sprite_id in sprite_ids),
        struct.pack(
            "<III",
            zone_id,
            map_id,
            world_node_id,
        ),
        b"\x00",
        bytes((position["map_res_id"] & 0xFF,)),
        struct.pack("<I", 0),
        b"\x00",
        struct.pack("<B", len(scene_name)),
        scene_name,
        b"\x00",
        b"\x00",
    ))
    return make_response(SCENE_UPDATE_COMMAND, payload)


def map_entry_response():
    position = get_player_position()
    return make_response(
        MAP_ENTRY_COMMAND,
        struct.pack(
            "<Ihh",
            position["zone_id"],
            position["x"],
            position["y"],
        ),
    )


def player_attributes_response():
    with PLAYER_STATE_LOCK:
        attributes = (
            PLAYER_STATE["attack"],
            PLAYER_STATE["defense"],
            PLAYER_STATE["magic_attack"],
            PLAYER_STATE["magic_defense"],
            PLAYER_STATE["max_hp"],
            PLAYER_STATE["max_mp"],
            PLAYER_STATE["hit"],
            PLAYER_STATE["dodge"],
            PLAYER_STATE["block"],
            PLAYER_STATE["crit"],
            PLAYER_STATE["toughness"],
            PLAYER_STATE["penetration"],
            100,
            100,
            100,
        )
    return make_response(
        PLAYER_ATTRIBUTES_COMMAND,
        struct.pack("<HHHHI10H", *attributes),
    )


def player_status_response():
    with PLAYER_STATE_LOCK:
        level = PLAYER_STATE["level"]
        exp = PLAYER_STATE["exp"]
        hp = PLAYER_STATE["hp"]
        mp = PLAYER_STATE["mp"]
        gold = PLAYER_STATE["gold"]
        max_exp = get_next_level_exp(level) - get_level_base_exp(level)
    payload = b"".join((
        struct.pack(
            "<BIIHB",
            level,
            max(0, exp - get_level_base_exp(level)),
            hp,
            mp,
            0,
        ),
        struct.pack("<IIIII", gold, 0, 0, 0, 0),
        bytes((0, 0, 0, 0, 0)),
        struct.pack("<6h", 0, 0, 0, 0, 0, 0),
        struct.pack("<III", max_exp, 0, SESSION_ID),
        b"\x00\x00",
    ))
    return make_response(PLAYER_STATUS_COMMAND, payload)


def get_level_base_exp(level):
    return PLAYER_EXP_TABLE[min(max(0, level), PLAYER_MAX_LEVEL)]


def get_next_level_exp(level):
    level = min(max(1, level), PLAYER_MAX_LEVEL)
    if level >= PLAYER_MAX_LEVEL:
        return PLAYER_EXP_TABLE[PLAYER_MAX_LEVEL]
    return PLAYER_EXP_TABLE[level + 1]


def player_hp_mp_response():
    with PLAYER_STATE_LOCK:
        payload = struct.pack(
            "<BIIHH",
            0x0F,
            PLAYER_STATE["hp"],
            PLAYER_STATE["max_hp"],
            PLAYER_STATE["mp"],
            PLAYER_STATE["max_mp"],
        )
    return make_response(PLAYER_STATUS_UPDATE_COMMAND, payload)


PLAYER_STATUS_UPDATE_COMMAND = 2111


def player_full_response():
    with PLAYER_STATE_LOCK:
        name = PLAYER_STATE["name"].encode("utf-8")
        flags = ((PLAYER_STATE["level"] & 0xFF) << 7) | ((CHARACTER_GENDER & 1) << 18) | (
            (CHARACTER_FACTION & 7) << 19
        ) | ((PLAYER_STATE["job"] & 7) << 22)
    if len(name) > MAX_FIELD_SIZE:
        raise ValueError("Player name exceeds the client string limit")
    payload = b"".join((
        struct.pack(
            "<IIHHI",
            CHARACTER_ID,
            get_player_position()["zone_id"],
            PLAYER_STATE["x"],
            PLAYER_STATE["y"],
            flags,
        ),
        bytes((0, 100, 100)),
        struct.pack("<I", len(name)),
        name,
        struct.pack("<HHI", 7000, 0, 0),
    ))
    return make_response(2102, payload)


def _create_consumable(def_id, name, view_id, count, position):
    return {
        "pos": position,
        "def_id": def_id,
        "name": name,
        "view_id": view_id,
        "count": count,
        "type": 2,
        "equip_pos": 255,
        "quality": 0,
        "req_level": 1,
        "job_req": 0,
        "stats": (0,) * 15,
        "score": 0,
        "sub_type": 0,
    }


def _create_equipment(job, level_index, slot, quality=0):
    if job not in range(3) or level_index not in range(13) or slot not in range(10):
        raise ValueError("Invalid generated equipment coordinates")
    quality = min(max(quality, 0), 5)
    stats = tuple(
        max(1, int(value * QUALITY_MULTIPLIERS[quality] + 0.5)) if value else 0
        for value in GEAR_STATS[(job, level_index, slot)]
    )
    base_name = (
        ARTIFACT_NAMES[(job, slot)]
        if quality == 5
        else GEAR_NAMES[(job, level_index, slot)]
    )
    name = base_name
    if 0 < quality < 5:
        name += "·" + QUALITY_NAMES[quality]
    level = 10 + level_index * 5
    price = 10 + level * 6 + quality * 40
    return {
        "pos": SLOT_KEY_POSITIONS[slot],
        "def_id": 2000 + level * 10 + slot,
        "name": name,
        "view_id": SLOT_VIEW_BY_JOB[job][slot],
        "count": 1,
        "type": 1,
        "equip_pos": slot,
        "quality": quality,
        "req_level": level,
        "job_req": job,
        "stats": stats,
        "score": sum(stats) // 10,
        "sub_type": 0,
        "buy_price": price,
        "sell_price": price // 3,
    }


def _write_item(item):
    name = item["name"].encode("utf-8")
    if len(name) > MAX_FIELD_SIZE:
        raise ValueError("Item name exceeds the client string limit")
    quality = min(max(item["quality"], 0), 7)
    item_type = item["type"]
    price = item.get("buy_price", 10 + item["req_level"] * 6 + quality * 40)
    payload = bytearray()
    payload.extend(struct.pack("<BHII", item["pos"], item["def_id"], item_type | (quality << 18), item["sub_type"]))
    payload.extend(struct.pack("<I", len(name)))
    payload.extend(name)
    payload.extend(struct.pack(
        "<HBBII B I H",
        item["view_id"],
        item["req_level"],
        0,
        price,
        item.get("sell_price", price // 3),
        0,
        0,
        1,
    ))
    payload.extend(b" ")
    payload.append(0)
    if item_type == 1:
        slot = item["equip_pos"] if item["equip_pos"] in range(10) else 0
        socket_count = quality if quality > 0 else 0
        socket_count = min(socket_count, 5)
        packed = (item["job_req"] & 3) | ((socket_count & 7) << 2) | (
            (SLOT_UI_POSITIONS[slot] & 31) << 5
        )
        payload.extend(struct.pack("<IHHH", packed, 0, 1000, 1000))
        payload.extend(b"\x00\x00" * socket_count)
        for group in range(4):
            attributes = [
                (index, value)
                for index, value in enumerate(item["stats"])
                if value > 0
            ] if group == 0 else []
            if len(attributes) > 255:
                raise ValueError("Equipment has too many attributes")
            payload.append(len(attributes))
            for index, value in attributes:
                payload.extend(struct.pack("<BH", index, min(value, 0xFFFF)))
        payload.append(0)
        payload.extend(struct.pack("<IHB I", price, 0, 0, item["score"]))
    else:
        payload.extend(struct.pack("<BB", 99, min(max(item["count"], 0), 255)))
    return bytes(payload)


def ensure_initial_items():
    global PLAYER_INITIALIZED
    if PLAYER_INITIALIZED:
        return
    PLAYER_INITIALIZED = True
    PLAYER_INVENTORY.append(_create_consumable(1, "金创药", 0, 5, 0))
    PLAYER_EQUIPMENT.append(_create_equipment(CHARACTER_CLASS, 0, 8))
    refresh_player_stats()
    PLAYER_STATE["hp"] = PLAYER_STATE["max_hp"]
    PLAYER_STATE["mp"] = PLAYER_STATE["max_mp"]


def _refresh_item_positions():
    for position, item in enumerate(PLAYER_INVENTORY):
        item["pos"] = position
    for item in PLAYER_EQUIPMENT:
        item["pos"] = SLOT_KEY_POSITIONS[item["equip_pos"]]


def refresh_player_stats():
    with PLAYER_STATE_LOCK:
        level = PLAYER_STATE["level"]
        base_hp = 100 + level * 20
        base_mp = 100 + level * 5
        stats = [0] * 15
        for item in PLAYER_EQUIPMENT:
            for index, value in enumerate(item["stats"]):
                stats[index] += value
        PLAYER_STATE["max_hp"] = max(1, base_hp + stats[8])
        PLAYER_STATE["max_mp"] = max(1, base_mp + stats[9])
        PLAYER_STATE["attack"] = max(1, (20 + level * 3) + stats[0])
        PLAYER_STATE["magic_attack"] = max(1, (20 + level * 3) + stats[1])
        PLAYER_STATE["defense"] = 10 + stats[2]
        PLAYER_STATE["magic_defense"] = 10 + stats[3]
        PLAYER_STATE["crit"] = 200 + stats[4]
        PLAYER_STATE["hit"] = 200 + stats[5]
        PLAYER_STATE["dodge"] = 100 + stats[6]
        PLAYER_STATE["block"] = 100 + stats[7]
        PLAYER_STATE["toughness"] = 100 + stats[13]
        PLAYER_STATE["penetration"] = 100 + stats[14]
        PLAYER_STATE["hp"] = min(PLAYER_STATE["hp"], PLAYER_STATE["max_hp"])
        PLAYER_STATE["mp"] = min(PLAYER_STATE["mp"], PLAYER_STATE["max_mp"])


def player_container_data_response(container_type, capacity):
    if container_type not in (0, 1, 5):
        raise ValueError("Unsupported player container type")
    if container_type == 0:
        ensure_initial_items()
        items = PLAYER_INVENTORY
    elif container_type == 1:
        ensure_initial_items()
        items = PLAYER_EQUIPMENT
    else:
        items = PLAYER_DEPOT
    if len(items) > capacity:
        raise ValueError("Container contents exceed its advertised capacity")
    payload = bytearray((container_type, capacity, len(items)))
    for item in items:
        payload.extend(_write_item(item))
    return make_response(PLAYER_CONTAINER_COMMAND, bytes(payload))


def player_skills_response():
    job = PLAYER_STATE["job"]
    if job not in range(3):
        raise ValueError("Character job is outside the supported range")
    skills = SKILL_LISTS[job]
    payload = bytearray((len(skills),))
    icon_base = (0, 8, 16)[job]
    for skill_id, name, icon, mp_cost, cooldown, load_time, distance in skills:
        encoded_name = name.encode("utf-8")
        if len(encoded_name) > 255:
            raise ValueError("Skill name exceeds the client string limit")
        payload.extend(struct.pack("<HBB", skill_id, 1, 10))
        payload.extend(struct.pack("<B", len(encoded_name)))
        payload.extend(encoded_name)
        payload.extend(struct.pack(
            "<BBHHHHBBB",
            icon_base + icon,
            0,
            mp_cost,
            cooldown // 100,
            load_time // 100,
            distance,
            0,
            0,
            0,
        ))
    return make_response(1220, bytes(payload))


def map_entry_initialization_responses():
    ensure_initial_items()
    return (
        player_full_response(),
        player_hp_mp_response(),
        player_attributes_response(),
        player_status_response(),
        player_skills_response(),
        player_container_data_response(1, PLAYER_EQUIPMENT_SLOT_COUNT),
        player_container_data_response(0, PLAYER_BAG_CAPACITY),
        map_navigation_flag_response(False),
        player_navigation_property_response(False),
        *monster_initialization_responses(),
        make_response(633, build_npc_data()),
    )


def reset_monsters(map_index):
    map_data = MAPS[map_index]
    if map_data.safe_zone:
        with MONSTER_LOCK:
            MONSTERS.clear()
        return
    width, height = get_map_dimensions(map_data.map_res_id)
    with MONSTER_LOCK:
        MONSTERS.clear()
        for index, (grid_x, grid_y) in enumerate(MONSTER_SPAWNS):
            level = min(80, map_data.base_level + index % 5 + map_data.faction_bonus)
            monster_x = min(width - 1, grid_x * 24 + 12)
            monster_y = min(height - 1, grid_y * 24 + 12)
            max_hp = 60 + level * 40
            MONSTERS.append({
                "id": 5001 + index,
                "sprite_id": MONSTER_SPRITES[index % len(MONSTER_SPRITES)],
                "name": MONSTER_NAMES[index % len(MONSTER_NAMES)],
                "level": level,
                "max_hp": max_hp,
                "hp": max_hp,
                "x": monster_x,
                "y": monster_y,
                "alive": True,
                "respawn_at": 0.0,
            })


def build_monster_data(monster):
    name = monster["name"].encode("utf-8")
    flags = (monster["level"] & 0xFF) << 11
    hp_percent = 0 if monster["max_hp"] <= 0 else monster["hp"] * 100 // monster["max_hp"]
    return b"".join((
        struct.pack(
            "<IIHHIHBBBB",
            monster["id"],
            get_player_position()["zone_id"],
            monster["x"],
            monster["y"],
            flags,
            monster["sprite_id"],
            0,
            0,
            hp_percent,
            0,
        ),
        struct.pack("<I", len(name)),
        name,
    ))


def monster_initialization_responses():
    reset_monsters(CURRENT_MAP_INDEX)
    with MONSTER_LOCK:
        return tuple(
            make_response(2104, build_monster_data(monster))
            for monster in MONSTERS
            if monster["alive"]
        )


def build_npc_data():
    zone_id = get_player_position()["zone_id"]
    payload = bytearray((len(NPCS),))
    for npc in NPCS:
        name = npc.name.encode("utf-8")
        if len(name) > 255:
            raise ValueError("NPC name exceeds the client string limit")
        payload.extend(struct.pack(
            "<IIBBHBIhh",
            npc.character_id,
            600 + npc.index,
            0,
            0,
            npc.sprite_id,
            0,
            zone_id,
            npc.x,
            npc.y,
        ))
        payload.extend(struct.pack("<B", len(name)))
        payload.extend(name)
        payload.append(0)
    return bytes(payload)


def map_navigation_menu_response():
    payload = bytearray((0,))
    payload.extend(struct.pack("<II", 0, 0x2345))
    payload.append(len(MAPS))
    for map_data in MAPS:
        label = "{} {}级".format(map_data.name, map_data.base_level).encode("utf-8")
        if len(label) > 255:
            raise ValueError("Map label exceeds the client string limit")
        payload.extend(struct.pack("<I", 1000 + map_data.index))
        payload.append(len(label))
        payload.extend(label)
    payload.append(0)
    return make_response(3007, bytes(payload))


def parse_map_menu_selection(frame):
    if len(frame) < 17:
        raise ValueError("Truncated map menu selection")
    if struct.unpack_from("<H", frame)[0] != len(frame):
        raise ValueError("Invalid map menu selection frame length")
    menu_id = struct.unpack_from("<I", frame, 12)[0]
    argument_count = frame[16]
    if len(frame) != 17 + argument_count * 4:
        raise ValueError("Invalid map menu selection argument list")
    map_index = menu_id - 1000
    if map_index not in range(len(MAPS)):
        raise ValueError("Unknown map menu selection: {}".format(menu_id))
    return map_index


def player_reposition_response():
    position = get_player_position()
    with PLAYER_STATE_LOCK:
        hp = PLAYER_STATE["hp"]
        max_hp = PLAYER_STATE["max_hp"]
    return make_response(
        2110,
        struct.pack(
            "<IHHBIIHH",
            CHARACTER_ID,
            position["x"],
            position["y"],
            0,
            hp,
            max_hp,
            position["x"],
            position["y"],
        ),
    )


def teleport_to_map_index(map_index):
    if map_index not in range(len(MAPS)):
        raise ValueError("Unknown map index: {}".format(map_index))
    map_data = MAPS[map_index]
    width, height = get_map_dimensions(map_data.map_res_id)
    set_player_position(
        map_data.zone_id,
        width // 2,
        height // 2,
        map_index + 1,
        map_data.map_res_id,
    )
    reset_monsters(map_index)
    with PLAYER_STATE_LOCK:
        PLAYER_STATE["hp"] = PLAYER_STATE["max_hp"]
        PLAYER_STATE["mp"] = PLAYER_STATE["max_mp"]
    return (
        scene_update_response(map_index + 1),
        player_reposition_response(),
    )


def map_navigation_flag_response(enabled):
    return make_response(MAP_NAVIGATION_FLAG_COMMAND, bytes((int(enabled),)))


def player_navigation_property_response(enabled):
    return make_response(
        PLAYER_PROPERTY_UPDATE_COMMAND,
        struct.pack("<BI", 12, int(enabled)),
    )


def player_container_response(container_type, capacity):
    if not 0 <= container_type <= 4:
        raise ValueError("Unsupported player container type")
    if not 0 <= capacity <= 255:
        raise ValueError("Container capacity must fit in one byte")
    return make_response(
        PLAYER_CONTAINER_COMMAND,
        bytes((container_type, capacity, 0)),
    )


def validate_client_cache_upload(frame, command):
    if len(frame) < 5:
        raise ValueError("Truncated client cache upload")
    item_count = frame[4]
    if command == SPRITE_CACHE_COMMAND:
        expected_length = 5 + item_count * 2
    elif command == ASSET_CACHE_COMMAND:
        expected_length = 5 + item_count
    else:
        raise ValueError("Unsupported cache upload command")
    if len(frame) != expected_length:
        raise ValueError("Invalid client cache upload length")


def save_game_state():
    position = get_player_position()
    with PLAYER_STATE_LOCK:
        state = {
            key: PLAYER_STATE[key]
            for key in ("level", "exp", "hp", "mp", "gold", "job", "name", "x", "y")
        }
        payload = {
            "player": state,
            "inventory": PLAYER_INVENTORY,
            "equipment": PLAYER_EQUIPMENT,
            "depot": PLAYER_DEPOT,
            "position": position,
        }
    SAVE_FILE.parent.mkdir(parents=True, exist_ok=True)
    temporary_path = SAVE_FILE.with_suffix(SAVE_FILE.suffix + ".tmp")
    with temporary_path.open("w", encoding="utf-8") as target:
        json.dump(payload, target, ensure_ascii=False, separators=(",", ":"))
        target.write("\n")
    os.replace(temporary_path, SAVE_FILE)


def load_game_state():
    global PLAYER_INITIALIZED
    if not SAVE_FILE.exists():
        return False
    with SAVE_FILE.open("r", encoding="utf-8") as source:
        payload = json.load(source)
    if not isinstance(payload, dict) or not all(
        key in payload for key in ("player", "inventory", "equipment", "depot", "position")
    ):
        raise ValueError("Save file has an invalid top-level structure")
    player = payload["player"]
    if not isinstance(player, dict) or player.get("job") not in range(3):
        raise ValueError("Save file contains an invalid player record")
    if player.get("level") not in range(1, PLAYER_MAX_LEVEL + 1):
        raise ValueError("Save file contains an invalid player level")
    for key in ("inventory", "equipment", "depot"):
        if not isinstance(payload[key], list):
            raise ValueError("Save file field {} must be a list".format(key))
    with PLAYER_STATE_LOCK:
        for key in ("level", "exp", "hp", "mp", "gold", "job", "name", "x", "y"):
            if key not in player:
                raise ValueError("Save file is missing player field {}".format(key))
            PLAYER_STATE[key] = player[key]
        PLAYER_INVENTORY[:] = payload["inventory"][:PLAYER_BAG_CAPACITY]
        PLAYER_EQUIPMENT[:] = payload["equipment"][:PLAYER_EQUIPMENT_SLOT_COUNT]
        PLAYER_DEPOT[:] = payload["depot"][:PLAYER_BAG_CAPACITY]
        PLAYER_INITIALIZED = True
    saved_position = payload["position"]
    set_player_position(
        saved_position["zone_id"],
        saved_position["x"],
        saved_position["y"],
        saved_position.get("world_node_id"),
        saved_position.get("map_res_id"),
    )
    refresh_player_stats()
    return True


def build_notice_dialog(title, text):
    encoded_title = title.encode("utf-8")
    encoded_text = text.encode("utf-8")
    return b"".join((
        struct.pack("<I", 0),
        struct.pack("<I", len(encoded_title)),
        encoded_title,
        bytes((3, 0)),
        struct.pack("<H", 0),
        struct.pack("<I", len(encoded_text)),
        encoded_text,
        b"\x00\x00\x00",
    ))


def _send_notice(connection, title, text):
    connection.sendall(make_response(2200, build_notice_dialog(title, text)))


def _build_shop_open():
    payload = bytearray((0, 0x11))
    payload.extend(struct.pack("<HB", 0, 0))
    payload.extend(struct.pack("<I", 9101))
    for text in ("药品商人", "金币"):
        encoded = text.encode("utf-8")
        payload.append(len(encoded))
        payload.extend(encoded)
    payload.append(1)
    tab_name = "药品".encode("utf-8")
    payload.append(len(tab_name))
    payload.extend(tab_name)
    return make_response(951, bytes(payload))


def _build_shop_tab():
    payload = bytearray((1, 0))
    payload.append(0)
    payload.extend(struct.pack("<I", 0))
    payload.extend((1, len(SHOP_STOCK)))
    for stock in SHOP_STOCK:
        item = _create_consumable(
            stock["def_id"],
            stock["name"],
            stock["view_id"],
            99,
            SHOP_STOCK.index(stock),
        )
        payload.extend(_write_item(item))
        payload.extend(struct.pack("<iH", stock["price"], 0xFFFF))
        payload.append(0)
    return make_response(951, bytes(payload))


def _send_inventory(connection):
    _refresh_item_positions()
    connection.sendall(player_container_data_response(0, PLAYER_BAG_CAPACITY))


def _send_gold(connection):
    with PLAYER_STATE_LOCK:
        gold = PLAYER_STATE["gold"]
    connection.sendall(make_response(621, struct.pack("<IB", gold, 0)))


def _add_inventory_item(item):
    remaining = item["count"]
    for existing in PLAYER_INVENTORY:
        if existing["type"] == 2 and existing["def_id"] == item["def_id"]:
            amount = min(99 - existing["count"], remaining)
            existing["count"] += amount
            remaining -= amount
            if remaining == 0:
                return
    while remaining > 0:
        if len(PLAYER_INVENTORY) >= PLAYER_BAG_CAPACITY:
            raise ValueError("Inventory is full")
        stack = dict(item)
        stack["count"] = min(remaining, 99)
        stack["pos"] = len(PLAYER_INVENTORY)
        PLAYER_INVENTORY.append(stack)
        remaining -= stack["count"]


def _can_add_inventory_item(item):
    remaining = item["count"]
    if item["type"] == 2:
        for existing in PLAYER_INVENTORY:
            if existing["type"] == 2 and existing["def_id"] == item["def_id"]:
                remaining -= min(99 - existing["count"], remaining)
                if remaining <= 0:
                    return True
    available_slots = PLAYER_BAG_CAPACITY - len(PLAYER_INVENTORY)
    return remaining <= available_slots * (99 if item["type"] == 2 else 1)


def _buy_from_shop(connection, def_id, count):
    stock = next((entry for entry in SHOP_STOCK if entry["def_id"] == def_id), None)
    if stock is None:
        connection.sendall(make_response(882, b"\x02"))
        return False
    if not 1 <= count <= 99:
        raise ValueError("Shop purchase count must be between 1 and 99")
    total = stock["price"] * count
    with PLAYER_STATE_LOCK:
        if PLAYER_STATE["gold"] < total:
            connection.sendall(make_response(882, b"\x01"))
            return False
    item = _create_consumable(
        stock["def_id"],
        stock["name"],
        stock["view_id"],
        count,
        len(PLAYER_INVENTORY),
    )
    if not _can_add_inventory_item(item):
        connection.sendall(make_response(882, b"\x03"))
        _send_notice(connection, "商店", "背包空间不足。")
        return False
    _add_inventory_item(item)
    with PLAYER_STATE_LOCK:
        PLAYER_STATE["gold"] -= total
    _send_gold(connection)
    _send_inventory(connection)
    connection.sendall(_build_shop_tab())
    save_game_state()
    return True


def _sell_inventory_item(connection, def_id, position, count):
    item_index = next((
        index for index, item in enumerate(PLAYER_INVENTORY)
        if item["def_id"] == def_id and item["pos"] == position
    ), None)
    if item_index is None:
        return False
    item = PLAYER_INVENTORY[item_index]
    amount = min(max(count, 1), item["count"])
    unit_price = 5 if item["type"] == 2 else 1 + item["req_level"] * 2 + item["quality"] * 20
    if item["type"] == 1:
        unit_price = 1 + item["req_level"] * 2 + item["quality"] * 20
    if amount == item["count"]:
        PLAYER_INVENTORY.pop(item_index)
    else:
        item["count"] -= amount
    _refresh_item_positions()
    with PLAYER_STATE_LOCK:
        PLAYER_STATE["gold"] += unit_price * amount
    _send_gold(connection)
    _send_inventory(connection)
    connection.sendall(_build_shop_tab())
    save_game_state()
    return True


def _sell_by_quality(connection, qualities):
    sold_count = 0
    proceeds = 0
    remaining = []
    for item in PLAYER_INVENTORY:
        if item["type"] == 1 and item["quality"] in qualities:
            sold_count += item["count"]
            proceeds += (1 + item["req_level"] * 2 + item["quality"] * 20) * item["count"]
        else:
            remaining.append(item)
    PLAYER_INVENTORY[:] = remaining
    _refresh_item_positions()
    with PLAYER_STATE_LOCK:
        PLAYER_STATE["gold"] += proceeds
    _send_gold(connection)
    _send_inventory(connection)
    _send_notice(connection, "收购商", "已出售 {} 件装备，获得 {} 金币。".format(sold_count, proceeds))
    save_game_state()


def _toggle_depot(connection):
    moved = 0
    if PLAYER_DEPOT:
        while PLAYER_DEPOT and len(PLAYER_INVENTORY) < PLAYER_BAG_CAPACITY:
            item = PLAYER_DEPOT.pop(0)
            item["pos"] = len(PLAYER_INVENTORY)
            PLAYER_INVENTORY.append(item)
            moved += 1
        message = "已取出 {} 件材料。".format(moved)
    else:
        kept = []
        for item in PLAYER_INVENTORY:
            if item["type"] == 2 and item["def_id"] >= 3:
                item["pos"] = len(PLAYER_DEPOT)
                PLAYER_DEPOT.append(item)
                moved += 1
            else:
                kept.append(item)
        PLAYER_INVENTORY[:] = kept
        message = "已存入 {} 件材料。再点一次取出。".format(moved)
    _refresh_item_positions()
    _send_inventory(connection)
    _send_notice(connection, "仓库管事", message)
    save_game_state()


def _handle_npc_interaction(connection, npc_id):
    npc_index = next((index for index, npc in enumerate(NPCS) if npc.character_id == npc_id), None)
    if npc_index is None:
        raise ValueError("Unknown NPC ID: {}".format(npc_id))
    if npc_id == 9101:
        connection.sendall(_build_shop_open())
    elif npc_id == 9102:
        connection.sendall(player_container_data_response(1, PLAYER_EQUIPMENT_SLOT_COUNT))
        connection.sendall(make_response(985, b"\x00\x00\x00\x00\x00"))
        _send_notice(connection, NPCS[npc_index].name, "装备已全部修理。")
    elif npc_id == 9103:
        _toggle_depot(connection)
    elif npc_id == 9104:
        connection.sendall(map_navigation_menu_response())
    elif npc_id == 9106:
        _sell_by_quality(connection, {0})
    else:
        connection.sendall(make_response(
            2200,
            build_notice_dialog(NPCS[npc_index].name, NPC_DIALOGS[npc_index]),
        ))


def _equip_from_inventory(connection, def_id, position):
    item_index = next((
        index for index, item in enumerate(PLAYER_INVENTORY)
        if item["def_id"] == def_id and item["pos"] == position and item["type"] == 1
    ), None)
    if item_index is None:
        return False
    item = PLAYER_INVENTORY.pop(item_index)
    slot = item["equip_pos"]
    if slot not in range(10):
        slot = item["def_id"] % 10
    item["equip_pos"] = slot
    for current in list(PLAYER_EQUIPMENT):
        if current["equip_pos"] == slot:
            PLAYER_EQUIPMENT.remove(current)
            current["pos"] = len(PLAYER_INVENTORY)
            current["equip_pos"] = 255
            PLAYER_INVENTORY.append(current)
    item["pos"] = SLOT_KEY_POSITIONS[slot]
    PLAYER_EQUIPMENT.append(item)
    _refresh_item_positions()
    refresh_player_stats()
    connection.sendall(player_container_data_response(1, PLAYER_EQUIPMENT_SLOT_COUNT))
    _send_inventory(connection)
    connection.sendall(player_attributes_response())
    connection.sendall(player_hp_mp_response())
    connection.sendall(make_response(
        1264,
        struct.pack("<IBH", CHARACTER_ID, 8, item["view_id"] if slot == 8 else 0),
    ))
    save_game_state()
    return True


def _unequip_item(connection, def_id):
    item = next((entry for entry in PLAYER_EQUIPMENT if entry["def_id"] == def_id), None)
    if item is None:
        return False
    if len(PLAYER_INVENTORY) >= PLAYER_BAG_CAPACITY:
        _send_notice(connection, "装备", "背包已满，无法卸下装备。")
        return False
    PLAYER_EQUIPMENT.remove(item)
    item["equip_pos"] = 255
    item["pos"] = len(PLAYER_INVENTORY)
    PLAYER_INVENTORY.append(item)
    _refresh_item_positions()
    refresh_player_stats()
    connection.sendall(player_container_data_response(1, PLAYER_EQUIPMENT_SLOT_COUNT))
    _send_inventory(connection)
    connection.sendall(player_attributes_response())
    connection.sendall(player_hp_mp_response())
    connection.sendall(make_response(1264, struct.pack("<IBH", CHARACTER_ID, 8, 0)))
    save_game_state()
    return True


def _update_monster_hp(connection, monster):
    percent = 0 if monster["max_hp"] <= 0 else monster["hp"] * 100 // monster["max_hp"]
    connection.sendall(make_response(
        1015,
        struct.pack("<IBB", monster["id"], 3, percent),
    ))


def _send_experience(connection):
    with PLAYER_STATE_LOCK:
        level = PLAYER_STATE["level"]
        exp = PLAYER_STATE["exp"]
        current_exp = max(0, exp - get_level_base_exp(level))
        next_exp = max(1, get_next_level_exp(level) - get_level_base_exp(level))
    payload = b"".join((
        b"\x01",
        struct.pack("<III", CHARACTER_ID, next_exp, current_exp),
        struct.pack("<B", level),
    ))
    connection.sendall(make_response(619, payload))


def _award_monster_kill(connection, monster):
    global PLAYER_AUTO_TARGET
    monster["alive"] = False
    monster["hp"] = 0
    monster["respawn_at"] = time.monotonic() + 12
    connection.sendall(make_response(2109, struct.pack("<I", monster["id"])))
    exp_gain = 10 + monster["level"] * 8
    with PLAYER_STATE_LOCK:
        PLAYER_STATE["exp"] += exp_gain
        while (
            PLAYER_STATE["level"] < PLAYER_MAX_LEVEL
            and PLAYER_STATE["exp"] >= PLAYER_EXP_TABLE[PLAYER_STATE["level"] + 1]
        ):
            PLAYER_STATE["level"] += 1
        if PLAYER_STATE["level"] >= PLAYER_MAX_LEVEL:
            PLAYER_STATE["level"] = PLAYER_MAX_LEVEL
            PLAYER_STATE["exp"] = PLAYER_EXP_TABLE[PLAYER_MAX_LEVEL]
    refresh_player_stats()
    with PLAYER_STATE_LOCK:
        PLAYER_STATE["hp"] = PLAYER_STATE["max_hp"]
        PLAYER_STATE["gold"] += random.randint(5, 15)
    _send_experience(connection)
    connection.sendall(player_status_response())
    connection.sendall(player_attributes_response())
    connection.sendall(player_hp_mp_response())
    _send_gold(connection)
    if random.random() < 0.8:
        _add_inventory_item(_create_consumable(1, "金创药", 0, 5, len(PLAYER_INVENTORY)))
    if random.random() < 0.25:
        level_index = min(12, max(0, (monster["level"] - 10) // 5))
        slot = random.randrange(10)
        quality = random.choices((0, 1, 2, 3, 4, 5), (62, 24, 9, 3, 1.4, 0.6))[0]
        _add_inventory_item(_create_equipment(PLAYER_STATE["job"], level_index, slot, quality))
    _send_inventory(connection)
    save_game_state()
    if PLAYER_AUTO_TARGET == monster["id"]:
        PLAYER_AUTO_TARGET = None


def _strike_player(connection, monster):
    with PLAYER_STATE_LOCK:
        if PLAYER_STATE["hp"] <= 0:
            return
        raw_damage = 8 + random.randrange(10) + monster["level"] * 3
        damage = max(1, raw_damage - PLAYER_STATE["defense"] // 2)
        if random.randrange(10000) < PLAYER_STATE["block"] * 30:
            damage //= 3
        PLAYER_STATE["hp"] = max(0, PLAYER_STATE["hp"] - damage)
        is_dead = PLAYER_STATE["hp"] == 0
    connection.sendall(player_hp_mp_response())
    if is_dead:
        connection.sendall(make_response(2109, struct.pack("<I", CHARACTER_ID)))
    else:
        save_game_state()


def _attack_monster(connection, target_id, skill_id=0):
    now = time.monotonic()
    skill = None
    if skill_id:
        skill = next(
            (entry for entry in SKILL_LISTS[PLAYER_STATE["job"]] if entry[0] == skill_id),
            None,
        )
        if skill is None:
            _send_notice(connection, "技能", "该技能不可用。")
            return
        cooldown_end = PLAYER_SKILL_COOLDOWNS.get(skill_id, 0)
        if now < cooldown_end:
            _send_notice(connection, "技能", "技能冷却中。")
            return
        if PLAYER_STATE["mp"] < skill[3]:
            _send_notice(connection, "技能", "法力不足。")
            return
        PLAYER_STATE["mp"] -= skill[3]
        PLAYER_SKILL_COOLDOWNS[skill_id] = now + skill[4] / 1000
    effect = SKILL_EFFECTS.get(skill_id, (0, 100, 0, 0))
    kind, power, buff_percent, buff_duration = effect
    if skill is not None and kind == 3:
        heal = max(1, PLAYER_STATE["magic_attack" if PLAYER_STATE["job"] == 2 else "attack"] * (
            2 if skill_id == 2007 else 3
        ))
        with PLAYER_STATE_LOCK:
            PLAYER_STATE["hp"] = min(PLAYER_STATE["max_hp"], PLAYER_STATE["hp"] + heal)
        connection.sendall(player_hp_mp_response())
        save_game_state()
        return
    if skill is not None and kind == 2:
        PLAYER_BUFFS[skill_id] = (now + buff_duration / 1000, buff_percent)
        connection.sendall(player_hp_mp_response())
        save_game_state()
        return

    with MONSTER_LOCK:
        target = next(
            (monster for monster in MONSTERS if monster["id"] == target_id and monster["alive"]),
            None,
        )
        if target is None:
            _send_notice(connection, "战斗", "目标已离开或不存在。")
            return
        affected = [target]
        if kind == 1:
            affected = [
                monster for monster in MONSTERS
                if monster["alive"]
                and abs(monster["x"] - target["x"]) <= 80
                and abs(monster["y"] - target["y"]) <= 80
            ]
        attack_stat = PLAYER_STATE["magic_attack"] if PLAYER_STATE["job"] == 2 else PLAYER_STATE["attack"]
        attack_stat = max(1, attack_stat)
        buff = PLAYER_BUFFS.get(1006)
        if buff is not None and now < buff[0]:
            attack_stat = attack_stat * (100 + buff[1]) // 100
        for monster in affected:
            base_damage = attack_stat * power // 100
            damage = base_damage + random.randrange(max(1, base_damage // 4 + 1))
            if random.randrange(10000) < PLAYER_STATE["crit"]:
                damage = damage * 3 // 2
            monster["hp"] = max(0, monster["hp"] - max(1, damage - (4 + monster["level"] * 2) // 2))
            if kind in (4, 5, 6, 7):
                monster["stun_until"] = now + buff_duration / 1000
            is_killed = monster["hp"] == 0
            _update_monster_hp(connection, monster)
            if is_killed:
                _award_monster_kill(connection, monster)
            elif kind == 8:
                with PLAYER_STATE_LOCK:
                    PLAYER_STATE["hp"] = min(
                        PLAYER_STATE["max_hp"],
                        PLAYER_STATE["hp"] + max(1, damage * 15 // 100),
                    )
                connection.sendall(player_hp_mp_response())
            if kind != 1:
                break
    if skill_id:
        connection.sendall(player_skills_response())
    if affected and any(monster["alive"] for monster in affected):
        for monster in affected:
            if monster["alive"]:
                _strike_player(connection, monster)
                break


def _use_item(connection, frame):
    if len(frame) < 11:
        raise ValueError("Truncated item-use request")
    item_id = struct.unpack_from("<H", frame, 4)[0]
    position = frame[6]
    item = next((
        entry for entry in PLAYER_INVENTORY
        if entry["def_id"] == item_id and entry["pos"] == position
    ), None)
    if item is None:
        _send_notice(connection, "物品", "背包中找不到该物品。")
        return
    if item_id == 4:
        for response in teleport_to_map_index(5):
            connection.sendall(response)
        return
    with PLAYER_STATE_LOCK:
        if item_id == 1:
            PLAYER_STATE["hp"] = PLAYER_STATE["max_hp"]
        elif item_id == 2:
            PLAYER_STATE["mp"] = PLAYER_STATE["max_mp"]
    item["count"] -= 1
    if item["count"] <= 0:
        PLAYER_INVENTORY.remove(item)
    _refresh_item_positions()
    connection.sendall(player_hp_mp_response())
    _send_inventory(connection)
    save_game_state()


def handle_game_command(connection, frame, command):
    body = frame[4:]
    if command == 601:
        if len(body) < 6:
            raise ValueError("Truncated attack request")
        target_id = struct.unpack_from("<I", body, 0)[0]
        skill_id = struct.unpack_from("<H", body, 4)[0]
        _attack_monster(connection, target_id, skill_id)
    elif command == 607:
        if len(body) != 4:
            raise ValueError("Invalid NPC interaction request")
        _handle_npc_interaction(connection, struct.unpack_from("<I", body)[0])
    elif command == 952:
        if len(body) != 10:
            raise ValueError("Invalid shop-tab request")
        connection.sendall(_build_shop_tab())
    elif command == 880:
        if len(body) != 10:
            raise ValueError("Invalid shop purchase request")
        def_id = struct.unpack_from("<H", body, 5)[0]
        count = body[7]
        _buy_from_shop(connection, def_id, count)
    elif command == 883:
        if len(body) != 8:
            raise ValueError("Invalid shop sale request")
        def_id = struct.unpack_from("<H", body, 4)[0]
        count = body[6]
        position = body[7]
        _sell_inventory_item(connection, def_id, position, count)
    elif command == 958:
        _use_item(connection, frame)
    elif command == 960:
        if len(body) != 3:
            raise ValueError("Invalid equipment request")
        def_id = struct.unpack_from("<H", body)[0]
        if not _equip_from_inventory(connection, def_id, body[2]):
            _send_notice(connection, "装备", "找不到可装备的物品。")
    elif command == 965:
        if len(body) != 3:
            raise ValueError("Invalid equipment removal request")
        if not _unequip_item(connection, struct.unpack_from("<H", body)[0]):
            _send_notice(connection, "装备", "没有找到该装备。")
    elif command == 962:
        connection.sendall(player_container_data_response(1, PLAYER_EQUIPMENT_SLOT_COUNT))
        connection.sendall(make_response(985, b"\x00\x00\x00\x00\x00"))
    elif command == 1079:
        if len(body) >= 8:
            def_id = struct.unpack_from("<I", body, 4)[0]
            if any(stock["def_id"] == def_id for stock in SHOP_STOCK):
                _buy_from_shop(connection, def_id, 1)
            else:
                position = body[3]
                _sell_inventory_item(connection, def_id, position, 1)
        else:
            connection.sendall(make_response(1079, b"\x00"))
    elif command == 3006:
        parse_navigation_map_request(frame)
        connection.sendall(map_navigation_menu_response())
    elif command == 3003:
        map_index = parse_map_menu_selection(frame)
        for response in teleport_to_map_index(map_index):
            connection.sendall(response)
    elif command == 1266:
        with PLAYER_STATE_LOCK:
            PLAYER_STATE["hp"] = PLAYER_STATE["max_hp"]
            PLAYER_STATE["mp"] = PLAYER_STATE["max_mp"]
        for response in (player_reposition_response(), player_hp_mp_response()):
            connection.sendall(response)
        save_game_state()
    elif command == 519:
        connection.sendall(make_response(519, struct.pack("<III", 0, 1, 2)))
    elif command == 600:
        destination = parse_world_map_teleport_request(frame)
        set_player_position(
            destination["zone_id"],
            destination["x"],
            destination["y"],
            destination["world_node_id"],
            destination["map_res_id"],
        )
        for response in (
            scene_update_response(destination["world_node_id"]),
            player_reposition_response(),
        ):
            connection.sendall(response)
    elif command == 2115:
        if len(body) != 4:
            raise ValueError("Invalid target selection request")
        actor_id = struct.unpack_from("<I", body)[0]
        if any(npc.character_id == actor_id for npc in NPCS):
            global PLAYER_AUTO_TARGET
            PLAYER_AUTO_TARGET = None
        else:
            PLAYER_AUTO_TARGET = actor_id


def handle_client(connection, address):
    try:
        with connection:
            connection.settimeout(300)
            zone_authenticated = False
            while True:
                frame = receive_frame(connection)
                if frame is None:
                    return

                command = struct.unpack_from("<H", frame, 2)[0]
                if command == VERSION_CHECK_COMMAND:
                    connection.sendall(latest_version_response())
                    print("{}: version check answered".format(address))
                    return

                if command == LOGIN_REQUEST_COMMAND:
                    try:
                        accepted = authenticate(frame)
                    except ValueError:
                        accepted = False
                    response = login_success_response() if accepted else make_response(
                        LOGIN_REQUEST_COMMAND,
                        bytes((LOGIN_FAILURE,)),
                    )
                    connection.sendall(response)
                    print("{}: login {}".format(
                        address,
                        "accepted" if accepted else "rejected",
                    ))
                    continue

                if command == RECONNECT_COMMAND:
                    try:
                        reconnect = parse_reconnect_request(frame)
                        set_player_position(
                            reconnect["zone_id"],
                            reconnect["x"],
                            reconnect["y"],
                            map_res_id=reconnect["map_res_id"],
                        )
                        response_frames = (
                            world_login_success_response(),
                            scene_update_response(),
                        )
                    except ValueError as error:
                        print("{}: {}".format(address, error))
                        return
                    zone_authenticated = True
                    for response in response_frames:
                        connection.sendall(response)
                    print("{}: reconnect accepted for character {} on map {}".format(
                        address,
                        reconnect["character_id"],
                        reconnect["zone_id"],
                    ))
                    continue

                if command == PLAYER_POSITION_UPDATE_COMMAND:
                    if not zone_authenticated:
                        print("{}: player position updated before zone authentication".format(
                            address,
                        ))
                        return
                    try:
                        position = parse_player_position_update(frame)
                        set_player_position(
                            position["zone_id"],
                            position["x"],
                            position["y"],
                            position["world_node_id"],
                            position["map_res_id"],
                        )
                    except ValueError as error:
                        print("{}: {}".format(address, error))
                        return
                    connection.sendall(player_reposition_response())
                    print("{}: player position updated to ({}, {})".format(
                        address,
                        position["x"],
                        position["y"],
                    ))
                    continue

                if command == WORLD_MAP_TELEPORT_COMMAND:
                    if not zone_authenticated:
                        print("{}: world-map teleport before zone authentication".format(address))
                        return
                    try:
                        destination = parse_world_map_teleport_request(frame)
                        set_player_position(
                            destination["zone_id"],
                            destination["x"],
                            destination["y"],
                            destination["world_node_id"],
                            destination["map_res_id"],
                        )
                    except ValueError as error:
                        print("{}: {}".format(address, error))
                        return
                    reset_monsters(CURRENT_MAP_INDEX)
                    connection.sendall(scene_update_response(destination["world_node_id"]))
                    connection.sendall(player_reposition_response())
                    print("{}: map-change queued for node {}: zone {}, mapResId {} ({}, {})".format(
                        address,
                        destination["world_node_id"],
                        destination["zone_id"],
                        destination["map_res_id"],
                        destination["x"],
                        destination["y"],
                    ))
                    continue

                if command == NAVIGATION_MAP_REQUEST_COMMAND:
                    if not zone_authenticated:
                        print("{}: navigation map request before zone authentication".format(address))
                        return
                    try:
                        parse_navigation_map_request(frame)
                    except ValueError as error:
                        print("{}: {}".format(address, error))
                        return
                    connection.sendall(map_navigation_menu_response())
                    print("{}: navigation map data sent for zone {}".format(
                        address,
                        get_player_position()["zone_id"],
                    ))
                    continue

                if command == ZONE_AUTH_COMMAND:
                    parse_zone_auth_request(frame)
                    zone_authenticated = True
                    connection.sendall(zone_auth_success_response())
                    print("{}: zone authentication accepted".format(address))
                    continue

                if command == CHARACTER_LIST_COMMAND:
                    if not zone_authenticated:
                        print("{}: character list requested before zone authentication".format(
                            address,
                        ))
                        return
                    if len(frame) != 8:
                        print("{}: malformed character-list request".format(address))
                        return
                    connection.sendall(character_list_response())
                    print("{}: synthetic character list sent".format(address))
                    continue

                if command == CHARACTER_SELECT_COMMAND:
                    if not zone_authenticated:
                        print("{}: character selected before zone authentication".format(
                            address,
                        ))
                        return
                    try:
                        character_id = parse_character_select_request(frame)
                    except ValueError as error:
                        print("{}: {}".format(address, error))
                        return
                    connection.sendall(character_select_response())
                    print("{}: character {} selection accepted".format(
                        address,
                        character_id,
                    ))
                    continue

                if command == CHARACTER_ENTER_COMMAND:
                    if not zone_authenticated:
                        print("{}: character entry requested before zone authentication".format(
                            address,
                        ))
                        return
                    try:
                        character_id = parse_character_enter_request(frame)
                        set_player_position(
                            STARTING_ZONE_ID,
                            STARTING_SPAWN_X,
                            STARTING_SPAWN_Y,
                            STARTING_WORLD_NODE_ID,
                            STARTING_MAP_RES_ID,
                        )
                        response_frames = (
                            character_enter_acceptance_response(),
                            world_login_success_response(),
                            scene_update_response(),
                        )
                    except ValueError as error:
                        print("{}: {}".format(address, error))
                        return
                    for response in response_frames:
                        connection.sendall(response)
                    print("{}: character {} entered synthetic scene {}".format(
                        address,
                        character_id,
                        STARTING_SCENE_NAME,
                    ))
                    continue

                if command in (SPRITE_CACHE_COMMAND, ASSET_CACHE_COMMAND):
                    if not zone_authenticated:
                        print("{}: client cache upload before zone authentication".format(
                            address,
                        ))
                        return
                    try:
                        validate_client_cache_upload(frame, command)
                    except ValueError as error:
                        print("{}: {}".format(address, error))
                        return
                    print("{}: client cache upload {} accepted".format(
                        address,
                        command,
                    ))
                    continue

                if command == MAP_LOADED_COMMAND:
                    if not zone_authenticated or len(frame) != 4:
                        print("{}: invalid map-loaded notification".format(address))
                        return
                    connection.sendall(map_entry_response())
                    print("{}: map-loaded notification received; entered zone {}".format(
                        address,
                        get_player_position()["zone_id"],
                    ))
                    continue

                if command == MAP_ENTRY_ACK_COMMAND:
                    if not zone_authenticated or len(frame) != 8:
                        print("{}: invalid map-entry acknowledgement".format(address))
                        return
                    map_index = struct.unpack_from("<I", frame, 4)[0]
                    position = get_player_position()
                    if map_index != position["zone_id"]:
                        print("{}: map-entry acknowledgement mismatch: client={} server={}".format(
                            address,
                            map_index,
                            position["zone_id"],
                        ))
                        return
                    global PLAYER_IN_GAME
                    PLAYER_IN_GAME = True
                    for response in map_entry_initialization_responses():
                        connection.sendall(response)
                    print("{}: client entered map {}".format(address, map_index))
                    print("{}: player, inventory, monsters, and NPCs initialized".format(
                        address,
                    ))
                    continue

                if command in {
                    601, 607, 952, 880, 883, 958, 960, 962, 965, 1079,
                    1266, 2115, 3003, 519,
                }:
                    if not zone_authenticated:
                        print("{}: gameplay command {} before zone authentication".format(
                            address,
                            command,
                        ))
                        return
                    try:
                        handle_game_command(connection, frame, command)
                    except ValueError as error:
                        print("{}: {}".format(address, error))
                        return
                    continue

                if command == HEARTBEAT_COMMAND:
                    if len(frame) != 8:
                        print("{}: malformed heartbeat".format(address))
                        return
                    connection.sendall(make_response(HEARTBEAT_COMMAND, frame[4:8]))
                    continue

                print("{}: unsupported command {}; closing connection".format(
                    address,
                    command,
                ))
                return
    except (OSError, ValueError) as error:
        print("{}: {}".format(address, error))


def main():
    if load_game_state():
        print("Loaded local player save from {}".format(SAVE_FILE))
    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as server:
        server.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
        server.bind((HOST, PORT))
        server.listen()
        print("Local login/version service listening on {}:{}".format(HOST, PORT))
        while True:
            connection, address = server.accept()
            threading.Thread(
                target=handle_client,
                args=(connection, address),
                daemon=True,
            ).start()


if __name__ == "__main__":
    main()
