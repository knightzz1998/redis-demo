package cn.knightzz.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 王天赐
 * @title: User
 * @projectName redis-demo
 * @description:
 * @website http://knightzz.cn/
 * @github https://github.com/knightzz1998
 * @date 2022/5/2 20:43
 */
@Data
public class User implements Serializable {
    private String name;
    private Integer age;
}
