package com.bjsxt.jedis;

import java.io.Serializable;

public class UserInfo implements Serializable {
	private static final long serialVersionUID = -7649642789381104212L;
	//名称
	private String name;
	//年龄
	private String age;
	//地址
	private String address;
	
	public UserInfo() {
		super();
	}
	public UserInfo(String name, String age, String address) {
		super();
		this.name = name;
		this.age = age;
		this.address = address;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
}
