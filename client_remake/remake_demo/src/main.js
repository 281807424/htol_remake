import Phaser from 'phaser';
import './style.css';
import {
  cycleAppearanceSlot,
  renderAOFrame,
} from './ao-character-renderer.js';
import {
  getPixelAlignedCameraBounds,
  snapFollowTargetToPixelGrid,
} from './camera-pixel-snap.js';
import { placeSpriteAtLogicalAnchor } from './sprite-anchor.js';

const ASSET_ROOT = '/assets';
const MAP_OBJECT_TRANSFORMS = [
  { flipX: false, flipY: false, rotation: 0 },
  { flipX: true, flipY: false, rotation: 0 },
  { flipX: false, flipY: true, rotation: 0 },
  { flipX: false, flipY: false, rotation: Math.PI },
  { flipX: false, flipY: false, rotation: Math.PI / 2 },
  { flipX: false, flipY: false, rotation: -Math.PI / 2 },
  { flipX: false, flipY: true, rotation: Math.PI / 2 },
  { flipX: true, flipY: false, rotation: Math.PI / 2 },
];

function applyMapObjectTransform(sprite, entry, transformIndex) {
  const transform = MAP_OBJECT_TRANSFORMS[Number(transformIndex) & 7];
  sprite.setOrigin(
    transform.flipX ? 1 - entry.originX : entry.originX,
    transform.flipY ? 1 - entry.originY : entry.originY,
  );
  sprite.setFlip(transform.flipX, transform.flipY);
  sprite.setRotation(transform.rotation);
}

let MAPS = {};
let START_MAP_ID = null;

const DIRECTIONS = {
  up: { x: 0, y: -1, animation: 4, idle: 1 },
  down: { x: 0, y: 1, animation: 5, idle: 2 },
  left: { x: -1, y: 0, animation: 3, idle: 0 },
  right: { x: 1, y: 0, animation: 3, idle: 0 },
};
const ROLE_PROFILES = {
  1: { spriteId: 74, profession: '先锋', weapons: ['剑', '斧'] },
  2: { spriteId: 75, profession: '百步', weapons: ['弓', '弩'] },
  3: { spriteId: 76, profession: '天灵', weapons: ['棍', '杖'] },
};
const ROLE_GENDERS = ['男', '女'];
const AO_CANVAS_SIZE = 512;
const AO_ROLE_SPRITE_IDS = new Set([74, 75, 76]);
const DEMO_SKILL_COUNT = 5;
const PLAYER_SPEED = 570;
const PLAYER_BODY_WIDTH = 24;
const PLAYER_BODY_HEIGHT = 16;
const TARGET_MARKER_SPRITE_ID = 32;
const TARGET_MARKER_ANIMATION_GROUP = 2;
const ICE_RING_OFFSET_Y = -24;
const PORTAL_SCALE = 0.55;
const PORTAL_LABEL_OFFSET_Y = 116;

class WorldScene extends Phaser.Scene {
  constructor() {
    super('world');
    this.mapId = null;
    this.portalCooldown = 0;
    this.blockedPortalConnectionId = null;
    this.mapObjects = [];
    this.blockers = null;
    this.mapCollider = null;
    this.cameraFollowTarget = null;
    this.combatTimers = new Set();
    this.followEffects = new Set();
    this.skillEffects = new Map();
    this.targetControls = new Map();
    this.monsters = [];
    this.ambientMonsters = [];
    this.selectedMonster = null;
    this.selectionMarker = null;
    this.heroActionEntries = [];
    this.selectedReleaseAction = null;
    this.appearanceSlot = 0;
    this.nextSkillNumber = 1;
    this.heroAOTexture = null;
    this.heroAOContext = null;
    this.heroAOFrame = null;
    this.heroAnimationClock = null;
    this.selectedRole = {
      ...ROLE_PROFILES[3],
      genderIndex: 0,
      weaponIndex: 0,
      setStart: 0,
    };
    this.releaseActionType = '施法';
    this.activeReleaseActionToken = null;
    this.releaseAnimationListener = null;
  }

  preload() {
    this.load.on('loaderror', (file) => {
      const message = `无法载入资源：${file.key || file.src}`;
      showFatalError(message);
    });

    this.load.image('terrain-209', `${ASSET_ROOT}/tilesets/sprite_209_tiles.png`);
    for (const character of Object.values(window.htolAoCharacters)) {
      this.load.image(
        character.componentTextureKey,
        `${ASSET_ROOT}/${character.componentAtlasFile}`,
      );
    }
    for (const sprite of window.htolSprites) {
      if (AO_ROLE_SPRITE_IDS.has(sprite.spriteId)) continue;
      const url = `${ASSET_ROOT}/${sprite.file}`;
      if (sprite.textureKind === 'spritesheet') {
        this.load.spritesheet(sprite.textureKey, url, {
          frameWidth: sprite.frameWidth,
          frameHeight: sprite.frameHeight,
        });
      } else if (sprite.textureKind === 'image') {
        this.load.image(sprite.textureKey, url);
      } else {
        throw new Error(`不支持的精灵格式：${sprite.textureKind}`);
      }
    }

    for (const map of Object.values(MAPS)) {
      this.load.tilemapTiledJSON(map.file, `${ASSET_ROOT}/maps/${map.file}.tmj`);
      this.load.json(
        `${map.file}-collision`,
        `${ASSET_ROOT}/collision/${map.file}.json`,
      );
    }
    for (const spriteId of window.htolWorldTilesetSpriteIds) {
      this.load.image(`terrain-${spriteId}`, `${ASSET_ROOT}/tilesets/sprite_${spriteId}_tiles.png`);
    }
  }

  create() {
    this.heroAOTexture = this.textures.createCanvas(
      'hero-ao-dynamic',
      AO_CANVAS_SIZE,
      AO_CANVAS_SIZE,
    );
    this.heroAOContext = this.heroAOTexture.getContext();
    this.heroAOContext.imageSmoothingEnabled = false;
    this.cursors = this.input.keyboard.createCursorKeys();
    this.keys = this.input.keyboard.addKeys('W,A,S,D');
    this.roleKeys = {
      profession: this.input.keyboard.addKey(Phaser.Input.Keyboard.KeyCodes.ONE),
      gender: this.input.keyboard.addKey(Phaser.Input.Keyboard.KeyCodes.TWO),
      weapon: this.input.keyboard.addKey(Phaser.Input.Keyboard.KeyCodes.THREE),
      appearance: this.input.keyboard.addKey(Phaser.Input.Keyboard.KeyCodes.FOUR),
    };
    this.releaseActionKey = this.input.keyboard.addKey(Phaser.Input.Keyboard.KeyCodes.Q);
    this.nextSkillKey = this.input.keyboard.addKey(Phaser.Input.Keyboard.KeyCodes.E);
    this.input.keyboard.on('keydown-ESC', () => this.game.canvas.blur());

    this.heroAnimations = {};
    this.createHeroAnimations();
    this.createHeroActionAnimations();
    this.createSelectionMarkerAnimation();
    this.debugBounds = this.add.graphics().setDepth(1000000);
    this.switchMap(START_MAP_ID, null, null);
    document.querySelector('#loading-state')?.remove();
    this.scale.on('resize', (size) => {
      this.cameras.main.setViewport(0, 0, size.width, size.height);
      this.updateCameraBounds();
    });
  }

