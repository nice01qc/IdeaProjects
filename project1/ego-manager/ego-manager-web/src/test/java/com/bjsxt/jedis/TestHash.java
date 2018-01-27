package com.bjsxt.jedis;

public class TestHash {
	
	public static void main(String[] args) {
		String key01="name";
		System.out.println(key01.hashCode());//3373707
		
		//求余
		int res01 = 3373707%16384;
		
		//得到的结果范围0-16383
		System.out.println(res01);
		
		
		
		
	}
}
