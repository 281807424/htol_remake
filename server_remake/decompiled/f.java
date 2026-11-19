/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import java.util.Vector;
import javax.microedition.lcdui.Graphics;

public final class f {
    private static int[] i = new int[]{169, 170, 172, 171, 39};
    private static int j = 0;
    static int a = 0;
    static int b = 0;
    private static int k = 0;
    static int c = 0;
    private static int l = 0;
    private static t m;
    static String[] d;
    private static String[] n;
    static String[] e;
    public static t f;
    public static byte g;
    public static int h;
    private static int o;
    private static boolean p;

    public f() {
        f.a(0);
    }

    public static void a(int n2) {
        l = n2;
        if (n2 == 6) {
            n2 = 0;
            for (int i2 = 0; i2 < bh.z.size(); ++i2) {
                r r2 = (r)bh.z.elementAt(i2);
                if (r2 == null || r2.h()) continue;
                ++n2;
            }
            bm.j(n2 + t.d.size(), 8);
        }
        bm.X = 3;
    }

    public static void a(Graphics graphics) {
        switch (l) {
            case 0: {
                f.c(graphics);
                return;
            }
            case 1: {
                f.c(graphics);
                Object object = m;
                if (object != null) {
                    object = ((t)object).v;
                    n = new String[((Vector)object).size() + 3];
                    for (int i2 = 0; i2 < ((Vector)object).size(); ++i2) {
                        cd cd2 = (cd)((Vector)object).elementAt(i2);
                        f.n[i2] = cd2.a;
                    }
                    f.n[f.n.length - 1] = bm.d(0, 39);
                    f.n[f.n.length - 2] = bm.d(0, 174);
                    f.n[f.n.length - 3] = bm.d(0, 168);
                    ay.a(graphics, n, 190, j, true, true);
                    bm.a(graphics, n.length);
                }
                return;
            }
            case 2: {
                f.b(graphics);
                return;
            }
            case 4: {
                graphics.setClip(0, 335, 640, 25);
                ay.a(graphics, true, -1, 3, bm.A(806));
                graphics.setClip(0, 0, 640, 360);
                ay.a(graphics, bm.A(807), 150, 60, true, bm.d(0, 162), true);
                return;
            }
            case 5: {
                f.b(graphics);
                bm.a(graphics, bm.d(0, 162), bm.d(0, 177));
                return;
            }
            case 6: {
                Graphics graphics2 = bm.W;
                ay.a(graphics2, true, 2, 3, bm.d(0, 206));
                ay.a(graphics, 2, 3, bm.d(0, 206));
                bz.a(graphics, 16179625, 0xFFFFFF, -1);
                bz.a(graphics, bm.d(0, 207), 320, 71, 1);
                ay.c(graphics, 252, 90, 265, 216, 8);
                graphics.setClip(0, 0, 640, 360);
                int n2 = bm.dY << 3;
                int n3 = t.d.size();
                for (int i3 = n2; i3 < n2 + 8; ++i3) {
                    Object object;
                    if (i3 - n2 == bm.dW) {
                        ay.b(graphics, 252, 90 + bm.dW * 27, 265, 27);
                    }
                    String string = "";
                    int n4 = -1;
                    if (i3 < t.d.size()) {
                        object = (cd)t.d.elementAt(i3);
                        if (object != null) {
                            string = ((cd)object).a == "" ? bm.A(805) : "\u3010" + ((cd)object).a + "\u3011";
                        }
                    } else if (i3 < t.d.size() + bh.z.size() && (object = (r)bh.z.elementAt(i3 - n3)) != null && !((r)object).h()) {
                        string = ((bh)object).E;
                        switch (((r)object).q) {
                            case 0: {
                                bm.D.j(0);
                                n4 = 37;
                                break;
                            }
                            case 1: {
                                bm.D.j(0);
                                n4 = 36;
                                break;
                            }
                            case 2: {
                                bm.D.j(1);
                                n4 = 37;
                                break;
                            }
                            case 4: {
                                bm.D.j(1);
                                n4 = 36;
                            }
                        }
                    }
                    bz.a(graphics, 16179625, 0, -1);
                    bz.a(graphics, string, 312, 90 + (i3 - n2) * 27 + 4);
                    if (n4 != -1) {
                        bm.D.a(graphics, n4, 272, 90 + (i3 - n2) * 27 + 4, 0);
                    }
                    bm.D.j(0);
                }
                bm.a(graphics, 252, 90, 1, true, -1, -1, -1);
                return;
            }
            case 7: {
                f.c(graphics);
                bm.a(graphics, bm.d(0, 162), bm.d(0, 262));
                return;
            }
            case 8: {
                f.c(graphics);
                ay.a(graphics, bm.d(0, 293), bx.i, j, 150, true, bm.d(0, 162), true);
                return;
            }
            case 9: {
                f.d(graphics);
                return;
            }
            case 10: {
                if (bm.X == 3) {
                    graphics.setClip(0, 0, 640, 360);
                    ay.a(bm.W, true, 2, 3, bm.A(837));
                    bm.a(bm.W, t.c.d);
                    ay.a(bm.W, 167, 90, 305, 235, false, "", false);
                    bm.X = 0;
                    bm.A(834);
                    Object var1_3 = null;
                    String cfr_ignored_0 = "" + t.c.c;
                    String string = "^g" + bm.A(835) + "^g";
                    int n5 = t.c.b & 0xFFFF;
                    int n6 = t.c.b >> 16;
                    String string2 = t.a(n5, true);
                    if (n6 == 1) {
                        string = "^r" + t.c.c + bm.A(838) + "^r";
                    }
                    bm.W.setColor(-597591);
                    bz.a(bm.W, bm.A(839) + " " + t.c.d, 172, 95, 0);
                    bz.a(bm.W, bm.A(840) + " " + string2, 172, 114, 0);
                    bz.a(bm.W, bm.A(841) + " " + string, 172, 133, 0);
                    bz.a(bm.W, "NPC: " + t.c.e, 172, 152, 0);
                    bz.a(bm.W, bm.A(842) + " " + t.c.f, 172, 171, 0);
                    bz.a(bm.W, bm.A(843), 172, 190, 0);
                    int n7 = 5;
                    int n8 = bz.a(bm.A(843)) + 8;
                    if (t.c.l != 0) {
                        ++n7;
                        bz.a(bm.W, bm.A(660) + t.c.l, n8 + 167, 190, 0);
                    }
                    if (t.c.j != 0) {
                        bz.a(bm.W, bm.A(845) + t.c.j, n8 + 167, 95 + n7++ * 19, 0);
                    }
                    if (t.c.k != 0) {
                        bz.a(bm.W, bm.A(846) + t.c.k, n8 + 167, 95 + n7++ * 19, 0);
                    }
                    if (t.c.m != 0) {
                        bz.a(bm.W, bm.A(847) + t.c.m, n8 + 167, 95 + n7++ * 19, 0);
                    }
                    if (t.c.n != 0) {
                        bz.a(bm.W, bm.A(661) + t.c.n, n8 + 167, 95 + n7++ * 19, 0);
                    }
                    if (t.c.o != 0) {
                        bz.a(bm.W, bm.A(848) + t.c.o, n8 + 167, 95 + n7 * 19, 0);
                    }
                    bm.X = 0;
                }
                graphics.drawImage(bm.V, 0, 0, 0);
                return;
            }
            case 11: {
                String[] stringArray = new String[]{bm.d(0, 169), bm.d(0, 170), bm.d(0, 172), bm.d(0, 171), bm.d(0, 39)};
                ay.a(graphics, stringArray, 190, j, true, true);
                return;
            }
            case 12: {
                t t2;
                int n9 = 2;
                int n10 = f.f.i;
                if (n10 == 2 || n10 == 3 || n10 == 4) {
                    n9 = -1;
                } else if (n10 == 1) {
                    n9 = 166;
                } else if (n10 == 0) {
                    n9 = 167;
                }
                if (bm.X != 0) {
                    String string = "";
                    if (bm.cf != null) {
                        string = bm.cf.E;
                    }
                    ay.a(bm.W, true, n9, 3, string);
                    bm.a(bm.W, bm.A(851));
                    if (n10 == 1 || n10 == 4 || n10 == 0) {
                        f.a(bm.W, bm.A(852));
                    } else if (n10 == 2 || n10 == 3) {
                        f.a(bm.W, bm.A(689));
                    }
                    bm.X = 0;
                    bm.W.setColor(0);
                }
                graphics.drawImage(bm.V, 0, 0, 0);
                if (n10 == 1 || n10 == 4 || n10 == 0) {
                    f.e(graphics);
                    return;
                }
                if (n10 != 2 && n10 != 3) break;
                Graphics graphics3 = graphics;
                bz.a(graphics3, 16179625, -13870883, -1);
                if (f.f.m != null) {
                    ay.a(graphics3, f.f.m, bf.a(f.f.m, 400), 118, 87, 420, 78, 0, 4, 16, false);
                }
                if ((t2 = t.a(f.f.e)) == null) break;
                ay.a(graphics3, t2.m, bf.a(t2.m, 400), 118, 212, 420, 90, 0, 4, 16, false);
            }
        }
    }

