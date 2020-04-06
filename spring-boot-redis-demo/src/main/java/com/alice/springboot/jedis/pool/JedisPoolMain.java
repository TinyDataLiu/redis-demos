package com.alice.springboot.jedis.pool;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Set;

@Slf4j
public class JedisPoolMain {
    public static void main(String[] args) {
        JedisPool pool = new JedisPool("192.168.1.153", 6379);
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
//        pool.initPool(config);
        Jedis jedis = pool.getResource();
        Set<String> keys = jedis.keys("*");
        keys.forEach(k -> log.info(k));
    }
}
