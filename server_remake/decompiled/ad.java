/*
 * Decompiled with CFR 0.152.
 */
class ad
implements ae {
    ah a;
    private cg p;
    ao b;
    int c;
    int d;
    boolean e;
    float f;
    float g;
    float h;
    float i;
    float j;
    float k;
    float l;
    float m;
    ar n;
    private int q;

    public ad(ah ah2, int n2) {
        boolean bl2;
        this.a = ah2;
        this.p = new cg(n2);
        v0.e = bl2 = true;
    }

    void a() {
        ad ad2 = this;
        if (this.e) {
            if (this.f == this.h && this.g == this.i) {
                this.h += this.j;
                this.i += this.k;
                this.f = this.h;
                this.g = this.i;
            } else {
                this.f += this.j;
                this.g += this.k;
            }
            this.j += this.l;
            this.k += this.m;
        }
    }

    final int[] c() {
        int[] nArray = this.n.a();
        int[] nArray2 = nArray;
        nArray[0] = nArray[0] + (int)this.f;
        nArray2[1] = nArray2[1] + (int)this.g;
        if ((this.d & 2) != 0) {
            nArray2[2] = nArray2[2] + nArray2[3];
            nArray2[3] = nArray2[2] - nArray2[3];
            nArray2[2] = nArray2[2] - nArray2[3];
        }
        return nArray2;
    }

    final boolean a(int[] nArray) {
        return this.f + (float)this.n.b() >= (float)nArray[0] && this.f <= (float)(nArray[0] + nArray[2]) && this.g + (float)this.n.c() >= (float)nArray[1] && this.g <= (float)(nArray[1] + nArray[3]);
    }

    final void a(int n2, int n3, int n4, int n5, int n6, int n7) {
        this.n = new ar(this, n2, n3, n4, n5, n6, n7);
    }

    final boolean a(int n2) {
        ad ad2 = this;
        if (ad2.a.d() == null) {
            return false;
        }
        ad2 = this;
        return ad2.a.d().d(n2);
    }

    final boolean b(int n2) {
        return this.p.a() == n2;
    }

    final void c(int n2) {
        this.p.a(n2);
    }

    final void a(float f2, float f3) {
        this.h = f2;
        this.i = f3;
        this.f = this.h;
        this.g = this.i;
    }

    final void b(float f2, float f3) {
        this.j = f2;
        this.k = f3;
    }

    final int d() {
        return this.q;
    }

    final void d(int n2) {
        this.q = n2;
    }

    final boolean e(int n2) {
        return this.a.b(n2);
    }
}

