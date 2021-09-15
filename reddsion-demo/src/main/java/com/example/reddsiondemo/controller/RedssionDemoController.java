package com.example.reddsiondemo.controller;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * @author lktbz
 * @version 1.0.0
 * @date 2021/9/15
 * @desc
 */
@RestController
public class RedssionDemoController {

    /**
     *  分布式锁demo
     *  模拟一个抢茅台数量的100瓶茅台
     *  在redis 中存储，使用jemter 模拟1000个线程并发抢购。。
     * @return
     */

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private RedissonClient redissonClient;
    @GetMapping("/add")
    public String addMouTao(){
        redisTemplate.opsForValue().set("moutai","100",10, TimeUnit.MINUTES);
        if(redisTemplate.hasKey("moutai")){
            return  "添加成功";
        }
        return "添加失败";
    }

    /**
     *   简单的一个分布式秒杀就搞定了。细节部分还需要完善很多。
     * @return
     */
    @ResponseBody
    @GetMapping("/test")
    public String createOrderTest()
    {
        RLock lock = redissonClient.getLock("mt");
          try {
               lock.lock();
               if(Integer.parseInt(redisTemplate.opsForValue().get("moutai"))<=0){
                   System.out.println("线程："+Thread.currentThread().getName()+"：不好意思，暂时没有茅台了，请明天再来。。。。");
                   return "不好意思请下次在抢";
               }
               redisTemplate.opsForValue().decrement("moutai");
              System.out.println("线程："+Thread.currentThread().getName()+"：获取到锁：并获取到一瓶茅台");
          }catch (Exception e){}
          finally {
              lock.unlock();
          }

        return "已成功抢到一瓶茅台。。";
    }

}
