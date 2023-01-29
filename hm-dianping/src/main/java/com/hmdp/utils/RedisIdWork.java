package com.hmdp.utils;


import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * @author 王天赐
 * @title: RedisIdWork
 * @projectName redis-demo
 * @description:
 * @website http://knightzz.cn/
 * @github https://github.com/knightzz1998
 * @date 2022/5/29 16:38
 */
@Component
public class RedisIdWork {

    /**
     * 起始时间
     */
    public static final long BEGIN_TIMESTAMP = 1640995200L;

    /**
     * 序列号位数
     */
    public static final int COUNT_BITS = 32;

    public StringRedisTemplate stringRedisTemplate;

    public RedisIdWork(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public long nextId(String keyPrefix) {
        // 1. 生成时间戳
        LocalDateTime now = LocalDateTime.now();
        long epochSecond = now.toEpochSecond(ZoneOffset.UTC);
        long timestamp = epochSecond - BEGIN_TIMESTAMP;

        // 2. 生成序列号
        // 获取当前日期
        String nowDate = now.format(DateTimeFormatter.ofPattern("yyyy:MM:dd"));
        // 序列号
        long count = stringRedisTemplate.opsForValue().increment("icr:" + keyPrefix + ":" + nowDate);

        // 3. 拼接并返回 => 时间戳 + 序列号
        return (timestamp << COUNT_BITS ) | count;
    }

    public static void main(String[] args) {

        LocalDateTime dateTime = LocalDateTime.of(2022, 1, 1, 0, 0, 0);
        // 获取1970-01-01 00:00:00 到 2022-01-01 00:00:00 的时间戳
        long second = dateTime.toEpochSecond(ZoneOffset.UTC);
        System.out.println(second);
    }

}
