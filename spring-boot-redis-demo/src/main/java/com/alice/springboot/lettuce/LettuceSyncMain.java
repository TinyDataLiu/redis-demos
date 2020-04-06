package com.alice.springboot.lettuce;

import io.lettuce.core.RedisClient;
import io.lettuce.core.TransactionResult;
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

        String multi = sync.multi();
//        sync.set("lettuce:sync", "lettuce:sync:666");
        sync.set("tom", "1000");
        sync.set("mic", "1000");
        sync.decrby("tom", 100);
        sync.incrby("mic", 100);
        TransactionResult exec = sync.exec();
//INFO com.alice.springboot.lettuce.LettuceSyncMain - multi=OK,tom=900,mic=1100,exec=DefaultTransactionResult [wasRolledBack=false, responses=4]
        log.info("multi={},tom={},mic={},exec={}", multi, sync.get("tom"), sync.get("mic"), exec);

        log.info("lettuce:sync={}", sync.get("lettuce:sync"));
//    20:27:16.622 [main] INFO com.alice.springboot.lettuce.LettuceSyncMain - lettuce:sync=lettuce:sync:666
    }
}
