/*
 * Decompiled with CFR 0.152.
 */
public final class cl {
    public static String[] a;
    public static String[] b;
    public static String[] c;
    public static String[] d;
    public static String[] e;
    public static String[] f;
    public static byte g;
    public static String h;
    public static byte i;
    public static byte j;
    public static byte k;
    public static byte l;
    public static byte m;
    public static byte n;
    public static int[] o;
    public static String[] p;
    public static long[] q;
    public static int[] r;
    public static int[] s;
    public static int[] t;
    public static String[] u;
    public static short[] v;
    public static byte[] w;
    public static byte[] x;
    public static byte[] y;

    public static void a() {
        String[] stringArray = new String[8];
        a = stringArray;
        stringArray[0] = bm.A(142);
        int n2 = 0;
        for (int i2 = 1; i2 < 8; ++i2) {
            cl.a[i2] = i2 == 1 ? n2 + 1 + "~" + (n2 + 10 - 1) : (i2 == 7 ? "60\u7d1a\u4ee5\u4e0a" : n2 + "~" + (n2 + 10 - 1));
            n2 += 10;
        }
    }

    public static byte a(int n2) {
        switch (n2) {
            case 0: {
                return -1;
            }
            case 1: {
                return 0;
            }
            case 2: {
                return 1;
            }
            case 3: {
                return 2;
            }
            case 4: {
                return 3;
            }
            case 5: {
                return 4;
            }
            case 6: {
                return 5;
            }
            case 7: {
                return 6;
            }
        }
        return -1;
    }

    public static byte b(int n2) {
        switch (n2) {
            case 0: {
                return -1;
            }
            case 1: {
                return 0;
            }
            case 2: {
                return 1;
            }
            case 3: {
                return 2;
            }
        }
        return -1;
    }

    public static byte c(int n2) {
        switch (n2) {
            case 0: {
                return -1;
            }
            case 1: {
                return 0;
            }
            case 2: {
                return 1;
            }
            case 3: {
                return 2;
            }
            case 4: {
                return 3;
            }
            case 5: {
                return 6;
            }
            case 6: {
                return 8;
            }
        }
        return -1;
    }

    public static byte d(int n2) {
        switch (n2) {
            case 0: {
                return -1;
            }
            case 1: {
                return 4;
            }
            case 2: {
                return 5;
            }
        }
        return -1;
    }

    public static byte e(int n2) {
        switch (n2) {
            case 0: {
                return -1;
            }
            case 1: {
                return 0;
            }
            case 2: {
                return 1;
            }
        }
        return -1;
    }

    public static void b() {
        h = "";
        i = 0;
        j = (byte)-1;
        k = 0;
        l = (byte)-1;
        m = (byte)-1;
        n = (byte)-1;
    }

    static {
        g = 0;
        h = "";
        i = 0;
        j = 0;
        k = 0;
        l = 0;
        m = 0;
        n = 0;
    }
}

