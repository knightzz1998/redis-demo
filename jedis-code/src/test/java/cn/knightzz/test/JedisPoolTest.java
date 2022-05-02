package cn.knightzz.test;

import cn.knightzz.factory.JedisConnectionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;

/**
 * @author 王天赐
 * @title: JedisPoolTest
 * @projectName redis-demo
 * @description:
 * @website http://knightzz.cn/
 * @github https://github.com/knightzz1998
 * @date 2022/5/2 17:26
 */
public class JedisPoolTest {
    private Jedis jedis;

    @BeforeEach
    void setUp(){

        // 1. 通过连接池建立连接
        jedis = JedisConnectionFactory.getJedis();
        // 2. 设置密码
        // jedis.auth("");
        // 3. 选择库
        jedis.select(0);
    }

    @Test
    void testString(){

        // 插入数据
        jedis.set("pool", "连接池");
        // 获取插入的数据
        String pool = jedis.get("pool");
        System.out.println("pool = " + pool);
    }


    @AfterEach
    void tearDown(){
        // 释放资源
        if (jedis != null) {
            jedis.close();
        }
    }

}
