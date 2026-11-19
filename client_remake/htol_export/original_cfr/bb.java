/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.io.Connector
 *  javax.wireless.messaging.Message
 *  javax.wireless.messaging.MessageConnection
 *  javax.wireless.messaging.TextMessage
 */
import javax.microedition.io.Connector;
import javax.wireless.messaging.Message;
import javax.wireless.messaging.MessageConnection;
import javax.wireless.messaging.TextMessage;

final class bb
extends Thread {
    private String b;
    private String c;
    private boolean d;
    MessageConnection a;

    bb(String string, String string2, boolean bl2) {
        this.b = string;
        this.c = string2;
        this.d = bl2;
    }

    public final void run() {
        try {
            String string = "sms://" + ((bb)object).b;
            if (((bb)object).d) {
                string = string + e.a;
            }
            ((bb)object).a = (MessageConnection)Connector.open((String)string);
            e.a(string);
            string = (TextMessage)((bb)object).a.newMessage("text");
            string.setPayloadText(((bb)object).c);
            ((bb)object).a.send((Message)string);
            e.a("Content:" + ((bb)object).c);
            ((bb)object).a.close();
            if (((bb)object).d) {
                ((bb)object).a = (MessageConnection)Connector.open((String)("sms://" + e.a));
                Object object = ((bb)object).a.receive();
                object = ((TextMessage)object).getPayloadText();
                e.a("Response:" + (String)object);
            }
            return;
        }
        catch (Exception exception) {
            return;
        }
    }
}

