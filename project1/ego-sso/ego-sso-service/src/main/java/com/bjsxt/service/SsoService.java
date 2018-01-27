package com.bjsxt.service;

import com.bjsxt.pojo.Admin;

public interface SsoService {
	/**
	 * 登录认证方法
	 * 返回公共票据 ticket
	 */
	String login(Admin admin);
	
	/**
	 * 验证
	 * 返回的用户信息
	 */
	Admin validate(String ticket);
	
	/**
	 * 退出
	 * @return 
	 */
	void logout(String ticket);
}
