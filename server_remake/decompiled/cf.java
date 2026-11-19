/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

final class cf {
    private boolean a;
    private String b;
    private String c;
    private int d;

    public cf(String string, String string2) {
        this.b = string;
        this.c = string2;
    }

    final void a() {
        if (bm.v(262144)) {
            bm.y(22);
        }
        cf cf2 = this;
        if (!cf2.a) {
            return;
        }
        if (this.d >= 2 && this.d < 8) {
            ++this.d;
        }
        if (this.d > 8) {
            this.d = 8;
        }
    }

    final void a(Graphics graphics) {
        Object object = object2;
        if (!((cf)object).a) {
            return;
        }
        object = graphics;
        object.setClip(0, 0, 640, 360);
        if (ck.b(13) != null) {
            ck.b(13).a((Graphics)object, 0, 0, 0, 0);
        }
        aq.e(-939524096);
        aq.b((Graphics)object, 0, 0, 640, 360);
        bm.D.a((Graphics)object, 0, 0, 0, 0);
        bm.D.a((Graphics)object, 4, 0, 0, 0);
        Graphics graphics2 = graphics;
        object = object2;
        bz.a(graphics2, -597591, -13032703, 4);
        object = bm.A(666) + ((cf)object).b;
        bf.a(graphics2, (String)object, bf.a((String)object, 260), 190, 70, 0, -1, 0, false);
        int n2 = 253;
        graphics2 = graphics;
        cf cf2 = object2;
        Object object2 = bm.A(667);
        if (cf2.b()) {
            object2 = cf2.c;
        }
        bz.a(graphics2, 16179625, -5868702, -1);
        bz.a(graphics2, (String)object2, 320, 223, 1);
        bm.D.b(0, cf2.d * (bm.D.c(0) - 1) / 8, 320, 253);
    }

    final void a(int n2) {
        this.d = n2;
    }

    final boolean b() {
        return this.d >= 8;
    }

    final boolean c() {
        return this.a;
    }

    final void a(boolean bl2) {
        this.a = bl2;
    }
}

