/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;
import javax.microedition.lcdui.Graphics;

public final class ay {
    private static boolean h = false;
    private static long i = 0L;
    static int a;
    public static boolean b;
    private static int[] j;
    private static int k;
    public static int c;
    private static int l;
    public static String d;
    public static String[] e;
    static int f;
    public static Hashtable g;

    public static void a(Graphics graphics, int n2, int n3, int n4, int n5, int n6) {
        n6 = 128 << 24;
        aq.e(n6);
        aq.b(graphics, n2, n3, n4, n5);
    }

    public static void a(Graphics graphics, boolean bl2, int n2, int n3, String string) {
        ay.a(graphics, true, n2, n3, string, false);
    }

    /*
     * WARNING - void declaration
     */
    public static void a(Graphics graphics, boolean bl2, int n2, int n3, String string, boolean bl3) {
        void var3_4;
        void var2_3;
        ao ao2;
        if (bl2) {
            if (ck.b(13) == null) {
                graphics.setColor(0x200900);
                graphics.fillRect(0, 0, 640, 360);
            } else {
                ck.b(13).a(graphics, 0, 0, 0, 0);
            }
            aq.e(-939524096);
            aq.b(graphics, 0, 0, 640, 360);
        }
        if (am.a() == 5 && (ao2 = ck.b(277 + am.d + 3 * am.c)) != null) {
            ao2.a(0, 0, 0, 0);
        }
        bm.D.a(graphics, 0, 0, 0, 0);
        if (bl2) {
            void var4_5;
            void var5_6;
            if (var5_6 != false) {
                ay.b(graphics, (String)var4_5, 320, 7, -5041, -6924519);
            } else {
                bz.a(graphics, -6924519, -6924519, 4);
                bz.a(graphics, (String)var4_5, 319, 7, 17);
                bz.a(graphics, (String)var4_5, 320, 7, 17);
                bz.a(graphics, -5041, -6924519, -1);
                bz.a(graphics, (String)var4_5, 320, 7, 17);
                bz.a(graphics, (String)var4_5, 321, 7, 17);
            }
        }
        void v0 = var2_3;
        var2_3 = var3_4;
        void var1_2 = v0;
        ay.a(graphics, (int)var1_2, (int)var2_3, false);
    }

    public static void a(Graphics graphics, int n2, int n3) {
        ay.a(graphics, n2, n3, false);
    }

    public static void a(Graphics graphics, int n2, int n3, boolean bl2) {
        bm.dD = n2;
        bm.dE = n3;
        if (bm.n(31) || bm.n(31) || bm.n(35) || bm.n(30) || bm.n(7) || bm.n(9) || bm.n(10) || bm.n(23) || bm.n(24) || bm.n(20) || bm.n(29) || bm.n(26) || bm.n(27) || bm.n(72) || bm.n(33) || bm.n(34) || bm.n(36) || bm.n(45) || bm.n(46) || bm.n(68) || bm.n(69) || bm.n(71) || bm.n(74) || bm.n(73) || bm.n(77) || bm.n(17)) {
            return;
        }
        if (bm.ab()) {
            n2 = bm.Z() != 4 ? 2 : -1;
            n3 = bm.Z() != 7 && bm.Z() != 8 && bm.Z() != 9 ? -1 : 39;
        } else if (bm.Y()) {
            n2 = 2;
            n3 = 3;
        } else if (bm.ac()) {
            switch (bm.af()) {
                case 3: {
                    n2 = -1;
                    n3 = 39;
                    break;
                }
                case 1: {
                    n2 = -1;
                    n3 = 39;
                }
            }
        }
        bz.a(graphics, 16249567, -11394001, 4);
        bm.D.a(graphics, 20, 0, 360 - bm.D.g(20), 16);
        if (n2 != -1) {
            bz.a(graphics, bm.d(0, n2), bm.D.f(20) / 2, 360 - bm.D.g(20) / 2, 3);
        }
        bm.D.a(graphics, 20, 640, 360 - bm.D.g(20), 1);
        if (n3 != -1) {
            bz.a(graphics, bm.d(0, n3), 640 - bm.D.f(20) / 2, 360 - bm.D.g(20) / 2, 3);
        }
    }

    public static void a() {
        a = -30;
    }

    public static void a(Graphics graphics, String string, int[] nArray, int n2, int n3, int n4, int n5, int n6, int n7, int n8, boolean bl2) {
        int n9 = n7;
        int n10 = n8;
        boolean bl3 = true;
        n8 = 0;
        n7 = n10;
        n6 = n9;
        Graphics graphics2 = graphics;
        int n11 = graphics2.getClipX();
        int n12 = graphics2.getClipY();
        int n13 = graphics2.getClipWidth();
        int n14 = graphics2.getClipHeight();
        int[] nArray2 = new int[]{n2, n3, n2 + n4, n3 + n5};
        int[] nArray3 = new int[]{n11, n12, n11 + n13, n12 + n14};
        if ((nArray2 = aq.b(nArray2, nArray3))[2] > nArray2[0] && nArray2[3] > nArray2[1]) {
            graphics2.setClip(nArray2[0], nArray2[1], nArray2[2] - nArray2[0], nArray2[3] - nArray2[1]);
        } else {
            graphics2.setClip(0, 0, 0, 0);
        }
        if (nArray[0] > n6) {
            if (!b) {
                a = a >= (nArray[0] - 1) * 14 ? -n5 : a + 2;
                b = true;
            }
            n3 -= a;
        }
        bf.a(graphics2, string, nArray, n2, n3, 0, -1, n7, n8 != 0);
        graphics2.setClip(n11, n12, n13, n14);
    }

    public static void a(Graphics graphics, String string, int[] nArray, int n2, int n3, int n4, int n5, int n6, int n7) {
        n7 = 0;
        graphics.setClip(n2 - 1, n3 - 1, n4 + 2, n5 + 2);
        if (nArray[0] > n6) {
            a = a >= (nArray[0] - 1) * bm.m.getHeight() ? -n5 : a + 2;
            n3 -= a;
        }
        graphics.setFont(bm.m);
        for (n4 = 0; n4 < nArray[0]; ++n4) {
            graphics.drawSubstring(string, n7, nArray[(n4 << 1) + 1], n2, n3 + n4 * bm.m.getHeight(), 0);
            n7 += nArray[(n4 << 1) + 1];
        }
        graphics.setClip(0, 0, 640, 360);
    }

