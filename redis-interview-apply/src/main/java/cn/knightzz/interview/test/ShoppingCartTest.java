package cn.knightzz.interview.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 王天赐
 * @title: ShoppingCartTest
 * @projectName redis-demo
 * @description:
 * @website <a href="http://knightzz.cn/">http://knightzz.cn/</a>
 * @github <a href="https://github.com/knightzz1998">https://github.com/knightzz1998</a>
 * @create: 2023-02-04 12:33
 */
@Slf4j
public class ShoppingCartTest {

    private static Jedis jedis = null;

    @BeforeAll
    public static void init() {
        jedis = new Jedis("127.0.0.1", 6379);
    }

    @Test
    public void addShop() {

        log.debug("添加商品 : ");

        // 存储 [商品id, 数量] 的键值对
        Map<String, String> map = new HashMap<>();
        map.put("s001", "2");
        map.put("s002", "3");
        map.put("s003", "4");
        // 将用户di作为hashKey
        String userId01 = "1001";
        String userId02 = "1002";

        jedis.hset(userId01, map);
        jedis.hset(userId02, map);
    }

    @Test
    public void selectAll() {

        log.debug("查询购物车信息 : ");

        Map<String, String> kv = jedis.hgetAll("1001");

        if (kv != null && kv.size() > 0) {
            kv.forEach((shopId, count) -> {
                log.debug("商品编号 : {} , 商品数量 : {} ", shopId, count);
            });
        } else {
            log.warn("查询的数据为空 !");
        }

    }

    @Test
    public void editShop() {

        log.debug("\n查询购物车信息 : ");

        Map<String, String> kv = jedis.hgetAll("1001");

        kv.forEach((shopId, count) -> {
            log.debug("商品编号 : {} , 商品数量 : {} ", shopId, count);
        });

        log.debug("\n修改购物车信息 : ");

        jedis.hset("1001", "s001", "200");

        kv = jedis.hgetAll("1001");
        kv.forEach((shopId, count) -> {
            log.debug("商品编号 : {} , 商品数量 : {} ", shopId, count);
        });

    }

    @Test
    public void deleteShop() {
        log.debug("\n删除用户 1001 购物车Id为s001的商品 : ");

        jedis.hdel("1001", "s001");

        selectAll();
    }

    @Test
    public void deleteCart() {
        log.debug("\n清空用户id为 1001 购物车: ");

        // 这里用的是默认的 del , 直接删除 key
        jedis.del("1001");
        selectAll();
    }

    @AfterAll
    public static void destroy() {
        jedis.close();
    }
}
