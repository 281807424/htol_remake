# Character state initialization

This note records client-side evidence for the initial player status after map
entry. The local values below are synthetic defaults, not values captured from
an official server.

## Health, mana, and base attributes

The response dispatcher in `decompiled/cc.java` handles command `623` by
reading 15 attributes in order: four unsigned 16-bit values, one 32-bit value,
then ten unsigned 16-bit values. The client stores these through `cr.i(index,
value)`; indexes `4` and `5` are read as maximum HP and maximum MP by the
client's status-bar rendering code.

Command `622` initializes a broader player status record. Its parser reads, in
order:

1. one-byte level;
2. 32-bit experience, 32-bit current HP, unsigned 16-bit current MP, and one
   status byte;
3. five 32-bit values and five one-byte values;
4. six signed 16-bit values;
5. three 32-bit values;
6. two one-byte-length-prefixed UTF-8 strings.

The unnamed fields are retained as zero in the synthetic response because
their meanings and valid values have not been established. The client stores
current HP/MP separately from maxima. Its HP status check treats current HP at
or below zero as dead; the incremental HP update handler also starts a death
transition when HP falls from positive to zero or less.

After the client acknowledges command `2112` with `2116`, the local server now
sends command `623` followed by command `622`. It supplies level 10, maximum
HP/MP 100, current HP/MP 100, and zero for other unconfirmed fields. Sending
the maxima first ensures the client can calculate full bars when current
values arrive.

## Appearance, equipment, and skills

Appearance is not initialized by commands `622` or `623`. The client has a
separate player-entity parser at command `2102`; its packed fields provide
character appearance and optional appearance data. The client also holds nine
equipment-item slots on the player entity. Equipment updates use serialized
item records, which the client parses as full item structures; inventing
item IDs or sending only slot numbers would not be a valid substitute.

The character-panel open path directly accesses the player's bag container
before drawing it. In `decompiled/bm.java`, `bm.a(int, boolean)` reads
`bm.aA.i`, assigns the bag and equipment containers to the panel, and then
draws the bag. `bm.aA` is initialized by the response parser for command `950`,
not by commands `622` or `623`. The parser in `decompiled/cc.java` reads
command `950` as a container type byte, a capacity byte, an item-count byte,
then that many serialized item records. Type `0` creates the bag (`bm.aA`);
type `1` creates the 22-slot equipment container (`bm.aB`).

The local server now sends empty type-`0` and type-`1` containers after the
initial `622`/`623` status responses. This provides valid, initialized UI
models without inventing item IDs or malformed item records. The zero-count
payload avoids claiming that the character has equipment; it is a structural
baseline for opening the panel. The packet shape and order have protocol unit
coverage, but actual rendering still needs verification in the original
emulator.

The scene update currently contains no player-entity snapshot. The `2100`
through `2102` object-update path, its request/response sequence, and the
correct self-entity appearance values still need to be traced before adding
equipment or changing the rendered sprite.

The client's `1503` handler does not establish a right-side skill-bar layout:
depending on its mode, it updates skill cooldowns or the `bm.aA` item
container. The IDs and mapping required for the on-screen skill buttons have
not yet been identified. No fabricated skills or equipment are sent.

Command `2109` has a distinct self-rebirth path that sets position, HP/MP and
returns the player to the normal action state. It is not used as a login
shortcut; its semantics are resurrection, not initial character creation.

## Evidence and limits

- Static parser evidence: `decompiled/cc.java`, dispatch cases `2102`, `622`,
  `623`, `950`, `1503`, plus the `2109` handler.
- Static state/render evidence: `decompiled/cr.java` for player fields and
  equipment slots; `decompiled/bh.java` and `decompiled/bm.java` for HP/death
  checks and status-bar ratios.
- Implemented synthetic behavior: `local_version_server.py` sends `623` and
  `622` after a valid `2116` acknowledgment; regression coverage is in
  `tests/test_local_version_server.py`.
- The packet layouts are inferred from the client's static readers. They are
  not official server captures. The new status values still require a test in
  the original client. Sprite appearance and skill-button contents remain
  unresolved.