  createHeroAnimations() {
    this.heroAnimations = {};
    for (const [direction, config] of Object.entries(DIRECTIONS)) {
      for (const walking of [false, true]) {
        const actionNumber = walking ? config.animation : config.idle;
        const group = this.selectedRole.setStart + actionNumber;
        const entry = this.getSpriteGroup(this.selectedRole.spriteId, group);
        if (!entry) throw new Error(`角色动画组 ${group} 缺少帧`);
        if (entry.frames.some((frame) => (
          !frame.offsetKnown ||
          !Number.isInteger(frame.drawOffsetX) ||
          !Number.isInteger(frame.drawOffsetY) ||
          !Number.isInteger(frame.height) ||
          frame.height <= 0
        )) || !Number.isFinite(entry.originX) ||
        !Number.isFinite(entry.originY)) {
          throw new Error(`角色动画组 ${group} 缺少源绘制原点数据`);
        }
        entry.animationKey = `hero-${entry.spriteId}-${group}`;
        this.createAOClockAnimation(entry, entry.animationKey, -1);
        this.heroAnimations[direction + (walking ? '-walk' : '-idle')] = entry;
      }
    }
  }

  createHeroActionAnimations() {
    this.heroActionEntries = window.htolSprites
      .filter((entry) => (
        entry.spriteId === this.selectedRole.spriteId &&
        entry.profession === this.selectedRole.profession &&
        entry.gender === ROLE_GENDERS[this.selectedRole.genderIndex] &&
        entry.weapon === this.selectedRole.weapons[this.selectedRole.weaponIndex] &&
        entry.setStart === this.selectedRole.setStart &&
        ['攻击', '施法'].includes(entry.action) &&
        entry.textureKind === 'spritesheet'
      ))
      .sort((left, right) => left.actionNumber - right.actionNumber);
    if (!this.heroActionEntries.length) {
      throw new Error(
        `${this.selectedRole.profession}${ROLE_GENDERS[this.selectedRole.genderIndex]}`
        + `${this.selectedRole.weapons[this.selectedRole.weaponIndex]}缺少攻击或施法动画`,
      );
    }
    for (const entry of this.heroActionEntries) {
      if (
        !Number.isFinite(entry.originX) ||
        !Number.isFinite(entry.originY) ||
        entry.frames.some((frame) => (
          !frame.offsetKnown ||
          !Number.isInteger(frame.drawOffsetX) ||
          !Number.isInteger(frame.drawOffsetY)
        ))
      ) {
        throw new Error(`角色动作 ${entry.actionNumber} 缺少源绘制原点数据`);
      }
      entry.animationKey = `hero-release-${entry.spriteId}-${entry.animationGroup}`;
      this.createAOClockAnimation(entry, entry.animationKey, 0, 2);
    }
    this.selectedReleaseAction = this.getReleaseActions()[0];
  }

  setRoleSelection(profile, genderIndex, weaponIndex) {
    const gender = ROLE_GENDERS[genderIndex];
    const weapon = profile.weapons[weaponIndex];
    const setStart = genderIndex === 0
      ? (weaponIndex === 0 ? 0 : 22)
      : (weaponIndex === 0 ? 51 : 73);
    if (!gender || !weapon) throw new Error('角色性别或武器配置无效');

    if (this.releaseAnimationListener && this.heroAnimationClock) {
      this.heroAnimationClock.off(
        Phaser.Animations.Events.ANIMATION_COMPLETE,
        this.releaseAnimationListener,
      );
    }
    this.heroAnimationClock?.anims.stop();
    this.releaseAnimationListener = null;
    this.activeReleaseActionToken = null;
    this.appearanceSlot = 0;
    this.heroAOFrame = null;
    this.selectedRole = {
      ...profile,
      genderIndex,
      weaponIndex,
      setStart,
    };
    this.createHeroAnimations();
    this.createHeroActionAnimations();

    if (this.heroSprite?.active) {
      this.heroAnimationClock?.anims.stop();
      this.playHeroAnimation(this.heroAnimations[`${this.facing}-idle`], true);
      this.heroSprite.setFlipX(this.facing === 'right');
    }
  }

  cycleProfession() {
    const profiles = Object.values(ROLE_PROFILES);
    const currentIndex = profiles.findIndex(
      (profile) => profile.spriteId === this.selectedRole.spriteId,
    );
    if (currentIndex < 0) throw new Error('当前职业不在角色配置中');
    const profile = profiles[(currentIndex + 1) % profiles.length];
    this.setRoleSelection(
      profile,
      this.selectedRole.genderIndex,
      this.selectedRole.weaponIndex,
    );
  }

  cycleGender() {
    this.setRoleSelection(
      this.selectedRole,
      (this.selectedRole.genderIndex + 1) % ROLE_GENDERS.length,
      this.selectedRole.weaponIndex,
    );
  }

  cycleWeapon() {
    this.setRoleSelection(
      this.selectedRole,
      this.selectedRole.genderIndex,
      (this.selectedRole.weaponIndex + 1) % this.selectedRole.weapons.length,
    );
  }

  cycleAppearance() {
    const character = window.htolAoCharacters[String(this.selectedRole.spriteId)];
    this.appearanceSlot = cycleAppearanceSlot(character, this.appearanceSlot);
    if (this.heroAnimationEntry && this.heroAOFrame) {
      this.renderHeroAOFrame(this.heroAnimationEntry, this.heroAOFrame.sheetFrame);
    }
  }

  castNextSkill() {
    const skillNumber = this.nextSkillNumber;
    this.nextSkillNumber = (skillNumber % DEMO_SKILL_COUNT) + 1;
    this.castSkill(skillNumber);
  }

  cycleReleaseActionType() {
    this.releaseActionType = this.releaseActionType === '施法' ? '攻击' : '施法';
    const action = this.getReleaseActions()[0];
    if (!action) {
      throw new Error(
        `${this.selectedRole.profession}${ROLE_GENDERS[this.selectedRole.genderIndex]}`
        + `${this.selectedRole.weapons[this.selectedRole.weaponIndex]}缺少${this.releaseActionType}动画`,
      );
    }

    if (this.releaseAnimationListener && this.heroAnimationClock) {
      this.heroAnimationClock.off(
        Phaser.Animations.Events.ANIMATION_COMPLETE,
        this.releaseAnimationListener,
      );
    }
    this.heroAnimationClock?.anims.stop();
    this.releaseAnimationListener = null;
    this.activeReleaseActionToken = null;
    this.selectedReleaseAction = action;
    if (this.heroSprite?.active) {
      this.heroAnimationClock?.anims.stop();
      this.playHeroAnimation(this.heroAnimations[`${this.facing}-idle`], true);
      this.heroSprite.setFlipX(this.facing === 'right');
    }
  }

