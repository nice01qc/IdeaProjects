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
    private static JedisPool jedisPool;
    private static Object lock = new Object();

    static {
        initialPool();
        Jedis jedis = jedisPool.getResource();
        jedis.flushDB();
        jedis.close();
    }

    private static void initialPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(20);
        config.setMaxIdle(15);
        config.setMinIdle(5);
        config.setMaxWaitMillis(600);
        config.setBlockWhenExhausted(true);
        config.setTestOnBorrow(false);
        config.setNumTestsPerEvictionRun(3);
        config.setTimeBetweenEvictionRunsMillis(5000);
        jedisPool = new JedisPool(config, host, port);
    }

    public static void emptyRedis() {
            Jedis jedis = null;
            try{
                jedis = jedisPool.getResource();
                jedis.flushDB();
            }catch (Exception e){

            }finally {
                jedis.close();
            }
    }

    public static Long delKey(String key) {
        Jedis jedis = null;
        Long result = null;
        try{
            jedis = jedisPool.getResource();
            result = jedis.del(key);
        }catch (Exception e){

        }finally {
            jedis.close();
        }
        return result;
    }

    public static Long delSetData(String key, String value) {
        Jedis jedis = null;
        Long result = null;
        try{
            jedis = jedisPool.getResource();
            result = jedis.srem(key, new String[]{value});
        }catch (Exception e){

        }finally {
            jedis.close();
        }
        return result;
    }

    public static boolean isExit(String key) {
            Jedis jedis = null;
            boolean result = false;
            try {
                jedis = jedisPool.getResource();
                result = jedis.exists(key);
            } catch (Exception var5) {
                logger.error("jedis exists get a error! (isExit method)");
            }finally {
                jedis.close();
            }
            return result;
    }

    public static void listAddValueByKey(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.lpush(key, new String[]{value});
        } catch (Exception var3) {
            logger.error("jedis lpush method get a error! (listAddValueByKey method) ");
        }finally {
            jedis.close();
        }
    }

    public static long getListLengthByKey(String key) {
        Jedis jedis = null;
        long result = 0L;
        try{
            jedis = jedisPool.getResource();
            if (!jedis.exists(key)) {
                return result;
            } else {
                result = jedis.llen(key);
            }
        }catch (Exception e){
            logger.error("jedis llen method get a error ! ( getListLengthByKey method )");
        }finally {
            jedis.close();
        }
        return result;
    }

    public static void setAddValueByKey(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.sadd(key, new String[]{value});
        } catch (Exception var3) {
            logger.error("jedis sadd method get a error ! ( setAddValueByKey method)");
        }finally {
            jedis.close();
        }
    }

    public static List<String> getAllByKey(String key) {
        Jedis jedis = null;
        List<String> result = null;

        try {
            jedis = jedisPool.getResource();
            result = jedis.lrange(key, 0L, -1L);
        } catch (Exception var3) {
            logger.error("jedis lrange method get a error ! (getAllByKey method)");
        }finally {
            jedis.close();
        }
        return result;
    }

    public static Set<String> getAllSetValue(String key) {
        Jedis jedis = null;
        Set result = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.smembers(key);
        } catch (Exception var3) {
            logger.error("jdeis smembers get a error ! (getAllSetValue method) ");
        }finally {
            jedis.close();
        }
        return result;
    }

}
