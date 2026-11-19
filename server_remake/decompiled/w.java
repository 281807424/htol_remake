/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class w {
    public static int a = -1;
    public static bk[][] b;
    private static int t;
    public static int c;
    public static byte d;
    static String e;
    static String f;
    private static int u;
    private static int v;
    private static int w;
    private static int x;
    private static int y;
    private static int z;
    static int g;
    static int h;
    static String i;
    static String j;
    static String k;
    static String l;
    static int m;
    static int n;
    static int o;
    static int p;
    static int q;
    static int r;
    static int s;
    private static int[] A;

    private static bk a(int n2, int n3) {
        if (b == null) {
            return null;
        }
        if (n2 >= 0 && n2 < b.length) {
            if (b[n2] == null) {
                return null;
            }
            if (n3 >= 0 && n3 < b[n2].length) {
                return b[n2][n3];
            }
        }
        return null;
    }

    static void a() {
        u = 0;
        t = 0;
        c = 0;
        b = null;
    }

    public static void b() {
        if (a == 0) {
            bc.a((byte)18);
            return;
        }
        if (a == 1) {
            w.a(2);
        }
    }

    public static void a(int n2) {
        w = n2;
        bm.X = 3;
    }

    public static void a(Graphics graphics) {
        switch (w) {
            case 0: {
                w.b(graphics);
                ay.a(graphics, 230, 90, 180, 75, false, "", false);
                bm.D.a(6, 222, 166, 0);
                ay.a(graphics, 230, 190, 180, 105, false, "", false);
                bz.a(graphics, 16577506, 16577506, -1);
                bz.a(graphics, bm.d(0, 233), 320, 70, 1);
                bz.a(graphics, bm.d(0, 235), 320, 170, 1);
                return;
            }
            case 1: {
                w.b(graphics);
                bm.D.a(6, 180, 130, 0);
                bz.b((byte)1);
                bz.a(graphics, 16179625, 0, -1);
                bz.a(graphics, bm.d(0, 235), 320, 138, 17);
                int n2 = 180 + bm.m.getHeight();
                int n3 = 170;
                graphics.setColor(16179625);
                graphics.setFont(bm.m);
                graphics.drawString(e, 320, 100, 17);
                for (int i2 = f.length(); i2 > 0; --i2) {
                    if (n2 >= 485 - bm.m.getHeight()) {
                        n2 = 145 + bm.m.getHeight();
                        n3 += bm.m.getHeight();
                    }
                    graphics.drawSubstring(f, f.length() - i2, 1, n2, n3, 20);
                    n2 += bm.m.substringWidth(f, f.length() - i2, 1);
                }
                return;
            }
            case 2: {
                if (!ci.a()) {
                    w.b(graphics);
                }
                ay.c(graphics, 180, 100, 280, 204, 6);
                for (int i3 = 0; i3 < 6; ++i3) {
                    bk bk2;
                    if (u == i3) {
                        ay.b(graphics, 180, 100 + i3 * 34, 280, 34);
                    }
                    if ((bk2 = w.a(t, i3)) == null) continue;
                    graphics.setColor(16179625);
                    bz.b((byte)0);
                    bz.a(graphics, bk2.b, 320, 100 + i3 * 34 + 2, 1);
                    bz.b((byte)1);
                }
                bz.a(graphics, 16179625, 0, -1);
                if (c > 1) {
                    ay.a(graphics, bg.a(bm.A(296), t + 1, c), 100, 330, 440, 22, 333);
                    if (!ci.a()) {
                        bm.D.a(2, 230, 330, 1);
                        bm.D.a(2, 410, 330, 0);
                    }
                }
                return;
            }
            case 3: {
                ay.a(graphics, 0, A, 180, v, true, true, false);
                return;
            }
            case 4: {
                return;
            }
            case 5: {
                if (bm.cb.aa == 0) {
                    bm.a(graphics, bm.d(0, 162), bm.d(0, 241), bm.d(0, 242));
                    return;
                }
                bm.a(graphics, bm.d(0, 162), bm.d(0, 243));
            }
        }
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    public static void c() {
        if (!bm.V()) {
            return;
        }
        block0 : switch (w.w) {
            case 0: {
                if (bm.v(196608)) {
                    bc.a((byte)18);
                    break;
                }
                if (bm.v(262144)) {
                    bm.H();
                }
                return;
            }
            case 1: {
                if (bm.v(196608) || bm.am()) {
                    if (w.e != null && w.e != "" && w.e != "0" && w.f != null && w.f != "" && w.f != "0") {
                        bm.bn.a(bm.cb.C, w.e, w.f);
                    }
                    bm.H();
                    break;
                }
                if (bm.v(262144) || bm.an()) {
                    bm.H();
                }
                return;
            }
            case 2: {
                var0 /* !! */  = null;
                var0 /* !! */  = bm.a(6, 100, 280, 34, 0);
                bm.dB = false;
                for (var1_3 = 0; var1_3 < 6; ++var1_3) {
                    if (!bm.a(var0 /* !! */ [var1_3])) continue;
                    if (w.u == var1_3) {
                        bm.dB = true;
                    } else if (w.t < w.c) {
                        w.u = var1_3;
                    }
                    ** GOTO lbl54
                }
                var1_3 = (int)bm.e(340, 325, 150, 32);
                var2_5 = bm.e(150, 325, 150, 32);
                if (w.c > 1) {
                    if (var2_5) {
                        if (--w.t < 0) {
                            w.t = w.c - 1;
                        }
                        if (w.b != null) {
                            if (w.b[w.t] == null) {
                                bm.ad();
                                bm.bn.b(w.d, w.t, 6);
                            }
                            w.u = 0;
                            bm.X = 3;
                        }
                    } else if (var1_3 != 0) {
                        if (++w.t >= w.c) {
                            w.t = 0;
                        }
                        if (w.b != null) {
                            if (w.b[w.t] == null) {
                                bm.ad();
                                bm.bn.b(w.d, w.t, 6);
                            }
                            w.u = 0;
                            bm.X = 3;
                        }
                    }
                }
lbl54:
                // 10 sources

                if (bm.v(16388)) {
                    if (--w.u < 0) {
                        w.u = 0;
                        break;
                    }
                } else if (bm.v(33024)) {
                    if (++w.u >= 6) {
                        w.u = 5;
                        break;
                    }
                } else if (bm.v(1024)) {
                    w.u = 0;
                    if (++w.t >= w.c) {
                        w.t = 0;
                    }
                    if (w.b != null && w.b[w.t] == null) {
                        bm.ad();
                        bm.bn.b(w.d, w.t, 6);
                        break;
                    }
                } else if (bm.v(2048)) {
                    w.u = 0;
                    if (--w.t < 0) {
                        w.t = w.c - 1;
                    }
                    if (w.b != null && w.b[w.t] == null) {
                        bm.ad();
                        bm.bn.b(w.d, w.t, 6);
                        break;
                    }
                } else if (bm.v(196608)) {
                    v0 = w.a(w.t, w.u);
                    var0 /* !! */  = (int[][])v0;
                    if (v0 != null) {
                        w.a(3);
                        break;
                    }
                } else if (bm.v(262144)) {
                    bm.H();
                    w.a();
                }
                return;
            }
            case 3: {
                var0_1 = w.A.length;
                var2_6 = 0;
                var1_4 = var0_1;
                var1_4 = bm.f(var0_1, 0);
                if (var1_4 != -1) {
                    if (w.v == var1_4) {
                        bm.dB = true;
                    } else {
                        w.v = var1_4;
                        bm.X = 3;
                    }
                }
                var1_4 = var0_1;
                var2_6 = var1_4 - 1;
                if (bm.v(16388)) {
                    w.v = w.v - 1 < 0 ? var2_6 : w.v - 1;
                } else if (bm.v(33024)) {
                    v1 = w.v = w.v + 1 > var2_6 ? 0 : w.v + 1;
                }
                if (bm.v(262144)) ** GOTO lbl-1000
                if (!bm.v(196608) || (var0_2 = w.a(w.t, w.u)) == null) ** GOTO lbl130
                switch (w.v) {
                    case 0: {
                        w.v = 0;
                        bm.ad();
                        w.a(2);
                        bm.bn.B(var0_2.a);
                        break block0;
                    }
                    case 1: {
                        w.v = 0;
                        w.a(5);
                        bm.bn.w(var0_2.a);
                        bm.c(bm.A(655));
                        bm.H();
                        break block0;
                    }
                    case 2: lbl-1000:
                    // 2 sources

                    {
                        w.v = 0;
                        w.a(2);
                    }
                }
lbl130:
                // 3 sources

                return;
            }
            case 4: {
                if (bm.v(262144) || bm.an()) {
                    w.a(2);
                    break;
                }
                if (bm.v(196608) || bm.am()) {
                    w.a(2);
                }
                return;
            }
            case 5: {
                if (!bm.v(262144)) break;
                bm.H();
            }
        }
    }

    private static void b(Graphics graphics) {
        if (w == 5) {
            ay.a(graphics, -1, 3, bm.d(0, 79));
        } else {
            ay.a(graphics, 2, 3, bm.d(0, 79));
        }
        bm.D.a(50, 0, 0, 0);
        bz.a(graphics, 16179625, 0, -1);
        bz.a(graphics, bm.A(657), 320, 70, 17);
    }

    static {
        v = 0;
        w = 0;
        int[] nArray = new int[]{238, 239, 240};
        x = 176;
        y = 187;
        z = 360 - y >> 1;
        A = new int[]{322, 237, 39};
        int[][] nArrayArray = new int[][]{{0, 0, 240, 28, 0xE0E0E0, 0xE0E0E0}, {0, 30, 240, 290, 5187116, 5187116}};
    }
}

