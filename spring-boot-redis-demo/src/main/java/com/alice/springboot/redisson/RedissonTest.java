package com.alice.springboot.redisson;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

@Slf4j
public class RedissonTest {
    public static void main(String[] args) {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://192.168.1.153:6379").setDatabase(1).setConnectTimeout(5000);
        RedissonClient client = Redisson.create(config);
//        RBloomFilter<String> filter = client.getBloomFilter("bloom-filter-redisson");
//        System.out.println(filter.isExists());
//        filter.tryInit(100_000, 0.01);
//        System.out.println(filter.contains(UUID.randomUUID().toString()));
        RAtomicLong atomicLong = client.getAtomicLong("atomic-long:order-id");
        long l = atomicLong.incrementAndGet();
        log.info("order-id:{}", l);

        client.shutdown();
    }
}
