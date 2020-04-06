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
////        不空过滤器，可以用于缓存穿透，做前置判断
//        RBloomFilter<String> filter = client.getBloomFilter("bloom-filter-redisson");
//        filter.tryInit(100_000, 0.01);
////      计数器，或者全局自增ID等
//        RAtomicLong atomicLong = client.getAtomicLong("order-id:order-id");
//        long l = atomicLong.incrementAndGet();
//        log.info("order-id:{}", l);
////      分布式锁服务
//        RLock lock = client.getLock("lock");
//        try {
//            lock.tryLock(10, TimeUnit.SECONDS);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } finally {
//            lock.unlock();
//        }
////      公平锁
//        RLock fairLock = client.getFairLock("fair-lock");
////      读写锁
//        RReadWriteLock readWriteLock = client.getReadWriteLock("r-w-lock");


        RAtomicLong user_pk = client.getAtomicLong("pk:tb_user");
        RAtomicLong emp_pk = client.getAtomicLong("pk:tb_emp");

        log.info("user_pk={},emp_pk={}", user_pk.incrementAndGet(), emp_pk.incrementAndGet());
        log.info("user_pk={},emp_pk={}", user_pk.get(), emp_pk.get());

        client.shutdown();
    }
}
