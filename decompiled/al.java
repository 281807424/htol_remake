/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.media.Manager
 *  javax.microedition.media.Player
 */
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import javax.microedition.media.Manager;
import javax.microedition.media.Player;

public final class al
implements bj {
    private static Player[] c;
    private static byte[][] d;
    private static int[] e;
    private static int[] f;
    private static int g;
    private static int[] h;
    private static int[] i;
    private static boolean j;
    public static boolean a;
    public static boolean b;
    private static int k;
    private static int l;
    private static int m;
    private static int n;
    private static boolean o;

    private static Player a(byte[] object) {
        try {
            String string = "audio/midi";
            if (object[0] == 82 && object[1] == 73 && object[2] == 70 && object[3] == 70) {
                string = "audio/x-wav";
            } else if (object[0] == 35 && object[1] == 33 && object[2] == 65 && object[3] == 77 && object[4] == 82) {
                string = "audio/amr";
            }
            Player player = Manager.createPlayer((InputStream)new ByteArrayInputStream((byte[])object), (String)string);
            object = player;
            player.realize();
            return object;
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            return null;
        }
    }

    public static void a() {
        if (j) {
            return;
        }
        if (!j) {
            c = new Player[20];
            d = new byte[20][];
        }
        aq.b("/2");
        try {
            int n2;
            for (n2 = 0; n2 < 20; ++n2) {
                int n3;
                if (c[n2] != null || (n3 = n2) < 0) continue;
                byte[] byArray = aq.c(n3);
                al.d[n2] = byArray;
                if (byArray.length <= 0) continue;
                al.c[n2] = al.a(d[n2]);
                if (c[n2] != null) continue;
                String cfr_ignored_0 = "Sound not created right" + n2;
            }
            for (n2 = 0; n2 < 2; ++n2) {
                al.h[n2] = -1;
            }
            j = true;
        }
        catch (Exception exception) {
            j = false;
        }
        aq.h();
        al.e();
    }

    private static void e() {
        int n2;
        e = new int[20];
        f = new int[20];
        for (n2 = 0; n2 < 20; ++n2) {
            al.e[n2] = 1;
            al.f[n2] = 2;
        }
        for (n2 = 0; n2 < 5; ++n2) {
            if (n2 >= 0) {
                al.e[n2] = -1;
            }
            if (n2 < 0) continue;
            al.f[n2] = 0;
        }
    }

    public static void b() {
        for (int i2 = 0; i2 < 2; ++i2) {
            al.i[i2] = -1;
            if (!al.d(h[i2])) continue;
            al.i[i2] = h[i2];
        }
        al.c();
    }

    private static void b(int n2) {
        block5: {
            if (n2 > -1 && c[n2] != null) {
                try {
                    if (c[n2].getState() == 400) {
                        c[n2].stop();
                    }
                    if (c[n2].getState() == 300) {
                        c[n2].deallocate();
                    }
                    return;
                }
                catch (Exception exception) {
                    bm.a(false, "Stop sound exception");
                    exception.printStackTrace();
                    if (c[n2] == null) break block5;
                    c[n2].close();
                }
            }
        }
    }

    private static void c(int n2) {
        al.b(h[n2]);
        al.h[n2] = -1;
    }

    public static void c() {
        if (!j) {
            return;
        }
        for (int i2 = 0; i2 < 2; ++i2) {
            al.c(i2);
        }
    }

    private static boolean d(int n2) {
        if (n2 > -1 && c[n2] != null) {
            try {
                if (c[n2].getState() == 400) {
                    return true;
                }
            }
            catch (Exception exception) {
                Exception exception2 = exception;
                exception.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public static void a(int n2) {
        al.a(n2, false);
    }

    private static void a(int n2, boolean bl2) {
        boolean bl3;
        int n3;
        if (n2 < 0 || !j) {
            return;
        }
        if (n2 <= 4 && !a) {
            return;
        }
        if (n2 > 4 && !b) {
            return;
        }
        int n4 = 1;
        if (n2 <= 4) {
            n4 = 0;
        }
        if ((n3 = n4) > 1) {
            n3 = -1;
        }
        g = n3;
        n3 = h[g];
        if (n4 == 0 && n3 == n2) {
            try {
                if (c[n3].getState() == 400) {
                    return;
                }
            }
            catch (Exception exception) {
                Exception exception2 = exception;
                exception.printStackTrace();
            }
        }
        boolean bl4 = n3 >= 0 ? f[n3] > f[n2] : (bl3 = false);
        if (n3 < 0) {
            bl2 = true;
        } else if (bl3) {
            bl2 = true;
        } else {
            try {
                if (c[n3] == null || c[n3].getState() != 400) {
                    bl2 = true;
                }
            }
            catch (Exception exception) {
                bl2 = true;
            }
        }
        if (n3 >= 0 && bl2 && n3 != n2) {
            al.b(n3);
        }
        try {
            if (bl2) {
                if (n3 != n2) {
                    al.h[al.g] = n2;
                    try {
                        if (c[n2].getState() == 100) {
                            c[n2].realize();
                        }
                    }
                    catch (Exception exception) {
                        c[n2].realize();
                    }
                    try {
                        if (c[n2].getState() == 200) {
                            c[n2].realize();
                        }
                    }
                    catch (Exception exception) {
                        c[n2].prefetch();
                    }
                    c[n2].setLoopCount(e[n2]);
                }
                c[n2].start();
            }
            return;
        }
        catch (Exception exception) {
            Exception exception3 = exception;
            exception.printStackTrace();
            return;
        }
    }

    public static void a(int n2, int n3, int n4) {
        al.a(n2, true);
    }

    public static void d() {
        if (o) {
            if (++l >= k) {
                al.c(1);
                o = false;
                return;
            }
            if (m <= l) {
                al.a(n, false);
            }
        }
    }

    static {
        int[] nArray = new int[]{2, 2, 2, 4, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3};
        g = -1;
        h = new int[2];
        i = new int[2];
        j = false;
        a = false;
        b = false;
    }
}

