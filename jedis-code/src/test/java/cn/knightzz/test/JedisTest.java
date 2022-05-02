package cn.knightzz.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;

/**
 * @author 王天赐
 * @title: JedisTest
 * @projectName redis-demo
 * @description:
 * @website http://knightzz.cn/
 * @github https://github.com/knightzz1998
 * @date 2022/5/2 16:38
 */
public class JedisTest {

    private Jedis jedis;

    @BeforeEach
    void setUp(){

        // 1. 建立连接
        jedis = new Jedis();
        // 2. 设置密码
        // jedis.auth("");
        // 3. 选择库
        jedis.select(0);
    }

    @Test
    void testString(){

        // 插入数据
        jedis.set("name", "张三");
        // 获取插入的数据
        String name = jedis.get("name");
        System.out.println("name = " + name);
    }


    @AfterEach
    void tearDown(){
        // 释放资源
        if (jedis != null) {
            jedis.close();
        }
    }

}
