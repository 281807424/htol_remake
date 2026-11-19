# HTOL Phaser demo

A small Phaser 3 + Vite reconstruction demo using exported HTOL map and sprite assets.

## Run it

```sh
npm install
npm run dev
```
资源尽量读取相邻的 `htol_export`，避免重复创建。

Open the URL printed by Vite. The page displays only the game at full viewport size and is titled **浩天奇缘ol**. Use **WASD** or the **arrow keys** to move; approach an animated teleportation array to travel to the connected map. The route includes the 桃花坞 branch.

The demo player supports all three jobs, both genders, and both weapons for each job (先锋/剑·斧, 百步/弓·弩, 天灵/棍·杖). Press the top-row **1** to cycle jobs, **2** to cycle male/female, **3** to cycle the current job's weapons, and **4** to cycle its original `/6` component-mapping slots. **Q** toggles the automatic release type between casting and attacking. Right-facing movement mirrors the left-facing animation. Role actions are rendered from the raw AO component atlas and frame draw data; `/6` replaces component IDs at draw time, without generating a second set of appearance-specific animation frames. The AO root stays at the player's logical world coordinate when the appearance changes. Actions of the selected type play automatically in a random sequence and temporarily lock locomotion until each action completes.

No on-screen control or information panels are displayed. Animated skill-effect groups remain available to the demo; static images are omitted. Press **E** to release the next of five skills in order: 陨石术, 光盾 (self), 冰冻之环, 落雷术, and 雷电束缚; the sequence wraps back to the first skill. Normal movement/idle animation resumes when a player action finishes.

Skills have no cost or cooldown in this demo and their effects can overlap. The 斗笠刀客 stays in place, plays an idle animation, and reacts once to hits before returning to idle.
Red outlines show the visible frame bounds of both characters and active skill effects. Skill animations anchor to the character's ground/body point and preserve each source frame's authored offsets, so directional effects retain their intended paths (for example, 天火术 falls from the upper right onto the character). Ground-centered legacy effects keep their own placement.
Before running or building after changing character assets, regenerate the AO data and atlases with `python ..\htol_export\export_character_phaser_assets.py`. The dev and build scripts then prepare collision grids and a small runtime manifest beside the neighboring `htol_export` directory. Map, tileset, and sprite image data is read directly from that export rather than duplicated under `remake_demo`. Development serves those source files in place; production build packages only the files used by this demo into `dist`. Map object rendering honors the source transform code for all sprites, including bridges and teleportation arrays.

```sh
npm run build
npm run preview
```

## World map runtime

The game loads the balanced three-country world and neighboring map exits from `../world_map_design/world_map.json`. Start location is `map_013`, the 玄月国主城; the nearby 斗笠刀客 remains present as the practice target. Every configured connection becomes an existing animated portal placed on a reachable clear tile near its declared map edge. Entering the paired portal leads to the neighboring map and arrives near the corresponding destination edge.

`scripts/prepare-assets.mjs` prepares collision data for the complete world and writes the runtime manifest to `htol_export/images/demo_runtime/`. Development and production builds load the maps, collision grids, and terrain tilesets directly from the neighboring export; assets are not duplicated in this project. The client source confirms collision attribute `23` is blocked.
