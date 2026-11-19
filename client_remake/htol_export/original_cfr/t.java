/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import java.util.Vector;
import javax.microedition.lcdui.Graphics;

public final class t {
    public static Vector a = new Vector();
    public static Vector b = new Vector();
    public static bs c = new bs();
    public static Vector d = new Vector();
    public int e = -1;
    public int f;
    private byte y;
    public byte g;
    public byte h;
    public int i;
    public int j;
    public int k;
    public String l;
    public String m;
    public int n = 0;
    public int o = 0;
    public int p = 0;
    public int q = 0;
    public int r = 0;
    public int s = 0;
    public Vector t = new Vector();
    public int u;
    public Vector v = new Vector();
    public Vector w = new Vector();
    public static Vector x = null;

    public static void a() {
        bg.a(b, 0, b.size() - 1, 1);
    }

    public static void b() {
        for (int i2 = 0; i2 < a.size(); ++i2) {
            t t2 = (t)a.elementAt(i2);
            if (t2 == null) continue;
            t2.i = t2.e() ? (byte)(1 << 7 + t2.g) : t2.g;
        }
        bg.a(a, 0, a.size() - 1, 5);
    }

    public static t a(int n2) {
        t t2 = null;
        int n3 = a.size();
        for (int i2 = 0; i2 < n3; ++i2) {
            t t3 = (t)a.elementAt(i2);
            if (t3 == null || t3.e != n2) continue;
            t2 = t3;
            break;
        }
        return t2;
    }

    public static void b(int n2) {
        int n3 = a.size();
        for (int i2 = 0; i2 < n3; ++i2) {
            t t2 = (t)a.elementAt(i2);
            if (t2 == null || t2.e != n2) continue;
            a.removeElementAt(i2);
            return;
        }
    }

    public static void c() {
        a.removeAllElements();
        b.removeAllElements();
    }

    public static void a(int n2, String string, int n3, int n4) {
        boolean bl2 = false;
        for (int i2 = 0; i2 < d.size(); ++i2) {
            cd cd2 = (cd)d.elementAt(i2);
            if (cd2 == null || cd2.a != string || cd2.e != n3 || cd2.f != n4) continue;
            bl2 = true;
            break;
        }
        if (!bl2) {
            cd cd3 = new cd();
            new cd().a = string;
            cd3.e = n3;
            cd3.f = n4;
            cd3.d = n2;
            d.addElement(cd3);
        }
    }

    public final void a(q q2) {
        int n2;
        int n3;
        t t2 = this;
        this.e = -1;
        t2.y = 0;
        t2.i = 0;
        t2.l = "";
        t2.m = "";
        t2.t.removeAllElements();
        t2.v.removeAllElements();
        this.e = q2.c();
        this.f = q2.c();
        this.y = q2.a();
        this.g = q2.a();
        this.j = q2.d();
        this.k = q2.d();
        short s2 = 0;
        s2 = q2.a();
        this.l = q2.a(0, s2);
        s2 = q2.c();
        String string = q2.a(0, s2);
        this.m = bg.c(string, "$");
        int n4 = q2.a();
        for (n3 = 0; n3 < n4; ++n3) {
            ap ap2 = new ap(this, 0);
            new ap(this, 0).c = q2.d();
            byte by2 = q2.a();
            ap2.a = q2.a(0, by2);
            ap2.d = q2.b();
            ap2.e = q2.b();
            this.t.addElement(ap2);
        }
        n3 = q2.a();
        if (n3 > 0) {
            for (int i2 = 0; i2 < n3; ++i2) {
                ap ap3 = new ap(this, 1);
                new ap(this, 1).c = q2.d();
                byte by3 = q2.a();
                ap3.a = q2.a(0, by3);
                ap3.d = 0;
                ap3.e = q2.b();
                this.t.addElement(ap3);
            }
        }
        this.u = q2.a();
        this.h = q2.a();
        if (this.h < bn.a.length && this.h >= 0) {
            this.m = bg.a(this.m, bm.A(783), bn.a[this.h]);
        }
        if ((n2 = q2.a()) < 6 && n2 > 0) {
            for (n4 = 0; n4 < n2; ++n4) {
                cd cd2 = new cd();
                byte by4 = q2.a();
                cd2.a = q2.a(0, by4);
                cd2.c = q2.a();
                cd2.d = q2.c();
                cd2.e = q2.c();
                cd2.f = q2.c();
                this.v.addElement(cd2);
            }
        }
        this.n = q2.e();
        this.s = q2.e();
        this.o = q2.e();
        this.p = q2.e();
        this.q = q2.e();
        this.r = q2.e();
        n4 = q2.a();
        this.w.removeAllElements();
        for (n3 = 0; n3 < n4; ++n3) {
            q2.a();
            q2.d();
            byte by5 = q2.a();
            String string2 = q2.a(0, by5);
            n2 = q2.d();
            this.w.addElement(string2 + " x " + n2);
        }
    }

