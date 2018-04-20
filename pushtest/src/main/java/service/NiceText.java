package service;

import util.RedisTool;
import websock.ManageSocket;
import websock.WebSocket;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NiceText extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp){
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

        String text = request.getParameter("text");
        String room = request.getParameter("room");
        if (text != null && text.length() > 0) {
            RedisTool.listAddValueByKey(room + "text", text);
            increTextNum();
            RedisTool.setExpire(room + "text", 60 * 60 * 5);
            WebSocket.sendMessageByOut(room, text);
            WebSocket.sendMessageByOut(room, "TextNum:" + request.getServletContext().getAttribute("allTextNum"));
            ManageSocket.updateTextNum();
        }

    }


    private void increTextNum() {
        int allImgNum = 1;
        String key = "allTextNum";
        if (RedisTool.isExit(key)) {
            allImgNum = Integer.parseInt(RedisTool.getStringValue(key)) + 1;
        }
        RedisTool.setStringValue(key, String.valueOf(allImgNum));
    }


}