    public static int[] a(String string, int n2) {
        int n3 = string.length();
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        int n7 = 0;
        int n8 = 1;
        for (int i2 = 0; i2 < n3; ++i2) {
            n6 = n5;
            if ((n5 += bm.m.substringWidth(string, i2, 1)) <= n2) continue;
            ay.j[n8++] = i2 - n7;
            ay.j[n8++] = n6;
            n5 = 0;
            n7 = i2--;
            ++n4;
        }
        if (n5 != 0) {
            if (n4 > 0) {
                ay.j[n8++] = n3 - n7;
                ay.j[n8++] = n5;
            } else {
                ay.j[n8++] = n3;
                ay.j[n8++] = n5;
            }
        }
        ay.j[0] = n8 >> 1;
        return j;
    }

    public static void a(Graphics graphics, String[] stringArray, int n2, int n3, int n4, int n5, boolean bl2) {
        ay.a(graphics, stringArray, n2, n3, n4, n5, false, false, bm.bF);
    }

    /*
     * WARNING - void declaration
     */
    public static void a(Graphics graphics, String[] stringArray, int n2, int n3, int n4, int n5, boolean bl2, boolean bl3, int n6) {
        void var8_9;
        if (stringArray == null) {
            return;
        }
        graphics.setClip(0, 0, 640, 360);
        int n7 = 0;
        String string = null;
        if (!bl3) {
            stringArray[stringArray.length - 1] = bm.A(262);
        }
        int n8 = n4;
        n7 = 0;
        int n9 = 0;
        if (ci.a()) {
            n7 = 0;
            n9 = n2;
        } else {
            n7 = 11;
            n9 = n2;
        }
        if (bl3 || stringArray.length > 3) {
            ay.a(graphics, n9 + (n8 -= n7), n3 + 2, n7, n5 * 3, (int)var8_9, 3, stringArray.length);
        }
        n7 = var8_9 - 2 < 0 ? 0 : (var8_9 > stringArray.length - 3 ? stringArray.length - 3 : var8_9 - true);
        int n10 = 0;
        while (n10 < 3) {
            if (n7 == var8_9) {
                ay.a(graphics, n9, n3 + n10 * 30 + 2, n8, n5);
            }
            bz.a(graphics, -597591, -13032703, 4);
            if (bl2 && bm.am[n7] > 0 && bm.am[n7] < 3000) {
                int n11;
                string = (bm.an[n7] & 0x40) == 64 ? bm.A(1291) : bm.A(1292);
                switch (bm.an[n7] & 0x7F) {
                    case 0: {
                        bm.D.j(0);
                        n11 = 37;
                        break;
                    }
                    case 1: {
                        bm.D.j(0);
                        n11 = 36;
                        break;
                    }
                    case 2: {
                        bm.D.j(1);
                        n11 = 37;
                        break;
                    }
                    case 4: {
                        bm.D.j(1);
                        n11 = 36;
                        break;
                    }
                    default: {
                        bm.D.j(1);
                        n11 = 36;
                    }
                }
                bm.D.a(graphics, n11, -10, n3 + n10 * 30 + 7, 0);
                bm.D.j(0);
            } else {
                string = "";
            }
            if (n7 >= 0 && n7 < stringArray.length && stringArray[n7] != null) {
                string = stringArray[n7] + string;
            }
            if (var8_9 == n7) {
                ay.a(graphics, string, n9, n3 + n10 * 30 + 7, n4 - 10, 25, 2, (byte)1, true, 0);
            } else {
                ay.a(graphics, string, n9, n3 + n10 * 30 + 7, n4 - 6, 25, 2, (byte)1, false, 0);
            }
            bm.b(graphics, n2, n3, n5, n8, stringArray.length);
            ++n10;
            ++n7;
        }
    }

    public static void a(Graphics graphics, String string, int n2, int n3, int n4, int n5) {
        ay.a(graphics, string, n2, n3, n4, 3, true);
    }

    public static void a(Graphics graphics, String string, int n2, int n3, int n4, int n5, boolean bl2) {
        int n6 = 0;
        bz.b(3);
        if (n4 == 0) {
            n6 = 4;
            bm.D.a(graphics, 107, n2 - 45, n3 - c - 2, 8);
        } else if (n4 == 5) {
            n6 = 4;
        } else {
            if (n4 == 4) {
                bz.b(4);
            }
            n6 = n5;
        }
        if (bl2) {
            bz.b((byte)2);
            bz.a(graphics, string, n2, n3 - c, n6);
        } else {
            bz.b((byte)1);
            bz.a(graphics, string, n2, n3 - c, n6);
        }
        bz.b((byte)1);
        if (c < l) {
            c += k;
            return;
        }
        c = 0;
    }

    public static void a(Graphics graphics, int n2, int n3, int n4, int n5, int n6, int n7, int[] nArray) {
        n5 = n5 >> 1 << 1;
        aq.a(graphics);
        graphics.setColor(n6);
        graphics.drawRect(n2, n3, n4, n5);
        graphics.setColor(2956290);
        graphics.drawRect(n2 - 1, n3 - 1, n4 + 2, n5 + 2);
        graphics.drawRect(n2 + 1, n3 + 1, n4 - 2, n5 - 2);
        if (nArray != null) {
            for (n5 = 0; n5 < nArray.length; ++n5) {
                int n8 = n4 - 4;
                int n9 = n2 + 1;
                aq.a(n9, n3 + nArray[n5], n8 >> 1, 0, n6, n7, 8);
                aq.a(n9 + (n8 >> 1), n3 + nArray[n5], (n8 >> 1) + 3, 0, n6, n7, 4);
                graphics.setColor(2956290);
                graphics.drawLine(n9, n3 + nArray[n5] - 1, n9 + n8 + 1, n3 + nArray[n5] - 1);
                graphics.drawLine(n9, n3 + nArray[n5] + 1, n9 + n8 + 1, n3 + nArray[n5] + 1);
            }
        }
        aq.a(bm.bi);
    }

    public static void a(Graphics graphics, String[] stringArray, int n2, int n3, int n4, boolean n5, boolean n6) {
        n6 = stringArray.length;
        int n7 = n6 * 28 + 4;
        if (n3 < 128) {
            n3 = 128;
        }
        int n8 = 640 - n3 >> 1;
        int n9 = 360 - n7 >> 1;
        ay.a(graphics, n8, n9 += n2, n3, n7, false, "", true);
        if (n5 != 0) {
            ay.d(graphics, n8, n9, n3, 28, n6);
        }
        n2 = n3 - 4;
        ay.a(graphics, 640 - n2 >> 1, n9 + 3 + n4 * 28, n2, 26);
        bz.a(graphics, 0, -13870883, -1);
        for (n5 = 0; n5 < n6; ++n5) {
            bz.a(graphics, -597591, -13032703, 4);
            if (n4 == n5) {
                ay.a(graphics, stringArray[n5], 320, n9 + 7 + n5 * 28, n3, n7, 2, (byte)1, true, 1);
                continue;
            }
            ay.a(graphics, stringArray[n5], 320, n9 + 7 + n5 * 28, n3, n7, 2, (byte)1, false, 1);
        }
        bm.g(640 - n2 >> 1, n9 + 3, n2, n7);
        bm.h(n8, n9, n3, n7);
    }

