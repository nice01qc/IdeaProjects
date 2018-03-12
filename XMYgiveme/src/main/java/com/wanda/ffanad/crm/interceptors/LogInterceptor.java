package com.wanda.ffanad.crm.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


/**
 * 用于记录每个请求的消耗时间
 * 
 * @Title : LogInterceptor.java
 * @author zhangxinfa
 * @date 2016年5月23日 15:16
 *
 */

public class LogInterceptor  extends HandlerInterceptorAdapter{
	
	private static Logger logger = LoggerFactory.getLogger(LogInterceptor.class);
	
	 @Override
	    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
	            throws Exception {

	    }

	    @Override
	    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
	            ModelAndView modelAndView) throws Exception {
	    	
	    	 long startTime = (Long)request.getAttribute("startTime");

	         long endTime = System.currentTimeMillis();

	         long executeTime = endTime - startTime;
	         
	         String requestUri = request.getRequestURI();  
	         String contextPath = request.getContextPath();  
	         String url = requestUri.substring(contextPath.length()); 
	         
	        logger.info("[" + url + "] executeTime : " + executeTime + "ms");
	         

	    }

	    @Override
	    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
	            throws Exception {
	    	 long startTime = System.currentTimeMillis();
	         request.setAttribute("startTime", startTime);
	         return super.preHandle(request, response, handler);
	    }
	 
	
	

}
