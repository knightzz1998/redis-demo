package com.hmdp.interceptor;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.hmdp.dto.UserDTO;
import com.hmdp.utils.UserHolder;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.hmdp.utils.RedisConstants.LOGIN_USER_KEY_PREFIX;
import static com.hmdp.utils.RedisConstants.LOGIN_USER_TTL;

/**
 * @author 王天赐
 * @title: RefreshTokenInterceptor
 * @projectName redis-demo
 * @description:
 * @website http://knightzz.cn/
 * @github https://github.com/knightzz1998
 * @date 2022/5/17 20:23
 */
public class RefreshTokenInterceptor  implements HandlerInterceptor {

    StringRedisTemplate stringRedisTemplate;

    public RefreshTokenInterceptor(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 从header中获取token
        String token = request.getHeader("authorization");

        if (StrUtil.isBlank(token)) {
            response.setStatus(401);
            return false;
        }

        String tokenKey = LOGIN_USER_KEY_PREFIX + token;
        // 根据tokenKey从Redis中读取用户数据
        Map<Object, Object> userMap = stringRedisTemplate.opsForHash().entries(tokenKey);

        // 判断查询到的用户数据是否存在
        if (userMap.isEmpty()) {
            response.setStatus(401);
            return false;
        }

        // 将 Map 转换为 UserDTO
        UserDTO userDto = BeanUtil.fillBeanWithMap(userMap, new UserDTO(), false);
        // 用户存在 => 保存用户到 ThreadLocal
        UserHolder.saveUser((UserDTO) userDto);
        // 刷新redis的有效期
        stringRedisTemplate.expire(tokenKey, LOGIN_USER_TTL , TimeUnit.MINUTES);
        return true;
    }
}
