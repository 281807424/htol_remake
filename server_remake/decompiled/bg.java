/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import javax.microedition.lcdui.Graphics;

public final class bg {
    private static Vector[] a;
    private static long b;
    private static boolean c;

    private bg() {
    }

    public static int a(byte[] byArray, int n2, short s2) {
        byArray[n2++] = (byte)s2;
        byArray[n2++] = (byte)(s2 >>> 8);
        return n2;
    }

    public static short a(byte[] byArray, int n2) {
        return (short)(byArray[n2++] & 0xFF | (byArray[n2] & 0xFF) << 8);
    }

    public static int b(byte[] byArray, int n2) {
        return byArray[4] & 0xFF | (byArray[5] & 0xFF) << 8 | (byArray[6] & 0xFF) << 16 | (byArray[7] & 0xFF) << 24;
    }

    public static void a(String string) {
        System.out.println(string);
    }

    static int a(int n2) {
        return aq.a(60);
    }

    static int b(int n2) {
        return aq.b(n2);
    }

    public static byte[] a(String object, String string) {
        if (object == null) {
            return null;
        }
        try {
            return ((String)object).getBytes(string);
        }
        catch (Exception exception) {
            object = exception;
            exception.printStackTrace();
            return null;
        }
    }

    public static String a(byte[] byArray, String string) {
        try {
            return new String(byArray, string);
        }
        catch (Exception exception) {
            return "";
        }
    }

    static String[] b(String string, String string2) {
        if (string == null) {
            return null;
        }
        int n2 = 0;
        String string3 = string;
        while (string3 != null) {
            int n3 = string3.indexOf(string2);
            if (n3 > 0 && n3 < string3.length()) {
                string3 = string3.substring(n3 + string2.length());
                ++n2;
                continue;
            }
            string3 = null;
        }
        return bg.a(string, string2, n2 + 1);
    }

    static String[] a(String string, String string2, int n2) {
        int n3 = 0;
        String[] stringArray = new String[n2];
        int n4 = 0;
        n3 = string.indexOf(string2);
        if (n3 == -1) {
            stringArray[n2 - 1] = string;
            return stringArray;
        }
        int n5 = string2.length();
        for (int i2 = 0; i2 < n2 - 1; ++i2) {
            if (n3 == -1) {
                return stringArray;
            }
            stringArray[i2] = string.substring(n4, n3);
            n4 = n3 + n5;
            n3 = string.indexOf(string2, n3 + n5);
        }
        stringArray[n2 - 1] = string.substring(n4 + 1 - n5);
        return stringArray;
    }

    static int a(Graphics graphics, int n2, String string, int n3, int n4, int n5) {
        bz.b((byte)0);
        graphics.setColor(n2);
        int n6 = bz.a(graphics, string, n3, n4, 0);
        bz.b((byte)1);
        return n6;
    }

    static String[] a(String string, int n2, String string2) {
        int n3;
        bm.bi.setFont(bm.m);
        Vector<String> vector = new Vector<String>();
        Object var4_4 = null;
        int n4 = 1;
        int n5 = 0;
        try {
            while (!string.equals("")) {
                n3 = string.indexOf("\n");
                if (n3 > 0 && aq.a.getFont().stringWidth(string.substring(0, n3 - 1)) >= n2) {
                    n3 = -1;
                }
                if (n3 == -1) {
                    if (n4 > string.length()) {
                        n3 = string.length();
                    } else {
                        n3 = n4;
                        int n6 = n5 = aq.a.getFont().stringWidth(string.substring(0, n3)) > n2 ? -1 : 1;
                        if (n3 < string.length()) {
                            while ((aq.a.getFont().stringWidth(string.substring(0, n3)) > n2 || aq.a.getFont().stringWidth(string.substring(0, n3 + 1)) <= n2) && (n3 += n5) != string.length()) {
                            }
                        }
                    }
                    if (!string2.equals("")) {
                        n4 = n3;
                        if (n3 < string.length() && (string.charAt(n3) >= 'A' && string.charAt(n3) <= 'Z' || string.charAt(n3) >= 'a' && string.charAt(n3) <= 'z')) {
                            while (string2.indexOf(string.substring(n3 - 1, n3)) == -1) {
                                if (--n3 != 0) continue;
                                n3 = n4;
                                break;
                            }
                        }
                    }
                }
                n4 = n3;
                vector.addElement(string.substring(0, n3));
                if (n3 == string.length()) {
                    string = "";
                    continue;
                }
                if (!(string = string.substring(n3)).substring(0, 1).equals("\n")) continue;
                string = string.substring(1);
            }
        }
        catch (Exception exception) {}
        String[] stringArray = new String[vector.size()];
        for (n3 = 0; n3 < vector.size(); ++n3) {
            stringArray[n3] = (String)vector.elementAt(n3);
        }
        return stringArray;
    }

