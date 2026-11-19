/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

final class s {
    private int[] a;
    private boolean b;
    private boolean c;
    private boolean d;
    private int e;

    public s(int[] nArray) {
        this.a = nArray;
    }

    final void a(boolean bl2) {
        boolean bl3 = true;
        s s2 = this;
        this.b = bl3;
        boolean bl4 = true;
        s s3 = this;
        this.c = true;
        this.e = 0;
        this.d = bl2;
    }

    final void a(Graphics graphics) {
        s s2 = ao3;
        if (!s2.c) {
            return;
        }
        Graphics graphics2 = graphics;
        s2 = ao3;
        int n2 = graphics2.getClipX();
        int n3 = graphics2.getClipY();
        int n4 = graphics2.getClipWidth();
        int n5 = graphics2.getClipHeight();
        graphics2.clipRect(0, 0, s2.a[2], s2.e);
        graphics2.setColor(0);
        graphics2.fillRect(0, 0, s2.a[2], s2.e);
        ao ao2 = ck.b(308);
        ao2.a(graphics2, 22, 0, s2.e - ao2.g(22), 0);
        graphics2.setClip(n2, n3, n4, n5);
        graphics2 = graphics;
        s2 = ao3;
        n2 = graphics2.getClipX();
        n3 = graphics2.getClipY();
        n4 = graphics2.getClipWidth();
        n5 = graphics2.getClipHeight();
        int n6 = s2.a[3] - s2.e;
        graphics2.clipRect(0, n6, s2.a[2], s2.e);
        graphics2.setColor(0);
        graphics2.fillRect(0, n6, s2.a[2], s2.e);
        ao ao3 = ck.b(308);
        ao3.a(graphics2, 22, 0, n6, 0);
        graphics2.setClip(n2, n3, n4, n5);
    }

    final void a() {
        s s2 = this;
        if (!s2.c) {
            return;
        }
        s2 = this;
        this.e += s2.b ? 30 : -30;
        s2 = this;
        if (s2.e >= s2.a[3] >> 1) {
            this.e = this.a[3] >> 1;
            s2 = this;
            boolean bl2 = !s2.d;
            s2 = this;
            this.b = bl2;
        }
        if (this.e <= 0) {
            this.e = 0;
        }
    }

    final boolean b() {
        return this.b;
    }

    final void b(boolean bl2) {
        this.d = true;
    }
}

