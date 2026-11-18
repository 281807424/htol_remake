/*
 * Decompiled with CFR 0.152.
 */
import java.util.Vector;

final class ac {
    Vector[] a = new Vector[4];

    ac(y y2, int n2) {
        for (int i2 = 0; i2 < this.a.length; ++i2) {
            this.a[i2] = new Vector();
        }
    }

    final az a(int n2, int n3) {
        return (az)this.a[n2].elementAt(n3);
    }

    final int a(int n2) {
        return this.a[n2].size();
    }

    final boolean a(az az2) {
        for (int i2 = 0; i2 < this.a.length; ++i2) {
            if (!this.a[i2].contains(az2)) continue;
            return true;
        }
        return false;
    }

    final void b(az az2) {
        for (int i2 = 0; i2 < this.a.length; ++i2) {
            this.a[i2].removeElement(az2);
        }
    }
}

