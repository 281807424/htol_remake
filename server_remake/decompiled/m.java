/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import java.util.Enumeration;
import java.util.Hashtable;
import javax.microedition.lcdui.Graphics;

public final class m {
    public static int a;
    private static boolean e;
    private static String f;
    private static int g;
    public static String b;
    private static int[][][] h;
    private static int[][] i;
    private static int j;
    private static int k;
    private static int l;
    private static int m;
    private static int n;
    private static int o;
    private static int p;
    private static int q;
    private int r = 0;
    public static String[] c;
    static Hashtable d;

    private static void b() {
        if (bm.cl >= i.length) {
            bm.cl = 1;
        }
        p = i[bm.cl][1];
        o = i[bm.cl][0];
        String string = bm.d(3, h[o][p][6]);
        e = true;
        bm.b(string, 150, 8);
    }

    public m(int n2) {
        int n3 = n2;
        m m2 = this;
        this.r = n3;
        bm.X = 3;
        m2 = this;
        switch (m2.r) {
            case 0: {
                return;
            }
            case 1: {
                a = 1;
                m.b();
                this.c();
            }
        }
    }

    public final void a(Graphics graphics) {
        Object object = m2;
        switch (((m)object).r) {
            case 0: {
                int n2;
                int n3;
                m m2 = graphics;
                m2.setClip(525, 12, 66, 65);
                ay.a((Graphics)m2, 525, 12, 66, 65, 50);
                block15: for (n3 = 0; n3 < bh.z.size(); ++n3) {
                    int n4;
                    r r2 = (r)bh.z.elementAt(n3);
                    if (!r2.e()) continue;
                    switch (r2.q) {
                        case 0: {
                            bm.D.j(0);
                            n4 = 73;
                            break;
                        }
                        case 1: {
                            bm.D.j(0);
                            n4 = 72;
                            break;
                        }
                        case 2: {
                            bm.D.j(1);
                            n4 = 73;
                            break;
                        }
                        case 4: {
                            bm.D.j(1);
                            n4 = 72;
                            break;
                        }
                        default: {
                            continue block15;
                        }
                    }
                    int n5 = 0;
                    int n6 = 0;
                    n5 = r2.k * 66 / bm.bT - 2;
                    n6 = r2.l * 65 / bm.bU - 10;
                    if (n5 < 0) {
                        n5 = 0;
                    } else if (n5 > 66) {
                        n5 = 59;
                    }
                    if (n6 < 0) {
                        n6 = 0;
                    }
                    bm.D.a(n4, n5 + 525, n6 + 12, 0);
                    bm.D.j(0);
                }
                m2.setClip(526, 80, 66, 23);
                ay.a((Graphics)m2, 527, 80, 62, 23, 50);
                m2 = graphics;
                m2.setClip(525, 12, 66, 65);
                m2.setColor(0xFFFF00);
                g = g == 4 ? 2 : 4;
                m2.fillRect(525 + bm.cb.k * 66 / bm.bT - (g >> 1), 12 + bm.cb.l * 65 / bm.bU - (g >> 1), g, g);
                m2.setColor(65535);
                for (n2 = 0; n2 < bh.z.size(); ++n2) {
                    r r3 = (r)bh.z.elementAt(n2);
                    if (r3 == null || r3.h() || r3.v != null) continue;
                    m2.fillRect(525 + r3.k * 66 / bm.bT, 12 + r3.l * 65 / bm.bU, 2, 2);
                }
                for (n2 = 0; n2 < bh.y.size(); ++n2) {
                    cr cr2 = (cr)bh.y.elementAt(n2);
                    if (cr2 == null || cr2.o() || cr2.B) continue;
                    if (cr2.k()) {
                        if (bm.bj % 5 >= 3) continue;
                        m2.setColor(65280);
                        m2.fillRect(525 + cr2.k * 66 / bm.bT, 12 + cr2.l * 65 / bm.bU, 2, 2);
                        continue;
                    }
                    if (bm.D(128) && bh.a(cr2) || cr.c(cr2)) {
                        m2.setColor(0xFF0000);
                        m2.fillRect(525 + cr2.k * 66 / bm.bT, 12 + cr2.l * 65 / bm.bU, 2, 2);
                        continue;
                    }
                    if (!bm.D(256) || bh.a(cr2)) continue;
                    m2.setColor(0xFFFF00);
                    m2.fillRect(525 + cr2.k * 66 / bm.bT, 12 + cr2.l * 65 / bm.bU, 2, 2);
                }
                for (n2 = 0; n2 < bm.ca.size(); ++n2) {
                    h h2 = (h)bm.ca.elementAt(n2);
                    if (h2.a != 4 || h2 == null) continue;
                    bv bv2 = (bv)h2;
                    if (!bv2.v) continue;
                    if (bv2.w == 1) {
                        m2.setColor(16560208);
                    } else {
                        m2.setColor(0xFFF0F0);
                    }
                    int n7 = 525 + bv2.k * 66 / bm.bT;
                    n3 = 12 + bv2.l * 65 / bm.bU;
                    ay.c((Graphics)m2, n7, n3, (bm.bj >> 1) % 2);
                }
                if (bm.D(128) || bm.D(256) || bm.D(1024)) {
                    for (n2 = 0; n2 < bh.x.size(); ++n2) {
                        ce ce2 = (ce)bh.x.elementAt(n2);
                        if (ce2 == null || ce2.o()) continue;
                        if (bm.D(512) && !bh.a(ce2)) {
                            m2.setColor(65280);
                            m2.fillRect(525 + ce2.k * 66 / bm.bT, 12 + ce2.l * 65 / bm.bU, 2, 2);
                        }
                        if (!bm.D(128) && !bm.D(1024) || !bh.a(ce2)) continue;
                        m2.setColor(0xFF0000);
                        m2.fillRect(525 + ce2.k * 66 / bm.bT, 12 + ce2.l * 65 / bm.bU, 2, 2);
                    }
                }
                if (d != null && !d.isEmpty()) {
                    Enumeration enumeration = d.elements();
                    while (enumeration.hasMoreElements()) {
                        ch ch2 = (ch)enumeration.nextElement();
                        m2.setColor(ch2.c);
                        m2.fillRect(525 + ch2.a * 66 / bm.bT, 12 + ch2.b * 65 / bm.bU, 2, 2);
                    }
                }
                m2 = graphics;
                m2.setClip(0, 0, 640, 360);
                bz.a((Graphics)m2, 0xFFF000, -16777216, 4);
                object = bm.cI;
                if (bm.D(4096)) {
                    if (bm.cV >= 0 && bm.cV < bn.a.length) {
                        object = "[" + bn.a[bm.cV] + "]" + (String)object;
                    } else {
                        bg.a(bm.A(734) + bm.cV);
                    }
                }
                ay.a((Graphics)m2, (String)object, 527, 82, 62, 21, 2, (byte)0, true, 0);
                return;
            }
            case 1: {
                object = graphics;
                switch (a) {
                    case 1: {
                        ay.a((Graphics)object, 2, 3, b, false);
                        m.b((Graphics)object);
                        bm.a((Graphics)object, 20, 180);
                        bm.X = 0;
                        m.a((Graphics)object, j, k);
                        return;
                    }
                    case 2: {
                        if (bm.X != 0) {
                            ay.a(bm.W, true, 2, -1, b);
                            m.b(bm.W);
                            bm.X = 0;
                        }
                        object.drawImage(bm.V, 0, 0, 0);
                        m.a((Graphics)object, j, k);
                        m m3 = object;
                        bm.a((Graphics)m3, 20, 180);
                        String[] stringArray = new String[]{bm.A(735), bm.A(262)};
                        ay.a((Graphics)m3, bm.d(0, 389), stringArray, q, 230, true, bm.A(249), true);
                        return;
                    }
                    case 4: {
                        if (bm.X != 0) {
                            ay.a(bm.W, true, 2, -1, b);
                            m.b(bm.W);
                            bm.X = 0;
                        }
                        object.drawImage(bm.V, 0, 0, 0);
                        m.a((Graphics)object, j, k);
                        bm.a((Graphics)object, bm.d(0, 162), f);
                    }
                }
            }
        }
    }

