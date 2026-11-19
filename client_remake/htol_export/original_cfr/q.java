/*
 * Decompiled with CFR 0.152.
 */
final class q {
    private byte[] b;
    private int c;
    int a;

    q(byte[] byArray, int n2) {
        this.b = byArray;
        this.a = this.c = n2;
    }

    final byte a() {
        int n2 = this.a++;
        byte[] byArray = this.b;
        byte by2 = this.b[n2];
        return by2;
    }

    final int b() {
        int n2 = this.a();
        if (n2 < 0) {
            --n2;
            n2 ^= 0xFF;
            n2 = 0 - n2;
        }
        return n2;
    }

    final short c() {
        int n2 = this.a;
        byte[] byArray = this.b;
        short s2 = (short)(this.b[n2] & 0xFF | (byArray[n2 + 1] & 0xFF) << 8);
        this.a += 2;
        return s2;
    }

    final int d() {
        int n2 = this.c();
        if (n2 < 0) {
            --n2;
            n2 ^= 0xFFFF;
            n2 = 0 - n2;
        }
        return n2;
    }

    final int e() {
        int n2 = this.a;
        byte[] byArray = this.b;
        int n3 = this.b[n2] & 0xFF | (byArray[n2 + 1] & 0xFF) << 8 | (byArray[n2 + 2] & 0xFF) << 16 | (byArray[n2 + 3] & 0xFF) << 24;
        this.a += 4;
        return n3;
    }

    final long f() {
        long l2 = this.b[this.a] & 0xFF | (this.b[this.a + 1] & 0xFF) << 8 | (this.b[this.a + 2] & 0xFF) << 16 | (this.b[this.a + 3] & 0xFF) << 24;
        this.a += 4;
        return l2;
    }

    final String a(int n2, int n3) {
        if (n3 >= 1 && n3 < 4096) {
            byte[] byArray = new byte[n3];
            System.arraycopy(this.b, this.a, byArray, 0, n3);
            this.a += n3;
            return bg.a(byArray, "UTF-8");
        }
        return "";
    }

    final String g() {
        int n2 = this.b();
        return this.a(0, n2);
    }

    final int h() {
        return this.a - this.c;
    }
}

