package com.bjsxt.service.impl;

import com.bjsxt.service.HelloService;

public class HelloServiceImpl implements HelloService {

	@Override
	public String sayHello() {
		System.out.println("Hello everyone.This is my first program.");
		return "success";
	}

}
