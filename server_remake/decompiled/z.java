/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

final class z {
    private ao a;
    private int b;
    private int c;
    private int d;
    private int e;
    private int f;
    private int g;
    private int[] h;
    private boolean i;

    public z(int n2, int n3, int n4, int[] nArray) {
        this.a = ck.b(n2);
        this.b = n3;
        this.c = 0;
        this.h = nArray;
        this.a(0, 0);
        this.f = this.a.f(this.b);
        this.g = this.a.g(this.b);
    }

    final void a(Graphics graphics) {
        this.a.a(graphics, this.b, this.h[0] + this.d, this.h[1] + this.e, this.c);
    }

    final void a(int n2, int n3) {
        this.d = n2;
        this.e = n3;
    }

    final int a() {
        return this.d;
    }

    final int b() {
        return this.e;
    }

    final boolean c() {
        return this.i;
    }

    final void a(boolean bl2) {
        this.i = bl2;
    }

    final boolean d() {
        return bm.e(this.d, this.e, this.f, this.g);
    }
}

