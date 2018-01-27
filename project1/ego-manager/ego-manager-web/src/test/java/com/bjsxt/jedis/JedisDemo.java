package com.bjsxt.jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import redis.clients.jedis.Jedis;

public class JedisDemo {

	Jedis jedis = null;

	@Before
	public void conn(){
		//实例jedis对象 指定redis服务地址
		jedis = new Jedis("192.168.88.10");
		//redis默认使用的实例为db0 可手动指定
		jedis.select(6);
	}

	//连接
	@Test
	public void testConn(){
		String result = jedis.ping();

		System.out.println("结果："+result);
	}

	//操作String类型
	@Test
	public void testOperStr(){
		//添加一条数据
		//jedis.set("key01","value01");
		//获取一条数据
		String result = jedis.get("key01");

		System.out.println(result);


		//添加多条数据 当参数为奇数时 表示key  当参数为偶数时 表示 value
		//jedis.mset("key02","value02","key03","value03");

		//获取多条数据
		List<String> strList = jedis.mget("key02","key03");
		for(String str:strList){
			System.out.println("结果："+str);
		}
	}

	//操作hash
	@Test
	public void testOperHash(){
		//添加一条数据 参数1：redis中的key  参数2：hash中的key(field) 参数3：hash中的value
//		jedis.hset("key04","hkey01","hvalue01");
		//获取一条数据 参数1：redis中的key  参数2：hash中的key(field)
		String res04 = jedis.hget("key04", "hkey01");
		System.out.println("结果04："+res04);
		
		Map<String,String> map = new HashMap<>();
		map.put("name", "zhangsan");
		map.put("age","18");
		map.put("address","jhl");
		//添加多条数据
//		jedis.hmset("userInfo", map);
		//获取全部hash值
		Map<String,String> userInfo = jedis.hgetAll("userInfo");
		for(Entry<String, String> entry: userInfo.entrySet()){
			System.out.println(entry.getKey()+":"+entry.getValue());
		}
		//指定key、field获取多条数据
		List<String> nameAge = jedis.hmget("userInfo", "name","age");
		for(String str:nameAge){
			System.out.println("结果05："+str);
		}
	}
	
	//操作list
	@Test
	public void testOperList(){
		//添加
//		jedis.lpush("季节","春","夏","秋","冬");
		//获取
		List<String> strList = jedis.lrange("季节", 0, 3);
		for(String str:strList){
			System.out.println("季节："+str);
		}
	}
	
	//操作set
	@Test
	public void testOperSet(){
		//添加
//		jedis.sadd("student","zhangsan","lisi","wangermazi");
		//获取
		Set<String> stuSet = jedis.smembers("student");
		for(String str:stuSet){
			System.out.println("student:"+str);
		}
	}
	
	//操作sorted set 常用于排序 成绩排名等‘
	@Test
	public void testOperSortedSet(){
		Map<String,Double> scoreMembers = new HashMap<String,Double>();
		scoreMembers.put("zhangsan",1d);
		scoreMembers.put("wangermazi",2d);
		scoreMembers.put("lisi",3d);
		//添加
//		jedis.zadd("score", scoreMembers);
		
		//获取
		Set<String> scores = jedis.zrange("score", 1,2);
		for(String score:scores){
			System.out.println("排名："+score);
		}
		
	}
	
	//测试删除 可用于5种类型
	@Test
	public void testDel(){
		jedis.del("score");
	}
	
	

}
