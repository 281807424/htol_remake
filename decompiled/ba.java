/*
 * Decompiled with CFR 0.152.
 */
import java.util.Hashtable;

final class ba {
    private Hashtable a = new Hashtable();

    public final void a(int n2, int n3, int n4) {
        int[] nArray = new int[]{n3, n3, n4};
        Integer n5 = new Integer(n2);
        if (this.d(n2)) {
            this.a.remove(n5);
        }
        this.a.put(n5, nArray);
    }

    private int[] e(int n2) {
        if (!((ba)object).d(n2)) {
            return null;
        }
        int[] nArray = (int[])((ba)object).a.get(new Integer(n2));
        Object object = nArray;
        return nArray;
    }

    public final int a(int n2) {
        if (this.e(n2) == null) {
            return -1;
        }
        return this.e(n2)[0];
    }

    public final int b(int n2) {
        if (this.e(n2) == null) {
            return -1;
        }
        return this.e(n2)[1];
    }

    public final void a(int n2, int n3) {
        if (!this.d(n2)) {
            return;
        }
        int[] nArray = this.e(n2);
        int[] nArray2 = nArray;
        nArray[2] = n3;
        this.a.put(new Integer(n2), nArray2);
    }

    public final int c(int n2) {
        int n3 = -1;
        if (this.d(n2)) {
            int[] nArray = this.e(n2);
            int n4 = n3 = nArray[1] + nArray[2];
            int n5 = n2;
            ba ba2 = this;
            int[] nArray2 = new int[]{n4, n4, 0};
            Integer n6 = new Integer(n5);
            if (ba2.d(n5)) {
                nArray2 = (int[])ba2.a.get(n6);
                ba2.a.remove(n6);
            }
            nArray2[1] = n4;
            ba2.a.put(n6, nArray2);
            if (n3 <= 0 && (ba2 = this).d(n5 = n2)) {
                ba2.a.remove(new Integer(n5));
            }
        }
        return n3;
    }

    public final boolean d(int n2) {
        return this.a.containsKey(new Integer(n2));
    }
}