    public final void a(byte by2, int n2, int n3, boolean bl2) {
        int n4;
        int n5 = n2;
        n2 = by2;
        t t2 = this;
        int n6 = -1;
        for (int i2 = 0; i2 < t2.t.size(); ++i2) {
            if (n2 != ((ap)t2.t.elementAt((int)i2)).b || n5 != ((ap)t2.t.elementAt((int)i2)).c) continue;
            n6 = i2;
            break;
        }
        if ((n4 = n6) >= 0 && n4 < this.t.size()) {
            if (bm.ac()) {
                bm.ae();
            }
            ap ap2 = (ap)this.t.elementAt(n4);
            if (ap2.d < n3) {
                ap2.f = bl2;
                t.a(this);
                bm.be = true;
                bm.y = true;
            }
            ap2.d = n3;
            if (ap2.d == ap2.e) {
                cd cd2;
                block1: for (n2 = 0; n2 < this.t.size(); ++n2) {
                    ap ap3 = (ap)this.t.elementAt(n2);
                    if (ap3.d >= ap3.e) continue;
                    for (n2 = 0; n2 < this.v.size(); ++n2) {
                        cd cd3 = (cd)this.v.elementAt(n2);
                        boolean bl3 = cd3.a.equals(ap3.a);
                        n5 = bl3 ? 1 : 0;
                        if (!bl3) {
                            for (n6 = 0; n6 < cd3.b.size(); ++n6) {
                                String string = (String)cd3.b.elementAt(n6);
                                if (!string.equals(ap3.a)) continue;
                                n5 = 1;
                                break;
                            }
                        }
                        if (n5 != 1) continue;
                        if (!bm.D(0x100000) || d.size() <= 0) break block1;
                        String string = "";
                        string = string + (ap2.b == 0 ? "^r" + ap2.a + "^r" : "^b" + ap2.a + "^b");
                        string = string + (ap2.b == 0 ? bm.A(784) : bm.A(785));
                        string = string + bm.A(786);
                        string = string + (ap3.b == 0 ? bg.d(bm.A(787), ap3.a) : bg.d(bm.A(788), ap3.a));
                        bm.a(string, (int)this.h, cd3.d, (short)cd3.e, (short)cd3.f, cd3.c);
                        break block1;
                    }
                    break;
                }
                if (this.e() && (cd2 = (cd)this.v.elementAt(0)) != null) {
                    if (bm.D(0x100000) && d.size() > 0) {
                        String string = bg.d(bm.A(789), cd2.a);
                        bm.a(string, (int)this.h, cd2.d, (short)cd2.e, (short)cd2.f, cd2.c);
                    }
                    String string = bg.b(bm.A(790), this.l, cd2.a);
                    bm.a(string, true);
                }
            }
        }
    }