    public static void a(Graphics graphics, int n2, int[] nArray, int n3, int n4, boolean bl2, boolean bl3, boolean bl4) {
        n3 = nArray.length;
        String[] stringArray = new String[n3];
        for (int i2 = 0; i2 < n3; ++i2) {
            stringArray[i2] = bm.d(n2, nArray[i2]);
            if (!bl4) continue;
            stringArray[i2] = i2 == n3 - 1 ? "0." + stringArray[i2] : i2 + 1 + "." + stringArray[i2];
        }
        ay.a(graphics, stringArray, 180, n4, bl2, true);
    }

    public static void a(Graphics graphics, String[] stringArray, int n2, int n3, boolean bl2, boolean bl3) {
        ay.a(graphics, stringArray, 0, n2, n3, bl2, false);
    }

    private static void a(Graphics graphics, String string, String[] stringArray, int n2, int n3, boolean bl2, String string2, boolean bl3, boolean bl4) {
        ay.a(graphics, string, stringArray, n2, n3, bl2, string2, bl3, true, false, false, false);
    }

    /*
     * WARNING - void declaration
     */
    public static void a(Graphics graphics, n n2, String string, String[] stringArray, int n3, String[] stringArray2, int n4, int n5, boolean bl2, String object, boolean n6, boolean bl3, boolean bl4, boolean bl5, boolean bl6) {
        void var14_20;
        void var13_19;
        int n7;
        void var12_18;
        int n8;
        n6 = stringArray.length;
        boolean bl7 = stringArray != null && n6 > 0;
        boolean bl8 = stringArray2 != null && stringArray2.length > 0;
        int n9 = 0;
        int n10 = 0;
        int n11 = 0;
        if (bl7) {
            for (n8 = 0; n8 < n6; ++n8) {
                if (stringArray[n8] == null) continue;
                ++n11;
            }
            n9 = n11 * 40;
        }
        if (stringArray2 != null && stringArray2.length > 0) {
            n9 = (n6 + 1) * 40;
        }
        if (string != null && string.trim() != "") {
            n8 = 5 + bf.a(string, n5 - 20)[0] * (bz.b(string) + 1);
            n10 = n8 + n9 + (bl2 ? 1 : 0) * 40 + 5;
        } else {
            n10 = n9 + (3 + (bl2 ? 1 : 0)) * 40;
        }
        if (n2 != null) {
            n10 += 25;
        }
        if (n5 < 128) {
            n5 = 128;
        }
        if (n10 < 72) {
            n10 = 72;
        }
        n8 = 640 - n5 >> 1;
        n9 = 360 - n10 >> 1;
        int n12 = n5 - 4;
        ay.a(graphics, n8, n9, n5, n10, bl2, (String)object, bl3, null, (byte)1);
        if (n2 != null) {
            int n13 = 10 + (640 - n12) >> 1;
            int n14 = n9 + 15 + 20;
            n.a(graphics, n13, n14, n2.j, n2.l, 1, false, false, -1);
            String string2 = "^" + n2.E + n2.e + "^" + n2.E;
            if (n2.A > 0) {
                string2 = string2 + "+" + n2.A;
            }
            String string3 = bm.d(0, bj.Q[n2.z]);
            String string4 = n2.k + bm.A(370);
            if (n2.g == 1) {
                if (!n2.f()) {
                    string2 = n2.z != bm.cb.u && n2.z != 3 ? string2 + " ^r" + string3 + "^r" : string2 + " " + string3;
                }
                string2 = n2.k > bm.cb.U && !n2.f() ? string2 + "^r" + string4 + "^r" : string2 + "" + string4;
            }
            string2 = string2.trim();
            bz.a(graphics, 16179625, 0, -1);
            ay.a(graphics, string2, n13, n14, n5, 21, 2, (byte)0, true, 0);
        }
        if (string != null && string.trim() != "") {
            void var11_16;
            bz.a(graphics, 16179625, -13870883, -1);
            object = bf.a(string, n5 - 20);
            int n15 = n9 + 15 + 20 - (!bl2 && object[0] > true ? 20 : 0);
            if (n2 != null) {
                var11_16 += 25;
            }
            bf.a(graphics, string, (int[])object, 10 + (640 - n12) >> 1, (int)var11_16, 0, n10, 20);
        }
        if (bl7) {
            if (var12_18 == false) {
                ay.d(graphics, n8, n9 + n10 - n11 * 40, n5, 40, n6);
            }
            ay.a(graphics, 640 - n12 >> 1, n9 + n10 - n11 * 40 + n3 * 40, n12, 40);
        }
        bz.a(graphics, -597591, -13032703, 4);
        for (n7 = 0; n7 < n6; ++n7) {
            if (var12_18 == false) {
                bz.a(graphics, stringArray[n7], 320, n9 + n10 - n11 * 40 + 20 + n7 * 40, 3);
                continue;
            }
            if (n3 == n7) {
                ay.a(graphics, stringArray[n7], 10 + (640 - n12) >> 1, n9 + n10 - n11 * 40 + 20 + n7 * 40 - (bz.b(stringArray[n7]) >> 1), n5, n10, 2, (byte)1, true, 0);
                continue;
            }
            ay.a(graphics, stringArray[n7], 10 + (640 - n12) >> 1, n9 + n10 - n11 * 40 + 20 + n7 * 40 - (bz.b(stringArray[n7]) >> 1), n5, n10, 2, (byte)1, false, 0);
        }
        n7 = n9 + n10 - (n11 + 1) * 40 + 20 - 5;
        if (bl8) {
            bz.a(graphics, -597591, -13032703, 4);
            int n16 = (n4 - 1 + stringArray2.length) % stringArray2.length;
            int n17 = (n4 + 1 + stringArray2.length) % stringArray2.length;
            bz.a(graphics, stringArray2[n4], 320, n7, 3);
            graphics.setColor(7035466);
            bz.a(graphics, stringArray2[n16], 320 - ((bz.a(stringArray2[n16]) >> 1) + bz.a), n7, 10);
            graphics.setColor(7035466);
            bz.a(graphics, stringArray2[n17], 320 + ((bz.a(stringArray2[n16]) >> 1) + bz.a), n7, 6);
            bm.E.c();
            bm.E.a(graphics, 140, n7 - 20 + 3, 0);
            bm.H.c();
            bm.H.a(graphics, 466, n7 - 20 + 3, 0);
        }
        if (var13_19 == true || var14_20 == true) {
            bm.G.c();
            if (var13_19 != false) {
                bm.G.a(graphics, 310, n9 + n10 - n11 * 40, 2);
            }
            if (var14_20 != false) {
                bm.G.a(graphics, 310, n9 + n10 - n11 * 40 + stringArray.length * 40, 0);
            }
        }
        bm.g(640 - n12 >> 1, n9 + n10 - n11 * 40, n12, n6 * 40);
        bm.h(n8, n9, n5, n10);
        bm.a(bl8, n7 - 20 + 3, n7 - 20 + 3);
    }

