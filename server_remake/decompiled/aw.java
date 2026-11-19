/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import java.util.Vector;
import javax.microedition.lcdui.Graphics;

final class aw {
    public String a;
    public int b;
    public int c;
    public int d;
    public static Vector e = new Vector();
    public static String f;
    private static int i;
    public static int g;
    public static int h;
    private static int j;
    private static int[] k;
    private static int[] l;

    aw() {
    }

    public static void a(int n2) {
        i = n2;
        j = 0;
        bm.X = 3;
    }

    public static void a(Graphics graphics) {
        switch (i) {
            case 1: {
                ay.a(graphics, 2, 3, bm.A(622), true);
                bm.a(graphics, bm.A(623));
                ay.a(graphics, 167, 90, 305, 255, false, "", false);
                int n2 = 7;
                int n3 = 290;
                int n4 = 80;
                int n5 = 174;
                Graphics graphics2 = graphics;
                int n6 = e.size();
                if (e.size() >= 7) {
                    bm.a(graphics2, 0, 0, 0, true, 315, 103, 283);
                }
                for (n4 = h; n4 < h + 7; ++n4) {
                    if (g + 1 == n4) {
                        ay.b(graphics2, 174, 73 + 30 * (n4 - h + 1), 290, 27);
                    }
                    graphics2.setColor(7035466);
                    graphics2.drawLine(175, 72 + (n4 - h + 1 + 1) * 30, 463, 72 + (n4 - h + 1 + 1) * 30);
                    Object object = bm.cb.ae;
                    n3 = bm.cb.ag;
                    int n7 = bm.cb.af;
                    if (n4 == h) {
                        graphics2.setColor(7035466);
                        graphics2.fillRect(279, 60 + 30 * (n4 - h + 1), 1, 35);
                        bz.a(graphics2, -597591, -13032703, 4);
                        bz.a(graphics2, bm.A(625), 223, 115, 1);
                        if (object != null) {
                            ay.b(graphics2, (String)object, 379, 70 + (n4 - h + 1) * 30, n7, n3);
                        }
                    }
                    if (n4 <= h || n4 > n6 || n4 - h >= 8 || (object = (aw)e.elementAt(n4 - 1)) == null) continue;
                    ay.b(graphics2, ((aw)object).a, 319, 75 + (n4 - h + 1) * 30, ((aw)object).b, ((aw)object).c);
                }
                bm.X = 0;
                return;
            }
            case 2: {
                if (bm.X != 0) {
                    if (g == 0) {
                        ay.a(graphics, 1, k, 180, j, true, true, false);
                    } else {
                        ay.a(graphics, 1, l, 180, j, true, true, false);
                    }
                    bm.X = 0;
                }
                return;
            }
            case 3: {
                String[] stringArray = new String[]{bm.d(0, 2), bm.d(0, 39)};
                ay.a(graphics, bm.A(624), stringArray, j, 140, true, "", true);
                return;
            }
            case 4: {
                aw aw2 = (aw)e.elementAt(g);
                if (aw2 == null) break;
                Object object = bm.A(626);
                String[] stringArray = bg.a(f, ";", 2);
                for (int i2 = 0; i2 < stringArray.length; ++i2) {
                    if (i2 == 1 && stringArray[i2] != null) {
                        object = (String)object + bm.A(627) + "\n";
                    }
                    if (stringArray[i2] == null || stringArray[i2].equals("")) continue;
                    object = (String)object + stringArray[i2] + "\n";
                }
                String string = bg.a((String)object, ";", "\n");
                object = bg.a(string, 175, "");
                int n8 = (((String[])object).length + 2) * 19;
                int n9 = (360 - n8) / 2;
                ay.a(graphics, 167, n9, 305, n8, false, "", true);
                ay.b(graphics, aw2.a, 320, n9 + 9, aw2.b, aw2.c);
                bz.a(graphics, -597591, -13032703, 4);
                for (n8 = 0; n8 < ((String[])object).length; ++n8) {
                    bz.a(graphics, object[n8], 177, n9 + 9 + 19 * (n8 + 1), 4);
                }
                break;
            }
        }
    }

