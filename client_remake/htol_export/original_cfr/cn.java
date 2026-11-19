/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import java.util.Hashtable;
import java.util.Vector;
import javax.microedition.lcdui.Graphics;

public final class cn {
    public int a;
    public static int b = 2;
    public static boolean c = false;
    public static int d = 0;
    public static Vector e = new Vector();
    public static int f = 0;
    public static boolean g = false;
    public Hashtable h = new Hashtable();
    public Hashtable i = null;
    public Hashtable j = null;
    private int as;
    public int k;
    public int l;
    public int m;
    public int n;
    public int o;
    public int p = 0;
    public int q = 0;
    private boolean at = true;
    public boolean r = false;
    public boolean s;
    public boolean t = false;
    static int u;
    private static int[][] au;
    public static cv v;
    private static int[][] av;
    private static int[][] aw;
    public static n w;
    private static n ax;
    public static bh x;
    public static String y;
    public static String z;
    private static n ay;
    public static int A;
    public static int B;
    private static int az;
    private static boolean aA;
    private static int[] aB;
    private static String[] aC;
    private static int aD;
    public static int C;
    public static int D;
    public static String E;
    private static int[] aE;
    private static int aF;
    private static int aG;
    private static int aH;
    private static int aI;
    public static String[] F;
    public static int G;
    public static int H;
    public static int I;
    public static boolean J;
    public static int K;
    public static String[] L;
    public static int[] M;
    public static int N;
    public static int O;
    public static String P;
    public static int Q;
    public static int R;
    public static boolean S;
    private static int aJ;
    private static int aK;
    public static int T;
    private static String aL;
    private static boolean aM;
    private static byte[] aN;
    private static int aO;
    private static boolean aP;
    public static String U;
    private static int aQ;
    public static String V;
    private static int[][] aR;
    public static int[] W;
    public static int[] X;
    public static int[] Y;
    public static short[] Z;
    private static int[] aS;
    public static byte aa;
    public static boolean ab;
    public static int ac;
    public static String ad;
    public static String ae;
    public static boolean af;
    public static int ag;
    public static String[] ah;
    static int ai;
    private static int[][][] aT;
    public static final int[][] aj;
    public static int[][] ak;
    public static int[][] al;
    public static int[] am;
    public static int an;
    public static String[] ao;
    public static boolean ap;
    public static int aq;
    public static n ar;
    private int[][] aU = null;
    private int[][] aV = null;
    private int[][] aW = null;

    public cn(int n2, int n3, int n4) {
        this.as = n4;
        this.a = n2;
        this.k = n3;
        if (n2 == 2) {
            for (int i2 = 0; i2 < 7; ++i2) {
                int[] nArray = new int[4];
                bm.D.a(89, i2, nArray, 0);
                cn.aS[i2 << 2] = nArray[0];
                cn.aS[(i2 << 2) + 1] = nArray[1];
                cn.aS[(i2 << 2) + 2] = nArray[2];
                cn.aS[(i2 << 2) + 3] = nArray[3];
            }
        } else if (n2 == 1) {
            int n5;
            for (n5 = 0; n5 < 20; ++n5) {
                int[] nArray = new int[4];
                n3 = 0;
                n3 = n5 >= 10 ? n5 - 10 : n5;
                bm.D.a(17, n3, nArray, 0);
                cn.aR[n5][0] = nArray[0];
                cn.aR[n5][1] = nArray[1];
            }
            for (n5 = 20; n5 < 22; ++n5) {
                int[] nArray = new int[4];
                n3 = n5 - 10;
                bm.D.a(17, n3, nArray, 0);
                cn.aR[n5][0] = nArray[0];
                cn.aR[n5][1] = nArray[1];
            }
            cn.B();
        }
    }

    private static void B() {
        for (int i2 = 0; i2 < 9; ++i2) {
            cn.aR[i2 + 22][0] = au[i2][0] - 65;
            cn.aR[i2 + 22][1] = au[i2][1];
        }
    }

    public final void a(int n2, int n3, int n4, int n5) {
        this.n = n3;
        this.o = n2;
        this.l = n4;
        this.m = n5;
        this.z();
    }

    public final boolean a(n n2, int n3) {
        Integer n4;
        if (bm.n(47)) {
            bm.X = 3;
        }
        if (this.h.containsKey(n4 = new Integer(n3))) {
            this.h.remove(n4);
        }
        this.h.put(n4, n2);
        if (this == bm.aA) {
            ab.b(n2.d);
            t.a(n2.d, bm.aA.d(n2.d));
            cn.f();
        }
        String cfr_ignored_0 = "add goods" + n2.e + "number" + n2.p;
        return true;
    }

    public final int a(n n2, int n3, int n4) {
        int n5;
        int n6;
        if (n4 == -1) {
            for (n6 = 0; n6 < this.k; ++n6) {
                short s2;
                n n7 = this.b(n6);
                if (n7 == null || n7.d != n2.d || (s2 = n7.o) <= n7.p) continue;
                int n8 = n7.p + n3;
                if (n8 > s2) {
                    n7.a(n2.c, s2 - n7.p);
                    n7.p = s2;
                    return n8 - n7.o;
                }
                n7.a(n2.c, n3);
                n7.p = (short)n8;
                return 0;
            }
        }
        if (n4 == -1) {
            n5 = this.c(n2);
        } else {
            n6 = n4;
            n n9 = n2;
            cn cn2 = this;
            int n10 = n6;
            if (cn2.a == 2 && !n9.d(X[n6])) {
                n10 = -1;
            }
            n5 = n6 = n10;
        }
        if (n5 == -1) {
            return -1;
        }
        if (this.b(n6) != null) {
            return -1;
        }
        short s3 = this.a == 2 ? Z[n6] : n2.o;
        n n11 = new n();
        n11.a(n2);
        n11.o = s3;
        n11.a();
        n11.c = (byte)n6;
        if (n3 <= s3) {
            n11.p = (short)n3;
            this.a(n11, (int)n11.c);
            n11.a(n2.c, n3);
            return 0;
        }
        n11.p = s3;
        this.a(n11, (int)n11.c);
        n11.a(n2.c, (int)s3);
        return n3 - s3;
    }

    private boolean i(int n2) {
        return this.a != 2 || X[n2] != -1;
    }

    public final int a() {
        int n2 = 0;
        for (int i2 = 0; i2 < this.k; ++i2) {
            n n3;
            if (!this.i(i2) || (n3 = this.b(i2)) != null) continue;
            ++n2;
        }
        return n2;
    }

    private int c(n n2) {
        for (int i2 = 0; i2 < this.k; ++i2) {
            if (!this.i(i2)) continue;
            n n3 = this.b(i2);
            if (n3 != null) {
                if (n2.d != n3.d || n3.o <= n3.p) continue;
                return i2;
            }
            if (this.a == 2) {
                if (!n2.d(X[i2])) continue;
                return i2;
            }
            return i2;
        }
        return -1;
    }

    public final void a(int n2, int n3) {
        for (int i2 = 0; i2 < this.k; ++i2) {
            n n4 = this.b(i2);
            if (n4 == null || n4.d != n2) continue;
            n4.a(n3);
        }
    }

    public final void b() {
        n n2;
        int n3;
        int n4 = 0;
        for (n3 = 0; n3 < this.k; ++n3) {
            n2 = this.b(n3);
            if (n2 == null || n2.b <= 0) continue;
            ++n4;
        }
        bm.bn.a((byte)n4);
        for (n3 = 0; n3 < this.k; ++n3) {
            n2 = this.b(n3);
            if (n2 == null || n2.b <= 0) continue;
            bm.bn.a(n2.d);
            bm.bn.a(n2.c);
            bm.bn.a(n2.b);
        }
    }

    public final boolean a(byte by2) {
        n n2;
        int n3;
        int n4 = 0;
        for (n3 = 0; n3 < this.k; ++n3) {
            n2 = this.b(n3);
            if (n2 == null) continue;
            ++n4;
        }
        bm.bn.a(by2);
        bm.bn.a((byte)n4);
        String cfr_ignored_0 = "strong goods' number " + n4;
        for (n3 = 0; n3 < this.k; ++n3) {
            n2 = this.b(n3);
            if (n2 == null) continue;
            String string = "ID:" + n2.d;
            bm.bn.a(n2.d);
            n4 = n2.b();
            if (n4 <= 0) continue;
            string = string + "number" + n4;
            bm.bn.a((byte)n4);
            for (n4 = 0; n4 < n2.x.length; ++n4) {
                if (n2.x[n4] == 0) continue;
                bm.bn.a((byte)(n2.x[n4] >> 8));
                bm.bn.a((byte)n2.x[n4]);
                bm.bn.a((byte)n3);
                bm.bn.a((byte)(n3 == ac ? 1 : 0));
                string = string + " POS " + (n2.x[n4] >> 8) + " Num " + (n2.x[n4] & 0xFF) + " | ";
            }
        }
        return true;
    }

    public final void c() {
        for (int i2 = 0; i2 < this.k; ++i2) {
            n n2 = this.b(i2);
            if (n2 == null || n2.b <= 0) continue;
            n2.b = 0;
        }
        this.t = false;
    }

    public final boolean a(int n2) {
        Integer n3 = new Integer(n2);
        if (this.h.containsKey(n3)) {
            n n4 = (n)this.h.get(n3);
            this.h.remove(n3);
            if (this == bm.aA) {
                ab.b(n4.d);
                t.a(n4.d, bm.aA.d(n4.d));
                cn.f();
            }
            String cfr_ignored_0 = "to delete goods is successful" + bm.A(1083) + n2;
        } else {
            String cfr_ignored_1 = "to delete goods fail" + bm.A(1083) + n2;
        }
        return true;
    }

    public final boolean b(int n2, int n3) {
        n n4 = this.b(n2);
        if (n4 != null) {
            if (n4.p <= n3) {
                this.a(n2);
                String cfr_ignored_0 = "clear goods" + n4.e + "number" + n3;
            } else {
                n4.p = (short)(n4.p - n3);
                if (this == bm.aA) {
                    ab.b(n4.d);
                    t.a(n4.d, bm.aA.d(n4.d));
                }
                String cfr_ignored_1 = "the number of goods decrease" + n4.e + "number" + n3;
            }
            if (this == bm.aA) {
                cn.f();
            }
        } else {
            String cfr_ignored_2 = "delete goods" + n4.e + "number" + n3 + "not success  position" + n2;
        }
        return true;
    }

    public final int d() {
        return this.h.size();
    }

    public final void e() {
        this.h.clear();
        if (this.a == 2) {
            for (int i2 = 0; i2 < 7; ++i2) {
                cn.X[i2] = -1;
            }
        }
    }

    public final void a(int n2, int n3, String string) {
        for (int i2 = 0; i2 < this.k; ++i2) {
            n n4 = this.b(i2);
            if (n4 == null || n4.d != n3 || n4.c != n2) continue;
            n4.t = string;
        }
    }

    public final void a(int n2, String string) {
        for (int i2 = 0; i2 < this.k; ++i2) {
            n n3 = this.b(i2);
            if (n3 == null || n3.d != n2) continue;
            n3.f = string;
        }
    }

    public static void f() {
        if (bm.aQ == bm.aD) {
            int n2 = bm.aA.k;
            if (bm.aD == null) {
                bm.aD = new cn(0, n2, 2);
                bm.aD.a(5, 8, 294, 127);
            } else {
                bm.aD.e();
                bm.aD.h.clear();
            }
            int n3 = 0;
            for (int i2 = 0; i2 < n2; ++i2) {
                n n4 = bm.aA.b(i2);
                if (n4 == null || (n4.h & 0x4000) != 16384 && !n4.f() || n4.p <= 0) continue;
                bm.aD.a(n4, n3);
                ++n3;
            }
            bm.aR = bm.aQ = bm.aD;
            bm.aQ.h();
        }
    }

    private n j(int n2) {
        int n3 = -1;
        if (this.j == null) {
            n3 = n2;
        } else if (this.j.get(new Integer(n2)) != null) {
            n3 = (Integer)this.j.get(new Integer(n2));
        }
        if (n3 == -1) {
            return null;
        }
        return this.b(n3);
    }

    public final n b(int n2) {
        Integer n3 = new Integer(n2);
        if (this.h.containsKey(n3)) {
            return (n)this.h.get(n3);
        }
        return null;
    }

