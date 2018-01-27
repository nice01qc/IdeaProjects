package com.bjsxt.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bjsxt.service.CookieService;
import com.bjsxt.util.CookieUtil;

@Service
public class CookieServiceImpl implements CookieService {

	@Value("${user.ticket}")
	String userTicket;

	@Override
	public boolean setCookie(HttpServletRequest request, HttpServletResponse response, String ticket) {
		CookieUtil.setCookie(request, response, userTicket, ticket);
		return true;
	}

	@Override
	public String getTicket(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return CookieUtil.getCookieValue(request, userTicket);
	}
	
}