  getReleaseActions() {
    return this.heroActionEntries.filter(
      (entry) => entry.action === this.releaseActionType,
    );
  }

  createSelectionMarkerAnimation() {
    const entry = this.getSpriteGroup(
      TARGET_MARKER_SPRITE_ID,
      TARGET_MARKER_ANIMATION_GROUP,
    );
    if (!entry || entry.textureKind !== 'spritesheet') {
      throw new Error('目标选中标记动画资源缺少帧');
    }
    entry.animationKey = `target-marker-${entry.textureKey}`;
    this.createSpriteAnimation(entry, entry.animationKey, -1);
    this.selectionMarkerEntry = entry;
  }

  createSpriteAnimation(entry, key, repeat, durationMultiplier = 1) {
    if (this.anims.exists(key)) return;
    this.anims.create({
      key,
      frames: entry.frames.map((frame) => ({
        key: entry.textureKey,
        frame: frame.sheetFrame,
        duration: frame.durationMs * durationMultiplier,
      })),
      duration: entry.durationMs * durationMultiplier,
      repeat,
    });
  }

  createAOClockAnimation(entry, key, repeat, durationMultiplier = 1) {
    if (this.anims.exists(key)) return;
    const animation = this.anims.create({
      key,
      frames: entry.frames.map((frame) => ({
        key: 'hero-ao-dynamic',
        frame: '__BASE',
        duration: frame.durationMs * durationMultiplier,
      })),
      duration: entry.durationMs * durationMultiplier,
      repeat,
    });
    animation.frames.forEach((frame, index) => {
      frame.sourceSheetFrame = entry.frames[index].sheetFrame;
    });
  }

  getSpriteGroup(spriteId, animationGroup) {
    return window.htolSprites.find(
      (entry) => entry.spriteId === spriteId && entry.animationGroup === animationGroup,
    );
  }

  renderHeroAOFrame(entry, sheetFrame) {
    if (!AO_ROLE_SPRITE_IDS.has(entry.spriteId)) {
      throw new Error(`不支持的 AO 角色资源：sprite ${entry.spriteId}`);
    }
    const frame = entry.frames.find(
      (candidate) => candidate.sheetFrame === Number(sheetFrame),
    );
    if (!frame) {
      throw new Error(
        `角色动画组 ${entry.animationGroup} 缺少帧 ${sheetFrame}`,
      );
    }
    const character = window.htolAoCharacters[String(entry.spriteId)];
    const atlasTexture = this.textures.get(character.componentTextureKey);
    if (!atlasTexture) {
      throw new Error(`AO 角色 sprite ${entry.spriteId} 缺少组件图集`);
    }
    const bounds = renderAOFrame(
      this.heroAOContext,
      character,
      atlasTexture.getSourceImage(),
      entry.animationGroup,
      frame.sourceFrameIndex,
      this.appearanceSlot,
    );
    this.heroAOTexture.refresh();
    this.heroAOFrame = { ...frame, bounds };
  }

  getCurrentSpriteFrame(sprite, entry) {
    if (sprite === this.heroSprite) {
      if (!this.heroAOFrame) throw new Error('角色 AO 动画尚未绘制首帧');
      return this.heroAOFrame;
    }
    const frameIndex = Number(sprite.anims.currentFrame?.textureFrame ?? sprite.frame.name);
    const frame = entry.frames.find(({ sheetFrame }) => sheetFrame === frameIndex);
    if (!frame) {
      throw new Error(`sprite ${entry.spriteId} 动画组 ${entry.animationGroup} 缺少帧 ${frameIndex}`);
    }
    return frame;
  }

  getVisibleSpriteBounds(sprite, entry) {
    if (sprite === this.heroSprite) {
      const { x, y, width, height } = this.getCurrentSpriteFrame(sprite, entry).bounds;
      const left = x - AO_CANVAS_SIZE / 2;
      const top = y - AO_CANVAS_SIZE / 2;
      return {
        x: sprite.x + (sprite.flipX ? -(left + width) : left) * sprite.scaleX,
        y: sprite.y + top * sprite.scaleY,
        width: width * sprite.scaleX,
        height: height * sprite.scaleY,
      };
    }
    const frame = this.getCurrentSpriteFrame(sprite, entry);
    const left = sprite.flipX
      ? -(frame.drawOffsetX + frame.width)
      : frame.drawOffsetX;
    return {
      x: sprite.x + left * sprite.scaleX,
      y: sprite.y + frame.drawOffsetY * sprite.scaleY,
      width: frame.width * sprite.scaleX,
      height: frame.height * sprite.scaleY,
    };
  }

  getCharacterEffectAnchor(character) {
    if (character === this.heroSprite) {
      return { x: this.player.x, y: this.player.y };
    }
    const monster = this.monsters.find((candidate) => candidate.sprite === character);
    const ambientMonster = this.ambientMonsters.find(
      (candidate) => candidate.sprite === character,
    );
    const target = monster ?? ambientMonster;
    if (!target) throw new Error('无法定位角色特效锚点');
    return { x: target.point.x, y: target.point.y };
  }

  positionSkillEffect(effect, placement) {
    if (placement.alignment !== 'origin' && placement.alignment !== 'center') {
      throw new Error(`不支持的技能特效对齐方式：${placement.alignment}`);
    }
    const frame = this.getCurrentSpriteFrame(effect, placement.entry);
    const localOffsetX = placement.alignment === 'center'
      ? frame.drawOffsetX + frame.width / 2
      : 0;
    const localOffsetY = placement.alignment === 'center'
      ? frame.drawOffsetY + frame.height / 2
      : 0;
    effect.setPosition(
      placement.anchorX - localOffsetX * effect.scaleX,
      placement.anchorY - localOffsetY * effect.scaleY,
    );
    effect.setDepth(placement.anchorY + 2);
  }

  drawDebugBounds() {
    this.debugBounds.clear();
    this.debugBounds.lineStyle(2, 0xff0000, 1);
    const drawBounds = (sprite, entry) => {
      if (!sprite?.active) return;
      const { x, y, width, height } = this.getVisibleSpriteBounds(sprite, entry);
      this.debugBounds.strokeRect(x, y, width, height);
    };
    drawBounds(this.heroSprite, this.heroAnimationEntry);
    if (this.selectedMonster) {
      drawBounds(this.selectedMonster.sprite, this.selectedMonster.animationEntry);
    }
    for (const [effect, placement] of this.skillEffects) {
      drawBounds(effect, placement.entry);
    }
  }

