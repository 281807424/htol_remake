/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

final class cb
implements ae {
    private boolean a = false;
    private boolean b = false;
    private ao c;
    private int d = this.e;
    private int e = 1000;
    private int f;

    public cb(int n2) {
        this.c = ck.b(309);
    }

    final void a(Graphics graphics) {
        int n2;
        int n3 = n2 = this.c.g(5) + -2;
        int n4 = this.d;
        this.c.a(5, 0, 0, 0);
        int n5 = n4 * n3 / this.e;
        graphics.setColor(1491657);
        graphics.fillRect(389, n2 + 138 - n5, 6, n5);
    }

    final void a() {
        cb cb2 = this;
        if (cb2.a) {
            cb2 = this;
            this.f -= bo.a() % 5 + 8;
            if (cb2.f + cb2.d <= 0) {
                cb2.f = -cb2.f;
            }
            int cfr_ignored_0 = cb2.f;
            this.d += this.f;
            this.d = this.d > this.e ? this.e : this.d;
        }
    }

    final int b() {
        return this.d;
    }

    final void c() {
        this.a = true;
        this.b = false;
    }

    final void d() {
        this.a = false;
        this.b = true;
    }

    final boolean e() {
        return this.a;
    }

    final boolean f() {
        return this.b;
    }

    final void a(boolean bl2) {
        this.b = false;
    }
}

