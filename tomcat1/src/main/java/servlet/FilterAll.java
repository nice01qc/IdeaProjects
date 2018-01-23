package servlet;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
@WebFilter(urlPatterns = {"/*","/view"})
public class FilterAll implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("All should pass me --> 1");

        chain.doFilter(request,response);

        System.out.println("All should pass me --> 2");
    }

    @Override
    public void destroy() {

    }
}
