# 浩天奇缘离线版 — 项目状态

## 构建
- 服务端编译：`powershell -NoProfile -ExecutionPolicy Bypass -File work\build_server.ps1`
  （javac --release 8 → d8，产物 `work/java/dex/classes.dex`）
- 一键构建：`powershell -NoProfile -ExecutionPolicy Bypass -File work\build.ps1`
- 产物：`work\build\offline.apk` → 复制为 `浩天奇缘OL-离线版.apk`
- 服务端源码：`work\java\htol\gamesuper\LocalServer.java`（编译进 classes2.dex）
- 客户端 smali：`work\build\smali\`（baksmali 自 base.apk，改完由 build.ps1 回编）
- 原始 smali 备份：`work\recover\`（从 base.apk 反汇编，用于恢复被误删的方法）

## 模拟器
- MuMu：`D:\Program Files\Netease\MuMu Player 12\nx_main\adb.exe`
- 需要 `ANDROID_ADB_SERVER_PORT=5039`，设备名常变（16384 / emulator-5556），连不上就 `kill-server` 再连
- 登录流程：进主菜单点两次"快速进入"，到角色列表点角色 → 确定 → 菜单"进入游戏"

## 关键协议结论
- 521 里的地图字段 = **地图资源编号**，不是逻辑地图 id。填 0 时怪物正常；填 7 会导致怪物被过滤（名字会变但没怪）。现已回退为 0。
- 2104 怪物包第二个 int 是地图字段，必须与 521 一致（当前都是 0）。
- 950 背包初始化：type 0 背包、type 1 装备栏。装备栏由客户端补丁从背包镜像（InitStatePackage）。
- 958 = 使用物品（defId ushort + pos + target）。
- 960 = 装备、965 = 卸下。
- 1088 = 确认物品操作（未用）。
- 619 经验、621 金币、622 基础属性、623 详细属性（下标：0物攻 1物防 2法攻 3法防 4暴击 5闪避 6命中 7格挡 8坚韧 9穿透）。
- 客户端 MAX_LEVEL = 70。

## 客户端补丁（work/build/smali）
1. `InitStatePackage`：预创建装备包 + 背包镜像到装备栏（按 m_EquipPos）
2. `pointerReleased`：左下角(游戏坐标 x<140, y>420) → `CPackage.InitMenu()`
3. 无等待框：SendRigRequest / 卸下分支移除 showWait

## 服务端要点
- 经验表 EXP_TABLE[1..70]（官方文档），升级重算属性
- 三职业：先锋/百步/天灵，成长在 baseHp/hpGrow/baseAtk/... 方法里
- 地图表：13 张（桃夭镇→青峰崖，1/5/10/18/25/32/40/48/55/62/68/72/76），怪物等级 = 基础..+4
- 16 只怪，坐标固定（仅第一张图验证过）
- 存档：/data/data/htol.gamesuper/files/htol_save.txt，关键节点保存
- 掉落：25% 装备（品质名后缀）、55% 金创药、金币 5~15

## 图标帧表（work/icon_frames.md）
含 0~59 帧的识别结果；装备图标都在 0~29。
注意：帧 3 用户反馈是"斧头"不是剑。

## 待办
1. 装备属性：写进物品的 4 组 property，服务端伤害计算叠加
2. 每张地图独立刷怪坐标（或解析地图阻挡层自动选点）
3. NPC 交互：633 现在只负责显示（type=0），商店/仓库/修理/传送需填 `NPC_TYPE_*` 并做对应下行
4. 卫承坐标 (300,180) 在默认镜头边缘，建议挪到城楼前空地
5. 技能系统（1220 已能下发技能栏）
6. 武器图标换成合适的帧（3 是斧头）
7. 移除调试代码：每 5 秒 1264 武器探针、自动打怪

## 原版 J2ME 源码（work\j2mesrc）关键发现
- 2108 = 攻击者id(int) + 目标id(int) + Anim(short)，Anim 是动画表下标（0~31）
- cn.aj = 32 组动画帧序列（以 39 结束）
- 521 解析（cc.bh()）：地图id(int) → 精灵数(byte≤40) → 精灵id(short[] ) →
  宽(int) 高(int) ?(int) → 地图资源编号(byte,int) → 若干字段 → 再一串传送点列表
- 2108→cc.W()，2109→X()(死亡)，2110→Y()(重生/坐标)
- 怪物动画有偏移映射 cr.x(n): bp==1? n : n+22

## 已实现：菜单传送（2026-10-21 验证通过）
- 菜单 → 导航（连点两下）→ 客户端发 cmd 3006 → 服务端 advanceMap()
- advanceMap: sMapIndex+1，发 521(新地图名) + 2110(玩家回出生点) + 每只怪 2104(新等级)
- 传送后补 5 瓶金创药并回满血
- 实测日志：map advance to 桃花小径 level base 5
- 注意：地图美术不变（521 资源字段未吃透），只改 id/名字/怪物等级
- 药水掉率 0.55 → 0.8

## 协议结论补充
- 客户端基本不发 602（仅进图 3 次），坐标不可依赖 → 传送改用菜单触发
- 导航菜单项 = cmd 3006（脚本菜单请求），已被复用为传送
- 1079 (CMD_CS_REQUEST_ITEM_OP_MENU) 已禁用，避免背包点选乱弹菜单

## 当前进度（第 11 轮）
- 导航环路已验证：定远城→桃夭镇→桃花小径→…→九歌城→回定远城，场景真实切换
- 药水掉落：80% 概率 × 5 瓶
- 未完成：地图自选列表(2503)、进游戏直达定远城、NPC 剧情(633)

## 协议结论（供后续）
- 521 只有在客户端处于"请求地图"流程时才会被采纳（菜单→导航触发 3006 后）
- 服务端单方面推 521：进图时卡"读取中"，游戏中无效
- 2503 列表对话框：直接推会导致弹"请稍候"，字段结构未对齐
- 633 NPC：加入后曾导致怪物列表丢失，字段需重新校准

## 2026-10-21：进图序列抓取 + 633 NPC 落地（已验证）

- 完整收发序列与逐字段比对：`work/进图收发序列与633核对.md`
- 原始日志：`work/logcat_final2.txt`（logcat 原样）、`work/entry_block_final.txt`
- 截图：`work/final_city4.png`（定远城城池 + NPC，NPC 可选中）

关键结论：
- **521** 必须按 `CMD_SC_SEND_RES` 顺序：`int tileSetId, byte sprCount, short[] sprIds, int zoneId,
  int mapId, int worldMapId, byte pal, byte mapResId, int zoneFlags, byte orgId, string8 name,
  byte doorCount, byte hasDefault`。旧代码错位，zoneId 被读成 0。
- **2112** 的 `int zoneId` 会覆盖客户端 `s_ZoneId`；必须与 521/2104/633 完全一致（定远城=12）。
- **633** 逐 NPC：`int charId, int defineId, byte type, byte dir, short spr, byte pal, int zone,
  short x, short y, string8 name, byte storeType`；type 11/12 再追加 3 个采集字段。
- 地图资源号：定远城/桃夭镇用 **6**（`assets/13` 城池）；7 是幽暗森林。
- 地图自动推进（每 10 杀）已移除；523 选角时强制回到定远城，避免在选角界面被自动战斗推图。
- 已实现 6 个 NPC：卫承(101)、药品商人(109)、铁匠(110)、仓库管事(107)、传送阵守卫(106)、练功馆教头(105)。

## 2026-10-22：NPC 对话 + 主城安全区（已验证）

- **NPC 交互协议**：客户端选中 NPC 后先发 `2115 CMD_CS_SELECT_TARGET(int actorId)`，
  再发 `607 CMD_CS_CHAT_TO_NPC(int npcCharId)`；服务端用 **2200 CMD_SC_SCRIPT_DIALOG**
  回对白，`style=3` 时才以“游戏中叠加对白框”渲染（style 1 进 state 73 不显示）。
  2200 字段：`int callbackId, string32 title, byte style, byte optionNum, short backDialogId,
  string32 text, byte menuCount, [string8 label, short, int arg]..., short argCount`。
- 已为 6 个 NPC 配置对白（卫承城主开场白取自《地图任务设定》）；验证截图 `work/dialog_style3.png`。
- **定远城改为安全区**：`MAP_IS_CITY==1` 时不再下发 2104 怪物，也不再自动索敌/攻击。
- 修复 **623 属性包**少发 5 个 short 导致客户端 `ArrayIndexOutOfBoundsException`。
- 修复 **背包无限膨胀**（8 小时挂机攒了 481 件，950 包 34KB）导致客户端反复重连：
  背包上限 30 格，`addItem` / `loadAll` / `buildInitPack` 三处截断。
- 卫承坐标移到 (300,336)，默认镜头内可见。
- 新增自动登录脚本：`work/drive_login2.ps1`（含原生输入框重试）。


## 2026-10-22（第 3 轮）：装备实装 + 品质 + NPC 功能

- 装备数据生成：work/gen_gear.py -> work/java/htol/gamesuper/GearData.java
  （3 职业 x 13 档 x 10 部位 = 390 件，属性取自《角色等级.docx》）
- writeItem 按 CItem.InitOneItem 重写：4 组属性、品质、需求等级、评分、买卖价
- 品质 0~5 = 白/蓝/紫/橙/青/神器，倍率 1.0/1.25/1.55/1.9/2.3/3.2
- 
efreshStats 汇总装备 15 项属性；伤害/防御/暴击/格挡参与计算
- 实测：70 级先锋生命 2980 -> 3230，法力 100 -> 140
- 商店：607(9101) -> 951 头 + 页签；952 -> 951 货架（4 件商品）；880/883 已实现
- 传送：607(9104) -> 3007 菜单（13 图），3006(1000+i) -> jumpToMap
- 详见 work/装备与NPC功能实装.md


## 2026-10-22（第 7 轮）：NPC 功能全部打通

- 出售：收购商 607=9106 -> 一键出售白装，实测 28 件 +700 金币
- 修理：铁匠 607=9102 -> 全部修理，弹窗验证
- 仓库：传送菜单新增“存取材料”(3003 id 2201) -> depot toggle，实测 n=2
- 商店购买：1079 defId 直购，金币 17259 -> 17249
- 传送/对话/装备属性品质均已验证


## 2026-10-22 追加：武器造型固定

- 根因：服务端每 5 秒的 weapon probe 会循环下发 1264(slot 8, 0x11..0x19)，导致身上武器造型一直变。
- 已删除该探针；改为固定造型 + 每 3 秒重发同一个 1264（防止移动/状态包把造型冲掉），装备不再自动频繁替换
  （autoEquipBest 只在评分高出 8 分以上才换）。
- 副产物：进入地图时先 autoEquipBest -> 下发 950 装备栏 -> 再下发 1264，顺序修正。
- 已用 9 次扫描（0x11..0x19）确认 1264(slot 8, display) 就是武器外观编码，
  job0 用 0x11、job1 用 0x21、job2 用 0x31；扫描代码已删除，改为每 3 秒重发同一值。
- 实测：隔 12 秒两张截图武器完全一致，日志 7 条 1264 全是 `08 1100`，无任何 scan 记录。


## 2026-10-22：装备名称文档实装

- `work/gen_names.py` 解析 `装备名称.docx` -> `GearNames.java`（3×13×10 + 3 套神器）
- makeEquip 按文档命名；蓝/紫/橙/青加 `·品质` 后缀，神器用套装名
- 旧存档打包时自动改名
- 实测客户端装备包已显示文档名（铁卫戒指）

## 一键构建
powershell -NoProfile -ExecutionPolicy Bypass -File work\build_server.ps1
powershell -NoProfile -ExecutionPolicy Bypass -File work\build.ps1
