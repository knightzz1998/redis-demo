package com.hmdp.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmdp.dto.LoginFormDTO;
import com.hmdp.dto.Result;
import com.hmdp.dto.UserDTO;
import com.hmdp.entity.User;
import com.hmdp.mapper.UserMapper;
import com.hmdp.service.IUserService;
import com.hmdp.utils.RegexUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.hmdp.utils.RedisConstants.*;
import static com.hmdp.utils.SystemConstants.USER_NICK_NAME_PREFIX;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Result sendCode(String phone, HttpSession session) {

        // 校验手机号
        if (RegexUtils.isPhoneInvalid(phone)) {
            // 不符合条件 : 返回错误信息
            return Result.fail("手机号不合法!");
        }
        // 符合条件 : 生成验证码
        String code = RandomUtil.randomNumbers(6);
        // 使用 Redis 解决分布式Session共享的问题
        stringRedisTemplate.opsForValue().set(LOGIN_CODE_KEY, code, LOGIN_CODE_TTL, TimeUnit.MINUTES);

        // 打印日志 : 模拟真实发送验证码的过程
        log.debug("验证码 : {} 发送成功! ", code);

        return Result.ok();
    }

    @Override
    public Result login(LoginFormDTO loginForm, HttpSession session) {

        String phone = loginForm.getPhone();
        // 校验手机号
        if (RegexUtils.isPhoneInvalid(phone)) {
            return Result.fail(" 手机号不合法! ");
        }

        // 校验验证码
        String codeFromRedis = stringRedisTemplate.opsForValue().get(LOGIN_CODE_KEY);
        String code = loginForm.getCode();
        if (codeFromRedis == null || !codeFromRedis.equals(code)) {
            return Result.fail("验证码校验失败!");
        }

        // 判断用户是否存在 : select * from tb_user where phone = ?;
        User user = query().eq("phone", phone).one();
        // 如果用户已经注册
        // ...
        if (user == null) {
            // 如果没有注册, 创建用户, 并将用户保存在数据库中
            user = createWithSaveUser(loginForm);
        }

        // 生成UUID作为登陆凭证
        String token = UUID.randomUUID().toString(true);
        // 将用户数据保存到Redis中, 使用的存储方式为 Hash 结构
        UserDTO userDto = BeanUtil.copyProperties(user, UserDTO.class);
        // 将 userDTO 转换为 Map
        // 这里需要注意一个点, 由于使用的都是StringRedisTemplate, 所以要求所有数据类型都必须是String类型
        // 但是 UserDTO 的 id 字段是 Long 类型的, 所以会出问题, 我们需要手动指定类型
        Map<String, Object> userMap = BeanUtil.beanToMap(userDto, new HashMap<>(),
                CopyOptions.create()
                        .setIgnoreNullValue(true)
                        .setFieldValueEditor((fieldName, fieldValue) -> fieldValue.toString()));
        // 将数据存储到 Redis 中
        String tokenKey = LOGIN_USER_KEY_PREFIX + token;
        stringRedisTemplate.opsForHash().putAll(tokenKey, userMap);
        // 设置过期时间
        stringRedisTemplate.expire(tokenKey, LOGIN_USER_TTL, TimeUnit.MINUTES);
        // 注意 : token会被存储在浏览器, 每次发送请求header都会携带 token :  header['authorization']
        return Result.ok(token);
    }

    private User createWithSaveUser(LoginFormDTO loginForm) {
        User user = new User();
        user.setPhone(loginForm.getPhone());
        user.setNickName(USER_NICK_NAME_PREFIX + RandomUtil.randomString(10));
        // 保存数据到数据库中
        save(user);
        return user;
    }
}
