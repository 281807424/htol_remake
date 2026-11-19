/*
 * Decompiled with CFR 0.152.
 */
import java.util.Hashtable;
import java.util.Vector;

public final class cc {
    public static int a = 0;
    public static int b = 0;
    private byte[] g;
    private short h = 0;
    private static int i = 0;
    public static Vector c = new Vector();
    public long d = bm.m();
    private byte[] j = null;
    private int k = 0;
    private short l = 0;
    private int m = 0;
    private int n = 0;
    private byte o;
    public static boolean e;
    private static int p;
    public ct f;
    private boolean q;

    public static void a() {
        i = 0;
    }

    public static int a(long l2, int n2) {
        int n3 = n2;
        int n4 = i;
        long l3 = l2;
        n4 = n4 < 0 || n3 < 1 ? 0 : (n4 = (int)(l3 >> n4 & (long)((1 << n3) - 1)));
        i += n2;
        return n4;
    }

    private static bh b(int n2, boolean bl2) {
        for (int i2 = 0; i2 < c.size(); ++i2) {
            bh bh2 = (bh)c.elementAt(i2);
            if (bh2 == null || bh2.C != n2) continue;
            c.removeElementAt(i2);
            if (!bl2) {
                c.addElement(bh2);
            }
            return bh2;
        }
        return null;
    }

    private void P() {
        int n2 = this.aj();
        bh bh2 = bh.f(n2);
        if (bh2 == null) {
            bh2 = cc.b(n2, false);
        }
        if (bh2 == null) {
            this.W(n2);
            String cfr_ignored_0 = "request full data of object  id" + n2;
            return;
        }
        int n3 = n2;
        this.c(8, (short)2151);
        this.Y(n3);
        this.ca();
        String cfr_ignored_1 = "request  object updating data id  " + n2;
    }

    private void Q() {
        int n2 = this.aj();
        if (bm.cb == null || n2 == bm.cb.C) {
            return;
        }
        bh bh2 = bh.f(n2);
        if (bh2 == null) {
            return;
        }
        String cfr_ignored_0 = "leave view id " + n2;
        if (bh2 == cr.aV) {
            cr.aV = null;
        }
        bh.g(n2);
        if (bh2.g()) {
            ((cr)bh2).e(false);
        }
        if (c.size() >= 20) {
            c.removeElementAt(0);
        }
        c.addElement(bh2);
        for (n2 = 0; n2 < c.size(); ++n2) {
            bh2 = (bh)c.elementAt(n2);
            if (bh2 != null) continue;
            c.removeElementAt(n2);
            --n2;
        }
    }

    private void R() {
        cc cc2;
        int n2 = this.aj();
        int n3 = this.aj();
        int n4 = this.ah();
        int n5 = this.ah();
        long l2 = this.al();
        i = 0;
        int n6 = cc.a(l2, 3);
        cc.a(l2, 4);
        int n7 = cc.a(l2, 8);
        int n8 = cc.a(l2, 1);
        int n9 = cc.a(l2, 1);
        int n10 = cc.a(l2, 1);
        int n11 = cc.a(l2, 1);
        int n12 = cc.a(l2, 3);
        int n13 = cc.a(l2, 3);
        cc.a(l2, 1);
        cc.a(l2, 1);
        int n14 = cc.a(l2, 1);
        byte by2 = (byte)cc.a(l2, 4);
        l2 = this.ag();
        i = 0;
        byte by3 = (byte)cc.a(l2, 2);
        int n15 = this.ag();
        int n16 = this.ag();
        String string = this.am();
        int n17 = 0;
        int n18 = 0;
        int n19 = 0;
        if (n9 == 1) {
            n17 = this.ag();
            n18 = this.aj();
            n19 = this.ah();
        }
        n9 = this.ah();
        int n20 = -1;
        String string2 = "";
        byte by4 = 0;
        byte by5 = 0;
        byte by6 = 0;
        byte by7 = 0;
        if (n10 == 1) {
            cc2 = this;
            n20 = cc2.ai();
            n10 = this.ae();
            string2 = this.m(0, n10);
            by4 = this.ae();
            by5 = this.ae();
            by6 = this.ae();
            by7 = this.ae();
        }
        n10 = n2;
        cr cr2 = null;
        cr2 = bm.cb != null && n10 == bm.cb.C ? bm.cb : bh.e(n10);
        n10 = 0;
        if (null == cr2 && n3 == bm.cj) {
            n10 = 1;
            cr2 = new cr();
        }
        if (cr2 == null) {
            return;
        }
        cr2.A = n8 == 1;
        cr2.ai = (byte)n11;
        cr2.a(string, n2, (byte)n13, (byte)n12);
        cr2.aj = by3;
        if (n14 == 1) {
            by3 = this.ae();
            cr2.ae = this.m(0, by3);
            cr2.ag = this.aj();
            cr2.af = this.aj();
        }
        cc2 = this;
        cr2.ah = cc2.ai();
        this.a(cr2, false);
        cr2.b(n3, n4, n5, n6);
        cr2.a(n20, string2, by4, by5, by6, by7);
        cr2.b(n17, n19, n18);
        cr2.U = (byte)n7;
        cr2.H = (byte)n15;
        cr2.I = (byte)n16;
        cr2.ak = by2;
        cr2.i(6, n9);
        cr.a(cr2);
        if (n10 != 0) {
            bh.y.addElement(cr2);
        } else {
            bm.aa();
            if (!bh.y.contains(cr2)) {
                bh.y.addElement(cr2);
            }
        }
        String cfr_ignored_0 = by2 + "data of player id" + n2 + " X=" + n4 + " y=" + n5 + " Name=" + cr2.E;
    }

    private void S() {
        int n2 = this.aj();
        boolean bl2 = false;
        bh bh2 = bh.e(n2);
        if (bh2 == null && (bh2 = cc.b(n2, true)) != null) {
            bl2 = true;
        }
        if (bh2 == null || !bh2.g()) {
            String cfr_ignored_0 = "receive updating data of player,but here has no data id" + n2;
            return;
        }
        int n3 = this.aj();
        int n4 = this.ah();
        int n5 = this.ah();
        long l2 = this.al();
        i = 0;
        int n6 = cc.a(l2, 3);
        cc.a(l2, 4);
        int n7 = cc.a(l2, 8);
        int n8 = cc.a(l2, 1);
        int n9 = cc.a(l2, 1);
        int n10 = cc.a(l2, 1);
        cc.a(l2, 1);
        int n11 = cc.a(l2, 1);
        byte by2 = (byte)cc.a(l2, 4);
        byte by3 = (byte)cc.a(l2, 2);
        int n12 = this.ag();
        int n13 = this.ag();
        int n14 = 0;
        int n15 = 0;
        int n16 = 0;
        if (n9 == 1) {
            n14 = this.ag();
            n15 = this.aj();
            n16 = this.ah();
        }
        n9 = this.ah();
        int n17 = -1;
        byte by4 = 0;
        byte by5 = 0;
        byte by6 = 0;
        byte by7 = 0;
        String string = "";
        if (n10 == 1) {
            cc cc2 = this;
            n17 = cc2.ai();
            n10 = this.ae();
            string = this.m(0, n10);
            by4 = this.ae();
            by5 = this.ae();
            by6 = this.ae();
            by7 = this.ae();
        }
        if (n11 == 1) {
            n10 = this.ae();
            ((cr)bh2).ae = this.m(0, n10);
            ((cr)bh2).ag = this.aj();
            ((cr)bh2).af = this.aj();
        }
        bh2.A = n8 == 1;
        cc cc3 = this;
        ((cr)bh2).ah = cc3.ai();
        this.a((cr)bh2, false);
        ((cr)bh2).aj = by3;
        bh2.F = n3;
        bh2.b(n3, n4, n5, n6);
        ((cr)bh2).a(n17, string, by4, by5, by6, by7);
        ((cr)bh2).b(n14, n16, n15);
        ((cr)bh2).U = (byte)n7;
        bh2.H = (byte)n12;
        bh2.I = (byte)n13;
        ((cr)bh2).ak = by2;
        ((cr)bh2).i(6, n9);
        cr.a((cr)bh2);
        if (bl2) {
            bh.y.addElement(bh2);
        } else {
            bm.aa();
            if (!bh.y.contains(bh2)) {
                bh.y.addElement(bh2);
            }
        }
        if (bh2.H != 0) {
            ((cr)bh2).w(0);
        }
        bh2.E = this.an();
        String cfr_ignored_1 = by2 + "player update data id" + n2 + " X=" + n4 + " y=" + n5 + " Name=" + bh2.E;
    }

    private void T() {
        int n2 = this.aj();
        int n3 = this.aj();
        int n4 = this.ah();
        int n5 = this.ah();
        long l2 = this.al();
        i = 0;
        int n6 = cc.a(l2, 3);
        int n7 = cc.a(l2, 8);
        cc cc2 = this;
        int n8 = cc2.ai();
        int n9 = cc.a(l2, 8);
        cc.a(l2, 1);
        int n10 = cc.a(l2, 1);
        int n11 = cc.a(l2, 1);
        int n12 = cc.a(l2, 1);
        int n13 = this.ag();
        int n14 = this.ag();
        int n15 = this.ag();
        int n16 = this.ag();
        String string = this.am();
        int n17 = n4;
        int n18 = n5;
        short s2 = 0;
        if (n11 == 1) {
            n17 = this.ah();
            n18 = this.ah();
            cc cc3 = this;
            s2 = cc3.ai();
        }
        int n19 = 0;
        if (n12 > 0) {
            n19 = this.aj();
        }
        int n20 = n2;
        ce ce2 = bh.c(n20);
        boolean bl2 = false;
        if (ce2 == null && n3 == bm.cj) {
            ce2 = new ce();
            bl2 = true;
        }
        if (ce2 == null) {
            return;
        }
        int n21 = n8;
        int n22 = n7;
        n8 = n20;
        String string2 = string;
        ce ce3 = ce2;
        ce2.c = (short)n21;
        ce3.d = ck.b(bm.z(ce3.c));
        ce3.a(string2, n8, n22);
        ce3.w();
        ce2.m = n13;
        ce2.H = (byte)n15;
        ce2.I = 0;
        ce2.q = (byte)n9;
        ce2.n = n14;
        ce2.b(n3, n4, n5, n6);
        ce2.r = n19;
        ce2.p = n16;
        if (n10 == 1) {
            ce2.A = true;
            ce2.b(4, ce2.m + 4);
        } else {
            ce2.A = false;
            if (n4 != n17 || n5 != n18) {
                ce2.b(1, 1);
                ce2.a(n17, n18, (int)s2);
            } else {
                ce2.b(0, 0);
            }
        }
        if (bl2) {
            bh.x.addElement(ce2);
        }
        String cfr_ignored_0 = n19 + "data of monster id " + n2 + " X=" + n4 + " y=" + n5 + " Name=" + string;
    }

    private void U() {
        int n2 = this.aj();
        int n3 = this.ah();
        int n4 = this.ah();
        int n5 = this.ah();
        int n6 = this.ah();
        cc cc2 = this;
        short s2 = cc2.ai();
        int n7 = this.ag();
        i = 0;
        int n8 = cc.a(n7, 1);
        n7 = cc.a(n7, 7);
        boolean bl2 = false;
        bh bh2 = bh.c(n2);
        if (bh2 == null && bh2 == null && (bh2 = cc.b(n2, true)) != null) {
            bl2 = true;
        }
        if (bh2 == null || !bh2.f()) {
            String cfr_ignored_0 = "receive updating data of monster ,but here has no data id" + n2;
            return;
        }
        v0.H = (byte)n7;
        bh2.k = n3;
        bh2.l = n4;
        if (n8 == 1) {
            bh2.b(6, 4);
        } else {
            bh2.A = false;
            if (n3 != n5 || n4 != n6) {
                bh2.b(1, 1);
                bh2.a(n5, n6, (int)s2);
            } else {
                bh2.b(0, 0);
                bh2.s();
            }
        }
        if (bl2) {
            bh.x.addElement(bh2);
        }
        String cfr_ignored_1 = "monster update data id" + n2 + " X=" + n3 + " Y=" + n4 + " Name=" + bh2.E;
    }

    private void V() {
        int n2 = this.aj();
        r r2 = bh.d(n2);
        if (r2 != null && r2.v != null) {
            byte by2 = this.ae();
            r2.v.ae = this.m(0, by2);
            r2.v.af = this.aj();
            r2.v.ag = this.aj();
        }
    }

    private void W(int n2) {
        this.c(8, (short)2150);
        this.Y(n2);
        this.ca();
    }

    private void W() {
        int n2 = ((cc)this).aj();
        int n3 = ((cc)this).aj();
        int n4 = ((cc)this).ah();
        int n5 = 0;
        int n6 = 0;
        if (((cc)this).m == 18) {
            n5 = ((cc)this).ah();
            n6 = ((cc)this).ah();
        }
        if ((this = bm.h(n2)) != null) {
            bh bh2;
            if (((bh)this).m()) {
                return;
            }
            ((bh)this).t();
            if (n5 != 0 || n6 != 0) {
                if (!((bh)this).B) {
                    ((h)this).k = n5;
                    ((h)this).l = n6;
                }
                if (((bh)this).B && bh.e(n5, n6)) {
                    String cfr_ignored_0 = "attacking move to physical place" + n5 + " " + n6;
                }
            }
            if ((bh2 = bm.h(n3)) != null) {
                ((bh)this).c(bh2.k, bh2.l);
                if (bh2.B) {
                    if (((h)this).g()) {
                        ag.a((cr)this);
                    }
                    if (bh.a((bh)this) && bm.u && bm.cb.J == 0) {
                        cr.aV = this;
                        bm.cb.a(i.k());
                    }
                }
            }
            if (((h)this).g() && !((bh)this).B) {
                ((cr)this).b(4, ((cr)this).x(n4));
            } else if (((h)this).f()) {
                ((bh)this).J = (byte)2;
                ((h)this).b(n4);
            }
            String cfr_ignored_1 = "attack id" + n2 + " Target=" + n3 + " Anim=" + n4 + " Name=" + ((bh)this).E;
        }
    }

    private void X() {
        int n2 = this.aj();
        bh bh2 = bm.h(n2);
        if (bh2 != null) {
            bh2.l();
            String cfr_ignored_0 = "dead id" + n2 + " Name=" + bh2.E;
        }
    }

    private void Y() {
        int n2 = this.aj();
        int n3 = this.ah();
        int n4 = this.ah();
        bh bh2 = bm.h(n2);
        if (bh2 != null) {
            bh2.k = n3;
            bh2.l = n4;
            if (!bh2.B) {
                bh2.H = (byte)this.ag();
                if (bh2.f()) {
                    bh2.b(0, 0);
                    bh2.w();
                    bh2.A = false;
                } else if (bh2.g()) {
                    ((cr)bh2).w(0);
                }
                String cfr_ignored_0 = "reborn id" + n2 + " Name=" + bh2.E;
                return;
            }
            bm.ae();
            bm.cb.s();
            n2 = this.aj();
            int n5 = this.aj();
            int n6 = this.ah();
            int n7 = this.ah();
            bm.cb.i(n2);
            bm.cb.r[4] = n5;
            bm.cb.j(n6);
            bm.cb.r[5] = n7;
            bm.cb.w(0);
            bm.H();
            String cfr_ignored_1 = "self reborn  HP=" + n2 + " x=" + n3 + " Y=" + n4;
            cr.L();
        }
    }

    private void Z() {
        int n2 = this.aj();
        int n3 = this.ah();
        int n4 = this.ah();
        cr cr2 = bm.cb;
        cr2.s();
        cr2.F = n2;
        cr2.k = n3;
        cr2.l = n4;
        bm.cj = n2;
        cw.c = true;
        cr.L();
        bm.u();
        int n5 = n2;
        this.c(4, (short)2116);
        this.Y(n5);
        this.ca();
        String cfr_ignored_0 = "enter map " + n2 + " X=" + n3 + " Y=" + n4;
    }

    private void aa() {
        int n2;
        if (bm.ac()) {
            bm.ae();
        }
        byte by2 = this.ae();
        bm.ag = this.aj();
        int n3 = this.aj();
        String string = this.am();
        String string2 = this.am();
        int n4 = this.ae();
        String[] stringArray = new String[n4];
        int[] nArray = new int[n4];
        for (n2 = 0; n2 < n4; ++n2) {
            stringArray[n2] = this.am();
            cc cc2 = this;
            nArray[n2] = cc2.ai();
        }
        bm.ah = null;
        n2 = this.ae();
        if (n2 > 0) {
            bm.ah = new int[n2];
            for (int i2 = 0; i2 < n2; ++i2) {
                bm.ah[i2] = this.aj();
            }
        }
        long l2 = this.al();
        n4 = this.ae();
        n n5 = null;
        if (n4 > 0) {
            q q2 = new q(this.j, this.k);
            n5 = n.a(q2);
            this.k += q2.h();
        }
        bm.a(n3, string, string2, stringArray, nArray, l2, n5, by2);
    }

    private void ab() {
        if (bm.ac()) {
            bm.ae();
        }
        int n2 = this.aj();
        bm.aj = this.am();
        byte by2 = this.ae();
        int n3 = this.ae();
        cc cc2 = this;
        bm.bI = cc2.ai();
        bm.af = by2;
        if (by2 != 3) {
            bm.H();
        }
        if (by2 == 2) {
            bm.bH = n3 == 0 ? 4 : (int)n3;
            bm.bG = 0;
        }
        bm.ak = this.am();
        n3 = this.ag();
        bm.j(n3 + 1);
        if (n3 != 0) {
            for (int i2 = 0; i2 < n3; ++i2) {
                String string = this.an();
                cc2 = this;
                cc2.ai();
                int n4 = 0;
                n4 = this.aj();
                bm.a(string, 3009, n4, i2);
            }
        }
        String string = bm.d(0, 3);
        if (bm.bI != 0) {
            string = bm.d(0, 38);
        }
        bm.a(string, 3009, 0, n3);
        cc2 = this;
        int n5 = cc2.ai();
        if (n5 > 0) {
            bm.ao = new int[n5];
            for (int i3 = 0; i3 < n5; ++i3) {
                bm.ao[i3] = this.aj();
            }
            if (by2 == 100) {
                bm.ao[n5 - 1] = 5;
            }
        } else {
            bm.ao = null;
        }
        bm.ae = n2;
        bm.ai = bm.aj;
        switch (by2) {
            default: {
                bm.m(1);
                break;
            }
            case 1: {
                bm.l(73);
                break;
            }
            case 2: {
                bm.l(74);
            }
            case 3: 
        }
        String cfr_ignored_0 = "receive script dialog call back id" + n2;
    }

    public final void a(int n2, int n3) {
        int n4 = 0;
        if (bm.ao != null && bm.ao.length > 0) {
            n4 = bm.ao.length;
        }
        this.c(14 + (n4 << 2), (short)2250);
        this.Y(n2);
        this.Y(n3);
        this.b((short)n4);
        for (n2 = 0; n2 < n4; ++n2) {
            this.Y(bm.ao[n2]);
        }
        this.ca();
    }

    public final void a(int n2, int n3, int n4, int[] nArray) {
        int n5 = 0;
        if (nArray != null) {
            n5 = nArray.length;
        }
        this.c(17 + (n5 << 2), (short)3003);
        this.Y(n2);
        this.Y(n3);
        this.Y(n4);
        this.a((byte)n5);
        for (n2 = 0; n2 < n5; ++n2) {
            this.Y(nArray[n2]);
        }
        this.ca();
    }

    private void ac() {
        bm.ae();
        bm.bE = this.ah();
        bm.ae = this.aj();
        bm.ai = this.an();
        bm.ak = this.an();
        byte by2 = this.ae();
        bm.bB = by2;
        if (by2 == 0) {
            bm.bB = 4;
        }
        int n2 = this.ae();
        bm.bF = 0;
        bm.bG = 0;
        bm.bC = 0;
        bm.bD = new String[n2];
        for (int i2 = 0; i2 < n2; ++i2) {
            bm.bD[i2] = this.an();
        }
        bm.l(77);
    }

    private void ad() {
        bm.ae();
        bm.bC = this.ae();
        int n2 = this.ag();
        bm.j(n2 + 1);
        bm.bF = 0;
        bm.bG = 0;
        if (n2 != 0) {
            for (int i2 = 0; i2 < n2; ++i2) {
                String string = this.am();
                int n3 = this.aj();
                bm.a(string, 3009, n3, i2);
            }
        }
        bm.a(bm.d(0, 3), 3009, 0, n2);
    }

    private byte ae() {
        return this.j[this.k++];
    }

    private short af() {
        return this.ai();
    }

    private int ag() {
        int n2 = this.ae();
        if (n2 < 0) {
            --n2;
            n2 ^= 0xFF;
            n2 = 0 - n2;
        }
        return n2;
    }

    private int ah() {
        int n2 = this.ai();
        if (n2 < 0) {
            --n2;
            n2 ^= 0xFFFF;
            n2 = 0 - n2;
        }
        return n2;
    }

    private short ai() {
        short s2 = (short)(this.j[this.k] & 0xFF | (this.j[this.k + 1] & 0xFF) << 8);
        this.k += 2;
        return s2;
    }

    private int aj() {
        int n2 = this.j[this.k] & 0xFF | (this.j[this.k + 1] & 0xFF) << 8 | (this.j[this.k + 2] & 0xFF) << 16 | (this.j[this.k + 3] & 0xFF) << 24;
        this.k += 4;
        return n2;
    }

    private long ak() {
        long l2 = this.al();
        long l3 = this.al();
        long l4 = l2 & 0xFFFFFFFFL | (l3 & 0xFFFFFFFFL) << 32;
        return l4;
    }

    private long al() {
        long l2 = (long)this.j[this.k] & 0xFFL | ((long)this.j[this.k + 1] & 0xFFL) << 8 | ((long)this.j[this.k + 2] & 0xFFL) << 16 | ((long)this.j[this.k + 3] & 0xFFL) << 24;
        this.k += 4;
        return l2;
    }

    private void a(byte[] byArray, int n2, int n3) {
        System.arraycopy(this.j, this.k, byArray, 0, n3);
        this.k += n3;
    }

    private void X(int n2) {
        ++this.k;
    }

    private String am() {
        int n2 = this.aj();
        if (n2 >= 1 && n2 < 4096) {
            byte[] byArray = new byte[n2];
            System.arraycopy(this.j, this.k, byArray, 0, n2);
            this.k += n2;
            return bg.a(byArray, "UTF-8");
        }
        return "";
    }

    private String an() {
        int n2 = this.ag();
        if (n2 >= 1 && n2 < 4096) {
            byte[] byArray = new byte[n2];
            System.arraycopy(this.j, this.k, byArray, 0, n2);
            this.k += n2;
            return bg.a(byArray, "UTF-8");
        }
        return "";
    }

    private String ao() {
        int n2 = this.ah();
        if (n2 >= 1) {
            byte[] byArray = new byte[n2];
            System.arraycopy(this.j, this.k, byArray, 0, n2);
            this.k += n2;
            return bg.a(byArray, "UTF-8");
        }
        return "";
    }

    private String m(int n2, int n3) {
        String string = "UTF-8";
        if (n3 <= 0) {
            return "";
        }
        byte[] byArray = new byte[n3];
        System.arraycopy(this.j, this.k, byArray, n2, n3);
        this.k += n3;
        return bg.a(byArray, string);
    }

    private String n(int n2, int n3) {
        n2 = 0;
        n3 = this.k;
        for (int i2 = 0; i2 < 33 && this.j[this.k + i2] != 0; ++i2) {
            ++n2;
        }
        String string = this.m(0, n2);
        this.k = n3 + 33;
        return string;
    }

    final void a(byte by2) {
        short s2 = this.h;
        this.h = (short)(s2 + 1);
        this.g[s2] = by2;
    }

    private void b(short s2) {
        short s3 = this.h;
        this.h = (short)(s3 + 1);
        this.g[s3] = (byte)s2;
        short s4 = this.h;
        this.h = (short)(s4 + 1);
        this.g[s4] = (byte)(s2 >> 8);
    }

    final void a(int n2) {
        short s2 = this.h;
        this.h = (short)(s2 + 1);
        this.g[s2] = (byte)n2;
        short s3 = this.h;
        this.h = (short)(s3 + 1);
        this.g[s3] = (byte)(n2 >> 8);
    }

    private void Y(int n2) {
        short s2 = this.h;
        this.h = (short)(s2 + 1);
        this.g[s2] = (byte)n2;
        short s3 = this.h;
        this.h = (short)(s3 + 1);
        this.g[s3] = (byte)(n2 >>> 8);
        short s4 = this.h;
        this.h = (short)(s4 + 1);
        this.g[s4] = (byte)(n2 >>> 16);
        short s5 = this.h;
        this.h = (short)(s5 + 1);
        this.g[s5] = (byte)(n2 >>> 24);
    }

    private void a(byte[] byArray) {
        if (byArray == null) {
            return;
        }
        for (int i2 = 0; i2 < byArray.length; ++i2) {
            this.g[this.h + i2] = byArray[i2];
        }
        this.h = (short)(this.h + byArray.length);
    }

    private void f(String object) {
        byte[] byArray = bg.a((String)object, "UTF-8");
        object = byArray;
        if (byArray == null) {
            this.Y(0);
            return;
        }
        this.a((byte)((Object)object).length);
        this.a((byte[])object);
    }

    private void c(int n2, short s2) {
        this.h = 0;
        this.b((short)n2);
        this.b(s2);
    }

    private void c(short s2) {
        this.c(0, s2);
    }

