/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import java.util.Vector;
import javax.microedition.lcdui.Graphics;

public final class k {
    public static final int[][] a = new int[][]{{55, 70}, {70, 60}, {40, 157}, {606, 0}, {622, 0}, {626, 0}, {624, 0}, {623, 0}, {626, 0}, {621, 0}, {626, 0}, {622, 0}, {637, 157}, {525, 12}, {7, 355}};
    private int b;
    private int c;
    private int d;
    private int e;
    private int f;
    private String g;
    private int h;
    private long i;
    private int j = 12;
    private static Vector k = new Vector();

    public static void a(int n2, int n3, int n4, int n5, int n6, String string) {
        if (string != null && string.length() > 0) {
            k k2 = new k();
            String string2 = string;
            int n7 = n6;
            n6 = n5;
            n5 = n4;
            n4 = n3;
            n3 = n2;
            k k3 = k2;
            n5 = n5 >> 1 << 1;
            k3.d = n3;
            k3.e = n4;
            k3.b = n5;
            k3.f = n6;
            k3.h = n7;
            k3.g = string2;
            k3.i = bm.m();
            int[] nArray = bf.a(k3.g, k3.b - 15);
            k3.c = nArray[0] * (bz.b(k3.g) + 1) + 4;
            k3.c = k3.c >> 1 << 1;
            k3.j = bm.D.g(194 + k3.f) - 2;
            k3 = k2;
            k.addElement(k3);
            String cfr_ignored_0 = k.size() + " +POPBOX " + k3.g;
        }
    }

    public static void a(Graphics graphics) {
        for (int i2 = 0; i2 < k.size(); ++i2) {
            k k2 = (k)k.elementAt(i2);
            if (k2 != null) {
                if (k2.h < 0) continue;
                Graphics graphics2 = graphics;
                if (k2.g == null) continue;
                if (bm.m() - k2.i > (long)k2.h) {
                    k2.h = -1;
                    continue;
                }
                int[] nArray = bf.a(k2.g, k2.b - 15);
                k k3 = k2;
                Object object = new int[4];
                switch (k3.f) {
                    case 0: {
                        object[0] = k3.d;
                        object[1] = k3.e - k3.j - k3.c;
                        object[2] = 0;
                        break;
                    }
                    case 1: {
                        object[0] = k3.d;
                        object[1] = k3.e;
                        object[2] = k3.j;
                        break;
                    }
                    case 2: {
                        object[0] = k3.d - k3.b;
                        object[1] = k3.e;
                        object[2] = k3.j;
                        break;
                    }
                    case 3: {
                        object[0] = k3.d - k3.b;
                        object[1] = k3.e - k3.j - k3.c;
                        object[2] = 0;
                    }
                }
                int[] nArray2 = object;
                int n2 = nArray2[1] + nArray2[2];
                int n3 = nArray2[0];
                object = graphics2;
                k3 = k2;
                aq.e(Integer.MIN_VALUE);
                aq.b((Graphics)object, n3, n2, k3.b, k3.c);
                object.setColor(16179625);
                object.drawRect(n3 + 1, n2 + 1, k3.b - 3, k3.c - 3);
                n2 = nArray2[1] + nArray2[2];
                n3 = nArray2[0];
                object = graphics2;
                k3 = k2;
                bz.a((Graphics)object, 16179625, -13870883, -1);
                bf.a((Graphics)object, k3.g, nArray, n3 + 4, n2 + 3, 0, k3.c, 0);
                object = graphics2;
                k3 = k2;
                bm.D.a((Graphics)object, 194 + k3.f, k3.d, k3.e, 0);
                continue;
            }
            k.removeElementAt(i2);
        }
    }
}

