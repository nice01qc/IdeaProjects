package service;

import websock.WebSocket;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class NiceCommand extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        String command = request.getParameter("command");
        String room = request.getParameter("room");

        if (command.equals("deleteAll") && !room.equals("")) {
            try {
                WebSocket.sendMessageByOut(room,command);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            WebSocket.clearOneRoomMessage(room);
        }

    }
}
