# Client protocol notes

This file holds extracted client/protocol facts. Keep `README.md` limited to project rules and pointers. Distinguish static observations from runtime-confirmed behavior; update confidence and open questions as evidence improves.

## Artifact inventory

| Path | Role |
|---|---|
| `浩天奇缘-神装现世_触摸屏通用版(360x640).jar` | Immutable original client JAR and comparison baseline. |
| `decompiled/` | CFR 0.152 decompilation. Preserve original obfuscated names and decompiler limitations. |
| `decompiled/summary.txt` | CFR summary; several methods were not fully structured. |
| `local_text_tables.json` | Five text tables extracted from the JAR `/1` resource; 1,932 decoded entries. |
| `浩天奇缘-神装现世_触摸屏通用版(360x640)_local_update.jar` | Separate client copy with only the `br.class` login endpoint changed to localhost. |
| `local_version_server.py` | Local version/login/zone and synthetic gameplay server. Its client compatibility still needs runtime verification. |

Important source files:

- `decompiled/br.java`: version `1.14.0`, original login endpoint list and WAP fallback.
- `decompiled/ct.java`: TCP framing, stream receive/send, and byte transform table.
- `decompiled/cc.java`: request builders and server-response parser.
- `decompiled/cs.java`: login UI state and request dispatch.
- `decompiled/am.java`, `decompiled/ca.java`: server-list display, selection and server-entry fields.
- `decompiled/bm.java`: global state and localized text table loading.

The local-update JAR was checked against the original: only `br.class` changed; the other 279 member contents were byte-identical. This does not imply the complete ZIP containers are byte-identical. The original JAR remains untouched.

## Static protocol observations

These are derived from decompiled client code, not a complete official protocol specification.

### Framing and byte transform

- TCP frames start with a four-byte header: little-endian `short` frame length followed by little-endian `short` command ID.
- The client has a 256-byte transform table in `ct.java`. Outgoing bytes add the table value at the same packet offset; incoming bytes subtract it. The table index wraps at 256.
- The receiver decrypts the first four bytes as the header and subsequent body bytes using their absolute frame offsets.
- Verify framing and transform against actual client traffic before treating this as a complete transport specification.

### Login request and response

- The login UI dispatch in `cs.e()` calls the two-string `cc.a(...)` overload.
- That overload in `cc.java` builds request command `257` in this order:
  - 32-bit little-endian username byte length and UTF-8 bytes;
  - 32-bit little-endian password-digest byte length and digest bytes;
  - 32-bit little-endian protocol value `11400`;
  - the `cb()` client metadata: one-byte flag, 32-bit little-endian client version, and one-byte-length-prefixed client/device string (`bm.by`);
  - trailing 32-bit little-endian value `7`.
- The password digest is generated through `bm.bY.a(...)`, which is the bundled MD5 implementation (`g.java`); the exact character encoding should be validated against a real client request.
- A command `257` response carries a one-byte status. Status `1` displays text table 1 entry `1069` (“该账号已登陆游戏！”), status `2` displays entry `1068` (account/password error), and status `6` follows the banned-account path. These are failure outcomes, not successful login.
- Successful login is parsed from command `258`. Its `bm()` response handler treats status `1` as success and reads:
  - a 32-bit integer into `bm.P`;
  - one byte into `bm.M`;
  - a 32-bit byte length and that many bytes into the UTF-8 session-key string `bm.N`;
  - a one-byte server count;
  - each server entry as: 32-bit ID, one-byte-length-prefixed name, one-byte-length-prefixed primary host, 16-bit little-endian primary port, one-byte-length-prefixed alternate host, 16-bit little-endian alternate port, one-byte state.
- The success branch installs the returned server entries in the client's server-list screen. `am.a(ca)` uses the entry's primary host and port for a later socket connection. The local process also handles this later connection, but the game-session values and responses are synthetic and remain unverified against the client.
- The meaning and correct values for session ID (`bm.P`), login-server ID (`bm.M`), session key (`bm.N`) and all server metadata have not been independently verified against a live/recorded server.
- Command `256` was initially misidentified as the main login request; its use in this client flow remains unknown. The observed `cs.e()` login path sends `257`.

### Zone connection and character list

Static evidence from `am.a(ca)` shows that choosing a zone opens a new socket to
that entry's primary host and port. The client then exchanges:

