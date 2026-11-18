/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

final class u
extends ad {
    private cy p;
    private ao q;
    private int r;

    public u(int n2, ah ah2) {
        super(ah2, cg.c);
        this.p = new cy(ck.b(n2), 1);
        this.d = 1;
        if (this.p == null) {
            System.out.println("unInit the pet anim!");
            return;
        }
        this.q = ck.b(308);
        this.r = 21;
        boolean n3 = true;
        Object object = this.p;
        this.p.e = n3;
        object = new int[4];
        this.p.a.a((int[])object, this.p.b, this.p.c, 0, 0, 0);
        reference var2_6 = object[2] - object[0];
        int n4 = bo.a((int)object[1]);
        int n5 = this.q.f(this.r) >> 1;
        int n6 = this.q.g(this.r);
        int n7 = -(n6 >> 1);
        super.a((int)var2_6, n4, 0, n7, n5, n6);
    }

    final void a(Graphics graphics, int n2) {
        if (!this.b(n2)) {
            return;
        }
        if (this.p == null) {
            System.out.println("unInit the pet anim!");
            return;
        }
        int[] nArray = this.c();
        u u2 = this;
        int n3 = u2.a.c()[0];
        u2 = this;
        int n4 = u2.a.c()[1];
        u2 = this;
        this.q.a(graphics, this.r, n3 + (int)u2.f - nArray[2], n4 + nArray[1], 0);
        u u3 = this;
        u2 = u3;
        u u4 = this;
        u2 = u4;
        u2 = this;
        this.p.a(graphics, (int)u3.f + n3, (int)u4.g + n4, u2.d);
    }

    /*
     * Enabled aggressive block sorting
     */
    final void a() {
        u u2;
        u u3;
        Object object;
        super.a();
        ((u)object).p.c();
        if (((ad)object).e(1)) {
            int n2;
            u u4;
            u u5;
            u u6;
            u u7 = object;
            if (u7.p != null && !u7.a(4) && !u7.e()) {
                u7.a(true);
            }
            u7 = object;
            if (bm.a(bm.dw, bm.dx, 0, 0, 640, 360)) {
                bm.F();
            }
            if (bm.a(bm.du, bm.dv, 0, 0, 640, 180)) {
                bm.t(16388);
            }
            if (bm.a(bm.du, bm.dv, 0, 180, 640, 180)) {
                bm.t(33024);
            }
            object = u7.c();
            u u8 = u7;
            int n3 = u8.a.c()[0];
            u8 = u7;
            int n4 = u8.a.c()[1];
            u8 = u7;
            n3 = (float)n3 + u6.g - (float)object[3] < 240.0f ? 1 : 0;
            u8 = u7;
            boolean bl2 = (float)n4 + u5.g + (float)object[3] > 365.0f;
            n4 = 4;
            u8 = u4 = u7;
            if (u4.a.d() == null) {
                n2 = -1;
            } else {
                u8 = u4;
                n2 = u8.a.d().c(4);
            }
            if (n2 >= 0) {
                u7.b(0.0f, 0.0f);
                return;
            }
            if (bm.w(16388) && n3 == 0) {
                u7.b(0.0f, -u7.f());
                return;
            }
            if (bm.w(33024) && !bl2) {
                u7.b(0.0f, u7.f());
                return;
            }
            u7.b(0.0f, 0.0f);
            return;
        }
        if (!((ad)object).e(0)) {
            if (!((ad)object).e(2)) return;
            u u9 = object;
            object = u9;
            u u10 = object;
            u u11 = object;
            u10 = u11;
            u10 = object;
            u9.b(((ad)object).a.e().g() + u11.a.e().h() * (float)u10.a.f().a(1), 0.0f);
            u9.n.a(false);
            return;
        }
        u u12 = u3 = object;
        if (u2.f > 180.0f) {
            u3.b(0.0f, 0.0f);
            u3.a(180.0f, 300.0f);
        }
        if (!u3.e()) {
            u12 = u3;
            if (u12.j != 0.0f) {
                u3.a(true);
            }
        }
        if (!u3.e()) return;
        u12 = u3;
        if (u12.j != 0.0f) return;
        u3.a(false);
    }

    private void a(boolean bl2) {
        if (bl2) {
            this.p.a(1, true);
            return;
        }
        this.p.a(0, true);
    }

    private boolean e() {
        return this.p.b == 1;
    }

    final void b() {
        this.a(false);
        this.a.f().a(3, -1);
    }

    private float f() {
        u u2 = this;
        u u3 = u2;
        u u4 = this;
        u3 = u4;
        u3 = this;
        return u2.a.e().c() + u4.a.e().d() * (float)u3.a.f().a(2);
    }
}