    private static String c(String string, String string2, String string3) {
        int n2 = string.indexOf(string2);
        if (n2 >= 0) {
            String string4 = string.substring(0, n2);
            string = string.substring(n2 + string2.length(), string.length());
            return string4 + string3 + string;
        }
        return string;
    }

    public static String a(String string, String string2, String string3) {
        int n2 = string.indexOf(string2);
        if (n2 >= 0) {
            String string4 = string.substring(0, n2);
            string = string.substring(n2 + string2.length(), string.length());
            return string4 + string3 + bg.a(string, string2, string3);
        }
        return string;
    }

    public static String a(long l2) {
        return bg.a(l2, false);
    }

    public static String a(long l2, boolean bl2) {
        Calendar calendar = Calendar.getInstance();
        Date date = new Date(l2);
        calendar.setTime(date);
        int n2 = calendar.get(1);
        int n3 = calendar.get(2) + 1;
        int n4 = calendar.get(5);
        int n5 = calendar.get(11);
        int n6 = calendar.get(12);
        int n7 = calendar.get(13);
        String string = n3 < 10 ? "0" + n3 : "" + n3;
        String string2 = n4 < 10 ? "0" + n4 : "" + n4;
        String string3 = n5 < 10 ? "0" + n5 : "" + n5;
        String string4 = n6 < 10 ? "0" + n6 : "" + n6;
        String string5 = n7 < 10 ? "0" + n7 : "" + n7;
        if (bl2) {
            return n2 + "-" + string + "-" + string2 + "\n" + string3 + ":" + string4 + ":" + string5;
        }
        return n2 + "-" + string + "-" + string2 + " " + string3 + ":" + string4 + ":" + string5;
    }

    public static String a(String[] stringArray) {
        String string = "";
        if (stringArray.length >= 1) {
            string = stringArray[0];
        }
        for (int i2 = 1; i2 < stringArray.length; ++i2) {
            string = string + "^" + stringArray[i2];
        }
        return string;
    }

    static boolean a() {
        c = false;
        if (System.currentTimeMillis() > 0L) {
            b = 0L;
            c = true;
        }
        return c;
    }

    static void a(Vector vector, int n2, int n3, int n4) {
        if (vector.size() <= 1) {
            return;
        }
        int n5 = n2;
        int n6 = n3;
        Object e2 = vector.elementAt(n2);
        while (n2 < n3) {
            while (bg.a(n4, e2, vector.elementAt(n3), true) && n2 < n3) {
                --n3;
            }
            if (n2 != n3) {
                vector.setElementAt(vector.elementAt(n3), n2);
                ++n2;
            }
            while (bg.a(n4, e2, vector.elementAt(n2), false) && n2 < n3) {
                ++n2;
            }
            if (n2 == n3) continue;
            vector.setElementAt(vector.elementAt(n2), n3);
            --n3;
        }
        vector.setElementAt(e2, n2);
        int n7 = n2;
        n2 = n5;
        n3 = n6;
        if (n2 < n7) {
            bg.a(vector, n2, n7 - 1, n4);
        }
        if (n3 > n7) {
            bg.a(vector, n7 + 1, n3, n4);
        }
    }