    public static void a(Graphics graphics, String string, String[] stringArray, int n2, int n3, boolean bl2, String string2, boolean bl3, boolean bl4, boolean bl5, boolean bl6, boolean bl7) {
        ay.a(graphics, null, string, stringArray, n2, null, 0, n3, bl2, string2, bl3, bl4, bl5, bl6, bl7);
    }

    public static void a(Graphics graphics, String string, String[] stringArray, int n2, int n3, boolean bl2, String string2, boolean bl3) {
        ay.a(graphics, string, stringArray, n2, n3, bl2, string2, bl3, true);
    }

    public static void a(Graphics graphics, String string, int[] nArray, int n2, int n3, boolean bl2, String string2, boolean bl3) {
        ay.b(graphics, string, nArray, n2, n3, true, string2, true);
    }

    public static void b(Graphics graphics, String string, int[] nArray, int n2, int n3, boolean bl2, String string2, boolean bl3) {
        int n4 = nArray.length;
        String[] stringArray = new String[n4];
        for (int i2 = 0; i2 < n4; ++i2) {
            stringArray[i2] = bm.d(0, nArray[i2]);
        }
        ay.a(graphics, string, stringArray, n2, n3, bl2, string2, bl3, true);
    }

    public static void a(Graphics graphics, int n2, int n3, int n4, int n5, boolean bl2, String string, boolean bl3, int[] nArray, byte by2) {
        if (bl3) {
            aq.e(-939524096);
            aq.b(graphics, n2, n3, n4, n5);
        } else {
            graphics.setColor(0);
            graphics.fillRect(n2, n3, n4, n5);
        }
        graphics.setClip(0, 0, 640, 360);
        ay.a(graphics, n2, n3, n4, n5, 8941627, 14600340, nArray);
        if (bl2) {
            ay.b(graphics, n2 + 1, n3 + 1, n4 - 2, 32, string);
        }
        bm.h(n2, n3, n4, n5);
    }

    public static void a(Graphics graphics, int n2, int n3, int n4, int n5, boolean bl2, String string, boolean bl3) {
        ay.a(graphics, n2, n3, n4, n5, bl2, string, bl3, true);
    }

    public static void a(Graphics graphics, int n2, int n3, int n4, int n5, boolean bl2, String string, boolean bl3, boolean bl4) {
        ay.a(graphics, n2, n3, n4, n5, bl2, string, bl4, null, (byte)0);
    }

    public static void a(Graphics graphics, String string, int n2, int n3, boolean bl2, String string2, boolean bl3) {
        if (n2 < 128) {
            n2 = 128;
        }
        if (n3 < 72) {
            n3 = 72;
        }
        int n4 = 640 - n2 >> 1;
        int n5 = 360 - n3 >> 1;
        ay.a(graphics, n4, n5, n2, n3, bl2, string2, true, null, (byte)1);
        bz.a(graphics, 16179625, -13870883, -1);
        if (bl2) {
            bf.a(graphics, string, bf.a(string, n2 - 28), 320, n5 + 45, 0, n3, 1, false);
            return;
        }
        bf.a(graphics, string, bf.a(string, n2 - 28), 320, 180, 0, n3, 3, false);
    }

    public static void a(Graphics graphics, int n2, int n3, int n4, int n5, String string) {
        ay.b(graphics, n2, n3, n4, n5, string);
    }

    private static void b(Graphics graphics, int n2, int n3, int n4, int n5, String string) {
        ay.c(graphics, n2, n3, n4, n5, 6113836, 9142619);
        bm.D.a(graphics, 167, n2, n3 + 1, 0);
        bm.D.a(graphics, 167, n2 + n4, n3 + 1, 1);
        graphics.setClip(0, 0, 640, 360);
        bz.a(graphics, -597591, -13032703, 4);
        ay.a(graphics, string, n2 + (n4 >> 1), n3 + (n5 >> 1), n4, n5, 1, (byte)1, true, 3);
    }

    private static void d(Graphics graphics, int n2, int n3, int n4, int n5, int n6) {
        graphics.setColor(7035466);
        for (int i2 = 1; i2 < n6; ++i2) {
            graphics.drawLine(n2 + 3, n3 + 2 + i2 * n5, n2 + n4 - 3, n3 + 2 + i2 * n5);
        }
    }

    public static void a(Graphics graphics, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9, int[] nArray) {
        int n10 = nArray.length;
        int n11 = 0;
        int n12 = n2 + n8;
        int n13 = 0;
        n11 = (n4 - n8) / nArray.length - n7;
        for (n8 = 0; n8 < n10; ++n8) {
            bz.a(bm.d(0, nArray[n8]));
            if (n8 == n9) {
                graphics.setColor(16702595);
                graphics.drawLine(n12 + n13, n3, n12 + n13 + n11, n3);
                graphics.drawLine(n12 + n13, n3, n12 + n13, n3 + n6);
                graphics.drawLine(n12 + n13 + n11, n3, n12 + n13 + n11, n3 + n6);
                graphics.drawLine(n2, n3 + n6, n12 + n13, n3 + n6);
                graphics.drawLine(n12 + n13 + n11, n3 + n6, n2 + n4, n3 + n6);
                graphics.drawLine(n2, n3 + n6 + n5, n2 + n4, n3 + n6 + n5);
                graphics.drawLine(n2, n3 + n6, n2, n3 + n6 + n5);
                graphics.drawLine(n2 + n4, n3 + n6, n2 + n4, n3 + n6 + n5);
                bz.a(graphics, -9080, -13032703, 4);
            } else {
                aq.a(graphics);
                aq.a(n12 + n13, n3 + 2, n11, n6 - 2, 1512467, 2953219, 16);
                aq.a(bm.bi);
                graphics.setColor(6968095);
                graphics.drawLine(n12 + n13, n3 + 1, n12 + n13 + n11, n3 + 1);
                graphics.drawLine(n12 + n13, n3 + 1, n12 + n13, n3 + n6 - 1);
                graphics.drawLine(n12 + n13 + n11, n3 + 1, n12 + n13 + n11, n3 + n6 - 1);
                bz.a(bm.W, 6902561, -13032703, -1);
            }
            ay.a(graphics, bm.d(0, nArray[n8]), n12 + n13 + (n11 >> 1), n3, n11, n6 - 5, 2, 1);
            n13 += n7 + n11;
        }
    }