    /*
     * Unable to fully structure code
     */
    public static void a() {
        if (!bm.V()) {
            return;
        }
        block0 : switch (f.l) {
            case 0: {
                bm.ap();
                bm.g(20, 180);
                if (f.a <= 0) {
                    f.a = 0;
                } else if (f.a >= t.a.size()) {
                    f.a = t.a.size() - 1;
                }
                if (bm.v(196608) || bm.dB || bm.am()) {
                    bm.X = 1;
                    f.m = (t)t.a.elementAt(t.a.size() - 1 - f.a);
                    if (f.m != null) {
                        bm.F(0);
                        f.a(1);
                        break;
                    }
                } else {
                    if (bm.v(262144) || bm.an()) {
                        bm.X = 1;
                        bm.H();
                        f.a = 0;
                        break;
                    }
                    if (bm.v(16388)) {
                        if (f.a > 0) {
                            --f.a;
                            ay.a = -15;
                            bm.X = 3;
                            break;
                        }
                    } else if (bm.v(33024)) {
                        if (f.e != null && f.a < f.e.length - 1) {
                            ++f.a;
                            ay.a = -15;
                            bm.X = 3;
                            break;
                        }
                    } else if (bm.v(1)) {
                        bm.bn.l(0);
                    }
                }
                return;
            }
            case 1: {
                var0 = f.n.length;
                f.a(var0, 0);
                bm.g(20, 180);
                f.b(var0);
                if (bm.v(196640)) {
                    bm.F(f.j);
                    if (f.j >= 0 && f.j < var0 - 3) {
                        f.k = f.j;
                        f.j = 0;
                        f.c = 1;
                        f.a(2);
                        break;
                    }
                    if (f.j == var0 - 3) {
                        f.j = 0;
                        f.a(11);
                        break;
                    }
                    if (f.j == var0 - 2) {
                        f.j = 0;
                        f.a(8);
                        break;
                    }
                    if (f.j == var0 - 1) {
                        f.j = 0;
                        f.a(0);
                        break;
                    }
                } else if (bm.v(262144)) {
                    bm.ah();
                    f.j = 0;
                    f.a(0);
                }
                return;
            }
            case 2: {
                bm.aq();
                bm.g(20, 180);
                if (bm.v(16388)) {
                    bm.bF = bm.bF > 0 ? --bm.bF : 2;
                    bm.X = 3;
                    break;
                }
                if (bm.v(33024)) {
                    bm.bF = bm.bF < 2 ? ++bm.bF : 0;
                    bm.X = 3;
                    break;
                }
                if (bm.v(196640) || bm.dB || bm.am()) {
                    bm.F(bm.bF);
                    bm.X = 1;
                    switch (bm.bF) {
                        case 1: {
                            var0_1 = -1;
                            var1_7 = -1;
                            var2_14 = 0;
                            var3_18 = 0;
                            if (f.c == 6) {
                                if (f.b < t.d.size()) {
                                    var4_23 = (cd)t.d.elementAt(f.b);
                                    if (var4_23 != null) {
                                        bm.H();
                                        bm.d(bm.A(808));
                                        break block0;
                                    }
                                } else {
                                    var4_24 = (bh)bh.z.elementAt(f.b - t.d.size());
                                    if (var4_24 != null) {
                                        f.b = 0;
                                        bm.bV = true;
                                        var0_1 = var4_24.F;
                                        var2_14 = var4_24.k;
                                        var3_18 = var4_24.l;
                                    }
                                }
                            } else if (f.c == 1) {
                                if (f.a >= 0 && f.a < t.a.size() && (var4_25 = f.m) != null) {
                                    if (var4_25.h != 5 && (var4_25.h != bm.cV || var4_25.h != bm.cb.v)) {
                                        bm.H();
                                        bm.d(bm.A(809));
                                        break block0;
                                    }
                                    var3_19 = (cd)var4_25.v.elementAt(f.k);
                                    var0_1 = var3_19.d;
                                    var1_7 = var4_25.e;
                                    var2_14 = var3_19.e;
                                    var3_18 = var3_19.f;
                                }
                            } else if (f.c == 10) {
                                var1_7 = t.c.a;
                                var0_1 = t.c.g;
                                var2_14 = t.c.h;
                                var3_18 = t.c.i;
                            }
                            if (var0_1 != -1) {
                                if (var0_1 == 0) {
                                    bm.H();
                                    bm.d(bm.A(810));
                                    break block0;
                                }
                                bm.cb.s();
                                cr.aD = false;
                                bm.s();
                                bm.co = true;
                                bm.bn.a(var1_7, var0_1, var2_14, var3_18);
                                bm.H();
                                if (bm.cp) {
                                    bm.O();
                                }
                                f.a = 0;
                                break;
                            }
                            if (f.c == 6) {
                                f.a(f.c);
                            } else {
                                f.a(0);
                            }
                            bm.d(bm.A(811));
                            break;
                        }
                        case 0: {
                            if (bm.t) {
                                bm.t = false;
                            }
                            if (f.c == 1) {
                                if (f.a < 0 || f.a >= t.a.size() || (var4_26 = f.m) == null) break;
                                if (!bm.D(0x100000) || t.d.size() == 0) {
                                    bm.H();
                                    bm.d(bm.A(812));
                                    break block0;
                                }
                                if (var4_26.h != 5 && var4_26.h != bm.cb.v) {
                                    bm.H();
                                    bm.d(bm.A(690));
                                    break block0;
                                }
                                if (bm.cb.v != bm.cV) {
                                    bm.H();
                                    bm.d(bm.A(813));
                                    break block0;
                                }
                                var3_20 = (cd)var4_26.v.elementAt(f.k);
                                if (var3_20 == null) break;
                                bm.s();
                                f.a(var3_20);
                                f.a = 0;
                                break;
                            }
                            if (f.c == 10) {
                                bm.H();
                                bm.s();
                                cr.E();
                                if (t.c.g == bm.cj) {
                                    bm.a((int)t.c.g, (int)t.c.h, (int)t.c.i);
                                } else {
                                    if (t.c.g == 0) {
                                        bm.d(bm.A(814));
                                    }
                                    bm.bn.a(t.c.g, t.c.h, t.c.i);
                                }
                                cr.aE = 1;
                                break;
                            }
                            if (f.c != 6) break;
                            var4_27 = 0;
                            var3_21 = 0;
                            var0_2 = -1;
                            if (f.b < t.d.size()) {
                                var1_8 = (cd)t.d.elementAt(f.b);
                                if (var1_8 != null) {
                                    f.b = 0;
                                    bm.bV = true;
                                    var0_2 = var1_8.d;
                                    var4_27 = var1_8.e;
                                    var3_21 = var1_8.f;
                                }
                            } else {
                                var1_9 = (bh)bh.z.elementAt(f.b - t.d.size());
                                if (var1_9 != null) {
                                    f.b = 0;
                                    bm.bV = true;
                                    var0_2 = var1_9.F;
                                    var4_27 = var1_9.k;
                                    var3_21 = var1_9.l;
                                    cr.aE = 1;
                                }
                            }
                            bm.H();
                            bm.s();
                            cr.E();
                            bm.a(var0_2, var4_27, var3_21);
                            f.a = 0;
                            break;
                        }
                        case 2: {
                            if (f.c == 1) {
                                f.a(0);
                                break;
                            }
                            if (f.c == 6) {
                                f.a(6);
                                break;
                            }
                            if (f.c != 10) break;
                            f.a(10);
                        }
                    }
                    bm.bF = 0;
                    bm.dB = false;
                    break;
                }
                if (bm.v(262144) || bm.an()) {
                    bm.ah();
                    bm.ah();
                    bm.X = 1;
                    if (f.c == 1) {
                        f.a(0);
                        break;
                    }
                    if (f.c == 6) {
                        f.a(6);
                        break;
                    }
                    if (f.c == 10) {
                        f.a(10);
                    }
                }
                return;
            }
            case 4: {
                f.b();
                return;
            }
            case 5: {
                f.b();
                return;
            }
            case 6: {
                bm.dB = false;
                var3_22 = null;
                var3_22 = bm.b(8, 385, 90, 265, 27, 0);
                for (var4_28 = 0; var4_28 < 8; ++var4_28) {
                    if (!bm.a(var3_22[var4_28])) continue;
                    if (bm.dW == var4_28) {
                        bm.dB = true;
                        continue;
                    }
                    bm.dW = var4_28;
                }
                bm.g(20, 180);
                if (bm.v(196640)) {
                    f.b = (bm.dY << 3) + bm.dW;
                    var0_3 = 0;
                    for (var1_10 = 0; var1_10 < bh.z.size(); ++var1_10) {
                        var2_15 = (r)bh.z.elementAt(var1_10);
                        if (var2_15 == null || var2_15.h()) continue;
                        ++var0_3;
                    }
                    if (f.b < t.d.size() + var0_3) {
                        f.c = 6;
                        f.a(2);
                        break;
                    }
                } else {
                    if (bm.v(262144)) {
                        bm.H();
                        break;
                    }
                    bm.a(false, true);
                }
                return;
            }
            case 7: {
                if (bm.v(262144)) {
                    f.a(0);
                }
                return;
            }
            case 8: {
                f.a(2, 0);
                bm.g(20, 180);
                f.b(2);
                if (!bm.v(196608)) ** GOTO lbl299
                switch (f.j) {
                    case 0: {
                        if (f.a >= 0 && f.a < t.a.size()) {
                            t.a.size();
                            var1_11 = f.m;
                            if (var1_11 != null) {
                                if (!var1_11.f()) {
                                    bm.d(bm.A(850));
                                    break;
                                }
                                bm.bn.k(var1_11.e);
                                break;
                            }
                        }
                        ** GOTO lbl302
                    }
                    case 1: {
                        f.j = 0;
                        f.a(0);
                        break;
                    }
                }
                break;
lbl299:
                // 1 sources

                if (bm.v(262144)) {
                    f.j = 0;
                    f.a(0);
                }
lbl302:
                // 4 sources

                return;
            }
            case 9: {
                bm.g(20, 180);
                var0_4 = bm.b(10, 167, 90, 305, 22);
                if (var0_4 != -1) {
                    if (bm.dW == var0_4) {
                        bm.dB = true;
                    } else {
                        bm.dW = var0_4;
                        bm.X = 3;
                    }
                }
                if (bm.v(262144)) {
                    f.a(0);
                    f.a = 0;
                } else if (bm.v(196640) || bm.v(196608)) {
                    var1_12 = t.b.size();
                    if (f.a >= 0 && f.a < var1_12) {
                        var2_16 = (b)t.b.elementAt(f.a);
                        bm.bn.l(var2_16.a);
                    }
                }
                bm.a(false, true);
                bm.X = 3;
                return;
            }
            case 10: {
                if (bm.v(262144)) {
                    f.a(9);
                    break;
                }
                if (bm.v(196640) || bm.v(196608)) {
                    f.k = f.j;
                    f.j = 0;
                    f.c = 10;
                    f.a(2);
                }
                return;
            }
            case 11: {
                var0_5 = f.i.length;
                f.a(var0_5, 0);
                bm.g(20, 180);
                f.b(var0_5);
                if (bm.v(262144)) {
                    f.a(0);
                    f.j = 0;
                    break;
                }
                if (bm.v(196640)) {
                    var1_13 = t.a.size();
                    if (f.a >= 0 && f.a < var1_13 && (var2_17 = f.m) != null) {
                        switch (f.i[f.j]) {
                            case 169: {
                                if (bm.cb.aj == 0) {
                                    bm.d(bm.A(849));
                                    break;
                                }
                                bm.bn.c((byte)0, var2_17.e, 0);
                                break;
                            }
                            case 170: 
                            case 171: 
                            case 172: {
                                bm.d(bm.d(0, 386));
                                break;
                            }
                            case 39: {
                                f.j = 0;
                            }
                        }
                    }
                    f.a(0);
                }
                return;
            }
            case 12: {
                var0_6 = f.f.i;
                if (var0_6 != 1) ** GOTO lbl366
                if (!bm.v(196608) && !bm.am()) ** GOTO lbl377
                bm.a(f.f.e, var0_6, (int)f.f.h, f.g);
                ** GOTO lbl376
lbl366:
                // 1 sources

                if (var0_6 != 0) ** GOTO lbl370
                if (!bm.v(196608) && !bm.am() || var0_6 == 2) ** GOTO lbl377
                bm.a(f.f.e, var0_6, (int)f.f.h, f.g);
                ** GOTO lbl376
lbl370:
                // 1 sources

                if (var0_6 != 2 && var0_6 != 3) ** GOTO lbl375
                if (bm.v(262144) || bm.an()) {
                    bm.H();
                    return;
                }
                ** GOTO lbl377
lbl375:
                // 1 sources

                if (var0_6 == 4 || var0_6 != 7 || !bm.v(196608) && !bm.am()) ** GOTO lbl377
lbl376:
                // 3 sources

                bm.H();
lbl377:
                // 5 sources

                if (!bm.v(262144) && !bm.an()) break;
                bm.H();
            }
        }
    }