    public final n c(int n2) {
        for (int i2 = 0; i2 < this.k; ++i2) {
            n n3 = this.b(i2);
            if (n3 == null || n3.d != n2) continue;
            return n3;
        }
        return null;
    }

    public final int d(int n2) {
        int n3 = 0;
        for (int i2 = 0; i2 < this.k; ++i2) {
            n n4 = this.b(i2);
            if (n4 == null || n4.d != n2 || n2 <= 0) continue;
            n3 += n4.p;
        }
        return n3;
    }

    public final void a(int n2, long l2) {
        for (int i2 = 0; i2 < this.k; ++i2) {
            n n3 = this.b(i2);
            if (n3 == null || n3.d != n2 || n2 <= 0) continue;
            n3.w = l2;
        }
    }

    public final int e(int n2) {
        int n3 = 0;
        for (int i2 = 0; i2 < this.k; ++i2) {
            n n4 = this.b(i2);
            if (n4 == null || !n4.d(n2)) continue;
            n3 += n4.p;
        }
        return n3;
    }

    public final void g() {
        this.q = 0;
        this.p = 0;
    }

    public final void h() {
        int n2;
        y = "";
        z = "";
        if (this.a == 1) {
            if (u == 2) {
                n2 = this.q;
                if (this.q > 21 && this.q < 31) {
                    n2 -= 22;
                }
                cr cr2 = bh.e(bm.v);
                if (bm.aJ != 1 || cr2 == null) {
                    cr2 = bm.cb;
                }
                w = cr2.ao[n2];
            } else {
                w = this.j(this.q);
            }
        } else {
            w = this.j(this.q);
        }
        if (w != null && cn.w.p - cn.w.b > 0) {
            if (cn.w.f == null) {
                cn.w.f = "";
                bm.bn.S(cn.w.d);
            }
            z = w.a(true) + "^" + cn.w.E + cn.w.e + "^" + cn.w.E;
            cn.E();
        }
        if (this.a == 1) {
            if (u == 2 && this.q == 0) {
                this.q = 22;
            }
            bm.c(aR[this.q][0] + -2, aR[this.q][1] + -2);
            return;
        }
        if (this.a == 2) {
            bm.c(aS[this.q << 2] + -2 + -55, aS[(this.q << 2) + 1] + -2 + 10);
            return;
        }
        if (this.a == 3) {
            if (this.q < 0) {
                this.q = 0;
            }
            bm.c(o.a_[this.q][0], o.a_[this.q][1]);
            return;
        }
        n2 = this.q - this.p * this.n;
        int n3 = n2 / this.n;
        bm.c(this.l + (n2 %= this.n) * 29, this.m + n3 * 29);
    }

