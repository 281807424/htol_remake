/*
 * Decompiled with CFR 0.152.
 */
final class cj {
    String a;
    String[] b;
    byte c;
    byte d;
    int e;

    cj(String string, byte by2, int n2) {
        this.a = string;
        this.d = by2;
    }

    cj(String string, String[] stringArray, int n2, int n3) {
        this.a = string;
        this.b = stringArray;
        this.c = (byte)n3;
        this.d = 1;
    }

    final boolean a(byte by2) {
        return by2 == this.d;
    }
}

