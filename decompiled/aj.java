/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

final class aj {
    private int a;
    private int b;
    private int c;
    private int d;
    private ao e;
    private int f;
    private int g;
    private int h;
    private boolean i;

    public aj(int n2, int n3, int n4, int n5, int n6) {
        this.a(n2, n3, n4, n5, n6);
    }

    final void a(int n2, int n3) {
        this.a(-1, -1, -1, n2, n3);
    }

    private void a(int n2, int n3, int n4, int n5, int n6) {
        ao ao2;
        if (n2 != -1) {
            this.a = n2;
        }
        if (n3 != -1) {
            this.b = n3;
        }
        if (n4 != -1) {
            this.e = ck.b(n4);
            ao2 = ck.b(n4);
        } else {
            ao2 = this.e;
        }
        if (n5 != -1 && this.g != n5) {
            this.g = n5;
        }
        if (n6 != -1 && this.h != n6) {
            this.h = n6;
        }
        this.c = ao2.f(n5);
        this.d = ao2.g(n5);
    }

    final boolean a() {
        boolean bl2 = this.i;
        this.i = false;
        return bl2;
    }

    final void a(Graphics graphics) {
        if (this.e == null) {
            return;
        }
        this.e.a(graphics, this.f, this.a, this.b, 0);
    }

    final void a(boolean bl2) {
        aj aj2 = this;
        if (aj2.f == aj2.h && !bl2) {
            this.i = true;
        }
        if (bl2) {
            this.f = this.h;
            return;
        }
        this.f = this.g;
    }

    final int b() {
        return this.d;
    }

    final int c() {
        return this.c;
    }

    final void b(int n2, int n3) {
        this.a = n2;
        this.b = 0;
    }
}

