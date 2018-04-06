package filter;

import util.RedisTool;

import javax.servlet.*;
import java.io.IOException;

public class FilterImg implements Filter {
    private ServletContext servletContext = null;

    public void init(FilterConfig filterConfig) throws ServletException {
        this.servletContext = filterConfig.getServletContext();
        servletContext.setAttribute("allImgNum", new Integer(0));
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding("utf-8");
        String room = servletRequest.getParameter("room");
        Integer allImgNum = (Integer) servletContext.getAttribute("allImgNum");
        if (allImgNum > 500) RedisTool.emptyRedis();

        servletContext.setAttribute("allImgNum", new Integer(allImgNum+1));

        if (room != null && !room.equals("") && room.matches("[0-9a-zA-Z]+")) {
            if (room.equals("clear")) RedisTool.emptyRedis();

            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    public void destroy() {

    }
}
