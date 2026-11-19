/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import java.util.Vector;
import javax.microedition.lcdui.Graphics;

public final class cr
extends bh
implements bj {
    String q;
    int[] r;
    byte s;
    byte t;
    byte u;
    byte v;
    byte U;
    int V;
    String W;
    String X;
    int Y;
    int Z;
    byte aa;
    int ab;
    int ac = 1;
    int ad;
    String ae;
    int af;
    int ag;
    int ah;
    byte ai;
    byte aj;
    byte ak;
    public static i al = null;
    public static int am = -1;
    public static int an;
    private int bj;
    private int bk;
    n[] ao;
    boolean ap;
    private static int bl;
    static int aq;
    static int ar;
    public static byte as;
    static int at;
    static int au;
    static int av;
    static int aw;
    static int ax;
    static int ay;
    static int az;
    static int aA;
    static int aB;
    static int aC;
    static boolean aD;
    static int aE;
    static int aF;
    static int aG;
    static int aH;
    static int aI;
    static int aJ;
    static int aK;
    static int aL;
    static int aM;
    static int aN;
    static long aO;
    static long aP;
    static int aQ;
    static int aR;
    static int aS;
    String aT;
    static int aU;
    static bh aV;
    private int bm;
    private int bn;
    private int bo;
    private int bp;
    private static int[] bq;
    private static int[] br;
    private static int[] bs;
    private static int[] bt;
    private static int[] bu;
    private int bv;
    private int bw;
    private int bx;
    private int by;
    cy aW;
    private cy bz;
    private cy bA;
    private cy bB;
    private cy bC;
    private cy bD;
    cv aX;
    static int aY;
    private static long bE;
    static cr aZ;
    static cr ba;
    static int bb;
    static int bc;
    private static int bF;
    private static int bG;
    static int bd;
    private String bH;
    private static int bI;
    private static int bJ;
    private static bh bK;
    static int be;
    private static long bL;
    private boolean bM;
    private static long bN;
    private static int bO;
    static int bf;
    static int bg;
    static int bh;
    private static int bP;
    private static byte[] bQ;
    private byte[] bR;
    int[] bi;
    private int bS;
    private int bT;
    private int bU;
    private static int[][] bV;
    private static byte[][] bW;
    private static byte[][][] bX;

    public cr() {
        bm.A(163);
        this.ah = -1;
        this.ao = new n[9];
        this.ap = false;
        this.bm = 0;
        this.bn = 0;
        this.bo = -1;
        this.aX = null;
        this.bM = false;
        this.bi = new int[]{-1, -1, -1};
        this.bS = 180;
        this.bT = 290;
        this.bU = 60;
        this.a = 0;
        this.r = new int[15];
    }

    public final void i(int n2, int n3) {
        if (n2 >= 0 && n2 < 15 && n3 != this.r[n2]) {
            this.r[n2] = n3;
            if (n2 == 6 && this.r()) {
                n2 = this.d(this.k, this.l, this.L, this.M);
                this.a(this.L, this.M, n2);
            }
        }
    }

    public final boolean h() {
        return this.aj == 2;
    }

    public final int i() {
        return this.ai * 51;
    }

    static boolean j() {
        return am != -1;
    }

    final void t(int n2) {
        this.bj |= n2;
    }

    private void B(int n2) {
        this.bj &= (n2 ^= 0xFFFFFFFF);
    }

    final boolean k() {
        return (this.bj & 2) != 0;
    }

    final boolean B() {
        return (this.bj & 4) != 0;
    }

    static void C() {
        for (int i2 = 0; i2 < bm.bq.size(); ++i2) {
            cr cr2 = (cr)bm.bq.elementAt(i2);
            if (cr2 == null) continue;
            cr.j(cr2.C, 2);
        }
        bm.bq.removeAllElements();
        bt.l[5] = 0;
        bt.k[5] = 0;
        bt.o[5] = false;
        if (bt.e == 5) {
            bt.a(5);
        }
    }

    static void D() {
        for (int i2 = 0; i2 < bm.br.size(); ++i2) {
            cr cr2 = (cr)bm.br.elementAt(i2);
            if (cr2 == null) continue;
            cr.j(cr2.C, 4);
        }
        bm.br.removeAllElements();
    }

    public static void E() {
        if (!bm.cb.X() && bm.bn != null) {
            bm.bn.l();
        }
    }

    static void j(int n2, int n3) {
        cr cr2 = bh.e(n2);
        if (cr2 != null) {
            cr2.B(n3);
            if (n3 == 4) {
                cr2.ab = 0;
                cr2.aa = 0;
                cr2.ad = 0;
            }
        }
    }

    static void u(int n2) {
        cr cr2 = bh.e(n2);
        if (cr2 != null) {
            cr2.t(2);
        }
    }

    static void a(int n2, int n3, byte by2) {
        cr cr2 = bh.e(n2);
        if (cr2 != null) {
            cr2.t(4);
            cr2.ab = n3;
            cr2.aa = by2;
        }
    }

    static void v(int n2) {
        if (n2 == bm.cb.C) {
            cr.D();
            bm.a(bm.A(605), true);
            w.l = "";
            cr.D();
            return;
        }
        cr cr2 = bh.a(bm.br, n2);
        if (cr2 != null) {
            bm.a(cr2.E + bm.A(606), true);
            bh.c(bm.br, n2);
            cr.j(n2, 4);
        }
    }

    static void a(int n2, byte by2) {
        bm.X = 3;
        cr cr2 = bh.a(bm.br, n2);
        if (cr2 != null) {
            if (by2 == 1) {
                w.j = cr2.E;
            }
            if (by2 > 0 && by2 <= 5) {
                bm.a(cr2.E + bm.A(607) + bn.k[by2], true);
            }
            cr2.aa = by2;
            cr cr3 = bh.e(n2);
            if (cr3 != null) {
                cr3.aa = by2;
            }
        }
    }

    static void a(cr object) {
        if (object == null) {
            return;
        }
        cr cr2 = bh.a(bm.br, ((bh)object).C);
        if (cr2 != null) {
            ((cr)object).t(4);
        } else {
            ((cr)object).B(4);
        }
        cr2 = bh.a(bm.bq, ((bh)object).C);
        if (cr2 != null) {
            ((cr)object).t(2);
            if (bm.cp) {
                object = bg.d(bm.A(608), ((bh)object).E);
                bm.a((String)object, true);
                return;
            }
        } else {
            ((cr)object).B(2);
        }
    }

    final void a(String string, int n2, byte by2, byte by3) {
        cr cr2 = this;
        this.bR = new byte[1];
        cr2.bR[0] = 0;
        this.u = by2;
        this.v = by3;
        this.c = this.u;
        this.d = ck.b(74 + this.c);
        this.a(string, n2, 0);
        cr2 = this;
        this.aW = new cy();
        cr2.aW.a(ck.b(68 + cr2.u));
        cr2.bv = 2;
        cr2.bw = -100;
        cr2.bx = -100;
        cr2.by = 0;
        cr2.aW.a(cr2.bv, true);
        cr2 = this;
        this.bB = new cy();
        cr2.bB.a(ck.b(291));
        cr2.bB.a(0, true);
        cr2.bA = new cy();
        cr2.bA.a(ck.b(291));
        cr2.bA.a(1, true);
        cr2.bz = new cy();
        cr2.bz.a(ck.b(291));
        cr2.bz.a(2, true);
        cr2.bC = new cy();
        cr2.bC.a(ck.b(292));
        cr2.bC.a(0, true);
        cr2.bD = new cy();
        cr2.bD.a(ck.b(293));
        cr2.bD.a(0, true);
        for (int i2 = 0; i2 < 20; ++i2) {
            this.l(i2, 0);
        }
        this.w();
    }

    final void w(int n2) {
        if (this.B && 5 == n2) {
            if (this.ai == 0) {
                al.a(14, 0, 0);
            } else {
                al.a(13, 0, 0);
            }
        }
        if (this.X() && this.J != 0 && this.J != 3 && this.J != 2 && this.J != 4 && this.J != 7) {
            this.e(true);
        }
        this.J = (byte)n2;
        this.a(this.D(this.J));
        switch (this.J) {
            case 5: 
            case 6: {
                if (this != bm.cb) break;
                cr.G(0);
                return;
            }
            case 0: {
                if (this == bm.cb) {
                    aD = false;
                }
                this.s();
                this.E(0);
                return;
            }
            case 2: {
                this.E(1);
            }
        }
    }

    final int[] F() {
        try {
            int n2;
            int n3;
            int[] nArray;
            if (this.G()) {
                this.aX.a();
                this.h = aq.a(this.h, this.aX.h);
            }
            if ((nArray = this.a(null, n3 = (this.k - bm.bW) * 3 >> 1, n2 = (this.l - bm.bX) * 3 >> 1, this.e, true)) != null) {
                this.h = aq.a(this.h, nArray);
            }
        }
        catch (Exception exception) {}
        return this.h;
    }

    final void a(Graphics graphics, int n2, int n3, boolean bl2) {
        this.a(graphics, n2, n3, bl2, true);
    }

    final void a(Graphics graphics, int n2, int n3, boolean bl2, boolean bl3) {
        int n4;
        cr cr2;
        Graphics graphics2;
        int n5;
        int n6;
        if (bl2) {
            this.h(2);
            this.a(this.Z());
        } else {
            this.bk = this.G;
        }
        if (bl3 && ag.a(11) == 1 && (this.bi[0] >= 0 || this.bi[1] >= 0 || this.bi[2] >= 0)) {
            n6 = n3;
            n5 = n2;
            graphics2 = graphics;
            cr2 = this;
            cr2.bC.c();
            cr2.bC.a(graphics2, n5, n6);
            int[] nArray = this.bi;
            n6 = n3;
            n5 = n2;
            graphics2 = graphics;
            cr2 = this;
            block10: for (n4 = 0; n4 < nArray.length; ++n4) {
                if (nArray[n4] < 0 || nArray[n4] > 2) continue;
                int n7 = n4;
                int n8 = nArray[n4];
                int n9 = n6;
                int n10 = n5;
                Graphics graphics3 = graphics2;
                cr cr3 = cr2;
                cr3.bS += 5;
                cr3.bT += 5;
                cr3.bU += 5;
                if (cr3.bS >= 360) {
                    cr3.bS = 0;
                }
                if (cr3.bT >= 360) {
                    cr3.bT = 0;
                }
                if (cr3.bU >= 360) {
                    cr3.bU = 0;
                }
                switch (n7) {
                    case 0: {
                        n10 += bV[cr3.bS / 5][0];
                        n9 += bV[cr3.bS / 5][1];
                        break;
                    }
                    case 1: {
                        n10 += bV[cr3.bT / 5][0];
                        n9 += bV[cr3.bT / 5][1];
                        break;
                    }
                    case 2: {
                        n10 += bV[cr3.bU / 5][0];
                        n9 += bV[cr3.bU / 5][1];
                    }
                }
                n9 += 30;
                switch (n8) {
                    case 0: {
                        cr3.bB.c();
                        cr3.bB.a(graphics3, n10, n9);
                        continue block10;
                    }
                    case 1: {
                        cr3.bz.c();
                        cr3.bz.a(graphics3, n10, n9);
                        continue block10;
                    }
                    case 2: {
                        cr3.bA.c();
                        cr3.bA.a(graphics3, n10, n9);
                    }
                }
            }
        }
        this.a(graphics, n2, n3, this.e, false);
        ao.a = this;
        this.b(graphics, n2, n3);
        ao.a = null;
        if (this.ap && ag.a(11) == 1) {
            n6 = n3;
            n5 = n2;
            graphics2 = graphics;
            cr2 = this;
            cr2.bD.c();
            cr2.bD.a(graphics2, n5, n6);
            if (cr2.bD.a()) {
                cr2.ap = false;
                cr2.bD.b();
            }
        }
        if (bl3) {
            graphics2 = graphics;
            cr2 = this;
            if (cr2.aW != null && cr2.bp != 0 && cr2.bv >= 0 && cr2.bw > 0 && cr2.bx > 0) {
                cr2.aW.c();
                int[] nArray = new int[4];
                cr2.aW.a.a(0, cr2.bp - 1, nArray, 0);
                n6 = nArray[3];
                int n11 = nArray[2];
                n4 = cr2.bw;
                int n12 = cr2.bx;
                if ((cr2.by & 4) != 0) {
                    n4 += n6 / 2;
                    n12 += n11 / 2;
                } else {
                    n4 += n11 / 2;
                    n12 += n6 / 2;
                }
                cr2.aW.a(graphics2, n4, n12, cr2.by);
            }
        }
        this.bw = -100;
        this.bx = -100;
        if (bl2) {
            this.h(this.bk);
            this.a(this.Z());
        }
        this.e(graphics, n2, n3);
        if (bl2) {
            this.p();
        }
    }

    final boolean G() {
        if (ag.a(5) == 0 && this != bm.cb) {
            return false;
        }
        return this.J != 6 && this.aX != null;
    }

    final void a(Graphics graphics, int n2, int n3) {
        n2 = n2 * 3 >> 1;
        n3 = n3 * 3 >> 1;
        this.f(graphics, n2, n3);
        bm.a(graphics, 50, n2, n3);
        this.d(graphics, n2, n3);
        this.a(graphics, n2, n3, false);
        if (this.G()) {
            this.aX.a(graphics);
            this.h = aq.a(this.h, this.aX.h);
            if (aY > 0) {
                --aY;
            }
        }
        this.g(graphics, n2, n3);
        if (this.B && bl > 0) {
            bm.D.a(graphics, 70, n2 - 25, n3 - 70 + bl * 3, 0);
            --bl;
        }
    }

    final void c(Graphics graphics, int n2, int n3) {
        if (aV != this) {
            if (ag.a(3) == 0) {
                return;
            }
            if (ag.a(3) == 1 && !this.B && !this.k()) {
                return;
            }
        }
        int n4 = 0xFFFFFF;
        int n5 = 0x3D3D3D;
        if (this.B || this.k()) {
            n4 = 0xF0FF70;
            n5 = 0;
            if (this.k()) {
                n4 = 0xF0FF70;
                n5 = 0;
            }
        } else if (bh.a(this)) {
            n4 = 0xFFFFFF;
            n5 = 0xA50000;
        }
        if (this.ae != null) {
            ay.b(graphics, this.ae, n2, n3 + -94 - bm.n, this.af, this.ag);
        }
        String string = this.E;
        if (this.aj == 2) {
            string = bm.A(609) + this.E;
        }
        ay.b(graphics, string, n2, n3 + -94, n4, n5);
        if (this.ah != -1) {
            int n6 = bm.m.substringWidth(string, 0, string.length()) / 2;
            ck.b(27).a(this.ah, n2 - n6 - 20, n3 + -94, 0);
        }
        if (this.aX != null) {
            if (ag.a(5) == 1) {
                if (ag.a(3) != 0) {
                    this.aX.c(graphics, n4, n5);
                    return;
                }
            } else if (this == bm.cb && ag.a(3) != 0) {
                this.aX.c(graphics, n4, n5);
            }
        }
    }

    private void R() {
        if (this.aX != null && this.aX.d != null) {
            this.aX.k = this.k;
            this.aX.l = this.l;
            this.aX.e = this.e;
            this.aX.h();
            this.aX.c();
            this.aX.k = this.aX.e == 1 ? (this.aX.k -= this.aX.A) : (this.aX.k += this.aX.A);
            this.aX.l -= this.aX.B;
        }
    }

    final void H() {
        this.R();
        if (this.J == 6) {
            this.a(this.D(6));
            this.g = this.d.c(this.f) - 1;
        }
        if (this.m() && this.J != 5) {
            this.w(6);
            this.w();
        }
        if (this.J == 2 || this.J == 3) {
            if (this.r()) {
                this.u();
                this.q();
            } else {
                this.w(0);
            }
            this.a(true);
        } else if (this.J == 5) {
            this.a(false);
            if (this.b()) {
                this.w(6);
                this.w();
            }
        } else if (this.J == 4) {
            this.a(false);
            if (this.b()) {
                this.w(0);
            }
        } else if (this.J != 6) {
            this.a(true);
        }
        this.p();
        this.y();
    }

    private static void S() {
        bm.cb.w(6);
        if (!bm.n(29)) {
            bm.l(23);
        }
        cr cr2 = bm.cb;
        cr2.O();
        bm.cb.x();
        bm.R();
    }

    private static boolean a(Vector vector, boolean bl2) {
        r r2;
        bh bh2 = null;
        for (int i2 = 0; i2 < vector.size(); ++i2) {
            bh bh3 = (bh)vector.elementAt(i2);
            if (bh3 == null || !bm.bp.contains(bh3) || bh3.F != bm.cj || bh3.B || bh3.m() || bh3.A || bh3.e() && !((r)bh3).j() || !bm.cb.c(bh3.k, bh3.l, 48) || (!bl2 || !bh.a(bh3)) && bl2) continue;
            if (bh2 == null) {
                bh2 = bh3;
                continue;
            }
            if (bm.cb.a(bh2.k, bh2.l) <= bm.cb.a(bh3.k, bh3.l)) continue;
            bh2 = bh3;
        }
        if (bh2 == null) {
            return false;
        }
        aV = bh2;
        if (aV != null && aV.e() && aD && (r2 = (r)aV) != null && bm.cb != null && aG < 0 && aE != 0 && bm.cb.c(r2.k, r2.l, 24)) {
            bm.cf = aV;
            bm.A();
            aD = false;
            aE = 0;
            bm.s();
        }
        return true;
    }

    /*
     * Unable to fully structure code
     */
    private static boolean T() {
        block10: {
            block9: {
                if (bm.cL == null) break block9;
                if (!bm.cb.c(bm.cL.k, bm.cL.l, 48)) {
                    bm.cL = null;
                    return false;
                }
                break block10;
            }
            for (var0 = 0; var0 < bm.ca.size(); ++var0) {
                var1_1 = (h)bm.ca.elementAt(var0);
                if (var1_1.a != 4 || var1_1 == null || !bm.cb.c(var1_1.k, var1_1.l, 48) || !((bv)var1_1).v) continue;
                bm.cL = (bv)var1_1;
                if (bm.cL.w == 1) {
                    if (bm.ct == 0) {
                        bm.bn.K(bm.cL.s);
                        bm.E(3);
                        continue;
                    }
                    bm.S();
                    continue;
                }
                if (bm.cL.w == 2) {
                    bm.bn.M(bm.cL.s);
                    continue;
                }
                var1_1 = bm.cL;
                if (var1_1.q == null || var1_1.q.equals("")) ** GOTO lbl-1000
                var2_2 = var1_1.q.indexOf("\u3010");
                var3_3 = var1_1.q.indexOf("\u3011");
                if (var2_2 != 0 || var3_3 != var1_1.q.length() - 1 || var3_3 <= var2_2) {
                    v0 = true;
                } else lbl-1000:
                // 2 sources

                {
                    v0 = false;
                }
                if (!v0) {
                    bm.N();
                    continue;
                }
                if (!bm.ab) {
                    bm.m(20);
                    continue;
                }
                if (!bm.t()) continue;
                bm.N();
            }
        }
        return false;
    }

    public static void a(int n2, boolean bl2) {
        int n3 = 0;
        String string = bm.A(544);
        if (bl2) {
            string = bm.A(551);
            n3 = n2 - au;
            au = n2;
        } else {
            n3 = n2 - at;
            at = n2;
        }
        if (n3 != 0) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(string);
            if (n3 > 0) {
                stringBuffer.append("+" + n3 + "");
            } else {
                stringBuffer.append(n3 + "");
            }
            bm.a(stringBuffer.toString(), true);
        }
    }

    static boolean I() {
        return (aU & 2) != 0;
    }

    static boolean J() {
        return (aU & 1) != 0;
    }

    private void U() {
        this.a(i.k());
    }

    private void V() {
        if (null == aV) {
            aV = cr.W();
            return;
        }
        this.bM = false;
        switch (cr.aV.a) {
            case 0: {
                if (bh.a(aV)) {
                    if (this.J != 0) break;
                    this.U();
                    return;
                }
                bm.cf = aV;
                if (cr.P()) break;
                bm.H(0);
                return;
            }
            case 1: {
                if (this.J != 0 || !bh.a(aV)) break;
                this.U();
                return;
            }
            case 2: {
                if (((r)aV).h() && !((r)cr.aV).A && !this.c(cr.aV.k, cr.aV.l, 48)) {
                    bm.a(cr.aV.k, cr.aV.l, false);
                    bm.Z = true;
                    this.w(2);
                    return;
                }
                bm.cf = aV;
                bm.A();
                aD = false;
                aE = 0;
                bm.s();
            }
        }
    }

    static boolean b(bh bh2) {
        if (bh2 == null || bh2.F != bm.cb.F || bh2 == bm.cb || bh2.m()) {
            return false;
        }
        aV = bh2;
        return true;
    }

    static void K() {
        aV = null;
        bm.cf = null;
    }

    private static bh W() {
        h h2;
        int n2;
        Vector<h> vector = new Vector<h>();
        if (as != 2) {
            for (n2 = 0; n2 < bh.x.size(); ++n2) {
                h2 = (bh)bh.x.elementAt(n2);
                if (h2 == null || !bh.a((bh)h2) && as == 1 || !h2.d() || ((bh)h2).m() || ((bh)h2).A) continue;
                vector.addElement(h2);
            }
        }
        h2 = null;
        for (n2 = 0; n2 < bm.bp.size(); ++n2) {
            h2 = (h)bm.bp.elementAt(n2);
            if (!h2.g()) continue;
            h2 = (cr)h2;
            if (as == 1 || h2 == null || !bh.a((bh)h2) && as == 2 || !h2.d() || ((bh)h2).m() || h2 == bm.cb || ((bh)h2).A) continue;
            vector.addElement(h2);
        }
        if (as == 0) {
            for (n2 = 0; n2 < bh.z.size(); ++n2) {
                h2 = (bh)bh.z.elementAt(n2);
                if (!h2.d() || !((r)h2).j() || ((bh)h2).A) continue;
                vector.addElement(h2);
            }
        }
        vector.size();
        h2 = cr.a(vector);
        return h2;
    }

    private static bh a(Vector vector) {
        int n2 = vector.size();
        int n3 = -1;
        long l2 = -1L;
        if (n2 > 0) {
            int n4;
            long[] lArray = new long[n2];
            for (n4 = 0; n4 < n2; ++n4) {
                bh bh2 = (bh)vector.elementAt(n4);
                if (bh2 == null) continue;
                int n5 = bm.cb.k - bh2.k;
                int n6 = bm.cb.l - bh2.l;
                lArray[n4] = (n5 * n5 + n6 * n6) * n2 + n4;
                if (aV != bh2) continue;
                l2 = lArray[n4];
            }
            long l3 = l2;
            int n7 = -1;
            int n8 = lArray.length;
            long l4 = 27000000L;
            for (n4 = 0; n4 < n8; ++n4) {
                if (lArray[n4] <= l3 || lArray[n4] >= l4) continue;
                n7 = n4;
                l4 = lArray[n4];
            }
            if (n7 == -1) {
                l4 = lArray[0];
                for (n4 = 0; n4 < n8; ++n4) {
                    if (lArray[n4] > l4) continue;
                    n7 = n4;
                    l4 = lArray[n4];
                }
            }
            n3 = n7;
        }
        if (n3 >= 0 && n3 < n2) {
            bh bh3 = (bh)vector.elementAt(n3);
            return bh3;
        }
        return null;
    }

    static void c(boolean n2) {
        cr cr2;
        int n3;
        block123: {
            boolean bl2;
            block130: {
                block124: {
                    cr cr3;
                    int n4;
                    int n5;
                    block125: {
                        i i2;
                        block127: {
                            block129: {
                                block128: {
                                    block126: {
                                        block119: {
                                            int n6;
                                            block117: {
                                                cr cr4;
                                                block120: {
                                                    block121: {
                                                        block122: {
                                                            block118: {
                                                                cr cr5 = bm.cb;
                                                                if (!cr5.m()) {
                                                                    bh.k(0);
                                                                    bh.k(1);
                                                                }
                                                                if (cr5.J == 5) {
                                                                    cr5.a(false);
                                                                    if (cr5.b()) {
                                                                        cr.S();
                                                                    }
                                                                } else {
                                                                    if (cr5.J == 6 && cr5.n() > 0) {
                                                                        cr5.w(0);
                                                                    }
                                                                    if (cr5.m() && bm.n(30) && bm.m() - bm.cN > 8000L) {
                                                                        cr.S();
                                                                    } else {
                                                                        cr5.a(true);
                                                                    }
                                                                    cr5.R();
                                                                    cr5.p();
                                                                    cr5.y();
                                                                }
                                                                n3 = n2;
                                                                if (bm.az && !bm.n(10)) {
                                                                    bh.w = false;
                                                                    n2 = (bm.cb.k - bm.bW) * 3 >> 1;
                                                                    n6 = (bm.cb.l - bm.bX) * 3 >> 1;
                                                                    if (!bm.aw[0] && (bm.v(4112) || bm.ds != -1 && bm.ds < n2)) {
                                                                        bh.w = true;
                                                                        bm.aw[0] = true;
                                                                        bm.ay = 1;
                                                                        bm.dj.a(8, true);
                                                                        n3 = 1;
                                                                    } else if (bm.aw[0] && !bm.aw[1] && (bm.v(8256) || bm.ds != -1 && bm.ds > n2)) {
                                                                        bh.w = true;
                                                                        bm.aw[1] = true;
                                                                        bm.ay = (byte)2;
                                                                        bm.dj.a(6, true);
                                                                        n3 = 1;
                                                                    } else if (bm.aw[1] && !bm.aw[2] && (bm.v(16388) || bm.dt != -1 && bm.dt < n6)) {
                                                                        bh.w = true;
                                                                        bm.aw[2] = true;
                                                                        bm.ay = (byte)3;
                                                                        bm.dj.a(9, true);
                                                                        n3 = 1;
                                                                    } else if (bm.aw[2] && !bm.aw[3] && (bm.v(33024) || bm.dt != -1 && bm.dt > n6)) {
                                                                        bh.w = true;
                                                                        bm.ay = (byte)-1;
                                                                        bm.aw[3] = true;
                                                                        n3 = 1;
                                                                    }
                                                                    bm.ax = true;
                                                                    for (n2 = 0; n2 < 4; ++n2) {
                                                                        if (bm.aw[n2]) continue;
                                                                        bm.ax = false;
                                                                        break;
                                                                    }
                                                                    if (bm.ax) {
                                                                        bm.bn.n(2);
                                                                        bm.az = false;
                                                                    }
                                                                }
                                                                if (aV != null && (aV.m() || !aV.d() || cr.aV.A)) {
                                                                    aV = null;
                                                                }
                                                                cr2 = bm.cb;
                                                                if (cr2.J != 0 && cr2.J != 2) break block117;
                                                                if (be != 1 || cr2.Y()) break block118;
                                                                cr2.h(n3 != 0);
                                                                break block119;
                                                            }
                                                            n5 = n3;
                                                            cr4 = cr2;
                                                            if (bm.cb.c(aq, ar, 48)) {
                                                                aq = -1;
                                                                ar = -1;
                                                            }
                                                            if (cr4.r()) break block120;
                                                            if (!aD) break block121;
                                                            if (n5 != 0 && (bm.w(aC) || bm.v(aC) || bm.w(65536) || bm.v(65536) || bm.w(1) || bm.v(1))) {
                                                                aD = false;
                                                                bm.s();
                                                            }
                                                            if (aG >= 0) break block122;
                                                            if (aE != 0) {
                                                                bm.bn.o(aE);
                                                            }
                                                            aD = false;
                                                            cr4.E(0);
                                                            if (bm.ac == -1 || bm.ac == bm.cj) {
                                                                bm.s();
                                                            }
                                                            if (bm.Z) {
                                                                cr4.V();
                                                                bm.Z = false;
                                                            }
                                                            break block119;
                                                        }
                                                        if (cr4.J == 0) {
                                                            cr4.w(2);
                                                        }
                                                        n5 = bm.U.a(aG);
                                                        n4 = bm.U.b(aG);
                                                        n5 = n5 * 24 + 12;
                                                        n4 = n4 * 24 + 12;
                                                        cr4.n(n5, n4);
                                                        cr4.w(2);
                                                        String cfr_ignored_0 = "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@  PATHELEN " + aF + "  CUR NODE = " + --aG + "    HERO FORCE TO  XX  = " + cr4.k + "   YY = " + cr4.l + " TARG TX = " + n5 + "   TARGY Ty = " + n4;
                                                        break block120;
                                                    }
                                                    if (!cr4.s(8) && !cr4.s(16) && (bm.w(aC) || bm.v(aC) || bh.w || bm.u(aC)) && n5 != 0) {
                                                        bh.w = false;
                                                        n5 = 0;
                                                        n4 = 0;
                                                        if (bm.az && !bm.n(10)) {
                                                            if (bm.w(aA) || bm.v(aA) || bm.e(216, 149, 50, 31)) {
                                                                n5 = -1;
                                                            } else if (bm.w(aB) || bm.v(aB) || bm.e(371, 150, 50, 31)) {
                                                                n5 = 1;
                                                            }
                                                            if (bm.w(ay) || bm.v(ay) || bm.e(293, 89, 50, 31)) {
                                                                n4 = -1;
                                                            } else if (bm.w(az) || bm.v(az) || bm.e(293, 220, 50, 31)) {
                                                                n4 = 1;
                                                            }
                                                        } else {
                                                            if (bm.w(aA) || bm.v(aA) || bm.u(aA)) {
                                                                n5 = -1;
                                                            } else if (bm.w(aB) || bm.v(aB) || bm.u(aB)) {
                                                                n5 = 1;
                                                            }
                                                            if (bm.w(ay) || bm.v(ay) || bm.u(ay)) {
                                                                n4 = -1;
                                                            } else if (bm.w(az) || bm.v(az) || bm.u(az)) {
                                                                n4 = 1;
                                                            }
                                                        }
                                                        if (n5 != 0 || n4 != 0) {
                                                            bm.t = false;
                                                        }
                                                        int n7 = n4;
                                                        n4 = n5;
                                                        cr cr6 = cr4;
                                                        if (n4 != 0 || n7 != 0) {
                                                            bm.bn.a(-1, -1, (short)-1, (short)-1);
                                                            int n8 = n4 * 24;
                                                            int n9 = n7 * 24;
                                                            int n10 = cr6.X() ? 15 : 5;
                                                            int n11 = cr6.X() ? 10 : 3;
                                                            int n12 = cr6.k + n4 * n10;
                                                            int n13 = cr6.l + n7 * n11;
                                                            n12 = cr.m(n12, n13);
                                                            if (n12 >= 0 && n12 <= 19) {
                                                                n13 = -1;
                                                                if (n7 != 0) {
                                                                    n13 = n7 == 1 ? 1 : 0;
                                                                } else if (n4 != 0) {
                                                                    int n14 = n13 = n4 == 1 ? 3 : 2;
                                                                }
                                                                if (n13 != -1) {
                                                                    byte[] byArray = bX[n12][n13];
                                                                    n8 = byArray[0];
                                                                    n9 = byArray[1];
                                                                }
                                                            }
                                                            n13 = cr6.k + n8;
                                                            n9 = cr6.l + n9;
                                                            n8 = n13 + n4 * n10;
                                                            n12 = 0;
                                                            n13 = 0;
                                                            n12 = 0;
                                                            n13 = 0;
                                                            int n15 = cr.m(n8, n9 += n7 * n11);
                                                            switch (n15) {
                                                                case 23: {
                                                                    if (n4 == 1) {
                                                                        n13 = -(n8 % 24 + 1);
                                                                    } else if (n4 == -1) {
                                                                        n13 = 24 - n8 % 24 + 1;
                                                                    }
                                                                    if (n7 == 1) {
                                                                        n12 = -(n9 % 24 + 1);
                                                                    } else if (n7 == -1) {
                                                                        n12 = 24 - n9 % 24 + 1;
                                                                    }
                                                                    n9 += n12;
                                                                    n12 = (n8 += n13) / 24;
                                                                    n13 = n9 / 24;
                                                                    int n16 = n7;
                                                                    n15 = n4;
                                                                    int[] nArray = new int[2];
                                                                    if (n15 != 0 && n16 != 0) {
                                                                        boolean bl3 = bh.g(n12, n13 + n16);
                                                                        n12 = bh.g(n12 + n15, n13) ? 1 : 0;
                                                                        if (!bl3 || n12 == 0) {
                                                                            if (bl3) {
                                                                                nArray[0] = n15 * 24;
                                                                            } else {
                                                                                nArray[1] = n16 * 24;
                                                                            }
                                                                        }
                                                                    } else if (n15 != 0) {
                                                                        int n17 = cr.a(n12, n13, true);
                                                                        nArray[1] = n17 > 0 ? 24 : (n17 < 0 ? -24 : 0);
                                                                    } else if (n16 != 0) {
                                                                        int n18 = cr.a(n12, n13, false);
                                                                        nArray[0] = n18 > 0 ? 24 : (n18 < 0 ? -24 : 0);
                                                                    }
                                                                    int[] nArray2 = nArray;
                                                                    n8 += nArray2[0];
                                                                    n9 += nArray2[1];
                                                                    break;
                                                                }
                                                                case -1: {
                                                                    break;
                                                                }
                                                                default: {
                                                                    n12 = n15;
                                                                    byte[] byArray = new byte[]{0, 0};
                                                                    byte[] byArray2 = n12 >= 0 && n12 <= 19 ? bW[n12] : byArray;
                                                                    n8 = n8 / 24 * 24 + byArray2[0];
                                                                    n9 = n9 / 24 * 24 + byArray2[1];
                                                                }
                                                            }
                                                            if (!bh.e(n8, n9)) {
                                                                int n19 = n8 - n4 * n10;
                                                                int n20 = n9 - n7 * n11;
                                                                cr6.n(n19, n20);
                                                            }
                                                        }
                                                    } else if (cr4.J == 2) {
                                                        cr4.w(0);
                                                    }
                                                }
                                                if (cr4.r()) {
                                                    cr4.u();
                                                    cr4.q();
                                                    if (!(bm.Z || bm.cW && aV != null)) {
                                                        switch (as) {
                                                            case 0: {
                                                                if (cr.a(bh.z, false) || cr.a(bh.y, false)) break;
                                                                cr.a(bh.x, false);
                                                                break;
                                                            }
                                                            case 1: {
                                                                cr.a(bh.x, true);
                                                                break;
                                                            }
                                                            case 2: {
                                                                cr.a(bh.y, true);
                                                            }
                                                        }
                                                    }
                                                    cr.T();
                                                }
                                                break block119;
                                            }
                                            if (cr2.J == 4 || cr2.J == 7) {
                                                cr2.h(n3 != 0);
                                                if (bm.t && n3 != 0) {
                                                    n6 = 0;
                                                    n5 = 0;
                                                    if (bm.w(aA) || bm.v(aA) || bm.u(aA)) {
                                                        n6 = -1;
                                                    } else if (bm.w(aB) || bm.v(aB) || bm.u(aB)) {
                                                        n6 = 1;
                                                    }
                                                    if (bm.w(ay) || bm.v(ay) || bm.u(ay)) {
                                                        n5 = -1;
                                                    } else if (bm.w(az) || bm.v(az) || bm.u(az)) {
                                                        n5 = 1;
                                                    }
                                                    if (n6 != 0 || n5 != 0) {
                                                        bm.t = false;
                                                    }
                                                }
                                            } else if (cr2.J == 6) {
                                                cr2.a(cr2.D(6));
                                                cr2.g = cr2.d.c(cr2.f) - 1;
                                            } else if (cr2.J == 8) {
                                                n6 = 0;
                                                n5 = 0;
                                                if (bm.w(aA) || bm.v(aA) || bm.u(aA)) {
                                                    n6 = -1;
                                                } else if (bm.w(aB) || bm.v(aB) || bm.u(aB)) {
                                                    n6 = 1;
                                                }
                                                if (bm.w(ay) || bm.v(ay) || bm.u(ay)) {
                                                    n5 = -1;
                                                } else if (bm.w(az) || bm.v(az) || bm.u(az)) {
                                                    n5 = 1;
                                                }
                                                if (n6 != 0 || n5 != 0) {
                                                    bm.cb.g(true);
                                                }
                                            } else if (cr2.b()) {
                                                cr2.w(0);
                                            }
                                        }
                                        if (n3 != 0 && cr2.J != 4 && bm.v(65536)) {
                                            cr2.V();
                                        }
                                        if (n3 == 0) break block123;
                                        cr3 = cr2;
                                        n5 = bm.as() - 1;
                                        if (n5 < 0 || n5 > 7 || (n5 = ab.c[ab.a][n5]) == -1 || n5 == 0) break block124;
                                        if ((n5 & Integer.MIN_VALUE) == 0) break block125;
                                        n4 = n5 & 0xFFFFFFF;
                                        i2 = i.a(n4);
                                        if (i2 == null) break block124;
                                        if (cr3.J != 4 || !bm.u || i2.h <= aJ) break block126;
                                        bl = 5;
                                        break block124;
                                    }
                                    if (cr3.J != 2 && cr3.J != 0 && cr3.J != 7 && be != 1) break block127;
                                    if (!i.c(i2) || bm.bq.size() == 0) break block128;
                                    i.p = n4;
                                    i.a(aV);
                                    bm.l(71);
                                    break block124;
                                }
                                if (i2 == null || !i2.c()) break block129;
                                cn.a(bm.A(538), 21, i2.c);
                                break block124;
                            }
                            bl2 = cr3.a(i2);
                            break block130;
                        }
                        if (i2.h <= aJ && !i2.j()) {
                            al = i2;
                        }
                        break block124;
                    }
                    if ((n5 & 0x40000000) != 0) {
                        n4 = n5 >> 16 & 0xFFF;
                        n n21 = bm.aA.b(n4);
                        if (n21 == null || (n5 &= 0xFFFF) != n21.d) {
                            n21 = bm.aA.c(n5);
                        }
                        if (n21 != null) {
                            if ((cr3.J == 4 || cr3.J == 7) && n21.d(64)) {
                                bm.c(bm.A(611));
                            } else if ((n21.h & 0x4000) == 16384) {
                                bm.bn.b(n21, cv.I);
                            } else {
                                bm.bn.a(n21, aV);
                            }
                        }
                    } else if ((n5 & 0x20000000) != 0) {
                        n4 = n5 & 0xFFFFFFF;
                        ab.a(n4);
                    }
                }
                bl2 = false;
            }
            if (bl2) {
                bm.Y = 3;
            }
        }
        if (bm.t) {
            if (aV == null || !bh.a(aV)) {
                byte by2 = as;
                if (by2 == 0) {
                    as = 1;
                    aV = cr.W();
                    if (aV == null || !bh.a(aV)) {
                        as = (byte)2;
                        aV = cr.W();
                    }
                    as = by2;
                } else {
                    aV = cr.W();
                }
            }
            if (aV != null && bm.cb.J == 0 && bh.a(aV)) {
                bm.cb.a(i.k());
            }
        }
        if (bm.v(1) && n3 != 0) {
            bm.bn.a(-1, -1, (short)-1, (short)-1);
            if (!cr2.r()) {
                aV = cr.W();
                if (bm.cb.J != 8) {
                    bm.cb.w(0);
                }
            }
        }
        cr.d(true);
    }

    static void d(boolean bl2) {
        int[] n2 = bm.cc;
        if (bm.cc[0] == bm.cb.k && n2[1] == bm.cb.l) {
            return;
        }
        if (bl2 && bm.m() - bm.ce < 2000L) {
            return;
        }
        int n3 = bm.cb.k;
        int n4 = bm.cb.l;
        if (bh.e(n3, n4)) {
            return;
        }
        bm.bn.a((short)n3, (short)n4);
        cr.L();
    }

    static void L() {
        bm.cc[0] = bm.cb.k;
        bm.cc[1] = bm.cb.l;
        bm.cd = 0;
        bm.ce = bm.m();
    }

    private boolean X() {
        return this.a == 0 && this.bm != 0 && this.bm >= 1 && this.bm <= 3;
    }

    final void b(int n2, int n3, int n4) {
        if (n2 <= 3 && n2 >= 1) {
            this.bm = n2;
            if (n3 != -1) {
                this.bn = n3;
            }
            this.bo = n4;
            this.b(0, this.Z());
        }
    }

    final void e(boolean bl2) {
        if (!this.X()) {
            return;
        }
        this.bm = 0;
        this.bn = 0;
        this.bo = -1;
        this.a(this.D(this.J));
        if (bl2 && this.B) {
            bm.bn.k();
        }
    }

    private int[] a(Graphics object, int n2, int n3, int n4, boolean bl2) {
        try {
            int n5;
            int n6;
            ao ao2;
            if (!this.X()) {
                return null;
            }
            if (this.bm < 0 || this.bm >= bj.T.length) {
                return null;
            }
            int n7 = bj.T[this.bm];
            if (n7 > 73) {
                n7 = 71;
                bg.a(bm.A(612) + this.bm);
            }
            if ((ao2 = ck.b(n7)) == null) {
                return null;
            }
            cr cr2 = this;
            if (this.f == this.aa() + cr2.ai * 51) {
                n6 = 1;
                n5 = this.g;
            } else {
                cr2 = this;
                if (this.f == this.Z() + cr2.ai * 51) {
                    n6 = 0;
                    n5 = this.g;
                } else {
                    n6 = 0;
                    n5 = 0;
                }
            }
            int n8 = this.bo & 0xFF;
            if (n8 >= 0 && n8 < ao2.d.length && ao2.d[n8] != null) {
                ao2.b(n8);
            }
            n8 = this.bo >> 8 & 0xFF;
            ao2.j(n8);
            if (n5 >= ao2.c(n6)) {
                n5 = 0;
            }
            if (!bl2) {
                ak.e[3] = ak.e[3] + 1;
                if (this.bm != 0) {
                    ak.a(this.bm);
                }
                ao2.a((Graphics)object, n6, n5, n2, n3, n4, 0, 0);
            }
            object = new int[4];
            ao2.a((int[])object, n6, n5, this.k * 3 >> 1, this.l * 3 >> 1, n4);
            return object;
        }
        catch (Exception exception) {
            return null;
        }
    }

    private boolean Y() {
        return this.bp == 0;
    }

    final int x(int n2) {
        cr cr2;
        if (cr3.Y()) {
            if (n2 == 16) {
                return n2;
            }
            return 43;
        }
        cr cr3 = cr3;
        cr3 = cr3;
        if (cr2.bp == 1) {
            return n2;
        }
        return n2 + 22;
    }

    private int C(int n2) {
        int n3 = 0;
        if (this.G == 0) {
            n3 = 1;
        } else if (this.G == 4) {
            n3 = 2;
        }
        return n2 + n3;
    }

    private int Z() {
        if (this.X()) {
            return bs[this.bp];
        }
        return this.C(bq[this.bp]);
    }

    private int aa() {
        if (this.X()) {
            return bt[this.bp];
        }
        return this.C(br[this.bp]);
    }

    private int ab() {
        return bu[this.bp];
    }

    private int D(int n2) {
        switch (n2) {
            case 0: 
            case 1: {
                return this.Z();
            }
            case 5: 
            case 6: {
                return this.x(16);
            }
            case 2: 
            case 3: {
                return this.aa();
            }
            case 4: {
                return this.x(6);
            }
            case 7: 
            case 8: {
                return this.x(20);
            }
        }
        return this.Z();
    }

    public final void a(int n2, String string, byte by2, byte by3, byte by4, byte by5) {
        if (this.aX == null) {
            this.aX = new cv();
        }
        this.aX.a(n2, by5);
        this.aX.z = string;
        this.aX.A = by2;
        this.aX.B = by3;
        this.aX.C = by4;
    }

    public final void M() {
        aY = 0;
        if (this.aX != null) {
            this.aX.a(-1, 0);
        }
        cv.I = -1;
    }

    private void E(int n2) {
        if (this.aX != null) {
            this.aX.K = n2;
        }
    }

    final void N() {
        aZ = null;
        ba = null;
        bb = 0;
        bc = 0;
        bd = 0;
        bE = 0L;
        bF = 0;
        bG = 0;
        this.bH = null;
    }

    final void O() {
        aZ = null;
        ba = null;
        bb = 0;
        bc = 0;
        bd = 0;
        bE = 0L;
        bF = 0;
        bG = 0;
        this.bH = null;
    }

    static void a(cr cr2, cr cr3) {
        aZ = cr2;
        ba = cr3;
    }

    final void a(cr cr2, cr cr3, int n2, int n3, int n4, int n5, int n6) {
        if (cr2 != bm.cb && cr3 != bm.cb) {
            bm.A(613);
            return;
        }
        cr.a(cr2, cr3);
        bd = 1;
        cr.F(n6);
        bF = n4;
        bG = n5;
        bb = n2;
        bc = n3;
        this.bH = "" + n6 / 1000;
        ay.c = 0;
    }

    static boolean P() {
        return bd != 0;
    }

    final boolean b(cr cr2) {
        return (bd == 2 || bd == 3) && (aZ == cr2 || bb == cr2.C || ba == cr2 || bc == cr2.C);
    }

    private static void F(int n2) {
        bE = bm.m() + (long)n2;
    }

    private boolean ac() {
        return !this.c(bF, bG, 300);
    }

    final void f(boolean bl2) {
        cr cr2 = this;
        bl2 = aZ == cr2 ? bl2 : !bl2;
        this.bH = bl2 ? bm.A(614) : bm.A(615);
        ay.c = 0;
        if (aZ != null) {
            aZ.w(0);
        }
        if (ba != null) {
            ba.w(0);
        }
        bd = 4;
    }

    final void Q() {
        if (!cr.P()) {
            return;
        }
        long l2 = bm.m();
        switch (bd) {
            case 1: {
                if (l2 > bE) {
                    bd = 2;
                    bE = 0L;
                    cr cr2 = aZ == this ? ba : aZ;
                    if (cr2 != null && cr2 != aV && this.c(cr2.k, cr2.l, 300)) {
                        aV = cr2;
                    }
                    ay.c = 0;
                    this.bH = bm.A(616);
                    return;
                }
                String string = "" + ((bE - l2) / 1000L + 1L);
                if (this.bH != null && this.bH.compareTo(string) == 0) break;
                ay.c = 0;
                this.bH = string;
                return;
            }
            case 2: {
                if (!this.ac()) break;
                bd = 3;
                cr.F(9999);
                this.bH = "" + ((bE - l2) / 1000L + 1L);
                return;
            }
            case 3: {
                if (l2 > bE) {
                    bm.bn.d(aZ, ba);
                    bE = 0L;
                    this.bH = null;
                    ay.c = 0;
                    return;
                }
                if (!this.ac()) {
                    bd = 2;
                    bE = 0L;
                    this.bH = null;
                    return;
                }
                String string = "" + ((bE - l2) / 1000L + 1L);
                if (this.bH != null && this.bH.compareTo(string) == 0) break;
                ay.c = 0;
                this.bH = string;
                return;
            }
            case 4: {
                if (this.bH != null) break;
                bd = 5;
                return;
            }
            case 5: {
                this.O();
                return;
            }
            default: {
                String cfr_ignored_0 = bm.A(617) + bd;
            }
        }
    }

    final void b(Graphics graphics) {
        if (!cr.P()) {
            return;
        }
        int n2 = (bF - bm.bW - 300) * 3 >> 1;
        int n3 = (bG - bm.bX - 300) * 3 >> 1;
        graphics.setColor(0xBBBB00 + aq.f % 5 * 0x111111);
        graphics.drawArc(n2, n3, 900, 900, 0, 360);
        graphics.setColor(0x666622 + aq.f % 5 * 0x111111);
        graphics.drawArc(n2 + 1, n3 + 1, 900, 900, 0, 360);
    }

    final void c(Graphics graphics) {
        if (!cr.P()) {
            return;
        }
        switch (bd) {
            case 1: 
            case 2: 
            case 3: 
            case 4: {
                if (this.bH == null) break;
                boolean bl2 = 4 != bd && 2 != bd;
                bz.b(4);
                if (bl2) {
                    bz.c(0);
                } else {
                    bz.a(graphics, 0xFF0000, 0xFFFFFF, -1);
                }
                ay.a(graphics, this.bH, (this.k - bm.bW) * 3 >> 1, ((this.l - bm.bX) * 3 >> 1) - bz.a(3), 4, 3, bl2);
                if (bl2) {
                    bz.c(0);
                }
                if (ay.c == 0) {
                    this.bH = null;
                }
                bz.b((byte)1);
            }
        }
    }

    private static void G(int n2) {
        if (n2 != 1) {
            bm.cb.e(true);
        }
        be = n2;
    }

    public static void y(int n2) {
        i i2 = i.a(n2);
        if (i2 != null) {
            i2.m = bm.m();
        }
        bm.Y = 3;
    }

    public static void k(int n2, int n3) {
        i i2 = i.a(n2);
        if (i2 != null) {
            i2.c(n3);
        }
        bm.Y = 3;
    }

    private boolean c(int n2, int n3, int n4) {
        int n5 = this.l - n3;
        return n4 * n4 >= (n2 = this.k - n2) * n2 + n5 * n5;
    }

    private boolean a(bh bh2, int n2) {
        i i2 = i.a(n2);
        if (i2 == null) {
            return false;
        }
        return this.c(bh2.k, bh2.l, i2.l);
    }

    /*
     * Enabled aggressive block sorting
     */
    final boolean a(i object) {
        Object object2;
        block29: {
            block31: {
                block33: {
                    block32: {
                        block30: {
                            if (object == null) {
                                return false;
                            }
                            Object object3 = this;
                            if (((h)object3).a == 0 && ((cr)object3).Y() && ((i)object).c != 3001) {
                                object3 = new StringBuffer();
                                ((StringBuffer)object3).append(bm.A(618));
                                bm.c(((StringBuffer)object3).toString());
                                return false;
                            }
                            if (bd == 1) {
                                bm.c(bm.A(619));
                                return false;
                            }
                            byte by2 = ((i)object).d;
                            if (by2 <= 0) {
                                return false;
                            }
                            if (!i.a((i)object)) break block30;
                            bK = this;
                            break block31;
                        }
                        if (!i.c((i)object)) break block32;
                        if (aV != null && !bh.a(aV)) break block33;
                        bK = this;
                        break block31;
                    }
                    if (aV == null || !bh.a(aV)) {
                        if (as == 0) {
                            as = 1;
                            aV = cr.W();
                            if (aV == null || !bh.a(aV)) {
                                as = (byte)2;
                                aV = cr.W();
                            }
                            as = 0;
                        } else {
                            aV = cr.W();
                        }
                    }
                }
                bK = aV;
            }
            Object object4 = object;
            object = bK;
            cr cr2 = this;
            if (object4 == null) {
                return false;
            }
            if (cr2.m()) {
                return false;
            }
            if (object == null) return false;
            if (((bh)object).m()) {
                return false;
            }
            if (((i)object4).j()) {
                return false;
            }
            if (bm.m() - bN < (long)i.a) {
                return false;
            }
            if (((bh)object).F != cr2.F) {
                return false;
            }
            if (i.a((i)object4) ? object != bm.cb : i.b((i)object4) && !bh.a((bh)object)) {
                return false;
            }
            if (((bh)object).s(6)) {
                return false;
            }
            if (((i)object4).h > aJ) {
                if (bm.u) {
                    object2 = i.k();
                    break block29;
                } else {
                    bl = 5;
                    return false;
                }
            }
            if (cr2.s(8)) return false;
            if (((i)object4).c != i.a() && cr2.s(7)) {
                return false;
            }
            object2 = object = object4;
        }
        if (object2 == null) {
            return false;
        }
        bI = ((i)object).c;
        if (this.J == 7) {
            this.J = (byte)4;
        }
        if (!this.a(bK, ((i)object).c)) {
            aD = false;
            this.s();
            bm.s();
            this.L = cr.bK.k;
            this.M = cr.bK.l;
            if (bm.a(this.L, this.M, false)) {
                cr.G(1);
                return true;
            }
            this.ad();
            return true;
        }
        if (this.G == 6 && this.k < cr.bK.k) {
            this.h(2);
        } else if (this.G == 2 && this.k > cr.bK.k) {
            this.h(6);
        }
        if (((i)object).k > 0) {
            cr.G(2);
            bL = bm.m();
            this.w(4);
            this.a(this.ab());
            this.e |= 0x40;
            bm.bn.b((short)this.ab(), (short)this.e);
        } else {
            this.b(bK, ((i)object).c);
        }
        this.d(0, 0);
        return true;
    }

    private void b(bh bh2, int n2) {
        int n3;
        i i2 = i.a(n2);
        if (i2 == null) {
            return;
        }
        cr.G(3);
        bN = bm.m();
        this.c(bh2.k, bh2.l);
        if (n2 == i.a()) {
            if (bm.cb.u == 0) {
                al.a(10, 0, 0);
            } else if (bm.cb.u == 1) {
                al.a(11, 0, 0);
            } else if (bm.cb.u == 2) {
                al.a(12, 0, 0);
            }
            if (bJ < this.x(bj.R[this.u << 1]) || bJ > this.x(bj.R[(this.u << 1) + 1])) {
                bJ = this.x(bj.R[this.u << 1]);
            }
            this.b(4, bJ);
            ++bJ;
        } else {
            n3 = i2.i;
            this.b(4, this.x(n3));
        }
        n3 = bh2.C;
        if (this.bM) {
            bm.bn.a(n3, n2, (short)this.k, (short)this.l);
        } else {
            bm.bn.a(n3, n2, (short)-1, (short)-1);
        }
        if (i2.c() || i2.d()) {
            this.ad();
            return;
        }
    }

    public final void z(int n2) {
        this.w(8);
        bL = bm.m();
        bO = n2;
        cr.d(false);
        bm.bn.G();
    }

    public final void g(boolean bl2) {
        cr.G(0);
        this.w(0);
        bO = 0;
        bL = 0L;
    }

    public final void d(Graphics graphics) {
        if (this.J == 8 || (this.J == 4 || this.J == 7) && (be == 2 || be == 4)) {
            int n2;
            int n3 = bm.D.f(69);
            int n4 = ((this.k - bm.bW) * 3 >> 1) - n3 / 2;
            int n5 = (this.l - bm.bX) * 3 >> 1;
            if (this.J == 8) {
                if (this.m() || this.s(8)) {
                    return;
                }
                n2 = bO;
            } else {
                n2 = be == 4 ? 3000 : i.b(bI);
            }
            if (n2 == 0) {
                return;
            }
            int n6 = (int)(bm.m() - bL);
            n2 = n6 * (n3 -= 4) / n2;
            if (n2 > n3) {
                n2 = n3;
            }
            bm.D.a(69, n4, n5, 0);
            graphics.setColor(1491657);
            graphics.fillRect(n4 += 2, n5 += 2, n2, 2);
        }
    }

    private static boolean c(bh bh2) {
        return bh2 == null || bh2.m() || bh2.A;
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    private void h(boolean var1_1) {
        if (bm.cb.Y()) {
            this.ad();
            return;
        }
        if (this.m() || cr.be == 0) {
            bm.bn.a(-1, -1, (short)-1, (short)-1);
            this.ad();
            return;
        }
        if (cr.c(cr.bK) && cr.be != 3) {
            bm.bn.a(-1, -1, (short)-1, (short)-1);
            this.ad();
            return;
        }
        if (cr.bI < 0) {
            if (this.b() && bm.m() - cr.bN > (long)i.a) {
                this.ad();
            }
            bm.bn.a(-1, -1, (short)-1, (short)-1);
            return;
        }
        cr.bP |= bm.bl;
        var2_2 = false;
        if (cr.H(cr.aC) && var1_1 && cr.be != 3) {
            cr.aD = false;
            bm.s();
            var2_2 = true;
            cr.bP = 0;
        }
        switch (cr.be) {
            case 1: {
                if (this.r() || cr.aD) {
                    this.bM = true;
                    if (!this.r()) {
                        if (cr.c(cr.bK)) {
                            cr.aD = false;
                            bm.s();
                            var2_2 = true;
                        } else if (cr.bK != null && this.a(cr.bK, cr.bI)) {
                            cr.aG = -1;
                            cr.aD = false;
                            bm.s();
                            if (cr.bK != null) {
                                this.a(i.a(cr.bI));
                            } else {
                                bm.bn.a(-1, -1, (short)-1, (short)-1);
                                this.ad();
                            }
                        } else if (cr.aG < 0 && !bm.c(cr.bK.k, cr.bK.l, this.L, this.M)) {
                            this.L = cr.bK.k;
                            this.M = cr.bK.l;
                            if (bm.a(this.L, this.M, false)) {
                                cr.G(1);
                            } else {
                                bm.bn.a(-1, -1, (short)-1, (short)-1);
                                this.ad();
                            }
                        } else {
                            var1_1 = bm.U.a(cr.aG);
                            var3_3 = bm.U.b(cr.aG);
                            if (this.J == 0) {
                                this.w(2);
                            }
                            var1_1 = var1_1 * 24 + 12;
                            var3_3 = var3_3 * 24 + 12;
                            var4_4 = this.d(this.k, this.l, (int)var1_1, var3_3);
                            this.a((int)var1_1, var3_3, (int)var4_4);
                            this.w(2);
                            --cr.aG;
                        }
                    }
                    if (!this.r()) break;
                    this.u();
                    this.q();
                    break;
                }
                var2_2 = true;
                break;
            }
            case 2: 
            case 4: {
                if (2 != cr.be || !cr.c(cr.bK)) {
                    if (cr.H(cr.aC) && var1_1) {
                        var2_2 = true;
                        cr.bP = 0;
                        break;
                    }
                    if (4 == cr.be) {
                        if (bm.m() < cr.bL + 3000L) break;
                        var1_1 = cr.bI;
                        bm.bn.d(this.C, (int)var1_1, this.C);
                        this.w(0);
                        break;
                    }
                    if (bm.m() < cr.bL + (long)i.b(cr.bI)) break;
                    this.b(cr.bK, cr.bI);
                    break;
                }
                ** GOTO lbl105
            }
            case 3: {
                if (bm.m() - cr.bN <= (long)i.a) break;
                if (cr.c(cr.bK)) {
                    bm.bn.a(-1, -1, (short)-1, (short)-1);
                } else if (cr.bK.a != 2) {
                    if (cr.H(cr.aC) && var1_1) {
                        var2_2 = true;
                        cr.bP = 0;
                        break;
                    }
                    if (bm.ad) {
                        var2_2 = true;
                        cr.bP = 0;
                        bm.ad = false;
                        break;
                    }
                    if (cr.al != null || bm.u) {
                        this.a(i.k());
                        break;
                    }
                }
lbl105:
                // 6 sources

                var2_2 = true;
            }
        }
        if (var2_2) {
            this.ad();
        }
    }

    private void ad() {
        cr.G(0);
        this.w(0);
        bm.bn.a(-1, -1, (short)-1, (short)-1);
        bm.bn.b((short)this.f, (short)this.e);
        i.o = 0;
        if (bf != -1) {
            f.a(bf, bg, bh, 0);
            bf = -1;
        }
    }

    private static boolean H(int n2) {
        return (bP & n2) != 0;
    }

    public final void l(int n2, int n3) {
        switch (n2) {
            case 0: {
                byte by2 = (byte)n3;
                n3 = 0;
                cr cr2 = this;
                String cfr_ignored_0 = cr2.E + bm.A(621) + "  M = " + by2;
                if (cr2.bR != null) {
                    cr2.bR[0] = by2;
                }
                return;
            }
            case 8: {
                int n4;
                cr cr3 = this;
                int n5 = n3 >> 4 & 0xF;
                cr cr4 = cr3;
                cr3.bp = n5;
                cr3.n = n3 & 0xF;
                n5 = n4 = n3 >> 8 & 0xF;
                cr cr5 = cr3;
                cr3.bv = n5;
                cr5.aW.a(cr5.bv, true);
                this.a(this.Z());
            }
        }
    }

    final int c(int n2, int n3, int n4, int n5) {
        if (n2 >= 0 && n2 < 17) {
            this.bw = n3;
            this.bx = n4;
            this.by = n5;
        }
        if (this.bR == null) {
            return n2;
        }
        for (n3 = 0; n3 < 1; ++n3) {
            if (this.bR[0] == 0) continue;
            boolean bl2 = false;
            cr cr2 = this;
            for (n3 = 0; n3 < bQ.length; ++n3) {
                if (bQ[n3] != n2) continue;
                return n2 + cr2.bR[0] * bQ.length;
            }
            return n2;
        }
        return n2;
    }

    private static int a(int n2, int n3, boolean bl2) {
        boolean bl3;
        boolean bl4 = !(!bl2 ? bh.g(n2 - 1, n3) : bh.g(n2, n3 - 1)) ? true : (bl3 = false);
        int n4 = !(!bl2 ? bh.g(n2 + 1, n3) : bh.g(n2, n3 + 1)) ? 1 : (n2 = 0);
        if (bl3 && n2 != 0) {
            n2 = 0;
            if (bl2) {
                n2 = bm.cb.G == 0 ? 1 : 0;
            } else {
                int n5 = n2 = bm.cb.G == 6 ? 1 : 0;
            }
            if (n2 != 0) {
                return -1;
            }
            return 1;
        }
        if (bl3) {
            return -1;
        }
        if (n2 != 0) {
            return 1;
        }
        return 0;
    }

    public static boolean c(cr cr2) {
        if (cr2 == null) {
            return false;
        }
        if (cr2.U < 20 || bm.cb.U < 20) {
            return false;
        }
        if (!bm.D(8192)) {
            return false;
        }
        return bm.cb.v != cr2.v;
    }

    private static int m(int n2, int n3) {
        return bh.h(n2 / 24, n3 / 24);
    }

    public static boolean A(int n2) {
        return n2 == 23;
    }

    public final short d(int n2, int n3, int n4, int n5) {
        cr cr2 = this;
        short s2 = bg.a(60) * cr2.r[6] / 10000;
        int n6 = 0;
        if (cr2.X()) {
            int n7 = cr2.bn;
            n6 = 0 + s2 * n7 / 100;
            String cfr_ignored_0 = bm.A(610) + n7 + "%";
        }
        int n8 = s2 + n6;
        s2 = 0;
        long l2 = bm.a((long)((n4 - n2) * (n4 - n2) + (n5 - n3) * (n5 - n3)), 30);
        if ((n8 = bg.b(n8)) == 0) {
            return 0;
        }
        short s3 = (short)(Math.abs(l2) * 1000L / (long)n8);
        s2 = s3;
        if (s3 > 3000 && this.B) {
            bg.a(bm.A(603) + n2 + "." + n3 + ")(" + n4 + "." + n5 + ")");
        }
        return s2;
    }

    private void n(int n2, int n3) {
        if (n2 != this.k || n3 != this.l) {
            short s2 = this.d(this.k, this.l, n2, n3);
            if (!bh.e(this.k, this.l) && bh.e(n2, n3)) {
                String cfr_ignored_0 = bm.A(604) + this.k + "," + this.l + ")-" + "(" + n2 + "." + n3 + ")";
                return;
            }
            bm.Z = false;
            this.a(n2, n3, (int)s2);
            this.w(2);
        }
    }

    static {
        bl = 0;
        aq = -1;
        ar = -1;
        ay = 16384;
        az = 32768;
        aA = 4096;
        aB = 8192;
        aC = ay | az | aA | aB;
        aK = 0;
        aL = 0;
        aM = 0;
        aN = 0;
        aV = null;
        bq = new int[]{43, 0, 22};
        br = new int[]{46, 3, 25};
        bs = new int[]{49, 18, 40};
        bt = new int[]{50, 19, 41};
        bu = new int[]{43, 17, 39};
        bE = 0L;
        bI = -1;
        bJ = -1;
        bK = null;
        be = 0;
        bf = -1;
        bQ = new byte[]{17, 18};
        bV = new int[][]{{40, 0}, {39, -3}, {38, -6}, {36, -9}, {34, -12}, {32, -14}, {29, -16}, {26, -18}, {23, -20}, {21, -21}, {18, -22}, {16, -22}, {13, -23}, {11, -24}, {8, -24}, {6, -24}, {4, -24}, {2, -24}, {0, -25}, {-2, -24}, {-4, -24}, {-6, -24}, {-8, -24}, {-11, -24}, {-13, -23}, {-16, -22}, {-18, -22}, {-21, -21}, {-23, -20}, {-26, -18}, {-29, -16}, {-32, -14}, {-34, -12}, {-36, -9}, {-38, -6}, {-39, -3}, {-40, 0}, {-39, 3}, {-38, 6}, {-36, 9}, {-34, 12}, {-32, 14}, {-29, 16}, {-26, 18}, {-23, 20}, {-21, 21}, {-18, 22}, {-16, 22}, {-13, 23}, {-11, 24}, {-8, 24}, {-6, 24}, {-4, 24}, {-2, 24}, {0, 25}, {2, 24}, {4, 24}, {6, 24}, {8, 24}, {11, 24}, {13, 23}, {16, 22}, {18, 22}, {21, 21}, {23, 20}, {26, 18}, {29, 16}, {32, 14}, {34, 12}, {36, 9}, {38, 6}, {39, 3}};
        bW = new byte[][]{{12, 18}, {12, 6}, {12, 6}, {12, 18}, {12, 6}, {12, 18}, {12, 18}, {12, 6}, {18, 12}, {6, 12}, {6, 12}, {18, 12}, {6, 12}, {18, 12}, {18, 12}, {6, 12}, {12, 12}, {12, 12}, {12, 12}, {12, 12}};
        bX = new byte[][][]{new byte[][]{{0, -24}, {-24, 24}, {-24, 0}, {24, 0}}, new byte[][]{{0, -24}, {-24, 0}, {-24, 0}, {24, -24}}, new byte[][]{{0, -24}, {24, 0}, {-24, -24}, {24, 0}}, new byte[][]{{0, -24}, {24, 24}, {-24, 0}, {24, 0}}, new byte[][]{{-24, -24}, {0, 24}, {-24, 0}, {24, 0}}, new byte[][]{{-24, 0}, {0, -24}, {-24, 0}, {24, 24}}, new byte[][]{{24, 0}, {0, 24}, {-24, 24}, {24, 0}}, new byte[][]{{24, -24}, {0, 24}, {-24, 0}, {24, 0}}, new byte[][]{{0, -24}, {0, 24}, {-24, 0}, {24, -24}}, new byte[][]{{0, -24}, {-24, 24}, {-24, 0}, {0, -24}}, new byte[][]{{0, -24}, {0, 24}, {-24, -24}, {24, 0}}, new byte[][]{{0, -24}, {24, 24}, {0, -24}, {24, 0}}, new byte[][]{{-24, -24}, {0, 24}, {-24, 0}, {0, 24}}, new byte[][]{{0, -24}, {0, 24}, {-24, 0}, {24, 24}}, new byte[][]{{24, -24}, {0, 24}, {0, 24}, {24, 0}}, new byte[][]{{0, -24}, {0, 24}, {-24, 24}, {24, 0}}, new byte[][]{{0, -24}, {-24, 24}, {-24, 0}, {24, -24}}, new byte[][]{{0, -24}, {24, 24}, {-24, -24}, {24, 0}}, new byte[][]{{24, -24}, {0, 24}, {-24, 24}, {24, 0}}, new byte[][]{{-24, -24}, {0, 24}, {-24, 0}, {24, 24}}};
    }
}

