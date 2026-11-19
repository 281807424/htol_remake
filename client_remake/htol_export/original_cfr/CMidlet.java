/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Display
 *  javax.microedition.midlet.MIDlet
 */
import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;

public class CMidlet
extends MIDlet {
    public static CMidlet s_midlet = null;
    public static bm s_game = null;
    public static Display s_display;

    public CMidlet() {
        s_midlet = this;
    }

    public void pauseApp() {
        if (s_game != null) {
            aq.c();
        }
    }

    public void startApp() {
        s_display = Display.getDisplay((MIDlet)this);
        if (s_game == null) {
            s_game = new bm((Object)this, s_display);
            s_game.b();
        }
        s_game.d();
    }

    public void destroyApp(boolean bl2) {
        s_game = null;
        this.notifyDestroyed();
    }

    public static void exitApp() {
        s_midlet.destroyApp(true);
        s_midlet.notifyDestroyed();
    }
}

