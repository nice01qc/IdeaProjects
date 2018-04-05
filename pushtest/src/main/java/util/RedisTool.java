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
        emptyRedis();
        jedis.set("imgdir", "/var/www/html/");
        jedis.set("webdir","http://118.25.1.128/");
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

    // 是否存在某个key
    public static boolean isExit(String key) {
        return jedis.exists(key);
    }

    // 添加数据
    public static Long listAddValueByKey(String key, String value) {
        return jedis.lpush(key, value);
    }
    public static String stringSetValueByKey(String key,String value){
        return jedis.set(key,value);
    }

    // 设置过期时间
    public static Long setExpire(String key, int seconds) {
        return jedis.expire(key, seconds);
    }

    // 通过key获取信息
    public static String getByKey(String key) {
        return jedis.get(key);
    }

    // 获取所有数据
    public static List<String> getAllByKey(String key) {
        return jedis.lrange(key, 0, -1);
    }


}
