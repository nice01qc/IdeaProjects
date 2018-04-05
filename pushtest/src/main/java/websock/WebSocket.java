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
    public void onMessage(String message, Session session) {
        String room = message.trim();
        if (!room.matches("^[0-9a-zA-Z]+$")) return;
        this.room = room;
        // 发送以存储的文字
        if (RedisTool.isExit(room+"text")){
            List<String> listroom = RedisTool.getAllByKey(room+"text");
            if (listroom != null && !listroom.isEmpty()) {
                for (String x : listroom) {
                    sendMessageByOut(room, x);
                }
            }
        }

        // 发送以存储的图片
        if (!RedisTool.isExit(room+"num")) return;
        int imgNum = Integer.parseInt(RedisTool.getByKey(room+"num"));
        String imgpath = RedisTool.getByKey("imgdir") + room + File.separator;
        for (int i = 1; i <= imgNum; i++) {
//            sendMessageByOut(room, imgpath + room + "-" + i + ".jpeg");
            sendMessageByOut(room,"http://img1.imgtn.bdimg.com/it/u=594559231,2167829292&fm=27&gp=0.jpg");
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
    public static void sendMessageByOut(String room, String message) {

        for (WebSocket item : webSocketSet) {
            if (!item.room.equals(room)) continue;
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
    }


}