  playHeroAnimation(entry, restart = false) {
    if (!this.heroAnimationClock) {
      throw new Error('角色 AO 动画时钟尚未初始化');
    }
    const sameAnimation = this.heroAnimationEntry?.animationKey === entry.animationKey;
    if (sameAnimation && !restart) return;
    this.heroAnimationEntry = entry;
    this.heroSprite.setOrigin(0.5, 0.5);
    const firstFrame = entry.frames[0];
    if (!firstFrame) {
      throw new Error(`角色动画组 ${entry.animationGroup} 没有帧`);
    }
    this.renderHeroAOFrame(entry, firstFrame.sheetFrame);
    this.heroAnimationClock.play(entry.animationKey, !restart);
  }

  playSelectedReleaseAction(entry = this.selectedReleaseAction) {
    if (!entry) throw new Error('未选择角色攻击或施法动作');
    this.selectedReleaseAction = entry;
    if (this.releaseAnimationListener) {
      this.heroAnimationClock.off(
        Phaser.Animations.Events.ANIMATION_COMPLETE,
        this.releaseAnimationListener,
      );
    }
    const token = {};
    this.activeReleaseActionToken = token;
    this.releaseAnimationListener = () => {
      if (this.activeReleaseActionToken !== token) return;
      this.activeReleaseActionToken = null;
      this.releaseAnimationListener = null;
    };
    this.heroAnimationClock.once(
      Phaser.Animations.Events.ANIMATION_COMPLETE,
      this.releaseAnimationListener,
    );
    this.playHeroAnimation(entry, true);
  }

  syncHeroSpriteToPlayer() {
    this.heroSprite.setPosition(this.player.x, this.player.y);
  }

  syncCameraFollowTarget() {
    const target = snapFollowTargetToPixelGrid(
      this.player.x,
      this.player.y,
      this.cameras.main,
    );
    this.cameraFollowTarget.setPosition(target.x, target.y);
  }

  updateCameraBounds() {
    const cameraBounds = getPixelAlignedCameraBounds(
      this.cameras.main,
      0,
      0,
      this.worldWidth,
      this.worldHeight,
    );
    this.cameras.main.setBounds(
      cameraBounds.x,
      cameraBounds.y,
      cameraBounds.width,
      cameraBounds.height,
    );
  }

  switchMap(mapId, entryTeleport, connectionId = null) {
    const config = MAPS[String(mapId)];
    if (!config) throw new Error(`未知地图：${mapId}`);

    this.clearCombatTimers();
    this.followEffects.clear();
    this.skillEffects.clear();
    this.targetControls.clear();
    this.monsters = [];
    this.ambientMonsters = [];
    this.selectedMonster = null;
    this.selectionMarker = null;
    for (const object of this.mapObjects) {
      this.tweens.killTweensOf(object);
      object.destroy();
    }
    this.mapObjects = [];
    this.groundLayer?.destroy();
    this.mapCollider?.destroy();
    this.mapCollider = null;
    this.blockers?.clear(true, true);
    this.blockers?.destroy();

    this.mapId = String(mapId);
    const map = this.make.tilemap({ key: config.file });
    const tilesets = map.tilesets.map((tileset) => {
      const match = /^sprite_(\d+)_tiles$/.exec(tileset.name);
      if (!match) throw new Error(`地图 ${config.name} 包含未知图集：${tileset.name}`);
      return map.addTilesetImage(
        tileset.name,
        `terrain-${Number(match[1])}`,
        tileset.tileWidth,
        tileset.tileHeight,
        tileset.tileMargin,
        tileset.tileSpacing,
        tileset.firstgid,
      );
    });
    if (tilesets.some((tileset) => !tileset)) {
      throw new Error(`地图 ${config.name} 缺少地形图集`);
    }
    this.groundLayer = map.createLayer('ground', tilesets, 0, 0);
    if (!this.groundLayer) throw new Error(`地图 ${config.name} 缺少 ground 图层`);
    const collisionData = this.getCollisionData(map, config);
    this.blockers = this.createBlockers(collisionData);

    this.worldWidth = map.widthInPixels;
    this.worldHeight = map.heightInPixels;
    this.physics.world.setBounds(0, 0, this.worldWidth, this.worldHeight);
    this.drawMapObjects(map);

    const requestedSpawn = entryTeleport
      ? { x: entryTeleport.x, y: entryTeleport.y }
      : { x: this.worldWidth * config.spawn.x, y: this.worldHeight * config.spawn.y };
    const spawn = this.getSafeSpawn(requestedSpawn, collisionData);

    if (!this.player) {
      this.player = this.add.zone(spawn.x, spawn.y, PLAYER_BODY_WIDTH, PLAYER_BODY_HEIGHT).setOrigin(0.5, 1);
      this.physics.add.existing(this.player);
      this.player.body.setSize(PLAYER_BODY_WIDTH, PLAYER_BODY_HEIGHT, false);
      this.player.body.setOffset(0, 0);
      this.player.body.setCollideWorldBounds(true);
      const idleAnimation = this.heroAnimations['down-idle'];
      this.heroSprite = this.add.sprite(spawn.x, spawn.y, 'hero-ao-dynamic')
        .setOrigin(0.5, 0.5);
      this.heroAnimationClock = this.add.sprite(
        -AO_CANVAS_SIZE,
        -AO_CANVAS_SIZE,
        'hero-ao-dynamic',
      ).setVisible(false);
      this.heroAnimationClock.on(
        Phaser.Animations.Events.ANIMATION_UPDATE,
        (_animation, frame) => {
          if (this.heroAnimationEntry) {
            this.renderHeroAOFrame(this.heroAnimationEntry, frame.sourceSheetFrame);
          }
        },
      );
      this.playHeroAnimation(idleAnimation);
      this.facing = 'down';
    } else {
      this.player.setPosition(spawn.x, spawn.y);
      this.player.body.reset(spawn.x, spawn.y);
    }
    this.mapCollider = this.physics.add.collider(this.player, this.blockers);
    this.player.setDepth(spawn.y + 1);
    this.syncHeroSpriteToPlayer();
    this.heroSprite.setDepth(spawn.y + 1);
    this.heroSprite.setFlipX(this.facing === 'right');

    this.drawMonsters(config.monsters);
    this.drawAmbientEntities(config.ambientEntities);
    this.drawPortals(config.teleports);
    const camera = this.cameras.main;
    camera.setZoom(0.75);
    this.updateCameraBounds();
    if (!this.cameraFollowTarget) {
      this.cameraFollowTarget = this.add.zone(spawn.x, spawn.y);
    }
    this.syncCameraFollowTarget();
    camera.startFollow(this.cameraFollowTarget, false, 1, 1);
    this.portalCooldown = 700;
    this.blockedPortalConnectionId = connectionId;

    this.events.emit('mapchanged', { id: mapId, name: config.name });
  }

