# 浩天奇缘客户端分析与本地服务

主要内容：分析一个早已经停服的java手游的客户端逻辑，并搭建本地服务端。
客户端已经反编译，但是变量名、方法名都是经过混淆的。原服务端完全缺失，所以只能通过逆向客户端来获取关键信息。

子项目client_remake的目的有所不同。
## 项目规则

- 原始 JAR 和 `decompiled/` 是证据基准，不要覆盖或修改。
- 客户端游戏逻辑保持不变；本地认证、区服和游戏服务端实现与客户端分析稿分开。
- 为阅读而重命名混淆符号或添加注释时，写入 `analysis/client-readable/`，并记录到 `analysis/symbol-map.csv`；不可把分析稿当作客户端源码重新编译。
- 区分静态代码证据、资源证据、运行时观察、推断和未知项。自造报文测试不等于客户端兼容性验证。
- 不访问已停服的原服务、不向外部转发账号信息。未经真实客户端验证，不宣称登录或游戏流程已经兼容。
- 每次改客户端地址都从原始 JAR 单独生成副本，记录精确改动并检查其他成员未变。

## 已有成果索引

- [客户端协议、资源、当前服务端边界和待验证问题](analysis/CLIENT_PROTOCOL.md)
- [地图资源清单、定远城候选及场景进入待验证项](analysis/MAP_ASSETS.md)
- [传送点协议、客户端绘制方式及缺失的服务端路由数据](analysis/TELEPORT_POINTS.md)
- [角色进入地图后的属性、生命/法力、外观与技能初始化分析](analysis/CHARACTER_INITIALIZATION.md)
- [客户端任务界面、任务更新协议及当前空列表原因](analysis/TASKS.md)
- 原始反编译源码：[decompiled/](decompiled/)
- 已解析的客户端文字表：[local_text_tables.json](local_text_tables.json)
- 地图资源结构与静态对象解析（建筑/场景物件，不等同传送点）：[analysis/parse_map_assets.py](analysis/parse_map_assets.py)、[analysis/map_catalog.json](analysis/map_catalog.json)、[analysis/parse_map_actors.py](analysis/parse_map_actors.py)、[analysis/map_actor_catalog.json](analysis/map_actor_catalog.json)
- 本地服务原型：[local_version_server.py](local_version_server.py)
- 交接地图、NPC 与装备数据加载：[local_game_data.py](local_game_data.py)
- 本地协议测试：[tests/test_local_version_server.py](tests/test_local_version_server.py)
- 原始 JAR：`htol_local.jar`（本项目逆向分析所需的原客户端文件）
- 仅替换登录地址的副本：`浩天奇缘-神装现世_触摸屏通用版(360x640)_local_update.jar`

后续会话开始分析或实现前，先阅读本文件及 [CLIENT_PROTOCOL.md](analysis/CLIENT_PROTOCOL.md)，复用已有证据并更新相应分析文件；不要把详细解析结果堆入 README。

此README.md允许更改修正。
当解析到新关键时，写入现有相应文件。
当解析到新类别的信息时，鼓励新开文件来记录。
当解析到与现有信息相违背的信息时，应该修正旧信息的描述。
必要时，允许对现有信息进行汇总。

## 本地 Python 服务端

继续使用指定的 `_local_update.jar` 客户端，服务端由 `local_version_server.py` 提供。当前实现涵盖合成角色登录、进图初始化、地图导航与传送、NPC 对话、基础商店/背包/装备交互，以及合成战斗和角色存档；地图、NPC、装备数据来自交接资料 CSV。实现边界、协议依据和未验证事项见 [analysis/CLIENT_PROTOCOL.md](analysis/CLIENT_PROTOCOL.md)。

这些功能尚未通过目标 JAR 的端到端实测；合成协议单元测试不代表客户端兼容性。任务内容、正式游戏数据和真实服务端语义仍未确认。