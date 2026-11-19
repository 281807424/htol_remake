/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import javax.microedition.lcdui.Graphics;

final class y
implements ae {
    private ah a;
    private ac b;
    private u c;
    private an d;

    y() {
    }

    final void a(ah ae2) {
        this.a = ae2;
        this.b = new ac(this, 4);
        ae2 = this;
        v0.c = new u(((y)ae2).a.e().e(), this.a);
        this.c.a(0.0f, 300.0f);
        this.c.b(8.0f, 0.0f);
    }

    final void a(Graphics graphics, int n2) {
        for (int i2 = 0; i2 < 4; ++i2) {
            for (int i3 = 0; i3 < this.b.a(i2); ++i3) {
                this.b.a(i2, i3).a(graphics, n2);
            }
        }
        int n3 = n2;
        Graphics graphics2 = graphics;
        ae ae2 = this;
        if (((y)ae2).d != null) {
            ((y)ae2).d.a(graphics2, n3);
        }
        graphics2 = graphics;
        ae2 = this.c;
        if (((ad)ae2).a(4) && ((ad)ae2).a(0) && ((ad)ae2).a.a(0)) {
            int n4;
            ae ae3;
            int n5;
            ae ae4;
            ae ae5;
            ae ae6 = ae2;
            n3 = ((ad)ae6).a.c()[0];
            ae6 = ae2;
            int n6 = ((ad)ae6).a.c()[1];
            int n7 = bm.D.f(69);
            ae6 = ae2;
            n3 = (int)((ad)ae6).f + n3 - ((n7 -= 4) >> 1);
            ae6 = ae2;
            ae6 = ae5 = ae2;
            int n8 = (((ad)ae5).d & 2) != 0 ? ((ad)ae5).n.b() : ((ad)ae5).n.c();
            n6 = (int)((ad)ae6).g + n6 - n8 - 4;
            n8 = 4;
            ae6 = ae4 = ae2;
            if (((ad)ae4).a.d() == null) {
                n5 = -1;
            } else {
                ae6 = ae4;
                n5 = ((ad)ae6).a.d().b(4);
            }
            int n9 = n5;
            bm.D.a(69, n3, n6, 0);
            n3 += 2;
            n6 += 2;
            int n10 = n9 * n7;
            n9 = 4;
            ae6 = ae3 = ae2;
            if (((ad)ae3).a.d() == null) {
                n4 = -1;
            } else {
                ae6 = ae3;
                n4 = ((ad)ae6).a.d().a(4);
            }
            int n11 = n10 / n4;
            n11 = n7 - n11;
            graphics2.setColor(1491657);
            graphics2.fillRect(n3, n6, n11, 2);
        }
        this.c.a(graphics, n2);
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    final void a() {
        block24: {
            this.c.a();
            var10_1 = this;
            if (var10_1.a.b() <= 0) break block24;
            var1_4 = this;
            var2_5 = -1;
            var4_7 = new Vector<az>();
            for (var5_9 = 0; var5_9 < 4; ++var5_9) {
                for (var6_13 = 0; var6_13 < var1_4.b.a(var5_9); ++var6_13) {
                    block25: {
                        var3_11 = var1_4.b.a(var5_9, var6_13);
                        var3_11.a();
                        var8_20 = var3_11;
                        var7_14 = var1_4;
                        if (!var8_20.h(0)) break block25;
                        if (var8_20.b(var7_14.c)) {
                            var8_20.c(cg.d);
                        } else {
                            var8_20.c(cg.c);
                        }
                        if (!var8_20.a(var7_14.c)) break block25;
                        if (!var8_20.g(2)) ** GOTO lbl45
                        if (var8_20.a(4)) break block25;
                        var10_2 = 3;
                        var9_23 /* !! */  = var7_14;
                        var10_1 = var9_23 /* !! */ ;
                        if (var9_23 /* !! */ .a.f() == null) ** GOTO lbl-1000
                        var10_1 = var9_23 /* !! */ ;
                        if (var10_1.a.f().a(3) > 0) {
                            v0 = true;
                        } else lbl-1000:
                        // 2 sources

                        {
                            v0 = false;
                        }
                        if (v0) {
                            var8_20.b(true);
                            var7_14.c.b();
                            var10_1 = var7_14;
                            var7_15 = -1;
                            var7_15 = var10_1.a.e().f();
                            var10_3 = 4;
                            var9_23 /* !! */  = var7_14.c;
                            var10_1 = var9_23 /* !! */ ;
                            if (var9_23 /* !! */ .a.d() != null) {
                                var10_1 = var9_23 /* !! */ ;
                                var10_1.a.d().a(4, var7_15, -1);
                            }
                        } else {
                            var8_20.d(8);
                            var8_20.e();
lbl45:
                            // 2 sources

                            var8_20.b();
                        }
                    }
                    var7_14 = var1_4;
                    var8_20 = new int[4];
                    v1 = var7_14;
                    var10_1 = v1;
                    var10_1 = var7_14;
                    System.arraycopy(v1.a.c(), 0, var8_20, 0, var10_1.a.c().length);
                    v2 = var8_20;
                    v2[0] = v2[0] - 100;
                    v3 = var8_20;
                    v3[1] = v3[1] - 100;
                    v4 = var8_20;
                    v4[2] = v4[2] + 200;
                    v5 = var8_20;
                    v5[3] = v5[3] + 200;
                    if (!var3_11.a((int[])var8_20)) {
                        if (var3_11.h(1)) {
                            var2_5 = var3_11.d();
                        }
                        var4_7.addElement(var3_11);
                        continue;
                    }
                    if (!var3_11.h(2)) continue;
                    var2_5 = var3_11.d();
                    var4_7.addElement(var3_11);
                }
                var8_19 = var2_5;
                var7_14 = var1_4;
                switch (var8_19) {
                    case -1: {
                        break;
                    }
                    case 8: {
                        var7_14.a(8, 1);
                        var7_14.a(0, -1);
                        break;
                    }
                    default: {
                        var7_14.a(var8_19, 1);
                    }
                }
                var2_5 = -1;
            }
            for (var5_9 = 0; var5_9 < var4_7.size(); ++var5_9) {
                var8_21 = (az)var4_7.elementAt(var5_9);
                var7_14 = var1_4;
                if (!var7_14.b.a(var8_21)) continue;
                var7_14.b.b(var8_21);
            }
            var4_7.removeAllElements();
            var2_6 = var1_4 = this;
            var3_12 = var1_4.a(2);
            var4_8 = var3_12 / 100 * 100;
            if (var2_6.d == null) {
                var10_1 = var2_6;
                if (var3_12 >= var10_1.a.e().k()) {
                    var9_23 /* !! */  = var2_6;
                    var10_1 = var9_23 /* !! */ ;
                    var10_1 = var9_23 /* !! */ .a.e();
                    var9_23 /* !! */ .d = new an(var10_1.i(), var10_1.j(), var10_1.l(), var9_23 /* !! */ .a);
                    var10_1 = var9_23 /* !! */ ;
                    var9_23 /* !! */ .d.a(var10_1.a.c()[2], 280.0f);
                }
            } else {
                var10_1 = var2_6;
                var5_10 = var10_1.a.e().a(var4_8);
                if (var5_10 != null) {
                    for (var6_13 = 0; var6_13 < var5_10.size(); ++var6_13) {
                        var7_17 = (Integer)var5_10.elementAt(var6_13);
                        var8_22 = var7_17;
                        var7_18 = var8_22 >> 16;
                        var2_6.d.a(-80, -40, var7_18, var2_6.c, var8_22 &= 65535);
                    }
                }
            }
            if (var1_4.d != null) {
                var1_4.d.a();
            }
            this.c();
            var1_4 = this;
            var3_12 = 4;
            var2_6 = var1_4;
            var10_1 = var1_4;
            if (var1_4.a.d().d(4)) {
                var10_1 = var1_4;
                if (var10_1.a.b() < 2) {
                    var1_4.a(false);
                    return;
                }
            }
            var1_4.a(true);
        }
    }

    private void c() {
        int n2 = this.a(2);
        float f2 = this.d();
        Object object = this;
        Hashtable hashtable = ((y)object).a.e().b(n2);
        Enumeration enumeration = hashtable.keys();
        while (enumeration.hasMoreElements()) {
            Integer n3 = (Integer)enumeration.nextElement();
            Vector vector = (Vector)hashtable.get(n3);
            for (int i2 = 0; i2 < vector.size(); ++i2) {
                int n4;
                Object object2 = (x)vector.elementAt(i2);
                int n5 = ((x)object2).b();
                int n6 = ((x)object2).c();
                int n7 = n4 = ((x)object2).a();
                float f3 = f2;
                int n8 = n6;
                n6 = n5;
                n5 = n3 % 100;
                object = object2 = this;
                object = object2;
                n5 = ((y)object2).a.c()[0] + ((y)object).a.c()[2] + n5;
                int n9 = 260 + n6 * 30;
                az az2 = new az(ck.b(308), n8, n7, ((y)object2).a);
                az2.a(n5, (float)n9);
                az2.b(f3, 0.0f);
                Object object3 = az2;
                int n10 = ((ad)object3).d();
                if (n10 == 0) {
                    ((az)object3).a(ae.o[0][0], ae.o[0][1], 10);
                } else {
                    n7 = n10;
                    if (n10 < 3) {
                        ((az)object3).a(ae.o[1][0], ae.o[1][1] + n7 * 40, 10);
                    } else {
                        n7 = n10 - 3;
                        if (n10 < 8) {
                            ((az)object3).a(ae.o[2][0], ae.o[2][1] + n7 * 40, 10);
                        }
                    }
                }
                az az3 = az2;
                n10 = n6;
                object3 = ((y)object2).b;
                ((ac)object3).a[n10].addElement(az3);
            }
        }
    }

    final void b() {
        for (int i2 = 0; i2 < 4; ++i2) {
            for (int i3 = 0; i3 < this.b.a(i2); ++i3) {
                az az2 = this.b.a(i2, i3);
                if (az2.h(1)) continue;
                az2.b(this.d(), 0.0f);
            }
        }
    }

    private void a(boolean bl2) {
        for (int i2 = 0; i2 < 4; ++i2) {
            for (int i3 = 0; i3 < this.b.a(i2); ++i3) {
                boolean bl3;
                az az2 = this.b.a(i2, i3);
                if (!az2.h(0) && !bl2) continue;
                v0.e = bl3 = bl2;
            }
        }
    }

    private void a(int n2, int n3) {
        y y2 = this;
        if (y2.a.f() == null) {
            return;
        }
        y2 = this;
        y2.a.f().a(n2, n3);
    }

    private float d() {
        y y2 = this;
        y y3 = y2;
        y y4 = this;
        y3 = y4;
        y3 = this;
        return y2.a.e().g() + y4.a.e().h() * (float)y3.a.f().a(1);
    }

    private int a(int n2) {
        return this.a.d().b(2);
    }
}

