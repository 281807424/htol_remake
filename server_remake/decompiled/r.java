/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import java.util.Vector;
import javax.microedition.lcdui.Graphics;

public final class r
extends bh
implements bj {
    public int q = -1;
    public int r;
    public int s;
    public int t = 0;
    public int u = 0;
    public cr v;

    public r() {
        this.a = 2;
    }

    final boolean h() {
        return this.r == 11 || this.r == 12;
    }

    final boolean i() {
        boolean bl2 = false;
        if (cr.aV != null && cr.aV.e()) {
            bl2 = this.C == cr.aV.C;
        }
        return bl2;
    }

    final boolean j() {
        if (this.A) {
            return false;
        }
        boolean bl2 = false;
        int n2 = t.a.size();
        block0: for (int i2 = 0; i2 < n2; ++i2) {
            t t2 = (t)t.a.elementAt(i2);
            if (t2 == null) continue;
            int n3 = t2.t.size();
            for (int i3 = 0; i3 < n3; ++i3) {
                ap ap2 = (ap)t2.t.elementAt(i3);
                if (ap2.b != 1 || ap2.c != this.t) continue;
                bl2 = true;
                continue block0;
            }
        }
        return this.r == 12 && bl2 || this.r != 12;
    }

    final void a(Graphics graphics, int n2, int n3) {
        r r2;
        if (this.A) {
            return;
        }
        n2 = n2 * 3 >> 1;
        n3 = n3 * 3 >> 1;
        if (this.j() && !(r2 = this).h()) {
            bm.a(graphics, 50, n2, n3);
        }
        this.d(graphics, n2, n3);
        if (this.v != null) {
            this.v.a(graphics, n2, n3, true, false);
            if (this.v.ae != null) {
                ay.b(graphics, this.v.ae, n2, n3 + -94 - bm.n, this.v.af, this.v.ag);
            }
        } else {
            this.b(graphics, n2, n3);
        }
        if (!this.h() || this.i()) {
            int n4 = n3;
            int n5 = n2;
            Graphics graphics2 = graphics;
            r2 = this;
            int n6 = -1;
            int n7 = -1;
            r r3 = r2;
            int n8 = n4 - r3.d.a(r3.f) - bm.D.g(36) - 40 + 45;
            switch (r2.q) {
                case 0: {
                    bm.D.j(0);
                    n7 = 1;
                    break;
                }
                case 1: {
                    bm.D.j(0);
                    n7 = 0;
                    break;
                }
                case 2: {
                    bm.D.j(1);
                    n6 = 37;
                    n5 -= bm.D.f(37);
                    break;
                }
                case 4: {
                    bm.D.j(1);
                    n6 = 36;
                    n5 -= bm.D.f(36);
                }
            }
            if (n6 != -1) {
                bm.D.a(graphics2, n6, n5, n8, 0);
            }
            if (n7 != -1) {
                bm.bv[n7].a(graphics2, n5, n8);
                bm.bv[n7].c();
            }
            bm.D.j(0);
        }
        if (this.r == 12 && this.j()) {
            this.g(graphics, n2, n3);
        }
    }

    final void k() {
        try {
            this.c();
            if (this.r == 12 && this.j()) {
                this.y();
            }
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    final void c(Graphics graphics, int n2, int n3) {
        if (this.A) {
            return;
        }
        if (cr.aV == this) {
            bz.a(graphics, 0xFFFFFF, -1160170, 4);
        } else {
            bz.a(graphics, 0xFFFFFF, -16757082, 4);
        }
        if (this.v != null) {
            ay.b(graphics, this.E, n2, n3 + -94, -16757082, 0xFFFFFF);
            return;
        }
        bz.a(graphics, this.E, n2, n3 - this.d.a(this.f) - 10, 33);
    }

    static {
        new Vector();
    }
}

