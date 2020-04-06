package com.alice.springboot.redisson;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

public class RedissonClientUtils {
    public static RedissonClient getClient() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://192.168.1.153:6379").setDatabase(1).setConnectTimeout(5000);
        RedissonClient client = Redisson.create(config);
        return client;
    }
}