  drawMonsters(monsters) {
    for (const monsterData of monsters) {
      const idle = this.getSpriteGroup(monsterData.spriteId, 0);
      if (!idle || idle.textureKind !== 'spritesheet') {
        throw new Error(`怪物 ${monsterData.name} sprite ${monsterData.spriteId} 缺少待机动画`);
      }
      idle.animationKey = `monster-${idle.textureKey}-idle`;
      this.createSpriteAnimation(idle, idle.animationKey, -1);

      const hit = this.getSpriteGroup(monsterData.spriteId, 3);
      if (hit?.textureKind === 'spritesheet') {
        hit.animationKey = `monster-${hit.textureKey}-hit`;
        this.createSpriteAnimation(hit, hit.animationKey, 0);
      }
      const monster = {
        ...monsterData,
        point: { x: monsterData.x, y: monsterData.y },
        idleAnimation: idle,
        hitAnimation: hit,
        animationEntry: idle,
        hitListener: null,
      };
      monster.sprite = this.add.sprite(
        monster.point.x,
        monster.point.y,
        idle.textureKey,
      )
        .setScale(0.92)
        .setDepth(monster.point.y + 1)
        .setInteractive({ useHandCursor: true })
        .play(idle.animationKey);
      monster.label = this.add.text(
        monster.point.x,
        monster.point.y - 91,
        monster.name,
        {
          fontFamily: 'Noto Sans SC, Microsoft YaHei, sans-serif',
          fontSize: '13px',
          color: '#fff1d4',
          backgroundColor: '#35251ed9',
          padding: { x: 7, y: 4 },
        },
      ).setOrigin(0.5, 1).setDepth(monster.point.y + 2);
      monster.sprite.on('pointerdown', () => this.selectMonster(monster));
      this.mapObjects.push(monster.sprite, monster.label);
      this.monsters.push(monster);
      this.syncMonsterSprite(monster);
    }
  }

  drawAmbientEntities(entities) {
    for (const entityData of entities) {
      const animationEntries = entityData.animationGroups.map((animationGroup) => {
        const entry = this.getSpriteGroup(entityData.spriteId, animationGroup);
        if (
          !entry ||
          !['spritesheet', 'image'].includes(entry.textureKind) ||
          (entry.textureKind === 'image' && entry.frameCount !== 1)
        ) {
          throw new Error(
            `${entityData.category} ${entityData.name} sprite ${entityData.spriteId} 动画组 ${animationGroup} 缺少帧`,
          );
        }
        if (entry.textureKind === 'spritesheet') {
          entry.ambientAnimationKey = `ambient-${entry.textureKey}`;
          this.createSpriteAnimation(
            entry,
            entry.ambientAnimationKey,
            entityData.category === 'NPC' ? -1 : 0,
          );
        }
        return entry;
      });
      if (!animationEntries.length) {
        throw new Error(`${entityData.category} ${entityData.name} 缺少动画组`);
      }

      const entity = {
        ...entityData,
        point: { x: entityData.x, y: entityData.y },
        animationEntries,
        animationIndex: 0,
        animationEntry: animationEntries[0],
        sprite: null,
        label: null,
      };
      entity.sprite = this.add.sprite(
        entity.point.x,
        entity.point.y,
        entity.animationEntry.textureKey,
      ).setScale(0.72).setDepth(entity.point.y + 1);
      entity.label = this.add.text(
        entity.point.x,
        entity.point.y,
        entity.name,
        {
          fontFamily: 'Noto Sans SC, Microsoft YaHei, sans-serif',
          fontSize: '11px',
          color: '#fff1d4',
          backgroundColor: '#35251ed9',
          padding: { x: 4, y: 2 },
        },
      ).setOrigin(0.5, 1).setDepth(entity.point.y + 2);
      if (entity.category === '怪物') {
        entity.sprite.setInteractive({ useHandCursor: true });
        entity.label.setInteractive({ useHandCursor: true });
        entity.sprite.on('pointerdown', () => this.selectMonster(entity));
        entity.label.on('pointerdown', () => this.selectMonster(entity));
        this.ambientMonsters.push(entity);
      }
      entity.sprite.on(
        Phaser.Animations.Events.ANIMATION_UPDATE,
        () => this.syncAmbientEntitySprite(entity),
      );
      if (entity.category === '怪物') {
        entity.sprite.on(
          Phaser.Animations.Events.ANIMATION_COMPLETE,
          () => this.advanceAmbientEntityAnimation(entity),
        );
      }
      this.mapObjects.push(entity.sprite, entity.label);
      this.playAmbientEntityAnimation(entity);
    }
  }

  playAmbientEntityAnimation(entity) {
    entity.animationEntry = entity.animationEntries[entity.animationIndex];
    if (entity.animationEntry.textureKind === 'spritesheet') {
      entity.sprite
        .setTexture(entity.animationEntry.textureKey)
        .play(entity.animationEntry.ambientAnimationKey);
    } else {
      entity.sprite.anims.stop();
      entity.sprite.setTexture(entity.animationEntry.textureKey);
      if (entity.category === '怪物') {
        this.time.delayedCall(
          entity.animationEntry.durationMs || 1000,
          () => this.advanceAmbientEntityAnimation(entity),
        );
      }
    }
    this.syncAmbientEntitySprite(entity);
  }

  advanceAmbientEntityAnimation(entity) {
    if (!entity.sprite.active) return;
    entity.animationIndex = (entity.animationIndex + 1) % entity.animationEntries.length;
    this.playAmbientEntityAnimation(entity);
  }

  syncAmbientEntitySprite(entity) {
    if (!entity.sprite.active) return;
    const frameIndex = entity.animationEntry.textureKind === 'spritesheet'
      ? Number(entity.sprite.anims.currentFrame?.textureFrame)
      : Number(entity.animationEntry.frames[0]?.sheetFrame);
    const frame = entity.animationEntry.frames.find(({ sheetFrame }) => sheetFrame === frameIndex);
    if (!frame) {
      throw new Error(
        `${entity.category} ${entity.name} 动画组 ${entity.animationEntry.animationGroup} 缺少帧 ${frameIndex}`,
      );
    }
    placeSpriteAtLogicalAnchor(entity.sprite, entity.point, entity.animationEntry);
    entity.sprite.setDepth(entity.point.y + 1);
    const footOffsetY = frame.drawOffsetY + frame.height;
    entity.label
      .setPosition(entity.point.x, entity.point.y - footOffsetY * entity.sprite.scaleY - 5)
      .setDepth(entity.point.y + 2);
  }

