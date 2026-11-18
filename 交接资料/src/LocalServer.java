package htol.gamesuper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.OutputStreamWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Embedded offline server for the game client.
 * Listens on 127.0.0.1 for the login server (28000) and game server (28001).
 */
public final class LocalServer {

    private static final byte[] KEY = new byte[] {
        0x3a,0x74,0x55,0x2b,0x61,0x44,0x79,0x2e,0x3e,0x70,0x19,0x4c,0x1e,0x0d,0x48,0x1e,
        0x66,0x09,0x1a,0x33,0x02,0x2c,0x25,0x10,0x1d,0x3f,0x0f,0x69,0x43,0x5e,0x6b,0x52,
        0x01,0x7a,0x14,0x27,0x27,0x41,0x06,0x5b,0x28,0x0e,0x2a,0x62,0x7d,0x63,0x49,0x34,
        0x56,0x3e,0x1a,0x76,0x09,0x78,0x3d,0x24,0x5f,0x3b,0x4b,0x6c,0x78,0x5b,0x3d,0x4c,
        0x5e,0x6f,0x5d,0x3c,0x7e,0x4c,0x3e,0x37,0x34,0x12,0x2e,0x0e,0x37,0x3f,0x37,0x1b,
        0x1e,0x42,0x40,0x5e,0x6d,0x15,0x7e,0x7b,0x6b,0x45,0x1e,0x4b,0x2f,0x79,0x2d,0x5c,
        0x1e,0x7d,0x1d,0x6c,0x2b,0x50,0x38,0x04,0x0f,0x4a,0x2e,0x22,0x6b,0x6f,0x2a,0x4a,
        0x1a,0x4d,0x50,0x6d,0x5f,0x1e,0x76,0x39,0x3d,0x4a,0x69,0x59,0x15,0x01,0x4d,0x39,
        0x5a,0x57,0x6d,0x51,0x3a,0x7d,0x6d,0x28,0x3f,0x2b,0x72,0x4c,0x71,0x6e,0x5d,0x1b,
        0x2d,0x3c,0x29,0x28,0x19,0x55,0x44,0x3c,0x4d,0x4a,0x25,0x5d,0x15,0x2a,0x6c,0x2a,
        0x1b,0x5f,0x3f,0x19,0x28,0x5f,0x28,0x3b,0x11,0x2f,0x67,0x5f,0x51,0x4e,0x62,0x19,
        0x2c,0x3e,0x64,0x32,0x7e,0x46,0x69,0x08,0x10,0x24,0x3f,0x66,0x0a,0x1d,0x2f,0x3e,
        0x4d,0x40,0x7f,0x5b,0x7e,0x45,0x44,0x5d,0x08,0x1b,0x2e,0x47,0x63,0x3e,0x1b,0x70,
        0x4d,0x57,0x71,0x5d,0x08,0x5c,0x6a,0x64,0x28,0x1d,0x04,0x48,0x12,0x22,0x4b,0x38,
        0x1b,0x4c,0x2b,0x5d,0x1e,0x4b,0x7b,0x29,0x7e,0x6f,0x3c,0x7c,0x49,0x29,0x1b,0x38,
        0x2a,0x67,0x7c,0x4f,0x27,0x3a,0x3f,0x4c,0x2c,0x2a,0x71,0x25,0x61,0x1b,0x59,0x1d
    };

    private static final int ACC_ID = 10001;
    private static final String[] MAP_NAMES = {"\u6843\u592d\u9547", "\u6843\u82b1\u5c0f\u5f84", "\u7acb\u9a6c\u5761", "\u843d\u82f1\u8c37", "\u5b9a\u8fdc\u57ce\u90ca", "\u5b9a\u8fdc\u57ce", "\u82cd\u832b\u8349\u539f", "\u7559\u9999\u5c71", "\u523a\u9aa8\u4e4b\u5730", "\u795e\u6708\u8c37", "\u7834\u519b\u539f", "\u53e4\u796d\u53f0", "\u4e07\u548c\u96c6\u5e02", "\u8bae\u56fd\u5ead", "\u6843\u82b1\u575e", "\u7eeb\u7f57\u57ce", "\u9752\u5cf0\u5d16", "\u4e5d\u6b4c\u57ce"};
    private static final int[] MAP_ZONE_IDS = {7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24};
    private static final int[] MAP_BASE_LEVEL = {1, 5, 10, 18, 25, 25, 32, 40, 48, 50, 55, 62, 65, 68, 72, 75, 76, 80};
    private static final int[] MAP_IS_CITY = {1, 0, 0, 0, 0, 1, 0, 0, 0, 2, 0, 0, 2, 0, 0, 2, 0, 2};
    private static final int[] MAP_RES_IDS = {6, 9, 20, 13, 12, 6, 29, 28, 10, 21, 19, 17, 22, 18, 9, 23, 10, 27};
    /** Sprite ids used by the 633 NPC list (verified humanoid zone sprites). */
    private static final int[] NPC_SPRITES = {101, 109, 110, 106, 105, 103};
    /** 633 NPC table: charId, sprite, x, y, npcType, storeType */
    private static final int[][] NPC_DEFS = {
        {9100, 101, 300, 336, 0, 0},   // 卫承（城主）
        {9101, 109, 252, 252, 0, 0},   // 药品商人
        {9102, 110, 348, 252, 0, 0},   // 铁匠
        {9103, 110, 252, 360, 0, 0},   // 仓库管事
        {9104, 106, 252, 300, 0, 0},   // 传送阵守卫
        {9105, 105, 348, 300, 0, 0},   // 练功馆教头
        {9106, 103, 372, 360, 0, 0}    // 收购商
    };
    private static final String[] NPC_NAMES = {
        "\u536b\u627f", "\u836f\u54c1\u5546\u4eba", "\u94c1\u5320",
        "\u4ed3\u5e93\u7ba1\u4e8b", "\u4f20\u9001\u9635\u5b88\u536b", "\u7ec3\u529f\u9986\u6559\u5934",
        "\u6536\u8d2d\u5546"
    };
    private static int sMapIndex = 5;

    private static final int[] EXP_TABLE = {0, 0, 120, 280, 480, 720, 1000, 1320, 1680, 2080, 2520, 3100, 3720, 4380, 5080, 5820, 6600, 7420, 8280, 9180, 10120, 11300, 12520, 13780, 15080, 16420, 17800, 19220, 20680, 22180, 23720, 25600, 27520, 29480, 31480, 33520, 35600, 37720, 39880, 42080, 44320, 47100, 49920, 52780, 55680, 58620, 61600, 64620, 67680, 70780, 73920, 77900, 81920, 85980, 90080, 94220, 98400, 102620, 106880, 111180, 115520, 120700, 125920, 131180, 136480, 141820, 147200, 152620, 158080, 163580, 169120};
    private static final int SPAWN_X = 300;
    private static final int SPAWN_Y = 252;
    private static final int MAX_CHARS = 6;

    private static volatile boolean sStarted = false;

    private static final class CharInfo {
        int slot;
        int id;
        String name;
        int sex;
        int job;
        int nation;
        int level = 1;
        int exp = 0;
        int maxExp = 120;
        int hp = 1000;
        int maxHp = 200;
        int atk = 10;
        int def = 0;
        int maxMp = 100;
        int mp = 100;
        int mag = 0;
        int mdef = 0;
        int crit = 200;
        int dodge = 100;
        int hit = 200;
        int block = 100;
        int tough = 100;
        int pen = 100;
        int px = 300;
        int py = 252;
        final java.util.Map<Integer, Long> cdUntil = new java.util.HashMap<Integer, Long>();
        long atkBuffUntil = 0;
        int atkBuffPct = 0;
        long critBuffUntil = 0;
        int critBuffPct = 0;
        long stealthUntil = 0;
        int gold = 100;
        final List<Item> items = new ArrayList<Item>();
        final List<Item> equipped = new ArrayList<Item>();
        final List<Item> depot = new ArrayList<Item>();
    }

    private static final class Item {
        int defId;
        String name;
        int viewId;
        int count;
        int pos;
        int type = 2;
        int equipPos = 0;
        int quality = 0;
        int propIdx = -1;
        int propVal = 0;
        int jobReq = 0;
        int subType = 0;
        int reqLevel = 1;
        int score = 0;
        int[] stats = new int[15];

        Item(int defId, String name, int viewId, int count, int pos) {
            this.defId = defId;
            this.name = name;
            this.viewId = viewId;
            this.count = count;
            this.pos = pos;
        }
    }

    private static final class Monster {
        int id;
        int sprId;
        String name;
        int level;
        int maxHp;
        int hp;
        int x;
        int y;
        boolean alive = true;
        long respawnAt = 0;
        long stunUntil = 0;
    }

    private static final List<CharInfo> sChars = new ArrayList<CharInfo>();
    private static final List<Monster> sMonsters = new ArrayList<Monster>();
    private static int sNextCharId = 1001;
    private static int sCurCharId = -1;
    private static volatile boolean sMonstersReady = false;
    private static volatile boolean sInGame = false;
    private static volatile boolean sShopOpen = false;
    private static long sWeaponProbeAt = 0;
    /** One delayed re-send of the fixed weapon look (client needs the actor ready). */
    private static long sWeaponRefreshAt = 0;
    private static int sWeaponScanId = 0;
    private static int sKills = 0;
    private static int sScanRes = 6;
    private static int sActionProbe = 0;
    private static int sWeaponProbeId = 0;

    private LocalServer() {
    }


    private static final String SAVE_DIR = "/data/data/htol.gamesuper/files";
    private static final String SAVE_FILE = SAVE_DIR + "/htol_save.txt";

    private static void loadItemExtra(Item it, String[] a) {
        if (a.length >= 12) {
            try {
                it.quality = Integer.parseInt(a[7]);
                it.reqLevel = Integer.parseInt(a[8]);
                it.score = Integer.parseInt(a[9]);
                it.jobReq = Integer.parseInt(a[10]);
                String[] st = a[11].split(",");
                for (int i = 0; i < 15 && i < st.length; i++) {
                    it.stats[i] = Integer.parseInt(st[i]);
                }
            } catch (Exception e) {
            }
        }
        if (it.type == 1) {
            int sum = 0;
            for (int i = 0; i < 15; i++) {
                sum += it.stats[i];
            }
            if (sum == 0) {
                // legacy save: rebuild stats from level/slot
                int slot = equipPosToSlot(it.equipPos);
                int lvl = it.reqLevel > 1 ? it.reqLevel : 10;
                Item g = makeEquip(null, lvl, slot, it.quality);
                it.stats = g.stats;
                it.reqLevel = g.reqLevel;
                it.score = g.score;
                if (it.viewId <= 0) {
                    it.viewId = g.viewId;
                }
            }
        }
    }

    private static int equipPosToSlot(int equipPos) {
        for (int i = 0; i < SLOT_EQUIP.length; i++) {
            if (SLOT_EQUIP[i] == equipPos) {
                return i;
            }
        }
        return 0;
    }

    private static String itemLine(Item it) {
        StringBuilder sb = new StringBuilder();
        sb.append(it.defId).append('\t').append(it.name).append('\t').append(it.viewId).append('\t')
                .append(it.count).append('\t').append(it.pos).append('\t').append(it.type).append('\t')
                .append(it.equipPos).append('\t').append(it.quality).append('\t').append(it.reqLevel)
                .append('\t').append(it.score).append('\t').append(it.jobReq).append('\t');
        for (int i = 0; i < 15; i++) {
            if (i > 0) {
                sb.append(',');
            }
            sb.append(it.stats[i]);
        }
        return sb.toString();
    }

    private static void saveAll() {
        try {
            new File(SAVE_DIR).mkdirs();
            PrintWriter w = new PrintWriter(new OutputStreamWriter(new FileOutputStream(SAVE_FILE), "UTF-8"));
            w.println("V1");
            synchronized (sChars) {
                w.println(sChars.size());
                for (CharInfo c : sChars) {
                    w.println(c.slot + "\t" + c.id + "\t" + c.name + "\t" + c.sex + "\t" + c.job + "\t"
                            + c.nation + "\t" + c.level + "\t" + c.exp + "\t" + c.maxExp + "\t" + c.gold
                            + "\t" + c.hp + "\t" + c.maxHp);
                    w.println(c.items.size());
                    for (Item it : c.items) {
                        w.println(itemLine(it));
                    }
                    w.println(c.equipped.size());
                    for (Item it : c.equipped) {
                        w.println(itemLine(it));
                    }
                    w.println(c.depot.size());
                    for (Item it : c.depot) {
                        w.println(itemLine(it));
                    }
                }
            }
            w.close();
        } catch (Throwable e) {
            log("save error " + e);
        }
    }

