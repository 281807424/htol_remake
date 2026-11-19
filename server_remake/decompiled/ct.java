/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.io.Connector
 *  javax.microedition.io.SocketConnection
 */
import java.io.DataInputStream;
import java.io.OutputStream;
import java.util.Vector;
import javax.microedition.io.Connector;
import javax.microedition.io.SocketConnection;

public final class ct
extends Thread {
    String a;
    int b;
    private String[] h;
    private int i;
    private boolean j;
    int c;
    boolean d;
    SocketConnection e;
    private DataInputStream k;
    boolean f = false;
    private OutputStream l;
    private Vector m = new Vector();
    private Vector n = new Vector();
    private static byte[] o = new byte[]{58, 116, 85, 43, 97, 68, 121, 46, 62, 112, 25, 76, 30, 13, 72, 30, 102, 9, 26, 51, 2, 44, 37, 16, 29, 63, 15, 105, 67, 94, 107, 82, 1, 122, 20, 39, 39, 65, 6, 91, 40, 14, 42, 98, 125, 99, 73, 52, 86, 62, 26, 118, 9, 120, 61, 36, 95, 59, 75, 108, 120, 91, 61, 76, 94, 111, 93, 60, 126, 76, 62, 55, 52, 18, 46, 14, 55, 63, 55, 27, 30, 66, 64, 94, 109, 21, 126, 123, 107, 69, 30, 75, 47, 121, 45, 92, 30, 125, 29, 108, 43, 80, 56, 4, 15, 74, 46, 34, 107, 111, 42, 74, 26, 77, 80, 109, 95, 30, 118, 57, 61, 74, 105, 89, 21, 1, 77, 57, 90, 87, 109, 81, 58, 125, 109, 40, 63, 43, 114, 76, 113, 110, 93, 27, 45, 60, 41, 40, 25, 85, 68, 60, 77, 74, 37, 93, 21, 42, 108, 42, 27, 95, 63, 25, 40, 95, 40, 59, 17, 47, 103, 95, 81, 78, 98, 25, 44, 62, 100, 50, 126, 70, 105, 8, 16, 36, 63, 102, 10, 29, 47, 62, 77, 64, 127, 91, 126, 69, 68, 93, 8, 27, 46, 71, 99, 62, 27, 112, 77, 87, 113, 93, 8, 92, 106, 100, 40, 29, 4, 72, 18, 34, 75, 56, 27, 76, 43, 93, 30, 75, 123, 41, 126, 111, 60, 124, 73, 41, 27, 56, 42, 103, 124, 79, 39, 58, 63, 76, 44, 42, 113, 37, 97, 27, 89, 29};
    private cx p;
    boolean g;

    public static int a(byte[] byArray) {
        if (bm.bn.f.f) {
            return bg.a(byArray, 0) & 0xFFFF;
        }
        return (short)((byArray[0] & 0xFF) << 8 | byArray[1] & 0xFF);
    }

    public static int b(byte[] byArray) {
        if (bm.bn.f.f) {
            return bg.a(byArray, 2) & 0xFFFF;
        }
        return (short)((byArray[2] & 0xFF) << 8 | byArray[3] & 0xFF);
    }

    ct(String string, boolean bl2, String[] stringArray) {
        this.j = bl2;
        this.a = string;
        this.b = 0;
        this.d = false;
        this.c = 0;
        this.h = stringArray;
        this.i = 0;
        this.start();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    final void a() {
        if (this.k == null) {
            return;
        }
        try {
            int n2;
            int n3;
            Object object = new byte[4];
            for (n3 = 0; n3 < 4; ++n3) {
                n2 = this.k.read();
                if (!this.g) {
                    return;
                }
                if (n2 == -1) {
                    object = this;
                    this.b = 3;
                    return;
                }
                object[n3] = (byte)n2;
                if (bm.bn == null) continue;
                bm.bn.d = bm.m();
            }
            this.a((byte[])object, 4, 2);
            n2 = (short)ct.a(object);
            short s2 = (short)ct.b(object);
            if (n2 < 4) {
                object = this;
                this.b = 3;
                return;
            }
            Object object2 = null;
            if (n2 > 4) {
                object2 = new byte[n2];
                System.arraycopy(object, 0, object2, 0, 4);
                for (n3 = 4; n3 < n2 && this.g; ++n3) {
                    int n4 = this.k.read();
                    if (!this.g) {
                        return;
                    }
                    if (n4 == -1) {
                        ct ct2 = this;
                        this.b = 3;
                        return;
                    }
                    if (bm.bn != null) {
                        bm.bn.d = bm.m();
                    }
                    object2[n3] = (byte)n4;
                }
            } else {
                object2 = object;
            }
            if (!this.g) {
                return;
            }
            this.a((byte[])object2, ((byte[])object2).length, 3);
            if (s2 == -257 && n2 == 8) {
                int n5 = bg.b(object2, 4);
                long l2 = aq.i();
                n5 = (int)l2 - n5;
                if (n5 < 0) {
                    n5 = 0;
                }
                bm.dr = n5;
                bm.A = l2;
                return;
            }
            String cfr_ignored_0 = "recv:" + s2 + "/" + n2;
            ct ct3 = this;
            Object object3 = object2;
            object = ct3;
            Vector vector = ct3.m;
            synchronized (vector) {
                object.m.addElement(object3);
            }
        }
        catch (Exception exception) {
            ct ct4 = this;
            this.b = 3;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void run() {
        this.d = true;
        while (this.d) {
            switch (this.b) {
                case 0: {
                    this.b = 1;
                    break;
                }
                case 1: {
                    Object object;
                    Object object2 = this;
                    try {
                        if (((ct)object2).e != null) {
                            try {
                                ((ct)object2).e.close();
                            }
                            catch (Exception exception) {
                                // empty catch block
                            }
                            ((ct)object2).e = null;
                            ((ct)object2).k = null;
                            ((ct)object2).l = null;
                        }
                        object = object2;
                        Vector vector = ((ct)object).m;
                        synchronized (vector) {
                            ((ct)object).m.removeAllElements();
                        }
                        vector = ((ct)object).n;
                        synchronized (vector) {
                            ((ct)object).n.removeAllElements();
                        }
                        ((ct)object2).b();
                        object = ((ct)object2).a;
                        ((ct)object2).e = (SocketConnection)Connector.open((String)object, (int)3, (boolean)false);
                        if (((ct)object2).e != null) {
                            ((ct)object2).f = !bm.p || !am.q;
                            ((ct)object2).e.setSocketOption((byte)2, 0);
                            ((ct)object2).e.setSocketOption((byte)0, 0);
                            ((ct)object2).e.setSocketOption((byte)1, 5);
                            ((ct)object2).k = ((ct)object2).e.openDataInputStream();
                            ((ct)object2).l = ((ct)object2).e.openOutputStream();
                            if (bm.bn != null) {
                                bm.bn.d = bm.m();
                            }
                            ((ct)object2).p = new cx((ct)object2);
                            ((ct)object2).p.start();
                            ((ct)object2).b = 2;
                            bm.z = 0L;
                            bm.A = 0L;
                            String cfr_ignored_0 = " OPEN  URL ..." + ((ct)object2).c;
                            break;
                        }
                        object = object2;
                        ((ct)object2).b = 3;
                    }
                    catch (Exception exception) {
                        object = object2;
                        ((ct)object2).b = 3;
                    }
                    break;
                }
                case 2: {
                    Object object;
                    Object object2 = this.h();
                    try {
                        if (object2 != null) {
                            this.l.write((byte[])object2, 0, ((Object)object2).length);
                            this.l.flush();
                        }
                        Thread.sleep(100L);
                    }
                    catch (Exception exception) {
                        object = this;
                        this.b = 3;
                    }
                    break;
                }
                case 3: {
                    this.b();
                    if (this.j) {
                        this.b = 1;
                        ++this.c;
                        try {
                            Thread.sleep(1000L);
                        }
                        catch (InterruptedException interruptedException) {}
                        break;
                    }
                    if (this.h != null && this.i < this.h.length - 1) {
                        this.b = 1;
                        this.a = this.h[++this.i].trim();
                        try {
                            Thread.sleep(1000L);
                        }
                        catch (InterruptedException interruptedException) {}
                        break;
                    }
                    this.d = false;
                }
            }
        }
        this.g = false;
        this.p = null;
        this.d = false;
    }

    public final void b() {
        this.g = false;
        this.p = null;
    }

    public final void c() {
        this.b = 3;
    }

    public final boolean d() {
        return this.b == 2 && this.f;
    }

    public final int e() {
        return this.c;
    }

    public final boolean f() {
        return this.b == 3;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final byte[] g() {
        Vector vector = this.m;
        synchronized (vector) {
            if (this.m.isEmpty()) {
                return null;
            }
            byte[] byArray = (byte[])this.m.elementAt(0);
            this.m.removeElementAt(0);
            return byArray;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private byte[] h() {
        Vector vector = this.n;
        synchronized (vector) {
            if (this.n.isEmpty()) {
                return null;
            }
            byte[] byArray = (byte[])this.n.elementAt(0);
            this.n.removeElementAt(0);
            return byArray;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final boolean a(byte[] object, int n2) {
        if (this.b != 2) {
            return false;
        }
        if (object == null) {
            return false;
        }
        if (n2 > ((byte[])object).length) {
            String cfr_ignored_0 = bm.A(870) + n2 + "/" + ((byte[])object).length;
            return false;
        }
        if (this.l == null) {
            return false;
        }
        try {
            int n3 = n2;
            Object object2 = object;
            object = this;
            byte[] byArray = new byte[n3];
            System.arraycopy(object2, 0, byArray, 0, n3);
            super.a(byArray, n3, 1);
            Vector vector = ((ct)object).n;
            object2 = vector;
            synchronized (vector) {
                ((ct)object).n.addElement(byArray);
                // ** MonitorExit[var2_4 /* !! */ ] (shouldn't be in output)
                return true;
            }
        }
        catch (Exception exception) {
            v2.b = 3;
            return false;
        }
    }

    private boolean a(byte[] byArray, int n2, int n3) {
        if (!this.f) {
            return false;
        }
        switch (n3) {
            case 1: {
                for (int i2 = 0; i2 < n2; ++i2) {
                    int n4 = i2;
                    byArray[n4] = (byte)(byArray[n4] + o[i2 & 0xFF]);
                }
                break;
            }
            case 2: {
                for (int i3 = 0; i3 < n2; ++i3) {
                    int n5 = i3;
                    byArray[n5] = (byte)(byArray[n5] - o[i3 & 0xFF]);
                }
                break;
            }
            case 3: {
                for (int i4 = 4; i4 < n2; ++i4) {
                    int n6 = i4;
                    byArray[n6] = (byte)(byArray[n6] - o[i4 & 0xFF]);
                }
                break;
            }
            default: {
                return false;
            }
        }
        return true;
    }
}

