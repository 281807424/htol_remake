/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class ce
extends bh
implements bj {
    byte q;
    int r;

    public ce() {
        this.a = 1;
    }

    final void a(Graphics graphics, int n2, int n3) {
        n2 = n2 * 3 >> 1;
        n3 = n3 * 3 >> 1;
        this.f(graphics, n2, n3);
        if (this.J != 4 || this.d == ck.b(159)) {
            bm.a(graphics, 50, n2, n3);
            this.d(graphics, n2, n3);
            this.b(graphics, n2, n3);
        }
        this.g(graphics, n2, n3);
        this.e(graphics, n2, n3);
    }
}