    private boolean ap() {
        while (this.f != null) {
            this.j = this.f.g();
            if (this.j == null) {
                return false;
            }
            this.k = 0;
            this.m = (short)ct.a(this.j);
            this.l = (short)ct.b(this.j);
            ak.d += (long)this.m;
            this.k = 4;
            try {
                boolean bl2 = false;
                Object object = this;
                boolean bl3 = true;
                switch (((cc)object).l) {
                    case 529: {
                        bm.at();
                        break;
                    }
                    case 2706: {
                        int n2 = ((cc)object).ae();
                        ag.e = new byte[n2];
                        for (int i2 = 0; i2 < n2; ++i2) {
                            ag.e[i2] = ((cc)object).ae();
                        }
                        af.a(true, 4);
                        break;
                    }
                    case 2702: {
                        byte by2 = ((cc)object).ae();
                        int n3 = ((cc)object).ae();
                        for (int i3 = 0; i3 < n3; ++i3) {
                            short s2 = ((cc)object).af();
                            int n4 = s2 >> 8 & 0xFF;
                            int n5 = s2 & 0xFF;
                            ag.a(by2, n4, n5);
                        }
                        af.a(true, 3);
                        ag.a();
                        break;
                    }
                    case 402: {
                        String string = ((cc)object).an();
                        int n6 = ((cc)object).ah();
                        int n7 = ((cc)object).aj();
                        am.p = new ca();
                        new ca().b = string;
                        am.p.c = n6;
                        am.p.g = n7;
                        bm.s = true;
                        ag.a(true);
                        break;
                    }
                    case 403: {
                        ag.a(true);
                        break;
                    }
                    case 401: 
                    case 517: {
                        bm.di = true;
                        int n8 = 0;
                        if (((cc)object).l == 517) {
                            bm.r = false;
                            n8 = 3;
                            bm.A(994);
                            byte by3 = super.ae();
                            String string = super.n(0, 33);
                            byte by4 = super.ae();
                            super.X(1);
                            int n9 = super.aj();
                            int n10 = super.aj();
                            int n11 = super.aj();
                            int n12 = super.aj();
                            int n13 = super.aj();
                            if (bm.cb == null) {
                                cr cr2 = new cr();
                                new cr().ai = by4;
                                cr2.B = true;
                                cr2.a(string, n9, (byte)n10, (byte)n12);
                                cr2.a(string, n9, 0);
                                super.a(cr2, true);
                                cr2.v = (byte)n12;
                                cr2.U = (byte)n11;
                                cr2.t = (byte)n13;
                                cr2.s = by3;
                                bm.cb = cr2;
                                bh.y.addElement(bm.cb);
                            }
                        } else {
                            n8 = ((cc)object).ae();
                        }
                        if (n8 == 3) {
                            bm.ae();
                            cn.k();
                            cn.s();
                            cn.x();
                            cr.C();
                            cr.am = -1;
                            bm.cb.ab = 0;
                            bm.cb.aa = 0;
                            cr.D();
                            bm.cb.O();
                            bm.A(995);
                            if (bm.aq == -1) {
                                bm.az = true;
                                for (int i4 = 0; i4 < 4; ++i4) {
                                    bm.aw[i4] = false;
                                }
                                bm.ax = false;
                            }
                            if (!bm.au()) break;
                            break;
                        }
                        String cfr_ignored_0 = "ReConnect fail " + n8;
                        bm.at();
                        break;
                    }
                    case 1: {
                        cc cc2 = object;
                        ((cc)object).o = cc2.ae();
                        String cfr_ignored_1 = bm.A(1075) + cc2.o;
                        if (1 == cc2.o) {
                            if (bc.a != null) {
                                cs.b = bc.a[0];
                                cs.c = bc.a[2];
                                af.a(true, 1);
                                bm.a(bm.d(0, 6), 2);
                                cs.c();
                            }
                        } else if (3 == cc2.o) {
                            bm.d(bm.A(1076));
                        } else if (9 == cc2.o) {
                            bm.d(bm.A(1077));
                        } else if (11 == cc2.o) {
                            bm.d(bm.A(1078));
                        } else if (12 == cc2.o) {
                            bm.d(bm.A(1079));
                        } else if (13 == cc2.o) {
                            bm.d(bm.A(1080));
                        } else if (14 == cc2.o) {
                            bm.d(bm.A(1081));
                        } else {
                            bm.d(bm.A(1082));
                        }
                        bm.ae();
                        break;
                    }
                    case 260: {
                        cc cc3 = object;
                        ((cc)object).o = cc3.ae();
                        if (2 == cc3.o) {
                            bm.d(bm.A(1072));
                        } else if (1 == cc3.o) {
                            cs.b = bc.a[0];
                            cs.c = bc.a[2];
                            af.a(true, 1);
                            bm.a(bm.A(1073), 3);
                        } else {
                            bm.d(bm.A(1074));
                        }
                        bm.ae();
                        break;
                    }
                    case 3: {
                        cs.g = true;
                        bm.ae();
                        break;
                    }
                    case 257: {
                        cc cc4 = object;
                        ((cc)object).o = cc4.ae();
                        String cfr_ignored_2 = bm.A(1067) + cc4.o;
                        cs.b(2);
                        if (2 == cc4.o) {
                            bm.d(bm.A(1068));
                        } else if (1 == cc4.o) {
                            bm.d(bm.A(1069));
                        } else if (6 == cc4.o) {
                            bm.d(bm.A(1070));
                        } else {
                            bm.d(bm.A(1071));
                        }
                        bm.ae();
                        break;
                    }
                    case 261: {
                        cc cc5 = object;
                        bm.O = cc5.ah();
                        String cfr_ignored_3 = bm.A(1066) + bm.O;
                        break;
                    }
                    case 258: {
                        ((cc)object).bm();
                        if (!cs.f) break;
                        af.a(true, 1);
                        break;
                    }
                    case 513: {
                        cc cc6 = object;
                        ((cc)object).o = cc6.ae();
                        if (cc6.o == 1) {
                            am.a = false;
                            if (bm.r) {
                                bm.E(7);
                                bm.bn.c(0);
                                break;
                            }
                            bm.aa();
                            am.a(4);
                        } else if (cc6.o == 8) {
                            bm.d(bm.A(309));
                            bm.bn = null;
                        } else if (cc6.o == 10) {
                            bm.d(bm.A(1049));
                            bm.bn = null;
                        } else if (cc6.o == 19) {
                            bm.d(bm.A(1050));
                            bm.bn = null;
                        } else {
                            bm.a(bm.A(303), 6);
                            bm.bn = null;
                        }
                        if (bm.r) {
                            am.c();
                        }
                        bm.ae();
                        break;
                    }
                    case 515: {
                        cc cc7 = object;
                        ((cc)object).o = cc7.ae();
                        bm.ae();
                        if (cc7.o != 1) break;
                        am.a(4);
                        am.a = false;
                        break;
                    }
                    case 518: {
                        ((cc)object).bl();
                        break;
                    }
                    case 514: {
                        ((cc)object).bk();
                        break;
                    }
                    case 519: {
                        ((cc)object).bj();
                        break;
                    }
                    case 523: {
                        cc cc8 = object;
                        ((cc)object).o = cc8.ae();
                        if (2 != cc8.o) break;
                        am.a(11);
                        break;
                    }
                    case 640: {
                        cc cc9 = object;
                        int n14 = cc9.aj();
                        if (bm.cb == null) break;
                        cr.aU = n14;
                        if (cr.I()) {
                            bm.d(bm.A(1044));
                            break;
                        }
                        if (!cr.J()) break;
                        bm.d(bm.A(1045));
                        break;
                    }
                    case 521: {
                        ((cc)object).bh();
                        break;
                    }
                    case 528: {
                        ((cc)object).bi();
                        break;
                    }
                    case 531: {
                        cc cc10 = object;
                        int n15 = cc10.aj();
                        int n16 = 0;
                        byte[] byArray = null;
                        switch (n15) {
                            case 0: {
                                break;
                            }
                            case 1: {
                                n16 = cc10.ah();
                                bm.bQ = new byte[n16];
                                cc10.a(bm.bQ, 0, n16);
                                break;
                            }
                            case 2: {
                                n16 = cc10.ah();
                                byArray = new byte[n16];
                                cc10.a(byArray, 0, n16);
                                bm.bR = bg.a(byArray, 0);
                                bm.bS = bg.a(byArray, 2);
                                bm.bT = bm.bR * 24;
                                bm.bU = bm.bS * 24;
                                bm.bO = bm.a(byArray, byArray.length - 4, 4);
                                break;
                            }
                            case 3: {
                                n16 = cc10.ah();
                                bm.bP = new byte[n16];
                                cc10.a(bm.bP, 0, n16);
                                break;
                            }
                            case 4: {
                                n16 = cc10.ah();
                                bm.bZ = new byte[n16];
                                cc10.a(bm.bZ, 0, n16);
                                bm.bo.b = 4;
                            }
                        }
                    }
                    case -1: {
                        cc cc11 = object;
                        int n17 = cc11.aj();
                        int n18 = (int)bm.m();
                        int n19 = n18 - n17;
                        if (n19 < 0) {
                            n19 = 0;
                        }
                        bm.dr = n19;
                        break;
                    }
                    case 1403: {
                        break;
                    }
                    case 607: {
                        ((cc)object).bf();
                        break;
                    }
                    case 618: {
                        ((cc)object).bg();
                        break;
                    }
                    case 3002: {
                        ((cc)object).aa();
                        break;
                    }
                    case 3004: {
                        ((cc)object).aV();
                        break;
                    }
                    case 2200: {
                        ((cc)object).ab();
                        break;
                    }
                    case 2202: {
                        ((cc)object).ac();
                        break;
                    }
                    case 2203: {
                        ((cc)object).ad();
                        break;
                    }
                    case 2152: {
                        bm.ae = ((cc)object).aj();
                        byte by5 = ((cc)object).ae();
                        String string = "";
                        String string2 = "";
                        if (((cc)object).k < ((cc)object).m) {
                            string = super.an();
                            string2 = super.an();
                        }
                        bc.a(by5, string, string2);
                        bm.ae();
                        break;
                    }
                    case 2154: {
                        bm.ae = ((cc)object).aj();
                        String string = ((cc)object).an();
                        int n20 = ((cc)object).ag();
                        if (n20 <= 0) break;
                        String[] stringArray = new String[n20];
                        for (int i5 = 0; i5 < n20; ++i5) {
                            String string3;
                            stringArray[i5] = string3 = super.an();
                        }
                        if (stringArray.length <= 0) break;
                        bm.ae();
                        bc.a((byte)51, 0, string, "", stringArray);
                        break;
                    }
                    case 608: {
                        ((cc)object).be();
                        break;
                    }
                    case 609: {
                        break;
                    }
                    case 632: {
                        cc cc12 = object;
                        short s3 = cc12.af();
                        t t2 = t.a(s3);
                        if (t2 == null) {
                            String cfr_ignored_4 = "CMD_SC_PERMISS_DATA_UPDATE mis==null id=" + s3;
                            break;
                        }
                        byte by6 = cc12.ae();
                        if (by6 == 0) {
                            int n21 = cc12.ah();
                            int n22 = cc12.ag();
                            t2.a((byte)0, n21, n22, true);
                            t.a(t2);
                            bm.be = true;
                            bm.y = true;
                            break;
                        }
                        if (by6 != 1) break;
                        t2.u = cc12.ah();
                        t2.d();
                        break;
                    }
                    case 615: 
                    case 616: {
                        ((cc)object).bd();
                        break;
                    }
                    case 613: {
                        cc cc13 = object;
                        bm.dm = true;
                        bm.di = true;
                        short s4 = cc13.af();
                        t.b(s4);
                        int n23 = t.a.size();
                        if (f.a < n23) break;
                        f.a = n23 - 1;
                        break;
                    }
                    case 610: {
                        break;
                    }
                    case 611: {
                        ((cc)object).bc();
                        break;
                    }
                    case 619: {
                        cc cc14 = object;
                        byte by7 = cc14.ae();
                        if (by7 == 0) {
                            bm.cb.Y = cc14.aj();
                            cr.aO = cc14.aj();
                        } else {
                            bm.cb.Y = cc14.aj();
                            cr.aP = cc14.aj();
                            cr.aO = cc14.aj();
                            byte by8 = cc14.ae();
                            if (by8 > bm.cb.U) {
                                bm.cb.v();
                            }
                            bm.cb.U = by8;
                        }
                        bm.aX = true;
                        break;
                    }
                    case 2800: {
                        cc cc15 = object;
                        cc15.aj();
                        bm.cb.W = cc15.an();
                        bm.cb.X = cc15.an();
                        bm.ae();
                        break;
                    }
                    case 622: {
                        cc cc16 = object;
                        bm.cb.U = cc16.ae();
                        cr.aO = cc16.aj();
                        bm.cb.i(cc16.aj());
                        bm.cb.j(cc16.ah());
                        bm.dn = cc16.ag() != 0;
                        cr.at = cc16.aj();
                        cr.au = cc16.aj();
                        cr.aw = cc16.aj();
                        cr.ax = cc16.aj();
                        bm.cb.Z = cc16.aj();
                        cc16.ag();
                        cr.aH = cc16.ag();
                        cc16.ag();
                        bm.cb.A = cc16.ag() == 0;
                        bm.cV = (byte)cc16.ag();
                        cr.aK = cc16.af();
                        cr.aL = cc16.af();
                        cr.aM = cc16.af();
                        cr.aN = cc16.af();
                        cr.aQ = cc16.af();
                        cr.aR = cc16.af();
                        cr.aP = cc16.aj();
                        bm.cb.ad = cc16.aj();
                        cc16.aj();
                        bm.cb.W = cc16.an();
                        bm.cb.X = cc16.an();
                        t.a.removeAllElements();
                        bm.dm = true;
                        bm.di = true;
                        break;
                    }
                    case 623: {
                        cc cc17 = object;
                        bm.cb.i(0, cc17.ah());
                        bm.cb.i(1, cc17.ah());
                        bm.cb.i(2, cc17.ah());
                        bm.cb.i(3, cc17.ah());
                        bm.cb.i(4, cc17.aj());
                        bm.cb.i(5, cc17.ah());
                        bm.cb.i(6, cc17.ah());
                        bm.cb.i(7, cc17.ah());
                        bm.cb.i(8, cc17.ah());
                        bm.cb.i(9, cc17.ah());
                        bm.cb.i(10, cc17.ah());
                        bm.cb.i(11, cc17.ah());
                        bm.cb.i(12, cc17.ah());
                        bm.cb.i(13, cc17.ah());
                        bm.cb.i(14, cc17.ah());
                        break;
                    }
                    case 624: {
                        cc cc18 = object;
                        cr cr3 = bm.cb;
                        int n24 = cc18.ag();
                        int n25 = cc18.aj();
                        switch (n24) {
                            case 3: {
                                if (n25 > cr3.U) {
                                    cr3.v();
                                }
                                cr3.U = (byte)n25;
                                String cfr_ignored_5 = "CMD_SC_BASEPROPERTY_UPDATE Level=" + n25;
                                break;
                            }
                            case 4: {
                                break;
                            }
                            case 5: {
                                if (n25 > 0 && bm.cb.J == 6) {
                                    bm.cb.w(0);
                                }
                                bm.cb.i(n25);
                                break;
                            }
                            case 6: {
                                bm.cb.j(n25);
                                break;
                            }
                            case 12: {
                                bm.dn = n25 != 0;
                                break;
                            }
                            case 13: {
                                cr.a(n25, false);
                                break;
                            }
                            case 80: {
                                cr.a(n25, true);
                                break;
                            }
                            case 15: {
                                cr3.ab = n25;
                                break;
                            }
                            case 16: {
                                cr.am = n25;
                                break;
                            }
                            case 17: {
                                cr3.Z = n25;
                                break;
                            }
                            case 18: {
                                bm.aA.f(n25);
                                break;
                            }
                            case 19: {
                                break;
                            }
                            case 20: {
                                cr.aH = n25;
                                break;
                            }
                            case 21: {
                                break;
                            }
                            case 8: {
                                cr3.A = n25 == 0;
                                break;
                            }
                            case 37: {
                                bm.cV = (byte)n25;
                                break;
                            }
                            case 46: {
                                cr.aK = n25;
                                break;
                            }
                            case 48: {
                                cr.aL = n25;
                                break;
                            }
                            case 50: {
                                cr.aM = n25;
                                break;
                            }
                            case 51: {
                                cr.aN = n25;
                                break;
                            }
                            case 59: {
                                cr.aw = n25;
                                break;
                            }
                            case 66: {
                                cr.ax = n25;
                                break;
                            }
                            case 129: {
                                cr.aQ = n25;
                                break;
                            }
                            case 130: {
                                cr.aR = n25;
                                break;
                            }
                            case 153: {
                                cr.aS = n25;
                                break;
                            }
                            case 145: {
                                if (bm.cb == null) break;
                                bm.cb.V = n25;
                            }
                        }
                        bm.X = 3;
                        String cfr_ignored_6 = "CMD_SC_BASEPROPERTY_UPDATE " + n24 + "=" + n25;
                        break;
                    }
                    case 627: {
                        ((cc)object).bb();
                        break;
                    }
                    case 628: {
                        cc cc19 = object;
                        int n26 = cc19.ag();
                        int n27 = cc19.aj();
                        int n28 = cc19.aj();
                        object = bm.h(n27);
                        if (object == null) break;
                        switch (n26) {
                            case 8: {
                                ((bh)object).A = n28 == 0;
                            }
                        }
                        break;
                    }
                    case 629: {
                        break;
                    }
                    case 630: {
                        cc cc20 = object;
                        cc20.ag();
                        cc20.aj();
                        break;
                    }
                    case 631: {
                        cc cc21 = object;
                        cc21.ae();
                        cc21.aj();
                        cc21.aj();
                        break;
                    }
                    case 621: {
                        cc cc22 = object;
                        int n29 = cc22.aj();
                        boolean bl4 = cc22.ae() == 1;
                        cr.a(n29, bl4);
                        cn.k();
                        break;
                    }
                    case 633: {
                        ((cc)object).ba();
                        break;
                    }
                    case 606: {
                        break;
                    }
                    case 634: {
                        ((cc)object).cC();
                        break;
                    }
                    case 605: {
                        cc cc23 = object;
                        int n30 = cc23.aj();
                        short s5 = cc23.af();
                        short s6 = cc23.af();
                        object = bh.f(n30);
                        if (object == null) break;
                        if (bh.e(s5, s6)) {
                            String cfr_ignored_7 = ((bh)object).E + bm.A(1028) + s5 + " " + s6;
                        } else {
                            ((h)object).k = s5;
                            ((h)object).l = s6;
                        }
                        if (object != bm.cb) break;
                        bm.cb.s();
                        cr.L();
                        break;
                    }
                    case 604: {
                        Object object2 = object;
                        int n31 = ((cc)object2).aj();
                        short s7 = ((cc)object2).af();
                        short s8 = ((cc)object2).af();
                        short s9 = ((cc)object2).af();
                        short s10 = ((cc)object2).af();
                        bh bh2 = bh.f(n31);
                        if (bh2 == null) break;
                        if (bh2.f()) {
                            short s11 = ((cc)object2).af();
                            object2 = (ce)bh2;
                            ((ce)bh2).k = s7;
                            ((h)object2).l = s8;
                            ((bh)object2).c(s9, s10);
                            if (s7 != s9 || s8 != s10) {
                                ((bh)object2).b(1, 1);
                                ((bh)object2).a(s9, (int)s10, (int)s11);
                                break;
                            }
                            ((bh)object2).s();
                            break;
                        }
                        if (bh2.B || !bh2.g()) break;
                        cr cr4 = (cr)bh2;
                        cr4.s();
                        cr4.k = s7;
                        cr4.l = s8;
                        short s12 = cr4.d(s7, s8, s9, s10);
                        cr4.a(s9, (int)s10, (int)s12);
                        cr4.w(2);
                        break;
                    }
                    case 603: {
                        break;
                    }
                    case 636: {
                        break;
                    }
                    case 2501: {
                        n n32;
                        q q2 = new q(((cc)object).j, ((cc)object).k);
                        cn.w = n32 = n.a(q2);
                        if (n32.f == null) {
                            cn.aq = n32.d;
                            n32.f = "";
                            bm.bn.S(n32.d);
                        }
                        cn.a(false, null, null);
                        bm.ae();
                        bt.d = (byte)3;
                        break;
                    }
                    case 2403: 
                    case 2404: 
                    case 2414: {
                        if (((cc)object).l != 2414) {
                            bd.a = false;
                        }
                        bd.f = new Vector();
                        byte by9 = ((cc)object).ae();
                        String string = null;
                        int n33 = 0;
                        for (byte by10 = 0; by10 < by9; by10 = (byte)(by10 + 1)) {
                            n33 = super.aj();
                            string = "" + n33;
                            string = string + "^" + super.aj();
                            String string4 = super.an();
                            String string5 = super.an();
                            byte by11 = super.ae();
                            if (string4.indexOf("^") != -1 || string5.indexOf("^") != -1) continue;
                            string = string + "^" + string4;
                            string = string + "^" + string5;
                            string = string + "^" + by11;
                            bd.f.addElement(string);
                        }
                        bg.a(bd.f, 0, bd.f.size() - 1, 4);
                        if (((cc)object).l == 2404) {
                            bm.l(64);
                        } else if (((cc)object).l == 2414) {
                            bm.l(79);
                        }
                        bm.ae();
                        break;
                    }
                    case 2411: {
                        bd.f.removeAllElements();
                        bm.d(bm.A(996));
                        bm.ae();
                        break;
                    }
                    case 2409: {
                        if (bm.n(65)) {
                            bm.v();
                        }
                        bd.f.removeElementAt(bd.b());
                        bd.c();
                        bm.d(bm.A(997));
                        bm.ae();
                        break;
                    }
                    case 2408: {
                        switch (((cc)object).ae()) {
                            case 1: {
                                String[] stringArray = bg.a((String)bd.f.elementAt(bd.q), "^", 5);
                                Byte.parseByte(stringArray[4]);
                                boolean bl5 = false;
                                stringArray[4] = "0";
                                bd.f.setElementAt(bg.a(stringArray), bd.q);
                                bd.l = 0;
                                bd.p = 0;
                                bd.o = null;
                                bd.n = 0;
                                bm.d(bm.A(998));
                                break;
                            }
                            case 2: {
                                bm.d(bm.A(999));
                            }
                        }
                        bm.ae();
                        break;
                    }
                    case 2405: {
                        bd.h = ((cc)object).aj();
                        bd.i = ((cc)object).aj();
                        bd.j = ((cc)object).an();
                        String string = ((cc)object).ao();
                        bd.k = "^x" + string + "^t";
                        bd.l = super.aj();
                        bd.p = super.aj();
                        bd.m = super.ak();
                        bd.n = super.ae();
                        if (bd.n > 0) {
                            q q3 = new q(((cc)object).j, ((cc)object).k);
                            bd.o = n.a(q3);
                        }
                        int n34 = bd.h;
                        cc cc24 = bm.bn;
                        cc24.c((short)2412);
                        cc24.Y(n34);
                        cc24.ca();
                        bm.m(65);
                        bm.ae();
                        break;
                    }
                    case 2402: {
                        bd.a = true;
                        al.a(7, 0, 0);
                        bm.A(1000);
                        break;
                    }
                    case 2401: {
                        String string = "";
                        switch (((cc)object).ae()) {
                            case 0: {
                                string = bm.A(1001);
                                bd.a();
                                bt.i();
                                bm.v();
                                break;
                            }
                            case 1: {
                                string = bm.A(1002);
                                break;
                            }
                            case 2: {
                                string = bm.A(1003);
                                break;
                            }
                            case 3: {
                                string = bm.A(1004);
                            }
                        }
                        bm.d(string);
                        bm.ae();
                        break;
                    }
                    case 769: {
                        cc cc25 = object;
                        int n35 = cc25.ae();
                        int n36 = cc25.aj();
                        String string = cc25.an();
                        object = cc25.ao();
                        long l2 = cc25.ak();
                        bt.a(n36, n35, string, (String)object, l2);
                        bt.a(bt.e, false);
                        if (n35 == 11) {
                            n35 = 7;
                        }
                        if (n35 == bt.e) {
                            bt.c = (byte)(bt.c + 1);
                            if (bt.y == null) {
                                bt.c = 0;
                            } else if (bt.c >= bt.y.length) {
                                bt.c = (byte)(bt.y.length - 1);
                            }
                        }
                        bt.a();
                        break;
                    }
                    case 806: {
                        cc cc26 = object;
                        int n37 = cc26.ag();
                        if (n37 == 5 || n37 == 6 || n37 == 13) {
                            cr.C();
                            if (bm.cb != null) {
                                cr.am = -1;
                            }
                        }
                        switch (n37) {
                            case 1: {
                                bm.a(bm.A(1016), true);
                                break;
                            }
                            case 2: {
                                bm.a(bm.A(882), true);
                                break;
                            }
                            case 3: {
                                bm.a(bm.A(1017), true);
                                break;
                            }
                            case 4: {
                                bm.a(bm.A(1018), true);
                                break;
                            }
                            case 5: {
                                bm.a(bm.A(1019), true);
                                break;
                            }
                            case 6: {
                                bm.a(bm.A(1020), true);
                                break;
                            }
                            case 7: {
                                bm.a(bm.A(1021), 0, 1500);
                                bm.H();
                                bm.O();
                                break;
                            }
                            case 8: {
                                bm.c(bm.A(879) + bm.B(264));
                                break;
                            }
                            case 9: {
                                bm.c(bm.A(1022));
                                break;
                            }
                            case 10: {
                                bm.c(bm.A(881));
                                break;
                            }
                            case 11: {
                                bm.c(bm.A(1023));
                                break;
                            }
                            case 12: {
                                bm.c(bm.A(1024));
                                break;
                            }
                            case 13: {
                                bm.d(bm.A(1025));
                            }
                        }
                        String cfr_ignored_8 = "CMD_CS_TEAM_FAIL:" + n37;
                        break;
                    }
                    case 807: {
                        Object object3 = object;
                        int n38 = ((cc)object3).aj();
                        int n39 = ((cc)object3).aj();
                        int n40 = ((cc)object3).ag();
                        int n41 = n40 & 0x7F;
                        int n42 = n40 >> 7;
                        int n43 = ((cc)object3).ag();
                        int n44 = ((cc)object3).ag();
                        n40 = ((cc)object3).ag();
                        int n45 = ((cc)object3).ag();
                        object3 = ((cc)object3).an();
                        bm.a(((String)object3).trim() + bm.d(0, 151), true);
                        cr.am = n38;
                        cr cr5 = bh.a(bm.bq, n39);
                        if (cr5 == null) {
                            cr5 = new cr();
                            bm.bq.addElement(cr5);
                        }
                        cr5.ai = (byte)n42;
                        cr5.a((String)object3, n39, (byte)n41, (byte)n43);
                        cr5.r[4] = 100;
                        cr5.r[5] = 100;
                        cr5.H = (byte)n40;
                        cr5.I = (byte)n45;
                        cr5.U = (byte)n44;
                        cr5.ac = 1;
                        cr5.t(2);
                        cr.u(n39);
                        if (n39 == cr.an) {
                            cr5.aj = (byte)2;
                        }
                        bm.aa();
                        String cfr_ignored_9 = bm.A(1012) + (String)object3;
                        break;
                    }
                    case 804: {
                        ((cc)object).aZ();
                        break;
                    }
                    case 805: {
                        cr cr6;
                        int n46;
                        cc cc27 = object;
                        cr.an = n46 = cc27.aj();
                        String cfr_ignored_10 = bm.A(1014) + n46;
                        cr cr7 = bh.e(n46);
                        if (cr7 != null) {
                            cr7.aj = (byte)2;
                        }
                        if ((cr6 = bh.a(bm.bq, n46)) == null) break;
                        cr6.aj = (byte)2;
                        String cfr_ignored_11 = bm.A(1015) + cr6.E;
                        break;
                    }
                    case 801: {
                        ((cc)object).aY();
                        break;
                    }
                    case 803: {
                        cc cc28 = object;
                        cc28.aj();
                        cc28.aj();
                        cc28.aj();
                        break;
                    }
                    case 812: {
                        cc cc29 = object;
                        cc29.aj();
                        cc29.ae();
                        break;
                    }
                    case 814: {
                        ((cc)object).aX();
                        break;
                    }
                    case 774: {
                        ((cc)object).aW();
                        break;
                    }
                    case 1093: {
                        cc cc30 = object;
                        bm.cb.Z = cc30.aj();
                        break;
                    }
                    case 1096: {
                        bm.d(bm.A(1048));
                        break;
                    }
                    case 2251: {
                        ((cc)object).bx();
                        break;
                    }
                    case 1073: {
                        ((cc)object).bv();
                        break;
                    }
                    case 1075: {
                        ((cc)object).bw();
                        break;
                    }
                    case 2252: {
                        ((cc)object).by();
                        break;
                    }
                    case 1081: {
                        ((cc)object).bz();
                        break;
                    }
                    case 1082: {
                        ((cc)object).bA();
                        break;
                    }
                    case 1083: {
                        ((cc)object).bB();
                        break;
                    }
                    case 1084: {
                        ((cc)object).bC();
                        break;
                    }
                    case 1085: {
                        ((cc)object).bD();
                        break;
                    }
                    case 1089: {
                        ((cc)object).bE();
                        break;
                    }
                    case 1079: {
                        ((cc)object).bF();
                        break;
                    }
                    case 2254: {
                        ((cc)object).bG();
                        break;
                    }
                    case 2284: {
                        ((cc)object).bH();
                        break;
                    }
                    case 2285: {
                        ((cc)object).bJ();
                        break;
                    }
                    case 2286: {
                        ((cc)object).bI();
                        break;
                    }
                    case 2306: {
                        ((cc)object).bK();
                        break;
                    }
                    case 2302: {
                        ((cc)object).bM();
                        break;
                    }
                    case 2304: {
                        ((cc)object).bL();
                        break;
                    }
                    case 2307: {
                        cc.bN();
                        break;
                    }
                    case 2305: {
                        ((cc)object).bO();
                        break;
                    }
                    case 2308: {
                        ((cc)object).bP();
                        break;
                    }
                    case 2281: {
                        int n47 = ((cc)object).aj();
                        int n48 = ((cc)object).aj();
                        int n49 = ((cc)object).ae();
                        bm.dM = n47;
                        bm.dN = n48;
                        bm.dI = new int[n49];
                        bm.dH = new String[n49];
                        for (int i6 = 0; i6 < n49; ++i6) {
                            bm.dI[i6] = super.aj();
                            bm.dH[i6] = super.am();
                        }
                        bm.ae();
                        bm.ar();
                        break;
                    }
                    case 2283: {
                        int n50;
                        int n51;
                        bm.dK = ((cc)object).ag();
                        bm.dL = ((cc)object).am();
                        int n52 = ((cc)object).ae();
                        bm.dJ = new String[n52];
                        for (n51 = 0; n51 < n52; ++n51) {
                            bm.dJ[n51] = super.am();
                        }
                        n51 = super.ae();
                        bm.dO = n51;
                        if (bm.dK == 255) {
                            bm.dT = new long[n51];
                            bm.dU = new short[n51];
                            bm.dV = new int[n51];
                            for (n50 = 0; n50 < n51; ++n50) {
                                bm.dT[n50] = super.al();
                                bm.dU[n50] = super.af();
                                bm.dV[n50] = super.aj();
                            }
                        } else {
                            bm.dP = new String[n51];
                            bm.dQ = new int[n51];
                            bm.dR = new int[n51];
                            bm.dS = new int[n51];
                            for (n50 = 0; n50 < n51; ++n50) {
                                bm.dQ[n50] = super.aj();
                                bm.dP[n50] = super.am();
                                bm.dR[n50] = super.aj();
                                bm.dS[n50] = n52 == 4 ? super.aj() : -1;
                            }
                        }
                        bm.aw();
                        break;
                    }
                    case 1501: 
                    case 2502: 
                    case 2507: 
                    case 2508: {
                        bm.ae();
                        String string = ((cc)object).am();
                        String string6 = bg.c(string, "$");
                        if (((cc)object).l == 1501 || ((cc)object).l == 2508) {
                            bm.a(string6, true);
                            if (((cc)object).l != 2508) break;
                            bt.a(0, 7, "", string6, 0L);
                            break;
                        }
                        bm.d(string6);
                        if (((cc)object).l != 2507) break;
                        bt.a(0, 7, "", string6, 0L);
                        break;
                    }
                    case 2509: {
                        ((cc)object).bQ();
                        break;
                    }
                    case 2503: {
                        int n53;
                        bu.a();
                        bu.b = ((cc)object).an();
                        bu.c = ((cc)object).an();
                        bu.e = ((cc)object).aj();
                        int n54 = ((cc)object).aj();
                        int n55 = super.ae();
                        String[] stringArray = new String[n55];
                        int[] nArray = new int[n55];
                        for (n53 = 0; n53 < n55; ++n53) {
                            stringArray[n53] = super.an();
                            nArray[n53] = super.ae();
                        }
                        n53 = super.ae();
                        if (n53 > 0) {
                            bu.g = new int[n53 + 1];
                            bu.h = new String[n53 + 1];
                            for (int i7 = 0; i7 < n53; ++i7) {
                                bu.h[i7] = super.an();
                                bu.g[i7] = super.aj();
                            }
                            bu.h[n53] = bm.A(718);
                            bu.g[n53] = 0;
                        }
                        int n56 = super.ae();
                        bu.a(stringArray, nArray, n54);
                        for (int i8 = 0; i8 < n56; ++i8) {
                            int n57 = super.aj();
                            String[] stringArray2 = new String[n55];
                            for (int i9 = 0; i9 < n55; ++i9) {
                                stringArray2[i9] = super.an();
                            }
                            bu.a(stringArray2, n57);
                        }
                        bu.d = super.aj();
                        if (n53 == 0) {
                            String cfr_ignored_12 = "LISTBOX_DIALOG MENU IS NULL" + bu.d;
                        }
                        bm.l(56);
                        bu.d();
                        bm.ae();
                        break;
                    }
                    case 2505: {
                        int n58;
                        i i10;
                        if (bm.n(56)) {
                            int n59 = ((cc)object).aj();
                            bw bw2 = bu.a(n59);
                            if (bw2 == null) break;
                            bw2.c = ((cc)object).ao();
                            break;
                        }
                        if (!bm.n(59) || (i10 = i.a(n58 = ((cc)object).aj())) == null) break;
                        i10.g = super.ao();
                        break;
                    }
                    case 2506: {
                        boolean bl6;
                        if (!bm.n(56)) break;
                        int n60 = ((cc)object).aj();
                        bw bw3 = bu.a(n60);
                        if (bw3 != null) {
                            for (int i11 = 0; i11 < bu.a; ++i11) {
                                bw3.b[i11] = super.an();
                            }
                        }
                        if (!(bl6 = ((cc)object).ae() == 1)) break;
                        bu.c = super.an();
                        break;
                    }
                    case 2351: {
                        int n61;
                        int n62 = ((cc)object).ae();
                        cn cn2 = cn.a((byte)2, false, 0);
                        cn cn3 = new cn(0, cn2.k, 7);
                        for (n61 = 0; n61 < n62; ++n61) {
                            byte by12 = super.ae();
                            byte by13 = super.ae();
                            n n63 = cn2.b(by12);
                            cn2.b(by12).c = (byte)n61;
                            n63.p = by13;
                            cn3.a(n63, n61);
                        }
                        cn2.e();
                        cn.g = true;
                        for (n61 = 0; n61 < n62; ++n61) {
                            n n64 = cn3.b(n61);
                            cn2.a(n64, n61);
                        }
                        cn.k();
                        cn.g = false;
                        bm.a(bm.A(1005), true);
                        break;
                    }
                    case 2350: {
                        int n65;
                        int n66 = ((cc)object).ae();
                        cn cn4 = cn.a((byte)0, false, 0);
                        cn cn5 = new cn(0, cn4.k, 0);
                        cn5.a(5, 8, 294, 127);
                        for (n65 = 0; n65 < n66; ++n65) {
                            byte by14 = super.ae();
                            byte by15 = super.ae();
                            n n67 = cn4.b(by14);
                            cn4.b(by14).c = (byte)n65;
                            n67.p = by15;
                            cn5.a(n67, n65);
                        }
                        cn4.e();
                        cn.g = true;
                        for (n65 = 0; n65 < n66; ++n65) {
                            n n68 = cn5.b(n65);
                            cn4.a(n68, n65);
                        }
                        cn.k();
                        cn.g = false;
                        bm.a(bm.A(1006), true);
                        break;
                    }
                    case -17: {
                        int n69 = ((cc)object).aj();
                        bm.at();
                        bm.d(bm.A(1007) + n69);
                        String cfr_ignored_13 = bm.A(1007) + n69;
                        break;
                    }
                    case -257: {
                        int n70 = ((cc)object).aj();
                        long l3 = bm.m();
                        int n71 = (int)l3 - n70;
                        if (n71 < 0) {
                            n71 = 0;
                        }
                        bm.dr = n71;
                        break;
                    }
                    case 2904: {
                        ((cc)object).bR();
                        break;
                    }
                    case 2903: {
                        n n72;
                        q q4 = new q(((cc)object).j, ((cc)object).k);
                        cn.w = n72 = n.a(q4);
                        cn.a(false, null, null);
                        if (n72.f == null) {
                            n72.f = "";
                            bm.bn.S(n72.d);
                        }
                        bm.ae();
                        l.f = 4;
                        break;
                    }
                    default: {
                        bl3 = false;
                    }
                }
                bl2 = bl3;
                if (!bl2) {
                    bl2 = this.aq();
                }
                if (!bl2) {
                    String cfr_ignored_14 = "ill-cmd: " + this.l;
                }
                this.j = null;
                this.k = 0;
            }
            catch (Exception exception) {
                String cfr_ignored_15 = "ParseData error!" + this.l;
                exception.printStackTrace();
                String cfr_ignored_16 = "PasDatErr!" + this.l + "" + exception.toString();
                this.k = 0;
                return true;
            }
        }
        return false;
    }

