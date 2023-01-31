package cn.knightzz.jedis.basetype;

import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * @author 王天赐
 * @title: JedisApiString
 * @projectName redis-demo
 * @description:
 * @website <a href="http://knightzz.cn/">http://knightzz.cn/</a>
 * @github <a href="https://github.com/knightzz1998">https://github.com/knightzz1998</a>
 * @create: 2023-01-30 16:00
 */
public class JedisApiString {

    public static void main(String[] args) {


        Jedis jedis = new Jedis("127.0.0.1", 6379);

        jedis.mset("str01", "v1", "str02", "v2");

        List<String> valueList = jedis.mget("str01", "str02");

        for (String value : valueList) {
            System.out.println(value);
        }

        // 关闭连接
        jedis.close();

    }
}
