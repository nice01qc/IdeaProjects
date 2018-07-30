package one.filter;

import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;

public class ExampleFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        request.setCharacterEncoding("UTF-8");

        response.setContentType("text/html;charset=utf-8");
        chain.doFilter(request,response);

        PrintWriter p = response.getWriter();

        p.write("<h1> 33333333</h1>");
        p.write("<h3>金3333333<h3/>");

        p.flush();
        p.close();

    }

    public void destroy() {

    }
}
