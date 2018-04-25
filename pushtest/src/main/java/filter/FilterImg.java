package filter;

import util.RedisTool;

import javax.servlet.*;
import java.io.IOException;

public class FilterImg implements Filter {

    public void init(FilterConfig filterConfig) {
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding("utf-8");
        String room = servletRequest.getParameter("room");

        long allImgNum = RedisTool.getListLengthByKey(room + "img");
        if (allImgNum > 500) RedisTool.delKey(room + "img");


        if (room != null && !room.equals("") && room.matches("[0-9a-zA-Z]+")) {
            if (room.equals("clear")) RedisTool.emptyRedis();

            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    public void destroy() {

    }
}
