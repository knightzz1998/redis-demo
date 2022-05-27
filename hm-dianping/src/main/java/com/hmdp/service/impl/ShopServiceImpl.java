package com.hmdp.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.hmdp.dto.Result;
import com.hmdp.entity.Shop;
import com.hmdp.mapper.ShopMapper;
import com.hmdp.service.IShopService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.concurrent.TimeUnit;

import static com.hmdp.utils.RedisConstants.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */

@Slf4j
@Service
public class ShopServiceImpl extends ServiceImpl<ShopMapper, Shop> implements IShopService {

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Override
    public Result queryById(Long id) {
        log.debug("id {} 当前时间 {}" , id , DateUtil.date());
        String redisKey = CACHE_SHOP_PREFIX + id;

        // 从Redis中查询商户缓存
        String resultFromRedis = stringRedisTemplate.opsForValue().get(redisKey);

        // 如果缓存存在则直接返回
        if (StrUtil.isNotBlank(resultFromRedis)) {
            // 将JSON数据转换为Bean
            Shop shop = JSONUtil.toBean(resultFromRedis, Shop.class);
            return Result.ok(shop);
        }

        if (resultFromRedis != null) {
            // 命中缓存 && 缓存值为空的情况
            return Result.fail("商户信息存在");
        }

        // 如果不存在 , 则从数据库中查询
        Shop shop = getById(id);

        // 如果数据库中未查询到 => 报错
        if (shop == null) {
            // 未查询到直接向缓存中写入空值
            stringRedisTemplate.opsForValue().set(redisKey, "", CACHE_NULL_TTL, TimeUnit.MINUTES);
            return Result.fail("店铺不存在!");
        }
        // 如果查询到, 存储到Redis中 , 并设置缓存过期时间
        stringRedisTemplate.opsForValue().set(redisKey, JSONUtil.toJsonStr(shop), CACHE_SHOP_TTL, TimeUnit.MINUTES);


        return Result.ok(shop);
    }

    @Override
    public Result updateShop(Shop shop) {

        // 先判断商户是否存在
        Long id = shop.getId();
        if (id == null) {
            return Result.fail("商户id不能为空");
        }

        // 先修改数据库
        updateById(shop);

        // 再删除缓存
        String redisKey = CACHE_SHOP_PREFIX + id;
        stringRedisTemplate.delete(redisKey);

        return Result.ok();
    }
}
