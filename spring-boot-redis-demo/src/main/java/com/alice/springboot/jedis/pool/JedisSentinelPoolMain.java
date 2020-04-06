package com.alice.springboot.jedis.pool;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.Set;

public class JedisSentinelPoolMain {
    public static void main(String[] args) {
        Set<String> sentinels = new HashSet<>();
        sentinels.add("192.168.1.153:26379");
        sentinels.add("192.168.1.153:26379");
        sentinels.add("192.168.1.153:26379");
        JedisSentinelPool jedisSentinelPool = new JedisSentinelPool("redis-master", sentinels);

        Jedis jedis = jedisSentinelPool.getResource();

    }
}