    private boolean aq() {
        boolean bl2 = true;
        switch (((cc)object).l) {
            case 3111: {
                String string = ((cc)object).an();
                String string2 = ((cc)object).an();
                e.a(string, string2, false);
                break;
            }
            case 3101: {
                String string = ((cc)object).an();
                String string3 = ((cc)object).an();
                e.a(string, string3, false);
                break;
            }
            case 265: {
                byte by2 = ((cc)object).ae();
                String string = ((cc)object).an();
                cs.l = ((cc)object).an();
                if (by2 == 10 || by2 == 19) {
                    bm.a(28, bm.A(873), string, 0);
                } else if (by2 == 21) {
                    bm.a(28, bm.A(873), string, 39);
                } else if (bm.bA) {
                    bm.d(bm.A(874));
                }
                bm.ae();
                break;
            }
            case 2710: {
                i.a = ((cc)object).aj();
                break;
            }
            case 2711: {
                for (int i2 = 0; i2 <= 3; ++i2) {
                    bm.aV[i2] = ((cc)object).aj();
                }
                break;
            }
            case 2712: {
                ag.a(bh.e(((cc)object).aj()));
                break;
            }
            case 10000: {
                byte by3 = ((cc)object).ae();
                if (by3 == 1) {
                    am.q = true;
                    v0.h = (short)4;
                    if (((cc)object).q) {
                        short s2 = ((cc)object).h;
                        ((cc)object).h = (short)(s2 + 1);
                        ((cc)object).g[s2] = 2;
                        short s3 = ((cc)object).h;
                        ((cc)object).h = (short)(s3 + 1);
                        ((cc)object).g[s3] = (byte)(am.o >>> 24);
                        short s4 = ((cc)object).h;
                        ((cc)object).h = (short)(s4 + 1);
                        ((cc)object).g[s4] = (byte)(am.o >>> 16);
                        short s5 = ((cc)object).h;
                        ((cc)object).h = (short)(s5 + 1);
                        ((cc)object).g[s5] = (byte)(am.o >>> 8);
                        short s6 = ((cc)object).h;
                        ((cc)object).h = (short)(s6 + 1);
                        ((cc)object).g[s6] = (byte)am.o;
                    } else {
                        short s7 = ((cc)object).h;
                        ((cc)object).h = (short)(s7 + 1);
                        ((cc)object).g[s7] = 1;
                        short s8 = ((cc)object).h;
                        ((cc)object).h = (short)(s8 + 1);
                        ((cc)object).g[s8] = 0;
                        short s9 = ((cc)object).h;
                        ((cc)object).h = (short)(s9 + 1);
                        ((cc)object).g[s9] = 0;
                        short s10 = ((cc)object).h;
                        ((cc)object).h = (short)(s10 + 1);
                        ((cc)object).g[s10] = 0;
                        short s11 = ((cc)object).h;
                        ((cc)object).h = (short)(s11 + 1);
                        ((cc)object).g[s11] = 0;
                    }
                    ((cc)object).g[2] = 39;
                    ((cc)object).g[3] = 17;
                    short s12 = ((cc)object).h;
                    ((cc)object).g[0] = (byte)(s12 >> 8);
                    ((cc)object).g[1] = (byte)s12;
                    super.a(((cc)object).g, (int)((cc)object).h);
                    break;
                }
                am.q = false;
                ((cc)object).f.f = true;
                break;
            }
            case 10002: {
                ((cc)object).f.f = true;
                break;
            }
            case 10003: {
                byte by4 = ((cc)object).ae();
                String cfr_ignored_0 = "WapProxyConn Fail:" + by4;
                bm.d(bm.A(875) + by4);
                ((cc)object).f.c();
                break;
            }
            case 2700: {
                int n2 = ((cc)object).ae();
                if (n2 != ag.j.length) {
                    String cfr_ignored_1 = "CHAR Set Len ERROR" + n2;
                    break;
                }
                for (int i3 = 0; i3 < n2; ++i3) {
                    ag.j[i3] = ((cc)object).ae();
                }
                break;
            }
            case 2707: {
                ((cc)object).aU();
                break;
            }
            case 2709: {
                int n3 = ((cc)object).ah();
                i i4 = i.a(n3);
                if (i4 == null) break;
                i4.a(false);
                break;
            }
            case 2705: {
                q q2 = new q(((cc)object).j, ((cc)object).k);
                bh.a(q2);
                break;
            }
            case 3100: {
                bm.ae();
                String string = ((cc)object).an();
                bm.e(string);
                break;
            }
            case 777: {
                bm.ae();
                byte by5 = ((cc)object).ae();
                int n4 = ((cc)object).aj();
                String string = ((cc)object).m(0, n4);
                if (by5 == 1) {
                    bm.a(string + bm.d(0, 89), true);
                    break;
                }
                if (by5 == 2) {
                    bm.a(bm.A(991) + string, true);
                    break;
                }
                if (by5 == 5) {
                    bm.a(string + bm.A(992), true);
                    break;
                }
                if (by5 != 6) break;
                bm.a(bm.A(993), true);
                break;
            }
            case 838: {
                byte by6 = ((cc)object).ae();
                switch (by6) {
                    case 1: {
                        bm.a(bm.A(982), true);
                        break;
                    }
                    case 2: {
                        bm.a(bm.A(882), true);
                        break;
                    }
                    case 3: {
                        bm.a(bm.A(983), true);
                        break;
                    }
                    case 4: {
                        bm.a(bm.A(984), true);
                        break;
                    }
                    case 5: {
                        bm.a(bm.A(985), true);
                        break;
                    }
                    case 6: {
                        bm.a(bm.A(906), true);
                        break;
                    }
                    case 7: {
                        bm.a(bm.A(986), true);
                        break;
                    }
                    case 8: {
                        bm.a(bm.A(987), true);
                        break;
                    }
                    case 9: {
                        bm.a(bm.A(988), true);
                        break;
                    }
                    case 10: {
                        bm.a(bm.A(989), true);
                        break;
                    }
                    case 11: {
                        bm.a(bm.A(990), true);
                    }
                }
                break;
            }
            case 839: {
                byte[] byArray;
                int n5 = ((cc)object).aj();
                ((cc)object).aj();
                ((cc)object).aj();
                int n6 = ((cc)object).aj();
                byte[] byArray2 = new byte[n6];
                super.a(byArray2, 0, n6);
                n6 = super.aj();
                if (n6 != 0) {
                    byArray = new byte[n6];
                    super.a(byArray, 0, n6);
                }
                n6 = super.aj();
                byArray = new byte[n6];
                super.a(byArray, 0, n6);
                n6 = super.aj();
                if (n6 != 0) {
                    byte[] byArray3 = new byte[n6];
                    super.a(byArray3, 0, n6);
                }
                bm.cb.ab = n5;
                break;
            }
            case 1000: {
                break;
            }
            case 1001: {
                ((cc)object).aT();
                break;
            }
            case 842: {
                byte by7 = ((cc)object).ae();
                int n7 = ((cc)object).aj();
                String string = ((cc)object).an();
                int n8 = ((cc)object).ag();
                int n9 = n8 & 0x7F;
                int n10 = n8 >> 7;
                byte by8 = super.ae();
                n8 = super.aj();
                byte by9 = super.ae();
                int n11 = super.aj();
                byte by10 = super.ae();
                switch (by7) {
                    case 1: {
                        cr cr2 = bh.a(bm.br, n7);
                        if (cr2 == null) {
                            cr2 = new cr();
                            bm.br.addElement(cr2);
                        } else {
                            bm.c(bm.A(980));
                        }
                        cr2.C = n7;
                        cr2.aa = by9;
                        cr2.U = by8;
                        cr2.ac = by10;
                        cr2.u = (byte)n9;
                        cr2.ai = (byte)n10;
                        if (cr2.C == bm.cb.C) {
                            bm.cb.aa = by9;
                        }
                        cr2.E = string;
                        cr2.ad = n11;
                        cr2.t(4);
                        cr.a(n7, n8, by9);
                        bm.a(cr2.E + bm.A(981), true);
                        break;
                    }
                    case 2: {
                        cr.v(n7);
                        bm.X = 3;
                        break;
                    }
                    case 3: {
                        cr.a(n7, by9);
                    }
                }
                break;
            }
            case 835: {
                int n12 = ((cc)object).aj();
                w.l = ((cc)object).m(0, n12);
                bm.X = 1;
                break;
            }
            case 849: {
                int n13 = ((cc)object).aj();
                String string = ((cc)object).an();
                if (bm.Y()) {
                    ((cc)object).aa(n13);
                    break;
                }
                if (!bm.a(6, bm.B(162), bg.d(bm.A(979), string), 2, 30000L)) break;
                b = n13;
                break;
            }
            case 1053: {
                ((cc)object).aj();
                ((cc)object).aj();
                int n14 = ((cc)object).aj();
                byte[] byArray = new byte[n14];
                ((cc)object).a(byArray, 0, n14);
                n14 = super.aj();
                byte[] byArray4 = new byte[n14];
                super.a(byArray4, 0, n14);
                break;
            }
            case 882: {
                cn.k();
                byte by11 = ((cc)object).ae();
                switch (by11) {
                    case 1: {
                        bm.a(bm.A(972), true);
                        break;
                    }
                    case 2: {
                        bm.c(bm.A(973));
                        break;
                    }
                    case 3: {
                        bm.a(bm.A(898), true);
                        break;
                    }
                    case 4: {
                        bm.c(bm.A(974));
                        break;
                    }
                    case 5: {
                        bm.c(bm.A(975));
                        break;
                    }
                    case 6: {
                        bm.c(bm.A(976));
                        break;
                    }
                    case 7: {
                        bm.c(bm.A(977));
                        break;
                    }
                    case 8: {
                        bm.c(bm.A(247));
                        break;
                    }
                    case 9: {
                        bm.c(bm.A(978));
                        bm.ae();
                    }
                }
                break;
            }
            case 884: {
                ((cc)object).aj();
                break;
            }
            case 954: {
                byte by12 = ((cc)object).ae();
                cn cn2 = cn.a(by12, false, 0);
                int n15 = ((cc)object).ah();
                byte by13 = ((cc)object).ae();
                n n16 = cn2.b(by13);
                if (n16 == null) {
                    bg.a("UpdateOneItem Null " + n15);
                    break;
                }
                if (n15 != n16.d) {
                    bg.a("UpdateOneItem Id Error" + n15);
                    break;
                }
                q q3 = new q(((cc)object).j, ((cc)object).k);
                n.a(q3, n16);
                String cfr_ignored_2 = "UpdateOneItem P=" + by13 + " D=" + n15;
                bm.X = 3;
                break;
            }
            case 958: {
                break;
            }
            case 885: {
                break;
            }
            case 951: {
                ((cc)object).aR();
                break;
            }
            case 953: {
                break;
            }
            case 950: {
                ((cc)object).aQ();
                break;
            }
            case 964: {
                break;
            }
            case 1220: {
                q q4 = new q(((cc)object).j, ((cc)object).k);
                i.a(q4);
                q q5 = q4;
                ((cc)object).k = q5.a;
                break;
            }
            case 1221: {
                q q6 = new q(((cc)object).j, ((cc)object).k);
                i.a(q6, null, false);
                break;
            }
            case 1224: {
                ((cc)object).aP();
                break;
            }
            case 1228: {
                byte by14 = ((cc)object).ae();
                if (by14 == 0) {
                    bm.U();
                    break;
                }
                bm.T();
                break;
            }
            case 1240: {
                int n17 = ((cc)object).aj();
                bh bh2 = bm.h(n17);
                if (bh2 == null) break;
                bh2.A();
                int n18 = ((cc)object).ag();
                for (int i5 = 0; i5 < n18; ++i5) {
                    q q7 = new q(((cc)object).j, ((cc)object).k);
                    cm cm2 = new cm();
                    cm2.a(q7);
                    bh2.a(cm2);
                    ((cc)object).k += q7.h();
                }
                break;
            }
            case 1241: {
                int n19 = ((cc)object).aj();
                bh bh3 = bm.h(n19);
                if (bh3 == null) break;
                q q8 = new q(((cc)object).j, ((cc)object).k);
                cm cm3 = new cm();
                cm3.a(q8);
                bh3.a(cm3);
                break;
            }
            case 1245: {
                cm cm4;
                int n20 = ((cc)object).ah();
                long l2 = ((cc)object).aj();
                if (bm.cb == null || (cm4 = bm.cb.q(n20)) == null) break;
                cm4.b += l2;
                break;
            }
            case 1246: {
                boolean bl3 = ((cc)object).ae() == 1;
                short s13 = ((cc)object).af();
                short s14 = ((cc)object).af();
                short s15 = ((cc)object).af();
                short s16 = ((cc)object).af();
                short s17 = super.af();
                byte by15 = super.ae();
                cu.a(bl3, s13, s14, s15, s16, s17, by15);
                break;
            }
            case 1242: {
                int n21 = ((cc)object).aj();
                bh bh4 = bm.h(n21);
                int n22 = ((cc)object).ag();
                if (bh4 == null) break;
                for (int i6 = 0; i6 < n22; ++i6) {
                    int n23 = super.ae() & 0xFF;
                    boolean bl4 = false;
                    String cfr_ignored_3 = "add effect: " + n23 + " to unit: " + n21;
                    bl4 = bh4.p(n23);
                    byte by16 = super.ae();
                    if (by16 != 1) continue;
                    short[] sArray = new short[2];
                    short[] sArray2 = sArray;
                    sArray[0] = super.af();
                    sArray2[1] = super.af();
                    if (!bl4) continue;
                    bh4.O.put(new Integer(n23), sArray2);
                }
                break;
            }
            case 1243: {
                int n24 = ((cc)object).aj();
                short s18 = ((cc)object).af();
                bh bh5 = bm.h(n24);
                if (bh5 == null) break;
                bh5.b(bh5.r(s18));
                break;
            }
            case 1244: {
                break;
            }
            case 1260: {
                break;
            }
            case 1261: {
                ((cc)object).cD();
                break;
            }
            case 647: {
                int n25 = ((cc)object).aj();
                byte by17 = ((cc)object).ae();
                cc.o(n25, by17);
                break;
            }
            case 648: {
                ((cc)object).aw();
                break;
            }
            case 649: {
                long l3 = ((cc)object).al();
                String string = ((cc)object).an();
                bm.a(string, l3);
                break;
            }
            case 651: {
                bm.cb.g(true);
                break;
            }
            case 700: {
                String string = ((cc)object).an();
                String string4 = ((cc)object).an();
                if (bm.cb == null) break;
                bm.cb.E = string;
                bm.cb.aT = string4;
                break;
            }
            case 701: {
                int n26 = ((cc)object).aj();
                String string = ((cc)object).an();
                cr cr3 = bh.e(n26);
                if (cr3 == null) break;
                cr3.E = string;
                break;
            }
            case 650: {
                ((cc)object).ax();
                break;
            }
            case 1264: {
                int n27 = ((cc)object).aj();
                cr cr4 = bh.e(n27);
                byte by18 = ((cc)object).ae();
                short s19 = ((cc)object).af();
                if (cr4 == null) break;
                cr4.l(by18, s19);
                break;
            }
            case 1225: {
                Object object;
                int n28 = ((cc)object).aj();
                long l4 = ((cc)object).al();
                bh bh6 = bm.h(n28);
                if (bh6 == null || bh6.m()) break;
                boolean bl5 = false;
                bh6.b = 3;
                bh6.a(l4);
                if (bh6.f()) {
                    object = (ce)bh6;
                    if (((h)object).d == ck.b(159)) {
                        ((bh)object).J = (byte)2;
                        ((h)object).b(3);
                    }
                }
                if (bh6 == bm.cb) break;
                bh6.N = 500;
                break;
            }
            case 1227: {
                int n29 = ((cc)object).ah();
                byte by19 = ((cc)object).ae();
                int n30 = ((cc)object).aj();
                cr.k(n29, n30);
                String cfr_ignored_4 = "SkillError skillId=" + n29 + " err=" + by19 + "   Lcd " + n30;
                break;
            }
            case 1268: {
                int n31 = ((cc)object).aj();
                cr cr5 = bh.e(n31);
                if (((cc)object).k < ((cc)object).m) {
                    int n32 = ((cc)object).ag();
                    short s20 = ((cc)object).af();
                    int n33 = super.aj();
                    if (cr5 == null) break;
                    cr5.b(n32, (int)s20, n33);
                    break;
                }
                if (cr5 == null) break;
                cr5.e(false);
                break;
            }
            case 1269: {
                bm.ae();
                byte by20 = ((cc)object).ae();
                if (by20 != 1) break;
                bm.a(bm.A(876), true);
                break;
            }
            case 955: {
                byte by21 = ((cc)object).ae();
                q q9 = new q(((cc)object).j, ((cc)object).k);
                n n34 = n.a(q9);
                ((cc)object).k += q9.h();
                if (by21 == 5) {
                    bm.cb.ao[n34.c] = n34;
                } else {
                    cn cn3 = cn.a(by21, false, 0);
                    cn3.a(n34, (int)n34.c);
                    bm.r(by21);
                }
                if (bm.aA.a() <= 3) {
                    bm.p(21);
                    k.a(85, 61, 180, 1, 5000, bm.B(380));
                    bm.ba[2] = bm.m();
                }
                cn.k();
                break;
            }
            case 901: {
                int n35 = ((cc)object).aj();
                int n36 = ((cc)object).aj();
                ((cc)object).n = ((cc)object).aj();
                String string = ((cc)object).m(0, ((cc)object).n);
                if (cr.aI > 0) {
                    if (bm.Y()) {
                        super.aa(n36);
                        break;
                    }
                    bm.aW = string;
                    bm.at = n35;
                    bm.au = n36;
                    bm.a(1, bm.B(162), "^b" + string + "^b" + bm.B(221), 2, 30000L);
                    break;
                }
                ((cc)object).c(n35, n36, 3);
                break;
            }
            case 905: {
                ((cc)object).aO();
                break;
            }
            case 907: {
                break;
            }
            case 913: {
                bm.a(bm.A(877), true);
                break;
            }
            case 914: {
                bm.a(bm.A(878), true);
                break;
            }
            case 915: {
                bm.a(bm.A(879) + bm.B(264), true);
                break;
            }
            case 916: {
                bm.a(bm.A(880), true);
                break;
            }
            case 917: {
                bm.a(bm.A(881), true);
                break;
            }
            case 908: {
                bm.ae();
                cn.k();
                byte by22 = ((cc)object).ae();
                switch (by22) {
                    case 0: {
                        bm.a(bm.A(967), true);
                        break;
                    }
                    case 2: {
                        bm.a(bm.A(968), true);
                        break;
                    }
                    case 1: {
                        bm.a(bm.A(969), true);
                        break;
                    }
                    case 3: {
                        bm.a(bm.A(970), true);
                        break;
                    }
                    case 4: {
                        bm.a(bm.A(971), true);
                    }
                }
                cn.c(false);
                bm.X();
                cn.U = null;
                bm.H();
                break;
            }
            case 910: {
                ((cc)object).aN();
                break;
            }
            case 909: {
                bm.a(bm.A(882), true);
                break;
            }
            case 912: {
                bm.ae();
                cn.k();
                cn.c(true);
                bm.H();
                bm.a(bm.A(883), true);
                break;
            }
            case 931: {
                bm.ae();
                bm.a(bm.A(884), true);
                break;
            }
            case 932: {
                int n37;
                ((cc)object).o = ((cc)object).ae();
                bm.bw = ((cc)object).o;
                if (1 != ((cc)object).o) break;
                cr.av = n37 = ((cc)object).aj();
                break;
            }
            case 930: {
                cn.k();
                ((cc)object).o = ((cc)object).ae();
                if (1 == ((cc)object).o) {
                    cr.av = ((cc)object).aj();
                    break;
                }
                if (0 == ((cc)object).o) {
                    bm.c(bm.A(885));
                    break;
                }
                if (2 != ((cc)object).o) break;
                bm.a(bg.d(bm.A(886), ay.d), true);
                break;
            }
            case 1202: {
                ((cc)object).cw();
                break;
            }
            case 989: {
                ((cc)object).c(true);
                break;
            }
            case 1004: {
                ((cc)object).aM();
                break;
            }
            case 1952: {
                int n38;
                byte by23 = ((cc)object).ae();
                byte by24 = ((cc)object).ae();
                byte by25 = ((cc)object).ae();
                byte by26 = ((cc)object).ae();
                if (by26 > 0) {
                    l.h = new int[by26];
                    l.i = new String[by26];
                    for (n38 = 0; n38 < by26; ++n38) {
                        l.h[n38] = super.aj();
                        l.i[n38] = n38 + 1 + "." + super.an();
                    }
                }
                n38 = ((cc)object).ae();
                bm.dW = 0;
                bm.dX = by24;
                if (bm.dY > by25) {
                    bm.dW = n38 - 1;
                }
                bm.dY = by25;
                l.e = new Vector();
                for (int i7 = 0; i7 < n38; ++i7) {
                    int n39 = super.aj();
                    String string = super.an();
                    by26 = super.ae();
                    byte by27 = super.ae();
                    int n40 = super.ag();
                    int n41 = n40 & 0x7F;
                    int n42 = n40 >> 7;
                    cr cr6 = new cr();
                    new cr().C = n39;
                    cr6.a(string, n39, (byte)n41, (byte)0);
                    cr6.ai = (byte)n42;
                    cr6.U = by27;
                    cr6.aj = by26;
                    cr6.ac = 1;
                    cr6.ac = by26 >= 3 ? 0 : 1;
                    l.e.addElement(cr6);
                }
                if (by23 == 0) {
                    bm.l(81);
                    l.a((byte)4);
                } else if (by23 == 4) {
                    bm.l(81);
                    l.a((byte)6);
                } else if (by23 == 100) {
                    bm.l(81);
                    l.a((byte)7);
                } else if (by23 == 1) {
                    bm.l(81);
                    l.a((byte)5);
                } else if (by23 == 3) {
                    bm.m(81);
                    l.a((byte)3);
                } else if (by23 == 2) {
                    bm.m(81);
                    l.a((byte)1);
                } else if (by23 == 5) {
                    bm.l(81);
                    l.a((byte)12);
                }
                bm.X = 3;
                bm.ae();
                break;
            }
            case 845: {
                ((cc)object).aS();
                break;
            }
            case 847: {
                break;
            }
            case 848: {
                bm.a(bm.A(887), true);
                break;
            }
            case 966: {
                int n43 = ((cc)object).ah();
                String string = ((cc)object).am();
                bm.a(n43, string);
                if (cn.w != null && cn.w.d == n43 && cn.aq == n43) {
                    cn.w.f = string;
                    cn.a(false, null, null);
                }
                bm.ae();
                break;
            }
            case 1009: {
                ((cc)object).aL();
                break;
            }
            case 1012: {
                break;
            }
            case 1015: 
            case 2111: {
                ((cc)object).b(((cc)object).l == 2111);
                break;
            }
            case 1016: {
                break;
            }
            case 1092: {
                ((cc)object).aK();
                break;
            }
            case 1013: {
                ((cc)object).aj();
                ((cc)object).aj();
                ((cc)object).aj();
                break;
            }
            case 1281: {
                ((cc)object).aJ();
                break;
            }
            case 1283: {
                break;
            }
            case 1289: {
                bm.cA = true;
                bm.cs = 2;
                break;
            }
            case 1288: {
                bm.cB = true;
                bm.cs = 2;
                break;
            }
            case 1290: {
                bm.cC = true;
                bm.cs = 2;
                break;
            }
            case 1291: {
                bm.cD = true;
                bm.cs = 2;
                break;
            }
            case 1292: {
                int n44 = ((cc)object).ag();
                byte by28 = (byte)(n44 & 0x7F);
                boolean bl6 = false;
                int n45 = ((cc)object).aj();
                int n46 = ((cc)object).aj();
                int n47 = super.aj();
                String cfr_ignored_5 = bm.A(888) + n44;
                for (int i8 = 0; i8 < bm.ca.size(); ++i8) {
                    h h2 = (h)bm.ca.elementAt(i8);
                    if (h2 == null || h2.a != 4) continue;
                    bv bv2 = (bv)h2;
                    if (bv2.t == by28 && bv2.w == 2) break;
                }
                bm.a(by28, n46, n47, n45, null, null, (byte)2, false);
                break;
            }
            case 1293: {
                Object object;
                int n48 = ((cc)object).ag();
                byte by29 = (byte)(n48 & 0x7F);
                boolean bl7 = n48 >> 7 == 0;
                byte by30 = ((cc)object).ae();
                String cfr_ignored_6 = bm.A(889) + n48;
                for (int i9 = 0; i9 < bm.ca.size(); ++i9) {
                    object = (h)bm.ca.elementAt(i9);
                    if (object == null || ((h)object).a != 4) continue;
                    bv bv3 = (bv)object;
                    if (bv3.t != by29) continue;
                    bv3.v = bl7;
                    if (!bv3.v) continue;
                    if (by30 == 1) {
                        new t();
                        Object var4_113 = null;
                        t.a(0, bv3.r, bv3.k, bv3.l);
                        bm.a(bm.A(890), (int)bm.cV, bm.cb.F, (short)bv3.k, (short)bv3.l, 0);
                    } else {
                        bm.a(bm.A(891), (int)bm.cV, bm.cb.F, (short)bv3.k, (short)bv3.l, 0);
                    }
                    bm.a(bv3);
                }
                break;
            }
            case 1294: {
                ((cc)object).aI();
                break;
            }
            case 1100: {
                ((cc)object).aH();
                break;
            }
            case 1103: {
                break;
            }
            case 1112: {
                break;
            }
            case 1102: {
                ((cc)object).aG();
                break;
            }
            case 1104: {
                bm.a(bm.A(946), true);
                bm.ae();
                break;
            }
            case 1106: {
                ((cc)object).aF();
                break;
            }
            case 1107: {
                ((cc)object).aE();
                break;
            }
            case 1108: {
                ((cc)object).o = ((cc)object).ae();
                if (1 != ((cc)object).o) break;
                break;
            }
            case 1109: {
                ((cc)object).aD();
                break;
            }
            case 1113: {
                ((cc)object).aC();
                break;
            }
            case 1110: {
                ((cc)object).o = ((cc)object).ae();
                if (1 != ((cc)object).o) break;
                bm.a(bm.A(892), true);
                break;
            }
            case 1114: {
                break;
            }
            case 1115: {
                ((cc)object).aB();
                break;
            }
            case 1118: {
                ((cc)object).aA();
                break;
            }
            case 1124: {
                ((cc)object).o = ((cc)object).ae();
                if (1 == ((cc)object).o) {
                    bm.ae();
                    bm.a(bm.A(896), true);
                    break;
                }
                bm.a(bm.A(897), true);
                break;
            }
            case 1296: {
                bm.cE = true;
                bm.cA = false;
                break;
            }
            case 1297: {
                bm.cF = true;
                bm.cA = false;
                break;
            }
            case 973: {
                bm.a(bm.A(898), true);
                bm.ae();
                bm.X = 3;
                break;
            }
            case 975: {
                bm.a(bm.A(899), true);
                bm.ae();
                break;
            }
            case 976: {
                ((cc)object).bn();
                break;
            }
            case 977: {
                ((cc)object).bo();
                break;
            }
            case 980: {
                ((cc)object).bp();
                break;
            }
            case 981: {
                ((cc)object).bq();
                break;
            }
            case 982: {
                ((cc)object).br();
                break;
            }
            case 983: {
                ((cc)object).bs();
                break;
            }
            case 974: {
                bm.ae();
                bm.a(bm.A(900), true);
                break;
            }
            case 641: {
                bm.a(bm.A(901), true);
                bm.X = 3;
                break;
            }
            case 642: {
                ((cc)object).bt();
                break;
            }
            case 1126: {
                if (f.c == 6) {
                    f.a(f.c);
                } else {
                    f.a(0);
                }
                bm.a(bm.A(902), true);
                bm.X = 3;
                break;
            }
            case 1127: {
                bm.ae();
                bm.c(bm.A(903));
                break;
            }
            case 1128: {
                ((cc)object).az();
                break;
            }
            case 1129: {
                ((cc)object).av();
                break;
            }
            case 1072: {
                bm.c(bm.A(904));
                bm.X = 3;
                break;
            }
            case 985: {
                byte by31 = ((cc)object).ae();
                int n49 = ((cc)object).aj();
                cc.p(by31, n49);
                break;
            }
            case 986: {
                byte by32 = ((cc)object).ae();
                cc.Z(by32);
                break;
            }
            case 987: {
                int n50 = ((cc)object).ah();
                int n51 = ((cc)object).ag();
                cc.q(n50, n51);
                break;
            }
            case 988: {
                ((cc)object).ay();
                break;
            }
            case 1262: {
                bm.c(bm.A(905));
                bm.X = 3;
                break;
            }
            case 620: {
                ((cc)object).au();
                break;
            }
            case 1051: {
                ((cc)object).at();
                break;
            }
            case 3007: {
                ((cc)object).cc();
                break;
            }
            case 4300: {
                bm.bd.addElement(((cc)object).an());
                break;
            }
            case 853: {
                bm.cb.ab = 0;
                bm.cb.aa = 0;
                bm.a(bm.A(906), true);
                cr.D();
                bm.X = 3;
                break;
            }
            case 854: {
                int n52 = ((cc)object).aj();
                byte by33 = ((cc)object).ae();
                ((cc)object).n = ((cc)object).aj();
                Object object = ((cc)object).m(0, ((cc)object).n);
                for (int i10 = 0; i10 < bm.br.size(); ++i10) {
                    if (((cr)bm.br.elementAt((int)i10)).C != n52) continue;
                    ((cr)bm.br.elementAt((int)i10)).ac = by33;
                    ((cr)bm.br.elementAt((int)i10)).E = object;
                }
                break;
            }
            case 1401: {
                bm.dn = ((cc)object).ag() != 0;
                break;
            }
            case 1402: {
                bm.ae();
                bm.d(bm.A(907));
                break;
            }
            case 771: {
                ((cc)object).as();
                break;
            }
            case 778: {
                ((cc)object).ar();
                break;
            }
            case 1298: {
                bm.cr = bm.m();
                bm.a(bm.A(908), true);
                bm.cq = true;
                break;
            }
            case 1299: {
                ((cc)object).aj();
                ((cc)object).aj();
                bm.a(bm.A(909), true);
                break;
            }
            case 1263: {
                bm.c(bm.A(910));
                break;
            }
            case 5: {
                ((cc)object).o = ((cc)object).ae();
                break;
            }
            case 1421: {
                ((cc)object).cd();
                break;
            }
            case 1423: {
                ((cc)object).ce();
                break;
            }
            case 1425: {
                ((cc)object).cf();
                break;
            }
            case 1431: {
                ((cc)object).cg();
                bm.H();
                break;
            }
            case 1433: {
                ((cc)object).ch();
                bm.H();
                break;
            }
            case 1445: {
                ((cc)object).ci();
                break;
            }
            case 1446: {
                ((cc)object).cj();
                break;
            }
            case 1437: {
                break;
            }
            case 1447: {
                ((cc)object).ck();
                break;
            }
            case 1439: {
                ((cc)object).cl();
                break;
            }
            case 1448: {
                ((cc)object).cm();
                break;
            }
            case 1435: {
                ((cc)object).cn();
                break;
            }
            case 1443: {
                ((cc)object).co();
                break;
            }
            case 1444: {
                ((cc)object).cp();
                break;
            }
            case 1441: {
                break;
            }
            case 1453: {
                ((cc)object).cq();
                break;
            }
            case 2601: {
                ((cc)object).cr();
                break;
            }
            case 2603: {
                ((cc)object).cs();
                break;
            }
            case 2605: {
                ((cc)object).ct();
                break;
            }
            case 2607: {
                break;
            }
            case 2608: {
                break;
            }
            case 2609: {
                ((cc)object).cu();
                break;
            }
            case 2610: {
                break;
            }
            case 2611: {
                ((cc)object).cv();
                break;
            }
            case 1500: {
                bm.ae();
                ((cc)object).o = ((cc)object).ae();
                if (1 == ((cc)object).o) {
                    ((cc)object).aj();
                    boolean bl8 = false;
                    ((cc)object).am();
                    Object var2_70 = null;
                    bc.a((byte)15);
                    break;
                }
                bm.a(bm.A(911), 0, 3000);
                break;
            }
            case 1503: {
                int n53 = ((cc)object).ag();
                int n54 = ((cc)object).ag();
                for (int i11 = 0; i11 < n53; ++i11) {
                    int n55 = ((cc)object).aj();
                    int n56 = ((cc)object).aj();
                    if (n54 == 1) {
                        cr.k(n55, n56);
                        String cfr_ignored_7 = "SkillId=" + n55 + " Remain=" + n56;
                        continue;
                    }
                    bm.aA.a(n55, n56);
                }
                bm.Y = 3;
                break;
            }
            case 1505: {
                int n57 = ((cc)object).ah();
                int n58 = ((cc)object).aj();
                cr.k(n57, n58);
                break;
            }
            case 1504: {
                int n59 = ((cc)object).ah();
                int n60 = ((cc)object).aj();
                bm.aA.a(n59, n60);
                bm.c(bm.A(912));
                break;
            }
            case 1449: {
                bm.d(bm.A(913));
                break;
            }
            case 1450: {
                int n61;
                if (bm.cb.aX == null) break;
                cv.J = n61 = ((cc)object).aj();
                cr.aY = 16;
                break;
            }
            case 2100: {
                ((cc)object).P();
                break;
            }
            case 2101: {
                ((cc)object).Q();
                break;
            }
            case 2102: {
                ((cc)object).R();
                break;
            }
            case 2103: {
                ((cc)object).S();
                break;
            }
            case 2104: {
                ((cc)object).T();
                break;
            }
            case 2105: {
                ((cc)object).U();
                break;
            }
            case 2107: {
                ((cc)object).cC();
                break;
            }
            case 2113: {
                ((cc)object).d(true);
                break;
            }
            case 2114: {
                ((cc)object).V();
                break;
            }
            case 2108: {
                ((cc)object).W();
                break;
            }
            case 2109: {
                ((cc)object).X();
                break;
            }
            case 2110: {
                ((cc)object).Y();
                break;
            }
            case 2112: {
                ((cc)object).Z();
                break;
            }
            case 1506: {
                ((cc)object).bu();
                break;
            }
            case 3200: {
                ((cc)object).cx();
                break;
            }
            case 3201: {
                ((cc)object).cy();
                break;
            }
            case 3301: {
                ((cc)object).cz();
                break;
            }
            case 3110: {
                ((cc)object).cA();
                break;
            }
            case 4101: {
                ((cc)object).cB();
                break;
            }
            case 652: {
                ((cc)object).bS();
                break;
            }
            case 3050: {
                ((cc)object).bT();
                break;
            }
            case 654: {
                ((cc)object).bU();
                break;
            }
            case 655: {
                ((cc)object).bV();
                break;
            }
            case 656: {
                ((cc)object).bW();
                break;
            }
            case 267: {
                ((cc)object).bX();
                break;
            }
            case 1140: {
                ((cc)object).bY();
                break;
            }
            case 1145: {
                ((cc)object).bZ();
                break;
            }
            case 3300: {
                ((cc)object).cG();
                break;
            }
            case 3302: {
                ((cc)object).cF();
                break;
            }
            case 3303: {
                cn.ai = ((cc)object).aj();
                break;
            }
            case 3309: {
                cc.cE();
                break;
            }
            case 3305: {
                ((cc)object).cH();
                break;
            }
            case 3307: {
                ((cc)object).cI();
                break;
            }
            case 3312: {
                ((cc)object).cJ();
                break;
            }
            case 3311: {
                ((cc)object).cK();
                break;
            }
            default: {
                bl2 = false;
            }
        }
        return bl2;
    }

