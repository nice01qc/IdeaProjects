package websock;

import util.RedisTool;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;


@ServerEndpoint("/websocket")
public class WebSocket {

    private String room = "";
    volatile public static boolean status = true;
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static CopyOnWriteArraySet<WebSocket> webSocketSet = new CopyOnWriteArraySet<WebSocket>();
    private static ConcurrentHashMap<String,Set<String>> statusHashMap = new ConcurrentHashMap<String,Set<String>>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    // 连接建立成功调用的方法
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.add(this);     //加入set中
    }

    // 连接关闭调用的方法
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  //从set中删除
    }

    // 收到客户端消息后调用的方法
    @OnMessage
    public void onMessage(String message, Session session) throws IOException, InterruptedException {
        if (message.matches("^[0-9]+:no$")) {
            sendMessageByOut(this.room, message.replaceAll("no", "yes"));
            addToStatusHashMap(this.room,message.replaceAll(":no",""));
            return;
        }
        if (message.matches("^[0-9]+:yes$")) {
            sendMessageByOut(this.room, message.replaceAll("yes", "no"));
            delFromStatusHashMap(this.room,message.replaceAll(":yes",""));
            return;
        }
        String room = message.trim();
        if (!room.matches("^[0-9a-zA-Z]+$")) return;
        // 把房间号加入到redis
        RedisTool.setAddValueByKey("clientRoom", room);
        // 更新房间号
        ManageSocket.updateroom(room);
        this.room = room;
        String roomText = room + "text";
        String roomImg = room + "img";
        // 发送以存储的文字
        if (RedisTool.isExit(roomText)) {
            sendMessage("TextNum:" + RedisTool.getListLengthByKey(roomText)); // 更新信息数量
            List<String> listroom = RedisTool.getAllByKey(roomText);
            if (listroom != null && !listroom.isEmpty()) {
                for (String x : listroom) {
                    sendMessage(x);
                }
            }
        }
        // 发送以存储的图片
        if (RedisTool.isExit(roomImg)) {
            sendMessage("imgNum:" + RedisTool.getListLengthByKey(roomImg)); // 更新图片数量
            List<String> listroom = RedisTool.getAllByKey(roomImg);
            if (listroom != null && !listroom.isEmpty()) {
                for (String x : listroom) {
                    sendMessage(x);
                }
            }
        }
        // 恢复照片状态
        if (statusHashMap.containsKey(this.room)){
            Set<String> stringSet = statusHashMap.get(this.room);
            String result = "status";
            for (String x : stringSet){
                result += "-" + x;
            }
            sendMessage(result);
        }

    }

    // 发生错误时调用
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }

    // 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    // 外部调用方法
    synchronized public static void sendMessageByOut(String room, String message) throws InterruptedException {

        while (!status) Thread.currentThread().sleep(100);
        status = false;
        for (WebSocket item : webSocketSet) {
            if (!item.room.equals(room)) continue;
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
        status = true;
    }

    // 清理所有房间
    public static void clearAllRoom() throws IOException {
        for (WebSocket webSocket : webSocketSet) {
            webSocket.session.close();
        }
        webSocketSet.clear();
        RedisTool.delKey("clientRoom");
    }

    // 清理指定房间名All
    public static void clearOneRoom(String room) throws IOException {
        for (WebSocket webSocket : webSocketSet) {
            if (webSocket.room.equals(room)) {
                webSocketSet.remove(webSocket);
                webSocket.session.close();
                break;
            }
        }
        if (RedisTool.isExit("clientRoom")) {
            RedisTool.delSetData("clientRoom", room);
        }
        if (RedisTool.isExit(room + "text")) {
            if (RedisTool.isExit("allTextNum")) {
                int tmpsum = Integer.parseInt(RedisTool.getStringValue("allTextNum"));
                int tmproomsum = (int) RedisTool.getListLengthByKey(room + "text");
                int diff = Math.abs(tmpsum - tmproomsum);
                RedisTool.setStringValue("allTextNum", String.valueOf(diff));
                ManageSocket.updateTextNum();
            }

            RedisTool.delKey(room + "text");
        }
        if (RedisTool.isExit(room + "img")) {
            if (RedisTool.isExit("allImgNum")) {
                int tmpsum = Integer.parseInt(RedisTool.getStringValue("allImgNum"));
                int tmproomsum = (int) RedisTool.getListLengthByKey(room + "img");
                int diff = Math.abs(tmpsum - tmproomsum);
                RedisTool.setStringValue("allImgNum", String.valueOf(diff));
                ManageSocket.updateImgNum();
            }
            RedisTool.delKey(room + "img");
        }
    }
    // 清理指定房间名内容
    public static void clearOneRoomMessage(String room) throws IOException {
        if (RedisTool.isExit(room + "text")) {
            if (RedisTool.isExit("allTextNum")) {
                int tmpsum = Integer.parseInt(RedisTool.getStringValue("allTextNum"));
                int tmproomsum = (int) RedisTool.getListLengthByKey(room + "text");
                int diff = Math.abs(tmpsum - tmproomsum);
                RedisTool.setStringValue("allTextNum", String.valueOf(diff));
                ManageSocket.updateTextNum();
            }

            RedisTool.delKey(room + "text");
        }
        if (RedisTool.isExit(room + "img")) {
            if (RedisTool.isExit("allImgNum")) {
                int tmpsum = Integer.parseInt(RedisTool.getStringValue("allImgNum"));
                int tmproomsum = (int) RedisTool.getListLengthByKey(room + "img");
                int diff = Math.abs(tmpsum - tmproomsum);
                RedisTool.setStringValue("allImgNum", String.valueOf(diff));
                ManageSocket.updateImgNum();
            }
            RedisTool.delKey(room + "img");
        }
        statusHashMap.clear();
    }

    // 以下用于恢复做题状态
    private void addToStatusHashMap(String room, String message){
        if (room.equals(""))return;
        if (statusHashMap.containsKey(room)){
            statusHashMap.get(room).add(message);
        }else {
            Set<String> stringSet = new HashSet<String>();
            stringSet.add(message);
            statusHashMap.put(room,stringSet);
        }
    }
    private void delFromStatusHashMap(String room, String message){
        if (room.equals(""))return;
        if (statusHashMap.containsKey(room)){
            if (statusHashMap.get(room).contains(message)){
                statusHashMap.get(room).remove(message);
            }
        }
    }
}