  syncMonsterSprite(monster) {
    if (!monster.sprite.active) return;
    const frameIndex = Number(monster.sprite.anims.currentFrame?.textureFrame);
    const frame = monster.animationEntry.frames.find(({ sheetFrame }) => sheetFrame === frameIndex);
    if (!frame) {
      throw new Error(`怪物 ${monster.name} 动画组 ${monster.animationEntry.animationGroup} 缺少帧 ${frameIndex}`);
    }
    placeSpriteAtLogicalAnchor(monster.sprite, monster.point, monster.animationEntry);
    monster.sprite.setDepth(monster.point.y + 1);
    monster.label.setPosition(monster.point.x, monster.point.y - 91);
    monster.label.setDepth(monster.point.y + 2);
  }

  selectMonster(monster) {
    this.selectedMonster = monster;
    if (!this.selectionMarker) {
      const entry = this.selectionMarkerEntry;
      this.selectionMarker = this.add.sprite(
        monster.point.x,
        monster.point.y + 8,
        entry.textureKey,
      )
        .setOrigin(entry.originX, entry.originY)
        .setScale(0.75)
        .play(entry.animationKey);
      this.mapObjects.push(this.selectionMarker);
    }
    this.selectionMarker
      .setPosition(monster.point.x, monster.point.y + 8)
      .setDepth(monster.point.y - 1);
  }

  playMonsterHit(monster = this.selectedMonster) {
    if (!monster?.sprite.active || !monster.hitAnimation?.animationKey) return;
    if (monster.hitListener) {
      monster.sprite.off(
        Phaser.Animations.Events.ANIMATION_COMPLETE,
        monster.hitListener,
      );
    }
    monster.animationEntry = monster.hitAnimation;
    monster.hitListener = () => {
      monster.hitListener = null;
      if (!monster.sprite.active) return;
      monster.animationEntry = monster.idleAnimation;
      monster.sprite.play(monster.idleAnimation.animationKey);
      this.syncMonsterSprite(monster);
    };
    monster.sprite.once(
      Phaser.Animations.Events.ANIMATION_COMPLETE,
      monster.hitListener,
    );
    monster.sprite.play(monster.hitAnimation.animationKey);
    this.syncMonsterSprite(monster);
  }

  playSkillEffect(spriteId, animationGroup, anchorX, anchorY, {
    scale = 1,
    loop = false,
    durationMs = 0,
    followPlayer = false,
    alignment = 'origin',
    destroyOnAnimationComplete = true,
  } = {}) {
    const entry = this.getSpriteGroup(spriteId, animationGroup);
    if (!entry || entry.textureKind !== 'spritesheet') {
      throw new Error(`技能特效 sprite ${spriteId} 动画组 ${animationGroup} 缺少帧`);
    }
    const animationKey = `skill-fx-${spriteId}-${animationGroup}-${loop ? 'loop' : 'once'}`;
    this.createSpriteAnimation(entry, animationKey, loop ? -1 : 0);
    const effect = this.add.sprite(anchorX, anchorY, entry.textureKey, entry.frames[0].sheetFrame)
      .setOrigin(entry.originX, entry.originY)
      .setScale(scale)
      .setDepth(anchorY + 2)
      .play(animationKey);
    const placement = { entry, anchorX, anchorY, alignment };
    this.skillEffects.set(effect, placement);
    this.positionSkillEffect(effect, placement);
    this.mapObjects.push(effect);
    if (followPlayer) {
      this.followEffects.add({
        sprite: effect,
        offsetX: anchorX - this.player.x,
        offsetY: anchorY - this.player.y,
      });
    }
    if (loop && durationMs > 0) {
      this.scheduleCombatTimer(durationMs, () => {
        this.destroyCombatEffect(effect);
      });
    } else if (!loop && destroyOnAnimationComplete) {
      effect.once(
        Phaser.Animations.Events.ANIMATION_COMPLETE,
        () => this.destroyCombatEffect(effect),
      );
    }
    return effect;
  }

  destroyCombatEffect(effect) {
    this.mapObjects = this.mapObjects.filter((object) => object !== effect);
    this.skillEffects.delete(effect);
    for (const following of this.followEffects) {
      if (following.sprite === effect) this.followEffects.delete(following);
    }
    effect.destroy();
  }

  scheduleCombatTimer(delay, callback) {
    let timer;
    timer = this.time.delayedCall(delay, () => {
      this.combatTimers.delete(timer);
      callback();
    });
    this.combatTimers.add(timer);
  }

  clearCombatTimers() {
    for (const timer of this.combatTimers) timer.remove(false);
    this.combatTimers.clear();
  }

  castSkill(skillNumber) {
    if (skillNumber === 1) {
      const target = this.selectedMonster;
      if (!target?.sprite.active) return;
      this.playSelectedReleaseAction();
      const meteor = this.playSkillEffect(
        31,
        0,
        target.point.x,
        target.point.y,
        { scale: 0.48, destroyOnAnimationComplete: false },
      );
      meteor.once(Phaser.Animations.Events.ANIMATION_COMPLETE, () => {
        this.playMonsterHit(target);
        this.destroyCombatEffect(meteor);
      });
      return;
    }

    if (skillNumber === 2) {
      this.playSelectedReleaseAction();
      const anchor = this.getCharacterEffectAnchor(this.heroSprite);
      this.playSkillEffect(32, 8, anchor.x, anchor.y, {
        scale: 0.9,
        loop: true,
        durationMs: 1800,
        followPlayer: true,
        alignment: 'origin',
      });
      return;
    }

    if (skillNumber === 3) {
      this.playSelectedReleaseAction();
      this.playSkillEffect(32, 6, this.player.x, this.player.y + ICE_RING_OFFSET_Y, {
        scale: 0.62,
        alignment: 'center',
      });
      this.applyTargetControl(32, 7, 0x7beaff, 1700);
      return;
    }

    if (skillNumber === 4) {
      const target = this.selectedMonster;
      if (!target?.sprite.active) return;
      this.playSelectedReleaseAction();
      this.playSkillEffect(32, 0, target.point.x, target.point.y, { scale: 0.58 });
      this.playMonsterHit(target);
      return;
    }

    if (skillNumber === 5) {
      this.playSelectedReleaseAction();
      this.applyTargetControl(32, 1, 0xf0ec8c, 1700);
    }
  }