    public final void a() {
        if (!bm.V()) {
            return;
        }
        m m2 = this;
        switch (m2.r) {
            case 0: {
                return;
            }
            case 1: {
                this.c();
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void c() {
        int n2;
        block53: {
            switch (a) {
                case 1: {
                    Object object = null;
                    n2 = 150 + h[o][p][0] + j - 35;
                    int n3 = 83 + h[o][p][1] + k - 7;
                    object = bm.c(2, n2 - 30, n3 - 15, 43, 43, 44);
                    boolean bl2 = false;
                    for (int i2 = 0; i2 < 2; ++i2) {
                        if (!bm.a((int[])object[i2])) continue;
                        if (0 == i2) {
                            p = h[o][p][4] == -1 ? p : h[o][p][4];
                            bl2 = true;
                            continue;
                        }
                        if (1 != i2) continue;
                        p = h[o][p][5] == -1 ? p : h[o][p][5];
                        bl2 = true;
                        if (p != 30) continue;
                        e = true;
                    }
                    if (bm.e(n2 + 13, n3 - 15, 44, 43)) {
                        bm.X = 1;
                        a = 2;
                    } else if (bm.e(n2 + 13, n3 - 15 - 13 - 30, 44, 43)) {
                        p = h[o][p][2] == -1 ? p : h[o][p][2];
                        bl2 = true;
                        if (p == 30) {
                            e = true;
                        }
                    } else if (bm.e(n2 + 13, n3 - 15 + 13 + 30, 44, 43)) {
                        p = h[o][p][3] == -1 ? p : h[o][p][3];
                        bl2 = true;
                        if (p == 26) {
                            e = true;
                        }
                    }
                    if (bl2) {
                        String string = bm.d(3, h[o][p][6]);
                        bm.b(string, 150, 8);
                    }
                    bm.g(20, 180);
                    if (bm.v(196608) || bm.am()) {
                        bm.X = 1;
                        a = 2;
                        break;
                    }
                    if (bm.v(262144) || bm.an()) {
                        bm.X = 1;
                        bm.H();
                        break block53;
                    } else {
                        bm.a(true);
                        boolean bl3 = false;
                        if (bm.v(16388)) {
                            p = h[o][p][2] == -1 ? p : h[o][p][2];
                            bl3 = true;
                            if (p == 30) {
                                e = true;
                            }
                        }
                        if (bm.v(33024)) {
                            p = h[o][p][3] == -1 ? p : h[o][p][3];
                            bl3 = true;
                            if (p == 26) {
                                e = true;
                            }
                        }
                        if (bm.v(4112)) {
                            p = h[o][p][4] == -1 ? p : h[o][p][4];
                            bl3 = true;
                        }
                        if (bm.v(8256)) {
                            p = h[o][p][5] == -1 ? p : h[o][p][5];
                            bl3 = true;
                            if (p == 30) {
                                e = true;
                            }
                        }
                        if (!bl3) break;
                        String string = bm.d(3, h[o][p][6]);
                        bm.b(string, 150, 8);
                        break;
                    }
                }
                case 2: {
                    n2 = 0;
                    int n4 = 2;
                    int n5 = bm.f(2, 0);
                    if (n5 != -1) {
                        if (q == n5) {
                            bm.dB = true;
                        } else {
                            q = n5;
                            bm.X = 3;
                        }
                    }
                    if (bm.v(196608)) {
                        bm.X = 1;
                        switch (q) {
                            case 0: {
                                if (bm.dn) {
                                    a = 4;
                                    f = bm.A(780);
                                    break;
                                }
                                bm.cb.s();
                                cr.aD = false;
                                bm.s();
                                n4 = h[o][p][6];
                                bm.co = true;
                                if (bm.ck != n4) {
                                    bm.t = false;
                                    bm.bn.d(n4);
                                    bm.E(3);
                                    if (bm.cp) {
                                        bm.O();
                                    }
                                    a = 3;
                                    bm.H();
                                    break;
                                }
                                bm.c(bm.A(782));
                            }
                            case 1: {
                                a = 1;
                                break;
                            }
                        }
                        bm.dB = false;
                        break block53;
                    } else {
                        bm.g(20, 180);
                        if (bm.v(262144)) {
                            a = 1;
                            break;
                        }
                        if (bm.v(16388)) {
                            if (q <= 0) {
                                q = 1;
                                break;
                            }
                            --q;
                            break;
                        }
                        if (!bm.v(33024)) break;
                        if (q >= 1) {
                            q = 0;
                            break;
                        }
                        ++q;
                        break;
                    }
                }
                case 3: {
                    break;
                }
                case 4: {
                    if (!bm.v(196608)) break;
                    bm.X = 3;
                    a = 1;
                    m.b();
                }
            }
            bm.dB = false;
        }
        switch (a) {
            case 1: {
                l = -(h[o][p][0] - 143);
                m = -(h[o][p][1] - 110);
                int n6 = ck.b(20).f(0) - 18;
                n2 = ck.b(20).g(0);
                if (l > 0) {
                    l = 0;
                } else if (l - 286 < -n6) {
                    l = -(n6 - 286);
                }
                if (m > 0) {
                    m = 0;
                } else if (m - 220 < -n2) {
                    m = -(n2 - 220);
                }
                if (e) {
                    j = l;
                    k = m;
                    e = false;
                    return;
                }
                if (j > l ? (j -= n) <= l : j >= l || (j += n) >= l) {
                    j = l;
                }
                if (!(k > m ? (k -= n) <= m : k >= m || (k += n) >= m)) return;
                k = m;
                return;
            }
        }
    }

    private static void b(Graphics graphics) {
        graphics.setClip(150, 83, 286, 220);
        graphics.setClip(0, 0, 640, 360);
        ay.a(graphics, 447, 82, 180, 220, false, "", false);
        ay.a(graphics, 178, 50, 230, 32, bm.A(733));
        ay.a(graphics, 457, 50, 160, 32, bm.A(736));
    }

    private static void a(Graphics graphics, int n2, int n3) {
        graphics.setClip(150, 83, 286, 220);
        switch (a) {
            case 1: {
                bz.a(graphics, -597591, -13032703, 4);
                if (p >= 30) {
                    ck.b(20).a(graphics, 5, n2 + 150, n3 + 83, 0, 0, 0);
                    for (int i2 = 30; i2 < h[0].length; ++i2) {
                        if (h[0][i2][0] == 0 || h[0][i2][1] == 1) continue;
                        int n4 = c[i2].indexOf("Lv");
                        String string = c[i2].substring(0, n4 > 0 && n4 < c[i2].length() ? n4 : c[i2].length()).trim();
                        bz.a(graphics, string, h[0][i2][0] + 150 + n2 - (bz.a(string) >> 1), h[0][i2][1] + 83 + n3 - 25, 0);
                    }
                } else {
                    ck.b(20).a(graphics, 0, n2 + 150, n3 + 83, 0, 0, 0);
                    for (int i3 = 0; i3 < 30; ++i3) {
                        if (h[0][i3][0] == 0 || h[0][i3][1] == 1) continue;
                        int n5 = c[i3].indexOf("Lv");
                        String string = c[i3].substring(0, n5 > 0 && n5 < c[i3].length() ? n5 : c[i3].length()).trim();
                        bz.a(graphics, string, h[0][i3][0] + 150 + n2 - (bz.a(string) >> 1), h[0][i3][1] + 83 + n3 - 25, 0);
                    }
                }
                bm.I.a(17);
                bm.I.c();
                bm.I.a(graphics, 150 + h[o][p][0] + n2, 83 + h[o][p][1] + n3, 0);
                graphics.setClip(0, 0, 640, 640);
                bm.D.a(graphics, 100, 150 + h[o][p][0] + n2, 83 + h[o][p][1] + n3, 0);
                break;
            }
            case 2: {
                if (p >= 30) {
                    ck.b(20).a(graphics, 5, n2 + 150, n3 + 83, 0, 0, 0);
                    break;
                }
                ck.b(20).a(graphics, 0, n2 + 150, n3 + 83, 0, 0, 0);
            }
        }
        graphics.setClip(150, 83, 286, 220);
        graphics.setClip(0, 0, 640, 360);
        bz.a(graphics, 0, -13870883, -1);
        if (o == 0) {
            bm.a(graphics, 456, 91, true);
        }
    }

    static {
        e = false;
        g = 4;
        h = new int[][][]{new int[][]{{0, 0, -1, -1, -1, -1, 0}, {255, 447, 2, -1, 2, -1, 1}, {177, 418, 3, 1, 14, 1, 2}, {117, 372, 4, 2, 14, 15, 3}, {77, 311, 6, 3, -1, 5, 4}, {177, 321, 6, 15, 4, -1, 5}, {132, 252, 9, 5, 4, 7, 6}, {207, 211, 19, 6, 6, -1, 7}, {117, 178, 10, 6, 9, 7, 8}, {55, 224, 10, 6, -1, 8, 9}, {60, 173, 24, 8, 9, 11, 10}, {145, 162, 12, 8, 10, 19, 11}, {82, 98, 24, 11, 10, 24, 12}, {132, 252, 6, 6, 6, 6, 13}, {69, 389, 4, 2, -1, 2, 14}, {206, 344, 5, 2, 3, -1, 15}, {69, 245, 6, 6, 6, 6, 16}, {69, 245, 6, 6, 6, 6, 17}, {69, 245, 6, 6, 6, 6, 18}, {240, 171, 21, 7, 11, 20, 19}, {321, 145, 23, 19, 19, -1, 20}, {196, 112, 22, 19, 24, 22, 21}, {257, 83, 26, 21, 24, 23, 22}, {346, 77, 26, 20, 22, -1, 23}, {126, 89, 27, 12, 25, 22, 24}, {65, 56, 27, 24, -1, 24, 25}, {238, 31, 30, 22, 27, 30, 26}, {142, 36, 26, 24, 25, 26, 27}, {220, 127, 19, 19, 19, 19, 28}, {194, 64, 27, 21, 24, 22, 29}, {325, 450, 31, 26, 31, -1, 30}, {308, 402, 37, 30, 32, -1, 31}, {216, 415, 33, -1, -1, 31, 32}, {202, 370, 38, 32, 34, 37, 33}, {131, 362, 36, -1, 35, 33, 34}, {52, 327, -1, 34, -1, 34, 35}, {99, 284, -1, 34, -1, 40, 36}, {297, 370, 39, 31, 38, -1, 37}, {250, 331, 40, 33, 40, 37, 38}, {326, 308, 42, 37, 43, -1, 39}, {194, 296, 44, 38, 41, -1, 40}, {165, 238, -1, 40, -1, 43, 41}, {347, 252, 46, 39, 43, -1, 42}, {262, 252, -1, 39, 41, 42, 43}, {220, 234, 48, 40, 41, 45, 44}, {279, 187, 47, 44, 48, 46, 45}, {353, 183, -1, 42, 45, -1, 46}, {253, 146, -1, 44, 48, 45, 47}, {178, 177, 49, 44, 49, 47, 48}, {127, 125, -1, 48, -1, 48, 49}}, new int[0][]};
        i = new int[][]{{0, 0}, {0, 1}, {0, 2}, {0, 3}, {0, 4}, {0, 5}, {0, 6}, {0, 7}, {0, 8}, {0, 9}, {0, 10}, {0, 11}, {0, 12}, {0, 13}, {0, 14}, {0, 15}, {0, 16}, {0, 17}, {0, 18}, {0, 19}, {0, 20}, {0, 21}, {0, 22}, {0, 23}, {0, 24}, {0, 25}, {0, 26}, {0, 27}, {0, 28}, {0, 29}, {0, 30}, {0, 31}, {0, 32}, {0, 33}, {0, 34}, {0, 35}, {0, 36}, {0, 37}, {0, 38}, {0, 39}, {0, 40}, {0, 41}, {0, 42}, {0, 43}, {0, 44}, {0, 45}, {0, 46}, {0, 47}, {0, 48}, {0, 49}};
        j = 0;
        k = 0;
        l = 0;
        m = 0;
        n = 10;
        o = 0;
        p = 0;
        q = 0;
        d = new Hashtable();
    }
}

