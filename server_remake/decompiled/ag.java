/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import java.util.Vector;
import javax.microedition.lcdui.Graphics;

public final class ag {
    public static int[] a;
    private static int[] p;
    public static int b;
    public static int c;
    private static int q;
    public static int d;
    private static int r;
    public static byte[] e;
    public static String[] f;
    public static String[][] g;
    public static String[][] h;
    private static int[] s;
    private static int[] t;
    private static int[] u;
    public static int[] i;
    private static int v;
    private static String w;
    private static int x;
    private static int y;
    private static int z;
    private static int A;
    private static int B;
    public static byte[] j;
    public static String[][] k;
    private static String[] C;
    private static String[][] D;
    private static int E;
    public static byte l;
    private static String F;
    private static int G;
    public static String[] m;
    public static String[] n;
    public static Vector o;
    private static int H;
    private static int I;
    private static int J;

    public static void a(byte by2, int n2, int n3) {
        if (n3 < 0 || n3 >= g[n2].length) {
            a.c[by2][n2] = 0;
            return;
        }
        a.c[by2][n2] = (byte)n3;
    }

    public static short[] a(byte by2) {
        int n2 = s.length;
        short[] sArray = new short[n2];
        for (int i2 = 0; i2 < n2; ++i2) {
            int n3 = ag.a(by2, s[i2]);
            sArray[i2] = (short)(s[i2] << 8 | n3 & 0xFF);
        }
        return sArray;
    }

    public static int a(int n2) {
        return ag.a(a.b, n2);
    }

    private static int a(byte by2, int n2) {
        byte by3 = a.c[by2][n2];
        ag.a(by2, n2, by3);
        return a.c[by2][n2];
    }

    public static void a() {
        int n2 = 7;
        if (ag.a(a.b, n2) == 1 || ag.a(a.b, n2 = 7) == 3) {
            al.a = true;
            bm.P();
        } else {
            n2 = 7;
            if (ag.a(a.b, n2) == 2 || ag.a(a.b, n2 = 7) == 0) {
                al.a = false;
                al.c();
            }
        }
        n2 = 7;
        if (ag.a(a.b, n2) == 2 || ag.a(a.b, n2 = 7) == 3) {
            al.b = true;
        } else {
            n2 = 7;
            if (ag.a(a.b, n2) == 1 || ag.a(a.b, n2 = 7) == 0) {
                al.b = false;
            }
        }
        n2 = 0;
        if (ag.a(a.b, n2) >= 0 && ag.a(a.b, n2 = 0) <= 3) {
            n2 = 0;
            bm.aU = bm.aV[ag.a(a.b, n2)];
        }
        if (!al.a && !al.b) {
            al.c();
        }
    }

    public static void b() {
        a = p;
    }

    private static boolean c(int n2) {
        return n2 == 7 || n2 == 8 || n2 == 9 || n2 == 10;
    }

    private static void d(int n2) {
        b = n2;
        bm.X = 3;
    }

