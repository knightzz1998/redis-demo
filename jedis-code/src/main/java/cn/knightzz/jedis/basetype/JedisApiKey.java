package cn.knightzz.jedis.basetype;

import redis.clients.jedis.Jedis;

import java.util.Set;

/**
 * @author 王天赐
 * @title: JedisApiKey
 * @projectName redis-demo
 * @description: 基本Key的使用
 * @website <a href="http://knightzz.cn/">http://knightzz.cn/</a>
 * @github <a href="https://github.com/knightzz1998">https://github.com/knightzz1998</a>
 * @create: 2023-01-30 15:40
 */
public class JedisApiKey {

    public static void main(String[] args) {


        Jedis jedis = new Jedis("127.0.0.1", 6379);

        String ping = jedis.ping();
        System.out.println(" 连接成功 " + ping);

        jedis.set("key01", "value01");
        jedis.set("key02", "value02");
        jedis.set("key03", "value03");
        jedis.set("key04", "value04");

        // 获取所有的 key

        Set<String> keys = jedis.keys("*");

        System.out.println("=============================================");
        for (String key : keys) {
            System.out.println(key);
        }
        System.out.println("=============================================");


        System.out.println("jedis.exists(key01) = " + jedis.exists("key01"));
        // TTL 以秒为单位，返回给定 key 的剩余生存时间
        System.out.println("jedis.ttl(key01) = " + jedis.ttl("key01"));

        System.out.println("jedis.get(key01) = " + jedis.get("key01"));

        // 关闭连接
        jedis.close();

    }
}
