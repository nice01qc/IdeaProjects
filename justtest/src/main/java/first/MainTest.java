package first;

import redis.clients.jedis.Jedis;

public class MainTest {
    public static void main(String[] args) {

        Jedis jedis = new Jedis("127.0.0.1");

        System.out.println(jedis.ping());

        System.out.println(jedis.get("nice"));

        System.out.println(jedis.getDB());

        jedis.mset(
                "a","b","c","d"
        );

    }
}
