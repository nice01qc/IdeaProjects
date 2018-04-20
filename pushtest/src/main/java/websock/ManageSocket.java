package websock;

import util.RedisTool;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint("/manage")
public class ManageSocket {

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static CopyOnWriteArraySet<ManageSocket> manageSocketSet = new CopyOnWriteArraySet<ManageSocket>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    // 连接建立成功调用的方法
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        manageSocketSet.add(this);     //加入set中
        updateImgNum();
        updateTextNum();
        updateAllRoom();
    }

    // 连接关闭调用的方法
    @OnClose
    public void onClose() {
        manageSocketSet.remove(this);  //从set中删除
    }

    // 收到客户端消息后调用的方法
    @OnMessage
    public void onMessage(String message, Session session) {
        String room = message.replaceAll(" ","");
        if (room.matches("[:a-zA-Z0-9]+")) {
            if (room.equals(0)){
                WebSocket.clearAllRoom();
            }
            if (room.matches("^room:[0-9a-zA-Z]+$")) {
                WebSocket.clearOneRoom(room.replaceAll("room:", ""));
            }
            if (room.equals("clear")) {
                RedisTool.emptyRedis();
            }
        }

    }

    // 发生错误时调用
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }


    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    // 外部调用方法
    public static void sendMessageByOut(String message) {
        for (ManageSocket item : manageSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    public static void updateImgNum(){
        if (RedisTool.isExit("allImgNum")) {
            sendMessageByOut("allImgNum:" + RedisTool.getStringValue("allImgNum"));
        }
    }

    public static void updateTextNum(){
        if (RedisTool.isExit("allTextNum")) {
            sendMessageByOut("allTextNum:" + RedisTool.getStringValue("allTextNum"));
        }
    }

    public static void updateroom(String message){
        sendMessageByOut("room:" + message);
    }

    public static void updateAllRoom(){
        if (RedisTool.isExit("clientRoom")) {
            Set<String> stringSet = RedisTool.getAllSetValue("clientRoom");
            for (String string : stringSet){
                sendMessageByOut("room:" + string);
            }
        }
    }


}