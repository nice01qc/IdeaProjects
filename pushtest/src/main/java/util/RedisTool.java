package util;

import redis.clients.jedis.*;

import java.util.List;
import java.util.Set;

public class RedisTool {
    private static String host = "127.0.0.1";
    private static int port = 6379;
    private static Jedis jedis;//非切片额客户端连接
    private static JedisPool jedisPool;//非切片连接池

    static {
        initialPool();
        jedis = jedisPool.getResource();
        emptyRedis();
    }

    // 初始化非切片池
    private static void initialPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(20);
        config.setMinIdle(3);
        config.setMaxWaitMillis(10001);
        config.setTestOnBorrow(false);
        config.setEvictionPolicyClassName("org.apache.commons.pool2.impl.DefaultEvictionPolicy");
        config.setNumTestsPerEvictionRun(2);
        jedisPool = new JedisPool(config, host, port);
    }

    // 清空redis缓存
    public static String emptyRedis() {
        return jedis.flushDB();
    }

    public static Long delKey(String key) {
        return jedis.del(key);
    }

    public static Long delSetData(String key, String value) {
        return jedis.srem(key, value);
    }

    // 是否存在某个key
    public static boolean isExit(String key) {
        return jedis.exists(key);
    }

    // 添加数据
    public static Long listAddValueByKey(String key, String value) {
        return jedis.lpush(key, value);
    }

    public static Long setAddValueByKey(String key, String value) {
        return jedis.sadd(key, value);
    }

    public static String setStringValue(String key, String value) {
        return jedis.set(key, value);
    }

    // 设置过期时间
    public static Long setExpire(String key, int seconds) {
        return jedis.expire(key, seconds);
    }

    // 获取所有数据
    public static List<String> getAllByKey(String key) {
        return jedis.lrange(key, 0, -1);
    }

    public static Set<String> getAllSetValue(String key) {
        return jedis.smembers(key);
    }



    public static String getStringValue(String key) {
        return jedis.get(key);
    }

}
