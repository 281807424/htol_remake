/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import java.util.Vector;
import javax.microedition.lcdui.Graphics;

public final class l {
    public static int a;
    public static boolean b;
    public static boolean c;
    private static int n;
    private static byte o;
    public static byte d;
    private static Vector p;
    public static Vector e;
    public static int f;
    public static final int[] g;
    private static int[] q;
    private static int[] r;
    private static int[] s;
    private static int[] t;
    private static int[] u;
    private static int[] v;
    private static int[] w;
    private static int[] x;
    private static int[] y;
    private static int[] z;
    private static int[] A;
    private static int[] B;
    private static int[] C;
    private static int[] D;
    private static int[] E;
    private static int[] F;
    private static int[] G;
    private static int[] H;
    private static int[] I;
    private static int[] J;
    private static int[] K;
    public static int[] h;
    public static String[] i;
    private static int L;
    public static cr j;
    public static n k;
    public static int[] l;
    public static int m;
    private static String[] M;
    private static int N;
    private static String O;
    private static boolean P;

    public static void a(int n2) {
        n = n2;
    }

    public static void a(byte by2) {
        o = by2;
        f = 0;
        l.a(false);
    }

    public static void a() {
        e = null;
        f = 0;
        b = false;
        c = false;
    }

    public static void a(Graphics graphics) {
        switch (f) {
            case 0: {
                l.c(graphics);
                return;
            }
            case 1: {
                l.c(graphics);
                l.b(graphics);
                return;
            }
            case 2: {
                l.c(graphics);
                String[] stringArray = new String[]{bm.d(0, 2), bm.d(0, 39)};
                ay.a(graphics, O, stringArray, L, 320, true, bm.d(0, 162), true);
                return;
            }
            case 3: {
                l.c(graphics);
                ay.a(graphics, M, 180, L, true, true);
                return;
            }
            case 4: {
                cn.a(graphics, 0, 0, -1, true);
            }
        }
    }

    public static void a(int n2, boolean bl2) {
        if (bl2 && n2 != a) {
            return;
        }
        L = 0;
        f = 1;
        b = true;
    }

    /*
     * Unable to fully structure code
     */
    public static void b() {
        switch (l.f) {
            case 0: {
                if (l.o != 9) ** GOTO lbl10
                if (!bm.v(262144)) ** GOTO lbl7
                l.a(l.d);
                ** GOTO lbl125
lbl7:
                // 1 sources

                if (!bm.v(196608)) break;
                l.e();
                break;
lbl10:
                // 1 sources

                if (l.P) {
                    bm.H();
                    break;
                }
                bm.g(20, 180);
                if (l.o != 4 && l.o != 5 && l.o != 6 && l.o != 7 && l.o != 8 && l.o != 10 && l.o != 11 && l.o != 12) ** GOTO lbl27
                var1 = null;
                var1 = bm.b(5, 385, 100, 306, 40, 0);
                bm.dB = false;
                for (var0_2 = 0; var0_2 < 5; ++var0_2) {
                    if (!bm.a(var1[var0_2])) continue;
                    if (bm.dW == var0_2) {
                        bm.dB = true;
                    } else {
                        bm.dW = var0_2;
                    }
                    bm.X = 3;
                    ** GOTO lbl79
                }
                ** GOTO lbl79
lbl27:
                // 1 sources

                var1 = null;
                var3_3 = 5;
                var1 = new int[4][4];
                var1[0][0] = 227;
                for (var4_4 = 0; var4_4 < 4; ++var4_4) {
                    bm.d(0, l.q[var4_4]).trim();
                    var0_2 = 315 / l.q.length - 20;
                    var2_5 = var4_4 == 0 ? 0 : var4_4 - 1;
                    var1[var4_4][0] = var1[var2_5][0] + var3_3;
                    var1[var4_4][1] = 50;
                    var1[var4_4][2] = var0_2;
                    var1[var4_4][3] = 28;
                    var3_3 = var0_2 + 20;
                }
                for (var4_4 = 0; var4_4 < 4; var4_4 = (int)((byte)(var4_4 + 1))) {
                    if (!bm.a(var1[var4_4])) continue;
                    bm.X = 3;
                    l.o = (byte)var4_4;
                    l.a(true);
                    ** GOTO lbl63
                }
                var4_4 = 0;
                if (l.o == 2) {
                    var4_4 = bm.b(l.g[l.o], 233, 182, 300, 41);
                } else if (l.o == 0) {
                    var4_4 = bm.b(l.g[l.o], 235, 120, 300, 41);
                } else if (l.o >= 0 && l.o < l.g.length) {
                    var4_4 = bm.b(l.g[l.o], 233, 128, 305, 40);
                }
                if (var4_4 != -1) {
                    if (bm.dW == var4_4) {
                        bm.dB = true;
                    } else {
                        bm.dW = var4_4;
                        bm.X = 3;
                    }
                }
lbl63:
                // 5 sources

                if (bm.v(4112)) {
                    if (l.o > 0) {
                        if (l.n != 0) {
                            var0_2 = l.n == 1 ? 387 : 388;
                            bm.d(bm.d(0, var0_2));
                            break;
                        }
                        l.o = (byte)(l.o - 1);
                        l.a(true);
                    }
                } else if (bm.v(8256) && l.o < 3) {
                    if (l.n != 0) {
                        var0_2 = l.n == 1 ? 387 : 388;
                        bm.d(bm.d(0, var0_2));
                        break;
                    }
                    l.o = (byte)(l.o + 1);
                    l.a(true);
                }
lbl79:
                // 6 sources

                var0_2 = bm.dY;
                bm.a(true, true);
                if (var0_2 == bm.dY || l.o != 4 && l.o != 3 && l.o != 1 && l.o != 5 && l.o != 6 && l.o != 7 && l.o != 8 && l.o != 10 && l.o != 11) ** GOTO lbl114
                var1_1 = bm.dY;
                if (l.o != 4) ** GOTO lbl86
                bm.bn.a((byte)0, var1_1, 5);
                ** GOTO lbl112
lbl86:
                // 1 sources

                if (l.o != 5) ** GOTO lbl89
                bm.bn.a((byte)1, var1_1, 5);
                ** GOTO lbl112
lbl89:
                // 1 sources

                if (l.o != 3) ** GOTO lbl92
                bm.bn.a((byte)3, var1_1, l.g[3]);
                ** GOTO lbl112
lbl92:
                // 1 sources

                if (l.o != 1) ** GOTO lbl95
                bm.bn.a((byte)2, var1_1, l.g[1]);
                ** GOTO lbl112
lbl95:
                // 1 sources

                if (l.o != 6) ** GOTO lbl98
                bm.bn.a((byte)4, var1_1, 5);
                ** GOTO lbl112
lbl98:
                // 1 sources

                if (l.o != 7) ** GOTO lbl101
                bm.bn.a((byte)100, var1_1, 5);
                ** GOTO lbl112
lbl101:
                // 1 sources

                if (l.o != 8) ** GOTO lbl104
                bm.bn.a(cl.g, cl.h, cl.i, cl.k, var1_1, (byte)5, cl.l, cl.m, cl.n, cl.j, -1);
                ** GOTO lbl112
lbl104:
                // 1 sources

                if (l.o != 10) ** GOTO lbl107
                bm.bn.d(var1_1, (byte)5);
                ** GOTO lbl112
lbl107:
                // 1 sources

                if (l.o != 11) ** GOTO lbl110
                bm.bn.e(var1_1, (byte)5);
                ** GOTO lbl112
lbl110:
                // 1 sources

                if (l.o != 12) ** GOTO lbl113
                bm.bn.a((byte)5, var1_1, 5);
lbl112:
                // 10 sources

                bm.ad();
lbl113:
                // 2 sources

                bm.dY = var0_2;
lbl114:
                // 2 sources

                if (bm.v(262144)) {
                    l.o = (byte)-1;
                    l.n = 0;
                    bm.v();
                    if (l.c) {
                        l.c = false;
                        break;
                    }
                } else if (bm.v(196608)) {
                    l.e();
                }
lbl125:
                // 5 sources

                return;
            }
            case 1: {
                l.c();
                return;
            }
            case 2: {
                l.c();
                return;
            }
            case 3: {
                l.c();
                return;
            }
            case 4: {
                if (bm.v(393216) || bm.am() || bm.an()) {
                    cn.aq = 0;
                    l.f = 0;
                    return;
                }
                cn.b(false);
            }
        }
    }

