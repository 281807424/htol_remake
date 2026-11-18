/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

abstract class j {
    private boolean a;
    private int[] b;

    public j(int[] nArray) {
        boolean bl2;
        this.b = nArray;
        v0.a = bl2 = false;
    }

    final void a(Graphics graphics) {
        Object object = this;
        if (!((j)object).a) {
            return;
        }
        object = graphics;
        int[] nArray = new int[4];
        int[] nArray2 = nArray;
        nArray[0] = object.getClipX();
        nArray2[1] = object.getClipY();
        nArray2[2] = object.getClipWidth();
        nArray2[3] = object.getClipHeight();
        object = nArray2;
        j.a(graphics, this.b);
        this.b(graphics);
        j.a(graphics, (int[])object);
    }

    private static void a(Graphics graphics, int[] nArray) {
        graphics.setClip(nArray[0], nArray[1], nArray[2], nArray[3]);
    }

    abstract void b(Graphics var1);

    abstract void a();

    final boolean b() {
        return this.a;
    }

    final void a(boolean bl2) {
        this.a = bl2;
    }
}

