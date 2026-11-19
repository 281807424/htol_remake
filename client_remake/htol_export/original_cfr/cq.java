/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

final class cq {
    private cg a;
    private Image b;
    private ao c;
    private final au d;

    public cq(au au2) {
        this.d = au2;
        v0.a = new cg(cg.e);
        this.b = Image.createImage((int)130, (int)au.a(this.d)[3]);
        au2 = this.b.getGraphics();
        au2.setColor(0);
        au2.fillRect(0, 0, 130, au.a(this.d)[3]);
        this.b = bo.a(this.b, -1996488705);
        this.c = ck.b(308);
    }

    final void a(Graphics graphics, int n2) {
        cq cq2;
        int n3 = n2;
        cq cq3 = cq2 = this;
        if (!(cq2.a.a() == n3)) {
            return;
        }
        Graphics graphics2 = graphics;
        cq2 = this;
        graphics2.drawImage(cq2.b, au.a(cq2.d)[0], au.a(cq2.d)[1], 0);
        graphics2.drawImage(cq2.b, au.a(cq2.d)[2] - 130 + au.a(cq2.d)[0], au.a(cq2.d)[1], 0);
        int n4 = cq2.c.f(3);
        cq2.c.a(graphics2, 3, 130 - n4 + au.a(cq2.d)[0], au.a(cq2.d)[1], 0);
        cq2.c.a(graphics2, 3, au.a(cq2.d)[2] - 130 + au.a(cq2.d)[0], au.a(cq2.d)[1], 0);
    }
}