    private void ar() {
        byte by2 = this.ae();
        if (by2 == 0) {
            bm.a(bm.A(921), true);
            return;
        }
        if (by2 == 2) {
            bm.c(bm.A(922));
        }
    }

    private void as() {
        int n2 = ((cc)((Object)string)).aj();
        byte by2 = ((cc)((Object)string)).ae();
        if (bm.Y()) {
            ((cc)((Object)string)).aa(n2);
            return;
        }
        String string = ((cc)((Object)string)).m(0, by2);
        if (bm.a(5, bm.B(162), bg.d(bm.A(923), string), 2, 30000L)) {
            b = n2;
        }
    }

    private void at() {
        int n2 = this.aj();
        int n3 = this.aj();
        String string = this.an();
        String string2 = this.an();
        if (bm.Y()) {
            this.aa(n2);
            return;
        }
        string2 = bg.b(bm.A(924), string, string2);
        if (bm.a(4, bm.B(162), string2, 2, 30000L)) {
            b = n2;
            a = n3;
        }
    }

    private void au() {
        int n2 = this.aj();
        byte by2 = this.ae();
        cr cr2 = bh.e(n2);
        if (cr2 != null) {
            if (by2 > cr2.U) {
                cr2.v();
            }
            cr2.U = by2;
        }
    }

    private void av() {
        int n2;
        int n3 = this.aj();
        bm.dd = new int[n3];
        bm.dc = new String[n3];
        for (n2 = 0; n2 < n3; ++n2) {
            bm.dd[n2] = this.aj();
        }
        for (n2 = 0; n2 < n3; ++n2) {
            this.n = this.aj();
            bm.dc[n2] = this.m(0, this.n);
        }
    }

    private static void o(int n2, int n3) {
        if (n2 < 0 || n3 < 0) {
            return;
        }
        if (n2 == 0) {
            if (bm.t) {
                bm.t = false;
            }
            bm.a(bm.A(925), true);
            return;
        }
        if (n3 != 0) {
            bm.t = true;
            n3 = n2 / 1000 / 60;
            int n4 = n2 / 1000 % 60;
            String string = bg.a(bm.A(926), n3, n4);
            bm.a(string, true);
            n3 = bm.cb.P.size();
            n4 = 0;
            for (int i2 = 0; i2 < n3; ++i2) {
                cm cm2 = (cm)bm.cb.P.elementAt(i2);
                if (cm2 == null || cm2.a != 100000) continue;
                n4 = 1;
                cm2.b = bm.m() + (long)n2;
                break;
            }
            if (n4 == 0) {
                cm cm3 = new cm();
                new cm().b = bm.m() + (long)n2;
                cm3.a = 100000;
                cm3.d = 256 + ab.b[14];
                cm3.e = 1;
                cm3.f = bm.A(927);
                bm.cb.P.addElement(cm3);
                return;
            }
        } else {
            bm.t = false;
            bm.c(bm.A(928));
            n3 = bm.cb.P.size();
            for (int i3 = 0; i3 < n3; ++i3) {
                cm cm4 = (cm)bm.cb.P.elementAt(i3);
                if (cm4 == null || cm4.a != 100000) continue;
                bm.cb.P.removeElementAt(i3);
                return;
            }
        }
    }

    private void aw() {
        int n2 = this.ae();
        bm.dh = 0;
        bm.dg = true;
        if (n2 > 0) {
            bm.df = new int[n2];
            for (int i2 = 0; i2 < n2; ++i2) {
                bm.df[i2] = this.ag();
            }
        }
    }

    private void ax() {
        int n2 = this.aj();
        cr cr2 = bh.e(n2);
        if (cr2 != null) {
            int n3 = this.ag();
            for (int i2 = 0; i2 < n3; ++i2) {
                int n4 = this.ag();
                int n5 = this.ah();
                if (n4 < 0 || n4 >= 15) continue;
                cr2.i(n4, n5);
            }
        }
    }

    private static void p(int n2, int n3) {
        if (n2 < 0 || n3 < 0) {
            return;
        }
        if (n2 == 0) {
            if (n3 == 0) {
                cn.a(bm.A(929), 0);
                return;
            }
            cn.a(bm.A(930) + n3, 5);
            return;
        }
        if (n3 == 0) {
            cn.a(bm.A(931), 0);
            return;
        }
        cn.a(bm.A(932) + n3, 6);
    }

    private static void Z(int n2) {
        if (n2 == 1 && bm.cb.U > 5) {
            bm.p(5);
            k.a(85, 61, 180, 1, 5000, bm.B(379));
            bm.ba[0] = bm.m();
            return;
        }
        bm.q(5);
    }

    private static void q(int n2, int n3) {
        if (n3 == -1) {
            return;
        }
        if (bm.cb.U < 5) {
            return;
        }
        if (bm.aB == null || bm.aA == null) {
            return;
        }
        n n4 = bm.aA.b(n3);
        if (n4 == null || n4.d != n2) {
            bg.a(bm.A(933) + n2 + " pos " + n3);
        }
        boolean bl2 = n.a(n4, bm.aB);
        n2 = bl2 ? 1 : 0;
        if (bl2) {
            bm.p(4);
            k.a(85, 61, 180, 1, 5000, bm.B(378));
            bm.ba[1] = bm.m();
        }
    }

    private void ay() {
        Object object;
        cc cc2 = this;
        short s2 = cc2.ai();
        cc2 = this;
        short s3 = cc2.ai();
        cc2 = this;
        short s4 = cc2.ai();
        if (s2 < cn.e.size() && s2 >= 0 && (object = (cn)cn.e.elementAt(s2)) != null && (object = ((cn)object).b(s3)) != null && s4 > 0) {
            ((n)object).p = s4;
        }
    }

    private void az() {
        bm.ae();
        int n2 = this.aj();
        int n3 = this.aj();
        switch (n2) {
            case 100004: {
                bm.a(bg.a(bm.A(934), n3), true);
            }
        }
    }

    private void aA() {
        int n2 = this.aj();
        int n3 = this.ae();
        if (n3 == 0) {
            bm.a(bm.A(935), true);
            return;
        }
        n3 = this.aj();
        int n4 = this.aj();
        if (n3 < 10000) {
            bm.cb.F = n3;
            n3 = n2;
            n2 = bm.cb.C;
            this.c(16, (short)1119);
            this.Y(n2);
            this.Y(n3);
            this.Y(n4);
            this.ca();
            bm.q();
        } else {
            bm.a(bm.A(936), true);
        }
        bm.ae();
    }

    private void aB() {
        int n2 = this.aj();
        switch (n2) {
            case 0: {
                bm.a(bm.A(937), true);
                break;
            }
            case 1: {
                bm.c(bm.A(938));
                break;
            }
            case 2: {
                bm.c(bm.A(939));
            }
        }
        bm.ae();
    }

    private void aC() {
        this.o = this.ae();
        this.n = this.aj();
        String string = this.m(0, this.n);
        if (1 == this.o) {
            bm.a(bm.A(941) + string, true);
        } else if (2 == this.o) {
            bm.a(bm.A(898), true);
        }
        bm.ae();
    }

    private void aD() {
        this.o = this.ae();
        this.n = this.aj();
        String string = this.m(0, this.n);
        if (1 == this.o) {
            bm.a(bm.A(941) + string, true);
        } else if (2 == this.o) {
            bm.a(bm.A(942), true);
        }
        bm.ae();
    }

    private void aE() {
        this.o = this.ae();
        int n2 = this.aj();
        int n3 = this.aj();
        if (1 == this.o) {
            bm.a(bg.a(bm.A(943), n2, n3), true);
        }
        bm.ae();
    }

    private void aF() {
        this.o = this.ae();
        if (2 == this.o) {
            bm.c(bm.A(944));
        } else if (1 == this.o) {
            bm.c(bm.A(945));
        }
        bm.ae();
    }

    private void aG() {
        int n2 = this.aj();
        switch (n2) {
            case 0: {
                bm.c(bm.A(944));
                break;
            }
            case 1: {
                bm.a(bm.A(947), true);
                break;
            }
            case 2: {
                bm.a(bm.A(948), true);
                break;
            }
            case 3: {
                bm.a(bm.A(949), true);
                break;
            }
            case 4: {
                bm.c(bm.A(950));
                break;
            }
            case 5: {
                bm.c(bm.A(950));
                break;
            }
            case 6: {
                bm.c(bm.A(951));
                break;
            }
            case 7: {
                bm.a(bm.A(952), true);
            }
        }
        bm.ae();
    }

    private void aH() {
        this.o = this.ae();
        this.n = this.aj();
        bm.cb.E = this.m(0, this.n);
        if (1 == this.o) {
            bm.a(bm.A(956), 5);
        } else if (0 == this.o) {
            bm.d(bm.A(957));
        } else if (0 == this.o) {
            bm.d(bm.A(958));
        }
        bm.ae();
    }

    private void aI() {
        int n2 = this.aj();
        int n3 = this.aj();
        int n4 = this.aj();
        int n5 = this.aj();
        int n6 = this.aj();
        int n7 = n2 + n3 + n4 + n5;
        bn.g[0][1] = n2 + "";
        bn.g[1][1] = n3 + "";
        bn.g[2][1] = n4 + "";
        bn.g[3][1] = n5 + "";
        bn.g[4][1] = n7 + "";
        bn.g[5][1] = n6 + "";
        bm.H();
        bm.l(28);
        bm.cs = 6;
        bm.I.b();
        bm.I.a(16);
    }

    private void aJ() {
        bm.cy = this.am();
        bm.cx = this.am();
        byte by2 = this.ae();
        bm.ct = by2;
        bm.cu = new int[by2];
        bm.cw = new short[bm.ct];
        bm.cv = new short[bm.ct];
        bm.cz = new String[bm.ct];
        for (int i2 = 0; i2 < bm.ct; ++i2) {
            bm.cu[i2] = this.aj();
            cc cc2 = this;
            bm.cv[i2] = cc2.ai();
            cc2 = this;
            bm.cw[i2] = cc2.ai();
            bm.cz[i2] = this.am();
        }
        bm.S();
    }

    private void aK() {
        int n2 = this.aj();
        int n3 = this.ag();
        int n4 = this.ag();
        int n5 = this.ag();
        if (n2 == bm.cb.C) {
            return;
        }
        for (int i2 = 0; i2 < bm.bq.size(); ++i2) {
            cr cr2 = (cr)bm.bq.elementAt(i2);
            if (cr2.C != n2) continue;
            cr2.H = (byte)n3;
            cr2.r[4] = 100;
            cr2.r[5] = 100;
            cr2.I = (byte)n4;
            cr2.U = (byte)n5;
        }
    }

    private void b(boolean n2) {
        int n3 = 0;
        n3 = n2 != 0 ? bm.cb.C : this.aj();
        byte by2 = this.ae();
        bh bh2 = bh.f(n3);
        if (bh2 != null) {
            if (n2 != 0) {
                cr cr2 = bm.cb;
                if ((by2 & 1) != 0) {
                    int n4 = this.aj();
                    if ((by2 & 0x40) != 0) {
                        int n5 = this.aj();
                        cr2.l(n5);
                    }
                    if (cr.aI > 0 && n4 <= 0) {
                        bm.t = false;
                        cr2.w(5);
                    }
                    bm.cb.i(n4);
                    String cfr_ignored_0 = "HP updating HP=" + cr.aI + " Name=" + cr2.E;
                }
                if ((by2 & 2) != 0) {
                    int n6;
                    cr2.r[4] = n6 = this.aj();
                    String cfr_ignored_1 = "HPMax updating HPMax=" + cr2.r[4] + " Name=" + cr2.E;
                }
                if ((by2 & 4) != 0) {
                    int n7 = this.ah();
                    if ((by2 & 0x40) != 0) {
                        cc cc2 = this;
                        short s2 = cc2.ai();
                        cr2.m(s2);
                    }
                    bm.cb.j(n7);
                    String cfr_ignored_2 = "MP update MP =" + cr.aJ + " Name=" + cr2.E;
                }
                if ((by2 & 8) != 0) {
                    int n8;
                    cr2.r[5] = n8 = this.ah();
                    String cfr_ignored_3 = "MPMax update MPMax=" + cr2.r[5] + " Name=" + cr2.E;
                    return;
                }
            } else {
                if ((by2 & 1) != 0 || (by2 & 2) != 0) {
                    n2 = this.ag();
                    bh2.H = (byte)n2;
                    if (n2 <= 0) {
                        bh2.l();
                    }
                    String cfr_ignored_4 = "HP% update HP=" + bh2.H + " Name=" + bh2.E;
                    return;
                }
                if ((by2 & 4) != 0 || (by2 & 8) != 0) {
                    n2 = this.ag();
                    bh2.I = (byte)n2;
                    String cfr_ignored_5 = "MP% update MP=" + bh2.I + " Name=" + bh2.E;
                }
            }
        }
    }

    private void aL() {
        this.aj();
        this.aj();
        this.aj();
        this.aj();
        this.aj();
        this.aj();
        this.aj();
        this.aj();
        this.aj();
        this.aj();
        this.aj();
        this.aj();
        this.aj();
        this.aj();
        this.aj();
    }

    private void aM() {
        Object object;
        int n2;
        int n3;
        int n4;
        String string;
        bm.ae();
        int n5 = this.aj();
        cr cr2 = bh.e(n5);
        if (cr2 == null) {
            cr2 = new cr();
        }
        cr2.E = string = this.am();
        block9: for (n4 = 0; n4 <= 6; ++n4) {
            switch (n4) {
                case 0: {
                    cr2.u = this.ae();
                    continue block9;
                }
                case 1: {
                    cr2.v = this.ae();
                    continue block9;
                }
                case 2: {
                    cr2.ai = this.ae();
                    continue block9;
                }
                case 3: {
                    cr2.U = this.ae();
                    continue block9;
                }
                case 4: {
                    this.aj();
                    n3 = 0;
                    continue block9;
                }
                case 5: {
                    cn.D = this.aj();
                    continue block9;
                }
                case 6: {
                    cn.C = this.aj();
                }
            }
        }
        n4 = this.aj();
        for (n3 = 0; n3 < 15; ++n3) {
            cr2.r[n3] = this.aj();
        }
        n3 = this.ag();
        int n6 = -1;
        if (n3 > 0 && n3 <= 3) {
            n6 = this.aj();
        }
        if (bm.aF == null) {
            bm.aF = new cn(1, 31, 2);
        } else {
            bm.aF.e();
        }
        int n7 = this.ae();
        for (n2 = 0; n2 < n7; ++n2) {
            q q2 = new q(this.j, this.k);
            object = n.a(q2);
            this.k += q2.h();
            bm.aF.a((n)object, (int)((n)object).c);
        }
        if (cr2.aW == null) {
            cr2.a(cr2.E, cr2.C, cr2.u, cr2.v);
        }
        this.a(cr2, false);
        cr2.Z = n4;
        cr2.W = this.an();
        cr2.X = this.an();
        this.an();
        this.c(false);
        cr2.V = this.aj();
        cn.E = this.an();
        if (n6 >= 0) {
            cr2.b(n3, 0, n6);
        }
        cn.a(cr2);
        bm.aK = string;
        bm.aQ = null;
        bm.aP = bm.aF;
        bm.aP.a(0, 0, 0, 0);
        bm.a(1, false);
        n2 = this.ae();
        bm.v = n5;
        for (int i2 = 0; i2 < n2; ++i2) {
            object = new q(this.j, this.k);
            n n8 = n.a((q)object);
            this.k += ((q)object).h();
            cr2.ao[n8.c] = n8;
        }
    }

    private void c(boolean bl2) {
        cn cn2 = bl2 ? bm.aB : bm.aF;
        if (cn2 == null) {
            return;
        }
        for (int i2 = 0; i2 < 2; ++i2) {
            boolean bl3;
            boolean bl4 = bl3 = this.ae() == 1;
            if (bl2 && !bl3) continue;
            n n2 = new n();
            byte by2 = this.ae();
            n2.c = by2 = (byte)(by2 + 20);
            int n3 = this.ae();
            if (n3 == 0) {
                cn2.a((int)by2);
                continue;
            }
            if (by2 == 20) {
                n2.j = (short)(n3 + 66 - 1);
                n2.A = this.ae();
                switch (n2.j) {
                    case 66: {
                        n2.e = bm.A(961) + n2.A;
                        break;
                    }
                    case 67: {
                        n2.e = bm.A(962) + n2.A;
                        break;
                    }
                    case 68: {
                        n2.e = bm.A(963) + n2.A;
                        break;
                    }
                    case 69: {
                        n2.e = bm.A(964) + n2.A;
                        break;
                    }
                    case 70: {
                        n2.e = bm.A(965) + n2.A;
                    }
                }
            } else if (by2 == 21) {
                n2.j = (short)65;
                n2.A = n3;
                n2.e = bm.A(966) + n3;
            }
            n2.g = (byte)3;
            n2.p = 1;
            n3 = this.ae();
            n2.a = new int[1][n3];
            for (int i3 = 0; i3 < n3; ++i3) {
                cc cc2 = this;
                n2.a[0][i3] = cc2.ai();
            }
            cn2.a(n2, (int)by2);
        }
    }

    private void aN() {
        this.aj();
        bm.aS = this.am();
        bm.H();
        bm.aa();
        bm.X();
        bm.a(5, true);
        bm.X = 3;
    }

    private void aO() {
        bm.ae();
        cn.k();
        cn.G = 2;
        cn.I = this.aj();
        int n2 = this.ae();
        for (int i2 = 0; i2 < n2; ++i2) {
            q q2 = new q(this.j, this.k);
            n n3 = n.a(q2);
            this.k += q2.h();
            n3.c = (byte)i2;
            if (bm.aO == null) continue;
            bm.aO.a(n3, (int)n3.c);
        }
    }

    private void aP() {
        for (int i2 = 0; i2 < ab.c.length; ++i2) {
            for (int i3 = 0; i3 < ab.c[i2].length; ++i3) {
                ab.c[i2][i3] = this.aj();
            }
        }
        bm.M();
    }

    private void aQ() {
        int n2;
        byte by2 = this.ae();
        if (by2 == 2) {
            cn.K = this.aj();
        }
        byte by3 = this.ae();
        cn cn2 = cn.a(by2, true, (int)by3);
        int n3 = this.ae();
        if (by2 == 5) {
            for (int i2 = 0; i2 < n3; ++i2) {
                q q2 = new q(this.j, this.k);
                System.out.println(q2);
                n n4 = n.a(q2);
                this.k += q2.h();
                bm.cb.ao[n4.c] = n4;
            }
            return;
        }
        for (n2 = 0; n2 < n3; ++n2) {
            q q3 = new q(this.j, this.k);
            n n5 = n.a(q3);
            this.k += q3.h();
            if (cn2 == null) continue;
            cn2.a(n5, (int)n5.c);
        }
        if (by2 == 1) {
            bm.aB.m();
        }
        if (by2 == 0) {
            n2 = bm.aA.e(512);
            int n6 = bm.aA.e(1024);
            if ((n2 <= 3 || n6 <= 3) && bm.cb.U > 5) {
                bm.p(2);
                k.a(85, 61, 180, 1, 5000, bm.B(376));
            }
            if (bm.aA.a() <= 3) {
                bm.p(21);
                k.a(85, 61, 180, 1, 5000, bm.B(380));
                bm.ba[2] = bm.m();
            }
        }
        bm.r(by2);
        if (cn2 == bm.aE) {
            bm.a(6, true);
            if (bm.ac()) {
                bm.ae();
            }
        }
    }

    private void aR() {
        int n2 = this.ae();
        if (n2 == 0) {
            n2 = this.ae();
            boolean bl2 = n2 >> 4 == 1;
            if (bl2) {
                bm.cf = null;
            }
            cn.c = (n2 & 0xF) == 1;
            int n3 = this.ah();
            byte by2 = this.ae();
            cn.f = this.ah();
            String string = this.an();
            String string2 = this.an();
            cn.e.removeAllElements();
            byte by3 = this.ae();
            cn.O = by3;
            cn.L = new String[by3];
            int[] nArray = new int[by3];
            cn.M = nArray;
            for (n2 = 0; n2 < by3; ++n2) {
                String string3;
                cn.L[n2] = string3 = this.an();
                cn.e.addElement(null);
                nArray[n2] = n2;
            }
            cn.M = nArray;
            cn.b = by2;
            bm.a(2, true);
            bm.aM = n3;
            bm.aL = string2;
            bm.aK = string;
            return;
        }
        if (bm.ac()) {
            bm.ae();
        }
        if (bm.n(47) && 2 == bm.aJ) {
            n2 = this.ae();
            cn.P = this.an();
            cn.Q = this.aj();
            int n4 = this.ae();
            cn cn2 = (cn)cn.e.elementAt(n2);
            if (cn2 == null) {
                cn.N = n2;
                byte by4 = this.ae();
                cn2 = new cn(0, by4, 1);
                cn2.a(2, 14, 120, 90);
                cn2.r = n4 == 1;
                for (n4 = 0; n4 < by4; ++n4) {
                    Object object = new q(this.j, this.k);
                    n n5 = n.a((q)object);
                    this.k += ((q)object).h();
                    n5.m = this.aj();
                    object = this;
                    short s2 = ((cc)object).ai();
                    n5.t = this.an();
                    if (s2 != -1) {
                        n5.p = s2;
                    }
                    n5.c = (byte)n4;
                    cn2.a(n5, (int)n5.c);
                }
                if (n2 < cn.e.size() && n2 >= 0) {
                    cn.e.setElementAt(cn2, n2);
                }
                bm.aP = cn2;
                if (bm.aR != null) {
                    bm.aR = bm.aP;
                    bm.aR.h();
                }
            }
        }
    }

