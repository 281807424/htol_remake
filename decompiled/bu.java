/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import java.util.Vector;
import javax.microedition.lcdui.Graphics;

public final class bu {
    private static int i;
    public static int a;
    private static Vector j;
    private static int[] k;
    private static int[] l;
    private static String[] m;
    public static String b;
    public static String c;
    public static int d;
    public static int e;
    public static int f;
    public static int[] g;
    public static String[] h;
    private static int n;
    private static i o;

    public static void a() {
        j.removeAllElements();
        m = null;
        g = null;
        h = null;
        b = null;
        c = null;
    }

    public static bw a(int n2) {
        for (int i2 = 0; i2 < j.size(); ++i2) {
            bw bw2 = (bw)j.elementAt(i2);
            if (bw2 == null || bw2.a != n2) continue;
            return bw2;
        }
        return null;
    }

    public static void a(String[] stringArray, int[] nArray, int n2) {
        a = stringArray.length;
        if (nArray.length != a) {
            bg.a(bm.A(535) + a);
            return;
        }
        l = nArray;
        i = n2;
        m = stringArray;
        k = new int[a + 1];
        int n3 = 0;
        for (n2 = 0; n2 < nArray.length; ++n2) {
            n3 += nArray[n2];
        }
        n2 = 244 / n3;
        n3 = 244 % n3 / a;
        int n4 = 120;
        bu.k[0] = 120;
        for (int i2 = 1; i2 < a + 1; ++i2) {
            bu.k[i2] = n4 += nArray[i2 - 1] * n2 + n3;
        }
    }

    public static void a(String[] stringArray, int n2) {
        bu.a(stringArray, n2, null);
    }

    public static void a(String[] stringArray, int n2, String string) {
        if (stringArray.length != a) {
            bg.a(bm.A(536) + stringArray.length);
            return;
        }
        bw bw2 = new bw();
        new bw().b = stringArray;
        bw2.a = n2;
        bw2.c = string;
        j.addElement(bw2);
    }

    public static void b() {
        bm.j(j.size(), 4);
    }

    public static void a(Graphics graphics) {
        switch (f) {
            case 0: {
                bu.b(graphics);
                return;
            }
            case 1: {
                bu.b(graphics);
                graphics.setClip(0, 0, 640, 360);
                ay.a(graphics, h, 180, n, true, true);
            }
        }
    }

