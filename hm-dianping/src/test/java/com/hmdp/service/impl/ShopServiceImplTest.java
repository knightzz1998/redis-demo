package com.hmdp.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author 王天赐
 * @title: ShopServiceImplTest
 * @projectName redis-demo
 * @description:
 * @website http://knightzz.cn/
 * @github https://github.com/knightzz1998
 * @date 2022/5/28 14:55
 */
@SpringBootTest
class ShopServiceImplTest {

    @Resource
    ShopServiceImpl shopService;

    @Test
    void saveShopToRedis() throws InterruptedException {
        shopService.saveShopToRedis(1L, 10L);
    }
}