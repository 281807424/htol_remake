/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Image;

public final class ck {
    private static boolean b = false;
    static int a;
    private static int c;
    private static Image[] d;
    private static long[] e;
    private static boolean[] f;
    private static ao[] g;
    private static byte[] h;
    private static short[] i;
    private static short j;

    ck() {
        ck.a();
    }

    public static void a(boolean n2) {
        if (n2 != 0) {
            n2 = 0;
            for (int i2 = 0; i2 < 350; ++i2) {
                if (!f[i2]) continue;
                ++n2;
            }
            a = n2;
            if (n2 < 349) {
                b = false;
                return;
            }
            b = true;
            return;
        }
        for (n2 = 0; n2 < 350; ++n2) {
            ck.f[n2] = false;
        }
    }

    static void a() {
        for (int i2 = 0; i2 < 350; ++i2) {
            ck.e[i2] = -1L;
            ck.d[i2] = null;
        }
        c = 0;
    }

    private static long b(ao ao2, int n2, int n3, int n4) {
        return ((long)ao2.b & 0xFFFFL) << 32 | (long)(n2 << 16) | (long)(n3 << 8) | (long)n4;
    }

    private static int a(long l2) {
        for (int i2 = 0; i2 < 176; ++i2) {
            if (e[i2] == l2) {
                return i2;
            }
            if (e[349 - i2] != l2) continue;
            return 349 - i2;
        }
        return -1;
    }

    static Image a(ao ao2, int n2, int n3, int n4) {
        long l2 = ck.b(ao2, n2, n3, n4);
        int n5 = ck.a(l2);
        if (n5 != -1) {
            ck.f[n5] = true;
            return d[n5];
        }
        return null;
    }

    static void a(Image image, ao ao2, int n2, int n3, int n4) {
        if (b) {
            return;
        }
        long l2 = ck.b(ao2, n2, n3, n4);
        int n5 = ck.a(l2);
        if (n5 == -1) {
            ck.e[ck.c] = l2;
            ck.d[ck.c] = image;
            if (++c >= 350) {
                c = 0;
                return;
            }
        } else {
            ck.e[n5] = l2;
            ck.d[n5] = image;
        }
    }

    public static int b() {
        return j;
    }

    public static void c() {
        for (int i2 = 0; i2 < i.length; ++i2) {
            ck.i[i2] = -1;
        }
        j = 0;
    }

    public static void a(int n2) {
        if (n2 < 0) {
            return;
        }
        if (j + 1 < i.length) {
            for (int i2 = 0; i2 < j; ++i2) {
                if (i[i2] != n2) continue;
                return;
            }
            short s2 = j;
            j = (short)(s2 + 1);
            ck.i[s2] = (short)n2;
            return;
        }
        bg.a("SPR CMD OutOfBounds");
    }

    public static void a(int n2, int n3) {
        for (int i2 = 0; i2 < n3 - n2 + 1; ++i2) {
            ck.a(n2 + i2);
        }
    }

    public static void a(int[] nArray) {
        for (int i2 = 0; i2 < nArray.length; ++i2) {
            ck.a(nArray[i2]);
        }
    }

    public static void b(int n2, int n3) {
        for (n2 = 85; n2 < 275; ++n2) {
            n3 = 1;
            for (int i2 = 0; i2 < j; ++i2) {
                if (i[i2] != n2) continue;
                n3 = 0;
                break;
            }
            if (n3 == 0) continue;
            ck.c(n2);
        }
    }

    public static void a(boolean n2, int n3) {
        if (n3 < 0 || n3 >= j) {
            return;
        }
        n2 = i[n3];
        if (n2 < 0 || n2 > g.length) {
            return;
        }
        if (n3 == 0) {
            aq.b("/5");
        }
        ck.d(n2);
        if (n3 == j - 1) {
            aq.h();
        }
    }

    public static ao b(int n2) {
        if (n2 < 0 || n2 >= g.length) {
            return null;
        }
        if (g[n2] == null) {
            aq.b("/5");
            ck.d(n2);
            aq.h();
        }
        return g[n2];
    }

    public static void c(int n2) {
        if (n2 < 0 || n2 > g.length) {
            return;
        }
        if (g[n2] != null) {
            ao ao2 = g[n2];
            for (int i2 = 0; i2 < e.length; ++i2) {
                if (e[i2] >> 32 != ((long)ao2.b & 0xFFFFL)) continue;
                ck.e[i2] = -1L;
                ck.d[i2] = null;
            }
            ck.g[n2] = null;
            ck.h[n2] = 0;
        }
    }

    private static ao d(int n2) {
        if (n2 < 0 || n2 > g.length) {
            return null;
        }
        if (g[n2] == null) {
            ao ao2 = new ao();
            byte[] byArray = aq.c(n2);
            ao2.a(byArray, 0, n2);
            ck.g[n2] = ao2;
            ck.h[n2] = 2;
        }
        return g[n2];
    }

    static {
        d = new Image[350];
        e = new long[350];
        f = new boolean[350];
        g = new ao[320];
        h = new byte[320];
        i = new short[320];
        j = 0;
    }
}

