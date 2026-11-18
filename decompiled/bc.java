/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Alert
 *  javax.microedition.lcdui.AlertType
 *  javax.microedition.lcdui.ChoiceGroup
 *  javax.microedition.lcdui.Command
 *  javax.microedition.lcdui.CommandListener
 *  javax.microedition.lcdui.Displayable
 *  javax.microedition.lcdui.Form
 *  javax.microedition.lcdui.Item
 *  javax.microedition.lcdui.ItemStateListener
 *  javax.microedition.lcdui.TextField
 */
import java.util.Vector;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.ItemStateListener;
import javax.microedition.lcdui.TextField;

public final class bc
implements CommandListener,
ItemStateListener {
    private static Form d;
    private static TextField[] e;
    private static Alert f;
    private static Command g;
    private static Command h;
    private static Command i;
    private static Command j;
    public static String[] a;
    public static String[] b;
    private static ChoiceGroup k;
    private static byte l;
    public static int c;
    private static bc m;
    private static boolean n;
    private static ChoiceGroup o;
    private static ChoiceGroup p;
    private static ChoiceGroup q;
    private static ChoiceGroup r;
    private static ChoiceGroup s;
    private static ChoiceGroup t;
    private static ChoiceGroup u;

    bc() {
    }

    public static void a(byte by2) {
        bc.a(by2, 0, "", "", null);
    }

    public static void a(byte by2, String string, String string2) {
        bc.a(by2, 0, string, string2, null);
    }

    public static void a(byte by2, int n2) {
        bc.a((byte)14, n2, "", "", null);
    }

    public static void a(byte by2, int n2, String string, String string2, String[] stringArray) {
        int n3;
        if (m != null && cp.c()) {
            return;
        }
        bm.G();
        bm.dB = false;
        o = null;
        p = null;
        q = null;
        r = null;
        s = null;
        t = null;
        k = null;
        j = null;
        i = null;
        h = null;
        g = null;
        n = false;
        l = by2;
        c = n2;
        String string3 = string;
        string = string2;
        int n4 = 0;
        int[] nArray = new int[]{0};
        int n5 = 1;
        int[] nArray2 = new int[]{};
        String[] stringArray2 = new String[]{};
        if (by2 == 28) {
            string3 = bm.A(1218);
            string = bm.A(1219);
            n4 = 50;
            k = new ChoiceGroup(bm.A(1220), 1, b, null);
        } else if (by2 == 0) {
            string3 = bm.d(0, 22);
            string = bm.d(0, 21);
            n4 = 15;
        } else if (by2 == 1) {
            string3 = bm.d(0, 24);
            string = bm.d(0, 23);
            nArray[0] = 65536;
            n4 = 15;
        } else if (by2 == 60) {
            n5 = 4;
            string3 = bm.A(429);
            String[] stringArray3 = new String[4];
            stringArray2 = stringArray3;
            stringArray3[0] = bm.d(0, 22);
            stringArray2[1] = bm.A(1221);
            stringArray2[2] = bm.A(1222);
            stringArray2[3] = bm.A(1223);
            nArray2 = new int[4];
            nArray = new int[4];
            nArray2[0] = 15;
            nArray[0] = 0;
            nArray2[1] = 15;
            nArray[1] = 65536;
            nArray2[2] = 15;
            nArray[2] = 65536;
            nArray2[3] = 11;
            nArray[3] = 65536;
        } else if (by2 == 61) {
            n5 = 3;
            string3 = bm.A(1224);
            String[] stringArray4 = new String[3];
            stringArray2 = stringArray4;
            stringArray4[0] = bm.d(0, 22);
            stringArray2[1] = bm.A(1221);
            stringArray2[2] = bm.A(1222);
            nArray2 = new int[3];
            nArray = new int[3];
            nArray2[0] = 15;
            nArray[0] = 0;
            nArray2[1] = 15;
            nArray[1] = 65536;
            nArray2[2] = 15;
            nArray[2] = 65536;
        } else if (by2 == 62) {
            string3 = bm.d(0, 22);
            string = bm.d(0, 21);
            n4 = 15;
        } else if (by2 == 63) {
            string3 = bm.d(0, 22);
            string = bm.d(0, 21);
            n4 = 15;
        } else if (by2 == 2) {
            n5 = 3;
            string3 = bm.A(1225);
            stringArray2 = new String[3];
            nArray = new int[3];
            stringArray2[0] = bm.d(0, 22);
            stringArray2[1] = bm.d(0, 24);
            stringArray2[2] = bm.A(1226);
            int[] nArray3 = new int[3];
            nArray2 = nArray3;
            nArray3[0] = 15;
            nArray[0] = 0;
            nArray2[1] = 15;
            nArray[1] = 65536;
            nArray2[2] = 15;
            nArray[2] = 65536;
        } else if (by2 == 52) {
            n5 = 1;
            if (cl.g == 0) {
                string3 = bm.A(1231);
                string = bm.A(1232);
            } else if (cl.g == 1) {
                string3 = bm.A(1233);
                string = bm.A(1234);
            } else if (cl.g == 2) {
                string3 = bm.A(1235);
                string = bm.A(1236);
            } else if (cl.g == 3) {
                string3 = bm.A(1237);
                string = bm.A(1238);
            }
            n4 = 6;
        } else if (by2 == 53) {
            n5 = 3;
            string3 = bm.A(1239);
            stringArray2 = new String[3];
            nArray = new int[3];
            stringArray2[0] = bm.A(1240);
            stringArray2[1] = bm.A(1241);
            stringArray2[2] = bm.A(1242);
            int[] nArray4 = new int[3];
            nArray2 = nArray4;
            nArray4[0] = 2;
            nArray[0] = 2;
            nArray2[1] = 9;
            nArray[1] = 2;
            nArray2[2] = 9;
            nArray[2] = 2;
        } else if (by2 == 54) {
            n5 = 1;
            string3 = bm.A(1243);
            string = bm.A(1244);
            n4 = 9;
            nArray[0] = 2;
        } else if (by2 == 3) {
            n5 = 1;
            string3 = bm.A(1245);
            string = bm.A(1059);
            n4 = 32;
        } else if (by2 == 6) {
            n5 = 4;
            string3 = bm.A(1246);
            String[] stringArray5 = new String[4];
            stringArray2 = stringArray5;
            stringArray5[0] = bm.d(0, 22);
            stringArray2[1] = bm.A(1247);
            stringArray2[2] = bm.A(1248);
            stringArray2[3] = bm.A(1226);
            nArray2 = new int[4];
            nArray = new int[4];
            nArray2[0] = 15;
            nArray[0] = 0;
            nArray2[1] = 15;
            nArray[1] = 65536;
            nArray2[2] = 15;
            nArray[2] = 65536;
            nArray2[3] = 15;
            nArray[3] = 65536;
        } else if (by2 == 10) {
            string3 = bm.d(0, 28);
            string = bm.d(0, 28);
            n4 = 6;
        } else if (by2 == 24) {
            string3 = bm.A(357);
            string = bm.A(1249);
            n4 = 8;
        } else if (by2 == 26) {
            string3 = bm.A(1250);
            string = bm.A(1251);
            n4 = 9;
            nArray[0] = 2;
        } else if (by2 == 27) {
            string3 = bm.A(504);
            string = bm.A(1252);
            n4 = 9;
            nArray[0] = 2;
        } else if (by2 == 25) {
            string3 = bm.A(1253);
            string = bm.A(1254);
            n4 = 80;
        } else if (by2 == 11) {
            string3 = bm.A(1255);
            string = bm.A(1251);
            n4 = 16;
            nArray[0] = 2;
        } else if (by2 == 12) {
            string3 = bm.A(1256);
            string = bm.A(1251);
            n4 = 16;
            nArray[0] = 2;
        } else if (by2 == 13) {
            string3 = bm.A(1257);
            string = bm.A(1258);
            n4 = 3;
        } else if (by2 == 14) {
            string3 = bm.d(0, 52);
            string = bm.d(0, 53);
            n4 = 40;
        } else if (by2 == 15) {
            string3 = bm.A(1259);
            string = bm.A(1260);
            n4 = 12;
        } else if (by2 == 18) {
            n5 = 2;
            nArray = new int[2];
            string3 = bm.d(0, 232);
            String[] stringArray6 = new String[2];
            stringArray2 = stringArray6;
            stringArray6[0] = bm.d(0, 234);
            stringArray2[1] = bm.d(0, 235);
            int[] nArray5 = new int[2];
            nArray2 = nArray5;
            nArray5[0] = 6;
            nArray[0] = 0;
            nArray2[1] = 50;
            nArray[1] = 0;
        } else if (by2 == 19) {
            string3 = bm.d(0, 135);
            string = bm.d(0, 163);
            n4 = 50;
        } else if (by2 == 20) {
            string3 = bm.A(1261);
            string = bm.A(1259);
            n4 = 12;
        } else if (by2 == 21) {
            string3 = bm.A(1262);
            string = bm.A(283);
            n4 = 20;
        } else if (by2 == 22) {
            string3 = bm.A(544);
            string = bm.A(1263);
            n4 = 16;
            nArray[0] = 2;
        } else if (by2 == 23) {
            n4 = 32;
        } else if (by2 == 51) {
            stringArray2 = stringArray;
            n5 = stringArray.length;
            nArray2 = new int[n5];
            nArray = new int[n5];
            for (int i2 = 0; i2 < n5; ++i2) {
                nArray2[i2] = 32;
                nArray[i2] = 0;
            }
        } else if (by2 == 50) {
            n4 = 15;
        }
        d = new Form(string3);
        if (k != null) {
            d.append((Item)k);
        }
        e = new TextField[n5];
        if (n5 > 1 || by2 == 51 && n5 > 0) {
            for (int i3 = 0; i3 < n5; ++i3) {
                bc.e[i3] = new TextField(stringArray2[i3], null, nArray2[i3], nArray[i3]);
            }
        } else if (n5 != 0) {
            bc.e[0] = new TextField(string, null, n4, nArray[0]);
        }
        i = new Command(bm.A(295), 4, 0);
        j = new Command(bm.A(237), 2, 0);
        a = n5 == 0 ? null : new String[n5];
        for (n3 = 0; n3 < n5; ++n3) {
            d.append((Item)e[n3]);
            bc.a[n3] = null;
        }
        if (by2 == 14) {
            g = new Command(bm.A(1270), 2, 0);
            h = new Command(bm.A(1271), 2, 0);
            d.addCommand(h);
            d.addCommand(g);
            bc.b();
            bc.a(bt.a(bt.i[bt.e], bt.l[bt.e], bt.k[bt.e], bt.o[bt.e]));
            e[0].setString(bt.b);
        } else if (by2 == 52) {
            cl.a();
            q = new ChoiceGroup(bm.A(1272), 4, cl.a, null);
            r = new ChoiceGroup(bm.A(1273), 4, cl.b, null);
            d.append((Item)q);
            d.append((Item)r);
            if (cl.g == 1 || cl.g == 2) {
                s = new ChoiceGroup(bm.A(1274), 4, cl.c, null);
                d.append((Item)s);
            }
            if (cl.g == 2) {
                t = new ChoiceGroup(bm.A(1275), 4, cl.d, null);
                d.append((Item)t);
            }
            if (cl.g == 3) {
                t = new ChoiceGroup(bm.A(1275), 4, cl.e, null);
                d.append((Item)t);
                u = new ChoiceGroup(bm.A(1276), 4, cl.f, null);
                d.append((Item)u);
            }
            d.setItemStateListener((ItemStateListener)m);
        } else if (by2 == 25) {
            String string4 = "";
            for (by2 = 0; bd.d != null && by2 < bd.d.length; by2 = (byte)(by2 + 1)) {
                string4 = string4 + bd.d[by2];
            }
            e[0].setString(string4);
        } else if (by2 == 53) {
            e[0].setString("1");
            e[1].setString("0");
            e[2].setString("0");
        } else if (by2 == 54) {
            n3 = cl.r[bm.dW] / 10;
            if (cl.r[bm.dW] == 0) {
                n3 = 1;
            }
            String string5 = "" + (cl.s[bm.dW] + n3);
            e[0].setString(string5);
        }
        d.addCommand(i);
        d.addCommand(j);
        m = new bc();
        d.setCommandListener((CommandListener)m);
        bm.X = 3;
        bm.G();
        aq.i = false;
        aq.c();
        CMidlet.s_display.setCurrent((Displayable)d);
        if (aq.h) {
            CMidlet.s_game.hideNotify();
        }
    }

    public final void commandAction(Command command, Displayable object) {
        if (command == h) {
            bt.c();
            bt.b = e[0].getString();
            bc.a();
        } else if (command == g) {
            bt.d();
            bt.b = e[0].getString();
            bc.a();
        } else if (command == i) {
            int n2;
            Object object2 = "";
            for (n2 = 0; n2 < e.length; ++n2) {
                if (l != 25 && l != 52 && l != 2 && l != 64 && (e[n2].getString() == null || e[n2].getString().trim().equals(""))) {
                    object2 = bm.A(1280);
                    break;
                }
                if ((e[n2].getConstraints() & 2) != 0 && !bg.b(e[n2].getString())) {
                    object2 = bm.A(1281);
                    break;
                }
                if (e[n2].getString() != null) {
                    object = e[n2].getString().getBytes();
                    for (int i2 = 0; i2 < e[n2].getString().length(); ++i2) {
                        if (object[i2] != 94 && object[i2] != 39 && object[i2] != 92 && object[i2] != 37) continue;
                        object2 = bm.A(1282);
                        break;
                    }
                    bc.a[n2] = e[n2].getString();
                    continue;
                }
                bc.a[n2] = "";
            }
            if (((String)object2).equals("")) {
                int n3;
                if (l == 2 || l == 6) {
                    String string = a[0];
                    object = a[1];
                    String string2 = a[2];
                    if (l == 6) {
                        object = a[2];
                        string2 = a[3];
                    }
                    if (string.length() < 6) {
                        object2 = bm.A(730);
                    } else if (!((String)object).equals(string2)) {
                        object2 = bm.d(0, 74);
                    } else if (!cs.a(string) || !cs.a((String)object)) {
                        object2 = bm.A(728);
                    } else if (((String)object).length() < 6) {
                        object2 = bm.A(729);
                    }
                } else if (l == 53 && (n2 = Integer.parseInt(a[1])) >= (n3 = Integer.parseInt(a[2])) && n3 != 0) {
                    object2 = bm.A(1285);
                }
            }
            if (!((String)object2).equals("")) {
                f.setString((String)object2);
                f.setTimeout(5000);
                f.setType(AlertType.ALARM);
                CMidlet.s_display.setCurrent(f, (Displayable)d);
                return;
            }
            n = true;
            bc.a();
            bm.X = 3;
        } else if (command == j) {
            if (l != -1) {
                switch (l) {
                    case 14: {
                        break;
                    }
                    case 18: {
                        bm.H();
                        break;
                    }
                    case 13: {
                        bm.H();
                    }
                }
            }
            bt.b = "";
            bc.a();
        }
        bm.G();
    }

    private static void a() {
        cp.b();
        CMidlet.s_game.showNotify();
        if (n && l != -1) {
            boolean bl2 = false;
            String string = "";
            if (a != null) {
                string = a[0];
            }
            switch (l) {
                case 28: {
                    int n2 = k.getSelectedIndex();
                    bm.bn.d(n2, string);
                    bm.d(bm.A(1287));
                    return;
                }
                case 24: {
                    bd.c = string;
                    return;
                }
                case 25: {
                    bd.d = bg.a(string, 400, " ,.?!");
                    return;
                }
                case 26: {
                    try {
                        bd.e = Integer.valueOf(string);
                        return;
                    }
                    catch (Exception exception) {
                        bd.e = 0;
                        return;
                    }
                }
                case 27: {
                    try {
                        bd.g = Integer.valueOf(string);
                        return;
                    }
                    catch (Exception exception) {
                        bd.g = 0;
                        return;
                    }
                }
                case 11: {
                    try {
                        int n3 = Integer.valueOf(string);
                        bm.bn.a((byte)1, n3);
                        return;
                    }
                    catch (Exception exception) {
                        return;
                    }
                }
                case 12: {
                    try {
                        int n4 = Integer.valueOf(string);
                        bm.bn.a((byte)0, n4);
                        return;
                    }
                    catch (Exception exception) {
                        return;
                    }
                }
                case 10: {
                    am.k = string;
                    return;
                }
                case 0: {
                    cs.b = string;
                    return;
                }
                case 1: {
                    cs.c = string;
                    return;
                }
                case 60: {
                    bm.y(5);
                    ag.b = 7;
                    return;
                }
                case 61: {
                    bm.y(5);
                    ag.b = 8;
                    return;
                }
                case 62: {
                    bm.y(5);
                    ag.b = 9;
                    return;
                }
                case 63: {
                    bm.y(5);
                    ag.b = 10;
                    return;
                }
                case 2: {
                    bm.y(9);
                    cs.m = 11;
                    return;
                }
                case 3: {
                    cs.d = string;
                    bm.y(9);
                    cs.m = 12;
                    return;
                }
                case 6: {
                    bm.y(9);
                    cs.m = 1;
                    return;
                }
                case 18: {
                    w.e = a[0];
                    w.f = a[1];
                    w.a(1);
                    return;
                }
                case 19: {
                    bm.bn.b(bm.cb.C, string);
                    return;
                }
                case 20: {
                    return;
                }
                case 14: {
                    String string2;
                    boolean bl3;
                    String string3 = string;
                    if (string3.equals("/FPS")) {
                        bm.do = !bm.do;
                        bl3 = true;
                    } else if (string3.equals("/PING")) {
                        bm.dq = !bm.dq;
                        bl3 = true;
                    } else {
                        bl3 = false;
                    }
                    if (bl3) break;
                    int n5 = 0;
                    n5 = bt.e != 6 ? ((string2 = o.getString(o.getSelectedIndex())).equals(bm.d(0, 42)) ? 1 : (string2.equals(bm.d(0, 46)) ? 2 : (string2.equals(bm.d(0, 49)) ? 3 : (string2.equals(bm.d(0, 48)) ? 4 : (string2.equals(bm.d(0, 45)) ? 5 : 0))))) : 6;
                    if (n5 == 2 && cp.d() - bt.a < 30000L || n5 == 6 && cp.d() - bt.a < 1000L || cp.d() - bt.a < 10000L) {
                        bm.c(bm.A(1288));
                        return;
                    }
                    bm.bn.a(c, (byte)n5, string);
                    c = 0;
                    bt.b = "";
                    bt.a = cp.d();
                    return;
                }
                case 21: {
                    return;
                }
                case 13: {
                    bm.bn.c((int)cv.s, string);
                    return;
                }
                case 22: {
                    try {
                        cn.H = Integer.valueOf(a[0]);
                        return;
                    }
                    catch (Exception exception) {
                        return;
                    }
                }
                case 15: {
                    return;
                }
                case 23: {
                    bm.bn.e(string);
                    return;
                }
                case 51: {
                    if (a == null || a.length <= 0) break;
                    bm.bn.a(a);
                    return;
                }
                case 52: {
                    cl.b();
                    if (a[0] != null) {
                        cl.h = a[0].trim();
                    }
                    cl.i = (byte)q.getSelectedIndex();
                    cl.j = cl.a(r.getSelectedIndex());
                    if (cl.g == 1 || cl.g == 2) {
                        cl.l = cl.b(s.getSelectedIndex());
                    }
                    if (cl.g == 2) {
                        cl.m = cl.c(t.getSelectedIndex());
                    } else if (cl.g == 3) {
                        cl.m = cl.d(t.getSelectedIndex());
                    }
                    if (cl.g == 3) {
                        cl.n = cl.e(u.getSelectedIndex());
                    }
                    bm.ad();
                    bm.bn.a(cl.g, cl.h, cl.i, cl.k, 0, (byte)5, cl.l, cl.m, cl.n, cl.j, -1);
                    return;
                }
                case 53: {
                    int n6 = Integer.parseInt(a[0]);
                    int n7 = Integer.parseInt(a[1]);
                    int n8 = Integer.parseInt(a[2]);
                    if (n7 >= n8 && n8 != 0) break;
                    bm.bn.a(cn.w.c, n6, n7, n8);
                    return;
                }
                case 54: {
                    int n9 = Integer.parseInt(a[0]);
                    int n10 = cl.o[bm.dW];
                    bm.bn.h(n10, n9);
                    if (l.d == 8) {
                        bm.bn.a(cl.g, cl.h, cl.i, cl.k, 0, (byte)5, cl.l, cl.m, cl.n, cl.j, -1);
                    } else if (l.d == 11) {
                        bm.bn.e(0, (byte)5);
                    }
                    bm.ad();
                }
            }
        }
    }

    private static void b() {
        int n2;
        Vector<String> vector = new Vector<String>();
        switch (bt.e) {
            case 0: 
            case 1: {
                vector.addElement(bm.d(0, 42));
                vector.addElement(bm.d(0, 46));
                vector.addElement(bm.d(0, 49));
                vector.addElement(bm.d(0, 48));
                vector.addElement(bm.d(0, 45));
                break;
            }
            case 2: {
                vector.addElement(bm.d(0, 46));
                vector.addElement(bm.d(0, 42));
                vector.addElement(bm.d(0, 49));
                vector.addElement(bm.d(0, 48));
                vector.addElement(bm.d(0, 45));
                break;
            }
            case 3: {
                vector.addElement(bm.d(0, 49));
                vector.addElement(bm.d(0, 42));
                vector.addElement(bm.d(0, 46));
                vector.addElement(bm.d(0, 48));
                vector.addElement(bm.d(0, 45));
                break;
            }
            case 4: {
                vector.addElement(bm.d(0, 48));
                vector.addElement(bm.d(0, 42));
                vector.addElement(bm.d(0, 46));
                vector.addElement(bm.d(0, 49));
                vector.addElement(bm.d(0, 45));
                break;
            }
            case 5: {
                vector.addElement(bm.d(0, 45));
                vector.addElement(bm.d(0, 42));
                vector.addElement(bm.d(0, 46));
                vector.addElement(bm.d(0, 49));
                vector.addElement(bm.d(0, 48));
            }
        }
        if (!cr.j()) {
            vector.removeElement(bm.d(0, 45));
        }
        if (bm.cb.ab == 0) {
            vector.removeElement(bm.d(0, 48));
        }
        if (vector.size() == 0) {
            return;
        }
        String[] stringArray = new String[vector.size()];
        for (int i2 = 0; i2 < vector.size(); ++i2) {
            stringArray[i2] = (String)vector.elementAt(i2);
        }
        o = new ChoiceGroup(bm.A(1289), 4, stringArray, null);
        d.append((Item)o);
        String[] stringArray2 = bt.a(bt.j[2], bt.m[2], bt.n[2], bt.p[2]);
        int n3 = stringArray2.length + bt.z.length;
        if (n3 > 10) {
            n3 = 10;
        }
        stringArray = new String[n3];
        for (n2 = 0; n2 < n3 - bt.z.length; ++n2) {
            stringArray[n2] = stringArray2[n2];
        }
        n2 = n3 - 1;
        n3 = 0;
        while (n3 < bt.z.length) {
            stringArray[n2] = bm.d(0, bt.z[n3]);
            ++n3;
            --n2;
        }
        p = new ChoiceGroup(bm.A(1290), 4, stringArray, null);
        d.append((Item)p);
        d.setItemStateListener((ItemStateListener)m);
    }

    private static void a(String[] stringArray) {
        if (stringArray == null) {
            return;
        }
        for (int i2 = 0; i2 < stringArray.length; ++i2) {
            d.append(bt.a(stringArray[i2]) + "\n");
        }
    }

    public final void itemStateChanged(Item item) {
        if (item == p) {
            int n2 = p.getSelectedIndex();
            e[0].setString(p.getString(n2));
        }
    }

    static {
        f = new Alert("");
        c = 0;
        n = false;
        o = null;
        p = null;
        q = null;
        r = null;
        s = null;
        t = null;
        u = null;
    }
}

