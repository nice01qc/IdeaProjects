package websock;

import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

@ServerEndpoint("/result")
public class ResultSocket {
    private static Logger logger = (Logger)LogManager.getLogger("other");
    private String room = "";
    private static CopyOnWriteArraySet<ResultSocket> resultSocketSet = new CopyOnWriteArraySet();
    private Session session;

    public ResultSocket() {
    }

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        resultSocketSet.add(this);
    }

    @OnClose
    public void onClose() {
        resultSocketSet.remove(this);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        this.room = message;
    }

    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("鍙戠敓閿欒\ue1e4");
        error.printStackTrace();
        logger.error(error.getMessage());
    }

    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    public static void sendMessageByOut(String room, String message) {
        Iterator var2 = resultSocketSet.iterator();

        while(var2.hasNext()) {
            ResultSocket item = (ResultSocket)var2.next();
            if (item.room.equals(room)) {
                try {
                    item.sendMessage(message);
                } catch (IOException var5) {
                    var5.printStackTrace();
                    logger.error(var5.getMessage());
                }
            }
        }

    }
}
