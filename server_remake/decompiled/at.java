/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

final class at
extends j
implements ae {
    private int[][] a;
    private ao b = ck.b(310);
    private ao c = ck.b(308);
    private aj d = new aj(464, 302, 310, 0, 1);
    private String e;
    private int f = this.b.f(2);
    private int g = this.b.g(2);
    private int h = this.b.f(3);
    private int i = this.b.g(4);

    public at(int[] nArray) {
        super(nArray);
    }

    final void a(int[][] nArray) {
        this.a = nArray;
        this.a(true);
    }

    final void b(Graphics graphics) {
        Graphics graphics2 = graphics;
        at at2 = this;
        at2.a(graphics2, 50, 50, 240, 230);
        at2.a(graphics2, 340, 50, 260, 230);
        graphics2 = graphics;
        at2 = this;
        at2.d.a(graphics2);
        String string = "";
        Object object = at2;
        if (((at)object).e != null && !((at)object).e.trim().equals("")) {
            string = at2.e;
        }
        bz.a(graphics2, -597591, -13032703, 4);
        object = bf.a(string, 220);
        bf.a(graphics2, string, (int[])object, 360, 70, 0, -1, 0, false);
        graphics2 = graphics;
        at2 = this;
        bz.b(3);
        bz.c(1);
        graphics2.setColor(65535);
        for (int i2 = 0; i2 < at2.a.length; ++i2) {
            int n2 = az.f(at2.a[i2][0]);
            at2.c.a(graphics2, n2, 70, 70 + i2 * 45, 0);
            bz.a(graphics2, "" + at2.a[i2][1], 120, 80 + i2 * 45, 5);
            graphics2.drawString("X", 147, 79 + i2 * 45, 0);
            bz.a(graphics2, "" + at2.a[i2][2], 180, 80 + i2 * 45, 5);
            graphics2.drawString("=", 210, 79 + i2 * 45, 0);
            bz.a(graphics2, "" + at2.a[i2][1] * at2.a[i2][2], 250, 80 + i2 * 45, 5);
        }
    }

    final void a() {
        at at2 = this;
        if (at2.d.a()) {
            bm.y(22);
        }
        at2 = this;
        at2.d.a(bm.a(bm.dy, bm.dz, 464, 302, at2.d.c(), at2.d.b()));
    }

    private void a(Graphics graphics, int n2, int n3, int n4, int n5) {
        int n6;
        n3 = (n4 - (this.f << 1)) / this.h + 1;
        n5 = (230 - (this.g << 1)) / this.i + 1;
        for (n6 = 0; n6 < n3; ++n6) {
            this.b.a(graphics, 3, n2 - 4 + this.f + n6 * this.h, 50, 0);
            this.b.a(graphics, 3, n2 - 4 + this.f + n6 * this.h, 280, 2);
        }
        for (n6 = 0; n6 < n5; ++n6) {
            this.b.a(graphics, 4, n2, 50 + this.g + n6 * this.i, 0);
            this.b.a(graphics, 4, n2 - 4 + n4, 50 + this.g + n6 * this.i, 1);
        }
        this.b.a(graphics, 2, n2 - 4, 50, 0);
        this.b.a(graphics, 2, n2 + n4, 50, 1);
        this.b.a(graphics, 2, n2 - 4, 280, 2);
        this.b.a(graphics, 2, n2 + n4, 280, 3);
        n6 = this.b.f(5);
        n3 = this.b.g(5);
        this.b.a(graphics, 5, n2 + (n4 >> 1) - (n6 >> 1), 50 - n3 + 4, 0);
    }

    final void a(String string) {
        this.e = string;
    }
}