- Command `513` is built by `cc.a(int, String)`: a 32-bit integer, a
  32-bit-length-prefixed UTF-8 string, a 32-bit-length-prefixed `"240W"` marker,
  the 32-bit login-server ID, and the 32-bit protocol value `11400`. A response
  containing status byte `1` is treated as success.
- After successful zone authentication, the character-selection state sends
  command `514` with one 32-bit zero. The `bk()` response parser interprets the
  response as a one-byte character count, one ignored 32-bit value, then that
  many character records. Each record contains: slot byte; fixed 33-byte
  NUL-padded UTF-8 name; status byte; one reserved byte; five 32-bit values
  (character ID, the value displayed as class, level, a value used to index the
  client's `bn.a` labels, and another character value); 20 little-endian signed
  16-bit attributes; one 32-bit attributes/flags value; and a
  one-byte-length-prefixed UTF-8 display string.
- The client maps class values `0`, `1`, and `2` to text table 0 entries `140`,
  `141`, and `142` respectively; entry `142` is “天灵”. It displays the level,
  character name, class, and a faction label selected from `bn.a`. The character
  creation UI initializes its binary sex selector to `0` and toggles between
  `0` and `1`; interpreting `0` as male is an inference from the UI, not a
  server-confirmed fact.
- Command `515` submits a selected character ID. Its response handler reads a
  status byte; status `1` calls `am.a(4)`. This response alone does not provide
  a map or spawn position, and the later game-world handshake is unresolved.
- The existing-character entry path in `am.a(cr, int)` sends command `523`
  with the character ID, `-1`, and `0`, then sends local cache data in commands
  `2702` and `2706`. The response handler treats command `523` status `2`
  specially; command `401` status `3` follows the client's successful
  initialization branch. Command `521` then supplies the map/scene state
  described in [MAP_ASSETS.md](MAP_ASSETS.md).
- After loading the map, the client can send an empty command `525`
  (`cc.d()`). The map-loading state in `cw.b()` waits after sending this
  command until `cw.c` becomes true. Incoming command `2112` is parsed by
  `cc.Z()` as a 32-bit map ID and two signed 16-bit coordinates; that handler
  sets `cw.c` and sends command `2116` with the map ID. Thus command `2112` is
  required to finish the client-side map-loading state.
- The reconnect helper `cc.K()` sends command `400` with session ID, character
  ID, current map/location ID, two 16-bit coordinates, a one-byte reconnect
  flag, and a 32-bit-length-prefixed UTF-8 session key. The local server
  validates the synthetic session and responds with command `401` status `3`
  followed by its scene update. A movement-position request is command `602`,
  built by `cc.a(short, short)` from the current player coordinates. The local
  server stores these coordinates and uses them in the next `2112` map-entry
  response; this prevents reconnect from reverting to the fixed spawn point.
  This is a reconstructed movement/reconnect path, not a captured official
  response.

The local prototype answers command `513` with status `1`, command `514` with
one fabricated role named “天灵男” (slot `0`, character ID `1`, class `2`,
level `10`, label index `0`), and command `515` with status `1` for that
character ID. For command `523` it validates the synthetic role ID and returns
command `523` status `1`, command `401` status `3`, then a command `521` scene
update selecting 定远城 zone ID `12`, world-map node `6`, and mapResId `6`.
The character record, player stats, skills, monsters, and NPC state are
synthetic; only the class mapping, packet parsers, and field layouts are
grounded in static client code. The character-list parser reads 20 signed
16-bit attribute values and one 32-bit value after the five character
integers; omitting these 44 bytes causes `cc.ai()` to read beyond the response.
Session credentials are not authenticated against a real server.

The client's `3006` request is answered with a `3007` navigation menu, whose
options are derived from the handoff map table; selecting an option through
`3003` changes the scene and sends a player reposition update. Command `600`
world-map destinations use the existing zone/resource mapping. After a map
loads and sends `525`, the prototype responds with `2112` (map ID and
coordinates); it accepts the follow-up `2116`. Map/zone assignments and NPC,
monster, and equipment data use the handoff tables and local synthetic rules;
the spawn coordinate remains a fallback, not an observed official spawn.
Neither these mappings nor the new gameplay responses have been validated by
the target JAR at runtime.

The command `521` scene parser also accepts an optional final map-point record
after the scene-entity list. The client renders the corresponding map point as
an orange minimap diamond, not as a portal object drawn into the game world.
The client contains this wire format and rendering path, but the marker
coordinates and portal route data are server-provided; no complete local
route table has been found in the JAR. The local server does not invent a
portal list or proximity-trigger coordinates. Its menu/world-map transition
handlers are separate reconstructed paths and do not establish portal
compatibility.
[`TELEPORT_POINTS.md`](./TELEPORT_POINTS.md) records the source evidence and
sprite-group finding.

### Other response paths and text resources

- The client emits eight-byte command `65535` heartbeat frames containing a four-byte timestamp. Its response parser reads a four-byte timestamp for that command. The local prototype echoes the payload as a heartbeat response; this behavior is inferred from the client parser and has not been validated against a live server.
- `local_text_tables.json` contains five resource tables with entry counts `428`, `1372`, `74`, `50`, and `8`. Strings are stored as Java `DataInputStream.readInt()` count followed by `DataInputStream.readUTF()` entries (modified UTF-8).
- The resource confirms UI text; it does not by itself prove the server-side status semantics beyond where client code dispatches to that text.

## Current local prototype and boundaries

`local_version_server.py` is configured with local test credentials `1` / `1`, listens on `127.0.0.1:28000`, keeps the existing version-check response, parses command `257` login requests, and builds a command `258` success response containing one local test-zone entry. On the subsequent zone connection, it answers command `513` authentication, command `514` character-list requests with one synthetic character, command `515` selection, and command `523` character entry. It accepts authenticated reconnect command `400`, the client's `2702`/`2706` cache uploads, empty command `525` map-loaded notifications, and command `2116` map-entry acknowledgements. After `2116`, it sends synthetic player, status, skill, inventory/equipment, monster, and NPC initialization packets. It accepts command `602` player-position updates, responds to navigation request `3006` with menu `3007` and handles selection `3003`, and persists the synthetic character's state for reconnect. It also answers command `65535` heartbeat frames by echoing their timestamp.

The server additionally offers synthetic NPC conversations, shop purchases/sales, item use, equipment changes, depot-style material storage, repairs, rest, and basic combat/level-up responses. It loads map, NPC, equipment-name, and equipment-stat data from the handoff CSVs. Task data and task progression are not implemented; storage uses a dialogue-based substitute rather than the client's native depot UI. These behaviors are reconstructed approximations, not verified official semantics.

The current session and zone values are placeholders. Passing unit tests proves the encoder/decoder and server behavior agree with these chosen values; it does not prove that the real client accepts the login or gameplay packets. The actual JAR has not yet been used for end-to-end verification.

After a valid command `2116` map-entry acknowledgment, the prototype sends
synthetic player entity (`2102`), HP/MP (`2111`), attributes (`623`), status
(`622`), skills (`1220`), containers (`950`), monsters (`2104`), and NPCs
(`633`). Packet layouts follow client parsers where identified, but values and
ordering remain unverified against runtime client traffic. See
[CHARACTER_INITIALIZATION.md](CHARACTER_INITIALIZATION.md) for static field
evidence and unresolved character initialization details.

## Open questions

- Verify request field contents and password-digest encoding from an actual client-generated request.
- Confirm command `258` success response values and the zone-list fields using runtime observation or a trustworthy retained protocol sample.
- Verify that the client accepts the synthetic command `513`/`514` exchange and renders the “天灵男” character as intended.
- Verify that the client accepts the command `523` -> `401` -> `521` -> `525` -> `2112` -> `2116` path and the initialization packets following `2116`.
- Determine whether there are additional login handshakes or commands before the zone list appears; heartbeat echo behavior also needs real-client verification.
- Verify the `3006` -> `3007` -> `3003` map navigation flow, command `600`
  world-map changes, and NPC interaction responses with the target JAR.
- Verify shop, inventory, equipment, combat, and save/reconnect behavior with
  the target JAR; replace synthetic rules where protocol evidence becomes
  available.
- Test the end-to-end path in the intended emulator. The configured localhost address works only when the client and service share the same machine/network namespace.

## Symbol readability work

Do not edit `decompiled/` to rename symbols. Put reconstructed, commented reading copies under `analysis/client-readable/` and keep an `analysis/symbol-map.csv` with original symbol, proposed name, evidence, and confidence. Treat uncertain names as hypotheses.
