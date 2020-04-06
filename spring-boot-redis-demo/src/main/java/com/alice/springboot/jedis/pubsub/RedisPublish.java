package com.alice.springboot.jedis.pubsub;

import redis.clients.jedis.Jedis;

import java.util.UUID;

public class RedisPublish {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("192.168.1.153", 6379);
        jedis.publish("redis-publish-1", UUID.randomUUID().toString());
        jedis.publish("redis-publish-2", UUID.randomUUID().toString());
        jedis.publish("redis-publish-3", UUID.randomUUID().toString());
    }
}
