package service;

import util.RedisTool;
import websock.ManageSocket;
import websock.WebSocket;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class NiceImg extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String imgdata = request.getParameter("img");
        String room = request.getParameter("room");

        if (imgdata != null && imgdata.length() > 200) {
            RedisTool.listAddValueByKey(room + "img", imgdata);
            increImgNum();  // 添加到数据库

            RedisTool.setExpire(room + "img", 60 * 60);
            WebSocket.sendMessageByOut(room, imgdata);
            WebSocket.sendMessageByOut(room, "imgNum:" + request.getServletContext().getAttribute("allImgNum"));
            ManageSocket.updateImgNum();
        }

    }


    private void increImgNum() {
        int allImgNum = 1;
        String key = "allImgNum";
        if (RedisTool.isExit(key)) {
            allImgNum = Integer.parseInt(RedisTool.getStringValue(key))+1;
        }
        RedisTool.setStringValue(key,String.valueOf(allImgNum));
    }
}