    public static void a(Graphics graphics, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9, int n10, String[] stringArray) {
        n2 = 0;
        n3 = 0;
        for (n4 = 0; n4 < n10; ++n4) {
            n2 = bz.a(stringArray[n4]) + 5;
            if (n4 == n9) {
                graphics.setColor(16702595);
                graphics.drawLine(n3 + 188, 190, n3 + 188 + n2, 190);
                graphics.drawLine(n3 + 188, 190, n3 + 188, 218);
                graphics.drawLine(n3 + 188 + n2, 190, n3 + 188 + n2, 218);
                graphics.drawLine(168, 218, n3 + 188, 218);
                graphics.drawLine(n3 + 188 + n2, 218, 473, 218);
                graphics.drawLine(168, 310, 473, 310);
                graphics.drawLine(168, 218, 168, 310);
                graphics.drawLine(473, 218, 473, 310);
                bz.a(graphics, -9080, -13032703, 4);
            } else {
                aq.a(graphics);
                aq.a(n3 + 188, 192, n2, 26, 1512467, 2953219, 16);
                aq.a(bm.bi);
                graphics.setColor(6968095);
                graphics.drawLine(n3 + 188, 191, n3 + 188 + n2, 191);
                graphics.drawLine(n3 + 188, 191, n3 + 188, 217);
                graphics.drawLine(n3 + 188 + n2, 191, n3 + 188 + n2, 217);
                bz.a(bm.W, 6902561, -13032703, -1);
            }
            bz.a(graphics, stringArray[n4], n3 + 188 + 2, 195, 0);
            n3 += n2 + 15;
        }
    }

    public static void a(Graphics graphics, int n2, int n3, int n4, int n5, int n6, int n7) {
        graphics.setColor(n6);
        graphics.fillRect(n2, n3, n4, n5);
        graphics.setColor(n7);
        graphics.drawRect(n2, n3, n4, n5);
    }

    public static void a(Graphics graphics, int n2, int n3, int n4) {
        if (n4 == -1) {
            return;
        }
        n3 = n2 * 28 + 4;
        if (n3 < 72) {
            n3 = 72;
        }
        n3 = 360 - n3 >> 1;
        if (n2 == 2) {
            n3 += 27;
        }
        ay.b(graphics, 254, n3 + 3 + n4 * 28, 132, 26, 0);
    }

    public static void b(Graphics graphics, int n2, int n3, int n4, int n5, int n6) {
        int n7 = graphics.getColor();
        if (n6 != -1) {
            bm.dk.c();
            bm.dk.a(graphics, n2, n3 - 15, 0);
        } else {
            ay.c(graphics, n2, n3, n4, n5, 6113836, 9142619);
        }
        graphics.setColor(n7);
    }

    public static void a(Graphics graphics, int n2, int n3, int n4, int n5) {
        ay.b(graphics, n2, n3, n4, n5, -1);
    }

    public static void a(Graphics graphics, int n2, int n3, int n4, int n5, boolean bl2) {
        if (bl2) {
            ay.a(graphics, n2, n3, n4, n5);
            return;
        }
        ay.b(graphics, n2, n3, n4, n5);
    }

    public static void b(Graphics graphics, int n2, int n3, int n4, int n5) {
        int n6 = graphics.getColor();
        graphics.setColor(65535);
        graphics.drawRect(n2, n3, n4, n5);
        graphics.setColor(n6);
    }

    public static void a(Graphics graphics, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
        ay.a(graphics, n2, n3, n4, n5, n6, n7, n8, false);
    }

    public static void a(Graphics graphics, int n2, int n3, int n4, int n5, int n6, int n7, int n8, boolean bl2) {
        if (n7 >= n8) {
            return;
        }
        n7 = graphics.getClipX();
        int n9 = graphics.getClipY();
        int n10 = graphics.getClipWidth();
        int n11 = graphics.getClipHeight();
        graphics.setClip(n2, n3, n4, n5);
        graphics.setColor(2956290);
        graphics.drawRect(n2, n3, n4, n5);
        graphics.setColor(8941627);
        graphics.drawRect(n2 + 1, n3 + 1, n4 - 2, n5 - 2);
        graphics.setColor(2956290);
        graphics.drawRect(n2 + 2, n3 + 2, n4 - 4, n5 - 4);
        n4 = 0;
        if (n8 > 1) {
            n4 = (n5 - 15) * n6 / (n8 - 1);
        }
        graphics.setClip(0, 0, 640, 360);
        if (bl2) {
            bm.D.j(1);
        } else {
            bm.D.j(0);
        }
        bm.D.a(graphics, 32, n2, n3 + n4, 0);
        bm.D.j(0);
        graphics.setClip(n7, n9, n10, n11);
    }

    public static void a(Graphics graphics) {
        if (ci.a()) {
            return;
        }
        if (bm.x() != 66) {
            bm.D.a(graphics, 51, 65, 0, 0);
            return;
        }
        bm.D.a(graphics, 51, 0, 0, 0);
    }

    public static void a(Graphics graphics, int n2, int n3, int n4, int n5, int n6, boolean bl2, boolean bl3) {
        ay.a(graphics, n2, n3, n4, n5, n6, true, bl3, true);
    }

    public static void a(Graphics graphics, int n2, int n3, int n4, int n5, int n6, boolean bl2, boolean bl3, boolean bl4) {
        String string = "";
        if (n3 >= 0 && n3 < e.length) {
            string = e[n3];
        }
        ay.a(graphics, n2, n3 + 21, string, n4, n5, n6, bl2, bl3, bl4);
    }

    /*
     * WARNING - void declaration
     */
    private static void a(Graphics graphics, int n2, int n3, String string, int n4, int n5, int n6, boolean bl2, boolean bl3, boolean bl4) {
        int n7 = bm.D.f(21) + 5;
        if (bl2) {
            void var8_9;
            void var9_10;
            bz.a(graphics, 16179625, -16777216, -1);
            int n8 = bz.a(string);
            if (var9_10 != false) {
                bz.a(graphics, string, n4 + 1, n5 + 5, 20);
            }
            ay.a(graphics, n4 + n8, n5, n6 - n8, 26, n7, "", n2 + "", (boolean)var8_9);
            bm.D.a(graphics, n3, n4 + n8 + 2, n5 + 4, 0);
            return;
        }
        bm.D.a(graphics, n3, n4 - 34, n5 + 2, 0);
        bz.a(graphics, 16179625, 0, -1);
        bz.a(graphics, n2 + "", n4 + n7, n5 + 2, 0);
    }

