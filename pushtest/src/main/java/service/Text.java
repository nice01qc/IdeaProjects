//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package service;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import util.RedisTool;
import websock.WebSocket;

public class Text extends HttpServlet {
    private static Logger logger = (Logger)LogManager.getLogger("other");

    public Text() {
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        this.doPost(req, resp);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String text = request.getParameter("text");
        String room = request.getParameter("room");
        String roomText = room + "text";
        if (text != null && text.length() > 0) {
            WebSocket.initState = true;
            RedisTool.listAddValueByKey(roomText, text);

            try {
                WebSocket.sendMessageByOut(room, text);
            } catch (InterruptedException var7) {
                var7.printStackTrace();
                logger.error(var7.getMessage());
            }
        }

        WebSocket.initState = true;
    }
}
