/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import java.util.Vector;
import javax.microedition.lcdui.Graphics;

public final class i {
    public static int a = 1000;
    static Vector b = new Vector();
    int c;
    byte d;
    byte e;
    String f;
    String g;
    int h;
    int i;
    int j;
    private int q;
    private int r;
    int k;
    int l;
    long m;
    int n;
    static int o = 0;
    private static Vector s = new Vector();
    private static int t = 0;
    public static int p = 0;
    private static int u = 0;
    private static int v = 0;
    private static int w = 0;

    public static int a() {
        int n2 = -1;
        switch (bm.cb.u) {
            case 0: {
                n2 = 0;
                break;
            }
            case 1: {
                n2 = 100;
                break;
            }
            case 2: {
                n2 = 200;
            }
        }
        return n2;
    }

    static i a(int n2) {
        for (int i2 = 0; i2 < b.size(); ++i2) {
            i i3 = (i)b.elementAt(i2);
            if (i3.c != n2) continue;
            return i3;
        }
        return null;
    }

    static boolean a(i i2) {
        if (i2 != null) {
            return i2.q == 0;
        }
        return false;
    }

    static boolean b(i i2) {
        if (i2 != null) {
            return i2.r == 1;
        }
        return false;
    }

    static boolean c(i i2) {
        if (i2 != null) {
            return i2.r == 2;
        }
        return false;
    }

    static int b(int n2) {
        i i2 = i.a(n2);
        if (i2 != null) {
            return i2.k;
        }
        return 0;
    }

    static void b() {
        b.removeAllElements();
    }

    static void a(q q2) {
        b.removeAllElements();
        int n2 = q2.b();
        for (int i2 = 0; i2 < n2; ++i2) {
            i i3 = new i();
            i3 = i.a(q2, i3, true);
            b.addElement(i3);
        }
    }

    static i a(q q2, i i2, boolean bl2) {
        short s2 = q2.c();
        if (!bl2 && (i2 = i.a(s2)) == null) {
            i2 = new i();
            b.addElement(i2);
        }
        i2.c = s2;
        i2.d = q2.a();
        q2.a();
        i2.f = q2.g();
        i2.e = q2.a();
        i2.i = q2.b();
        i2.h = q2.c();
        i2.j = q2.c() * 100;
        i2.k = q2.c() * 100;
        i2.l = q2.c();
        i2.q = q2.b();
        i2.r = q2.b();
        i2.n = q2.b();
        return i2;
    }

    final boolean c() {
        return this.c == 3001;
    }

    final boolean d() {
        return this.c == 110;
    }

    final boolean e() {
        return this.n == 0;
    }

    final boolean f() {
        return (this.n & 0x80) != 0 && !this.e();
    }

    final int g() {
        if (this.f() && !this.e()) {
            return this.n & 0x7F;
        }
        return 0;
    }

    final void a(boolean bl2) {
        if (bl2) {
            this.n |= 0x80;
            return;
        }
        this.n &= 0x7F;
    }

    public static void h() {
        for (int i2 = 0; i2 < b.size(); ++i2) {
            i i3 = (i)b.elementAt(i2);
            if (!i3.f()) continue;
            i3.a(!i3.f());
        }
    }

    public final void a(Graphics graphics, int n2, int n3, boolean bl2) {
        bm.a(ck.b(25), graphics, (int)this.e, n2, n3, 0);
        if (this.f()) {
            graphics.setColor(0xFFFFFF);
            graphics.drawRect(n2 + -1, n3 + -1, 27, 27);
            bz.a(graphics, 16179625, 16772818, -1);
            if (bl2) {
                bz.a(graphics, bm.A(495) + this.g(), n2 + 25, n3, 0);
            }
        }
    }

    public final boolean i() {
        return this.c == 10001 || this.c == 10002 || this.c == 10003;
    }

    public final boolean j() {
        long l2 = bm.m();
        return l2 < this.m + (long)this.j;
    }

    final void c(int n2) {
        this.m = bm.m() - (long)(this.j - n2);
    }

