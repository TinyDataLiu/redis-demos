package com.alice.springboot.jedis;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.util.Hashing;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
public class ShardingTest {
    public static void main(String[] args) {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        List<JedisShardInfo> shardInfos = new ArrayList<>();
        shardInfos.add(new JedisShardInfo("192.168.1.153", 6379));
        shardInfos.add(new JedisShardInfo("192.168.1.153", 7379));

        ShardedJedisPool pool = new ShardedJedisPool(poolConfig, shardInfos, Hashing.MURMUR_HASH);

        ShardedJedis jedis = pool.getResource();
        for (int i = 0; i < 100; i++) {

//            jedis.set("ShardedJedisPool:" + i, UUID.randomUUID().toString());

            jedis.set("jedis:" + i, UUID.randomUUID().toString());
        }
        String s = jedis.get("ShardedJedisPool:1");
        System.out.println(s);
    }
}
