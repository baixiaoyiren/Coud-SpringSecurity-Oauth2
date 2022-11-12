package com.javasm.cloud.controller;

import com.javasm.cloud.entity.Response;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-11-03-17:39
 * Description: 分布式锁的使用场景
 */
@RestController
@RequestMapping("/api/v1/coupon")
@Slf4j
public class CouponController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    @GetMapping("add")
    public Response saveCoupon(@RequestParam("couponId") int couponId) {
        // 加一个uuid唯一标识防止误删
        String uuid = UUID.randomUUID().toString();

        String lockKey = "lock:coupon:" + couponId;
        lock(uuid, lockKey);
        return Response.builder().msg("成功").code(HttpStatus.OK.value()).build();
    }


    /**
     * 使用redission实现分布式锁
     *
     * @param couponId
     * @return
     */
    @GetMapping("addCoupon")
    public Response addCoupon(@RequestParam("couponId") int couponId) throws InterruptedException {

        String lockKey = "lock:coupon:" + couponId;
        // 获取锁对象
        RLock lock = redissonClient.getLock(lockKey);
        // 尝试加锁
        //有看门狗机制
        lock.lock();
        TimeUnit.SECONDS.sleep(1);
        log.error(Thread.currentThread().getName());
        lock.unlock();
        return Response.builder().msg("成功le").code(HttpStatus.OK.value()).build();

    }

    private void lock(String uuid, String lockKey) {

        // lua脚本
        String script = "if redis.call('get',KEYS[1])  ==ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";
        Boolean flag = redisTemplate.opsForValue().setIfAbsent(lockKey, uuid, 10, TimeUnit.SECONDS);
        log.info("{}加锁状态:{}", uuid, flag);
        if (Boolean.TRUE.equals(flag)) {
            //加锁成功
            try {
                // todo 业务逻辑
                TimeUnit.SECONDS.sleep(30);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // 固定写法  script 是固定脚本  Integer.class是响应结果类型
                Long result = redisTemplate.execute(new DefaultRedisScript<>(script, Long.class), Arrays.asList(lockKey), uuid);
                log.info("解锁状态：" + result);
            }
        } else {
            try {
                TimeUnit.SECONDS.sleep(5);
                log.info("加锁失败，进入自旋，睡眠5秒");
            } catch (InterruptedException e) {
                throw new RuntimeException("线程被中断");
            }
            // 睡眠一会儿再尝试获取锁
            lock(uuid, lockKey);
        }
    }
}