    private void aS() {
        int n2 = this.aj();
        int n3 = this.aj();
        byte by2 = this.ae();
        if (n3 == 0) {
            w.a();
        }
        if (w.b == null) {
            w.b = new bk[n2][];
        }
        if (w.b[n3] == null) {
            w.b[n3] = new bk[by2];
        }
        w.c = n2;
        for (n2 = 0; n2 < by2; ++n2) {
            int n4 = this.aj();
            String string = this.am();
            w.b[n3][n2] = new bk();
            w.b[n3][n2].a = n4;
            w.b[n3][n2].b = string;
        }
        bm.ae();
    }

    private void aT() {
        cr.D();
        int n2 = w.g;
        int n3 = this.ae();
        for (int i2 = 0; i2 < n3; ++i2) {
            int n4 = this.aj();
            String string = this.an();
            byte by2 = this.ae();
            int n5 = this.ag();
            int n6 = n5 & 0x7F;
            n5 >>= 7;
            byte by3 = this.ae();
            byte by4 = this.ae();
            int n7 = this.aj();
            cr cr2 = bh.a(bm.br, n4);
            if (cr2 == null) {
                cr2 = new cr();
                bm.br.addElement(cr2);
            }
            cr2.a(string, n4, (byte)n6, (byte)0);
            cr2.ai = (byte)n5;
            cr2.U = by3;
            cr2.ac = by4;
            cr2.aa = by2;
            cr2.ad = n7;
            if (cr2.C == bm.cb.C) {
                bm.cb.aa = by2;
            }
            cr.a(n4, n2, by2);
        }
        cr cr3 = bh.a(bm.br, w.h);
        if (cr3 != null) {
            w.j = cr3.E;
        }
    }

    private void aU() {
        int n2 = this.ae();
        for (int i2 = 0; i2 < n2; ++i2) {
            int n3 = this.ah();
            int n4 = this.ag();
            i i3 = i.a(n3);
            if (i3 == null) continue;
            i3.n = n4;
        }
    }

    public final void b(int n2) {
        short s2 = 3005;
        cc cc2 = this;
        cc2.c(0, s2);
        this.Y(bm.em);
        this.b((short)bm.en);
        this.Y(bm.ee);
        this.Y(n2);
        this.ca();
    }

    private void aV() {
        bm.em = this.aj();
        bm.eg = this.an();
        bm.ef = this.an();
        cc cc2 = this;
        bm.en = cc2.ai();
        cc2 = this;
        bm.ei = cc2.ai();
        bm.ej = this.ae();
        bm.eh = this.an();
        cc2 = this;
        bm.ec = cc2.ai();
        cc2 = this;
        bm.ed = cc2.ai();
        cc2 = this;
        bm.ee = cc2.ai();
        int n2 = this.ae();
        bm.ek = new int[n2];
        bm.el = new String[n2];
        for (int i2 = 0; i2 < n2; ++i2) {
            int n3 = bm.ek[i2] = this.aj();
            bm.el[i2] = bm.ek[i2] >= 428 ? i2 + 1 + "." + this.an() : i2 + 1 + "." + bm.d(0, n3);
        }
        bm.eo = true;
    }

    private void aW() {
        byte by2 = ((cc)((Object)string)).ae();
        int n2 = ((cc)((Object)string)).aj();
        String string = ((cc)((Object)string)).m(0, n2);
        if (by2 == 1) {
            bm.a((String)string + bm.d(0, 88), true);
            return;
        }
        if (by2 == 2) {
            bm.a(bm.A(1008) + (String)string, true);
            return;
        }
        if (by2 == 5) {
            bm.a((String)string + bm.A(1009), true);
            return;
        }
        if (by2 == 6) {
            bm.a(bm.A(1010), true);
        }
    }

    private void aX() {
        int n2 = this.aj();
        byte by2 = this.ae();
        cr cr2 = bh.e(n2);
        if (cr2 != null) {
            cr2.aj = by2;
        }
        if ((cr2 = bh.a(bm.bq, n2)) != null) {
            cr2.aj = by2;
            bm.X = 3;
        }
    }

    private void aY() {
        int n2 = this.aj();
        this.n = this.aj();
        String string = this.m(0, this.n);
        bm.as = n2;
        if (bm.cb.m()) {
            bm.bn.a(n2, (byte)3);
            return;
        }
        if (ag.j[8] == 0) {
            bm.bn.a(n2, (byte)1);
            return;
        }
        if (ag.j[8] == 1) {
            bm.c(bm.A(1011));
            bm.bn.a(n2, (byte)0);
            return;
        }
        if (ag.j[8] == 2) {
            if (bm.Y()) {
                this.aa(n2);
                return;
            }
            bm.a(2, bm.B(162), "^b" + string + "^b" + bm.B(152), 2, 30000L);
        }
    }

    private void aZ() {
        int n2 = this.aj();
        if (n2 == bm.cb.C) {
            cr.C();
            bm.a(bm.A(1013) + bm.d(0, 156), true);
            return;
        }
        for (int i2 = 0; i2 < bm.bq.size(); ++i2) {
            cr cr2 = (cr)bm.bq.elementAt(i2);
            if (cr2 == null || cr2.C != n2) continue;
            cr.j(n2, 2);
            bm.bq.removeElementAt(i2);
            bm.a(cr2.E + bm.d(0, 156), true);
        }
    }

    private void ba() {
        int n2 = this.ae();
        for (int i2 = 0; i2 < n2; ++i2) {
            cc cc2 = this;
            cc2.d(false);
        }
    }

    private void bb() {
        cr cr2 = bm.cb;
        n.O = "";
        int n2 = 0;
        int n3 = this.ag();
        for (int i2 = 0; i2 < n3; ++i2) {
            int n4 = this.ag();
            int n5 = this.aj();
            int n6 = cr2.r[n4];
            if ((n6 = n5 - n6) != 0) {
                ++n2;
                char c2 = n6 > 0 ? (char)'\u2191' : '\u2193';
                String string = null;
                String string2 = "";
                if (n4 == 7 || n4 == 9 || n4 == 10 || n4 == 11 || n4 == 14) {
                    string = bn.i[n4] + bm.A(1030);
                    string2 = string2 + Math.abs(n6) / 100 + "." + Math.abs(n6) % 100 + "%";
                } else if (n4 == 13) {
                    string = bn.i[n4] + bm.A(1029);
                    string2 = string2 + Math.abs(n6) / 100 + "." + Math.abs(n6) % 100 + "%";
                } else {
                    string = bn.i[n4] + ":";
                    string2 = "" + Math.abs(n6);
                }
                if (n6 > 0) {
                    n.a("^g" + string + c2 + string2 + "^g");
                } else {
                    n.a("^r" + string + c2 + string2 + "^r");
                }
            }
            cr2.r[n4] = n5;
        }
        cn.k();
        if (n2 > 0 && bm.n(47)) {
            cn.a(true, null, null, true);
        }
        String cfr_ignored_0 = "CMD_SC_PROPERTIES_UPDATE propertyCount=" + n3;
    }

    private void bc() {
        byte by2;
        int n2;
        int n3 = this.ae();
        int[] nArray = new int[n3];
        int[] nArray2 = new int[n3];
        for (n2 = 0; n2 < n3; ++n2) {
            int n4 = this.aj();
            by2 = this.ae();
            nArray[n2] = n4;
            nArray2[n2] = by2;
            r r2 = bh.d(n4);
            if (r2 == null) continue;
            if (r2.q != 0 && by2 == 0) {
                bm.dl = true;
                bm.dm = true;
                if (bm.aq != 0) {
                    bm.di = true;
                }
            }
            r2.q = by2;
            String cfr_ignored_0 = "Npc " + r2.E + " state=" + r2.q;
        }
        for (n2 = 0; n2 < bh.z.size(); ++n2) {
            int n5;
            r r3 = (r)bh.z.elementAt(n2);
            if (r3 == null) continue;
            by2 = 0;
            for (n5 = 0; n5 < nArray.length; ++n5) {
                if (r3.C != nArray[n5]) continue;
                by2 = 1;
                break;
            }
            if (by2 == 0) {
                r3.q = 5;
            }
            n5 = t.a.size();
            for (int i2 = 0; i2 < n5; ++i2) {
                Object object = (t)t.a.elementAt(i2);
                if (object == null || ((t)object).f != r3.D || ((t)object).v.size() <= 0 || (object = (cd)((t)object).v.elementAt(0)) == null) continue;
                ((cd)object).d = r3.F;
                ((cd)object).e = r3.k;
                ((cd)object).f = r3.l;
            }
        }
    }

    private void bd() {
        bm.dm = true;
        bm.di = true;
        q q2 = new q(((cc)((Object)r2)).j, ((cc)((Object)r2)).k);
        t t2 = new t();
        t2.a(q2);
        t2.a(((cc)((Object)r2)).l == 615);
        t.a.addElement(t2);
        if (((cc)((Object)r2)).l == 615) {
            bm.dF = t2.m;
            bm.l(68);
            if (cr.aV != null && cr.aV.e()) {
                r r2 = (r)cr.aV;
                if (r2 != null) {
                    if (bm.ap > 1) {
                        bm.cn = t2.v.size();
                        bm.dG = true;
                        if (t2.v.size() > 1) {
                            --bm.cn;
                        }
                    } else {
                        bm.cn = 0;
                        bm.dG = false;
                    }
                }
            } else {
                bm.cn = 0;
                bm.dG = false;
            }
        }
        if (t2.g()) {
            bm.ag();
            bm.aq = t2.e;
            if (bm.aq == -1) {
                bm.az = true;
                for (int i2 = 0; i2 < 4; ++i2) {
                    bm.aw[i2] = false;
                }
                bm.ay = 0;
                bm.ax = false;
                bm.F();
            }
        }
    }

    private void be() {
        int n2 = this.ae();
        f.f.i = n2;
        int n3 = n2;
        t t2 = f.f;
        if (n3 == 1 || n3 == 4 || n3 == 0) {
            String string;
            if (n3 == 1) {
                f.g = this.ae();
                if (f.g == 0) {
                    f.h = this.aj();
                }
            } else {
                f.g = 1;
                f.h = -1;
            }
            t2.h = (byte)this.ag();
            t2.e = this.ah();
            n3 = this.ah();
            if (t2.h < 5 && t2.h >= 0) {
                string = bg.a(this.m(0, n3), bm.A(783), bn.a[t2.h]);
                t2.m = bg.c(string, "$");
            } else {
                string = this.m(0, n3);
                t2.m = bg.c(string, "$");
            }
            t2.j = this.ah();
            t2.k = this.ah();
            t2.n = this.aj();
            t2.s = this.aj();
            t2.o = this.aj();
            t2.p = this.aj();
            t2.q = this.aj();
            t2.r = this.aj();
            int n4 = this.ae();
            t2.w.removeAllElements();
            for (int i2 = 0; i2 < n4; ++i2) {
                n3 = this.ae();
                int n5 = this.ah();
                String string2 = "@" + n3 + "@" + n5;
                n3 = this.ae();
                String string3 = this.m(0, n3);
                cc cc2 = this;
                short s2 = cc2.ai();
                string3 = string3 + " x " + s2 + string2;
                t2.w.addElement(string3);
            }
        } else if (n3 == 2 || n3 == 3) {
            t2.h = (byte)this.ag();
            t2.e = this.ah();
            n3 = this.ah();
            t2.m = t2.h < bn.a.length && t2.h >= 0 ? bg.a(this.m(0, n3), bm.A(783), bn.a[t2.h]) : this.m(0, n3);
        }
        bm.ae();
        bm.l(57);
        f.a(12);
        ay.a();
    }

    /*
     * Unable to fully structure code
     */
    private void bf() {
        if (bm.ac()) {
            bm.ae();
        }
        bm.H();
        if (!bm.n(7) || bm.aq == 1) {
            if (bm.cf != null) {
                bm.aj = bm.cf.E;
            }
            bm.ai = bm.A(1033);
            bm.m(1);
        }
        var1_2 = this.ae();
        var2_4 = bm.A(1034);
        if (var1_2 == 20) {
            var2_4 = this.an();
        }
        var3_6 = this.ah();
        var3_7 = this.m(0, var3_6);
        bm.ak = bg.c(var3_7, "$");
        bm.ap = 0;
        var4_13 = new Vector<String[]>();
        var5_14 = this.ae();
        for (var6_15 = 0; var6_15 < var5_14; ++var6_15) {
            var7_16 = new b();
            var3_8 = this;
            new b().a = var3_8.ai();
            var8_17 = this.ae();
            var9_19 = this.ae();
            var7_16.b = (var8_17 << 8) + (255 - var9_19);
            var3_9 = this.ae();
            if (var3_9 >= 0) {
                var7_16.d = this.m(0, var3_9);
            }
            var4_13.addElement(var7_16);
            if (var8_17 != 1) continue;
            ++bm.ap;
        }
        bm.ae = this.aj();
        var6_15 = this.ae();
        var7_16 = new String[var6_15];
        var8_18 = new String[var6_15];
        var9_20 = new int[var6_15];
        for (var10_21 = 0; var10_21 < var6_15; ++var10_21) {
            var3_10 = this.ae();
            if (var3_10 >= 0) {
                var7_16[var10_21] = this.m(0, var3_10);
            }
            var8_18[var10_21] = this.ao();
            var3_11 = this;
            var9_20[var10_21] = var3_11.ai();
        }
        var10_21 = var6_15 + var5_14;
        if (var1_2 == 0) ** GOTO lbl-1000
        if (var1_2 == 1) {
            bm.j(var10_21 + 3);
            bm.a(bm.d(0, 251), 3013, 0, var10_21);
            bm.a(bm.d(0, 252), 3000, 0, var10_21 + 1);
        } else if (var1_2 == 2) {
            bm.j(var10_21 + 2);
            bm.a(bm.d(0, 254), 3001, 0, var10_21);
        } else if (var1_2 == 3) {
            bm.j(var10_21 + 2);
            bm.a(bm.d(0, 253), 3002, 0, var10_21);
        } else if (var1_2 == 4) {
            bm.j(var10_21 + 4);
            bm.a(bm.d(0, 251), 3013, 0, var10_21);
            bm.a(bm.d(0, 252), 3000, 0, var10_21 + 1);
            bm.a(bm.d(0, 253), 3002, 0, var10_21 + 2);
        } else if (var1_2 == 6) {
            bm.j(var10_21 + 2);
            bm.a(bm.A(1035), 3005, 0, var10_21);
        } else if (var1_2 == 5) {
            bm.j(var10_21 + 2);
            bm.a(bm.d(0, 250), 3004, 0, var10_21);
        } else if (var1_2 == 8) {
            bm.j(var10_21 + 2);
            bm.a(bm.A(1036), 3000, 0, var10_21);
        } else if (var1_2 == 9) {
            bm.j(var10_21 + 4);
            bm.a(bm.d(0, 251), 3013, 0, var10_21);
            bm.a(bm.d(0, 252), 3000, 0, var10_21 + 1);
            bm.a(bm.A(1037), 3010, 0, var10_21 + 2);
        } else if (var1_2 == 10) {
            bm.j(var10_21 + 2);
            bm.a(bm.A(1038), 3000, 0, var10_21);
        } else if (var1_2 == 13) {
            bm.j(var10_21 + 2);
            bm.a(bm.A(1039), 3011, 0, var10_21);
        } else if (var1_2 == 14) {
            bm.j(var10_21 + 2);
            bm.a(bm.A(1040), 3012, 0, var10_21);
        } else if (var1_2 == 15) {
            bm.j(var10_21 + 2);
            bm.a(bm.A(1041), 3000, 0, var10_21);
        } else if (var1_2 == 17) {
            bm.j(var10_21 + 2);
            bm.a(bm.A(1042), 3000, 0, var10_21);
        } else if (var1_2 == 20) {
            bm.j(var10_21 + 2);
            bm.a(var2_4, 3000, 0, var10_21);
        } else lbl-1000:
        // 2 sources

        {
            bm.j(var10_21 + 1);
        }
        if (var4_13.size() > 1) {
            bg.a(var4_13, 0, var4_13.size() - 1, 6);
        }
        for (var0_1 = 0; var0_1 < var4_13.size(); ++var0_1) {
            var1_3 = (b)var4_13.elementAt(var0_1);
            if (var1_3 == null) continue;
            var2_5 = (byte)(var1_3.b >> 8);
            var3_12 = (byte)(255 - (byte)var1_3.b);
            bm.a(var1_3.d, var1_3.a, var2_5, var3_12, var0_1);
        }
        for (var0_1 = 0; var0_1 < var6_15; ++var0_1) {
            bm.a(var7_16[var0_1], var8_18[var0_1], 3009, var9_20[var0_1], var0_1 + var5_14);
        }
        bm.a(bm.k(0), bm.al);
    }

    private void bg() {
        int n2 = this.ae();
        if ((n2 & 2) == 2) {
            if ((n2 & 1) != 1) {
                n2 = this.ae();
                t.b.removeAllElements();
                for (int i2 = 0; i2 < n2; ++i2) {
                    b b2 = new b();
                    cc cc2 = this;
                    new b().a = cc2.ai();
                    b2.b = this.aj();
                    b2.c = this.ae();
                    byte by2 = this.ae();
                    b2.d = this.m(0, by2);
                    t.b.addElement(b2);
                }
                if (t.b.size() > 0) {
                    t.a();
                    bm.j(t.b.size(), 10);
                }
                f.a(9);
                f.a = 0;
                return;
            }
            if ((n2 & 1) == 1) {
                cc cc3 = this;
                t.c.a = cc3.ai();
                t.c.b = this.aj();
                t.c.c = this.ae();
                n2 = this.ae();
                t.c.d = this.m(0, n2);
                byte by3 = this.ae();
                t.c.e = this.m(0, by3);
                byte by4 = this.ae();
                t.c.f = this.m(0, by4);
                cc3 = this;
                t.c.g = cc3.ai();
                cc3 = this;
                t.c.h = cc3.ai();
                cc3 = this;
                t.c.i = cc3.ai();
                t.c.j = this.aj();
                t.c.k = this.aj();
                t.c.l = this.aj();
                t.c.m = this.aj();
                t.c.n = this.aj();
                t.c.o = this.aj();
                f.a(10);
            }
        }
    }

    private void bh() {
        int n2;
        int n3;
        bm.ae();
        int n4 = 0;
        cw.a = true;
        bm.cg = this.aj();
        n4 = this.ae();
        if (n4 >= 40) {
            bg.a("TooManySprInzone " + n4);
            n4 = 39;
        }
        int[] nArray = new int[40];
        for (n3 = 0; n3 < 40; ++n3) {
            nArray[n3] = -1;
        }
        for (n3 = 0; n3 < n4; ++n3) {
            cc cc2 = this;
            short by2 = cc2.ai();
            nArray[n3] = by2;
        }
        bm.cb.F = bm.cj = this.aj();
        bm.ck = this.aj();
        bm.cl = this.aj();
        n3 = this.ae();
        int n5 = this.ag();
        if (bm.ch != n3) {
            cw.a = false;
        }
        if (bm.ci != n5) {
            cw.a = false;
        }
        bm.bs = nArray;
        bm.ch = n3;
        bm.ci = n5;
        bm.q();
        bm.cJ = this.aj();
        bm.cb.ak = this.ae();
        bm.cI = this.an();
        t.d.removeAllElements();
        n4 = this.ae();
        for (n2 = 0; n2 < n4; ++n2) {
            n3 = this.ag();
            byte by2 = (byte)(n3 & 0x7F);
            boolean bl2 = n3 >> 7 == 0;
            int n6 = this.aj();
            n3 = this.aj();
            int n7 = this.aj();
            String string = this.an();
            String string2 = this.an();
            if (n6 == 0 && !string2.equals("")) {
                int n8 = string2.indexOf("\u3010");
                int n9 = string2.indexOf("\u3011");
                if (n8 >= 0 && n9 >= 0 && n9 > n8) {
                    string = string2.substring(n8 + 1, n9);
                }
            }
            bm.a(by2, n3, n7, n6, string, string2, (byte)0, bl2);
            new t();
            if (!bl2) continue;
            t.a(n6, string, n3, n7);
        }
        byte by3 = this.ae();
        n2 = by3;
        if (by3 == 1) {
            n3 = this.aj();
            int n10 = this.aj();
            int n11 = this.aj();
            String string = this.an();
            bm.a((byte)0, n10, n11, n3, string, "", (byte)1, true);
            new t();
            t.a(n3, string, n10, n11);
        }
        if (bm.ck < 10000) {
            bm.O();
        } else if (bm.ck > 10000 && bm.ck < 20000) {
            bm.cp = true;
        }
        bm.y(11);
        String cfr_ignored_0 = bm.cb.ak + "change map z=" + bm.cj + " M=" + bm.ck + " R=" + bm.ci;
    }

    private void bi() {
        int n2 = this.ae();
        if (n2 == 1) {
            bm.s();
            cc cc2 = this;
            n2 = cc2.ai();
            cc2 = this;
            short s2 = cc2.ai();
            cc2 = this;
            short s3 = cc2.ai();
            byte by2 = this.ae();
            bm.b(n2, (int)s2, (int)s3);
            for (n2 = 0; n2 < by2; ++n2) {
                cc cc3 = this;
                s2 = cc3.ai();
                bm.aa.addElement(new Integer(s2));
            }
            bm.ab = true;
            bm.u();
        } else if (n2 == 0) {
            bm.s();
            bm.H();
            cc cc4 = this;
            n2 = cc4.ai();
            cc4 = this;
            short s4 = cc4.ai();
            cr.E();
            bm.a(bm.cj, n2, (int)s4);
        }
        cr.aE = 1;
    }

    private void bj() {
        am.a(18);
        bm.cm = (byte)bn.a.length;
        bm.w = new byte[bm.cm];
        for (byte by2 = 0; by2 < bm.cm; by2 = (byte)(by2 + 1)) {
            bm.w[by2] = (byte)this.aj();
        }
        am.m = 0;
        am.a(19);
        am.a = false;
        bm.ae();
    }

    private void bk() {
        int n2;
        bm.K();
        int n3 = this.ae();
        this.aj();
        String cfr_ignored_0 = bm.A(1046) + n3;
        for (n2 = 0; n2 < 3; ++n2) {
            am.g[n2].C = -1;
            am.g[n2].E = null;
        }
        n2 = -1;
        for (int i2 = 0; i2 < n3; ++i2) {
            int n4 = this.ae();
            am.h[i2] = i2;
            String string = this.n(0, 33);
            byte by2 = this.ae();
            this.X(1);
            int n5 = this.aj();
            int n6 = this.aj();
            int n7 = this.aj();
            int n8 = this.aj();
            int n9 = this.aj();
            cr cr2 = new cr();
            new cr().B = true;
            cr2.a(string, n5, (byte)n6, (byte)n8);
            cr2.U = (byte)n7;
            cr2.t = (byte)n9;
            cr2.ai = by2;
            this.a(cr2, true);
            cr2.q = this.an();
            if (am.g[n4].E != null) {
                for (n4 = 0; n4 < 3; ++n4) {
                    if (am.g[n4].E != null) continue;
                    cr2.s = (byte)n4;
                }
            } else {
                cr2.s = (byte)n4;
            }
            if (n5 == cs.a[1]) {
                n2 = cr2.s;
            }
            am.g[cr2.s] = cr2;
            String cfr_ignored_1 = bm.A(1047) + cr2.E;
        }
        if (bm.r && n2 >= 0) {
            am.a(am.g[n2], -1);
            bm.r = false;
            return;
        }
        if (bm.r) {
            bm.r = false;
        }
        if (bm.I() == 22 || bm.I() == 11) {
            bm.r();
            bm.az();
        }
        bm.y(10);
        am.a(0);
        am.j = 0;
        am.b = true;
        bm.s();
        bm.ae();
    }

    private void bl() {
        this.o = this.ae();
        if (1 == this.o) {
            byte by2 = this.ae();
            String string = this.n(0, 33);
            byte by3 = this.ae();
            this.X(1);
            int n2 = this.aj();
            int n3 = this.aj();
            int n4 = this.aj();
            int n5 = this.aj();
            int n6 = this.aj();
            cr cr2 = new cr();
            new cr().B = true;
            cr2.a(string, n2, (byte)n3, (byte)n5);
            this.a(cr2, true);
            cr2.U = (byte)n4;
            cr2.t = (byte)n6;
            cr2.ai = by3;
            if (am.g[by2].E != null) {
                for (int i2 = 0; i2 < 3; ++i2) {
                    if (am.g[i2].E != null) continue;
                    cr2.s = (byte)i2;
                }
            } else {
                cr2.s = by2;
            }
            am.g[cr2.s] = cr2;
            am.a(cr2, -1);
            return;
        }
        am.a(5);
        am.d();
        if (3 == this.o) {
            bm.d(bm.B(40));
        } else if (9 == this.o) {
            bm.d(bm.A(907));
        }
        String cfr_ignored_0 = "del or creat char fail" + this.o;
        bm.ae();
    }

    private void bm() {
        bm.bt.removeAllElements();
        bm.bu.removeAllElements();
        int n2 = this.ag();
        if (n2 == 2) {
            bm.d(bm.A(1053));
        } else if (n2 == 3) {
            bm.d(bm.A(1054));
        } else if (n2 == 6) {
            n2 = this.aj();
            bm.d(bm.A(1055) + n2);
        } else if (n2 == 7) {
            bm.d(bm.A(1056));
        } else if (n2 == 15) {
            bm.d(bm.A(1057));
        } else if (n2 != 10) {
            if (n2 == 19) {
                this.aj();
                n2 = 0;
                this.ag();
                String string = this.an();
                if (string.length() > 0) {
                    bm.d(string);
                } else {
                    bm.d(bm.A(1058));
                }
            } else if (n2 == 16) {
                bc.a((byte)3, bm.A(1059), bm.A(1059));
            } else if (n2 == 17) {
                bm.d(bm.A(1060));
            } else if (n2 == 18) {
                bm.d(bm.A(1061));
            } else if (n2 == 20) {
                bm.d(bm.A(1062));
            } else if (n2 == 1) {
                int n3;
                bm.P = n2 = this.aj();
                bm.M = this.ag();
                int n4 = this.aj();
                bm.N = this.m(0, n4);
                String cfr_ignored_0 = "LoginServerId=" + bm.M + " LoginKey=" + bm.N;
                n4 = this.ag();
                if (n4 == 0) {
                    bm.ae();
                    bm.d(bm.A(1063));
                    return;
                }
                for (n3 = 0; n3 < n4; ++n3) {
                    ca ca2 = new ca();
                    new ca().g = this.aj();
                    ca2.a = this.an();
                    ca2.b = this.an();
                    ca2.c = this.ah();
                    ca2.d = this.an();
                    ca2.e = this.ah();
                    ca2.f = this.ae();
                    boolean bl2 = false;
                    ca2.h = false;
                    if (ca2.g == am.o) {
                        ca ca3 = new ca();
                        new ca().g = ca2.g;
                        ca3.a = bm.A(1064) + ca2.a;
                        ca3.b = ca2.b;
                        ca3.c = ca2.c;
                        ca3.d = ca2.d;
                        ca3.e = ca2.e;
                        ca3.f = ca2.f;
                        bm.bt.insertElementAt(null, 0);
                        bm.bt.insertElementAt(ca3, 0);
                    }
                    bm.bt.addElement(ca2);
                }
                if (bm.bn != null) {
                    bm.bn.J();
                }
                bm.bn = null;
                bh.K = new cy(bh.n(21), bh.o(21));
                new cy(bh.n(21), bh.o(21)).e = true;
                am.k = null;
                am.a(4);
                am.i = n2;
                am.a = false;
                bm.O = 0;
                if (bm.s) {
                    am.a(false);
                    am.a(am.p);
                    am.p = null;
                    bm.cb = null;
                    bm.s = false;
                    return;
                }
                if (bm.r) {
                    am.l = 0;
                    ca ca4 = am.b(am.o);
                    if (ca4 != null && !ca4.a()) {
                        am.a(false);
                        am.a(ca4);
                        return;
                    }
                    bm.y(10);
                    am.a(13);
                } else if (bm.r) {
                    am.c();
                } else {
                    am.l = 0;
                    for (n3 = 0; n3 < bm.bt.size(); ++n3) {
                        ca ca5 = (ca)bm.bt.elementAt(n3);
                        if (ca5 == null || ca5.g != am.o) continue;
                        am.l = n3;
                        break;
                    }
                    bm.y(10);
                    am.a(13);
                }
            } else {
                bm.d(bm.A(1065));
            }
        }
        bm.ae();
        this.J();
    }

    private void bn() {
        byte by2 = this.ae();
        int n2 = this.ah();
        byte by3 = this.ae();
        byte by4 = this.ae();
        if (by2 == 5) {
            bm.cb.ao[by3] = null;
        }
        cn cn2 = cn.a(by2, false, 0);
        n n3 = bm.aA.c(n2);
        if (cn2 != null) {
            cn2.b(by3, by4);
        }
        if (bm.ac()) {
            bm.ae();
            bm.aT = 0;
        }
        if (cn2 == bm.aA && n3 != null) {
            if (n3.h == 512 || n3.h == 1024) {
                bm.s(by2);
            }
            if (bm.aA.a() > 3) {
                bm.q(21);
            }
        }
        cn.k();
    }

