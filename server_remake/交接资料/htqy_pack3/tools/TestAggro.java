import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 怪物仇恨+追击实测工具。
 * 流程：登录进场景 → 找一只可见怪 → 移动到它射程内攻击一次 →
 * 监听服务端下发的 604(追击移动包) 和 1225(玩家掉血红字)，验证怪会追击并反击。
 * 用法：java -Dghost.host=127.0.0.1 -cp "classes;../classes" TestAggro
 */
public class TestAggro {
    static class Client {
        Socket sock; InputStream in; OutputStream out;
        int accId, roleId; String name; int lastCmd; byte[] lastBody;
        Client(String name){this.name=name;}

        void switchMap(int mapId,int x,int y) throws IOException {
            ByteArrayOutputStream b=new ByteArrayOutputStream();
            b.write(i32(mapId)); b.write(new byte[]{0,0});
            send(524,b.toByteArray());
        }
        void teleport(int x,int y) throws IOException { moveTo(x,y); }

        void login() throws Exception {
            String host=System.getProperty("ghost.host","127.0.0.1");
            sock=new Socket(host,28000); sock.setSoTimeout(6000);
            in=sock.getInputStream(); out=sock.getOutputStream();
            send(265,new byte[]{0,0,0}); send(257,loginBody(name));
            if(!expect(258,12)) throw new Exception("登录服失败");
            byte[] b=lastBody;
            accId=(b[1]&0xFF)|((b[2]&0xFF)<<8)|((b[3]&0xFF)<<16)|((b[4]&0xFF)<<24);
            sock.close();
            sock=new Socket(host,2890); sock.setSoTimeout(6000);
            in=sock.getInputStream(); out=sock.getOutputStream();
            send(513,enterGameBody());
            if(!expect(514,12)) throw new Exception("进服失败");
            roleId=firstRoleId(lastBody);
            if(roleId==0){ send(518,createBody(name,1)); expect(518,12); send(514,i32(0)); expect(514,12); roleId=firstRoleId(lastBody); }
            send(523,zoneBody(roleId)); expect(517,12); expect(521,20);
            send(525,new byte[0]); expect(2112,12);
            send(2116,i32(1)); drain(600);
        }
        byte[] enterGameBody() throws IOException {
            ByteArrayOutputStream b=new ByteArrayOutputStream();
            b.write(i32(accId)); str32(b,"KEY"+accId); str32(b,""); b.write(1); b.write(i32(11400));
            return b.toByteArray();
        }
        void moveTo(int x,int y) throws IOException {
            // 602: u16 x | u16 y
            send(602,new byte[]{(byte)x,(byte)(x>>8),(byte)y,(byte)(y>>8)});
        }
        void attack(int targetId,int skillId,int x,int y) throws IOException {
            ByteArrayOutputStream b=new ByteArrayOutputStream();
            b.write(i32(targetId)); b.write(i16(skillId)); b.write(i16(x)); b.write(i16(y));
            send(601,b.toByteArray());
        }
        // 监听 ms 毫秒，统计 604 和 1225 包
        int[] listenMoveHurt(int ms){
            int move=0,hurt=0,atk=0;
            try{
                sock.setSoTimeout(ms);
                while(true){
                    recv();
                    if(lastCmd==604) move++;
                    else if(lastCmd==1225) hurt++;
                    else if(lastCmd==2108) atk++;
                }
            }catch(SocketTimeoutException te){}catch(Exception ignore){}
            finally{ try{sock.setSoTimeout(6000);}catch(Exception e){} }
            return new int[]{move,hurt,atk};
        }
        // 收集可见怪：2100(id) / 2104(id+坐标)
        Map<Integer,int[]> collectMonsters(int ms){
            Map<Integer,int[]> m=new LinkedHashMap<>();
            try{
                sock.setSoTimeout(ms);
                while(true){
                    recv();
                    if(lastCmd==2112){ // 进场景广播，里面可能带怪
                        parseNpcs(lastBody,m);
                    } else if(lastCmd==2104){
                        // u32 id | u32 zoneId | u16 x | u16 y | ...
                        if(lastBody.length>=12){
                            int id=readI32(lastBody,0);
                            int x=(lastBody[8]&0xFF)|((lastBody[9]&0xFF)<<8);
                            int y=(lastBody[10]&0xFF)|((lastBody[11]&0xFF)<<8);
                            m.put(id,new int[]{x,y});
                        }
                    } else if(lastCmd==2100){
                        if(lastBody.length>=4){ int id=readI32(lastBody,0); m.put(id,new int[]{0,0}); }
                    }
                }
            }catch(SocketTimeoutException te){}catch(Exception ignore){}
            finally{ try{sock.setSoTimeout(6000);}catch(Exception e){} }
            return m;
        }
        void parseNpcs(byte[] b,Map<Integer,int[]> m){
            // 尽力解析：扫描可能的 id+坐标三元组（id 5xxxx，坐标 <2000）
            for(int i=0;i+8<=b.length;i+=4){
                int id=readI32(b,i);
                if(id>=50000 && id<51000){
                    int x=i+4<b.length?((b[i+4]&0xFF)|((b[i+5]&0xFF)<<8)):0;
                    int y=i+6<b.length?((b[i+6]&0xFF)|((b[i+7]&0xFF)<<8)):0;
                    if(x>0&&x<2000&&y>0&&y<2000) m.put(id,new int[]{x,y});
                }
            }
        }
        void drain(int ms){ try{sock.setSoTimeout(ms);}catch(Exception e){} }
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
        Client c=new Client("aggro"+seed);
        System.out.println("登录 "+c.name+" ...");
        c.login(); System.out.println("  roleId="+c.roleId);