    public static void a(Graphics graphics, int n2, int n3, int n4, int n5, boolean bl2, boolean bl3, boolean bl4) {
        ay.a(graphics, n2, bm.aM, bm.aL, 250, 280, 200, true, false, true);
    }

    public static void b(Graphics graphics, int n2, int n3, int n4, int n5, int n6, int n7) {
        int n8 = graphics.getClipX();
        int n9 = graphics.getClipY();
        int n10 = graphics.getClipWidth();
        int n11 = graphics.getClipHeight();
        for (int i2 = 0; i2 < n5 * n6 && (n7 == -1 || i2 + n4 * n6 < n7); ++i2) {
            ay.b(graphics, n2 + i2 % n6 * 29, n3 + i2 / n6 * 29);
        }
        graphics.setClip(n8, n9, n10, n11);
    }

    public static void b(Graphics graphics, int n2, int n3) {
        ay.b(graphics, n2, n3, 29);
    }

    public static void b(Graphics graphics, int n2, int n3, int n4) {
        graphics.setColor(4204306);
        graphics.drawRect(n2, n3, 29, 29);
        graphics.setColor(10182707);
        graphics.drawRect(n2 + 1, n3 + 1, 27, 27);
    }

    public static String a(int n2, int n3) {
        String string = new String();
        new Random();
        for (n3 = 0; n3 < 4; ++n3) {
            string = string + bm.e(0, 9);
        }
        return string;
    }

    public static boolean a(Graphics graphics, String string, int n2, int n3, int n4, int n5, boolean bl2, int n6) {
        if (string == null) {
            string = "";
        }
        if (string.length() <= f) {
            f = string.length();
        }
        if (n6 == 8) {
            n2 -= bz.a(string);
        }
        String string2 = string.substring(0, f);
        if (bl2) {
            bz.b(3);
            bz.c(5);
            bz.a(graphics, string2, n2, n3, 4);
            bz.c(0);
            bz.b((byte)1);
        } else {
            bz.a(graphics, n5, -1, -1);
            bz.a(graphics, string2, n2, n3, 4);
        }
        if (string.length() == f) {
            f = 0;
            return true;
        }
        ++f;
        return false;
    }

    public static void a(Graphics graphics, cr cr2) {
        graphics.setClip(0, 0, 640, 360);
        cr2.a(graphics, o.j[cr2.u], o.k[cr2.u], false);
        graphics.setClip(0, 0, 640, 360);
    }

    public static void a(Graphics graphics, String string, int n2, int n3, int n4, int n5, int n6) {
        int n7 = n2;
        int n8 = 0;
        n2 = 333;
        n8 = 22;
        n8 = n4;
        n8 = 330;
        n8 = n7;
        Graphics graphics2 = graphics;
        bz.a(graphics2, 16179625, 16179625, -1);
        bz.a(graphics2, string, 320, n2, 17);
    }

    public static void b(Graphics graphics, String string, int n2, int n3, int n4, int n5) {
        bz.b((byte)0);
        graphics.setColor(n5);
        bz.a(graphics, string, n2, n3 + 1, 1);
        bz.a(graphics, string, n2 + 1, n3, 1);
        graphics.setColor(n4);
        bz.a(graphics, string, n2, n3, 1);
        bz.b((byte)1);
    }

    public static void c(Graphics graphics, int n2, int n3, int n4) {
        int n5 = n2 - n4;
        int n6 = n2 + n4;
        int n7 = n3 - n4;
        n4 = n3 + n4;
        graphics.drawLine(n2, n7, n5, n3);
        graphics.drawLine(n5, n3, n2, n4);
        graphics.drawLine(n2, n4, n6, n3);
        graphics.drawLine(n6, n3, n2, n7);
    }

    public static void c(Graphics graphics, int n2, int n3, int n4, int n5, int n6, int n7) {
        graphics.setColor(n7);
        graphics.drawLine(n2 + 1, n3 + 1, n2 + n4 - 1, n3 + 1);
        aq.a(graphics);
        aq.a(n2, n3 + 2, n4, n5 - 3, n6, n7, 16);
        aq.a(bm.bi);
        graphics.setColor(n6);
        graphics.drawLine(n2 + 1, n3 + n5 - 1, n2 + n4 - 1, n3 + n5 - 1);
    }

    public static void a(Graphics graphics, int n2, int n3, int n4, int n5, int n6, String string, String string2, boolean bl2) {
        ay.a(graphics, n2, n3, n4, n5, n6, string, string2, bl2, false);
    }

    public static void a(Graphics graphics, int n2, int n3, int n4, int n5, int n6, String string, String string2, boolean bl2, boolean bl3) {
        int n7 = n6 == 0 ? 0 : 8;
        ay.c(graphics, n2, n3, n4, n5, 6113836, 9142619);
        ay.c(graphics, n2 + (n6 += n7) + 4, n3 + 4, n4 - n6 - 8, n5 - 8, 2953219, 1512467);
        if (bl2) {
            graphics.setColor(65535);
        } else {
            graphics.setColor(-13032703);
        }
        graphics.drawRoundRect(n2 + n6 + 4 - 1, n3 + 4 - 1, n4 - n6 - 8 + 2, n5 - 8 + 2, 8, 8);
        bz.a(graphics, -597591, -13032703, 4);
        bz.a(graphics, string, n2 + 4, n3 + (n5 >> 1), 2);
        bz.a(graphics, 0xFFFFFF, -13032703, -1);
        if (bl3) {
            ay.a(graphics, string2, n2 + n6 + 4, n3 + 4, n4 - n6 - 8, n5 - 8, 2, (byte)1, true, 0);
            return;
        }
        ay.a(graphics, string2, n2 + n6 + 4, n3 + 4, n4 - n6 - 8, n5 - 8, 2, (byte)1, false, 0);
    }

    public static void c(Graphics graphics, int n2, int n3, int n4, int n5, int n6) {
        int[] nArray = new int[]{0};
        ay.a(graphics, n2, n3, n4, n5, n6, nArray);
    }