    private static void loadAll() {
        try {
            File f = new File(SAVE_FILE);
            if (!f.exists()) {
                return;
            }
            BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF-8"));
            String head = r.readLine();
            if (head == null || !head.equals("V1")) {
                r.close();
                return;
            }
            int n = Integer.parseInt(r.readLine().trim());
            synchronized (sChars) {
                sChars.clear();
                for (int i = 0; i < n; i++) {
                    String[] f1 = r.readLine().split("\t");
                    CharInfo c = new CharInfo();
                    c.slot = Integer.parseInt(f1[0]);
                    c.id = Integer.parseInt(f1[1]);
                    c.name = f1[2];
                    c.sex = Integer.parseInt(f1[3]);
                    c.job = Integer.parseInt(f1[4]);
                    c.nation = Integer.parseInt(f1[5]);
                    c.level = Integer.parseInt(f1[6]);
                    c.exp = Integer.parseInt(f1[7]);
                    c.maxExp = Integer.parseInt(f1[8]);
                    c.gold = Integer.parseInt(f1[9]);
                    c.hp = Integer.parseInt(f1[10]);
                    c.maxHp = Integer.parseInt(f1[11]);
                    int ni = Integer.parseInt(r.readLine().trim());
                    for (int k = 0; k < ni; k++) {
                        String[] a = r.readLine().split("\t");
                        Item it = new Item(Integer.parseInt(a[0]), a[1], Integer.parseInt(a[2]),
                                Integer.parseInt(a[3]), Integer.parseInt(a[4]));
                        it.type = Integer.parseInt(a[5]);
                        it.equipPos = Integer.parseInt(a[6]);
                        loadItemExtra(it, a);
                        c.items.add(it);
                    }
                    int ne = Integer.parseInt(r.readLine().trim());
                    for (int k = 0; k < ne; k++) {
                        String[] a = r.readLine().split("\t");
                        Item it = new Item(Integer.parseInt(a[0]), a[1], Integer.parseInt(a[2]),
                                Integer.parseInt(a[3]), Integer.parseInt(a[4]));
                        it.type = Integer.parseInt(a[5]);
                        it.equipPos = Integer.parseInt(a[6]);
                        loadItemExtra(it, a);
                        c.equipped.add(it);
                    }
                    String depotLine = r.readLine();
                    int nd = depotLine == null ? 0 : Integer.parseInt(depotLine.trim());
                    for (int k = 0; k < nd; k++) {
                        String[] a = r.readLine().split("\t");
                        Item it = new Item(Integer.parseInt(a[0]), a[1], Integer.parseInt(a[2]),
                                Integer.parseInt(a[3]), Integer.parseInt(a[4]));
                        it.type = Integer.parseInt(a[5]);
                        it.equipPos = Integer.parseInt(a[6]);
                        loadItemExtra(it, a);
                        c.depot.add(it);
                    }
                    for (int k = c.items.size() - 1; k >= 0; k--) {
                        int df = c.items.get(k).defId;
                        if (df >= 700 && df < 800) {
                            c.items.remove(k);
                        }
                    }
                    while (c.items.size() > 30) {
                        c.items.remove(c.items.size() - 1);
                    }
                    refreshStats(c);
                    sChars.add(c);
                    if (c.id >= sNextCharId) {
                        sNextCharId = c.id + 1;
                    }
                }
                if (!sChars.isEmpty()) {
                    sCurCharId = sChars.get(0).id;
                }
            }
            r.close();
            log("loaded " + sChars.size() + " chars");
        } catch (Throwable e) {
            log("load error " + e);
        }
    }

    public static synchronized void start() {
        if (sStarted) {
            return;
        }
        sStarted = true;
        loadAll();
        synchronized (sChars) {
            if (sChars.isEmpty()) {
                CharInfo c = new CharInfo();
                c.slot = 0;
                c.id = sNextCharId++;
                c.name = "\u6d4b\u8bd5\u8005";
                c.sex = 0;
                c.job = 0;
                c.nation = 0;
                sChars.add(c);
                sCurCharId = c.id;
                addItem(c, "\u91d1\u521b\u836f", 1, 5);
                addWeapon(c);
            }
        }
        setupMonsters();
        Thread game = new Thread(new Runnable() {
            public void run() {
                serveGame();
            }
        }, "LocalGameServer");
        game.setDaemon(true);
        game.start();
        Thread login = new Thread(new Runnable() {
            public void run() {
                serveLogin();
            }
        }, "LocalLoginServer");
        login.setDaemon(true);
        login.start();
        log("started");
    }


    private static int baseHp(int job) { return job == 0 ? 220 : (job == 1 ? 160 : 130); }
    private static int hpGrow(int job) { return job == 0 ? 40 : (job == 1 ? 28 : 22); }
    private static int baseAtk(int job) { return job == 0 ? 22 : (job == 1 ? 16 : 9); }
    private static int atkGrow(int job) { return job == 0 ? 6 : (job == 1 ? 4 : 2); }
    private static int baseMag(int job) { return job == 0 ? 0 : (job == 1 ? 6 : 24); }
    private static int magGrow(int job) { return job == 0 ? 0 : (job == 1 ? 2 : 7); }
    private static int baseDef(int job) { return job == 0 ? 18 : (job == 1 ? 11 : 7); }
    private static int defGrow(int job) { return job == 0 ? 5 : (job == 1 ? 3 : 2); }

    private static void refreshStats(CharInfo c) {
        if (c == null) {
            return;
        }
        int lv = Math.max(1, c.level) - 1;
        int maxHp = baseHp(c.job) + hpGrow(c.job) * lv;
        int atk = baseAtk(c.job) + atkGrow(c.job) * lv;
        int def = baseDef(c.job) + defGrow(c.job) * lv;
        int mag = baseMag(c.job) + magGrow(c.job) * lv;
        int mdef = (c.job == 0 ? 2 : (c.job == 1 ? 4 : 6)) + lv * 2;
        int crit = c.job == 0 ? 200 : (c.job == 1 ? 700 : 400);
        int dodge = c.job == 0 ? 100 : (c.job == 1 ? 350 : 200);
        int hit = c.job == 0 ? 200 : (c.job == 1 ? 500 : 300);

        int[] sum = new int[15];
        for (Item it : c.equipped) {
            for (int i = 0; i < 15; i++) {
                sum[i] += it.stats[i];
            }
        }
        maxHp += sum[8];
        int maxMp = 100 + sum[9];
        atk += sum[0];
        mag += sum[1];
        def += sum[2];
        mdef += sum[3];
        crit += sum[4];
        hit += sum[5];
        dodge += sum[6];
        int block = 100 + sum[7];
        int tough = 100 + sum[13];
        int pen = 100 + sum[14];

        c.maxHp = maxHp;
        c.maxMp = maxMp;
        c.atk = atk;
        c.mag = mag;
        c.def = def;
        c.mdef = mdef;
        c.crit = crit;
        c.dodge = dodge;
        c.hit = hit;
        c.block = block;
        c.tough = tough;
        c.pen = pen;
        if (c.hp > c.maxHp) {
            c.hp = c.maxHp;
        }
        if (c.mp > c.maxMp) {
            c.mp = c.maxMp;
        }
    }

    private static void log(String msg) {
        System.out.println("[LocalServer] " + msg);
    }

    private static void setupMonsters() {
        if (sMonstersReady) {
            return;
        }
        sMonstersReady = true;
        int[][] spots = new int[][] {
            {7, 9}, {9, 9}, {11, 9}, {13, 9}, {15, 9}, {12, 11},
            {5, 12}, {7, 13}, {10, 12}, {14, 12}, {16, 11}, {18, 10},
            {4, 8}, {18, 14}, {3, 14}, {19, 8}
        };
        int[] sprites = new int[] {85, 86, 87, 88, 89, 90};
        String[] names = new String[] {
            "\u5c0f\u9e21", "\u91ce\u5154", "\u5c71\u8d3c", "\u72fc", "\u9e7f", "\u8759\u8760"
        };
        synchronized (sMonsters) {
            for (int i = 0; i < spots.length; i++) {
                Monster m = new Monster();
                m.id = 5001 + i;
                m.sprId = sprites[i % sprites.length];
                m.name = names[i % names.length];
                int base = MAP_BASE_LEVEL[sMapIndex];
                m.level = base + (i % 5);
                if (m.level > 80) {
                    m.level = 80;
                }
                m.maxHp = 60 + m.level * 40;
                m.hp = m.maxHp;
                m.x = spots[i][0] * 24 + 12;
                m.y = spots[i][1] * 24 + 12;
                sMonsters.add(m);
            }
        }
    }

    private static CharInfo currentChar() {
        synchronized (sChars) {
            for (CharInfo c : sChars) {
                if (c.id == sCurCharId) {
                    return c;
                }
            }
            if (!sChars.isEmpty()) {
                sCurCharId = sChars.get(0).id;
                return sChars.get(0);
            }
            return null;
        }
    }

    private static byte[] decrypt(byte[] b) {
        byte[] r = new byte[b.length];
        for (int i = 0; i < b.length; i++) {
            r[i] = (byte) ((b[i] - KEY[i & 255]) & 0xFF);
        }
        return r;
    }

    private static byte[] encrypt(byte[] b) {
        byte[] r = new byte[b.length];
        for (int i = 0; i < b.length; i++) {
            r[i] = (byte) ((b[i] + KEY[i & 255]) & 0xFF);
        }
        return r;
    }

    private static final class Packet {
        int cmd;
        byte[] body;
    }

    private static Packet readPacket(DataInputStream in) throws IOException {
        byte[] h = new byte[4];
        in.readFully(h);
        byte[] hd = decrypt(h);
        int len = (hd[0] & 0xFF) | ((hd[1] & 0xFF) << 8);
        int cmd = (hd[2] & 0xFF) | ((hd[3] & 0xFF) << 8);
        if (len < 4) {
            throw new IOException("bad length " + len);
        }
        byte[] full = new byte[len];
        System.arraycopy(h, 0, full, 0, 4);
        if (len > 4) {
            in.readFully(full, 4, len - 4);
        }
        full = decrypt(full);
        Packet p = new Packet();
        p.cmd = cmd;
        p.body = new byte[len - 4];
        System.arraycopy(full, 4, p.body, 0, len - 4);
        return p;
    }

    private static void sendPacket(OutputStream out, int cmd, byte[] body) throws IOException {
        int len = body.length + 4;
        byte[] pkt = new byte[len];
        pkt[0] = (byte) (len & 0xFF);
        pkt[1] = (byte) ((len >> 8) & 0xFF);
        pkt[2] = (byte) (cmd & 0xFF);
        pkt[3] = (byte) ((cmd >> 8) & 0xFF);
        System.arraycopy(body, 0, pkt, 4, body.length);
        log("-> cmd=" + cmd + " len=" + len + " body=" + hex(body));
        synchronized (out) {
            out.write(encrypt(pkt));
            out.flush();
        }
    }

    private static String hex(byte[] d) {
        char[] cs = new char[d.length * 2];
        final char[] H = "0123456789abcdef".toCharArray();
        for (int i = 0; i < d.length; i++) {
            cs[i * 2] = H[(d[i] >> 4) & 0xF];
            cs[i * 2 + 1] = H[d[i] & 0xF];
        }
        return new String(cs);
    }

    private static void putInt(ByteArrayOutputStream b, int v) {
        b.write(v & 0xFF);
        b.write((v >> 8) & 0xFF);
        b.write((v >> 16) & 0xFF);
        b.write((v >> 24) & 0xFF);
    }

    private static void putShort(ByteArrayOutputStream b, int v) {
        b.write(v & 0xFF);
        b.write((v >> 8) & 0xFF);
    }

    private static void putString8(ByteArrayOutputStream b, String s) {
        byte[] d = utf8(s);
        b.write(d.length & 0xFF);
        b.write(d, 0, d.length);
    }

    private static void putString32(ByteArrayOutputStream b, String s) {
        byte[] d = utf8(s);
        putInt(b, d.length);
        b.write(d, 0, d.length);
    }

    private static byte[] utf8(String s) {
        try {
            return s.getBytes("UTF-8");
        } catch (Exception e) {
            return s.getBytes();
        }
    }

    private static void serveLogin() {
        ServerSocket ss = null;
        try {
            ss = new ServerSocket(28000);
            while (true) {
                final Socket s = ss.accept();
                Thread t = new Thread(new Runnable() {
                    public void run() {
                        handleLogin(s);
                    }
                }, "LLoginConn");
                t.setDaemon(true);
                t.start();
            }
        } catch (Throwable e) {
            log("login server error " + e);
        } finally {
            close(ss);
        }
    }

    private static void serveGame() {
        ServerSocket ss = null;
        try {
            ss = new ServerSocket(28001);
            while (true) {
                final Socket s = ss.accept();
                Thread t = new Thread(new Runnable() {
                    public void run() {
                        handleGame(s);
                    }
                }, "LGameConn");
                t.setDaemon(true);
                t.start();
            }
        } catch (Throwable e) {
            log("game server error " + e);
        } finally {
            close(ss);
        }
    }

    private static void close(ServerSocket s) {
        try {
            if (s != null) {
                s.close();
            }
        } catch (Exception e) {
        }
    }

    private static void handleLogin(Socket s) {
        DataInputStream in = null;
        OutputStream out = null;
        try {
            in = new DataInputStream(s.getInputStream());
            out = s.getOutputStream();
            while (true) {
                Packet p = readPacket(in);
                if (p.cmd == 265) {
                    sendPacket(out, 5, new byte[] {0});
                } else if (p.cmd == 0xFFFF) {
                    byte[] r = new byte[4];
                    System.arraycopy(p.body, 0, r, 0, Math.min(4, p.body.length));
                    sendPacket(out, 0xFEFF, r);
                } else if (p.cmd == 257) {
                    sendPacket(out, 258, buildServerList());
                }
            }
        } catch (Throwable e) {
            log("login conn end " + e);
        } finally {
            try {
                s.close();
            } catch (Exception e) {
            }
        }
    }

    private static byte[] buildServerList() {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        b.write(1);
        putInt(b, ACC_ID);
        b.write(1);
        putString32(b, "LOCALKEY001");
        b.write(1);
        putInt(b, 1);
        putString8(b, "\u672c\u5730\u4e00\u533a");
        putString8(b, "127.0.0.1");
        putShort(b, 28001);
        putString8(b, "127.0.0.1");
        putShort(b, 28001);
        b.write(2);
        return b.toByteArray();
    }

