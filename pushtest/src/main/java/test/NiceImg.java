package test;

import util.RedisTool;
import util.TransformToImg;
import websock.WebSocket;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
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

        String path = RedisTool.getByKey("imgdir");

        request.setCharacterEncoding("utf-8");
        String imgdata = request.getParameter("img");
        String room = request.getParameter("room");

        if (imgdata != null && imgdata.length() > 200 && room != null && room.matches("[0-9a-zA-Z]+")) {

            int index = imgdata.indexOf("base64,") + "base64,".length();
            imgdata = imgdata.substring(index);
            String imgName = getImgName(room);
            TransformToImg.GenerateImage(imgdata, imgName, path + room);
//            WebSocket.sendMessageByOut(room,path + room + File.separator + imgName + ".jpeg");
            WebSocket.sendMessageByOut(room,"http://img1.imgtn.bdimg.com/it/u=594559231,2167829292&fm=27&gp=0.jpg");
        }

        response.setContentType("text/html;charset=utf-8");

        response.getWriter().write("ok");

    }

    private String getImgName(String room) {
        if (room == null || room.equals("")) {
            System.out.println("NiceImg.getImgName 中 room 不合格");
            return "";
        }
        String result = null;
        String num;
        if (RedisTool.isExit(room+"num")) {
            num = String.valueOf(Integer.parseInt(RedisTool.getByKey(room+"num")) + 1);
            RedisTool.stringSetValueByKey(room+"num", num);
        } else {
            RedisTool.stringSetValueByKey(room+"num", "1");
            num = "1";
        }

        RedisTool.setExpire(room+"num", 60 * 60 * 4);

        result = room + "-" + num;

        return result;
    }


}
