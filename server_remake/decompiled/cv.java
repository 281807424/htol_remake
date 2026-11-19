/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import java.util.Vector;
import javax.microedition.lcdui.Graphics;

final class cv
extends h
implements bj {
    static String[] q;
    static int r;
    static short s;
    static int t;
    static boolean u;
    public int v;
    public int w;
    public int x;
    public int y;
    public String z;
    public byte A;
    public byte B;
    public byte C;
    public static String D;
    public static int E;
    public static int F;
    public static long[] G;
    public static Vector H;
    public static int I;
    public static int J;
    int K = 0;
    public static byte L;
    public static int M;
    public static byte N;

    public cv() {
        this.a = 3;
    }

    public static void a(q q2) {
        byte by2 = q2.a();
        D = q2.a(0, by2);
        E = q2.a();
        cv.G[1] = q2.a();
        cv.G[2] = q2.a();
        cv.G[0] = G[2];
        cv.G[3] = q2.b();
        F = q2.a();
        cv.G[4] = 0L;
        cv.G[5] = q2.e();
        cv.G[6] = q2.d();
        cv.G[7] = q2.d();
        cv.G[8] = q2.d();
        cv.G[9] = q2.d();
        cv.G[10] = q2.d();
        cv.G[11] = q2.d();
        cv.G[12] = q2.d();
        cv.G[13] = q2.d();
        cv.G[14] = q2.d();
        cv.G[15] = q2.d();
        cv.G[16] = q2.f();
        cv.G[17] = q2.f();
        cv.G[18] = q2.d();
        cv.G[19] = q2.d();
    }

    public static String c(int n2) {
        if (n2 >= 1 && n2 <= 50) {
            return bm.A(257);
        }
        if (n2 >= 51 && n2 <= 90) {
            return bm.A(596);
        }
        if (n2 >= 91 && n2 <= 99) {
            return bm.A(597);
        }
        if (n2 == 100) {
            return bm.A(598);
        }
        return bm.A(599) + n2;
    }

    private static ao d(int n2) {
        if (n2 == -1) {
            return null;
        }
        return ck.b(n2);
    }

    private void e(int n2) {
        if (this.d == null) {
            return;
        }
        this.K = n2;
        switch (n2) {
            case 0: {
                this.a(0);
                return;
            }
            case 1: {
                this.a(1);
                return;
            }
            case 2: {
                this.a(2);
                return;
            }
            case 3: {
                this.a(3);
                return;
            }
            case 4: {
                this.a(4);
            }
        }
    }

    public final void h() {
        if (this.K == 1) {
            this.e(1);
            return;
        }
        if (this.K == 0) {
            this.e(0);
        }
    }

    final void a(int n2, int n3) {
        this.v = n2;
        this.d = cv.d(n2);
        this.m = n3;
        this.a(0);
    }

    final void a(Graphics graphics) {
        if (this.d != null) {
            int n2 = (this.k - bm.bW) * 3 >> 1;
            int n3 = (this.l - bm.bX) * 3 >> 1;
            if (this.C >> 7 != 0) {
                bm.a(graphics, 50, n2, n3);
            }
            this.p = this.C & 0xF;
            ak.e[5] = ak.e[5] + 1;
            ak.b(this.v);
            this.b(graphics, n2, n3);
        }
    }

    final void c(Graphics graphics, int n2, int n3) {
        try {
            if (this.z != null && this.d != null) {
                int n4 = this.d.e(0);
                int n5 = this.d.g(0);
                int n6 = (this.k - bm.bW) * 3 >> 1;
                n5 = ((this.l - bm.bX) * 3 >> 1) - n5 - 19;
                n6 = this.e == 1 ? (n6 -= n4) : (n6 += n4);
                ay.b(graphics, this.z, n6, n5, n2, n3);
                if (cr.aY > 0 && this == bm.cb.aX) {
                    bz.b(3);
                    bz.a(graphics, "" + J, n6, n5 + cr.aY, 1);
                }
            }
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public static void b(Graphics graphics) {
        ay.a(graphics, 2, 3, bm.A(601));
        bm.a(graphics, bm.A(602));
        ay.a(graphics, 167, 80, 305, 260, false, "", false);
        int n2 = 5;
        n2 = 255;
        n2 = 290;
        n2 = 85;
        n2 = 174;
        Graphics graphics2 = graphics;
        int n3 = bm.D.f(112);
        int n4 = H.size();
        Object var3_5 = null;
        boolean bl2 = I != -1;
        s = (short)bm.ay();
        bz.a(graphics2, 16179625, 16772818, -1);
        bm.dX = (cr.aH - 1) / 5 + 1;
        bm.a(graphics2, 0, 0, 0, true, -1, -1, -1);
        bm.dZ = 5;
        t = bm.dY * bm.dZ;
        int[] nArray = new int[]{1, 0};
        ay.a(graphics2, 174, 85, 290, 250, 5, nArray);
        if (bl2 && I >= t && I < t + 5) {
            ay.a(graphics2, 174, 85 + (I - t) * 51, 290, 51);
        }
        for (int i2 = t; i2 < t + 5; ++i2) {
            if (i2 >= n4) continue;
            cv cv2 = (cv)H.elementAt(i2);
            Object object = cv.d(cv2.v);
            ((cv)cv.H.elementAt((int)i2)).k = 174 + (n3 >> 1) - ((ao)object).b(0, 0);
            ((cv)cv.H.elementAt((int)i2)).l = 136 + (i2 - t) * 51;
            if (((cv)cv.H.elementAt((int)i2)).d != null) {
                ((cv)H.elementAt(i2)).b(graphics2, ((cv)cv.H.elementAt((int)i2)).k, ((cv)cv.H.elementAt((int)i2)).l);
            }
            ((cv)H.elementAt(i2)).c();
            object = cv.c(cv2.x);
            bz.a(graphics2, -597591, -13032703, 4);
            bz.a(graphics2, (String)object + "\n" + cv2.w + bm.A(370), 229, 90 + (i2 - t) * 50, 0);
            int n5 = bz.a(bm.A(595));
            n5 = bz.a(bm.A(595)) < bz.a(cv2.y + "/200") ? bz.a(cv2.y + "/200") : n5;
            bz.a(graphics2, bm.A(595) + cv2.y + "/200", 464 - n5, 90 + (i2 - t) * 50, 4);
            bz.b((byte)0);
            graphics2.setColor(16179625);
            bz.a(graphics2, cv2.z, 319, 90 + (i2 - t) * 50, 17);
            bz.b((byte)1);
        }
        ay.b(graphics2, 174, 85 + (s - t) * 50, 290, 50);
    }

    public static void i() {
        if (!bm.V()) {
            return;
        }
        bm.dB = false;
        int n2 = bm.b(5, 174, 85, 290, 50);
        if (n2 != -1) {
            if (bm.dW == n2) {
                bm.dB = true;
            } else {
                bm.dW = n2;
                bm.X = 3;
            }
        }
        if (bm.v(262144)) {
            bm.H();
        } else if ((bm.v(196640) || bm.v(196608)) && s < H.size()) {
            l.a = aq.g();
            bm.bn.a(l.a, L, s, M, N);
        }
        bm.a(false, true);
        bm.X = 3;
    }

    static {
        r = 0;
        s = 0;
        t = 0;
        u = true;
        G = new long[20];
        H = new Vector();
        I = -1;
    }
}

