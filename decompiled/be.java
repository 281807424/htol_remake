/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

final class be {
    private cg a;
    private Image b;
    private final au c;

    public be(au au2) {
        this.c = au2;
        be be2 = this;
        this.b = Image.createImage((int)au.a(be2.c)[2], (int)au.a(be2.c)[3]);
        Graphics graphics = be2.b.getGraphics();
        graphics.setColor(0xFFFFFF);
        graphics.fillRect(0, 0, au.a(be2.c)[2], au.a(be2.c)[3]);
        be2.b = bo.a(be2.b, -1426063361);
        this.a = new cg(cg.h);
        au.b(au2).a(6, 5, -1);
    }

    final void a(Graphics graphics, int n2) {
        be be2 = this;
        int n3 = n2;
        be be3 = be2;
        be3 = be2;
        if (!(be2.a.a() == n3)) {
            return;
        }
        int n4 = au.b(this.c).b(6);
        if (n4 % 2 == 0) {
            Graphics graphics2 = graphics;
            be be4 = this;
            graphics2.drawImage(be4.b, au.a(be4.c)[0], au.a(be4.c)[1], 0);
        }
    }

    final int a() {
        int n2 = 6;
        be be2 = this;
        int n3 = au.b(be2.c).c(6);
        return n3;
    }
}

