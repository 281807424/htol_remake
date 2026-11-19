# Teleport-point data and client rendering

## Findings

- The client JAR contains map scenery resources, sprite groups, text tables,
  and the code to parse/draw scene entities. The map-resource section 3 records
  parsed in `map_actor_catalog.json` are static actors and layer assets; they
  do not encode source-map/destination-map pairs or a portal trigger table.
- The packaged text table supplies map names and cardinal adjacency text. It
  does not supply portal coordinates, destination spawn coordinates, or a
  machine-readable map-resource mapping.
- The client does have a server protocol for scene entities. In
  [`cc.java`](../decompiled/cc.java), command `521` parses a list of entities
  containing a type, object ID, X/Y coordinates, name, and description; it
  then parses an optional map-point record. The latter is a presence byte,
  marker ID, X/Y coordinates, and name. These are runtime data in the
  command-`521` response, not a local table discovered in the JAR.
- The client text explicitly describes orange minimap points as teleport
  points. The marker is drawn by [`m.java`](../decompiled/m.java) through
  [`ay.c(Graphics,int,int,int)`](../decompiled/ay.java), which draws four
  lines to form a diamond. **The minimap marker has no sprite image.**
- The response handler for command `2152` passes its subtype to
  [`bc.java`](../decompiled/bc.java); subtype `26` selects the localized
  `【传送门】` dialog. This proves a portal interaction UI path, but does not
  show that all scene entities use that subtype.
- The ordinary scene-entity construction path in
  [`bm.java`](../decompiled/bm.java) obtains its actor from `bh.n(26)` with
  animation `bh.o(26)`. In [`bh.java`](../decompiled/bh.java), the descriptor
  at index `26` is `[38, 0, 1, -10]`, resolving to sprite group `38` in sprite
  resource `/5`, animation `0`. However, `bm.a(...)` uses this same descriptor
  for ordinary scene entities; it is a shared rendering path, **not evidence
  of a unique portal sprite**. The JAR code alone does not establish which
  in-world graphic is the portal or which frame is used. A decoded visual of
  group `38` or a client screenshot is needed to confirm its appearance.

## What is and is not recoverable from this JAR

- **Recoverable:** the command-`521` entity and map-marker wire layouts, the
  orange minimap drawing behavior, and the client-side scene-entity sprite
  group candidate described above.
- **Not found in the packaged client data:** a complete list of each map's
  portal coordinates, destination IDs, trigger sizes, or arrival coordinates.
  Those values appear to be supplied by the server through scene data and
  subsequent server responses. The stopped server cannot be queried to obtain
  them. Static map actors can be examined for likely visual portal structures,
  but that does not establish a portal's interaction target or destination.

## Local server state

The previous prototype inserted guessed map-point coordinates and a guessed
proximity-based transition for map indices `6` and `5`. Those values were not
recovered from the client and have been removed. The local server currently
does not advertise a teleport marker or perform a map transition. It now
implements the world-map confirmation request, while in-world teleport markers
remain unsupported pending route evidence.

## Local world-map teleport implementation

The handoff note (`交接说明.md`, section 2.1/3.2/3.3) clarifies that these are
separate IDs:

- `worldMapId`: selected world-map entry (for example 33 for 青鱼村).
- `zoneId`: the gameplay zone identifier used by command `2112` and actor
  packets. The user's `ZONE_96` naming corresponds to zone ID 96.
- `mapResId`: map data selector from command `521`; the client loads archive
  member `7 + mapResId`. It is **not** `zoneId`.

The local server now translates the selected world-map node to its `zoneId`,
then looks up `mapResId`. For 青鱼村, the supplied screenshot matches the
handoff's `mapResId 28` visual (plain with pink tree and wooden box), so the
current mapping is `worldMapId 33 -> zoneId 96 -> mapResId 28`. 定远城 uses
`worldMapId 6 -> zoneId 12 -> mapResId 6`.

Opening navigation sends command `3006`; the server responds with command
`521` for the current scene. Selecting a destination then sends command `600`
with its world-map node ID. The server updates the pending scene, responds with
command `521` using the distinct IDs and `mapResId`, waits for the client's
empty command `525` after local map loading, then answers with command `2112`
carrying `zoneId, x, y`. The client's `2116` acknowledgment is checked against
that zone ID. The spawn is still a center-of-map fallback, not a verified
walkable point.

`ZONE_TO_MAP_RES_ID` currently contains the handoff's listed mappings plus the
visually matched 青鱼村 entry. Other `ZONE` entries without a known `mapResId`
are rejected rather than silently treating their zone IDs as map resources.
- The client displays “此地图不能使用国家地图导航” when its
  `bm.dn` restriction flag is set. Command `622` sets that flag from a byte in
  the player-status response; command `1401` updates it directly; and command
  `624` property type `12` updates it as well. After map entry the local server
  now sends both explicit false updates (`1401` and `624`, type `12`) to clear
  any stale restriction.

To build a complete route catalog, each route needs evidence for the
origin-map identity, portal object/marker ID, coordinates and trigger area,
destination map, and arrival coordinates. Viable evidence would be a retained
command-`521`/transition capture, server-side data files, or individually
verified in-client observations. Until then, retain unknowns rather than
filling the catalog with estimates.