  applyTargetControl(spriteId, animationGroup, tint, durationMs) {
    const monster = this.selectedMonster;
    if (!monster?.sprite.active) return;
    this.playSkillEffect(
      spriteId,
      animationGroup,
      monster.point.x,
      monster.point.y,
      { scale: 0.85, loop: true, durationMs },
    );
    monster.sprite.setTint(tint);
    const controlToken = {};
    this.targetControls.set(controlToken, { monster, tint });
    this.playMonsterHit(monster);
    this.scheduleCombatTimer(durationMs, () => {
      this.targetControls.delete(controlToken);
      if (!monster.sprite.active) return;
      const activeTints = [...this.targetControls.values()]
        .filter((control) => control.monster === monster)
        .map((control) => control.tint);
      if (activeTints.length) monster.sprite.setTint(activeTints[activeTints.length - 1]);
      else monster.sprite.clearTint();
    });
  }

  getCollisionData(map, config) {
    const data = this.cache.json.get(`${config.file}-collision`);
    if (!data || data.mapResourceId !== Number(config.file.slice(-3))) {
      throw new Error(`地图 ${config.name} 缺少逐格碰撞数据`);
    }
    if (
      data.width !== map.width ||
      data.height !== map.height ||
      data.cellSize !== 36 ||
      data.blockingAttribute !== 23 ||
      !Array.isArray(data.attributes) ||
      data.attributes.length !== data.width * data.height ||
      data.attributes.some((value) => !Number.isInteger(value) || value < 0 || value > 255)
    ) {
      throw new Error(`地图 ${config.name} 的逐格碰撞数据格式无效`);
    }
    return data;
  }

  createBlockers({ width, height, cellSize, blockingAttribute, attributes }) {
    const blockers = this.physics.add.staticGroup();
    for (let y = 0; y < height; y += 1) {
      let x = 0;
      while (x < width) {
        if (attributes[y * width + x] !== blockingAttribute) {
          x += 1;
          continue;
        }
        const startX = x;
        while (x < width && attributes[y * width + x] === blockingAttribute) x += 1;
        const runWidth = (x - startX) * cellSize;
        const blocker = this.add
          .zone((startX * cellSize) + runWidth / 2, y * cellSize + cellSize / 2)
          .setSize(runWidth, cellSize);
        this.physics.add.existing(blocker, true);
        blockers.add(blocker);
      }
    }
    return blockers;
  }

  getSafeSpawn(
    requested,
    { width, height, cellSize, blockingAttribute, attributes },
    avoidPoint = null,
  ) {
    const isClear = (x, y) => {
      const left = x - PLAYER_BODY_WIDTH / 2;
      const right = x + PLAYER_BODY_WIDTH / 2;
      const top = y - PLAYER_BODY_HEIGHT;
      const bottom = y;
      if (left < 0 || top < 0 || right > width * cellSize || bottom > height * cellSize) {
        return false;
      }
      const minX = Math.floor(left / cellSize);
      const maxX = Math.floor((right - Number.EPSILON) / cellSize);
      const minY = Math.floor(top / cellSize);
      const maxY = Math.floor((bottom - Number.EPSILON) / cellSize);
      for (let tileY = minY; tileY <= maxY; tileY += 1) {
        for (let tileX = minX; tileX <= maxX; tileX += 1) {
          if (attributes[tileY * width + tileX] === blockingAttribute) return false;
        }
      }
      return true;
    };

    let nearest = null;
    let nearestDistance = Infinity;
    for (let y = 0; y < height; y += 1) {
      for (let x = 0; x < width; x += 1) {
        const candidate = { x: (x + 0.5) * cellSize, y: (y + 0.5) * cellSize };
        if (!isClear(candidate.x, candidate.y)) continue;
        if (
          avoidPoint &&
          Phaser.Math.Distance.Between(candidate.x, candidate.y, avoidPoint.x, avoidPoint.y) <
            PLAYER_BODY_WIDTH * 4
        ) {
          continue;
        }
        const distance = (candidate.x - requested.x) ** 2 + (candidate.y - requested.y) ** 2;
        if (distance >= nearestDistance) continue;
        nearest = candidate;
        nearestDistance = distance;
      }
    }
    if (!nearest) throw new Error(`地图 ${this.mapId} 没有可用的出生位置`);
    return nearest;
  }

  drawMapObjects(map) {
    const objectLayer = map.getObjectLayer('source_objects');
    if (!objectLayer) return;

    for (const object of objectLayer.objects) {
      const properties = Object.fromEntries((object.properties || []).map(({ name, value }) => [name, value]));
      const spriteId = Number(properties.spriteResourceId);
      const group = Number(properties.animationGroup || 0);
      const entry = this.getSpriteGroup(spriteId, group);
      if (!entry) throw new Error(`地图对象 sprite ${spriteId} 动画组 ${group} 缺少帧`);
      const firstFrame = entry.textureKind === 'spritesheet' ? 0 : undefined;
      const transformIndex = Number(properties.field5 || 0) & 7;
      const renderX = Number(properties.renderXpx ?? object.x);
      const renderY = Number(properties.renderYpx ?? object.y);
      const sprite = this.add.sprite(
        renderX,
        renderY,
        entry.textureKey,
        firstFrame,
      );
      applyMapObjectTransform(sprite, entry, transformIndex);
      sprite.setScale(1);
      sprite.setDepth(renderY + 1);
      if (entry.textureKind === 'spritesheet') {
        const animationKey = `map-${entry.textureKey}`;
        if (!this.anims.exists(animationKey)) {
          this.anims.create({
            key: animationKey,
            frames: entry.frames.map((frame) => ({
              key: entry.textureKey,
              frame: frame.sheetFrame,
              duration: frame.durationMs,
            })),
            duration: entry.durationMs,
            repeat: -1,
          });
        }
        sprite.play(animationKey);
      }
      this.mapObjects.push(sprite);
    }
  }

  drawPortals(teleports) {
    this.portalHints = [];
    if (!teleports.length) return;
    const portalSprite = this.getSpriteGroup(38, 1);
    if (!portalSprite || portalSprite.textureKind !== 'spritesheet') {
      throw new Error('传送阵动画资源 sprite 38 group 1 缺少帧');
    }
    const animationKey = `map-${portalSprite.textureKey}`;
    if (!this.anims.exists(animationKey)) {
      this.anims.create({
        key: animationKey,
        frames: portalSprite.frames.map((frame) => ({
          key: portalSprite.textureKey,
          frame: frame.sheetFrame,
          duration: frame.durationMs,
        })),
        duration: portalSprite.durationMs,
        repeat: -1,
      });
    }
    teleports.forEach((teleport) => {
      const point = { x: teleport.x, y: teleport.y };
      const target = teleport.targetMapId === null
        ? null
        : MAPS[String(teleport.targetMapId)];
      const exit = target && {
        to: target.id,
        label: `${target.key} · ${target.kind === '野外地图' ? target.category : target.name}`,
        entryTeleport: target.teleports.find(
          ({ id }) => id === teleport.targetTeleportId,
        ) ?? null,
        connectionId: teleport.connectionId ?? teleport.id,
      };
      const marker = this.add.sprite(point.x, point.y, portalSprite.textureKey)
        .setOrigin(portalSprite.originX, portalSprite.originY)
        .setScale(PORTAL_SCALE)
        .setDepth(point.y + 1)
        .play(animationKey);
      const labelText = exit
        ? `前往 ${exit.label}`
        : `传送点 · ${teleport.direction}`;
      const label = this.add.text(point.x, point.y - PORTAL_LABEL_OFFSET_Y, labelText, {
        fontFamily: 'Noto Sans SC, Microsoft YaHei, sans-serif',
        fontSize: '13px',
        color: '#f5fffa',
        backgroundColor: '#122a30d9',
        padding: { x: 9, y: 5 },
      }).setOrigin(0.5, 1).setDepth(point.y + 2);
      this.mapObjects.push(marker, label);
      if (exit) this.portalHints.push({ exit, point });
    });
  }

