package servlet;


import javax.servlet.*;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "view",value = {"/view","/nice","/haoge"},loadOnStartup = 1,
initParams = {@WebInitParam(name = "haoge",value = "haoge jiu shi haoge")},asyncSupported = true)
public class View extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
//        resp.setContentType("application/pdf");
        resp.addHeader("haoge", "small boss");
        resp.addDateHeader("data-1", 1000000);
        resp.addIntHeader("IntHeader", 232323);
//        resp.setStatus(301);
//        resp.setLocale(new Locale("https://www.baidu.com"));


//        ServletContextListener a = null;
//        ServletRequestListener b = null;
//        HttpSessionListener c = null;
//        ServletRequestAttributeListener d = null;
//        HttpSessionAttributeListener e = null;
//        a.contextDestroyed(new ServletContextEvent(getServletContext()));

    }
}
