/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import java.util.Vector;
import javax.microedition.lcdui.Graphics;

public final class bd {
    public static boolean a = false;
    public static byte b = 0;
    private static byte r = 0;
    public static String c = "";
    public static String[] d;
    public static int e;
    public static Vector f;
    public static int g;
    public static int h;
    public static int i;
    private static int s;
    public static String j;
    public static String k;
    public static int l;
    public static long m;
    public static byte n;
    public static n o;
    public static int p;
    private static byte t;
    private static int u;
    private static int v;
    public static int q;
    private static int[][] w;
    private static int x;

    public static void a() {
        r = 0;
        c = "";
        d = null;
        e = 0;
        g = 0;
        bt.q = (byte)-1;
        bt.r = -1;
        bt.s = (byte)-1;
        bt.t = 0;
        bt.u = "";
        bt.v = -1;
        bt.w = (byte)-1;
        bt.x = 0;
    }

    public static void a(Graphics graphics) {
        graphics.setFont(bm.m);
        int n2 = graphics.getFont().getHeight();
        int n3 = 0;
        n3 = 0;
        if (!ci.a()) {
            ay.a(graphics, 2, 3, bm.A(336));
            bm.D.a(graphics, 51, 0, 0, 0);
        }
        bz.a(graphics, 0xA50000, 0xA50000, -1);
        ay.a(graphics, 355, 65, 170, 30, 5, "", bm.A(339), r == 1);
        ay.a(graphics, 115, 65, 215, 30, bz.a(bm.A(337)) + 5, bm.A(338), "^x" + c + "^t", r == 0);
        int n4 = 0;
        ay.a(graphics, 115, 98, 410, 152);
        bz.a(graphics, -597591, -13032703, 4);
        bz.a(graphics, bm.A(340), 115, 101, 0);
        n3 = 101 + (bz.a + 5);
        n4 = 250 - n3 - 2;
        ay.a(graphics, 115, n3, 410, n4, 5, "", "", r == 2);
        n4 = (byte)((n4 - 5) / n2);
        if (d == null) {
            bm.dX = 0;
            u = 0;
        } else {
            bm.dX = d.length / n4 + (d.length % n4 == 0 ? 0 : 1);
        }
        for (int n5 = 0; n5 < n4 && d != null && n5 + u * n4 < d.length; n5 = (int)((byte)(n5 + 1))) {
            bg.a(graphics, 0xFFFFFF, d[n5 + u * n4], 123, n3 + 2 + n5 * n2, 0);
        }
        if (bm.dX > 1 && r == 2) {
            ay.a(graphics, bm.A(341) + (u + 1) + "/" + bm.dX + ")", 100, 330, 440, 22, 333);
        }
        ay.a(graphics, 115, 253, 410, 30, bz.a(bm.A(342)) + 5, bm.A(343), "", r == 3);
        n3 = 115 + bz.a(bm.A(342));
        if (bt.x > 0) {
            n.a(graphics, n3 + 3, 256, bt.r, bt.s, bt.x, false, true, bt.q);
            bz.a(graphics, bt.t, bt.t, -1);
            bz.a(graphics, bt.u, n3 + 3 + 29, 258, 0);
        }
        ay.a(graphics, 105, 285, 195, 30, bz.a(bm.A(344)) + 5, bm.A(345), "" + e, r == 4);
        ay.a(graphics, 340, 285, 195, 30, bz.a(bm.A(346)) + 5, bm.A(347), "" + g, r == 5);
        if (b == 2) {
            cn.i(graphics);
        }
    }

    public static int b() {
        int n2 = u * bm.dZ + v;
        if (n2 >= 0 && n2 < f.size()) {
            return n2;
        }
        bg.a(n2 + " MAIl-INDEXERROR " + f.size());
        return -1;
    }

    public static void c() {
        bm.j(f.size(), 5);
        v = 0;
        u = 0;
    }