    private static void a(boolean bl2) {
        bm.X = 3;
        if (bl2) {
            if (o == 3) {
                e = null;
                bm.ax();
                bm.bn.a((byte)3, 0, g[3]);
                bm.ad();
                return;
            }
            if (o == 1) {
                e = null;
                bm.ax();
                bm.bn.a((byte)2, 0, g[1]);
                bm.ad();
                return;
            }
        }
        P = false;
        if (o == 4 || o == 5 || o == 6 || o == 7 || o == 8 || o == 10 || o == 11 || o == 12) {
            bm.dZ = g[o];
            return;
        }
        if (o != 9) {
            switch (o) {
                case 0: {
                    p = bm.bq;
                    break;
                }
                case 2: {
                    p = bm.br;
                    break;
                }
                case 1: 
                case 3: {
                    p = e;
                }
            }
            bl2 = false;
            for (int i2 = 0; i2 < p.size(); ++i2) {
                Object e2 = p.elementAt(i2);
                if (e2 != null) {
                    bl2 += 1;
                    continue;
                }
                p.removeElementAt(i2);
            }
            if (p.size() != 0 && o == 2) {
                bg.a(p, 0, p.size() - 1, 0);
            }
            if (o != 3 && o != 1) {
                bm.j(bl2 ? 1 : 0, g[o]);
                return;
            }
            bm.dZ = g[o];
        }
    }

    private static int d() {
        return 80 + bm.D.g(6) + 15;
    }

