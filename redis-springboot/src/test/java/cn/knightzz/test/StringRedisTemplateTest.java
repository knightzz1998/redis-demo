package cn.knightzz.test;

import cn.knightzz.pojo.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;

/**
 * @author 王天赐
 * @title: StringRedisTemplateTest
 * @projectName redis-demo
 * @description:
 * @website http://knightzz.cn/
 * @github https://github.com/knightzz1998
 * @date 2022/5/2 21:11
 */
@SpringBootTest
public class StringRedisTemplateTest {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    // JSON工具
    private static final ObjectMapper mapper = new ObjectMapper();

    @Test
    void testObject() throws JsonProcessingException {
        // 准备对象
        User user = new User();
        user.setAge(22);
        user.setName("stringRedisTemplate");
        // 手动序列化成JSON
        String json = mapper.writeValueAsString(user);

        // 将数据写入json
        stringRedisTemplate.opsForValue().set("userString", json);
        // 读取数据
        String userString = stringRedisTemplate.opsForValue().get("userString");
        // 反序列化
        User userFromJson = mapper.readValue(userString, User.class);
        System.out.println("userFromJson = " + userFromJson);
    }
}
