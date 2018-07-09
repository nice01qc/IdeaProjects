package service;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import util.RedisTool;
import websock.WebSocket;

public class Img extends HttpServlet {
    private static Logger logger = (Logger)LogManager.getLogger("other");

    public Img() {
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        this.doPost(req, resp);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        String imgdata = request.getParameter("img");
        String room = request.getParameter("room");
        String roomImg = room + "img";
        if (imgdata != null && imgdata.length() > 200) {
            WebSocket.initState = false;
            RedisTool.listAddValueByKey(roomImg, imgdata);

            try {
                WebSocket.sendMessageByOut(room, imgdata);
            } catch (InterruptedException var7) {
                var7.printStackTrace();
                logger.error(var7.getMessage());
            }
        }

        WebSocket.initState = true;
    }
}
