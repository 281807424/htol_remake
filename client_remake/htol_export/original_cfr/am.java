/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class am {
    public static boolean a;
    public static boolean b;
    public static byte c;
    public static byte d;
    private static int r;
    private static int s;
    static byte[] e;
    private static int t;
    public static ca f;
    public static cr[] g;
    public static int[] h;
    public static int i;
    public static int j;
    private static int u;
    static String k;
    private static int v;
    private static int[] w;
    private static int[] x;
    private static String y;
    private static int[] z;
    private static int[] A;
    static int l;
    public static byte m;
    public static boolean n;
    public static int o;
    public static ca p;
    public static boolean q;

    public am() {
        int[][] nArrayArray = new int[][]{{23, 47, 192, 19, 9919524, 9919524}, {23, 97, 192, 19, 9919524, 9919524}};
        int[][] nArrayArray2 = new int[][]{{30, 62, 57, 106, 16577506, 11637914}, {91, 62, 57, 106, 16577506, 11637914}, {152, 62, 57, 106, 16577506, 11637914}, {38, 181, 161, 18, 16577506, 0xD7D7D7}, {38, 204, 161, 18, 16577506, 0xD7D7D7}, {38, 227, 161, 18, 16577506, 0xD7D7D7}, {38, 251, 161, 18, 16577506, 0xD7D7D7}};
        int[][] nArrayArray3 = new int[][]{{75, 150, 90, 25, 16642254, 16642254}, {75, 175, 90, 25, 16642254, 16642254}, {75, 200, 90, 25, 16642254, 16642254}};
    }

    public static void a(boolean bl2) {
        bm.t = false;
        bd.a = false;
        bt.g();
        bt.h();
        bm.bd.removeAllElements();
        cv.H.removeAllElements();
        t.c();
        if (bl2) {
            s = j;
            al.a(0);
        }
    }

    public static void a(int n2) {
        v = n2;
        bm.X = 3;
    }

    public static int a() {
        return v;
    }

    public static void a(cr cr2, int n2) {
        cs.a[1] = cr2.C;
        af.a(true, 5);
        cr cr3 = cr2;
        bm.E(7);
        bm.bn.b(cr3.C, -1);
        bh.y.addElement(cr2);
        bm.cb = cr2;
        if (bm.bq != null) {
            bm.bq.removeAllElements();
        }
        bm.bn.M();
        bm.bn.N();
    }

    /*
     * Unable to fully structure code
     */
    public static void b() {
        switch (am.v) {
            case 4: {
                if (!am.a) {
                    bm.E(7);
                    bm.bn.c(0);
                    am.a = true;
                }
                if (bm.v(262144)) {
                    am.c();
                }
                return;
            }
            case 0: {
                var0 = new int[3][4];
                var0[0][0] = d.f;
                var0[2][1] = 60;
                var0[1][1] = 60;
                var0[0][1] = 60;
                var0[2][2] = 102;
                var0[1][2] = 102;
                var0[0][2] = 102;
                var0[2][3] = 120;
                var0[1][3] = 120;
                var0[0][3] = 120;
                var0[1][0] = var0[0][0] + 102 + 30;
                var0[2][0] = var0[1][0] + 102 + 30;
                bm.dB = false;
                for (var1_7 = 0; var1_7 < 3; ++var1_7) {
                    if (!bm.a(var0[var1_7])) continue;
                    if (am.s == var1_7) {
                        bm.dB = true;
                    } else {
                        am.s = var1_7;
                    }
                    bm.X = 3;
                    break;
                }
                if (bm.v(4112)) {
                    if (am.s > 0) {
                        --am.s;
                        break;
                    }
                    am.s = 2;
                    break;
                }
                if (bm.v(8256)) {
                    if (am.s < 2) {
                        ++am.s;
                        break;
                    }
                    am.s = 0;
                    break;
                }
                if (!bm.v(196608) && !bm.dB && !bm.am()) ** GOTO lbl58
                if (am.g[am.s].C != -1) {
                    am.j = am.s;
                    am.a(1);
                    am.r = 0;
                    af.a(false, 2);
                    if (am.e[am.s] != 0) {
                        break;
                    }
                } else {
                    am.r = 0;
                    am.a(9);
                    break;
lbl58:
                    // 1 sources

                    if (bm.v(262144) || bm.an()) {
                        am.c();
                    }
                }
                return;
            }
            case 1: {
                var0_1 = am.w.length;
                am.a(var0_1, 0);
                am.c(var0_1);
                if (bm.v(196608)) {
                    if (am.r == 0) {
                        bm.ad();
                        am.a = false;
                        am.a(7);
                        break;
                    }
                    if (am.r == 1) {
                        am.a(10);
                        bm.cn = 1;
                        am.r = 0;
                        break;
                    }
                    if (am.r == 2) {
                        am.a(0);
                        break;
                    }
                } else if (bm.v(262144)) {
                    am.r = 0;
                    am.a(0);
                }
                return;
            }
            case 9: {
                var0_2 = am.x.length;
                am.a(var0_2, 0);
                am.c(var0_2);
                if (bm.v(196608)) {
                    if (am.r == 0) {
                        am.d = 0;
                        am.t = 1;
                        am.u = 0;
                        am.a(5);
                        am.r = 0;
                        am.d();
                        break;
                    }
                    if (am.r == 1) {
                        am.a(0);
                        am.r = 0;
                        break;
                    }
                } else if (bm.v(262144)) {
                    am.a(0);
                    am.r = 0;
                }
                return;
            }
            case 5: {
                var0_3 = new int[2][4];
                var1_8 = new int[3][4];
                var2_11 = new int[4];
                var0_3[0][0] = 305;
                var0_3[1][1] = 175;
                var0_3[0][1] = 175;
                var0_3[1][2] = 140;
                var0_3[0][2] = 140;
                var0_3[1][3] = 35;
                var0_3[0][3] = 35;
                var0_3[1][0] = var0_3[0][0] + 140;
                var1_8[0][0] = 305;
                var1_8[2][1] = 89;
                var1_8[1][1] = 89;
                var1_8[0][1] = 89;
                var1_8[2][2] = 93;
                var1_8[1][2] = 93;
                var1_8[0][2] = 93;
                var1_8[2][3] = 35;
                var1_8[1][3] = 35;
                var1_8[0][3] = 35;
                var1_8[1][0] = var1_8[0][0] + 93;
                var1_8[2][0] = var1_8[1][0] + 93;
                var2_11[0] = 360;
                var2_11[1] = 220;
                var2_11[2] = 222;
                var2_11[3] = 32;
                bm.dB = false;
                for (var3_14 = 0; var3_14 < 2; ++var3_14) {
                    if (!bm.a(var0_3[var3_14])) continue;
                    am.c = (byte)var3_14;
                    am.d();
                    bm.X = 3;
                    am.t = 2;
                    ** GOTO lbl163
                }
                for (var3_14 = 0; var3_14 < 3; var3_14 = (int)((byte)(var3_14 + 1))) {
                    if (!bm.a(var1_8[var3_14])) continue;
                    if (am.u != var3_14) {
                        am.u = var3_14;
                        am.d = (byte)var3_14;
                        am.d();
                        ay.a = -35;
                    }
                    bm.X = 3;
                    am.t = 1;
                    ** GOTO lbl163
                }
                if (bm.a(var2_11)) {
                    if (am.t == 3) {
                        bm.dB = true;
                    } else {
                        if (am.u <= 0 || am.u > 2) {
                            am.u = 0;
                            am.d = 0;
                            am.d();
                            ay.a = -35;
                        }
                        bm.X = 3;
                        am.t = 3;
                    }
                }
lbl163:
                // 6 sources

                if (bm.v(16388)) {
                    if (am.t > 1) {
                        --am.t;
                    }
                    bm.X = 3;
                } else if (bm.v(33024)) {
                    if (am.t < 3) {
                        ++am.t;
                    }
                    bm.X = 3;
                }
                if (am.t != 0) {
                    if (am.t == 2) {
                        if (bm.v(4112)) {
                            am.c = (byte)(am.c == 0);
                            am.d();
                        } else if (bm.v(8256)) {
                            am.c = (byte)(am.c == 0);
                            am.d();
                        }
                    } else if (am.t == 1) {
                        if (bm.v(4112)) {
                            if (am.u > 0) {
                                --am.u;
                                am.d = (byte)(am.d - 1);
                            }
                            am.d();
                            ay.a = -35;
                            bm.X = 3;
                        } else if (bm.v(8256)) {
                            if (am.u < 2) {
                                ++am.u;
                                am.d = (byte)(am.d + 1);
                            }
                            am.d();
                            ay.a = -35;
                            bm.X = 3;
                        }
                    } else if (am.t == 3) {
                        if (bm.v(65536) || bm.dB) {
                            bc.a((byte)10);
                            break;
                        }
                        if (am.k != null) {
                            if (am.k.length() > 6) {
                                am.k = null;
                                am.y = bm.A(300);
                                bm.d(am.y);
                            } else if (am.k.indexOf(94) != -1 || am.k.indexOf(32) != -1) {
                                am.k = null;
                                am.y = bm.A(301);
                                bm.d(am.y);
                            }
                        }
                    }
                }
                if (bm.v(131072) || bm.am()) {
                    if (am.k != null) {
                        bm.ad();
                        am.a = false;
                        bm.bn.c();
                    } else {
                        am.y = bm.A(302);
                        bm.d(am.y);
                    }
                }
                if (bm.v(262144) || bm.an()) {
                    am.k = null;
                    am.a(0);
                }
                return;
            }
            case 10: {
                var0_4 = bm.av.length;
                bm.i(var0_4, 0);
                bm.o(var0_4);
                if (bm.v(196608)) {
                    if (bm.cn == 0) {
                        bm.bn.e(am.g[am.s].C);
                        am.a = false;
                        am.e[am.s] = 1;
                        af.a(true, 2);
                        am.a(0);
                        bm.cn = 0;
                        bm.ad();
                        break;
                    }
                    if (bm.cn == 1) {
                        am.a(0);
                        bm.cn = 0;
                        break;
                    }
                } else if (bm.v(262144)) {
                    am.a(0);
                    am.r = 0;
                    bm.cn = 0;
                }
                return;
            }
            case 6: {
                if (!am.a) {
                    bm.bn.e(am.g[am.j].C);
                    am.a = true;
                }
                return;
            }
            case 18: {
                am.m = am.g[am.j].t;
                if (bm.v(262144) || bm.an()) {
                    am.a(0);
                }
                return;
            }
            case 19: {
                var0_5 = bm.cm;
                if (bm.v(16388)) {
                    am.m = am.m - 1 < 0 ? (byte)(var0_5 - 1) : (byte)(am.m - 1);
                } else if (bm.v(33024)) {
                    v0 = am.m = am.m + 1 > var0_5 - 1 ? 0 : (byte)(am.m + 1);
                }
                if (bm.v(262144) || bm.an()) {
                    am.a(0);
                    am.k = null;
                    break;
                }
                if (bm.v(196608) || bm.dB || bm.am()) {
                    bm.bn.a((int)am.c, am.u, (int)bm.w[am.m], am.s, am.k);
                    am.k = null;
                    bm.E(5);
                    break;
                }
                var2_12 = false;
                var1_9 = var0_5;
                var3_15 = (byte)bm.f(var1_9, 0);
                if (var3_15 != -1) {
                    if (am.m == var3_15) {
                        bm.dB = true;
                    } else {
                        am.m = var3_15;
                        bm.X = 3;
                    }
                }
                if (bm.dB) {
                    bm.bn.a((int)am.c, am.u, (int)bm.w[am.m], am.s, am.k);
                    am.k = null;
                    bm.E(5);
                }
                return;
            }
            case 7: {
                if (!am.a) {
                    am.a(am.g[am.j], -1);
                    am.a = true;
                    am.a(0);
                }
                return;
            }
            case 8: {
                return;
            }
            case 11: {
                if (bm.E()) {
                    am.a(0);
                }
            }
            case 13: {
                if (bm.v(262144)) {
                    am.c();
                    break;
                }
                if (bm.bn != null) ** GOTO lbl339
                var1_10 = null;
                var1_10 = bm.b(9, 318, 90, 234, 23, 0);
                bm.dB = false;
                var2_13 = 0;
                if (bm.bt.size() > 9) {
                    var2_13 = am.l <= 8 ? 0 : am.l - 9 + 1;
                }
                for (var3_16 = 0; var3_16 < 9; ++var3_16) {
                    if (!bm.a(var1_10[var3_16])) continue;
                    if (am.l == var3_16 + var2_13) {
                        bm.dB = true;
                    } else if (var3_16 + var2_13 < bm.bt.size() && bm.bt.elementAt(var3_16 + var2_13) != null) {
                        am.l = var3_16 + var2_13;
                    }
                    bm.X = 3;
                    ** GOTO lbl327
                }
                if (bm.e(290, 40, 60, 50)) {
                    if (am.l > 0 && bm.bt.elementAt(--am.l) == null) {
                        --am.l;
                    }
                } else if (bm.e(290, 300, 60, 50) && am.l < bm.bt.size() - 1 && bm.bt.elementAt(++am.l) == null) {
                    ++am.l;
                }
lbl327:
                // 5 sources

                if (bm.v(16384) && am.l > 0 && bm.bt.elementAt(--am.l) == null) {
                    --am.l;
                }
                if (bm.v(32768) && am.l < bm.bt.size() - 1 && bm.bt.elementAt(++am.l) == null) {
                    ++am.l;
                }
                if ((bm.v(196608) || bm.dB || bm.am()) && (var0_6 = (ca)bm.bt.elementAt(am.l)) != null) {
                    if (var0_6.a()) {
                        bm.d(bm.A(309));
                        break;
                    }
                    af.a(true, 5);
                    am.a(var0_6);
                    am.b = false;
                }
lbl339:
                // 4 sources

                return;
            }
            case 15: {
                am.e();
                return;
            }
            case 16: {
                am.e();
            }
        }
    }

    private static void e() {
        if (bm.v(393216)) {
            am.a(13);
            bm.aa();
        }
    }

    private static void c(int n2) {
        --n2;
        if (bm.v(16388)) {
            r = r - 1 < 0 ? n2 : r - 1;
            return;
        }
        if (bm.v(33024)) {
            r = r + 1 > n2 ? 0 : r + 1;
        }
    }

    static void a(Graphics graphics) {
        switch (v) {
            case 1: {
                am.b(graphics);
                am.a(graphics, w);
                return;
            }
            case 5: {
                int n2;
                int n3;
                int n4;
                int n5;
                Graphics graphics2 = bm.bi;
                if (t == 3 || k != null) {
                    ay.a(graphics2, true, 2, 3, bm.d(0, 144));
                } else {
                    ay.a(graphics2, true, -1, 3, bm.d(0, 144));
                }
                bm.D.a(graphics2, 6, 305, 50, 0);
                bm.D.a(graphics2, 6, 305, 135, 0);
                bm.D.a(graphics2, 169, 0, 0, 0);
                bz.a(graphics2, bm.A(308), 305 + (bm.D.f(6) >> 1), 143, 1);
                bz.a(graphics2, bm.d(0, 139), 305 + (bm.D.f(6) >> 1), 58, 1);
                bm.D.j(0);
                int n6 = 145;
                if (t == 2) {
                    n6 = 145 + bm.bj % 2;
                }
                int[] nArray = new int[6];
                bm.D.a(nArray, n6, 0, 0, 0);
                nArray[2] = bm.D.f(n6);
                nArray[3] = bm.D.g(n6);
                nArray[4] = (nArray[2] >> 1) + nArray[0];
                nArray[5] = (nArray[3] >> 1) + nArray[1];
                bm.D.a(graphics2, n6, 330 + c * 145, 179, 0);
                n6 = 143;
                if (t == 1) {
                    n6 = 143 + bm.bj % 2;
                }
                for (n5 = 0; n5 < 2; ++n5) {
                    n4 = bm.D.h(n5 + 125);
                    n3 = bm.D.f(n5 + 125) >> 1;
                    n2 = bm.D.g(n5 + 125) >> 1;
                    bm.D.a(graphics2, n5 + 125, 330 + n5 * 145 + nArray[4] - n3 - n4, 179 + nArray[5] - n2, 0);
                }
                bm.D.a(nArray, n6, 0, 0, 0);
                nArray[2] = bm.D.f(n6);
                nArray[3] = bm.D.g(n6);
                nArray[4] = (nArray[2] >> 1) + nArray[0];
                nArray[5] = (nArray[3] >> 1) + nArray[1];
                bm.D.a(graphics2, n6, 290 + d * 90, 91, 0);
                for (n5 = 0; n5 < 3; ++n5) {
                    n4 = bm.D.h(n5 + 120);
                    n3 = bm.D.f(n5 + 120) >> 1;
                    n2 = bm.D.g(n5 + 120) >> 1;
                    bm.D.a(graphics2, n5 + 120, 290 + n5 * 90 + nArray[4] - n3 - n4, 91 + nArray[5] - n2, 0);
                }
                String string = bm.d(0, 257);
                if (d == 0) {
                    string = bm.d(0, 257);
                } else if (d == 1) {
                    string = bm.d(0, 258);
                } else if (d == 2) {
                    string = bm.d(0, 259);
                }
                bz.a(graphics, 16179625, 0xFFFFFF, -1);
                ay.a(graphics, string, bf.a(string, 270), 310, 260, 280, 40, 0, 2, 0, false);
                String string2 = "";
                if (k != null) {
                    string2 = "^x" + k + "^t";
                }
                ay.a(graphics, 305, 220, 260, 32, 60, bm.d(0, 249), string2, t == 3);
                return;
            }
            case 10: {
                am.b(graphics);
                String[] stringArray = new String[]{bm.d(0, 2), bm.d(0, 39)};
                ay.a(graphics, bm.d(0, 153) + bm.d(0, 27), bm.av, bm.cn, 250, true, bm.d(0, 162), true);
                return;
            }
            case 9: {
                am.b(graphics);
                am.a(graphics, x);
                return;
            }
            case 11: {
                am.b(graphics);
                bm.a(graphics, bm.d(0, 162), bm.d(0, 255));
                return;
            }
            case 13: {
                am.c(graphics);
                return;
            }
            case 15: {
                bm.d(bm.A(303));
                return;
            }
            case 16: {
                bm.d(bm.A(304));
                return;
            }
            case 19: {
                am.d(graphics);
                return;
            }
        }
        am.b(graphics);
    }

    private static void a(Graphics graphics, int[] nArray) {
        ay.a(graphics, 0, nArray, 180, r, false, true, false);
    }

    private static void b(Graphics graphics) {
        Object[] objectArray;
        int n2;
        if (bm.X == 3) {
            ay.a(bm.W, true, 2, 3, bm.d(0, 143));
            bm.D.a(bm.W, 11, 0, 0, 0);
            ay.a(bm.W, 185, 185, 270, 33, "");
            bm.D.a(bm.W, 4, 0, 0, 0);
            bm.X = 0;
        }
        graphics.drawImage(bm.V, 0, 0, 0);
        if (b) {
            for (n2 = 0; n2 < 3; ++n2) {
                if (am.g[n2].C != -1) {
                    g[n2].a(true);
                    g[n2].a(graphics, 143 + n2 * 130 + 45, 147, true);
                } else {
                    bz.a(graphics, -597591, -13032703, 4);
                    objectArray = bf.a(bm.d(0, 144), 86);
                    bf.a(graphics, bm.d(0, 144), objectArray, 143 + n2 * 130 + 5, 112, 0, -1, 0, false);
                }
                if (s != n2) continue;
                graphics.setClip(0, 0, 640, 360);
                bm.D.a(141 + bm.bj % 2, 143 + n2 * 130, 52, 0);
            }
        }
        graphics.setClip(0, 0, 640, 360);
        bz.a(graphics, 0xFFFFFF, 0, -1);
        if (am.g[am.s].E != null) {
            ay.b(graphics, am.g[am.s].E, 320, 205 - (graphics.getFont().getHeight() >> 1), -597591, -13032703);
        }
        objectArray = new String[]{bm.A(306), bm.d(0, 145), bm.d(0, 146), bm.A(307)};
        String[] stringArray = new String[]{am.g[am.s].q, String.valueOf(am.g[am.s].U), bm.d(0, 140 + am.g[am.s].u), bn.a[am.g[am.s].v]};
        n2 = ay.a((String[])objectArray);
        for (int i2 = 0; i2 < 4; ++i2) {
            String string = "";
            String string2 = "";
            switch (i2) {
                case 3: {
                    string = bm.A(306);
                    if (am.g[am.s].C == -1) break;
                    string2 = am.g[am.s].q;
                    break;
                }
                case 0: {
                    string = bm.d(0, 145);
                    if (am.g[am.s].C == -1) break;
                    string2 = "" + am.g[am.s].U;
                    break;
                }
                case 1: {
                    string = bm.d(0, 146);
                    if (am.g[am.s].C == -1) break;
                    string2 = bm.d(0, 140 + am.g[am.s].u);
                    break;
                }
                case 2: {
                    string = bm.A(307);
                    if (am.g[am.s].C == -1) break;
                    string2 = bn.a[am.g[am.s].v];
                }
            }
            ay.a(graphics, d.d[i2], d.e[i2], 210, 32, n2, string, string2, false, true);
        }
    }

    public static void c() {
        bm.az();
        r = 0;
        s = 0;
        if (bm.bn != null) {
            bm.bn.a(0, false);
        }
        bm.y(5);
        bm.ae();
        if (bm.bn != null) {
            bm.bn.J();
            bm.bn = null;
        }
    }

    public static void d() {
        byte by2 = c;
        boolean bl2 = false;
        int n2 = u;
        bl2 = true;
        String string = "NB";
        cr cr2 = new cr();
        cr2.a(string, 1, (byte)n2, (byte)0);
        cr2.ai = by2;
        cr2.l(0, 6);
        cr2.l(8, 1312);
    }

    private static void c(Graphics graphics) {
        int n2;
        Graphics graphics2 = graphics;
        if (bm.X == 3) {
            ay.a(bm.W, true, 2, 3, bm.d(0, 150));
            ay.a(graphics2, 640 - bm.D.f(6) >> 1, 50, 270, 33, "");
            bm.D.a(bm.W, 4, 0, 0, 0);
            ay.a(bm.W, 200, 90, 240, 210, false, null, true);
            bm.X = 0;
        }
        graphics2.drawImage(bm.V, 0, 0, 0);
        bz.a(graphics, -597591, -13032703, 4);
        bz.a(graphics, bm.B(236), 320, 57, 1);
        int n3 = 0;
        if (bm.bt.size() > 9) {
            n3 = l <= 8 ? 0 : l - 9 + 1;
        }
        for (n2 = 0; n2 < 9 && n2 + n3 < bm.bt.size(); ++n2) {
            if (bm.bt.elementAt(n2 + n3) == null) continue;
            if (n2 + n3 == l) {
                ay.a(graphics, 203, 92 + n2 * 23, 234, 23, true);
                bz.a(graphics, 15880461, -13032703, 4);
            } else {
                bz.a(graphics, -597591, -13032703, 4);
            }
            byte by2 = ((ca)bm.bt.elementAt((int)(n2 + n3))).f;
            String string = ((ca)bm.bt.elementAt((int)(n2 + n3))).a;
            String string2 = bm.B(A[by2]);
            int n4 = bz.a(string2);
            String string3 = bm.A(1064);
            boolean bl2 = string3.length() < string.length() ? string3.equals(string.substring(0, string3.length())) : false;
            if (bl2) {
                bz.a(graphics, string.substring(0, string3.length()), 206, 94 + n2 * 23);
                bz.a(graphics, string.substring(string3.length(), string.length()), 206, 94 + 23 * (n2 + 1));
            } else {
                bz.a(graphics, string, 206, 94 + n2 * 23);
            }
            bz.a(graphics, z[by2], -13032703, 4);
            bz.a(graphics, string2, 440 - n4 - 2, 94 + n2 * 23);
        }
        n2 = bm.D.g(68);
        bm.G.c();
        bm.G.a(graphics, 640 - n2 >> 1, 90, 2);
        bm.G.a(graphics, 640 - n2 >> 1, 300, 0);
    }

    private static void d(Graphics graphics) {
        ay.a(bm.W, true, 2, 3, bm.d(0, 150));
        bm.D.a(bm.W, 6, 640 - bm.D.f(6) >> 1, 50, 0);
        bm.D.a(bm.W, 4, 0, 0, 0);
        ay.a(bm.W, 240, 220, 160, 80, false, null, true);
        bm.X = 0;
        graphics.drawImage(bm.V, 0, 0, 0);
        bz.a(graphics, -597591, -13032703, 4);
        bz.a(graphics, bm.A(310), 320, 57, 1);
        String[] stringArray = new String[bm.cm];
        for (int i2 = 0; i2 < bm.cm; ++i2) {
            stringArray[i2] = bn.a[bm.w[i2]];
        }
        ay.a(graphics, stringArray, -20, 180, (int)m, true, false);
        bz.a(graphics, -597591, -13032703, 4);
        String string = bm.d(0, bm.w[m] + 324);
        ay.a(graphics, string, bf.a(string, 150), 245, 225, 150, 70, 0, 4, 0, false);
    }

    public static ca b(int n2) {
        ca ca2;
        int n3;
        if (bm.bt == null) {
            return null;
        }
        for (n3 = 0; n3 < bm.bt.size(); ++n3) {
            ca2 = (ca)bm.bt.elementAt(n3);
            if (ca2 == null || ca2.g != n2) continue;
            return ca2;
        }
        for (n3 = 0; n3 < bm.bu.size(); ++n3) {
            ca2 = (ca)bm.bu.elementAt(n3);
            if (ca2 == null || ca2.g != n2) continue;
            return ca2;
        }
        return null;
    }

    public static void a(ca ca2) {
        if (ca2 == null) {
            return;
        }
        f = ca2;
        bm.bm = "socket://" + am.f.b + ":" + Integer.toString(am.f.c) + "";
        o = ca2.g;
        if (bm.p) {
            bm.bm = q ? "socket://" + am.f.d + ":" + Integer.toString(am.f.e) + "" : "socket://" + am.f.d + ":" + Integer.toString(am.f.e) + "";
        }
        bm.bn = new cc();
        bm.bn.a(bm.bm, true);
        n = true;
        bm.E(7);
    }

    private static void a(int n2, int n3) {
        if ((n2 = bm.f(n2, 0)) != -1) {
            if (r == n2) {
                bm.dB = true;
                return;
            }
            r = n2;
            bm.X = 3;
        }
    }

    static {
        int[] nArray = new int[]{15, 10, 1, 15, 15, 15};
        b = false;
        e = new byte[]{1, 1, 1};
        t = 1;
        g = new cr[]{new cr(), new cr(), new cr()};
        h = new int[]{-1, -1, -1};
        i = 0;
        int[] nArray2 = new int[]{37, 27, 38};
        int[] nArray3 = new int[]{26, 38};
        String[] stringArray = new String[]{bm.d(0, 29), bm.d(0, 29), bm.d(0, 29)};
        int[] nArray4 = new int[]{43, 43, 43};
        v = 0;
        w = new int[]{147, 148, 149};
        x = new int[]{144, 149};
        y = "";
        z = new int[]{0xA50000, 16750080, 48401, 0xFF00F6};
        A = new int[]{278, 279, 280, 281};
        l = 0;
        p = null;
        q = false;
    }
}