    private static void putCharBody(ByteArrayOutputStream b, CharInfo c, boolean withZone) {
        b.write(c.slot);
        byte[] name = utf8(c.name);
        byte[] padded = new byte[33];
        System.arraycopy(name, 0, padded, 0, Math.min(name.length, 33));
        b.write(padded, 0, 33);
        b.write(c.sex);
        b.write(0);
        putInt(b, c.id);
        putInt(b, c.job);
        putInt(b, c.level);
        putInt(b, c.nation);
        putInt(b, 0);
        for (int i = 0; i < 20; i++) {
            putShort(b, 0);
        }
        putInt(b, 0);
        if (withZone) {
            putString8(b, MAP_NAMES[sMapIndex]);
        }
    }

    private static byte[] buildCharList() {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        synchronized (sChars) {
            b.write(sChars.size());
            putInt(b, ACC_ID);
            for (CharInfo c : sChars) {
                putCharBody(b, c, true);
            }
        }
        return b.toByteArray();
    }

    private static byte[] buildCreateResult(int status, CharInfo c) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        b.write(status);
        if (status == 1 && c != null) {
            putCharBody(b, c, false);
        }
        return b.toByteArray();
    }

    /**
     * 521 CMD_SC_SEND_RES. Field order taken from NetComm.CMD_SC_SEND_RES():
     *   int tileSetId; byte sprCount; short[sprCount]; int zoneId; int mapId;
     *   int worldMapId; byte tileSetPal; ubyte mapResId; int zoneFlags;
     *   byte orgId; string8 zoneName; byte doorCount; [doors...]; byte hasDefault...
     */
    private static byte[] buildSendRes() {
        int zone = MAP_ZONE_IDS[sMapIndex];
        int[] sprs = zoneSprites();
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        putInt(b, 0);                       // tileSetId
        b.write(sprs.length & 0xFF);        // sprCount (<=40)
        for (int i = 0; i < sprs.length; i++) {
            putShort(b, sprs[i]);           // sprIds
        }
        putInt(b, zone);                    // zoneId
        putInt(b, sMapIndex);               // mapId
        putInt(b, 0);                       // worldMapId
        b.write(0);                         // tileSetPal
        b.write(MAP_RES_IDS[sMapIndex]);    // mapResId -> asset pack (7 + res)
        putInt(b, 0);                       // zoneFlags
        b.write(0);                         // orgId
        putString8(b, MAP_NAMES[sMapIndex]);
        b.write(0);                         // transDoor count
        b.write(0);                         // no default transDoor
        return b.toByteArray();
    }

    /** Sprite ids that the client preloads for this zone (monsters + NPCs). */
    private static int[] zoneSprites() {
        int[] monsters = {85, 86, 87, 88, 89, 90};
        int[] npcs = NPC_SPRITES;
        int[] all = new int[monsters.length + npcs.length];
        System.arraycopy(monsters, 0, all, 0, monsters.length);
        System.arraycopy(npcs, 0, all, monsters.length, npcs.length);
        return all;
    }

    private static byte[] buildEntryScene() {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        putInt(b, MAP_ZONE_IDS[sMapIndex]);  // zone id, must equal 521/2104/633
        putShort(b, SPAWN_X);
        putShort(b, SPAWN_Y);
        return b.toByteArray();
    }

    private static byte[] buildPlayerFull() {
        CharInfo c = currentChar();
        String name = c == null ? "\u73a9\u5bb6" : c.name;
        int level = c == null ? 1 : c.level;
        int job = c == null ? 0 : c.job;
        int nation = c == null ? 0 : c.nation;
        int sex = c == null ? 0 : c.sex;
        int id = c == null ? 1001 : c.id;

        ByteArrayOutputStream b = new ByteArrayOutputStream();
        putInt(b, id);
        putInt(b, MAP_ZONE_IDS[sMapIndex]);
        putShort(b, SPAWN_X);
        putShort(b, SPAWN_Y);
        int flags = ((level & 0xFF) << 7) | ((sex & 1) << 18) | ((nation & 7) << 19) | ((job & 7) << 22);
        putInt(b, flags);
        b.write(0);
        b.write(100);
        b.write(100);
        putString32(b, name);
        putShort(b, 7000);
        putShort(b, 0);
        putInt(b, 0);
        return b.toByteArray();
    }

    private static byte[] buildHpMp() {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        b.write(0x0F);
        CharInfo hc = currentChar();
        int chp = hc == null ? 200 : hc.hp;
        int cmax = hc == null ? 200 : hc.maxHp;
        putInt(b, chp);
        putInt(b, cmax);
        int cmp = hc == null ? 100 : hc.mp;
        int cmmp = hc == null ? 100 : hc.maxMp;
        putShort(b, cmp);
        putShort(b, cmmp);
        return b.toByteArray();
    }

    private static byte[] buildReborn() {
        CharInfo c = currentChar();
        int id = c == null ? 1001 : c.id;
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        putInt(b, id);
        putShort(b, SPAWN_X);
        putShort(b, SPAWN_Y);
        putInt(b, 1000);
        putInt(b, 1000);
        putShort(b, 500);
        putShort(b, 500);
        return b.toByteArray();
    }

    private static byte[] buildBaseProps() {
        CharInfo c = currentChar();
        int level = c == null ? 1 : c.level;
        int hp = c == null ? 200 : c.hp;
        int gold = c == null ? 0 : c.gold;
        int base = c == null ? 0 : EXP_TABLE[Math.min(c.level, 70)];
        int exp = c == null ? 0 : (c.exp - base);
        int maxExp = c == null ? 120 : Math.max(1, (c.level < 70 ? EXP_TABLE[c.level + 1] : EXP_TABLE[70]) - EXP_TABLE[Math.min(c.level, 70)]);
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        b.write(level & 0xFF);
        putInt(b, exp);
        putInt(b, hp);
        putShort(b, c == null ? 100 : c.mp);
        b.write(0);
        putInt(b, gold);
        putInt(b, 0);
        putInt(b, 0);
        putInt(b, 0);
        putInt(b, 0);
        b.write(0);
        b.write(0);
        b.write(0);
        b.write(1);
        b.write(0);
        putShort(b, 100);
        putShort(b, 100);
        putShort(b, 0);
        putShort(b, 100);
        putShort(b, 100);
        putShort(b, 100);
        putInt(b, maxExp);
        putInt(b, 0);
        putInt(b, ACC_ID);
        putString8(b, "");
        putString8(b, "");
        return b.toByteArray();
    }

    private static byte[] buildPropsAll() {
        CharInfo c = currentChar();
        int maxHp = c == null ? 200 : c.maxHp;
        int maxMp = c == null ? 100 : c.maxMp;
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        putShort(b, c == null ? 10 : c.atk);    // 0 atk
        putShort(b, c == null ? 5 : c.def);     // 1 def
        putShort(b, c == null ? 0 : c.mag);     // 2 magic atk
        putShort(b, c == null ? 0 : c.mdef);    // 3 magic def
        putInt(b, maxHp);                       // 4 max HP (HUD bar uses this)
        putShort(b, maxMp);                     // 5 max MP
        putShort(b, c == null ? 200 : c.hit);   // 6 hit
        putShort(b, 100);                       // 7
        putShort(b, 100);                       // 8
        putShort(b, 100);                       // 9
        return b.toByteArray();
    }

    private static byte[] buildChangeEqu(int charId, int slot, int display) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        putInt(b, charId);
        b.write(slot & 0xFF);
        putShort(b, display);
        return b.toByteArray();
    }

    /** Fixed weapon look: per-job display id when a weapon is equipped, 0 otherwise. */
    private static int weaponDisplay(CharInfo c) {
        if (c == null) {
            return 0;
        }
        for (Item it : c.equipped) {
            if (it.equipPos == 8) {
                // 武器造型随武器品质/等级档变化：类型固定 1，模组 0..9
                int tier = (it.reqLevel - 10) / 5;
                if (tier < 0) tier = 0;
                if (tier > 9) tier = 9;
                int mould = (it.quality * 2 + tier) % 10;
                return 0x10 | mould;
            }
        }
        return 0;
    }

    private static byte[] buildNationList() {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        for (int i = 0; i < 3; i++) {
            putInt(b, i);
        }
        return b.toByteArray();
    }

    private static byte[] buildGold(int total) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        putInt(b, total);
        b.write(0);
        return b.toByteArray();
    }

    private static void writeItem(ByteArrayOutputStream b, Item it) {
        int wirePos = it.pos;
        if (it.type == 1 && it.equipPos >= 0 && it.equipPos <= 9) {
            wirePos = SLOT_KEY[it.equipPos];
        }
        b.write(wirePos & 0xFF);
        putShort(b, it.defId);
        int flags = (it.type & 7) | ((it.quality & 7) << 18);
        putInt(b, flags);
        putInt(b, it.subType); // sub type
        putString32(b, it.name);
        putShort(b, it.viewId);
        b.write(it.reqLevel & 0xFF); // use min level
        b.write(0); // palette
        b.write(0); // bind type
        int price = 10 + it.reqLevel * 6 + it.quality * 40;
        putInt(b, price);       // buy price
        putInt(b, price / 3);   // sell price
        b.write(0);    // sell gold type
        putInt(b, 0);  // cd
        putShort(b, 1); // extra desc (string16) - one space, avoids null in UI
        b.write(' ');
        b.write(0);     // expand count
        if (it.type == 1) {
            int sockets = it.quality >= 1 ? (it.quality > 5 ? 5 : it.quality) : 0;
            int uiPos = (it.equipPos >= 0 && it.equipPos <= 9) ? SLOT_UI[it.equipPos] : 0;
            int packed = (it.jobReq & 3) | ((sockets & 7) << 2) | ((uiPos & 31) << 5);
            putInt(b, packed);
            putShort(b, 0);     // suit id
            putShort(b, 1000);  // total due
            putShort(b, 1000);  // cur due
            for (int g = 0; g < sockets; g++) {
                putShort(b, 0); // empty gem socket
            }
            for (int g = 0; g < 4; g++) {
                if (g != 0) {
                    b.write(0);
                    continue;
                }
                int n = 0;
                for (int i = 0; i < 15; i++) {
                    if (it.stats[i] > 0) {
                        n++;
                    }
                }
                b.write(n & 0xFF);
                for (int i = 0; i < 15; i++) {
                    if (it.stats[i] > 0) {
                        b.write(i & 0xFF);
                        putShort(b, it.stats[i] & 0xFFFF);
                    }
                }
            }
            b.write(0);     // wu xing
            putInt(b, price); // origin price
            putShort(b, 0); // identify factor
            b.write(0);     // identify gold type
            putInt(b, it.score); // equip score
        } else {
            b.write(99);
            b.write(it.count & 0xFF);
        }
    }

    private static byte[] buildGetItem(int packType, Item it) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        b.write(packType);
        writeItem(b, it);
        return b.toByteArray();
    }

    private static void sendEquipItems(java.io.OutputStream out, CharInfo c) {
        try {
            for (Item it : c.equipped) {
                sendPacket(out, 955, buildGetItem(1, it));
            }
        } catch (Exception e) {
            log("sendEquipItems error " + e);
        }
    }

    private static void autoEquipWeapons(CharInfo c) {
        boolean changed = false;
        for (int k = c.items.size() - 1; k >= 0; k--) {
            Item it = c.items.get(k);
            if (it.type == 1 && it.equipPos == 8) {
                c.items.remove(k);
                it.pos = SLOT_KEY[8];
                boolean already = false;
                for (int j = 0; j < c.equipped.size(); j++) {
                    if (c.equipped.get(j).equipPos == it.equipPos) {
                        c.equipped.set(j, it);
                        already = true;
                        break;
                    }
                }
                if (!already) {
                    c.equipped.add(it);
                }
                changed = true;
            }
        }
        if (changed) {
            log("auto equip weapons -> " + c.equipped.size());
        }
    }

    private static byte[] buildInitPack(int packType, int gridNum, CharInfo c, boolean withItems) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        b.write(packType);
        b.write(gridNum);
        if (!withItems || c == null) {
            b.write(0);
            return b.toByteArray();
        }
        List<Item> list = packType == 1 ? c.equipped : c.items;
        for (Item ri : list) {
            try {
                refreshItemName(ri);
            } catch (Throwable t) {
                log("refreshItemName error def=" + ri.defId + " " + t);
            }
        }
        while (list.size() > gridNum) {
            list.remove(list.size() - 1); // never send more slots than the grid has
        }
        log("init pack type=" + packType + " grid=" + gridNum + " items=" + list.size());
        if (packType == 1) {
            for (Item gi : list) {
                log("  equip item def=" + gi.defId + " name=" + gi.name + " pos=" + gi.equipPos + " score=" + gi.score);
            }
        }
        b.write(list.size());
        for (Item it : list) {
            writeItem(b, it);
        }
        return b.toByteArray();
    }

    private static byte[] buildBagItems(CharInfo c) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        b.write(1);
        List<Item> list = c.items;
        b.write(list.size());
        for (Item it : list) {
            writeItem(b, it);
        }
        return b.toByteArray();
    }

    private static void markEquip(CharInfo c, int defId, int equipPos) {
        for (Item it : c.items) {
            if (it.defId == defId && it.type == 1) {
                it.equipPos = equipPos;
                log("mark equip " + defId + " -> " + equipPos);
            }
        }
    }

    private static boolean autoHeal(OutputStream out, CharInfo c) {
        if (c.hp > c.maxHp / 3) {
            return false;
        }
        for (int k = 0; k < c.items.size(); k++) {
            Item it = c.items.get(k);
            if (it.defId == 1 && it.count > 0) {
                it.count--;
                if (it.count <= 0) {
                    c.items.remove(k);
                }
                c.hp = c.maxHp;
                try {
                    sendPacket(out, 2111, buildHpMp());
                    sendPacket(out, 950, buildInitPack(0, 30, c, true));
                    sendPacket(out, 621, buildGold(c.gold));
                sKills++;
                } catch (Exception e) {
                }
                return true;
            }
        }
        return false;
    }


    private static final String[] SLOT_NAMES = {"\u8863", "\u62a4\u624b", "\u62a4\u817f", "\u5934\u76d4", "\u9879\u94fe", "\u6212\u6307", "\u6212\u6307", "\u9774\u5b50", "\u6b66\u5668", "\u6cd5\u5668"};
    private static final int[] SLOT_VIEW = {4, 12, 19, 16, 15, 10, 10, 14, 3, 9};
    private static final int[] SLOT_EQUIP = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    /** slot -> 950 equip pack key (6 = second ring, 7 necklace, 8 weapon, 9 relic) */
    private static final int[] SLOT_KEY = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    /** slot -> client UI equip position (0..8, both rings share 5) */
    private static final int[] SLOT_UI = {0, 1, 2, 3, 4, 5, 5, 6, 7, 8};
    private static final String[] QUALITY_NAMES = {"", "\u84dd", "\u7d2b", "\u6a59", "\u9752", "\u795e\u5668"};
    private static final double[] QUALITY_MUL = {1.0, 1.25, 1.55, 1.9, 2.3, 3.2};
    private static final int[] JOB_WEAPON_DISPLAY = {0x0011, 0x0012, 0x0013};
    /**
     * 装备图标（锁死）：viewId = 帧号，来自 ui信息/装备图标 标注。
     * 槽位顺序：胸甲0 护手1 护腿2 头盔3 项链4 戒指1 5 戒指2 6 靴7 武器8 法器9
     */
    private static final int[][] SLOT_VIEW_JOB = {
        {11, 12, 8, 10, 29, 37, 37, 9, 3, 39},   // 先锋：斧 a3
        {16, 17, 13, 15, 29, 37, 37, 14, 4, 39}, // 百步：弓 a4
        {21, 22, 18, 20, 29, 37, 37, 19, 5, 39}, // 天灵：法杖 a5
    };

    private static Item makeEquip(CharInfo c, int idx, int slot, int quality) {
        if (idx < 0) {
            idx = 0;
        }
        if (idx > 12) {
            idx = 12;
        }
        int job = c == null ? 0 : (c.job & 3);
        if (job > 2) {
            job = 0;
        }
        int lvl = 10 + idx * 5;
        if (slot < 0 || slot > 9) {
            slot = 0;
        }
        int q = quality < 0 ? 0 : (quality > 5 ? 5 : quality);
        int[] base;
        try {
            base = parseStats(htol.gamesuper.GearData.TABLE[job][idx][slot]);
        } catch (Throwable t) {
            base = new int[15];
        }
        int[] st = new int[15];
        double mul = QUALITY_MUL[q];
        for (int i = 0; i < 15; i++) {
            if (base[i] > 0) {
                int v = (int) Math.round(base[i] * mul);
                st[i] = v < 1 ? 1 : v;
            }
        }
        String baseName;
        if (q == 5) {
            try {
                baseName = htol.gamesuper.GearNames.ARTIFACT[job][slot];
            } catch (Throwable t) {
                baseName = SLOT_NAMES[slot];
            }
        } else {
            try {
                baseName = htol.gamesuper.GearNames.TABLE[job][idx][slot];
            } catch (Throwable t) {
                baseName = SLOT_NAMES[slot];
            }
        }
        String name = (q > 0 && q < 5) ? baseName + "\u00b7" + QUALITY_NAMES[q] : baseName;
        Item it = new Item(2000 + lvl * 10 + slot, name,
                SLOT_VIEW_JOB[job][slot], 1, c == null ? 0 : c.items.size());
        it.type = 1;
        it.equipPos = slot;
        it.quality = q;
        it.jobReq = job;
        it.reqLevel = lvl;
        it.stats = st;
        it.subType = 0;
        int sc = 0;
        for (int i = 0; i < 15; i++) {
            sc += st[i];
        }
        it.score = sc / 10;
        return it;
    }

    /** Re-apply doc name + locked icon for generated gear (also old saves). */
    private static void refreshItemName(Item it) {
        if (it == null || it.type != 1 || it.defId < 2000) {
            return;
        }
        int slot;
        if (it.equipPos >= 0 && it.equipPos <= 9) {
            slot = it.equipPos;
        } else if (it.defId >= 2000 && it.defId < 3000) {
            slot = it.defId % 10;
        } else {
            return;
        }
        int idx = (it.reqLevel - 10) / 5;
        if (idx < 0) {
            idx = 0;
        }
        if (idx > 12) {
            idx = 12;
        }
        int job = it.jobReq;
        if (job < 0 || job > 2) {
            job = 0;
        }
        String base;
        if (it.quality == 5) {
            base = htol.gamesuper.GearNames.ARTIFACT[job][slot];
        } else {
            base = htol.gamesuper.GearNames.TABLE[job][idx][slot];
        }
        it.name = (it.quality > 0 && it.quality < 5)
                ? base + "\u00b7" + QUALITY_NAMES[it.quality] : base;
        it.viewId = SLOT_VIEW_JOB[job][slot];
        // 老存档/异常物品属性为空时，按文档重新生成
        int sum = 0;
        for (int i = 0; i < 15; i++) {
            sum += it.stats[i];
        }
        if (sum == 0) {
            int[] raw;
            try {
                raw = parseStats(htol.gamesuper.GearData.TABLE[job][idx][slot]);
            } catch (Throwable t) {
                raw = new int[15];
            }
            double mul = QUALITY_MUL[it.quality < 0 ? 0 : (it.quality > 5 ? 5 : it.quality)];
            for (int i = 0; i < 15; i++) {
                it.stats[i] = raw[i] > 0 ? Math.max(1, (int) Math.round(raw[i] * mul)) : 0;
            }
            int sc = 0;
            for (int i = 0; i < 15; i++) {
                sc += it.stats[i];
            }
            it.score = sc / 10;
            log("repair stats for " + it.name + " slot=" + slot);
        }
    }

    private static int[] parseStats(String enc) {
        int[] out = new int[15];
        if (enc == null || enc.length() == 0) {
            return out;
        }
        String[] parts = enc.split(",");
        for (String part : parts) {
            try {
                int c = part.indexOf(':');
                if (c <= 0) {
                    continue;
                }
                int idx = Integer.parseInt(part.substring(0, c));
                int val = Integer.parseInt(part.substring(c + 1));
                if (idx >= 0 && idx < 15) {
                    out[idx] = val;
                }
            } catch (Exception e) {
            }
        }
        return out;
    }

    private static void autoEquipBest(CharInfo c) {
        if (c == null) {
            return;
        }
        for (int slot = 0; slot <= 9; slot++) {
            Item best = null;
            int bestVal = -1;
            for (Item it : c.items) {
                if (it.type == 1 && it.quality != 5 && it.equipPos == slot && it.score > bestVal) {
                    best = it;
                    bestVal = it.score;
                }
            }
            if (best == null) {
                continue;
            }
            Item cur = null;
            for (Item e : c.equipped) {
                if (e.equipPos == slot) {
                    cur = e;
                    break;
                }
            }
            if (cur != null && cur.score + 8 >= best.score) {
                continue;
            }
            c.items.remove(best);
            if (cur != null) {
                c.equipped.remove(cur);
                cur.equipPos = 0xFF;
                cur.pos = c.items.size() + 1;
                c.items.add(cur);
            }
            best.pos = SLOT_KEY[slot];
            c.equipped.add(best);
        }
    }

    private static int rollQuality(int monsterLevel, int mapBase) {
        double r = Math.random();
        int bonus = mapBase / 20;
        if (r < 0.006 + bonus * 0.002) {
            return 5; // 神器
        }
        if (r < 0.03 + bonus * 0.008) {
            return 4; // 青
        }
        if (r < 0.10 + bonus * 0.015) {
            return 3; // 橙
        }
        if (r < 0.28 + bonus * 0.02) {
            return 2; // 紫
        }
        if (r < 0.62) {
            return 1; // 蓝
        }
        return 0;    // 白
    }

    /** TEMP: icon probe - 20 items with candidate viewIds. */
    /** One full artifact (quality 5) set straight into the bag, for inspection. */
    private static void grantArtifactSet(CharInfo c) {
        if (c == null) {
            return;
        }
        // artifacts always live in the bag (never auto-equipped)
        for (int k = c.equipped.size() - 1; k >= 0; k--) {
            Item e = c.equipped.get(k);
            if (e.type == 1 && e.quality == 5) {
                c.equipped.remove(k);
                e.equipPos = (e.defId >= 2000 && e.defId < 3000) ? (e.defId % 10) : 0xFF;
                e.pos = c.items.size();
                c.items.add(e);
            }
        }
        boolean[] have = new boolean[10];
        for (Item it : c.items) {
            if (it.type == 1 && it.quality == 5 && it.equipPos >= 0 && it.equipPos <= 9) {
                have[it.equipPos] = true;
            }
        }
        boolean complete = true;
        for (boolean h : have) {
            if (!h) {
                complete = false;
                break;
            }
        }
        if (complete) {
            return;
        }
        int idx = 12; // 70级档
        for (int slot = 0; slot <= 9; slot++) {
            if (have[slot] || c.items.size() >= 30) {
                continue;
            }
            Item it = makeEquip(c, idx, slot, 5);
            it.pos = c.items.size();
            c.items.add(it);
        }
        log("granted artifact set, bag=" + c.items.size());
    }

    private static void dropEquipment(OutputStream out, CharInfo c, int monsterLevel) {
        if (c == null || Math.random() > 0.35) {
            return;
        }
        int slot = (int) (Math.random() * 10);
        int q = rollQuality(monsterLevel, MAP_BASE_LEVEL[sMapIndex]);
        Item it = makeEquip(c, monsterLevel, slot, q);
        it.pos = c.items.size();
        if (it.pos >= 30) {
            return;
        }
        c.items.add(it);
        autoEquipBest(c);
        refreshStats(c);
        try {
            sendPacket(out, 950, buildInitPack(0, 30, c, true));
            sendPacket(out, 1264, buildChangeEqu(c.id, 8, weaponDisplay(c)));
        } catch (Exception e) {
        }
        saveAll();
    }

    private static void addItem(CharInfo c, String name, int defId, int count) {
        for (Item it : c.items) {
            if (it.defId == defId && it.count < 99 && it.type == 2) {
                it.count = Math.min(99, it.count + count);
                return;
            }
        }
        if (c.items.size() >= 30) {
            return; // bag is full (30 slots), drop the pickup
        }
        c.items.add(new Item(defId, name, 1, count, c.items.size()));
    }

    /** New characters must not enter with an empty equipment pack. */
    private static void ensureStarterGear(CharInfo c) {
        if (c == null) {
            return;
        }
        for (Item e : c.equipped) {
            if (e.equipPos == 8) {
                return;
            }
        }
        Item w = makeEquip(c, 0, 8, 0);
        w.equipPos = 8;
        w.pos = SLOT_KEY[8];
        c.equipped.add(w);
        refreshStats(c);
        log("starter weapon for char " + c.name);
    }

    private static void addWeapon(CharInfo c) {
        Item w = makeEquip(c, 10, 8, 0);
        w.pos = c.items.size();
        c.items.add(w);
        refreshStats(c);
    }

    private static final class SkillDef {
        int id;
        int icon;
        int mp;
        int cdMs;
        int loadMs;
        int range;
        int kind;      // 0 single,1 aoe,2 buff,3 heal,4 stun,5 root,6 dot,7 silence,8 lifesteal
        int power;     // percent of atk/mag
        int buffPct;   // buff value
        int buffMs;    // buff duration
        String name;

        SkillDef(int id, String name, int icon, int mp, int cdMs, int loadMs, int range,
                int kind, int power, int buffPct, int buffMs) {
            this.id = id;
            this.name = name;
            this.icon = icon;
            this.mp = mp;
            this.cdMs = cdMs;
            this.loadMs = loadMs;
            this.range = range;
            this.kind = kind;
            this.power = power;
            this.buffPct = buffPct;
            this.buffMs = buffMs;
        }
    }

    private static final SkillDef[][] SKILLS = {
        { // 先锋
            new SkillDef(1001, "\u82f1\u52c7\u51b2\u950b", 0, 15, 12000, 0, 3, 4, 150, 0, 2000),
            new SkillDef(1002, "\u5927\u529b\u6325\u51fb", 1, 12, 6000, 0, 3, 0, 180, 0, 0),
            new SkillDef(1003, "\u5229\u5203\u98ce\u66b4", 2, 30, 18000, 0, 2, 1, 120, 0, 0),
            new SkillDef(1004, "\u594b\u529b\u4e00\u51fb", 3, 20, 15000, 0, 3, 0, 250, 0, 0),
            new SkillDef(1005, "\u55dc\u8840\u653b\u51fb", 4, 18, 12000, 0, 3, 8, 160, 0, 0),
            new SkillDef(1006, "\u72c2\u66b4\u4e4b\u8840", 5, 20, 45000, 0, 5, 2, 0, 30, 10000),
            new SkillDef(1007, "\u5927\u5730\u5b88\u62a4", 6, 25, 10000, 0, 5, 2, 0, 20, 600000),
            new SkillDef(1008, "\u55dc\u8840\u6740\u622e", 7, 30, 30000, 0, 3, 8, 150, 0, 0),
            new SkillDef(1009, "\u6218\u795e\u4e4b\u63e1", 8, 20, 20000, 0, 3, 5, 120, 0, 2000)
        },
        { // 百步
            new SkillDef(2001, "\u4e09\u8fde\u5c04", 0, 10, 6000, 0, 6, 0, 120, 0, 0),
            new SkillDef(2002, "\u6bd2\u7bad", 1, 15, 15000, 0, 6, 6, 150, 0, 0),
            new SkillDef(2003, "\u7206\u88c2\u7bad", 2, 30, 20000, 800, 2, 1, 110, 0, 0),
            new SkillDef(2004, "\u4f1a\u5fc3\u672f", 3, 20, 60000, 0, 5, 2, 0, 60, 10000),
            new SkillDef(2005, "\u51b0\u7bad", 4, 22, 23000, 0, 6, 4, 207, 0, 2500),
            new SkillDef(2006, "\u5b9a\u795e\u7bad", 5, 15, 18000, 0, 6, 5, 100, 0, 3000),
            new SkillDef(2007, "\u6d77\u7eb3\u767e\u5ddd", 6, 25, 45000, 0, 5, 3, 0, 0, 15000),
            new SkillDef(2008, "\u9690\u9041", 7, 20, 60000, 0, 5, 2, 0, 60, 6000)
        },
        { // 天灵
            new SkillDef(3001, "\u5929\u706b\u672f", 0, 15, 6000, 0, 6, 0, 150, 0, 0),
            new SkillDef(3002, "\u843d\u96f7\u672f", 1, 30, 15000, 0, 6, 1, 120, 0, 0),
            new SkillDef(3003, "\u51b0\u66b4\u672f", 2, 22, 20000, 0, 6, 4, 180, 0, 1500),
            new SkillDef(3004, "\u5bd2\u51b0\u7532", 3, 20, 30000, 0, 5, 2, 0, 25, 600000),
            new SkillDef(3005, "\u56de\u5929\u4e4b\u672f", 4, 30, 25000, 0, 6, 3, 0, 0, 4000),
            new SkillDef(3006, "\u96f7\u7535\u675f\u7f1a", 5, 28, 30000, 0, 6, 7, 120, 0, 6000),
            new SkillDef(3007, "\u51b0\u51bb\u4e4b\u73af", 6, 30, 25000, 0, 2, 1, 110, 0, 2000),
            new SkillDef(3008, "\u9668\u77f3\u672f", 7, 40, 45000, 0, 6, 1, 200, 0, 0)
        }
    };

    private static SkillDef findSkill(CharInfo c, int id) {
        if (c == null) {
            return null;
        }
        SkillDef[] list = SKILLS[c.job >= 0 && c.job < 3 ? c.job : 0];
        for (SkillDef d : list) {
            if (d.id == id) {
                return d;
            }
        }
        return null;
    }

    /** Equip an item straight from the bag (click item -> equip). */
    private static boolean equipFromBag(OutputStream out, int defId, int pos) throws IOException {
        CharInfo c = currentChar();
        if (c == null) {
            return false;
        }
        Item target = null;
        for (int i = 0; i < c.items.size(); i++) {
            Item it = c.items.get(i);
            if (it.defId == defId && (pos < 0 || it.pos == pos)) {
                target = it;
                break;
            }
        }
        if (target == null) {
            for (Item it : c.items) {
                if (it.defId == defId) {
                    target = it;
                    break;
                }
            }
        }
        log("1079 bag pos=" + pos + " def=" + defId + " found=" + (target != null)
                + (target == null ? "" : (" type=" + target.type + " slot=" + target.equipPos)));
        if (target == null) {
            // maybe the item is already equipped: treat as no-op success
            for (Item e : c.equipped) {
                if (e.defId == defId) {
                    log("1079 already equipped " + defId);
                    return true;
                }
            }
        }
        if (target == null || target.type != 1) {
            return false;
        }
        int slot = target.equipPos;
        if (slot < 0 || slot > 9) {
            if (target.defId >= 2000 && target.defId < 3000) {
                slot = target.defId % 10;
            }
        }
        if (slot < 0 || slot > 9) {
            return false;
        }
        Item cur = null;
        for (Item e : c.equipped) {
            if (e.equipPos == slot) {
                cur = e;
                break;
            }
        }
        if (cur != null) {
            c.equipped.remove(cur);
            cur.equipPos = 0xFF;
            cur.pos = c.items.size();
            c.items.add(cur);
        }
        c.items.remove(target);
        target.pos = SLOT_KEY[slot];
        target.equipPos = slot;
        c.equipped.add(target);
        refreshStats(c);
        sendPacket(out, 950, buildInitPack(1, 22, c, true));
        sendPacket(out, 950, buildInitPack(0, 30, c, true));
        sendPacket(out, 1082, buildBagItems(c));
        int wd = weaponDisplay(c);
        if (wd != 0) {
            sendPacket(out, 1264, buildChangeEqu(c.id, 8, wd));
        }
        saveAll();
        log("equip from bag def=" + defId + " slot=" + slot + " name=" + target.name);
        return true;
    }

    private static boolean castSkill(OutputStream out, CharInfo c, int skillId, int target) throws IOException {
        SkillDef d = findSkill(c, skillId);
        if (d == null) {
            return false;
        }
        long now = System.currentTimeMillis();
        Long until = c.cdUntil.get(skillId);
        if (until != null && now < until.longValue()) {
            sendPacket(out, 1227, skillError(skillId, 1, until.longValue() - now));
            return true;
        }
        if (c.mp < d.mp) {
            sendPacket(out, 1227, skillError(skillId, 2, 0));
            return true;
        }
        c.mp -= d.mp;
        c.cdUntil.put(skillId, Long.valueOf(now + d.cdMs));
        if (d.kind == 3) {
            int heal = Math.max(1, (c.job == 2 ? c.mag : c.atk) * (d.id == 2007 ? 2 : 3));
            c.hp = Math.min(c.maxHp, c.hp + heal);
            sendPacket(out, 2111, buildHpMp());
            log("skill heal " + d.name + " +" + heal);
            return true;
        }
        if (d.kind == 2) {
            if (d.id == 1006) {
                c.atkBuffUntil = now + d.buffMs;
                c.atkBuffPct = d.buffPct;
            } else if (d.id == 2004) {
                c.critBuffUntil = now + d.buffMs;
                c.critBuffPct = d.buffPct;
            } else if (d.id == 2008) {
                c.stealthUntil = now + d.buffMs;
            }
            log("skill buff " + d.name);
            return true;
        }
        int hits = 0;
        synchronized (sMonsters) {
            for (Monster m : sMonsters) {
                if (!m.alive) {
                    continue;
                }
                boolean primary = m.id == target;
                if (!primary) {
                    if (d.kind != 1) {
                        continue;
                    }
                    Monster t = null;
                    for (Monster q : sMonsters) {
                        if (q.id == target) {
                            t = q;
                            break;
                        }
                    }
                    int cx = t == null ? c.px : t.x;
                    int cy = t == null ? c.py : t.y;
                    if (Math.abs(m.x - cx) > 80 || Math.abs(m.y - cy) > 80) {
                        continue;
                    }
                }
                int base = (c.job == 2 ? Math.max(1, c.mag) : Math.max(1, c.atk));
                int dmg = base * d.power / 100;
                dmg += (int) (Math.random() * (dmg / 4 + 1));
                int crit = c.crit;
                if (now < c.critBuffUntil) {
                    crit += c.critBuffPct * 100;
                }
                if (Math.random() * 10000 < crit) {
                    dmg = dmg * 3 / 2;
                }
                // 狂暴之血
                if (now < c.atkBuffUntil) {
                    dmg = dmg * (100 + c.atkBuffPct) / 100;
                }
                int mdef = 4 + m.level * 2;
                dmg = Math.max(1, dmg - mdef / 2);
                m.hp -= dmg;
                if (m.hp <= 0) {
                    m.hp = 0;
                    m.alive = false;
                    onMonsterKilled(out, m);
                }
                if (d.kind == 4 || d.kind == 5) {
                    m.stunUntil = now + d.buffMs;
                }
                if (d.kind == 8) {
                    int heal = Math.max(1, dmg * 15 / 100);
                    c.hp = Math.min(c.maxHp, c.hp + heal);
                    sendPacket(out, 2111, buildHpMp());
                }
                // action animation
                ByteArrayOutputStream a = new ByteArrayOutputStream();
                putInt(a, c.id);
                putInt(a, m.id);
                putShort(a, (short) 1);
                sendPacket(out, 2108, a.toByteArray());
                ByteArrayOutputStream db = new ByteArrayOutputStream();
                putInt(db, m.id);
                putInt(db, dmg);
                sendPacket(out, 1225, db.toByteArray());
                if (d.kind == 1 || d.kind == 7) {
                    sendPacket(out, 1220, buildSkills(c));
                }
                hits++;
                if (d.kind != 1) {
                    break;
                }
            }
        }
        log("skill cast " + d.name + " target=" + target + " hits=" + hits + " mp=" + c.mp);
        if (hits == 0 && d.kind == 0) {
            attackMonster(out, target);
        }
        return true;
    }

    private static byte[] skillError(int id, int err, long cdLeft) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        putShort(b, id);
        b.write(err & 0xFF);
        putInt(b, (int) Math.max(0, cdLeft));
        return b.toByteArray();
    }

    private static byte[] buildSkills(CharInfo c) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        int job = c == null ? 0 : (c.job >= 0 && c.job < 3 ? c.job : 0);
        SkillDef[] list = SKILLS[job];
        int iconBase = job == 0 ? 0 : (job == 1 ? 8 : 16);
        b.write(list.length);
        for (SkillDef d : list) {
            putShort(b, d.id);
            b.write(1);            // level
            b.write(10);           // max level
            putString8(b, d.name);
            int icon = iconBase + d.icon;
            if (d.id == 1009) {
                icon = 24; // 战神之握：原版后加的技能，图标在图集末尾
            }
            b.write(icon & 0xFF);
            b.write(0);            // anim id
            putShort(b, d.mp);
            putShort(b, d.cdMs / 100);
            putShort(b, d.loadMs / 100);
            putShort(b, d.range);
            b.write(0);            // target type
            b.write(0);            // target obj type
            b.write(0);            // auto cast
        }
        return b.toByteArray();
    }


    private static byte[] buildMonsterFull(Monster m) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        putInt(b, m.id);
        putInt(b, MAP_ZONE_IDS[sMapIndex]);
        putShort(b, m.x);
        putShort(b, m.y);
        int flags = (m.level & 0xFF) << 11;
        putInt(b, flags);
        putShort(b, m.sprId);
        b.write(0);
        b.write(0);
        int pct = m.maxHp <= 0 ? 0 : (m.hp * 100 / m.maxHp);
        b.write(pct & 0xFF);
        b.write(0);
        putString32(b, m.name);
        return b.toByteArray();
    }

    private static void sendMonsters(OutputStream out) throws IOException {
        if (MAP_IS_CITY[sMapIndex] == 1) {
            return; // main city is a safe zone: no monsters
        }
        synchronized (sMonsters) {
            for (Monster m : sMonsters) {
                if (m.alive) {
                    sendPacket(out, 2104, buildMonsterFull(m));
                }
            }
        }
    }

    private static String readFixedString(byte[] body, int off, int len) {
        int n = 0;
        while (n < len && off + n < body.length && body[off + n] != 0) {
            n++;
        }
        try {
            return new String(body, off, n, "UTF-8");
        } catch (Exception e) {
            return "";
        }
    }

    private static int readInt(byte[] b, int off) {
        return (b[off] & 0xFF) | ((b[off + 1] & 0xFF) << 8) | ((b[off + 2] & 0xFF) << 16) | ((b[off + 3] & 0xFF) << 24);
    }

    private static void handleGame(Socket s) {
        DataInputStream in = null;
        OutputStream out = null;
        try {
            in = new DataInputStream(s.getInputStream());
            out = s.getOutputStream();
            boolean skillsSent = false;
            long lastPackSent = 0;
            long lastSave = 0;
            long lastAtk = 0;
            final OutputStream fout = out;
            final int[] autoTarget = new int[] {-1};
            final boolean[] closed = new boolean[] {false};
            Thread ticker = new Thread(new Runnable() {
                public void run() {
                    while (!closed[0]) {
                        try {
                            Thread.sleep(900);
                        } catch (Exception e) {
                        }
                        int t = autoTarget[0];
                        if (t > 0) {
                            try {
                                if (!isMonsterAlive(t)) {
                                    autoTarget[0] = -1;
                                } else {
                                    attackMonster(fout, t);
                                    monsterStrike(fout, t);
                                }
                            } catch (Exception e) {
                            }
                        }
                    }
                }
            }, "LCombat");
            ticker.setDaemon(true);
            ticker.start();
            while (true) {
                Packet p = readPacket(in);
                log("<- cmd=" + p.cmd + " len=" + (p.body.length + 4) + " body=" + hex(p.body));
                long nowAtk = System.currentTimeMillis();
                // Fixed weapon look, re-applied periodically (state packets can clear it).
                if (sInGame && nowAtk - sWeaponProbeAt > 3000) {
                    sWeaponProbeAt = nowAtk;
                    CharInfo rc = currentChar();
                    if (rc != null) {
                        try {
                            int wd = weaponDisplay(rc);
                            if (wd != 0) {
                                sendPacket(out, 1264, buildChangeEqu(rc.id, 8, wd));
                            }
                        } catch (Exception e) {
                        }
                    }
                }
                if (!sInGame) {
                    autoTarget[0] = -1;
                }
                if (sInGame && MAP_IS_CITY[sMapIndex] == 0 && autoTarget[0] <= 0) {
                    synchronized (sMonsters) {
                        for (Monster mm : sMonsters) {
                            if (mm.alive && mm.hp > 0) {
                                autoTarget[0] = mm.id;
                                break;
                            }
                        }
                    }
                }
                if (sInGame && MAP_IS_CITY[sMapIndex] == 0 && autoTarget[0] > 0 && nowAtk - lastAtk >= 900) {
                    lastAtk = nowAtk;
                    if (isMonsterAlive(autoTarget[0])) {
                        attackMonster(out, autoTarget[0]);
                        monsterStrike(out, autoTarget[0]);
                    } else {
                        autoTarget[0] = -1;
                    }
                }

                if (p.cmd == 602 && p.body.length >= 4) {
                    CharInfo pc3 = currentChar();
                    if (pc3 != null) {
                        pc3.px = (p.body[0] & 0xFF) | ((p.body[1] & 0xFF) << 8);
                        pc3.py = (p.body[2] & 0xFF) | ((p.body[3] & 0xFF) << 8);
                        log("pos " + pc3.px + "," + pc3.py);
                        if (pc3.px >= 520 && sMapIndex + 1 < MAP_NAMES.length) {
                            sMapIndex++;
                            pc3.px = 60;
                            pc3.py = 252;
                            sendPacket(out, 521, buildSendRes());
                            sendPacket(out, 2110, buildPlayerRepos(pc3));
                            synchronized (sMonsters) {
                                for (Monster mm : sMonsters) {
                                    int lv = MAP_BASE_LEVEL[sMapIndex] + (mm.id % 5) + (MAP_IS_CITY[sMapIndex] == 2 ? 2 : 0);
                                    mm.level = lv > 80 ? 80 : lv;
                                    mm.maxHp = 60 + mm.level * 40;
                                    mm.hp = mm.maxHp;
                                    mm.alive = true;
                                    sendPacket(out, 2104, buildMonsterFull(mm));
                                }
                            }
                            log("teleport map=" + sMapIndex);
                        }
                    }
                }
                if (!skillsSent && (p.cmd == 602 || p.cmd == 2115)) {
                    skillsSent = true;
                    sendPacket(out, 2102, buildPlayerFull());
                    sendPacket(out, 2111, buildHpMp());
                    sendPacket(out, 1220, buildSkills(currentChar()));
                    // Server already starts on 定远城 (sMapIndex = 5); do not push an
                    // unsolicited 521 here, the client only accepts it in its own map
                    // request flow (523 / 3006).
                    sSpawnedAtCity = true;
                    log("sent skills and hp");
                }

                if (p.cmd == 513) {
                    sendPacket(out, 514, buildCharList());
                } else if (p.cmd == 400) {
                    sendPacket(out, 401, new byte[] {3});
                    sendPacket(out, 521, buildSendRes());
                } else if (p.cmd == 958) {
                    CharInfo c = currentChar();
                    if (c != null && p.body.length >= 3) {
                        int defId = (p.body[0] & 0xFF) | ((p.body[1] & 0xFF) << 8);
                        int pos = p.body[2] & 0xFF;
                        log("use item defId=" + defId + " pos=" + pos);
                        for (int k = 0; k < c.items.size(); k++) {
                            Item it = c.items.get(k);
                            if (it.pos == pos && it.defId == defId) {
                                it.count--;
                                if (it.count <= 0) {
                                    c.items.remove(k);
                                }
                                c.hp = c.maxHp;
                                break;
                            }
                        }
                        sendPacket(out, 2111, buildHpMp());
                        sendPacket(out, 950, buildInitPack(0, 30, c, true));
                        saveAll();
                    }
                } else if (p.cmd == 1088) {
                    CharInfo c = currentChar();
                    if (c != null && p.body.length >= 2) {
                        int op = p.body[0] & 0xFF;
                        int pos = p.body[1] & 0xFF;
                        log("item op " + op + " pos " + pos);
                        if (op == 0) {
                            Item used = null;
                            for (Item it : c.items) {
                                if (it.pos == pos && it.defId == 1) {
                                    used = it;
                                    break;
                                }
                            }
                            if (used != null) {
                                used.count--;
                                if (used.count <= 0) {
                                    c.items.remove(used);
                                }
                                c.hp = c.maxHp;
                                sendPacket(out, 2111, buildHpMp());
                                sendPacket(out, 950, buildInitPack(0, 30, c, true));
                                saveAll();
                            }
                        }
                    }
                } else if (p.cmd == 960 || p.cmd == 961) {
                    CharInfo c = currentChar();
                    if (c != null && p.body.length >= 3) {
                        int defId0 = (p.body[0] & 0xFF) | ((p.body[1] & 0xFF) << 8);
                        for (int k = c.items.size() - 1; k >= 0; k--) {
                            Item it = c.items.get(k);
                            if (it.defId == defId0 && it.type == 1) {
                                c.items.remove(k);
                                int slotPos = it.equipPos >= 0 && it.equipPos <= 9 ? it.equipPos : 8;
                                for (int e = c.equipped.size() - 1; e >= 0; e--) {
                                    if (c.equipped.get(e).equipPos == slotPos) {
                                        Item oldIt = c.equipped.remove(e);
                                        oldIt.equipPos = 0xFF;
                                        oldIt.pos = c.items.size() + 1;
                                        c.items.add(oldIt);
                                    }
                                }
                                it.pos = SLOT_KEY[slotPos];
                                it.equipPos = slotPos;
                                c.equipped.add(it);
                                break;
                            }
                        }
                        refreshStats(c);
                        sendPacket(out, 950, buildInitPack(1, 22, c, true));
                        sendPacket(out, 950, buildInitPack(0, 30, c, true));
                        sendPacket(out, 1264, buildChangeEqu(c.id, 8, weaponDisplay(c)));
                        saveAll();
                        sendPacket(out, 950, buildInitPack(0, 30, c, true));
                    }
                } else if (p.cmd == 965) {
                    CharInfo c = currentChar();
                    if (c != null && p.body.length >= 3) {
                        int defId1 = (p.body[0] & 0xFF) | ((p.body[1] & 0xFF) << 8);
                        for (int k = c.equipped.size() - 1; k >= 0; k--) {
                            Item it = c.equipped.get(k);
                            if (it.defId == defId1) {
                                c.equipped.remove(k);
                                it.pos = c.items.size() + 1;
                                it.equipPos = 0xFF;
                                c.items.add(it);
                                break;
                            }
                        }
                        sendPacket(out, 950, buildInitPack(1, 22, c, true));
                        sendPacket(out, 950, buildInitPack(0, 30, c, true));
                        sendPacket(out, 1264, buildChangeEqu(c.id, 8, weaponDisplay(c)));
                        saveAll();
                    }
                } else if (p.cmd == 519) {
                    sendPacket(out, 519, buildNationList());
                } else if (p.cmd == 523) {
                    if (p.body.length >= 4) {
                        sCurCharId = readInt(p.body, 0);
                    }
                    // Every character selection starts in 定远城 (main city).
                    sMapIndex = 5;
                    sKills = 0;
                    sInGame = false;
                    sShopOpen = false;
                    sendPacket(out, 521, buildSendRes());
                } else if (p.cmd == 518) {
                    // body: byte flag, byte job, byte sex, byte nation, name[33]
                    int sex = p.body.length > 2 ? (p.body[2] & 0xFF) : 0;
                    int job = p.body.length > 1 ? (p.body[1] & 0xFF) : 0;
                    int nation = p.body.length > 3 ? (p.body[3] & 0xFF) : 0;
                    String name = readFixedString(p.body, 4, 33);
                    if (name.length() == 0) {
                        name = "\u73a9\u5bb6";
                    }
                    CharInfo created = null;
                    int wantSlot = p.body.length >= 38 ? (p.body[37] & 0xFF) : -1;
                    synchronized (sChars) {
                        if (sChars.size() < MAX_CHARS) {
                            int free = -1;
                            if (wantSlot >= 0 && wantSlot < MAX_CHARS) {
                                boolean used = false;
                                for (CharInfo ex : sChars) {
                                    if (ex.slot == wantSlot) {
                                        used = true;
                                        break;
                                    }
                                }
                                if (!used) {
                                    free = wantSlot;
                                }
                            }
                            if (free < 0) {
                                for (int k = 0; k < MAX_CHARS; k++) {
                                    boolean used = false;
                                    for (CharInfo ex : sChars) {
                                        if (ex.slot == k) {
                                            used = true;
                                            break;
                                        }
                                    }
                                    if (!used) {
                                        free = k;
                                        break;
                                    }
                                }
                            }
                            created = new CharInfo();
                            created.slot = free < 0 ? sChars.size() : free;
                            created.id = sNextCharId++;
                            created.name = name;
                            created.sex = sex;
                            created.job = job;
                            created.nation = nation;
                            sChars.add(created);
                            sCurCharId = created.id;
                            addItem(created, "\u91d1\u521b\u836f", 1, 5);
                            ensureStarterGear(created);
                        }
                    }
                    sendPacket(out, 518, buildCreateResult(created == null ? 9 : 1, created));
                    if (created != null) {
                        saveAll();
                        log("char created slot=" + created.slot + " id=" + created.id + " total=" + sChars.size());
                    }
                } else if (p.cmd == 525) {
                    sInGame = true;
                    CharInfo ec = currentChar();
                    if (ec != null) {
                        ec.hp = ec.maxHp;
                    }
                    sendPacket(out, 2112, buildEntryScene());
                    sendPacket(out, 2102, buildPlayerFull());
                    sendPacket(out, 2111, buildHpMp());
                    CharInfo wc = currentChar();
                    // Equip first, then tell the client what is worn; otherwise the
                    // weapon slot is empty and the model has to be guessed.
                    ensureStarterGear(wc);
                    autoEquipBest(wc);
                    refreshStats(wc);
                    grantArtifactSet(wc);
                    refreshStats(wc);
                    sendPacket(out, 950, buildInitPack(1, 22, wc, true));
                    int wdisp = weaponDisplay(wc);
                    if (wdisp == 0 && wc != null) {
                        wdisp = JOB_WEAPON_DISPLAY[wc.job & 3];
                    }
                    sendPacket(out, 1264, buildChangeEqu(wc == null ? 1001 : wc.id, 8, wdisp));
                    sendPacket(out, 622, buildBaseProps());
                    sendPacket(out, 623, buildPropsAll());
                    sendPacket(out, 1220, buildSkills(currentChar()));
                    sendPacket(out, 950, buildInitPack(0, 30, wc, true));
                    sendMonsters(out);
                    sendPacket(out, 633, buildNpcs());
                    sWeaponRefreshAt = System.currentTimeMillis() + 1500;
                    log("entered zone");
                } else if (p.cmd == 964) {
                    CharInfo c = currentChar();
                    if (c != null) {
                        autoEquipWeapons(c);
                        refreshStats(c);
                        sendPacket(out, 950, buildInitPack(0, 30, c, true));
                    }
                } else if (p.cmd == 953) {
                    CharInfo c = currentChar();
                    if (c != null) {
                        autoEquipWeapons(c);
                        refreshStats(c);
                        sendPacket(out, 950, buildInitPack(0, 30, c, true));
                        sendPacket(out, 1082, buildBagItems(c));
                    }
                } else if (p.cmd == 601) {
                    handleAttack(out, p.body);
                    if (p.body.length >= 4) {
                        autoTarget[0] = readInt(p.body, 0);
                    }
                } else if (p.cmd == 607) {
                    // NPC chat request: int npcCharId
                    if (p.body.length >= 4) {
                        int npc = readInt(p.body, 0);
                        if (npc == 9100) {
                            sendCustomMenu(out, new int[] {2201},
                                    new String[] {"\u5b58\u53d6\u6750\u6599"});
                        } else if (npc == 9101) {
                            sShopOpen = true;
                            openShop(out);
                        } else if (npc == 9102) {
                            repairAll(out);
                        } else if (npc == 9106) {
                            sellAllByQuality(out, 0);
                        } else if (npc == 9103) {
                            depotToggle(out);
                        } else if (npc == 9104) {
                            sendTeleportMenu(out, npc);
                        } else {
                            sendPacket(out, 2200, buildNpcDialog(npc));
                        }
                    }
                } else if (p.cmd == 952) {
                    // enter shop tab: byte flag, int shopId, byte flags, int tabId
                    int tab = p.body.length >= 10 ? readInt(p.body, 6) : 0;
                    sendPacket(out, 951, buildShopTab(tab));
                } else if (p.cmd == 880) {
                    // buy: byte flag, int charId, ushort defId, byte count, byte tab, byte gold
                    if (p.body.length >= 9) {
                        int defId = (p.body[5] & 0xFF) | ((p.body[6] & 0xFF) << 8);
                        int cnt = p.body[7] & 0xFF;
                        if (cnt <= 0) {
                            cnt = 1;
                        }
                        buyByDefId(out, defId, cnt);
                    }
                } else if (p.cmd == 883) {
                    if (p.body.length >= 9) {
                        int defId = (p.body[5] & 0xFF) | ((p.body[6] & 0xFF) << 8);
                        int cnt = p.body[7] & 0xFF;
                        int pos = p.body[8] & 0xFF;
                        sellToShop(out, defId, pos, cnt);
                    }
                } else if (p.cmd == 962) {
                    // repair request: equipment durability is always full; refresh panels
                    CharInfo rc = currentChar();
                    if (rc != null) {
                        sendPacket(out, 950, buildInitPack(1, 22, rc, true));
                        sendPacket(out, 985, new byte[] {0, 0, 0, 0, 0});
                        log("repair ok");
                    }
                } else if (p.cmd == 1079) {
                    // In shop the second int is the selected item's defId.
                    // Stock defId -> buy; any other defId -> sell one from the bag.
                    if (sShopOpen && p.body.length >= 8) {
                        int defId = readInt(p.body, 4);
                        if (defId > 0) {
                            if (!buyByDefId(out, defId, 1)) {
                                sellByDefId(out, defId, 1);
                            }
                        }
                        // always answer with the tab so the client's wait closes
                        sendPacket(out, 951, buildShopTab(0));
                    } else {
                        // Bag: byte increaseType, byte stateType, byte packType, byte pos, int defId.
                        if (p.body.length >= 8) {
                            int pos = p.body[3] & 0xFF;
                            int defId = readInt(p.body, 4);
                            if (equipFromBag(out, defId, pos)) {
                                // equipped directly, no menu needed
                            } else {
                                sendPacket(out, 2200, buildNoticeDialog("\u88c5\u5907",
                                        "\u672a\u627e\u5230\u53ef\u88c5\u5907\u7269\u54c1 def=" + defId + " pos=" + pos));
                                sendPacket(out, 1079, new byte[] {0});
                            }
                        } else {
                            sendPacket(out, 1079, new byte[] {0});
                        }
                    }
                } else if (p.cmd == 3006) {
                    // Social/script menu request: int menuId, int serial, int charId.
                    int menuId = p.body.length >= 4 ? readInt(p.body, 0) : 0;
                    handleMenuAction(out, menuId);
                } else if (p.cmd == 3003) {
                    // Custom social-menu callback: int ?, int callbackId, int menuId, ...
                    int menuId = p.body.length >= 12 ? readInt(p.body, 8) : 0;
                    handleMenuAction(out, menuId);
                } else if (p.cmd == 2115) {
                    // CMD_CS_SELECT_TARGET: int actorId. Selecting an NPC opens its
                    // dialogue; selecting a monster only sets the auto-combat target.
                    if (p.body.length >= 4) {
                        int sel = readInt(p.body, 0);
                        if (npcIndex(sel) >= 0) {
                            autoTarget[0] = -1;
                        } else {
                            autoTarget[0] = sel;
                        }
                    }
                } else if (p.cmd == 1266) {
                    CharInfo rc = currentChar();
                    if (rc != null) {
                        rc.hp = rc.maxHp;
                    }
                    sendPacket(out, 2110, buildReborn());
                } else if (p.cmd == 0xFFFF) {
                    byte[] r = new byte[4];
                    System.arraycopy(p.body, 0, r, 0, Math.min(4, p.body.length));
                    sendPacket(out, 0xFEFF, r);
                    checkRespawn(out);

                    long now = System.currentTimeMillis();


                }
            }
        } catch (Throwable e) {
            log("game conn end " + e);
        } finally {
            try {
                s.close();
            } catch (Exception e) {
            }
        }
    }

    private static void handleAttack(OutputStream out, byte[] body) throws IOException {
        if (body.length < 4) {
            return;
        }
        log("attack len=" + body.length);
        int target = readInt(body, 0);
        int skillId = body.length >= 6 ? ((body[4] & 0xFF) | ((body[5] & 0xFF) << 8)) : 0;
        CharInfo c = currentChar();
        if (skillId != 0 && c != null && findSkill(c, skillId) != null) {
            if (castSkill(out, c, skillId, target)) {
                return;
            }
        }
        attackMonster(out, target);
    }

    private static void attackMonster(OutputStream out, int target) throws IOException {
        CharInfo c = currentChar();
        int myId = c == null ? 1001 : c.id;
        Monster m = null;
        synchronized (sMonsters) {
            for (Monster it : sMonsters) {
                if (it.id == target) {
                    m = it;
                    break;
                }
            }
        }
        if (m == null || !m.alive) {
            return;
        }
        int atk = c == null ? 20 : Math.max(1, c.atk);
        int damage = atk + (int) (Math.random() * (atk / 2 + 1));
        if (c != null && Math.random() * 10000 < c.crit) {
            damage = damage * 3 / 2; // 暴击
        }
        int mdef = 4 + m.level * 2;
        damage = Math.max(1, damage - mdef / 2);
        m.hp -= damage;
        if (m.hp < 0) {
            m.hp = 0;
        }

        int actionId = 1;
        if (c != null && c.job == 1) {
            actionId = 6;
        } else if (c != null && c.job == 2) {
            actionId = 9;
        }
        ByteArrayOutputStream a = new ByteArrayOutputStream();
        putInt(a, myId);
        putInt(a, m.id);
        putShort(a, actionId);
        sendPacket(out, 2108, a.toByteArray());

        ByteArrayOutputStream d = new ByteArrayOutputStream();
        putInt(d, m.id);
        putInt(d, damage);
        sendPacket(out, 1225, d.toByteArray());

        ByteArrayOutputStream hpb = new ByteArrayOutputStream();
        putInt(hpb, m.id);
        hpb.write(3);
        hpb.write(m.maxHp <= 0 ? 0 : (m.hp * 100 / m.maxHp));
        sendPacket(out, 1015, hpb.toByteArray());

        if (m.hp <= 0) {
            onMonsterKilled(out, m);
        }
    }

    private static void onMonsterKilled(OutputStream out, Monster m) throws IOException {
        CharInfo c = currentChar();
        m.alive = false;
        m.respawnAt = System.currentTimeMillis() + 12000;
        ByteArrayOutputStream dead = new ByteArrayOutputStream();
        putInt(dead, m.id);
        sendPacket(out, 2109, dead.toByteArray());
        if (c != null) {
            c.exp += 10 + m.level * 8;
            while (c.level < 70 && c.exp >= EXP_TABLE[c.level + 1]) {
                c.level++;
            }
            refreshStats(c);
            c.hp = c.maxHp;
            if (c.level >= 70) {
                c.level = 70;
                c.exp = EXP_TABLE[70];
            }
            c.maxExp = c.level < 70 ? EXP_TABLE[c.level + 1] : EXP_TABLE[70];
            sendExp(out, c);
            sendPacket(out, 2111, buildHpMp());
            c.gold += 5 + (int) (Math.random() * 10);
            sendPacket(out, 621, buildGold(c.gold));
            sKills++;
            if (Math.random() < 0.8) {
                addItem(c, "\u91d1\u521b\u836f", 1, 5);
            }
            dropEquipment(out, c, m.level);
        }
    }

    private static void monsterStrike(OutputStream out, int monsterId) throws IOException {
        CharInfo c = currentChar();
        if (c == null || c.hp <= 0) {
            return;
        }
        Monster m = null;
        synchronized (sMonsters) {
            for (Monster it : sMonsters) {
                if (it.id == monsterId) {
                    m = it;
                    break;
                }
            }
        }
        if (m == null || !m.alive) {
            return;
        }
        if (System.currentTimeMillis() < m.stunUntil) {
            return;
        }
        int raw = 8 + (int) (Math.random() * 10) + m.level * 3;
        int damage = Math.max(1, raw - c.def / 2);
        if (Math.random() * 10000 < c.block * 30) {
            damage = damage / 3; // 格挡减伤
        }

        ByteArrayOutputStream a = new ByteArrayOutputStream();
        putInt(a, m.id);
        putInt(a, c.id);
        putShort(a, 1);
        sendPacket(out, 2108, a.toByteArray());

        ByteArrayOutputStream d = new ByteArrayOutputStream();
        putInt(d, c.id);
        putInt(d, damage);
        sendPacket(out, 1225, d.toByteArray());

        c.hp -= damage;
        if (c.hp < 0) {
            c.hp = 0;
        }
        ByteArrayOutputStream hp = new ByteArrayOutputStream();
        hp.write(0x01);
        putInt(hp, c.hp);
        sendPacket(out, 2111, hp.toByteArray());

        if (c.hp <= 0) {
            ByteArrayOutputStream dead = new ByteArrayOutputStream();
            putInt(dead, c.id);
            sendPacket(out, 2109, dead.toByteArray());
        } else {
            autoHeal(out, c);
        }
    }

    private static void sendExp(OutputStream out, CharInfo c) throws IOException {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        int base = EXP_TABLE[Math.min(c.level, 70)];
        int next = c.level < 70 ? EXP_TABLE[c.level + 1] : EXP_TABLE[70];
        b.write(1);
        putInt(b, 12);
        putInt(b, Math.max(next - base, 1));
        putInt(b, Math.max(0, c.exp - base));
        b.write(c.level & 0xFF);
        sendPacket(out, 619, b.toByteArray());
    }

    private static boolean isMonsterAlive(int id) {
        synchronized (sMonsters) {
            for (Monster m : sMonsters) {
                if (m.id == id) {
                    return m.alive;
                }
            }
        }
        return false;
    }

    private static void advanceMap(OutputStream out, CharInfo c) {
        int next;
        if (sMapIndex == 5) {
            next = 0;
        } else if (sMapIndex + 1 >= MAP_NAMES.length) {
            next = 5;
        } else {
            next = sMapIndex + 1;
        }
        sMapIndex = next;
        sKills = 0;
        addItem(c, "\u91d1\u521b\u836f", 1, 5);
        c.hp = c.maxHp;

        try {
            sendPacket(out, 521, buildSendRes());
            c.px = 300;
            c.py = 252;
            sendPacket(out, 2112, buildEntryScene());
            sendPacket(out, 2110, buildPlayerRepos(c));
            boolean city = MAP_IS_CITY[sMapIndex] == 1;
            if (city) {
                sendPacket(out, 633, buildNpcs());
            }
            if (city) {
                sendPacket(out, 633, buildNpcs());
            }
            synchronized (sMonsters) {
                for (Monster mm : sMonsters) {
                    int lv = MAP_BASE_LEVEL[sMapIndex] + (mm.id % 5) + (MAP_IS_CITY[sMapIndex] == 2 ? 2 : 0);
                    mm.level = lv > 80 ? 80 : lv;
                    mm.maxHp = 60 + mm.level * 40;
                    mm.hp = mm.maxHp;
                    mm.alive = !city;
                    if (!city) {
                        sendPacket(out, 2104, buildMonsterFull(mm));
                    } else {
                        ByteArrayOutputStream dd = new ByteArrayOutputStream();
                        putInt(dd, mm.id);
                        sendPacket(out, 2109, dd.toByteArray());
                    }
                }
            }
            log("map advance to " + MAP_NAMES[sMapIndex] + " level base " + MAP_BASE_LEVEL[sMapIndex]);
        } catch (Exception e) {
        }
    }


    private static boolean sSpawnedAtCity = false;
    private static boolean sFirstNavDone = false;

    private static void jumpToMap(OutputStream out, CharInfo c, int idx) {
        if (idx < 0 || idx >= MAP_NAMES.length) {
            return;
        }
        sMapIndex = idx;
        try {
            sendPacket(out, 521, buildSendRes());
            c.px = 300;
            c.py = 252;
            sendPacket(out, 2112, buildEntryScene());
            sendPacket(out, 2110, buildPlayerRepos(c));
            boolean city = MAP_IS_CITY[sMapIndex] == 1;
            synchronized (sMonsters) {
                for (Monster mm : sMonsters) {
                    int lv = MAP_BASE_LEVEL[sMapIndex] + (mm.id % 5);
                    mm.level = lv > 80 ? 80 : lv;
                    mm.maxHp = 60 + mm.level * 40;
                    mm.hp = mm.maxHp;
                    mm.alive = !city;
                    if (!city) {
                        sendPacket(out, 2104, buildMonsterFull(mm));
                    }
                }
            }
            log("jump to " + MAP_NAMES[sMapIndex]);
        } catch (Exception e) {
        }
    }


    private static byte[] buildMapList() {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        putShort(b, 0);
        putShort(b, 0);
        putInt(b, 0);
        putInt(b, 0);
        b.write(MAP_NAMES.length);
        for (int i = 0; i < MAP_NAMES.length; i++) {
            putString8(b, MAP_NAMES[i] + " " + MAP_BASE_LEVEL[i] + "\u7ea7");
            putShort(b, i);
        }
        b.write(0);
        b.write(0);
        putInt(b, 0);
        return b.toByteArray();
    }


    /** 607 交谈文本，按 NPC_DEFS 顺序。 */
    private static final String[] NPC_DIALOGS = {
        "冒险者，欢迎来到定远。\n"
        + "你脚下这座城，是浩天大陆最后一处人族庇护之地。\n"
        + "五百年前，妖灵之王九歌破开封印，妖潮席卷天下，万和集市、绫罗城、神月谷相继陷落。\n"
        + "城内锻造、药坊、仓库、练功馆都可为你所用；但所有矿石、草药、灵材，都要你亲自出城取回。\n"
        + "切记，出城即是险地。若你力竭，便立刻回城休整。人族的火种，就寄托在你们这些冒险者身上。",
        "金创药、回灵药，都是出城保命的东西。\n别等血空了才想起来买。",
        "刀剑钝了、甲胄破了就拿来，我替你打磨修补。",
        "多余的材料和装备可以存在这里，出门在外轻装上路。",
        "定远城的传送阵还能用。想去哪片地界，跟我说一声。",
        "在护城阵法的庇护下打坐，比在外面拿命换经验稳当。",
        "白装不值钱，但积少成多。点交互，我全收了。"
    };

    /** 药品商人的货架：defId, name, viewId, price */
    private static final Object[][] SHOP_STOCK = {
        {1, "\u91d1\u521b\u836f", 0, 10},
        {2, "\u56de\u7075\u836f", 2, 15},
        {3, "\u89e3\u6bd2\u8349", 30, 20},
        {4, "\u56de\u57ce\u7b26", 17, 50}
    };

    private static Item makeShopItem(int idx, int pos, int count) {
        Object[] row = SHOP_STOCK[idx];
        int defId = (Integer) row[0];
        String name = (String) row[1];
        int view = (Integer) row[2];
        Item it = new Item(defId, name, view, count, pos);
        it.type = 2;
        it.quality = 0;
        it.reqLevel = 1;
        return it;
    }

    /** 951 initial: shop header + tab list. */
    private static byte[] buildShopOpen() {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        b.write(0);            // 0 = header
        b.write(0x11);         // can sell + normal
        putShort(b, 0);        // gold icon
        b.write(0);            // gold type: 0 = gold
        putShort(b, 9101);     // npc shop id
        putString8(b, "\u836f\u54c1\u5546\u4eba");
        putString8(b, "\u91d1\u5e01");
        b.write(1);            // one tab
        putString8(b, "\u836f\u54c1");
        return b.toByteArray();
    }

    /** 951 tab content: one item per stock row. */
    private static byte[] buildShopTab(int tab) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        b.write(1);            // 1 = tab content
        b.write(tab & 0xFF);   // tab index
        putString8(b, "");     // discount desc
        putInt(b, 0);          // discount
        b.write(1);            // show stackable
        b.write(SHOP_STOCK.length);
        for (int i = 0; i < SHOP_STOCK.length; i++) {
            Item it = makeShopItem(i, i, 99);
            writeItem(b, it);
            putInt(b, (Integer) SHOP_STOCK[i][3]); // buy price
            putShort(b, -1);   // cur stackable: -1 = use item's own
            putString8(b, "");
        }
        return b.toByteArray();
    }

    private static boolean buyByDefId(OutputStream out, int defId, int cnt) throws IOException {
        for (int i = 0; i < SHOP_STOCK.length; i++) {
            if (((Integer) SHOP_STOCK[i][0]).intValue() == defId) {
                buyFromShop(out, i, cnt);
                return true;
            }
        }
        return false;
    }

    private static void sellByDefId(OutputStream out, int defId, int cnt) throws IOException {
        CharInfo c = currentChar();
        if (c == null) {
            return;
        }
        for (int i = 0; i < c.items.size(); i++) {
            Item it = c.items.get(i);
            if (it.defId == defId) {
                sellToShop(out, defId, it.pos, cnt);
                return;
            }
        }
    }

    private static void buyFromShop(OutputStream out, int idx, int cnt) throws IOException {
        CharInfo c = currentChar();
        if (c == null || idx < 0 || idx >= SHOP_STOCK.length) {
            return;
        }
        int price = (Integer) SHOP_STOCK[idx][3] * cnt;
        if (c.gold < price) {
            sendPacket(out, 882, new byte[] {1}); // not enough gold
            return;
        }
        c.gold -= price;
        for (int k = 0; k < cnt; k++) {
            Item it = makeShopItem(idx, c.items.size(), 1);
            addStackable(c, it);
        }
        refreshStats(c);
        sendPacket(out, 621, buildGold(c.gold));
        sendPacket(out, 950, buildInitPack(0, 30, c, true));
        sendPacket(out, 951, buildShopTab(0));
        saveAll();
        log("buy " + SHOP_STOCK[idx][1] + " x" + cnt + " gold=" + c.gold);
    }

    private static void addStackable(CharInfo c, Item it) {
        for (Item ex : c.items) {
            if (ex.type == 2 && ex.defId == it.defId && ex.count < 99) {
                ex.count = Math.min(99, ex.count + it.count);
                return;
            }
        }
        if (c.items.size() < 30) {
            it.pos = c.items.size();
            c.items.add(it);
        }
    }

    private static void sellToShop(OutputStream out, int defId, int pos, int cnt) throws IOException {
        CharInfo c = currentChar();
        if (c == null) {
            return;
        }
        int idx = -1;
        for (int i = 0; i < c.items.size(); i++) {
            Item t = c.items.get(i);
            if (t.defId == defId && t.pos == pos) {
                idx = i;
                break;
            }
        }
        if (idx < 0 && pos >= 0 && pos < c.items.size()) {
            idx = pos;
        }
        if (idx < 0) {
            return;
        }
        Item it = c.items.get(idx);
        int unit = 1 + it.reqLevel * 2 + it.quality * 20;
        if (it.type == 2) {
            unit = 5;
        }
        int total = unit * Math.max(1, it.count);
        c.items.remove(idx);
        for (int i = 0; i < c.items.size(); i++) {
            c.items.get(i).pos = i;
        }
        c.gold += total;
        sendPacket(out, 621, buildGold(c.gold));
        sendPacket(out, 950, buildInitPack(0, 30, c, true));
        sendPacket(out, 951, buildShopTab(0));
        saveAll();
        log("sell " + it.name + " +" + total);
    }

    /** 2200 notice dialog with one close button. */
    private static byte[] buildNoticeDialog(String title, String text) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        putInt(b, 0);
        putString32(b, title);
        b.write(3);
        b.write(0);
        putShort(b, 0);
        putString32(b, text);
        b.write(0);
        putShort(b, 0);
        return b.toByteArray();
    }

    private static void sellAllByQuality(OutputStream out, int... qualities) throws IOException {
        CharInfo c = currentChar();
        if (c == null) {
            return;
        }
        int gold = 0;
        int n = 0;
        for (int i = c.items.size() - 1; i >= 0; i--) {
            Item it = c.items.get(i);
            if (it.type != 1) {
                continue;
            }
            boolean match = false;
            for (int q : qualities) {
                if (it.quality == q) {
                    match = true;
                    break;
                }
            }
            if (!match) {
                continue;
            }
            gold += 5 + it.reqLevel * 2 + it.quality * 20;
            c.items.remove(i);
            n++;
        }
        for (int i = 0; i < c.items.size(); i++) {
            c.items.get(i).pos = i;
        }
        c.gold += gold;
        sendPacket(out, 621, buildGold(c.gold));
        sendPacket(out, 950, buildInitPack(0, 30, c, true));
        sendPacket(out, 2200, buildNoticeDialog("\u5546\u4eba",
                "\u5df2\u51fa\u552e " + n + " \u4ef6\uff0c\u83b7\u5f97 " + gold + " \u91d1\u5e01\u3002"));
        saveAll();
        log("sell all q=" + qualities[0] + " n=" + n + " gold+" + gold);
    }

    private static void depotToggle(OutputStream out) throws IOException {
        CharInfo c = currentChar();
        if (c == null) {
            return;
        }
        int n = 0;
        if (c.depot.isEmpty()) {
            for (int i = c.items.size() - 1; i >= 0; i--) {
                Item it = c.items.get(i);
                if (it.type == 2) {
                    c.items.remove(i);
                    it.pos = c.depot.size();
                    c.depot.add(it);
                    n++;
                }
            }
            for (int i = 0; i < c.items.size(); i++) {
                c.items.get(i).pos = i;
            }
            sendPacket(out, 950, buildInitPack(0, 30, c, true));
            sendPacket(out, 2200, buildNoticeDialog("\u4ed3\u5e93\u7ba1\u4e8b",
                    "\u5df2\u5b58\u5165 " + n + " \u4ef6\u6750\u6599\u3002\u518d\u70b9\u4e00\u6b21\u53d6\u51fa\u3002"));
        } else {
            for (Item it : c.depot) {
                if (c.items.size() >= 30) {
                    break;
                }
                it.pos = c.items.size();
                c.items.add(it);
                n++;
            }
            c.depot.clear();
            sendPacket(out, 950, buildInitPack(0, 30, c, true));
            sendPacket(out, 2200, buildNoticeDialog("\u4ed3\u5e93\u7ba1\u4e8b",
                    "\u5df2\u53d6\u51fa " + n + " \u4ef6\u6750\u6599\u3002"));
        }
        saveAll();
        log("depot toggle n=" + n + " left=" + c.depot.size());
    }

    private static void repairAll(OutputStream out) throws IOException {
        CharInfo c = currentChar();
        if (c == null) {
            return;
        }
        sendPacket(out, 950, buildInitPack(1, 22, c, true));
        sendPacket(out, 2200, buildNoticeDialog("\u94c1\u5320", "\u88c5\u5907\u5df2\u5168\u90e8\u4fee\u7406\u3002"));
        log("repair all");
    }

    private static void openShop(OutputStream out) throws IOException {
        sendPacket(out, 951, buildShopOpen());
    }

    /** 3007 custom menu: int[] ids + labels (ids >= 428 so the client reads strings). */
    private static void sendCustomMenu(OutputStream out, int[] ids, String[] labels) throws IOException {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        b.write(0);
        putInt(b, 0);
        putInt(b, 0x2345);
        b.write(ids.length);
        for (int i = 0; i < ids.length; i++) {
            putInt(b, ids[i]);
            putString8(b, labels[i]);
        }
        b.write(0);
        sendPacket(out, 3007, b.toByteArray());
    }

    /** 3007 social menu used by the teleport NPC. */
    private static void sendTeleportMenu(OutputStream out, int npcId) throws IOException {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        b.write(0);            // menu type
        putInt(b, 0);          // menu id
        putInt(b, 0x1234);     // callback id
        int n = MAP_NAMES.length + 1;
        b.write(n);
        for (int i = 0; i <= 5; i++) {
            putInt(b, 1000 + i);
            putString8(b, MAP_NAMES[i]);
        }
        putInt(b, 2201);
        putString8(b, "\u5b58\u53d6\u6750\u6599");
        for (int i = 6; i < MAP_NAMES.length; i++) {
            putInt(b, 1000 + i);   // >=428 -> custom label follows
            putString8(b, MAP_NAMES[i]);
        }
        b.write(0);            // no args
        sendPacket(out, 3007, b.toByteArray());
    }

    private static void handleMenuAction(OutputStream out, int menuId) throws IOException {
        CharInfo mc = currentChar();
        if (menuId == 2001) {
            sShopOpen = true;
            openShop(out);
        } else if (menuId == 2002) {
            sellAllByQuality(out, 0);
        } else if (menuId == 2003) {
            sellAllByQuality(out, 1, 2);
        } else if (menuId == 2201) {
            depotToggle(out);
        } else if (menuId == 2101) {
            repairAll(out);
        } else if (menuId == 2102) {
            sendPacket(out, 2200, buildNoticeDialog("\u94c1\u5320",
                    "\u5f3a\u5316\u529f\u80fd\u5c1a\u672a\u5f00\u653e\uff0c\u5148\u628a\u88c5\u5907\u4fee\u597d\u5427\u3002"));
        } else if (menuId >= 1000 && menuId < 1000 + MAP_NAMES.length) {
            if (mc != null) {
                jumpToMap(out, mc, menuId - 1000);
            }
        } else if (menuId == 415) {
            if (mc != null) {
                advanceMap(out, mc);
            }
        }
    }

    private static int npcIndex(int npcId) {
        for (int i = 0; i < NPC_DEFS.length; i++) {
            if (NPC_DEFS[i][0] == npcId) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 2200 CMD_SC_SCRIPT_DIALOG, style 3 = 游戏中叠加对白框:
     *   int callbackId; string32 title; byte style(=3); byte optionNum;
     *   short backDialogDefineId; string32 text; byte menuCount;
     *   [string8 label; short ?; int menuArg]...; short argCount
     */
    private static byte[] buildNpcDialog(int npcId) {
        int idx = npcIndex(npcId);
        String title = idx >= 0 ? NPC_NAMES[idx] : "定远城";
        String body = idx >= 0 ? NPC_DIALOGS[idx] : "……";
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        putInt(b, 0);              // callbackId
        putString32(b, title);
        b.write(3);                // style 3: overlay dialogue during gameplay
        b.write(0);                // optionNum
        putShort(b, 0);            // backDialogDefineId
        putString32(b, body);
        b.write(0);                // no menu: client appends "关闭"
        putShort(b, 0);            // no menu args
        return b.toByteArray();
    }

    /**
     * 633 CMD_SC_INIT_NPCS_INZONE. byte count, then per NPC:
     *   int charId; int defineId; byte npcType; byte dir; short sprId;
     *   byte palette; int zoneId; short x; short y; string8 name; byte storeType
     * (types 11/12 append short gatherItemId, short gatherTime, byte invisible)
     */
    private static byte[] buildNpcs() {
        int zone = MAP_ZONE_IDS[sMapIndex];
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        b.write(NPC_DEFS.length);
        for (int i = 0; i < NPC_DEFS.length; i++) {
            int[] d = NPC_DEFS[i];
            putInt(b, d[0]);              // charId
            putInt(b, 600 + i);           // defineId
            b.write(d[4]);                // npcType (0 = mission only, safest)
            b.write(0);                   // dir
            putShort(b, d[1]);            // sprId
            b.write(0);                   // palette
            putInt(b, zone);              // zoneId, must equal 521/2112/2104
            putShort(b, d[2]);            // x
            putShort(b, d[3]);            // y
            putString8(b, NPC_NAMES[i]);
            b.write(d[5]);                // storeType
        }
        return b.toByteArray();
    }

    private static byte[] buildPlayerRepos(CharInfo c) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        putInt(b, c.id);
        putShort(b, c.px);
        putShort(b, c.py);
        b.write(0);
        return b.toByteArray();
    }

    private static byte[] buildMonsterReborn(Monster m) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        putInt(b, m.id);
        putShort(b, m.x);
        putShort(b, m.y);
        b.write(100);
        return b.toByteArray();
    }

    private static void checkRespawn(OutputStream out) throws IOException {
        long now = System.currentTimeMillis();
        synchronized (sMonsters) {
            for (Monster m : sMonsters) {
                if (!m.alive && now >= m.respawnAt) {
                    m.alive = true;
                    m.hp = m.maxHp;
                    sendPacket(out, 2104, buildMonsterFull(m));
                    sendPacket(out, 2110, buildMonsterReborn(m));
                }
            }
        }
    }
}