    /*
     * Unable to fully structure code
     */
    public static void a() {
        switch (aw.i) {
            case 1: {
                bm.g(20, 180);
                var0 = bm.b(6, 167, 133, 305, 30);
                if (var0 != -1 && var0 < aw.e.size()) {
                    if (aw.g == var0 + aw.h) {
                        bm.dB = true;
                    } else {
                        aw.g = var0 + aw.h;
                        bm.X = 3;
                    }
                }
                if (bm.v(262144)) {
                    bm.H();
                } else if (bm.v(16388)) {
                    if (aw.g > 0) {
                        if (--aw.g < aw.h) {
                            --aw.h;
                        }
                    } else {
                        if (aw.e.size() - 1 > 5) {
                            aw.h = aw.e.size() - 1 - 5;
                        }
                        aw.g = aw.e.size() - 1;
                    }
                } else if (bm.v(33024)) {
                    if (aw.g < aw.e.size() - 1) {
                        if (++aw.g > aw.h + 5) {
                            ++aw.h;
                        }
                    } else {
                        aw.h = 0;
                        aw.g = 0;
                    }
                } else if (bm.v(196640) || bm.v(196608)) {
                    aw.a(2);
                }
                if (aw.g != aw.e.size() - 1 && aw.g == aw.h + 5) {
                    ++aw.h;
                    break;
                }
                if (aw.g != 0 && aw.g == aw.h) {
                    --aw.h;
                }
                return;
            }
            case 2: {
                var0_1 = 0;
                var0_1 = aw.g == 0 ? 2 : 3;
                var0_1 = bm.f(var0_1, 0);
                if (var0_1 != -1) {
                    if (aw.j == var0_1) {
                        bm.dB = true;
                    } else {
                        aw.j = var0_1;
                        bm.X = 3;
                    }
                }
                bm.g(20, 180);
                if (bm.v(196640) || bm.v(196608)) {
                    var0_2 = (aw)aw.e.elementAt(aw.g);
                    if (var0_2 != null) {
                        if (aw.g != 0) {
                            switch (aw.j) {
                                case 0: {
                                    if (bm.cb.ae == null || !bm.cb.ae.equals(var0_2.a)) {
                                        bm.bn.P(var0_2.d);
                                        break;
                                    }
                                    aw.a(1);
                                    break;
                                }
                                case 1: {
                                    bm.bn.O(var0_2.d);
                                    break;
                                }
                                case 2: {
                                    aw.a(1);
                                }
                            }
                        } else {
                            switch (aw.j) {
                                case 0: {
                                    if (bm.cb.ae != null) {
                                        bm.bn.P(var0_2.d);
                                        break;
                                    }
                                    aw.a(1);
                                    break;
                                }
                                case 1: {
                                    aw.a(1);
                                }
                                default: {
                                    break;
                                }
                            }
                        }
                    }
                } else if (bm.v(262144)) {
                    aw.a(1);
                } else if (bm.v(16388)) {
                    aw.j = aw.j > 0 ? --aw.j : (aw.g == 0 ? 1 : 2);
                } else if (bm.v(33024)) {
                    aw.j = aw.g == 0 ? (aw.j < 1 ? ++aw.j : 0) : (aw.j < 2 ? ++aw.j : 0);
                }
                bm.X = 3;
                return;
            }
            case 3: {
                if (bm.v(262144)) ** GOTO lbl-1000
                if (!bm.v(16388)) ** GOTO lbl94
                aw.j = aw.j > 0 ? --aw.j : 1;
                ** GOTO lbl106
lbl94:
                // 1 sources

                if (!bm.v(33024)) ** GOTO lbl97
                aw.j = aw.j < 1 ? ++aw.j : 0;
                ** GOTO lbl106
lbl97:
                // 1 sources

                if (!bm.v(196640) && !bm.v(196608)) ** GOTO lbl106
                switch (aw.j) {
                    case 0: {
                        var0_3 = (aw)aw.e.elementAt(aw.g);
                        if (var0_3 == null) break;
                        bm.bn.Q(var0_3.d);
                        break;
                    }
                    case 1: lbl-1000:
                    // 2 sources

                    {
                        aw.a(2);
                    }
                }
lbl106:
                // 7 sources

                bm.X = 3;
                return;
            }
            case 4: {
                if (bm.v(262144) || bm.v(196640) || bm.v(196608)) {
                    aw.a(1);
                }
                bm.X = 3;
            }
        }
    }

    static {
        i = 0;
        g = 0;
        h = 0;
        j = 0;
        k = new int[]{134, 129};
        l = new int[]{134, 123, 129};
    }
}

