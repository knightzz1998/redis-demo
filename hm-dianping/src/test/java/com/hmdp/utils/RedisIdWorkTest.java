package com.hmdp.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author 王天赐
 * @title: RedisIdWorkTest
 * @projectName redis-demo
 * @description:
 * @website http://knightzz.cn/
 * @github https://github.com/knightzz1998
 * @date 2022/5/29 19:03
 */
@SpringBootTest
class RedisIdWorkTest {

    @Resource
    RedisIdWork redisIdWork;

    private ExecutorService es = Executors.newFixedThreadPool(500);
    @Test
    void nextId() {
        // https://blog.csdn.net/hbtj_1216/article/details/109655995
        CountDownLatch latch = new CountDownLatch(300);
        Runnable task = () -> {
            for (int i = 0; i < 100; i++) {
                long id = redisIdWork.nextId("order");
                System.out.println("id = " + id);
            }
        };

        long start = System.currentTimeMillis();
        for (int i = 0; i < 300; i++) {
            es.submit(task);
        }

        long end = System.currentTimeMillis();
        System.out.println("time = " + (end - start));
    }
}