    private static void b() {
        if (bm.v(262144) || bm.al()) {
            f.a(2);
        }
    }

    private static void b(Graphics graphics) {
        graphics.setClip(0, 0, 640, 360);
        int n2 = l;
        if (n2 == 5) {
            ay.a(graphics, -1, 3, bm.d(0, 173), true);
        } else if (n2 == 2) {
            ay.a(graphics, 2, 3, bm.d(0, 173), true);
        } else {
            ay.a(graphics, 2, -1, bm.d(0, 173), true);
        }
        if (!ci.a()) {
            bm.D.a(graphics, 64, 65, 0, 0);
        }
        ay.a(graphics, 170, 48, 430, 32, bm.A(817));
        ay.a(graphics, 170, 182, 430, 32, bm.A(818));
        ay.a(graphics, d, 320, 218, 100, 29, false);
        bz.a(graphics, 16179625, -1, -1);
        ay.a(graphics, bm.d(0, 205), bf.a(bm.d(0, 205), 385), 182, 94, 405, 74, 0, 3, 16, false);
    }

    private static void c(Graphics graphics) {
        graphics.setClip(0, 0, 640, 360);
        int n2 = t.a.size();
        int n3 = l;
        if (n3 == 5 || n3 == 4 || n2 == 0 || n3 == 7) {
            ay.a(graphics, -1, 3, bm.d(0, 165), true);
        } else {
            ay.a(graphics, -1, 3, bm.d(0, 165), true);
        }
        if (ci.a()) {
            ay.a(graphics, 0, 0, 0, 0, 8941627, 14600340, null);
            ay.a(graphics, 0, 0, 0, 0, 8941627, 14600340, null);
            ay.a(graphics, 0, 0, 0, 0, bm.A(689));
            ay.a(graphics, 0, 0, 0, 0, bm.A(820));
        } else {
            bm.D.a(graphics, 64, 65, 1, 0);
            bm.D.a(graphics, 90, 0, 0, 0);
            bz.a(graphics, 0xFFFFFF, 0, 4);
            bz.a(graphics, bm.A(819), 320, 360 - bm.D.g(90) + 3, 1);
            ay.a(graphics, 170, 48, 430, 32, bm.A(820));
            ay.a(graphics, 170, 182, 430, 32, bm.A(689));
        }
        if (n2 == 0) {
            return;
        }
        e = new String[n2];
        for (n3 = 0; n3 < n2; ++n3) {
            t t2 = (t)t.a.elementAt(n3);
            if (t2 == null) continue;
            f.e[n3] = t.a((int)t2.g, false);
            int n4 = n3;
            e[n4] = e[n4] + (t2.e() ? bm.A(193) : bm.A(192));
            int n5 = n3;
            e[n5] = e[n5] + t2.l;
        }
        if (a >= 0 && a < n2) {
            t t3;
            m = t3 = (t)t.a.elementAt(a);
            if (t3 != null) {
                int n6;
                ay.a(graphics, e, 265, 84, 260, 30, false, true, a);
                String string = bm.A(821) + t3.m;
                if (t3.j != 0) {
                    string = string + bm.A(822) + t3.k + "/" + t3.j;
                }
                for (n6 = 0; n6 < t3.t.size(); ++n6) {
                    ap ap2 = (ap)t3.t.elementAt(n6);
                    if (ap2.b == 0) {
                        string = string + bm.A(823) + ap2.a + "^r]\uff1a" + ap2.d + "/" + ap2.e;
                        continue;
                    }
                    if (ap2.b != 1) continue;
                    int n7 = ap2.d >= ap2.e ? ap2.e : ap2.d;
                    string = string + bm.A(824) + ap2.a + "]\uff1a" + n7 + "/" + ap2.e;
                }
                string = string + bm.A(825);
                if (t3.o != 0) {
                    string = string + bm.A(826) + t3.o;
                }
                if (t3.n != 0) {
                    string = string + bm.A(827) + t3.n;
                }
                if (t3.s != 0) {
                    string = string + bm.A(828) + t3.s;
                }
                if (t3.p != 0) {
                    string = string + bm.A(829) + t3.p;
                }
                if (t3.q != 0) {
                    string = string + bm.A(830) + t3.q;
                }
                if (t3.r != 0) {
                    string = string + bm.A(831) + t3.r;
                }
                for (n6 = 0; n6 < t3.w.size(); ++n6) {
                    string = string + "\n" + (String)t3.w.elementAt(n6);
                }
                if (ci.a()) {
                    ay.a(graphics, string, bf.a(string, -38), 9, 0, 0, 0, 0, 0, 16, false);
                    return;
                }
                ay.a(graphics, string, bf.a(string, 420), 176, 225, 430, 88, 0, 4, 16, false);
            }
        }
    }

