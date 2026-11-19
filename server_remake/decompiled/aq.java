/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Canvas
 *  javax.microedition.lcdui.Display
 *  javax.microedition.lcdui.Displayable
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 *  javax.microedition.midlet.MIDlet
 */
import java.io.InputStream;
import java.util.Date;
import java.util.Hashtable;
import java.util.Random;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.midlet.MIDlet;

public abstract class aq
extends Canvas
implements Runnable {
    public static Graphics a = null;
    private static Graphics m = null;
    private static boolean n;
    static int b;
    private static long o;
    static boolean c;
    private static int p;
    private static int q;
    private static Display r;
    private static boolean s;
    private static MIDlet t;
    private static int u;
    private long v;
    private long w;
    static int d;
    private static long x;
    private static int y;
    static int e;
    static int f;
    static aq g;
    static boolean h;
    static boolean i;
    private static int z;
    private static int A;
    private static int B;
    private static int C;
    private static Hashtable D;
    private static Hashtable E;
    private static Random F;
    private static String G;
    private static InputStream H;
    private static int I;
    private static short J;
    private static int[] K;
    private static short L;
    private static short[] M;
    private static int N;
    private static byte[] O;
    private static long P;
    private static int Q;
    private static int[] R;
    private static Image S;
    private static int T;
    private static int[][] U;
    static boolean j;
    static int k;
    static int l;

    abstract void a();

    public aq(Object object, Object object2) {
        g = this;
        b = -1;
        s = true;
        t = (MIDlet)object;
        r = (Display)object2;
        this.e();
        E = new Hashtable();
        D = new Hashtable<Integer, Integer>();
        D.put(new Integer(48), new Integer(6));
        D.put(new Integer(49), new Integer(7));
        D.put(new Integer(50), new Integer(1));
        D.put(new Integer(51), new Integer(9));
        D.put(new Integer(52), new Integer(3));
        D.put(new Integer(53), new Integer(5));
        D.put(new Integer(54), new Integer(4));
        D.put(new Integer(55), new Integer(13));
        D.put(new Integer(56), new Integer(2));
        D.put(new Integer(57), new Integer(15));
        D.put(new Integer(35), new Integer(17));
        D.put(new Integer(42), new Integer(16));
        D.put(new Integer(-6), new Integer(18));
        D.put(new Integer(-7), new Integer(19));
        E.put(new Integer(-5), new Integer(5));
        E.put(new Integer(-1), new Integer(1));
        E.put(new Integer(-2), new Integer(2));
        E.put(new Integer(-3), new Integer(3));
        E.put(new Integer(-4), new Integer(4));
        this.v = x = aq.i();
    }

    private static boolean m() {
        boolean bl2 = false;
        if (System.getProperty("java.specification.version") != null) {
            bl2 = true;
        }
        if (!bl2) {
            try {
                if (Class.forName("java.applet.Applet") != null) {
                    bl2 = true;
                }
            }
            catch (Exception exception) {
                bl2 = false;
            }
        }
        if (!bl2) {
            try {
                if (Class.forName("emulator.Emulator") != null) {
                    bl2 = true;
                }
            }
            catch (Exception exception) {
                bl2 = false;
            }
        }
        return bl2;
    }

    protected final void b() {
        if (b >= 0) {
            return;
        }
        p = 640;
        q = 360;
        h = aq.m();
        long l2 = aq.i();
        if (F == null) {
            F = new Random(l2);
        } else {
            F.setSeed(l2);
        }
        b = 0;
        new Thread(this).start();
    }

    protected static void c() {
        if (!n) {
            n = true;
        }
    }

    protected final void d() {
        if (n) {
            long l2;
            o = l2 = aq.i();
            x = l2;
            this.v = l2;
            n = false;
            this.e();
            c = true;
        }
    }

    public void hideNotify() {
        aq.c();
    }

    public void showNotify() {
        this.d();
    }

    public void sizeChanged(int n2, int n3) {
        p = n2;
        q = n3;
    }

    protected final void e() {
        cp.b();
        if (i) {
            return;
        }
        i = true;
        if (r != null && r.getCurrent() != this) {
            r.setCurrent((Displayable)this);
        }
        this.setFullScreenMode(true);
        bm.Y = 3;
    }

    protected static final void f() {
        b = -1;
    }

    public void run() {
        try {
            this.e();
            s = false;
            while (b >= 0) {
                if (!n) {
                    cp.b();
                    this.repaint();
                    this.serviceRepaints();
                    long l2 = aq.i();
                    this.v = Math.min(this.v, l2);
                    do {
                        Thread.yield();
                        l2 = aq.i();
                        this.v = Math.min(this.v, l2);
                    } while (l2 - this.v < (long)u);
                    this.v = l2 = aq.i();
                    if (l2 - this.w <= 9000L || ag.a(10) != 1) continue;
                    Display.getDisplay((MIDlet)t).flashBacklight(0);
                    Display.getDisplay((MIDlet)t).flashBacklight(100);
                    this.w = l2;
                    continue;
                }
                this.v = Math.min(this.v, aq.i());
                Thread.yield();
            }
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            b = -1;
        }
        System.gc();
        t.notifyDestroyed();
    }

    public void paint(Graphics graphics) {
        cp.b();
        if (n || s) {
            return;
        }
        s = true;
        A = 0;
        B = 0;
        if (C > 0) {
            if (C != Integer.MAX_VALUE) {
                C -= d;
            }
            z = 0;
            A = 0;
            B = 0;
        }
        if ((d = (int)((o = aq.i()) - x)) < 0) {
            d = 0;
        }
        if (d > 1000) {
            d = 1000;
        }
        x = o;
        y += d;
        if (d > 0) {
            e = 10000 / d;
        }
        ++f;
        try {
            ck.a(false);
            a = m = graphics;
            ((aq)((Object)exception2)).a();
            ck.a(true);
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            b = -1;
        }
        c = false;
        s = false;
    }

    protected void keyPressed(int n2) {
        int n3 = 1 << aq.h(n2);
        A |= n3;
        z |= n3;
    }

    protected void keyReleased(int n2) {
        int n3 = 1 << aq.h(n2);
        B |= n3;
        z &= ~n3;
    }

    private static byte h(int n2) {
        Integer n3 = new Integer(n2);
        if (D == null) {
            return 0;
        }
        Integer n4 = (Integer)D.get(n3);
        if (n4 != null) {
            return n4.byteValue();
        }
        n4 = (Integer)E.get(n3);
        if (n4 != null) {
            return n4.byteValue();
        }
        return 0;
    }

    static final int a(int n2) {
        return n2 << 8;
    }

    static final int b(int n2) {
        return n2 + 128 >> 8;
    }

    static final int g() {
        return F.nextInt();
    }

    public static InputStream a(String object) {
        object = "".getClass().getResourceAsStream((String)object);
        return object;
    }

    static void b(String object) {
        if (G != null && ((String)object).compareTo(G) == 0) {
            return;
        }
        aq.h();
        G = object;
        object = G;
        object = aq.a(G);
        H = object;
        J = (short)aq.p();
        L = (short)aq.p();
        M = new short[L];
        for (int i2 = 0; i2 < L; ++i2) {
            aq.M[i2] = (short)aq.p();
        }
        N = 0;
        aq.n();
    }

    private static void n() {
        int n2 = N == L - 1 ? J - M[N] : M[N + 1] - M[N];
        K = new int[n2 + 1];
        for (int i2 = 0; i2 < n2 + 1; ++i2) {
            aq.K[i2] = aq.o() & 0xFF | (aq.o() & 0xFF) << 8 | ((aq.o() & 0xFF) << 16 | (aq.o() & 0xFF) << 24);
        }
    }

    static void h() {
        if (H != null) {
            try {
                H.close();
            }
            catch (Exception exception) {}
            H = null;
        }
        I = 0;
        System.gc();
    }

    static byte[] c(int n2) {
        Object object;
        int n3;
        for (n3 = L - 1; n3 >= 0 && M[n3] > n2; --n3) {
        }
        if (N != n3) {
            N = n3;
            aq.h();
            if (N == 0) {
                String string = G;
                G = null;
                aq.b(string);
            } else {
                object = G + "." + N;
                object = aq.a((String)object);
                H = object;
                aq.n();
            }
        } else if (H == null) {
            if (N == 0) {
                String string = G;
                G = null;
                aq.b(string);
            } else {
                object = G + "." + N;
                object = aq.a((String)object);
                H = object;
            }
        }
        int n4 = K[n2 -= M[N]];
        n2 = K[n2 + 1] - K[n2];
        if (I != n4) {
            if (I > n4) {
                aq.h();
                if (N == 0) {
                    object = G;
                    object = aq.a((String)object);
                    H = object;
                } else {
                    object = G + "." + N;
                    object = aq.a((String)object);
                    H = object;
                }
            } else {
                n4 -= I;
            }
            aq.i(n4);
        }
        if (n2 > 0) {
            aq.o();
            --n2;
        }
        byte[] byArray = null;
        byArray = new byte[n2];
        aq.a(byArray, 0, byArray.length);
        return byArray;
    }

    private static void i(int n2) {
        if (n2 == 0) {
            return;
        }
        if (O == null) {
            O = new byte[256];
        }
        while (n2 > 256) {
            aq.a(O, 0, 256);
            n2 -= 256;
        }
        if (n2 > 0) {
            aq.a(O, 0, n2);
        }
    }

    private static int o() {
        int n2 = 0;
        try {
            n2 = H.read();
        }
        catch (Exception exception) {}
        ++I;
        return n2;
    }

    private static int p() {
        return aq.o() & 0xFF | (aq.o() & 0xFF) << 8;
    }

    private static int a(byte[] byArray, int n2, int n3) {
        n2 = 0;
        int n4 = n3;
        int n5 = 0;
        try {
            while (n4 > 0) {
                n5 = H.read(byArray, n2, n4);
                n4 -= n5;
                n2 += n5;
            }
        }
        catch (Exception exception) {}
        I += n3;
        return n3;
    }

    static final void a(Graphics graphics) {
        if (graphics == null) {
            a = m;
            return;
        }
        a = graphics;
    }

    public static final long i() {
        bm.B = !h ? System.currentTimeMillis() : new Date().getTime();
        return bm.B;
    }

    static final void j() {
        System.gc();
    }

    static final void a(int n2, int n3) {
        a.translate(n2, n3);
    }

    private static void a(int n2, int n3, int n4) {
        a.setColor(0xFF000000 | n2 << 16 | n3 << 8 | n4);
    }

    static final void a(int n2, int n3, int n4, int n5) {
        aq.c(a, n2, n3, n4, n5);
    }

    private static void c(Graphics graphics, int n2, int n3, int n4, int n5) {
        graphics.setClip(n2, n3, n4, n5);
    }

    private static void c(int n2, int n3, int n4, int n5) {
        a.drawLine(n2, n3, n4, n5);
    }

    static final void a(Graphics graphics, int n2, int n3, int n4, int n5) {
        graphics.fillRect(n2, n3, n4, n5);
    }

    static final void b(int n2, int n3, int n4, int n5) {
        a.drawRect(n2, n3, n4, n5);
    }

    static final Image a(int[] nArray, int n2, int n3, boolean bl2) {
        Image image = null;
        image = Image.createRGBImage((int[])nArray, (int)n2, (int)n3, (boolean)bl2);
        return image;
    }

    static final void a(Graphics graphics, int[] nArray, int n2, int n3, int n4, int n5, int n6, int n7, boolean bl2) {
        n2 = 0;
        graphics.drawRGB(nArray, n2, n3, n4, n5, n6, n7, bl2);
    }

    public static void d(int n2) {
        try {
            if (P < o) {
                r.vibrate(n2);
                P = o + 200L;
            }
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    static void a(Object object, int n2, Object object2, int n3, int n4) {
        System.arraycopy(object, n2, object2, n3, n4);
    }

    public static void e(int n2) {
        n2 = -1275068416;
        if (-1275068416 != Q || S == null) {
            Q = n2;
            R = ao.c;
            if (ao.c == null) {
                R = new int[4096];
            }
            int n3 = 4096;
            while (n3 > 0) {
                aq.R[--n3] = n2;
            }
            S = aq.a(R, 64, 64, true);
        }
    }

    public static void b(Graphics graphics, int n2, int n3, int n4, int n5) {
        int n6 = a.getClipX();
        int n7 = a.getClipY();
        int n8 = a.getClipWidth();
        int n9 = a.getClipHeight();
        int n10 = n2 > n6 ? n2 : n6;
        int n11 = n3 > n7 ? n3 : n7;
        n4 = (n2 + n4 < n6 + n8 ? n2 + n4 : n6 + n8) - n10;
        n5 = (n3 + n5 < n7 + n9 ? n3 + n5 : n7 + n9) - n11;
        if (n4 <= 0 || n5 <= 0) {
            return;
        }
        graphics.setClip(n10, n11, n4, n5);
        n4 += n10;
        n5 += n11;
        for (n2 = n10; n2 < n4; n2 += 64) {
            for (n3 = n11; n3 < n5; n3 += 64) {
                graphics.drawImage(S, n2, n3, 0);
            }
        }
        aq.c(graphics, n6, n7, n8, n9);
    }

    static void a(Graphics graphics, int n2, int n3, int n4, int n5, int n6) {
        int n7 = n6 >>> 24;
        aq.a(graphics, n2 + 1, n3, n2 + n4, n3, n6 &= 0xFFFFFF, n7, n7);
        aq.a(graphics, n2, n3, n2, n3 + n5, n6, n7, n7);
        aq.a(graphics, n2 + n4, n3 + n5, n2 + n4, n3, n6, n7, n7);
        aq.a(graphics, n2 + n4 + 1, n3 + n5, n2, n3 + n5, n6, n7, n7);
    }

    static final int f(int n2) {
        int n3 = n2 >> 16 & 0xFF & 0xFF;
        return n2 & 0xFFFF0000 | n3 << 8 | n3;
    }

    static final void k() {
        T = 0;
        int[][] nArrayArray = new int[12][];
        U = nArrayArray;
        nArrayArray[10] = new int[2];
        int n2 = 10;
        aq.U[10][1] = 255;
        aq.U[10][0] = -1;
    }

    static final boolean g(int n2) {
        return (0 & 1 << n2) != 0;
    }

    static final int l() {
        return 0;
    }

    static final int b(int n2, int n3) {
        return U[10][1];
    }

    static final int[] a(Graphics object, Image image, int n2, int n3, int n4, int n5, int n6, boolean bl2, boolean bl3) {
        object = ao.a(null);
        int n7 = n5;
        int n8 = n4;
        boolean bl4 = false;
        bl4 = false;
        n3 = n4;
        bl4 = false;
        Object object2 = object;
        Image image2 = image;
        image2.getRGB((int[])object2, 0, n3, 0, 0, n8, n7);
        return aq.a((int[])object, n4, n5, n6, bl2, bl3);
    }

    static final int[] a(int[] nArray, int n2, int n3, int n4, boolean bl2, boolean bl3) {
        j = bl2;
        k = n2;
        l = n3;
        ao.a(nArray, n2, n3);
        return null;
    }

    public static void a(int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
        block5: {
            int n9;
            int n10;
            int n11;
            int n12;
            block7: {
                block6: {
                    block4: {
                        n12 = n6 >> 16 & 0xFF;
                        n11 = n6 >> 8 & 0xFF;
                        n6 &= 0xFF;
                        n10 = n7 >> 16 & 0xFF;
                        n9 = n7 >> 8 & 0xFF;
                        n7 &= 0xFF;
                        n10 -= n12;
                        n9 -= n11;
                        n7 -= n6;
                        n4 = n2 + n4;
                        n5 = n3 + n5;
                        n12 <<= 16;
                        n11 <<= 16;
                        n6 <<= 16;
                        if (n8 != 4) break block4;
                        n8 = n4 - n2;
                        n10 = (n10 << 16) / n8;
                        n9 = (n9 << 16) / n8;
                        n7 = (n7 << 16) / n8;
                        for (n8 = n4 - 1; n8 >= n2; --n8) {
                            aq.a(n12 >> 16, n11 >> 16, n6 >> 16);
                            aq.c(n8, n3, n8, n5);
                            n12 += n10;
                            n11 += n9;
                            n6 += n7;
                        }
                        break block5;
                    }
                    if (n8 != 8) break block6;
                    n8 = n4 - n2;
                    n10 = (n10 << 16) / n8;
                    n9 = (n9 << 16) / n8;
                    n7 = (n7 << 16) / n8;
                    for (n8 = n2; n8 < n4; ++n8) {
                        aq.a(n12 >> 16, n11 >> 16, n6 >> 16);
                        aq.c(n8, n3, n8, n5);
                        n12 += n10;
                        n11 += n9;
                        n6 += n7;
                    }
                    break block5;
                }
                if (n8 != 16) break block7;
                n8 = n5 - n3;
                n10 = (n10 << 16) / n8;
                n9 = (n9 << 16) / n8;
                n7 = (n7 << 16) / n8;
                for (n8 = n5 - 1; n8 >= n3; --n8) {
                    aq.a(n12 >> 16, n11 >> 16, n6 >> 16);
                    aq.c(n2, n8, n4, n8);
                    n12 += n10;
                    n11 += n9;
                    n6 += n7;
                }
                break block5;
            }
            if (n8 != 32) break block5;
            n8 = n5 - n3;
            n10 = (n10 << 16) / n8;
            n9 = (n9 << 16) / n8;
            n7 = (n7 << 16) / n8;
            for (n8 = n3; n8 < n5; ++n8) {
                aq.a(n12 >> 16, n11 >> 16, n6 >> 16);
                aq.c(n2, n8, n4, n8);
                n12 += n10;
                n11 += n9;
                n6 += n7;
            }
        }
    }

    private static void a(Graphics graphics, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
        int n9;
        int n10 = n4 - n2;
        if ((n10 < 0 ? -n10 : n10) > ((n9 = n5 - n3) < 0 ? -n9 : n9)) {
            if (n4 < n2) {
                n10 = n2;
                n2 = n4;
                n4 = n10;
                n10 = n3;
                n3 = n5;
                n5 = n10;
                n10 = n7;
                n7 = n8;
                n8 = n10;
            }
            n10 = n4 - n2;
            n9 = n5 - n3;
            int n11 = 0;
            if (n10 != 0) {
                n11 = (n9 << 16) / n10;
            }
            int n12 = n2;
            n2 = (n3 << 16) + n11 * (n12 - n2);
            n3 = n12;
            n2 += n11;
            n4 = n12 = n4;
            n5 = 0;
            if (n10 != 0) {
                n5 = (n8 - n7 << 16) / n10;
            }
            int[] nArray = ao.a(null);
            n7 <<= 16;
            while (n3 < n4) {
                n10 = n2 >> 16;
                n9 = n2 & 0xFFFF;
                n12 = 65535 - n9;
                n12 = (n12 >> 8) * n7 & 0xFF000000;
                nArray[0] = n12 | n6;
                n12 = (n9 >> 8) * n7 & 0xFF000000;
                nArray[1] = n12 | n6;
                aq.a(graphics, nArray, 0, 1, n3, n10, 1, 2, true);
                n2 += n11;
                n7 += n5;
                ++n3;
            }
        } else {
            if (n5 < n3) {
                n10 = n2;
                n2 = n4;
                n4 = n10;
                n10 = n3;
                n3 = n5;
                n5 = n10;
                n10 = n7;
                n7 = n8;
                n8 = n10;
            }
            n10 = n4 - n2;
            n9 = n5 - n3;
            int n13 = 0;
            if (n9 != 0) {
                n13 = (n10 << 16) / n9;
            }
            int n14 = n3;
            n2 = (n2 << 16) + n13 * (n14 - n3);
            n3 = n14;
            n2 += n13;
            n4 = n14 = n5;
            n5 = 0;
            if (n9 != 0) {
                n5 = (n8 - n7 << 16) / n9;
            }
            n7 <<= 16;
            int[] nArray = ao.c;
            while (n3 < n4) {
                n10 = n2 >> 16;
                n9 = n2 & 0xFFFF;
                n14 = 65535 - n9;
                n14 = (n14 >> 8) * n7 & 0xFF000000;
                nArray[0] = n14 | n6;
                n14 = (n9 >> 8) * n7 & 0xFF000000;
                nArray[1] = n14 | n6;
                aq.a(graphics, nArray, 0, 2, n10, n3, 2, 1, true);
                n2 += n13;
                n7 += n5;
                ++n3;
            }
        }
    }

    public static int[] a(int[] nArray, int[] nArray2) {
        int n2 = nArray2[0];
        int n3 = nArray[0];
        nArray[0] = Math.min(n3, n2);
        n2 = nArray2[1];
        n3 = nArray[1];
        nArray[1] = Math.min(n3, n2);
        n2 = nArray2[2];
        n3 = nArray[2];
        nArray[2] = Math.max(n3, n2);
        n2 = nArray2[3];
        n3 = nArray[3];
        nArray[3] = Math.max(n3, n2);
        return nArray;
    }

    public static int[] b(int[] nArray, int[] nArray2) {
        int n2 = nArray2[0];
        int n3 = nArray[0];
        nArray[0] = Math.max(n3, n2);
        n2 = nArray2[1];
        n3 = nArray[1];
        nArray[1] = Math.max(n3, n2);
        n2 = nArray2[2];
        n3 = nArray[2];
        nArray[2] = Math.min(n3, n2);
        n2 = nArray2[3];
        n3 = nArray[3];
        nArray[3] = Math.min(n3, n2);
        return nArray;
    }

    static {
        p = 640;
        q = 360;
        u = 100;
        h = false;
        i = false;
        T = 0;
        U = null;
    }
}

