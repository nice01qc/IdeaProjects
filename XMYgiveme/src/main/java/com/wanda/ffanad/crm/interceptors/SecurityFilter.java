/**
 * 
 */
package com.wanda.ffanad.crm.interceptors;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.util.StringUtils;

import com.wanda.ffanad.core.common.constant.SystemConstant;

/**
 * @author 姜涛
 *
 */
@WebFilter(filterName = "SecurityFilter", urlPatterns = "*.html")
public class SecurityFilter implements Filter {

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.Filter#destroy()
     */
    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
     * javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        if (httpRequest.getServletPath().indexOf("login.html") != -1) {
            chain.doFilter(request, response);
            return;
        }
        HttpSession session = httpRequest.getSession();
        String accountEmail = (String) session.getAttribute(SystemConstant.SESSION_ACCOUNT_EMAIL);
        if (StringUtils.isEmpty(accountEmail)) {
            httpResponse.sendRedirect("/login.html");
        } else {
            chain.doFilter(request, response);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    @Override
    public void init(FilterConfig arg0) throws ServletException {
        // TODO Auto-generated method stub

    }

}
