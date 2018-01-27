package com.bjsxt.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CookieService {
	/**
	 * 将ticket写入cookie中
	 * @param request
	 * @param response
	 * @param ticket
	 * @return
	 */
	public boolean setCookie(HttpServletRequest request,HttpServletResponse response,String ticket);

	/**
	 * 获取票据
	 * @param request
	 * @param response
	 * @return
	 */
	public String getTicket(HttpServletRequest request, HttpServletResponse response);
}