    private static void d(Graphics graphics) {
        if (bm.X == 3) {
            graphics.setClip(0, 0, 640, 360);
            ay.a(bm.W, true, 2, 3, bm.A(832));
            bm.a(bm.W, bm.A(833));
            ay.a(bm.W, 167, 90, 305, 235, false, "", false);
            bz.a(bm.W, 16179625, 16772818, -1);
            bm.a(bm.W, 0, 0, 0, true, -1, -1, -1);
            int[] nArray = new int[]{1, 0};
            ay.a(bm.W, 167, 90, 305, 220, 10, nArray);
            int n2 = t.b.size();
            int n3 = bm.dY * bm.dZ;
            a = bm.ay();
            for (int i2 = n3; i2 < n3 + 10 && i2 < n2; ++i2) {
                b b2 = (b)t.b.elementAt(i2);
                if (b2 == null) continue;
                int n4 = i2 - n3;
                bm.A(834);
                String string = null;
                String string2 = "" + b2.c;
                String string3 = b2.d;
                int n5 = b2.b & 0xFFFF;
                int n6 = b2.b >> 16;
                String string4 = bm.A(835);
                string = t.a(n5, true);
                if (n6 == 1) {
                    string4 = bm.A(836);
                }
                bm.W.setColor(-597591);
                bz.a(bm.W, string, 177, 95 + n4 * 22, 0);
                if (n6 != 1) {
                    bm.W.setColor(0x11AA11);
                    bz.a(bm.W, string4, 302, 95 + n4 * 22, 0);
                    bm.W.setColor(-597591);
                    bz.a(bm.W, string2, 262, 95 + n4 * 22, 0);
                } else {
                    bm.W.setColor(0xAA1111);
                    bz.a(bm.W, string4, 302, 95 + n4 * 22, 0);
                    bm.W.setColor(-597591);
                    bz.a(bm.W, string2, 262, 95 + n4 * 22, 0);
                }
                bz.a(bm.W, string3, 362, 95 + n4 * 22, 0);
            }
            ay.b(bm.W, 167, 90 + (a - n3) * 22, 305, 22);
            bm.a(bm.W, 20, 180);
            bm.X = 0;
        }
        graphics.drawImage(bm.V, 0, 0, 0);
    }

