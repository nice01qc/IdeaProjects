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
        String roomText = room + "text";    // 用于存放特定room发过来的文字

        if (text != null && text.length() > 0) {
            RedisTool.listAddValueByKey(roomText, text);
            increTextNum();
            RedisTool.setExpire(roomText, 60 * 60 * 5);
            try {
                WebSocket.sendMessageByOut(room, text);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                if (RedisTool.isExit(roomText)){
                    WebSocket.sendMessageByOut(room, "TextNum:" + RedisTool.getListLengthByKey(roomText));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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