    public final void d() {
        cd cd2;
        if (this.e() && (cd2 = (cd)this.v.elementAt(0)) != null) {
            String string;
            if (bm.D(0x100000) && d.size() > 0) {
                string = bg.d(bm.A(789), cd2.a);
                bm.a(string, (int)this.h, cd2.d, (short)cd2.e, (short)cd2.f, cd2.c);
            }
            string = bg.d(bm.A(790), cd2.a);
            bm.a(string, true);
        }
    }

    public final void a(boolean bl2) {
        if (bm.aA == null) {
            return;
        }
        for (int i2 = 0; i2 < this.t.size(); ++i2) {
            ap ap2 = (ap)this.t.elementAt(i2);
            if (ap2.b != 1) continue;
            this.a((byte)1, ap2.c, bm.aA.d(ap2.c), bl2);
        }
    }

    public final boolean e() {
        boolean bl2 = true;
        for (int i2 = 0; i2 < this.t.size(); ++i2) {
            ap ap2 = (ap)this.t.elementAt(i2);
            int n2 = 0;
            if (ap2.b == 0) {
                n2 = ap2.d;
            } else if (ap2.b == 1) {
                n2 = bm.aA.d(ap2.c);
            }
            if (n2 >= ap2.e) continue;
            bl2 = false;
            break;
        }
        return bl2 && this.u == 1;
    }

    public final boolean f() {
        boolean bl2 = (this.y & 8) > 0;
        return bl2;
    }

    public final boolean g() {
        boolean bl2 = (this.y & 1) > 0;
        return bl2;
    }

    public static String a(int n2, boolean bl2) {
        String string = bl2 ? bm.A(791) : bm.A(792);
        switch (n2) {
            case 0: {
                string = bl2 ? bm.A(793) : bm.A(195);
                break;
            }
            case 1: {
                string = bl2 ? bm.A(794) : bm.A(196);
                break;
            }
            case 2: {
                string = bl2 ? bm.A(795) : bm.A(197);
                break;
            }
            case 3: {
                string = bl2 ? bm.A(796) : bm.A(797);
                break;
            }
            case 4: {
                string = bl2 ? bm.A(798) : bm.A(783);
                break;
            }
            case 5: {
                string = bl2 ? bm.A(799) : bm.A(800);
                break;
            }
            case 6: {
                string = bl2 ? bm.A(801) : bm.A(802);
                break;
            }
            case 7: {
                string = bl2 ? bm.A(803) : bm.A(804);
            }
        }
        return string;
    }

    public static void a(int n2, int n3) {
        if (!cn.g) {
            int n4 = a.size();
            for (int i2 = 0; i2 < n4; ++i2) {
                t t2 = (t)a.elementAt(i2);
                if (t2 == null) continue;
                t2.a((byte)1, n2, n3, true);
            }
        }
    }

    public static void a(t t2) {
        boolean bl2 = true;
        for (int i2 = 0; i2 < bm.x.size(); ++i2) {
            t t3 = (t)bm.x.elementAt(i2);
            if (t3 == null || t2.e != t3.e) continue;
            bl2 = false;
            break;
        }
        if (bl2) {
            bm.x.addElement(t2);
        }
    }

    public static void a(Graphics graphics, int n2, int n3) {
        if (x != null && x.size() > 0) {
            n2 = x.size();
            n3 = graphics.getClipX();
            int n4 = graphics.getClipY();
            int n5 = graphics.getClipWidth();
            int n6 = graphics.getClipHeight();
            graphics.setClip(515, 115, 80, n2 * 35);
            bz.a(graphics, 0xFFF000, -16777216, 4);
            for (int i2 = 0; i2 < n2; ++i2) {
                aq.b(graphics, 515, 115 + i2 * 35, 80, 25);
                cd cd2 = (cd)x.elementAt(i2);
                if (cd2 == null) continue;
                bz.a(graphics, cd2.a, 515, 123 + i2 * 35);
            }
            graphics.setClip(n3, n4, n5, n6);
        }
    }
}

