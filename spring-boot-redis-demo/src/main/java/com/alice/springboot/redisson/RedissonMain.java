package com.alice.springboot.redisson;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.concurrent.TimeUnit;

@Slf4j
public class RedissonMain {
    public static void main(String[] args) {
        Config config = new Config();
        config.useSingleServer().setConnectTimeout(5000).setDatabase(0).setAddress("redis://192.168.1.153:6379");
        RedissonClient client = Redisson.create(config);
//      分布式锁服务
        RLock lock = client.getLock("lock:by-alice");
//        最多等待 100 秒、上锁 10s 以后自动解锁
        try {
            if (lock.tryLock(100, Integer.MAX_VALUE, TimeUnit.SECONDS)) {
                log.info("Get lock success");
                TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        client.shutdown();
    }
}
