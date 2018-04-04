package test;

import util.TransformToImg;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class NiceImg extends HttpServlet {

    private ServletConfig servletConfig = null;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.servletConfig = config;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String path = "C:\\Users\\nice01qc\\Desktop\\";
        request.setCharacterEncoding("utf-8");
        String imgdata = request.getParameter("img");
        String room = request.getParameter("room");

        if (imgdata != null && imgdata.length() > 200 && room != null &&  !room.equals("")) {
            int index = imgdata.indexOf("base64,") + "base64,".length();
            imgdata = imgdata.substring(index);
            TransformToImg.GenerateImage(imgdata,getImgName(room),path + room);
        }

        response.setContentType("text/html;charset=utf-8");

        response.getWriter().write("ok");

    }

    private String getImgName(String room){
        Integer localRoom = (Integer)servletConfig.getServletContext().getAttribute("room");
        if (localRoom != null && !localRoom.equals("")){
            servletConfig.getServletContext().setAttribute("room",new Integer(localRoom+1));
            return room + "-" + localRoom;
        }else {
            servletConfig.getServletContext().setAttribute("room",new Integer(1));
            return room + "-" + 1;
        }
    }


}
