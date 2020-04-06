package com.alice.springboot.jedis.lua;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Transaction;

import java.util.Arrays;
import java.util.UUID;

@Slf4j
public class LuaTest {

    public static void main(String[] args) {
        for (int i = 0; i < 12; i++) {
            limitIp();
        }
    }


    public static void simple() {
        final String KEY = "TEST:LUA:KEY";
        Jedis jedis = getJedis();
        jedis.eval("return redis.call('set',KEYS[1],ARGV[1])", 1, KEY, UUID.randomUUID().toString());
        log.info("{}", jedis.get(KEY));
        Transaction multi = jedis.multi();
        multi.exec();
    }

    public static void limitIp() {
        Jedis jedis = getJedis();
        // 只在第一次对key设置过期时间
        String lua = "local num = redis.call('incr', KEYS[1])\n" +
                "if tonumber(num) == 1 then\n" +
                "\tredis.call('expire', KEYS[1], ARGV[1])\n" +
                "\treturn 1\n" +
                "elseif tonumber(num) > tonumber(ARGV[2]) then\n" +
                "\treturn 0\n" +
                "else \n" +
                "\treturn 1\n" +
                "end\n";
        Object result = jedis.evalsha(jedis.scriptLoad(lua), Arrays.asList("localhost"), Arrays.asList("10", "10"));
        log.info("result={}", result);
    }


    private static Jedis getJedis() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        JedisPool pool = new JedisPool(jedisPoolConfig, "192.168.1.153", 6379, 10000);
        return pool.getResource();
    }
}
