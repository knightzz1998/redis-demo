package cn.knightzz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 王天赐
 * @title: HelloController
 * @projectName redis-demo
 * @description:
 * @website <a href="http://knightzz.cn/">http://knightzz.cn/</a>
 * @github <a href="https://github.com/knightzz1998">https://github.com/knightzz1998</a>
 * @create: 2023-01-31 11:11
 */
@Controller
public class HelloController {

    @RequestMapping("/hello")
    public String hello() {
        System.out.println("hello...");
        return "hello";
    }

}