    public static void a(Graphics graphics, int n2, int n3, int n4, int n5, int n6, int[] nArray) {
        int n7;
        int n8 = graphics.getColor();
        graphics.setColor(7035466);
        graphics.drawRect(n2, n3, n4, n5);
        int n9 = n5 / n6 + n5 % n6 / n6;
        for (n7 = 1; n7 < n6; ++n7) {
            graphics.drawLine(n2, n3 + n7 * n9, n2 + n4, n3 + n7 * n9);
        }
        n7 = 0;
        for (n6 = 0; n6 < nArray.length; ++n6) {
            n7 += nArray[n6];
        }
        if (n7 == 0) {
            graphics.setColor(n8);
            return;
        }
        n6 = n4 / n7 + n4 % n7 / n7;
        n4 = 0;
        for (n9 = 1; n9 < nArray.length; ++n9) {
            graphics.drawLine(n2 + n4 + nArray[n9 - 1] * n6, n3, n2 + n4 + nArray[n9 - 1] * n6, n3 + n5);
            n4 += nArray[n9 - 1] * n6;
        }
        graphics.setColor(n8);
    }

    public static void a(Graphics graphics, String string, int n2, int n3, int n4, boolean bl2) {
        if (bl2) {
            ay.a(graphics, bm.D, string, 220, 221, n2, n3, n4);
            return;
        }
        ay.a(graphics, bm.D, string, 218, 219, n2, n3, n4);
    }

    public static void a(Graphics graphics, String[] stringArray, int n2, String string, String string2, String string3, int n3, int n4, int n5) {
        int n6 = bz.a;
        int n7 = bz.a >> 1;
        int n8 = 300 - n6;
        int[] nArray = bf.a(string, n8);
        int n9 = nArray[1];
        int n10 = n9 + (n3 == -1 ? 0 : 25) + stringArray.length * 40 + (n5 == -1 || n3 == -1 ? 0 : n6 << 1) + n6 * 3 + n7;
        int n11 = 360 - n10 >> 1;
        ay.a(graphics, 170, n11, 300, n10, true, string2, true, null, (byte)0);
        bm.h(170, n11, 300, n10);
        int n12 = 170 + (bz.a >> 1);
        n10 = n11 + 32 + (bz.a >> 1);
        bf.a(graphics, string, nArray, n12, n10, 0, -1, 20, false);
        n10 += n7 + n9;
        if (n3 != -1) {
            int n13 = 25 + bz.a(string3) + n6;
            n12 = 640 - n13 >> 1;
            n.a(graphics, n12, n10, n3, n4, 1, true, false, 0);
            bz.a(graphics, -597591, -13032703, 4);
            bz.a(graphics, string3, n12 += n6 + 25, n10);
            n10 += n7 + 25;
            n13 = bz.a(bm.d(0, 63)) + 26 + 30 + (n6 << 1);
            if (n5 != -1) {
                string = Integer.toString(n5);
                int n14 = bz.a(string) + n6;
                n3 = n6 + n7;
                n12 = 640 - n13 >> 1;
                bz.a(graphics, bm.d(0, 63), n12, n10);
                n4 = n12 += bz.a(bm.d(0, 63)) + n6;
                n5 = n10;
                bm.F.a(graphics, n4, n5, 0);
                graphics.setColor(14595437);
                graphics.fillRect(n12 += n6, n10 - n7, n14, n3);
                graphics.setColor(0);
                graphics.drawRect(n12, n10 - n7, n14, n3);
                bz.a(graphics, 5385734, 5385734, 4);
                bz.a(graphics, string, n12 + n7, n10);
                int n15 = n12 += n6 + n14;
                n12 = n10;
                bm.F.a(graphics, n15, n12, 1);
                bm.e(n4 + 13 - 60, n5 - 25, n15 - 13, n12 - 25, 60, 50);
                n10 += n6 * 2;
            }
        }
        ay.d(graphics, 170, n10, 300, 40, stringArray.length);
        bm.g(170, n10, 300, stringArray.length * 40);
        for (int i2 = 0; i2 < stringArray.length; ++i2) {
            if (i2 == n2) {
                ay.a(graphics, 170, n10 + i2 * 40, 300, 40);
            }
            int n16 = bz.a(stringArray[i2]);
            n12 = 640 - n16 >> 1;
            bz.a(graphics, -597591, -13032703, 4);
            bz.a(graphics, stringArray[i2], n12, n10 + i2 * 40 + 20, 2);
        }
        bm.F.c();
    }

    public static void a(Graphics graphics, int n2, int n3, int n4, int n5, long l2, String string, boolean bl2) {
        long l3 = (l2 - bm.m()) / 60000L;
        long l4 = (l2 - bm.m()) / 1000L - l3 * 60L;
        String string2 = "";
        string2 = string2 + (l3 > 0L ? l3 + ":" : "00:");
        string2 = string2 + l4;
        bl2 = l2 - bm.m() >= 0L;
        if (bl2) {
            graphics.setColor(7035466);
            graphics.drawRect(185, 68, 50, 25);
            aq.e(Integer.MIN_VALUE);
            aq.b(graphics, 186, 69, 49, 24);
            bz.a(graphics, 16179625, 5385734, 0);
            bz.a(graphics, string, 188, 70);
            bz.a(graphics, string2, 188, 82);
            return;
        }
    }

    public static void a(Graphics graphics, int n2, int n3, String string) {
        ay.a(graphics, n2, n3, string, false);
    }

    public static void a(Graphics graphics, int n2, int n3, String string, boolean bl2) {
        ay.a(graphics, n2, n3, string, bl2, false);
    }

    public static void a(Graphics graphics, int n2, int n3, String string, boolean bl2, boolean bl3) {
        if (bm.X != 0) {
            ay.a(bm.W, true, n2, n3, string, bl3);
            if (bl2) {
                bm.a(bm.W, 20, 180);
            }
            bm.X = 0;
        }
        graphics.drawImage(bm.V, 0, 0, 0);
    }

    public static int a(int n2, int[] nArray) {
        n2 = 0;
        bz.b((byte)1);
        for (int i2 = 0; i2 < nArray.length; ++i2) {
            int n3 = bz.a(bm.d(0, nArray[i2]));
            n2 = n3 > n2 ? n3 : n2;
        }
        return n2;
    }

    public static int a(String[] stringArray) {
        int n2 = 0;
        bz.b((byte)1);
        for (int i2 = 0; i2 < stringArray.length; ++i2) {
            int n3 = bz.a(stringArray[i2]);
            n2 = n3 > n2 ? n3 : n2;
        }
        return n2;
    }

