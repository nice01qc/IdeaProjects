package one;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import sun.misc.BASE64Encoder;
import utils.GetAllMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Test extends HttpServlet {

    public static ConcurrentHashMap<String,String> concurrentHashMap = new ConcurrentHashMap<String, String>();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("utf-8");

        concurrentHashMap.put(Thread.currentThread().getName(),Thread.currentThread().getName());

        System.out.println(concurrentHashMap.size());

//        System.out.println("req.getPathInfo(): " + req.getPathInfo());
//
//        System.out.println("req.getPathTranslated(): " + req.getPathTranslated());
//
//        System.out.println("req.getContextPath()" + req.getContextPath());
//
//        System.out.println("req.getServletPath(): " + req.getServletPath());
//
//        System.out.println("req.getRemoteUser(): " + req.getRemoteUser());
//
//        System.out.println("req.getQueryString(): " + req.getQueryString());
//
//        System.out.println();

//        try {
//            GetAllMethod.printHttpServletRequestAllMethodValue(req);
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }


        System.out.println(this.getServletContext().getInitParameter("nice"));
        this.getServletContext().setAttribute("jake","testJake");
        System.out.println(this.getServletContext().getAttribute("jake"));

        this.getServletConfig().getServletName()
    }

}