    private static void b(Graphics graphics) {
        Object object;
        int n2;
        int n3;
        ay.a(graphics, 2, 3, b, true, false);
        Graphics graphics2 = graphics;
        int n4 = 0;
        n4 = bm.dW;
        bm.D.a(graphics2, 50, 65, 0, 0);
        bm.D.a(graphics2, 56, 65, 0, 0);
        ay.a(graphics2, cr.au, 2, 400, 105, 200, true, false, false);
        bz.a(graphics2, -597591, -13032703, 4);
        bz.a(graphics2, c, 385, 67, 1);
        for (n3 = 0; n3 < a; ++n3) {
            n2 = k[n3] + (k[n3 + 1] - k[n3]) / 2;
            bz.a(graphics2, m[n3], n2 + 65, 112, 1);
        }
        ay.a(graphics2, 185, 162, 244, 116, 4, l);
        ay.b(graphics2, k[0] + 65, 162 + n4 * 29, 244, 29);
        bm.X = 0;
        int n5 = 0;
        n4 = 0;
        n5 = bm.ay();
        n4 = bm.dY * bm.dZ;
        bz.a(graphics, 16179625, 16772818, -1);
        bm.a(graphics, 0, 0, 0, true, 300, 150, 290);
        for (n3 = 0; n3 < 4; ++n3) {
            n2 = n4 + n3;
            if (n2 >= j.size() || (object = (bw)j.elementAt(n2)) == null) continue;
            for (n2 = 0; n2 < a; ++n2) {
                int n6;
                if (n2 == 0 && i != 1) {
                    n6 = 0;
                    n6 = 162 + n3 * 29 + 3;
                    if (i == 0) {
                        i i2 = i.a(((bw)object).a);
                        if (i2 == null) continue;
                        i2.a(graphics, 199, n6, true);
                        continue;
                    }
                    if (i == 2) {
                        int n7 = Integer.parseInt(((bw)object).b[n2], 10);
                        bm.a(ck.b(25), graphics, n7, 199, n6, 0);
                        continue;
                    }
                    if (i != 3) continue;
                    n6 = 162 + n3 * 29 + 3;
                    int n8 = Integer.parseInt(((bw)object).b[n2], 10);
                    ck.b(27).a(graphics, n8, 189, n6, 0);
                    continue;
                }
                n6 = k[n2] + (k[n2 + 1] - k[n2]) / 2;
                if (n2 % 2 == 1) {
                    bz.a(graphics, ((bw)object).b[n2], n6 + 65, 162 + n3 * 29 + 3 + 3, 1);
                    continue;
                }
                if (bm.dW == n3) {
                    ay.a(graphics, ((bw)object).b[n2], n6 + 65 - 51, 162 + n3 * 29 + 3 + 3, 103, 100, 2, (byte)1, true, 0);
                    continue;
                }
                ay.a(graphics, ((bw)object).b[n2], n6 + 65 - 51, 162 + n3 * 29 + 3 + 3, 103, 100, 2, (byte)1, false, 0);
            }
        }
        if (n5 >= 0) {
            if (n5 >= j.size()) {
                return;
            }
            bw bw2 = (bw)j.elementAt(n5);
            if (bw2 == null) {
                return;
            }
            String string = bm.A(537);
            if (bw2.c != null) {
                string = bw2.c;
            } else {
                bm.bn.l(e, bw2.a);
            }
            object = bf.a(string, 120);
            ay.a(graphics, string, (int[])object, 475, 158, 140, 123, 0, 7, 16, false);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static void c() {
        switch (f) {
            case 0: {
                int n2 = bm.b(4, 185, 162, 244, 29);
                if (n2 != -1) {
                    if (bm.dW == n2) {
                        bm.dB = true;
                    } else {
                        bm.dW = n2;
                        bm.X = 3;
                    }
                }
                boolean bl2 = false;
                int n3 = 0;
                bl2 = bm.v(196608);
                n3 = bm.ay();
                if (bl2) {
                    bw bw2;
                    if (n3 < j.size() && (bw2 = (bw)j.elementAt(n3)) != null) {
                        if (i == 0) {
                            o = i.a(bw2.a);
                            if (o == null) return;
                            if (bu.o.c == 3001 || o.e()) {
                                String[] stringArray = new String[3];
                                h = stringArray;
                                stringArray[0] = bm.d(0, 381);
                                bu.h[1] = bm.d(0, 224);
                                bu.h[2] = bm.d(0, 3);
                            } else {
                                String[] stringArray = new String[6];
                                h = stringArray;
                                stringArray[0] = bm.d(0, 381);
                                bu.h[1] = bm.d(0, 224);
                                bu.h[2] = bm.d(0, 382);
                                bu.h[3] = bm.d(0, 383);
                                bu.h[4] = bm.d(0, 384);
                                bu.h[5] = bm.d(0, 3);
                            }
                        }
                        if (h != null) {
                            f = 1;
                        }
                        n = 0;
                    }
                } else if (bm.v(262144)) {
                    bu.a();
                    bm.X = 1;
                    bm.H();
                }
                bm.g(20, 180);
                bm.b(true);
                return;
            }
            case 1: {
                int n4 = h.length;
                bm.g(20, 180);
                int n5 = 0;
                int n6 = n4;
                n6 = bm.f(n6, 0);
                if (n6 != -1) {
                    if (n == n6) {
                        bm.dB = true;
                    } else {
                        n = n6;
                        bm.X = 3;
                    }
                }
                n6 = n4;
                n5 = n6 - 1;
                if (bm.v(16384)) {
                    n = n - 1 < 0 ? n5 : n - 1;
                } else if (bm.v(32768)) {
                    int n7 = n = n + 1 > n5 ? 0 : n + 1;
                }
                if (bm.v(196608)) {
                    if (i == 1 || i == 2 || i == 3) {
                        bw bw3 = (bw)j.elementAt(bm.ay());
                        if (bw3 != null && g[n] != 0) {
                            bm.bn.a(d, e, bw3.a, g[n], (int)cv.s);
                        }
                    } else if (i == 0 && h != null && h.length >= 2 && n >= 0 && n < h.length) {
                        String string = h[n];
                        if (string == bm.d(0, 224)) {
                            bu.a();
                            bm.l(59);
                            ab.a(true);
                        } else if (string == bm.d(0, 382)) {
                            bm.l(59);
                            ab.a(false, true);
                        } else if (string == bm.d(0, 383)) {
                            if (o.f()) {
                                o.a(!o.f());
                                bm.bn.N(bu.o.c);
                            }
                        } else if (string == bm.d(0, 381)) {
                            if (o == null) return;
                            if (bm.cb.J == 0 || bm.cb.J == 7) {
                                if (i.c(o) && bm.bq.size() != 0) {
                                    i.p = bu.o.c;
                                    i.a(cr.aV);
                                    bm.l(71);
                                } else if (o.c()) {
                                    cn.a(bm.A(538), 21, bu.o.c);
                                } else {
                                    bm.cb.a(o);
                                }
                            } else if (bu.o.h <= cr.aJ && !o.j()) {
                                cr.al = o;
                            }
                        } else if (string == bm.d(0, 384)) {
                            bm.bn.E();
                        }
                    }
                    bm.X = 3;
                    f = 0;
                    return;
                }
                if (!bm.v(262144)) return;
                bm.X = 3;
                f = 0;
            }
        }
    }

    public static void d() {
        bw bw2 = (bw)j.elementAt(bm.ay());
        if (bw2 == null) {
            return;
        }
        if (bw2.c == null) {
            bm.bn.l(e, bw2.a);
        }
    }

    static {
        j = new Vector();
        String[] stringArray = new String[]{bm.d(0, 224), bm.d(0, 3)};
    }
}

