package com.bjsxt.jedis;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Test;

import com.bjsxt.util.SerializeUtil;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class JedisClusterTest {

	JedisCluster cluster = null;

	@Before
	public void testConnn(){
		//set集合
		Set<HostAndPort> nodes = new HashSet<>();
		//添加redis连接
		nodes.add(new HostAndPort("192.168.88.11", 6379));
		nodes.add(new HostAndPort("192.168.88.12", 6379));
		nodes.add(new HostAndPort("192.168.88.13", 6379));
		nodes.add(new HostAndPort("192.168.88.14", 6379));
		nodes.add(new HostAndPort("192.168.88.15", 6379));
		nodes.add(new HostAndPort("192.168.88.16", 6379));

		//实例化JedisCluster对象
		cluster = new JedisCluster(nodes);
	}

	//测试操作String类型
	@Test
	public void testOperString(){
		//添加一条数据
		//		cluster.set("age","18");

		String age = cluster.get("age");

		System.out.println(age);


		//添加多条
		//		cluster.mset("address","jhl","post","100100");

		//获取多条数据
		//		List<String> strList = cluster.mget("address","post");
		//		for(String str:strList){
		//			System.out.println("结果："+str);
		//		}

		byte[] key = SerializeUtil.serialize("userInfo");
		UserInfo uInfo = new UserInfo("zhangsan", "18", "jhl");

		byte[] val = SerializeUtil.serialize(uInfo);
		//		cluster.set(key, val);

		byte[] result = cluster.get(key);

		UserInfo ui = (UserInfo) SerializeUtil.unserialize(result);
		System.out.println(ui.getAddress()+":"+ui.getAge()+":"+ui.getName());
	}

	//操作hash
	@Test
	public void testOperHash(){
		//添加一条数据 参数1：redis中的key  参数2：hash中的key(field) 参数3：hash中的value
		//		cluster.hset("key04","hkey01","hvalue01");
		//获取一条数据 参数1：redis中的key  参数2：hash中的key(field)
		String res04 = cluster.hget("key04", "hkey01");
		System.out.println("结果04："+res04);

		Map<String,String> map = new HashMap<>();
		map.put("name", "zhangsan");
		map.put("age","18");
		map.put("address","jhl");
		//添加多条数据
		cluster.hmset("userInfo", map);
		//获取全部hash值
		Map<String,String> userInfo = cluster.hgetAll("userInfo");
		for(Entry<String, String> entry: userInfo.entrySet()){
			System.out.println(entry.getKey()+":"+entry.getValue());
		}
		//指定key、field获取多条数据
		List<String> nameAge = cluster.hmget("userInfo", "name","age");
		for(String str:nameAge){
			System.out.println("结果05："+str);
		}
	}

	//操作list
	@Test
	public void testOperList(){
		//添加
		cluster.lpush("季节","春","夏","秋","冬");
		//获取
		List<String> strList = cluster.lrange("季节", 0, 3);
		for(String str:strList){
			System.out.println("季节："+str);
		}
	}

	//操作set
	@Test
	public void testOperSet(){
		//添加
		cluster.sadd("student","zhangsan","lisi","wangermazi");
		//获取
		Set<String> stuSet = cluster.smembers("student");
		for(String str:stuSet){
			System.out.println("student:"+str);
		}
	}

	//操作sorted set 常用于排序 成绩排名等‘
	@Test
	public void testOperSortedSet(){
//		Map<String,Double> scoreMembers = new HashMap<String,Double>();
//		scoreMembers.put("zhangsan",1d);
//		scoreMembers.put("wangermazi",2d);
//		scoreMembers.put("lisi",3d);
//		scoreMembers.put("lisi",4d);
//		//添加
//		cluster.zadd("userCart10001", scoreMembers);
		System.out.println("count:"+cluster.zcount("userCart10001", 0,999999999));
		System.out.println(System.currentTimeMillis());
		//获取
//		Set<String> scores = cluster.zrange("userCart10001", 0,0);
//		for(String score:scores){
//			System.out.println("排名："+score);
//		}
//		
		cluster.del("userCart10001");

	}
	
	/**
	 * 删除所有数据
	 */
	@Test
	public void testDeleteAll(){
		Set<byte[]> keysAll = getAllRediskeys(cluster);
		for(byte[] key:keysAll){
			cluster.del(key);
		}
	}
	
	/**
	 * 获取所有key的方法
	 */
	private Set<byte[]> getAllRediskeys(JedisCluster jedisCluster){
		Set<byte[]> keysAll = new HashSet<>();
		//获取到所有连接池
		Map<String, JedisPool> poolMap = jedisCluster.getClusterNodes();
		for(Entry<String, JedisPool> entry:poolMap.entrySet()){
			Jedis jedis = entry.getValue().getResource();
			byte[] pattern = {'*'};
			Set<byte[]> keys = jedis.keys(pattern);
			keysAll.addAll(keys);
			
		}
		return keysAll;
		
	}
	
	
	//操作hash
		@Test
		public void testOperHashByte(){
//			String key = "userCart10001";
//			Map<byte[],byte[]> hash = new HashMap<>();
//			hash.put(SerializeUtil.serialize("qwe123"), SerializeUtil.serialize("qwe123v"));
//			hash.put(SerializeUtil.serialize("qwe1234"), SerializeUtil.serialize("qwe1234v"));
//			//hash 添加
////			cluster.hmset(SerializeUtil.serialize(key), hash);
//			
//			cluster.hdel(SerializeUtil.serialize(key), SerializeUtil.serialize("qwe123"),SerializeUtil.serialize("qwe1234"));
//			
//			//获取
//			Map<byte[], byte[]>  result = cluster.hgetAll(SerializeUtil.serialize(key));
//			for(Entry<byte[], byte[]> entry:result.entrySet()){
//				String str = (String) SerializeUtil.unserialize(entry.getValue());
//				System.out.println("结果："+str);
//			}
			String key = "userCart2";
			cluster.del(SerializeUtil.serialize(key));
		}
		
		/**
		 * redis 自增
		 */
		@Test
		public void testIncr(){
			String key = "redis_incr";
			Long result = cluster.incr(key);
			System.out.println(result);
		}
}
