package com.bjsxt.jedis;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

public class JedisTransactionDemo {

	JedisPool jedisPool = null;

	@Before
	public void connn(){
		//初始化jedis 连接池
		jedisPool = new JedisPool("192.168.88.10",6379);
	}

	@Test
	public void testConn(){
		//测试连接
		System.out.println(jedisPool.getResource().ping());
	}
	
	/**
	 * 测试事务
	 */
	@Test
	public void testTransaction(){
		//获取jedis连接
		Jedis jedis = jedisPool.getResource();
		
		//创建事务
		Transaction transaction = jedis.multi();
		transaction.set("name", "123456");
		//提交
		transaction.exec();
	}
	
	/**
	 * 测试乐观锁
	 */
	@Test
	public void testWatch(){
		//获取jedis连接
		Jedis jedis = jedisPool.getResource();
		
		//创建事务
		jedis.watch("name");
		Transaction transaction = jedis.multi();
		/**如果 在此之前有其他的 小伙伴或进行对name进行了更改，那么redis数据库中版本已变化 
		 * 此时 当前程序 再次修改会失败
		 */
		transaction.set("name", "654321");
		transaction.exec();
	}
	
	
	@After
	public void close(){
		if(null == jedisPool){
			jedisPool.close();
		}
	}

}
