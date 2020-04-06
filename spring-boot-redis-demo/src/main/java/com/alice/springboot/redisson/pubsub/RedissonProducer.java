package com.alice.springboot.redisson.pubsub;

import com.alice.springboot.redisson.RedissonClientUtils;
import org.redisson.api.RedissonClient;

public class RedissonProducer {
    public static void main(String[] args) {
        RedissonClient client = RedissonClientUtils.getClient();
         
    }
}
