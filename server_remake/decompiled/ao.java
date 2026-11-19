/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class ao {
    public static cr a;
    private int e = -1;
    int b;
    private int f = -1;
    private static int g;
    static int[] c;
    private int h;
    private short[] i;
    private short[] j;
    private short[] k;
    private short[] l;
    private short[] m;
    private short[] n;
    private byte[] o;
    private short[] p;
    private byte[] q;
    private short[] r;
    private byte[] s;
    private short[] t;
    private short[] u;
    private byte[] v;
    private short[] w;
    private short[] x;
    private byte[] y;
    private byte[] z;
    private short[] A;
    private byte[] B;
    private byte[] C;
    private short[] D;
    private short[] E;
    private byte[] F;
    short[][] d;
    private int G;
    private byte[] H;
    private int[] I;
    private int J;
    private int[][] K;
    private int L;
    private int M;
    private int N;
    private boolean O;
    private boolean P;
    private short Q;
    private int R;
    private int S;
    private Image[][] T;
    private static int[] U;
    private static int[] V;
    private static int[] W;
    private static int[] X;
    private byte[] Y;
    private int[] Z;
    private static int aa;
    private static int ab;
    private static int ac;
    private static int ad;
    private static int[] ae;
    private static boolean af;

    private int k(int n2) {
        int n3 = n2 & 0xFFFFFF;
        if (n3 == 0xFF00FF) {
            return n2;
        }
        if (n3 == 2493449) {
            return n2;
        }
        int n4 = this.e;
        if (n4 > 0 && n4 < 255) {
            return this.e << 24 | n3;
        }
        return n2;
    }

    final void a(int n2, int n3, int n4, int n5) {
        this.a(aq.a, n2, n3, n4, n5);
    }

    final void b(int n2, int n3, int n4, int n5) {
        this.b(aq.a, 0, n3, n4, n5, 0);
    }

    final void a(byte[] object, int n2, int n3) {
        this.b = n3;
        int n4 = 1;
        n4 = 0xFFFFFF;
        n2 = 0;
        ao ao2 = this;
        if (object == null) {
            return;
        }
        try {
            int n5;
            int n6;
            int n7;
            int n8;
            int n9;
            int n10;
            int n11;
            if (af) {
                System.gc();
            }
            ++n2;
            int n12 = ++n2;
            int n13 = ++n2;
            int n14 = ++n2;
            int n15 = ++n2;
            ao2.J = (object[n12] & 0xFF) + ((object[n13] & 0xFF) << 8) + ((object[n14] & 0xFF) << 16) + ((object[n15] & 0xFF) << 24);
            byte[] byArray = object;
            n3 = ++n2;
            ao ao3 = ao2;
            ao2.h = (short)((byArray[n3++] & 0xFF) + ((byArray[n3++] & 0xFF) << 8));
            if (ao3.h > 0) {
                ao3.i = new short[ao3.h];
                ao3.j = new short[ao3.h];
                ao3.k = new short[ao3.h];
                ao3.l = new short[ao3.h];
                n11 = 0;
                n10 = 0;
                short[][] sArrayArray = null;
                ao3.Y = new byte[ao3.h];
                ao3.Z = new int[ao3.h];
                n9 = 0;
                n8 = 0;
                n7 = 0;
                n6 = 0;
                boolean bl2 = false;
                for (n5 = 0; n5 < ao3.h; ++n5) {
                    short[] sArray;
                    n6 = 0;
                    bl2 = false;
                    if ((byArray[n3] & 0xFF) == 0) {
                        ++n3;
                        ao3.Y[n5] = 0;
                        n9 = 0;
                        n8 = 1;
                        n7 = 1;
                    } else if ((byArray[n3] & 0xFF) == 255) {
                        ++n3;
                        ao3.Y[n5] = 1;
                        n9 = 1;
                        n8 = 0;
                        n7 = 1;
                    } else if ((byArray[n3] & 0xFF) == 254) {
                        ++n3;
                        ao3.Y[n5] = 2;
                        n9 = 1;
                        n8 = 0;
                        n7 = 1;
                    } else if ((byArray[n3] & 0xFF) == 253) {
                        ++n3;
                        ao3.Y[n5] = 5;
                        n9 = 0;
                        n8 = 0;
                        n7 = 1;
                    } else if ((byArray[n3] & 0xFF) == 252) {
                        ++n3;
                        ao3.Y[n5] = 3;
                        n9 = 1;
                        n8 = 0;
                        n7 = 1;
                        n6 = 1;
                    } else if ((byArray[n3] & 0xFF) == 251) {
                        ++n3;
                        ao3.Y[n5] = 4;
                        n9 = 1;
                        n8 = 0;
                        n7 = 1;
                        n6 = 1;
                    } else if ((byArray[n3] & 0xFF) == 250) {
                        ++n3;
                        ao3.Y[n5] = 6;
                        n9 = 1;
                        n8 = 0;
                        n7 = 0;
                        bl2 = true;
                    } else if ((byArray[n3] & 0xFF) == 249) {
                        ++n3;
                        ao3.Y[n5] = 7;
                        n9 = 1;
                        n8 = 0;
                        n7 = 0;
                        bl2 = true;
                    }
                    if (n9 != 0) {
                        ao3.Z[n5] = (byArray[n3++] & 0xFF) + ((byArray[n3++] & 0xFF) << 8) + ((byArray[n3++] & 0xFF) << 16) + ((byArray[n3++] & 0xFF) << 24);
                    }
                    if (n8 != 0) {
                        ao3.i[n5] = (short)((byArray[n3++] & 0xFF) + ((byArray[n3++] & 0xFF) << 8));
                        ao3.j[n5] = (short)((byArray[n3++] & 0xFF) + ((byArray[n3++] & 0xFF) << 8));
                    }
                    if (n7 != 0) {
                        ao3.k[n5] = (short)((byArray[n3++] & 0xFF) + ((byArray[n3++] & 0xFF) << 8));
                        ao3.l[n5] = (short)((byArray[n3++] & 0xFF) + ((byArray[n3++] & 0xFF) << 8));
                    }
                    if (n6 != 0) {
                        if (sArrayArray == null) {
                            sArrayArray = new short[ao3.h][];
                        }
                        short[] sArray2 = new short[2];
                        sArray = sArray2;
                        sArray2[0] = (short)((byArray[n3++] & 0xFF) + ((byArray[n3++] & 0xFF) << 8));
                        sArray[1] = (short)((byArray[n3++] & 0xFF) + ((byArray[n3++] & 0xFF) << 8));
                        sArrayArray[n5] = sArray;
                        ++n11;
                        n10 += 2;
                    }
                    if (!bl2) continue;
                    if (sArrayArray == null) {
                        sArrayArray = new short[ao3.h][];
                    }
                    short[] sArray3 = new short[4];
                    sArray = sArray3;
                    sArray3[0] = (short)((byArray[n3++] & 0xFF) + ((byArray[n3++] & 0xFF) << 8));
                    sArray[1] = (short)((byArray[n3++] & 0xFF) + ((byArray[n3++] & 0xFF) << 8));
                    sArray[2] = (short)((byArray[n3++] & 0xFF) + ((byArray[n3++] & 0xFF) << 8));
                    sArray[3] = (short)((byArray[n3++] & 0xFF) + ((byArray[n3++] & 0xFF) << 8));
                    sArrayArray[n5] = sArray;
                    ++n11;
                    n10 += 4;
                }
                if (n11 > 0) {
                    ao3.m = new short[n10];
                    ao3.n = new short[n11 << 1];
                    n5 = 0;
                    short s2 = 0;
                    for (n10 = 0; n10 < ao3.h; n10 = (int)((short)(n10 + 1))) {
                        n11 = ao3.Y[n10] == 3 || ao3.Y[n10] == 4 ? 2 : (ao3.Y[n10] == 6 || ao3.Y[n10] == 7 ? 4 : -1);
                        if (n11 <= 0) continue;
                        ao3.n[n5 << 1] = n10;
                        ao3.n[(n5 << 1) + 1] = s2;
                        for (int i2 = 0; i2 < n11; ++i2) {
                            ao3.m[s2] = sArrayArray[n10][i2];
                            s2 = (short)(s2 + 1);
                        }
                        sArrayArray[n10] = null;
                        ++n5;
                    }
                }
            }
            int n16 = n3;
            byArray = object;
            n3 = n16;
            ao ao4 = ao2;
            if ((n11 = (int)((byArray[n3++] & 0xFF) + ((byArray[n3++] & 0xFF) << 8))) > 0) {
                ao4.v = new byte[n11];
                ao4.w = new short[n11];
                ao4.x = new short[n11];
                ao4.y = new byte[n11];
                for (n10 = 0; n10 < n11; ++n10) {
                    ao4.v[n10] = byArray[n3++];
                    ao4.w[n10] = (short)((byArray[n3++] & 0xFF) + ((byArray[n3++] & 0xFF) << 8));
                    ao4.x[n10] = (short)((byArray[n3++] & 0xFF) + ((byArray[n3++] & 0xFF) << 8));
                    ao4.y[n10] = byArray[n3++];
                }
            }
            int n17 = n3;
            byArray = object;
            n3 = n17;
            ao ao5 = ao2;
            if ((ao5.J & 0x8000) != 0) {
                n11 = (short)((byArray[n3++] & 0xFF) + ((byArray[n3++] & 0xFF) << 8));
                if ((ao5.J & 0x400) == 0) {
                    ao5.s = new byte[n11 << 2];
                    System.arraycopy(byArray, n3, ao5.s, 0, n11 << 2);
                    n3 += n11 << 2;
                } else {
                    ao5.t = new short[n11 << 2];
                    int n18 = n11 << 2;
                    n11 = 0;
                    int n19 = n18;
                    n5 = 0;
                    short[] sArray = ao5.t;
                    n6 = n3;
                    byte[] byArray2 = byArray;
                    for (n10 = 0; n10 < n19; ++n10) {
                        sArray[n10] = (short)((byArray2[n6++] & 0xFF) + ((byArray2[n6++] & 0xFF) << 8));
                    }
                    n3 = n6;
                }
            }
            if ((n11 = (int)((byArray[n3++] & 0xFF) + ((byArray[n3++] & 0xFF) << 8))) > 0) {
                ao5.o = new byte[n11];
                ao5.p = new short[n11];
                if ((ao5.J & 0x8000) != 0) {
                    ao5.u = new short[n11 + 1];
                }
                short s3 = 0;
                for (n9 = 0; n9 < n11; ++n9) {
                    ao5.o[n9] = byArray[n3++];
                    n8 = 0;
                    int n20 = ++n3;
                    int n21 = ++n3;
                    ++n3;
                    ao5.p[n9] = (short)((byArray[n20] & 0xFF) + ((byArray[n21] & 0xFF) << 8));
                    if ((ao5.J & 0x8000) == 0 || (ao5.J & 0x8000) == 0) continue;
                    ao5.u[n9] = s3;
                    s3 = (short)(s3 + byArray[n3++]);
                }
                if ((ao5.J & 0x8000) != 0) {
                    ao5.u[ao5.u.length - 1] = s3;
                }
                if ((ao5.J & 0x1000) == 0) {
                    n9 = n11 << 2;
                    if ((ao5.J & 0x400) == 0) {
                        ao5.q = new byte[n9];
                        for (n8 = 0; n8 < n9; ++n8) {
                            ao5.q[n8] = byArray[n3++];
                        }
                    } else {
                        ao5.r = new short[n9];
                        for (n8 = 0; n8 < n9; ++n8) {
                            ao5.r[n8] = (short)((byArray[n3++] & 0xFF) + ((byArray[n3++] & 0xFF) << 8));
                        }
                    }
                }
            }
            int n22 = n3;
            byArray = object;
            n3 = n22;
            ao ao6 = ao2;
            short s4 = (short)((byArray[n3++] & 0xFF) + ((byArray[n3++] & 0xFF) << 8));
            n11 = s4;
            if (s4 > 0) {
                ao6.B = new byte[n11];
                ao6.C = new byte[n11];
                ao6.D = new short[n11];
                ao6.E = new short[n11];
                ao6.F = new byte[n11];
                for (n10 = 0; n10 < n11; ++n10) {
                    ao6.B[n10] = byArray[n3++];
                    ao6.C[n10] = byArray[n3++];
                    ao6.D[n10] = (short)((byArray[n3++] & 0xFF) + ((byArray[n3++] & 0xFF) << 8));
                    ao6.E[n10] = (short)((byArray[n3++] & 0xFF) + ((byArray[n3++] & 0xFF) << 8));
                    ao6.F[n10] = byArray[n3++];
                }
            }
            int n23 = n3;
            byArray = object;
            n3 = n23;
            ao ao7 = ao2;
            short s5 = (short)((byArray[n3++] & 0xFF) + ((byArray[n3++] & 0xFF) << 8));
            n11 = s5;
            if (s5 > 0) {
                ao7.z = new byte[n11];
                ao7.A = new short[n11];
                for (int i3 = 0; i3 < n11; ++i3) {
                    ao7.z[i3] = byArray[n3++];
                    int n24 = ++n3;
                    int n25 = ++n3;
                    ++n3;
                    ao7.A[i3] = (short)((byArray[n24] & 0xFF) + ((byArray[n25] & 0xFF) << 8));
                }
            }
            int n26 = n3;
            if (ao2.h <= 0) {
                if (af) {
                    System.gc();
                }
                return;
            }
            byArray = object;
            n3 = n26;
            ao ao8 = ao2;
            if (n3 < byArray.length) {
                n11 = (short)((byArray[n3++] & 0xFF) + ((byArray[n3++] & 0xFF) << 8));
                ao8.L = byArray[n3++] & 0xFF;
                ao8.M = byArray[n3++] & 0xFF;
                if (ao8.M == 0) {
                    ao8.M = 256;
                }
                if (ao8.K == null) {
                    ao8.K = new int[16][];
                }
                for (n10 = 0; n10 < ao8.L; ++n10) {
                    ao8.K[n10] = new int[ao8.M];
                    if (n11 == -30584) {
                        for (int i4 = 0; i4 < ao8.M; ++i4) {
                            if (((n9 = (byArray[n3++] & 0xFF) + ((byArray[n3++] & 0xFF) << 8) + ((byArray[n3++] & 0xFF) << 16) + ((byArray[n3++] & 0xFF) << 24)) & 0xFF000000) != -16777216) {
                                ao8.O = true;
                                if ((n9 & 0xFF000000) != 0) {
                                    ao8.P = true;
                                }
                            }
                            if ((n9 & 0xFFFFFF) == 2493449) {
                                n9 &= 0xFFFFFF;
                                n9 |= 0x64000000;
                            }
                            ao8.K[n10][i4] = n9;
                        }
                        continue;
                    }
                    if (n11 == 17476) {
                        for (int i5 = 0; i5 < ao8.M; ++i5) {
                            short s6 = (short)((byArray[n3++] & 0xFF) + ((byArray[n3++] & 0xFF) << 8));
                            n9 = s6;
                            if ((s6 & 0xF000) != 61440) {
                                ao8.O = true;
                                if ((n9 & 0xF000) != 0) {
                                    ao8.P = true;
                                }
                            }
                            ao8.K[n10][i5] = (n9 & 0xF000) << 16 | (n9 & 0xF000) << 12 | (n9 & 0xF00) << 12 | (n9 & 0xF00) << 8 | (n9 & 0xF0) << 8 | (n9 & 0xF0) << 4 | (n9 & 0xF) << 4 | n9 & 0xF;
                        }
                        continue;
                    }
                    if (n11 == 21781) {
                        for (int i6 = 0; i6 < ao8.M; ++i6) {
                            n9 = (short)((byArray[n3++] & 0xFF) + ((byArray[n3++] & 0xFF) << 8));
                            n8 = -16777216;
                            if ((n9 & 0x8000) != 32768) {
                                n8 = 0;
                                ao8.O = true;
                            }
                            if ((n7 = n8 | (n9 & 0x7C00) << 9 | (n9 & 0x3E0) << 6 | (n9 & 0x1F) << 3) == 0xF800F8) {
                                n7 = 0xFF00FF;
                            }
                            ao8.K[n10][i6] = n7;
                        }
                        continue;
                    }
                    if (n11 != 25861) continue;
                    for (int i7 = 0; i7 < ao8.M; ++i7) {
                        n9 = (short)((byArray[n3++] & 0xFF) + ((byArray[n3++] & 0xFF) << 8));
                        n8 = -16777216;
                        if ((n9 & 0xFFFF) == 63519) {
                            n8 = 0;
                            ao8.O = true;
                        }
                        if ((n7 = n8 | (n9 & 0xF800) << 8 | (n9 & 0x7E0) << 5 | (n9 & 0x1F) << 3) == 0xF800F8) {
                            n7 = 0xFF00FF;
                        }
                        ao8.K[n10][i7] = n7;
                    }
                }
                ao8.Q = (short)((byArray[n3++] & 0xFF) + ((byArray[n3++] & 0xFF) << 8));
                if (ao8.Q == -24281 || ao8.Q == -23978) {
                    ao8.O = true;
                    ao8.P = true;
                }
                if (ao8.Q == 25840 || ao8.Q == -22976) {
                    n10 = ao8.M - 1;
                    ao8.R = 1;
                    ao8.S = 0;
                    while (n10 != 0) {
                        n10 >>= 1;
                        ao8.R <<= 1;
                        ++ao8.S;
                    }
                    --ao8.R;
                }
                if (ao8.h > 0) {
                    ao8.I = new int[ao8.h];
                    n10 = 0;
                    int n27 = n3;
                    for (n9 = 0; n9 < ao8.h; ++n9) {
                        n8 = (ao8.J & 0x80) != 0 ? (byArray[n3++] & 0xFF) + ((byArray[n3++] & 0xFF) << 8) + ((byArray[n3++] & 0xFF) << 16) + ((byArray[n3++] & 0xFF) << 24) : (int)((short)((byArray[n3++] & 0xFF) + ((byArray[n3++] & 0xFF) << 8)));
                        ao8.I[n9] = n10;
                        n10 += n8;
                        n3 += n8;
                    }
                    n3 = n27;
                    ao8.H = new byte[n10];
                    n9 = 0;
                    while (n9 < ao8.h) {
                        n8 = (ao8.J & 0x80) != 0 ? (byArray[n3++] & 0xFF) + ((byArray[n3++] & 0xFF) << 8) + ((byArray[n3++] & 0xFF) << 16) + ((byArray[n3++] & 0xFF) << 24) : (int)((short)((byArray[n3++] & 0xFF) + ((byArray[n3++] & 0xFF) << 8)));
                        ao ao9 = ao8;
                        n11 = n9++;
                        object = ao9;
                        System.arraycopy(byArray, n3, ao8.H, ao9.I[n11], n8);
                        n3 += n8;
                    }
                }
            }
            ao2.d = new short[16][];
            ao2.G = -1;
            if ((ao2.J & 0x1000) != 0 && ao2.o != null) {
                int n28 = 0;
                int n29 = ao2.o.length;
                if ((ao2.J & 0x400) == 0) {
                    ao2.q = new byte[n29 << 2];
                    for (n3 = 0; n3 < n29; ++n3) {
                        ao2.a(ae, n3, 0, 0, 0);
                        ao2.q[n28++] = (byte)ae[0];
                        ao2.q[n28++] = (byte)ae[1];
                        ao2.q[n28++] = (byte)(ae[2] - ae[0]);
                        ao2.q[n28++] = (byte)(ae[3] - ae[1]);
                    }
                } else {
                    ao2.r = new short[n29 << 2];
                    for (n3 = 0; n3 < n29; ++n3) {
                        ao2.a(ae, n3, 0, 0, 0);
                        ao2.r[n28++] = (short)ae[0];
                        ao2.r[n28++] = (short)ae[1];
                        ao2.r[n28++] = (short)(ae[2] - ae[0]);
                        ao2.r[n28++] = (short)(ae[3] - ae[1]);
                    }
                }
            }
            if (af) {
                System.gc();
            }
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public final int a(int n2) {
        int n3 = 0;
        for (int i2 = 0; i2 < this.c(n2); ++i2) {
            n3 = this.g(this.d(n2, i2)) > n3 ? this.g(this.d(n2, i2)) : n3;
        }
        return n3;
    }

    final void a(int n2, byte[] byArray) {
        int n3;
        if (this.d[n2] == null) {
            this.d[n2] = new short[this.h];
            for (n3 = 0; n3 < this.h; ++n3) {
                this.d[n2][n3] = (short)n3;
            }
        }
        if (byArray == null) {
            return;
        }
        n3 = 0;
        while (n3 < byArray.length) {
            int n4 = (byArray[n3++] & 0xFF) + ((byArray[n3++] & 0xFF) << 8);
            int n5 = (byArray[n3++] & 0xFF) + ((byArray[n3++] & 0xFF) << 8);
            this.d[n2][n4] = (short)n5;
        }
    }

    final void b(int n2) {
        this.G = n2;
    }

    final int a(int n2, int n3) {
        return this.C[this.A[n2] + n3] & 0xFF;
    }

    final int c(int n2) {
        return this.z[n2] & 0xFF;
    }

    final int d(int n2) {
        return this.k[n2] & 0xFFFF;
    }

    private int l(int n2) {
        return this.l[n2] & 0xFFFF;
    }

    final int e(int n2) {
        return this.D[n2];
    }

    final int b(int n2, int n3) {
        n2 = this.A[0];
        return this.D[n2];
    }

    final int c(int n2, int n3) {
        n2 = this.A[n2] + n3;
        return this.E[n2];
    }

    final int f(int n2) {
        if ((this.J & 0x400) == 0) {
            return this.q[(n2 << 2) + 2] & 0xFF;
        }
        return this.r[(n2 << 2) + 2] & 0xFFFF;
    }

    final int g(int n2) {
        if ((this.J & 0x400) == 0) {
            return this.q[(n2 << 2) + 3] & 0xFF;
        }
        return this.r[(n2 << 2) + 3] & 0xFFFF;
    }

    final int h(int n2) {
        if ((this.J & 0x400) == 0) {
            return this.q[n2 << 2];
        }
        return this.r[n2 << 2];
    }

    final int i(int n2) {
        if ((this.J & 0x400) == 0) {
            return this.q[1];
        }
        return this.r[1];
    }

    private int d(int n2, int n3) {
        n2 = this.A[n2] + n3;
        n3 = this.B[n2] & 0xFF;
        return n3 |= (this.F[n2] & 0xC0) << 2;
    }

    final void a(int[] nArray, int n2, int n3, int n4, int n5, int n6) {
        aa = Integer.MAX_VALUE;
        ab = Integer.MAX_VALUE;
        ac = Integer.MIN_VALUE;
        ad = Integer.MIN_VALUE;
        g = 1;
        this.b(null, n2, n3, n4, n5, n6);
        g = 0;
        nArray[0] = aa;
        nArray[1] = ab;
        nArray[2] = ac;
        nArray[3] = ad;
    }

    final void a(int[] nArray, int n2, int n3, int n4, int n5) {
        aa = Integer.MAX_VALUE;
        ab = Integer.MAX_VALUE;
        ac = Integer.MIN_VALUE;
        ad = Integer.MIN_VALUE;
        g = 1;
        this.a((Graphics)null, n2, 0, 0, 0);
        g = 0;
        nArray[0] = aa;
        nArray[1] = ab;
        nArray[2] = ac;
        nArray[3] = ad;
    }

    final int a() {
        if (this.z == null) {
            return 0;
        }
        return this.z.length;
    }

    final int b() {
        if (this.o == null) {
            return 0;
        }
        return this.o.length;
    }

    final void a(int n2, int n3, int[] nArray, int n4) {
        if (this.u != null && nArray != null) {
            int n5 = this.u[n2];
            n2 = this.u[n2 + 1];
            if ((n2 -= n5) > 0 && n3 < n2) {
                n5 = n5 + n3 << 2;
                if ((this.J & 0x400) != 0) {
                    if (this.t != null) {
                        nArray[0] = this.t[n5];
                        nArray[1] = this.t[n5 + 1];
                        nArray[2] = this.t[n5 + 2] & 0xFFFF;
                        nArray[3] = this.t[n5 + 3] & 0xFFFF;
                    }
                } else if (this.s != null) {
                    nArray[0] = this.s[n5];
                    nArray[1] = this.s[n5 + 1];
                    nArray[2] = this.s[n5 + 2] & 0xFF;
                    nArray[3] = this.s[n5 + 3] & 0xFF;
                }
                if ((n4 & 1) != 0) {
                    nArray[0] = -nArray[0] - nArray[2];
                }
                if ((n4 & 2) != 0) {
                    nArray[1] = -nArray[1] - nArray[3];
                    return;
                }
            } else {
                nArray[0] = 0;
                nArray[1] = 0;
                nArray[2] = 0;
                nArray[3] = 0;
            }
        }
    }

    public static int[] a(int[] nArray) {
        if (c == null) {
            c = new int[81920];
        }
        return c;
    }

    private void b(Graphics graphics, int n2, int n3, int n4, int n5, int n6) {
        this.a(graphics, n2, n3, n4, n5, n6, 0, 0);
    }

    final void a(Graphics graphics, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
        try {
            n2 = this.A[n2] + n3;
            n3 = this.B[n2] & 0xFF;
            n3 |= (this.F[n2] & 0xC0) << 2;
            int n9 = n2;
            ao ao2 = this;
            n7 = ao2.D[n9];
            n9 = n2;
            ao2 = this;
            int n10 = ao2.E[n9];
            n9 = this.F[n2] & 0xF;
            if ((n6 & 1) != 0) {
                n9 = V[n9 & 7] | n9 & 0xFFFFFFF8;
                n7 = -n7;
            }
            if ((n6 & 2) != 0) {
                n9 = W[n9 & 7] | n9 & 0xFFFFFFF8;
                n10 = -n10;
            }
            if ((n6 & 4) != 0) {
                n9 = X[n9 & 7] | n9 & 0xFFFFFFF8;
                n2 = n7;
                n7 = -n10;
                n10 = n2;
            }
            this.a(graphics, n3, n4 + n7, n5 + n10, n9, 0, 0);
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    final void a(Graphics graphics, int n2, int n3, int n4, int n5) {
        this.a(graphics, n2, n3, n4, n5, 0, 0);
    }

    final void a(Graphics graphics, int n2, int n3, int n4, int n5, int n6, int n7) {
        try {
            if (n2 >= this.o.length || n2 < 0) {
                return;
            }
            int n8 = this.o[n2] & 0xFF;
            if (n8 > 0) {
                for (int i2 = 0; i2 < n8; ++i2) {
                    this.b(graphics, n2, i2, n3, n4, n5, n6, n7);
                }
            }
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    final void a(Graphics graphics, int n2, int n3, int n4, int n5, int n6) {
        this.b(graphics, 0, n3, n4, n5, 0, 0, 0);
    }

    private void b(Graphics graphics, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
        int n9;
        n2 = this.p[n2] + n3;
        n3 = this.y[n2] & 0xFF;
        int n10 = n9 = this.v[n2] & 0xFF;
        if ((n3 & 0x10) == 0 && this.G >= 0) {
            n9 = this.d[this.G][n9];
        }
        int n11 = 0;
        boolean bl2 = false;
        int n12 = 0;
        int n13 = 0;
        if ((n3 & 0x10) == 0) {
            n12 = this.d(n9);
            n13 = this.l(n9);
        }
        int n14 = n2;
        ao ao2 = this;
        n11 = ao2.w[n14];
        n14 = n2;
        ao2 = this;
        int n15 = ao2.x[n14];
        if ((n3 & 4) != 0) {
            n2 = n12;
            n12 = n13;
            n13 = n2;
        }
        if ((n6 & 1) != 0) {
            n3 = V[n3 & 7] | n3 & 0xFFFFFFF8;
            n11 = -n11 - n12;
        }
        if ((n6 & 2) != 0) {
            n3 = W[n3 & 7] | n3 & 0xFFFFFFF8;
            n15 = -n15 - n13;
        }
        if ((n6 & 4) != 0) {
            n3 = X[n3 & 7] | n3 & 0xFFFFFFF8;
            n2 = n11;
            n11 = -n15 - n13;
            n15 = n2;
        }
        n4 += n11;
        n5 += n15;
        if ((n3 & 0x10) != 0) {
            this.a(graphics, n9, n4, n5, n3 & 0xF, n7, n8);
            return;
        }
        this.b(graphics, n10, n4, n5, n3 & 0xF);
    }

    private int m(int n2) {
        if (this.n != null) {
            for (int i2 = 0; i2 < this.n.length; i2 += 2) {
                if (this.n[i2] != n2) continue;
                return this.n[i2 + 1];
            }
        }
        return -1;
    }

    /*
     * WARNING - void declaration
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    final void b(Graphics graphics, int n2, int n3, int n4, int n5) {
        Image image;
        int n6;
        if (((ao)((Object)exception2)).G >= 0) {
            n2 = ((ao)((Object)exception2)).d[((ao)((Object)exception2)).G][n2];
        }
        int n7 = n5;
        int n8 = n4;
        int n9 = n3;
        int n10 = n2;
        n2 = a == null ? n10 : a.c(n10, n9, n8, n7);
        int n11 = ((ao)((Object)exception2)).d(n2);
        int n12 = ((ao)((Object)exception2)).l(n2);
        int n13 = n11;
        int n14 = n12;
        if ((n5 & 4) != 0) {
            n6 = n11;
            n11 = n12;
            n12 = n6;
        }
        if (((ao)((Object)exception2)).Y[n2] != 0 && graphics != null) {
            if (aq.g(7)) {
                graphics.setColor(aq.f(((ao)((Object)exception2)).Z[n2]));
            } else {
                graphics.setColor(((ao)((Object)exception2)).Z[n2]);
            }
            switch (((ao)((Object)exception2)).Y[n2]) {
                case 2: {
                    if ((((ao)((Object)exception2)).Z[n2] & 0xFF000000) == -16777216 || (((ao)((Object)exception2)).Z[n2] & 0xFF000000) == 0) {
                        if (aq.g(10)) {
                            n6 = aq.b(10, 1);
                            aq.e(((ao)((Object)exception2)).Z[n2] & 0xFFFFFF | n6 << 24);
                            aq.b(graphics, n3, n4, n11, n12);
                            return;
                        }
                        aq.a(graphics, n3, n4, n11, n12);
                        return;
                    }
                    if (aq.g(7)) {
                        aq.e(aq.f(((ao)((Object)exception2)).Z[n2]));
                        aq.b(graphics, n3, n4, n11, n12);
                        return;
                    }
                    aq.e(((ao)((Object)exception2)).Z[n2]);
                    aq.b(graphics, n3, n4, n11, n12);
                    return;
                }
                case 1: {
                    --n11;
                    --n12;
                    if (aq.g(10)) {
                        n6 = aq.b(10, 1);
                        aq.a(graphics, n3, n4, n11, n12, ((ao)((Object)exception2)).Z[n2] & 0xFFFFFF | n6 << 24);
                        return;
                    }
                    aq.b(n3, n4, n11, n12);
                    return;
                }
                case 3: 
                case 4: {
                    int n15;
                    int n16;
                    int n17;
                    n6 = ((ao)((Object)exception2)).m(n2);
                    if (n6 == -1) return;
                    short s2 = ((ao)((Object)exception2)).m[n6];
                    n10 = ((ao)((Object)exception2)).m[n6 + 1];
                    if ((n5 & 1) != 0) {
                        n17 = 90 - s2;
                    }
                    if ((n5 & 2) != 0) {
                        n16 = -n17;
                        n10 = -n10;
                    }
                    if ((n5 & 4) != 0) {
                        n15 = n16 - 90;
                    }
                    if (((ao)((Object)exception2)).Y[n2] == 3) {
                        graphics.drawArc(n3, n4, n11, n12, n15, n10);
                        return;
                    }
                    graphics.fillArc(n3, n4, n11, n12, n15, n10);
                    return;
                }
                case 6: 
                case 7: {
                    n6 = ((ao)((Object)exception2)).m(n2);
                    if (n6 == -1) return;
                    short s3 = ((ao)((Object)exception2)).m[n6];
                    n10 = ((ao)((Object)exception2)).m[n6 + 1];
                    n9 = ((ao)((Object)exception2)).m[n6 + 2];
                    n6 = ((ao)((Object)exception2)).m[n6 + 3];
                    if (((ao)((Object)exception2)).Y[n2] == 6) {
                        graphics.drawLine(n3, n4, n3 + s3, n4 + n10);
                        graphics.drawLine(n3 + s3, n4 + n10, n3 + n9, n4 + n6);
                        graphics.drawLine(n3, n4, n3 + n9, n4 + n6);
                        return;
                    }
                    graphics.fillTriangle(n3, n4, n3 + s3, n4 + n10, n3 + n9, n4 + n6);
                }
            }
            return;
        }
        if (g == 1) {
            if (n3 < aa) {
                aa = n3;
            }
            if (n4 < ab) {
                ab = n4;
            }
            if (n3 + n11 > ac) {
                ac = n3 + n11;
            }
            if (n4 + n12 <= ad) return;
            ad = n4 + n12;
            return;
        }
        if (n11 <= 0) return;
        if (n12 <= 0) {
            return;
        }
        if (graphics != null) {
            n6 = graphics.getClipX();
            int n18 = graphics.getClipY();
            n10 = graphics.getClipWidth();
            n9 = graphics.getClipHeight();
            if (n3 + n11 < n6) return;
            if (n4 + n12 < n18) return;
            if (n3 >= n6 + n10) return;
            if (n4 >= n18 + n9) {
                return;
            }
        }
        Object var11_31 = null;
        n9 = 1;
        if ((n5 & 4) != 0) {
            n9 = 2;
        }
        Image image2 = ck.a((ao)((Object)exception2), n2, ((ao)((Object)exception2)).N, n9);
        n6 = ((ao)((Object)exception2)).O ? 1 : 0;
        if (image2 == null) {
            void var11_51;
            int n19;
            Object var11_33 = null;
            n9 = n2;
            ao ao2 = exception2;
            if (ao2.I == null) return;
            if (ao2.H == null) return;
            int n20 = n9;
            ao ao3 = ao2;
            int n21 = ao2.d(n9);
            n9 = ao2.l(n9);
            int n22 = n21;
            int n23 = ao3.I[n20];
            byte[] byArray = ao2.H;
            ao ao4 = ao2;
            int n24 = 0;
            n22 *= n9;
            if (c == null) {
                c = new int[81920];
            }
            int[] nArray = null;
            if (ao4.K == null) {
                return;
            }
            nArray = ao4.K[ao4.N];
            if (nArray == null) {
                return;
            }
            int n25 = 0;
            if (ao4.Q == 25840) {
                while (n24 < n22) {
                    void var11_36;
                    n19 = byArray[++var11_36] & 0xFF;
                    n25 = ao4.k(nArray[n19 & ao4.R]);
                    n19 >>= ao4.S;
                    while (n19-- >= 0) {
                        ao.c[n24++] = n25;
                    }
                }
            } else if (ao4.Q == 10225) {
                while (n24 < n22) {
                    void var11_37;
                    if ((n19 = byArray[var11_37++] & 0xFF) > 127) {
                        n25 = byArray[var11_37++] & 0xFF;
                        n25 = ao4.k(nArray[n25]);
                        n19 -= 128;
                        while (n19-- > 0) {
                            ao.c[n24++] = n25;
                        }
                        continue;
                    }
                    ao.c[n24++] = ao4.k(nArray[n19]);
                }
            } else if (ao4.Q == 22258) {
                while (n24 < n22) {
                    void var11_38;
                    if ((n19 = byArray[var11_38++] & 0xFF) > 127) {
                        n19 -= 128;
                        while (n19-- > 0) {
                            ao.c[n24++] = ao4.k(nArray[byArray[var11_38++] & 0xFF]);
                        }
                        continue;
                    }
                    n25 = ao4.k(nArray[byArray[var11_38++] & 0xFF]);
                    while (n19-- > 0) {
                        ao.c[n24++] = n25;
                    }
                }
            } else if (ao4.Q == 5632) {
                n22 += (n22 & 1) == 0 ? 0 : 2;
                n22 >>= 1;
                while (--n22 >= 0) {
                    void var11_39;
                    n19 = byArray[++var11_39];
                    ao.c[n24++] = nArray[n19 >> 4 & 0xF];
                    ao.c[n24++] = nArray[n19 & 0xF];
                }
            } else if (ao4.Q == 1024) {
                n22 += (n22 & 3) == 0 ? 0 : 4;
                n22 >>= 2;
                while (--n22 >= 0) {
                    void var11_40;
                    n19 = byArray[++var11_40];
                    ao.c[n24++] = nArray[n19 >> 6 & 3];
                    ao.c[n24++] = nArray[n19 >> 4 & 3];
                    ao.c[n24++] = nArray[n19 >> 2 & 3];
                    ao.c[n24++] = nArray[n19 & 3];
                }
            } else if (ao4.Q == 512) {
                n22 += (n22 & 7) == 0 ? 0 : 8;
                n22 >>= 3;
                while (--n22 >= 0) {
                    void var11_41;
                    n19 = byArray[++var11_41];
                    ao.c[n24++] = nArray[n19 >> 7 & 1];
                    ao.c[n24++] = nArray[n19 >> 6 & 1];
                    ao.c[n24++] = nArray[n19 >> 5 & 1];
                    ao.c[n24++] = nArray[n19 >> 4 & 1];
                    ao.c[n24++] = nArray[n19 >> 3 & 1];
                    ao.c[n24++] = nArray[n19 >> 2 & 1];
                    ao.c[n24++] = nArray[n19 >> 1 & 1];
                    ao.c[n24++] = nArray[n19 & 1];
                }
            } else if (ao4.Q == 22018) {
                while (--n22 >= 0) {
                    void var11_42;
                    ao.c[n24++] = ao4.k(nArray[byArray[++var11_42] & 0xFF]);
                }
            } else if (ao4.Q == -23198) {
                while (--n22 >= 0) {
                    void var11_43;
                    ao.c[n24++] = (byArray[++var11_43] & 0xFF) << 24 | nArray[byArray[++var11_43] & 0xFF] & 0xFFFFFF;
                }
            } else if (ao4.Q == -24281) {
                while (n24 < n22) {
                    void var11_44;
                    if ((n19 = byArray[var11_44++] & 0xFF) > 127) {
                        n25 = byArray[var11_44++] & 0xFF;
                        n25 = nArray[n25];
                        n19 -= 128;
                        while (n19-- > 0) {
                            ao.c[n24++] = n25;
                        }
                        continue;
                    }
                    ao.c[n24++] = nArray[n19];
                }
                n24 = 0;
                while (n24 < n22) {
                    void var11_45;
                    if ((n19 = byArray[var11_45++] & 0xFF) == 254) {
                        n25 = byArray[var11_45++] & 0xFF;
                        n19 = byArray[var11_45++] & 0xFF;
                        while (n25-- > 0) {
                            ao.c[n24] = n19 << 24 | c[n24] & 0xFFFFFF;
                            ++n24;
                        }
                        continue;
                    }
                    ao.c[n24] = n19 << 24 | c[n24] & 0xFFFFFF;
                    ++n24;
                }
            } else if (ao4.Q == -23978) {
                while (n24 < n22) {
                    void var11_46;
                    if ((n19 = byArray[var11_46++] & 0xFF) > 127) {
                        n19 -= 128;
                        while (n19-- > 0) {
                            ao.c[n24++] = nArray[byArray[var11_46++] & 0xFF];
                        }
                        continue;
                    }
                    n25 = nArray[byArray[var11_46++] & 0xFF];
                    while (n19-- > 0) {
                        ao.c[n24++] = n25;
                    }
                }
                n24 = 0;
                while (n24 < n22) {
                    void var11_47;
                    if ((n19 = byArray[var11_47++] & 0xFF) == 254) {
                        n25 = byArray[var11_47++] & 0xFF;
                        n19 = byArray[var11_47++] & 0xFF;
                        while (n25-- > 0) {
                            ao.c[n24] = n19 << 24 | c[n24] & 0xFFFFFF;
                            ++n24;
                        }
                        continue;
                    }
                    ao.c[n24] = n19 << 24 | c[n24] & 0xFFFFFF;
                    ++n24;
                }
            }
            boolean bl2 = true;
            if (!bl2) return;
            int[] nArray2 = c;
            int[] nArray3 = nArray2;
            if (nArray2 == null) {
                return;
            }
            if (aq.l() != 0) {
                int[] nArray4 = aq.a(nArray3, n13, n14, n5, n6 != 0, ((ao)((Object)exception2)).P);
                if (nArray4 == null) {
                    return;
                }
                n6 = aq.j;
                n11 = aq.k;
                n12 = aq.l;
            }
            if (aq.l() == 0) {
                try {
                    void var11_54;
                    if (((ao)((Object)exception2)).T == null) {
                        ((ao)((Object)exception2)).T = new Image[((ao)((Object)exception2)).L][];
                        for (n13 = 0; n13 < ((ao)((Object)exception2)).L; ++n13) {
                            ((ao)((Object)exception2)).T[n13] = new Image[((ao)((Object)exception2)).h];
                        }
                    }
                    n13 = 1;
                    if ((n5 & 4) != 0) {
                        n13 = 2;
                        Image image3 = aq.a((int[])var11_51, n12, n11, ((ao)((Object)exception2)).O);
                    } else {
                        Image image4 = aq.a((int[])var11_51, n11, n12, ((ao)((Object)exception2)).O);
                    }
                    ck.a((Image)var11_54, (ao)((Object)exception2), n2, ((ao)((Object)exception2)).N, n13);
                    void var14_11 = var11_54;
                    if (var14_11 == null) {
                        return;
                    }
                    boolean bl3 = true;
                    if (!bl3) return;
                    if (U[n5 & 7] == 0) {
                        try {
                            if (g == 1) return;
                            graphics.drawImage((Image)var11_54, n3, n4, 0);
                            return;
                        }
                        catch (Exception exception) {
                            Exception exception2 = exception;
                            exception.printStackTrace();
                        }
                        return;
                    }
                    if ((n5 & 4) != 0) {
                        graphics.drawRegion((Image)var11_54, 0, 0, n12, n11, U[n5 & 7], n3, n4, 0);
                        return;
                    }
                    graphics.drawRegion((Image)var11_54, 0, 0, n11, n12, U[n5 & 7], n3, n4, 0);
                    return;
                }
                catch (Exception exception) {}
                return;
            }
            aq.a(graphics, (int[])var11_51, 0, n11, n3, n4, n11, n12, n6 != 0);
            return;
        }
        if (aq.l() != 0) {
            int[] nArray = aq.a(graphics, image2, n3, n4, n13, n14, n5, n6 != 0, ((ao)((Object)exception2)).P);
            if (nArray == null) {
                return;
            }
            n6 = aq.j ? 1 : 0;
            n11 = aq.k;
            n12 = aq.l;
            aq.a(graphics, nArray, 0, n11, n3, n4, n11, n12, n6 != 0);
            return;
        }
        n11 = image2.getWidth();
        n12 = image2.getHeight();
        Image image5 = image2;
        n9 = n2;
        ao ao5 = exception2;
        if (ao5.f >= 0 && ao5.T[ao5.N][n9] == null) {
            short s4;
            n7 = null[ao5.f];
            Object var16_58 = null;
            null[n7] = s4 = (short)((n9 & 0x3FF) + (ao5.N << 10));
            null[n7] = ao5;
            null[ao5.f] = (null[ao5.f] + true) % null[ao5.f];
            ao5.T[ao5.N][n9] = image5;
        }
        if ((image = image2) == null) {
            return;
        }
        boolean bl4 = true;
        if (!bl4) return;
        if (U[n5 & 7] == 0) {
            try {
                if (g == 1) return;
                graphics.drawImage(image2, n3, n4, 0);
                return;
            }
            catch (Exception exception) {
                Exception exception3 = exception;
                exception.printStackTrace();
                return;
            }
        }
        graphics.drawRegion(image2, 0, 0, n11, n12, U[n5 & 7], n3, n4, 0);
    }

    final void j(int n2) {
        if (n2 < this.L && n2 >= 0) {
            this.N = n2;
            return;
        }
    }

    final int c() {
        return this.N;
    }

    static final int[] a(int[] nArray, int n2, int n3) {
        int[] nArray2 = null;
        nArray2 = ao.a(null);
        if (nArray2 == nArray) {
            nArray2 = new int[n2 * n3];
        }
        return nArray2;
    }

    ao() {
    }

    static {
        String[] stringArray = new String[]{"drawImgCC", "drawRegionCC", "drawRGBCC", "crImgCC", "decodeImgCC", "alphaBlendCC"};
        byte[] byArray = new byte[]{-119, 80, 78, 71, 13, 10, 26, 10};
        byte[] byArray2 = new byte[]{73, 72, 68, 82};
        byte[] byArray3 = new byte[]{80, 76, 84, 69};
        byte[] byArray4 = new byte[]{116, 82, 78, 83};
        byte[] byArray5 = new byte[]{73, 68, 65, 84};
        byte[] byArray6 = new byte[]{73, 69, 78, 68};
        byte[] byArray7 = new byte[]{8, 6, 0, 0, 0};
        byte[] byArray8 = new byte[]{8, 3, 0, 0, 0};
        byte[] byArray9 = new byte[]{0, 0, 0, 0, 73, 69, 78, 68, -82, 66, 96, -126};
        byte[] byArray10 = new byte[]{120, -100, 1};
        g = 0;
        U = new int[]{0, 2, 1, 3, 5, 7, 4, 6};
        V = new int[]{1, 0, 3, 2, 6, 7, 4, 5};
        W = new int[]{2, 3, 0, 1, 5, 4, 7, 6};
        X = new int[]{4, 5, 6, 7, 3, 2, 1, 0};
        ae = new int[4];
        af = true;
    }
}

