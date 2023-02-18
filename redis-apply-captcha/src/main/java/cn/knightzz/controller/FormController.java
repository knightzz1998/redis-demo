package cn.knightzz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 王天赐
 * @title: FormController
 * @projectName redis-demo
 * @description:
 * @website <a href="http://knightzz.cn/">http://knightzz.cn/</a>
 * @github <a href="https://github.com/knightzz1998">https://github.com/knightzz1998</a>
 * @create: 2023-02-05 19:43
 */
@Controller
public class FormController {


    @RequestMapping("/form2")
    public String form(@RequestParam(value = "selectedList") String selectedList) {
        return "success";
    }

}
