package com.alice.redis.spring.session.springsessionredis;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Slf4j
@SpringBootTest
class SpringSessionRedisApplicationTests {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Test
    void contextLoads() {
        redisTemplate.executePipelined((RedisCallback<Object>) connection -> {
            connection.openPipeline();
            for (int i = 0; i < 100; i++) {
                try {
                    connection.set(("pipeline:" + i).getBytes("UTF-8"), UUID.randomUUID().toString().getBytes("Utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            connection.close();
            return null;
        });
        Set<Object> keys = redisTemplate.keys("pipeline:*");
        keys.forEach(key -> log.info("key={},value={}", key, redisTemplate.opsForValue().get(key)));
    }


    @Test
    void addUser() {
        for (int i = 0; i < 20; i++) {
            redisTemplate.opsForValue().set("user:" + i, User.builder().age(i).name(UUID.randomUUID().toString()).build());
        }

        redisTemplate.keys("user*").forEach(key -> log.info("key = {} , value = {}", key, redisTemplate.opsForValue().get(key)));
    }


    @Test
    void set() {
        List<Integer> integers = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            integers.add(i);
        }
        redisTemplate.opsForSet().add("set", integers.toArray());
        Object set = redisTemplate.opsForSet().pop("set");
        Object randomMember = redisTemplate.opsForSet().randomMember("set");
        log.info("set={},randomMember={},size={}", set, randomMember, redisTemplate.opsForSet().members("set"));
    }


}
