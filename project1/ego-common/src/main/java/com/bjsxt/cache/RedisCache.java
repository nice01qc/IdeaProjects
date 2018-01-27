package com.bjsxt.cache;

import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.ibatis.cache.Cache;

import com.bjsxt.util.SerializeUtil;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

/**
 * redis缓存 对象 供 mybatis进行调用
 * @author zhiduo
 *
 */
public class RedisCache implements Cache {

	private String id;
	
	//全局读写锁
	private static final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	
	//redisCluster对象
	private static final JedisCluster jedisCluster = getJedisCluster();
	
	//缓存标识(唯一标识)
	private static final String cacheFlag = "redis_mybatis_cache_775e64aa499942cba767235532e51e90";
	
	/**
	 * 构造器
	 * @param id
	 */
	public RedisCache(String id) {
		super();
		this.id = id;
	}

	/**
	 * 清空缓存
	 */
	@Override
	public void clear() {
		// TODO Auto-generated method stub
		Set<byte[]> keys = getAllRediskeys(jedisCluster);
		for(byte[] key:keys){
			jedisCluster.del(key);
		}
	}

	/**
	 * 缓存id
	 */
	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return id;
	}

	/**
	 * 添加缓存
	 */
	@Override
	public void putObject(Object key, Object value) {
		jedisCluster.set(SerializeUtil.serialize(cacheFlag+key), SerializeUtil.serialize(value));
	}
	/**
	 * 获取缓存
	 */
	@Override
	public Object getObject(Object key) {
		return SerializeUtil.unserialize(jedisCluster.get(SerializeUtil.serialize(cacheFlag+key)));
	}

	/**
	 * 获取读写锁
	 */
	@Override
	public ReadWriteLock getReadWriteLock() {
		return readWriteLock;
	}

	/**
	 * 获取缓存个数
	 */
	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return jedisCluster.dbSize().intValue();
	}

	/**
	 * 删除缓存
	 */
	@Override
	public Object removeObject(Object key) {
		return jedisCluster.del(SerializeUtil.serialize(cacheFlag+key));
	}
	
	//得到redisCluster
	private static JedisCluster getJedisCluster(){
		Set<HostAndPort> nodes = new HashSet<>();
		nodes.add(new HostAndPort("192.168.88.11", 6379));
		nodes.add(new HostAndPort("192.168.88.12", 6379));
		nodes.add(new HostAndPort("192.168.88.13", 6379));
		nodes.add(new HostAndPort("192.168.88.14", 6379));
		nodes.add(new HostAndPort("192.168.88.15", 6379));
		nodes.add(new HostAndPort("192.168.88.16", 6379));
		return new JedisCluster(nodes);
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
			for(byte[] key:keys){
				//根据byte[] key转换为String
				String keyStr = new String(key);
				if(keyStr.contains(cacheFlag)){//如果key当中包含唯一缓存标识
					keysAll.add(key);
				}
			}
			
		}
		return keysAll;
		
	}

}
