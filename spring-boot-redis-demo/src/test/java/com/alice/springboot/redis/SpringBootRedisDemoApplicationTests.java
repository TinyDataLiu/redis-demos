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
import java.util.function.Consumer;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootRedisDemoApplicationTests {


    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    @Test
    public void contextLoads() {
        Set<String> keys = redisTemplate.keys("*");
        keys.forEach(key -> {
            System.out.println(key + " ----->" + redisTemplate.boundValueOps(key).get());
        });
    }
}
