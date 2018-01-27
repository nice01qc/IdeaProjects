package com.bjsxt.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bjsxt.pojo.Admin;
import com.bjsxt.result.BaseResult;
import com.bjsxt.service.CookieService;
import com.bjsxt.service.SsoService;
import com.bjsxt.util.CookieUtil;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	SsoService ssoService;
	@Autowired
	CookieService cookieService;
	/**
	 * 登录
	 */
	@RequestMapping("/login")
	@ResponseBody
	public BaseResult login(Admin admin,HttpServletRequest request,HttpServletResponse response){
		//1.登录 生成票据
		String ticket = ssoService.login(admin);
		if(ticket!=null && ticket.length()>0){
			//6.将票据写入cookie中
			cookieService.setCookie(request, response, ticket);
			return BaseResult.success();
		}
		return BaseResult.error();
	}
	/**
	 * 退出
	 */
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request,HttpServletResponse response){
		String ticket = cookieService.getTicket(request, response);
		ssoService.logout(ticket);
		return "login";
	}
}
