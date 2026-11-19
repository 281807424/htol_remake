/*
 * Decompiled with CFR 0.152.
 */
public final class g {
    private static byte[] a = new byte[]{-128, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    private long[] b = new long[4];
    private long[] c = new long[2];
    private byte[] d = new byte[64];
    private byte[] e;
    private byte[] f = new byte[16];

    public final byte[] a(String object) {
        this.a();
        this.a(((String)object).getBytes(), ((String)object).length());
        object = this;
        byte[] byArray = new byte[8];
        g.a(byArray, ((g)object).c, 8);
        int n2 = (int)(((g)object).c[0] >>> 3) & 0x3F;
        n2 = n2 < 56 ? 56 - n2 : 120 - n2;
        super.a(a, n2);
        super.a(byArray, 8);
        g.a(((g)object).f, ((g)object).b, 16);
        this.e = new byte[32];
        object = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        for (int i2 = 0; i2 < 16; ++i2) {
            this.e[i2 << 1] = (byte)object[this.f[i2] >>> 4 & 0xF];
            this.e[(i2 << 1) + 1] = (byte)object[this.f[i2] & 0xF];
        }
        return this.e;
    }

    public g() {
        this.a();
    }

    private void a() {
        this.c[0] = 0L;
        this.c[1] = 0L;
        this.b[0] = 1732584193L;
        this.b[1] = 4023233417L;
        this.b[2] = 2562383102L;
        this.b[3] = 271733878L;
    }

    private long a(long l2, long l3, long l4, long l5, long l6, long l7, long l8) {
        long l9 = l5;
        long l10 = l4;
        long l11 = l3;
        l2 += (l11 & l10 | (l11 ^ 0xFFFFFFFFFFFFFFFFL) & l9) + l6 + l8;
        l2 = (int)l2 << (int)l7 | (int)l2 >>> (int)(32L - l7);
        return l2 += l3;
    }

    private long b(long l2, long l3, long l4, long l5, long l6, long l7, long l8) {
        long l9 = l5;
        long l10 = l4;
        long l11 = l3;
        l2 += (l11 & l9 | l10 & (l9 ^ 0xFFFFFFFFFFFFFFFFL)) + l6 + l8;
        l2 = (int)l2 << (int)l7 | (int)l2 >>> (int)(32L - l7);
        return l2 += l3;
    }

    private long c(long l2, long l3, long l4, long l5, long l6, long l7, long l8) {
        long l9 = l5;
        long l10 = l4;
        long l11 = l3;
        l2 += (l11 ^ l10 ^ l9) + l6 + l8;
        l2 = (int)l2 << (int)l7 | (int)l2 >>> (int)(32L - l7);
        return l2 += l3;
    }

    private long d(long l2, long l3, long l4, long l5, long l6, long l7, long l8) {
        long l9 = l5;
        long l10 = l4;
        long l11 = l3;
        l2 += (l10 ^ (l11 | l9 ^ 0xFFFFFFFFFFFFFFFFL)) + l6 + l8;
        l2 = (int)l2 << (int)l7 | (int)l2 >>> (int)(32L - l7);
        return l2 += l3;
    }

    private void a(byte[] byArray, int n2) {
        byte[] byArray2 = new byte[64];
        int n3 = (int)(this.c[0] >>> 3) & 0x3F;
        this.c[0] = this.c[0] + (long)(n2 << 3);
        if (this.c[0] < (long)(n2 << 3)) {
            this.c[1] = this.c[1] + 1L;
        }
        this.c[1] = this.c[1] + (long)(n2 >>> 29);
        int n4 = 64 - n3;
        if (n2 >= n4) {
            g.a(this.d, byArray, n3, 0, n4);
            this.a(this.d);
            while (n4 + 63 < n2) {
                g.a(byArray2, byArray, 0, n4, 64);
                this.a(byArray2);
                n4 += 64;
            }
            n3 = 0;
        } else {
            n4 = 0;
        }
        g.a(this.d, byArray, n3, n4, n2 - n4);
    }

    private static void a(byte[] byArray, byte[] byArray2, int n2, int n3, int n4) {
        for (int i2 = 0; i2 < n4; ++i2) {
            byArray[n2 + i2] = byArray2[n3 + i2];
        }
    }

    private void a(byte[] byArray) {
        long l2 = this.b[0];
        long l3 = this.b[1];
        long l4 = this.b[2];
        long l5 = this.b[3];
        long[] lArray = new long[16];
        int n2 = 64;
        byte[] byArray2 = byArray;
        long[] lArray2 = lArray;
        int n3 = 0;
        for (int i2 = 0; i2 < 64; i2 += 4) {
            lArray2[n3] = g.a(byArray2[i2]) | g.a(byArray2[i2 + 1]) << 8 | g.a(byArray2[i2 + 2]) << 16 | g.a(byArray2[i2 + 3]) << 24;
            ++n3;
        }
        l2 = this.a(l2, l3, l4, l5, lArray[0], 7L, 3614090360L);
        l5 = this.a(l5, l2, l3, l4, lArray[1], 12L, 3905402710L);
        l4 = this.a(l4, l5, l2, l3, lArray[2], 17L, 606105819L);
        l3 = this.a(l3, l4, l5, l2, lArray[3], 22L, 3250441966L);
        l2 = this.a(l2, l3, l4, l5, lArray[4], 7L, 4118548399L);
        l5 = this.a(l5, l2, l3, l4, lArray[5], 12L, 1200080426L);
        l4 = this.a(l4, l5, l2, l3, lArray[6], 17L, 2821735955L);
        l3 = this.a(l3, l4, l5, l2, lArray[7], 22L, 4249261313L);
        l2 = this.a(l2, l3, l4, l5, lArray[8], 7L, 1770035416L);
        l5 = this.a(l5, l2, l3, l4, lArray[9], 12L, 2336552879L);
        l4 = this.a(l4, l5, l2, l3, lArray[10], 17L, 4294925233L);
        l3 = this.a(l3, l4, l5, l2, lArray[11], 22L, 2304563134L);
        l2 = this.a(l2, l3, l4, l5, lArray[12], 7L, 1804603682L);
        l5 = this.a(l5, l2, l3, l4, lArray[13], 12L, 4254626195L);
        l4 = this.a(l4, l5, l2, l3, lArray[14], 17L, 2792965006L);
        l3 = this.a(l3, l4, l5, l2, lArray[15], 22L, 1236535329L);
        l2 = this.b(l2, l3, l4, l5, lArray[1], 5L, 4129170786L);
        l5 = this.b(l5, l2, l3, l4, lArray[6], 9L, 3225465664L);
        l4 = this.b(l4, l5, l2, l3, lArray[11], 14L, 643717713L);
        l3 = this.b(l3, l4, l5, l2, lArray[0], 20L, 3921069994L);
        l2 = this.b(l2, l3, l4, l5, lArray[5], 5L, 3593408605L);
        l5 = this.b(l5, l2, l3, l4, lArray[10], 9L, 38016083L);
        l4 = this.b(l4, l5, l2, l3, lArray[15], 14L, 3634488961L);
        l3 = this.b(l3, l4, l5, l2, lArray[4], 20L, 3889429448L);
        l2 = this.b(l2, l3, l4, l5, lArray[9], 5L, 568446438L);
        l5 = this.b(l5, l2, l3, l4, lArray[14], 9L, 3275163606L);
        l4 = this.b(l4, l5, l2, l3, lArray[3], 14L, 4107603335L);
        l3 = this.b(l3, l4, l5, l2, lArray[8], 20L, 1163531501L);
        l2 = this.b(l2, l3, l4, l5, lArray[13], 5L, 2850285829L);
        l5 = this.b(l5, l2, l3, l4, lArray[2], 9L, 4243563512L);
        l4 = this.b(l4, l5, l2, l3, lArray[7], 14L, 1735328473L);
        l3 = this.b(l3, l4, l5, l2, lArray[12], 20L, 2368359562L);
        l2 = this.c(l2, l3, l4, l5, lArray[5], 4L, 4294588738L);
        l5 = this.c(l5, l2, l3, l4, lArray[8], 11L, 2272392833L);
        l4 = this.c(l4, l5, l2, l3, lArray[11], 16L, 1839030562L);
        l3 = this.c(l3, l4, l5, l2, lArray[14], 23L, 4259657740L);
        l2 = this.c(l2, l3, l4, l5, lArray[1], 4L, 2763975236L);
        l5 = this.c(l5, l2, l3, l4, lArray[4], 11L, 1272893353L);
        l4 = this.c(l4, l5, l2, l3, lArray[7], 16L, 4139469664L);
        l3 = this.c(l3, l4, l5, l2, lArray[10], 23L, 3200236656L);
        l2 = this.c(l2, l3, l4, l5, lArray[13], 4L, 681279174L);
        l5 = this.c(l5, l2, l3, l4, lArray[0], 11L, 3936430074L);
        l4 = this.c(l4, l5, l2, l3, lArray[3], 16L, 3572445317L);
        l3 = this.c(l3, l4, l5, l2, lArray[6], 23L, 76029189L);
        l2 = this.c(l2, l3, l4, l5, lArray[9], 4L, 3654602809L);
        l5 = this.c(l5, l2, l3, l4, lArray[12], 11L, 3873151461L);
        l4 = this.c(l4, l5, l2, l3, lArray[15], 16L, 530742520L);
        l3 = this.c(l3, l4, l5, l2, lArray[2], 23L, 3299628645L);
        l2 = this.d(l2, l3, l4, l5, lArray[0], 6L, 4096336452L);
        l5 = this.d(l5, l2, l3, l4, lArray[7], 10L, 1126891415L);
        l4 = this.d(l4, l5, l2, l3, lArray[14], 15L, 2878612391L);
        l3 = this.d(l3, l4, l5, l2, lArray[5], 21L, 4237533241L);
        l2 = this.d(l2, l3, l4, l5, lArray[12], 6L, 1700485571L);
        l5 = this.d(l5, l2, l3, l4, lArray[3], 10L, 2399980690L);
        l4 = this.d(l4, l5, l2, l3, lArray[10], 15L, 4293915773L);
        l3 = this.d(l3, l4, l5, l2, lArray[1], 21L, 2240044497L);
        l2 = this.d(l2, l3, l4, l5, lArray[8], 6L, 1873313359L);
        l5 = this.d(l5, l2, l3, l4, lArray[15], 10L, 4264355552L);
        l4 = this.d(l4, l5, l2, l3, lArray[6], 15L, 2734768916L);
        l3 = this.d(l3, l4, l5, l2, lArray[13], 21L, 1309151649L);
        l2 = this.d(l2, l3, l4, l5, lArray[4], 6L, 4149444226L);
        l5 = this.d(l5, l2, l3, l4, lArray[11], 10L, 3174756917L);
        l4 = this.d(l4, l5, l2, l3, lArray[2], 15L, 718787259L);
        l3 = this.d(l3, l4, l5, l2, lArray[9], 21L, 3951481745L);
        this.b[0] = this.b[0] + l2;
        this.b[1] = this.b[1] + l3;
        this.b[2] = this.b[2] + l4;
        this.b[3] = this.b[3] + l5;
    }

    private static void a(byte[] byArray, long[] lArray, int n2) {
        int n3 = 0;
        for (int i2 = 0; i2 < n2; i2 += 4) {
            byArray[i2] = (byte)(lArray[n3] & 0xFFL);
            byArray[i2 + 1] = (byte)(lArray[n3] >>> 8 & 0xFFL);
            byArray[i2 + 2] = (byte)(lArray[n3] >>> 16 & 0xFFL);
            byArray[i2 + 3] = (byte)(lArray[n3] >>> 24 & 0xFFL);
            ++n3;
        }
    }

    private static long a(byte by2) {
        if (by2 < 0) {
            return by2 & 0xFF;
        }
        return by2;
    }
}

