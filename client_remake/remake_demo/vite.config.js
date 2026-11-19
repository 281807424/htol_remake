import { readFile } from 'node:fs/promises';
import { extname, resolve, sep } from 'node:path';
import { fileURLToPath } from 'node:url';

const projectRoot = fileURLToPath(new URL('.', import.meta.url));
const exportImageRoot = resolve(projectRoot, '..', 'htol_export', 'images');
const runtimeRoot = resolve(exportImageRoot, 'demo_runtime');
const mimeTypes = {
  '.json': 'application/json; charset=utf-8',
  '.png': 'image/png',
  '.tmj': 'application/json; charset=utf-8',
};

async function loadRuntimeMap(mapName) {
  if (!/^map_\d{3}\.tmj$/.test(mapName)) throw new Error(`Invalid map asset path: ${mapName}`);
  const mapPath = resolve(exportImageRoot, 'phaser', 'maps', mapName);
  const map = JSON.parse(await readFile(mapPath, 'utf8'));
  map.tilesets = await Promise.all((map.tilesets || []).map(async (tileset) => {
    const tilesetPath = resolve(exportImageRoot, 'phaser', 'maps', tileset.source);
    const definition = JSON.parse(await readFile(tilesetPath, 'utf8'));
    return { ...definition, firstgid: tileset.firstgid };
  }));
  return map;
}

function exportedAssets() {
  const routeRoots = [
    ['/assets/collision/', resolve(exportImageRoot, 'map_collision')],
    ['/assets/sprite_library/', resolve(exportImageRoot, 'sprite_library')],
    ['/assets/tilesets/', resolve(exportImageRoot, 'phaser', 'maps', 'tilesets')],
  ];

  return {
    name: 'htol-exported-assets',
    configureServer(server) {
      server.middlewares.use(async (request, response, next) => {
        const pathname = new URL(request.url, 'http://localhost').pathname;
        const isMapRequest = pathname.startsWith('/assets/maps/');
        const mapName = isMapRequest
          ? decodeURIComponent(pathname.slice('/assets/maps/'.length))
          : null;
        const sourcePath = pathname === '/assets/manifest.json'
          ? resolve(runtimeRoot, 'manifest.json')
          : isMapRequest
            ? /^map_\d{3}\.tmj$/.test(mapName)
              ? mapName
              : null
            : (() => {
            const route = routeRoots.find(([prefix]) => pathname.startsWith(prefix));
            if (!route) return null;
            const relativePath = decodeURIComponent(pathname.slice(route[0].length));
            const target = resolve(route[1], relativePath);
            return target.startsWith(`${route[1]}${sep}`) ? target : null;
          })();
        if (!sourcePath) {
          next();
          return;
        }

        try {
          const contents = isMapRequest
            ? JSON.stringify(await loadRuntimeMap(sourcePath))
            : await readFile(sourcePath);
          response.statusCode = 200;
          response.setHeader('Content-Type', mimeTypes[extname(isMapRequest ? mapName : sourcePath)] || 'application/octet-stream');
          response.end(contents);
        } catch (error) {
          if (error.code === 'ENOENT') {
            next();
            return;
          }
          next(error);
        }
      });
    },
    async generateBundle() {
      const manifest = JSON.parse(await readFile(resolve(runtimeRoot, 'manifest.json'), 'utf8'));
      const emitted = new Set();
      const addFile = async (outputPath, sourcePath) => {
        if (emitted.has(outputPath)) return;
        emitted.add(outputPath);
        this.emitFile({
          type: 'asset',
          fileName: outputPath,
          source: await readFile(sourcePath),
        });
      };

      await addFile('assets/manifest.json', resolve(runtimeRoot, 'manifest.json'));
      for (const mapId of manifest.mapIds) {
        const mapName = `map_${String(mapId).padStart(3, '0')}`;
        const map = await loadRuntimeMap(`${mapName}.tmj`);
        this.emitFile({
          type: 'asset',
          fileName: `assets/maps/${mapName}.tmj`,
          source: JSON.stringify(map),
        });
        await addFile(
          `assets/collision/${mapName}.json`,
          resolve(exportImageRoot, 'map_collision', `${mapName}.json`),
        );
        for (const tileset of map.tilesets) {
          const imageName = tileset.image.split(/[\\/]/).pop();
          await addFile(
            `assets/tilesets/${imageName}`,
            resolve(exportImageRoot, 'phaser', 'maps', 'tilesets', imageName),
          );
        }
      }
      for (const sprite of manifest.sprites) {
        if ([74, 75, 76].includes(sprite.spriteId)) continue;
        await addFile(
          `assets/${sprite.file}`,
          resolve(exportImageRoot, sprite.file),
        );
      }
      for (const character of Object.values(manifest.aoCharacters)) {
        await addFile(
          `assets/${character.componentAtlasFile}`,
          resolve(exportImageRoot, character.componentAtlasFile),
        );
      }
    },
  };
}

export default {
  publicDir: false,
  plugins: [exportedAssets()],
};
