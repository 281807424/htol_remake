/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public class h
implements bj {
    public int a = 3;
    public int b = 3;
    public short c;
    public ao d;
    public int e;
    public int f = -1;
    public int g;
    private int q;
    public int[] h = new int[4];
    public int[] i;
    public boolean j;
    public int k;
    public int l;
    public int m = 0;
    public int n = -1;
    public int o;
    public int p = 0;

    public final void a(int[] nArray) {
        if (!this.j) {
            this.i[0] = nArray[0];
            this.i[1] = nArray[1];
            this.i[2] = nArray[2];
            this.i[3] = nArray[3];
            this.j = true;
            return;
        }
        this.i = aq.a(this.i, nArray);
    }

    public h() {
    }

    public h(short[] sArray, int n2) {
        this.a = 3;
        this.k = (sArray[2] << 1) / 3;
        this.l = (sArray[3] << 1) / 3;
        this.c = sArray[0];
        this.e = sArray[5];
        this.o = sArray[7];
        this.p = n2 >= 9 ? sArray[8] : 0;
        if (this.c != -1) {
            this.d = ck.b(bm.i(this.c));
        }
        if (this.d == null) {
            bg.a("SPR ID:" + this.c + " NULL!");
        }
        if (sArray[4] < 0 || sArray[4] >= this.d.a()) {
            this.a(0);
            bg.a("set to actorType:" + this.c + " anim:" + sArray[4] + " is invlaid! please check!");
        } else {
            this.a(sArray[4]);
        }
        try {
            this.a();
            if (this.d.c(this.f) <= 1 && this.o <= 0) {
                this.i = new int[4];
                bm.et.addElement(this);
                return;
            }
            bm.ca.addElement(this);
        }
        catch (Exception exception) {
            bg.a("CreateActorError SPR = " + this.c + " Ani=" + this.f + " Frame=" + this.g);
        }
    }

    final void a() {
        this.d.a(this.h, this.f, this.g, this.k * 3 >> 1, this.l * 3 >> 1, this.e);
    }

    void a(Graphics graphics, int n2, int n3) {
        if (this.j) {
            Graphics graphics2 = graphics;
            h h2 = this;
            this.i = aq.b(h2.i, h2.h);
            int n4 = h2.i[0] - (bm.bW * 3 >> 1);
            int n5 = h2.i[1] - (bm.bX * 3 >> 1);
            int n6 = h2.i[2] - h2.i[0];
            int n7 = h2.i[3] - h2.i[1];
            graphics2.setClip(n4, n5, n6, n7);
        }
        n2 = 0 + (n2 * 3 >> 1);
        n3 = 0 + (n3 * 3 >> 1);
        this.b(graphics, n2, n3);
        graphics.setClip(0, 0, 640, 360);
    }

    final void b(Graphics object, int n2, int n3) {
        try {
            int n4 = this.e;
            int n5 = n3;
            n3 = n2;
            Graphics graphics = object;
            object = this;
            if (((h)object).d != null && ((h)object).q >= 0) {
                if (((h)object).g()) {
                    ((h)object).d.b(((h)object).n);
                } else if (((h)object).n >= 1) {
                    ((h)object).d.b(((h)object).n);
                }
                int n6 = ((h)object).d.c();
                ((h)object).d.j(((h)object).p);
                ((h)object).d.a(graphics, ((h)object).f, ((h)object).g, n3, n5, n4, 0, 0);
                ((h)object).d.j(n6);
            }
            return;
        }
        catch (Exception exception) {
            bg.a("PaintActorError TYPE = " + this.a + "  SPR ID = " + this.c);
            return;
        }
    }

    final void a(int n2) {
        this.b(n2 += this.m);
    }

    final void b(int n2) {
        if (this.g()) {
            n2 += ((cr)this).i();
        }
        if (this.d != null && n2 >= this.d.a()) {
            bg.a("AniOutOfBounds " + n2 + "SprId = " + this.c);
            n2 = 0;
        }
        if (n2 != this.f) {
            this.f = n2;
            this.g = 0;
            this.q = 0;
            this.e &= 0xFFFFFFBF;
        }
    }

    public final boolean b() {
        return this.g >= this.d.c(this.f) - 1 && this.d.a(this.f, this.g) - 1 <= this.q;
    }

    final void c() {
        block6: {
            try {
                int n2 = this.d.a(this.f, this.g);
                this.a();
                if (n2 == 0) {
                    return;
                }
                ++this.q;
                if (n2 > this.q) {
                    return;
                }
                this.q = 0;
                ++this.g;
                if (this.g >= this.d.c(this.f)) {
                    if ((this.e & 0x40) != 0) {
                        --this.g;
                        break block6;
                    }
                    this.g = 0;
                }
                return;
            }
            catch (Exception exception) {}
        }
    }

    final void a(boolean bl2) {
        if (this.d == null) {
            return;
        }
        int n2 = this.d.a(this.f, this.g);
        this.a();
        if (n2 == 0) {
            return;
        }
        ++this.q;
        if (n2 > this.q) {
            return;
        }
        this.q = 0;
        ++this.g;
        if (this.g >= this.d.c(this.f)) {
            if ((this.e & 0x40) != 0) {
                --this.g;
                return;
            }
            if (bl2) {
                this.g = 0;
                return;
            }
            this.g = this.d.c(this.f) - 1;
        }
    }

    public final boolean d() {
        int n2 = this.h[0] - (bm.bW * 3 >> 1);
        int n3 = this.h[2] - (bm.bW * 3 >> 1);
        int n4 = this.h[1] - (bm.bX * 3 >> 1);
        int n5 = this.h[3] - (bm.bX * 3 >> 1);
        if (n3 <= 0 || n2 >= 639) {
            return false;
        }
        return n5 > 0 && n4 < 360;
    }

    public final boolean b(int[] nArray) {
        return h.a(nArray, this.h);
    }

    public static boolean a(int[] nArray, int[] nArray2) {
        return nArray[1] <= nArray2[3] && nArray[3] >= nArray2[1] && nArray[2] >= nArray2[0] && nArray[0] <= nArray2[2];
    }

    public final boolean e() {
        return this.a == 2;
    }

    public final boolean f() {
        return this.a == 1;
    }

    public final boolean g() {
        return this.a == 0;
    }
}

