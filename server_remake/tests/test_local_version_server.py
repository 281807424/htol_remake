import copy
import hashlib
import socket
import struct
import tempfile
import threading
import unittest
from pathlib import Path
from unittest.mock import patch

import local_version_server as service


def make_login_request(username, password, device_id=b"test-device"):
    username_bytes = username.encode("utf-8")
    digest = hashlib.md5(password.encode("utf-8")).hexdigest().encode("ascii")
    payload = b"".join((
        struct.pack("<I", len(username_bytes)),
        username_bytes,
        struct.pack("<I", len(digest)),
        digest,
        struct.pack("<I", 11400),
        b"\x01",
        struct.pack("<I", 46),
        struct.pack("<B", len(device_id)),
        device_id,
        struct.pack("<I", 7),
    ))
    return service.transform(
        struct.pack("<HH", len(payload) + 4, service.LOGIN_REQUEST_COMMAND)
        + payload,
        1,
    )


def make_zone_auth_request():
    payload = b"".join((
        struct.pack("<I", service.CHARACTER_ID),
        struct.pack("<I", len(service.SESSION_KEY)),
        service.SESSION_KEY,
        struct.pack("<I", len(service.ZONE_AUTH_MAGIC)),
        service.ZONE_AUTH_MAGIC,
        struct.pack("<II", service.LOGIN_SERVER_ID, service.GAME_PROTOCOL_VERSION),
    ))
    return service.transform(
        struct.pack("<HH", len(payload) + 4, service.ZONE_AUTH_COMMAND)
        + payload,
        1,
    )


def make_character_select_request(character_id=service.CHARACTER_ID):
    return service.transform(
        struct.pack(
            "<HHI",
            8,
            service.CHARACTER_SELECT_COMMAND,
            character_id,
        ),
        1,
    )


def make_character_enter_request(character_id=service.CHARACTER_ID):
    payload = struct.pack("<III", character_id, 0xFFFFFFFF, 0)
    return service.transform(
        struct.pack(
            "<HH",
            len(payload) + 4,
            service.CHARACTER_ENTER_COMMAND,
        ) + payload,
        1,
    )


def make_reconnect_request(
    x=service.STARTING_SPAWN_X,
    y=service.STARTING_SPAWN_Y,
    map_id=service.STARTING_ZONE_ID,
):
    key = service.SESSION_KEY
    payload = struct.pack(
        "<IIIhhBI",
        service.SESSION_ID,
        service.CHARACTER_ID,
        map_id,
        x,
        y,
        0,
        len(key),
    ) + key
    return service.transform(
        struct.pack(
            "<HH",
            len(payload) + 4,
            service.RECONNECT_COMMAND,
        ) + payload,
        1,
    )


def make_player_position_update(x, y):
    return service.transform(
        struct.pack(
            "<HHhh",
            8,
            service.PLAYER_POSITION_UPDATE_COMMAND,
            x,
            y,
        ),
        1,
    )


def make_world_map_teleport_request(map_id):
    return service.transform(
        struct.pack("<HHI", 8, service.WORLD_MAP_TELEPORT_COMMAND, map_id),
        1,
    )


def make_navigation_map_request(
    request_type=0,
    timestamp=123,
    character_id=service.CHARACTER_ID,
):
    return service.transform(
        struct.pack(
            "<HHIII",
            16,
            service.NAVIGATION_MAP_REQUEST_COMMAND,
            request_type,
            timestamp,
            character_id,
        ),
        1,
    )


def receive_response(client):
    encrypted_header = bytearray()
    while len(encrypted_header) < 4:
        chunk = client.recv(4 - len(encrypted_header))
        if not chunk:
            break
        encrypted_header.extend(chunk)
    if len(encrypted_header) < 4:
        raise AssertionError("Server closed before sending a response")

    header = service.transform(bytes(encrypted_header), -1)
    frame_size = struct.unpack_from("<H", header)[0]
    while len(encrypted_header) < frame_size:
        chunk = client.recv(frame_size - len(encrypted_header))
        if not chunk:
            break
        encrypted_header.extend(chunk)
    if len(encrypted_header) != frame_size:
        raise AssertionError("Server closed before completing its response")
    return service.transform(bytes(encrypted_header), -1)


def scene_fields(frame):
    frame_size, command = struct.unpack_from("<HH", frame)
    if frame_size != len(frame) or command != service.SCENE_UPDATE_COMMAND:
        raise AssertionError("Invalid scene-update frame")
    payload = frame[4:]
    sprite_count = payload[4]
    fields_offset = 5 + sprite_count * 2
    zone_id, map_id, world_node_id = struct.unpack_from("<III", payload, fields_offset)
    map_res_id = payload[fields_offset + 13]
    return sprite_count, zone_id, map_id, world_node_id, map_res_id


