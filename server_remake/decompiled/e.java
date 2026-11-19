/*
 * Decompiled with CFR 0.152.
 */
import java.util.Vector;

public final class e {
    public static String a = ":3388";
    private static bb b;
    private static Vector c;

    public static void a(String string, String string2, boolean bl2) {
        if (b != null) {
            try {
                e.b.a.close();
            }
            catch (Exception exception) {
                Exception exception2 = exception;
                exception.printStackTrace();
            }
            b = null;
        }
        b = new bb(string, string2, false);
        b.start();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void a(String string) {
        Vector vector = c;
        synchronized (vector) {
            c.addElement(string);
            return;
        }
    }

    static {
        c = new Vector();
    }
}

