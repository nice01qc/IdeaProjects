package com.bjsxt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

	/**
	 * 比如：前台传welcome 跳转至/WEB-INF/pages/welcom.jsp
	 * 比如：前台传login 跳转至/WEB-INF/pages/login.jsp
	 * 公共的页面跳转 restful风格
	 */
	@RequestMapping("/{page}")
	public String page(@PathVariable String page){
		return page;
	}
	
	/**
	 * 精确匹配login
	 */
	@RequestMapping("/login")
	public String login(String redirectUrl,ModelMap modelMap){
		modelMap.addAttribute("redirectUrl", redirectUrl);
		return "login";
	}
}