    private static void b(int n2) {
        --n2;
        if (bm.v(16388)) {
            j = j - 1 < 0 ? n2 : j - 1;
            return;
        }
        if (bm.v(33024)) {
            j = j + 1 > n2 ? 0 : j + 1;
        }
    }

    private static void a(int n2, int n3) {
        if ((n2 = bm.f(n2, 0)) != -1) {
            if (j == n2) {
                bm.dB = true;
                return;
            }
            j = n2;
            bm.X = 3;
        }
    }

    private static void a(Graphics graphics, String string) {
        ay.a(graphics, 113, 82, 430, 92, false, "", false);
        bm.D.a(graphics, 71, 320, 175, 0);
        bz.a(graphics, -597591, -13032703, 4);
        bz.a(graphics, string, 320, 190, 3);
        o = 212;
        p = false;
        ay.a(graphics, 113, 207, 435, 100, false, "", false);
    }

    private static void e(Graphics graphics) {
        bz.a(graphics, 16179625, -13870883, -1);
        String string = f.f.m;
        if (f.f.j != 0) {
            string = string + bm.A(822) + f.f.k + "/" + f.f.j;
        }
        ay.a(graphics, string, bf.a(string, 400), 118, 87, 400, 78, 0, 4, 16, false);
        graphics.setClip(113, 210, 435, 94);
        int n2 = o;
        int n3 = 0;
        if (f.f.w != null && f.f.w.size() > 0) {
            int n4 = f.f.w.size();
            bz.a(graphics, 16179625, -13870883, -1);
            String string2 = bm.d(0, 367);
            bz.a(graphics, string2, 118, n2, 0);
            n2 += 25;
            bz.a(graphics, -983184, -13870883, -1);
            for (int i2 = 0; i2 < n4 && n3 < 3; ++i2) {
                String string3 = (String)f.f.w.elementAt(i2);
                if (string3 == null || string3.equals("")) continue;
                int n5 = string3.indexOf("@");
                String string4 = string3.substring(0, n5);
                string3 = string3.substring(n5 + 1);
                n5 = string3.indexOf("@");
                String string5 = string3.substring(0, n5);
                string3 = string3.substring(n5 + 1);
                n5 = Integer.valueOf(string5);
                int n6 = Integer.valueOf(string3);
                n.a(graphics, 169, n2 - 3, n6, n5, 1, false, false, 0);
                ay.a(graphics, string4, 198, n2, 345, 25, 2, (byte)1, true, 0);
                n2 += 25;
                ++n3;
            }
        }
        bz.a(graphics, 16179625, -13870883, -1);
        String string6 = null;
        if (f.f.o != 0) {
            string6 = bm.d(0, 364) + f.f.o;
            bz.a(graphics, string6, 118, n2, 0);
            n2 += 25;
        }
        if (f.f.n != 0) {
            string6 = bm.d(0, 365) + f.f.n;
            bz.a(graphics, string6, 118, n2, 0);
            n2 += 25;
        }
        if (f.f.s != 0) {
            string6 = bm.d(0, 366) + f.f.s;
            bz.a(graphics, string6, 118, n2, 0);
            n2 += 25;
        }
        if (f.f.p != 0) {
            string6 = bm.d(0, 368) + f.f.p;
            bz.a(graphics, string6, 118, n2, 0);
            n2 += 25;
        }
        if (f.f.r != 0) {
            string6 = bm.d(0, 369) + f.f.r;
            bz.a(graphics, string6, 118, n2, 0);
            n2 += 25;
        }
        if (f.f.q != 0) {
            string6 = bm.A(661) + f.f.q;
            bz.a(graphics, string6, 118, n2, 0);
            n2 += 25;
        }
        if (!p && n2 > 307) {
            p = true;
        }
        if (p) {
            if (n2 < 207) {
                o = 312;
                p = false;
                return;
            }
            --o;
        }
    }

    public static void a(cd cd2) {
        f.a(cd2.d, cd2.e, cd2.f, cd2.c);
    }

    public static void a(int n2, int n3, int n4, int n5) {
        bm.H();
        cr.E();
        cr.aE = n5;
        if (n2 == bm.cj) {
            if (bm.cb.J == 4 || bm.cb.J == 7) {
                cr.bf = n2;
                cr.bg = n3;
                cr.bh = n4;
                return;
            }
            bm.a(n2, n3, n4);
            return;
        }
        if (n2 == 0) {
            bm.d(bm.A(814));
            return;
        }
        if (bm.cb.J == 4 || bm.cb.J == 7) {
            cr.bf = n2;
            cr.bg = n3;
            cr.bh = n4;
            return;
        }
        bm.bn.a((short)n2, (short)n3, (short)n4);
    }

    static {
        int[] nArray = new int[]{173, 174, 39};
        int[] nArray2 = new int[]{176, 175, 39};
        d = new String[]{bm.A(815), bm.A(816), bm.A(262)};
        f = new t();
        g = 1;
        h = -1;
        o = 212;
        p = false;
    }
}

