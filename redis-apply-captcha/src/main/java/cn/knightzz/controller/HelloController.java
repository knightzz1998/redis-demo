package cn.knightzz.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.*;

/**
 * @author 王天赐
 * @title: HelloController
 * @projectName redis-demo
 * @description:
 * @website <a href="http://knightzz.cn/">http://knightzz.cn/</a>
 * @github <a href="https://github.com/knightzz1998">https://github.com/knightzz1998</a>
 * @create: 2023-01-31 11:11
 */
@RestController
public class HelloController {

    @PostMapping("/hello")
    public JSONObject hello(@RequestBody JSONObject selectedList) {

        System.out.println("hello...");
        JSONObject result = new JSONObject();
        result.put("status", 404);
        return result;
    }
}
