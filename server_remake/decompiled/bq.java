/*
 * Decompiled with CFR 0.152.
 */
final class bq {
    private static int[] a = new int[]{0, -1, 0, 1, -1, 0, 1, 0, -1, -1, 1, -1, -1, 1, 1, 1};
    private short[] b;
    private short[] c;
    private short[] d;
    private short[] e;
    private short[] f;
    private int g;
    private int h;
    private int i;
    private int j;
    private int k;
    private int l;
    private byte[] m;
    private short[] n;
    private int o;
    private int p;

    private int a(int n2, int n3) {
        return n3 * this.k + n2;
    }

    public final void a(int n2, int n3, byte[] byArray, int n4, int n5, int n6, int n7, int n8) {
        this.k = n2;
        this.l = n3;
        this.m = byArray;
        this.g = 1;
        this.h = 1;
        this.i = 0;
        this.j = 8;
        this.b = new short[3000];
        this.c = new short[3000];
        this.d = new short[3000];
        this.e = new short[3000];
        this.f = new short[3000];
        this.p = -1;
        this.n = new short[3000];
        this.o = -1;
    }

    public final void a(int n2, int n3, int n4, int n5, int n6) {
        int n7;
        int n8;
        int n9 = this.l;
        int n10 = this.k;
        boolean bl2 = false;
        bl2 = false;
        n4 = 0;
        bq bq2 = this;
        for (n8 = 0; n8 < 3000; ++n8) {
            bq2.b[n8] = -1;
            bq2.c[n8] = -1;
            bq2.d[n8] = -1;
            bq2.e[n8] = 0;
            bq2.f[n8] = 0;
            bq2.n[n8] = 0;
        }
        bq2.p = -1;
        n2 = n3 * n10 + n2;
        int n11 = n5;
        int n12 = n6;
        while (n2 != -1) {
            bq2.d(n2);
            bq2.e[n2] = -1;
            bq2.f[n2] = -1;
            n3 = n2 % n10;
            n5 = n2 / n10;
            if (n3 == n11 && n5 == n12) break;
            for (n7 = 0; n7 < bq2.j; ++n7) {
                int n13;
                int n14;
                int n15;
                block19: {
                    n6 = a[n7 << 1];
                    n15 = a[(n7 << 1) + 1];
                    n14 = n6 != 0 && n15 != 0 ? bq2.h : bq2.g;
                    if ((n6 += n3) < 0 || n6 >= n10 || (n15 += n5) < 0 || n15 >= n9) continue;
                    if (n7 >= 4) {
                        n8 = n2 % n10;
                        n13 = n2 / n10;
                        if (n7 == 4) {
                            if (cr.A(bq2.m[bq2.a(n8 - 1, n13)]) || cr.A(bq2.m[bq2.a(n8, n13 - 1)])) {
                                continue;
                            }
                        } else if (n7 == 5) {
                            if (cr.A(bq2.m[bq2.a(n8 + 1, n13)]) || cr.A(bq2.m[bq2.a(n8, n13 - 1)])) {
                                continue;
                            }
                        } else if (n7 != 6 ? n7 == 7 && (cr.A(bq2.m[bq2.a(n8 + 1, n13)]) || cr.A(bq2.m[bq2.a(n8, n13 + 1)])) : cr.A(bq2.m[bq2.a(n8 - 1, n13)]) || cr.A(bq2.m[bq2.a(n8, n13 + 1)])) continue;
                    }
                    n8 = n15 * n10 + n6;
                    try {
                        if (cr.A(bq2.m[bq2.a(n6, n15)])) {
                        }
                        break block19;
                    }
                    catch (Exception exception) {
                        Exception exception2 = exception;
                        exception.printStackTrace();
                    }
                    continue;
                }
                if (bq2.e[n8] == -1) continue;
                n13 = n4 == n7 ? 0 : bq2.i;
                n14 = bq2.e[n2] + n14 + n13;
                n6 = Math.abs(n6 - n11);
                n15 = Math.abs(n15 - n12);
                n6 = n6 > n15 ? bq2.h * n15 + bq2.g * (n6 - n15) : bq2.h * n6 + bq2.g * (n15 - n6);
                if (bq2.c[n8] == -1 && bq2.d[n8] == -1 && bq2.p != n8) {
                    bq2.b[n8] = (short)n2;
                    bq2.e[n8] = (short)n14;
                    bq2.f[n8] = (short)n6;
                    bq2.c(n8);
                    continue;
                }
                if (bq2.e[n8] <= n14) continue;
                bq2.b[n8] = (short)n2;
                bq2.e[n8] = (short)n14;
                bq2.d(n8);
                bq2.c(n8);
            }
            n2 = bq2.p;
            if (n2 == -1) continue;
            n3 = bq2.b[n2];
            if (n2 % n10 != n3 % n10) {
                if (n2 % n10 > n3 % n10) {
                    n4 = 3;
                    continue;
                }
                n4 = 2;
                continue;
            }
            n5 = n2;
            bq bq3 = bq2;
            int n16 = n5 / bq3.k;
            n5 = n3;
            bq3 = bq2;
            if (n16 > n5 / bq3.k) {
                n4 = 1;
                continue;
            }
            n4 = 0;
        }
        if (n2 == -1) {
            bq2.o = -1;
            return;
        }
        bq2.o = 0;
        while (n2 != -1) {
            n7 = n2 % n10;
            n8 = n2 / n10;
            bq2.n[bq2.o++] = (short)bq2.a(n7, n8);
            n2 = bq2.b[n2];
        }
        --bq2.o;
    }

    public final int a() {
        return this.o + 1;
    }

    public final int a(int n2) {
        n2 = this.n[n2];
        return n2 % this.k;
    }

    public final int b(int n2) {
        n2 = this.n[n2];
        return n2 / this.k;
    }

    private void c(int n2) {
        if (this.p == -1) {
            this.p = n2;
            return;
        }
        int n3 = this.e[n2] + this.f[n2];
        int n4 = this.p;
        while (n4 != -1) {
            int n5 = this.e[n4] + this.f[n4];
            if (n3 < n5) {
                if (this.c[n4] == -1) {
                    this.p = n2;
                } else {
                    n3 = this.c[n4];
                    this.d[n3] = (short)n2;
                }
                this.c[n2] = this.c[n4];
                this.d[n2] = (short)n4;
                this.c[n4] = (short)n2;
                return;
            }
            if (this.d[n4] == -1) {
                this.d[n4] = (short)n2;
                this.c[n2] = (short)n4;
                return;
            }
            n4 = this.d[n4];
        }
    }

    private void d(int n2) {
        short s2;
        if (this.d[n2] != -1) {
            s2 = this.d[n2];
            this.c[s2] = this.c[n2];
        }
        if (this.p == n2) {
            this.p = this.d[n2];
        } else if (this.c[n2] != -1) {
            s2 = this.c[n2];
            this.d[s2] = this.d[n2];
        }
        this.c[n2] = -1;
        this.d[n2] = -1;
    }
}

