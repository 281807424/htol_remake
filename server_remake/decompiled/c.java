/*
 * Decompiled with CFR 0.152.
 */
public final class c {
    int a;
    byte b;
    short[] c = new short[2];
    short[] d = new short[2];
    static short[] e = new short[2];
    byte f;

    final boolean a() {
        return this.f < 0;
    }

    public final void a(boolean bl2, boolean bl3) {
        byte by2 = this.f;
        this.f = (byte)(by2 - 1);
        if (by2 > 0) {
            for (int i2 = 0; i2 < 2; ++i2) {
                int n2 = i2;
                this.d[n2] = (short)(this.d[n2] + e[i2]);
                int n3 = i2;
                this.c[n3] = (short)(this.c[n3] + this.d[i2]);
            }
            if (bl2) {
                this.a = bm.e(0, 3);
            }
            if (bl3 && (this.c[0] < cu.a[0] || this.c[0] > cu.b[0] || this.c[1] < cu.a[1] || this.c[1] > cu.b[1])) {
                if (this.f > 15) {
                    this.c[0] = (short)bm.e(cu.a[0], cu.b[0]);
                    this.c[1] = (short)bm.e(cu.a[1], cu.b[1]);
                }
                this.f = (byte)-1;
            }
        }
    }

    final int a(boolean bl2) {
        if (bl2) {
            return this.c[0];
        }
        return this.c[0] - bm.bW;
    }

    final int b(boolean bl2) {
        if (bl2) {
            return this.c[1];
        }
        return this.c[1] - bm.bX;
    }
}

