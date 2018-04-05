package test;

import util.RedisTool;
import websock.WebSocket;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class NiceImg extends HttpServlet {


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        String imgdata = request.getParameter("img");
        String room = request.getParameter("room");

        if (imgdata != null && imgdata.length() > 200 && room != null && room.matches("[0-9a-zA-Z]+")) {

            RedisTool.listAddValueByKey(room + "img", imgdata);
            WebSocket.sendMessageByOut(room,imgdata);
        }

    }


}
