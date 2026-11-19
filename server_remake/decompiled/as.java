/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

final class as {
    private cg a;
    private ao b;
    private int c;
    private int d;
    private int[] e;
    private int f;
    private int g;
    private int h;
    private int i;
    private boolean j;
    private int k;
    private int l;
    private int m;
    private int n;

    public as(ao ao2, int n2, int[] nArray) {
        this.b = ao2;
        this.c = n2;
        this.h = ao2.f(n2);
        this.i = ao2.g(n2);
        this.e = nArray;
        v0.d = 0;
        this.f = 0;
        this.g = 0;
        this.k = 0;
        this.l = 0;
        this.m = 0;
        this.n = 0;
        this.j = false;
        this.a = new cg(0);
    }

    final void a(Graphics graphics, int n2) {
        int n3 = n2;
        as as2 = this;
        if (!(as2.a.a() == n3)) {
            return;
        }
        Graphics graphics2 = graphics;
        as2 = this;
        int n4 = graphics2.getClipX();
        int n5 = graphics2.getClipY();
        int n6 = graphics2.getClipWidth();
        int n7 = graphics2.getClipHeight();
        graphics2.setClip(as2.e[0], as2.e[1], as2.e[2], as2.e[3]);
        Graphics graphics3 = graphics2;
        int n8 = as2.e[0];
        int n9 = as2.e[1];
        as2.b.a(graphics3, as2.c, as2.f + n8, as2.g + n9, 0);
        if (as2.k != 0) {
            if (as2.e[0] + as2.e[2] > as2.f + as2.h) {
                as2.b.a(graphics3, as2.c, as2.f + n8 + (as2.h - 1), as2.g + n9, 0);
            }
            if (as2.e[0] < as2.f) {
                as2.b.a(graphics3, as2.c, as2.f + n8 - (as2.h - 1), as2.g + n9, 0);
            }
        }
        if (as2.l != 0) {
            if (as2.e[1] + as2.e[3] > as2.g + as2.i) {
                as2.b.a(graphics3, as2.c, as2.f + n8, as2.g + n9 + (as2.i - 1), 0);
            }
            if (as2.e[1] < as2.g) {
                as2.b.a(graphics3, as2.c, as2.f + n8, as2.g + n9 - (as2.i - 1), 0);
            }
        }
        graphics2.setClip(n4, n5, n6, n7);
    }

    final void a() {
        if (this.j) {
            this.f += this.k;
            this.g += this.l;
            if (bo.a(this.e[0] - this.f) >= this.h) {
                this.f = this.e[0];
            }
            if (bo.a(this.e[1] - this.g) >= this.i) {
                this.g = this.e[1];
            }
        }
    }

    final void a(boolean bl2) {
        this.j = bl2;
    }

    final void a(int n2, int n3) {
        this.k = n2;
        this.l = 0;
    }
}

