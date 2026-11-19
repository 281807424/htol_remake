/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Font
 *  javax.microedition.lcdui.Graphics
 */
import java.util.Vector;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;

public final class bz {
    private static aa e;
    private static Font f;
    private static ao g;
    private static byte h;
    private static String i;
    private static int j;
    static int a;
    static int b;
    static boolean c;
    static Vector d;

    public static void a(Font font, aa aa2, int n2) {
        c = true;
        f = font;
        e = aa2;
        g = ck.b(2);
        bz.d(2);
        h = 0;
        j = f == null ? 0 : f.getHeight();
        a = 19;
        b = g == null ? 0 : g.g(0);
    }

    public static boolean a(char c2) {
        return c2 == '^';
    }

    public static byte b(char c2) {
        int n2 = -1;
        if (c2 == 'x') {
            n2 = 0;
        } else if (c2 == 't') {
            n2 = 1;
        } else if (c2 == 's') {
            n2 = 2;
        }
        return (byte)n2;
    }

    public static int c(char c2) {
        int n2 = -1;
        if (c2 == 'r') {
            n2 = 0xFF0000;
        } else if (c2 == 'g') {
            n2 = 48401;
        } else if (c2 == 'b') {
            n2 = 0x1F88FF;
        }
        return n2;
    }

    private static void d(int n2) {
        switch (n2) {
            case 1: {
                i = " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz";
                return;
            }
            case 2: {
                i = "1234567890.";
                return;
            }
            case 4: {
                i = "1234567890-";
                return;
            }
            case 3: {
                i = "1234567890-+";
            }
        }
    }

    public static int a(Graphics graphics, String string, int n2, int n3, int n4) {
        int n5 = bz.a(string);
        int n6 = bz.b(string);
        if ((n4 & 8) != 0) {
            n2 -= n5;
        } else if ((n4 & 1) != 0) {
            n2 -= n5 >> 1;
        }
        if ((n4 & 0x40) != 0 || (n4 & 0x20) != 0) {
            n3 -= n6;
        } else if ((n4 & 2) != 0) {
            n3 -= n6 >> 1;
        }
        bz.b(graphics, string, n2, n3, n6);
        return n5;
    }

    public static void a(Graphics graphics, String string, int n2, int n3) {
        bz.a(string);
        bz.b(graphics, string, n2, n3, bz.b(string));
    }

    public static int a(char[] cArray, int n2) {
        if (n2 + 1 >= cArray.length) {
            return -1;
        }
        char c2 = cArray[n2 + 1];
        if (cArray[n2] == '%' && c2 >= 'a' && c2 <= 'z') {
            return c2 - 97;
        }
        return -1;
    }

    private static void b(Graphics graphics, String string, int n2, int n3, int n4) {
        if (string == null) {
            return;
        }
        graphics.setFont(f);
        int n5 = n2;
        n3 += n4;
        char[] cArray = string.toCharArray();
        int n6 = cArray.length;
        for (int i2 = 0; i2 < n6; ++i2) {
            int n7;
            int n8 = cArray[i2];
            int n9 = bz.a(cArray, i2);
            if (n9 >= 0) {
                ++i2;
                try {
                    bm.bM.a(graphics, n9, n5, n3, 0);
                    n5 += bm.bM.f(n9);
                }
                catch (Exception exception) {}
                continue;
            }
            if (bz.a((char)n8)) {
                if (i2 + 1 >= string.length()) break;
                n8 = cArray[i2 + 1];
                byte by2 = h = bz.b((char)n8) == -1 ? h : bz.b((char)n8);
                if (n8 == 114) {
                    bz.a(graphics, 0xFF0000);
                } else if (n8 == 103) {
                    bz.a(graphics, 48401);
                } else if (n8 == 98) {
                    bz.a(graphics, 0x1F88FF);
                } else {
                    n9 = n8;
                    if (n9 > 65 && n9 < 90 || n9 - 48 > -1 && n9 - 48 < n.P.length) {
                        n7 = n8;
                        Graphics graphics2 = graphics;
                        if (n7 > 65 && n7 < 90) {
                            bz.a(graphics2, n.P[n7 - 65 + 10]);
                        } else {
                            bz.a(graphics2, n.P[n7 - 48]);
                        }
                    }
                }
                ++i2;
                continue;
            }
            if (n8 == 92) {
                if (!c) continue;
                if (i2 + 1 >= string.length() - 1) break;
                n8 = cArray[i2 + 1];
                if (n8 != 110 && n8 != 114) continue;
                n3 += n4;
                n5 = n2;
                ++i2;
                continue;
            }
            if (n8 == 10) {
                if (!c) continue;
                n3 += n4;
                n5 = n2;
                continue;
            }
            byte by3 = h;
            int n10 = n3;
            int n11 = n5;
            n7 = n8;
            Graphics graphics3 = graphics;
            switch (by3) {
                case 0: {
                    int n12 = 36;
                    graphics3.drawChar((char)n7, n11, n10, 36);
                    break;
                }
                case 1: {
                    int n13 = 36;
                    e.a(graphics3, (char)n7, n11, n10, 36);
                    break;
                }
                case 2: {
                    int n14 = 36;
                    n7 = i.indexOf(n7);
                    if (n7 == -1) break;
                    g.a(graphics3, 0, n7, n11, n10 -= b, 0);
                }
            }
            n5 += bz.a((char)n8, h);
        }
    }

