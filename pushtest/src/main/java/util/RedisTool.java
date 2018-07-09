package util;

import java.util.List;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisTool {
    private static Logger logger = (Logger)LogManager.getLogger("RedisTool");
    private static String host = "127.0.0.1";
    private static int port = 6379;
    private static Jedis jedis;
    private static JedisPool jedisPool;

    public RedisTool() {
    }

    private static void initialPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(100);
        config.setMinIdle(10);
        config.setMaxWaitMillis(-1L);
        config.setTestOnBorrow(false);
        config.setEvictionPolicyClassName("org.apache.commons.pool2.impl.DefaultEvictionPolicy");
        config.setNumTestsPerEvictionRun(6);
        jedisPool = new JedisPool(config, host, port);
    }

    public static synchronized void emptyRedis() {
        initialPool();
        jedis = jedisPool.getResource();
        jedis.flushDB();
    }

    public static Long delKey(String key) {
        return jedis.del(key);
    }

    public static Long delSetData(String key, String value) {
        return jedis.srem(key, new String[]{value});
    }

    public static synchronized boolean isExit(String key) {
        boolean result = false;

        try {
            result = jedis.exists(key);
        } catch (Exception var5) {
            try {
                Thread.sleep(1000L);
                result = jedis.exists(key);
            } catch (Exception var4) {
                logger.error("jedis exists get a error! (isExit method)");
                emptyRedis();
            }
        }

        return result;
    }

    public static synchronized void listAddValueByKey(String key, String value) {
        try {
            jedis.lpush(key, new String[]{value});
        } catch (Exception var3) {
            logger.error("jedis lpush method get a error! (listAddValueByKey method) ");
            emptyRedis();
        }

    }

    public static long getListLengthByKey(String key) {
        long result = 0L;
        if (!jedis.exists(key)) {
            return result;
        } else {
            try {
                result = jedis.llen(key);
            } catch (Exception var4) {
                logger.error("jedis llen method get a error ! ( getListLengthByKey method )");
            }

            return result;
        }
    }

    public static void setAddValueByKey(String key, String value) {
        try {
            jedis.sadd(key, new String[]{value});
        } catch (Exception var3) {
            logger.error("jedis sadd method get a error ! ( setAddValueByKey method)");
            emptyRedis();
        }

    }

    public static synchronized List<String> getAllByKey(String key) {
        List result = null;

        try {
            result = jedis.lrange(key, 0L, -1L);
        } catch (Exception var3) {
            logger.error("jedis lrange method get a error ! (getAllByKey method)");
            emptyRedis();
        }

        return result;
    }

    public static Set<String> getAllSetValue(String key) {
        Set result = null;

        try {
            result = jedis.smembers(key);
        } catch (Exception var3) {
            logger.error("jdeis smembers get a error ! (getAllSetValue method) ");
            emptyRedis();
        }

        return result;
    }

    static {
        initialPool();
        jedis = jedisPool.getResource();
        jedis.flushDB();
    }
}
