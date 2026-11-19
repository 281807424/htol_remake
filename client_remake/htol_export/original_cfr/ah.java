/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import java.util.Vector;
import javax.microedition.lcdui.Graphics;

public final class ah
implements ae {
    private cg b;
    private int[] c = new int[4];
    static ah a;
    private boolean d;
    private int e;
    private at f;
    private ba g;
    private by h;
    private bl i;
    private y j;
    private au k;
    private as l;
    private as m;

    public ah(int n2, int n3, int n4, int n5) {
        this.c[0] = 0;
        this.c[1] = 0;
        this.c[2] = 640;
        this.c[3] = 360;
        this.b = new cg(0);
    }

    final void a(Graphics graphics) {
        Object object = this;
        if (!((ah)object).d) {
            return;
        }
        if (!this.g()) {
            return;
        }
        int n2 = graphics.getClipX();
        int n3 = graphics.getClipY();
        int n4 = graphics.getClipWidth();
        int n5 = graphics.getClipHeight();
        graphics.setClip(this.c[0], this.c[1], this.c[2], this.c[3]);
        graphics.setColor(0);
        graphics.fillRect(this.c[0], this.c[1], this.c[2], this.c[3]);
        object = this;
        if (((ah)object).e < 4) {
            Graphics graphics2;
            ah ah2;
            for (int i2 = 0; i2 < cg.i; ++i2) {
                int n6 = i2;
                ah2 = this;
                ah2.b.a(n6);
                graphics2 = graphics;
                ah2 = this;
                object = ah2;
                ah2.l.a(graphics2, ((ah)object).b.a());
                object = ah2;
                ah2.m.a(graphics2, ((ah)object).b.a());
                graphics2 = graphics;
                ah2 = this;
                object = ah2;
                ah2.j.a(graphics2, ((ah)object).b.a());
                graphics2 = graphics;
                ah2 = this;
                object = ah2;
                int n7 = ((ah)object).b.a();
                Graphics graphics3 = graphics2;
                object = ah2.k;
                ((au)object).a.a(graphics3, n7);
                if (((au)object).b != null) {
                    ((au)object).b.a(graphics3, n7);
                }
                graphics2 = graphics;
                ah2 = this;
                object = ah2;
                ah2.i.a(graphics2, ((ah)object).b.a());
                graphics2 = graphics;
                ah2 = this;
                if (ah2.b(0)) {
                    bz.b(4);
                    bz.c(3);
                    bz.a(graphics2, "" + ah2.f(1) / 10, ah2.c[2] >> 1, ah2.c[3] >> 1, 3);
                    continue;
                }
                bz.b(3);
                bz.c(2);
                bz.a(graphics2, 0x1F88FF, -14257310, 4);
                bz.a(graphics2, "" + ah2.f(0) / 10, 470, 50, 1);
                bz.b((byte)1);
                bz.a(graphics2, -1, -15641174, 4);
                bz.a(graphics2, bm.A(367), 460, 50, 8);
                bz.b((byte)2);
                bz.a(graphics2, "" + ah2.f(2), 240, 54, 1);
                bz.b((byte)1);
                bz.a(graphics2, -1, -15619499, 4);
                bz.a(graphics2, bm.A(1369), 210, 50, 8);
            }
            graphics2 = graphics;
            ah2 = this;
            if (ah2.b(2) || ah2.b(3)) {
                object = ck.b(311);
                int n8 = ah2.c[0] + (ah2.c[2] >> 1) - (((ao)object).f(0) >> 1);
                int n9 = ah2.c[1] + (ah2.c[3] >> 1) - (((ao)object).g(0) >> 1);
                ((ao)object).a(graphics2, 0, n8, n9, 0);
            }
        } else {
            Graphics graphics4 = graphics;
            ah ah3 = this;
            ah3.f.a(graphics4);
        }
        graphics.setClip(n2, n3, n4, n5);
    }

    final void a() {
        Object object = this;
        if (!((ah)object).d || !this.g()) {
            return;
        }
        object = this;
        if (((ah)object).e < 4) {
            int n2;
            Object object2;
            int n3;
            ah ah2 = this;
            if (ah2.e(5) == 0) {
                n3 = 3;
                object = ah2;
                ah2.e = n3;
            }
            if (!(ah2.g(0) && ah2.a(0) || ah2.g(5) || !ah2.b(1))) {
                ah ah3 = ah2;
                int n4 = 4;
                object2 = new int[4][3];
                for (n2 = 0; n2 < ((int[][])object2).length; ++n2) {
                    object2[n2][0] = n4;
                    object2[n2][1] = ah3.d(n4);
                    ++n4;
                }
                n2 = ah3.f(2);
                int n5 = ah3.f(3);
                int n6 = ah3.d(9);
                bm.bn.a(n2, n5, n6, (int[][])object2);
                n3 = 2;
                object = ah2;
                ah2.e = n3;
                ah2.a(5, 30, -1);
                ah2.a(true);
            }
            if ((ah2 = this).e(1) == 0) {
                n3 = 1;
                object = ah2;
                ah2.e = n3;
            }
            if (ah2.b(1)) {
                ah2.e(0);
                object = ah2;
                if (!((ah)object).g(4)) {
                    n3 = (int)bo.a(ah2.h());
                    ah2.e(2);
                    ah2.a(2, n3);
                    if (ah2.d(8) <= 0) {
                        ah2.e(3);
                        ah2.a(3, n3);
                    }
                }
            }
            ah2 = this;
            Object object3 = ah2;
            ah2.j.a();
            object3 = ah2;
            if (((ah)object3).b(1)) {
                object = object3;
                if (((ah)object).g(4)) {
                    ((ah)object3).a(false);
                } else {
                    ((ah)object3).a(true);
                }
            }
            object = ah2 = this;
            if (ah2.e > 0) {
                ah2.l.a();
                ah2.m.a();
            }
            ah2 = this;
            object3 = ah2.k;
            if (((au)object3).b != null && ((au)object3).b.a() <= 0) {
                object = object3;
                ((au)object3).b = null;
            }
            ah2 = this;
            object3 = ah2.i.a();
            if (!((Vector)object3).isEmpty()) {
                block5: for (int i2 = 0; i2 < ((Vector)object3).size(); ++i2) {
                    n2 = (Integer)((Vector)object3).elementAt(i2);
                    object2 = ah2;
                    switch (n2) {
                        case 8: {
                            Object object4 = object2;
                            au au2 = ((ah)object4).k;
                            ((ah)object4).k.b = new be(au2);
                            continue block5;
                        }
                        case 1: {
                            Object object5 = object2;
                            ((ah)object5).l.a((int)super.h() + 4, 0);
                            ((ah)object5).m.a((int)super.h(), 0);
                            ((ah)object5).j.b();
                        }
                    }
                }
            }
            return;
        }
        ah ah4 = this;
        at at2 = ah4.f;
        if (at2.b()) {
            ((j)at2).a();
        }
    }

    final void a(int[][] nArray) {
        this.f.a(nArray);
    }

    final void a(String string) {
        this.f.a(string);
    }

    final void a(q q2) {
        ah ah2;
        ah ah3;
        int n2;
        int n3;
        this.h = new by();
        int n4 = q2.e();
        float f2 = q2.e();
        float f3 = q2.e();
        f3 = -f3;
        this.h.a(n4, f2, f3);
        float f4 = q2.e();
        f2 = q2.e();
        f2 = -f2;
        int n5 = q2.e();
        int n6 = q2.e();
        int n7 = q2.e();
        this.h.a(f4, f2, n5, n6, n7);
        int n8 = q2.e();
        for (n3 = 0; n3 < n8; ++n3) {
            byte by2 = q2.a();
            n7 = n6 = q2.e();
            n6 = by2;
            ah ah4 = this;
            if (ah4.i == null) {
                ah4.i = new bl(ah4.h.e());
            }
            ah4.i.b(n6, n7);
        }
        n8 = q2.e();
        for (n3 = 0; n3 < n8; ++n3) {
            byte by3 = q2.a();
            n6 = q2.a();
            n7 = q2.e();
            n2 = q2.a();
            this.h.b(by3, n6, n2, n7);
        }
        n3 = q2.e();
        int n9 = q2.e();
        n6 = q2.e();
        n7 = q2.a();
        this.h.a(n3, n9, n7, n6);
        n8 = q2.e();
        for (n2 = 0; n2 < n8; ++n2) {
            n3 = q2.e();
            n9 = q2.a();
            n6 = q2.e();
            this.h.a(n3, n9, n6);
        }
        ah ah5 = ah3 = (ah2 = this);
        ah2.a(0, ah5.h.a(), -1);
        ah5 = ah3;
        ah3.a(1, ah5.h.b(), -1);
        ah3.a(2, 1, 0);
        ah3.a(3, 1, 0);
        ah3 = ah2;
        ah2.l = new as(ck.b(308), 0, ah3.c);
        ah3.m = new as(ck.b(308), 1, ah3.c);
        boolean bl2 = true;
        bl2 = true;
        ah5 = ah3;
        ah5.l.a((int)ah5.h() + 4, 0);
        ah5.l.a(true);
        ah5.m.a((int)ah5.h(), 0);
        ah5.m.a(true);
        ah3 = ah2;
        ah2.j = new y();
        ah3.j.a(ah3);
        ah3 = ah2;
        ah2.k = new au(ah3);
        ah3 = ah2;
        ah2.f = new at(ah3.c);
        boolean bl3 = true;
        ah3 = ah2;
        ah2.d = true;
        int n10 = 0;
        ah ah6 = ah2;
        ah2.e = n10;
    }

    private boolean g() {
        return this.h != null;
    }

    private float h() {
        return this.h.g() + this.h.h() * (float)this.i.a(1);
    }

    final boolean a(int n2) {
        return this.i.a(0) > 0;
    }

    private int d(int n2) {
        return this.i.a(n2);
    }

    private void a(boolean bl2) {
        this.l.a(bl2);
        this.m.a(bl2);
    }

    final int b() {
        return this.e;
    }

    final boolean b(int n2) {
        return this.e == n2;
    }

    final void c(int n2) {
        this.e = n2;
    }

    private void a(int n2, int n3, int n4) {
        if (this.g == null) {
            this.g = new ba();
        }
        this.g.a(n2, n3, n4);
    }

    private int e(int n2) {
        return this.g.c(n2);
    }

    private int f(int n2) {
        return this.g.b(n2);
    }

    private boolean g(int n2) {
        return this.g.d(n2);
    }

    private void a(int n2, int n3) {
        this.g.a(n2, n3);
    }

    final int[] c() {
        return this.c;
    }

    final ba d() {
        return this.g;
    }

    final by e() {
        return this.h;
    }

    final bl f() {
        return this.i;
    }
}