def receive_map_initialization(client):
    expected_prefix = [
        2102,
        service.PLAYER_STATUS_UPDATE_COMMAND,
        service.PLAYER_ATTRIBUTES_COMMAND,
        service.PLAYER_STATUS_COMMAND,
        1220,
        service.PLAYER_CONTAINER_COMMAND,
        service.PLAYER_CONTAINER_COMMAND,
        service.MAP_NAVIGATION_FLAG_COMMAND,
        service.PLAYER_PROPERTY_UPDATE_COMMAND,
    ]
    frames = [receive_response(client) for _ in expected_prefix]
    commands = [struct.unpack_from("<H", frame, 2)[0] for frame in frames]
    if commands != expected_prefix:
        raise AssertionError("Unexpected map initialization prefix: {}".format(commands))
    while True:
        frame = receive_response(client)
        command = struct.unpack_from("<H", frame, 2)[0]
        frames.append(frame)
        if command == 633:
            break
        if command != 2104:
            raise AssertionError("Unexpected map entity initialization command: {}".format(
                command,
            ))
    return frames


def exchange_with_handler(request):
    client, server = socket.socketpair()
    worker = threading.Thread(
        target=service.handle_client,
        args=(server, ("test", 0)),
    )
    worker.start()
    client.settimeout(2)
    client.sendall(request)

    response = receive_response(client)
    client.close()
    worker.join(2)
    if worker.is_alive():
        raise AssertionError("Server handler did not finish")
    return response