    static i k() {
        int n2;
        i i2 = null;
        if (cr.al != null) {
            i2 = cr.al;
            cr.al = null;
            return i2;
        }
        Vector<i> vector = new Vector<i>();
        for (n2 = 0; n2 < b.size(); ++n2) {
            i2 = (i)b.elementAt(n2);
            if (i2 == null || i2.e() || !i2.f()) continue;
            vector.addElement(i2);
        }
        bg.a(vector, 0, vector.size() - 1, 7);
        for (n2 = 0; n2 < vector.size(); ++n2) {
            if (o >= vector.size()) {
                o = 0;
            }
            int n3 = (o + n2) % vector.size();
            i2 = (i)vector.elementAt(n3);
            if (i2.h > cr.aJ || i2.j()) continue;
            return i2;
        }
        return i.a(i.a());
    }

    public static void a(bh bh2) {
        s.removeAllElements();
        s.addElement(bm.cb);
        if (bh2 != null && bh2.g() && !((cr)bh2).k()) {
            s.addElement(bh2);
        }
        for (int i2 = 0; i2 < bm.bq.size(); ++i2) {
            cr cr2 = (cr)bm.bq.elementAt(i2);
            if (cr2 == null || bm.cb.C == cr2.C || (cr2 = bh.e(cr2.C)) == null || !cr2.k()) continue;
            s.addElement(cr2);
        }
    }

    public static void a(Graphics graphics) {
        u = 33 * s.size() + 5 * (s.size() + 1);
        v = (360 - u) / 2;
        w = u + 26 + 18;
        ay.a(graphics, 253, v - 13 - 20, 134, w, true, bm.A(496), true);
        for (int i2 = 0; i2 < s.size(); ++i2) {
            bh bh2 = (bh)s.elementAt(i2);
            if (bh2 == null) continue;
            if (i2 == t) {
                aq.a(255, v + 3 + i2 * 38, 130, 33, 6113836, 9142619, 16);
            }
            int n2 = v + i2 * 38;
            int n3 = 265;
            bh bh3 = bh2;
            bh2 = graphics;
            bm.a((Graphics)bh2, bh3, 270, n2 + 3, 1);
            bm.D.a(14, 270, n2 + 3, 0);
            bh.e(bh3.C);
            int n4 = bh3.H;
            n4 = n4 * 56 / 100;
            int n5 = bh3.I;
            n5 = n5 * 56 / 100;
            if (bh3.C == bm.cb.C) {
                n4 = cr.aI * 56 / ((cr)bh3).r[4];
                n5 = cr.aJ * 56 / ((cr)bh3).r[5];
            }
            bh2.setColor(0xFF0000);
            bh2.fillRect(303, n2 + 3 + 17, n4, 3);
            bh2.setColor(255);
            bh2.fillRect(303, n2 + 3 + 22, n5, 3);
            bh2.setColor(-597591);
            bz.b((byte)0);
            bz.a((Graphics)bh2, bh3.E, 305, n2, 4);
            bz.b((byte)1);
        }
        bm.X = 0;
    }

    public static void l() {
        bm.dB = false;
        int[][] nArray = null;
        nArray = bm.a(s.size(), v + 3, 130, 33, 5);
        for (int i2 = 0; i2 < s.size(); ++i2) {
            bh bh2 = (bh)s.elementAt(i2);
            if (bh2 == null || !bm.a(nArray[i2])) continue;
            if (t == i2) {
                bm.dB = true;
            } else {
                t = i2;
            }
            bm.X = 3;
            break;
        }
        int n2 = s.size();
        if (bm.v(16388)) {
            if (t > 0) {
                --t;
                return;
            }
            t = n2 - 1;
            return;
        }
        if (bm.v(33024)) {
            if (t < n2 - 1) {
                ++t;
                return;
            }
            t = 0;
            return;
        }
        if (bm.v(196640) || bm.v(196608) || bm.e(510, 268, 90, 90)) {
            cr.aV = (bh)s.elementAt(t);
            bm.cb.a(i.a(p));
            if (cr.aV.C == bm.cb.C) {
                cr.aV = null;
            }
            bm.H();
            t = 0;
            return;
        }
        if (bm.v(262144) || bm.ak()) {
            t = 0;
            bm.H();
        }
    }
}

