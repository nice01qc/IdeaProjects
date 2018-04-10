package websock;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint("/result")
public class ResultSocket {

    private String room = "";
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static CopyOnWriteArraySet<ResultSocket> resultSocketSet = new CopyOnWriteArraySet<ResultSocket>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    // 连接建立成功调用的方法
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        resultSocketSet.add(this);     //加入set中

    }

    // 连接关闭调用的方法
    @OnClose
    public void onClose() {
        resultSocketSet.remove(this);  //从set中删除
    }

    // 收到客户端消息后调用的方法
    @OnMessage
    public void onMessage(String message, Session session) {
        this.room = message;
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
    public static void sendMessageByOut(String room, String message) {
        for (ResultSocket item : resultSocketSet) {
            if (! item.room.equals(room)) continue;
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
    }

}
