package cn.knightzz.jedis.basetype;

import redis.clients.jedis.Jedis;

import java.util.Set;

/**
 * @author 王天赐
 * @title: JedisApiString
 * @projectName redis-demo
 * @description:
 * @website <a href="http://knightzz.cn/">http://knightzz.cn/</a>
 * @github <a href="https://github.com/knightzz1998">https://github.com/knightzz1998</a>
 * @create: 2023-01-30 16:00
 */
public class JedisApiSet {

    public static void main(String[] args) {


        Jedis jedis = new Jedis("127.0.0.1", 6379);

        // 列表尾部添加数据
        jedis.sadd("jedis-set", "set01","set02","set03","set04");

        // 删除指定值
        jedis.srem("jedis-set", "set02");

        // 获取刚刚添加的所有数据
        Set<String> smembers = jedis.smembers("jedis-set");
        for (String value : smembers) {
            System.out.println(value);
        }
        // 关闭连接
        jedis.close();

    }
}
