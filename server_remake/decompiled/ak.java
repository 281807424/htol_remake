/*
 * Decompiled with CFR 0.152.
 */
public final class ak {
    public static int a;
    public static int b;
    public static long c;
    public static long d;
    private static boolean g;
    public static int[] e;
    private static int[] h;
    private static int[] i;
    public static boolean f;

    public static void a(int n2, int n3) {
        b = n2;
        a = n3;
        f = true;
    }

    public static String a() {
        StringBuffer stringBuffer = new StringBuffer(150);
        switch (b) {
            case 1: {
                if (ak.c(1)) {
                    stringBuffer.append(Integer.toString(br.a) + ",");
                }
                if (ak.c(2)) {
                    stringBuffer.append(bm.by + ",");
                }
                if (ak.c(4)) {
                    stringBuffer.append(Integer.toString(11400) + ",");
                }
                if (ak.c(8)) {
                    stringBuffer.append(Integer.toString(7) + ",");
                }
                if (ak.c(16)) {
                    stringBuffer.append(am.f.a + ",");
                }
                if (ak.c(32)) {
                    stringBuffer.append(Integer.toString(cs.o[0]) + ",");
                }
                String string = "";
                try {
                    string = bm.bn.f.e.getLocalAddress();
                }
                catch (Exception exception) {
                    Exception exception2 = exception;
                    exception.printStackTrace();
                }
                finally {
                    if (ak.c(64)) {
                        stringBuffer.append(string + ",");
                    }
                }
            }
            case 2: {
                if (ak.c(128)) {
                    stringBuffer.append(cs.b + ",");
                }
                if (ak.c(256)) {
                    stringBuffer.append(bm.cb == null ? "" : bm.cb.E + ",");
                }
                if (ak.c(512)) {
                    stringBuffer.append(bg.a(aq.i()) + ",");
                }
                if (ak.c(1024)) {
                    stringBuffer.append(Integer.toString(ag.a(0)) + ",");
                }
                if (ak.c(2048)) {
                    stringBuffer.append(Integer.toString(ag.a(3)) + ",");
                }
                if (ak.c(4096)) {
                    stringBuffer.append(Integer.toString(ag.a(5)) + ",");
                }
                if (ak.c(8192)) {
                    stringBuffer.append(Integer.toString(ag.a(4)) + ",");
                }
                if (ak.c(16384)) {
                    stringBuffer.append(Integer.toString(ag.a(6)) + ",");
                }
                if (ak.c(32768)) {
                    stringBuffer.append(Integer.toString(bm.cb.F) + ",");
                }
                if (ak.c(65536)) {
                    stringBuffer.append(Integer.toString(bm.cb.k) + ",");
                }
                if (ak.c(131072)) {
                    stringBuffer.append(Integer.toString(bm.cb.l) + ",");
                }
                if (ak.c(262144)) {
                    stringBuffer.append(Integer.toString(e[0]) + ",");
                }
                if (ak.c(524288)) {
                    stringBuffer.append(Integer.toString(e[1]) + ",");
                }
                if (ak.c(0x100000)) {
                    stringBuffer.append(Integer.toString(e[2]) + ",");
                }
                if (ak.c(0x200000)) {
                    stringBuffer.append(Integer.toString(e[3]) + ",");
                }
                if (ak.c(0x400000)) {
                    stringBuffer.append(Integer.toString(e[5]) + ",");
                }
                if (ak.c(0x800000)) {
                    stringBuffer.append(Integer.toString(e[7]) + ",");
                }
                if (ak.c(0x1000000)) {
                    stringBuffer.append(Integer.toString(e[4]) + ",");
                }
                if (ak.c(0x2000000)) {
                    stringBuffer.append(Integer.toString(e[6]) + ",");
                }
                if (ak.c(0x4000000)) {
                    stringBuffer.append(Integer.toString(e[8]) + ",");
                }
                if (ak.c(0x8000000)) {
                    stringBuffer.append(Integer.toString(aq.e) + ",");
                }
                if (ak.c(0x10000000)) {
                    stringBuffer.append(Integer.toString(bm.dr) + ",");
                }
                long l2 = Runtime.getRuntime().freeMemory() / 1024L;
                if (ak.c(0x20000000)) {
                    stringBuffer.append(Long.toString(l2) + ",");
                }
                if (ak.c(0x40000000)) {
                    stringBuffer.append(Integer.toString(ck.a * 100 / 350) + ",");
                }
                if (!ak.c(Integer.MIN_VALUE)) break;
                stringBuffer.append(Long.toString(c + d) + ",");
            }
        }
        return stringBuffer.toString();
    }

    public static void a(int n2) {
        for (int i2 = 0; i2 < h.length && h[i2] != n2; ++i2) {
            if (h[i2] != 0) continue;
            ak.h[i2] = n2;
            return;
        }
    }

    public static byte b() {
        byte by2 = 0;
        for (int i2 = 0; i2 < h.length && h[i2] != 0; ++i2) {
            by2 = (byte)(by2 + 1);
        }
        return by2;
    }

    public static void b(int n2) {
        for (int i2 = 0; i2 < i.length && i[i2] != n2; ++i2) {
            if (i[i2] != 0) continue;
            ak.i[i2] = n2;
            return;
        }
    }

    public static byte c() {
        byte by2 = 0;
        for (int i2 = 0; i2 < i.length && i[i2] != 0; ++i2) {
            by2 = (byte)(by2 + 1);
        }
        return by2;
    }

    public static void d() {
        int n2;
        b = 0;
        a = 0;
        for (n2 = 0; n2 < e.length; ++n2) {
            ak.e[n2] = 0;
        }
        for (n2 = 0; n2 < i.length; ++n2) {
            ak.i[n2] = 0;
        }
        for (n2 = 0; n2 < h.length; ++n2) {
            ak.h[n2] = 0;
        }
        f = false;
    }

    private static boolean c(int n2) {
        return (a & n2) != 0;
    }

    static {
        g = false;
        e = new int[9];
        h = new int[3];
        i = new int[20];
    }
}

