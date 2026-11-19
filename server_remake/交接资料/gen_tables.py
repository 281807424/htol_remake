# -*- coding: utf-8 -*-
"""从 Java 源码导出交接用的 CSV 数据表。"""
import re, os, csv, io

ROOT = r'D:\HAOTIAN\HAOTIANnew'
SRC = os.path.join(ROOT, 'work', 'java', 'htol', 'gamesuper')
OUT = os.path.join(ROOT, 'work', 'handoff', 'tables')
os.makedirs(OUT, exist_ok=True)


def read(name):
    with open(os.path.join(SRC, name), encoding='utf-8') as f:
        return f.read()


def unesc(s):
    return re.sub(r'\\u([0-9a-fA-F]{4})', lambda m: chr(int(m.group(1), 16)), s)


def grab_array(text, name):
    """返回 name = { ... }; 中花括号里的内容（按括号配对）。"""
    m = re.search(r'\b' + name + r'\s*=\s*\{', text)
    if not m:
        return None
    i = m.end() - 1
    depth = 0
    for j in range(i, len(text)):
        if text[j] == '{':
            depth += 1
        elif text[j] == '}':
            depth -= 1
            if depth == 0:
                return text[i + 1:j]
    return None


def parseInts(body):
    return [int(x) for x in re.findall(r'-?\d+', body)]


def parseStrings(body):
    return [unesc(x) for x in re.findall(r'"((?:[^"\\]|\\.)*)"', body)]


def nested_rows(body):
    """把 {{a,b},{c,d}} 解析成 [[a,b],[c,d]]（只处理两层，元素为串或数字）。"""
    rows = []
    depth = 0
    start = None
    for i, ch in enumerate(body):
        if ch == '{':
            depth += 1
            if depth == 1 and start is None:
                start = i + 1
        elif ch == '}':
            if depth == 1 and start is not None:
                rows.append(body[start:i])
                start = None
            depth -= 1
    return rows


LS = read('LocalServer.java')

# ---------- 地图表 ----------
names = parseStrings(grab_array(LS, 'MAP_NAMES'))
zones = parseInts(grab_array(LS, 'MAP_ZONE_IDS'))
levels = parseInts(grab_array(LS, 'MAP_BASE_LEVEL'))
cities = parseInts(grab_array(LS, 'MAP_IS_CITY'))
res = parseInts(grab_array(LS, 'MAP_RES_IDS'))

with open(os.path.join(OUT, '地图表.csv'), 'w', encoding='utf-8-sig', newline='') as f:
    w = csv.writer(f)
    w.writerow(['序号', '地图名', 'zoneId(分区)', '地图资源号 mapResId',
                '怪物基础等级', '类型', '资源文件'])
    for i in range(len(names)):
        t = {0: '野外', 1: '安全区(主城/村)', 2: '妖族占领地(怪+2级)'}[cities[i]]
        w.writerow([i, names[i], zones[i], res[i], levels[i], t, 'assets/%d' % (7 + res[i])])

# ---------- 部位 / 品质 / 图标 ----------
slot_names = parseStrings(grab_array(LS, 'SLOT_NAMES'))
slot_ui = parseInts(grab_array(LS, 'SLOT_UI'))
slot_key = parseInts(grab_array(LS, 'SLOT_KEY'))
view_job = [parseInts(r) for r in nested_rows(grab_array(LS, 'SLOT_VIEW_JOB'))]
qnames = parseStrings(grab_array(LS, 'QUALITY_NAMES'))
qmul = [float(x) for x in re.findall(r'\d+\.\d+', grab_array(LS, 'QUALITY_MUL'))]

with open(os.path.join(OUT, '部位与图标.csv'), 'w', encoding='utf-8-sig', newline='') as f:
    w = csv.writer(f)
    w.writerow(['内部槽位', '部位名', 'UI槽位 SLOT_UI', '装备栏key SLOT_KEY',
                '先锋图标帧', '百步图标帧', '天灵图标帧'])
    for i in range(len(slot_names)):
        w.writerow([i, slot_names[i], slot_ui[i], slot_key[i],
                    view_job[0][i], view_job[1][i], view_job[2][i]])

with open(os.path.join(OUT, '品质表.csv'), 'w', encoding='utf-8-sig', newline='') as f:
    w = csv.writer(f)
    w.writerow(['品质索引', '名称', '属性倍率', '宝石孔数'])
    for i, n in enumerate(qnames):
        label = n if n else '白'
        w.writerow([i, label, qmul[i] if i < len(qmul) else '', i if i <= 5 else ''])

