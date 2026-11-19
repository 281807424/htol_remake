/*
 * Decompiled with CFR 0.152.
 */
import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

final class by {
    private int a;
    private int b;
    private int c;
    private float d;
    private float e;
    private int f;
    private float g;
    private float h;
    private Hashtable i = new Hashtable();
    private v j = new v(this);

    by() {
    }

    final void a(int n2, float f2, float f3) {
        this.c = n2;
        this.d = f2;
        this.g = f3;
    }

    final void a(float f2, float f3, int n2, int n3, int n4) {
        this.e = f2;
        this.h = f3;
        this.a = n2;
        this.b = n3;
        this.f = n4;
    }

    final void a(int n2, int n3, int n4, int n5) {
        this.j.a(n2, n3, n4, n5);
    }

    final void a(int n2, int n3, int n4) {
        this.j.a(n2, n3, n4);
    }

    final Vector a(int n2) {
        return this.j.a(n2);
    }

    final void b(int n2, int n3, int n4, int n5) {
        Serializable serializable = new Integer(n5);
        if (!((by)((Object)x2)).i.containsKey(serializable)) {
            ((by)((Object)x2)).i.put(serializable, new Vector());
        }
        serializable = (Vector)((by)((Object)x2)).i.get(serializable);
        x x2 = new x((by)((Object)x2), n2, n3, n4);
        ((Vector)serializable).addElement(x2);
    }

    final Hashtable b(int n2) {
        Enumeration enumeration = this.i.keys();
        Vector vector = new Vector();
        while (enumeration.hasMoreElements()) {
            vector.addElement(enumeration.nextElement());
        }
        for (int i2 = 0; i2 < vector.size(); ++i2) {
            if ((Integer)vector.elementAt(i2) <= n2) continue;
            vector.removeElementAt(i2);
            --i2;
        }
        Hashtable<Integer, Vector> hashtable = new Hashtable<Integer, Vector>();
        for (n2 = 0; n2 < vector.size(); ++n2) {
            Vector vector2;
            Integer n3;
            Integer n4 = n3 = (Integer)vector.elementAt(n2);
            Hashtable hashtable2 = this.i;
            if (hashtable2 == null) {
                vector2 = null;
            } else if (hashtable2.isEmpty() || !hashtable2.containsKey(n3)) {
                vector2 = null;
            } else {
                Vector vector3 = (Vector)hashtable2.get(n3);
                hashtable2.remove(n3);
                vector2 = vector3;
            }
            hashtable.put(n4, vector2);
        }
        return hashtable;
    }

    final int a() {
        return this.a;
    }

    final int b() {
        return this.b;
    }

    final float c() {
        return this.d;
    }

    final float d() {
        return this.e;
    }

    final int e() {
        return this.c;
    }

    final int f() {
        return this.f;
    }

    final float g() {
        return this.g;
    }

    final float h() {
        return this.h;
    }

    final int i() {
        return this.j.a();
    }

    final int j() {
        return this.j.b();
    }

    final int k() {
        return this.j.c();
    }

    final int l() {
        return this.j.d();
    }
}

