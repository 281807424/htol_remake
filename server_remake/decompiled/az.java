/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

final class az
extends ad {
    private int p;
    private static int[][] q = new int[][]{{4, 13}, {5, 14}, {6, 15}, {7, 16}, {8, 17}, {9, 18}, {10, 19}, {11, 20}, {12, -1}, {23, -1}};
    private static int[][] r = new int[][]{{0, -1}, {1, 0, 1, 2, 3}, {2, 4, 5, 6, 7}, {3, 8, 9}};
    private p s;
    private boolean t;
    private boolean u;
    private int v;

    static int f(int n2) {
        return q[n2][1];
    }

    public az(ao ao2, int n2, int n3, ah object) {
        super((ah)object, cg.d);
        az az2;
        az3.b = ao2;
        int n4 = n2;
        az3.c = q[n4][0];
        az3.d = n3;
        az3.d(n2);
        az az3 = az2 = az3;
        n2 = 0;
        n3 = 0;
        object = new int[]{0, 0, 0, 0};
        if (az3.b != null) {
            n2 = az3.b.f(az3.c);
            n3 = az3.b.g(az3.c);
            az3.b.a(az3.c, 0, (int[])object, az3.d);
        }
        super.a(n2, n3, (int)object[0], (int)object[1], (int)object[2], (int)object[3]);
        az3 = az2;
        n2 = 0;
        az2 = az3;
        az3.p = n2;
        n2 = 1;
        az2 = az3;
        az3.e = n2;
        az3.f = az3.h;
        az3.g = az3.i;
        az3.j = 0.0f;
        az3.k = 0.0f;
        az3.l = 0.0f;
        az3.m = 0.0f;
    }

    final void a(Graphics object, int n2) {
        if (!this.b(n2)) {
            return;
        }
        if (this.b != null) {
            az az2 = this;
            object = az2;
            object = this;
            this.b.a(this.c, (int)this.f + az2.a.c()[0], (int)this.g + object.a.c()[1], this.d);
        }
    }

    final void a() {
        int n2;
        super.a();
        az az2 = az5;
        if (!az2.a(4)) {
            az az3 = az2;
            if (az3.u) {
                az2.b();
                n2 = 1;
                az3 = az2;
                az2.e = n2;
                n2 = 0;
                az3 = az2;
                az2.u = n2;
            }
        }
        az2 = az5;
        if (az2.s != null) {
            float f2 = bo.b(bo.a(az2.j), bo.a(az2.k));
            f2 = bo.b(20.0f, f2);
            if (bo.a(az2.h - az2.f) < f2 && bo.a(az2.i - az2.g) < f2 && az2.h(1)) {
                n2 = 2;
                az az4 = az2;
                az2.p = n2;
            }
        }
        az az5 = az2 = az5;
        if (az2.t) {
            int n3;
            int[] nArray = new int[]{0, 7, 3, 4};
            int n4 = n3 = nArray[az2.v % nArray.length];
            az az6 = az2;
            az2.d = n4;
            ++az2.v;
        }
    }

    final boolean g(int n2) {
        for (int i2 = 1; i2 < r[n2].length; ++i2) {
            int n3 = r[n2][i2];
            az az2 = this;
            if (!(n3 == az2.d())) continue;
            return true;
        }
        return false;
    }

    private void c(float f2, float f3) {
        this.h = f2;
        this.i = f3;
        p p2 = this.s;
        int n2 = p2.c;
        this.l = az.a(this.j, f2 - this.f, n2);
        this.m = -az.a(-this.k, this.g - f3, n2);
    }

    private static float a(float f2, float f3, int n2) {
        if (!bo.a(f2, f3)) {
            f3 = bo.a(f3);
            f2 = bo.a(f2);
            f2 = 2.0f * (f3 + f2 * (float)n2) / (float)(n2 * n2);
            if (f3 < 0.0f) {
                f2 = -f2;
            }
        } else {
            f2 = 2.0f * (f3 - f2 * (float)n2) / (float)(n2 * n2);
        }
        return f2;
    }

    final boolean a(ad object) {
        if (!((ad)object).n.d()) {
            return false;
        }
        object = ((ad)object).c();
        int[] nArray = this.c();
        if (nArray[0] + nArray[2] < object[0] || nArray[0] > object[0] + object[2] || nArray[1] + nArray[3] < object[1] || nArray[1] > object[1] + object[3]) {
            return false;
        }
        return this.h(0);
    }

    final boolean b(ad ad2) {
        return this.g > ad2.g;
    }

    final void b() {
        if (this.s == null) {
            int n2 = 2;
            az az2 = this;
            this.p = n2;
            return;
        }
        if (this.g(3)) {
            p p2 = this.s;
            p p3 = p2;
            p3 = this.s;
            this.c(p2.a, p3.b);
            this.c(cg.f);
            return;
        }
        az az3 = this;
        int n3 = az3.d();
        this.c = q[n3][1];
        p p4 = this.s;
        Object object = p4;
        object = this.s;
        this.c(p4.a, ((p)object).b);
        int n4 = 1;
        object = this;
        this.p = n4;
        this.c(cg.h);
    }

    final void a(boolean bl2) {
        this.t = true;
    }

    final void a(int n2, int n3, int n4) {
        this.s = new p(this, n2, n3, n4);
    }

    final void e() {
        this.s = null;
    }

    final boolean h(int n2) {
        return this.p == n2;
    }

    final void b(boolean bl2) {
        this.u = bl2;
    }
}