# ---------- 装备属性 ----------
ATTR = ['物攻', '法攻', '物防', '法防', '暴击', '命中', '闪避', '格挡', '生命', '法力',
        '速度', '暴击倍数', '命中系数', '坚韧', '穿透']

GD = read('GearData.java')
tnode = GD[GD.index('TABLE'):]
rows = nested_rows(tnode)
# rows 顺序：每个 job 13 行，每行 10 个字符串
attrs = {}
flat = []
for r in rows:
    strs = re.findall(r'"([^"]*)"', r)
    if strs:
        flat.append(strs)
# flat: 30 行（3 job × 13? 实际嵌套层级不同），按字符串块重组
all_str = re.findall(r'"([^"]*)"', tnode)
per_job = 13 * 10
jobs = ['先锋', '百步', '天灵']
with open(os.path.join(OUT, '装备属性表.csv'), 'w', encoding='utf-8-sig', newline='') as f:
    w = csv.writer(f)
    w.writerow(['职业', '等级', '档位idx', '部位'] + ATTR)
    for job in range(3):
        for idx in range(13):
            for slot in range(10):
                k = job * per_job + idx * 10 + slot
                if k >= len(all_str):
                    continue
                s = all_str[k]
                vals = [0] * 15
                for pair in s.split(','):
                    if ':' in pair:
                        a, v = pair.split(':')
                        vals[int(a)] = int(v)
                attrs[(job, idx, slot)] = vals
                w.writerow([jobs[job], 10 + idx * 5, idx, slot_names[slot]] + vals)

# ---------- 装备名称 ----------
GN = read('GearNames.java')
gn_table = gn_table_str = None
m = re.search(r'TABLE\s*=\s*', GN)
tstart = m.end()
# TABLE 到 ARTIFACT 之前
a = re.search(r'ARTIFACT\s*=\s*', GN)
tbl_block = GN[tstart:a.start()]
names_all = [unesc(x) for x in re.findall(r'"((?:[^"\\]|\\.)*)"', tbl_block)]
art_block = GN[a.end():]
art = [unesc(x) for x in re.findall(r'"((?:[^"\\]|\\.)*)"', art_block)]

with open(os.path.join(OUT, '装备名称表.csv'), 'w', encoding='utf-8-sig', newline='') as f:
    w = csv.writer(f)
    w.writerow(['职业', '等级', '档位idx', '部位', '装备名'])
    for job in range(3):
        for idx in range(13):
            for slot in range(10):
                k = job * per_job + idx * 10 + slot
                if k < len(names_all):
                    w.writerow([jobs[job], 10 + idx * 5, idx, slot_names[slot], names_all[k]])

with open(os.path.join(OUT, '神器名称表.csv'), 'w', encoding='utf-8-sig', newline='') as f:
    w = csv.writer(f)
    w.writerow(['职业', '部位', '神器名'])
    n = len(art)
    for job in range(min(3, max(1, n // 10))):
        for slot in range(10):
            k = job * 10 + slot
            if k < n:
                w.writerow([jobs[job], slot_names[slot], art[k]])

# ---------- NPC ----------
npc_names = parseStrings(grab_array(LS, 'NPC_NAMES'))
npc_spr = parseInts(grab_array(LS, 'NPC_SPRITES'))
npc_defs = [parseInts(r) for r in nested_rows(grab_array(LS, 'NPC_DEFS'))]
with open(os.path.join(OUT, 'NPC表.csv'), 'w', encoding='utf-8-sig', newline='') as f:
    w = csv.writer(f)
    w.writerow(['序号', 'charId', 'NPC_DEFS精灵号', 'x', 'y', 'NPC_SPRITES精灵号', '名字'])
    for i in range(min(len(npc_names), len(npc_defs))):
        d = npc_defs[i]
        w.writerow([i, d[0] if d else '', d[1] if len(d) > 1 else '',
                    d[2] if len(d) > 2 else '', d[3] if len(d) > 3 else '',
                    npc_spr[i] if i < len(npc_spr) else '', npc_names[i]])

print('导出完成 ->', OUT)
for fn in sorted(os.listdir(OUT)):
    print('  ', fn, os.path.getsize(os.path.join(OUT, fn)))
