/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class n {
    public int[][] a = null;
    public byte b;
    public byte c;
    public int d;
    public String e;
    public String f;
    private short Q;
    public byte g = 0;
    public int h = 0;
    public byte i = 0;
    public short j = 0;
    public byte k = 0;
    public byte l = 0;
    private byte R;
    public int m = 0;
    public int n = 0;
    public short o;
    public short p;
    private int S;
    public long q;
    public int r;
    public int[] s;
    public String t;
    public int u;
    private int T;
    private int U;
    private int V;
    private int W;
    public byte v = 0;
    public long w;
    public int[] x;
    public static final String[] y = new String[]{ay.d, bm.A(543), bm.A(544), bm.A(545), bm.A(546), bm.A(547), bm.A(548), bm.A(549), bm.A(550), bm.A(551)};
    public byte z;
    public byte A;
    private byte X;
    public byte B;
    private int Y;
    public int C;
    public int D;
    public byte E;
    private byte Z;
    public int F;
    private byte aa;
    public static String[] G;
    public byte H;
    public int[] I;
    private String[] ab;
    private int[][] ac;
    public int[] J;
    public int[] K;
    public int[] L;
    public int[] M;
    public static String[] N;
    public static String O;
    public static int[] P;

    public final void a(n n2) {
        this.d = n2.d;
        this.g = n2.g;
        this.h = n2.h;
        this.Q = n2.Q;
        this.i = n2.i;
        this.e = n2.e;
        this.f = n2.f;
        this.j = n2.j;
        this.k = n2.k;
        this.l = n2.l;
        this.R = n2.R;
        this.m = n2.m;
        this.n = n2.n;
        this.r = n2.r;
        this.S = n2.S;
        this.q = n2.q;
        this.o = n2.o;
        this.p = n2.p;
        this.z = n2.z;
        this.A = n2.A;
        this.X = n2.X;
        this.B = n2.B;
        this.Z = n2.Z;
        this.Y = n2.Y;
        this.C = n2.C;
        this.D = n2.D;
        this.a = n2.a;
        this.E = n2.E;
        this.s = n2.s;
        this.t = n2.t;
        this.u = n2.u;
        this.T = n2.T;
        this.U = n2.U;
        this.V = n2.V;
        this.H = n2.H;
        this.I = n2.I;
        this.ab = n2.ab;
        this.ac = n2.ac;
        this.J = n2.J;
        this.K = n2.K;
        this.L = n2.L;
        this.M = n2.M;
        this.W = n2.W;
    }

    public final void a() {
        this.x = new int[this.o];
    }

    public final int b() {
        int n2 = 0;
        if (this.x != null) {
            for (int i2 = 0; i2 < this.x.length; ++i2) {
                if (this.x[i2] == 0) continue;
                ++n2;
            }
        }
        return n2;
    }

    public final void a(int n2, int n3) {
        int n4;
        if (this.x == null) {
            return;
        }
        for (n4 = 0; n4 < this.x.length; ++n4) {
            if (this.x[n4] == 0 || n2 != this.x[n4] >> 8) continue;
            int n5 = n4;
            this.x[n5] = this.x[n5] + n3;
            return;
        }
        for (n4 = 0; n4 < this.x.length; ++n4) {
            if (this.x[n4] != 0) continue;
            this.x[n4] = (short)(n2 << 8);
            int n6 = n4;
            this.x[n6] = this.x[n6] + n3;
            return;
        }
    }

    public final int a(cn cn2, int n2) {
        int n3 = n2;
        this.p = (short)(this.p - n2);
        for (n2 = 0; n2 < this.x.length; ++n2) {
            if (this.x[n2] == 0) continue;
            int n4 = this.x[n2] >> 8;
            n n5 = cn2.b(n4);
            int n6 = this.x[n2] & 0xFF;
            if (n5 == null && n4 == -1) {
                return this.c;
            }
            if (n3 <= 0) break;
            if (n6 <= n3) {
                this.x[n2] = 0;
                n5.b = (byte)(n5.b - n6);
                n3 -= n6;
                continue;
            }
            int n7 = n2;
            this.x[n7] = this.x[n7] - n3;
            n5.b = (byte)(n5.b - n3);
            break;
        }
        if (this.p <= 0) {
            return this.c;
        }
        return -1;
    }

    public final boolean c() {
        boolean bl2 = false;
        if (this.x != null) {
            for (int i2 = 0; i2 < this.x.length; ++i2) {
                int n2 = this.x[i2] >> 8;
                if (n2 != -1) continue;
                bl2 = true;
                break;
            }
        }
        return bl2;
    }

    final boolean d() {
        long l2 = bm.m() - this.w;
        return l2 > 0L && l2 < (long)this.r;
    }

    final void a(int n2) {
        this.w = bm.m() - (long)(this.r - n2);
    }

    final boolean e() {
        return this.z >= 3 || this.z == bm.cb.u;
    }

    public final void a(Graphics graphics, int n2, int n3, boolean bl2, boolean bl3) {
        this.a(graphics, n2, n3, this.p, false, bl3);
    }

    public final void a(Graphics graphics, int n2, int n3, int n4, boolean bl2, boolean bl3) {
        if (this.d(131072) && this.s.length >= 4) {
            n.b(graphics, n2, n3, this.j, this.l, n4, bl2, bl3, this.s[2]);
            return;
        }
        if (this.d(0x10000000) && this.s.length == 3) {
            n.b(graphics, n2, n3, this.j, this.l, n4, bl2, bl3, this.s[0]);
            return;
        }
        if (this.d(32768) && this.s.length > 0) {
            n.b(graphics, n2, n3, this.j, this.l, n4, bl2, bl3, this.s[0]);
            return;
        }
        if (this.g == 3) {
            n.b(graphics, n2, n3, this.j, this.l, n4, bl2, bl3, this.A);
            return;
        }
        n.b(graphics, n2, n3, this.j, this.l, n4, bl2, bl3, 0);
    }

    public static void a(Graphics graphics, int n2, int n3, int n4, int n5, int n6, boolean bl2, boolean bl3, int n7) {
        n.b(graphics, n2, n3, n4, n5, n6, bl2, bl3, n7);
    }

    public static void b(Graphics graphics, int n2, int n3, int n4, int n5, int n6, boolean n7, boolean bl2, int n8) {
        if (n6 <= 0) {
            return;
        }
        if (n7 != 0) {
            ay.b(graphics, n2 - 2, n3 - 2);
        }
        try {
            n7 = (0xFF80 & n4) >> 7;
            n4 = 0x7F & n4;
            if (n7 == 1) {
                ck.b(26).j(n5);
                ck.b(26).a(graphics, n4, n2, n3, 0);
            } else if (n7 == 2) {
                ck.b(25).j(n5);
                ck.b(25).a(graphics, n4, n2, n3, 0);
            } else if (n7 == 3) {
                ck.b(307).j(n5);
                ck.b(307).a(graphics, n4, n2, n3, 0);
            } else {
                ck.b(23).j(n5);
                ck.b(23).a(graphics, n4, n2, n3, 0);
            }
            bz.b(2);
            if (bl2 && n6 > 1) {
                bz.a(graphics, n6 + "", n2 + 29 + -4, n3 + 29 + -5, 40);
            }
            if (n8 > 0) {
                bz.a(graphics, n8 + "", n2 + 29 + -4, n3, 24);
            }
            bz.b((byte)1);
            return;
        }
        catch (Exception exception) {
            bm.b(graphics, n2, n3);
            return;
        }
    }

    private String n() {
        if (this.i == 1) {
            return bm.A(562);
        }
        if (this.R == 1) {
            return bm.A(563);
        }
        if (this.R == 2) {
            return bm.A(564);
        }
        if (this.R == 3) {
            return bm.A(565);
        }
        return "";
    }

    public final int b(int n2) {
        int n3 = 0;
        for (int i2 = 0; i2 < 4; ++i2) {
            int n4;
            int n5 = n2;
            int n6 = i2 + 0;
            n n7 = this;
            if (n5 >= 15) {
                n4 = 0;
            } else if (n7.a == null) {
                n4 = 0;
            } else {
                int n8 = 0;
                if (n7.a[n6] != null) {
                    n8 = n7.a[n6][n5];
                }
                n4 = n8;
            }
            n3 += n4;
        }
        return n3;
    }

    public final boolean f() {
        return (this.Q & 0x20) == 32;
    }

    public final boolean g() {
        return (this.h & 0x4000) == 16384;
    }

    public final boolean h() {
        return (this.h & 0x2000) == 8192 || (this.h & 0x1000) == 4096;
    }

    public final boolean i() {
        return (this.h & 0x200) == 512 || (this.h & 0x400) == 1024;
    }

    public static void a(String string) {
        if (string != null) {
            O = O + string + '\n';
        }
    }

    /*
     * Unable to fully structure code
     */
    public final int j() {
        block46: {
            n.O = "";
            if (this.g != 1 || this.A <= 0) break block46;
            var1_1 = this;
            var2_5 = "";
            if (var1_1.g != 1) ** GOTO lbl-1000
            for (var3_6 = var1_1.A; var3_6 > 0; --var3_6) {
                var2_5 = (String)var2_5 + "\u2605";
            }
            if (var1_1.A >= 3 && var1_1.A <= 5) {
                v0 = "^F" + (String)var2_5 + "^F";
            } else if (var1_1.A >= 6 && var1_1.A <= 8) {
                v0 = "^G" + (String)var2_5 + "^G";
            } else if (var1_1.A >= 9 && var1_1.A <= 10) {
                v0 = "^H" + (String)var2_5 + "^H";
            } else if (var1_1.A > 10) {
                v0 = "^I" + (String)var2_5 + "^I";
            } else lbl-1000:
            // 2 sources

            {
                v0 = var2_5;
            }
            n.a(v0);
        }
        if (!this.f()) {
            n.a(bm.d(0, 190) + bn.j[this.g]);
        } else {
            n.a(bm.d(0, 190) + bm.A(131));
        }
        if (this.g != 3) {
            var1_1 = bm.d(0, 191) + this.k;
            if (this.k > bm.cb.U) {
                var1_1 = "^r" + (String)var1_1 + "^r";
            }
            n.a((String)var1_1);
        }
        switch (this.g) {
            case 3: {
                n.a(n.N[0]);
                for (var1_2 = 0; var1_2 < 15; ++var1_2) {
                    var2_5 = "";
                    var3_6 = 0;
                    if (this.a[0] != null && this.a[0][var1_2] > 0) {
                        var2_5 = (String)var2_5 + this.a[0][var1_2];
                        var3_6 = 1;
                    }
                    if (var3_6 == 0) continue;
                    n.a(bn.i[var1_2] + ":" + (String)var2_5);
                }
                v1 = bf.a(n.O, 293);
                this = v1;
                return v1[0];
            }
            case 0: {
                if ((this.d(262144) || this.d(524288)) && this.s != null && this.s.length >= 1) {
                    n.a(bm.A(567) + this.s[0]);
                }
                n.a(bm.d(0, 193) + this.r / 1000 + bm.A(568));
                ** GOTO lbl113
            }
            case 1: {
                var1_1 = bm.d(0, 192) + bm.d(0, bj.Q[this.z]);
                if (this.z != bm.cb.u && this.z != 3) {
                    var1_1 = "^r" + (String)var1_1 + "^r";
                }
                if (!this.f()) {
                    n.a((String)var1_1);
                }
                n.a(super.n());
                if (this.C <= 1) {
                    n.a(bm.A(569));
                } else {
                    n.a(bm.d(0, 195) + this.D / 10 + "/" + this.C / 10);
                }
                if (this.d(131072) && this.s.length >= 4) {
                    n.a(bm.d(0, 194) + this.s[0] + "/" + this.s[1]);
                }
                if (this.a == null) break;
                var2_5 = cn.a((n)this);
                n.a(n.N[0]);
                for (var3_6 = 0; var3_6 < 15; ++var3_6) {
                    var1_1 = "";
                    var4_7 = 0;
                    if (this.a[0] != null && this.a[0][var3_6] > 0) {
                        var1_1 = (String)var1_1 + this.a[0][var3_6];
                        var4_7 = 1;
                    }
                    if (this.a[2] != null && this.a[2][var3_6] > 0) {
                        var1_1 = (String)var1_1 + " + ^b" + this.a[2][var3_6] + "^b";
                        var4_7 = 1;
                    }
                    if (var4_7 == 0) continue;
                    var5_8 = "";
                    if (var2_5 != this && var2_5 != null) {
                        var5_8 = cn.a((n)var2_5, (n)this, var3_6, false);
                    }
                    n.a(bn.i[var3_6] + ":" + (String)var1_1 + var5_8);
                }
                if (this.a[1] != null) {
                    n.a(n.N[1]);
                    for (var3_6 = 0; var3_6 < 15; ++var3_6) {
                        if (this.a[1][var3_6] <= 0) continue;
                        n.a("^" + this.E + bn.i[var3_6] + ":" + this.a[1][var3_6] + "^" + this.E);
                    }
                }
                if (this.H > 0) {
                    for (var3_6 = 0; var3_6 < this.H; ++var3_6) {
                        if (this.I[var3_6] <= 0) continue;
                        var1_1 = "";
                        if (this.L[var3_6] < n.P.length && this.L[var3_6] >= 0) {
                            var1_1 = "^" + Integer.toString(this.L[var3_6]);
                        }
                        n.a((String)var1_1 + this.ab[var3_6] + ":" + (String)var1_1);
                        for (var4_7 = 0; var4_7 < 15; ++var4_7) {
                            var5_10 = this.ac[var3_6][var4_7];
                            if (var5_10 <= 0) continue;
                            n.a("  " + bn.i[var4_7] + ":" + var5_10);
                        }
                    }
                }
                if (this.aa >= 0) {
                    n.a(bm.A(570) + this.a(false));
                }
                if (this.a[3] != null) {
                    for (var1_3 = 0; var1_3 < 15; ++var1_3) {
                        if (this.a[3][var1_3] <= 0) continue;
                        n.a(bn.i[var1_3] + ":" + this.a[3][var1_3]);
                    }
                }
                if (this.W <= 0) break;
                n.a(bm.A(1309) + ": " + this.W);
                break;
            }
lbl113:
            // 2 sources

            case 2: {
                n.a(this.n());
            }
        }
        if (cn.l()) {
            n.a(bm.A(571) + this.m);
        } else {
            n.a(bm.d(0, 196) + this.n + n.y[this.u]);
        }
        if (this.V == 1 || this.V == 3 || this.V == 5) {
            if (this.U > 0) {
                n.a(bm.A(572) + this.U + "%");
            } else {
                n.a(bm.A(572) + "??");
            }
        }
        var1_4 = bm.A(573) + this.f + this.t;
        n.a(var1_4);
        if (this.a((short)2) && bm.aR == bm.aA) {
            n.a(bm.A(574));
            n.a(bg.a(this.q + (long)this.S * 60L * 1000L));
            n.a(bm.A(575));
        }
        v2 = bf.a(n.O, 293);
        this = v2;
        return v2[0];
    }

    public final String a(boolean bl2) {
        if (this.aa >= 0 && this.aa < G.length && (!bl2 || bl2 && this.aa > 0)) {
            char c2 = (char)(65 + (this.aa - 1));
            return "^" + c2 + "\u3010" + G[this.aa] + "\u3011" + "^" + c2;
        }
        return "";
    }

    public final int k() {
        return P[this.E];
    }

    public final void a(Graphics graphics, int n2, int n3, int n4) {
        if (this.p - this.b <= 0) {
            return;
        }
        int n5 = this.k();
        bz.a(graphics, n5, -13032703, 4);
        String string = this.a(true) + this.e;
        if (this.A > 0 && this.g != 3) {
            string = string + "+" + this.A;
        }
        if (this.g == 1 && this.k > 0) {
            string = string + " " + this.k + bm.A(370);
        }
        bz.a(graphics, string, n2, n3, n4);
    }

    public final boolean a(short s2) {
        return (this.Q & s2) != 0;
    }

    public final boolean c(int n2) {
        if (n2 < 0) {
            return false;
        }
        return this.g == n2;
    }

    public final boolean d(int n2) {
        if (n2 < 0) {
            return false;
        }
        return (this.h & n2) != 0;
    }

    public static n a(q q2) {
        return n.a(q2, null);
    }

    public static n a(q q2, n n2) {
        if (n2 == null) {
            n2 = new n();
        }
        n2.c = q2.a();
        n2.d = q2.d();
        long l2 = q2.f();
        cc.a();
        n2.g = (byte)cc.a(l2, 3);
        n2.i = (byte)cc.a(l2, 2);
        cc.a(l2, 3);
        cc.a(l2, 3);
        cc.a(l2, 7);
        n2.E = (byte)cc.a(l2, 3);
        n2.Q = (short)cc.a(l2, 8);
        n2.h = q2.e();
        int n3 = q2.e();
        n2.e = q2.a(0, n3);
        n2.j = q2.c();
        n2.k = q2.a();
        n2.l = q2.a();
        n2.R = q2.a();
        n2.m = q2.e();
        n2.n = q2.e();
        n2.u = q2.a();
        if (n2.a((short)2)) {
            long l3;
            n2.S = q2.e();
            q q3 = q2;
            long l4 = q3.f();
            long l5 = q3.f();
            n2.q = l3 = l4 & 0xFFFFFFFFL | (l5 & 0xFFFFFFFFL) << 32;
        }
        n2.r = q2.e() * 1000;
        q q4 = q2;
        short s2 = q4.c();
        n2.t = q4.a(0, s2);
        int n4 = q2.b();
        n2.s = new int[n4];
        for (int i2 = 0; i2 < n4; ++i2) {
            n2.s[i2] = q2.e();
        }
        if (n2.g == 0 || n2.g == 2) {
            n2.o = (short)q2.b();
            n2.p = (short)q2.b();
        } else if (n2.g == 1 || n2.g == 4) {
            int n5;
            int n6;
            int n7;
            n2.a = new int[4][];
            n2.o = 1;
            n2.p = 1;
            long l6 = q2.f();
            cc.a();
            n2.z = (byte)cc.a(l6, 2);
            n2.H = (byte)cc.a(l6, 3);
            n2.A = (byte)cc.a(l6, 5);
            n2.X = (byte)cc.a(l6, 4);
            n2.B = (byte)cc.a(l6, 5);
            n2.Y = q2.d();
            n2.C = q2.d();
            n2.D = q2.d();
            if (n2.a((short)64)) {
                n2.Z = q2.a();
                q2.e();
                n2.F = q2.e();
                if (n2.Z == 2 || n2.Z == 3) {
                    q2.g();
                }
            }
            n2.ab = null;
            n2.J = null;
            n2.K = null;
            n2.I = null;
            n2.L = null;
            n2.M = null;
            n2.ac = null;
            if (n2.H > 0) {
                n2.ab = new String[n2.H];
                n2.J = new int[n2.H];
                n2.K = new int[n2.H];
                n2.I = new int[n2.H];
                n2.L = new int[n2.H];
                n2.M = new int[n2.H];
                n2.ac = new int[n2.H][15];
                for (n4 = 0; n4 < n2.H; ++n4) {
                    n2.I[n4] = n7 = q2.d();
                    if (n7 <= 0) continue;
                    n2.M[n4] = q2.b();
                    n2.L[n4] = q2.b();
                    n2.ab[n4] = q2.g();
                    n2.J[n4] = q2.d();
                    n2.K[n4] = q2.b();
                    n6 = q2.b();
                    for (n5 = 0; n5 < n6; ++n5) {
                        int n8 = q2.b();
                        n2.ac[n4][n8] = n7 = q2.d();
                    }
                }
            }
            for (n4 = 0; n4 < 4; ++n4) {
                n7 = q2.a();
                if (n7 > 0) {
                    n2.a[n4] = new int[15];
                }
                for (n6 = 0; n6 < n7; ++n6) {
                    int n9;
                    n5 = q2.a();
                    n2.a[n4][n5] = n9 = q2.d();
                }
            }
            n2.aa = q2.a();
            n2.T = q2.e();
            n2.U = q2.c();
            n2.V = q2.a();
            n2.W = q2.e();
        } else {
            n2.o = 1;
            n2.p = 1;
        }
        String cfr_ignored_0 = bm.A(576) + n2.e;
        return n2;
    }

    public static boolean a(n n2, cn object) {
        if (n2 == null) {
            return false;
        }
        if (!n2.h()) {
            return false;
        }
        if (!n2.e()) {
            return false;
        }
        if ((object = cn.a((cn)object, (int)n2.B)) == null) {
            return true;
        }
        for (int i2 = 0; i2 < 15; ++i2) {
            if (n2.b(i2) >= ((n)object).b(i2)) continue;
            return false;
        }
        return true;
    }

    public final byte l() {
        for (int i2 = 0; i2 < this.x.length; ++i2) {
            if (this.x[i2] == 0) continue;
            return (byte)(this.x[i2] >> 8);
        }
        return -1;
    }

    public final byte m() {
        for (int i2 = 0; i2 < this.x.length; ++i2) {
            if (this.x[i2] == 0) continue;
            return (byte)this.x[i2];
        }
        return -1;
    }

    static {
        P = new int[]{0xFFFFFF, 48401, 0x1F88FF, 0xFF00F6, 16750080, 65535, 65535, 0xFFFFFF, 0xA8A8A8, 0, 16757248, 48401, 26367, 0xFF0000, 0x5B5B5B, 16179625, 1236579, 14549247, 16754688};
    }
}

