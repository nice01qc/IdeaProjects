package filter;

import util.RedisTool;

import javax.servlet.*;
import java.io.IOException;

public class FilterText implements Filter {
    private ServletContext servletContext = null;

    public void init(FilterConfig filterConfig) throws ServletException {
        this.servletContext = filterConfig.getServletContext();
        servletContext.setAttribute("allTextNum", new Integer(0));
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding("utf-8");
        String room = servletRequest.getParameter("room");
        Integer allTextNum = (Integer) servletContext.getAttribute("allTextNum");
        if (allTextNum > 2000) RedisTool.emptyRedis();

        servletContext.setAttribute("allTextNum", new Integer(allTextNum + 1));

        if (room != null && !room.equals("") && room.matches("[0-9a-zA-Z]+")) {
            if (room.equals("clear")) RedisTool.emptyRedis();
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    public void destroy() {

    }
}
