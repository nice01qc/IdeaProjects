package com.bjsxt.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.bjsxt.pojo.Admin;
import com.bjsxt.service.SsoService;
import com.bjsxt.util.CookieUtil;

@Component("loginIntercepor")
public class LoginInterceptor implements HandlerInterceptor {
	
	@Autowired
	SsoService ssoService;
	@Value("${user.ticket}")
	String userTicket;
	@Value("${currentUser}")
	String currentUser;
	@Value("${ego.portal.login}")
	String portalLogin;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//1.获取用户票据
		String ticket = CookieUtil.getCookieValue(request, userTicket);
		if(ticket!=null && ticket.length()>0){
			//2.验证
			Admin admin = ssoService.validate(ticket);
			if(admin!=null){//3.认证通过
				//将用户信息，添加至session中
				request.getSession().setAttribute(currentUser,admin);
				return true;
			}
		}
		//重定向至login
		response.sendRedirect(portalLogin+"?redirectUrl="+request.getRequestURL());
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
