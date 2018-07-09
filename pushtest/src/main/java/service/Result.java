package service;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import websock.ResultSocket;

public class Result extends HttpServlet {
    public Result() {
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        this.doPost(req, resp);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String text = request.getParameter("result");
        String room = request.getParameter("room");
        System.out.println(text);
        if (text != null && text.length() > 0 && room != null && !room.equals("")) {
            ResultSocket.sendMessageByOut(room, text);
        }

    }
}
