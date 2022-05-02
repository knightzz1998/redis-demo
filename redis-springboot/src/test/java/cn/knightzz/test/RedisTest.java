package cn.knightzz.test;

import cn.knightzz.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

/**
 * @author 王天赐
 * @title: RedisTest
 * @projectName redis-demo
 * @description:
 * @website http://knightzz.cn/
 * @github https://github.com/knightzz1998
 * @date 2022/5/2 20:25
 */
@SpringBootTest
public class RedisTest {

    @Resource
    private RedisTemplate redisTemplate;


    @Test
    void testString(){

        redisTemplate.opsForValue().set("name03", "SpringBoot");
        String name03 = (String) redisTemplate.opsForValue().get("name03");
        System.out.println("name03 = " + name03);
    }

    @Test
    void testObject(){
        User user = new User();
        user.setName("张飞");
        user.setAge(12);
        redisTemplate.opsForValue().set("user01", user);
    }
}
