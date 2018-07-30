package one;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class Test1 extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println(this.getServletContext().getInitParameter("nice"));
        System.out.println(this.getServletContext().getAttribute("jake"));

        PrintWriter p = resp.getWriter();

        p.write("<h1> 123</h1>");
        p.write("<h3>金<h3/>");

        p.flush();


    }


}
