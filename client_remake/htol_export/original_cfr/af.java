/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.rms.RecordStore
 */
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.microedition.rms.RecordStore;

final class af {
    af() {
    }

    static void a() {
        try {
            RecordStore recordStore = RecordStore.openRecordStore((String)"HTQY10006", (boolean)true);
            int n2 = recordStore.getNumRecords();
            if (n2 != 12) {
                if (n2 != 0) {
                    RecordStore.deleteRecordStore((String)"HTQY10006");
                }
                for (n2 = 0; n2 < 12; ++n2) {
                    recordStore.addRecord(new byte[0], 0, 0);
                }
                af.a(true, 3);
                af.a(true, 4);
                af.a(true, 5);
            }
            recordStore.closeRecordStore();
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    private static boolean a(int n2) {
        boolean bl2 = true;
        try {
            RecordStore recordStore = RecordStore.openRecordStore((String)"HTQY10006", (boolean)true);
            if (recordStore.getRecordSize(n2) == 0 || recordStore.getRecord(n2) == null) {
                bl2 = false;
            }
            recordStore.closeRecordStore();
        }
        catch (Exception exception) {
            bl2 = false;
        }
        return bl2;
    }

    static boolean a(boolean bl2, int n2) {
        if (!bl2 && !af.a(n2)) {
            return false;
        }
        boolean bl3 = true;
        try {
            RecordStore recordStore = RecordStore.openRecordStore((String)"HTQY10006", (boolean)true);
            switch (n2) {
                case 1: {
                    if (bl2) {
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
                        try {
                            dataOutputStream.writeInt(65535);
                            byte[] byArray = bm.a(cs.b.getBytes());
                            dataOutputStream.writeByte(byArray.length);
                            dataOutputStream.write(byArray, 0, byArray.length);
                            dataOutputStream.writeBoolean(cs.f);
                            byte[] byArray2 = bm.a(cs.c.getBytes());
                            dataOutputStream.writeByte(byArray2.length);
                            dataOutputStream.write(byArray2, 0, byArray2.length);
                            dataOutputStream.writeInt(cs.e + 1);
                        }
                        catch (IOException iOException) {
                            IOException iOException2 = iOException;
                            iOException.printStackTrace();
                        }
                        recordStore.setRecord(n2, byteArrayOutputStream.toByteArray(), 0, byteArrayOutputStream.toByteArray().length);
                        byteArrayOutputStream.close();
                        dataOutputStream.close();
                        break;
                    }
                    Object object = new ByteArrayInputStream(recordStore.getRecord(n2));
                    DataInputStream dataInputStream = new DataInputStream((InputStream)object);
                    try {
                        int n3 = dataInputStream.readInt();
                        if (n3 != 65535) {
                            ((ByteArrayInputStream)object).close();
                            dataInputStream.close();
                            object = new ByteArrayInputStream(recordStore.getRecord(n2));
                            dataInputStream = new DataInputStream((InputStream)object);
                            cs.b = dataInputStream.readUTF();
                            cs.f = dataInputStream.readBoolean();
                            cs.c = dataInputStream.readUTF();
                            cs.e = dataInputStream.readInt();
                            break;
                        }
                        byte by2 = dataInputStream.readByte();
                        object = new byte[by2];
                        dataInputStream.read((byte[])object, 0, by2);
                        cs.b = new String(bm.a((byte[])object));
                        cs.f = dataInputStream.readBoolean();
                        by2 = dataInputStream.readByte();
                        object = new byte[by2];
                        dataInputStream.read((byte[])object, 0, by2);
                        cs.c = new String(bm.a((byte[])object));
                        cs.e = dataInputStream.readInt();
                    }
                    catch (Exception exception) {}
                    break;
                }
                case 5: {
                    if (bl2) {
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
                        try {
                            dataOutputStream.writeInt(am.o);
                            dataOutputStream.writeInt(cs.a[1]);
                            dataOutputStream.writeByte(cs.a[2]);
                        }
                        catch (IOException iOException) {
                            IOException iOException3 = iOException;
                            iOException.printStackTrace();
                        }
                        recordStore.setRecord(n2, byteArrayOutputStream.toByteArray(), 0, byteArrayOutputStream.toByteArray().length);
                        byteArrayOutputStream.close();
                        dataOutputStream.close();
                        break;
                    }
                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(recordStore.getRecord(n2));
                    DataInputStream dataInputStream = new DataInputStream(byteArrayInputStream);
                    am.o = dataInputStream.readInt();
                    cs.a[1] = dataInputStream.readInt();
                    cs.a[2] = dataInputStream.readByte();
                    if (cs.a[2] == 0) {
                        bm.bz = false;
                        break;
                    }
                    bm.bz = true;
                    break;
                }
                case 2: {
                    if (bl2) {
                        recordStore.setRecord(n2, am.e, 0, am.e.length);
                        break;
                    }
                    recordStore.getRecord(n2, am.e, 0);
                    break;
                }
                case 6: {
                    if (bl2) {
                        recordStore.setRecord(n2, cs.o, 0, cs.o.length);
                        break;
                    }
                    recordStore.getRecord(n2, cs.o, 0);
                    bm.p = cs.o[0] == 1;
                    am.q = true;
                    break;
                }
                case 3: {
                    if (bl2) {
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
                        for (int i2 = 0; i2 < 3; ++i2) {
                            ((OutputStream)dataOutputStream).write(a.c[i2]);
                        }
                        recordStore.setRecord(n2, byteArrayOutputStream.toByteArray(), 0, byteArrayOutputStream.toByteArray().length);
                        byteArrayOutputStream.close();
                        dataOutputStream.close();
                        break;
                    }
                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(recordStore.getRecord(n2));
                    DataInputStream dataInputStream = new DataInputStream(byteArrayInputStream);
                    if (dataInputStream.available() == 36) {
                        for (int i3 = 0; i3 < 3; ++i3) {
                            dataInputStream.read(a.c[i3]);
                        }
                    }
                    byteArrayInputStream.close();
                    dataInputStream.close();
                    break;
                }
                case 4: {
                    if (bl2) {
                        recordStore.setRecord(n2, ag.e, 0, ag.e.length);
                        break;
                    }
                    recordStore.getRecord(n2, ag.e, 0);
                    break;
                }
                case 7: {
                    int n4;
                    if (bl2) {
                        Object var0_7 = null;
                        DataOutputStream dataOutputStream = null;
                        int n5 = 0;
                        int[] nArray = new int[5];
                        for (int i4 = 0; i4 < 5; ++i4) {
                            nArray[i4] = -1;
                        }
                        byte[] byArray = recordStore.getRecord(n2);
                        if (byArray != null) {
                            InputStream inputStream = new ByteArrayInputStream(byArray);
                            inputStream = new DataInputStream(inputStream);
                            n5 = ((DataInputStream)inputStream).readByte();
                            for (int i5 = 0; i5 < 5; ++i5) {
                                nArray[i5] = ((DataInputStream)inputStream).readInt();
                            }
                            ((FilterInputStream)inputStream).close();
                            ((FilterInputStream)inputStream).close();
                        }
                        nArray[n5] = bm.ci;
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        dataOutputStream = new DataOutputStream(byteArrayOutputStream);
                        int n6 = (n5 + 1) % 5;
                        dataOutputStream.writeByte((byte)n6);
                        for (n6 = 0; n6 < 5; ++n6) {
                            dataOutputStream.writeInt(nArray[n6]);
                        }
                        recordStore.setRecord(n2, byteArrayOutputStream.toByteArray(), 0, byteArrayOutputStream.toByteArray().length);
                        byteArrayOutputStream.close();
                        dataOutputStream.close();
                        byteArrayOutputStream = new ByteArrayOutputStream();
                        dataOutputStream = new DataOutputStream(byteArrayOutputStream);
                        dataOutputStream.writeInt(bm.bR);
                        dataOutputStream.writeInt(bm.bS);
                        dataOutputStream.writeInt(bm.bO.length);
                        ((OutputStream)dataOutputStream).write(bm.bO);
                        dataOutputStream.writeInt(bm.bP.length);
                        ((OutputStream)dataOutputStream).write(bm.bP);
                        dataOutputStream.writeInt(bm.bQ.length);
                        ((OutputStream)dataOutputStream).write(bm.bQ);
                        dataOutputStream.writeInt(bm.bZ.length);
                        ((OutputStream)dataOutputStream).write(bm.bZ);
                        recordStore.setRecord(n5 + 7 + 1, byteArrayOutputStream.toByteArray(), 0, byteArrayOutputStream.toByteArray().length);
                        byteArrayOutputStream.close();
                        dataOutputStream.close();
                        break;
                    }
                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(recordStore.getRecord(n2));
                    DataInputStream dataInputStream = new DataInputStream(byteArrayInputStream);
                    int n7 = -1;
                    dataInputStream.readByte();
                    for (n4 = 0; n4 < 5; ++n4) {
                        if (bm.ci != dataInputStream.readInt()) continue;
                        n7 = n4 + 1;
                        break;
                    }
                    byteArrayInputStream.close();
                    dataInputStream.close();
                    if (n7 != -1) {
                        byteArrayInputStream = new ByteArrayInputStream(recordStore.getRecord(n2 + n7));
                        dataInputStream = new DataInputStream(byteArrayInputStream);
                        bm.bR = dataInputStream.readInt();
                        bm.bS = dataInputStream.readInt();
                        bm.bT = bm.bR * 24;
                        bm.bU = bm.bS * 24;
                        n4 = dataInputStream.readInt();
                        bm.bO = new byte[n4];
                        dataInputStream.read(bm.bO);
                        n4 = dataInputStream.readInt();
                        bm.bP = new byte[n4];
                        dataInputStream.read(bm.bP);
                        n4 = dataInputStream.readInt();
                        bm.bQ = new byte[n4];
                        dataInputStream.read(bm.bQ);
                        n4 = dataInputStream.readInt();
                        bm.bZ = new byte[n4];
                        dataInputStream.read(bm.bZ);
                        byteArrayInputStream.close();
                        dataInputStream.close();
                        break;
                    }
                    bl3 = false;
                }
            }
            recordStore.closeRecordStore();
        }
        catch (Exception exception) {
            bl3 = false;
        }
        return bl3;
    }
}

