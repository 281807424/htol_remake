import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 聊天系统双客户端实测：A、B 两个幽灵玩家进场景，A 分别发世界/国家/私聊，
 * B 监听 769 包并解码，验证服务端转发是否真的送达。
 * 用法：java -Dghost.host=127.0.0.1 -cp .:../classes TestChat
 */
public class TestChat {
    static class Client {
        Socket sock; InputStream in; OutputStream out;
        int accId, roleId, nation;
        String name;
        int lastCmd; byte[] lastBody;

        Client(String name) { this.name = name; }

        void login() throws Exception {
            String host = System.getProperty("ghost.host", "127.0.0.1");
            sock = new Socket(host, 28000); sock.setSoTimeout(6000);
            in = sock.getInputStream(); out = sock.getOutputStream();
            send(265, new byte[]{0,0,0});
            send(257, loginBody(name));
            if (!expect(258, 12)) throw new Exception(name+" 登录服失败");
            byte[] b = lastBody;
            accId = (b[1]&0xFF)|((b[2]&0xFF)<<8)|((b[3]&0xFF)<<16)|((b[4]&0xFF)<<24);
            sock.close();

            sock = new Socket(host, 2890); sock.setSoTimeout(6000);
            in = sock.getInputStream(); out = sock.getOutputStream();
            send(513, enterGameBody());
            if (!expect(514, 12)) throw new Exception(name+" 进服失败");

            roleId = firstRoleId(lastBody);
            if (roleId == 0) {
                send(518, createBody(name, 1));   // 国家=1，保证 A/B 同国
                expect(518, 12);
                send(514, i32(0)); expect(514, 12);
                roleId = firstRoleId(lastBody);
            }
            send(523, zoneBody(roleId)); expect(517, 12); expect(521, 20);
            send(525, new byte[0]); expect(2112, 12);
            send(2116, i32(1)); drain(600);
        }

        byte[] enterGameBody() throws IOException {
            ByteArrayOutputStream b=new ByteArrayOutputStream();
            b.write(i32(accId));
            str32(b,"KEY"+accId);
            str32(b,"");
            b.write(1);
            b.write(i32(11400));
            return b.toByteArray();
        }

        void sendChat(int ch, int targetId, String text) throws Exception {
            // 真实客户端布局：u8频道 | i32目标 | u8物品位 | i32长度 | UTF8文本(含6个^装饰字段)
            String payload = text + "^0^0^0^0^0^0";   // 11段，过 CheckChat
            byte[] tb = payload.getBytes(StandardCharsets.UTF_8);
            ByteArrayOutputStream cb = new ByteArrayOutputStream();
            cb.write(ch & 0xFF);
            cb.write(i32(targetId));
            cb.write(0);                              // u8 物品位
            cb.write(i32(tb.length));
            cb.write(tb);
            send(769, cb.toByteArray());
        }

        // 监听 ms 毫秒内收到的 769 聊天包，返回解码后的 "频道|发送者名|内容"
        List<String> listenChat(int ms) {
            List<String>got = new ArrayList<>();
            int old = 6000;
            try {
                sock.setSoTimeout(ms);
                while (true) {
                    recv();
                    if (lastCmd == 769) {
                        // 下发包体：u8频道 | u32 id | str8 名 | str16 内容 | u64 时间
                        int off=0;
                        int ch = lastBody[off++] & 0xFF;
                        off += 4; // id
                        int nl = lastBody[off++] & 0xFF;
                        String from = new String(lastBody,off,nl,"UTF-8"); off+=nl;
                        int tl = (lastBody[off]&0xFF)|((lastBody[off+1]&0xFF)<<8); off+=2;
                        String text = new String(lastBody,off,tl,"UTF-8");
                        got.add("频道"+ch+"|"+from+"|"+text);
                    }
                }
            } catch (SocketTimeoutException te) {
            } catch (Exception ignore) {
            } finally { try{sock.setSoTimeout(old);}catch(Exception e){} }
            return got;
        }

