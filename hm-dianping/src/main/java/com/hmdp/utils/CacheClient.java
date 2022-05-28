package com.hmdp.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static com.hmdp.utils.RedisConstants.CACHE_NULL_TTL;

/**
 * @author 王天赐
 * @title: CacheClient
 * @projectName redis-demo
 * @description: 封装Redis工具类
 * @website http://knightzz.cn/
 * @github https://github.com/knightzz1998
 * @date 2022/5/28 10:39
 */
@Slf4j
@Component
public class CacheClient {

    public final StringRedisTemplate stringRedisTemplate;

    public CacheClient(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 设置缓存
     *
     * @param key
     * @param value
     * @param time  过期时间
     * @param unit  单位
     */
    public void set(String key, Object value, Long time, TimeUnit unit) {
        stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(value), time, unit);
    }

    /**
     * 设置逻辑过期
     * @param key
     * @param value
     * @param time
     * @param unit
     */
    public void setWithLogicalExpire(String key, Object value, Long time, TimeUnit unit) {

        RedisData redisData = new RedisData();
        redisData.setData(value);
        redisData.setExpireTime(LocalDateTime.now().plusSeconds(time));
        // 写入Redis
        stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(value), time, unit);
    }

    public <T, N> T queryWithPassThrough(String keyPrefix, N id, Class<T> type, Function<N, T> dbFallback, Long time, TimeUnit unit) {
        String redisKey = keyPrefix + id;

        // 从Tedis中查询商户缓存
        String redisJson = stringRedisTemplate.opsForValue().get(redisKey);

        // 如果缓存存在则直接返回
        if (StrUtil.isNotBlank(redisJson)) {
            // 将JSON数据转换为Bean
            return JSONUtil.toBean(redisJson, type);
        }

        if (redisJson != null) {
            // 命中缓存 && 缓存值为空的情况
            return null;
        }

        // 如果不存在 , 则从数据库中查询 => 传入数据库查询的逻辑
        T t = dbFallback.apply(id);

        // 如果不存在
        if (t == null) {

            // r如果不存在, 将空值写入到Redis中
            stringRedisTemplate.opsForValue().set(redisKey, "", CACHE_NULL_TTL, TimeUnit.MINUTES);
            return null;
        }

        // 如果存在, 则写入到Redis
        this.set(redisKey, t, time, unit);

        return t;
    }
}