        // 切到野区「桃花小径」resId=1，那里有 50 个刷点
        int mapId=args.length>0?Integer.parseInt(args[0]):1;
        System.out.println("切到野区地图 resId="+mapId);
        c.switchMap(mapId,300,300); c.drain(1200);
        c.send(2116,i32(1)); c.drain(800);

        // 收集可见怪
        Map<Integer,int[]> mons=c.collectMonsters(2000);
        System.out.println("\n可见怪数量: "+mons.size());
        if(mons.isEmpty()){
            System.out.println("未发现可见怪，尝试走动后再收集");
            c.moveTo(300,300); c.drain(500);
            mons=c.collectMonsters(1500);
            System.out.println("再次收集: "+mons.size());
        }
        Map.Entry<Integer,int[]> target=null;
        for(Map.Entry<Integer,int[]> e:mons.entrySet()){ if(e.getValue()[0]>0){target=e;break;} }
        if(target==null && !mons.isEmpty()) target=mons.entrySet().iterator().next();
        if(target==null){ System.out.println("仍无怪，退出"); c.sock.close(); return; }

        int mid=target.getKey(); int mx=target.getValue()[0], my=target.getValue()[1];
        System.out.println("选中怪 id="+mid+" 坐标="+mx+","+my);

        // 走到怪的射程内（怪坐标 -60px，仍在视野内），只打 1 次建立仇恨（不秒杀）
        int px=Math.max(50,mx-60), py=Math.max(50,my);
        System.out.println("移动到玩家坐标 "+px+","+py+" 攻击 1 次建立仇恨");
        c.moveTo(px,py); c.drain(400);
        c.attack(mid,0,px,py);
        Thread.sleep(500);
        // 后撤到怪的右下 200px（<800 脱战线，但超出贴身，怪应追过来）
        int rx=mx+120, ry=my+120;
        System.out.println("后撤到 "+rx+","+ry+"，观察怪是否追击");
        c.moveTo(rx,ry);

        // 监听 6 秒，看是否收到追击包(604)和玩家掉血(1225)
        System.out.println("\n监听服务端下发(6秒)...");
        int[] r=c.listenMoveHurt(6000);
        System.out.println("  604 追击移动包: "+r[0]);
        System.out.println("  1225 玩家掉血 : "+r[1]);
        System.out.println("  2108 攻击动作 : "+r[2]);
        System.out.println("\n========== 结论 ==========");
        System.out.println("  怪物追击: "+(r[0]>0?"✅ 收到604追击包":"❌ 无追击包"));
        System.out.println("  怪物反击: "+(r[1]>0?"✅ 玩家掉血":"⚠ 未掉血(可能怪未贴近/在被击杀)"));
        c.sock.close();
    }

    static byte[] loginBody(String name) throws IOException {
        ByteArrayOutputStream b=new ByteArrayOutputStream();
        str32(b,name); byte[] md5=new byte[16]; for(int i=0;i<16;i++)md5[i]=(byte)(i*7+3);
        b.write(i32(md5.length)); b.write(md5); b.write(i32(11400));
        return b.toByteArray();
    }
    static byte[] createBody(String name,int nation) throws IOException {
        ByteArrayOutputStream b=new ByteArrayOutputStream();
        b.write(0);b.write(0);b.write(nation);b.write(0);
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
    static byte[] i16(int v){return new byte[]{(byte)v,(byte)(v>>8)};}
    static int readI32(byte[] b,int o){return (b[o]&0xFF)|((b[o+1]&0xFF)<<8)|((b[o+2]&0xFF)<<16)|((b[o+3]&0xFF)<<24);}
    static void str32(ByteArrayOutputStream b,String s)throws IOException{byte[] d=s.getBytes(StandardCharsets.UTF_8);b.write(i32(d.length));b.write(d);}
}
