/*
 * Decompiled with CFR 0.152.
 */
import java.util.Hashtable;
import java.util.Vector;

final class v {
    private int a;
    private int b;
    private int c;
    private int d;
    private Hashtable e = new Hashtable();

    v(by by2) {
    }

    final void a(int n2, int n3, int n4, int n5) {
        this.a = n2;
        this.b = n3;
        this.d = n4;
        this.c = n5;
    }

    final int a() {
        return this.a;
    }

    final int b() {
        return this.b;
    }

    final int c() {
        return this.c;
    }

    final int d() {
        return this.d;
    }

    final void a(int n2, int n3, int n4) {
        Integer n5 = new Integer(n2);
        if (!((v)((Object)vector)).e.containsKey(n5)) {
            ((v)((Object)vector)).e.put(n5, new Vector());
        }
        Vector vector = (Vector)((v)((Object)vector)).e.get(n5);
        n5 = new Integer(n3 << 16 | n4);
        vector.addElement(n5);
    }

    final Vector a(int n2) {
        Integer n3 = new Integer(n2);
        if (!this.e.containsKey(n3)) {
            return null;
        }
        Vector vector = (Vector)this.e.get(n3);
        this.e.remove(n3);
        return vector;
    }
}

