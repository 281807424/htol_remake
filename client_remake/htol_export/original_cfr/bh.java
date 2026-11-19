/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import java.util.Hashtable;
import java.util.Vector;
import javax.microedition.lcdui.Graphics;

public class bh
extends h
implements bj {
    public static boolean w;
    public static Vector x;
    public static Vector y;
    public static Vector z;
    boolean A;
    boolean B = false;
    public int C;
    public int D;
    public String E;
    public int F;
    byte G = (byte)4;
    byte H;
    byte I;
    private static int[][][] q;
    byte J;
    public static cy K;
    private byte r;
    private byte s;
    private boolean t;
    private int u;
    public int L;
    public int M;
    private long[] v;
    private int[] U;
    private int V;
    int N = 0;
    private int[] W;
    private int[] X;
    private int Y;
    private int Z = 0;
    private int[] aa;
    private int[] ab;
    private int ac;
    private int ad = 0;
    private static int[] ae;
    private cy[] af = null;
    private int[] ag = null;
    public Hashtable O = new Hashtable();
    Vector P;

    public static int a(int n2, int n3, int n4, int n5) {
        n2 = n4 - n2;
        if ((n3 = n5 - n3) == 0) {
            if (n2 > 0) {
                return 2;
            }
            return 6;
        }
        if (n2 == 0) {
            if (n3 < 0) {
                return 0;
            }
            return 4;
        }
        if (n3 < 0) {
            if (Math.abs(n3) > Math.abs(n2) * 3) {
                return 0;
            }
            if (Math.abs(n2) > 3 * Math.abs(n3)) {
                if (n2 > 0) {
                    return 2;
                }
                return 6;
            }
            if (n2 > 0) {
                return 1;
            }
            return 7;
        }
        if (Math.abs(n3) > Math.abs(n2) * 3) {
            return 4;
        }
        if (Math.abs(n2) > 3 * Math.abs(n3)) {
            if (n2 > 0) {
                return 2;
            }
            return 6;
        }
        if (n2 > 0) {
            return 3;
        }
        return 5;
    }

    public static ce c(int n2) {
        return (ce)bh.b(x, n2);
    }

    public static r d(int n2) {
        return (r)bh.b(z, n2);
    }

    public static cr e(int n2) {
        return (cr)bh.b(y, n2);
    }

    public static cr a(Vector object, int n2) {
        if (object == null) {
            return null;
        }
        if ((object = bh.b((Vector)object, n2)) != null && ((h)object).g()) {
            return (cr)object;
        }
        return null;
    }

    public static bh b(Vector vector, int n2) {
        if (vector == null) {
            return null;
        }
        bh bh2 = null;
        for (int i2 = 0; i2 < vector.size(); ++i2) {
            bh2 = (bh)vector.elementAt(i2);
            if (bh2 == null || bh2.C != n2) continue;
            return bh2;
        }
        return null;
    }

    public static bh f(int n2) {
        bh bh2 = bh.e(n2);
        if (bh2 != null) {
            return bh2;
        }
        bh2 = bh.c(n2);
        if (bh2 != null) {
            return bh2;
        }
        return bh.d(n2);
    }

    public static boolean g(int n2) {
        bh.c(bm.bp, n2);
        bh.c(ag.o, n2);
        if (bh.c(y, n2)) {
            return true;
        }
        if (bh.c(x, n2)) {
            return true;
        }
        return bh.c(z, n2);
    }

    public static boolean c(Vector vector, int n2) {
        if (vector == null) {
            return false;
        }
        for (int i2 = 0; i2 < vector.size(); ++i2) {
            h h2 = (h)vector.elementAt(i2);
            if (!h2.f() && !h2.e() && !h2.g() || h2 == null || ((bh)h2).C != n2) continue;
            vector.removeElementAt(i2);
            return true;
        }
        return false;
    }

    public static void b(boolean bl2) {
        x.removeAllElements();
        z.removeAllElements();
        cc.c.removeAllElements();
        y.removeAllElements();
        if (!bl2) {
            y.addElement(bm.cb);
        }
        System.gc();
    }

    public final void l() {
        if (this.f()) {
            ((ce)this).b(3, 4);
        } else if (this.g()) {
            ((cr)this).w(5);
        }
        this.i(0);
    }

    final void a(String string, int n2, int n3) {
        this.E = string;
        this.C = n2;
        this.D = n3;
        this.a(this.m);
    }

    final void b(int n2, int n3, int n4, int n5) {
        this.F = n2;
        this.k = n3;
        this.l = n4;
        this.G = (byte)n5;
        this.h(this.G);
    }

    final int a(int n2, int n3) {
        n2 = this.k - n2;
        int n4 = this.l - n3;
        return n2 * n2 + n4 * n4;
    }

    final void h(int n2) {
        this.G = (byte)n2;
        switch (this.G) {
            case 6: {
                this.e &= 0xFFFFFFFE;
                return;
            }
            case 2: {
                this.e |= 1;
                return;
            }
        }
        this.e &= 0xFFFFFFFE;
    }

    public final boolean m() {
        if (this.B) {
            return cr.aI <= 0;
        }
        return this.H <= 0;
    }

    public final int n() {
        if (this.B) {
            return cr.aI;
        }
        return this.H;
    }

    public final void i(int n2) {
        if (this.B) {
            cr.aI = n2;
            return;
        }
        this.H = (byte)n2;
    }

    public final void j(int n2) {
        if (this.B) {
            cr.aJ = n2;
            return;
        }
        this.I = (byte)n2;
    }

    public static void k(int n2) {
        int n3 = 0;
        int n4 = n2 == 0 ? bm.cb.r[4] : bm.cb.r[5];
        int n5 = n2 == 0 ? cr.aI : cr.aJ;
        n3 = 0;
        if (ag.j[n2 + 2] != ag.n.length - 1) {
            n3 = 10 + ag.j[n2 + 2] * 10;
            if (n5 <= (n3 = n4 * n3 / 100)) {
                bh.i(n2, 0);
            }
        }
        if (ag.j[n2 + 0] != ag.m.length - 1) {
            n3 = 30 + ag.j[n2 + 0] * 10;
            if (n5 <= (n3 = n4 * n3 / 100)) {
                bh.i(n2, 1);
            }
        }
    }

    public static void a(q q2) {
        for (int i2 = 0; i2 < 2; ++i2) {
            for (int i3 = 0; i3 < 2; ++i3) {
                int n2 = q2.a();
                bh.q[i2][i3] = new int[n2];
                for (int i4 = 0; i4 < n2; ++i4) {
                    bh.q[i2][i3][i4] = q2.e();
                }
            }
        }
    }

    private static boolean i(int n2, int n3) {
        int[] nArray = q[n2][n3];
        if (nArray == null) {
            return false;
        }
        if (bm.aA != null) {
            n n4;
            for (n3 = 0; n3 < nArray.length; ++n3) {
                n4 = bm.aA.c(nArray[n3]);
                if (n4 == null || !n4.d()) continue;
                return false;
            }
            for (n3 = 0; n3 < nArray.length; ++n3) {
                n4 = bm.aA.c(nArray[n3]);
                if (n4 == null || n4.k > bm.cb.U) continue;
                bm.bn.a(n4, (bh)bm.cb);
                return true;
            }
        }
        return false;
    }

    public final boolean o() {
        if (this.B) {
            return false;
        }
        if (this.F != bm.cb.F) {
            return true;
        }
        return this.m();
    }

    final void b(int n2, int n3) {
        if (this.f() && this.J == 3 && n2 != 4) {
            return;
        }
        this.J = (byte)n2;
        this.a(n3 >= 0 ? n3 : 0);
    }

    static boolean a(bh bh2) {
        if (bh2.f()) {
            int n2 = ((ce)bh2).r;
            if (bm.Q()) {
                return bm.cb.ak != n2;
            }
            boolean bl2 = n2 >> 16 != 0;
            n2 &= 0xFFFF;
            if (bl2) {
                return n2 != bm.cb.v;
            }
            return true;
        }
        if (bh2.g()) {
            if ((bh2 = (cr)bh2) == bm.cb) {
                return false;
            }
            if (bm.cb.b((cr)bh2) || bm.D(1)) {
                return true;
            }
            if (bm.D(262144)) {
                return !((cr)bh2).k() && !((cr)bh2).B();
            }
            if (bm.Q()) {
                return bm.cb.ak != ((cr)bh2).ak;
            }
            return cr.c((cr)bh2);
        }
        return false;
    }

    final void d(Graphics graphics, int n2, int n3) {
        if (cr.aV == this) {
            K.c();
            int n4 = bh.K.a.c();
            if (bh.a(cr.aV)) {
                bh.K.a.j(1);
            } else {
                bh.K.a.j(0);
            }
            K.a(graphics, n2, n3, this.e);
            bh.K.a.j(n4);
        }
    }

    final void e(Graphics graphics, int n2, int n3) {
        int n4 = n3;
        int n5 = n2;
        Graphics graphics2 = graphics;
        bh bh2 = this;
        if (bh2.V > 0) {
            n4 = (2 == bh2.a || 1 == bh2.a) && bh2.d != ck.b(159) ? n4 - bh2.d.g(0) - 10 : (n4 -= 60);
            int n6 = bh2.B ? 30 : -30;
            for (int i2 = 0; i2 < bh2.V; ++i2) {
                long l2;
                int n7 = bh2.U[i2] * n6 / 1000;
                long l3 = bh2.v[i2];
                int n8 = (int)(l3 >> 16 & 0xFFFFL) - 1;
                if (n8 >= 0 && n8 < ck.b(5).b()) {
                    ck.b(5).a(graphics2, n8, n5, n4 + n7, 0);
                }
                if ((n8 = (int)((l2 = bh2.v[i2]) & 0xFFFFL)) == 0) break;
                if (bh.b(bh2.v[i2])) {
                    bz.b(4);
                } else {
                    bz.b(3);
                }
                if (bh2.a == 0) {
                    if (bh2.b == 5) {
                        bz.c(2);
                    } else {
                        bz.c(3);
                    }
                } else if (bh2.a == 1) {
                    bz.c(4);
                }
                bz.b((byte)2);
                bz.a(graphics2, "-" + n8, n5, n4 + n7, 17);
                bz.c(0);
                bz.b((byte)1);
            }
        }
        this.c(graphics, n2 - 13, n3);
        this.h(graphics, n2 + -10, n3 - 27);
    }

    final void p() {
        int n2;
        bh bh2 = this;
        for (n2 = 0; n2 < bh2.P.size(); ++n2) {
            cm cm2 = (cm)bh2.P.elementAt(n2);
            if (cm2 != null) {
                if (!cm2.a()) continue;
                bh2.b(cm2);
                continue;
            }
            bh2.P.removeElementAt(n2);
        }
        bh2 = this;
        if (bh2.V > 0) {
            for (n2 = 0; n2 < bh2.V; ++n2) {
                if (bh2.U[n2] >= 1000) {
                    for (int i2 = n2; i2 < bh2.V - 1; ++i2) {
                        bh2.v[i2] = bh2.v[i2 + 1];
                        bh2.U[i2] = bh2.U[i2 + 1];
                    }
                    --bh2.V;
                    --n2;
                    continue;
                }
                int n3 = n2;
                bh2.U[n3] = bh2.U[n3] + aq.d;
            }
            bh2.N -= aq.d;
        }
        bh2 = this;
        if (bh2.Y > 0) {
            for (n2 = 0; n2 < bh2.Y; ++n2) {
                if (bh2.X[n2] >= 1000) {
                    for (int i3 = n2; i3 < bh2.Y - 1; ++i3) {
                        bh2.W[i3] = bh2.W[i3 + 1];
                        bh2.X[i3] = bh2.X[i3 + 1];
                    }
                    --bh2.Y;
                    --n2;
                    continue;
                }
                int n4 = n2;
                bh2.X[n4] = bh2.X[n4] + aq.d;
            }
            bh2.Z -= aq.d;
        }
        bh2 = this;
        if (bh2.ac > 0) {
            for (n2 = 0; n2 < bh2.ac; ++n2) {
                if (bh2.ab[n2] >= 1000) {
                    for (int i4 = n2; i4 < bh2.ac - 1; ++i4) {
                        bh2.aa[i4] = bh2.aa[i4 + 1];
                        bh2.ab[i4] = bh2.ab[i4 + 1];
                    }
                    --bh2.ac;
                    --n2;
                    continue;
                }
                int n5 = n2;
                bh2.ab[n5] = bh2.ab[n5] + aq.d;
            }
            bh2.ad -= aq.d;
        }
    }

    final void f(Graphics graphics, int n2, int n3) {
        if (ag.a(4) == 0) {
            return;
        }
        if (ag.a(4) == 1 && this.C != bm.cb.C) {
            return;
        }
        if (this.ag != null) {
            int n4;
            for (int i2 = 0; i2 < 5 && ((n4 = this.ag[i2]) & 0xFFFF) != 0; ++i2) {
                n4 = bh.u(this.ag[i2]);
                int n5 = this.e;
                if ((bh.t(this.ag[i2]) & 8) != 0) {
                    n2 = 0;
                    n3 = 0;
                    n5 = 0;
                }
                if (n4 >= 0) break;
                this.af[i2].a(graphics, n2, n3, n5);
            }
        }
    }

    final void g(Graphics graphics, int n2, int n3) {
        if (ag.a(4) == 0) {
            return;
        }
        if (ag.a(4) == 1 && this.C != bm.cb.C) {
            return;
        }
        if (this.ag != null) {
            int n4;
            for (int i2 = 0; i2 < 5 && ((n4 = this.ag[i2]) & 0xFFFF) != 0; ++i2) {
                Object object;
                int n5 = n2;
                int n6 = n3;
                int n7 = bh.u(this.ag[i2]);
                if (n7 < 0) continue;
                n7 = this.e;
                if ((bh.t(this.ag[i2]) & 8) != 0) {
                    n5 = 0;
                    n6 = 0;
                    n7 = 0;
                }
                if ((object = (short[])this.O.get(new Integer((n4 = this.ag[i2]) & 0xFFFF))) != null) {
                    n5 = (object[0] - bm.bW) * 3 >> 1;
                    n6 = (object[1] - bm.bX) * 3 >> 1;
                }
                if (this.g() && (bh.t(this.ag[i2]) & 0x10) != 0) {
                    cr cr2 = (cr)this;
                    if (!cr2.G()) continue;
                    ao ao2 = cr2.aX.d;
                    object = ao2;
                    if (ao2 == null) continue;
                    n5 = cr2.e == 1 ? -((ao)object).b(0, 0) : ((ao)object).b(0, 0);
                    n5 = ((cr2.aX.k - bm.bW) * 3 >> 1) + n5;
                    n6 = ((cr2.aX.l - bm.bX) * 3 >> 1) + ((ao)object).c(0, 0);
                }
                this.af[i2].a(graphics, n5, n6, n7);
                ak.e[7] = ak.e[7] + 1;
            }
        }
    }

    final void c(int n2, int n3) {
        int n4;
        block10: {
            int n5;
            block3: {
                block7: {
                    int n6;
                    block6: {
                        block8: {
                            block9: {
                                block4: {
                                    block5: {
                                        n5 = this.k;
                                        n5 = n2 - n5;
                                        n4 = 0;
                                        if (!this.g()) break block3;
                                        n4 = n3;
                                        n5 = n2;
                                        n3 = this.l;
                                        n2 = this.k;
                                        n2 = n5 - n2;
                                        n3 = n4 - n3;
                                        if (n2 <= 0) break block4;
                                        if (2 * Math.abs(n2) <= Math.abs(n3)) break block5;
                                        n6 = 2;
                                        break block6;
                                    }
                                    n2 = n3 < 0 ? 0 : 4;
                                    break block7;
                                }
                                if (n2 >= 0) break block8;
                                if (2 * Math.abs(n2) <= Math.abs(n3)) break block9;
                                n6 = 6;
                                break block6;
                            }
                            n2 = n3 < 0 ? 0 : 4;
                            break block7;
                        }
                        n6 = n3 < 0 ? 0 : 4;
                    }
                    n2 = n6;
                }
                n4 = n2;
                break block10;
            }
            if (n5 > 0) {
                n4 = 2;
            } else if (n5 < 0) {
                n4 = 6;
            }
        }
        this.h(n4);
    }

    public final void d(int n2, int n3) {
        this.r = (byte)n2;
        this.s = (byte)n3;
    }

    public final void q() {
        this.k += this.r;
        this.l += this.s;
        this.r = 0;
        this.s = 0;
    }

    final boolean r() {
        return this.t;
    }

    final void a(int n2, int n3, int n4) {
        if (this.B && this.m()) {
            bm.d(bm.A(539));
            return;
        }
        this.t = true;
        this.u = n4;
        this.L = n2;
        this.M = n3;
        this.c(this.L, this.M);
        if (bh.e(n2, n3)) {
            String cfr_ignored_0 = this.E + bm.A(540) + n2 + " ," + n3 + ")";
        }
    }

    final void s() {
        bh bh2 = bh3;
        bh bh3 = bh2;
        bh3 = bh2;
        bh2.t = false;
        bh3.u = 0;
        bh3.L = 0;
        bh3.M = 0;
    }

    final void t() {
        bh bh2 = this;
        if (bh2.t) {
            this.k = this.L;
            this.l = this.M;
            this.s();
        }
    }

    final void u() {
        if (!this.t || this.s(16) || this.s(8)) {
            return;
        }
        int n2 = this.L - this.k;
        int n3 = this.M - this.l;
        int n4 = 0;
        int n5 = 0;
        if (aq.d < this.u) {
            n4 = n2 * aq.d / this.u;
            n5 = n3 * aq.d / this.u;
            this.u -= aq.d;
        } else {
            if (this.g()) {
                n4 = n2;
                n5 = n3;
            }
            this.s();
        }
        this.d(n4, n5);
    }

    public static boolean e(int n2, int n3) {
        return bh.f(n2 / 24, n3 / 24);
    }

    public static boolean f(int n2, int n3) {
        if (n2 < 1 || n2 > bm.bR - 1 || n3 < 1 || n3 > bm.bS - 1) {
            return true;
        }
        return cr.A(bh.h(n2, n3));
    }

    public static boolean g(int n2, int n3) {
        if (n2 < 1 || n2 > bm.bR - 1 || n3 < 1 || n3 > bm.bS - 1) {
            return true;
        }
        return bh.h(n2, n3) != -1;
    }

    public static int h(int n2, int n3) {
        if (n2 < 1 || n2 > bm.bR - 1 || n3 < 1 || n3 > bm.bS - 1) {
            return 23;
        }
        return bm.bQ[n2 + n3 * bm.bR];
    }

    public final void v() {
        this.p(22);
        this.p(23);
        if (this.B) {
            al.a(6, 0, 0);
        }
    }

    final void w() {
        bh bh2 = this;
        this.v = new long[5];
        bh2.U = new int[5];
        bh2.V = 0;
        bh2.N = 0;
        this.x();
        bh2 = this;
        this.P = new Vector();
        if (this.B) {
            bh2 = this;
            this.W = new int[10];
            bh2.X = new int[10];
            bh2.Y = 0;
            bh2.Z = 0;
            bh2 = this;
            this.aa = new int[10];
            bh2.ab = new int[10];
            bh2.ac = 0;
            bh2.ad = 0;
        }
    }

    private static boolean b(long l2) {
        return (int)(l2 >> 16 & 0xFFFFL) == 4;
    }

    final void a(long l2) {
        long l3 = l2;
        int n2 = (int)(l3 & 0xFFFFL);
        if (bh.b(l2)) {
            String cfr_ignored_0 = "" + this.C + "/" + n2 + " F?:" + bh.b(l2);
        }
        if (this.V >= 5) {
            for (n2 = 0; n2 < 4; ++n2) {
                this.v[n2] = this.v[n2 + 1];
                this.U[n2] = this.U[n2 + 1];
            }
            --this.V;
        }
        this.v[this.V] = l2;
        this.U[this.V] = 0;
        ++this.V;
        if (bh.b(l2) && !this.B) {
            bm.A(541);
            bm.d(5, 1, 1, 1);
        }
    }

    final void l(int n2) {
        if (this.Y >= 10) {
            for (int i2 = 0; i2 < 9; ++i2) {
                this.W[i2] = this.W[i2 + 1];
                this.X[i2] = this.X[i2 + 1];
            }
            --this.Y;
        }
        this.W[this.Y] = n2;
        this.X[this.Y] = 0;
        ++this.Y;
    }

    private void c(Graphics graphics, int n2, int n3) {
        if (this.Y <= 0) {
            return;
        }
        n3 -= 85;
        for (int i2 = 0; i2 < this.Y; ++i2) {
            int n4 = this.X[i2] * -30 / 1000;
            long l2 = this.W[i2];
            int n5 = (int)(l2 & 0xFFFFL);
            bz.b(3);
            bz.c(2);
            String string = n5 >= 0 ? "+" : "";
            bz.b((byte)2);
            bz.a(graphics, string + n5, n2, n3 + n4, 17);
            bz.c(0);
            bz.b((byte)1);
        }
    }

    final void m(int n2) {
        if (this.ac >= 10) {
            for (int i2 = 0; i2 < 9; ++i2) {
                this.aa[i2] = this.aa[i2 + 1];
                this.ab[i2] = this.ab[i2 + 1];
            }
            --this.ac;
        }
        this.aa[this.ac] = n2;
        this.ab[this.ac] = 0;
        ++this.ac;
    }

    private void h(Graphics graphics, int n2, int n3) {
        if (this.ac <= 0) {
            return;
        }
        n3 -= 60;
        for (int i2 = 0; i2 < this.ac; ++i2) {
            int n4 = this.ab[i2] * -30 / 1000;
            int n5 = this.aa[i2];
            bz.b(3);
            bz.c(1);
            String string = n5 >= 0 ? "+" : "";
            bz.b((byte)2);
            bz.a(graphics, string + n5, n2, n3 + n4, 17);
            bz.b((byte)1);
            bz.c(0);
        }
    }

    static ao n(int n2) {
        n2 = ae[n2 << 2];
        return ck.b(n2);
    }

    static int o(int n2) {
        return ae[(n2 << 2) + 1];
    }

    private static int t(int n2) {
        return n2 >>> 24;
    }

    private static int u(int n2) {
        return (byte)(n2 >> 16);
    }

    final void x() {
        this.af = new cy[5];
        this.ag = new int[5];
        for (int i2 = 0; i2 < 5; ++i2) {
            this.af[i2] = new cy();
            this.af[i2].a(null);
            this.ag[i2] = 0;
        }
    }

    final boolean p(int n2) {
        int n3;
        int n4;
        if (n2 <= 0) {
            return false;
        }
        int n5 = n2;
        int n6 = ae[(n5 << 2) + 2];
        bh bh2 = this;
        int n7 = n2;
        int n8 = ae[(n7 << 2) + 3];
        bh bh3 = bh2;
        if (n2 <= 0 || n2 >= 52) {
            return false;
        }
        int n9 = bh3.ag[4];
        if ((n9 & 0xFFFF) != 0) {
            return false;
        }
        for (n4 = 0; n4 < 5 && ((n9 = bh3.ag[n4]) & 0xFFFF) != 0; ++n4) {
            n9 = bh3.ag[n4];
            if ((n9 & 0xFFFF) == n2) {
                return false;
            }
            if (bh.u(bh3.ag[n4]) < n8) continue;
            cy cy2 = bh3.af[4];
            for (n3 = 4; n3 > n4; --n3) {
                bh3.ag[n3] = bh3.ag[n3 - 1];
                bh3.af[n3] = bh3.af[n3 - 1];
            }
            bh3.ag[n4] = 0;
            bh3.af[n4] = cy2;
            bh3.af[n4].a(null);
            break;
        }
        if (n4 < 0 || n4 >= 5) {
            return false;
        }
        int n10 = n8;
        n3 = n6;
        int n11 = n2;
        n8 = n11 & 0xFFFF | (n10 & 0xFF) << 16 | (n3 & 0xFF) << 24;
        n10 = bh.o(n2);
        ao ao2 = bh.n(n2);
        cy cy3 = bh3.af[n4];
        cy3.a(ao2);
        cy3.a(n10);
        n2 = (n6 & 1) != 0 ? 1 : 0;
        cy3 = bh3.af[n4];
        bh3.af[n4].e = n2;
        bh3.ag[n4] = n8;
        return true;
    }

    private void v(int n2) {
        if (n2 < 0 || n2 >= 5) {
            return;
        }
        int n3 = this.ag[n2];
        this.O.remove(new Integer(n3 & 0xFFFF));
        cy cy2 = this.af[n2];
        while (n2 < 4) {
            this.ag[n2] = this.ag[n2 + 1];
            this.af[n2] = this.af[n2 + 1];
            ++n2;
        }
        cy2.a(null);
        this.af[4] = cy2;
        this.ag[4] = 0;
    }

    final void y() {
        if (this.ag == null) {
            return;
        }
        if (this.af != null) {
            for (int i2 = 0; i2 < 5; ++i2) {
                int n2 = this.ag[i2];
                if ((n2 & 0xFFFF) == 0) continue;
                if (this.af[i2] != null && this.af[i2].a != null && this.af[i2].a() && ((n2 = bh.t(this.ag[i2])) & 1) == 0) {
                    if ((n2 & 2) != 0) continue;
                    this.v(i2);
                    --i2;
                    continue;
                }
                this.af[i2].c();
            }
        }
    }

    final void a(Graphics graphics) {
        int n2;
        boolean bl2;
        bh bh2 = this;
        if (bm.aq == -1 && !bm.ax) {
            bl2 = true;
        } else if (bh2.af != null) {
            for (n2 = 0; n2 < 5; ++n2) {
                if (bh2.af[n2] == null || bh2.af[n2].a == null || (bh.t(bh2.ag[n2]) & 4) == 0) continue;
                bl2 = true;
                break;
            }
        } else {
            bl2 = false;
        }
        if (!bl2) {
            return;
        }
        int n3 = this.k - bm.bW;
        n2 = this.l - bm.bX;
        aq.e(Integer.MIN_VALUE);
        aq.b(graphics, 0, 0, 640, 360);
        this.a(graphics, n3, n2);
    }

    final void a(cm object) {
        Object object2 = this.q(((cm)object).a);
        if (object2 != null) {
            this.b((cm)object2);
        }
        this.P.addElement(object);
        cm cm2 = object;
        object2 = this;
        if (cm2.c != null) {
            for (int i2 = 0; i2 < cm2.c.length; ++i2) {
                if (cm2.c[i2] == 0) continue;
                ((bh)object2).p(cm2.c[i2]);
            }
        }
        if (((cm)object).a(8) || ((cm)object).a(16)) {
            object = this;
            if (((bh)object).t) {
                this.s();
            }
        }
    }

    final cm q(int n2) {
        cm cm2 = null;
        for (int i2 = 0; i2 < this.P.size(); ++i2) {
            cm cm3 = (cm)this.P.elementAt(i2);
            if (cm3 == null || cm3.a != n2) continue;
            cm2 = cm3;
            break;
        }
        return cm2;
    }

    final void z() {
        int n2 = 0;
        int n3 = this.P.size();
        --n3;
        while (n3 >= 0 && n2 < 4) {
            cm cm2 = (cm)this.P.elementAt(n3);
            if (cm2 != null && cm2.e == 1) {
                long l2 = cm2.b - bm.m();
                int n4 = 70 + n2 * 25;
                if (l2 > 0L) {
                    bm.bi.setColor(10182707);
                    bm.bi.drawRect(n4 - 2, 68, 27, 27);
                    if (l2 > 5000L || l2 <= 5000L && bm.bj % 5 < 2) {
                        cm.a(bm.bi, cm2.d, n4, 70);
                    }
                    ++n2;
                }
            }
            --n3;
        }
    }

    final void A() {
        for (int i2 = 0; i2 < this.P.size(); ++i2) {
            cm cm2 = (cm)this.P.elementAt(i2);
            this.b(cm2);
        }
        this.P.removeAllElements();
    }

    final cm r(int n2) {
        for (int i2 = 0; i2 < this.P.size(); ++i2) {
            cm cm2 = (cm)this.P.elementAt(i2);
            if (cm2 == null || cm2.a != n2) continue;
            return cm2;
        }
        return null;
    }

    final void b(cm cm2) {
        int n2;
        if (cm2 == null) {
            return;
        }
        if (cm2.c == null) {
            return;
        }
        for (n2 = 0; n2 < cm2.c.length; ++n2) {
            int n3;
            if (cm2.c[n2] == 0) continue;
            int n4 = cm2.c[n2];
            bh bh2 = this;
            if (n4 >= 0) {
                for (int i2 = 0; i2 < 5; ++i2) {
                    int n5 = bh2.ag[i2];
                    if ((n5 & 0xFFFF) != n4) continue;
                    n3 = i2;
                    break;
                }
            } else {
                n3 = -1;
            }
            int n6 = n3;
            this.v(n6);
        }
        for (n2 = 0; n2 < this.P.size(); ++n2) {
            cm cm3 = (cm)this.P.elementAt(n2);
            if (cm3 == null || cm3.a != cm2.a) continue;
            this.P.removeElementAt(n2);
        }
    }

    final boolean s(int n2) {
        for (int i2 = 0; i2 < this.P.size(); ++i2) {
            cm cm2 = (cm)this.P.elementAt(i2);
            if (cm2 == null || !cm2.a(n2)) continue;
            return true;
        }
        return false;
    }

    static {
        boolean[] blArray = new boolean[]{false, false, false, false};
        w = false;
        x = new Vector();
        y = new Vector();
        z = new Vector();
        q = new int[2][2][];
        ae = new int[]{-1, -1, 0, 0, 35, 0, 0, 0, 34, 0, 0, 0, 35, 1, 0, 0, 42, 0, 1, 0, 42, 1, 1, 0, 35, 3, 4, 0, 33, 0, 0, 0, 31, 0, 0, 0, 32, 0, 0, 0, 32, 1, 1, 0, 32, 3, 0, 0, 32, 4, 0, 0, 32, 5, 1, 0, 32, 6, 0, 0, 32, 7, 0, 0, 36, 0, 0, 0, 30, 0, 0, 0, 35, 2, 0, 0, 42, 2, 0, 0, 42, 3, 0, 0, 32, 2, 0, -10, 37, 0, 4, -10, 37, 1, 4, 0, 39, 0, 0, 0, 40, 0, 0, 0, 38, 0, 1, -10, 38, 1, 1, 0, 43, 0, 2, 0, 32, 8, 1, 0, 41, 0, 1, -10, 41, 1, 1, 0, 37, 0, 1, -10, 32, 0, 4, 0, 51, 0, 0, 0, 52, 0, 0, 0, 35, 4, 0, 0, 54, 0, 0, 0, 56, 0, 0, 0, 57, 0, 0, 0, 58, 0, 0, 0, 59, 0, 0, 0, 44, 0, 12, -10, 45, 0, 12, -10, 46, 0, 12, -10, 47, 0, 1, 0, 60, 0, 0, 0, 61, 0, 0, 0, 65, 0, 16, 0, 62, 0, 0, 0, 63, 0, 0, 0, 64, 0, 0, 0};
    }
}