    public static void a(Graphics graphics) {
        switch (b) {
            case 0: {
                int n2 = ay.a(0, a);
                z = n2 + 76;
                A = 180;
                x = (640 - z >> 1) + -140;
                y = 51;
                int n3 = z = z < 152 ? 152 : z;
                if (bm.X == 3) {
                    bm.a(bm.W, true);
                    bm.X = 0;
                }
                graphics.drawImage(bm.V, 0, 0, 0);
                ay.a(graphics, 2, -1, false);
                n2 = 0;
                if (a.length > 5) {
                    n2 = c <= 4 ? 0 : c - 5 + 1;
                }
                for (int i2 = 0; i2 + n2 < a.length && i2 < 5; ++i2) {
                    bz.a(graphics, -597591, -13032703, 4);
                    ay.a(graphics, bm.d(0, a[i2 + n2]), x, 51 + i2 * 36, z, i2 + n2 == c);
                }
                if (a.length > 5) {
                    bm.G.c();
                    bm.G.a(graphics, x + (z - 13 >> 1), y - 6, 2);
                    bm.G.a(graphics, x + (z - 13 >> 1), y + A + 6, 0);
                }
                bz.a(graphics, "wapht.dkmol.com", 636, 359, 40);
                return;
            }
            case 1: {
                ag.b(graphics);
                return;
            }
            case 2: {
                ay.a(graphics, true, -1, 3, bm.d(0, 150));
                bm.D.a(1, 0, -10, 0);
                bz.a(graphics, 0xFFFFFF, -16777216, -1);
                bz.a(graphics, bm.d(0, 66), 320, 61, 17);
                bz.a(graphics, 16179625, -13870883, -1);
                bm.a(graphics, 320, 80, true, 17);
                return;
            }
            case 3: {
                ag.c(graphics);
                return;
            }
            case 4: {
                if (bm.X != 0) {
                    ay.a(bm.W, true, 2, 3, bm.d(0, 216));
                    bm.X = 0;
                }
                graphics.drawImage(bm.V, 0, 0, 0);
                String[] stringArray = new String[]{bm.A(295), bm.A(262)};
                ay.a(graphics, bm.A(428), stringArray, c, 250, true, bm.A(249), true);
                return;
            }
            case 6: {
                if (bm.X == 3) {
                    ay.a(bm.W, true, 2, 3, bm.d(0, 150));
                    bm.X = 0;
                }
                graphics.drawImage(bm.V, 0, 0, 0);
                for (int i3 = 0; i3 < t.length; ++i3) {
                    bz.a(graphics, -597591, -13032703, 4);
                }
                break;
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static void c() {
        switch (b) {
            case 0: {
                bm.ao();
                if (bm.v(16388)) {
                    c = c == 0 ? a.length - 1 : --c;
                }
                if (bm.v(33024)) {
                    c = c == a.length - 1 ? 0 : ++c;
                }
                if (!bm.v(196608)) return;
                bm.dB = false;
                switch (a[c]) {
                    case 36: {
                        ag.a(true);
                        return;
                    }
                    case 37: {
                        ag.a(false);
                        return;
                    }
                    case 13: {
                        bc.a((byte)2);
                        return;
                    }
                    case 14: {
                        ag.d(6);
                        q = 0;
                        return;
                    }
                    case 65: {
                        ag.d(1);
                        bm.b(bm.d(0, i[d]), 435, 9);
                        return;
                    }
                    case 66: {
                        StringBuffer stringBuffer = new StringBuffer(bm.d(0, r));
                        if (stringBuffer.charAt(3) == '\u7f18' && bm.bx != null) {
                            stringBuffer.insert(4, " v" + bm.bx);
                        }
                        w = stringBuffer.toString();
                        bm.b(w, 435, 15);
                        ag.d(2);
                        return;
                    }
                    case 67: {
                        ag.d(3);
                        af.a(false, 3);
                        ag.b(0);
                        return;
                    }
                    case 1: {
                        cs.a(16);
                        bm.bA = true;
                        return;
                    }
                    case 68: {
                        c = 1;
                        ag.d(4);
                        return;
                    }
                    case 12: {
                        cs.a(5);
                    }
                }
                return;
            }
            case 1: {
                ag.d();
                return;
            }
            case 2: {
                if (bm.v(262144)) {
                    ag.d(0);
                    r = 214;
                    bm.X = 3;
                }
                bm.a(true);
                return;
            }
            case 3: {
                ag.f();
                return;
            }
            case 4: {
                bm.dB = false;
                int n2 = bm.f(2, 0);
                if (n2 != -1) {
                    if (c == n2) {
                        bm.dB = true;
                    }
                    c = n2;
                }
                if (bm.v(16388)) {
                    if (c > 0) {
                        --c;
                        return;
                    }
                    c = 1;
                    return;
                }
                if (bm.v(33024)) {
                    if (c < 1) {
                        ++c;
                        return;
                    }
                    c = 0;
                    return;
                }
                if (bm.v(196640) || bm.am() || bm.dB) {
                    switch (c) {
                        case 0: {
                            bm.e(bm.av());
                            aq.f();
                            return;
                        }
                        case 1: {
                            c = 0;
                            ag.d(0);
                        }
                        default: {
                            return;
                        }
                    }
                }
                if (!bm.v(262144) && !bm.an()) return;
                c = 0;
                ag.d(0);
                return;
            }
            case 6: {
                bm.dB = false;
                int n3 = 0;
                if (bm.e(235, 65, 180, 223)) {
                    n3 = (bm.dt - 70) / 38;
                    if (n3 == q) {
                        bm.dB = true;
                    }
                    q = n3;
                }
                if (bm.v(16388)) {
                    q = q == 0 ? t.length - 1 : --q;
                    bm.X = 3;
                    return;
                }
                if (bm.v(33024)) {
                    q = q == t.length - 1 ? 0 : ++q;
                    bm.X = 3;
                    return;
                }
                if (bm.v(196608)) {
                    switch (t[q]) {
                        case 15: {
                            bc.a((byte)60);
                            break;
                        }
                        case 16: {
                            bc.a((byte)61);
                            break;
                        }
                        case 7: {
                            bc.a((byte)6);
                            break;
                        }
                        case 19: {
                            bc.a((byte)62);
                            break;
                        }
                        case 20: {
                            bc.a((byte)63);
                            break;
                        }
                        case 38: {
                            ag.d(0);
                        }
                    }
                    bm.X = 3;
                    return;
                }
                if (!bm.v(262144)) return;
                ag.d(0);
                return;
            }
            case 7: {
                cs.a(12);
                ag.d(6);
                return;
            }
            case 8: {
                cs.a(13);
                ag.d(6);
                return;
            }
            case 9: {
                cs.a(14);
                ag.d(6);
                return;
            }
            case 10: {
                cs.a(15);
                ag.d(6);
            }
        }
    }

    public static void d() {
        int n2;
        if (!bm.V()) {
            return;
        }
        Object object = null;
        int n3 = 20;
        object = new int[3][4];
        object[0][0] = 80;
        for (n2 = 0; n2 < 3; ++n2) {
            bm.d(0, u[n2]).trim();
            int n4 = 460 / u.length - 10;
            int n5 = n2 == 0 ? 0 : n2 - 1;
            object[n2][0] = object[n5][0] + n3;
            object[n2][1] = 40;
            object[n2][2] = n4;
            object[n2][3] = 40;
            n3 = n4 + 10;
        }
        bm.dB = false;
        for (n2 = 0; n2 < 3; ++n2) {
            if (!bm.a(object[n2])) continue;
            d = n2;
            bm.b(bm.d(0, i[d]), 435, 9);
            bm.X = 3;
            break;
        }
        if (bm.v(262144)) {
            if (bm.n(44)) {
                c = 0;
                bm.X = 3;
                bm.bV = true;
                bm.Y = 3;
                bm.H();
            } else {
                ag.d(0);
                r = 214;
                bm.X = 3;
            }
        }
        if (bm.v(4112)) {
            bm.X = 3;
            if (d > 0) {
                --d;
            }
            bm.b(bm.d(0, i[d]), 435, 9);
        } else if (bm.v(8256)) {
            bm.X = 3;
            if (d < 2) {
                ++d;
            }
            bm.b(bm.d(0, i[d]), 435, 9);
        } else if (bm.v(16388) || bm.e(300, 84, 40, 30)) {
            if (bm.ea > 0) {
                bm.ea -= 2;
            }
        } else if ((bm.v(33024) || bm.e(300, 268, 40, 30)) && bm.ea + 9 < (object = (Object)bf.a(bm.eb, 455))[0]) {
            bm.ea += 2;
        }
        if (100 - v <= bz.a(bm.d(0, 320))) {
            v -= 2;
            return;
        }
        v = 435;
    }

    public static void b(Graphics graphics) {
        ay.a(graphics, -1, 3, bm.d(0, 150));
        ay.a(graphics, 80, 45, 480, 240, 30, 10, 20, d, u);
        bm.X = 0;
        bz.a(graphics, 16179625, -13870883, -1);
        int[] nArray = bf.a(bm.eb, 455);
        bf.a(graphics, bm.eb, nArray, 90, 105, bm.ea, 9, 0, false);
        int n2 = bm.D.g(68);
        bm.G.c();
        if (bm.ea > 0) {
            bm.G.a(graphics, 640 - n2 >> 1, 105, 2);
        }
        if (bm.ea + 9 < nArray[0]) {
            bm.G.a(graphics, 640 - n2 >> 1, 276, 0);
        }
    }

    public static void e() {
        byte[] byArray = new byte[]{2, 2, 4, 4, 0, 0, 0, 0, 2, 0};
        for (int i2 = 0; i2 < 10; ++i2) {
            ag.j[i2] = byArray[i2];
        }
    }

    public static void b(int n2) {
        B = 0;
        G = n2;
        if (n2 == 0) {
            l = 1;
            F = bm.A(452);
            C = f;
            D = g;
            E = s.length;
            return;
        }
        if (n2 == 1) {
            l = 0;
            F = bm.A(453);
            C = new String[]{bm.A(454), bm.A(455), bm.A(456), bm.A(457), bm.A(458), bm.A(459), bm.B(285), bm.B(286), bm.B(287), bm.A(460)};
            D = new String[][]{m, m, n, n, {bm.B(70), bm.B(69)}, {bm.B(70), bm.B(69)}, {bm.A(461), bm.A(462)}, {bm.B(70), bm.B(69)}, {bm.B(288), bm.B(289), bm.B(290)}, {bm.A(463), bm.A(464)}};
            E = C.length;
        }
    }

    public static void c(Graphics graphics) {
        String string;
        bm.a(bm.W, 20, 180);
        if (bm.X == 3) {
            ay.a(bm.W, true, 2, 3, F);
            ay.a(bm.W);
            bm.X = 0;
        }
        graphics.drawImage(bm.V, 0, 0, 0);
        ay.a(graphics, bm.A(166), 68, 330, 584, 22, 333);
        if (G == 0) {
            string = bm.A(465) + a.a[l];
            int n2 = (640 - bz.a(string) - 29 >> 1) + 65;
            bz.a(graphics, string, n2, 55);
            bm.a(ck.b(25), graphics, 41, n2 + bz.a(string), 55, 0);
            int n3 = 0;
            int n4 = 0;
            switch (l) {
                case 0: {
                    n3 = 1;
                    break;
                }
                case 1: {
                    n3 = 2;
                    n4 = 1;
                }
            }
            ab.a(graphics, n2 + bz.a(string), 55, n4, 0L, n3);
            bm.E.c();
            bm.E.a(graphics, 205, 55, 0);
            bm.H.c();
            bm.H.a(graphics, 535, 55, 0);
            graphics.setColor(-597591);
            graphics.drawLine(215, 80, 540, 80);
            ag.a(graphics, 150, 85, 470, 220);
        } else if (G == 1) {
            ag.a(graphics, 160, 80, 440, 220);
        }
        bz.a(graphics, 0xFFFFFF, 0xFFFFFF, -1);
        string = null;
        string = G == 1 ? k[B][j[B]] : h[s[B]][a.c[l][s[B]]];
        ay.a(graphics, string, 320, 290, 420, 19, 2, (byte)0, true, 1);
    }

    public static void f() {
        if (!bm.V()) {
            return;
        }
        bm.g(20, 180);
        int n2 = 0;
        if (G == 1) {
            if (bm.e(160, 71, 440, 220)) {
                n2 = (bm.ds - 160) / (440 / I) * H + (bm.dt - 80 + 9) / (220 / H);
                n2 = n2 > H - 1 ? H - 1 : n2;
                if (n2 + J == B) {
                    bm.bl = 65536;
                } else {
                    B = n2 + J;
                }
                if (n2 == 0 && J > 0) {
                    --J;
                } else if (n2 == H - 1 && J <= E - H - 1) {
                    ++J;
                }
            }
        } else if (bm.e(150, 76, 470, 220)) {
            n2 = (bm.ds - 150) / (470 / I) * H + (bm.dt - 85 + 9) / (220 / H);
            n2 = n2 > H - 1 ? H - 1 : n2;
            if (n2 + J == B) {
                bm.bl = 65536;
            } else {
                B = n2 + J;
            }
            if (n2 == 0 && J > 0) {
                --J;
            } else if (n2 == H - 1 && J <= E - H - 1) {
                ++J;
            }
        } else if (bm.e(205, 36, 130, 40)) {
            bm.bl = 2048;
        } else if (bm.e(425, 36, 130, 40)) {
            bm.bl = 1024;
        }
        if (bm.v(4112) || bm.v(8256)) {
            n2 = B;
            if (n2 < H) {
                n2 = n2 + H > E - 1 ? E - 1 : n2 + H;
            } else if (n2 >= H) {
                n2 = n2 - H < 0 ? 0 : n2 - H;
            }
            int n3 = J = n2 > E - H ? E - H : n2;
            if (G == 0 && (l == 2 || l == 0)) {
                while (!ag.c(s[n2])) {
                    n2 = --n2 < 0 ? E - 1 : n2;
                }
            }
            B = n2;
        }
        if (bm.v(2048)) {
            if (G == 0) {
                l = (byte)((l = (byte)(l - 1)) < 0 ? 2 : (int)l);
                B = 0;
                if (l == 2 || l == 0) {
                    int n4 = B = ++B >= E ? 0 : B;
                    while (!ag.c(s[B])) {
                        B = ++B >= E ? 0 : B;
                    }
                }
            }
        } else if (bm.v(1024)) {
            if (G == 0) {
                l = (l = (byte)(l + 1)) >= 3 ? (byte)0 : l;
                B = 0;
                if (l == 2 || l == 0) {
                    int n5 = B = ++B >= E ? 0 : B;
                    while (!ag.c(s[B])) {
                        B = ++B >= E ? 0 : B;
                    }
                }
            }
        } else if (bm.v(16388)) {
            int n6 = B = --B < 0 ? E - 1 : B;
            if (G == 0 && (l == 2 || l == 0)) {
                while (!ag.c(s[B])) {
                    B = --B < 0 ? E - 1 : B;
                }
            }
            if (B < J || B >= J + H * I) {
                if (B == E - 1) {
                    J = B - H * I + 1;
                }
                if (B >= 0 && B <= J) {
                    J = B;
                    return;
                }
            }
        } else if (bm.v(33024)) {
            int n7 = B = ++B >= E ? 0 : B;
            if (G == 0 && (l == 2 || l == 0)) {
                while (!ag.c(s[B])) {
                    B = ++B >= E ? 0 : B;
                }
            }
            if (B < J || B >= J + H * I) {
                J = Math.max(0, B - H * I + 1);
                return;
            }
        } else {
            if (bm.v(65536)) {
                if (G == 0) {
                    n2 = s[B];
                    if (a.c[l][n2] + 1 < D[n2].length) {
                        byte[] byArray = a.c[l];
                        int n8 = n2;
                        byArray[n8] = (byte)(byArray[n8] + 1);
                        return;
                    }
                    a.c[ag.l][n2] = 0;
                    return;
                }
                if (j[B] + 1 < D[B].length) {
                    int n9 = B;
                    j[n9] = (byte)(j[n9] + 1);
                    return;
                }
                ag.j[ag.B] = 0;
                return;
            }
            if (bm.v(131072)) {
                if (G == 0) {
                    bm.a(27, bm.A(295), bm.A(466), 0);
                    return;
                }
                bm.bn.L();
                c = 0;
                bm.H();
                bm.X = 3;
                bm.bV = true;
                bm.Y = 3;
                return;
            }
            if (bm.v(262144)) {
                if (G == 0) {
                    af.a(false, 3);
                } else {
                    bm.bn.L();
                }
                c = 0;
                bm.X = 3;
                bm.bV = true;
                bm.Y = 3;
                bm.H();
            }
        }
    }

    public static void a(boolean bl2) {
        if (bm.bn != null) {
            bm.bn.J();
            bm.bn = null;
        }
        if (!cs.b()) {
            bl2 = false;
        }
        bm.r = bl2;
        if (bl2) {
            af.a(false, 5);
            cs.a(3);
            return;
        }
        cs.c();
    }

    public static void a(cr cr2) {
        if (cr2 == null || o.contains(cr2)) {
            return;
        }
        while (o.size() >= bm.aV[3]) {
            o.removeElementAt(0);
        }
        o.addElement(cr2);
    }

    private static void a(Graphics graphics, int n2, int n3, int n4, int n5) {
        int n6;
        n5 = 0;
        int n7 = D.length;
        for (n6 = 0; n6 < n7; ++n6) {
            n5 = Math.max(n5, bz.a(C[n6]) + ay.a(D[n6]));
        }
        n6 = 0;
        if (G == 0) {
            n6 = s.length;
        } else if (G == 1) {
            n6 = j.length;
        }
        n5 = 0;
        n7 = 0;
        H = 6;
        I = 1;
        for (int i2 = 0; i2 < 1; ++i2) {
            int n8;
            for (int i3 = 0; i3 < 6 && (n8 = i2 * 6 + i3 + J) <= n6 - 1; ++i3) {
                bz.a(graphics, 16179625, 16179625, -1);
                n5 = n2 + i2 * n4;
                n7 = n3 + i3 * 36;
                if (B == n8) {
                    ay.a(graphics, n5, n7 - 9, n4, 36);
                    bz.a(graphics, -5041, 16179625, -1);
                }
                if (!(G != 0 || l != 2 && l != 0 || ag.c(n8))) {
                    bz.a(graphics, 0xA8A8A8, 0xA8A8A8, -1);
                }
                String string = "";
                String string2 = "";
                if (G == 0) {
                    string = C[s[n8]];
                    string2 = D[s[n8]][a.c[l][s[n8]]];
                } else if (G == 1) {
                    string = C[n8];
                    string2 = D[n8][j[n8]];
                }
                bz.a(graphics, string, n5 + 9, n3 + i3 * 36, 4);
                bz.a(graphics, string2, n5 + n4 - 9, n7, 8);
            }
        }
    }

    static {
        p = new int[]{36, 37, 1, 68};
        b = 0;
        c = 0;
        q = 0;
        d = 0;
        r = 214;
        e = new byte[]{0, 0, 0, 0, 0, 0, 0, 0};
        s = new int[]{0, 3, 5, 1, 2, 4, 6, 7, 8, 9, 10};
        t = new int[]{15, 16, 7, 19, 20, 38};
        int[] nArray = new int[]{5, 10, 18, 17};
        u = new int[]{310, 311, 417};
        i = new int[]{315, 316, 214};
        v = 435;
        B = 0;
        j = new byte[10];
        G = -1;
        o = new Vector();
    }
}

