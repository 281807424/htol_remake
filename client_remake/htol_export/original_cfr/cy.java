/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class cy {
    public ao a;
    public int b;
    public int c;
    public int d;
    private boolean i;
    public boolean e;
    public int f;
    public int g;
    public int h;

    public final boolean a() {
        return this.c >= this.a.c(this.b) - 1 && this.d >= this.a.a(this.b, this.c) - 1;
    }

    public cy() {
        this.b();
    }

    public final void b() {
        this.b = 0;
        this.c = 0;
        this.d = 0;
        this.i = false;
        this.e = false;
        this.h = 0;
    }

    public cy(ao ao2) {
        this.a(ao2);
    }

    public cy(ao ao2, int n2) {
        this.a(ao2);
        this.a(n2);
    }

    public final void a(int n2) {
        if (n2 != this.b || this.i) {
            this.b = n2;
            this.c = 0;
            this.d = 0;
            this.i = false;
            this.e = false;
        }
    }

    public final void a(int n2, boolean bl2) {
        this.a(n2);
        n2 = bl2 ? 1 : 0;
        v0.e = n2;
    }

    public final void a(ao ao2) {
        this.a = ao2;
        this.b();
    }

    public final void c() {
        if (this.a == null) {
            return;
        }
        if (this.d < 0) {
            return;
        }
        int n2 = this.a.a(this.b, this.c);
        if (n2 == 0) {
            return;
        }
        ++this.d;
        if (n2 > this.d) {
            return;
        }
        this.d = 0;
        ++this.c;
        if (this.c >= this.a.c(this.b)) {
            this.i = true;
            if (!this.e) {
                --this.c;
                return;
            }
            this.c = 0;
        }
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (this.a != null) {
            this.a.a(graphics, this.b, this.c, n2, n3, 0, 0, 0);
        }
    }

    public final void a(Graphics graphics, int n2, int n3, int n4) {
        if (this.a != null) {
            this.a.a(graphics, this.b, this.c, n2, n3, n4, 0, 0);
        }
    }
}