        void drain(int ms) { try{sock.setSoTimeout(ms);}catch(Exception e){} }
        boolean expect(int cmd,int max){ try{for(int i=0;i<max;i++){recv();if(lastCmd==cmd)return true;}}catch(Exception e){} return false; }
        void recv() throws IOException {
            byte[] h=new byte[4]; readN(h,4);
            for(int i=0;i<4;i++)h[i]=(byte)(h[i]-htqysrv.KeyMap.KEY[i&255]);
            int len=(h[0]&0xFF)|((h[1]&0xFF)<<8);
            int cmd=(h[2]&0xFF)|((h[3]&0xFF)<<8);
            byte[] body=new byte[Math.max(0,len-4)];
            if(body.length>0)readN(body,body.length);
            for(int i=0;i<body.length;i++)body[i]=(byte)(body[i]-htqysrv.KeyMap.KEY[(i+4)&255]);
            lastCmd=cmd; lastBody=body;
        }
        void send(int cmd,byte[] body) throws IOException {
            int len=4+body.length; byte[] b=new byte[len];
            b[0]=(byte)len;b[1]=(byte)(len>>8);b[2]=(byte)cmd;b[3]=(byte)(cmd>>8);
            System.arraycopy(body,0,b,4,body.length);
            for(int i=0;i<len;i++)b[i]=(byte)(b[i]+htqysrv.KeyMap.KEY[i&255]);
            out.write(b);out.flush();
        }
        void readN(byte[] b,int n) throws IOException { int o=0;while(o<n){int r=in.read(b,o,n-o);if(r<0)throw new EOFException();o+=r;} }
    }

    public static void main(String[] args) throws Exception {
        long seed=System.currentTimeMillis()%100000;
        Client a=new Client("chtA"+seed), b=new Client("chtB"+seed);
        System.out.println("登录 A/B ...");
        a.login(); System.out.println("  A roleId="+a.roleId+" accId="+a.accId);
        b.login(); System.out.println("  B roleId="+b.roleId+" accId="+b.accId);
        Thread.sleep(800);

        System.out.println("\n[测试1] 世界频道 频道=1");
        a.sendChat(1,0,"WORLD-TEST-"+seed);
        List<String> r1=b.listenChat(2500);
        System.out.println("  B 收到 "+r1.size()+" 条: "+r1);

        System.out.println("\n[测试2] 国家频道 频道=2");
        a.sendChat(2,0,"NATION-TEST-"+seed);
        List<String> r2=b.listenChat(2500);
        System.out.println("  B 收到 "+r2.size()+" 条: "+r2);

        System.out.println("\n[测试3] 私聊 频道=6 targetId="+b.roleId);
        a.sendChat(6,b.roleId,"PRIVATE-TEST-"+seed);
        List<String> r3=b.listenChat(2500);
        System.out.println("  B 收到 "+r3.size()+" 条: "+r3);

        System.out.println("\n========== 结论 ==========");
        System.out.println("  世界频道: "+(r1.isEmpty()?"❌ 未送达":"✅ 送达"));
        System.out.println("  国家频道: "+(r2.isEmpty()?"❌ 未送达":"✅ 送达"));
        System.out.println("  私聊    : "+(r3.isEmpty()?"❌ 未送达":"✅ 送达"));
        a.sock.close(); b.sock.close();
    }

    static byte[] loginBody(String name) throws IOException {
        ByteArrayOutputStream b=new ByteArrayOutputStream();
        str32(b,name);
        byte[] md5=new byte[16]; for(int i=0;i<16;i++)md5[i]=(byte)(i*7+3);
        b.write(i32(md5.length)); b.write(md5); b.write(i32(11400));
        return b.toByteArray();
    }
    static byte[] createBody(String name,int nation) throws IOException {
        ByteArrayOutputStream b=new ByteArrayOutputStream();
        b.write(0); b.write(0); b.write(nation); b.write(0);
        byte[] n=name.getBytes(StandardCharsets.UTF_8); byte[] fixed=new byte[33];
        System.arraycopy(n,0,fixed,0,Math.min(n.length,32)); b.write(fixed);
        return b.toByteArray();
    }
    static byte[] zoneBody(int id) throws IOException {
        ByteArrayOutputStream b=new ByteArrayOutputStream();
        b.write(i32(id)); b.write(i32(-1)); b.write(i32(0));
        return b.toByteArray();
    }
    static int firstRoleId(byte[] body){ if(body==null||body.length<45)return 0;int c=body[0]&0xFF;if(c<=0)return 0;int off=5+1+33+1+1;return (body[off]&0xFF)|((body[off+1]&0xFF)<<8)|((body[off+2]&0xFF)<<16)|((body[off+3]&0xFF)<<24);}
    static byte[] i32(int v){return new byte[]{(byte)v,(byte)(v>>8),(byte)(v>>16),(byte)(v>>24)};}
    static void str32(ByteArrayOutputStream b,String s)throws IOException{byte[] d=s.getBytes(StandardCharsets.UTF_8);b.write(i32(d.length));b.write(d);}
}