    public final void f(int n2) {
        this.k = n2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void a(Graphics object) {
        int n2;
        int n3;
        int n4;
        int n5;
        int n6;
        Object object2;
        block61: {
            Object object3;
            block60: {
                int n7;
                Object object4;
                block58: {
                    cn cn2;
                    block59: {
                        block57: {
                            if (!cn2.at) {
                                return;
                            }
                            if (cn2.a != 1) break block57;
                            if (u == 2) {
                                cn2 = object;
                                cn.a((Graphics)cn2, 180, 130, 45, 1);
                                cn.a((Graphics)cn2, 180, 175, 45, 4);
                                cn.a((Graphics)cn2, 180, 220, 45, 7);
                                cn cn3 = cn2;
                                cr cr2 = bh.e(bm.v);
                                if (bm.aJ != 1 || cr2 == null) {
                                    cr2 = bm.cb;
                                }
                                int n8 = 0;
                                while (n8 < cr2.ao.length) {
                                    n n9 = cr2.ao[n8];
                                    if (n9 != null) {
                                        n9.a((Graphics)cn3, au[n8][0] + 3, au[n8][1] + 3, false, false);
                                    }
                                    ++n8;
                                }
                                return;
                            }
                            object4 = object;
                            bm.D.a((Graphics)object4, 17, 65, 0, 0);
                            n7 = 0;
                            if (u == 1) {
                                n7 = 10;
                            }
                            break block58;
                        }
                        if (cn2.a != 2) break block59;
                        object3 = object;
                        bm.D.a((Graphics)object3, 89, 0, 0, 0);
                        bm.D.a((Graphics)object3, 8, (640 - bm.D.f(8) >> 1) + 65, 285, 0);
                        bz.a(object3, -597591, -13032703, 4);
                        bz.a(object3, bm.A(1130), 385, 289, 1);
                        break block60;
                    }
                    if (cn2.a == 3) {
                        ao ao2;
                        Object object5 = object;
                        ay.c(object5, 165, 165, 90, 32, 6113836, 9142619);
                        bm.D.a((Graphics)object5, 113, 175, 72, 0);
                        bm.D.a((Graphics)object5, 130, 30, 0, 0);
                        bz.b((byte)0);
                        object5.setColor(16179625);
                        bz.a(object5, cv.D, 211, 172, 1);
                        bz.b((byte)1);
                        if (v != null && (ao2 = cn.v.d) != null) {
                            v.b((Graphics)object5, 210, 138);
                            v.c();
                            object5.setClip(0, 0, 640, 360);
                        }
                        int n10 = 0;
                        while (n10 < 8) {
                            n n11 = cn2.b(n10);
                            if (n11 != null && n11.c >= 0 && n11.c < 3) {
                                n11.a((Graphics)object5, o.a_[n11.c][0] + 3 + 65, o.a_[n11.c][1] + 1, false, false);
                            }
                            ++n10;
                        }
                        return;
                    }
                    if (cn2.a == 4) {
                        cn2 = object;
                        cn2.setColor(65535);
                        ck.b(ai).a((Graphics)cn2, 1, 265, 245, 0);
                        bm.D.a((Graphics)cn2, 8, (640 - bm.D.f(8) >> 1) + 65, 285, 0);
                        bz.a((Graphics)cn2, -597591, -13032703, 4);
                        bz.a((Graphics)cn2, bm.A(1130), 385, 289, 1);
                        bm.D.a((Graphics)cn2, 90, 0, 0, 0);
                        bz.a((Graphics)cn2, -597591, -13032703, 4);
                        bz.a((Graphics)cn2, ae, 320, 360 - bm.D.g(90) + 2, 1);
                        return;
                    }
                    object2 = object;
                    n6 = cn2.l + 65;
                    n5 = cn2.m;
                    if (cn2.s) {
                        n4 = 0;
                        if (bm.x() != 59 && bm.x() != 51) {
                            ay.a(object2, cn2.l - 8 + 65, cn2.m - 8, 29 * cn2.n + 24, 29 * cn2.o + 16, false, "", false);
                        } else {
                            ay.a(object2, cn2.l - 8, cn2.m - 8, 29 * cn2.n + 24, 29 * cn2.o + 16, false, "", false);
                        }
                    }
                    n4 = cn2.l;
                    if (bm.x() != 59 && bm.x() != 51) {
                        n4 = cn2.l + 65;
                    }
                    ay.b(object2, n4, cn2.m, cn2.p, cn2.o, cn2.n, cn2.k);
                    if (cn2.n == 0) return;
                    n3 = cn2.p * cn2.n;
                    n2 = 0;
                    n2 = cn2.k / cn2.n;
                    if (cn2.k % cn2.n > 0) {
                        ++n2;
                    }
                    break block61;
                }
                int n12 = 0;
                while (n12 < cn2.k) {
                    if (n12 >= n7 && n12 < n7 + 10 || n12 >= 20) {
                        n n13 = cn2.b(n12);
                        if (n13 != null) {
                            n13.a((Graphics)object4, aR[n12][0] + 65, aR[n12][1], false, false);
                        } else if (n12 == 20) {
                            ck.b(23).j(8);
                            ck.b(23).a((Graphics)object4, 66, aR[n12][0] + 65, aR[n12][1], 0);
                        } else if (n12 == 21) {
                            ck.b(23).j(8);
                            ck.b(23).a((Graphics)object4, 65, aR[n12][0] + 65, aR[n12][1], 0);
                        } else if (n12 <= 21) {
                            bz.a(object4, 0xFFFFFF, 0, -1);
                            ay.a(object4, bn.h[n12], aR[n12][0] + 65 + 12, aR[n12][1] - 5, 58, 29, 2, 1);
                        }
                    }
                    ++n12;
                }
                return;
            }
            for (int i2 = 0; i2 < 7; ++i2) {
                int n14 = aS[i2 << 2] + 65;
                int n15 = aS[(i2 << 2) + 1];
                int n16 = aS[(i2 << 2) + 2];
                int n17 = aS[(i2 << 2) + 3];
                if (X[i2] == -1) continue;
                bz.a(object3, 7820825, 0, -1);
                bz.a(object3, ah[i2], n14 - 65 + (n16 >> 1), n15 + (n17 >> 1), 3);
                n n18 = cn2.b(i2);
                int n19 = n14 + 15 - 4 - 65;
                int n20 = n15 + 15 - 5;
                n14 -= 65;
                if (n18 != null) {
                    n18.a((Graphics)object3, n19, n20, false, true);
                    if (n18.v == 0) {
                        if (n18.c()) {
                            object3.setColor(0xFF0000);
                            object3.drawRect(n14 + 1, n15 + 1, n16 - 2, n17 - 2);
                            object3.drawRect(n14, n15, n16, n17);
                            object3.drawRect(n14 - 1, n15 - 1, n16 + 2, n17 + 2);
                        }
                    } else if (n18.v == -1) {
                        object3.setColor(0xFF0000);
                        object3.drawRect(n14 + 1, n15 + 1, n16 - 2, n17 - 2);
                        object3.drawRect(n14, n15, n16, n17);
                        object3.drawRect(n14 - 1, n15 - 1, n16 + 2, n17 + 2);
                    } else if (n18.v == 1) {
                        object3.setColor(0x1F88FF);
                        object3.drawRect(n14 + 1, n15 + 1, n16 - 2, n17 - 2);
                        object3.drawRect(n14, n15, n16, n17);
                        object3.drawRect(n14 - 1, n15 - 1, n16 + 2, n17 + 2);
                    }
                }
                if (ac != i2) continue;
                object3.setColor(0x1F88FF);
                object3.drawRect(n14 + 1, n15 + 1, n16 - 2, n17 - 2);
                object3.drawRect(n14, n15, n16, n17);
                object3.drawRect(n14 - 1, n15 - 1, n16 + 2, n17 + 2);
            }
            if (bm.aT != 0) {
                bm.D.j(1);
                bm.D.a((Graphics)object3, 94, 0, 0, 0);
                bm.D.j(0);
                return;
            }
            if (ag == 1) {
                bm.D.a((Graphics)object3, 215, 0, 0, 0);
            } else {
                bm.D.a((Graphics)object3, 90, 0, 0, 0);
            }
            bz.a(object3, -597591, -13032703, 4);
            switch (ag) {
                case 1: {
                    ae = bm.A(1132);
                    break;
                }
            }
            if (ag != 1) {
                bz.a(object3, ae, 320, 360 - bm.D.g(90) + 2, 1);
                return;
            }
            ay.a(object3, ae, 257, 360 - bm.D.g(90), 97, bm.D.g(90) - 5, 2, 1);
            String string = null;
            string = S ? bm.A(1134) : bm.A(289);
            ay.a(object3, string, 380, 360 - bm.D.g(90), 97, bm.D.g(90) - 5, 2, 1);
            return;
        }
        object2.setClip(n4, cn2.m, cn2.n * 29, cn2.o * 29);
        int n21 = 0;
        while (true) {
            int n22;
            int n23;
            if (n21 < n2) {
            } else {
                if (cn2.as != 5 && cn2.as != 6) {
                    n21 = cn2.q / cn2.n;
                    n23 = cn2.k / cn2.n + (cn2.k % cn2.n == 0 ? 0 : 1);
                    n22 = n6 + 29 * cn2.n;
                    int n24 = 118 * cn2.o / 4 - 3;
                    if (bm.x() == 59 || bm.x() == 51) {
                        n22 = n6 + 29 * cn2.n - 65;
                    }
                    ay.a(object2, n22, n5, 11, n24, n21, cn2.o, n23);
                }
                object2.setClip(0, 0, 640, 360);
                return;
            }
            for (n23 = 0; n23 < cn2.n; ++n23) {
                n n25;
                n22 = n3 + n21 * cn2.n + n23;
                if (n22 >= cn2.k || (n25 = cn2.j(n22)) == null) continue;
                n22 = cn2.t ? n25.p - n25.b : n25.p;
                if (n25.E > 0 && n22 > 0) {
                    object2.setColor(n25.k());
                    if (bm.x() != 59 && bm.x() != 51) {
                        object2.drawRect(n6 + n23 * 29 + 1, n5 + n21 * 29 + 1, 27, 27);
                    } else {
                        object2.drawRect(n6 + n23 * 29 + 1 - 65, n5 + n21 * 29 + 1, 27, 27);
                    }
                }
                n22 = n6 + n23 * 29 + 3;
                int n26 = n5 + n21 * 29 + 3;
                if (cn2.as == 1 || cn2.as == 4) {
                    if (cn2.r) {
                        n25.a((Graphics)object2, n22, n26, n25.p, false, n25.o > 1);
                    } else {
                        n25.a((Graphics)object2, n22, n26, 1, false, n25.o > 1);
                    }
                } else {
                    n22 = n6 + n23 * 29 + 3 - 65;
                    if (bm.x() != 59 && bm.x() != 51) {
                        n22 = n6 + n23 * 29 + 3;
                    }
                    if (cn2.t) {
                        n25.a((Graphics)object2, n22, n26, n25.p - n25.b, false, true);
                    } else {
                        n25.a((Graphics)object2, n22, n26, false, true);
                    }
                }
                ab.a(object2, n22, n26, System.currentTimeMillis(), n25.w, n25.r);
            }
            ++n21;
        }
    }

    private static void a(Graphics graphics, int n2, int n3, int n4, int n5) {
        ay.b(graphics, 180, n3);
        ay.b(graphics, 225, n3);
        ay.b(graphics, 270, n3);
        graphics.drawRect(175, n3 - 5, 135, 40);
        graphics.setColor(0xFFFFFF);
        bz.a(graphics, n5 + "", 191, n3 + 7);
        bz.a(graphics, n5 + 1 + "", 236, n3 + 7);
        bz.a(graphics, n5 + 2 + "", 281, n3 + 7);
    }

    public static void b(Graphics graphics) {
        bz.a(graphics, 0, -13870883, -1);
        int[] nArray = new int[]{1, 1};
        ay.a(graphics, 291, 124, 290, 150, 4, nArray);
        int n2 = cv.r;
        for (int i2 = 0; i2 < 4; ++i2) {
            bz.a(graphics, 16179625, -13032703, -1);
            if (n2 < 16) {
                int n3;
                String string = cv.q[n2];
                if (n2 >= 6 && n2 <= 11 && (1 << n2 - 6 & cv.F) > 0) {
                    string = string + bm.A(1117);
                }
                bz.a(graphics, string, 296, 135 + i2 * 37);
                if (n2 == 0) {
                    bz.a(graphics, cv.c((int)cv.G[n2]), 431, 135 + i2 * 37, 8);
                } else if (n2 != 4) {
                    if (n2 >= 12 && n2 < 16) {
                        int n4 = (int)cv.G[n2] / 100;
                        n3 = (int)cv.G[n2] % 100;
                        bz.a(graphics, "" + n4 + "." + n3 + "%", 431, 135 + i2 * 37, 8);
                    } else {
                        bz.a(graphics, "" + cv.G[n2], 431, 135 + i2 * 37, 8);
                    }
                }
                String string2 = cv.q[n2 + 1];
                if (n2 + 1 >= 6 && n2 + 1 <= 11 && (1 << n2 - 5 & cv.F) > 0) {
                    string2 = string2 + bm.A(1117);
                }
                bz.a(graphics, string2, 441, 135 + i2 * 37);
                if (n2 + 1 == 3) {
                    bz.a(graphics, "" + cv.G[n2 + 1] + "/200", 576, 135 + i2 * 37, 8);
                } else if (n2 + 1 >= 12 && n2 + 1 <= 16) {
                    int n5 = (int)cv.G[n2 + 1] / 100;
                    n3 = (int)cv.G[n2 + 1] % 100;
                    bz.a(graphics, "" + n5 + "." + n3 + "%", 576, 135 + i2 * 37, 8);
                } else {
                    bz.a(graphics, "" + cv.G[n2 + 1], 576, 135 + i2 * 37, 8);
                }
                n2 += 2;
                continue;
            }
            if (n2 < 16 || n2 >= 19) continue;
            if (n2 == 18) {
                bz.a(graphics, cv.q[n2], 296, 135 + i2 * 37);
                bz.a(graphics, "" + cv.G[n2 + 1] + "/" + cv.G[n2], 576, 135 + i2 * 37, 8);
            } else {
                bz.a(graphics, cv.q[n2], 296, 135 + i2 * 37);
                bz.a(graphics, "" + cv.G[n2], 576, 135 + i2 * 37, 8);
            }
            ++n2;
        }
        ay.a(graphics, 586, 124, 12, 150, cv.r, 4, 12, cv.u);
        bm.G.c();
        if (n2 > 8) {
            bm.G.a(graphics, 430, 119, 2);
        }
        if (n2 != 19) {
            bm.G.a(graphics, 430, 279, 0);
        }
    }

    public final int a(boolean n2) {
        if (bm.v(196608) || bm.am() || bm.dB) {
            if (w != null) {
                if (cn.w.p <= 0) {
                    return 0;
                }
                if (this.t && cn.w.p - cn.w.b <= 0) {
                    return 0;
                }
                if (cn.w.g == 3) {
                    boolean bl2 = true;
                    Object var0_2 = null;
                    var0_2 = null;
                    boolean bl3 = false;
                    cn.a(false, true, 10);
                } else {
                    bm.bn.a(ag, bm.aR.a, bm.aJ, cn.w.c, cn.w.d);
                }
                bm.X = 3;
            } else if (bm.aR.a == 1) {
                if (this.q == 20) {
                    bm.d(bm.A(1118));
                } else if (this.q == 21) {
                    bm.d(bm.A(1119));
                }
            }
            return 0;
        }
        if (this.a == 1) {
            if (bm.aQ == null && (bm.e(457, 95, 28, 28) || bm.e(457, 245, 28, 28) || bm.e(580, 105, 25, 150))) {
                return 2;
            }
            n2 = cn.D();
            if (n2 == -1) {
                return 0;
            }
            n2 = av[this.q][n2];
            if (u == 1 && n2 < 20) {
                n2 += 10;
            }
            if (u == 2 && n2 < 10 && n2 > 0) {
                n2 += 21;
            }
            if (n2 == -1) {
                return 2;
            }
            this.q = n2;
            this.h();
            return 0;
        }
        if (this.a == 2) {
            bm.g(20, 180);
            n2 = cn.D();
            if (n2 != -1) {
                int[] nArray = aT[this.q][n2];
                for (int i2 = 0; i2 < nArray.length; ++i2) {
                    if (nArray[i2] == -1) {
                        return 2;
                    }
                    if (!this.i(nArray[i2])) continue;
                    this.q = nArray[i2];
                    this.h();
                    break;
                }
            }
            return 0;
        }
        if (this.a == 3) {
            if (bm.aQ == null && (bm.e(422, 96, 28, 28) || bm.e(422, 274, 28, 28) || bm.e(580, 115, 25, 170))) {
                return 2;
            }
            if (this.q <= 0) {
                this.q = 0;
            }
            if ((n2 = cn.D()) == -1) {
                return 0;
            }
            if ((n2 = aw[this.q][n2]) == -1) {
                this.q = 0;
                return 2;
            }
            this.q = n2;
            this.h();
            return 0;
        }
        if (this.a != 4 && this.a == 0) {
            return this.i();
        }
        return 0;
    }

    private void C() {
        if (this.q >= this.k) {
            this.q = this.k - 1;
            if (this.q < 0) {
                this.q = 0;
                this.p = 0;
            }
        }
        int n2 = this.q / this.n;
        int n3 = this.k / this.n + (this.k % this.n == 0 ? 0 : 1);
        if (n2 >= this.p + this.o && n2 < n3) {
            bm.X = 3;
            ++this.p;
        }
        this.h();
    }

    public final int i() {
        if (bm.x() == 51 || bm.x() == 59) {
            bm.a(this.l - 30, this.m - 15, 29 * this.n + 60, 29 * this.o + 30, 10, true);
        } else {
            bm.a(this.l + 65 - 30, this.m - 15, 29 * this.n + 60, 29 * this.o + 30, 10, true);
        }
        if (bm.v(4112)) {
            if (this.q == 0) {
                return 1;
            }
            if (this.as == 5 && this.q % this.n == 0) {
                return 3;
            }
            --this.q;
            if (this.q < this.p * this.n && this.p > 0) {
                --this.p;
            }
            if (this.q < 0) {
                this.q = 0;
                this.p = 0;
            }
            this.h();
        } else if (bm.v(8256)) {
            if (this.as == 6 && this.q % this.n == this.n - 1) {
                return 4;
            }
            ++this.q;
            this.C();
        } else if (bm.v(16388)) {
            int n2;
            if (this.q < this.n) {
                return 1;
            }
            this.q -= this.n;
            if (this.q < 0) {
                this.q = 0;
                this.p = 0;
            }
            if ((n2 = this.q / this.n) < this.p) {
                bm.X = 3;
                --this.p;
                if (this.p < 0) {
                    this.p = 0;
                }
            }
            this.h();
            bm.X = 3;
        } else if (bm.v(33024)) {
            if (this.q == this.k - 1) {
                return 2;
            }
            this.q += this.n;
            this.C();
            bm.X = 3;
        }
        return 0;
    }

    private static int D() {
        int n2 = -1;
        if (bm.v(16388)) {
            n2 = 0;
        } else if (bm.v(33024)) {
            n2 = 1;
        }
        if (bm.v(4112)) {
            n2 = 2;
        } else if (bm.v(8256)) {
            n2 = 3;
        }
        return n2;
    }

    public final void a(int[] nArray) {
        if (this.j(this.q) == null) {
            return;
        }
        am = null;
        am = nArray;
        an = 0;
        if (am != null) {
            bm.aT = 1;
        }
    }

    public final boolean j() {
        if ((((cn)((Object)string)).q <= 21 || ((cn)((Object)string)).q >= 31) && ((cn)((Object)string)).j(((cn)((Object)string)).q) == null) {
            return false;
        }
        am = null;
        switch (bm.aJ) {
            case 0: {
                if (((cn)((Object)string)).a == 1) {
                    am = aj[3];
                    break;
                }
                if (((cn)((Object)string)).a != 0) break;
                if (bm.aT != 5) {
                    if (cn.w.g == 1) {
                        am = aj[6];
                        break;
                    }
                    if (cn.w.g == 4) {
                        am = aj[30];
                        break;
                    }
                    if (w.a((short)1)) {
                        if (w.d(0x2000000)) {
                            am = aj[24];
                            break;
                        }
                        am = aj[8];
                        break;
                    }
                    am = aj[7];
                    break;
                }
                am = aj[18];
                break;
            }
            case 1: {
                if (((cn)((Object)string)).a != 1) break;
                am = aj[0];
                break;
            }
            case 2: {
                if (((cn)((Object)string)).as == 0) {
                    if (b == 0) {
                        am = aj[0];
                        break;
                    }
                    if (c) {
                        am = aj[1];
                        break;
                    }
                    am = aj[0];
                    break;
                }
                if (b == 0) {
                    am = aj[17];
                    break;
                }
                am = aj[2];
                break;
            }
            case 4: {
                if (cn.w.g == 1) {
                    am = aj[9];
                    break;
                }
                int n2 = 0;
                String string = bm.B(218);
                cn.a(string, n2, -1);
                return false;
            }
            case 5: {
                if (G == 0) {
                    if (((cn)((Object)string)).as == 5) {
                        am = aj[11];
                        break;
                    }
                    am = aj[10];
                    break;
                }
                am = aj[0];
                break;
            }
            case 6: {
                if (bm.aR == bm.aA) {
                    am = aj[12];
                    break;
                }
                am = aj[13];
                break;
            }
            case 9: {
                if (bm.aR != bm.aA) break;
                am = aj[19];
                break;
            }
            case 3: {
                if (ab) {
                    if (bm.aR == bm.aQ) {
                        am = aj[27];
                        break;
                    }
                    am = aj[28];
                    break;
                }
                if (bm.aR == bm.aA) {
                    if (w.d(0x2000000)) {
                        am = aj[25];
                        break;
                    }
                    if (cn.w.g == 4) {
                        am = aj[31];
                        break;
                    }
                    am = aj[15];
                    break;
                }
                if (ag == 9) {
                    if (((cn)((Object)string)).b(((cn)((Object)string)).q).c()) {
                        am = aj[22];
                        break;
                    }
                    if (w.d(0x2000000)) {
                        am = aj[26];
                        break;
                    }
                    am = aj[16];
                    break;
                }
                if (ag == 8 || ag == 7) {
                    if (((cn)((Object)string)).b(((cn)((Object)string)).q).c()) {
                        am = aj[23];
                        break;
                    }
                    if (w.d(0x2000000)) {
                        am = aj[26];
                        break;
                    }
                    am = aj[16];
                    break;
                }
                if (w.d(0x2000000)) {
                    am = aj[26];
                    break;
                }
                am = aj[16];
                break;
            }
            case 7: {
                if (((cn)((Object)string)).a != 3) break;
                am = aj[0];
                break;
            }
            case 8: {
                if (((cn)((Object)string)).a == 3) {
                    am = aj[3];
                    break;
                }
                if (((cn)((Object)string)).a != 0) break;
                am = cn.w.g == 1 ? aj[6] : (w.a((short)1) ? aj[8] : aj[7]);
            }
        }
        an = 0;
        if (am != null) {
            bm.aT = 1;
        }
        return true;
    }

    public static cn a(byte by2, boolean bl2, int n2) {
        if (by2 == 0) {
            if (!bl2) {
                return bm.aA;
            }
            if (bm.aA == null) {
                bm.aA = new cn(0, n2, 0);
                bm.aA.a(5, 8, 294, 127);
            } else {
                bm.aA.e();
                bm.aA.k = n2;
            }
            return bm.aA;
        }
        if (by2 == 1) {
            if (!bl2) {
                return bm.aB;
            }
            if (bm.aB == null) {
                bm.aB = new cn(1, 22, 0);
                bm.aB.z();
            } else {
                bm.aB.e();
            }
            return bm.aB;
        }
        if (by2 == 2) {
            if (!bl2) {
                return bm.aE;
            }
            if (bm.aE == null) {
                bm.aE = new cn(0, n2, 7);
                bm.aE.a(4, 8, 294, 50);
            } else {
                bm.aE.e();
                bm.aE.k = n2;
            }
            return bm.aE;
        }
        if (by2 == 3) {
            if (!bl2) {
                return bm.aC;
            }
            if (bm.aC == null) {
                bm.aC = new cn(3, 3, 0);
            } else {
                bm.aC.e();
            }
        } else if (by2 == 4) {
            if (!bl2) {
                return bm.aD;
            }
            if (bm.aD == null) {
                bm.aD = new cn(0, n2, 0);
                bm.aD.a(5, 8, 294, 127);
            } else {
                bm.aD.e();
                bm.aD.k = n2;
            }
            return bm.aD;
        }
        return null;
    }

    public static void k() {
        bm.X = 3;
        if (bm.aR != null) {
            bm.aR.h();
        }
        if (bm.ac() && bm.n(47)) {
            bm.ae();
            bm.aT = 0;
        }
    }

    public static boolean l() {
        boolean bl2 = false;
        if (bm.aR != null && (bm.aR.as == 1 || bm.aR.as == 4)) {
            bl2 = true;
        }
        return bl2;
    }

    private static void E() {
        Object object;
        if (w == null) {
            return;
        }
        if (cn.w.g != 1) {
            y = cn.w.g == 0 && w.d(32) && cn.w.s.length == 1 ? bm.A(1120) + cn.w.s[0] : cn.w.f;
            if (cn.w.g == 4 && cn.w.A > 0) {
                z = z + "+" + cn.w.A;
            }
            if (cn.w.k > 0) {
                String string = cn.w.k + bm.A(370);
                z = cn.w.k > bm.cb.U ? z + " ^r" + string + "^r" : z + " " + string;
            }
            if (cn.l()) {
                z = b == 0 && cr.av < cn.w.m || b == 5 && d < cn.w.m || b == 6 && d < cn.w.m || b == 2 && cr.at < cn.w.m ? z + bm.A(1121) + cn.w.m + "^r" : z + bm.A(1122) + cn.w.m;
                int n2 = cn.k(cn.w.m);
                if (n2 != cn.w.m) {
                    z = z + "(" + P + n2 + ")";
                    return;
                }
            } else if (cn.w.n > 0) {
                z = z + bm.A(1123) + cn.w.n;
            }
            return;
        }
        z = w.a(true) + "^" + cn.w.E + cn.w.e + "^" + cn.w.E;
        if (cn.w.A > 0) {
            z = z + "+" + cn.w.A;
        }
        if (cn.w.M != null && cn.w.M.length > 0) {
            object = " [";
            block5: for (int i2 = 0; i2 < cn.w.M.length; ++i2) {
                switch (cn.w.M[i2]) {
                    case 0: {
                        object = (String)object + "\u3007";
                        continue block5;
                    }
                    case 1: {
                        object = (String)object + "\u203b";
                        continue block5;
                    }
                    case 2: {
                        object = (String)object + "^r\u203b^r";
                    }
                }
            }
            z = z + (String)object + "] ";
        }
        object = bm.d(0, bj.Q[cn.w.z]);
        String string = " " + cn.w.k + bm.A(370);
        if (!w.f()) {
            z = cn.w.z != bm.cb.u && cn.w.z != 3 ? z + " ^r" + (String)object + "^r" : z + " " + (String)object;
        }
        z = cn.w.k > bm.cb.U && !w.f() ? z + "^r" + string + "^r" : z + "" + string;
        object = cn.a(w);
        if (object != null) {
            for (int i3 = 0; i3 < 14; ++i3) {
                y = y + cn.a((n)object, w, i3, true);
            }
        }
        if (cn.l()) {
            if (b == 0 && cr.av < cn.w.m || b == 5 && d < cn.w.m || b == 6 && d < cn.w.m || b == 2 && cr.at < cn.w.m) {
                z = z + bm.A(1121) + cn.w.m + "^r";
                return;
            }
            z = z + bm.A(1122) + cn.w.m;
            return;
        }
        if (cn.w.n > 0) {
            z = z + " " + n.y[cn.w.u];
        }
    }

    static String a(n n2, n n3, int n4, boolean bl2) {
        int n5;
        int n6 = 0;
        int n7 = 0;
        if (n2 != null) {
            n6 = n2.b(n4);
        }
        if (n3 != null) {
            n7 = n3.b(n4);
        }
        if ((n5 = n7 - n6) == 0) {
            return "";
        }
        char c2 = n5 > 0 ? (char)'\u2191' : '\u2193';
        String string = "";
        if (bl2) {
            string = bn.i[n4] + ":";
        }
        if (n5 > 0) {
            return "^g" + string + c2 + Math.abs(n5) + "^g ";
        }
        return "^r" + string + c2 + Math.abs(n5) + "^r ";
    }

    static n a(n n2) {
        if (n2 == null || n2.g != 1) {
            return null;
        }
        if (w.f() && bm.aJ != 8 || !w.f() && bm.aJ != 0) {
            return null;
        }
        int n3 = n2.B;
        if (n3 > 5) {
            ++n3;
        }
        cn cn2 = bm.aB;
        if (n2.f()) {
            cn2 = bm.aC;
        }
        if (cn2 == null) {
            return ay;
        }
        if (n3 == 5) {
            n2 = cn2.b(5);
            n n4 = cn2.b(6);
            if (n2 != null && n4 != null) {
                return n4;
            }
            return ay;
        }
        n2 = cn2.b(n3);
        if (n2 == null) {
            return ay;
        }
        return n2;
    }

    public final void m() {
        for (int i2 = 0; i2 <= this.k; ++i2) {
            n n2 = this.b(i2);
            if (n2 == null || n2.C <= 1 || n2.D * 100 / n2.C > 10) continue;
            bm.p(1);
            if (bm.cb.U < 30) {
                k.a(85, 61, 180, 1, 5000, bm.B(375));
            }
            return;
        }
        bm.q(1);
    }

    public final void n() {
        boolean bl2 = false;
        this.j = new Hashtable();
        int n2 = 0;
        if (W == null) {
            return;
        }
        for (int i2 = 0; i2 < this.k; ++i2) {
            n n3 = this.b(i2);
            if (n3 == null) continue;
            for (int i3 = 0; i3 < W.length && !(bl2 = n3.d(W[i3])); ++i3) {
            }
            if (!bl2) continue;
            this.j.put(new Integer(n2++), new Integer(i2));
        }
    }

    public final void a(int[][] nArray) {
        boolean bl2 = false;
        this.j = new Hashtable();
        int n2 = 0;
        for (int i2 = 0; i2 < this.k; ++i2) {
            n n3 = this.b(i2);
            if (n3 == null) continue;
            for (int i3 = 0; i3 < nArray.length; ++i3) {
                bl2 = n3.c(nArray[i3][0]) && n3.d(nArray[i3][1]);
                if (!bl2) continue;
                this.j.put(new Integer(n2++), new Integer(i2));
            }
        }
    }

    public static void a(boolean bl2, n n2, n n3) {
        cn.a(false, false, 10);
    }

    public static void a(boolean bl2, n n2, n n3, boolean bl3) {
        cn.a(bl2, true, 10);
    }

    public static void a(boolean bl2, boolean bl3, int n2) {
        aA = bl2;
        az = 0;
        if (aA) {
            int[] nArray = bf.a(n.O, 295);
            A = nArray[0];
        } else {
            if (w == null) {
                return;
            }
            A = w.j();
        }
        int n3 = B = A > 10 ? 10 : A;
        if (bl3) {
            bm.aT = 2;
        }
        bm.X = 3;
    }

    public static void a(Graphics graphics, int n2, int n3, int n4, boolean bl2) {
        int n5;
        boolean bl3 = true;
        n4 = -1;
        Graphics graphics2 = graphics;
        int n6 = graphics2.getClipX();
        int n7 = graphics2.getClipY();
        int n8 = graphics2.getClipWidth();
        int n9 = graphics2.getClipHeight();
        int n10 = 21 * (B + 1) + 57;
        if (n2 == 0) {
            n2 = 167;
        }
        if (n4 == -1) {
            n4 = 360 - n10 >> 1;
        }
        if (aA) {
            ay.a(graphics2, n2, n4, 305, n10, true, bm.A(1137), true);
        } else {
            aq = cn.w.d;
            ay.a(graphics2, n2, n4, 305, n10, true, "", true, null, (byte)1);
            w.a(graphics2, n2 + 152, n4 + 19 - 10, 1);
        }
        int n11 = n2 + 5;
        int n12 = n4 + 32 + 9;
        int n13 = 0;
        if (w != null && w.d(8192) && !aA && (n5 = cn.w.H) > 0) {
            for (n13 = 0; n13 < n5; ++n13) {
                if (cn.w.I[n13] > 0) {
                    if (w.d(131072) && cn.w.s.length >= 5) {
                        n.b(graphics2, n11 + n13 * 29, n12, cn.w.J[n13], cn.w.K[n13], 1, false, true, cn.w.s[4]);
                    } else {
                        n.b(graphics2, n11 + n13 * 29, n12, cn.w.J[n13], cn.w.K[n13], 1, false, true, 0);
                    }
                    if (cn.w.L[n13] >= n.P.length) continue;
                    graphics2.setColor(n.P[cn.w.L[n13]]);
                    graphics2.drawRect(n11 + n13 * 29 - 1, n12 - 1, 27, 27);
                    continue;
                }
                ay.b(graphics2, n11 + n13 * 29 - 2, n12 - 2, 29);
            }
            n13 = 38;
        }
        bz.a(graphics2, 16179625, 0, -1);
        graphics2.setClip(n2, n12 += n13, 305, n10 - (n13 + 9 + 32));
        int[] nArray = bf.a(n.O, 290);
        bf.a(graphics2, n.O, nArray, n11, n12 + (0 - az) * 19, 0, 100, 4, false);
        graphics2.setClip(0, 0, 640, 360);
        if (B < A) {
            if (bl2) {
                bm.G.c();
                if (az != 0) {
                    bm.G.a(graphics2, n3 + 320, n4 + 32, 2);
                }
                if (az != A - B) {
                    bm.G.a(graphics2, n3 + 320, n4 + n10, 0);
                }
            } else {
                n13 = bm.D.f(33);
                n2 = bm.D.g(33);
                if (az != 0) {
                    bm.E.c();
                    bm.E.a(graphics2, 320 + (n2 >> 1) + n3, n4 - n13, 4);
                }
                if (az != A - B) {
                    bm.H.c();
                    bm.H.a(graphics2, 320 + (n2 >> 1) + n3, n4 + n10, 4);
                }
            }
        }
        graphics2.setClip(n6, n7, n8, n9);
    }

    public static void o() {
        if (bm.v(196608)) {
            bm.aT = 0;
            aq = 0;
        } else if (bm.v(262144) || bm.ak()) {
            bm.aT = 0;
            aq = 0;
            bm.de = false;
        }
        cn.b(false);
    }

    public static void b(boolean bl2) {
        int n2;
        int n3;
        int n4;
        if (bm.x() == 51) {
            n4 = 430;
            n3 = 70;
            n2 = 260;
        } else {
            n4 = 304;
            n3 = 65;
            n2 = 235;
        }
        int n5 = bl2 ? (bm.v(2048) || bm.e(n4, n3, 60, 60) ? 1 : 0) : (n3 = bm.v(16388) || bm.e(300, 70, 60, 60) ? 1 : 0);
        boolean bl3 = bl2 ? bm.v(1024) || bm.e(n4, n2, 60, 60) : (bl2 = bm.v(33024) || bm.e(300, 260, 60, 60));
        if (n3 != 0) {
            if (az > 0 && --az < 0) {
                az = 0;
                return;
            }
        } else if (bl2 && B < A && ++az + B > A) {
            az = A - B;
        }
    }

    public static void a(cr cr2) {
        int n2;
        String string;
        String string2;
        int n3;
        int n4;
        x = cr2;
        aD = 0;
        if (cr2.B) {
            aB = new int[36];
            n4 = cr.aI;
            n3 = cr.aJ;
            string2 = "" + cr.aK;
            string = "" + cr.aL;
        } else {
            aB = new int[30];
            n4 = D;
            n3 = C;
            string2 = "???";
            string = "???";
        }
        n4 = n4 > cr2.r[4] ? cr2.r[4] : n4;
        n3 = n3 > cr2.r[5] ? cr2.r[5] : n3;
        for (n2 = 0; n2 < aB.length; ++n2) {
            cn.aB[n2] = n2 + 87;
        }
        aC = new String[aB.length];
        n2 = 0;
        if (cr2 != null) {
            ++n2;
            cn.aC[0] = "";
            ++n2;
            cn.aC[1] = n4 + "/" + cr2.r[4];
            ++n2;
            cn.aC[2] = "";
            ++n2;
            cn.aC[3] = n3 + "/" + cr2.r[5];
            ++n2;
            cn.aC[4] = "";
            ++n2;
            cn.aC[5] = cr2.V + "";
            ++n2;
            cn.aC[6] = "";
            ++n2;
            cn.aC[7] = "" + string2 + "/" + string;
            ++n2;
            cn.aC[8] = cr2.U + "";
            switch (cr2.u) {
                case 0: {
                    cn.aC[9] = bm.d(0, 140);
                    break;
                }
                case 1: {
                    ++n2;
                    cn.aC[9] = bm.d(0, 141);
                    break;
                }
                case 2: {
                    ++n2;
                    cn.aC[9] = bm.d(0, 142);
                }
            }
            int n5 = ++n2;
            ++n2;
            cn.aC[n5] = bn.a[cr2.v];
            if (cr2.B) {
                cn.aC[n2++] = cr.aw + "";
                cn.aC[n2++] = cr2.ad + "";
                cn.aC[n2++] = cr2.Z + "";
                cn.aC[n2++] = cr.av + "";
                cn.aC[n2++] = cr.ax + "";
            } else {
                cn.aC[n2++] = "???";
                cn.aC[n2++] = "???";
                cn.aC[n2++] = "???";
                cn.aC[n2++] = "???";
                cn.aC[n2++] = "???";
            }
            cn.aC[n2++] = cr2.r[0] + "";
            cn.aC[n2++] = cr2.r[2] + "";
            cn.aC[n2++] = cr2.r[1] + "";
            cn.aC[n2++] = cr2.r[3] + "";
            cn.aC[n2++] = cr2.r[7] / 100 + "." + cr2.r[7] % 100 + "%";
            cn.aC[n2++] = cr2.r[9] / 100 + "." + cr2.r[9] % 100 + "%";
            cn.aC[n2++] = cr2.r[10] / 100 + "." + cr2.r[10] % 100 + "%";
            cn.aC[n2++] = cr2.r[11] / 100 + "." + cr2.r[11] % 100 + "%";
            cn.aC[n2++] = cr2.r[13] / 100 + "." + cr2.r[13] % 100 + "%";
            cn.aC[n2++] = cr2.r[14] / 100 + "." + cr2.r[14] % 100 + "%";
            cn.aC[n2++] = "";
            cn.aC[n2++] = "" + "\u3010" + cr2.W + "\u3011" + "^x" + cr2.X + "^t";
            if (cr2.B) {
                cn.aC[n2++] = "";
                cn.aC[n2++] = "" + cr.aM + "/" + cr.aN;
                cn.aC[n2++] = "";
                cn.aC[n2++] = "" + cr.aO;
                cn.aC[n2++] = "";
                cn.aC[n2++] = "" + cr.aP;
                cn.aC[n2++] = "";
                cn.aC[n2] = "" + cr.aQ + "/" + (cr.aR + cr.aS);
            }
        }
    }

    public static void c(Graphics graphics) {
        int n2;
        aE = new int[4];
        bm.D.a(66, 0, aE, 0);
        bm.D.a(graphics, 66, 65, 0, 0);
        aF = aE[0] + 65;
        aG = aE[1];
        aH = (aE[2] >> 1) - 2;
        aI = aE[3];
        int[] nArray = new int[]{1, 1};
        ay.a(graphics, aF, aG, aE[2], aI, 4, nArray);
        int n3 = 2 * aD;
        for (n2 = 0; n2 < 4; ++n2) {
            if (n3 >= aC.length) continue;
            if (aB[n3] == 113) {
                n3 += 2;
            }
            String string = bm.d(1, aB[n3]);
            if (aB[n3] == 101) {
                string = ay.d;
            }
            bz.a(graphics, 16179625, -13870883, -1);
            bz.a(graphics, string, aF + 2, aG + n2 * 33 + 4, 20);
            string = aC[n3];
            if (aC[n3] == "") {
                int n4 = n3 + 1;
                if (n4 < aC.length) {
                    string = aC[n4];
                }
                n3 += 2;
            } else {
                ++n3;
            }
            bz.a(graphics, string, aF + (aH << 1) - 1 + 3, aG + n2 * 33 + 4, 24);
        }
        n2 = (aC.length + 2) / 2 - 4 + 1 + aC.length % 2;
        if (bm.aR == null) {
            ay.a(graphics, aF + aE[2] + 4, aG, 11, aI, aD, 4, n2);
        } else {
            ay.a(graphics, aF + aE[2] + 4, aG, 11, aI, aD, 4, n2, true);
        }
        if (aD > 0) {
            bm.G.a(graphics, aF + aE[2] / 2 - 4, aG, 2);
        }
        if (aD != n2 - 1) {
            bm.G.a(graphics, aF + aE[2] / 2 - 4, aG + aI + 5, 0);
        }
    }

    public static boolean p() {
        bm.g(20, 180);
        int n2 = (aC.length + 2) / 2 - 4 + 1 + aC.length % 2;
        boolean bl2 = bm.v(16388) || bm.w(16388) || bm.f(457, 95, 28, 28);
        boolean bl3 = bm.v(33024) || bm.w(33024) || bm.f(457, 245, 28, 28);
        bm.a(ci.a(5), ci.b(5), ci.c(5), ci.d(5), 10, false);
        if (bl2) {
            if (--aD < 0) {
                aD = 0;
            }
        } else if (bl3 && ++aD > n2 - 1) {
            aD = n2 - 1;
        }
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static boolean q() {
        bm.g(20, 180);
        bm.a(580, 115, 25, 170, 20, true);
        if (bm.v(16388) || bm.e(422, 96, 28, 28)) {
            if (cv.u) return false;
            if (cv.r <= 0) return true;
            cv.r -= 2;
            return false;
        } else {
            if (!bm.v(33024) && !bm.e(422, 274, 28, 28) || cv.u || cv.r >= 14) return false;
            cv.r += 2;
        }
        return false;
    }

    public static void d(Graphics graphics) {
        ay.b(graphics, bm.aS, 235, 69, 0xF0FF70, 0);
        ay.b(graphics, bm.A(1176), 235, 191, 0xF0FF70, 0);
        ay.a(graphics, I, 2, 155, 140, 170, true, false);
        ay.a(graphics, H, 2, 155, 266, 170, true, J);
        bm.aN.a(graphics);
        bm.aO.a(graphics);
        aq.e(Integer.MIN_VALUE);
        if (G == 0 || G == 1) {
            aq.b(graphics, 154, 93, 202, 87);
        }
        bz.a(graphics, -597591, -13032703, 4);
        if (bm.aT == 0) {
            bm.D.a(graphics, 90, 0, 0, 0);
            bz.a(graphics, F[G], 320, 360 - bm.D.g(90), 1);
            return;
        }
        bm.D.j(1);
        bm.D.a(graphics, 94, 0, 0, 0);
        bm.D.j(0);
    }

    public static void r() {
        if (bm.aQ != null && bm.aQ.A()) {
            bm.aR = bm.aQ;
            J = false;
        }
        if (bm.aN != null && !bm.dB && bm.aN.A()) {
            bm.aR = bm.aN;
            J = false;
        }
        if (!bm.dB && bm.e(173, 266, 180, 32)) {
            if (J && G == 0) {
                bc.a((byte)22);
            } else {
                J = true;
                bm.aR = null;
                bm.c(-100, -100);
            }
        }
        if (G != 0 && G != 1 && bm.aO != null && !bm.dB && bm.aO.A()) {
            bm.aR = bm.aO;
            J = false;
        }
        if (bm.v(262144) || bm.an()) {
            int n2 = 10;
            String string = bm.A(1177);
            cn.a(string, n2, -1);
            return;
        }
        if (bm.aR != null) {
            int n3 = bm.aR.a(true);
            if (n3 > 0) {
                if (bm.aR == bm.aN) {
                    if (n3 == 2) {
                        J = true;
                        bm.aR = null;
                        bm.c(-100, -100);
                    } else if ((n3 == 3 || n3 == 1) && G == 2) {
                        bm.aR = bm.aO;
                    }
                } else if (bm.aR == bm.aO) {
                    if (n3 == 2) {
                        bm.aR = bm.aA;
                    } else if (n3 == 4) {
                        bm.aR = bm.aN;
                    }
                } else if (bm.aR == bm.aA && n3 == 1) {
                    J = true;
                    bm.aR = null;
                    bm.c(-100, -100);
                }
                if (bm.aR != null) {
                    bm.aR.h();
                }
            }
        } else if ((bm.v(196608) || bm.am()) && G == 0) {
            bc.a((byte)22);
        } else if (bm.v(16388)) {
            bm.aR = bm.aN;
            bm.aR.h();
            J = false;
        } else if (bm.v(33024)) {
            bm.aR = bm.aA;
            bm.aR.h();
            J = false;
        }
        if (bm.v(1)) {
            if (G == 0) {
                G = 1;
                bm.bn.a(H, bm.aN);
                return;
            }
            if (G != 1 && G == 2) {
                int n4 = 9;
                String string = bm.A(1179);
                cn.a(string, n4, -1);
            }
        }
    }

    public static void c(boolean n2) {
        if (bm.aN == null || bm.aO == null) {
            return;
        }
        if (n2 == 0) {
            for (n2 = 0; n2 < bm.aN.k; ++n2) {
                bm.aN.b(n2);
            }
            bm.aN.e();
            bm.aO.e();
            H = 0;
            I = 0;
            return;
        }
        bm.aN.e();
        bm.aO.e();
        H = 0;
        I = 0;
    }

    public static void s() {
        if (bm.n(47) && bm.aJ == 5) {
            bm.bn.z(bm.cb.C);
            bm.H();
        }
    }

    public static void e(Graphics graphics) {
        ay.a(graphics, K, 2, 260, 125, 200, true, J);
    }

    public static void t() {
        if (bm.aQ != null && bm.aQ.A()) {
            bm.aR = bm.aQ;
            J = false;
        } else if (bm.aP != null && !bm.dB && bm.aP.A()) {
            bm.aR = bm.aP;
            J = false;
        } else if (!bm.dB && bm.e(260, 125, 200, 26)) {
            if (J) {
                an = 0;
                am = aj[14];
                bm.aT = 1;
            } else {
                J = true;
                bm.aR = null;
                bm.c(-100, -100);
            }
        }
        if (bm.v(262144)) {
            bm.B();
            return;
        }
        if (bm.aR != null) {
            int n2 = bm.aR.a(true);
            if (n2 > 0) {
                if (bm.aR == bm.aE) {
                    if (n2 == 2) {
                        J = true;
                        bm.aR = null;
                        bm.c(-100, -100);
                    }
                } else if (bm.aR == bm.aA && n2 == 1) {
                    J = true;
                    bm.aR = null;
                    bm.c(-100, -100);
                }
                if (bm.aR != null) {
                    bm.aR.h();
                    return;
                }
            }
        } else {
            if (bm.v(196608)) {
                an = 0;
                am = aj[14];
                bm.aT = 1;
                return;
            }
            if (bm.v(16388)) {
                bm.aR = bm.aE;
                bm.aR.h();
                J = false;
                return;
            }
            if (bm.v(33024)) {
                bm.aR = bm.aA;
                bm.aR.h();
                J = false;
            }
        }
    }

    public static n a(cn cn2, int n2) {
        switch (n2) {
            case 0: {
                return cn2.b(0);
            }
            case 1: {
                return cn2.b(1);
            }
            case 2: {
                return cn2.b(2);
            }
            case 3: {
                return cn2.b(3);
            }
            case 4: {
                return cn2.b(4);
            }
            case 5: {
                if (cn2.b(5) != null && cn2.b(6) == null) {
                    return null;
                }
                return cn2.b(5);
            }
            case 6: {
                return cn2.b(7);
            }
            case 7: {
                return cn2.b(8);
            }
            case 8: {
                return cn2.b(9);
            }
        }
        return null;
    }

    public static void f(Graphics graphics) {
        bm.D.a(graphics, 53, 65, 55, 0);
        bz.a(graphics, -9080, -13032703, 4);
        bz.a(graphics, L[N], 385, 60, 1);
        bz.a(bm.W, 6902561, -13032703, -1);
        int n2 = N - 1;
        if (n2 < 0) {
            n2 = O - 1;
        }
        bz.a(graphics, L[n2], 285, 60, 1);
        n2 = N + 1;
        if (n2 >= O) {
            n2 = 0;
        }
        bz.a(graphics, L[n2], 485, 60, 1);
    }

    public static void u() {
        if (bm.v(2048) || bm.v(4096) && bm.aR == null || bm.e(205, 45, 130, 40)) {
            if (--N < 0) {
                N = O - 1;
            }
            cn.F();
            bm.X = 3;
        }
        if (bm.v(1024) || bm.v(8192) && bm.aR == null || bm.e(425, 45, 130, 40)) {
            if (++N >= O) {
                N = 0;
            }
            cn.F();
            bm.X = 3;
        }
    }

    private static void F() {
        if (bm.aJ == 2) {
            bm.bn.a(f, M[N], false, b == 9);
        }
    }

    private static int k(int n2) {
        boolean bl2 = n2 * Q % 100 > 0;
        n2 = n2 * Q / 100;
        if (bl2) {
            ++n2;
        }
        if (n2 > 0) {
            return n2;
        }
        return 1;
    }

    private static void a(String string, int n2, int n3, int n4, boolean bl2, boolean bl3) {
        aM = false;
        aL = string;
        T = n3;
        aK = n4;
        aP = bl3;
        an = 0;
        aJ = n2;
        bm.aT = 3;
    }

    public static void g(Graphics graphics) {
        int n2;
        if (aM) {
            int n3 = 0;
            boolean bl2 = false;
            int n4 = 0;
            bl2 = false;
            ay.a(graphics, 220, 80, 200, 200, true, aL, true);
            n4 = 640 - aO * 12 >> 1;
            for (n3 = 0; n3 < aO; ++n3) {
                bz.a(graphics, 16179625, 0, -1);
                bz.a(graphics, "" + aN[n3], n4 + n3 * 12, 180);
            }
            bm.G.c();
            bm.G.a(graphics, n4 + an * 12, 194, 0);
            bm.G.a(graphics, n4 + an * 12, 174, 2);
            return;
        }
        String string = "";
        if (w != null) {
            if (aP) {
                n2 = cn.w.m;
                String string2 = bm.A(1164) + n2 * T;
                if (cn.l() && (n2 = cn.k(n2)) != cn.w.m) {
                    string2 = string2 + "(" + P + n2 * T + ")";
                }
                string = string2;
            } else if (cn.w.n != 0) {
                string = bm.A(1164) + cn.w.n * T;
            }
        }
        n2 = bz.a(string) + 25 + 19;
        int n5 = Math.max(n2, 300);
        n2 = 320 - (n5 >> 1) + (n5 >> 3);
        ay.b(graphics, "", bm.av, an, n5, true, aL, true);
        String string3 = cn.w.e;
        bz.a(graphics, 16179625, -16777216, -1);
        bz.a(graphics, string3, n2 + 29, 130);
        graphics.setColor(14595437);
        graphics.fillRect(335, 178, 30, 20);
        graphics.setColor(0);
        graphics.drawRect(335, 178, 30, 20);
        bz.a(graphics, T + "", 340, 180);
        bz.a(graphics, 16179625, -16777216, -1);
        bz.a(graphics, bm.d(0, 63), 252, 180);
        graphics.setColor(16179625);
        graphics.drawRect(n2, 138, 27, 27);
        bz.a(graphics, 16179625, -16777216, -1);
        if (w != null) {
            w.a(graphics, n2, 141, false, false);
            bz.a(graphics, string, n2 + 29, 150);
        }
        bm.F.c();
        bm.F.a(graphics, 310, 180, 0);
        bm.F.a(graphics, 390, 180, 1);
    }

    public static void v() {
        if (bm.v(262144)) {
            bm.aT = 0;
            return;
        }
        if (aM) {
            if (bm.v(16388)) {
                int n2 = an;
                aN[n2] = (byte)(aN[n2] + 1);
                if (aN[an] > 9) {
                    cn.aN[cn.an] = 0;
                    return;
                }
            } else if (bm.v(33024)) {
                int n3 = an;
                aN[n3] = (byte)(aN[n3] - 1);
                if (aN[an] < 0) {
                    cn.aN[cn.an] = 9;
                    return;
                }
            } else if (bm.w(8256)) {
                if (++an >= aO) {
                    an = aO - 1;
                    return;
                }
            } else if (bm.w(4112)) {
                if (--an < 0) {
                    an = 0;
                    return;
                }
            } else if (bm.v(196608)) {
                Object object = aN;
                int n4 = 0;
                for (int i2 = 0; i2 < ((byte[])object).length; ++i2) {
                    int n5 = 1;
                    for (int i3 = 0; i3 < ((byte[])object).length - i2 - 1; ++i3) {
                        n5 *= 10;
                    }
                    n4 += n5 * object[i2];
                }
                T = n4;
                if (T > aK) {
                    String string = bm.A(1165);
                    n4 = 0;
                    object = string;
                    cn.a(string, n4, -1);
                    return;
                }
                cn.l(aJ);
                return;
            }
        } else {
            int n6 = bm.av.length;
            cn.c(n6, 0);
            cn.h(aK);
            cn.g(n6);
            if (bm.w(4112)) {
                if (--T <= 0) {
                    T = aK;
                    return;
                }
            } else if (bm.w(8256)) {
                if (++T > aK) {
                    if (w.i()) {
                        T = 1;
                        return;
                    }
                    T = aK;
                    return;
                }
            } else if (bm.v(196608)) {
                if (an == 1) {
                    bm.aT = 0;
                    return;
                }
                cn.l(aJ);
            }
        }
    }

    public static void a(String string, int n2, int n3) {
        aQ = n3;
        aJ = n2;
        T = 1;
        U = string;
        bm.aT = 4;
        bm.X = 3;
        an = 0;
    }

    public static void a(String string, int n2) {
        cn.a(string, n2, -1);
    }

    public static void h(Graphics graphics) {
        if (U == null) {
            return;
        }
        if (aJ == 0) {
            bm.a(graphics, bm.B(162), U);
            return;
        }
        ay.a(graphics, U, bm.av, an, 300, true, bm.B(162), true);
    }

    public static void w() {
        if (U == null) {
            return;
        }
        if (aJ != 0) {
            int n2 = bm.av.length;
            cn.c(n2, 0);
            cn.g(n2);
        }
        if (bm.v(196608)) {
            if (aJ == 0) {
                bm.aT = 0;
                bm.X = 3;
                cn.l(aJ);
                U = null;
                return;
            }
            if (an == 0) {
                bm.aT = 0;
                cn.l(aJ);
                U = null;
                return;
            }
            U = null;
            bm.aT = 0;
            bm.X = 3;
            return;
        }
        if (bm.v(262144) || bm.ak()) {
            U = null;
            bm.aT = 0;
            bm.X = 3;
        }
    }

    private static void l(int n2) {
        if (n2 == 0) {
            return;
        }
        switch (n2) {
            case 1: {
                if (b == 0 && cr.av < cn.w.m) {
                    bm.d(V);
                    return;
                }
                if (b == 5 && d < cn.w.m) {
                    bm.d(bm.A(1168));
                    return;
                }
                if (b == 6 && d < cn.w.m) {
                    bm.d(bm.A(1169));
                    return;
                }
                bm.bn.a(cn.w.d, (byte)T, (byte)N);
                break;
            }
            case 2: {
                bm.bn.b(cn.w.d, (byte)T, cn.w.c);
                break;
            }
            case 23: {
                bm.bn.r();
                break;
            }
            case 24: {
                bm.bn.s();
                break;
            }
            case 3: {
                bm.bn.c(cn.w.d, (byte)T, cn.w.c);
                break;
            }
            case 4: {
                H = T;
                bm.aT = 0;
                return;
            }
            case 5: {
                if (bm.aR == bm.aB) {
                    bm.bn.b(w, false, (byte)1);
                    break;
                }
                bm.bn.b(w, false, (byte)0);
                break;
            }
            case 6: {
                bm.bn.b(null, true, (byte)0);
                break;
            }
            case 7: {
                cn.a(true, bm.aN, w, T);
                return;
            }
            case 8: {
                cn.a(false, bm.aN, w, T);
                return;
            }
            case 9: {
                bm.bn.y(bm.cb.C);
                G = 3;
                break;
            }
            case 10: {
                bm.bn.z(bm.cb.C);
                bm.E(4);
                return;
            }
            case 13: {
                bm.bn.a((byte)1, T);
                break;
            }
            case 14: {
                bm.bn.a((byte)0, T);
                break;
            }
            case 11: {
                bm.bn.a((byte)1, cn.w.d, cn.w.c, (int)((byte)T));
                break;
            }
            case 12: {
                bm.bn.a((byte)0, cn.w.d, cn.w.c, (int)((byte)T));
                break;
            }
            case 15: {
                cn.a(true, bm.aG, w, T);
                return;
            }
            case 16: {
                cn.a(false, bm.aG, w, T);
                return;
            }
            case 18: {
                if (b == 0 && cr.av < cn.w.m) {
                    bm.d(V);
                    return;
                }
                l.a(2);
                bm.ax();
                bm.bn.a((byte)2, 0, l.g[1]);
                bm.ad();
                return;
            }
            case 19: {
                l.a(1);
                bm.ax();
                bm.bn.a((byte)2, 0, l.g[1]);
                bm.ad();
                return;
            }
            case 20: {
                bm.bn.a(ag, false, (byte)1);
                return;
            }
            case 21: {
                i i2 = i.a(aQ);
                if (i2 != null) {
                    if (!i2.j()) {
                        bm.cb.a(i2);
                    } else {
                        bm.c(bm.A(212));
                    }
                    bm.H();
                }
                return;
            }
            case 22: {
                bm.bn.a(cn.w.d, cn.w.c, R);
                return;
            }
            case 27: {
                bm.bn.b(cn.w.c);
                bm.aT = 0;
                return;
            }
        }
        bm.E(3);
        bm.aT = 0;
        bm.X = 3;
    }

    public static boolean a(boolean bl2, cn cn2, n n2, int n3) {
        boolean bl3 = bl2;
        bl2 = false;
        bl2 = bl3;
        return cn.a(bl3, cn2, n2, n3, false, -1);
    }

    public static boolean a(boolean n2, cn object, n n3, int n4, boolean n5, int n6) {
        int n7 = 0;
        if (n2) {
            int n8;
            if (n5 != 0) {
                n3.c = (byte)-1;
            }
            n3.b = (byte)(n3.b + n4);
            cn cn2 = object;
            n5 = (byte)n4;
            object = n3;
            cn cn3 = cn2;
            if (bm.n(47)) {
                bm.X = 3;
            }
            while (true) {
                if ((n7 = cn3.a((n)object, n5, n6)) == -1) {
                    n8 = n5;
                    break;
                }
                if (n7 == 0) {
                    n8 = 0;
                    break;
                }
                n5 = n7;
            }
            int n9 = n8;
            if (n8 == n4) {
                n4 = 0;
                object = bm.A(1162);
                cn.a((String)object, n4, -1);
            }
            if (n9 > 0) {
                n3.b = (byte)(n3.b - n9);
            }
            n7 = 1;
        } else {
            n2 = n3.a(bm.aA, n4);
            if (n2 >= 0) {
                ((cn)object).a(n2);
                n7 = 1;
            }
        }
        bm.aT = 0;
        if (bm.aR != null) {
            bm.aR.h();
        }
        return n7 != 0;
    }

    public static void a(int n2, int n3, int n4) {
        n n5 = bm.aA.b(n2);
        bm.aG.h.clear();
        bm.aA.c();
        if (bm.n(47) && 3 == bm.aJ) {
            bm.aA.t = true;
            if (n5 != null) {
                cn.b(n5, n3, n4);
            }
        }
    }

    public static void x() {
        if (bm.n(47) && 3 == bm.aJ) {
            bm.aG.h.clear();
            bm.aA.c();
            bm.B();
        }
    }

    public static void i(Graphics graphics) {
        if (ap) {
            ay.a(graphics, ao, 180, an, true, true);
            return;
        }
        if (am != null) {
            int n2 = am.length;
            String[] stringArray = new String[n2];
            for (int i2 = 0; i2 < n2; ++i2) {
                stringArray[i2] = "" + (i2 + 1) + "." + bm.d(0, am[i2]);
            }
            ay.a(graphics, stringArray, 180, an, true, true);
        }
    }

    public static void y() {
        if (am == null) {
            return;
        }
        int n2 = am.length;
        int n3 = bm.as() - 1;
        if (n3 >= 0 && n3 < n2) {
            an = n3;
            cn.G();
            return;
        }
        cn.c(n2, 0);
        cn.g(n2);
        if (bm.v(196608)) {
            cn.G();
            bm.dB = false;
            if (bm.aJ == 6 && bm.aR == null) {
                bm.aT = 0;
            }
            bm.X = 3;
            return;
        }
        if (bm.v(262144)) {
            bm.aT = am == aj[18] ? 5 : 0;
            bm.X = 3;
        }
    }

    public static void g(int n2) {
        --n2;
        if (bm.v(16384)) {
            an = an - 1 < 0 ? n2 : an - 1;
            return;
        }
        if (bm.v(32768)) {
            an = an + 1 > n2 ? 0 : an + 1;
        }
    }

    private static void G() {
        block80: {
            if (an > am.length - 1) {
                return;
            }
            bm.X = 3;
            if (bm.aR != null) {
                bm.aR.h();
            }
            int n2 = am[an];
            switch (n2) {
                case 39: {
                    if (am == aj[18]) {
                        bm.aT = 5;
                        return;
                    }
                    bm.aT = 0;
                    return;
                }
                case 210: {
                    n2 = 1;
                    Object var0_1 = null;
                    Object var1_13 = null;
                    boolean bl2 = false;
                    cn.a(false, true, 10);
                    return;
                }
                case 212: {
                    bm.bn.z();
                    break;
                }
                case 213: {
                    bm.bn.A();
                    break;
                }
                case 185: {
                    if (cn.w.o <= 1) {
                        int n3 = 1;
                        String string = bg.b(bm.A(1141), cn.w.m, cn.w.e);
                        cn.a(string, n3, -1);
                        return;
                    }
                    n2 = w.i() ? cn.w.p : (short)1;
                    cn.a(bm.A(1143) + cn.w.m, 1, n2, (int)cn.w.p, false, true);
                    return;
                }
                case 246: {
                    if (cn.w.p > 1) {
                        cn.a(bm.A(1144) + cn.w.n, 2, 1, (int)cn.w.p, false, false);
                        return;
                    }
                    int n4 = 2;
                    String string = bg.a(bm.A(1145), cn.w.n, n.y[cn.w.u], cn.w.e);
                    cn.a(string, n4, -1);
                    return;
                }
                case 187: {
                    bm.bn.r();
                    bm.ad();
                    break;
                }
                case 186: {
                    bm.bn.s();
                    bm.ad();
                    break;
                }
                case 60: {
                    if (ax != null && w != null) {
                        bm.bn.a(ax, (int)cn.w.c);
                        if (bm.aQ == bm.aD) {
                            bm.aR = bm.aQ = bm.aA;
                            bm.aT = 0;
                        }
                        bm.X = 3;
                        return;
                    }
                    break block80;
                }
                case 188: {
                    if (cn.w.g == 1) {
                        int n5 = 18;
                        String string = bg.b(bm.A(1146), cn.w.m, cn.w.e);
                        cn.a(string, n5, -1);
                        return;
                    }
                    cn.a(bm.A(1143) + cn.w.m, 18, 1, (int)cn.w.p, false, true);
                    return;
                }
                case 189: {
                    if (cn.w.g == 1) {
                        int n6 = 19;
                        String string = bg.b(bm.A(1147), cn.w.m, cn.w.e);
                        cn.a(string, n6, -1);
                        return;
                    }
                    cn.a(bm.A(1143) + cn.w.m, 19, 1, (int)cn.w.p, false, true);
                    return;
                }
                case 59: {
                    if (bm.aJ != 8) {
                        bm.bn.c(cn.w.d, cn.w.c);
                        bm.E(3);
                        return;
                    }
                    bm.bn.b(cv.s, cn.w.d, cn.w.c);
                    return;
                }
                case 61: {
                    if (w == null) {
                        return;
                    }
                    if (w.g()) {
                        if (bm.aJ == 8) {
                            bm.bn.b(w, (int)cv.s);
                            bm.aT = 0;
                            return;
                        }
                        bm.bn.a((byte)1, cn.w.d, cn.w.c);
                        return;
                    }
                    if (cn.w.d == 30108) {
                        bm.aT = 5;
                        bm.aQ = bm.aD;
                        am = aj[18];
                        ax = w;
                        if (bm.aQ == bm.aD) {
                            n2 = bm.aA.k;
                            if (bm.aD == null) {
                                bm.aD = new cn(0, n2, 2);
                                bm.aD.a(5, 8, 294, 127);
                            } else {
                                bm.aD.e();
                                bm.aD.h.clear();
                            }
                            int n7 = 0;
                            for (int i2 = 0; i2 < n2; ++i2) {
                                n n8 = bm.aA.b(i2);
                                if (n8 == null || !n8.h() || n8.i != 1 || n8.p <= 0) continue;
                                bm.aD.a(n8, n7);
                                ++n7;
                            }
                            bm.aR = bm.aQ = bm.aD;
                            bm.aQ.h();
                        }
                        return;
                    }
                    bm.bn.a(w, bm.cf);
                    bm.aT = 0;
                    return;
                }
                case 267: {
                    bm.bn.F(2);
                    ar = w;
                    return;
                }
                case 268: {
                    bm.bn.F(1);
                    ar = w;
                    return;
                }
                case 269: {
                    if (w != null) {
                        bm.bn.e(cn.w.d, cn.w.c, 0);
                    }
                    bm.aT = 0;
                    bm.X = 3;
                    return;
                }
                case 270: {
                    if (w != null) {
                        bm.bn.e(cn.w.d, cn.w.c, 1);
                    }
                    bm.aT = 0;
                    bm.X = 3;
                    return;
                }
                case 271: {
                    l.j = bm.cb;
                    if (bm.aR != null && bm.aR.a == 0) {
                        bm.bn.b(cn.w.c, cn.w.d);
                    }
                    bm.bn.f(271);
                    bm.aT = 0;
                    return;
                }
                case 272: {
                    if (w != null) {
                        int n9 = 27;
                        String string = bm.A(1310);
                        cn.a(string, n9, -1);
                        return;
                    }
                    break block80;
                }
                case 273: {
                    if (w != null) {
                        bm.bn.F(4);
                        ar = w;
                        return;
                    }
                    break block80;
                }
                case 274: {
                    if (w != null) {
                        bm.bn.F(3);
                        ar = w;
                        return;
                    }
                    break block80;
                }
                case 56: {
                    if (bm.aJ != 8) {
                        if (!w.f()) {
                            boolean bl3;
                            if (!w.e()) {
                                int n10 = 0;
                                String string = bm.A(1084);
                                cn.a(string, n10, -1);
                                bl3 = false;
                            } else if (cn.w.k > bm.cb.U) {
                                int n11 = 0;
                                String string = bm.A(1085);
                                cn.a(string, n11, -1);
                                bl3 = false;
                            } else {
                                bl3 = true;
                            }
                            if (bl3) {
                                if (bm.aq == 4 && !bm.di) {
                                    bm.bn.n(4);
                                }
                                bm.bn.b(cn.w.d, cn.w.c);
                                bm.E(3);
                            }
                        } else {
                            bm.bn.a((byte)1, cn.w.d, cn.w.c);
                        }
                        bm.aT = 0;
                        bm.X = 3;
                        return;
                    }
                    if (w.f()) {
                        bm.bn.a(cv.s, cn.w.d, cn.w.c);
                    }
                    bm.aT = 0;
                    bm.X = 3;
                    return;
                }
                case 181: {
                    cn.b(w, -1, -1);
                    return;
                }
                case 182: {
                    cn.b(w);
                    return;
                }
                case 57: {
                    if (cn.w.p > 1) {
                        cn.a(bm.A(1148), 3, 1, (int)cn.w.p, false, false);
                        return;
                    }
                    int n12 = 3;
                    String string = bm.A(1149);
                    cn.a(string, n12, -1);
                    return;
                }
                case 219: {
                    if (bm.aR == bm.aB) {
                        bm.bn.a(w, false, (byte)1);
                        return;
                    }
                    bm.bn.a(w, false, (byte)0);
                    return;
                }
                case 220: {
                    bm.bn.a(null, true, (byte)0);
                    return;
                }
                case 294: {
                    if (cn.w.i == 1) {
                        int n13 = 0;
                        String string = bm.A(1151);
                        cn.a(string, n13, -1);
                        return;
                    }
                    n2 = cn.w.p - cn.w.b;
                    if (n2 > 1) {
                        cn.a(bm.A(1152), 7, 1, n2, false, false);
                        return;
                    }
                    if (bm.aN.c(w) == -1) {
                        int n14 = 0;
                        String string = bm.A(1153);
                        cn.a(string, n14, -1);
                        return;
                    }
                    cn.a(true, bm.aN, w, 1);
                    return;
                }
                case 178: {
                    if (cn.w.p > 1 && cn.w.i == 0) {
                        cn.a(bm.A(1154), 8, 1, (int)cn.w.p, false, false);
                        return;
                    }
                    cn.a(false, bm.aN, w, 1);
                    return;
                }
                case 179: {
                    if (cn.w.p > 1) {
                        cn.a(bm.A(1155), 11, 1, (int)cn.w.p, false, false);
                        return;
                    }
                    bm.bn.a((byte)1, cn.w.d, cn.w.c, 1);
                    bm.aT = 0;
                    bm.E(3);
                    return;
                }
                case 180: {
                    if (cn.w.p > 1) {
                        cn.a(bm.A(1156), 12, 1, (int)cn.w.p, false, false);
                        return;
                    }
                    bm.bn.a((byte)0, cn.w.d, cn.w.c, 1);
                    bm.aT = 0;
                    bm.E(3);
                    return;
                }
                case 183: {
                    bc.a((byte)11);
                    return;
                }
                case 184: {
                    bc.a((byte)12);
                    return;
                }
                case 2: {
                    bm.a(9, true);
                    bc.a((byte)53);
                    return;
                }
                case 211: {
                    ac = ac == bm.aG.q ? -1 : bm.aG.q;
                    bm.aT = 0;
                    System.out.println("s_ItemBeChoose = " + ac);
                    return;
                }
                case 416: {
                    bm.bn.w();
                    return;
                }
                default: {
                    if (bm.aR == null || w == null) break;
                    bm.bn.a(n2, ag, bm.aR.a, bm.aJ, cn.w.c, cn.w.d);
                }
            }
            bm.aT = 0;
            bm.X = 3;
        }
    }

    public static void b(n object, int n2, int n3) {
        int n4;
        int n5;
        block15: {
            n n6;
            int n7;
            Object object2;
            Object object3;
            if (ag == 1 && ((n)object).d(8192)) {
                Object object4;
                block14: {
                    object3 = bm.aG;
                    for (int i2 = 0; i2 < ((cn)object3).k; ++i2) {
                        n n8 = ((cn)object3).b(i2);
                        object2 = n8;
                        if (n8 == null || !((n)object2).d(8192)) continue;
                        object4 = object2;
                        break block14;
                    }
                    object4 = object3 = null;
                }
                if (object4 != null) {
                    cn.b((n)object3);
                }
            }
            n n9 = object;
            object3 = bm.aG;
            int[] nArray = new int[2];
            object2 = nArray;
            nArray[0] = 0;
            object2[1] = 0;
            if (n9 != null) {
                for (n7 = 0; n7 < ((cn)object3).k; ++n7) {
                    if (X[n7] < 0 || !n9.d(X[n7]) || Y[n7] != 0 && n9.d(Y[n7])) continue;
                    n6 = ((cn)object3).b(n7);
                    if (n6 != null) {
                        Object object5 = object2;
                        object5[0] = object5[0] + n6.p;
                    }
                    Object object6 = object2;
                    object6[1] = object6[1] + Z[n7];
                }
            }
            Object object7 = object2;
            object3 = object7;
            if (object7[1] == false) {
                n2 = 0;
                object = bm.A(1159);
                cn.a((String)object, n2, -1);
                return;
            }
            if (object3[0] >= object3[1]) {
                n2 = 0;
                object = bm.A(1160);
                cn.a((String)object, n2, -1);
                return;
            }
            n5 = ((n)object).h;
            object3 = bm.aG;
            for (int i3 = 0; i3 < ((cn)object3).k; ++i3) {
                if (X[i3] < 0 || (X[i3] & n5) != n5) continue;
                n7 = 0;
                n6 = ((cn)object3).b(i3);
                if (n6 != null) {
                    n7 = n6.p;
                }
                n4 = Z[i3] - n7;
                break block15;
            }
            n4 = 0;
        }
        int n10 = n4;
        n5 = ((n)object).p - ((n)object).b;
        if (n10 > n5) {
            n10 = n5;
        }
        if (n10 > 1 && n3 == -1) {
            cn.a(bm.A(1161), 15, 1, n10, false, false);
            return;
        }
        int n11 = n3 = n3 == -1 ? 1 : n3;
        if (bm.aG.c((n)object) == -1) {
            n2 = 0;
            object = bm.A(1162);
            cn.a((String)object, n2, -1);
            return;
        }
        boolean bl2 = cn.a(true, bm.aG, (n)object, n3, false, n2);
        if (bl2 && aa == 1) {
            bm.ad();
            bm.bn.a(ag, false, (byte)0);
        }
    }

    public static void b(n n2) {
        if (n2.p > 1) {
            cn.a(bm.A(1163), 16, 1, (int)n2.p, false, false);
            return;
        }
        boolean bl2 = cn.a(false, bm.aG, n2, 1);
        if (bl2) {
            if ((ag == 7 || ag == 8 || ag == 9) && n2.H > 0) {
                for (int i2 = 0; i2 < bm.aG.k; ++i2) {
                    n n3 = bm.aG.b(i2);
                    if (n3 == null) continue;
                    cn.a(false, bm.aG, n3, n3.p);
                }
            }
            if (aa == 1) {
                bm.ad();
                bm.bn.a(ag, false, (byte)0);
            }
        }
    }

    public final void z() {
        if (this.a == 1) {
            if (this.aU == null) {
                this.aU = new int[31][4];
                for (int i2 = 0; i2 < 31; ++i2) {
                    this.aU[i2][0] = aR[i2][0] + 65 - 2;
                    this.aU[i2][1] = aR[i2][1] - 2;
                    this.aU[i2][2] = 27;
                    this.aU[i2][3] = 27;
                }
            }
            return;
        }
        if (this.a == 3) {
            if (this.aV == null) {
                this.aV = new int[3][4];
                for (int i3 = 0; i3 < 3; ++i3) {
                    this.aV[i3][0] = o.a_[i3][0] + 65;
                    this.aV[i3][1] = o.a_[i3][1];
                    this.aV[i3][2] = 27;
                    this.aV[i3][3] = 27;
                }
            }
            return;
        }
        if (this.a == 2 && this.aW == null) {
            this.aW = new int[9][4];
            for (int i4 = 0; i4 < 9; ++i4) {
                bm.D.a(89, i4, this.aW[i4], 0);
            }
        }
    }

    public final boolean A() {
        int n2;
        int n3;
        if (bm.ab()) {
            return false;
        }
        if (this.a == 1) {
            if (this.aU == null) {
                return false;
            }
            for (n3 = 0; n3 < 12; ++n3) {
                n2 = n3;
                if (u == 1 || n3 >= 10) {
                    n2 += 10;
                }
                if (u == 2) {
                    n2 += 22;
                }
                if (n2 >= this.aU.length || !bm.a(this.aU[n2])) continue;
                if (this.q == n2 && bm.aR == this) {
                    bm.dA = true;
                } else {
                    this.q = n2;
                }
                bm.X = 3;
                this.h();
                return true;
            }
        }
        if (this.a == 3) {
            if (this.aV == null) {
                return false;
            }
            for (n3 = 0; n3 < 3; ++n3) {
                if (!bm.a(this.aV[n3])) continue;
                if (this.q == n3 && bm.aR == this) {
                    bm.dB = true;
                } else {
                    this.q = n3;
                }
                bm.X = 3;
                this.h();
                return true;
            }
        } else {
            if (this.a == 2) {
                for (n3 = 0; n3 < 9; ++n3) {
                    if (!bm.a(this.aW[n3])) continue;
                    if (this.q == n3 && bm.aR == this) {
                        bm.dB = true;
                    } else if (this.i(n3)) {
                        this.q = n3;
                    } else {
                        return false;
                    }
                    this.h();
                    bm.X = 3;
                    return true;
                }
                return false;
            }
            n3 = 0;
            if (bm.x() != 51) {
                n3 = 65;
            }
            if (bm.e(this.l + n3, this.m, this.n * 29, this.o * 29)) {
                n3 = (bm.ds - n3 - this.l) / 29;
                n2 = (bm.dt - this.m) / 29;
                n3 = this.p * this.n + n2 * this.n + n3;
                if (n2 == this.o - 1) {
                    n2 = this.k / this.n + (this.k % this.n == 0 ? 0 : 1);
                    if (this.p + this.o < n2) {
                        ++this.p;
                    }
                } else if (n2 == 0 && this.p > 0) {
                    --this.p;
                }
                if (n3 > this.k - 1) {
                    return true;
                }
                if (this.q == n3 && bm.aR == this) {
                    bm.dA = true;
                } else {
                    this.q = n3;
                }
                bm.X = 3;
                this.h();
                return true;
            }
        }
        return false;
    }

    public static void h(int n2) {
        int[][] nArray = null;
        nArray = bm.c(2, 270, 150, 60, 60, 40);
        for (int i2 = 0; i2 < 2; ++i2) {
            if (!bm.a(bm.du, bm.dv, nArray[i2])) continue;
            if (0 == i2) {
                if (--T <= 0) {
                    T = n2;
                    return;
                }
            } else if (++T > n2) {
                T = 1;
            }
            return;
        }
    }

    public static void c(int n2, int n3) {
        bm.dB = false;
        if ((n2 = bm.f(n2, 0)) != -1) {
            if (an == n2) {
                bm.dB = true;
                return;
            }
            an = n2;
            bm.X = 3;
        }
    }

    static {
        au = new int[][]{{180, 130, 31, 31}, {225, 130, 31, 31}, {270, 130, 31, 31}, {180, 175, 31, 31}, {225, 175, 31, 31}, {270, 175, 31, 31}, {180, 220, 31, 31}, {225, 220, 31, 31}, {270, 220, 31, 31}};
        av = new int[][]{{0, 1, 0, 3}, {0, 2, 1, 8}, {1, 5, 2, 7}, {3, 8, 0, -1}, {2, 9, 5, 6}, {2, 20, 5, 4}, {7, 21, 4, -1}, {8, 6, 2, -1}, {3, 7, 1, -1}, {4, 9, 20, 21}, {0, 1, 0, 3}, {0, 2, 1, 8}, {1, 5, 2, 7}, {3, 8, 0, -1}, {2, 9, 5, 6}, {2, 20, 5, 4}, {7, 21, 4, -1}, {8, 6, 2, -1}, {3, 7, 1, -1}, {4, 9, 20, 21}, {5, 20, 20, 9}, {6, 21, 9, -1}, {22, 25, 22, 23}, {23, 26, 22, 24}, {24, 27, 23, 25}, {22, 28, 25, 26}, {23, 29, 25, 27}, {24, 30, 26, 28}, {25, 28, 28, 29}, {26, 29, 28, 30}, {27, 30, 29, -1}};
        aw = new int[][]{{-1, 1, 1, 2}, {0, -1, 2, 2}, {0, -1, 1, 1}};
        ay = new n();
        aD = 0;
        aF = 0;
        aG = 0;
        aH = 0;
        aI = 0;
        S = false;
        aP = false;
        aR = new int[31][2];
        X = new int[7];
        Y = new int[7];
        Z = new short[7];
        aS = new int[28];
        ab = false;
        ac = -1;
        aT = new int[][][]{new int[][]{{1, 2, 4}, {6, -1}, {3, 2}, {5, 4}}, new int[][]{{1}, {0, 6, 2, 4, 3, 5, -1}, {2, 3}, {4, 5}}, new int[][]{{1}, {3, 0, 5, 6, -1}, {3}, {4, 1, 0, 5, 6}}, new int[][]{{2, 1, 4}, {6, -1}, {3}, {0, 5, 2, 6, 1, 4}}, new int[][]{{1}, {5, 0, 3, 6, -1}, {2, 1, 0, 3, 6}, {5}}, new int[][]{{4, 1, 2}, {6, -1}, {0, 3, 1, 6, 2}, {5}}, new int[][]{{0, 1, 3, 5, 2, 4}, {-1}, {3, 2}, {5, 4}}};
        aj = new int[][]{{210, 39}, {246, 187, 186, 210, 39}, {185, 210, 39}, {59, 210, 39}, {61, 210, 39}, {61, 210, 57, 39}, {56, 210, 57, 212, 269, 270, 271, 272, 39}, {61, 210, 57, 212, 39}, {210, 57, 212, 39}, {219, 220, 210, 39}, {294, 210, 39}, {178, 210, 39}, {179, 210, 39}, {180, 213, 210, 39}, {183, 184, 39}, {181, 210, 271, 39}, {182, 210, 271, 39}, {185, 188, 189, 210, 39}, {60, 39}, {2, 210, 39}, {185, 210, 39}, {274, 273, 39}, {211, 210, 39}, {210, 39}, {210, 416, 57, 212, 39}, {181, 416, 210, 271, 39}, {182, 416, 210, 271, 39}, new int[0], new int[0], {185, 39}, {56, 210, 57, 39}, {181, 210, 39}};
        ap = false;
    }
}

