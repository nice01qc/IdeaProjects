package servlet;



import utils.SendMailUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/email-2")
public class Email2 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("utf-8");
        String name = req.getParameter("name");
        String age = req.getParameter("age");
        String email = req.getParameter("email");

        //初始化4个邮件类，用于多线程
        SendMailUtil one =new SendMailUtil();
        one.setName(name);
        one.setToEmail(email);

        SendMailUtil two =new SendMailUtil();
        two.setName(name);
        two.setToEmail(email);

        SendMailUtil three =new SendMailUtil();
        three.setName(name);
        three.setToEmail(email);

        SendMailUtil four =new SendMailUtil();
        four.setName(name);
        four.setToEmail(email);

        //搞4个线程
        Thread thread1 = new Thread(one);
        Thread thread2 = new Thread(two);
        Thread thread3 = new Thread(three);
        Thread thread4 = new Thread(four);

        //开启4个线程
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();


        resp.setContentType("text/html;charset=utf-8");
        //通过转发来代替返回数据，以此来看效果
        resp.sendRedirect("succeed.jsp");

    }
}
