# Client map asset inventory

The original JAR remains immutable. The reproducible structural parser is
[`parse_map_assets.py`](./parse_map_assets.py); its output is
[`map_catalog.json`](./map_catalog.json). The catalog records each packaged
resource, client map index, grid/pixel dimensions, decoded section lengths,
and SHA-256 digests.
Section-3 actor records for all maps are decoded by
[`parse_map_actors.py`](./parse_map_actors.py) into
[`map_actor_catalog.json`](./map_actor_catalog.json).

## Evidence and format

- REA `inspect_artifact` evidence `ev_8d647a3e2dd7b769b764a7602aa828fe1b3f8158947e49f1e90a922cf67145a5`
  identifies the original JAR as a ZIP with SHA-256
  `a126c100616819139767c1c7d0ed6354b4f1025e16e5faacc6e3d6f821c5c180`.
  This is archive inventory evidence, not execution evidence.
- Static code in `decompiled/aq.java` describes the resource container:
  a little-endian entry count and partition table, an offset table for each
  partition, and a one-byte prefix skipped before each indexed payload.
- Static code in `decompiled/bm.java` (`bm.L()`) loads
  `"/" + (mapIndex + 7)`, reads four indexed payloads, and gets the two
  dimensions from the first two little-endian 16-bit values in payload 0.
- Static code in `decompiled/bm.java` (`bm.o()` / `bm.p()`) parses section 3
  as repeated one-byte field counts followed by that many little-endian
  signed 16-bit values. Records with a first value below `300` create static
  map actors; other records load map-layer sprite assets. The decoded fields,
  actor coordinates, and map dimensions are preserved in
  `map_actor_catalog.json`.
  The dimensions are in 24-pixel tiles.
- Parsing the original archive found all 155 resources `7` through `161`.
  All contain four indexed payloads with valid dimensions. This is structural
  parsing only; it does not yet decode their tile semantics or render them.

## 定远城

- `local_text_tables.json`, table 3 entry 6, describes “定远城（城镇）”.
- `decompiled/m.java` displays a world-map node label through text table 3,
  using the node's field 6. Node 6 therefore carries that label.
- The handoff note distinguishes `zoneId` from `mapResId`: `ZONE_12` is
  定远城's zone ID, while its mapResId is `6`. The client loads archive member
  `7 + mapResId`, therefore 定远城 uses member `13` / catalog index `6`.
  The user's world-map node `6` maps to zone ID `12`. 青鱼村 node `33` maps
  to zone ID `96`; its screenshot matches the handoff visual for mapResId `28`.
  This node -> zone -> mapResId chain is implemented separately by
  `WORLD_NODE_TO_ZONE_ID` and `ZONE_TO_MAP_RES_ID`.
- The table-3 descriptions establish the logical adjacency: 定远城 connects
  north to 刺骨, south to 定远城郊, and east to 苍茫; 定远城郊 connects north
  to 定远城, south to 立马坡, and west to 落英谷. This text does not encode
  map-resource IDs or coordinates.
- Section-3 records are visual actors/layer assets, not an explicit
  source-to-destination portal table. Their coordinates can help locate map
  features, but alone they do not prove which actor is a portal or where its
  trigger region lies.

## Teleports

The distinction between map scenery, a world-space portal object, and an
orange minimap teleport marker is documented in
[`TELEPORT_POINTS.md`](./TELEPORT_POINTS.md). The client has rendering and
interaction paths, but no complete portal-route data table has been found in
the packaged map assets or text tables.

## Entry into a scene

- The response handler for command `515` reads a status byte; status `1` calls
  `am.a(4)`. It does not itself contain a map or spawn position.
- The local server now returns status `1` for command `515` when the selected
  character ID matches the synthetic role. A user-reported attempt returned to
  the initial screen; the only supplied client output was RMS record-store
  activity, so this does not confirm whether the response was accepted.
- Static call tracing found the actual existing-character entry method sends
  command `523` with `(characterId, -1, 0)`, followed by local cache uploads
  `2702` and `2706`. Previous local-server output recorded command `523` as
  unsupported and closed the socket, which explains the observed return to the
  first screen. The server now accepts this entry request and sends synthetic
  command `523`, command `401` status `3`, and command `521` scene-update
  responses.
- The current command `521` prototype uses zoneId `12`, mapId `12`,
  worldMapId `6`, and mapResId `6` for 定远城. The local server answers
  command `3006` with the current scene and command `600` with the selected
  destination. Spawn coordinates still require client confirmation.
- The client subsequently sent empty command `525` after loading map data. The
  prior server treated it as unsupported, closed the socket, and the client
  began reconnecting (then sent command `400`). The server now accepts command
  `525` as a no-response map-loaded notification; this is grounded in the
  observed sequence, but the client must be retested to learn what follows.
- On the next client run, the server accepted the map-loaded notification, then
  after the connection idled the client opened reconnect sockets and sent
  command `400`. The old server rejected these as unsupported. The client
  builder `cc.K()` supplies the session ID, role ID, map/location ID,
  coordinates, reconnect flag, and login key; the local server now validates
  these and sends synthetic command `401` status `3` plus command `521`.
- In the following test, command `400` was accepted repeatedly, but each new
  reconnect connection then sent command `525`; the server rejected it because
  that fresh connection had not been marked authenticated. The reconnect path
  now marks the validated session active, and a live TCP probe verified that
  the subsequent `525` no longer closes the connection.
- `cc.K()` builds command `400` from the session ID, selected character ID,
  `bm.cb.F`, `bm.cb.k`, `bm.cb.l`, `bm.ar`, and the UTF-8 login key.
- Command `521` is parsed by `cc.bh()` as a map/scene update. The handler sets
  the current resource map index (`bm.ci`) and other scene, location, and
  entity state before asking the client to load the map.
- `cw.b()` loads the map resources, sends empty command `525`, and then waits
  in state 10 for `cw.c` to become true. The incoming command `2112` handler
  (`cc.Z()`) reads a 32-bit map ID and signed 16-bit X/Y coordinates, sets
  `cw.c = true`, updates the player location, and sends command `2116` with the
  map ID. This is the missing server response that explains the loading stall.
- The local server now returns command `2112` for zone ID `12` at
  `(696, 528)` and accepts the follow-up `2116`. Coordinates are a chosen
  center-of-map spawn hypothesis using the 24-pixel tile scale; walkability and
  client acceptance remain unverified.
- Earlier real-client attempts reached the bundled map load, sent `525`, and
  repeatedly reconnected because the required `2112` response was absent.
  Real-client confirmation is still required after the new response is tested.
