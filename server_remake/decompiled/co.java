/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class co
implements ae {
    public static co a;
    private int[] c = new int[]{0, 0, 640, 360};
    private int d;
    bp b;
    private aj e;
    private aj f;
    private aj g;
    private cb h;

    public co(int n2, int n3, int n4, int n5) {
    }

    final void a() {
        ai.a(3);
        bm.X = 3;
        co co2 = this;
        this.e = new aj(0, 0, 309, 7, 8);
        co2.f = new aj(0, 0, 309, 11, 12);
        co2.f.b(co2.c[2] - co2.f.c(), 0);
        co2.g = new aj(237, 329, 309, 15, 16);
        co2 = this;
        this.h = new cb(1000);
        co2 = this;
        this.b = new bp(co2.c);
    }

    final void a(q q2) {
        int n2 = q2.a();
        z[] zArray = new z[n2];
        for (int i2 = 0; i2 < n2; ++i2) {
            int n3 = q2.c();
            int n4 = (0xFF80 & n3) >> 7;
            n3 = 0x7F & n3;
            switch (n4) {
                case 1: {
                    n4 = 26;
                    break;
                }
                case 2: {
                    n4 = 25;
                    break;
                }
                case 3: {
                    n4 = 307;
                    break;
                }
                default: {
                    n4 = 23;
                }
            }
            zArray[i2] = new z(n4, n3, 0, this.c);
        }
        this.d = q2.e();
        this.b.a(zArray);
    }

    final void b() {
        co co2 = this;
        if (co2.f.a()) {
            bm.y(22);
        }
        if (co2.g.a() && !co2.h.e() && !co2.b.c()) {
            bm.bn.T(co2.b.f());
        }
        if (co2.e.a() && !co2.b.c()) {
            if (!co2.h.e()) {
                co2.h.c();
                co2.b.b();
                co2.e.a(9, 10);
            } else {
                co2.h.d();
            }
        }
        co2.e.a();
        if (co2.h.f()) {
            bm.bn.U(co2.h.b());
            co2.h.a(false);
        }
        co2 = this;
        co2.b.a();
        co2 = this;
        if (bm.af != 3) {
            co2.e.a(bm.a(bm.dy, bm.dz, 272, 156, co2.e.c(), co2.e.b()));
            co2.f.a(bm.a(bm.dy, bm.dz, co2.c[2] - co2.f.c(), 0, co2.f.c(), co2.f.b()));
            co2.g.a(bm.a(bm.dy, bm.dz, 237, 329, co2.g.c(), co2.g.b()));
        }
        co2 = this;
        co2.h.a();
        co2 = this;
        if (co2.b.d() && !co2.h.e()) {
            co2.e.a(7, 8);
        }
        if (co2.b.d()) {
            System.out.println("CLottery.gameStart() \u6211\u64e6 = " + co2.b.f());
            bm.bn.V(co2.b.f());
            co2.b.e();
        }
    }

    final void a(Graphics graphics) {
        Graphics graphics2 = graphics;
        co co2 = this;
        graphics2.setColor(0);
        graphics2.fillRect(co2.c[0], co2.c[1], co2.c[2], co2.c[3]);
        graphics2 = graphics;
        co2 = this;
        ao ao2 = ck.b(309);
        ao2.a(graphics2, 0, co2.c[0], co2.c[1], 0);
        ao2.a(graphics2, 2, co2.c[0], co2.c[1], 0);
        ao2.a(graphics2, 1, co2.c[0], co2.c[1], 0);
        ao2.a(graphics2, 3, co2.c[0], co2.c[1], 0);
        ao2.a(graphics2, 4, co2.c[0], co2.c[1], 0);
        bz.b((byte)1);
        bz.a(graphics2, -1, -6724045, 4);
        bz.a(graphics2, "" + co2.d, 335, 296, 4);
        bz.a(graphics2, bm.A(1370), 300, 296, 8);
        bz.a(graphics2, -1, -13408615, 4);
        bz.a(graphics2, bm.A(1371), 260, 326, 4);
        graphics2 = graphics;
        co2 = this;
        co2.e.a(graphics2);
        co2.f.a(graphics2);
        co2.g.a(graphics2);
        graphics2 = graphics;
        co2 = this;
        co2.b.a(graphics2);
        graphics2 = graphics;
        co2 = this;
        co2.h.a(graphics2);
    }
}

