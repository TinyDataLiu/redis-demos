package com.alice.springboot.jedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.Set;

public class JedisPollTest {


    public static void main(String[] args) {
        Set<String> set = new HashSet<>();
        set.add("192.168.1.153:26379");
        set.add("192.168.1.154:26379");
        set.add("192.168.1.155:26379");
        JedisSentinelPool jedisSentinelPool = new JedisSentinelPool("redis-master", set);

        Jedis resource = jedisSentinelPool.getResource();
        Set<String> keys = resource.keys("*");
//        for (int i = 0; i < 100; i++) {
//            resource.set("jedisSentinelPool:" + i, UUID.randomUUID().toString());
//        }
        keys.forEach(key -> System.out.println(key));


    }

}
