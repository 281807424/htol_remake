/*
 * Decompiled with CFR 0.152.
 */
public final class ax {
    byte[] a;
    int b;

    public ax(byte[] byArray) {
        this.a = byArray;
        this.b = 0;
    }

    public final int a() {
        return this.a[this.b++] & 0xFF;
    }

    public final int b() {
        return this.a() << 24 | this.a() << 16 | this.a() << 8 | this.a();
    }
}

