package service;


import websock.ResultSocket;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NiceResult extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response){
        String text = request.getParameter("result");
        String room = request.getParameter("room");
        if (text != null && text.length() > 0 && room != null && !room.equals("")) {
            ResultSocket.sendMessageByOut(room, text);
        }

    }
}
