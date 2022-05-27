package com.hmdp.utils;

/**
 * Redis KEY
 * @author knightzz98
 */
public class RedisConstants {

    /**
     * 用户登陆验证码的KEY
     */
    public static final String LOGIN_CODE_KEY = "login:code";

    /**
     * 用户登录验证码过期时间(单位: 分钟)
     */
    public static final Long LOGIN_CODE_TTL = 2L;

    /**
     * 存储用户信息KEY前缀
     */
    public static final String LOGIN_USER_KEY_PREFIX = "login:user:";

    /**
     * 用户信息过期时间(单位: 分钟)
     */
    public static final Long LOGIN_USER_TTL = 30L;

    /**
     * 商品查询缓存前缀
     */
    public static final String CACHE_SHOP_PREFIX = "shop:cache:";

    /**
     * 商品缓存过期时间
     */
    public static final Long CACHE_SHOP_TTL = 30L;
}
