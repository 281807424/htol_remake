/*
 * Decompiled with CFR 0.152.
 */
final class ar {
    private boolean a;
    private int b;
    private int c;
    private int[] d;

    public ar(ad ad2, int n2, int n3, int n4, int n5, int n6, int n7) {
        this.b = n2;
        this.c = n3;
        this.d = new int[4];
        this.d[0] = n4;
        this.d[1] = n5;
        this.d[2] = n6;
        this.d[3] = n7;
        this.a = true;
    }

    final int[] a() {
        int[] nArray = new int[this.d.length];
        System.arraycopy(this.d, 0, nArray, 0, this.d.length);
        return nArray;
    }

    final int b() {
        return this.b;
    }

    final int c() {
        return this.c;
    }

    final boolean d() {
        return this.a;
    }

    final void a(boolean bl2) {
        this.a = false;
    }
}