  update(_time, delta) {
    if (!this.player?.body) return;
    this.portalCooldown = Math.max(0, this.portalCooldown - delta);
    if (Phaser.Input.Keyboard.JustDown(this.roleKeys.profession)) this.cycleProfession();
    if (Phaser.Input.Keyboard.JustDown(this.roleKeys.gender)) this.cycleGender();
    if (Phaser.Input.Keyboard.JustDown(this.roleKeys.weapon)) this.cycleWeapon();
    if (Phaser.Input.Keyboard.JustDown(this.roleKeys.appearance)) this.cycleAppearance();
    if (Phaser.Input.Keyboard.JustDown(this.releaseActionKey)) {
      this.cycleReleaseActionType();
    }
    if (Phaser.Input.Keyboard.JustDown(this.nextSkillKey)) this.castNextSkill();

    const left = this.cursors.left.isDown || this.keys.A.isDown;
    const right = this.cursors.right.isDown || this.keys.D.isDown;
    const up = this.cursors.up.isDown || this.keys.W.isDown;
    const down = this.cursors.down.isDown || this.keys.S.isDown;
    let x = Number(right) - Number(left);
    let y = Number(down) - Number(up);
    const moving = x !== 0 || y !== 0;

    if (this.activeReleaseActionToken) {
      this.player.body.setVelocity(0, 0);
    } else if (moving) {
      const direction = Math.abs(x) > Math.abs(y) ? (x > 0 ? 'right' : 'left') : (y > 0 ? 'down' : 'up');
      this.facing = direction;
      const magnitude = Math.hypot(x, y);
      this.player.body.setVelocity((x / magnitude) * PLAYER_SPEED, (y / magnitude) * PLAYER_SPEED);
      if (!this.activeReleaseActionToken) {
        this.playHeroAnimation(this.heroAnimations[`${direction}-walk`]);
      }
    } else {
      this.player.body.setVelocity(0, 0);
      if (!this.activeReleaseActionToken) {
        this.playHeroAnimation(this.heroAnimations[`${this.facing}-idle`]);
      }
    }
    this.heroSprite.setFlipX(this.facing === 'right');
    this.player.setDepth(this.player.y + 1);
    this.syncHeroSpriteToPlayer();
    this.syncCameraFollowTarget();
    this.heroSprite.setDepth(this.player.y + 1);
    for (const monster of this.monsters) this.syncMonsterSprite(monster);
    if (this.selectionMarker && this.selectedMonster) {
      this.selectionMarker
        .setPosition(
          this.selectedMonster.point.x,
          this.selectedMonster.point.y + 8,
        )
        .setDepth(this.selectedMonster.point.y - 1);
    }
    for (const following of this.followEffects) {
      if (following.sprite.active) {
        const placement = this.skillEffects.get(following.sprite);
        placement.anchorX = this.player.x + following.offsetX;
        placement.anchorY = this.player.y + following.offsetY;
        this.positionSkillEffect(following.sprite, placement);
      }
    }
    this.drawDebugBounds();
    if (this.portalCooldown > 0) return;
    const exit = this.portalHints.find(({ exit: portalExit, point }) => {
      const nearMarker = Phaser.Math.Distance.Between(this.player.x, this.player.y, point.x, point.y) < 86;
      if (portalExit.connectionId === this.blockedPortalConnectionId) {
        if (nearMarker) return false;
        this.blockedPortalConnectionId = null;
      }
      return nearMarker;
    });

    if (exit) {
      this.blockedPortalConnectionId = null;
      this.switchMap(
        exit.exit.to,
        exit.exit.entryTeleport,
        exit.exit.connectionId,
      );
    }
  }
}

function showFatalError(message) {
  const error = document.querySelector('#game-error');
  if (!error) return;
  error.hidden = false;
  error.textContent = `${message}。请确认项目资源已准备完整，并查看浏览器控制台。`;
  document.querySelector('#loading-state')?.remove();
}

async function loadAssetManifest() {
  const response = await fetch(`${ASSET_ROOT}/manifest.json`);
  if (!response.ok) throw new Error(`地图资源清单加载失败：${response.status}`);
  const manifest = await response.json();
  if (
    !Array.isArray(manifest.sprites) ||
    !manifest.aoCharacters ||
    [74, 75, 76].some((spriteId) => !manifest.aoCharacters[String(spriteId)]) ||
    !Array.isArray(manifest.maps) ||
    !Array.isArray(manifest.tilesetSpriteIds) ||
    !Number.isInteger(manifest.startMapId) ||
    !manifest.maps.some(({ mapId }) => mapId === manifest.startMapId) ||
    manifest.maps.some((map) => (
      !Array.isArray(map.teleports) ||
      !Array.isArray(map.monsters) ||
      !Array.isArray(map.ambientEntities)
    ))
  ) {
    throw new Error('游戏地图资源清单格式无效');
  }
  MAPS = Object.fromEntries(manifest.maps.map((map) => [map.id, map]));
  START_MAP_ID = String(manifest.startMapId);
  window.htolMapSpriteIds = manifest.mapSpriteIds;
  window.htolWorldTilesetSpriteIds = manifest.tilesetSpriteIds;
  window.htolSprites = manifest.sprites;
  window.htolAoCharacters = manifest.aoCharacters;
}

loadAssetManifest().then(() => {
  const game = new Phaser.Game({
    type: Phaser.AUTO,
    parent: 'game',
    width: 1280,
    height: 720,
    backgroundColor: '#17251f',
    transparent: false,
    pixelArt: true,
    antialias: false,
    roundPixels: true,
    scale: { mode: Phaser.Scale.RESIZE, autoCenter: Phaser.Scale.CENTER_BOTH },
    physics: { default: 'arcade', arcade: { debug: false } },
    scene: [WorldScene],
  });
  game.events.on('blur', () => {
    const scene = game.scene.getScene('world');
    scene?.player?.body?.setVelocity(0, 0);
  });
}).catch((error) => showFatalError(error.message));
