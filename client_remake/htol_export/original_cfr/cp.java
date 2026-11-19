/*
 * Decompiled with CFR 0.152.
 */
public final class cp
extends Thread {
    private static cp a;
    private boolean b = false;
    private static boolean c;
    private long d = 0L;

    public static synchronized void a() {
        if (!cp.c()) {
            a = new cp();
            a.start();
            al.b();
        }
    }

    public static void b() {
        try {
            if (a != null) {
                cp.a.b = false;
                while (c) {
                    try {
                        Thread.sleep(10L);
                    }
                    catch (Exception exception) {}
                }
                bm.Y = 3;
                bm.G();
                bm.P();
                c = false;
                try {
                    a.join();
                }
                catch (Exception exception) {}
            }
        }
        catch (Exception exception) {
            a = null;
        }
        a = null;
    }

    public static boolean c() {
        if (a == null) {
            return false;
        }
        return c;
    }

    public static long d() {
        if (a == null) {
            return System.currentTimeMillis();
        }
        return 0L;
    }

    public final void run() {
        this.b = true;
        c = true;
        while (this.b) {
            try {
                if (CMidlet.s_game == null) break;
                try {
                    bm.x(1);
                }
                catch (Exception exception) {
                    // empty catch block
                }
                bm.y();
            }
            catch (Exception exception) {
                Exception exception2 = exception;
                exception.printStackTrace();
            }
            try {
                Thread.sleep(100L);
            }
            catch (Exception exception) {}
        }
        a = null;
        c = false;
        try {
            this.join();
        }
        catch (Exception exception) {}
        bm.X = 3;
        CMidlet.s_game.e();
    }

    static {
        c = false;
    }
}

