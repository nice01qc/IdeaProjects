package filter;

import javax.servlet.*;
import java.io.IOException;

public class SecretFilt implements Filter {
    private ServletContext servletContext = null;

    public void init(FilterConfig filterConfig) throws ServletException {
        this.servletContext = filterConfig.getServletContext();
        this.servletContext.setAttribute("secret", "open");
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding("utf-8");
        String secret = (String) this.servletContext.getAttribute("secret");
        String room = servletRequest.getParameter("room");
        if (room != null && !room.equals("")) {
            if (room.equals("open")) {
                this.servletContext.setAttribute("secret", "open");
            } else if (room.equals("close")){
                this.servletContext.setAttribute("secret", "close");
            }
            if (secret.equals("close")) return;
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

    public void destroy() {

    }
}
