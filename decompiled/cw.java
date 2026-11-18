/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class cw {
    public static boolean a = false;
    public int b = 0;
    public static boolean c;
    private static int d;

    public cw() {
        if (bm.bn == null) {
            bm.bn = new cc();
        }
        System.gc();
        try {
            Thread.sleep(100L);
            return;
        }
        catch (InterruptedException interruptedException) {
            InterruptedException interruptedException2 = interruptedException;
            interruptedException.printStackTrace();
            return;
        }
    }

    public final void a() {
        al.c();
        ck.a();
        bm.C = null;
        this.b = a ? 5 : 0;
        cr.aD = false;
        bm.G();
        bm.X = 3;
        d = bm.e(1, 73);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final void b() {
        try {
            switch (this.b) {
                case 0: {
                    this.b = bm.L();
                    return;
                }
                case 1: {
                    this.b = af.a(false, 7) ? 5 : 2;
                    return;
                }
                case 2: {
                    bm.bn.m();
                    this.b = 3;
                    return;
                }
                case 4: {
                    af.a(true, 7);
                    this.b = 5;
                    return;
                }
                case 5: {
                    bm.U = new bq();
                    bm.U.a(bm.bR, bm.bS, bm.bQ, 1, 1, 0, 8, 106);
                    ck.c();
                    bm.o();
                    ck.a(bm.bs);
                    bm.es = 0;
                    ck.b(85, 275);
                    this.b = 6;
                    return;
                }
                case 6: {
                    for (int i2 = 0; i2 < ck.b() / 2; ++i2) {
                        ck.a(false, bm.es++);
                    }
                    this.b = 7;
                    return;
                }
                case 7: {
                    for (int i3 = 0; i3 < ck.b() / 2; ++i3) {
                        ck.a(false, bm.es++);
                    }
                    this.b = 8;
                    return;
                }
                case 8: {
                    while (bm.es < ck.b()) {
                        ck.a(false, bm.es++);
                    }
                    bm.p();
                    this.b = 9;
                    return;
                }
                case 9: {
                    c = false;
                    if (bm.bn != null) {
                        bm.bn.d();
                    }
                    this.b = 10;
                    return;
                }
                case 10: {
                    if (!c) return;
                    this.b = 11;
                    return;
                }
                case 11: {
                    bm.ai();
                    bm.X = 3;
                    bm.y(22);
                    bm.u();
                }
                default: {
                    return;
                }
            }
        }
        catch (Exception exception) {
            bg.a("loadErr at" + this.b + " " + exception.toString());
        }
    }

    public final void a(Graphics graphics) {
        Object object = graphics;
        object.setClip(0, 0, 640, 360);
        if (ck.b(13) != null) {
            ck.b(13).a((Graphics)object, 0, 0, 0, 0);
        }
        aq.e(-939524096);
        aq.b(object, 0, 0, 640, 360);
        bm.D.a((Graphics)object, 0, 0, 0, 0);
        bm.D.a((Graphics)object, 4, 0, 0, 0);
        object = graphics;
        bz.a(object, -597591, -13032703, 4);
        String string = bm.A(666) + bm.d(2, d);
        bf.a(object, string, bf.a(string, 260), 190, 70, 0, -1, 0, false);
        if (d >= 45) {
            int n2 = -1;
            switch (d) {
                case 45: {
                    n2 = 296;
                    break;
                }
                case 46: {
                    n2 = 298;
                    break;
                }
                case 47: {
                    n2 = 284;
                }
            }
            if (n2 > 0) {
                n.a(object, 305, 180, n2, 0, 1, false, false, 0);
            }
        }
        int n3 = 253;
        Graphics graphics2 = graphics;
        object = this;
        bz.a(graphics2, 16179625, -5868702, -1);
        bz.a(graphics2, bm.A(667), 320, 233, 1);
        bm.D.b(0, object.b * (bm.D.c(0) - 1) / 11, 320, 253);
    }
}

