/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

final class bp
implements ae {
    private boolean a;
    private boolean b;
    private float c;
    private int d;
    private int[] e;
    private int f;
    private z[] g;
    private ba h;
    private static int i = 0;
    private static int[][] j = new int[][]{{40, 1}, {30, 2}, {20, 3}, {10, 5}, {0, 11}};

    public bp(int[] nArray) {
        this.e = nArray;
    }

    final void a(z[] zArray) {
        int n2;
        this.h = new ba();
        this.g = zArray;
        for (n2 = 0; n2 < 5; ++n2) {
            zArray[n2].a(242 + n2 * 27, 127);
        }
        for (n2 = 0; n2 < 3; ++n2) {
            zArray[n2 + 5].a(350, 127 + 27 * (n2 + 1));
        }
        for (n2 = 0; n2 < 5; ++n2) {
            zArray[n2 + 8].a(242 + 27 * (4 - n2), 235);
        }
        for (n2 = 0; n2 < 3; ++n2) {
            zArray[n2 + 13].a(242, 127 + 27 * (3 - n2));
        }
    }

    final void a(Graphics graphics) {
        int n2;
        int n3 = 0;
        for (n2 = 0; n2 < this.g.length; ++n2) {
            if (this.g[n2] == null || !this.g[n2].c()) continue;
            n3 = n2;
            break;
        }
        if (n3 >= 0) {
            n2 = this.g[n3].a();
            n3 = this.g[n3].b();
            ao ao2 = ck.b(309);
            ao2.a(graphics, 6, this.e[0] + n2 + -7, this.e[1] + n3 + -16, 0);
        }
        for (n3 = 0; n3 < this.g.length; ++n3) {
            if (this.g[n3] == null) continue;
            this.g[n3].a(graphics);
        }
    }

    /*
     * Unable to fully structure code
     */
    final void a() {
        this.g[this.f].a(false);
        var1_1 = this;
        for (var2_2 = 0; var2_2 < var1_1.g.length; ++var2_2) {
            if (var1_1.g[var2_2] == null || !var1_1.g[var2_2].d()) continue;
            var3_5 = var1_1;
            if (var3_5.a) continue;
            var4_7 = var2_2;
            var3_5 = var1_1;
            var1_1.f = var4_7;
        }
        var1_1 = this;
        var1_1.h.c(0);
        if (var1_1.h.d(0)) ** GOTO lbl-1000
        var3_5 = var1_1;
        if (var3_5.a) {
            var2_3 = var1_1;
            var1_1.a = false;
            var2_3.b = true;
        } else lbl-1000:
        // 2 sources

        {
            var2_4 = var1_1;
            if (var2_4.h.d(0)) {
                var1_1 = var2_4;
                var3_6 = var1_1.h.b(0);
                if ((float)var3_6 % var1_1.c == (float)var1_1.d) {
                    var1_1 = var2_4;
                    var3_6 = var1_1.h.b(0);
                    if (var1_1.c != (float)(var3_6 = bp.c(var3_6))) {
                        var1_1.d(var3_6);
                    }
                    var2_4.g();
                }
            }
        }
        this.g[this.f].a(true);
    }

    private void g() {
        ++this.f;
        if (this.f >= this.g.length) {
            this.f = 0;
        }
        while (this.g[this.f] == null) {
            ++this.f;
            if (this.f < this.g.length) continue;
            this.f = 0;
        }
    }

    final void b() {
        int n2 = -1;
        bp bp2 = this;
        this.f = n2;
        this.g();
    }

    private static int c(int n2) {
        int n3 = -1;
        for (int i2 = 0; i2 < j.length - 1; ++i2) {
            if (n2 >= j[i2][0] && i2 == 0) {
                n3 = j[i2][1];
                break;
            }
            if (n2 >= j[i2][0] || n2 < j[i2 + 1][0]) continue;
            n3 = j[i2 + 1][1];
            break;
        }
        return n3;
    }

    private void d(int n2) {
        if (n2 <= 0) {
            this.c = 1.0f;
            return;
        }
        if (this.c <= 1.0f) {
            this.c = 1.0f;
        }
        int n3 = this.h.b(0);
        this.d = n3 % n2;
        this.c = n2;
    }

    final void a(int n2) {
        this.g[n2] = null;
        this.g();
    }

    final boolean c() {
        return this.a;
    }

    final boolean d() {
        return this.b;
    }

    final void b(int n2) {
        bp bp2 = this;
        this.a = true;
        bp2.b = false;
        bp2 = this;
        this.c = 0.0f;
        this.b();
        bp bp3 = this;
        bp2 = bp3;
        int n3 = n2;
        int n4 = bp2.f;
        bp bp4 = bp3;
        n3 -= n4 + 1;
        int n5 = 0;
        for (n4 = 0; n4 < n3; ++n4) {
            n5 += bp.c(n5);
        }
        this.h.a(0, n5, -1);
        this.d(1);
    }

    final void e() {
        this.b = false;
    }

    final int f() {
        return this.f;
    }
}

