package com.hmdp.utils;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 王天赐
 * @title: RedisData
 * @projectName redis-demo
 * @description:
 * @website http://knightzz.cn/
 * @github https://github.com/knightzz1998
 * @date 2022/5/27 19:03
 */
@Data
public class RedisData {

    /**
     * 逻辑过期时间
     */
    private LocalDateTime expireTime;
    private Object data;

}