    private void bo() {
        byte by2 = this.ae();
        cn cn2 = cn.a(by2, false, 0);
        cc cc2 = this;
        short s2 = cc2.ai();
        int n2 = this.ae();
        n n3 = cn2.b(n2);
        if (n3 == null) {
            String cfr_ignored_0 = "updating the attrib of goods has error  definition id " + s2 + bm.A(1083) + n2;
            return;
        }
        n2 = this.ae();
        for (int i2 = 0; i2 < n2; ++i2) {
            byte by3 = this.ae();
            i = 0;
            byte by4 = (byte)cc.a(by3, 2);
            by3 = (byte)cc.a(by3, 6);
            int n4 = this.aj();
            if (by3 < 15) {
                if (n3.a == null) {
                    n3.a = new int[4][];
                }
                if (n3.a[by4] == null) {
                    n3.a[by4] = new int[15];
                }
                n3.a[by4][by3] = n4;
                continue;
            }
            if (by3 == 15) {
                String cfr_ignored_1 = "update the item num to " + n4 + " itemName : " + n3.e + " itemPos : " + n3.c;
                n3.p = (short)n4;
                if (cn2 == bm.aA) {
                    ab.b(s2);
                    t.a(n3.d, bm.aA.d(n3.d));
                }
                bm.r(by2);
                continue;
            }
            if (by3 == 16) {
                n3.C = (short)n4;
                continue;
            }
            if (by3 == 17) {
                n3.D = (short)n4;
                if (by2 != 1) continue;
                bm.aB.m();
                continue;
            }
            if (by3 == 18) {
                n3.A = (byte)n4;
                String cfr_ignored_2 = n3.e + "strong =" + n3.A;
                continue;
            }
            if (by3 == 19) {
                n3.i = (byte)n4;
                String cfr_ignored_3 = n3.e + "bind =" + n3.i;
                continue;
            }
            if (by3 != 20) continue;
            n3.F = n4;
            String cfr_ignored_4 = n3.e + "free value of record" + n3.F;
        }
        cn.k();
    }

    private void bp() {
        cn.k();
        byte by2 = this.ae();
        byte by3 = this.ae();
        if (by2 == 0) {
            if (by3 == 1) {
                bm.d(bm.A(1084));
                return;
            }
            if (by3 == 2) {
                bm.d(bm.A(1085));
                return;
            }
            if (by3 == 0) {
                cc cc2 = this;
                cc2.ai();
                by2 = this.ae();
                by3 = this.ae();
                cc2 = this;
                short s2 = cc2.ai();
                byte by4 = this.ae();
                n n2 = bm.aB.b(by3);
                n n3 = bm.aA.b(by2);
                bm.aA.a((int)by2);
                if (s2 > 0) {
                    bm.aB.a((int)by3);
                }
                n3.c = by3;
                bm.aB.a(n3, (int)by3);
                if (s2 > 0 && n2 != null) {
                    n2.c = by4;
                    bm.aA.a(n2, (int)n2.c);
                }
            }
        } else if (by2 == 1) {
            if (by3 == 3) {
                bm.d(bm.A(898));
            } else if (by3 == 0) {
                Object object = this;
                ((cc)object).ai();
                by2 = this.ae();
                by3 = this.ae();
                object = bm.aB.b(by2);
                bm.aB.a((int)by2);
                ((n)object).c = by3;
                bm.aA.a((n)object, (int)by3);
            }
        }
        bm.aT = 0;
    }

    private void bq() {
        this.ae();
        cn.K = this.aj();
        cn.k();
    }

    private void br() {
        if (cn.b != 0 && cn.b != 1 && cn.b != 2) {
            cn.d = this.aj();
        }
    }

    private void bs() {
        byte by2 = this.ae();
        Object object = cn.a(by2, false, 0);
        cc cc2 = this;
        short s2 = cc2.ai();
        int n2 = this.ae();
        if ((object = ((cn)object).b(n2)) == null) {
            String cfr_ignored_0 = "updating goods extend attrib has error  definite id" + s2 + bm.A(1083) + n2;
            return;
        }
        s2 = this.ae();
        ((n)object).s = new int[s2];
        for (n2 = 0; n2 < s2; ++n2) {
            ((n)object).s[n2] = this.aj();
        }
    }

    private void bt() {
        long l2 = this.al();
        byte by2 = this.ae();
        boolean bl2 = false;
        for (int i2 = 0; i2 < bh.z.size(); ++i2) {
            r r2 = (r)bh.z.elementAt(i2);
            if (r2 == null || l2 != (long)r2.C) continue;
            r2.A = by2 == 1;
            bl2 = true;
            if (!r2.A || cr.aV != r2) break;
            cr.aV = null;
            break;
        }
        if (!bl2) {
            this.W((int)l2);
        }
    }

    private void bu() {
        int n2;
        cr.C();
        int n3 = this.ae();
        cc cc2 = this;
        cr.am = cc2.ai();
        cr.an = n2 = this.aj();
        for (int i2 = 0; i2 < n3; ++i2) {
            int n4 = this.aj();
            int n5 = this.ag();
            int n6 = n5 & 0x7F;
            n5 >>= 7;
            int n7 = this.ag();
            int n8 = this.ag();
            int n9 = this.ag();
            int n10 = this.ag();
            String string = this.an();
            cr cr2 = bh.a(bm.bq, n4);
            if (cr2 == null) {
                cr2 = new cr();
                bm.bq.addElement(cr2);
            }
            cr2.ai = (byte)n5;
            cr2.a(string, n4, (byte)n6, (byte)n7);
            cr2.t(2);
            cr.u(n4);
            cr2.r[4] = 100;
            cr2.r[5] = 100;
            cr2.H = (byte)n9;
            cr2.I = (byte)n10;
            cr2.U = (byte)n8;
            cr2.ac = 1;
            if (n4 != n2) continue;
            cr2.aj = (byte)2;
            if (n2 != bm.cb.C) continue;
            bm.cb.aj = (byte)2;
        }
        String cfr_ignored_0 = "ting infornation of team : number of person" + n3;
        bm.ae();
    }

    private void bv() {
        String string = ((cc)((Object)string)).am();
        if (bm.n(47)) {
            cn.a(string, 20);
        }
        bm.ae();
    }

    private void bw() {
        String string = this.am();
        cn.R = this.ae();
        if (bm.n(47)) {
            cn.a(string, 22);
        }
        bm.ae();
    }

    private void bx() {
        int n2;
        int n3;
        int n4;
        int n5;
        int n6;
        byte by2 = this.ae();
        cn.aa = n6 = this.ae();
        if (by2 == 0) {
            cn.ag = this.ae();
            bm.aK = this.am();
            cn.ad = this.am();
            cn.ae = this.am();
            bm.aG = new cn(2, 7, 0);
            bm.aG.z();
            bm.aG.e();
        }
        n6 = this.ae();
        for (n5 = 0; n5 < 7; ++n5) {
            cn.X[n5] = -1;
            cn.Y[n5] = -1;
            cn.Z[n5] = -1;
        }
        for (n5 = 0; n5 < n6; ++n5) {
            n4 = this.ae();
            n3 = this.aj();
            n2 = this.aj();
            cc cc2 = this;
            short s2 = cc2.ai();
            cc2 = this;
            short s3 = cc2.ai();
            cn.X[n4] = n3;
            cn.Y[n4] = n2;
            cn.Z[n4] = s2;
            if (s3 <= 0) continue;
            q q2 = new q(this.j, this.k);
            n n7 = n.a(q2);
            this.k += q2.h();
            n3 = this.ae();
            if (bm.aG.b(n4) != null) {
                cn.a(false, bm.aG, bm.aG.b(n4), bm.aG.b((int)n4).p);
            }
            cn.a(true, bm.aG, n7, n3, true, n4);
        }
        n5 = this.ae();
        cn.W = new int[n5];
        for (n4 = 0; n4 < n5; ++n4) {
            cn.W[n4] = this.aj();
        }
        if (by2 == 0) {
            bm.a(3, true);
        }
        n4 = this.ae();
        n3 = 0;
        boolean bl2 = cn.ab = n4 == 1;
        if (n4 == 1) {
            cn.aj[27] = null;
            cn.aj[28] = null;
            cn.ak = null;
            cn.al = null;
            byte by3 = this.ae();
            n3 = by3;
            if (by3 > 0) {
                cn.aj[27] = new int[n3];
                for (n2 = 0; n2 < n3; ++n2) {
                    cn.aj[27][n2] = this.aj();
                }
            }
            byte by4 = this.ae();
            n3 = by4;
            cn.ak = new int[by4][2];
            if (n3 > 0) {
                for (n2 = 0; n2 < n3; ++n2) {
                    cn.ak[n2][0] = this.aj();
                    cn.ak[n2][1] = this.aj();
                }
            }
            byte by5 = this.ae();
            n3 = by5;
            if (by5 > 0) {
                cn.aj[28] = new int[n3];
                for (n2 = 0; n2 < n3; ++n2) {
                    cn.aj[28][n2] = this.aj();
                }
            }
            byte by6 = this.ae();
            n3 = by6;
            cn.al = new int[by6][2];
            if (n3 > 0) {
                for (n2 = 0; n2 < n3; ++n2) {
                    cn.al[n2][0] = this.aj();
                    cn.al[n2][1] = this.aj();
                }
            }
        }
        bm.aA.n();
        cn.ac = -1;
        bm.ae();
    }

    private void by() {
        byte by2 = this.ae();
        this.ae();
        byte by3 = this.ae();
        byte by4 = this.ae();
        byte by5 = this.ae();
        bm.aA.n();
        bm.L.b();
        switch (by2) {
            case 0: {
                cn.af = true;
                al.a(9, 0, 0);
            }
            case 1: {
                cn.a((int)by3, (int)by5, (int)by4);
            }
        }
        bm.X = 3;
        bm.ae();
    }

    private void bz() {
        byte by2 = this.ae();
        if (by2 == 1) {
            by2 = this.ae();
            byte by3 = this.ae();
            bm.ep = by3;
            bm.eq = by2;
            bm.er = this.ae() == 1;
            bm.J(0);
            return;
        }
        cn.S = this.ae() == 1;
        if (!cn.S) {
            bm.a(bm.A(1087), true);
        }
    }

    private void bA() {
        if (bm.aJ == 3) {
            if (bm.aA == null) {
                return;
            }
            cn.w = null;
            int n2 = ((cc)((Object)n6)).ae();
            if (n2 == 1) {
                bm.aA.i = bm.aA.h;
                bm.aA.h = new Hashtable();
                n2 = ((cc)((Object)n6)).ae();
                for (int i2 = 0; i2 < n2; ++i2) {
                    q q2 = new q(((cc)((Object)n6)).j, ((cc)((Object)n6)).k);
                    n n3 = n.a(q2);
                    ((cc)((Object)n6)).k += q2.h();
                    bm.aA.a(n3, (int)n3.c);
                }
                bm.aA.n();
                cn.ac = -1;
                return;
            }
            Hashtable<Integer, Object> hashtable = new Hashtable<Integer, Object>();
            int n4 = bm.aA.k;
            for (int i3 = 0; i3 < n4; ++i3) {
                n n5 = bm.aA.b(i3);
                if (n5 == null) continue;
                n n6 = new n();
                n6.a(n5);
                n6.c = n5.c;
                hashtable.put(new Integer(n6.c), n6);
            }
            bm.aA.i = bm.aA.h;
            bm.aA.h = hashtable;
            bm.aA.n();
            cn.ac = -1;
        }
    }

    private void bB() {
        if (bm.aJ == 3) {
            Object object;
            if (bm.aQ == null || bm.aP == null) {
                return;
            }
            int n2 = ((cc)object).ae();
            if (n2 == 1) {
                ((cc)object).aj();
                n2 = 0;
                int n3 = ((cc)object).ag();
                ((cc)object).ae();
                boolean bl2 = false;
                byte by2 = ((cc)object).ae();
                byte by3 = ((cc)object).ae();
                object = new q(((cc)object).j, ((cc)object).k);
                n n4 = n.a((q)object);
                if (n4 != null) {
                    n4.p = (byte)n3;
                    n4.c = by2;
                    n4.v = by3;
                    bm.aP.a((int)by2);
                    bm.aP.a(n4, (int)by2);
                    return;
                }
            } else {
                n2 = ((cc)object).aj();
                int n5 = ((cc)object).ag();
                byte by4 = ((cc)object).ae();
                byte by5 = ((cc)object).ae();
                byte by6 = ((cc)object).ae();
                object = bm.aQ.b(by4);
                n n6 = bm.aP.b(by5);
                cn.w = null;
                if (object != null && ((n)object).d == n2 && ((n)object).p >= n5) {
                    if (n6 == null) {
                        n n7 = new n();
                        n7.a((n)object);
                        n7.p = (byte)n5;
                        n7.c = by5;
                        n7.v = by6;
                        ((n)object).p = (short)(((n)object).p - (byte)n5);
                        bm.aP.a(n7, (int)by5);
                        if (((n)object).p == 0) {
                            bm.aQ.a((int)by4);
                            return;
                        }
                    } else if (n6.p + n5 <= n6.o) {
                        n6.p = (short)(n6.p + n5);
                        ((n)object).p = (short)(((n)object).p - n5);
                    }
                }
            }
        }
    }

    private void bC() {
        if (bm.aJ == 3) {
            if (bm.aQ == null || bm.aP == null) {
                return;
            }
            this.aj();
            int n2 = this.ag();
            byte by2 = this.ae();
            byte by3 = this.ae();
            n n3 = bm.aP.b(by2);
            if (n3 != null) {
                if (n3.p < n2) {
                    return;
                }
                n3.p = (short)(n3.p - n2);
                if (n3.p == 0) {
                    bm.aP.a((int)by2);
                }
                if (by3 != -1) {
                    bm.aQ.a(n3, n2, -1);
                }
            }
        }
    }

    private void bD() {
        if (bm.aJ == 3) {
            if (bm.aP == null) {
                return;
            }
            this.aj();
            byte by2 = this.ae();
            byte by3 = this.ae();
            n n2 = bm.aP.b(by2);
            if (n2 != null) {
                n2.v = by3;
            }
        }
    }

    private void bE() {
        byte by2 = ((cc)((Object)string)).ae();
        byte by3 = ((cc)((Object)string)).ae();
        int n2 = ((cc)((Object)string)).aj();
        String string = ((cc)((Object)string)).an();
        bm.a((int)by2, (int)by3, n2, string);
    }

    private void bF() {
        if (bm.n(47) && bm.aR != null) {
            int n2 = this.ae();
            if (n2 == 1) {
                n2 = this.ae();
                int[] nArray = new int[n2];
                for (int i2 = 0; i2 < n2; ++i2) {
                    nArray[i2] = this.aj();
                }
                cn.ap = false;
                bm.aR.a(nArray);
                return;
            }
            if (n2 == 2) {
                n2 = this.ae();
                String[] stringArray = new String[n2];
                int[] nArray = new int[n2];
                for (int i3 = 0; i3 < n2; ++i3) {
                    nArray[i3] = this.aj();
                    stringArray[i3] = this.am();
                }
                cn.am = nArray;
                cn.ao = stringArray;
                cn.ap = true;
                bm.aT = 1;
                return;
            }
            cn.ap = false;
            bm.aR.j();
        }
    }

    private void bG() {
        byte by2 = this.ae();
        switch (by2) {
            case 1: {
                bm.c(bm.A(1088));
                return;
            }
            case 2: {
                bm.c(bm.A(1088));
                return;
            }
            case 3: {
                bm.c(bm.A(1088));
                return;
            }
            case 4: {
                bm.c(bm.A(1088));
                return;
            }
            case 5: {
                bm.c(bm.A(1088));
                return;
            }
            case 6: {
                bm.c(bm.A(1088));
                return;
            }
            case 8: {
                bm.c(bm.A(1089));
                return;
            }
            case 9: {
                bm.c(bm.A(1090));
                return;
            }
            case 10: {
                bm.c(bm.A(1091));
                return;
            }
            case 11: {
                bm.c(bm.A(1092));
                return;
            }
            case 12: {
                bm.c(bm.A(1093));
                return;
            }
            case 13: {
                bm.c(bm.A(1094));
            }
        }
    }

    private void bH() {
        cc cc2 = this;
        cr.am = cc2.ai();
        cr.an = this.aj();
    }

    private void bI() {
        byte by2 = this.ae();
        int n2 = this.ae();
        if (by2 >= cn.aj.length) {
            return;
        }
        cn.aj[by2] = new int[n2];
        for (int i2 = 0; i2 < n2; ++i2) {
            cn.aj[by2][i2] = this.aj();
        }
    }

    private void bJ() {
        if (bm.bN == null) {
            bm.bN = new Hashtable();
        }
        bm.bN.clear();
        int n2 = this.ae();
        for (int i2 = 0; i2 < n2; ++i2) {
            cc cc2 = this;
            short s2 = cc2.ai();
            String string = this.an();
            bm.bN.put(new Integer(s2), string);
        }
    }

    private void bK() {
        byte by2 = this.ae();
        int n2 = this.aj();
        bm.C(n2);
        bm.cK = true;
        switch (by2) {
            case 1: 
            case 2: {
                bm.bb = 0;
                bm.bc = 0;
            }
        }
        bm.A(1095);
    }

    private void bL() {
        bm.cG = true;
        this.ae();
        bm.cO = this.ae();
        bm.cQ = this.an();
        bm.cR[0] = this.aj();
        bm.cR[1] = this.aj();
        bm.cS[0] = this.aj();
        bm.cS[1] = this.aj();
        int n2 = this.ae();
        bm.cT = new String[n2];
        bm.cU = new int[n2];
        for (int i2 = 0; i2 < n2; ++i2) {
            bm.cT[i2] = this.an();
            bm.cU[i2] = this.aj();
        }
        bm.cP.a(bm.cO, false);
        bm.H();
        bm.l(29);
        bm.cH = 0;
    }

    private void bM() {
        byte by2 = this.ae();
        if (bm.a(21, bm.B(162), bm.A(1096), 2)) {
            bm.cM = by2;
        }
    }

    private static void bN() {
        bm.A(1097);
    }