    private static void g() {
        b = 0;
        int n2 = cn.am[cn.an];
        switch (n2) {
            case 121: {
                bt.d();
                return;
            }
            case 122: {
                bt.i();
                return;
            }
            case 123: {
                String[] stringArray = bg.a((String)f.elementAt(bd.b()), "^", 5);
                int n3 = Integer.parseInt(stringArray[4]);
                stringArray[4] = "" + (n3 & 0xFFFFFFFE);
                s = n3 & 0xFFFFFFFE;
                f.setElementAt(bg.a(stringArray), bd.b());
                bm.bn.g(Integer.parseInt(stringArray[0]));
                bm.ad();
                if (bm.x() != 64) break;
                bd.j();
                return;
            }
            case 124: {
                b = (byte)3;
                x = 0;
                t = 1;
                return;
            }
            case 128: {
                b = (byte)3;
                x = 0;
                t = (byte)4;
                return;
            }
            case 127: {
                String[] stringArray = bg.a((String)f.elementAt(bd.b()), "^", 5);
                int n4 = bm.n(64) ? Integer.parseInt(stringArray[4]) : s;
                if ((n4 & 0x40) != 0) {
                    bm.d(bm.A(348));
                    return;
                }
                c = bm.n(64) ? stringArray[2] : j;
                bm.m(63);
                bd.j();
                return;
            }
            case 125: {
                if (n > 0 || l > 0) {
                    if (p > 0) {
                        b = (byte)3;
                        x = 0;
                        t = 0;
                        return;
                    }
                    bm.bn.i(h);
                    bm.ad();
                    return;
                }
                bm.d(bm.A(349));
                return;
            }
            case 126: {
                if (n > 0 || l > 0) {
                    b = (byte)3;
                    x = 0;
                    t = (byte)3;
                    return;
                }
                bm.d(bm.A(349));
                return;
            }
            case 129: {
                if (n > 0 || l > 0) {
                    cn.w = o;
                    cn.a(false, null, null);
                    if (bd.o.f == null) {
                        bd.o.f = "";
                        bm.bn.S(bd.o.d);
                    }
                    b = (byte)3;
                    x = 0;
                    t = (byte)2;
                    return;
                }
                bm.d(bm.A(349));
                return;
            }
            case 88: {
                n2 = 0;
                if (bm.n(64)) {
                    String[] stringArray = bg.a((String)f.elementAt(bd.b()), "^", 5);
                    if (Integer.parseInt(stringArray[1]) == bm.cb.C) {
                        bm.d(bm.A(172));
                        return;
                    }
                    n2 = Integer.parseInt(stringArray[1]);
                } else {
                    if (i == bm.cb.C) {
                        bm.d(bm.A(172));
                        return;
                    }
                    n2 = i;
                }
                bm.bn.s(n2);
            }
        }
    }

