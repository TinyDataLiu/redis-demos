package com.alice.springboot.jedis.lua;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.UUID;

@Slf4j
public class LuaTest {

    public static void main(String[] args) {
        final String KEY = "TEST:LUA:KEY";
        Jedis jedis = getJedis();
        jedis.eval("return redis.call('set',KEYS[1],ARGV[1])", 1, KEY, UUID.randomUUID().toString());
        log.info("{}", jedis.get(KEY));

    }


    private static Jedis getJedis() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        JedisPool pool = new JedisPool(jedisPoolConfig, "192.168.1.153", 6379, 10000);
        return pool.getResource();
    }
}
