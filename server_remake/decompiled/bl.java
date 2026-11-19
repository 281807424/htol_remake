/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import java.util.Vector;
import javax.microedition.lcdui.Graphics;

final class bl
implements ae {
    private cg a;
    private int[] b = new int[20];
    private cy c;

    public bl(int n2) {
        ((bl)((Object)cy2)).a = new cg(cg.g);
        ((bl)((Object)cy2)).c = new cy(ck.b(n2), 0);
        n2 = 1;
        cy cy2 = ((bl)((Object)cy2)).c;
        ((bl)((Object)cy2)).c.e = n2;
    }

    final void a(Graphics object, int n2) {
        bl bl2;
        int n3 = n2;
        bl bl3 = bl2 = bl4;
        if (!(bl2.a.a() == n3)) {
            return;
        }
        Graphics graphics = object;
        bl2 = bl4;
        bl bl4 = graphics;
        bl3 = bl2;
        object = ck.b(308);
        int n4 = ((ao)object).f(2);
        int n5 = ((ao)object).g(2);
        int n6 = bl4.getClipX();
        int n7 = bl4.getClipY();
        int n8 = bl4.getClipWidth();
        int n9 = bl4.getClipHeight();
        bl4.setClip(18, 16, n4 - 12, n5 - 18);
        int n10 = bl3.c.a.g(0) - bo.a(bl3.c.a.i(0));
        bl3.c.a((Graphics)bl4, 12 + (n4 >> 1), 10 + (n5 >> 1) + n10, 1);
        bl4.setClip(n6, n7, n8, n9);
        bl3.c.c();
        ((ao)object).a((Graphics)bl4, 2, 12, 10, 0);
        bl4 = graphics;
        bl3 = bl2;
        object = ck.b(308);
        bz.b(3);
        bz.c(1);
        for (n7 = 0; n7 < 8; ++n7) {
            if (n7 == 0) {
                int n11 = n7;
                bl2 = bl3;
                n8 = bl2.b[n11];
                n8 = n8 > 8 ? 8 : n8;
                for (n9 = 0; n9 < n8; ++n9) {
                    ((ao)object).a((Graphics)bl4, n7 + 13, ae.o[0][0] + n9 * 40, ae.o[0][1], 0);
                }
                continue;
            }
            n4 = n7;
            if (n7 < 3) {
                ((ao)object).a((Graphics)bl4, n7 + 13, ae.o[1][0], ae.o[1][1] + n4 * 40, 0);
                bz.a((Graphics)bl4, "" + bl3.b[n7], ae.o[1][0] + 60, ae.o[1][1] + n4 * 40 + 5, 5);
                continue;
            }
            n4 = n7 - 3;
            if (n7 >= 8) continue;
            ((ao)object).a((Graphics)bl4, n7 + 13, ae.o[2][0], ae.o[2][1] + n4 * 40, 0);
            bz.a((Graphics)bl4, "" + bl3.b[n7], ae.o[2][0] + 60, ae.o[2][1] + n4 * 40 + 5, 5);
        }
    }

    final void a(int n2, int n3) {
        this.b(n2, n3);
        this.b(n2 += 10, n3);
    }

    final Vector a() {
        Vector<Integer> vector = new Vector<Integer>();
        for (int i2 = 10; i2 < 20; ++i2) {
            if (this.b[i2] == 0) continue;
            this.b[i2] = 0;
            vector.addElement(new Integer(i2 - 10));
        }
        return vector;
    }

    final void b(int n2, int n3) {
        int n4 = n2;
        this.b[n4] = this.b[n4] + n3;
    }

    final int a(int n2) {
        return this.b[n2];
    }
}

