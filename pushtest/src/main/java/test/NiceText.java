package test;

import util.RedisTool;
import websock.WebSocket;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

public class NiceText extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        String text = request.getParameter("text");
        String room = request.getParameter("room");
        if (text != null && text.length() > 0) {
            text = text.replaceAll(" ", "");
            System.out.println(room + ": " + text);
            RedisTool.addValueByKey(room, text);
            WebSocket.sendMessageByOut(room, text);
        }

        response.setContentType("text/html;charset=utf-8");

        response.getWriter().write("text ok");

    }


}
