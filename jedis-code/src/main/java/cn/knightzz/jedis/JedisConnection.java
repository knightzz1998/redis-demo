package cn.knightzz.jedis;

import redis.clients.jedis.Jedis;

/**
 * @author 王天赐
 * @title: JedisDemo01
 * @projectName redis-demo
 * @description:
 * @website <a href="http://knightzz.cn/">http://knightzz.cn/</a>
 * @github <a href="https://github.com/knightzz1998">https://github.com/knightzz1998</a>
 * @create: 2023-01-30 15:29
 */
public class JedisConnection {

    public static void main(String[] args) {

        Jedis jedis = new Jedis("127.0.0.1", 6379);

        String ping = jedis.ping();

        System.out.println("连接成功 " + ping);

        // 关闭连接
        jedis.close();

    }
}
