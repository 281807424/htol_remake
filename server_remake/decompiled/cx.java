/*
 * Decompiled with CFR 0.152.
 */
final class cx
extends Thread {
    private final ct a;

    cx(ct ct2) {
        this.a = ct2;
    }

    public final void run() {
        this.a.g = true;
        while (this.a.g) {
            this.a.a();
            Thread.yield();
        }
        this.a.g = false;
    }
}

