/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class ab {
    private static int d = 0;
    private static int e = 0;
    private static byte f = 0;
    private static int g = 0;
    private static int[] h = new int[]{30, 298, 230};
    private static int i = -1;
    static byte a = 0;
    static final int[] b = new int[]{28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 47, 52};
    private static int[] j = new int[]{328, 330, 332, 334, 336, 338, 340, 342, 344, 346, 348, 350, 352, 354, 356, 358, 360, 362};
    private static int[] k = new int[]{329, 331, 333, 335, 337, 339, 341, 343, 345, 347, 349, 351, 353, 355, 357, 359, 361, 363};
    private static int[] l = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17};
    private static boolean m = false;
    private static int[] n;
    static int[][] c;
    private static int[][] o;
    private static int p;
    private static int q;
    private static String[] r;
    private static cn s;

    static void a(int n2) {
        switch (n2) {
            case 0: {
                bm.m(57);
                return;
            }
            case 1: {
                bm.J();
                return;
            }
            case 2: {
                bm.m(61);
                return;
            }
            case 3: {
                bm.a(1, true);
                return;
            }
            case 4: {
                l.a((byte)0);
                bm.l(81);
                return;
            }
            case 5: {
                bm.ax();
                bm.bn.a((byte)2, 0, l.g[1]);
                bm.ad();
                return;
            }
            case 6: {
                bm.a(0, true);
                return;
            }
            case 7: {
                l.a((byte)2);
                bm.l(81);
                return;
            }
            case 12: {
                cr.as = (byte)(cr.as + 1);
                if (cr.as >= 3) {
                    cr.as = 0;
                }
                String string = "";
                switch (cr.as) {
                    case 0: {
                        string = bm.A(1186);
                        break;
                    }
                    case 1: {
                        string = bm.A(1187);
                        break;
                    }
                    case 2: {
                        string = bm.A(1188);
                    }
                }
                bm.c(string);
                return;
            }
            case 13: {
                a.b = (byte)(a.b + 1);
                if (a.b >= 3) {
                    a.b = 0;
                }
                bm.bn.M();
                String string = "";
                switch (a.b) {
                    case 0: {
                        string = bm.A(178);
                        break;
                    }
                    case 1: {
                        string = bm.A(179);
                        break;
                    }
                    case 2: {
                        string = bm.A(180);
                    }
                }
                bm.c(string);
                return;
            }
            case 14: {
                bm.t = !bm.t;
                return;
            }
            case 15: {
                bm.bn.G(10090);
                return;
            }
            case 16: {
                bm.z();
                return;
            }
            case 17: {
                if (bm.u = !bm.u) {
                    bm.c(bm.A(1189));
                } else {
                    bm.c(bm.A(1190));
                }
                bm.bn.a(bm.u);
            }
        }
    }

    public ab() {
        ab.b();
    }

    private static void b() {
        for (int i2 = 0; i2 < 8; ++i2) {
            ab.c[0][i2] = -1;
            ab.c[1][i2] = -1;
        }
        ab.c[0][0] = -1;
        ab.c[0][1] = 1073742825;
        ab.c[0][2] = 1073742830;
        ab.c[0][3] = 0x20000006;
        ab.c[0][4] = Integer.MIN_VALUE | i.a();
        ab.c[0][5] = 0x20000000;
        ab.c[0][6] = 0x2000000D;
        ab.c[0][7] = 1073771872;
        ab.c[1][0] = 0x20000002;
        ab.c[1][1] = 0x20000007;
        ab.c[1][2] = 0x20000004;
        ab.c[1][3] = 0x20000005;
        ab.c[1][4] = 0x20000006;
        ab.c[1][5] = 0x20000000;
        ab.c[1][6] = 0x2000000E;
        ab.c[1][7] = 0x20000001;
    }

    public static void a(boolean bl2) {
        ab.a(true, false);
    }

    public static void a(boolean n2, boolean bl2) {
        if (n2 != 0) {
            bm.aA.a(2, 14, 110, 236);
            bm.aA.s = false;
            bm.aR = bm.aA;
        }
        ab.c(0);
        e = 0;
        f = 0;
        g = 0;
        q = 0;
        d = 29;
        if (bl2) {
            n = new int[16];
            g = 0;
            m = true;
            for (n2 = 0; n2 < i.b.size(); ++n2) {
                i i2 = (i)i.b.elementAt(n2);
                if (i2 == null || i2.e() || !i2.f() || i2.g() > n.length) continue;
                ab.n[i2.g() - 1] = Integer.MIN_VALUE | i2.c;
            }
        } else {
            m = false;
            for (n2 = 0; n2 < 8; ++n2) {
                ab.o[0][n2] = c[0][n2];
                ab.o[1][n2] = c[1][n2];
            }
            s = new cn(0, 16, 0);
            s.a(5, 8, 294, 127);
        }
    }

    private static void c(int n2) {
        i = n2;
        bm.X = 3;
    }

    public static void a(Graphics graphics) {
        int n2;
        i i2;
        int n3;
        int n4;
        Graphics graphics2 = graphics;
        if (m) {
            g = 0;
            ay.a(graphics2, -1, 38, bm.A(1193));
        } else {
            ay.a(graphics2, -1, 38, bm.B(1193));
        }
        bm.D.a(graphics2, 52 + g, 0, 202, 0);
        bm.D.a(graphics2, 4, 0, 0, 0);
        bm.D.a(graphics2, 6, 640 - bm.D.f(6) >> 1, 57, 0);
        bz.a(graphics2, 16773329, 16773329, -1);
        bm.D.a(graphics2, 6, 640 - bm.D.f(6) >> 1, 142, 0);
        if (m) {
            ay.b(graphics2, 564, 70, 0, 8, 2, -1);
            for (n4 = 0; n4 < n.length; ++n4) {
                n3 = 70 + n4 % 8 * 29 + 3;
                int n5 = 567;
                if (n4 > 7) {
                    n5 += 29;
                }
                if ((n[n4] & Integer.MIN_VALUE) != 0 && (i2 = i.a(n2 = n[n4] & 0xFFFFFFF)) != null) {
                    i2.a(graphics2, n5, n3, false);
                }
                bz.b(2);
                bz.a(graphics2, n4 + 1 + "", n5, n3, 0);
                bz.b((byte)1);
            }
        } else {
            ab.a(graphics2, c[0], 606 - bm.D.f(191) - 4, 33, 35, null, 0, 0, false);
            ab.a(graphics2, c[1], 604, 33, 35, null, 0, 0, false);
            bm.D.a(graphics2, 57, 572, 317, 0);
        }
        if (g == 1) {
            bm.aA.a(graphics2);
        } else {
            n4 = 0;
            ay.b(graphics2, 117, 236, 0, 2, 14, -1);
            if (g == 0) {
                for (n3 = 0; n3 < i.b.size(); ++n3) {
                    i i3 = (i)i.b.elementAt(n3);
                    if (i3 == null) continue;
                    bm.a(ck.b(25), graphics2, (int)i3.e, 119 + n3 % 14 * 29 + 1, 238 + n3 / 14 * 29 + 1, 0);
                }
            } else if (g == 2) {
                for (n3 = 0; n3 < l.length; ++n3) {
                    bm.a(ck.b(25), graphics2, b[n3], 119 + n3 % 14 * 29 + 1, 238 + n3 / 14 * 29 + 1, 0);
                }
            }
        }
        for (n4 = 0; n4 < 3; ++n4) {
            if (n4 == g) {
                bz.a(graphics2, -9080, -13032703, 4);
            } else {
                bz.a(graphics2, 6902561, -13032703, -1);
            }
            bz.a(graphics2, bm.d(0, h[n4]), 233 + n4 * 87, 210, 1);
        }
        bz.a(graphics2, 16179625, -13032703, -1);
        bz.a(graphics2, bm.A(1194), 320, 120, 1);
        if (f != 0) {
            if (m) {
                bm.a(graphics2, 565 + e / 8 * 29 + 1 - 3, 70 + e % 8 * 29 + 1 - 2, 29, 29, false);
            } else {
                bm.a(graphics2, 565 + e / 8 * 38 + 1 - 3, 41 + e % 8 * 35 + 1 - 2, 29, 29, false);
            }
            for (n4 = 0; n4 < 16; ++n4) {
                aq.e(Integer.MIN_VALUE);
                if (n4 == e) continue;
                if (m) {
                    aq.b(graphics2, 565 + n4 / 8 * 29, 70 + n4 % 8 * 29, 29, 29);
                    continue;
                }
                aq.b(graphics2, 565 + n4 / 8 * 38, 41 + n4 % 8 * 35, 26, 26);
            }
        } else {
            aq.e(Integer.MIN_VALUE);
            aq.b(graphics2, 107, 234, 424, 61);
        }
        bm.D.a(graphics2, 90, 0, 0, 0);
        bz.a(graphics2, -597591, -13032703, 4);
        bz.a(graphics2, bm.A(703), 320, 360 - bm.D.g(90) + 2, 1);
        bz.a(graphics2, 16179625, 16179625, -1);
        if (f == 0) {
            String string = "";
            int n6 = c[e / 8][e % 8];
            if (m) {
                n6 = n[e];
            }
            if (n6 != -1 && n6 != 0) {
                if ((n6 & Integer.MIN_VALUE) != 0) {
                    n2 = n6 & 0xFFFFFFF;
                    i2 = i.a(n2);
                    if (i2 != null) {
                        string = i2.f;
                    }
                } else if ((n6 & 0x40000000) != 0) {
                    n2 = n6 >> 16 & 0xFFF;
                    int n7 = n6 & 0xFFFF;
                    n n8 = bm.aA.b(n2);
                    if (n8 == null || n7 != n8.d) {
                        n8 = bm.aA.c(n7);
                    }
                    if (n8 != null) {
                        string = n8.e;
                    }
                } else if ((n6 & 0x20000000) != 0 && (n2 = n6 & 0xFFFFFFF) < l.length && l[n2] >= 0) {
                    string = bm.d(0, j[l[n2]]);
                }
                bz.a(aq.a, string, 320, 65, 1);
            }
        }
        String string = "";
        switch (g) {
            case 0: {
                i i4;
                if (q >= i.b.size() || (i4 = (i)i.b.elementAt(q)) == null) break;
                string = i4.f;
                break;
            }
            case 1: {
                string = cn.z;
                break;
            }
            case 2: {
                if (q >= l.length || l[q] < 0) break;
                string = bm.d(0, j[l[q]]);
            }
        }
        ay.a(graphics2, string, 181, 149, 277, 21, 2, (byte)0, true, 0);
        if (f != 1) {
            bm.c(graphics2, bm.aH, bm.aI, d, 29);
        }
        switch (i) {
            case 0: {
                return;
            }
            case 1: {
                graphics2 = graphics;
                ay.a(graphics2, bm.A(1198), r, p, 180, true, bm.A(232), true);
                return;
            }
            case 2: {
                cn.a(graphics, 0, 0, -1, true);
                return;
            }
            case 3: {
                ay.a(graphics, 107, 60, 426, 240, false, null, true);
                int n9 = 0;
                if (f == 0) {
                    n9 = c[e / 8][e % 8] & 0xFFFFFFF;
                    if (m) {
                        n9 = n[e] & 0xFFFFFFF;
                    }
                } else if (f == 2 && g == 0) {
                    n9 = ((i)i.b.elementAt((int)ab.q)).c;
                }
                Object object = i.a(n9);
                String string2 = bm.A(537);
                if (((i)object).g != null) {
                    string2 = ((i)object).g;
                } else {
                    bm.bn.l(0, ((i)object).c);
                }
                bz.a(graphics, -597591, -13032703, 4);
                object = bf.a(string2, 416);
                ay.a(graphics, string2, (int[])object, 112, 65, 416, 230, 0, 15, 16, false);
                return;
            }
            case 4: {
                ay.a(graphics, 107, 90, 426, 180, false, null, true);
                int n10 = 0;
                if (f == 0) {
                    n10 = c[e / 8][e % 8] & 0xFFFFFFF;
                } else if (f == 2 && g == 2) {
                    n10 = l[q];
                }
                String string3 = bm.d(0, k[n10]);
                bz.a(graphics, -597591, -13032703, 4);
                int[] nArray = bf.a(string3, 416);
                ay.a(graphics, string3, nArray, 112, 95, 416, 170, 0, 11, 16, false);
            }
        }
    }

    /*
     * Unable to fully structure code
     */
    public static void a() {
        switch (ab.i) {
            case 0: {
                if (ab.f == 0) ** GOTO lbl28
                if (ab.g != 1) ** GOTO lbl19
                var1 = bm.aA.l;
                var2_8 = bm.aA.m;
                var3_13 = 29 * bm.aA.n;
                var0_14 = 29 * bm.aA.o;
                if (!bm.e(var1, var2_8, var3_13, var0_14)) ** GOTO lbl54
                var0_14 = (bm.dt - var2_8) / 29;
                if ((var0_14 = bm.aA.p * bm.aA.n + var0_14 * bm.aA.n + (var1 = (bm.ds - var1) / 29)) <= bm.aA.k - 1) {
                    if (bm.aA.q == var0_14) {
                        bm.dB = true;
                    } else {
                        bm.aA.q = var0_14;
                    }
                }
                bm.aA.h();
                bm.X = 3;
                ** GOTO lbl54
lbl19:
                // 1 sources

                if (!bm.e(119, 238, 406, 58)) ** GOTO lbl54
                var0_14 = (bm.dt - 238) / 29;
                var1 = (bm.ds - 119) / 29;
                if (ab.q == (var0_14 = var0_14 * 14 + var1)) {
                    bm.dB = true;
                } else {
                    ab.q = var0_14;
                }
                bm.X = 3;
                ** GOTO lbl54
lbl28:
                // 1 sources

                var0_15 = null;
                var1_1 = null;
                v0 = var2_8 = ab.m != false ? ab.n.length >> 1 : 8;
                if (ab.m) {
                    var0_15 = bm.d(var2_8, 565, 70, 29, 29, 0);
                    var1_1 = bm.d(var2_8, 594, 70, 29, 29, 0);
                } else {
                    var0_15 = bm.d(var2_8, 560, 36, 36, 36, -1);
                    var1_1 = bm.d(var2_8, 596, 36, 36, 36, -1);
                }
                bm.dB = false;
                for (var3_13 = 0; var3_13 < var2_8; ++var3_13) {
                    if (!bm.a(var0_15[var3_13])) continue;
                    if (ab.e == var3_13) {
                        bm.dB = true;
                    } else {
                        ab.e = var3_13;
                    }
                    bm.X = 3;
                    ** GOTO lbl54
                }
                for (var3_13 = 0; var3_13 < var2_8; ++var3_13) {
                    if (!bm.a(var1_1[var3_13])) continue;
                    if (ab.e == var3_13 + var2_8) {
                        bm.dB = true;
                    } else {
                        ab.e = var3_13 + var2_8;
                    }
                    bm.X = 3;
                    break;
                }
lbl54:
                // 7 sources

                var2_9 = null;
                var2_9 = bm.c(3, 174, 204, 97, 28, 1);
                for (var0_16 = 0; var0_16 < 3; ++var0_16) {
                    if (!bm.a(var2_9[var0_16])) continue;
                    if (ab.g == var0_16) break;
                    ab.g = ab.m != false ? 0 : var0_16;
                    bm.X = 3;
                    break;
                }
                if (!bm.v(2048) || ab.m) ** GOTO lbl67
                ab.q = 0;
                ab.g = --ab.g < 0 ? 2 : ab.g;
                bm.X = 3;
                ** GOTO lbl112
lbl67:
                // 1 sources

                if (!bm.v(1024) || ab.m) ** GOTO lbl72
                ab.q = 0;
                ab.g = ++ab.g > 2 ? 0 : ab.g;
                bm.X = 3;
                ** GOTO lbl112
lbl72:
                // 1 sources

                if (!bm.v(1)) ** GOTO lbl112
                if (ab.m) {
                    ab.g = 0;
                }
                if (ab.f != 0) ** GOTO lbl100
                var0_16 = ab.c[ab.e / 8][ab.e % 8];
                if (ab.m) {
                    var0_16 = ab.n[ab.e];
                }
                if (var0_16 == -1 || var0_16 == 0) break;
                if ((var0_16 & -2147483648) != 0) {
                    var1_2 = var0_16 & 0xFFFFFFF;
                    v1 = i.a(var1_2);
                    var2_9 = v1;
                    if (v1 == null) break;
                    ab.c(3);
                } else if ((var0_16 & 0x40000000) != 0) {
                    var1_3 = var0_16 >> 16 & 4095;
                    var2_10 = var0_16 & 65535;
                    var1_4 = bm.aA.b(var1_3);
                    if (var1_4 == null || var2_10 != var1_4.d) {
                        var1_4 = bm.aA.c(var2_10);
                    }
                    if ((cn.w = var1_4) == null) break;
                    cn.a(false, null, null);
                    ab.c(2);
                } else {
                    if ((var0_16 & 0x20000000) == 0) break;
                    ab.c(4);
                }
                ** GOTO lbl245
lbl100:
                // 1 sources

                if (ab.f != 2 || ab.g != 1) ** GOTO lbl105
                if (cn.w == null) break;
                cn.a(false, null, null);
                ab.c(2);
                ** GOTO lbl245
lbl105:
                // 1 sources

                if (ab.f != 2 || ab.g != 0) ** GOTO lbl109
                if (ab.q >= i.b.size() || (var0_17 = (i)i.b.elementAt(ab.q)) == null) break;
                ab.c(3);
                ** GOTO lbl245
lbl109:
                // 1 sources

                if (ab.f != 2 || ab.g != 2 || ab.q >= ab.l.length) break;
                ab.c(4);
                break;
lbl112:
                // 3 sources

                if (ab.f != 0) ** GOTO lbl147
                ab.d = 29;
                if (bm.v(4096)) {
                    if (ab.e - 8 >= 0) {
                        ab.e -= 8;
                    }
                } else if (bm.v(8192)) {
                    if (ab.e + 8 < 16) {
                        ab.e += 8;
                    }
                } else if (bm.v(16384)) {
                    ab.e = --ab.e < 0 ? 0 : ab.e;
                } else if (bm.v(32768)) {
                    ab.e = ++ab.e >= 16 ? 15 : ab.e;
                } else if (bm.v(65536) || bm.dB) {
                    ab.f = (byte)2;
                    ab.q = 0;
                    bm.dB = false;
                    bm.X = 3;
                    if (ab.g == 1) {
                        bm.aA.h();
                        ab.d = 29;
                        break;
                    }
                } else if (bm.v(262144)) {
                    bm.X = 3;
                    ab.c(1);
                    ab.p = 0;
                }
                if (ab.m) {
                    bm.c(565 + ab.e / 8 * 29 + 1 - 3, 70 + ab.e % 8 * 29 + 1 - 2);
                } else {
                    bm.c(565 + ab.e / 8 * 38 + 1 - 3, 41 + ab.e % 8 * 35 + 1 - 2);
                }
                ** GOTO lbl244
lbl147:
                // 1 sources

                if (ab.f != 1) ** GOTO lbl172
                ab.d = 87;
                if (bm.v(4096)) {
                    ab.g = --ab.g < 0 ? 2 : ab.g;
                    bm.X = 3;
                } else if (bm.v(8192)) {
                    ab.g = ++ab.g > 2 ? 0 : ab.g;
                    bm.X = 3;
                } else if (bm.v(32768)) {
                    ab.f = (byte)2;
                    ab.q = 0;
                    if (ab.g == 1) {
                        bm.aA.h();
                        ab.d = 29;
                        break;
                    }
                } else if (bm.v(262144)) {
                    ab.f = 0;
                    ab.q = ab.e;
                    bm.X = 3;
                    break;
                }
                bm.c(233 - ab.g - (ab.d >> 1) + ab.g * 87, 205);
                ** GOTO lbl244
lbl172:
                // 1 sources

                if (ab.f != 2) ** GOTO lbl244
                ab.d = 29;
                if (bm.v(16384) && ab.q < 14) {
                    ab.f = ab.m != false ? 0 : 1;
                    bm.X = 3;
                    break;
                }
                if (!bm.v(65536) && !bm.dB) ** GOTO lbl218
                var0_16 = 0;
                if (ab.g != 1) ** GOTO lbl194
                var1_5 = bm.aA.b(ab.q);
                if (var1_5 == null) ** GOTO lbl212
                v2 = var1_5;
                var2_9 = v2;
                if (!(v2 != null && var2_9.a((short)1) == false)) ** GOTO lbl-1000
                v3 = var1_5;
                var2_9 = v3;
                if (v3 != null && var2_9.a((short)128) == false) {
                    ab.c[ab.e / 8][ab.e % 8] = 0x40000000 | var1_5.c << 16 | var1_5.d;
                    var0_16 = 1;
                } else lbl-1000:
                // 2 sources

                {
                    bm.d(bm.A(1196));
                    break;
lbl194:
                    // 1 sources

                    if (ab.g == 0) {
                        if (ab.q >= 0 && ab.q < i.b.size() && (var1_6 = (i)i.b.elementAt(ab.q)) != null) {
                            if (ab.m) {
                                if (var1_6.c == 3001 || var1_6.e()) {
                                    bm.d(bm.A(1197));
                                    break;
                                }
                                ab.n[ab.e] = -2147483648 | var1_6.c;
                                for (var2_11 = 0; var2_11 < ab.n.length; ++var2_11) {
                                    if (var2_11 == ab.e || ab.n[var2_11] != ab.n[ab.e]) continue;
                                    ab.n[var2_11] = 0;
                                }
                            } else {
                                ab.c[ab.e / 8][ab.e % 8] = -2147483648 | var1_6.c;
                            }
                            var0_16 = 1;
                        }
                    } else if (ab.g == 2 && ab.q >= 0 && ab.q < ab.l.length && ab.l[ab.q] >= 0) {
                        ab.c[ab.e / 8][ab.e % 8] = 0x20000000 | ab.l[ab.q];
                        var0_16 = 1;
                    }
                }
lbl212:
                // 6 sources

                if (var0_16 != 0) {
                    ab.f = 0;
                    ab.q = ab.e;
                    bm.X = 3;
                    break;
                }
                ** GOTO lbl223
lbl218:
                // 1 sources

                if (bm.v(262144)) {
                    ab.f = 0;
                    ab.q = ab.e;
                    bm.X = 3;
                    break;
                }
lbl223:
                // 3 sources

                if (ab.g == 1) {
                    bm.aA.i();
                    ab.q = bm.aA.q;
                } else {
                    if (bm.v(16384)) {
                        if ((ab.q -= 14) < 0) {
                            ab.q = 0;
                        }
                    } else if (bm.v(32768)) {
                        bm.X = 3;
                        if ((ab.q += 14) >= 28) {
                            ab.q -= 14;
                        }
                    } else if (bm.v(4096)) {
                        if (--ab.q < 0) {
                            ab.q = 0;
                        }
                    } else if (bm.v(8192) && ++ab.q >= 28) {
                        ab.q = 27;
                    }
                    bm.c(119 + ab.q % 14 * 29 + 1 - 3, 238 + ab.q / 14 * 29 + 1 - 2);
                }
lbl244:
                // 6 sources

                bm.dB = false;
lbl245:
                // 6 sources

                return;
            }
            case 1: {
                var0_18 = ab.r.length;
                var1_7 = 0;
                var2_12 = var0_18;
                var2_12 = bm.f(var0_18, 0);
                if (var2_12 != -1) {
                    if (var2_12 == ab.p) {
                        bm.dB = true;
                    } else {
                        ab.p = var2_12;
                        bm.X = 3;
                    }
                }
                var2_12 = var0_18;
                var1_7 = var2_12 - 1;
                if (bm.v(16384)) {
                    v4 = ab.p = ab.p - 1 < 0 ? var1_7 : ab.p - 1;
                }
                if (bm.v(32768)) {
                    v5 = ab.p = ab.p + 1 > var1_7 ? 0 : ab.p + 1;
                }
                if (bm.v(196608)) {
                    if (ab.p == 0) {
                        if (ab.m) {
                            i.h();
                            bm.bn.a(ab.n);
                        } else {
                            bm.M();
                            bm.bn.a(ab.c);
                        }
                        bm.H();
                    } else {
                        if (!ab.m) {
                            for (var1_7 = 0; var1_7 < 8; ++var1_7) {
                                ab.c[0][var1_7] = ab.o[0][var1_7];
                                ab.c[1][var1_7] = ab.o[1][var1_7];
                            }
                        }
                        bm.X = 3;
                        bm.H();
                    }
                } else if (bm.v(262144)) {
                    ab.p = 0;
                    ab.c(0);
                }
                bm.dB = false;
                return;
            }
            case 2: {
                cn.b(false);
                if (!bm.v(262144) && !bm.v(196608) && !bm.ak()) break;
                ab.c(0);
                return;
            }
            case 3: 
            case 4: {
                if (!bm.v(262144) && !bm.v(196608) && !bm.ak()) break;
                ab.c(0);
            }
        }
    }

    public static void a(Graphics graphics, int[] nArray, int n2, int n3, int n4, int[][] nArray2, int n5, int n6, boolean bl2) {
        int n7;
        long l2 = bm.m();
        for (n7 = 0; n7 < 8; ++n7) {
            bm.D.a(graphics, 191, n2 - 6, n3 + 3 + n7 * n4, 0);
        }
        for (n7 = 0; n7 < 8; ++n7) {
            if (nArray[n7] == -1) continue;
            graphics.setColor(0xFFFFFF);
            long l3 = 0L;
            long l4 = 0L;
            if ((nArray[n7] & Integer.MIN_VALUE) != 0) {
                n5 = nArray[n7] & 0xFFFFFFF;
                i i2 = i.a(n5);
                if (i2 != null) {
                    i2.a(graphics, n2, n3 + n7 * n4 + 9, false);
                    if (i2.j()) {
                        l3 = i2.j;
                        l4 = i2.m;
                    }
                }
            } else if ((nArray[n7] & 0x40000000) != 0) {
                n5 = nArray[n7] >> 16 & 0xFFF;
                n6 = nArray[n7] & 0xFFFF;
                n n8 = bm.aA.b(n5);
                if (n8 == null || n6 != n8.d) {
                    n8 = bm.aA.c(n6);
                }
                if (n8 != null) {
                    boolean bl3 = true;
                    if (n8.g == 1) {
                        bl3 = false;
                    }
                    n6 = bm.aA.d(n6);
                    n8.a(graphics, n2, n3 + n7 * n4 + 9, n6, false, bl3);
                    l3 = n8.r;
                    l4 = n8.w;
                }
            } else if ((nArray[n7] & 0x20000000) != 0) {
                n5 = 0;
                n6 = 0;
                n5 = n2;
                n6 = n3 + n7 * n4 + 9;
                bm.a(ck.b(25), graphics, b[nArray[n7] & 0xFFFFFFF], n5, n6, 0);
                if ((nArray[n7] & 0xFFFFFFF) == 14 && !bm.t || (nArray[n7] & 0xFFFFFFF) == 17 && !bm.u) {
                    l3 = 100000L;
                    l4 = l2;
                } else if ((nArray[n7] & 0xFFFFFFF) == 12) {
                    switch (cr.as) {
                        case 0: {
                            l3 = 0L;
                            l4 = l2;
                            break;
                        }
                        case 1: {
                            l3 = 4L;
                            l4 = l2 - 2L;
                            break;
                        }
                        case 2: {
                            l3 = 100000L;
                            l4 = l2;
                        }
                    }
                } else if ((nArray[n7] & 0xFFFFFFF) == 13) {
                    switch (a.b) {
                        case 0: {
                            l3 = 100000L;
                            l4 = l2;
                            break;
                        }
                        case 1: {
                            l3 = 4L;
                            l4 = l2 - 2L;
                            break;
                        }
                        case 2: {
                            l3 = 0L;
                            l4 = l2;
                        }
                    }
                }
            }
            if (!bl2) continue;
            ab.a(graphics, n2, n3 + n7 * n4 + 9, l2, l4, l3);
        }
    }

    public static void a(Graphics graphics, int n2, int n3, long l2, long l3, long l4) {
        if (l4 <= 0L) {
            return;
        }
        int n4 = (int)(l2 - l3);
        if ((long)n4 > l4) {
            return;
        }
        if ((n4 = (int)(25L * (l4 - (long)n4) / l4)) > 0 && n4 <= 25) {
            aq.e(Integer.MIN_VALUE);
            aq.b(graphics, n2, n3 + (25 - n4), 25, n4);
        }
    }

    public static boolean b(int n2) {
        if (s != null) {
            short s2 = (short)bm.aA.d(n2);
            n n3 = s.c(n2);
            if (n3 != null) {
                n3.p = s2;
            }
        }
        return true;
    }

    static {
        c = new int[2][8];
        o = new int[2][8];
        p = 0;
        r = new String[]{bm.A(1191), bm.A(1192)};
    }
}

