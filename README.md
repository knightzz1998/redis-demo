## 项目介绍

![Custom badge](https://img.shields.io/badge/redis-github-red)![Custom badge](https://img.shields.io/badge/github-code-red?style=plastic&logo=appveyor)

----

- :white_check_mark: Redis登陆短信验证
- 商户查询缓存
- 优惠券秒杀

## 文章列表

- [Java开发日志 - 误提交.idea文件的解决办法](https://blog.csdn.net/weixin_40040107/article/details/124481121)
- [Redis系列 - Redis的Java客户端](https://blog.csdn.net/weixin_40040107/article/details/124547074)

## 黑马点评项目介绍

主要使用Redis进行短信登陆

### 数据库表

**MySQL版本需要使用大于5.7以上的版本**

- tb_user:用户表
- tb_user_info:用户详情表
- tb_shop:商户信息表
- tb_shop_type:商户类型表
- tb_blog:用户日记表（达人探店日记)
- tb_follow:用户关注表
- tb_voucher:优惠券表
- tb_voucher_order:优惠券的订单表

### 基本功能

需要实现的功能流程图如下 : 

![](https://haloos.oss-cn-beijing.aliyuncs.com/typero/20220505143313.png)





### 缓存一致性



![img](https://haloos.oss-cn-beijing.aliyuncs.com/typero/1653617543277-44ba04e9-0df9-4f74-8162-f492bd66fde8.png)