    private static void a(Graphics graphics, ao ao2, String string, int n2, int n3, int n4, int n5, int n6) {
        int n7 = ao2.f(n2);
        int n8 = ao2.f(n3);
        int n9 = ao2.g(n3);
        int n10 = n6 - (n7 << 1);
        int n11 = bz.a(string);
        int n12 = 0;
        while (n12 * n8 < n10) {
            ao2.a(graphics, n3, n4 + n7 + n12 * n8, n5, 0);
            ++n12;
        }
        ao2.a(graphics, n2, n4, n5, 0);
        ao2.a(graphics, n2, n4 + n6, n5, 1);
        bz.a(graphics, string, n4 + (n6 - n11 >> 1), n5 + (n9 - bz.a >> 1));
    }

    public static String a(String string) {
        if (string == null) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (int i2 = 0; i2 < string.length(); ++i2) {
            stringBuffer.append("*");
        }
        return stringBuffer.toString();
    }

    public static void a(Graphics graphics, String string, int n2, int n3, int n4, int n5, int n6, byte by2, boolean n7, int n8) {
        int n9 = graphics.getClipX();
        int n10 = graphics.getClipY();
        int n11 = graphics.getClipWidth();
        int n12 = graphics.getClipHeight();
        if (bz.a(string) > n4 - 7) {
            if (n7 != 0) {
                if ((n8 & 1) != 0) {
                    n2 -= n4 - 7 >> 1;
                    n4 -= 7;
                }
                if ((n8 & 2) != 0) {
                    n3 -= bz.b(string) >> 1;
                }
                if ((n7 = bz.a(string)) + 19 <= n4) {
                    bz.a(graphics, string, n2 + (n4 >> 1), n3, 1);
                } else {
                    graphics.setClip(n2 + 1, n3 - 1, n4 - 2, n5 + 2);
                    String string2 = string + "c" + Integer.toString(n2) + "c" + Integer.toString(n3);
                    String[] stringArray = null;
                    int n13 = 0;
                    n8 = 0;
                    if (!g.containsKey(string2)) {
                        g.put(string2, Integer.toString(0) + "c" + Integer.toString(0));
                    }
                    if ((stringArray = bg.b((String)g.get(string2), "c")).length != 2) {
                        bz.a(graphics, string, n2 + (n4 >> 1), n3, 1);
                        return;
                    }
                    n13 = Integer.parseInt(stringArray[0]);
                    n8 = Integer.parseInt(stringArray[1]);
                    if (by2 == 0) {
                        n13 = n13 < -n7 ? n4 : (n13 -= n6);
                    } else if (by2 == 1) {
                        if (n8 == 0 && n13 <= -(n7 + 19 - n4)) {
                            n8 = 1;
                        } else if (n8 == 1 && n13 >= 19) {
                            n8 = 0;
                        }
                        if (n8 == 0) {
                            n13 -= n6;
                        } else if (n8 == 1) {
                            n13 += n6;
                        }
                    }
                    bz.a(graphics, string, n2 += n13, n3);
                    g.put(string2, Integer.toString(n13) + "c" + Integer.toString(n8));
                }
            } else {
                int n14;
                n7 = ay.a(string, ' ');
                String string3 = string;
                while (n7 != -1 && bz.a(string3 = string3.substring(0, n7)) > n4 - 15) {
                    n7 = ay.a(string3, ' ');
                }
                int n15 = n14 = bz.a(string3);
                if ((n8 & 1) != 0) {
                    n15 = (n14 >> 1) + 19;
                }
                bz.a(graphics, string3, n2, n3, n8);
                bz.a(graphics, "..", n2 + n15, n3, n8);
            }
            graphics.setClip(n9, n10, n11, n12);
            return;
        }
        bz.a(graphics, string, n2, n3, n8);
    }

    private static int a(String string, char c2) {
        for (c2 = (char)(string.length() - 1); c2 >= '\u0000'; c2 = (char)(c2 - 1)) {
            if (string.charAt(c2) != ' ') continue;
            return c2;
        }
        return -1;
    }

    public static void a(Graphics graphics, String object, int n2, int n3, int n4, int n5, int n6, int n7) {
        n6 = bz.a((String)object);
        n5 /= 2;
        if (n6 < n4) {
            bz.a(graphics, (String)object, n2, n3 + (n5 * 1 >> 1), 1);
            return;
        }
        if (n6 % n4 < n4 * 5 / 6) {
            while (n6 / n4 < 2 || n6 / n4 == 2 && n6 % n4 == 0) {
                if (n6 / (n4 -= n4 / 10 > 0 ? n4 / 10 : 1) <= 2 && (n6 / n4 != 2 || n6 % n4 == 0)) continue;
                n4 += n4 / 9 > 0 ? n4 / 9 : 1;
                break;
            }
        }
        n7 = n4;
        boolean bl2 = false;
        Vector<String> vector = new Vector<String>();
        int n8 = 1;
        int n9 = 0;
        for (int i2 = 0; i2 < ((String)object).length(); ++i2) {
            int n10 = n9 + n8;
            int n11 = n10 = n10 > ((String)object).length() ? ((String)object).length() : n10;
            if (bz.a(((String)object).substring(n9, n10)) > n7 || i2 == ((String)object).length() - 1) {
                n8 = 32;
                if (n10 < ((String)object).length() - 1) {
                    n8 = ((String)object).charAt(n10);
                }
                char c2 = ((String)object).charAt(n10 - 1);
                char c3 = ((String)object).charAt(n10 - 2);
                if (n8 != 32) {
                    if (c2 != ' ') {
                        if (c3 != ' ') {
                            bl2 = true;
                        }
                        --n10;
                        --i2;
                    }
                } else if (n10 < ((String)object).length()) {
                    ++n10;
                    ++i2;
                }
                String string = ((String)object).substring(n9, n10);
                if (bl2 && i2 != ((String)object).length() - 1) {
                    string = string + "-";
                }
                vector.addElement(string);
                n9 = n10;
                n8 = 1;
                continue;
            }
            ++n8;
        }
        object = vector;
        if (n6 / n4 > 2 || n6 / n4 == 2 && n6 % n4 != 0) {
            for (n4 = 0; n4 < 2; ++n4) {
                String string = (String)((Vector)object).elementAt(n4);
                if (n4 == 1) {
                    string = string.substring(0, string.length() - 3) + "...";
                }
                bz.a(graphics, string, n2, n3 + n4 * n5, 1);
            }
            return;
        }
        for (n4 = 0; n4 < ((Vector)object).size(); ++n4) {
            String string = (String)((Vector)object).elementAt(n4);
            bz.a(graphics, string, n2, n3 + n4 * n5, 1);
        }
    }

    static {
        int[] nArray = new int[]{2, 3};
        int[] nArray2 = new int[]{56, 147, 129, 70};
        a = 0;
        j = new int[255];
        k = 1;
        c = 0;
        l = 20;
        f = 0;
        g = new Hashtable();
    }
}

