package com.supers.basic.api.controller;

import com.supers.common.util.jwt.annotation.Access;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Api(tags="Redis")
@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private Redisson redisson;
    @Autowired
    private RedisTemplate redisTemplate;

    @Access(accessNoToken = true)
    @ApiOperation(value = "Redis分布式锁")
    @GetMapping("/redisson/lock")
    public ResponseEntity redissonLock(@ApiParam(value = "订单ID") @RequestParam(value = "orderId") String orderId) {
        RLock redissonLock  = redisson.getLock(orderId);
        try {
            redissonLock.lock();
         }catch (Exception e){
            e.printStackTrace();
        }finally {
            redissonLock.unlock();
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @Access(accessNoToken = true)
    @ApiOperation(value = "Redis分布式锁（未完全实现并发安全）")
    @GetMapping("/redis-template/lock")
    public ResponseEntity redisTemplateLock(@ApiParam(value = "订单ID") @RequestParam(value = "orderId") String orderId) {
        // clientId 保证谁加锁谁来解锁
        String clientId = UUID.randomUUID().toString();
        try {
            redisTemplate.opsForValue().setIfAbsent(orderId, clientId, 10, TimeUnit.SECONDS);

            /**
             * 处理业务逻辑
             */
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(clientId.equals(redisTemplate.opsForValue().get("orderId"))){
                redisTemplate.delete("orderId");
            }
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}