class LocalServerProtocolTests(unittest.TestCase):
    def setUp(self):
        service.set_player_position(
            service.STARTING_ZONE_ID,
            service.STARTING_SPAWN_X,
            service.STARTING_SPAWN_Y,
            service.STARTING_WORLD_NODE_ID,
            service.STARTING_MAP_RES_ID,
        )

    def test_correct_account_receives_success_and_one_zone(self):
        response = exchange_with_handler(make_login_request("1", "1"))
        frame_size, command = struct.unpack_from("<HH", response)
        self.assertEqual(frame_size, len(response))
        self.assertEqual(command, service.LOGIN_RESPONSE_COMMAND)

        payload = response[4:]
        offset = 0
        self.assertEqual(payload[offset], 1)
        offset += 1
        self.assertEqual(struct.unpack_from("<I", payload, offset)[0], service.SESSION_ID)
        offset += 4
        self.assertEqual(payload[offset], service.LOGIN_SERVER_ID)
        offset += 1
        key_size = struct.unpack_from("<I", payload, offset)[0]
        offset += 4
        self.assertEqual(payload[offset:offset + key_size], service.SESSION_KEY)
        offset += key_size
        self.assertEqual(payload[offset], 1)
        offset += 1

        zone_id = struct.unpack_from("<I", payload, offset)[0]
        offset += 4
        self.assertEqual(zone_id, service.ZONE_ID)
        name_size = payload[offset]
        offset += 1
        self.assertEqual(payload[offset:offset + name_size].decode("utf-8"), service.ZONE_NAME)
        offset += name_size
        host_size = payload[offset]
        offset += 1
        self.assertEqual(payload[offset:offset + host_size].decode("utf-8"), service.ZONE_HOST)
        offset += host_size
        self.assertEqual(struct.unpack_from("<H", payload, offset)[0], service.ZONE_PORT)
        offset += 2
        alternate_host_size = payload[offset]
        offset += 1
        self.assertEqual(
            payload[offset:offset + alternate_host_size].decode("utf-8"),
            service.ZONE_HOST,
        )
        offset += alternate_host_size
        self.assertEqual(struct.unpack_from("<H", payload, offset)[0], service.ZONE_PORT)
        offset += 2
        self.assertEqual(payload[offset], service.ZONE_STATUS)
        self.assertEqual(offset + 1, len(payload))

    def test_wrong_password_receives_client_login_failure(self):
        response = exchange_with_handler(make_login_request("1", "wrong"))
        self.assertEqual(
            response,
            struct.pack("<HHB", 5, service.LOGIN_REQUEST_COMMAND, 2),
        )

    def test_wrong_username_receives_client_login_failure(self):
        response = exchange_with_handler(make_login_request("other", "1"))
        self.assertEqual(
            response,
            struct.pack("<HHB", 5, service.LOGIN_REQUEST_COMMAND, 2),
        )

    def test_version_check_response_is_preserved(self):
        request = service.transform(
            struct.pack("<HH", 4, service.VERSION_CHECK_COMMAND),
            1,
        )
        response = exchange_with_handler(request)
        self.assertEqual(
            response,
            struct.pack("<HHBBB", 7, service.VERSION_CHECK_COMMAND, 0, 0, 0),
        )

    def test_malformed_login_is_rejected(self):
        request = service.transform(
            struct.pack("<HH", 4, service.LOGIN_REQUEST_COMMAND),
            1,
        )
        response = exchange_with_handler(request)
        self.assertEqual(
            response,
            struct.pack("<HHB", 5, service.LOGIN_REQUEST_COMMAND, 2),
        )

    def test_heartbeat_is_answered_without_closing_connection(self):
        timestamp = struct.pack("<I", 123456)
        request = service.transform(
            struct.pack("<HH", 8, service.HEARTBEAT_COMMAND) + timestamp,
            1,
        )
        response = exchange_with_handler(request)
        self.assertEqual(
            response,
            struct.pack("<HH", 8, service.HEARTBEAT_COMMAND) + timestamp,
        )

    def test_login_connection_remains_available_for_heartbeat(self):
        client, server = socket.socketpair()
        worker = threading.Thread(
            target=service.handle_client,
            args=(server, ("test", 0)),
        )
        worker.start()
        client.settimeout(2)

        client.sendall(make_login_request("1", "1"))
        login_response = receive_response(client)
        self.assertEqual(
            struct.unpack_from("<H", login_response, 2)[0],
            service.LOGIN_RESPONSE_COMMAND,
        )

        timestamp = struct.pack("<I", 987654)
        heartbeat = service.transform(
            struct.pack("<HH", 8, service.HEARTBEAT_COMMAND) + timestamp,
            1,
        )
        client.sendall(heartbeat)
        self.assertEqual(
            receive_response(client),
            struct.pack("<HH", 8, service.HEARTBEAT_COMMAND) + timestamp,
        )

        client.close()
        worker.join(2)
        self.assertFalse(worker.is_alive(), "Server handler did not finish")

    def test_zone_authentication_returns_one_tianling_male_character(self):
        client, server = socket.socketpair()
        worker = threading.Thread(
            target=service.handle_client,
            args=(server, ("test", 0)),
        )
        worker.start()
        client.settimeout(2)

        try:
            client.sendall(make_zone_auth_request())
            auth_response = receive_response(client)
            self.assertEqual(
                auth_response,
                struct.pack("<HHB", 5, service.ZONE_AUTH_COMMAND, 1),
            )

            list_request = service.transform(
                struct.pack("<HHI", 8, service.CHARACTER_LIST_COMMAND, 0),
                1,
            )
            client.sendall(list_request)
            response = receive_response(client)
        finally:
            client.close()
            worker.join(2)
        self.assertFalse(worker.is_alive(), "Server handler did not finish")

        frame_size, command = struct.unpack_from("<HH", response)
        self.assertEqual(frame_size, len(response))
        self.assertEqual(command, service.CHARACTER_LIST_COMMAND)
        payload = response[4:]
        self.assertEqual(payload[0], 1)
        self.assertEqual(struct.unpack_from("<I", payload, 1)[0], 0)
        offset = 5

        self.assertEqual(payload[offset], service.CHARACTER_SLOT)
        offset += 1
        name_field = payload[offset:offset + 33]
        self.assertEqual(
            name_field.split(b"\x00", 1)[0].decode("utf-8"),
            service.CHARACTER_NAME,
        )
        offset += 33
        self.assertEqual(payload[offset], service.CHARACTER_STATUS)
        self.assertEqual(payload[offset + 1], service.CHARACTER_GENDER)
        offset += 2
        self.assertEqual(
            struct.unpack_from("<IIIII", payload, offset),
            (
                service.CHARACTER_ID,
                service.CHARACTER_CLASS,
                service.CHARACTER_LEVEL,
                service.CHARACTER_FACTION,
                service.CHARACTER_MODEL,
            ),
        )
        offset += 20
        self.assertEqual(
            struct.unpack_from("<20hI", payload, offset),
            service.CHARACTER_ATTRIBUTES + (service.CHARACTER_ATTRIBUTE_FLAGS,),
        )
        offset += 44
        extra_name_size = payload[offset]
        offset += 1
        self.assertEqual(
            payload[offset:offset + extra_name_size].decode("utf-8"),
            service.CHARACTER_NAME,
        )
        self.assertEqual(offset + extra_name_size, len(payload))

    def test_character_list_is_not_sent_before_zone_authentication(self):
        client, server = socket.socketpair()
        worker = threading.Thread(
            target=service.handle_client,
            args=(server, ("test", 0)),
        )
        worker.start()
        client.settimeout(2)
        request = service.transform(
            struct.pack("<HHI", 8, service.CHARACTER_LIST_COMMAND, 0),
            1,
        )
        client.sendall(request)
        self.assertEqual(client.recv(1), b"")
        client.close()
        worker.join(2)
        self.assertFalse(worker.is_alive(), "Server handler did not finish")

    def test_character_selection_receives_success_after_zone_authentication(self):
        client, server = socket.socketpair()
        worker = threading.Thread(
            target=service.handle_client,
            args=(server, ("test", 0)),
        )
        worker.start()
        client.settimeout(2)

        try:
            client.sendall(make_zone_auth_request())
            self.assertEqual(
                receive_response(client),
                struct.pack("<HHB", 5, service.ZONE_AUTH_COMMAND, 1),
            )
            client.sendall(make_character_select_request())
            self.assertEqual(
                receive_response(client),
                struct.pack("<HHB", 5, service.CHARACTER_SELECT_COMMAND, 1),
            )
        finally:
            client.close()
            worker.join(2)
        self.assertFalse(worker.is_alive(), "Server handler did not finish")

    def test_character_selection_rejects_an_unknown_character(self):
        client, server = socket.socketpair()
        worker = threading.Thread(
            target=service.handle_client,
            args=(server, ("test", 0)),
        )
        worker.start()
        client.settimeout(2)

        try:
            client.sendall(make_zone_auth_request())
            receive_response(client)
            client.sendall(make_character_select_request(service.CHARACTER_ID + 1))
            self.assertEqual(client.recv(1), b"")
        finally:
            client.close()
            worker.join(2)
        self.assertFalse(worker.is_alive(), "Server handler did not finish")

    def test_character_entry_receives_world_login_and_scene(self):
            client, server = socket.socketpair()
            worker = threading.Thread(
                target=service.handle_client,
                args=(server, ("test", 0)),
            )
            worker.start()
            client.settimeout(2)

            try:
                client.sendall(make_zone_auth_request())
                receive_response(client)

                client.sendall(make_character_enter_request())
                expected_commands = (
                    service.CHARACTER_ENTER_COMMAND,
                    service.WORLD_LOGIN_RESPONSE_COMMAND,
                    service.SCENE_UPDATE_COMMAND,
                )
                responses = [receive_response(client) for _ in expected_commands]

                for response, expected_command in zip(responses, expected_commands):
                    frame_size, command = struct.unpack_from("<HH", response)
                    self.assertEqual(frame_size, len(response))
                    self.assertEqual(command, expected_command)

                self.assertEqual(responses[0], struct.pack(
                    "<HHB",
                    5,
                    service.CHARACTER_ENTER_COMMAND,
                    1,
                ))
                self.assertEqual(responses[1], struct.pack(
                    "<HHB",
                    5,
                    service.WORLD_LOGIN_RESPONSE_COMMAND,
                    3,
                ))

                scene_payload = responses[2][4:]
                self.assertEqual(struct.unpack_from("<I", scene_payload, 0)[0], 0)
                sprite_count, zone_id, map_id, world_node_id, map_res_id = scene_fields(
                    responses[2],
                )
                self.assertEqual(
                    sprite_count,
                    len(service.MONSTER_SPRITES) + len(service.NPCS),
                )
                self.assertEqual(
                    (zone_id, map_id, world_node_id),
                    (
                        service.STARTING_ZONE_ID,
                        service.get_map_index(service.STARTING_ZONE_ID),
                        service.STARTING_WORLD_NODE_ID,
                    ),
                )
                self.assertEqual(map_res_id, service.STARTING_MAP_RES_ID)
                name_offset = 24 + sprite_count * 2
                scene_name_size = scene_payload[name_offset]
                self.assertEqual(
                    scene_payload[name_offset + 1:name_offset + 1 + scene_name_size].decode("utf-8"),
                    service.STARTING_SCENE_NAME,
                )
            finally:
                client.close()
                worker.join(2)
            self.assertFalse(worker.is_alive(), "Server handler did not finish")

    def test_map_loaded_notification_keeps_the_zone_connection_open(self):
            client, server = socket.socketpair()
            worker = threading.Thread(
                target=service.handle_client,
                args=(server, ("test", 0)),
            )
            worker.start()
            client.settimeout(2)

            try:
                client.sendall(make_zone_auth_request())
                receive_response(client)
                client.sendall(service.transform(
                    struct.pack("<HH", 4, service.MAP_LOADED_COMMAND),
                    1,
                ))
                map_entry = receive_response(client)
                self.assertEqual(
                    map_entry,
                    struct.pack(
                        "<HHIhh",
                        12,
                        service.MAP_ENTRY_COMMAND,
                        service.STARTING_ZONE_ID,
                        service.STARTING_SPAWN_X,
                        service.STARTING_SPAWN_Y,
                    ),
                )
                client.sendall(service.transform(
                    struct.pack("<HHI", 8, service.MAP_ENTRY_ACK_COMMAND, service.STARTING_ZONE_ID),
                    1,
                ))
                initialization = receive_map_initialization(client)
                attributes = initialization[2]
                self.assertEqual(
                    struct.unpack_from("<HH", attributes),
                    (len(attributes), service.PLAYER_ATTRIBUTES_COMMAND),
                )

                status = initialization[3]
                self.assertEqual(
                    struct.unpack_from("<HH", status),
                    (len(status), service.PLAYER_STATUS_COMMAND),
                )
                self.assertEqual(len(status), 67)
                self.assertEqual(status[4], service.PLAYER_STATE["level"])
                self.assertEqual(
                    struct.unpack_from("<IIH", status, 5),
                    (
                        max(0, service.PLAYER_STATE["exp"] - service.get_level_base_exp(
                            service.PLAYER_STATE["level"],
                        )),
                        service.PLAYER_STATE["hp"],
                        service.PLAYER_STATE["mp"],
                    ),
                )
                self.assertEqual(status[15], 0)
                self.assertEqual(status[-2:], b"\x00\x00")

                self.assertEqual(
                    initialization[7],
                    struct.pack(
                        "<HHB",
                        5,
                        service.MAP_NAVIGATION_FLAG_COMMAND,
                        0,
                    ),
                )

                equipment = initialization[5]
                self.assertEqual(
                    struct.unpack_from("<HHBBB", equipment),
                    (
                        len(equipment),
                        service.PLAYER_CONTAINER_COMMAND,
                        1,
                        service.PLAYER_EQUIPMENT_SLOT_COUNT,
                        len(service.PLAYER_EQUIPMENT),
                    ),
                )
                self.assertEqual(
                    struct.unpack_from("<HHBBB", initialization[6]),
                    (
                        len(initialization[6]),
                        service.PLAYER_CONTAINER_COMMAND,
                        0,
                        service.PLAYER_BAG_CAPACITY,
                        len(service.PLAYER_INVENTORY),
                    ),
                )
                self.assertEqual(
                    initialization[8],
                    struct.pack(
                        "<HHBI",
                        9,
                        service.PLAYER_PROPERTY_UPDATE_COMMAND,
                        12,
                        0,
                    ),
                )

                timestamp = struct.pack("<I", 123456)
                client.sendall(service.transform(
                    struct.pack("<HH", 8, service.HEARTBEAT_COMMAND) + timestamp,
                    1,
                ))
                self.assertEqual(
                    receive_response(client),
                    struct.pack("<HH", 8, service.HEARTBEAT_COMMAND) + timestamp,
                )
            finally:
                client.close()
                worker.join(2)
            self.assertFalse(worker.is_alive(), "Server handler did not finish")

    def test_player_container_response_has_valid_empty_inventory_layout(self):
        bag = service.transform(
            service.player_container_response(
                0,
                service.PLAYER_BAG_CAPACITY,
            ),
            -1,
        )
        self.assertEqual(
            bag,
            struct.pack(
                "<HHBBB",
                7,
                service.PLAYER_CONTAINER_COMMAND,
                0,
                service.PLAYER_BAG_CAPACITY,
                0,
            ),
        )

        equipment = service.transform(
            service.player_container_response(
                1,
                service.PLAYER_EQUIPMENT_SLOT_COUNT,
            ),
            -1,
        )
        self.assertEqual(
            equipment,
            struct.pack(
                "<HHBBB",
                7,
                service.PLAYER_CONTAINER_COMMAND,
                1,
                service.PLAYER_EQUIPMENT_SLOT_COUNT,
                0,
            ),
        )

    def test_player_container_response_rejects_unsupported_type(self):
        with self.assertRaises(ValueError):
            service.player_container_response(5, 0)

    def test_initial_player_status_has_full_nonzero_hp_and_mp(self):
        service.ensure_initial_items()
        attributes = service.transform(service.player_attributes_response(), -1)
        self.assertEqual(
            struct.unpack_from("<HH", attributes),
            (len(attributes), service.PLAYER_ATTRIBUTES_COMMAND),
        )
        decoded_attributes = struct.unpack_from("<HHHHI10H", attributes, 4)
        self.assertEqual(decoded_attributes[0], service.PLAYER_STATE["attack"])
        self.assertEqual(decoded_attributes[1], service.PLAYER_STATE["defense"])
        self.assertEqual(decoded_attributes[2], service.PLAYER_STATE["magic_attack"])
        self.assertEqual(decoded_attributes[3], service.PLAYER_STATE["magic_defense"])
        self.assertEqual(decoded_attributes[4], service.PLAYER_STATE["max_hp"])
        self.assertEqual(decoded_attributes[5], service.PLAYER_STATE["max_mp"])

        status = service.transform(service.player_status_response(), -1)
        self.assertEqual(
            struct.unpack_from("<HH", status),
            (len(status), service.PLAYER_STATUS_COMMAND),
        )
        self.assertEqual(len(status), 67)
        self.assertEqual(
            struct.unpack_from("<IIH", status, 5)[1:],
            (service.PLAYER_STATE["hp"], service.PLAYER_STATE["mp"]),
        )
        self.assertEqual(status[15], 0)
        self.assertGreater(service.PLAYER_STATE["hp"], 0)
        self.assertGreater(service.PLAYER_STATE["mp"], 0)

    def test_save_state_round_trip_restores_character_and_position(self):
        state = copy.deepcopy(service.PLAYER_STATE)
        inventory = copy.deepcopy(service.PLAYER_INVENTORY)
        equipment = copy.deepcopy(service.PLAYER_EQUIPMENT)
        depot = copy.deepcopy(service.PLAYER_DEPOT)
        position = service.get_player_position()
        initialized = service.PLAYER_INITIALIZED
        try:
            map_data = service.MAPS[2]
            width, height = service.get_map_dimensions(map_data.map_res_id)
            with tempfile.TemporaryDirectory() as temporary_directory:
                save_file = Path(temporary_directory) / "player.json"
                with patch.object(service, "SAVE_FILE", save_file):
                    service.PLAYER_STATE.update({
                        "level": 11,
                        "exp": 1234,
                        "gold": 777,
                    })
                    service.set_player_position(
                        map_data.zone_id,
                        width // 2,
                        height // 2,
                        map_data.index + 1,
                        map_data.map_res_id,
                    )
                    service.save_game_state()

                    service.PLAYER_STATE["gold"] = 1
                    service.set_player_position(
                        service.STARTING_ZONE_ID,
                        service.STARTING_SPAWN_X,
                        service.STARTING_SPAWN_Y,
                        service.STARTING_WORLD_NODE_ID,
                        service.STARTING_MAP_RES_ID,
                    )
                    self.assertTrue(service.load_game_state())
                    self.assertEqual(service.PLAYER_STATE["level"], 11)
                    self.assertEqual(service.PLAYER_STATE["exp"], 1234)
                    self.assertEqual(service.PLAYER_STATE["gold"], 777)
                    self.assertEqual(service.get_player_position(), {
                        "zone_id": map_data.zone_id,
                        "map_id": map_data.zone_id,
                        "world_map_id": map_data.index + 1,
                        "world_node_id": map_data.index + 1,
                        "map_res_id": map_data.map_res_id,
                        "x": width // 2,
                        "y": height // 2,
                    })
        finally:
            service.set_player_position(
                position["zone_id"],
                position["x"],
                position["y"],
                position["world_node_id"],
                position["map_res_id"],
            )
            service.PLAYER_STATE.clear()
            service.PLAYER_STATE.update(state)
            service.PLAYER_INVENTORY[:] = inventory
            service.PLAYER_EQUIPMENT[:] = equipment
            service.PLAYER_DEPOT[:] = depot
            service.PLAYER_INITIALIZED = initialized

    def test_reconnect_request_restores_world_login_and_scene(self):
        client, server = socket.socketpair()
        worker = threading.Thread(
            target=service.handle_client,
            args=(server, ("test", 0)),
        )
        worker.start()
        client.settimeout(2)
        try:
            client.sendall(make_reconnect_request())
            world_login = receive_response(client)
            scene_update = receive_response(client)
            self.assertEqual(
                world_login,
                struct.pack("<HHB", 5, service.WORLD_LOGIN_RESPONSE_COMMAND, 3),
            )
            self.assertEqual(
                struct.unpack_from("<H", scene_update, 2)[0],
                service.SCENE_UPDATE_COMMAND,
            )
            client.sendall(service.transform(
                struct.pack("<HH", 4, service.MAP_LOADED_COMMAND),
                1,
            ))
            self.assertEqual(
                receive_response(client),
                struct.pack(
                    "<HHIhh",
                    12,
                    service.MAP_ENTRY_COMMAND,
                    service.STARTING_ZONE_ID,
                    service.STARTING_SPAWN_X,
                    service.STARTING_SPAWN_Y,
                ),
            )
            client.sendall(service.transform(
                struct.pack("<HHI", 8, service.MAP_ENTRY_ACK_COMMAND, service.STARTING_ZONE_ID),
                1,
            ))
            initialized = receive_map_initialization(client)
            self.assertEqual(
                struct.unpack_from("<H", initialized[2], 2)[0],
                service.PLAYER_ATTRIBUTES_COMMAND,
            )
            self.assertEqual(
                struct.unpack_from("<H", initialized[3], 2)[0],
                service.PLAYER_STATUS_COMMAND,
            )
            self.assertEqual(
                initialized[7],
                struct.pack("<HHB", 5, service.MAP_NAVIGATION_FLAG_COMMAND, 0),
            )
            self.assertEqual(
                initialized[8],
                struct.pack(
                    "<HHBI",
                    9,
                    service.PLAYER_PROPERTY_UPDATE_COMMAND,
                    12,
                    0,
                ),
            )
            timestamp = struct.pack("<I", 777)
            client.sendall(service.transform(
                struct.pack("<HH", 8, service.HEARTBEAT_COMMAND) + timestamp,
                1,
            ))
            self.assertEqual(
                receive_response(client),
                struct.pack("<HH", 8, service.HEARTBEAT_COMMAND) + timestamp,
            )
        finally:
            client.close()
            worker.join(2)
        self.assertFalse(worker.is_alive(), "Server handler did not finish")

    def test_player_movement_accepts_declared_client_frame_size(self):
        client, server = socket.socketpair()
        try:
            frame = service.transform(
                struct.pack("<HHhh", 14, service.PLAYER_POSITION_UPDATE_COMMAND, 696, 528),
                1,
            )
            client.sendall(frame)
            received = service.receive_frame(server)
            self.assertEqual(
                received,
                struct.pack("<HHhh", 14, service.PLAYER_POSITION_UPDATE_COMMAND, 696, 528),
            )
        finally:
            client.close()
            server.close()

    def test_player_movement_updates_position_and_survives_reconnect(self):
        x, y = 720, 552
        client, server = socket.socketpair()
        worker = threading.Thread(
            target=service.handle_client,
            args=(server, ("test", 0)),
        )
        worker.start()
        client.settimeout(2)

        try:
            client.sendall(make_zone_auth_request())
            receive_response(client)
            client.sendall(make_player_position_update(x, y))
            self.assertEqual(
                receive_response(client),
                struct.pack(
                    "<HHIHHBIIHH",
                    25,
                    2110,
                    service.CHARACTER_ID,
                    x,
                    y,
                    0,
                    service.PLAYER_STATE["hp"],
                    service.PLAYER_STATE["max_hp"],
                    x,
                    y,
                ),
            )
            timestamp = struct.pack("<I", 789)
            client.sendall(service.transform(
                struct.pack("<HH", 8, service.HEARTBEAT_COMMAND) + timestamp,
                1,
            ))
            self.assertEqual(
                receive_response(client),
                struct.pack("<HH", 8, service.HEARTBEAT_COMMAND) + timestamp,
            )
            self.assertEqual(service.get_player_position(), {
                "zone_id": service.STARTING_ZONE_ID,
                "map_id": service.STARTING_ZONE_ID,
                "world_map_id": service.STARTING_WORLD_NODE_ID,
                "map_res_id": service.STARTING_MAP_RES_ID,
                "world_node_id": service.STARTING_WORLD_NODE_ID,
                "x": x,
                "y": y,
            })
        finally:
            client.close()
            worker.join(2)
        self.assertFalse(worker.is_alive(), "Server handler did not finish")

        client, server = socket.socketpair()
        worker = threading.Thread(
            target=service.handle_client,
            args=(server, ("test-reconnect", 0)),
        )
        worker.start()
        client.settimeout(2)
        try:
            client.sendall(make_reconnect_request(x, y))
            receive_response(client)
            receive_response(client)
            client.sendall(service.transform(
                struct.pack("<HH", 4, service.MAP_LOADED_COMMAND),
                1,
            ))
            self.assertEqual(
                receive_response(client),
                struct.pack(
                    "<HHIhh",
                    12,
                    service.MAP_ENTRY_COMMAND,
                    service.STARTING_ZONE_ID,
                    x,
                    y,
                ),
            )
        finally:
            client.close()
            worker.join(2)
        self.assertFalse(worker.is_alive(), "Server handler did not finish")

    def test_world_map_teleport_node_maps_to_zone_resource_center(self):
        destination = service.parse_world_map_teleport_request(struct.pack(
            "<HHI",
            8,
            service.WORLD_MAP_TELEPORT_COMMAND,
            33,
        ))
        width, height = service.get_map_dimensions(28)
        self.assertEqual(destination, {
            "zone_id": 96,
            "map_id": 96,
            "map_res_id": 28,
            "world_node_id": 33,
            "x": width // 2,
            "y": height // 2,
        })

    def test_navigation_map_request_returns_menu(self):
        client, server = socket.socketpair()
        worker = threading.Thread(
            target=service.handle_client,
            args=(server, ("test-navigation-map", 0)),
        )
        worker.start()
        client.settimeout(2)
        try:
            client.sendall(make_zone_auth_request())
            receive_response(client)

            client.sendall(make_navigation_map_request())
            menu_frame = receive_response(client)
            menu_size, menu_command = struct.unpack_from("<HH", menu_frame)
            self.assertEqual(menu_size, len(menu_frame))
            self.assertEqual(menu_command, 3007)
            self.assertEqual(menu_frame[4], 0)
            self.assertEqual(struct.unpack_from("<II", menu_frame, 5), (0, 0x2345))
            option_count = menu_frame[13]
            self.assertEqual(option_count, len(service.MAPS))
            offset = 14
            options = []
            for _ in range(option_count):
                option_id = struct.unpack_from("<I", menu_frame, offset)[0]
                label_size = menu_frame[offset + 4]
                offset += 5
                label = menu_frame[offset:offset + label_size].decode("utf-8")
                offset += label_size
                options.append((option_id, label))
            self.assertEqual(
                options[0],
                (1000, "{} {}级".format(service.MAPS[0].name, service.MAPS[0].base_level)),
            )
            self.assertEqual(menu_frame[offset], 0)
            self.assertEqual(offset + 1, len(menu_frame))
        finally:
            client.close()
            worker.join(2)
        self.assertFalse(worker.is_alive(), "Server handler did not finish")

    def test_map_menu_selection_changes_scene_and_map_entry(self):
        map_index = 2
        map_data = service.MAPS[map_index]
        width, height = service.get_map_dimensions(map_data.map_res_id)
        client, server = socket.socketpair()
        worker = threading.Thread(
            target=service.handle_client,
            args=(server, ("test-map-selection", 0)),
        )
        worker.start()
        client.settimeout(2)
        try:
            client.sendall(make_zone_auth_request())
            receive_response(client)

            selection = service.transform(
                struct.pack("<HHIIIB", 17, 3003, 0, 0x2345, 1000 + map_index, 0),
                1,
            )
            client.sendall(selection)
            self.assertEqual(
                scene_fields(receive_response(client)),
                (
                    len(service.MONSTER_SPRITES) + len(service.NPCS),
                    map_data.zone_id,
                    map_index,
                    map_index + 1,
                    map_data.map_res_id,
                ),
            )
            reposition = receive_response(client)
            self.assertEqual(
                struct.unpack_from("<HHIHHBIIHH", reposition),
                (
                    len(reposition),
                    2110,
                    service.CHARACTER_ID,
                    width // 2,
                    height // 2,
                    0,
                    service.PLAYER_STATE["hp"],
                    service.PLAYER_STATE["max_hp"],
                    width // 2,
                    height // 2,
                ),
            )

            client.sendall(service.transform(
                struct.pack("<HH", 4, service.MAP_LOADED_COMMAND),
                1,
            ))
            map_entry = receive_response(client)
            self.assertEqual(
                struct.unpack_from("<HHIhh", map_entry),
                (
                    len(map_entry),
                    service.MAP_ENTRY_COMMAND,
                    map_data.zone_id,
                    width // 2,
                    height // 2,
                ),
            )
        finally:
            client.close()
            worker.join(2)
        self.assertFalse(worker.is_alive(), "Server handler did not finish")

    def test_world_map_node_mapping_matches_supplied_zone_numbers(self):
        expected = {
            1: 7, 2: 8, 3: 9, 4: 10, 5: 11, 6: 12, 7: 13,
            8: 14, 9: 15, 10: 16, 11: 17, 12: 18, 13: 19,
            14: 74, 15: 75, 16: 76, 17: 78, 18: 79, 19: 80,
            20: 83, 21: 84, 22: 85, 23: 86, 24: 87, 25: 88,
            26: 89, 27: 90, 28: 91, 29: 92, 30: 93, 31: 94,
            32: 95, 33: 96, 34: 97, 35: 98, 36: 107, 37: 108,
            38: 109, 39: 110, 40: 111, 41: 112, 42: 113,
            43: 114, 44: 115, 45: 116, 46: 117, 47: 118,
            48: 119, 49: 120,
        }
        self.assertEqual(service.WORLD_NODE_TO_ZONE_ID, expected)
        self.assertEqual(service.STARTING_ZONE_ID, 12)
        self.assertEqual(service.WORLD_NODE_TO_ZONE_ID[33], 96)
        self.assertEqual(service.ZONE_TO_MAP_RES_ID[12], 6)
        self.assertEqual(service.ZONE_TO_MAP_RES_ID[96], 28)

    def test_world_map_teleport_handler_returns_map_entry(self):
        client, server = socket.socketpair()
        worker = threading.Thread(
            target=service.handle_client,
            args=(server, ("test-teleport", 0)),
        )
        worker.start()
        client.settimeout(2)
        try:
            client.sendall(make_zone_auth_request())
            receive_response(client)
            client.sendall(make_world_map_teleport_request(33))
            scene_frame = receive_response(client)
            self.assertEqual(
                scene_fields(scene_frame),
                (
                    len(service.MONSTER_SPRITES) + len(service.NPCS),
                    96,
                    96,
                    33,
                    28,
                ),
            )
            reposition = receive_response(client)
            self.assertEqual(
                struct.unpack_from("<H", reposition, 2)[0],
                2110,
            )

            client.sendall(service.transform(
                struct.pack("<HH", 4, service.MAP_LOADED_COMMAND),
                1,
            ))

            frame = receive_response(client)
            frame_size, command, map_id, x, y = struct.unpack_from("<HHIhh", frame)
            self.assertEqual(frame_size, len(frame))
            self.assertEqual(command, service.MAP_ENTRY_COMMAND)
            self.assertEqual(map_id, 96)
            width, height = service.get_map_dimensions(28)
            self.assertEqual((x, y), (width // 2, height // 2))
        finally:
            client.close()
            worker.join(2)
        self.assertFalse(worker.is_alive(), "Server handler did not finish")

    def test_world_map_teleport_rejects_unknown_map(self):
        with self.assertRaises(ValueError):
            service.parse_world_map_teleport_request(struct.pack(
                "<HHI",
                8,
                service.WORLD_MAP_TELEPORT_COMMAND,
                999,
            ))

    def test_player_position_update_rejects_malformed_and_out_of_map_values(self):
        with self.assertRaises(ValueError):
            service.parse_player_position_update(
                struct.pack("<HHh", 6, service.PLAYER_POSITION_UPDATE_COMMAND, 100),
            )
        with self.assertRaises(ValueError):
            service.parse_player_position_update(struct.pack(
                "<HHhh",
                8,
                service.PLAYER_POSITION_UPDATE_COMMAND,
                service.STARTING_MAP_WIDTH,
                0,
            ))

if __name__ == "__main__":
    unittest.main()
