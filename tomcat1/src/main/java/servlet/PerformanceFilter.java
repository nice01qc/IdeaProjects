package servlet;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;
@WebFilter(servletNames = {"view"},initParams = {@WebInitParam(name="haoge",value = "gg")})
public class PerformanceFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println(filterConfig.getInitParameter("haoge"));
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("PerformanceFilter-> one");
        chain.doFilter(request,response);
        System.out.println("PerformanceFilter-> two");

    }

    @Override
    public void destroy() {

    }
}
