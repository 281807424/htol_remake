/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class ai {
    public static ai a;
    private int b;
    private boolean c;
    private cf d;
    private s e;

    public static void a() {
        cp.a();
        bm.y(42);
        a = new ai();
    }

    public static void a(int n2) {
        if (a == null) {
            ai.a();
        }
        ai ai2 = a;
        int n3 = n2;
        ai ai3 = ai2;
        ai3 = ai2;
        ai2.b = n3;
    }

    public final void a(Graphics graphics) {
        ai ai2 = this;
        if (!ai2.c) {
            return;
        }
        ai2 = this;
        if (!ai2.d.c()) {
            ai2 = this;
            if (!ai2.e.b()) {
                ai2 = this;
                switch (ai2.b) {
                    case 2: {
                        ah.a.a(graphics);
                        break;
                    }
                    case 3: {
                        co.a.a(graphics);
                    }
                }
            }
        }
        Graphics graphics2 = graphics;
        ai2 = this;
        if (this.d != null) {
            ai2.d.a(graphics2);
        }
        graphics2 = graphics;
        ai2 = this;
        if (ai2.e != null) {
            ai2.e.a(graphics2);
        }
    }

    public final void b() {
        ai ai2;
        ai ai3 = this;
        if (!ai3.c) {
            return;
        }
        ai ai4 = ai2 = this;
        ai3 = ai2;
        if (ai2.d.c()) {
            ai ai5 = ai4;
            if (ai5.d.b() && bm.aj()) {
                boolean bl2 = false;
                ai5 = ai4;
                ai5.d.a(false);
                ai4.a(true);
            }
        }
        ai4 = ai2;
        if (ai4.d != null) {
            ai4.d.a();
        }
        ai4 = ai2;
        if (ai4.e != null) {
            ai4.e.a();
        }
        ai4 = ai2;
        boolean bl3 = false;
        ai3 = ai4;
        switch (ai3.b) {
            case 2: {
                bl3 = ah.a.b(3);
                if (!bl3) break;
                ah.a.c(4);
            }
        }
        if (bl3) {
            ai4.a(true);
        }
        ai3 = this;
        if (!ai3.d.c()) {
            ai3 = this;
            if (!ai3.e.b()) {
                ai3 = this;
                switch (ai3.b) {
                    case 2: {
                        ah.a.a();
                        return;
                    }
                    case 3: {
                        co.a.b();
                    }
                }
            }
        }
    }

    final void a(String object, String string) {
        this.d = new cf((String)object, string);
        object = new int[]{0, 0, 640, 360};
        this.e = new s((int[])object);
        object = this;
        object = ((ai)object).d;
        ((cf)object).a(true);
        this.b(0);
        this.c = true;
    }

    final void b(int n2) {
        this.d.a(n2);
    }

    private void a(boolean bl2) {
        this.e.a(true);
    }

    final void c() {
        this.e.b(true);
    }
}

