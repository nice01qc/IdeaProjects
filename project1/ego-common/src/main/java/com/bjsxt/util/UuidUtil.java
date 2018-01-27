package com.bjsxt.util;

import java.util.UUID;

public class UuidUtil {

	/**
	 * 获取uuid 返回结果没有中横线
	 * @return 
	 */
	public static String getUuid(){
		return UUID.randomUUID().toString().replace("-","");
	}
	public static void main(String[] args) {
		System.out.println(getUuid());
	}
}
