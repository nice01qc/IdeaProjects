package util;

import redis.clients.jedis.*;

import java.util.List;

public class RedisTool {
    private static String host = "127.0.0.1";
    private static int port = 6379;
    private static Jedis jedis;//非切片额客户端连接
    private static JedisPool jedisPool;//非切片连接池

    static {
        initialPool();
        jedis = jedisPool.getResource();
        jedis.set("imgdir","C:\\Users\\nice01qc\\Desktop\\");
    }

    // 初始化非切片池
    private static void initialPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(20);
        config.setMaxIdle(5);
        config.setMaxWaitMillis(10001);
        config.setTestOnBorrow(false);

        jedisPool = new JedisPool(config, host, port);
    }

    // 清空redis缓存
    public static String emptyRedis() {
        return jedis.flushDB();
    }

    // 清空给定key
    public static Long delByKey(String key) {
        return jedis.del(key);
    }

    // 增加某个key
    public static Long increKeyValue(String key){
        return jedis.incr(key);
    }

    // 是否存在某个key
    public static boolean isExit(String key){
        return jedis.exists(key);
    }

    // 添加数据
    public static Long addValueByKey(String key, String value) {
        return jedis.lpush(key, value);
    }

    // 设置过期时间
    public static Long setExpire(String key, int seconds) {
        return jedis.expire(key, seconds);
    }

    // 获取所有数据
    public static List<String> getAllByKey(String key){
        return jedis.lrange(key,0,-1);
    }


}
