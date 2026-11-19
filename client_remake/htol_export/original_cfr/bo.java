/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Image
 */
import java.util.Random;
import javax.microedition.lcdui.Image;

final class bo {
    private static Random a = new Random();

    bo() {
    }

    public static Image a(Image image, int n2) {
        int n3 = image.getWidth();
        int n4 = image.getHeight();
        int[] nArray = new int[n3 * n4];
        int[] nArray2 = new int[n3 * n4];
        image.getRGB(nArray, 0, n3, 0, 0, n3, n4);
        for (int i2 = 0; i2 < nArray.length; ++i2) {
            nArray2[i2] = nArray[i2] & n2;
        }
        return Image.createRGBImage((int[])nArray2, (int)n3, (int)n4, (boolean)true);
    }

    static boolean a(float f2, float f3) {
        return f2 > 0.0f && f3 > 0.0f || f2 < 0.0f && f3 < 0.0f || f2 == 0.0f || f3 == 0.0f;
    }

    public static int a(int n2) {
        if (n2 < 0) {
            n2 = -n2;
        }
        return n2;
    }

    public static float a(float f2) {
        if (f2 < 0.0f) {
            f2 = -f2;
        }
        return f2;
    }

    public static float b(float f2, float f3) {
        if (f2 == f3) {
            return f2;
        }
        if (f2 > f3) {
            return f2;
        }
        return f3;
    }

    public static int a() {
        return bo.a(a.nextInt());
    }
}

