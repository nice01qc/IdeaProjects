package servlet;


import utils.SendMailUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/email-1")
public class Email1 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("utf-8");
        String name = req.getParameter("name");
        String age = req.getParameter("age");
        String email = req.getParameter("email");

        //直接发送4封邮件
        SendMailUtil.sendMail(name,email);
        SendMailUtil.sendMail(name,email);
        SendMailUtil.sendMail(name,email);
        SendMailUtil.sendMail(name,email);

        resp.setContentType("text/html;charset=utf-8");

        //通过转发来代替返回数据，以此来看效果
        resp.sendRedirect("succeed.jsp");

    }
}
