package com.alice.springboot.jedis.pubsub;

import redis.clients.jedis.Jedis;

public class ListenTest {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("192.168.1.153", 6379);
        jedis.psubscribe(new ChannelListener(), "redis-publish-*");
    }
}
