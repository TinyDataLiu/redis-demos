package com.alice.springboot.redis;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootRedisDemoApplicationTests {


    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    @Test
    public void strings() {
//        设值 set
        redisTemplate.boundValueOps("key").set(UUID.randomUUID().toString());
        System.err.println(redisTemplate.boundValueOps("key").get());
//        设置字符串类型的值，带有超市时间
        redisTemplate.opsForValue().set("TIME_OUT", UUID.randomUUID().toString(), 10L, TimeUnit.SECONDS);
    }
}
