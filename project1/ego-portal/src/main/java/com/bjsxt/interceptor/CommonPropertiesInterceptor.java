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

/**
 * 加载全局配置文件
 * @author zhiduo
 *
 */
@Component("commonPropertiesInterceptor")
public class CommonPropertiesInterceptor implements HandlerInterceptor {
	@Value("${order.system.preOrder}")
	String orderSystemPreOrder;
	@Value("${ftp.host}")
	String ftpHost;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//获取application对象
		String orderSystemPreOrderUrl = (String) request.getServletContext().getAttribute("orderSystemPreOrder");
		String ftpHostUrl = (String) request.getServletContext().getAttribute("ftpHost");
		if(!(orderSystemPreOrderUrl!=null && orderSystemPreOrderUrl.length()>0)){
			request.getServletContext().setAttribute("orderSystemPreOrder",orderSystemPreOrder);
		}
		if(!(ftpHostUrl!=null && ftpHostUrl.length()>0)){
			request.getServletContext().setAttribute("ftpHost",ftpHost);
		}
		return true;
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