    private static void c(Graphics graphics) {
        int n2;
        int n3;
        int n4;
        int n5 = l.d();
        if (o == 9) {
            String string = bm.A(511);
            ay.a(graphics, true, 2, 3, string);
            bz.a(graphics, -597591, -13032703, 4);
            bz.a(graphics, bm.A(512), 30, 50, 0);
            bz.a(graphics, bm.A(513), 30, 70, 0);
            bz.a(graphics, bm.A(514), 30, 90, 0);
            bz.a(graphics, bm.A(515), 30, 110, 0);
            bz.a(graphics, bm.A(516), 30, 130, 0);
            bz.a(graphics, bm.A(517), 30, 150, 0);
            int n6 = bz.a(bm.A(512));
            n.a(graphics, n6 + 30, 50, cl.v[bm.dW], cl.y[bm.dW], cl.w[bm.dW], false, true, 0);
            bz.a(graphics, n.P[cl.x[bm.dW]], -13032703, 4);
            bz.a(graphics, cl.u[bm.dW], n6 + 55, 50, 0);
            bz.a(graphics, 16179625, -13032703, -1);
            bz.a(graphics, "^x" + cl.p[bm.dW] + "^t", 30 + bz.a(bm.A(513)), 70, 0);
            bz.a(graphics, bg.a(cl.q[bm.dW] * 1000L), 30 + bz.a(bm.A(514)), 90, 0);
            bz.a(graphics, "" + cl.r[bm.dW], 30 + bz.a(bm.A(515)), 110, 0);
            bz.a(graphics, "" + cl.s[bm.dW], 30 + bz.a(bm.A(516)), 130, 0);
            String string2 = "" + cl.t[bm.dW];
            if (cl.t[bm.dW] == 0) {
                string2 = bm.A(163);
            }
            bz.a(graphics, string2, 30 + bz.a(bm.A(517)), 150, 0);
            return;
        }
        if (P) {
            bm.D();
            return;
        }
        graphics.setClip(0, 0, 640, 360);
        if (o == 4 || o == 5 || o == 6 || o == 7 || o == 12) {
            String string = bm.d(0, 81);
            if (o == 5) {
                string = bm.A(228);
            } else if (o == 6) {
                string = bm.A(497);
            } else if (o == 7) {
                string = bm.A(498);
            } else if (o == 12) {
                string = bm.A(499);
            }
            ay.a(graphics, 2, 3, string, true);
            bm.D.a(graphics, 50, 65, 0, 0);
            n4 = 0;
            bz.a(graphics, -597591, -13032703, 4);
            bz.a(graphics, bm.A(500), 254, 66, 4);
            bz.a(graphics, bm.A(305), 355, 66, 1);
            bz.a(graphics, bm.A(294), 425, 66, 1);
            if (o == 7) {
                bz.a(graphics, bm.A(501), 490, 66, 1);
            } else {
                bz.a(graphics, bm.A(291), 490, 66, 1);
            }
            ay.c(graphics, 232, 100, 306, 200, 5);
        } else if (o == 8 || o == 11 || o == 10) {
            String string = bm.A(502);
            ay.a(graphics, 2, 3, string, true);
            bm.D.a(graphics, 50, 65, 0, 0);
            n4 = 0;
            bz.a(graphics, -597591, -13032703, 4);
            bz.a(graphics, bm.A(503), 245, 66, 0);
            bz.a(graphics, bm.A(504), 415, 66, 0);
            ay.c(graphics, 232, 100, 306, 200, 5);
        } else {
            ay.a(graphics, 2, 3, bm.d(0, 164), true);
            ay.a(graphics);
            ay.a(graphics, 227, 50, 320, 230, 28, 20, 5, o, q);
            if (o != 2) {
                bm.D.a(graphics, 6, 65 + (640 - bm.D.f(6) >> 1), 80, 0);
                bz.a(graphics, 16179625, 0, -1);
                bz.a(graphics, bm.d(0, q[o]) + bm.A(505), 385, 88, 1);
            } else {
                bm.D.a(graphics, 7, 65 + (640 - bm.D.f(7) >> 1), 80, 0);
            }
            if (o != 0) {
                int n7 = 232;
                n4 = n5;
                n3 = 306;
                n2 = 4;
                if (o == 2) {
                    n4 += 55;
                    n2 = g[o];
                    if (ci.a()) {
                        n7 = 350;
                        n4 = 170;
                        n3 = 420;
                    }
                }
                if (p.size() != 0 || o != 2) {
                    ay.c(graphics, n7, n4, n3, n2 * 40, n2);
                }
            } else {
                ay.c(graphics, 235, 121, 300, 40 * g[o], g[o]);
            }
        }
        l.d(graphics);
        if (o == 2 && p.size() != 0) {
            if (ci.a()) {
                ay.a(graphics, 250, 85, 269, 47, true, bm.d(0, 163), false, false);
                graphics.setColor(0xFFFFFF);
                boolean bl2 = false;
                n4 = 0;
                n3 = 0;
                n2 = 0;
                String string = w.l;
                ay.a(graphics, string, ay.a(string, 259), 255, 122, 259, 5, 5 / graphics.getFont().getHeight(), 0);
            } else {
                graphics.setColor(0xFFFFFF);
                String string = bm.d(0, 163) + w.l;
                ay.a(graphics, string, ay.a(string, 269), 250, 85, 269, 47, 47 / graphics.getFont().getHeight(), 0);
            }
        }
        int n8 = 0;
        int n9 = 0;
        n3 = bm.D.g(68);
        if (o == 4 || o == 5 || o == 6 || o == 7 || o == 8 || o == 10 || o == 11 || o == 12) {
            if (o == 8 || o == 10 || o == 11 ? cl.o == null || cl.o.length == 0 : e == null || e.size() == 0) {
                return;
            }
            n8 = 100;
            n9 = 300;
        } else {
            if (p.size() == 0) {
                return;
            }
            if (o != 0) {
                n2 = n5;
                n9 = 4;
                if (o == 2) {
                    n2 += 55;
                    --n9;
                }
                n8 = n2;
                n9 = n8 + n9 * 40;
            } else {
                n8 = 121;
                n9 = 121 + 40 * g[o];
            }
        }
        bm.a(graphics, 0, 0, 0, true, (640 - n3 >> 1) + 65, n8, n9);
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    private static void d(Graphics var0) {
        block45: {
            block44: {
                var1_1 = l.d();
                if (l.p.size() == 0) {
                    if (l.o == 0) {
                        bm.a(var0, bm.B(162), bm.d(1, 138));
                        return;
                    }
                    if (l.o == 2) {
                        bm.a(var0, bm.B(162), bm.d(1, 139));
                        return;
                    }
                }
                if (l.o != 4 && l.o != 5 && l.o != 6 && l.o != 7 && l.o != 8 && l.o != 11 && l.o != 10 && l.o != 12) break block44;
                var2_3 = 100 + bm.dW * 40;
                ** GOTO lbl43
            }
            if (l.p.size() == 0 || l.o == 0) break block45;
            var2_3 = var1_1 + bm.dW * 40;
            if (l.o != 2) ** GOTO lbl43
            if (ci.a()) {
                var2_3 = 170 + bm.dW * 40;
                bz.a(var0, 16179625, 0xFFFFFF, -1);
                bz.a(var0, bm.A(506) + bm.br.size() + "/" + w.o, 250, 137, 0);
                var1_1 = 0;
                bz.a(var0, 16179625, 0, -1);
                bz.a(var0, bm.A(507), 350, 151, 0);
                bz.a(var0, bm.A(508), 388, 151, 0);
                bz.a(var0, bm.A(291), 435, 151, 0);
                ay.b(var0, 350, var2_3, 420, 40);
            } else {
                var2_3 = var1_1 + 40 + 15 + bm.dW * 40;
                bz.a(var0, 16179625, 0xFFFFFF, -1);
                bz.a(var0, bm.A(506) + bm.br.size() + "/" + w.o, 234, var1_1 - 25 + 40, 0);
                var1_1 = var1_1 - 25 + 80 - 20;
                bz.a(var0, 16179625, 0, -1);
                bz.a(var0, bm.A(507), 234, var1_1, 0);
                bz.a(var0, bm.A(508), 388, var1_1, 0);
                bz.a(var0, bm.A(291), 435, var1_1, 0);
lbl43:
                // 3 sources

                l.a(var0, var2_3);
            }
        }
        for (var2_3 = 0; var2_3 < l.g[l.o]; ++var2_3) {
            var1_1 = bm.dY * l.g[l.o] + var2_3;
            if (l.o == 1 || l.o == 3) {
                var1_1 = var2_3;
            }
            var3_4 = null;
            if (l.o == 4 || l.o == 5 || l.o == 6 || l.o == 7 || l.o == 3 || l.o == 1 || l.o == 12) {
                if (l.e != null && l.e.size() > 0 && var2_3 < l.e.size()) {
                    var3_4 = (cr)l.e.elementAt(var2_3);
                }
            } else if (var1_1 >= 0 && var1_1 < l.p.size()) {
                var3_4 = (cr)l.p.elementAt(var1_1);
            }
            bz.a(bm.W, 16179625, -3066322, -1);
            if (l.o == 0) {
                var4_7 = var2_3;
                var1_2 = var0;
                if (var3_4 == null) continue;
                bm.a(var1_2, var3_4, 305, 126 + var4_7 * 40, 2);
                if (var3_4.h()) {
                    bz.a(var1_2, 0xFFF000, -16777216, 4);
                    bz.a(var1_2, bm.d(0, 159), 238, 123 + var4_7 * 40 + 8, 0);
                } else {
                    bz.a(var1_2, 16179625, 0, -1);
                    bz.a(var1_2, bm.d(0, 160), 238, 123 + var4_7 * 40 + 8, 0);
                }
                bz.b((byte)0);
                bz.a(var1_2, var3_4.E, 372, 123 + var4_7 * 40 + 10, 0);
                bz.a(var1_2, 16179625, 0, -1);
                bz.a(var1_2, var3_4.U + bm.A(370), 477, 123 + var4_7 * 40 + 10, 0);
                continue;
            }
            if (l.o == 2) {
                var4_8 = var2_3;
                var1_2 = var0;
                var5_12 = l.d();
                if (var3_4 == null) continue;
                var5_12 = var5_12 + 40 + 15;
                if (ci.a()) {
                    var5_12 = 170;
                }
                bz.a(var1_2, 16179625, 0, 4);
                if (var3_4.ac == 0) {
                    bz.a(var1_2, 0x6E6E6E, 0, 4);
                }
                bz.b((byte)0);
                if (ci.a()) {
                    bz.a(var1_2, var3_4.E, 350, var5_12 + var4_8 * 40 + 3, 0);
                } else {
                    bz.a(var1_2, var3_4.E, 233, var5_12 + var4_8 * 40 + 3, 0);
                }
                bz.b((byte)1);
                var6_16 = "";
                if (var3_4.aa > 0 && var3_4.aa <= 5) {
                    var6_16 = bn.k[var3_4.aa];
                    if (var3_4.aa == 1) {
                        bz.a(var1_2, 0xFFF000, -16777216, 4);
                    } else if (var3_4.aa == 2) {
                        bz.a(var1_2, 0xFFF000, -16777216, 4);
                    } else if (var3_4.aa == 3) {
                        bz.a(var1_2, -16757082, -16777216, 4);
                    } else if (var3_4.aa == 4) {
                        bz.a(var1_2, -16728815, -16777216, 4);
                    } else if (var3_4.aa == 5) {
                        bz.a(var1_2, 16179625, 0, 4);
                    }
                    if (var3_4.ac == 0) {
                        bz.a(var1_2, 0x6E6E6E, 0, 4);
                    }
                    bz.a(var1_2, var6_16, 392, var5_12 + var4_8 * 40 + 3, 0);
                }
                bz.a(var1_2, 16179625, 0, 4);
                if (var3_4.ac == 0) {
                    bz.a(var1_2, 0x6E6E6E, 0, 4);
                }
                bz.a(var1_2, var3_4.U + "", 392 + bz.a(var6_16 + ":"), var5_12 + var4_8 * 40 + 3, 0);
                bz.a(var1_2, 8794902, 0, 4);
                if (var3_4.ac == 0) {
                    bz.a(var1_2, 0x6E6E6E, 0, 4);
                }
                bz.a(var1_2, bm.A(519), 392, var5_12 + var4_8 * 40 + 3 + 14, 0);
                bz.a(var1_2, 16179625, 0, 4);
                if (var3_4.ac == 0) {
                    bz.a(var1_2, 0x6E6E6E, 0, 4);
                }
                bz.a(var1_2, "" + var3_4.ad, 392 + bz.a(bm.A(519)), var5_12 + var4_8 * 40 + 3 + 14, 0);
                bm.a(var1_2, var3_4, 356, var5_12 + var4_8 * 40 + 3, 2);
                continue;
            }
            if (l.o == 4 || l.o == 5 || l.o == 6 || l.o == 7 || l.o == 12) {
                var4_9 = var2_3;
                var1_2 = var0;
                if (var3_4 == null) continue;
                bz.b((byte)0);
                bz.a(var1_2, var3_4.E, 242, 100 + var4_9 * 40 + 4, 0);
                bz.b((byte)1);
                var5_13 = ck.b(15).f(12);
                bm.a(var1_2, var3_4, 355 - (var5_13 >> 1), 100 + var4_9 * 40 + 4, 2);
                var6_17 = 100 + var4_9 * 40 + 4 + 5;
                bz.a(var1_2, 16179625, 0, -1);
                bz.a(var1_2, var3_4.U + "", 495, var6_17, 1);
                var4_10 = "";
                if (l.o == 5 || l.o == 6 || l.o == 7) {
                    var3_5 = var3_4.ac == 0 ? 245 : 244;
                    var4_10 = bm.d(0, var3_5);
                } else if (var3_4.aj == 0) {
                    var4_10 = bm.A(518);
                } else if (var3_4.aj == 1) {
                    var4_10 = bm.d(0, 160);
                } else if (var3_4.aj == 2) {
                    var4_10 = bm.d(0, 159);
                }
                bz.a(var1_2, var4_10, 425, var6_17, 1);
                continue;
            }
            if (l.o == 8 || l.o == 11 || l.o == 10) {
                var3_6 = var2_3;
                var1_2 = var0;
                if (var3_6 >= cl.o.length) continue;
                n.a(var1_2, 245, 100 + var3_6 * 40 + 4, cl.v[var3_6], cl.y[var3_6], cl.w[var3_6], false, true, 0);
                bz.a(var1_2, n.P[cl.x[var3_6]], -13032703, 4);
                var5_14 /* !! */  = bf.a(cl.u[var3_6], 145);
                bf.a(var1_2, cl.u[var3_6], var5_14 /* !! */ , 270, 100 + var3_6 * 40 + 4, 0, -1, 0);
                bz.a(var1_2, 16750080, -13032703, 4);
                bz.a(var1_2, bm.A(509), 415, 100 + var3_6 * 40 + 4, 0);
                bz.a(var1_2, "" + cl.s[var3_6], 415 + bz.a(bm.A(509)), 100 + var3_6 * 40 + 4, 0);
                var5_14 /* !! */  = (int[])("" + cl.t[var3_6]);
                if (cl.t[var3_6] == 0) {
                    var5_14 /* !! */  = (int[])bm.A(163);
                }
                bz.a(var1_2, bm.A(510), 415, 100 + var3_6 * 40 + 4 + 19, 0);
                bz.a(var1_2, (String)var5_14 /* !! */ , 415 + bz.a(bm.A(510)), 100 + var3_6 * 40 + 4 + 19, 0);
                continue;
            }
            var4_11 = var2_3;
            var1_2 = var0;
            var5_15 = l.d();
            if (var3_4 == null) continue;
            var4_11 = var5_15 + var4_11 * 40 + 3;
            bz.a(var1_2, 16179625, 0, -1);
            bz.b((byte)0);
            bz.a(var1_2, var3_4.E, 235, var4_11, 0);
            bz.b((byte)1);
            bm.a(var1_2, var3_4, 358, var4_11, 2);
            bz.a(var1_2, var3_4.U + bm.d(0, 64), 397, var4_11 + 3, 0);
            var5_15 = var3_4.ac == 0 ? 245 : 244;
            bz.a(var1_2, bm.d(0, var5_15), 482, var4_11 + 3, 0);
        }
        if (l.p.size() != 0 && l.o == 0) {
            ay.b(var0, 235, 120 + bm.dW * 40, 300, 42);
        }
    }

    private static void a(Graphics graphics, int n2) {
        ay.b(graphics, 233, n2, 305, 40);
    }

    public static void a(int[] nArray) {
        h = nArray;
        i = new String[nArray.length];
        for (int n2 = 0; n2 < h.length; n2 = (int)((byte)(n2 + 1))) {
            if (h[n2] >= 428) continue;
            l.i[n2] = n2 + 1 + "." + bm.d(0, h[n2]);
        }
    }

    /*
     * Handled duff style switch with additional control
     * Enabled aggressive block sorting
     */
    private static void e() {
        int n2;
        int n3 = bm.dW;
        if (o == 2) {
            n3 = bm.dY * g[o] + bm.dW;
        }
        cr cr2 = null;
        if (o == 4 || o == 5 || o == 6 || o == 7 || o == 12) {
            if (e != null && bm.dW < e.size() && (cr2 = (cr)e.elementAt(bm.dW)) != null && cr2.C == bm.cb.C) {
                return;
            }
        } else if (o == 8 || o == 9 || o == 10 || o == 11) {
            if (cl.o == null || cl.o.length == 0 || n3 >= cl.o.length) {
                return;
            }
            L = 0;
            switch (o) {
                case 8: {
                    d = o;
                    h = r;
                    break;
                }
                case 9: {
                    if (d == 10) {
                        h = u;
                        break;
                    }
                    h = s;
                    break;
                }
                case 10: {
                    d = o;
                    h = t;
                    break;
                }
                case 11: {
                    d = o;
                    h = v;
                    break;
                }
            }
            if (h != null) {
                n3 = h.length;
                i = new String[n3];
                for (n2 = 0; n2 < n3; ++n2) {
                    l.i[n2] = "" + (n2 + 1) + "." + bm.d(0, h[n2]);
                }
                f = 1;
            }
        } else {
            if (n3 >= p.size()) {
                return;
            }
            cr2 = (cr)p.elementAt(n3);
        }
        if (cr2 == null) {
            return;
        }
        j = cr2;
        L = 0;
        if (c) {
            f = 1;
            return;
        }
        switch (o) {
            case 0: {
                if (cr2.C == bm.cb.C) {
                    if (bm.cb.h()) {
                        h = w;
                        break;
                    }
                    h = x;
                    break;
                }
                if (bm.cb.h()) {
                    h = y;
                    break;
                }
                h = z;
                break;
            }
            case 1: {
                if (n == 0) {
                    h = A;
                    break;
                }
                if (n == 1) {
                    h = C;
                    L = 1;
                    break;
                }
                if (n == 2) {
                    h = D;
                    L = 1;
                    break;
                }
                if (n != 3) break;
                if (l.j.C == bm.cb.C) {
                    bm.d(bm.A(174));
                    return;
                }
                bd.c = l.j.E;
                n3 = 0;
                n = 0;
                bm.v();
                break;
            }
            case 2: {
                cr cr3 = cr2;
                if (cr3.C == bm.cb.C) {
                    if (bm.cb.aa == 1) {
                        h = I;
                        break;
                    }
                    h = J;
                    break;
                }
                int n4 = 0;
                block22: do {
                    switch (n4 == 0 ? bm.cb.aa : n4) {
                        case 1: {
                            h = E;
                            break;
                        }
                        case 2: {
                            n4 = 4;
                            if (cr3.aa == 1) continue block22;
                            h = F;
                            break;
                        }
                        case 3: {
                            if (cr3.aa > 3) {
                                h = G;
                                break;
                            }
                            h = H;
                            break;
                        }
                        case 4: 
                        case 5: {
                            h = H;
                            break;
                        }
                    }
                    break;
                } while (true);
                break;
            }
            case 3: {
                h = B;
                break;
            }
            case 4: 
            case 5: {
                h = K;
                break;
            }
            case 6: 
            case 7: {
                f = 1;
                return;
            }
            case 12: {
                bm.bn.f(411);
                return;
            }
        }
        if (h != null) {
            int n5 = h.length;
            i = new String[n5];
            for (n2 = 0; n2 < n5; ++n2) {
                l.i[n2] = "" + (n2 + 1) + "." + bm.d(0, h[n2]);
            }
            f = 1;
        }
    }

    private static void f() {
        block81: {
            int n2 = h[L];
            int n3 = n2;
            n3 = 0;
            n3 = n2;
            f = 0;
            b = false;
            switch (n3) {
                case 410: {
                    bm.G(12);
                    return;
                }
                case 321: {
                    bc.a((byte)19);
                    bm.l(81);
                    l.a((byte)2);
                    return;
                }
                case 322: {
                    bm.l(81);
                    l.a((byte)2);
                    return;
                }
                case 323: {
                    bm.m(36);
                    return;
                }
                case 83: {
                    if (l.k.d == -1) {
                        bm.d(bm.A(171));
                        return;
                    }
                    bm.bn.a(l.j.C, l.k.d, l.k.q);
                    break;
                }
                case 82: {
                    bt.d = (byte)4;
                    return;
                }
                case 84: {
                    if (bt.e == 6 && (bt.y == null || bt.y.length <= 0)) {
                        bm.d(bm.A(169));
                        return;
                    }
                    if (bt.e == 2 && bm.cb.U < 10) {
                        bm.d(bm.A(170));
                        return;
                    }
                    bc.a((byte)14);
                    bt.f = bt.e;
                    return;
                }
                case 103: {
                    l.b(1);
                    return;
                }
                case 99: {
                    f = 3;
                    M = new String[5 - bm.cb.aa];
                    for (n3 = 0; n3 < M.length; ++n3) {
                        l.M[n3] = bn.l[bm.cb.aa + n3];
                    }
                    break block81;
                }
                case 100: {
                    l.b(2);
                    return;
                }
                case 106: 
                case 107: 
                case 108: 
                case 109: 
                case 110: 
                case 111: {
                    bm.bn.f(h[L]);
                    return;
                }
                case 85: {
                    if (l.j.C == bm.cb.C) {
                        bm.a(1, true);
                        return;
                    }
                    bm.bn.A(l.j.C);
                    bm.E(3);
                    return;
                }
                case 86: {
                    if (l.j.C == bm.cb.C) {
                        bm.d(bm.A(168));
                        return;
                    }
                    bt.f = bt.e;
                    bt.e = 6;
                    bc.a((byte)14, l.j.C);
                    return;
                }
                case 89: {
                    if (l.j.C == bm.cb.C) {
                        bm.d(bm.A(173));
                        return;
                    }
                    bm.bn.u(l.j.C);
                    return;
                }
                case 88: {
                    if (l.j.C == bm.cb.C) {
                        bm.d(bm.A(172));
                        return;
                    }
                    bm.bn.s(l.j.C);
                    return;
                }
                case 94: {
                    if (l.j.C == bm.cb.C) {
                        bm.d(bm.A(174));
                        return;
                    }
                    bd.c = l.j.E;
                    bm.m(63);
                    return;
                }
                case 101: {
                    l.b(3);
                    return;
                }
                case 102: {
                    l.b(4);
                    return;
                }
                case 105: {
                    bm.bn.v(l.j.C);
                    l.a(true);
                    return;
                }
                case 96: {
                    if (bm.cb.h()) {
                        l.b(6);
                        return;
                    }
                    bm.d(bm.A(520));
                    return;
                }
                case 98: {
                    if (bm.cp) {
                        bm.d(bm.A(521));
                        return;
                    }
                    if (cr.j() && bm.cb.h()) {
                        l.b(8);
                        return;
                    }
                    if (!cr.j()) {
                        bm.d(bm.d(0, 157));
                        return;
                    }
                    break block81;
                }
                case 97: {
                    if (bm.cp) {
                        bm.d(bm.A(522));
                        return;
                    }
                    if (cr.j()) {
                        l.b(7);
                        return;
                    }
                    if (!cr.j()) {
                        bm.d(bm.d(0, 157));
                        return;
                    }
                    break block81;
                }
                case 113: {
                    d = o;
                    l.a((byte)9);
                    return;
                }
                case 115: {
                    l.b(9);
                    return;
                }
                case 114: {
                    bc.a((byte)54);
                    return;
                }
                case 120: {
                    bm.ad();
                    bm.bn.D(cl.o[bm.dW]);
                    bm.bn.d(0, (byte)5);
                    return;
                }
                case 116: 
                case 117: 
                case 118: {
                    bm.bn.a(cl.g, cl.h, cl.i, cl.k, 0, (byte)5, cl.l, cl.m, cl.n, cl.j, h[L]);
                    break;
                }
                case 119: {
                    if (o == 8) {
                        bm.bn.a(cl.g, cl.h, cl.i, cl.k, 0, (byte)5, cl.l, cl.m, cl.n, cl.j, -1);
                    } else if (o == 11) {
                        bm.bn.e(0, (byte)5);
                    } else if (o == 10) {
                        bm.bn.d(0, (byte)5);
                    }
                    bm.ad();
                    return;
                }
                case 112: {
                    n3 = cl.o[bm.dW];
                    bm.bn.C(n3);
                    return;
                }
                case 95: {
                    if (bm.cp) {
                        bm.a(bm.A(523), true);
                        bm.H();
                        return;
                    }
                    n3 = bm.cb.C;
                    int n4 = l.j.C;
                    if (n3 != n4) {
                        bm.bn.r(n4);
                        bm.c(bm.A(524));
                        bm.H();
                        return;
                    }
                    break block81;
                }
                case 90: {
                    n3 = bm.cb.C;
                    int n5 = l.j.C;
                    if (n3 != n5) {
                        bm.bn.f(n3, n5);
                        bm.H();
                        return;
                    }
                    break block81;
                }
                case 104: {
                    l.b(5);
                    return;
                }
                case 87: {
                    if (l.j.C == bm.cb.C) {
                        bm.d(bm.A(175));
                        return;
                    }
                    bm.bn.p(l.j.C);
                    return;
                }
                case 93: {
                    if (l.j.C == bm.cb.C) {
                        bm.d(bm.A(175));
                        return;
                    }
                    bm.H();
                    bm.bn.x(l.j.C);
                    bm.a(bm.B(284), true);
                    bm.G();
                    return;
                }
                case 91: {
                    if (!bm.D(16)) {
                        bm.d(bm.B(296));
                        return;
                    }
                    if (cr.P()) {
                        bm.d(bm.A(525));
                        return;
                    }
                    bm.bn.a((bh)bm.cb, (bh)j);
                    bm.H();
                    bm.d(bm.B(284));
                    bm.G();
                    return;
                }
                case 92: {
                    if (bm.cf.g()) {
                        h h2 = (cr)bm.cf;
                        h2 = h2.aX;
                        if (h2 != null) {
                            bm.l(30);
                            bm.bn.b(l.j.C, (short)-1);
                            bm.a(bm.B(284), true);
                            bm.G();
                            return;
                        }
                        bm.l(30);
                        bm.c(bm.A(526));
                        return;
                    }
                    break block81;
                }
                case 188: {
                    bm.bn.a(cn.w.d, cn.T, l.j.E, (byte)1);
                    bm.H();
                    bm.G();
                    n3 = 0;
                    n = 0;
                    return;
                }
                case 189: {
                    bm.bn.a(cn.w.d, cn.T, l.j.E);
                    bm.H();
                    bm.G();
                    n3 = 0;
                    n = 0;
                    return;
                }
                case 423: {
                    bm.bn.e(l.j.C, l.j.E);
                    return;
                }
                case 39: {
                    f = 0;
                    return;
                }
                case 403: {
                    bm.J();
                    return;
                }
                case 404: {
                    bm.l(61);
                    return;
                }
                case 405: {
                    bm.bn.G(10005);
                    return;
                }
                case 406: {
                    bm.bn.G(10090);
                    return;
                }
                default: {
                    int n6 = 0;
                    if (o == 6) {
                        n6 = 4;
                    } else if (o == 7) {
                        n6 = 100;
                    }
                    if (bm.cf != null) {
                        bm.cf = null;
                    }
                    if (n6 == 0) {
                        bm.bn.a(j == null ? 0 : l.j.C, m, n3, l);
                        break;
                    }
                    bm.bn.a(3400, new int[]{j == null ? 0 : l.j.C, n6, n3, g[o]});
                }
            }
            bm.ad();
        }
    }

    public static void b(Graphics graphics) {
        if (n == 0 || o != 1) {
            ay.a(graphics, i, 180, L, true, true);
            return;
        }
        if (n == 1) {
            ay.a(graphics, bg.d(bm.A(527), l.j.E), h, L, 180, true, bm.A(249), true);
            return;
        }
        if (n == 2) {
            ay.a(graphics, bg.d(bm.A(528), l.j.E), h, L, 180, true, bm.A(249), true);
        }
    }

    public static void c() {
        block36: {
            block35: {
                block37: {
                    int n2;
                    int n3 = h.length;
                    if (f == 3) {
                        n3 = M.length;
                    }
                    if (f == 2) {
                        n3 = 2;
                    } else if (f == 1 && (n2 = bm.as() - 1) >= 0 && n2 < n3) {
                        L = n2;
                        l.f();
                        return;
                    }
                    n2 = 0;
                    n2 = n3;
                    n2 = bm.f(n2, 0);
                    if (n2 != -1) {
                        if (L == n2) {
                            bm.dB = true;
                        } else {
                            L = n2;
                            bm.X = 3;
                        }
                    }
                    if (bm.x() == 72) {
                        if (bm.h(bm.ds, bm.dt) != -1) {
                            bm.bl = bm.h(bm.ds, bm.dt);
                        }
                    } else if (o != 9) {
                        bm.g(20, 180);
                    }
                    if (bm.v(16384)) {
                        L = L - 1 < 0 ? n3 - 1 : L - 1;
                        return;
                    }
                    if (bm.v(32768)) {
                        L = L + 1 > n3 - 1 ? 0 : L + 1;
                        return;
                    }
                    if (!bm.v(196608)) break block35;
                    if (f == 1) {
                        l.f();
                        return;
                    }
                    if (f == 3) {
                        if (j != null) {
                            bm.bn.a(l.j.C, bm.cb.ab, L + bm.cb.aa + 1);
                        }
                        f = 0;
                        return;
                    }
                    if (f != 2) break block36;
                    if (L != 1) break block37;
                    f = 0;
                    break block36;
                }
                switch (N) {
                    case 1: {
                        bm.bn.a(l.j.C, bm.cb.ab, 1);
                        f = 0;
                        break block36;
                    }
                    case 2: {
                        if (bm.cb.C != l.j.C) {
                            bm.bn.a(l.j.C, bm.cb.ab, 0);
                        } else {
                            bm.d(bm.A(533));
                        }
                        bm.c(bm.A(524));
                        bm.H();
                        break block36;
                    }
                    case 3: {
                        bm.bn.q();
                        f = 0;
                        break block36;
                    }
                    case 4: {
                        bm.bn.p();
                        f = 0;
                        break block36;
                    }
                    case 5: {
                        bm.bn.t(l.j.C);
                        bm.ax();
                        bm.bn.a((byte)2, 0, g[1]);
                        bm.ad();
                        f = 0;
                        break block36;
                    }
                    case 6: {
                        bm.bn.q(l.j.C);
                        f = 0;
                        break block36;
                    }
                    case 7: {
                        bm.bn.n();
                        bm.H();
                        f = 0;
                        break block36;
                    }
                    case 8: {
                        bm.bn.o();
                        bm.H();
                        f = 0;
                        break block36;
                    }
                    case 9: {
                        if (cl.t[bm.dW] == 0) {
                            bm.d(bm.A(534));
                        } else {
                            bm.bn.g(cl.o[bm.dW], cl.t[bm.dW]);
                        }
                        if (d == 8) {
                            bm.bn.a(cl.g, cl.h, cl.i, cl.k, 0, (byte)5, cl.l, cl.m, cl.n, cl.j, -1);
                        } else if (d == 11) {
                            bm.bn.e(0, (byte)5);
                        }
                        bm.ad();
                    }
                    default: {
                        return;
                    }
                }
            }
            if (bm.v(262144)) {
                f = 0;
                b = false;
            }
        }
    }

    private static void b(int n2) {
        N = n2;
        f = 2;
        L = 1;
        switch (N) {
            case 1: {
                O = bm.d(0, 153) + bm.d(0, 155);
                return;
            }
            case 2: {
                O = bm.d(0, 153) + bm.d(0, 154);
                return;
            }
            case 3: {
                O = bm.d(0, 306);
                return;
            }
            case 4: {
                O = bm.d(0, 307);
                return;
            }
            case 5: {
                O = bm.d(0, 292);
                return;
            }
            case 6: {
                O = bm.A(529);
                return;
            }
            case 7: {
                O = bm.A(530);
                return;
            }
            case 8: {
                O = bm.A(531);
                return;
            }
            case 9: {
                O = bm.A(532);
            }
        }
    }

    public static void a(cr cr2, int[] nArray) {
        j = cr2;
        if (cr2 == null) {
            return;
        }
        P = true;
        bm.l(72);
        L = 0;
        h = nArray;
        int n2 = nArray.length;
        i = new String[n2];
        for (int i2 = 0; i2 < n2; ++i2) {
            l.i[i2] = "" + (i2 + 1) + "." + bm.d(0, h[i2]);
        }
        f = 1;
    }

    static {
        b = false;
        c = false;
        n = 0;
        o = 0;
        d = 0;
        p = new Vector();
        e = new Vector();
        g = new int[]{3, 4, 3, 4, 5, 5, 5, 5, 5, 5, 5, 5, 5};
        q = new int[]{77, 78, 79, 80};
        r = new int[]{112, 113, 114, 115, 116, 117, 118, 119, 39};
        s = new int[]{112, 114, 115, 39};
        t = new int[]{113, 119, 120, 39};
        u = new int[]{112, 120, 39};
        v = new int[]{113, 114, 115, 119, 39};
        w = new int[]{97, 98, 39};
        x = new int[]{97, 39};
        y = new int[]{85, 86, 95, 96, 97, 98, 111, 39};
        z = new int[]{85, 86, 97, 111, 39};
        A = new int[]{85, 86, 104, 87, 109, 423, 39};
        B = new int[]{85, 105, 39};
        C = new int[]{189, 39};
        D = new int[]{188, 39};
        E = new int[]{85, 86, 103, 99, 100, 110, 423, 39};
        F = new int[]{85, 86, 100, 99, 110, 423, 39};
        G = new int[]{85, 86, 99, 110, 423, 39};
        H = new int[]{85, 86, 110, 423, 39};
        I = new int[]{101, 39};
        J = new int[]{102, 39};
        K = new int[]{85, 86, 87, 107, 423, 39};
        N = 0;
        P = false;
    }
}

