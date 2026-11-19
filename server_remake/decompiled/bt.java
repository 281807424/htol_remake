/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class bt
implements bj {
    public static long a = 0L;
    private static int A = 0;
    private static int B = bm.bi.getFont().getHeight() < 30 ? 30 : bm.bi.getFont().getHeight();
    public static String b = "";
    private static byte C = 0;
    public static byte c = 0;
    private static byte D = 0;
    public static byte d = 0;
    public static int e;
    public static int f;
    private static int E;
    private static String[] F;
    private static int G;
    public static boolean g;
    public static boolean h;
    private static byte H;
    public static String[][] i;
    public static String[][] j;
    public static int[] k;
    public static int[] l;
    public static int[] m;
    public static int[] n;
    public static boolean[] o;
    public static boolean[] p;
    private static int[] I;
    private static int[] J;
    public static byte q;
    public static int r;
    public static byte s;
    public static int t;
    public static String u;
    public static int v;
    public static byte w;
    public static byte x;
    public static String[] y;
    private static String K;
    private static String L;
    private static int M;
    private static int N;
    private static int O;
    private static int P;
    private static int U;
    private static int V;
    private static int[] W;
    private static int[] X;
    private static int[] Y;
    private static int[] Z;
    private static int[] aa;
    private static int[] ab;
    private static int[] ac;
    private static int[][] ad;
    public static int[] z;

    static void a() {
        if (y == null) {
            return;
        }
        int n2 = 88 + B;
        if (c < C) {
            C = 0;
        }
        for (byte by2 = c; by2 >= 0; by2 = (byte)(by2 - 1)) {
            if (by2 >= y.length || y[by2] == null || (n2 += bt.a(bg.a(y[by2], "^", 11), 520)) <= 298) continue;
            C = (byte)(by2 + 1);
            return;
        }
    }

    private static void a(Graphics graphics, String[] stringArray, int n2, int n3, int n4, int n5) {
        int n6 = 0;
        int n7 = n2;
        n6 = Integer.parseInt(stringArray[1]);
        if (n5 == -1) {
            n5 = ab[n6];
        }
        int n8 = bg.a(graphics, n5, "[" + bm.d(0, W[n6]) + "]", n7, n3, 0);
        n7 += n8;
        n6 = n8 + 0;
        n8 = bg.a(graphics, n5, stringArray[2] + ":", n7, n3, 0);
        n7 += n8;
        n6 += n8;
        for (n8 = 0; n8 < stringArray[3].length(); ++n8) {
            int n9;
            char c2 = stringArray[3].charAt(n8);
            if (c2 == '%' && n8 + 1 < stringArray[3].length()) {
                n9 = bz.a(stringArray[3].toCharArray(), n8);
                if (n9 >= 0) {
                    ++n8;
                    try {
                        c2 = bm.bM.g(n9);
                        bm.bM.a(graphics, n9, n7, n3 + c2, 0);
                        n7 += bm.bM.f(n9);
                    }
                    catch (Exception exception) {}
                    continue;
                }
            } else if (stringArray[3].charAt(n8) == '/' && n8 + 2 < stringArray[3].length()) {
                if (stringArray[3].charAt(n8 + 1) >= '0' && stringArray[3].charAt(n8 + 1) <= '1' && stringArray[3].charAt(n8 + 2) >= '0' && stringArray[3].charAt(n8 + 2) <= '9' && Integer.parseInt(stringArray[3].substring(n8 + 1, n8 + 3)) < 12) {
                    if ((n6 += 30) < n4) {
                        ck.b(24).a(graphics, Integer.parseInt(stringArray[3].substring(n8 + 1, n8 + 3)), n7, n3, 0);
                        n7 += 30;
                        n8 += 2;
                        continue;
                    }
                    n7 = n2;
                    n6 = 0;
                    --n8;
                    n3 += B;
                    continue;
                }
                if (stringArray[3].charAt(n8 + 1) == '-' && stringArray[3].charAt(n8 + 2) == '1' && Integer.parseInt(stringArray[9]) != -1) {
                    n6 += 25;
                    String string = stringArray[8];
                    if ((n6 += bm.m.stringWidth(string)) < n4) {
                        n.a(graphics, n7, n3, Integer.parseInt(stringArray[5]), Integer.parseInt(stringArray[6]), 1, false, false, Integer.parseInt(stringArray[4]));
                        bg.a(graphics, Integer.parseInt(stringArray[7]), string, n7 += 25, n3, 0);
                        n7 += bm.m.stringWidth(string);
                        n8 += 2;
                        stringArray[9] = "-1";
                        continue;
                    }
                    n7 = n2;
                    n6 = 0;
                    --n8;
                    n3 += B;
                    continue;
                }
            }
            if ((n6 += (n9 = bm.m.charWidth(c2))) <= n4) {
                graphics.setColor(n5);
                if (n7 >= 0) {
                    graphics.drawChar(c2, n7, n3, 0);
                }
                if ((n7 += n9) < 640) continue;
                return;
            }
            n7 = n2;
            n6 = 0;
            --n8;
            n3 += B;
        }
    }

    public static void b() {
        c = 0;
        e = f;
        bm.m(49);
        d = 0;
        if (bm.J == null) {
            bm.J = new cy(bm.D, 19);
        }
        boolean bl2 = true;
        cy cy2 = bm.J;
        bm.J.e = bl2;
        if (bm.K == null) {
            bm.K = new cy(bm.D, 20);
        }
        bl2 = true;
        cy2 = bm.K;
        bm.K.e = bl2;
        int n2 = e;
        bt.a(n2, true);
    }

    public static void c() {
        bm.m(50);
    }

    public static void d() {
        bm.m(51);
        if (bm.aA != null) {
            bm.aA.a(5, 8, 30, 80);
            bm.aA.g();
            bm.aA.h();
            bm.aA.c();
            bm.aA.j = null;
            bm.aJ = 0;
            cn.a(false, false, 10);
            A = bm.aA.q;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void e() {
        int n2;
        int n3;
        int n4;
        block48: {
            int n5;
            block49: {
                block47: {
                    if (d != 0) break block47;
                    n5 = 0;
                    n4 = 0;
                    if (y == null) break block48;
                    break block49;
                }
                if (d == 1) {
                    if (cn.am == null) return;
                    if (d == 1) {
                        if (bm.e(10, 90, 50, 90)) {
                            bm.dC = true;
                            bm.bl = 16384;
                        }
                        if (bm.e(10, 180, 50, 90)) {
                            bm.dC = true;
                            bm.bl = 32768;
                        }
                    }
                    int n6 = cn.am.length;
                    cn.c(n6, 0);
                    bm.dC = false;
                    cn.g(n6);
                    int n7 = bm.as() - 1;
                    if (n7 >= 0 && n7 < n6) {
                        cn.an = n7;
                        bt.k();
                        return;
                    }
                    if (bm.v(196608)) {
                        bt.k();
                        return;
                    }
                    if (!bm.v(262144)) return;
                    d = 0;
                    return;
                }
                if (d == 3) {
                    if (!(bm.v(393216) || bm.am() || bm.an())) {
                        cn.b(false);
                        return;
                    }
                    cn.aq = 0;
                    d = 0;
                    return;
                }
                if (d != 4) return;
                bm.g(20, 180);
                if (bm.e(305, 71, 180, 210)) {
                    int n8 = (bm.dt - 71) / 30 + 1;
                    if (n8 == H) {
                        bm.bl = 65536;
                    }
                    H = (byte)n8;
                }
                if (bm.v(16388)) {
                    if (H == 1) {
                        H = (byte)(ac.length - 1);
                        return;
                    }
                    H = (byte)(H - 1);
                    return;
                }
                if (bm.v(33024)) {
                    if (H == ac.length - 1) {
                        H = 1;
                        return;
                    }
                    H = (byte)(H + 1);
                    return;
                }
                if (bm.v(65536)) {
                    if (ag.e[H] + 1 != ad[H].length) {
                        byte by2 = H;
                        ag.e[by2] = (byte)(ag.e[by2] + 1);
                        return;
                    }
                    ag.e[bt.H] = 0;
                    return;
                }
                if (bm.v(131072)) {
                    f = 0;
                    e = 0;
                    d = 0;
                    af.a(true, 4);
                    bm.bn.N();
                    bm.d(bm.A(164));
                    return;
                }
                if (!bm.v(262144)) return;
                d = 0;
                return;
            }
            for (n3 = C; n3 < y.length && y[n3] != null; n5 += n4, n3 = (int)((byte)(n3 + 1))) {
                n4 = bt.a(bg.a(y[n3], "^", 11), 520);
                if (n3 == C) {
                    n5 = 88 + B;
                } else if (n5 + n4 > 298) break;
                if (!bm.e(75, n5 - 1, 518, n4 + 1)) continue;
                if (c == n3) {
                    bm.dA = true;
                    break;
                }
                c = (byte)n3;
                break;
            }
        }
        int[][] nArray = null;
        int n9 = 10;
        nArray = new int[8][4];
        nArray[0][0] = 70;
        for (n2 = 0; n2 < 8; ++n2) {
            bm.d(0, W[n2]).trim();
            n3 = 540 / W.length - 4;
            n4 = n2 == 0 ? 0 : n2 - 1;
            nArray[n2][0] = nArray[n4][0] + n9;
            nArray[n2][1] = 40;
            nArray[n2][2] = n3;
            nArray[n2][3] = 34;
            n9 = n3 + 4;
        }
        for (n2 = 0; n2 < 8; ++n2) {
            if (!bm.a(nArray[n2])) continue;
            if (e == n2) {
                bm.dB = true;
                continue;
            }
            n4 = e = n2;
            bt.a(e, true);
            c = 0;
        }
        if (bm.v(4112)) {
            if (--e < 0) {
                e = W.length - 1;
            }
            n4 = e;
            bt.a(n4, true);
            c = 0;
        } else if (bm.v(8256)) {
            if (++e >= W.length) {
                e = 0;
            }
            n4 = e;
            bt.a(n4, true);
            c = 0;
        }
        if (bm.v(262144)) {
            bm.H();
            I = new int[7];
            for (int i2 = 0; i2 < 7; ++i2) {
                bt.I[i2] = 0;
                bt.J[i2] = 0;
            }
        } else if (bm.v(196640)) {
            int n10 = 0;
            String[] stringArray = null;
            if (y != null && y.length > 0) {
                stringArray = bg.a(y[c], "^", 11);
                n10 = Integer.parseInt(stringArray[0]);
            }
            cn.an = 0;
            switch (e) {
                case 1: 
                case 2: 
                case 3: 
                case 4: 
                case 5: 
                case 8: {
                    if (e == 4 && bm.cb.ab == 0 || e == 5 && !cr.j() || e == 8 && bm.cb.W.equals(bm.A(163))) {
                        l.a(Y);
                        break;
                    }
                    if (n10 == 0) {
                        l.a(X);
                        break;
                    }
                    l.a(Z);
                    break;
                }
                case 7: {
                    l.a(Y);
                    break;
                }
                case 0: 
                case 6: {
                    if (n10 == 0) {
                        l.a(Y);
                        break;
                    }
                    l.a(aa);
                    break;
                }
            }
            if (y != null && y.length > 0) {
                stringArray = bg.a(y[c], "^", 11);
                n10 = Integer.parseInt(stringArray[0]);
                l.j = new cr();
                new cr().C = n10;
                l.j.E = stringArray[2];
                l.k = new n();
                new n().d = Integer.parseInt(stringArray[9]);
                l.k.q = Long.parseLong(stringArray[10]);
            }
            l.a(0, false);
        }
        bm.a(70, 73, 524, 240, 30, false);
        if (ci.a()) return;
        if (bm.v(16388) || bm.e(10, 90, 50, 90)) {
            c = (byte)(c - 1);
            if (y != null && c < 0) {
                c = 0;
            }
            if (c >= C) return;
            C = c;
            return;
        }
        if (!bm.v(33024)) {
            if (!bm.e(10, 180, 50, 90)) return;
        }
        c = (byte)(c + 1);
        if (y != null && c >= y.length) {
            c = 0;
        }
        bt.a();
    }

    public static void a(Graphics graphics) {
        int n2;
        if (d != 4) {
            Graphics graphics2;
            Graphics graphics3 = graphics2 = graphics;
            aq.a(0, 0, 640, 360);
            ay.a(graphics3, 2, 3, bm.d(0, 31));
            ay.a(graphics3, 70, 45, 550, 240, 28, 4, 10, e, W);
            if (W[e] == 48 && bm.br.size() == 0) {
                bm.a(graphics3, bm.B(162), bm.d(1, 139));
            }
            if (W[e] == 45 && bm.bq.size() == 0) {
                bm.a(graphics3, bm.B(162), bm.d(1, 138));
            }
            graphics3 = graphics2;
            n2 = 88 + B;
            int n3 = 0;
            n3 = 0;
            int n4 = 0;
            if (y != null) {
                for (byte by2 = C; by2 < y.length && y[by2] != null; by2 = (byte)(by2 + 1)) {
                    n3 = bt.a(bg.a(y[by2], "^", 11), 520);
                    if (by2 == C) {
                        n2 = 88 + B;
                    } else if (n2 + n3 > 298) break;
                    if (c == by2) {
                        graphics3.setColor(118467);
                        aq.b(75, n2 - 1, 518, n3 + 1);
                    }
                    bt.a(graphics3, bg.a(y[by2], "^", 11), 75, n2, 520, -1);
                    n2 += n3;
                    ++n4;
                }
                n3 = y.length;
            }
            ay.a(graphics3, 595, 88 + B, 11, 210 - B, (int)c, n4, n3);
            if (!ci.a()) {
                bm.D.a(graphics3, 182, -50, -92, 0);
                bm.D.a(graphics3, 186, -50, -92, 0);
            }
        }
        if (d == 1) {
            cn.i(graphics);
            return;
        }
        if (d == 3) {
            cn.a(graphics, 0, 0, -1, true);
            return;
        }
        if (d == 4) {
            Graphics graphics4 = graphics;
            ay.a(graphics4, true, 2, 3, bm.A(165));
            ay.a(graphics4);
            ay.a(graphics4, bm.A(166), 68, 330, 584, 22, 333);
            ay.a(graphics4, 305, 41 + H * 30, 208, 26, true);
            for (n2 = 1; n2 < ac.length; ++n2) {
                if (H == n2) {
                    bz.a(graphics4, -5041, 16179625, -1);
                } else {
                    bz.a(graphics4, 5385734, 16179625, -1);
                }
                bz.a(graphics4, bm.B(ac[n2]) + bm.A(167), 313, 45 + n2 * 30);
                bz.a(graphics4, bm.B(ad[n2][ag.e[n2]]), 488, 45 + n2 * 30);
            }
        }
    }

    public static void f() {
        block12: {
            block15: {
                block14: {
                    block13: {
                        block11: {
                            if (bm.e(70, 30, 480, 240)) {
                                bm.bl = 131072;
                                D = (byte)((bm.ds - 70) / 160 + (bm.dt - 30) / 60 * 3);
                            }
                            if (!bm.v(4096)) break block11;
                            if ((D = (byte)(D - 1)) >= 0) break block12;
                            D = (byte)11;
                            break block12;
                        }
                        if (!bm.v(8192)) break block13;
                        if ((D = (byte)(D + 1)) < 12) break block12;
                        D = 0;
                        break block12;
                    }
                    if (!bm.v(16384)) break block14;
                    if ((D = (byte)(D - 3)) >= 0) break block12;
                    D = (byte)(D + 12);
                    break block12;
                }
                if (!bm.v(32768)) break block15;
                if ((D = (byte)(D + 3)) < 12) break block12;
                D = (byte)(D - 12);
                break block12;
            }
            if (bm.v(262144)) {
                bm.v();
                bc.a((byte)14);
            } else if (bm.v(1)) {
                D = (byte)10;
                bm.bl = 131072;
            } else if (bm.v(2048)) {
                D = (byte)9;
                bm.bl = 131072;
            } else if (bm.v(1024)) {
                D = (byte)11;
                bm.bl = 131072;
            } else {
                for (int n2 = 1; n2 < bm.bk.length; n2 = (int)((byte)(n2 + 1))) {
                    if (!bm.v(bm.bk[n2])) continue;
                    byte by2 = (byte)(n2 - 1);
                    n2 = by2;
                    D = by2;
                    bm.bl = 131072;
                    break;
                }
            }
        }
        if (bm.v(131072)) {
            b = D < 10 ? b + "/0" + D : b + "/" + D;
            bc.a((byte)14, bc.c);
            bm.v();
        }
    }

    public static void b(Graphics graphics) {
        String string = null;
        aq.a(0, 0, 640, 360);
        ay.a(graphics, true, 2, 3, bm.d(0, 31));
        for (int i2 = 0; i2 < 12; ++i2) {
            string = i2 == 9 ? "*" : (i2 == 10 ? "0" : (i2 == 11 ? "#" : i2 + 1 + ""));
            ck.b(24).a(graphics, i2, 145 + i2 % 3 * 160, 45 + i2 / 3 * 60, 0);
            bz.a(graphics, string, 160 + i2 % 3 * 160, 75 + i2 / 3 * 60, 1);
            if (D != i2) continue;
            graphics.drawRect(145 + i2 % 3 * 160, 45 + i2 / 3 * 60, 30, 40);
        }
    }

    private static void k() {
        d = 0;
        int n2 = cn.am[cn.an];
        String[] stringArray = null;
        int n3 = 0;
        if (y != null && y.length > 0) {
            stringArray = bg.a(y[c], "^", 11);
            n3 = Integer.parseInt(stringArray[0]);
        }
        switch (n2) {
            case 86: {
                if (n3 == bm.cb.C) {
                    bm.d(bm.A(168));
                    return;
                }
                f = e;
                e = 6;
                bc.a((byte)14, n3);
                return;
            }
            case 84: {
                if (e == 6 && (y == null || y.length <= 0)) {
                    bm.d(bm.A(169));
                    return;
                }
                if (e == 2 && bm.cb.U < 10) {
                    bm.d(bm.A(170));
                    return;
                }
                bc.a((byte)14);
                f = e;
                return;
            }
            case 83: {
                n2 = Integer.parseInt((String)stringArray[9]);
                long l2 = Long.parseLong((String)stringArray[10]);
                if (n2 == -1) {
                    bm.d(bm.A(171));
                    return;
                }
                bm.bn.a(n3, n2, l2);
                bm.ad();
                return;
            }
            case 85: {
                if (n3 == bm.cb.C) {
                    bm.a(1, true);
                    return;
                }
                bm.ad();
                bm.bn.A(n3);
                return;
            }
            case 88: {
                if (n3 == bm.cb.C) {
                    bm.d(bm.A(172));
                    return;
                }
                bm.bn.s(n3);
                return;
            }
            case 89: {
                if (n3 == bm.cb.C) {
                    bm.d(bm.A(173));
                    return;
                }
                bm.ad();
                bm.bn.u(n3);
                return;
            }
            case 82: {
                d = (byte)4;
                return;
            }
            case 94: {
                if (n3 == bm.cb.C) {
                    bm.d(bm.A(174));
                    return;
                }
                bd.c = stringArray[2];
                bm.m(63);
                return;
            }
            case 87: {
                if (n3 == bm.cb.C) {
                    bm.d(bm.A(175));
                    return;
                }
                bm.bn.p(n3);
                return;
            }
            case 93: {
                if (n3 == bm.cb.C) {
                    bm.d(bm.A(175));
                    return;
                }
                bm.I(n3);
            }
        }
    }

    public static void g() {
        for (int n2 = 0; n2 < 8; n2 = (int)((byte)(n2 + 1))) {
            bt.l[n2] = 0;
            bt.k[n2] = 0;
            bt.o[n2] = false;
        }
        y = null;
        a = 0L;
    }

    public static void h() {
        for (int n2 = 0; n2 < 7; n2 = (int)((byte)(n2 + 1))) {
            bt.n[n2] = 0;
            bt.m[n2] = 0;
            bt.p[n2] = false;
        }
    }

    public static void i() {
        q = (byte)-1;
        r = -1;
        s = (byte)-1;
        u = "";
        v = -1;
        w = (byte)-1;
        x = 0;
        t = 0;
    }

    static void a(int n2) {
        bt.a(n2, true);
    }

    static void a(int n2, boolean bl2) {
        if (bl2) {
            if (n2 == 6) {
                g = false;
            } else if (n2 == 5) {
                h = false;
            }
        }
        y = n2 == 4 && bm.br.size() == 0 || n2 == 5 && bm.bq.size() == 0 || n2 == 8 && bm.cb.W.endsWith(bm.A(163)) ? null : bt.a(i[n2], l[n2], k[n2], o[n2]);
        C = 0;
    }

    static String[] a(String[] stringArray, int n2, int n3, boolean n4) {
        if (stringArray == null) {
            return null;
        }
        int n5 = bt.a(stringArray, n2, n3);
        String[] stringArray2 = new String[n5];
        if (n4 == 0) {
            --n3;
            for (n4 = 0; n3 >= n2 && n4 < n5; --n3, ++n4) {
                stringArray2[n4] = stringArray[n3];
            }
        } else {
            --n3;
            for (n4 = 0; n4 < n5; ++n4) {
                if (n3 < 0) {
                    n3 = n5 - 1;
                }
                stringArray2[n4] = stringArray[n3];
                --n3;
            }
        }
        return stringArray2;
    }

    static void a(int n2, int n3, String string, String string2, long l2) {
        if (string2.equals("")) {
            return;
        }
        int n4 = n3;
        if (n3 == 11) {
            n4 = 7;
        }
        if (!bt.b(string = n2 + "^" + n4 + "^" + string + "^" + string2 + "^" + l2)) {
            return;
        }
        bt.i[n4][bt.k[n4]] = string;
        bt.i[0][bt.k[0]] = string;
        bt.a(l, k, o, n4, 30);
        bt.a(l, k, o, 0, 30);
        if (n3 == 7) {
            bt.a(5, string);
            return;
        }
        if (n3 == 11) {
            al.a(8, 0, 0);
            bt.a(6, string);
            return;
        }
        if (n3 == 6) {
            g = true;
            if (bm.n(49) && e == 6 || n2 == bm.cb.C) {
                g = false;
            }
            if (g) {
                al.a(8, 0, 0);
            }
            if (ag.e[6] == 0) {
                bt.a(3, string);
                return;
            }
        } else if (n3 == 5) {
            h = true;
            if (bm.n(49) && e == 5 || n2 == bm.cb.C) {
                h = false;
            }
            if (ag.e[5] == 0) {
                bt.a(4, string);
                return;
            }
        } else {
            bt.a(0, string);
        }
    }

    static void a(int[] nArray, int[] nArray2, boolean[] blArray, int n2, int n3) {
        if (!blArray[n2]) {
            int n4 = n2;
            nArray2[n4] = nArray2[n4] + 1;
            if (nArray2[n4] >= n3) {
                nArray2[n2] = 0;
                nArray[n2] = 1;
                blArray[n2] = true;
                return;
            }
        } else {
            int n5 = n2;
            nArray2[n5] = nArray2[n5] + 1;
            if (nArray2[n5] >= n3) {
                nArray2[n2] = 0;
            }
            int n6 = n2;
            nArray[n6] = nArray[n6] + 1;
            if (nArray[n6] >= n3) {
                nArray[n2] = 0;
            }
        }
    }

    private static int a(String[] stringArray, int n2) {
        int n3 = B;
        int n4 = bm.bi.getFont().stringWidth("[" + bm.d(0, W[Integer.parseInt(stringArray[1])]) + "]");
        n4 += bm.bi.getFont().stringWidth(stringArray[2] + ":");
        for (int i2 = 0; i2 < stringArray[3].length(); ++i2) {
            char c2 = stringArray[3].charAt(i2);
            if (stringArray[3].charAt(i2) == '/' && i2 + 2 < stringArray[3].length()) {
                if (stringArray[3].charAt(i2 + 1) >= '0' && stringArray[3].charAt(i2 + 1) <= '1' && stringArray[3].charAt(i2 + 2) >= '0' && stringArray[3].charAt(i2 + 2) <= '9' && Integer.parseInt(stringArray[3].substring(i2 + 1, i2 + 3)) < 12) {
                    if ((n4 += 30) <= n2) continue;
                    n4 = 0;
                    --i2;
                    n3 += B;
                    continue;
                }
                if (stringArray[3].charAt(i2 + 1) == '-' && stringArray[3].charAt(i2 + 2) == '1' && Integer.parseInt(stringArray[9]) != -1) {
                    n4 += 25;
                    String string = stringArray[8];
                    if ((n4 += aq.a.getFont().stringWidth(string)) > n2) {
                        n4 = 0;
                        --i2;
                        n3 += B;
                        continue;
                    }
                    stringArray[9] = "-1";
                    continue;
                }
            }
            if ((n4 += bm.m.charWidth(c2)) <= n2) continue;
            n4 = 0;
            --i2;
            n3 += B;
        }
        return n3;
    }

    static String a(String stringArray) {
        stringArray = bg.a((String)stringArray, "^", 11);
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[");
        stringBuffer.append(bm.d(0, W[Integer.parseInt(stringArray[1])]));
        stringBuffer.append("]");
        stringBuffer.append(stringArray[2] + ":");
        stringBuffer.append(stringArray[3]);
        return stringBuffer.toString();
    }

    public static void c(Graphics graphics) {
        if (!ci.a()) {
            ay.a(graphics, true, 2, 3, bm.d(0, 121));
        }
        bm.aA.a(graphics);
        bm.c(graphics, bm.aH, bm.aI, 29, 29);
        if (cn.A - cn.B > 0) {
            ay.a(graphics, bm.A(176), 100, 330, 440, 22, 333);
        }
        if (cn.w != null) {
            cn.a(graphics, 300, 130, -1, true);
        }
        if (bd.b == 1) {
            Object object = new String[]{bm.d(0, 2), bm.d(0, 39)};
            ay.a(graphics, "", object, cn.an, 300, true, "", true);
            object = new int[]{259, 139, 27, 27, 347218, 0xFEEFCE};
            ay.a(graphics, (int)object[0], (int)object[1], (int)object[2], (int)object[3], (int)object[4], (int)object[5]);
            graphics.setColor(0);
            graphics.drawRect(258, 138, 29, 29);
            if (cn.w != null) {
                cn.w.a(graphics, 261, 141, false, false);
                object = cn.w.e;
                bz.a(graphics, 16179625, -16777216, -1);
                bz.a(graphics, (String)object, 310, 130);
            }
            graphics.setColor(15910705);
            graphics.fillRect(335, 178, 30, 20);
            graphics.setColor(0);
            graphics.drawRect(335, 178, 30, 20);
            bz.a(graphics, 0, 0, -1);
            bz.a(graphics, cn.T + "", 340, 180);
            bz.a(graphics, 16179625, -16777216, -1);
            bz.a(graphics, bm.d(0, 63), 252, 180);
            bm.F.c();
            bm.F.a(graphics, 310, 180, 0);
            bm.F.a(graphics, 390, 180, 1);
        }
    }

    public static void j() {
        if (bd.b == 1) {
            int n2 = bm.av.length;
            cn.h(cn.w.p);
            cn.c(n2, 0);
            cn.g(n2);
            if (bm.w(4112)) {
                if (--cn.T <= 0) {
                    cn.T = cn.w.p;
                    return;
                }
            } else if (bm.w(8256)) {
                if (++cn.T > cn.w.p) {
                    cn.T = 1;
                    return;
                }
            } else {
                if (bm.v(262144)) {
                    bd.b = 0;
                    return;
                }
                if (bm.v(196608)) {
                    if (cn.an == 1) {
                        bd.b = 0;
                        return;
                    }
                    n n3 = bm.aA.b(bm.aA.q);
                    if (n3 == null) {
                        return;
                    }
                    if (n3.d(32768) && n3.s.length > 0) {
                        q = (byte)n3.s[0];
                    }
                    r = n3.j;
                    s = n3.l;
                    t = n3.k();
                    u = n3.e;
                    v = n3.d;
                    w = n3.c;
                    x = (byte)cn.T;
                    bd.b = 0;
                    bm.v();
                }
            }
            return;
        }
        if (bm.v(262144)) {
            if (bm.w() == 49) {
                bc.a((byte)14, bc.c);
            }
            bm.v();
            cn.aq = 0;
        }
        bm.aA.i();
        bm.aR = bm.aA;
        bm.aA.A();
        if (A != bm.aA.q && cn.w != null) {
            cn.a(false, false, 10);
            A = bm.aA.q;
        }
        if (bm.v(196608) || bm.dB) {
            n n4 = bm.aA.b(bm.aA.q);
            if (n4 == null) {
                return;
            }
            if (n4.d(32768) && n4.s.length > 0) {
                q = (byte)n4.s[0];
            }
            r = n4.j;
            s = n4.l;
            t = n4.k();
            u = n4.e;
            v = n4.d;
            w = n4.c;
            if (bm.w() == 63) {
                if (n4.i == 1) {
                    bm.d(bm.A(177));
                    return;
                }
                if (cn.w.p > 1) {
                    cn.T = 1;
                    cn.an = 0;
                    bd.b = 1;
                    return;
                }
                x = 1;
                bm.v();
                return;
            }
            b = b + "/-1";
            bc.a((byte)14, bc.c);
            bm.v();
            return;
        }
        cn.b(true);
    }

    static void a(int n2, String string) {
        int n3;
        if (n2 == 1) {
            if (ag.e[7] == 1 && string.indexOf(bm.A(178)) == -1 && string.indexOf(bm.A(179)) == -1 && string.indexOf(bm.A(180)) == -1) {
                return;
            }
            for (n3 = 0; n3 < j[n2].length; n3 = (int)((byte)(n3 + 1))) {
                if (!string.equals(j[n2][n3])) continue;
                return;
            }
        }
        bt.j[n2][bt.n[n2]] = string;
        bt.a(m, n, p, n2, 10);
        int n4 = n2;
        I[n4] = I[n4] + 1;
        n3 = bt.a(j[n2], m[n2], n[n2]);
        if (I[n2] > n3) {
            bt.I[n2] = n3;
        }
    }

    static void d(Graphics graphics) {
        int n2;
        int n3;
        int n4 = 0;
        if (L == null) {
            U = 3;
            L = bt.a(j[3], m[3], n[3], p[3], I[3]);
            if (L == null) {
                L = bt.a(j[4], m[4], n[4], p[4], I[4]);
                U = 4;
            }
            if (L == null) {
                L = bt.a(j[0], m[0], n[0], p[0], I[0]);
                U = 0;
            }
            if (L != null) {
                n3 = bt.a(bg.a(L, "^", 11), 290);
                bt.J[bt.U] = n3 / B * M - 1;
            }
        }
        if (L != null) {
            String string = L;
            n2 = O;
            n3 = 0;
            Graphics graphics2 = graphics;
            n4 = bt.a(graphics2, 0, n2, string, -1);
            ak.e[8] = ak.e[8] + 1;
            if (J[U] % M < N) {
                O += 3;
            }
            int n5 = U;
            J[n5] = J[n5] - 3;
            if (J[U] <= 0) {
                bt.J[bt.U] = 0;
                L = null;
                int n6 = U;
                I[n6] = I[n6] - 1;
                O = 0;
            }
        }
        if (K == null && (K = bt.a(j[1], m[1], n[1], p[1], I[1])) != null) {
            n3 = bt.a(bg.a(K, "^", 11), 290);
            bt.J[1] = n3 / B * M - 1;
        }
        if (K != null) {
            bt.a(graphics, n4, P, K, 16179625);
            ak.e[8] = ak.e[8] + 1;
            if (J[1] % M < N) {
                P += 3;
            }
            J[1] = J[1] - 3;
            if (J[1] <= 0) {
                P = 0;
                bt.J[1] = 0;
                K = null;
                int n7 = p[1];
                int n8 = I[1];
                int n9 = 1;
                int[] nArray = n;
                int[] nArray2 = m;
                String[] stringArray = j[1];
                if (n8 > 0 && stringArray != null) {
                    int n10 = nArray2[1];
                    int n11 = nArray[1];
                    n9 = bt.a(stringArray, n10, n11);
                    if (n7 == 0) {
                        --n11;
                        for (n7 = 0; n11 >= n10 && n7 < n9; --n11, ++n7) {
                            if (n7 < n8 - 1) continue;
                            stringArray[n11] = null;
                        }
                    } else {
                        --n11;
                        for (n7 = 0; n7 < n9; ++n7) {
                            if (n11 < 0) {
                                n11 = n9 - 1;
                            }
                            if (n7 >= n8 - 1) {
                                stringArray[n11] = null;
                            }
                            --n11;
                        }
                    }
                }
                I[1] = I[1] - 1;
            }
        }
        if (F == null) {
            V = 6;
            String string = bt.a(j[6], m[6], n[6], p[6], I[6]);
            if (string == null) {
                V = 5;
                string = bt.a(j[5], m[5], n[5], p[5], I[5]);
            }
            if (string != null) {
                F = bg.a(string, "^", 11);
                String[] stringArray = F;
                int n12 = bm.bi.getFont().stringWidth("[" + bm.d(0, W[Integer.parseInt(stringArray[1])]) + "]");
                n12 += bm.bi.getFont().stringWidth(stringArray[2] + ":");
                for (n2 = 0; n2 < stringArray[3].length(); ++n2) {
                    char c2 = stringArray[3].charAt(n2);
                    if (c2 == '/' && n2 + 2 < stringArray[3].length()) {
                        if (stringArray[3].charAt(n2 + 1) >= '0' && stringArray[3].charAt(n2 + 1) <= '1' && stringArray[3].charAt(n2 + 2) >= '0' && stringArray[3].charAt(n2 + 2) <= '9' && Integer.parseInt(stringArray[3].substring(n2 + 1, n2 + 3)) < 12) {
                            n12 += 30;
                            continue;
                        }
                        if (stringArray[3].charAt(n2 + 1) == '-' && stringArray[3].charAt(n2 + 2) == '1' && Integer.parseInt(stringArray[9]) != -1) {
                            n12 += 25;
                            String string2 = stringArray[8];
                            n12 += aq.a.getFont().stringWidth(string2);
                            stringArray[9] = "-1";
                            continue;
                        }
                    }
                    n12 += bm.m.charWidth(c2);
                }
                G = n12;
                E = -640;
                return;
            }
        } else if (F != null) {
            graphics.setClip(0, 0, 531, B);
            aq.a(0, 5, 640, B);
            aq.e(0x70000000);
            aq.b(graphics, 0, 5, 640, B);
            bt.a(graphics, F, -E, 5, G, -1);
            graphics.setClip(0, 0, 640, 360);
            ak.e[8] = ak.e[8] + 1;
            if ((E += 9) > G) {
                F = null;
                E = -640;
                int n13 = V;
                I[n13] = I[n13] - 1;
            }
        }
    }

    private static String a(String[] stringArray, int n2, int n3, boolean n4, int n5) {
        if (n5 <= 0) {
            return null;
        }
        if (stringArray == null) {
            return null;
        }
        int n6 = bt.a(stringArray, n2, n3);
        if (n4 == 0) {
            --n3;
            for (n4 = 0; n3 >= n2 && n4 < n6; --n3, ++n4) {
                if (n4 != n5 - 1) continue;
                return stringArray[n3];
            }
        } else {
            --n3;
            for (n4 = 0; n4 < n6; ++n4) {
                if (n3 < 0) {
                    n3 = n6 - 1;
                }
                if (n4 == n5 - 1) {
                    return stringArray[n3];
                }
                --n3;
            }
        }
        return null;
    }

    private static int a(String[] stringArray, int n2, int n3) {
        if ((n2 = n3 - n2) < 0) {
            return stringArray.length;
        }
        return n2;
    }

    private static int a(Graphics graphics, int n2, int n3, String string, int n4) {
        int n5 = bt.a(bg.a(string, "^", 11), 290);
        n5 = n5 / B > 2 ? B << 1 : n5;
        aq.a(182, 360 - n5 - 10 - n2, 290, n5);
        aq.e(0x70000000);
        aq.b(graphics, 182, 360 - n5 - 10 - n2, 290, n5);
        bt.a(graphics, bg.a(string, "^", 11), 182, 360 - n5 - 10 - n2 - n3, 290, n4);
        aq.a(0, 0, 640, 360);
        return n5;
    }

    public static boolean b(String stringArray) {
        try {
            stringArray = bg.a((String)stringArray, "^", 11);
            Integer.parseInt(stringArray[0]);
            int n2 = 0;
            n2 = Integer.parseInt(stringArray[1]);
            if (n2 < 0 || n2 > 8) {
                return false;
            }
            Integer.parseInt(stringArray[4]);
            Integer.parseInt(stringArray[5]);
            Integer.parseInt(stringArray[6]);
            Integer.parseInt(stringArray[7]);
            Integer.parseInt(stringArray[9]);
            Long.parseLong(stringArray[10]);
            return true;
        }
        catch (Exception exception) {
            bm.A(181);
            return false;
        }
    }

    static {
        E = 0;
        g = false;
        h = false;
        H = 1;
        i = new String[8][30];
        j = new String[7][10];
        k = new int[8];
        l = new int[8];
        m = new int[7];
        n = new int[7];
        o = new boolean[8];
        p = new boolean[7];
        I = new int[7];
        J = new int[7];
        q = (byte)-1;
        r = -1;
        s = (byte)-1;
        t = 0;
        u = "";
        v = -1;
        w = (byte)-1;
        x = 0;
        M = B + 27;
        N = B;
        W = new int[]{43, 42, 46, 49, 48, 45, 44, 47};
        X = new int[]{84, 82};
        Y = new int[]{82};
        Z = new int[]{84, 85, 86, 87, 83, 106, 82};
        aa = new int[]{85, 86, 87, 83, 106, 82};
        ab = new int[]{0, 13683249, 15032576, 8987135, 2408192, 1998333, 15162562, 0xFF0000, 65535};
        ac = new int[]{130, 131, 132, 133, 134, 135, 136, 137};
        ad = new int[][]{{70, 70}, {70, 69}, {70, 69}, {70, 69}, {70, 69}, {70, 69}, {70, 69}, {70, 69}};
        z = new int[]{370, 371, 372, 373, 374};
    }
}

