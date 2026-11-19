/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class cm {
    int a;
    long b;
    int[] c;
    private int[] h;
    int d = 0;
    byte e;
    String f = "";
    public static int g = 0;

    final void a(q q2) {
        int n2;
        this.a = q2.d();
        int n3 = q2.e();
        this.b = bm.m() + (long)n3;
        this.f = q2.g();
        this.d = q2.c();
        this.e = q2.a();
        n3 = q2.b();
        int n4 = n3 >> 4;
        this.c = new int[n4];
        for (n2 = 0; n2 < n4; ++n2) {
            this.c[n2] = q2.b();
        }
        n2 = n3 & 0xF;
        this.h = new int[n2];
        for (n3 = 0; n3 < n2; ++n3) {
            this.h[n3] = q2.b();
        }
    }

    final boolean a(int n2) {
        if (this.h == null) {
            return false;
        }
        for (int i2 = 0; i2 < this.h.length; ++i2) {
            if (this.h[i2] != n2) continue;
            return true;
        }
        return false;
    }

    final boolean a() {
        return bm.m() >= this.b;
    }

    public static void a(Graphics graphics) {
        ay.a(graphics, -1, 3, bm.d(0, 208), true);
        if (!ci.a()) {
            bm.D.a(graphics, 1, 65, 0, 0);
        }
        bz.a(graphics, 16179625, 0xFFFFFF, -1);
        bz.a(graphics, bm.d(0, 209), 385, 71, 1);
        int[] nArray = new int[]{9, 7};
        ay.a(graphics, 252, 90, 265, 216, 8, nArray);
        bm.X = 0;
        int n2 = bm.dY << 3;
        int n3 = 0;
        for (int i2 = n2; i2 < n2 + 8; ++i2) {
            long l2;
            Object object;
            if (i2 - n2 == bm.dW) {
                ay.b(graphics, 252, 90 + bm.dW * 27, 265, 27);
            }
            if (i2 >= g || i2 >= bm.cb.P.size() || (object = (cm)bm.cb.P.elementAt(i2)) == null || (l2 = ((cm)object).b - bm.m()) < 0L || ((cm)object).e != 1) continue;
            cm.a(bm.bi, ((cm)object).d, 257, 90 + (n3 - n2) * 27 + 4 - 2);
            bz.a(graphics, 16179625, 0, -1);
            bz.a(graphics, ((cm)object).f, 297, 90 + (n3 - n2) * 27 + 4);
            long l3 = (((cm)object).b - bm.m()) / 3600000L;
            long l4 = (((cm)object).b - bm.m()) / 60000L - l3 * 60L;
            long l5 = (((cm)object).b - bm.m()) / 1000L - l3 * 60L * 60L - l4 * 60L;
            object = bm.A(542);
            object = (String)object + (l3 > 0L ? l3 + ":" : "00:");
            object = (String)object + (l4 > 0L ? l4 + ":" : "00:");
            object = (String)object + l5;
            bz.a(graphics, (String)object, 402, 90 + (n3 - n2) * 27 + 4);
            ++n3;
        }
        graphics.setClip(0, 0, 640, 360);
    }

    public static void b() {
        bm.g(20, 180);
        if (bm.v(262144)) {
            bm.H();
            return;
        }
        bm.a(false, true);
    }

    public static void a(Graphics graphics, int n2, int n3, int n4) {
        n.a(graphics, n3, n4, n2, 0, 1, false, false, 0);
    }
}

