package filter;

import util.RedisTool;

import javax.servlet.*;
import java.io.IOException;

public class FilterText implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding("utf-8");
        String room = servletRequest.getParameter("room");
        long allTextNum = RedisTool.getListLengthByKey(room+"text");
        if (allTextNum > 500) RedisTool.delKey(room+"text");


        if (room != null && !room.equals("") && room.matches("[0-9a-zA-Z]+")) {
            if (room.equals("clear")) RedisTool.emptyRedis();
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    public void destroy() {

    }
}
