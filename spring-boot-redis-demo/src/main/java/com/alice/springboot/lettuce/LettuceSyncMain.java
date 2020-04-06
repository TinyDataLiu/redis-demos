package com.alice.springboot.lettuce;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.extern.slf4j.Slf4j;

/**
 * 同步调用
 */
@Slf4j
public class LettuceSyncMain {
    public static void main(String[] args) {
        RedisClient client = RedisClient.create("redis://192.168.1.153:6379");
//      线程安全的长连接，连接丢失时会自动重连
        StatefulRedisConnection<String, String> connect = client.connect();
//      获取同步执行命令，默认超时时间为 60s
        RedisCommands<String, String> sync = connect.sync();
        sync.set("lettuce:sync", "lettuce:sync:666");
        log.info("lettuce:sync={}", sync.get("lettuce:sync"));
//    20:27:16.622 [main] INFO com.alice.springboot.lettuce.LettuceSyncMain - lettuce:sync=lettuce:sync:666
    }
}
