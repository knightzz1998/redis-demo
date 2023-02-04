package cn.knightzz.interview.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.Set;

/**
 * @author 王天赐
 * @title: RedisRankTest
 * @projectName redis-demo
 * @description: Redis实现排行榜
 * @website <a href="http://knightzz.cn/">http://knightzz.cn/</a>
 * @github <a href="https://github.com/knightzz1998">https://github.com/knightzz1998</a>
 * @create: 2023-02-04 13:25
 */
@Slf4j
public class RedisRankTest {

    private static Jedis jedis = null;

    @BeforeAll
    public static void init() {
        jedis = new Jedis("127.0.0.1", 6379);
    }

    @Test
    public void addMember() {

        log.debug("添加成员以及分数信息");
        jedis.zadd("student-rank", 32, "Tom");
        jedis.zadd("student-rank", 80, "Zika");
        jedis.zadd("student-rank", 66, "jacklove");
        jedis.zadd("student-rank", 70, "rookie");
        jedis.zadd("student-rank", 70, "rookie2");
        jedis.zadd("student-rank", 23, "rookie3");
        jedis.zadd("student-rank", 78, "rookie4");
        jedis.zadd("student-rank", 69, "rookie5");

    }

    @Test
    public void rank() {

        // 从小到大排列
        Set<String> zrangeSet = jedis.zrange("student-rank", 0, -1);
        log.debug("从小到大排列结果 : ");
        zrangeSet.forEach((member) -> {
            System.out.println(member);
        });
        Set<String> zrevrangeByScore = jedis.zrevrangeByScore("student-rank", 100, 60);
        log.debug("60分以上的结果: ");
        zrevrangeByScore.forEach((item) -> {
            System.out.println(item);
        });
        log.debug("从大到小排列结果 : ");
        Set<Tuple> tuples = jedis.zrevrangeWithScores("student-rank", 0, -1);
        tuples.forEach((tuple) -> {
            System.out.println("姓名 " + tuple.getElement() + ", 分数 : " + tuple.getScore());
        });
    }

    @Test
    public void rankByMember() {

        log.debug("查看指定用户的排名");
        Long rookieRank = jedis.zrevrank("student-rank", "rookie");
        log.debug("rookie 排名 {} ", rookieRank);
    }

    @Test
    public void rankByDate() {

        log.debug("添加每日充值数据");
        // 添加数据
        // 2022年10月11日
        jedis.zadd("20221011", 19999, "王少");
        jedis.zadd("20221011", 39999, "王少");
        jedis.zadd("20221011", 39299, "张少");
        jedis.zadd("20221011", 49999, "李少");

        // 2022年10月12
        jedis.zadd("20221012", 39999, "王少");
        jedis.zadd("20221012", 29999, "王少");
        jedis.zadd("20221012", 9299, "张少");
        jedis.zadd("20221012", 19999, "李少");

    }

    @AfterAll
    public static void destroy() {
        jedis.close();
    }

}
