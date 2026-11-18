/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

final class bf {
    bf() {
    }

    public static int[] a(String object, int n2) {
        int n3;
        int n4;
        if (object == null) {
            return null;
        }
        int[] nArray = new int[255];
        int n5 = 0;
        byte by2 = bz.a();
        int n6 = -1;
        int n7 = ((String)object).length();
        byte by3 = bz.a();
        if (n7 > 2 && bz.a(((String)object).charAt(0))) {
            n4 = ((String)object).charAt(1);
            by3 = bz.b((char)n4) == -1 ? by3 : bz.b((char)n4);
        }
        n4 = 0;
        int n8 = 0;
        int n9 = 0;
        int n10 = 0;
        int n11 = 0;
        n11 = 0;
        int n12 = 0;
        boolean bl2 = false;
        char c2 = '\u0000';
        boolean bl3 = false;
        for (n3 = 0; n3 < n7; ++n3) {
            char c3;
            c2 = ((String)object).charAt(n3);
            if (bz.a(c2)) {
                if (n3 + 1 > n7) break;
                c3 = ((String)object).charAt(n3 + 1);
                byte by4 = by2 = bz.b(c3) == -1 ? by2 : bz.b(c3);
                if (bz.c(c3) != -1) {
                    n6 = n6 == bz.c(c3) ? -1 : bz.c(c3);
                }
                if (++n3 != n7 - 1) continue;
            }
            n4 = n12 > n4 ? n12 : n4;
            n11 = bz.a(c2, by2);
            n12 = bz.a(by2);
            n8 += n11;
            c3 = '\u0000';
            boolean bl4 = bl2 = c2 == '\n' || c2 == '\r';
            if (c2 == '\\') {
                if (n3 + 1 > n7 - 1) break;
                char c4 = ((String)object).charAt(n3 + 1);
                if (c4 == 'n' || c4 == 'r') {
                    bl2 = true;
                    c3 = '\u0001';
                }
            }
            c2 = n8 > n2 ? (char)'\u0001' : '\u0000';
            boolean bl5 = bl3 = n3 == n7 - 1;
            if (!bl2 && c2 == '\u0000' && !bl3) continue;
            n11 = n3 - 1;
            if (n5 * 5 > nArray.length - 10) {
                int[] nArray2 = new int[nArray.length];
                System.arraycopy(nArray, 0, nArray2, 0, nArray.length);
                nArray = new int[nArray2.length + 50];
                System.arraycopy(nArray2, 0, nArray, 0, nArray2.length);
            }
            nArray[n5 * 5] = n10;
            nArray[n5 * 5 + 1] = n11;
            nArray[n5 * 5 + 2] = by2;
            nArray[n5 * 5 + 3] = n6;
            nArray[n5 * 5 + 4] = n4;
            n9 += n4;
            n8 = 0;
            n4 = 0;
            if (c3 != '\u0000') {
                n3 += 2;
            }
            n10 = n3--;
            if (bl3) {
                int n13 = n5 * 5 + 1;
                nArray[n13] = nArray[n13] + 1;
                break;
            }
            if (c2 != '\u0000') {
                ++n5;
            }
            if (bl2) {
                ++n5;
                continue;
            }
            if (n5 >= 255) break;
        }
        n3 = n5 + 1;
        int[] nArray3 = new int[n3 * 5 + 3];
        object = nArray3;
        nArray3[0] = n3;
        object[1] = n9;
        object[2] = by3;
        System.arraycopy(nArray, 0, object, 3, n3 * 5);
        return object;
    }

    public static void a(Graphics graphics, String string, int[] nArray, int n2, int n3, int n4, int n5, int n6, boolean bl2) {
        bf.a(graphics, string, nArray, n2, n3, n4, n5, n6);
    }

    public static void a(Graphics graphics, String string, int[] nArray, int n2, int n3, int n4, int n5, int n6) {
        if (string == null || string.equals("") || nArray == null) {
            return;
        }
        int n7 = nArray[0];
        if (n5 == -1) {
            n5 = n7 + 1;
        }
        int n8 = n2;
        n2 = n4;
        n5 = n4 + n5 >= n7 ? n7 : n4 + n5;
        n7 = graphics.getColor();
        int n9 = nArray[2];
        n2 = n2 == 0 ? n7 : nArray[3 + (n2 - 1) * 5 + 3];
        n7 = nArray[3 + (n5 - 1) * 5 + 3];
        bz.d.removeAllElements();
        bz.a(graphics, n2);
        bz.b((byte)(n4 == 0 ? n9 : nArray[3 + (n4 - 1) * 5 + 2]));
        bz.c = false;
        while (n4 < n5) {
            String string2 = string.length() == 0 ? "" : string.substring(nArray[3 + n4 * 5], nArray[3 + n4 * 5 + 1] + 1);
            bz.a(graphics, string2, n8, n3, n6);
            n3 += nArray[3 + n4 * 5 + 4];
            bz.b((byte)nArray[3 + n4 * 5 + 2]);
            ++n4;
        }
        bz.c = true;
        bz.a(graphics, n7);
        bz.d.removeAllElements();
        bz.a(graphics, 16179625);
    }
}

