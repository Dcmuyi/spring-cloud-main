package com.muyi.servicestudy.controller;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.muyi.servicestudy.utils.RedisCacheUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author Muyi, dcmuyi@qq.com
 * @date 2019/11/5.
 */
@Slf4j
@RestController
@RequestMapping("/demo/redis")
public class RedisDemoController {
    @Autowired
    private RedisCacheUtil redisCacheUtil;
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    //100W容量,容错0.0003
    private static BloomFilter<Integer> bf = BloomFilter.create(Funnels.integerFunnel(), 1000000, 0.0003);

    @RequestMapping("getCache")
    public void getCache() {
        //通过supplier获取data
        String resSu = redisCacheUtil.getCacheData("key1", ()->getData("muyi11"));
        log.info("su:"+resSu);

        String resRef = redisCacheUtil.getCacheData("key2", this.getClass(), "getData");
        log.info("ref:"+resRef);
    }

    public String getData(String str) {
        log.info("===get data===");

        return "hello "+str;
    }

    @RequestMapping(value = "/lock", method = RequestMethod.GET)
    public void lock() {
        String lockKey = "study:muyi_lock";
        String uniqueId = "1";    //唯一值，防止解锁混乱
        try {
            //获取锁，失败重试
            while (!redisCacheUtil.tryLock(lockKey, uniqueId, 5000)) {
                log.info("get lock fail");
                Thread.sleep(1000);
            }

            log.info("get lock");
            //sleep 2S，当做业务耗时
            Thread.sleep(2000);

        }catch (Exception e) {
            log.info("errors:"+e.getMessage());
        }finally {
            redisCacheUtil.releaseLock(lockKey, uniqueId);
            log.info("release lock");
        }
    }

    @RequestMapping(value = "/bloomfilter", method = RequestMethod.GET)
    public void BloomFilter() {
        int total = 1000000;
        // 初始化1000000条数据到过滤器中
        for (int i = 0; i < total; i++) {
            bf.put(i);
        }

        // 匹配已在过滤器中的值，是否有匹配不上的
        for (int i = 0; i < total; i++) {
            if (!bf.mightContain(i)) {
                log.info("有坏人逃脱了~~~");
            }
        }

        // 匹配不在过滤器中的10000个值，有多少匹配出来
        int count = 0;
        for (int i = total; i < total + 10000; i++) {
            if (bf.mightContain(i)) {
                count++;
            }
        }

        log.info("误伤的数量：" + count);
    }


    /**
     * 使用分布式锁排队获取缓存-防止缓存击穿
     * @param key
     * @return
     */
    @RequestMapping(value = "/get-cache", method = RequestMethod.GET)
    public String getWithLock(String key) {
        String val = redisTemplate.opsForValue().get(key);
        String lockKey = "study:lock_"+key;
        String lockVal = "random_string";//随机字符串

        if (StringUtils.isEmpty(val)) {
            try {
                Boolean getLock = redisCacheUtil.tryLock(lockKey, lockVal, 30000);
                //未获得锁-1S后重新获取
                if (!getLock) {
                    Thread.sleep(1000);

                    //递归有风险，调用需谨慎
                    val = getWithLock(key);
                    //直接返回，防止递归中执行业务逻辑
                    return val;
                }

                //业务逻辑...
//                Thread.sleep(3000);
                val = "val";

                //设置redis
                redisTemplate.opsForValue().set(key, val, 30, TimeUnit.SECONDS);
                redisCacheUtil.releaseLock(lockKey, lockVal);
                return val;
            } catch (Exception e) {
                log.error("getWithLock error:"+e);
                redisCacheUtil.releaseLock(lockKey, lockVal);
                return val;
            }
        }

        return val;
    }

    /**
     * 有序集合做排行榜
     */
    @RequestMapping(value = "/rank", method = RequestMethod.GET)
    public void rank() {
        try {
            String key = "study:rank-list";
            int totalSize = 10000;
            //清除可能的已有数据
            redisTemplate.delete(key);
            //模拟生成若干个游戏选手
            List<String> playerList = new ArrayList<String>();
            for (int i = 0; i < totalSize; ++i)
            {
                //随机生成每个选手的名称
                playerList.add("muyi_"+i);
            }
            log.info("输入全部" + totalSize +" 选手 ");
            //记录每个选手的得分
            for (int i = 0; i < playerList.size(); i++)
            {
                //随机生成数字，模拟选手的游戏得分
                int score = (int)(Math.random()*5000);
                String member = playerList.get(i);
                log.info("选手名称：" + member + "， 选手得分: " + score);

                //将选手的名称和得分，都加到对应key的SortedSet中去
                redisTemplate.opsForZSet().add(key, member, score);
            }
            //从对应key的SortedSet中获取所有已经排序的选手列表
//            Set<ZSetOperations.TypedTuple<String>> scoreList = redisTemplate.opsForZSet().reverseRangeWithScores(key, 0L, -1L);

            //输出打印Top100选手排⾏行榜
            log.info("Top 100 选手");
            Set<ZSetOperations.TypedTuple<String>> scoreList = redisTemplate.opsForZSet().reverseRangeWithScores(key, 0, 99);
            for (ZSetOperations.TypedTuple<String> item : scoreList) {
                log.info("选手名称："+item.getValue()+"， 选手得分:"+Double.valueOf(item.getScore()).intValue());
            }
            //输出某个选手的排名
            String player = "muyi_7381";
            log.info("选手"+player+"的排名: "+ redisTemplate.opsForZSet().reverseRank(key,player));
        } catch (Exception e) {
            log.info("error:"+e.getMessage());
        }
    }

    /**
     * HyperLogLog不精确去重统计
     */
    @RequestMapping(value = "count", method = RequestMethod.GET)
    public void count() {
        String key = "study:unique_ip";

        for (int i = 0; i < 100; i++) {
            int section1 = new Random().nextInt(255);
            int section2 = new Random().nextInt(253);
            String ip = "10.10." + section1 + "." + section2;

            redisTemplate.opsForHyperLogLog().add(key, ip);
        }
        System.out.println("今天访问的独立ip数为:"+redisTemplate.opsForHyperLogLog().size(key));
    }

}
