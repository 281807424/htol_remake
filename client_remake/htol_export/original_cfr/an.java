/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import java.util.Vector;
import javax.microedition.lcdui.Graphics;

final class an
extends ad {
    private cy p;
    private u q;
    private Vector r;

    public an(int n2, int n3, int n4, ah ah2) {
        super(ah2, cg.d);
        this.d = n4;
        this.p = new cy(ck.b(n2), n3);
        n3 = 1;
        cy cy2 = this.p;
        this.p.e = n3;
        this.r = new Vector();
    }

    final void a() {
        Object object;
        int n2;
        super.a();
        ((an)((Object)cy2)).p.c();
        an an2 = cy2;
        if (an2.r != null || !an2.b()) {
            for (n2 = 0; n2 < an2.r.size(); ++n2) {
                az az2 = (az)an2.r.elementAt(n2);
                if (az2.b(an2.q)) {
                    az2.c(cg.d);
                } else {
                    az2.c(cg.b);
                }
                az2.a();
                if (az2.a(an2.q)) {
                    object = an2;
                    ((ad)object).a.f().a(8, 1);
                    object = an2;
                    ((ad)object).a.f().a(0, -1);
                    an2.r.removeElementAt(n2);
                }
                object = an2;
                if (az2.a(((ad)object).a.c())) continue;
                object = an2;
                ((ad)object).a.f().a(9, 1);
                an2.r.removeElementAt(n2);
                --n2;
            }
        }
        an2 = cy2;
        object = an2;
        int n3 = an2.a.c()[0];
        object = an2;
        n2 = ((ad)object).a.c()[1];
        an an3 = an2;
        object = an3;
        object = an2;
        n2 = (int)((ad)object).g + n2;
        n3 = (int)an3.f + n3;
        cy cy2 = an2.p;
        an2.p.f = n3;
        cy2.g = n2;
        if (an2.p.a() && an2.p.b == 2 || an2.p.b != 2) {
            if (!an2.b() && !an2.a(4) || an2.a.b() >= 2) {
                an2.p.a(1);
                int n4 = an2.d;
                object = an2.p;
                an2.p.h = n4;
            } else {
                int n5 = an2.d == 0 ? 1 : 0;
                an2.p.a(0);
                object = an2.p;
                an2.p.h = n5;
            }
        }
        if (!an2.b() || an2.a(4) && an2.a.b() < 2) {
            object = an2;
            if (((ad)object).f != 0.0f) {
                an2.b(0.0f, 0.0f);
                return;
            }
        } else {
            an an4 = an2;
            object = an4;
            an an5 = an2;
            object = an5;
            object = an2;
            float f2 = an4.a.e().g() + an5.a.e().h() * (float)((ad)object).a.f().a(1);
            an2.b(f2, 0.0f);
        }
    }

    private boolean b() {
        an an2;
        return an2.f > 430.0f;
    }

    final void a(Graphics graphics, int n2) {
        int n3 = n2;
        Graphics graphics2 = graphics;
        Object object = this;
        if (((an)object).r != null) {
            for (int i2 = 0; i2 < ((an)object).r.size(); ++i2) {
                az az2 = (az)((an)object).r.elementAt(i2);
                az2.a(graphics2, n3);
            }
        }
        if (!this.b(n2)) {
            return;
        }
        if (this.p == null) {
            System.out.println("unInit the pet anim!");
            return;
        }
        graphics2 = graphics;
        object = this.p;
        if (((cy)object).a != null && ((cy)object).d >= 0) {
            ((cy)object).a.a(graphics2, ((cy)object).b, ((cy)object).c, ((cy)object).f, ((cy)object).g, ((cy)object).h, 0, 0);
        }
    }

    final void a(int n2, int n3, int n4, u u2, int n5) {
        this.q = u2;
        az az2 = new az(ck.b(308), n4, 0, this.a);
        an an2 = this;
        Object object = an2;
        object = this;
        az2.a(an2.f + (float)-80, ((ad)object).g + (float)-40);
        u u3 = this.q;
        object = u3;
        object = this.q;
        az2.a((int)u3.f, (int)((ad)object).g, n5);
        az2.b();
        az2.a(true);
        this.r.addElement(az2);
        int n6 = this.d == 0 ? 1 : 0;
        this.p.a(2);
        this.p.c = 2;
        int n7 = n6;
        object = this.p;
        this.p.h = n7;
    }
}