    private static boolean a(int n2, Object object, Object object2, boolean bl2) {
        if (bl2) {
            if (object == null) {
                return false;
            }
            if (object2 == null) {
                return true;
            }
        } else {
            if (object == null) {
                return true;
            }
            if (object2 == null) {
                return false;
            }
        }
        switch (n2) {
            case 0: {
                if (bl2) {
                    return ((cr)object2).aa >= ((cr)object).aa;
                }
                return ((cr)object2).aa <= ((cr)object).aa;
            }
            case 4: {
                String[] stringArray = bg.a((String)object, "^", 5);
                byte by2 = Byte.parseByte(stringArray[4]);
                by2 = (by2 & 1) != 0 ? (byte)0 : 1;
                stringArray = bg.a((String)object2, "^", 5);
                byte by3 = Byte.parseByte(stringArray[4]);
                byte by4 = by3 = (by3 & 1) != 0 ? (byte)0 : 1;
                if (bl2) {
                    return by3 >= by2;
                }
                return by3 <= by2;
            }
            case 1: {
                if (bl2) {
                    return ((b)object2).b >= ((b)object).b;
                }
                return ((b)object2).b <= ((b)object).b;
            }
            case 5: {
                if (bl2) {
                    return ((t)object2).i >= ((t)object).i;
                }
                return ((t)object2).i <= ((t)object).i;
            }
            case 6: {
                if (bl2) {
                    return ((b)object2).b >= ((b)object).b;
                }
                return ((b)object2).b <= ((b)object).b;
            }
            case 2: {
                h h2 = (h)object2;
                h h3 = (h)object;
                int n3 = (h2.o << 8) + h2.l;
                int n4 = (h3.o << 8) + h3.l;
                if (n3 == n4 && h2.a == 3 && h3.a == 3) {
                    n3 = h2.c << 16 + h2.f << 8 + h2.g << 8;
                    n4 = h3.c << 16 + h3.f << 8 + h3.g << 8;
                }
                if (bl2) {
                    return n3 >= n4;
                }
                return n3 <= n4;
            }
            case 3: {
                n2 = bm.cb.k;
                int n5 = bm.cb.l;
                int n6 = ((h)object).k;
                int n7 = ((h)object).l;
                int n8 = ((h)object2).k;
                int n9 = ((h)object2).l;
                n7 = (n6 - n2) * (n6 - n2) + (n7 - n5) * (n7 - n5);
                n2 = (n8 - n2) * (n8 - n2) + (n9 - n5) * (n9 - n5);
                if (bl2) {
                    return n2 >= n7;
                }
                return n2 <= n7;
            }
            case 7: {
                if (!bl2) {
                    return ((i)object2).g() <= ((i)object).g();
                }
                return ((i)object2).g() >= ((i)object).g();
            }
        }
        return true;
    }

    public static String c(String string, String string2) {
        int n2 = string.indexOf(string2);
        while (n2 != -1) {
            int n3 = string.indexOf(string2, n2 + 1);
            try {
                int n4 = Integer.parseInt(new String(string.substring(n2 + 1, n3)));
                String string3 = bm.d(4, n4);
                string = string.substring(0, n2) + string3 + string.substring(n3 + 1);
                n2 = string.indexOf(string2);
            }
            catch (Exception exception) {
                return string;
            }
        }
        return string;
    }

    public static boolean b(String string) {
        if (string == null || string.length() == 0) {
            return false;
        }
        for (int i2 = 0; i2 < string.length(); ++i2) {
            char c2 = string.charAt(i2);
            if (Character.isDigit(c2)) continue;
            return false;
        }
        return true;
    }

    public static String a(String string, int n2) {
        if (string == null) {
            return null;
        }
        return bg.d(string, String.valueOf(n2));
    }

    public static String d(String string, String string2) {
        if (string == null || string2 == null) {
            return null;
        }
        string = bg.c(string, "%s", string2);
        return string;
    }

    public static String b(String string, String string2, String string3) {
        if (string == null || string2 == null || string3 == null) {
            return null;
        }
        string = bg.c(string, "%s", string2);
        string = bg.c(string, "%s", string3);
        return string;
    }

    public static String b(String string, String string2, int n2) {
        if (string == null || string2 == null) {
            return null;
        }
        string = bg.c(string, "%s", string2);
        string = bg.c(string, "%s", String.valueOf(n2));
        return string;
    }

    public static String b(String string, int n2, String string2) {
        if (string == null || string2 == null) {
            return null;
        }
        string = bg.c(string, "%s", String.valueOf(n2));
        string = bg.c(string, "%s", string2);
        return string;
    }

    public static String a(String string, int n2, int n3) {
        if (string == null) {
            return null;
        }
        string = bg.c(string, "%s", String.valueOf(n2));
        string = bg.c(string, "%s", String.valueOf(n3));
        return string;
    }

    public static String a(String string, String string2, long l2, String string3) {
        if (string == null || string2 == null || string3 == null) {
            return null;
        }
        string = bg.c(string, "%s", string2);
        string = bg.c(string, "%s", String.valueOf(l2));
        string = bg.c(string, "%s", string3);
        return string;
    }

    public static String a(String string, int n2, String string2, String string3) {
        if (string == null || string2 == null || string3 == null) {
            return null;
        }
        string = bg.c(string, "%s", String.valueOf(n2));
        string = bg.c(string, "%s", string2);
        string = bg.c(string, "%s", string3);
        return string;
    }

    static {
        byte[] byArray = new byte[]{-96, -96, -96, -80, -64, -48, -32, 16, -96, -96, -96, -96, -96, -96, -96, -96};
        byte[] byArray2 = new byte[]{32, 112, 32, 32, 32, 32, 32, 32, 32, 32, 32, 48, 64, 80, 96, 32};
        a = new Vector[7];
    }
}

