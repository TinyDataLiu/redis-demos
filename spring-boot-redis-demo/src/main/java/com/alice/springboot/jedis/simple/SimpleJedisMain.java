package com.alice.springboot.jedis.simple;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

import java.util.Set;
import java.util.UUID;

@Slf4j
public class SimpleJedisMain {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("192.168.1.153", 6379);
        Set<String> keys = jedis.keys("*");
//        keys.forEach(key -> log.info("key={} ", key));
//        jedis.hset("user:1", "name", "alice");
//        jedis.hset("user:1", "age", "1");
//
//        String name = jedis.hget("user:1", "name");
//        log.info("name={}", name);
//        Map<String, String> map = jedis.hgetAll("user:1");
//        log.info("user={}", map);

        Long s = System.currentTimeMillis();
        for (int i = 0; i < 20_000; i++) {
            jedis.set("test:key:" + i, UUID.randomUUID().toString());
        }

        log.info("time={}", System.currentTimeMillis() - s);
    }
}
