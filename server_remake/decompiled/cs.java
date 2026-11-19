/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class cs {
    private static int[] p = new int[]{5, 10, 71, 11, 13, 7, 4};
    static int[] a = new int[]{0, 0, 0};
    public static String b;
    public static String c;
    static String d;
    static int e;
    private static String q;
    private static String r;
    private static String s;
    public static boolean f;
    private static int t;
    static boolean g;
    private static int u;
    private static String v;
    private static String w;
    private static String x;
    private static String[] y;
    private static String[] z;
    static boolean h;
    private static int A;
    public static boolean i;
    public static boolean j;
    public static boolean k;
    static String l;
    static int m;
    private static int B;
    static String[] n;
    static byte[] o;
    private static String C;
    private static String D;

    public static void a() {
        String string = null;
        try {
            string = CMidlet.s_midlet.getAppProperty("Login-Server");
            v = CMidlet.s_midlet.getAppProperty("Login-Server-WAP");
        }
        catch (Exception exception) {
            string = null;
            v = null;
        }
        try {
            w = CMidlet.s_midlet.getAppProperty("Login-Server-WAP");
            x = CMidlet.s_midlet.getAppProperty("LOGIN_SERVER_URL_WAP_PROXY2");
        }
        catch (Exception exception) {
            w = null;
            x = null;
        }
        if (string == null || string.compareTo("") == 0) {
            string = br.c;
        }
        if (w == null || w.compareTo("") == 0) {
            w = br.d;
        }
        String[] stringArray = bg.a(string, ",", 2);
        int n2 = 0;
        for (n2 = 1; n2 >= 0 && stringArray[n2] != null; --n2) {
        }
        y = new String[2 - (n2 + 1)];
        System.arraycopy(stringArray, n2 + 1, y, 0, 2 - (n2 + 1));
        stringArray = bg.a(w, ",", 2);
        for (n2 = 1; n2 >= 0 && stringArray[n2] != null; --n2) {
        }
        z = new String[2 - (n2 + 1)];
        System.arraycopy(stringArray, n2 + 1, z, 0, 2 - (n2 + 1));
    }

    static boolean b() {
        af.a(false, 1);
        return b != null && c != null;
    }

    static void c() {
        if (cs.b()) {
            t = 3;
        }
        if (bm.bn != null) {
            bm.bn.J();
            bm.bn = null;
        }
        bm.y(9);
        cs.b(2);
    }

    public static void d() {
        if (bm.bn != null) {
            bm.bn.J();
            bm.bn = null;
        }
    }

    public static void a(int n2) {
        k = n2 != 16;
        cs.d();
        if (bm.p) {
            am.q = true;
        }
        bm.bn = new cc();
        bm.bn.a(bm.p ? z[0].trim() : y[0].trim(), false, bm.p ? z : y);
        bm.E(3);
        A = n2;
        i = true;
    }

    public static void e() {
        switch (A) {
            case 0: {
                return;
            }
            case 1: {
                bm.E(0);
                try {
                    bm.bn.a(bc.a[0], bc.a[1], null);
                    return;
                }
                catch (Exception exception) {
                    return;
                }
            }
            case 2: {
                bm.E(0);
                bm.bn.b(bc.a[0], bc.a[1], bc.a[2]);
                return;
            }
            case 3: {
                bm.E(1);
                bm.bn.a(b, c);
                bm.C();
                return;
            }
            case 4: {
                bm.E(0);
                bm.bn.c(b, c, d);
                return;
            }
            case 5: {
                bm.E(0);
                bm.bn.g();
                return;
            }
            case 12: {
                bm.E(0);
                bm.bn.a(bc.a[0], bc.a[1], bc.a[2], bc.a[3]);
                bm.ae();
                return;
            }
            case 13: {
                bm.E(0);
                bm.bn.d(bc.a[0], bc.a[1], bc.a[2]);
                bm.ae();
                return;
            }
            case 14: {
                bm.E(0);
                bm.bn.a(bc.a[0]);
                bm.ae();
                return;
            }
            case 15: {
                bm.E(0);
                bm.bn.b(bc.a[0]);
                bm.ae();
                return;
            }
            case 16: {
                bm.E(0);
                bm.bn.e();
            }
            case 19: {
                bm.bn.f();
            }
        }
    }

    static void b(int n2) {
        m = n2;
        bm.X = 3;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static void f() {
        switch (m) {
            case 11: {
                cs.a(1);
                m = 2;
                return;
            }
            case 2: {
                bm.dB = false;
                if (!bm.ab()) {
                    int n2;
                    int[][] nArray = new int[p.length][4];
                    nArray[2][0] = 160;
                    nArray[1][0] = 160;
                    nArray[0][0] = 160;
                    nArray[0][1] = 75;
                    nArray[1][1] = nArray[0][1] + 46;
                    nArray[2][1] = nArray[1][1] + 46;
                    nArray[2][2] = 320;
                    nArray[1][2] = 320;
                    nArray[0][2] = 320;
                    nArray[2][3] = 44;
                    nArray[1][3] = 44;
                    nArray[0][3] = 44;
                    int n3 = ay.a(0, p) + 76;
                    if (d.b[0] == d.b[1] && d.b[2] == d.b[3] && d.b[0] + n3 > d.b[1]) {
                        n3 = d.b[2] - d.b[0];
                    }
                    n3 = n3 < 133 ? 133 : n3;
                    boolean bl2 = false;
                    for (n2 = 3; n2 < p.length - 1; ++n2) {
                        if (d.a[n2 - 3] != d.a[n2 - 2]) continue;
                        bl2 = true;
                        break;
                    }
                    for (n2 = 3; n2 < p.length; ++n2) {
                        if (bl2) {
                            if (n2 % 2 == 1) {
                                nArray[n2][0] = 320 - n3;
                            }
                            if (n2 % 2 == 0) {
                                nArray[n2][0] = 320;
                            }
                        } else {
                            nArray[n2][0] = 640 - n3 >> 1;
                        }
                        nArray[n2][1] = d.a[n2 - 3];
                        nArray[n2][2] = n3;
                        nArray[n2][3] = 44;
                    }
                    for (n2 = 0; n2 < p.length; ++n2) {
                        if (!bm.a(nArray[n2])) continue;
                        if (t == n2) {
                            bm.dB = true;
                            break;
                        }
                        t = n2;
                        bm.X = 3;
                        break;
                    }
                }
                if (bm.v(20500)) {
                    if (t != 0) {
                        --t;
                    }
                    bm.X = 3;
                } else if (bm.v(41280)) {
                    if (t != p.length - 1) {
                        ++t;
                    }
                    bm.X = 3;
                } else if (bm.v(196608) || bm.dB || bm.am()) {
                    if (t == 0) {
                        bc.a((byte)0);
                    } else if (t == 1) {
                        bc.a((byte)1);
                    } else if (t == 2) {
                        f = !f;
                    } else if (t == 3) {
                        if (b != null && c != null) {
                            cs.a(3);
                        } else if (b == null) {
                            bm.d(bm.d(0, 72));
                        } else {
                            bm.d(bm.d(0, 73));
                        }
                    } else if (t == 4) {
                        bc.a((byte)2);
                    } else if (t == 5) {
                        bc.a((byte)6);
                    } else if (t == 6) {
                        cs.b(5);
                        B = 0;
                    }
                } else if (bm.v(262144) || bm.an()) {
                    bm.y(5);
                    cs.d();
                }
                if (b == null || cs.a(b)) return;
                b = "";
                bm.a(bm.A(728), -1, 2000);
                return;
            }
            case 1: {
                cs.a(2);
                m = 2;
                return;
            }
            case 12: {
                cs.a(4);
                m = 2;
                return;
            }
            case 5: {
                int n4 = n.length;
                boolean bl3 = false;
                int n5 = n4;
                int n6 = bm.f(n4, 0);
                if (n6 != -1) {
                    if (B == n6) {
                        bm.dB = true;
                    } else {
                        B = n6;
                        bm.X = 3;
                    }
                }
                if (bm.v(16388)) {
                    if (B != 0) {
                        --B;
                        return;
                    }
                    B = n4 - 1;
                    return;
                }
                if (bm.v(33024)) {
                    if (B != n4 - 1) {
                        ++B;
                        return;
                    }
                    B = 0;
                    return;
                }
                if (!bm.v(262144)) {
                    if (!bm.v(65536) && !bm.dB) return;
                    if (B == 1) {
                        bm.o = false;
                        bm.p = true;
                        bm.q = false;
                        am.q = true;
                        cs.o[0] = 1;
                        af.a(true, 6);
                        bm.A(726);
                    } else {
                        bm.o = false;
                        bm.p = false;
                        bm.q = false;
                        cs.o[0] = 0;
                        af.a(true, 6);
                        bm.A(280);
                    }
                }
                cs.b(2);
                return;
            }
            case 6: {
                bm.v(196608);
                return;
            }
            case 7: {
                bm.v(196608);
                return;
            }
            case 8: {
                bm.v(196608);
                return;
            }
            case 9: {
                bm.v(196608);
                return;
            }
            case 10: {
                if (!bm.v(262144) && !bm.v(196640) && !bm.al()) return;
                cs.b(2);
            }
        }
    }

    static void a(Graphics graphics) {
        switch (m) {
            case 2: 
            case 11: {
                cs.c(graphics);
                return;
            }
            case 10: {
                ay.a(graphics, bm.A(708), 150, 50, false, null, true);
                return;
            }
            case 5: {
                cs.c(graphics);
                ay.a(graphics, bm.A(724), n, B, 300, true, bm.A(725), true);
                return;
            }
            case 6: {
                cs.b(graphics);
                bm.a(graphics, bm.B(162), bg.b(bm.A(722), r, s));
                return;
            }
            case 7: {
                cs.b(graphics);
                bm.a(graphics, bm.B(162), bm.A(721));
                return;
            }
            case 8: {
                cs.b(graphics);
                bm.a(graphics, bm.B(162), bm.B(276));
                return;
            }
            case 9: {
                cs.b(graphics);
                bm.a(graphics, bm.B(162), bm.B(277));
            }
        }
    }

    private static void b(Graphics graphics) {
        if (bm.X == 3) {
            ay.a(bm.W, true, 2, 3, bm.d(0, 0));
            bm.D.a(bm.W, 4, 0, 0, 0);
            bm.X = 0;
        }
        if (g) {
            C = ay.a(4, 0);
            g = false;
        }
        graphics.drawImage(bm.V, 0, 0, 0);
        bm.D.a(6, 165, 140, 0);
        ay.a(graphics, 265, 144, 205, 20, true);
        bz.a(graphics, 5385734, -3066322, -1);
        bz.a(graphics, bm.d(0, 266), 180, 145, 0);
        bz.a(graphics, q, 269, 145, 0);
        bm.D.a(6, 165, 200, 0);
        ay.a(graphics, 265, 204, 205, 20, false);
        bz.a(graphics, 5385734, -3066322, -1);
        bz.a(graphics, bm.d(0, 275), 180, 205, 0);
        bz.a(graphics, null, 269, 205, 0);
        graphics.setColor(0xFFFFFF);
        graphics.fillRect(300, 260, 40, 20);
        bz.a(graphics, 5385734, -3066322, -1);
        if (C != null) {
            bz.a(graphics, C, 320, 265, 1);
        }
    }

    private static void c(Graphics graphics) {
        int n2;
        if (bm.X == 3) {
            ay.a(bm.W, true, 2, 3, bm.d(0, 150));
            bm.X = 0;
        }
        bm.D.a(bm.W, 4, 0, 2, 0);
        graphics.drawImage(bm.V, 0, 0, 0);
        int n3 = Math.max(bz.a(bm.d(0, p[0])) + 10, bz.a(bm.d(0, p[1])) + 10);
        ay.a(graphics, 172, 79, 296, 27, n3, bm.d(0, p[0]), b, t == 0);
        ay.a(graphics, 172, 125, 296, 27, n3, bm.d(0, p[1]), ay.a(c), t == 1);
        graphics.setColor(0);
        graphics.fillRect(210, 170, 22, 20);
        graphics.setColor(16179625);
        graphics.drawRect(210, 170, 22, 20);
        if (t == 2) {
            ay.b(graphics, 210, 170, 22, 20);
        }
        bz.a(graphics, -13559551, -2242154, 4);
        bz.a(graphics, bm.d(0, p[2]), 248, 170, 0);
        if (f) {
            bm.D.a(38, 210, 170, 0);
        }
        n3 = ay.a(0, p) + 76;
        if (d.b[0] == d.b[1] && d.b[2] == d.b[3] && d.b[0] + n3 > d.b[1]) {
            n3 = d.b[2] - d.b[0];
        }
        n3 = n3 < 133 ? 133 : n3;
        boolean bl2 = false;
        for (n2 = 3; n2 < p.length - 1; ++n2) {
            if (d.a[n2 - 3] != d.a[n2 - 2]) continue;
            bl2 = true;
            break;
        }
        for (n2 = 3; n2 < p.length; ++n2) {
            bz.a(graphics, -597591, -13032703, 4);
            if (bl2) {
                if (n2 % 2 == 1) {
                    ay.a(graphics, bm.d(0, p[n2]), 320 - n3, d.c[n2 - 3], n3, n2 == t);
                }
                if (n2 % 2 != 0) continue;
                ay.a(graphics, bm.d(0, p[n2]), 320, d.c[n2 - 3], n3, n2 == t);
                continue;
            }
            ay.a(graphics, bm.d(0, p[n2]), 640 - n3 >> 1, d.c[n2 - 3], n3, n2 == t);
        }
        bz.a(graphics, 0xFFFFFF, 0xFFFFFF, 1);
        String string = bm.p ? "WAP" : "NET";
        bz.a(graphics, bm.A(727) + string, 320, 330, 1);
    }

    public static boolean a(String string) {
        for (int i2 = 0; i2 < string.length(); ++i2) {
            char c2 = string.charAt(i2);
            if (!(c2 < '0' || c2 > '9' && c2 < 'A' || c2 > 'Z' && c2 < 'a') && c2 <= 'z') continue;
            return false;
        }
        return true;
    }

    public static void g() {
        t = 0;
        b = null;
        c = null;
        q = null;
    }

    static {
        e = 0;
        q = "";
        r = "";
        s = "";
        f = false;
        t = 0;
        g = true;
        u = 0;
        h = false;
        i = false;
        j = true;
        k = true;
        l = "";
        m = 2;
        o = new byte[]{0};
        C = null;
        D = null;
        int[][] nArrayArray = new int[][]{{0, 0, 240, 28, 0xE0E0E0, 0xE0E0E0}, {0, 30, 240, 290, 5187116, 5187116}, {24, 58, 189, 201, 14469061, 14469061}};
        int[][] nArrayArray2 = new int[][]{{87, 75, 98, 15, 16577506}, {87, 110, 98, 15, 16577506}, {79, 145, 17, 17, 16577506}, {38, 187, 160, 20, 16577506}, {38, 213, 160, 20, 16577506}, {38, 239, 160, 20, 16577506}};
        int[][] nArrayArray3 = new int[][]{{45, 78}, {45, 112}, {101, 148}, {94, 190}, {94, 216}, {94, 242}};
    }
}

