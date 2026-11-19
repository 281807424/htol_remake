/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import java.util.Hashtable;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

final class aa {
    private static Hashtable f = new Hashtable();
    private char[] g;
    int[] a;
    byte b;
    byte c;
    private int[] h;
    private byte[] i;
    byte d;
    int e;

    aa() {
    }

    private static String a(char c2, int n2, int n3, int n4) {
        StringBuffer stringBuffer = new StringBuffer().append(c2);
        int n5 = n2;
        c2 = (char)n5;
        int n6 = n3;
        c2 = (char)n6;
        int n7 = n6 & 0xFFFFFF;
        c2 = (char)n7;
        return stringBuffer.append(Integer.toString(n5 & 0xFFFFFF)).append(Integer.toString(n7 & 0xFFFFFF)).append(Integer.toString(n4)).toString();
    }

    public final void a(byte[] object) {
        Object object2;
        int n2;
        ax ax2 = new ax((byte[])object);
        object = ax2;
        int n3 = ax2.a() == 0 ? 1 : 0;
        this.b = (byte)((ax)object).a();
        int n4 = ((ax)object).b();
        this.g = new char[n4];
        if (n3 != 0) {
            this.a = new int[n4];
        }
        for (n2 = 0; n2 < n4; ++n2) {
            object2 = object;
            this.g[n2] = (char)((((ax)object2).a[((ax)object2).b++] & 0xFF) << 8 | ((ax)object2).a[((ax)object2).b++] & 0xFF);
            if (n3 == 0) continue;
            this.a[n2] = ((ax)object).b();
        }
        n2 = this.b * this.b % 8 == 0 ? 0 : 1;
        this.c = (byte)(this.b * this.b / 8 + n2);
        this.h = new int[this.b * this.b];
        n3 = this.c * n4;
        this.i = new byte[n3];
        boolean bl2 = false;
        byte[] byArray = this.i;
        object2 = object;
        try {
            aq.a(((ax)object2).a, ((ax)object2).b, byArray, 0, n3);
            ((ax)object2).b += n3;
        }
        catch (Exception exception) {}
        object2 = object;
        ((ax)object).b = 0;
        ((ax)object2).a = null;
    }

    public final int a(char c2) {
        int n2 = -1;
        for (int i2 = 0; i2 < this.g.length; ++i2) {
            if (this.g[i2] != c2) continue;
            n2 = i2;
            break;
        }
        if (n2 > 0) {
            return n2 * this.c;
        }
        return n2;
    }

    private void a(int[] nArray, int n2) {
        if ((nArray[n2] & 0xFF000000) == 0) {
            nArray[n2] = this.e;
        }
    }

    public final void a(Graphics graphics, char c2, int n2, int n3, int n4) {
        Image n13;
        if (n2 < 0 || n2 > 640) {
            return;
        }
        if (n3 < 0 || n3 > 360) {
            return;
        }
        int n5 = graphics.getClipX();
        int n6 = graphics.getClipY();
        int n7 = graphics.getClipWidth();
        int n8 = graphics.getClipHeight();
        if (n2 + this.b <= n5) {
            return;
        }
        if (n2 >= n5 + n7) {
            return;
        }
        if (n3 + this.b <= n6) {
            return;
        }
        if (n3 >= n6 + n8) {
            return;
        }
        n5 = n4;
        n4 = this.d;
        n6 = graphics.getColor() | 0xFF000000;
        Object var8_16 = null;
        int n9 = n4;
        int c3 = this.e;
        n8 = n6;
        char c4 = c2;
        String n14 = aa.a(c4, n8, c3, n9);
        Image image2 = n13 = (Image)f.get(n14);
        if (n13 != null) {
            graphics.drawImage(image2, n2, n3, n5);
            return;
        }
        int n10 = this.a(c2);
        if (n10 < 0) {
            n10 = this.a('?');
        }
        try {
            Image image3;
            Object object;
            char c5 = c2;
            c2 = (char)n5;
            int n11 = n6;
            int n12 = n4;
            int n15 = n3;
            int n16 = n2;
            n9 = n10;
            char c6 = c5;
            Graphics graphics2 = graphics;
            aa aa2 = this;
            n2 = 0;
            for (n3 = n9; n3 < n9 + aa2.c; ++n3) {
                n4 = aa2.i[n3];
                for (n5 = 0; n5 < 8; ++n5) {
                    if (n2 + 1 >= aa2.h.length) continue;
                    aa2.h[n2++] = (n4 & 1 << n5) != 0 ? n11 : 0;
                }
            }
            int[] nArray = aa2.h;
            n4 = aa2.b;
            if (n12 > -1) {
                n4 = n12;
                nArray = aa2.h;
                aa aa3 = aa2;
                n6 = aa3.b + 2;
                n9 = aa3.b;
                object = new int[n6 * n6];
                for (n5 = 0; n5 < aa3.b; ++n5) {
                    for (int i2 = 0; i2 < aa3.b; ++i2) {
                        int n17 = n5 * n9 + i2;
                        if (nArray[n17] == 0) continue;
                        object[(n5 + 1) * n6 + i2 + 1] = nArray[n17];
                        if (n4 == 4) {
                            aa3.a((int[])object, n5 * n6 + i2 + 1);
                            aa3.a((int[])object, n5 * n6 + i2 + 1);
                            aa3.a((int[])object, (n5 + 2) * n6 + i2 + 1);
                            aa3.a((int[])object, (n5 + 1) * n6 + i2);
                            aa3.a((int[])object, (n5 + 1) * n6 + i2 + 2);
                            continue;
                        }
                        if (n4 == 0) {
                            aa3.a((int[])object, (n5 + 2) * n6 + i2 + 2);
                            continue;
                        }
                        if (n4 == 1) {
                            aa3.a((int[])object, (n5 + 2) * n6 + i2);
                            continue;
                        }
                        if (n4 == 2) {
                            aa3.a((int[])object, n5 * n6 + i2);
                            continue;
                        }
                        if (n4 != 3) continue;
                        aa3.a((int[])object, n5 * n6 + i2 + 2);
                    }
                }
                nArray = object;
                n4 = aa2.b + 2;
            }
            Image image4 = image3 = Image.createRGBImage((int[])nArray, (int)n4, (int)n4, (boolean)true);
            n6 = aa2.e;
            n4 = n12;
            int n162 = n11;
            char c62 = c6;
            if (f.size() >= 150) {
                f.clear();
            }
            object = aa.a(c62, n162, n6, n4);
            f.put(object, image4);
            graphics2.drawImage(image3, n16, n15, (int)c2);
            return;
        }
        catch (Exception exception) {
            return;
        }
    }
}