    private void bO() {
        int n2 = this.ae();
        for (int i2 = 1; i2 <= n2; ++i2) {
            if (i2 == bm.cb.ak) {
                bm.bb = this.ae();
                continue;
            }
            bm.bc = this.ae();
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void bP() {
        int n2 = ((cc)((Object)cr3)).ae();
        if (n2 == 1) {
            w.g = ((cc)((Object)cr3)).aj();
            w.h = ((cc)((Object)cr3)).aj();
            w.i = ((cc)((Object)cr3)).an();
            w.l = ((cc)((Object)cr3)).an();
            w.k = ((cc)((Object)cr3)).an();
            w.m = super.aj();
            w.n = super.ah();
            w.r = super.ah();
            w.s = super.ah();
            w.p = super.ae();
            w.o = super.ae();
            w.q = super.ae();
            cr cr2 = bh.a(bm.br, w.h);
            if (cr2 != null) {
                w.i = cr2.E;
            }
            bm.cb.ab = w.g;
            return;
        }
        if (n2 == 0) {
            n2 = ((cc)((Object)cr3)).ae();
            switch (n2) {
                case 0: {
                    w.g = ((cc)((Object)cr3)).aj();
                    return;
                }
                case 1: {
                    w.h = ((cc)((Object)cr3)).aj();
                    cr cr3 = bh.a(bm.br, w.h);
                    if (cr3 == null) return;
                    w.i = cr3.E;
                    return;
                }
                case 2: {
                    w.i = ((cc)((Object)cr3)).an();
                    return;
                }
                case 3: {
                    w.l = ((cc)((Object)cr3)).an();
                    return;
                }
                case 4: {
                    w.k = ((cc)((Object)cr3)).an();
                    return;
                }
                case 5: {
                    w.m = ((cc)((Object)cr3)).aj();
                    return;
                }
                case 6: {
                    w.n = ((cc)((Object)cr3)).ah();
                    return;
                }
                case 7: {
                    w.r = ((cc)((Object)cr3)).ah();
                    return;
                }
                case 8: {
                    w.s = ((cc)((Object)cr3)).ah();
                    return;
                }
                case 9: {
                    w.p = ((cc)((Object)cr3)).ae();
                    return;
                }
                case 10: {
                    w.o = ((cc)((Object)cr3)).ae();
                    return;
                }
                case 11: {
                    w.q = ((cc)((Object)cr3)).ae();
                }
                default: {
                    return;
                }
            }
        }
        if (n2 != 2) return;
        n2 = ((cc)((Object)cr3)).aj();
        int n3 = ((cc)((Object)cr3)).aj();
        cr cr4 = bh.a(bm.br, n2);
        if (cr4 != null) {
            cr4.ad = n3;
        }
        if (bm.cb.C != n2) return;
        bm.cb.ad = n3;
    }

    private void bQ() {
        cc cc2 = object;
        short s2 = cc2.ai();
        cc2 = object;
        short s3 = cc2.ai();
        byte by2 = ((cc)object).ae();
        cc2 = object;
        short s4 = cc2.ai();
        int n2 = ((cc)object).ae();
        Object object = ((cc)object).m(0, n2);
        object = bg.c((String)object, "$");
        n2 = 0;
        int n3 = 0;
        if (s2 < k.a.length) {
            n2 = k.a[s2][0];
            n3 = k.a[s2][1];
        }
        k.a(n2, n3, s3, by2, s4, (String)object);
    }

    private void bR() {
        byte by2 = this.ae();
        cc cc2 = this;
        int n2 = cc2.ai();
        cc2 = this;
        short s2 = cc2.ai();
        if (s2 > 0) {
            s2 = (short)(s2 - 1);
        }
        byte by3 = this.ae();
        bm.dW = 0;
        bm.dX = n2;
        if (bm.dY > s2 && by3 > 0) {
            bm.dW = by3 - 1;
        }
        bm.dY = s2;
        cl.o = new int[by3];
        cl.r = new int[by3];
        cl.s = new int[by3];
        cl.t = new int[by3];
        cl.u = new String[by3];
        cl.v = new short[by3];
        cl.w = new byte[by3];
        cl.x = new byte[by3];
        cl.y = new byte[by3];
        cl.p = new String[by3];
        cl.q = new long[by3];
        if (by3 > 0) {
            for (n2 = 0; n2 < by3; ++n2) {
                cl.o[n2] = this.aj();
                cc cc3 = this;
                cl.v[n2] = cc3.ai();
                cl.u[n2] = this.an();
                cl.p[n2] = by2 == 10 ? bm.cb.E : this.an();
                cl.r[n2] = this.aj();
                cl.t[n2] = this.aj();
                cl.s[n2] = this.aj();
                cl.w[n2] = this.ae();
                cl.x[n2] = this.ae();
                cl.y[n2] = this.ae();
                cl.q[n2] = this.ak();
            }
        }
        bm.m(81);
        l.a(by2);
        bm.X = 3;
        bm.ae();
    }

    private void bS() {
        int n2 = this.aj();
        for (int i2 = 0; i2 < n2; ++i2) {
            cc cc2 = this;
            short s2 = cc2.ai();
            cc2 = this;
            short s3 = cc2.ai();
            byte by2 = this.ae();
            int n3 = this.aj();
            if (by2 == 0) {
                m.d.remove(ch.a(s2, s3));
                continue;
            }
            m.d.put(ch.a(s2, s3), new ch(s2, s3, by2, n3));
        }
    }

    private void bT() {
        int n2 = this.aj();
        int n3 = this.aj();
        ak.a(n2, n3);
    }

    private void bU() {
        byte by2 = this.ae();
        bm.u = by2 == 1;
    }

    private void bV() {
        byte by2 = this.ae();
        bm.t = by2 == 1;
    }

    private void bW() {
        if (t.x == null) {
            t.x = new Vector();
        }
        t.x.removeAllElements();
        int n2 = this.ae();
        for (int i2 = 0; i2 < n2; ++i2) {
            cd cd2 = new cd();
            new cd().a = this.an();
            cc cc2 = this;
            cd2.d = cc2.ai();
            cc2 = this;
            cd2.e = cc2.ai();
            cc2 = this;
            cd2.f = cc2.ai();
            cd2.c = 1;
            t.x.addElement(cd2);
        }
    }

    private void bX() {
        av.a = this.am();
        System.out.println("url:" + av.a);
    }

    private void bY() {
        int n2 = this.aj();
        cr cr2 = bh.e(n2);
        if (cr2 != null) {
            cr2.bi[0] = this.ae();
            cr2.bi[1] = this.ae();
            cr2.bi[2] = this.ae();
        }
    }

    private void bZ() {
        int n2 = this.aj();
        cr cr2 = bh.e(n2);
        if (cr2 == null) {
            return;
        }
        cr2.ap = true;
        long l2 = this.aj();
        if (cr2.m()) {
            return;
        }
        cr2.b = 5;
        cr2.a(l2);
    }

    private void ca() {
        bg.a(this.g, 0, this.h);
        this.a(this.g, (int)this.h);
    }

    private void a(byte[] byArray, int n2) {
        int n3 = ct.a(byArray);
        int n4 = ct.b(byArray);
        ak.c += (long)n3;
        if (n3 != n2) {
            String cfr_ignored_0 = "len!=size " + n3 + "/" + n4;
            return;
        }
        if (n2 >= 4) {
            String cfr_ignored_1 = "send:" + n4 + "/" + n3;
            this.f.a(byArray, n2);
        }
    }

    public final void b() {
        if (this.f != null && bm.n()) {
            long l2;
            bm.z = l2 = aq.i();
            this.c(8, (short)-1);
            this.Y((int)l2);
            this.ca();
        }
    }

    public final void a(int n2, String object) {
        String string = "240W";
        byte[] byArray = bg.a("240W", "UTF-8");
        if (byArray == null) {
            return;
        }
        string = object;
        object = bg.a(string, "UTF-8");
        this.c(16 + byArray.length + ((Object)object).length, (short)513);
        this.Y(n2);
        this.Y(((Object)object).length);
        this.a((byte[])object);
        this.Y(byArray.length);
        this.a(byArray);
        this.Y(bm.M);
        this.Y(11400);
        this.ca();
    }

    public final void c(int n2) {
        this.c(8, (short)514);
        this.Y(0);
        this.ca();
    }

    public final void c() {
        this.c(4, (short)519);
        this.ca();
    }

    public final void b(int n2, int n3) {
        this.c(48, (short)523);
        this.Y(n2);
        this.Y(n3);
        this.Y(0);
        this.ca();
    }

    public final void a(int n2, short s2) {
        try {
            short s3 = 524;
            cc cc2 = this;
            cc2.c(0, s3);
            this.Y(n2);
            this.b(s2);
            this.ca();
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public final void d(int n2) {
        short s2 = 600;
        cc cc2 = this;
        cc2.c(0, s2);
        this.Y(n2);
        this.ca();
    }

    public final void d() {
        this.c(4, (short)525);
        this.ca();
    }

    public final void a(short s2, short s3, short s4) {
        this.c(12, (short)527);
        this.b((short)bm.cj);
        this.b(s2);
        this.b(s3);
        this.b(s4);
        this.ca();
    }

    private void cb() {
        if (bm.bz) {
            this.a((byte)0);
        } else {
            this.a((byte)1);
            cs.a[2] = 1;
            af.a(true, 5);
        }
        this.Y(br.a);
        this.f(bm.by);
    }

    public final void a(String object, String object2, String object3) {
        if (object == null || object2 == null) {
            return;
        }
        object2 = bm.bY.a((String)object2);
        object3 = object;
        object = bg.a((String)object3, "UTF-8");
        short s2 = 256;
        object3 = this;
        ((cc)object3).c(0, s2);
        this.cb();
        this.Y(((Object)object).length);
        this.a((byte[])object);
        this.Y(((Object)object2).length);
        this.a((byte[])object2);
        this.Y(0);
        this.Y(7);
        this.ca();
    }

    public final void a(String object, String string) {
        if (object == null || string == null) {
            return;
        }
        byte[] byArray = bm.bY.a(string);
        object = bg.a((String)object, "UTF-8");
        this.c(4 + ((Object)object).length + 4 + byArray.length + 4, (short)257);
        this.Y(((Object)object).length);
        this.a((byte[])object);
        this.Y(byArray.length);
        this.a(byArray);
        this.Y(11400);
        this.cb();
        this.Y(7);
        String cfr_ignored_0 = string + "PW(" + byArray + "," + byArray.length + ")";
        this.ca();
    }

    public final void b(String object, String object2, String object3) {
        if (object == null || object2 == null || object3 == null) {
            return;
        }
        object2 = bm.bY.a((String)object2);
        object3 = bm.bY.a((String)object3);
        object = bg.a((String)object, "UTF-8");
        this.c(4 + ((Object)object).length + 4 + ((Object)object2).length + 4 + ((Object)object3).length + 4, (short)259);
        this.Y(((Object)object).length);
        this.a((byte[])object);
        this.Y(((Object)object2).length);
        this.a((byte[])object2);
        this.Y(((Object)object3).length);
        this.a((byte[])object3);
        this.ca();
    }

    public final void c(String object, String object2, String object3) {
        if (object == null || object2 == null || object3 == null) {
            return;
        }
        object2 = bm.bY.a((String)object2);
        String string = object;
        object = bg.a(string, "UTF-8");
        string = object3;
        object3 = bg.a(string, "UTF-8");
        this.c(4 + ((Object)object).length + 4 + ((Object)object2).length + 4 + ((Object)object3).length + 4, (short)263);
        this.Y(((Object)object).length);
        this.a((byte[])object);
        this.Y(((Object)object2).length);
        this.a((byte[])object2);
        this.Y(((Object)object3).length);
        this.a((byte[])object3);
        this.ca();
    }

    public final void a(String string, String string2, String string3, String string4) {
        if (string == null || string2 == null || string3 == null || string4 == null) {
            return;
        }
        short s2 = 270;
        cc cc2 = this;
        cc2.c(0, s2);
        this.f(string);
        this.f(string2);
        this.f(string3);
        this.f(string4);
        this.ca();
    }

    public final void d(String string, String string2, String string3) {
        if (string == null || string2 == null || string3 == null) {
            return;
        }
        short s2 = 272;
        cc cc2 = this;
        cc2.c(0, s2);
        this.f(string);
        this.f(string2);
        this.f(string3);
        this.ca();
    }

    public final void a(String string) {
        short s2 = 274;
        cc cc2 = this;
        cc2.c(0, s2);
        this.f(string);
        this.ca();
    }

    public final void b(String string) {
        short s2 = 275;
        cc cc2 = this;
        cc2.c(0, s2);
        this.f(string);
        this.ca();
    }

    public final void e() {
        short s2 = 265;
        cc cc2 = this;
        cc2.c(0, s2);
        this.Y(11400);
        this.Y(br.a);
        this.Y(7);
        this.f(bm.by);
        this.ca();
    }

    public final void f() {
        short s2 = 267;
        cc cc2 = this;
        cc2.c(0, s2);
        this.ca();
    }

    public final void g() {
        short s2 = 4100;
        cc cc2 = this;
        cc2.c(0, s2);
        this.Y(11400);
        this.cb();
        this.Y(7);
        this.ca();
    }

    public final void a(int n2, int n3, int n4, int n5, String object) {
        if (object == null) {
            return;
        }
        Object object2 = object;
        object = bg.a((String)object2, "UTF-8");
        short s2 = 518;
        object2 = this;
        ((cc)object2).c(0, s2);
        this.a((byte)n2);
        this.a((byte)n3);
        this.a((byte)n4);
        this.a((byte)n5);
        n2 = 33;
        Object object3 = object;
        cc cc2 = this;
        cc2.a((byte[])object3);
        for (n4 = 0; n4 < 33 - ((Object)object3).length; ++n4) {
            cc2.a((byte)0);
        }
        this.ca();
    }

    public final void e(int n2) {
        this.c(8, (short)515);
        this.Y(n2);
        this.ca();
    }

    public final void a(int n2, int n3, long l2) {
        short s2 = 2500;
        cc cc2 = this;
        cc2.c(0, s2);
        this.Y(n2);
        this.Y(n3);
        long l3 = l2;
        cc cc3 = this;
        short s3 = cc3.h;
        cc3.h = (short)(s3 + 1);
        cc3.g[s3] = (byte)(l3 & 0xFFL);
        short s4 = cc3.h;
        cc3.h = (short)(s4 + 1);
        cc3.g[s4] = (byte)(l3 >>> 8 & 0xFFL);
        short s5 = cc3.h;
        cc3.h = (short)(s5 + 1);
        cc3.g[s5] = (byte)(l3 >>> 16 & 0xFFL);
        short s6 = cc3.h;
        cc3.h = (short)(s6 + 1);
        cc3.g[s6] = (byte)(l3 >>> 24 & 0xFFL);
        short s7 = cc3.h;
        cc3.h = (short)(s7 + 1);
        cc3.g[s7] = (byte)(l3 >>> 32 & 0xFFL);
        short s8 = cc3.h;
        cc3.h = (short)(s8 + 1);
        cc3.g[s8] = (byte)(l3 >>> 40 & 0xFFL);
        short s9 = cc3.h;
        cc3.h = (short)(s9 + 1);
        cc3.g[s9] = (byte)(l3 >>> 48 & 0xFFL);
        short s10 = cc3.h;
        cc3.h = (short)(s10 + 1);
        cc3.g[s10] = (byte)(l3 >>> 56 & 0xFFL);
        this.ca();
    }

    public final void f(int n2) {
        short s2 = 3006;
        cc cc2 = this;
        cc2.c(0, s2);
        l.a = aq.g();
        this.Y(n2);
        this.Y(l.a);
        if (l.j == null) {
            this.Y(0);
        } else {
            this.Y(l.j.C);
        }
        this.ca();
        bm.E(8);
    }

    private void cc() {
        int n2;
        int n3;
        byte by2 = this.ae();
        int n4 = this.aj();
        l.m = this.aj();
        int n5 = this.ae();
        l.h = new int[n5];
        l.i = new String[n5];
        for (n3 = 0; n3 < n5; ++n3) {
            n2 = l.h[n3] = this.aj();
            l.i[n3] = l.h[n3] >= 428 ? n3 + 1 + "." + this.an() : n3 + 1 + "." + bm.d(0, n2);
        }
        n3 = this.ae();
        l.l = new int[n3];
        for (n2 = 0; n2 < l.l.length; ++n2) {
            l.l[n2] = this.aj();
        }
        if (by2 == 2) {
            l.c = true;
            bm.l(81);
            l.a((byte)n4);
        } else {
            l.a(n4, true);
        }
        bm.ae();
    }

    public final void a(int n2, byte by2, String object) {
        if (((String)object).indexOf("/-1") == -1) {
            bt.i();
        }
        if (!bt.u.equals("")) {
            int n3 = ((String)object).indexOf("/-1");
            bt.a(2, ((String)object).substring(0, n3) + bt.u + ((String)object).substring(n3 + 3));
        } else {
            bt.a(2, (String)object);
        }
        String string = (String)object + "^" + bt.q + "^" + bt.r + "^" + bt.s + "^" + bt.t + "^" + bt.u + "^" + bt.v;
        object = string;
        object = string;
        byte[] byArray = bg.a(string, "UTF-8");
        short s2 = 769;
        object = this;
        ((cc)object).c(0, s2);
        this.a(by2);
        this.Y(n2);
        this.a(bt.w);
        this.Y(byArray.length);
        this.a(byArray);
        this.ca();
        bt.i();
    }

    public final void h() {
        int n2 = 2400;
        Object object = this;
        ((cc)object).c(0, (short)n2);
        object = bd.c;
        n2 = ((String)object).lastIndexOf(93);
        if (n2 != -1) {
            object = ((String)object).substring(n2 + 1);
        }
        object = bg.a((String)object, "UTF-8");
        this.Y(((Object)object).length);
        this.a((byte[])object);
        object = "";
        for (n2 = 0; n2 < bd.d.length; ++n2) {
            object = (String)object + bd.d[n2];
        }
        object = bg.a((String)object, "UTF-8");
        this.Y(((Object)object).length);
        this.a((byte[])object);
        this.Y(bd.e);
        this.Y(bd.g);
        this.a(bt.w);
        this.a(bt.x);
        this.ca();
    }

    public final void i() {
        short s2 = 2403;
        cc cc2 = this;
        cc2.c(0, s2);
        this.ca();
    }

    public final void c(String string) {
        short s2 = 4301;
        cc cc2 = this;
        cc2.c(0, s2);
        this.f(string);
        this.ca();
    }

    public final void g(int n2) {
        short s2 = 2405;
        cc cc2 = this;
        cc2.c(0, s2);
        this.Y(n2);
        this.ca();
    }

    public final void h(int n2) {
        short s2 = 2409;
        cc cc2 = this;
        cc2.c(0, s2);
        this.Y(n2);
        this.ca();
    }

    public final void i(int n2) {
        short s2 = 2407;
        cc cc2 = this;
        cc2.c(0, s2);
        this.Y(n2);
        this.ca();
    }

    public final void j() {
        short s2 = 2411;
        cc cc2 = this;
        cc2.c(0, s2);
        this.ca();
    }

    public final void j(int n2) {
        short s2 = 2410;
        cc cc2 = this;
        cc2.c(0, s2);
        this.Y(n2);
        this.ca();
    }

    public final void k(int n2) {
        if (n2 == 1) {
            f.a(7);
            return;
        }
        f.a(0);
        this.c(6, (short)612);
        this.b((short)n2);
        this.ca();
    }

    public final void l(int n2) {
        this.c(6, (short)617);
        this.b((short)n2);
        this.ca();
    }

    public final void m(int n2) {
        this.c(8, (short)607);
        this.Y(n2);
        this.ca();
    }

    public final void c(int n2, int n3) {
        this.c(10, (short)608);
        this.Y(n2);
        this.b((short)n3);
        this.ca();
    }

    public final void a(int n2, int n3, int n4, int n5, byte by2) {
        this.c(13, (short)609);
        this.Y(n2);
        this.b((short)n3);
        this.a((byte)n4);
        this.a((byte)n5);
        if (n4 == 1) {
            this.a(by2);
        } else if (n4 == 0) {
            this.a((byte)1);
        }
        this.ca();
    }

    public final void n(int n2) {
        this.c(8, (short)638);
        this.Y(n2);
        this.ca();
    }

    public final void a(short s2, short s3) {
        this.c(14, (short)602);
        this.b(s2);
        this.b(s3);
        this.ca();
    }

    public final void o(int n2) {
        short s2 = 532;
        cc cc2 = this;
        cc2.c(0, s2);
        this.Y(n2);
        this.ca();
    }

    final void k() {
        this.c(4, (short)1267);
        this.ca();
    }

    final void b(short s2, short s3) {
        this.c(8, (short)1226);
        this.b(s2);
        this.b(s3);
        this.ca();
    }

    final void a(int n2, int n3, short s2, short s3) {
        if (n2 == -1 && n3 == -1) {
            if (e) {
                e = false;
            } else {
                return;
            }
        }
        short s4 = 601;
        cc cc2 = this;
        cc2.c(0, s4);
        if (n2 != -1 || n3 != -1) {
            this.Y(n2);
            this.b((short)n3);
            cr.y(n3);
            if (s2 >= 0 && s3 >= 0) {
                this.b(s2);
                this.b(s3);
            }
            String cfr_ignored_0 = "SEND ATT + " + n3 + "  T " + bm.m();
            e = true;
        }
        this.ca();
        ++i.o;
    }

    public final void l() {
        short s2 = 1268;
        cc cc2 = this;
        cc2.c(0, s2);
        this.ca();
    }

    public final void a(n n2, bh bh2) {
        if (!bm.cb.m()) {
            if (n2.d()) {
                return;
            }
            n2.w = bm.m();
            bm.aA.a(n2.d, n2.w);
            short s2 = 958;
            cc cc2 = this;
            cc2.c(0, s2);
            this.a(n2.d);
            this.a(n2.c);
            if (bh2 != null) {
                this.Y(bh2.C);
            } else {
                this.Y(0);
            }
            this.ca();
        }
    }

    public final void m() {
        short s2 = 530;
        cc cc2 = this;
        cc2.c(0, s2);
        this.Y(bm.ci);
        this.ca();
    }

    public final void a(n n2, int n3) {
        if (!bm.cb.m()) {
            if (n2.d()) {
                return;
            }
            bm.aA.a(n2.d, n2.r);
            short s2 = 958;
            cc cc2 = this;
            cc2.c(0, s2);
            this.a(n2.d);
            this.a(n2.c);
            this.Y(n3);
            this.ca();
        }
    }

    public final void b(n n2, int n3) {
        if (!bm.cb.m()) {
            if (n2.d()) {
                return;
            }
            n2.w = bm.m();
            short s2 = 958;
            cc cc2 = this;
            cc2.c(0, s2);
            this.a(n2.d);
            this.a(n2.c);
            this.Y(n3);
            this.ca();
        }
    }

    public final void p(int n2) {
        this.c(8, (short)800);
        this.Y(n2);
        this.ca();
    }

    public final void q(int n2) {
        this.c(8, (short)805);
        this.Y(n2);
        this.ca();
    }

    public final void n() {
        this.c(4, (short)804);
        this.ca();
    }

    public final void o() {
        this.c(4, (short)815);
        this.ca();
    }

    public final void a(int n2, byte by2) {
        this.c(9, (short)809);
        this.Y(n2);
        this.a(by2);
        this.ca();
    }

    public final void r(int n2) {
        this.c(8, (short)813);
        this.Y(n2);
        this.ca();
    }

    public final void s(int n2) {
        this.c(8, (short)770);
        this.Y(n2);
        this.ca();
    }

    public final void d(int n2, int n3) {
        this.c(9, (short)772);
        this.Y(n2);
        this.a((byte)n3);
        this.ca();
    }

    public final void t(int n2) {
        this.c(8, (short)773);
        this.Y(n2);
        this.ca();
    }

    public final void u(int n2) {
        this.c(8, (short)775);
        this.Y(n2);
        this.ca();
    }

    public final void v(int n2) {
        this.c(8, (short)776);
        this.Y(n2);
        this.ca();
    }

    public final void a(int n2, String object, String object2) {
        if (object == null || object2 == null) {
            return;
        }
        String string = object;
        object = bg.a(string, "UTF-8");
        string = object2;
        object2 = bg.a(string, "UTF-8");
        this.c(12 + ((Object)object).length + ((Object)object2).length + 4, (short)830);
        this.Y(n2);
        this.Y(((Object)object).length);
        this.a((byte[])object);
        this.Y(((Object)object2).length);
        this.a((byte[])object2);
        this.ca();
    }

    public final void w(int n2) {
        this.c(8, (short)831);
        this.Y(n2);
        this.ca();
    }

    public final void e(int n2, int n3) {
        this.c(9, (short)850);
        this.Y(n2);
        this.a((byte)n3);
        this.ca();
    }

    public final void p() {
        this.c(4, (short)832);
        this.ca();
    }

    public final void b(int n2, String object) {
        object = bg.a((String)object, "UTF-8");
        this.c(8 + ((Object)object).length + 4, (short)834);
        this.Y(n2);
        this.Y(((Object)object).length);
        this.a((byte[])object);
        this.ca();
    }

    public final void a(int n2, int n3, int n4) {
        this.c(13, (short)833);
        this.Y(n2);
        this.Y(n3);
        this.a((byte)n4);
        this.ca();
    }

    public final void x(int n2) {
        this.c(8, (short)1053);
        this.Y(n2);
        this.ca();
    }

    public final void b(int n2, int n3, int n4) {
        this.c(16, (short)1054);
        this.Y(n2);
        this.Y(n3);
        this.Y(n4);
        this.ca();
    }

    public final void q() {
        this.c(4, (short)852);
        this.ca();
    }

    public final void r() {
        this.c(4, (short)886);
        if (bm.cf != null) {
            this.Y(bm.cf.C);
        } else {
            this.Y(0);
        }
        this.ca();
    }

    public final void s() {
        this.c(4, (short)887);
        if (bm.cf != null) {
            this.Y(bm.cf.C);
        } else {
            this.Y(0);
        }
        this.ca();
    }

    public final void a(int n2, byte by2, byte by3) {
        this.c(13, (short)880);
        if (bm.cf != null) {
            this.a((byte)1);
            this.Y(bm.cf.C);
        } else {
            this.a((byte)0);
            this.Y(cn.f);
        }
        this.a((int)((short)n2));
        this.a(by3);
        this.a(by2);
        this.a((byte)cn.b);
        this.ca();
    }

    public final void b(int n2, byte by2, byte by3) {
        this.c(8, (short)883);
        if (bm.cf != null) {
            this.Y(bm.cf.C);
        } else {
            this.Y(0);
        }
        this.a(n2);
        this.a(by3);
        this.a(by2);
        this.ca();
    }

    public final void a(int n2, int n3, boolean bl2, boolean bl3) {
        Object object;
        if (!bl2 && cn.e.size() > 0 && (object = (cn)cn.e.elementAt(n3)) != null) {
            cn.N = n3;
            bm.aP = object;
            if (bm.aR != null) {
                bm.aR = bm.aP;
                bm.aR.h();
            }
            return;
        }
        if (cn.b == 0) {
            this.t();
        }
        cn.f = n2;
        short s2 = 952;
        object = this;
        ((cc)object).c(0, s2);
        if (bm.cf != null) {
            this.a((byte)1);
            cn.f = bm.cf.C;
        } else {
            cn.f = n2;
            this.a((byte)0);
        }
        this.Y(cn.f);
        byte by2 = (byte)(bl2 ? 1 : 0);
        by2 = (byte)(by2 | (bl3 ? 2 : 0) & 0xFF);
        this.a(by2);
        this.Y(n3);
        this.ca();
        bm.E(3);
    }

    public final void a(int n2, int n3, boolean bl2) {
        this.a(n2, 0, true, false);
    }

    public final void a(int n2, boolean bl2) {
        this.c(9, (short)526);
        this.Y(n2);
        if (bl2) {
            this.a((byte)1);
        } else {
            this.a((byte)0);
        }
        this.ca();
    }

    public final void b(int n2, byte by2) {
        this.c(9, (short)960);
        this.a(n2);
        this.a(by2);
        this.ca();
    }

    public final void c(int n2, byte by2) {
        this.c(9, (short)965);
        this.a(n2);
        this.a(by2);
        this.ca();
    }

    public final void c(int n2, byte by2, byte by3) {
        this.c(8, (short)956);
        this.a(n2);
        this.a(by2);
        this.a(by3);
        this.ca();
    }

    public final void a(n n2, boolean bl2, byte by2) {
        short s2 = 984;
        cc cc2 = this;
        cc2.c(0, s2);
        if (bm.cf != null) {
            this.Y(bm.cf.C);
        } else {
            this.Y(0);
        }
        if (bl2) {
            this.a((byte)1);
        } else {
            this.a((byte)0);
            this.a(by2);
            this.a(n2.d);
            this.a(n2.c);
        }
        this.ca();
    }

    public final void b(n n2, boolean bl2, byte by2) {
        short s2 = 962;
        cc cc2 = this;
        cc2.c(0, s2);
        if (bm.cf != null) {
            this.Y(bm.cf.C);
        } else {
            this.Y(0);
        }
        if (bl2) {
            this.a((byte)1);
        } else {
            this.a((byte)0);
            this.a(by2);
            this.a(n2.d);
            this.a(n2.c);
        }
        this.ca();
    }

    public final void f(int n2, int n3) {
        this.c(12, (short)900);
        this.Y(n2);
        this.Y(n3);
        this.ca();
    }

    public final void c(int n2, int n3, int n4) {
        this.c(16, (short)902);
        this.Y(n2);
        this.Y(n3);
        this.Y(n4);
        this.ca();
    }

    public final void y(int n2) {
        this.c(8, (short)906);
        this.Y(n2);
        this.ca();
    }

    public final void z(int n2) {
        this.c(8, (short)911);
        this.Y(n2);
        this.ca();
    }

    public final void a(int n2, cn object) {
        ((cn)object).d();
        short s2 = 904;
        object = this;
        ((cc)object).c(0, s2);
        this.Y(n2);
        bm.aA.b();
        this.ca();
    }

    public final void t() {
        this.c(4, (short)932);
        this.ca();
    }

    public final void A(int n2) {
        this.c(8, (short)978);
        this.Y(n2);
        this.ca();
    }

    public final void a(byte by2, int n2, int n3) {
        short s2 = 1952;
        cc cc2 = this;
        cc2.c(0, s2);
        this.a(by2);
        this.a((byte)n2);
        this.a((byte)n3);
        this.ca();
    }

    public final void b(byte by2, int n2, int n3) {
        short s2 = 844;
        cc cc2 = this;
        cc2.c(0, s2);
        this.a(by2);
        this.Y(n2);
        this.a((byte)6);
        this.ca();
    }

    public final void B(int n2) {
        this.c(8, (short)846);
        this.Y(n2);
        this.ca();
    }

    public final void u() {
        this.c(4, (short)967);
        if (bm.cf != null) {
            this.Y(bm.cf.C);
        } else {
            this.Y(0);
        }
        this.ca();
    }

    public final void a(byte by2, int n2, byte by3, int n3) {
        this.c(12, (short)968);
        if (bm.cf != null) {
            this.Y(bm.cf.C);
        } else {
            this.Y(0);
        }
        this.a(by2);
        this.a(n2);
        this.a(by3);
        this.Y(n3);
        this.ca();
    }

    public final void a(byte by2, int n2) {
        this.c(9, (short)969);
        if (bm.cf != null) {
            this.Y(bm.cf.C);
        } else {
            this.Y(0);
        }
        this.a(by2);
        this.Y(n2);
        this.ca();
    }

    public final void a(byte by2, String string, byte by3, byte by4, int n2, byte by5, byte by6, byte by7, byte by8, byte by9, int n3) {
        if (n3 == 116) {
            by4 = 1;
        } else if (n3 == 117) {
            by4 = (byte)2;
        } else if (n3 == 118) {
            by4 = (byte)3;
        }
        cl.k = by4;
        ++n2;
        by5 = (byte)string.length();
        this.c(by5 + 1 + 1 + 1 + 2 + 1 + 4, (short)2900);
        this.a((byte)8);
        this.a(by2);
        this.f(string);
        this.a(by3);
        this.a(by4);
        this.b((short)n2);
        this.a((byte)5);
        this.a(by6);
        this.a(by7);
        this.a(by8);
        this.a(by9);
        this.ca();
    }

    public final void a(byte by2, int n2, int n3, int n4) {
        this.c(14, (short)2901);
        this.a(by2);
        this.a((byte)n2);
        this.Y(n3);
        this.Y(n4);
        this.ca();
    }

    public final void g(int n2, int n3) {
        short s2 = 2906;
        cc cc2 = this;
        cc2.c(0, s2);
        this.Y(n2);
        this.Y(n3);
        this.ca();
    }

    public final void h(int n2, int n3) {
        this.c(4, (short)2905);
        this.Y(n2);
        this.Y(n3);
        this.ca();
    }

    public final void C(int n2) {
        this.c(4, (short)2902);
        this.Y(n2);
        this.ca();
    }

    public final void D(int n2) {
        this.c(4, (short)2909);
        this.Y(n2);
        this.ca();
    }

    public final void d(int n2, byte by2) {
        short s2 = 2907;
        cc cc2 = this;
        cc2.c(0, s2);
        this.a((byte)10);
        this.b((short)(++n2));
        this.a((byte)5);
        this.ca();
    }

    public final void e(int n2, byte by2) {
        short s2 = 2908;
        cc cc2 = this;
        cc2.c(0, s2);
        this.a((byte)11);
        this.b((short)(++n2));
        this.a((byte)5);
        this.ca();
    }

    public final void d(int n2, int n3, int n4) {
        boolean bl2 = true;
        cc cc2 = this;
        cc2.c(16, (short)1008);
        cc2.Y(n2);
        cc2.Y(n3);
        cc2.Y(n4);
        cc2.Y(1);
        cc2.ca();
    }

    public final void a(int[][] nArray) {
        this.c((nArray.length * nArray[0].length << 2) + 4, (short)1223);
        for (int i2 = 0; i2 < nArray.length; ++i2) {
            for (int i3 = 0; i3 < nArray[i2].length; ++i3) {
                this.Y(nArray[i2][i3]);
            }
        }
        this.ca();
    }

    public final void i(int n2, int n3) {
        this.c(12, (short)1266);
        this.Y(n2);
        this.Y(n3);
        this.ca();
    }

    public final void j(int n2, int n3) {
        this.c(12, (short)1270);
        this.Y(n2);
        this.Y(0);
        this.ca();
    }

    public final void E(int n2) {
        this.c(8, (short)1271);
        this.Y(n2);
        this.ca();
    }

    public final void v() {
        short s2 = 1265;
        cc cc2 = this;
        cc2.c(0, s2);
        this.ca();
    }

    public final void F(int n2) {
        cn.ar = null;
        short s2 = 2201;
        cc cc2 = this;
        cc2.c(0, s2);
        this.Y(n2);
        this.a((byte)0);
        this.ca();
        bm.ad();
    }

    public final void G(int n2) {
        this.c(10, (short)2510);
        this.Y(n2);
        this.b((short)bm.bJ);
        this.ca();
    }

    public final void a(short s2) {
        this.c(10, (short)2511);
        this.Y(bm.bE);
        this.b(s2);
        this.ca();
    }

    public final void a(int n2, int[] nArray) {
        short s2 = 2201;
        cc cc2 = this;
        cc2.c(0, s2);
        this.Y(n2);
        this.a((byte)nArray.length);
        for (n2 = 0; n2 < nArray.length; ++n2) {
            this.Y(nArray[n2]);
        }
        this.ca();
    }

    public final void H(int n2) {
        int[] nArray = new int[]{bm.dN, n2};
        this.a(bm.dM, nArray);
    }

    public final void I(int n2) {
        this.a(n2, true, (byte)1);
    }

    public final void a(int n2, boolean bl2, byte by2) {
        if (bl2) {
            short s2 = 1073;
            cc cc2 = this;
            cc2.c(0, s2);
        } else {
            short s3 = 1070;
            cc cc3 = this;
            cc3.c(0, s3);
        }
        this.a((byte)n2);
        bm.aG.a(by2);
        this.ca();
    }

    public final void e(int n2, int n3, int n4) {
        short s2 = 1074;
        cc cc2 = this;
        cc2.c(0, s2);
        this.a((byte)n4);
        this.Y(n2);
        this.a((byte)n3);
        this.ca();
    }

    public final void w() {
        short s2 = 1076;
        cc cc2 = this;
        cc2.c(0, s2);
        this.Y(0);
        this.ca();
    }

    public final void b(byte by2, int n2) {
        short s2 = 1077;
        cc cc2 = this;
        cc2.c(0, s2);
        this.a(by2);
        this.Y(n2);
        this.ca();
    }

    public final void a(int n2, int n3, int n4, int n5, byte by2, int n6) {
        short s2 = 1078;
        cc cc2 = this;
        cc2.c(0, s2);
        this.b((short)n2);
        this.a((byte)n3);
        this.a((byte)n5);
        this.a((byte)n4);
        this.a(by2);
        this.Y(n6);
        this.ca();
    }

    public final void a(int n2, int n3, int n4, byte by2, int n5) {
        short s2 = 1079;
        cc cc2 = this;
        cc2.c(0, s2);
        this.a((byte)n2);
        this.a((byte)n4);
        this.a((byte)n3);
        this.a(by2);
        this.Y(n5);
        this.ca();
    }

    public final void a(int n2, byte by2, int n3) {
        short s2 = 1075;
        cc cc2 = this;
        cc2.c(0, s2);
        this.a((byte)n3);
        this.Y(n2);
        this.a(by2);
        this.ca();
    }

    public final void b(byte by2) {
        short s2 = 3010;
        cc cc2 = this;
        cc2.c(0, s2);
        this.a(by2);
        this.ca();
    }

    public final void a(int n2, int n3, byte by2, short s2) {
        this.a(n2, n3, by2, s2, true, false, 0, 0, 0);
    }

    public final void J(int n2) {
        if (cn.S) {
            cn.S = false;
            this.a(n2, 0, (byte)0, (short)0, false, false, 0, 0, 0);
        }
    }

    public final void a(int n2, int n3, byte by2, short s2, boolean bl2, boolean bl3, int n4, int n5, int n6) {
        short s3 = 1080;
        cc cc2 = this;
        cc2.c(0, s3);
        this.a((byte)n2);
        this.Y(n3);
        this.a(by2);
        this.a((byte)s2);
        this.a((byte)(bl2 ? 1 : 0));
        if (!bl2) {
            this.a((byte)(bl3 ? 1 : 0));
            if (bl3) {
                this.a((byte)n6);
                this.a((byte)n4);
                this.a((byte)n5);
            }
        }
        this.ca();
    }

    public final void x() {
        short s2 = 2287;
        cc cc2 = this;
        cc2.c(0, s2);
        this.ca();
    }

    public final void y() {
        short s2 = 1087;
        cc cc2 = this;
        cc2.c(0, s2);
        this.ca();
    }

    public final void k(int n2, int n3) {
        short s2 = 1088;
        cc cc2 = this;
        cc2.c(0, s2);
        this.a((byte)n2);
        this.a((byte)n3);
        this.ca();
    }

    public final void z() {
        short s2 = 2350;
        cc cc2 = this;
        cc2.c(0, s2);
        this.ca();
    }

    public final void A() {
        short s2 = 2351;
        cc cc2 = this;
        cc2.c(0, s2);
        if (bm.cf != null) {
            this.Y(bm.cf.C);
        } else {
            this.Y(0);
        }
        this.ca();
    }

    public final void a(bh bh2) {
        int n2 = 0;
        int n3 = n2 = bh2 == null ? 0 : bh2.C;
        if (p == n2) {
            return;
        }
        n2 = p = n2;
        if (bh2 == null || bh2.g() || bh2.f() || bh2.e()) {
            this.c(8, (short)2115);
            this.Y(n2);
            this.ca();
        }
    }

    public final void K(int n2) {
        this.c(8, (short)1280);
        this.Y(n2);
        this.ca();
    }

    public final void L(int n2) {
        this.c(8, (short)1282);
        this.Y(n2);
        this.ca();
    }

    public final void M(int n2) {
        short s2 = 1285;
        cc cc2 = this;
        cc2.c(0, s2);
        this.Y(n2);
        this.ca();
    }

    public final void B() {
        this.c(4, (short)1286);
        this.ca();
    }

    public final void C() {
        this.c(4, (short)1287);
        this.ca();
    }

    public final void D() {
        this.c(4, (short)1295);
        this.ca();
    }

    public final void a(int n2, int n3, int n4, int n5, int n6) {
        short s2 = 2504;
        cc cc2 = this;
        cc2.c(0, s2);
        this.Y(n2);
        this.Y(n3);
        this.Y(n4);
        this.Y(n5);
        this.Y(n6);
        this.ca();
    }

    public final void l(int n2, int n3) {
        if (n2 != 5) {
            short s2 = 2505;
            cc cc2 = this;
            cc2.c(0, s2);
            this.Y(n2);
            this.Y(n3);
            this.ca();
            return;
        }
        short s3 = 2505;
        cc cc3 = this;
        cc3.c(0, s3);
        this.Y(n2);
        this.Y(n3);
        this.b(cv.s);
        this.ca();
    }

    public final void N(int n2) {
        short s2 = 2709;
        cc cc2 = this;
        cc2.c(0, s2);
        this.b((short)n2);
        this.ca();
    }

    public final void E() {
        short s2 = 2708;
        cc cc2 = this;
        cc2.c(0, s2);
        this.ca();
    }

    public final void b(int n2, short s2) {
        this.c(10, (short)1422);
        this.Y(n2);
        this.b((short)-1);
        this.ca();
    }

    public final void a(short s2, int n2, byte by2) {
        this.c(11, (short)1426);
        this.b(s2);
        this.Y(n2);
        this.a(by2);
        this.ca();
    }

    public final void b(short s2, int n2, byte by2) {
        this.c(11, (short)1428);
        this.b(s2);
        this.Y(n2);
        this.a(by2);
        this.ca();
    }

    public final void c(int n2, String object) {
        byte[] byArray = bg.a((String)object, "UTF-8");
        object = byArray;
        int n3 = byArray.length;
        this.c(n3 + 12, (short)1442);
        this.Y(n2);
        this.Y(n3);
        this.a((byte[])object);
        this.ca();
    }

    public final void a(byte by2, int n2, byte by3) {
        short s2 = 1452;
        cc cc2 = this;
        cc2.c(0, s2);
        this.a(by2);
        this.Y(n2);
        this.a(by3);
        this.ca();
    }

    public final void a(int n2, byte by2, short s2, int n3, byte by3) {
        short s3 = 1454;
        cc cc2 = this;
        cc2.c(0, s3);
        this.Y(n2);
        this.a(by2);
        this.b(s2);
        this.Y(n3);
        this.a(by3);
        this.ca();
    }

    private void cd() {
        int n2 = this.ae();
        if (n2 == 1) {
            n2 = this.ae();
            cv.H.removeAllElements();
            cv.I = -1;
            for (int i2 = 0; i2 < n2; ++i2) {
                cv cv2 = new cv();
                cc cc2 = this;
                new cv().v = cc2.ai();
                int n3 = this.ag();
                cv2.y = this.ag();
                cv2.x = n3;
                if ((n3 >> 7 & 1) > 0) {
                    cv.I = i2;
                    cv2.x = n3 - 128;
                }
                cv2.w = this.ae();
                n3 = this.ae();
                cv2.z = this.m(0, n3);
                cv2.a(cv2.v, 0);
                cv2.a(0);
                cv.H.addElement(cv2);
            }
            bm.l(48);
            bm.j(cv.H.size(), 5);
            cv.t = 0;
            cv.s = 0;
        }
    }

    private void ce() {
        byte by2 = this.ae();
        if (by2 == 1) {
            byte by3 = by2 = this.ae() == 1 ? (byte)1 : 0;
            if (bm.aC == null) {
                bm.aC = new cn(3, 22, 2);
                bm.aC.z();
            } else {
                bm.aC.e();
            }
            cc cc2 = this;
            short s2 = cc2.ai();
            int n2 = this.ae();
            for (int i2 = 0; i2 < n2; ++i2) {
                q q2 = new q(this.j, this.k);
                n n3 = n.a(q2);
                this.k += q2.h();
                bm.aC.a(n3, (int)n3.c);
            }
            q q3 = new q(this.j, this.k);
            cv.a(q3);
            bm.H();
            bm.aa();
            if (by2 == 1) {
                bm.l(48);
            }
            bm.a(7, true);
            cv.s = s2;
            bm.X = 3;
        }
        bm.ae();
    }

    private void cf() {
        byte by2 = this.ae();
        if (by2 == 1) {
            if (bm.aC == null) {
                bm.aC = new cn(3, 22, 2);
                bm.aC.z();
            } else {
                bm.aC.e();
            }
            cc cc2 = this;
            short s2 = cc2.ai();
            int n2 = this.ae();
            cv.D = this.m(0, n2);
            cv.E = this.ae();
            n2 = this.ae();
            for (int i2 = 0; i2 < n2; ++i2) {
                q q2 = new q(this.j, this.k);
                n n3 = n.a(q2);
                this.k += q2.h();
                bm.aC.a(n3, (int)n3.c);
            }
            bm.H();
            bm.aa();
            bm.l(48);
            bm.a(8, true);
            cv.s = s2;
            cn.f();
            bm.X = 3;
        }
        bm.ae();
    }

    private void cg() {
        byte by2 = this.ae();
        if (by2 == 1) {
            bm.a(bm.A(1099), true);
            cc cc2 = this;
            short s2 = cc2.ai();
            cv.I = this.ae();
            byte by3 = this.ae();
            String string = this.m(0, by3);
            byte by4 = this.ae();
            byte by5 = this.ae();
            byte by6 = this.ae();
            byte by7 = this.ae();
            bm.cb.a(s2, string, by4, by5, by6, by7);
        }
        bm.ae();
    }

    private void ch() {
        byte by2 = this.ae();
        if (by2 == 1) {
            bm.a(bm.A(1100), true);
            if (bm.cb != null) {
                bm.cb.M();
            }
            cv.I = -1;
        }
        bm.ae();
    }

    private void ci() {
        int n2 = this.ae();
        if (n2 == 1) {
            n2 = this.aj();
            cc cc2 = this;
            short s2 = cc2.ai();
            byte by2 = this.ae();
            String string = this.m(0, by2);
            byte by3 = this.ae();
            byte by4 = this.ae();
            byte by5 = this.ae();
            byte by6 = this.ae();
            cr cr2 = bh.e(n2);
            if (cr2 != null) {
                cr2.a(s2, string, by3, by4, by5, by6);
            }
        }
    }

    private void cj() {
        int n2;
        cr cr2;
        byte by2 = this.ae();
        if (by2 == 1 && (cr2 = bh.e(n2 = this.aj())) != null) {
            cr2.M();
        }
    }

    private void ck() {
        byte by2 = this.ae();
        if (by2 == 1) {
            cc cc2 = this;
            short s2 = cc2.ai();
            byte by3 = this.ae();
            cv cv2 = null;
            if (s2 < cv.H.size()) {
                cv2 = (cv)cv.H.elementAt(s2);
            }
            if (cv2 != null) {
                cv2.w = by3;
            }
            bm.a(bg.a(bm.A(1101), (int)by3), true);
        }
    }

    private void cl() {
        int n2 = this.ae();
        if (n2 == 1) {
            this.ae();
            n2 = this.aj();
            int n3 = this.aj();
            cr cr2 = bh.e(n3);
            if (cr2 != null) {
                if (cr2 == bm.cb) {
                    bm.a(bm.A(1102) + n2 + "\uff01", true);
                }
                if (n2 > 10) {
                    bm.q(3);
                }
            }
        }
        bm.ae();
    }

    private void cm() {
        int n2 = this.ae();
        if (n2 == 1) {
            cv cv2;
            n2 = this.aj();
            int n3 = this.aj();
            if (n2 < cv.H.size() && n2 >= 0 && (cv2 = (cv)cv.H.elementAt(n2)) != null) {
                cv2.y = n3;
                cv.G[3] = n3;
            }
            if (n3 <= 10) {
                bm.p(3);
                if (bm.cb.U < 30) {
                    k.a(85, 61, 180, 1, 5000, bm.B(377));
                    return;
                }
            } else if (n3 > 10) {
                bm.q(3);
            }
        }
    }

    private void cn() {
        byte by2 = this.ae();
        if (by2 == 1) {
            bm.a(bm.A(1103), true);
            this.a((byte)0, 0, (byte)0);
        }
        bm.ae();
    }

    private void co() {
        byte by2 = this.ae();
        if (by2 == 1) {
            cc cc2 = this;
            short s2 = cc2.ai();
            if (s2 != -1 && s2 == cv.I) {
                s2 = this.ae();
                bm.cb.aX.z = this.m(0, s2);
            }
            bm.a(bm.A(1104), true);
            this.a((byte)0, 0, (byte)0);
            return;
        }
        bm.a(bm.A(1105), true);
    }

    private void cp() {
        cr cr2;
        int n2 = this.ae();
        if (n2 == 1 && (cr2 = bh.e(n2 = this.aj())) != null && cr2.aX != null) {
            byte by2 = this.ae();
            cr2.aX.z = this.m(0, by2);
        }
    }

    private void cq() {
        byte by2 = this.ae();
        int n2 = this.ae();
        int n3 = this.aj();
        int n4 = this.ae();
        cv.L = (byte)n2;
        cv.M = n3;
        cv.N = (byte)n4;
        if (by2 == 1) {
            by2 = this.ae();
            cv.H.removeAllElements();
            cv.I = -1;
            for (n2 = 0; n2 < by2; ++n2) {
                cv cv2 = new cv();
                cc cc2 = this;
                new cv().v = cc2.ai();
                n4 = this.ag();
                cv2.y = this.ag();
                cv2.x = n4;
                if ((n4 >> 7 & 1) > 0) {
                    cv.I = n2;
                    cv2.x = n4 - 128;
                }
                cv2.w = this.ae();
                n4 = this.ae();
                cv2.z = this.m(0, n4);
                cv2.a(cv2.v, 0);
                cv2.a(0);
                cv.H.addElement(cv2);
            }
            bm.l(48);
            bm.j(cv.H.size(), 5);
            cv.t = 0;
            cv.s = 0;
        }
    }

    public final void F() {
        this.c(4, (short)2600);
        this.ca();
    }

    public final void O(int n2) {
        this.c(8, (short)2602);
        this.Y(n2);
        this.ca();
    }

    public final void P(int n2) {
        this.c(8, (short)2604);
        this.Y(n2);
        this.ca();
    }

    public final void Q(int n2) {
        this.c(8, (short)2606);
        this.Y(n2);
        this.ca();
    }

    private void cr() {
        int n2 = this.ae();
        if (n2 == 1) {
            this.aj();
            n2 = this.ae();
            aw.e.removeAllElements();
            aw aw2 = new aw();
            new aw().a = bm.A(1106);
            aw2.d = -1;
            aw2.c = -13032703;
            aw2.b = -597591;
            aw.e.addElement(aw2);
            for (int i2 = 0; i2 < n2; ++i2) {
                aw aw3 = new aw();
                new aw().d = this.ah();
                this.ah();
                aw3.b = this.aj();
                aw3.c = this.aj();
                byte by2 = this.ae();
                aw3.a = this.m(0, by2);
                aw.e.addElement(aw3);
            }
            aw.g = 0;
            aw.h = 0;
            bm.l(70);
            aw.a(1);
        }
    }

    private void cs() {
        byte by2 = this.ae();
        if (by2 == 1) {
            by2 = this.ae();
            aw.f = this.m(0, by2);
            aw.a(4);
        }
    }

    private void ct() {
        int n2 = this.ae();
        if (n2 != 4) {
            n2 = this.aj();
            if (n2 != -1) {
                bm.cb.af = this.aj();
                bm.cb.ag = this.aj();
                n2 = this.ae();
                bm.cb.ae = this.m(0, n2);
            } else {
                bm.cb.ae = null;
            }
            aw.a(1);
            return;
        }
        cc cc2 = this;
        bm.cb.ah = cc2.ai();
        this.an();
    }

    private void cu() {
        byte by2 = ((cc)object).ae();
        if (by2 != 4) {
            int n2 = ((cc)object).ae();
            Object object = ((cc)object).m(0, n2);
            for (n2 = 0; n2 < aw.e.size(); ++n2) {
                aw aw2 = (aw)aw.e.elementAt(n2);
                if (!aw2.a.equals(object)) continue;
                aw.e.removeElementAt(n2);
                if (by2 != 1) {
                    object = bm.A(1107) + (String)object + "\u3011\uff01";
                    bm.a((String)object, true);
                    return;
                }
                break;
            }
        } else {
            bm.cb.ah = -1;
        }
    }

    private void cv() {
        int n2 = this.ae();
        if (n2 != 4) {
            n2 = this.aj();
            cr cr2 = bh.e(n2);
            if (cr2 != null) {
                int n3 = this.aj();
                if (n3 != -1) {
                    cr2.af = this.aj();
                    cr2.ag = this.aj();
                    n3 = this.ae();
                    cr2.ae = this.m(0, n3);
                    return;
                }
                cr2.ae = null;
                return;
            }
        } else {
            n2 = this.aj();
            cr cr3 = bh.e(n2);
            if (cr3 != null) {
                cr3.ah = this.ai();
            }
        }
    }

    public final void a(int n2, int n3, String object, byte by2) {
        byte[] byArray = bg.a((String)object, "UTF-8");
        object = byArray;
        int n4 = byArray.length;
        this.c(n4 + 14, (short)1204);
        if (bm.cf != null) {
            this.a((byte)1);
            this.Y(bm.cf.C);
        } else {
            this.a((byte)0);
            this.Y(cn.f);
        }
        this.a((int)((short)n2));
        this.a((byte)n3);
        this.a(by2);
        this.a((byte)n4);
        this.a((byte[])object);
        this.ca();
    }

    public final void a(int n2, int n3, String object) {
        byte[] byArray = bg.a((String)object, "UTF-8");
        object = byArray;
        int n4 = byArray.length;
        this.c(n4 + 10, (short)1200);
        this.Y(n2);
        this.a((byte)n3);
        this.a((byte)n4);
        this.a((byte[])object);
        this.ca();
    }

    public final void d(String object) {
        byte[] byArray = bg.a((String)object, "UTF-8");
        object = byArray;
        int n2 = byArray.length;
        this.c(n2 + 5, (short)1203);
        this.a((byte)n2);
        this.a((byte[])object);
        this.ca();
    }

    private void cw() {
        bm.cZ = this.al();
        bm.da = this.ae();
        bm.db = this.al();
        byte by2 = this.ae();
        bm.cX = this.m(0, by2);
        by2 = this.ae();
        bm.cY = this.m(0, by2);
        bm.a(23, bm.A(249), "^b" + bm.cX + bm.A(1108) + bm.da + bm.A(1109) + bm.cY + "^b", 282, 30000L);
    }

    private void cx() {
        String[] stringArray = stringArray.am();
        String cfr_ignored_0 = "TempDebug Start receive CMG_STR = " + (String)stringArray;
        stringArray = bg.b((String)stringArray, ";");
        bm.bg = (byte)stringArray.length;
        bm.bf = (byte)bg.b(stringArray[0], ",").length;
        bm.bh = new String[bm.bg][bm.bf];
        for (int i2 = 0; i2 < bm.bg; ++i2) {
            for (int i3 = 0; i3 < bm.bf; ++i3) {
                if (i3 >= bg.b(stringArray[i2], ",").length) continue;
                bm.bh[i2][i3] = bg.b(stringArray[i2], ",")[i3];
            }
        }
        bm.ae();
    }

    private void cy() {
        int n2 = this.aj();
        String cfr_ignored_0 = "receive uiStyle = " + n2;
        int n3 = this.aj();
        String cfr_ignored_1 = "receive CMG_COIN = " + n3;
        bm.ae();
    }

    private void cz() {
        byte by2 = this.ae();
        if (by2 == 1) {
            cs.a(3);
        } else if (by2 == 0) {
            bm.d(bm.A(1112));
        }
        bm.ae();
    }

    private void cA() {
        byte by2 = this.ae();
        int n2 = this.aj();
        byte by3 = this.ae();
        String string = null;
        if (by3 == 1) {
            string = this.an();
        }
        if (by2 == 0) {
            bm.p(n2);
            k.a(85, 61, 180, 1, 5000, string);
            if (n2 == 20) {
                bm.ba[3] = bm.m();
                return;
            }
        } else if (by2 == 1) {
            bm.q(n2);
        }
    }

    private void cB() {
        byte by2 = this.ae();
        cs.b = this.an();
        cs.c = this.an();
        bm.W();
        if (by2 == 1) {
            bm.a(26, bm.A(1113), bg.b(bm.A(1114), cs.b, cs.c), 0);
        } else {
            bm.d(bm.A(1115));
        }
        bm.ae();
    }

    public final void G() {
        if (cr.aV == null || bm.cb.m() || bm.cb.s(8)) {
            return;
        }
        if (cr.aV.e()) {
            this.c(8, (short)643);
            this.Y(cr.aV.C);
            this.ca();
        }
    }

    public final void c(byte by2, int n2, int n3) {
        this.c(13, (short)644);
        this.a((byte)0);
        this.Y(n2);
        this.Y(0);
        this.ca();
    }

    public final void R(int n2) {
        this.c(8, (short)645);
        this.Y(n2);
        this.ca();
    }

    public final void e(String object) {
        if (object == null) {
            object = "";
        }
        byte[] byArray = bg.a((String)object, "UTF-8");
        object = byArray;
        int n2 = byArray.length;
        this.c(n2 + 12, (short)2153);
        this.Y(bm.ae);
        this.Y(n2);
        this.a((byte[])object);
        this.ca();
    }

    public final void a(String[] stringArray) {
        if (stringArray == null) {
            return;
        }
        this.c(0, (short)2154);
        this.Y(bm.ae);
        this.a((byte)stringArray.length);
        for (int i2 = 0; i2 < stringArray.length; ++i2) {
            this.f(stringArray[i2]);
        }
        this.ca();
    }

    public final void d(int n2, String object) {
        if (object == null) {
            return;
        }
        object = bg.a((String)object, "UTF-8");
        this.c(12 + ((Object)object).length, (short)338);
        this.Y(n2);
        this.Y(((Object)object).length);
        this.a((byte[])object);
        this.ca();
    }

    public final void a(int n2, int n3, int n4, int n5) {
        this.c(20, (short)1125);
        this.Y(n2);
        this.Y(n3);
        this.Y(n4);
        this.Y(n5);
        this.ca();
    }

    public cc() {
        v0.g = new byte[1024];
    }

    public final boolean a(String string, boolean bl2) {
        return this.a(string, true, null);
    }

    public final boolean a(String string, boolean bl2, String[] stringArray) {
        if (this.f != null && this.f.d()) {
            this.f.c();
            this.f = null;
        }
        this.q = bl2;
        this.f = new ct(string, bl2, stringArray);
        return false;
    }

    public final boolean H() {
        if (this.f == null) {
            return false;
        }
        return this.f.d();
    }

    public final void I() {
        long l2;
        if (this.f != null && this.f.b == 2 && this.d > 0L && (l2 = bm.m() - this.d) > 30000L) {
            this.f.c();
        }
        this.ap();
    }

    public final void J() {
        if (this.f != null) {
            this.f.c();
            this.f.b();
            this.f.d = false;
            this.f = null;
        }
        this.d = 0L;
        this.g = null;
        cs.i = false;
        am.n = false;
    }

    private void cC() {
        this.d(false);
    }

    private void d(boolean bl2) {
        try {
            int n2 = this.aj();
            int n3 = this.aj();
            r r2 = bh.d(n2);
            if (r2 == null) {
                r2 = new r();
            }
            byte by2 = this.ae();
            byte by3 = this.ae();
            cc cc2 = this;
            short s2 = cc2.ai();
            byte by4 = this.ae();
            int n4 = this.aj();
            cc2 = this;
            short s3 = cc2.ai();
            cc2 = this;
            short s4 = cc2.ai();
            byte by5 = this.ae();
            String string = this.m(0, by5);
            byte by6 = this.ae();
            short s5 = 0;
            short s6 = 0;
            if (by2 == 11 || by2 == 12) {
                cc cc3 = this;
                s5 = cc3.ai();
                cc3 = this;
                s6 = cc3.ai();
                boolean bl3 = r2.A = this.ae() == 1;
                if (by2 == 12) {
                    r2.x();
                    r2.p(45);
                }
            }
            r2.H = (byte)100;
            r2.v = null;
            if (n4 == bm.cj) {
                if (bl2) {
                    byte by7;
                    byte by8;
                    boolean bl4 = this.ae() == 1;
                    if (bl4) {
                        byte by9 = this.ae();
                        by8 = this.ae();
                        r2.v = new cr();
                        r2.v.a("", 0, by8, (byte)0);
                        r2.v.ai = by9;
                        this.a(r2.v, false);
                    }
                    if ((by7 = this.ae()) == 1) {
                        by8 = this.ae();
                        r2.v.ae = this.m(0, by8);
                        r2.v.af = this.aj();
                        r2.v.ag = this.aj();
                    } else {
                        r2.v.ae = null;
                    }
                }
                r2.c = s2;
                int n5 = bm.z(s2);
                r2.d = r2.v == null ? ck.b(n5) : r2.v.d;
                r2.a(string, n2, n3);
                r2.b(n4, s3, s4, by3);
                r2.r = by2;
                r2.s = by6;
                r2.t = s5;
                r2.u = s6;
                r2.p = by4;
                bh.z.addElement(r2);
            }
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    private void a(byte by2, bh bh2, bh bh3) {
        this.a(by2);
        if (bh2 != null) {
            this.Y(bh2.C);
        } else {
            this.Y(cr.bb);
        }
        if (bh3 != null) {
            this.Y(bh3.C);
        } else {
            this.Y(cr.bc);
        }
        String cfr_ignored_0 = "send order of pk " + by2 + "challenger id" + bh2.E + "accepter id" + bh3.E;
    }

    final void a(bh bh2, bh bh3) {
        this.c(13, (short)1260);
        this.a((byte)1, bh2, bh3);
        this.ca();
    }

    final void b(bh bh2, bh bh3) {
        this.c(13, (short)1260);
        this.a((byte)2, bh2, bh3);
        this.ca();
    }

    final void c(bh bh2, bh bh3) {
        this.c(13, (short)1260);
        this.a((byte)3, bh2, bh3);
        this.ca();
    }

    final void d(bh bh2, bh bh3) {
        this.c(13, (short)1260);
        this.a((byte)5, bh2, bh3);
        this.ca();
    }

    private void cD() {
        int n2 = this.ae();
        int n3 = this.aj();
        int n4 = this.aj();
        cr cr2 = bh.e(n3);
        cr cr3 = bh.e(n4);
        String cfr_ignored_0 = "receive order of pk:" + n2 + "challenger id" + n3 + "accepter id" + n4;
        switch (n2) {
            case 6: {
                if (cr3 == null || cr3 != bm.cb || cr2 == null || cr2.F != cr3.F || cr2.m() || cr3.m()) break;
                if (bm.Y()) {
                    cr cr4 = cr3;
                    cr cr5 = cr2;
                    this.c(13, (short)1260);
                    this.a((byte)4, cr5, cr4);
                    this.ca();
                    return;
                }
                if (ag.j[6] != 0) {
                    this.c(cr2, cr3);
                    return;
                }
                cr.a(cr2, cr3);
                bm.a(3, bm.B(162), "^b" + cr2.E + "^b" + bm.B(263), 2, 30000L);
                return;
            }
            case 7: {
                if (cr2 == null || cr2 != bm.cb || cr3 == null) break;
                bm.a(cr3.E + bm.B(264), true);
                return;
            }
            case 11: {
                if (cr2 != null && cr2 == bm.cb && cr3 != null) {
                    bm.a(cr3.E + bm.A(1116), true);
                }
                if (cr2 == null || cr3 != bm.cb || cr3 == null) break;
                bm.a(cr2.E + bm.A(1116), true);
                return;
            }
            case 8: {
                if (cr2 == null || cr2 != bm.cb || cr3 == null) break;
                bm.a(cr3.E + bm.B(265), true);
                return;
            }
            case 9: {
                n2 = this.ah();
                int n5 = this.ah();
                int n6 = this.aj();
                bm.cb.a(cr2, cr3, n3, n4, n2, n5, n6);
                bm.aa();
                return;
            }
            case 10: {
                boolean bl2;
                boolean bl3 = bl2 = this.ae() != 0;
                if (!cr.P()) break;
                bm.cb.f(bl2);
            }
        }
    }

    final void K() {
        if (bm.cb != null) {
            short s2 = 400;
            Object object = this;
            ((cc)object).c(0, s2);
            this.Y(bm.P);
            this.Y(bm.cb.C);
            this.Y(bm.cb.F);
            this.b((short)bm.cb.k);
            this.b((short)bm.cb.l);
            this.a(bm.ar);
            String cfr_ignored_0 = "LOGIN KEY = " + bm.N;
            object = bm.N;
            object = bg.a((String)object, "UTF-8");
            this.Y(((Object)object).length);
            this.a((byte[])object);
            this.ca();
        }
    }

    private void aa(int n2) {
        short s2 = 2253;
        cc cc2 = this;
        cc2.c(0, s2);
        this.Y(n2);
        this.ca();
    }

    final void S(int n2) {
        short s2 = 966;
        cc cc2 = this;
        cc2.c(0, s2);
        this.b((short)n2);
        this.ca();
    }

    final void c(byte by2) {
        short s2 = 2303;
        cc cc2 = this;
        cc2.c(0, s2);
        this.a(by2);
        this.ca();
    }

    final void L() {
        short s2 = 2701;
        cc cc2 = this;
        cc2.c(0, s2);
        this.a((byte)ag.j.length);
        this.a(ag.j);
        this.ca();
    }

    final void a(int[] nArray) {
        int n2 = 2707;
        cc cc2 = this;
        cc2.c(0, (short)n2);
        int n3 = 0;
        for (n2 = 0; n2 < nArray.length; ++n2) {
            if (nArray[n2] == 0) continue;
            n3 = (byte)(n3 + 1);
        }
        this.a((byte)n3);
        if (n3 == 0) {
            this.ca();
            return;
        }
        for (n2 = 0; n2 < nArray.length; ++n2) {
            if (nArray[n2] == 0) continue;
            n3 = n2 + 1;
            i i2 = i.a(nArray[n2] & 0xFFFFFFF);
            i2.a(true);
            i2.n &= 0x80;
            i2.n |= n3;
            this.Y(nArray[n2] & 0xFFFFFFF);
            this.Y(i2.n);
        }
        this.ca();
    }

    final void M() {
        short s2 = 2702;
        Object object = this;
        ((cc)object).c(0, s2);
        object = ag.a(a.b);
        this.a((byte)((Object)object).length);
        Object object2 = object;
        object = this;
        for (int i2 = 0; i2 < ((Object)object2).length; ++i2) {
            super.b((short)object2[i2]);
        }
        this.ca();
        ag.a();
    }

    final void N() {
        short s2 = 2706;
        cc cc2 = this;
        cc2.c(0, s2);
        this.a((byte)ag.e.length);
        this.a(ag.e);
        this.ca();
    }

    public final void O() {
        Object object = ak.a();
        short s2 = 3051;
        Object object2 = this;
        ((cc)object2).c(0, s2);
        this.Y(ak.b);
        this.Y(ak.a);
        object2 = object;
        object = this;
        byte[] byArray = bg.a((String)object2, "UTF-8");
        object2 = byArray;
        if (byArray == null) {
            super.Y(0);
        } else {
            super.b((short)((Object)object2).length);
            super.a((byte[])object2);
        }
        this.ca();
    }

    public final void a(boolean bl2) {
        short s2 = 653;
        cc cc2 = this;
        cc2.c(0, s2);
        this.a(bl2 ? (byte)1 : 0);
        this.ca();
    }

    private void a(cr cr2, boolean bl2) {
        if (cr2 == null) {
            return;
        }
        if (bl2) {
            boolean bl3 = false;
            for (int i2 = 0; i2 < 20; ++i2) {
                byte by2 = (byte)i2;
                cc cc3 = this;
                short s3 = cc3.ai();
                cr2.l(by2, s3);
            }
            this.aj();
            return;
        }
        int n2 = this.aj();
        for (int i3 = 0; i3 < 20; ++i3) {
            int n3 = 1 << i3;
            if ((n3 & n2) == 0) continue;
            cc cc2 = this;
            short s2 = cc2.ai();
            cr2.l(i3, s2);
        }
    }

    final void e(int n2, String string) {
        short s2 = 3301;
        cc cc2 = this;
        cc2.c(0, s2);
        this.Y(n2);
        this.f(string);
        this.ca();
    }

    private static void cE() {
        bm.ax();
        bm.bn.a((byte)2, 0, l.g[1]);
        bm.ad();
    }

    private void cF() {
        cn.ag = this.ae();
        bm.aK = this.am();
        cn.ad = this.am();
        cn.ae = this.am();
        cn.ai = this.aj();
        bm.aG = new cn(4, 7, 0);
        bm.aG.z();
        bm.aG.e();
        bm.a(10, true);
        byte by2 = this.ae();
        int[][] nArray = new int[by2][2];
        for (int i2 = 0; i2 < nArray.length; ++i2) {
            nArray[i2][0] = this.ae();
            nArray[i2][1] = this.aj();
        }
        bm.aA.a(nArray);
        bm.aA.h();
        cn.ac = -1;
        bm.ae();
    }

    private void cG() {
        ai.a();
        byte by2 = ((cc)((Object)q2)).ae();
        String string = ((cc)((Object)q2)).an();
        String string2 = ((cc)((Object)q2)).an();
        ai.a.a(string, string2);
        ai.a.b(1);
        q q2 = new q(((cc)((Object)q2)).j, ((cc)((Object)q2)).k);
        switch (by2) {
            case 2: {
                ah.a = new ah(0, 0, 640, 360);
                ai.a(2);
                bm.X = 3;
                ah.a.a(q2);
                break;
            }
            case 3: {
                co.a = new co(0, 0, 640, 360);
                co.a.a();
                co.a.a(q2);
            }
        }
        ai.a.b(2);
    }

    final void T(int n2) {
        short s2 = 3304;
        cc cc2 = this;
        cc2.c(0, s2);
        this.Y(n2);
        this.ca();
    }

    private void cH() {
        int n2;
        int n3 = n2 = this.aj();
        co co2 = co.a;
        co2.b.a(n3);
    }

    final void U(int n2) {
        short s2 = 3306;
        cc cc2 = this;
        cc2.c(0, s2);
        this.Y(n2);
        this.ca();
    }

    private void cI() {
        int n2 = this.aj();
        byte by2 = this.ae();
        co co2 = co.a;
        co2.b.b(n2);
    }

    private void cJ() {
        q q2 = new q(((cc)((Object)q2)).j, ((cc)((Object)q2)).k);
        co.a.a(q2);
    }

    final void V(int n2) {
        short s2 = 3308;
        cc cc2 = this;
        cc2.c(0, s2);
        this.Y(n2);
        this.ca();
    }

    final void a(int n2, int n3, int n4, int[][] nArray) {
        short s2 = 3310;
        cc cc2 = this;
        cc2.c(0, s2);
        this.Y(n2);
        this.Y(n3);
        this.Y(n4);
        this.Y(nArray.length);
        for (n2 = 0; n2 < nArray.length; ++n2) {
            this.a((byte)nArray[n2][0]);
            this.Y(nArray[n2][1]);
        }
        this.ca();
    }

    private void cK() {
        int n2 = this.aj();
        int[][] nArray = new int[n2][3];
        for (int i2 = 0; i2 < n2; ++i2) {
            nArray[i2][0] = this.ae();
            nArray[i2][1] = this.ae();
            nArray[i2][2] = this.aj();
        }
        String string = this.an();
        ah.a.a(string);
        ah.a.a(nArray);
        ai.a.c();
    }
}

