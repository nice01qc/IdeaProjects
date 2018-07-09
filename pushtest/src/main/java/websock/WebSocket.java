package websock;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import util.RedisTool;

@ServerEndpoint("/websocket")
public class WebSocket {
    private static Logger logger = (Logger)LogManager.getLogger("other");
    private String room = "";
    public static volatile boolean initState = true;
    private static CopyOnWriteArraySet<WebSocket> webSocketSet = new CopyOnWriteArraySet();
    private static ConcurrentHashMap<String, Set<String>> statusHashMap = new ConcurrentHashMap();
    private Session session;

    public WebSocket() {
    }

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.add(this);
    }

    @OnClose
    public void onClose() {
        webSocketSet.remove(this);
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException, InterruptedException {
        if (message.matches("^[0-9]+:no$")) {
            sendMessageByOut(this.room, message.replaceAll("no", "yes"));
            this.addToStatusHashMap(this.room, message.replaceAll(":no", ""));
        } else if (message.matches("^[0-9]+:yes$")) {
            sendMessageByOut(this.room, message.replaceAll("yes", "no"));
            this.delFromStatusHashMap(this.room, message.replaceAll(":yes", ""));
        } else {
            String room = message.trim();
            if (room.matches("^[0-9a-zA-Z]+$")) {
                RedisTool.setAddValueByKey("clientRoom", room);
                ManageSocket.updateroom(room);
                this.room = room;
                String roomText = room + "text";
                String roomImg = room + "img";
                List listroom;
                int i;
                if (RedisTool.isExit(roomText)) {
                    while(!initState) {
                        Thread.sleep(500L);
                    }

                    this.sendMessage("TextNum:" + RedisTool.getListLengthByKey(roomText));
                    listroom = RedisTool.getAllByKey(roomText);
                    if (listroom == null) {
                        Thread.sleep(400L);
                        listroom = RedisTool.getAllByKey(roomText);
                    }

                    if (listroom.size() > 500) {
                        RedisTool.delKey(roomText);
                    }

                    if (listroom != null && !listroom.isEmpty()) {
                        for(i = listroom.size() - 1; i >= 0; --i) {
                            this.sendMessage((String)listroom.get(i));
                        }
                    }
                }

                if (RedisTool.isExit(roomImg)) {
                    while(!initState) {
                        Thread.sleep(500L);
                    }

                    this.sendMessage("imgNum:" + RedisTool.getListLengthByKey(roomImg));
                    listroom = RedisTool.getAllByKey(roomImg);
                    if (listroom == null) {
                        Thread.sleep(400L);
                        listroom = RedisTool.getAllByKey(roomImg);
                    }

                    if (listroom.size() > 250) {
                        RedisTool.delKey(roomImg);
                    }

                    if (listroom != null && !listroom.isEmpty()) {
                        for(i = listroom.size() - 1; i >= 0; --i) {
                            this.sendMessage((String)listroom.get(i));
                        }
                    }
                }

                if (statusHashMap.containsKey(this.room)) {
                    Set<String> stringSet = (Set)statusHashMap.get(this.room);
                    String result = "status";

                    String x;
                    for(Iterator var8 = stringSet.iterator(); var8.hasNext(); result = result + "-" + x) {
                        x = (String)var8.next();
                    }

                    this.sendMessage(result);
                }

            }
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
        logger.error(error.getMessage());
    }

    public synchronized void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    public static void sendMessageByOut(String room, String message) throws InterruptedException {
        Iterator var2 = webSocketSet.iterator();

        while(var2.hasNext()) {
            WebSocket item = (WebSocket)var2.next();
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

    public static void clearAllRoom() throws IOException {
        Iterator var0 = webSocketSet.iterator();

        while(var0.hasNext()) {
            WebSocket webSocket = (WebSocket)var0.next();
            webSocket.session.close();
        }

        webSocketSet.clear();
        RedisTool.delKey("clientRoom");
    }

    public static void clearOneRoom(String room) throws IOException {
        Iterator var1 = webSocketSet.iterator();

        while(var1.hasNext()) {
            WebSocket webSocket = (WebSocket)var1.next();
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
            RedisTool.delKey(room + "text");
        }

        if (RedisTool.isExit(room + "img")) {
            RedisTool.delKey(room + "img");
        }

    }

    public static void clearOneRoomMessage(String room) throws IOException {
        if (RedisTool.isExit(room + "text")) {
            RedisTool.delKey(room + "text");
        }

        if (RedisTool.isExit(room + "img")) {
            RedisTool.delKey(room + "img");
        }

        statusHashMap.clear();
    }

    private void addToStatusHashMap(String room, String message) {
        if (!room.equals("")) {
            if (statusHashMap.containsKey(room)) {
                ((Set)statusHashMap.get(room)).add(message);
            } else {
                Set<String> stringSet = new HashSet();
                stringSet.add(message);
                statusHashMap.put(room, stringSet);
            }

        }
    }

    private void delFromStatusHashMap(String room, String message) {
        if (!room.equals("")) {
            if (statusHashMap.containsKey(room) && ((Set)statusHashMap.get(room)).contains(message)) {
                ((Set)statusHashMap.get(room)).remove(message);
            }

        }
    }
}
