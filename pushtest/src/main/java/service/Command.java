package service;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import websock.IndexSocket;

@WebServlet(name = "Command",urlPatterns = {"/command"})
public class Command extends HttpServlet {
    private static Logger logger = (Logger)LogManager.getLogger("other");

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        this.doPost(req, resp);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        String command = request.getParameter("command");
        String room = request.getParameter("room");
        if (command.equals("deleteAll") && !room.equals("")) {
            try {
                IndexSocket.sendMessageByOut(room, command);
            } catch (InterruptedException var6) {
                var6.printStackTrace();
                logger.error(var6.getMessage());
            }

            IndexSocket.clearOneRoomMessage(room);
        }

    }
}
