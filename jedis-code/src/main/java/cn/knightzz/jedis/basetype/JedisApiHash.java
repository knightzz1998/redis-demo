package cn.knightzz.jedis.basetype;

import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 王天赐
 * @title: JedisApiString
 * @projectName redis-demo
 * @description:
 * @website <a href="http://knightzz.cn/">http://knightzz.cn/</a>
 * @github <a href="https://github.com/knightzz1998">https://github.com/knightzz1998</a>
 * @create: 2023-01-30 16:00
 */
public class JedisApiHash {

    public static void main(String[] args) {


        Jedis jedis = new Jedis("127.0.0.1", 6379);

        jedis.hset("jedis-hash", "username" , "root");
        jedis.hset("jedis-hash", "password" , "123456");

        String username = jedis.hget("jedis-hash", "username");
        System.out.println("username = " + username);

        // 批量添加
        Map<String, String> map = new HashMap<String, String>();

        map.put("name" , "张三");
        map.put("age", "37");


        jedis.hmset("user", map);

        List<String> stringList = jedis.hmget("user", "name", "age");

        System.out.println(stringList);

        // 关闭连接
        jedis.close();

    }
}
