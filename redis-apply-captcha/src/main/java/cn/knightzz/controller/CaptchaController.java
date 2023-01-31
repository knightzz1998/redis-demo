package cn.knightzz.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import java.util.Random;

/**
 * @author 王天赐
 * @title: CaptchaController
 * @projectName redis-demo
 * @description: 验证码处理
 * @website <a href="http://knightzz.cn/">http://knightzz.cn/</a>
 * @github <a href="https://github.com/knightzz1998">https://github.com/knightzz1998</a>
 * @create: 2023-01-30 16:57
 */
@RestController
public class CaptchaController {

    Jedis jedis = new Jedis("127.0.0.1",6379);

    public String verifyCode(String phone){

        // 构造key
        // 存储验证码次数的key
        String countKey = "verify:"+phone+":count";
        // 存储验证码的key
        String codeKey = "verify:"+phone+":code";

        String count = jedis.get(countKey);
        if(count == null) {
            // 首次发送验证码
            jedis.setex(countKey, 24 * 60 * 60, "1");
        }else if(Integer.parseInt(codeKey) <= 2) {
            // 3 次以内
            jedis.incr(codeKey);
        }else{
            return "今天的发送次数已经超过3次!";
        }

        String code = getVerifyCode();
        jedis.set(codeKey, code);
        return code;
    }


    @PostMapping("/sendCaptcha")
    public JSONObject sendCaptchaCode(@RequestBody JSONObject params) {

        String phone = (String) params.get("phone");

        String code = verifyCode(phone);

        // 生成六位的验证码
        System.out.println("验证码发送成功!");

        JSONObject result = new JSONObject();
        result.put("code", code);

        return result;
    }

    private static String getVerifyCode() {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for(int i = 0 ; i < 6 ; i++) {
            code.append(random.nextInt(10));
        }
        return code.toString();
    }

    @PostMapping("/verifyCaptcha")
    public JSONObject verifyCaptcha(@RequestBody JSONObject params){

        String phone = (String) params.get("phone");
        String code = (String) params.get("code");

        String codeKey = "verify:"+phone+":code";

        String codeFromRedis = jedis.get(codeKey);

        JSONObject result = new JSONObject();
        if(codeFromRedis != null && codeFromRedis.equals(code)){
            result.put("result", "<p style=\"color:green\">验证码正确!</p>");
        }else{
            result.put("result", "<p style=\"color:red\">验证码错误!</p>");
        }
        return result;
    }

}
