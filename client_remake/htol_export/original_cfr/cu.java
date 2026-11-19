/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class cu {
    private static int[] c = new int[]{0xFF0000, 65280, 255, 0xFFFF00, 0xFF00FF, 65535};
    private c[] d;
    private int e;
    private int f;
    private int g;
    private int[] h;
    private ao i;
    private cy j;
    public static short[] a = new short[2];
    public static short[] b = new short[2];
    private static cu[] k = new cu[5];

    private boolean a(int n2) {
        return (this.f & n2) != 0;
    }

    private void a() {
        this.d = null;
        this.e = 0;
        this.f = 0;
        this.j = null;
        this.i = null;
        this.h = null;
        aq.j();
    }

    private void a(int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9, int n10) {
        n7 = n4;
        cu cu2 = cy2;
        ((cu)((Object)cy2)).f = n7;
        ((cu)((Object)cy2)).e = n3;
        if (((cu)((Object)cy2)).a(64)) {
            cu.a[0] = 0;
            cu.a[1] = 0;
            cu.b[0] = 640;
            cu.b[1] = 360;
        } else {
            cu.a[0] = (short)bm.bW;
            cu.a[1] = (short)bm.bX;
            cu.b[0] = (short)(bm.bW + 640);
            cu.b[1] = (short)(bm.bX + 360);
        }
        if (((cu)((Object)cy2)).a(256)) {
            ((cu)((Object)cy2)).j = new cy(((cu)((Object)cy2)).i, n2);
            n2 = 0;
            cy cy2 = ((cu)((Object)cy2)).j;
            ((cu)((Object)cy2)).j.e = n2;
            return;
        }
        ((cu)((Object)cy2)).d = new c[n2];
        c.e[0] = 0;
        c.e[1] = 1;
        int n11 = ((cu)((Object)cy2)).a(8) ? 360 : 0;
        n7 = super.a(64) ? 0 : bm.bW;
        n8 = super.a(64) ? 0 : bm.bX;
        for (n9 = 0; n9 < n2; ++n9) {
            ((cu)((Object)cy2)).d[n9] = new c();
            ((cu)((Object)cy2)).d[n9].f = (byte)bm.e(n3 / 6, n3 * 3 / 4);
            ((cu)((Object)cy2)).d[n9].c[0] = super.a(32) ? (short)(n7 + bm.e(0, 640)) : (short)(n7 + 320);
            ((cu)((Object)cy2)).d[n9].c[1] = (short)(n8 + n11);
            ((cu)((Object)cy2)).d[n9].d[0] = (short)bm.e(-n5, n5);
            ((cu)((Object)cy2)).d[n9].d[1] = super.a(8) ? (short)bm.e(n6 * -2, 0) : (short)bm.e(0, n6);
            if (super.a(1)) {
                n10 = bm.e(0, 5);
                ((cu)((Object)cy2)).d[n9].a = c[n10];
                ((cu)((Object)cy2)).d[n9].b = (byte)bm.e(1, 3);
                continue;
            }
            if (super.a(2)) {
                ((cu)((Object)cy2)).d[n9].b = (byte)bm.e(0, 2);
                continue;
            }
            if (!super.a(512)) continue;
            ((cu)((Object)cy2)).d[n9].b = (byte)3;
        }
    }

    public static boolean a(boolean bl2, int n2, int n3, int n4, int n5, int n6, int n7) {
        if (bl2 && k[0] != null) {
            return false;
        }
        int n8 = -1;
        for (int i2 = 1; i2 < 5; ++i2) {
            if (k[i2] != null) continue;
            n8 = i2;
            break;
        }
        if (n8 == -1 && !bl2) {
            return false;
        }
        cu cu2 = new cu();
        if ((n4 & 0x100) != 0 || (n4 & 2) != 0 || (n4 & 0x200) != 0) {
            cu2.i = ck.b(48);
        }
        int n9 = n7;
        n7 = n6;
        n6 = n5;
        n5 = n4;
        n4 = n3;
        n3 = n2;
        cu cu3 = cu2;
        if (n9 > 0) {
            cu3.h = new int[5];
            cu3.h[0] = n3;
            cu3.h[1] = n4;
            cu3.h[2] = n5;
            cu3.h[3] = n6;
            cu3.h[4] = n7;
        }
        cu3.g = n9;
        cu3.a(n3, n4, n5, n6, n7, 0, 640, 0, 360);
        if (bl2) {
            cu.k[0] = cu2;
        } else {
            cu.k[n8] = cu2;
        }
        return true;
    }

    public static void a(Graphics graphics, boolean bl2) {
        for (int i2 = 0; i2 < 5; ++i2) {
            if (k[i2] == null) continue;
            boolean bl3 = bl2;
            Graphics graphics2 = graphics;
            cu cu2 = k[i2];
            if (cu2.e-- < 0) {
                if (cu2.g-- >= 0 && cu2.h != null) {
                    cu2.a(cu2.h[0], cu2.h[1], cu2.h[2], cu2.h[3], cu2.h[4], 0, 640, 0, 360);
                }
            } else if (cu2.a(256) && cu2.j != null) {
                cu2.j.c();
                cu2.j.a(graphics2, 320, 180, 0);
                if (cu2.j.a() && cu2.g <= 0) {
                    cu2.a();
                }
            } else if (cu2.d != null && cu2.g >= 0) {
                for (int i3 = 0; i3 < cu2.d.length; ++i3) {
                    cu2.d[i3].a(cu2.a(514), cu2.a(4));
                    if (cu2.d[i3].a() && !cu2.a(4) || bl3 && !cu2.a(128)) continue;
                    int n2 = cu2.d[i3].a(cu2.a(64));
                    int n3 = cu2.d[i3].b(cu2.a(64));
                    graphics2.setClip(0, 0, 640, 360);
                    if (cu2.a(1)) {
                        graphics2.setColor(cu2.d[i3].a);
                        graphics2.fillRect(n2, n3, (int)cu2.d[i3].b, (int)cu2.d[i3].b);
                        continue;
                    }
                    if (!cu2.a(2) && !cu2.a(512) || cu2.i == null) continue;
                    cu2.i.a((cu2.d[i3].b << 2) + cu2.d[i3].a, n2, n3, 0);
                }
            }
            if (cu.k[i2].g >= 0) continue;
            k[i2].a();
            cu.k[i2] = null;
        }
    }
}

