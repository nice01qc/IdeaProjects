package websock;

import util.RedisTool;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;


@ServerEndpoint("/websocket")
public class WebSocket {

    private String room = "";
    volatile public static boolean status = true;
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static CopyOnWriteArraySet<WebSocket> webSocketSet = new CopyOnWriteArraySet<WebSocket>();

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
    public void onMessage(String message, Session session) throws IOException {
        String room = message.trim();
        if (!room.matches("^[0-9a-zA-Z]+$")) return;
        // 把房间号加入到redis
        RedisTool.setAddValueByKey("clientRoom", room);
        // 更新房间号
        ManageSocket.updateroom(room);
        this.room = room;
        // 发送以存储的文字
        if (RedisTool.isExit(room + "text")) {
            List<String> listroom = RedisTool.getAllByKey(room + "text");
            if (listroom != null && !listroom.isEmpty()) {
                for (String x : listroom) {
                    sendMessage(x);
                }
            }
        }

        // 发送以存储的图片
        if (RedisTool.isExit(room + "img")) {
            List<String> listroom = RedisTool.getAllByKey(room + "img");
            if (listroom != null && !listroom.isEmpty()) {
                for (String x : listroom) {
                    sendMessage(x);
                }
            }
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
    public static void clearAllRoom() {
        for (WebSocket webSocket : webSocketSet) {
            webSocket = null;
        }
        webSocketSet.clear();
        RedisTool.delKey("clientRoom");
    }

    // 清理指定房间名
    public static void clearOneRoom(String room) {
        for (WebSocket webSocket : webSocketSet) {
            if (webSocket.room.equals(room)) {
                webSocketSet.remove(webSocket);
                break;
            }
        }

        if (RedisTool.isExit("clientRoom")) {
            RedisTool.delSetData("clientRoom", room);
        }

        if (RedisTool.isExit(room + "text")){
            if (RedisTool.isExit("allTextNum")){
                int tmpsum = Integer.parseInt(RedisTool.getStringValue("allTextNum"));
                int tmproomsum = (int) RedisTool.getListLengthByKey(room + "text");
                int diff = Math.abs(tmpsum - tmproomsum);
                RedisTool.setStringValue("allTextNum", String.valueOf(diff));
                ManageSocket.updateTextNum();
            }

            RedisTool.delKey(room + "text");
        }

        if (RedisTool.isExit(room + "img")){

            if (RedisTool.isExit("allImgNum")){
                int tmpsum = Integer.parseInt(RedisTool.getStringValue("allImgNum"));
                int tmproomsum = (int) RedisTool.getListLengthByKey(room + "img");
                int diff = Math.abs(tmpsum - tmproomsum);
                RedisTool.setStringValue("allImgNum", String.valueOf(diff));
                ManageSocket.updateImgNum();
            }

            RedisTool.delKey(room + "img");
        }




    }


}