    public static void a(Graphics graphics, int n2) {
        if (n2 == -1) {
            return;
        }
        if (d.size() == 0) {
            d.addElement(new Integer(graphics.getColor()));
            d.addElement(new Integer(n2));
        } else {
            int n3 = (Integer)d.lastElement();
            if (n3 != n2) {
                d.addElement(new Integer(n2));
            } else {
                d.removeElementAt(d.size() - 1);
            }
        }
        graphics.setColor(d.size() == 0 ? 16179625 : (Integer)d.lastElement());
    }

    public static int a(String object) {
        if (object == null) {
            return 0;
        }
        object = ((String)object).toCharArray();
        Object object2 = 0;
        int n2 = ((Object)object).length;
        byte by2 = h;
        for (int i2 = 0; i2 < n2; ++i2) {
            Object object3 = object[i2];
            int n3 = bz.a((char[])object, i2);
            if (bz.a((char)object3)) {
                if (i2 + 1 >= n2) break;
                object3 = object[i2 + 1];
                h = bz.b((char)object3) == -1 ? h : bz.b((char)object3);
                ++i2;
                continue;
            }
            if (n3 >= 0) {
                ++i2;
                try {
                    object2 += bm.bM.f(n3);
                }
                catch (Exception exception) {}
                continue;
            }
            int n4 = object2;
            Object object4 = object3;
            object2 = object4;
            object2 = n4 + bz.a((char)object4, h);
        }
        h = by2;
        return object2;
    }

    public static int a(char c2, byte n2) {
        int n3 = 0;
        switch (n2) {
            case 0: {
                n2 = c2;
                n3 = f.charWidth((char)n2);
                break;
            }
            case 1: {
                int n4;
                n2 = c2;
                if (e == null) {
                    n4 = 0;
                } else {
                    n3 = 0;
                    n3 = n2 >= 48 && n2 <= 57 || n2 >= 65296 && n2 <= 65305 ? bz.e.b >> 1 : (n2 < 913 ? bz.e.b >> 1 : (int)bz.e.b);
                    n4 = n3 = n3;
                }
                if (bm.bL.a == null) break;
                int n5 = bm.bL.a(c2);
                n2 = n5;
                if (n5 <= 0) break;
                n3 = bm.bL.a[n2 /= bm.bL.c];
                break;
            }
            case 2: {
                n2 = c2;
                n3 = i.indexOf(n2);
                n2 = 0;
                if (n3 != -1) {
                    n2 = g == null ? 0 : g.d(n3);
                }
                n3 = n2;
            }
        }
        if (c2 == '\n') {
            n3 = 0;
        }
        return n3;
    }

    public static int b(String string) {
        if (string == null) {
            return 0;
        }
        int n2 = bz.a(h);
        if (string.indexOf("^x") != -1 && string.indexOf("^x") != string.length() - "^x".length()) {
            int n3 = n2 = n2 < j ? j : n2;
        }
        if (string.indexOf("^t") != -1 && string.indexOf("^t") != string.length() - "^t".length()) {
            int n4 = n2 = n2 < a ? a : n2;
        }
        if (string.indexOf("^s") != -1 && string.indexOf("^s") != string.length() - "^s".length()) {
            n2 = n2 < b ? b : n2;
        }
        return n2;
    }

    public static int a(byte by2) {
        int n2 = 0;
        switch (by2) {
            case 0: {
                n2 = j;
                break;
            }
            case 1: {
                n2 = a;
                break;
            }
            case 2: {
                n2 = b;
            }
        }
        return n2;
    }

    public static int a(int n2) {
        return ck.b(3).g(0);
    }

    public static byte a() {
        return h;
    }

    public static boolean b(byte by2) {
        if (by2 >= 0 && by2 < 3) {
            h = by2;
            return true;
        }
        return false;
    }

    public static void b(int n2) {
        g = ck.b(n2);
        bz.d(n2);
        b = g == null ? 0 : g.g(0);
        h = (byte)2;
    }

    public static void a(Graphics graphics, int n2, int n3, int n4) {
        if (e == null) {
            return;
        }
        graphics.setColor(n2);
        bz.e.e = n3;
        bz.e.d = (byte)n4;
        h = 1;
    }

    public static void c(int n2) {
        if (g == null) {
            return;
        }
        g.j(n2);
    }

    static {
        f = bm.m;
        d = new Vector();
    }
}