    private static void h() {
        if (cn.am == null) {
            return;
        }
        int n2 = cn.am.length;
        cn.c(n2, 0);
        cn.g(n2);
        int n3 = bm.as() - 1;
        if (n3 >= 0 && n3 < n2) {
            cn.an = n3;
            bd.g();
            return;
        }
        if (bm.v(196608)) {
            bd.g();
            return;
        }
        if (bm.v(262144)) {
            b = 0;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static void d() {
        int[][] nArray = new int[6][4];
        nArray[3][0] = 115;
        nArray[2][0] = 115;
        nArray[0][0] = 115;
        nArray[1][1] = 65;
        nArray[0][1] = 65;
        nArray[0][2] = 215;
        nArray[5][3] = 30;
        nArray[4][3] = 30;
        nArray[3][3] = 30;
        nArray[1][3] = 30;
        nArray[0][3] = 30;
        nArray[1][0] = 355;
        nArray[1][2] = 170;
        nArray[3][2] = 410;
        nArray[2][2] = 410;
        nArray[4][0] = 105;
        nArray[4][1] = 285;
        nArray[4][2] = 195;
        nArray[5][0] = 340;
        nArray[5][1] = 285;
        nArray[5][2] = 195;
        nArray[2][1] = 98;
        nArray[2][3] = 150;
        nArray[3][1] = 253;
        bm.dB = false;
        for (byte by2 = 0; by2 < 6; by2 = (byte)(by2 + 1)) {
            if (!bm.a(nArray[by2])) continue;
            if (r == by2) {
                bm.dB = true;
                break;
            }
            r = by2;
            break;
        }
        if (b == 2) {
            bd.h();
            return;
        }
        if (r == 0 && bm.v(8256)) {
            r = (byte)(r + 1);
        } else if (r == 1 && bm.v(4112)) {
            r = (byte)(r - 1);
        }
        if (bm.v(16388)) {
            if ((r = (byte)(r - 1)) >= 0) return;
            r = (byte)5;
            return;
        }
        if (bm.v(33024)) {
            if ((r = (byte)(r + 1)) < 6) return;
            r = 0;
            return;
        }
        if (bm.v(65568)) {
            switch (r) {
                case 0: {
                    bc.a((byte)24);
                    return;
                }
                case 2: {
                    bc.a((byte)25);
                    return;
                }
                case 4: {
                    bc.a((byte)26);
                    return;
                }
                case 5: {
                    bc.a((byte)27);
                    return;
                }
                case 1: {
                    l.a(3);
                    bm.ax();
                    bm.bn.a((byte)2, 0, l.g[1]);
                    bm.ad();
                    return;
                }
                case 3: {
                    if (bt.x > 0) {
                        cn.an = 0;
                        cn.am = w[0];
                        b = (byte)2;
                        return;
                    }
                    bt.d();
                }
                default: {
                    return;
                }
            }
        }
        if (bm.v(1024)) {
            if (++u < bm.dX) return;
            u = 0;
            return;
        }
        if (bm.v(262144)) {
            bd.a();
            if (bm.w() == 64) {
                bd.c();
            }
            bm.v();
            return;
        }
        if (!bm.v(196608)) return;
        if (c.equals("")) {
            bm.d(bm.A(350));
            return;
        }
        if (d == null || d.length == 0) {
            bm.d(bm.A(351));
            return;
        }
        if (e > cr.at) {
            bm.d(bm.A(352));
            return;
        }
        if (e > 0 && g > 0) {
            bm.d(bm.A(353));
            return;
        }
        bm.bn.h();
        bm.ad();
        bm.H();
    }

    public static void e() {
        block29: {
            block28: {
                block26: {
                    int n2;
                    block27: {
                        if (b == 2) {
                            bd.h();
                            return;
                        }
                        if (b == 3) {
                            bd.i();
                            return;
                        }
                        n2 = bm.b(5, 180, 100, 280, 40);
                        if (n2 != -1) {
                            if (v == n2) {
                                bm.dB = true;
                            } else {
                                v = n2;
                                bm.X = 3;
                            }
                        }
                        if (bm.e(290, 50, 60, 50)) {
                            v = v > 0 ? --v : 4;
                        } else if (bm.e(290, 300, 60, 50)) {
                            v = v < 4 ? ++v : 0;
                        }
                        if (bm.v(196608)) {
                            if (bd.b() == -1) {
                                return;
                            }
                            q = bd.b();
                            cn.ap = false;
                            cn.an = 0;
                            if (bm.x() == 64) {
                                cn.am = w[1];
                            } else if (bm.x() == 79) {
                                cn.am = w[3];
                            }
                            b = (byte)2;
                        } else if (bm.v(262144)) {
                            if (bm.n(64)) {
                                a = false;
                                bd.j();
                            }
                            f = null;
                            bm.v();
                        }
                        n2 = 1;
                        n2 = bm.e(340, 325, 150, 32) ? 1 : 0;
                        boolean bl2 = bm.e(150, 325, 150, 32);
                        if (bm.dX <= 1) break block26;
                        if (!bl2) break block27;
                        if (--u < 0) {
                            u = bm.dX - 1;
                        }
                        v = 0;
                        break block28;
                    }
                    if (n2 == 0) break block26;
                    if (++u >= bm.dX) {
                        u = 0;
                    }
                    v = 0;
                    break block28;
                }
                if (bm.v(16388)) {
                    if (--v < 0) {
                        if (u > 0) {
                            --u;
                        }
                        v = bm.dZ > 0 ? bm.dZ - 1 : 0;
                    }
                    ay.a();
                    bm.X = 3;
                    return;
                }
                if (!bm.v(33024)) break block29;
                if (++v >= bm.dZ) {
                    if (u < bm.dX - 1) {
                        ++u;
                    }
                    v = 0;
                }
            }
            ay.a();
            bm.X = 3;
        }
    }

    public static void b(Graphics graphics) {
        int n2;
        int n3;
        if (!ci.a()) {
            if (bm.n(64)) {
                ay.a(graphics, 2, 3, bm.A(354) + f.size() + "/" + 20 + ")");
            } else if (bm.n(79)) {
                ay.a(graphics, 2, 3, bm.A(355) + f.size() + "/" + 20 + ")");
            }
            bm.D.a(graphics, 50, 0, 0, 0);
        }
        ay.c(graphics, 180, 100, 280, 200, 5);
        bz.a(graphics, -597591, -13032703, 4);
        if (bm.n(64)) {
            bz.a(graphics, bm.A(356), 190, 65, 0);
        } else if (bm.n(79)) {
            bz.a(graphics, bm.A(357), 190, 65, 0);
        }
        bz.a(graphics, bm.A(358), 315, 65, 0);
        bz.a(graphics, bm.A(359), 395, 65, 0);
        int n4 = u * bm.dZ;
        ay.b(graphics, 181, 100 + v * 40 + 1, 278, 38);
        for (n3 = 0; n3 < 5 && (n2 = n4 + n3) < f.size(); ++n3) {
            String[] stringArray = bg.a((String)f.elementAt(n2), "^", 5);
            byte by2 = Byte.parseByte(stringArray[4]);
            if ((by2 & 1) != 0) {
                bz.a(graphics, 48401, 16179625, -1);
            } else {
                bz.a(graphics, 16179625, 16179625, -1);
            }
            String string = stringArray[2];
            if (Integer.parseInt(stringArray[1]) == bm.cb.C) {
                string = bm.A(360);
            }
            bz.b((byte)0);
            bz.a(graphics, string, 190, 100 + n3 * 40 + 4, 0);
            if ((by2 & 2) != 0 || (by2 & 4) != 0) {
                bz.a(graphics, bm.A(361), 414, 100 + n3 * 40 + 4, 1);
            } else {
                bz.a(graphics, bm.A(163), 414, 100 + n3 * 40 + 4, 1);
            }
            bz.a(graphics, stringArray[3], 315, 100 + n3 * 40 + 4, 0);
            bz.b((byte)1);
        }
        if (!ci.a()) {
            n3 = bm.D.g(68);
            bm.G.c();
            bm.G.a(graphics, 640 - n3 >> 1, 100, 2);
            bm.G.a(graphics, 640 - n3 >> 1, 300, 0);
        }
        if (b == 2) {
            cn.i(graphics);
        } else if (b == 3) {
            bd.d(graphics);
        }
        int n5 = -1;
        n5 = -1;
        n5 = -1;
        Graphics graphics2 = graphics;
        if (bm.dX > 1) {
            ay.a(graphics2, bg.a(bm.A(296), u + 1, bm.dX), 100, 330, 440, 22, 333);
            bm.D.a(2, 230, 330, 1);
            bm.D.a(2, 410, 330, 0);
        }
    }

    public static void f() {
        bm.a(true);
        if (b == 2) {
            bd.h();
            return;
        }
        if (3 == b) {
            bd.i();
            return;
        }
        if (bm.v(262144)) {
            if (bm.w() == 64 || bm.w() == 79) {
                bd.c();
            }
            bm.v();
            return;
        }
        if (bm.v(196608) && bm.w() == 64) {
            cn.an = 0;
            cn.am = w[2];
            b = (byte)2;
        }
    }

    private static void i() {
        if (t == 2) {
            if (bm.v(196608)) {
                b = 0;
                return;
            }
            if (bm.v(262144)) {
                b = 0;
                return;
            }
            cn.b(true);
            return;
        }
        int n2 = bm.av.length;
        int n3 = 0;
        n3 = n2;
        n3 = bm.f(n2, 0);
        if (n3 != -1) {
            if (x == n3) {
                bm.dB = true;
            } else {
                x = n3;
                bm.X = 3;
            }
        }
        if (bm.v(16388)) {
            x = x - 1 < 0 ? n2 - 1 : x - 1;
            return;
        }
        if (bm.v(33024)) {
            x = x + 1 > n2 - 1 ? 0 : x + 1;
            return;
        }
        if (bm.v(196608)) {
            if (x == 0) {
                if (t == 0) {
                    bm.bn.i(h);
                } else if (t == 1) {
                    String[] stringArray = bg.a((String)f.elementAt(bd.b()), "^", 5);
                    bm.bn.h(Integer.parseInt(stringArray[0]));
                } else if (t == 3) {
                    bm.bn.j(h);
                } else if (t == 4) {
                    bm.bn.j();
                    bm.bn.i();
                }
                b = 0;
                bm.ad();
                return;
            }
            b = 0;
            return;
        }
        if (bm.v(262144)) {
            b = 0;
        }
    }

    public static void c(Graphics graphics) {
        graphics.setFont(bm.m);
        int n2 = graphics.getFont().getHeight();
        int n3 = bz.a;
        int n4 = 0;
        boolean bl2 = false;
        ay.a(graphics, true, 2, 3, bm.A(362));
        bm.D.a(graphics, 51, 0, 0, 0);
        bz.a(graphics, 0xA50000, 0xA50000, -1);
        if (bm.w() == 64) {
            bz.a(graphics, bm.A(363), 115, 65, 0);
        } else if (bm.w() == 79) {
            bz.a(graphics, bm.A(364), 115, 65, 0);
        }
        n4 = 65 + (n3 + 3);
        String string = j;
        if (bm.cb.C == i) {
            bz.a(graphics, -11488, -1745920, 4);
            bz.a(graphics, bm.A(365), 119, n4 + 1, 0);
        } else if ((s & 0x40) != 0) {
            bz.a(graphics, -11488, -1745920, 4);
            bz.a(graphics, "[" + string + "]", 119, n4 + 1, 0);
        } else {
            bz.a(graphics, 16179625, 16179625, 4);
            bz.a(graphics, bm.A(366) + string + "^t", 119, n4 + 1, 0);
        }
        bz.a(graphics, 0xA50000, 0xA50000, -1);
        bz.a(graphics, bm.A(367), 115, n4 += n2 + 3, 0);
        bz.a(graphics, 16179625, 0, -1);
        bz.a(graphics, bg.a(m * 1000L), 115 + bz.a(bm.A(367)), n4, 0);
        bz.a(graphics, 0xA50000, 0xA50000, -1);
        bz.a(graphics, bm.A(368), 115, n4 += n3 + 3, 0);
        n2 = 250 - (n4 += n3 + 3);
        bf.a(k, 405);
        bz.a(graphics, 16179625, -16777216, -1);
        bm.a(graphics, 119, n4, true);
        bz.a(graphics, 0xA50000, 0xA50000, -1);
        bz.a(graphics, bm.A(369), 115, n4 += n2 + 3, 0);
        int n5 = 115 + bz.a(bm.A(369));
        if (n > 0) {
            o.a(graphics, n5, n4, bd.o.p, false, true);
            o.a(graphics, n5 += 29, n4, 0);
            if (n > 1) {
                String string2 = bd.o.e;
                if (bd.o.A > 0) {
                    string2 = string2 + "+" + bd.o.A;
                }
                if (bd.o.g == 1 && bd.o.k > 0) {
                    string2 = string2 + " " + bd.o.k + bm.A(370);
                }
                bz.b(2);
                bz.a(graphics, n + "", n5 += bz.a(string2), n4, 0);
                bz.b((byte)1);
            }
        }
        bz.a(graphics, 0xA50000, 0xA50000, -1);
        bz.a(graphics, bm.A(371), 115, n4 += 24, 0);
        n5 = 115 + bz.a(bm.A(371));
        bm.D.a(graphics, 23, n5, n4, 0);
        bz.a(graphics, 16179625, 0, -1);
        bz.a(graphics, p + "", (n5 += bm.D.f(23)) + 7, n4, 0);
        bz.a(graphics, 0xA50000, 0xA50000, -1);
        bz.a(graphics, bm.A(372), 115, n4 += 24, 0);
        n5 = 115 + bz.a(bm.A(372));
        bm.D.a(graphics, 23, n5, n4, 0);
        bz.a(graphics, 16179625, 0, -1);
        bz.a(graphics, l + "", (n5 += bm.D.f(23)) + 7, n4, 0);
        if (b == 2) {
            cn.i(graphics);
            return;
        }
        if (3 == b) {
            bd.d(graphics);
        }
    }

    private static void d(Graphics graphics) {
        Object object = "";
        if (t == 2) {
            cn.a(graphics, 0, 0, -1, false);
            return;
        }
        if (t == 0) {
            object = bg.a(bm.A(373), p);
        } else if (t == 1) {
            object = bg.a((String)f.elementAt(bd.b()), "^", 5);
            byte by2 = Byte.parseByte(object[4]);
            object = (by2 & 2) != 0 || (by2 & 4) != 0 ? bm.A(374) : bm.A(375);
        } else if (t == 3) {
            object = bm.A(376);
        } else if (t == 4) {
            object = bm.A(377);
        }
        ay.a(graphics, (String)object, bm.av, x, 300, true, bm.B(162), true);
    }

    private static void j() {
        byte by2 = 0;
        if (f != null && f.size() != 0) {
            for (int i2 = 0; i2 < f.size(); ++i2) {
                String[] stringArray = bg.a((String)f.elementAt(i2), "^", 5);
                by2 = Byte.parseByte(stringArray[4]);
                if ((by2 & 1) == 0) continue;
                a = true;
                return;
            }
        } else {
            a = false;
        }
    }

    static {
        e = 0;
        f = null;
        g = 0;
        h = 0;
        i = 0;
        s = 0;
        j = "";
        k = "";
        l = 0;
        m = 0L;
        n = 0;
        o = null;
        p = 0;
        q = 0;
        w = new int[][]{{121, 122}, {123, 127, 88, 124, 128}, {127, 88, 129, 125, 126, 124}, {123}};
    }
}

