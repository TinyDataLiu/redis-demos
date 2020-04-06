package com.alice.springboot.lettuce;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Slf4j
public class LettuceASyncMain {
    public static void main(String[] args) {
        RedisClient client = RedisClient.create("redis://192.168.1.153:6379");
        StatefulRedisConnection<String, String> connect = client.connect();
        RedisAsyncCommands<String, String> async = connect.async();
        async.set("lettuce:async", "lettuce:async:6666");
//      异步的结果使用 RedisFuture 包装，提供了大量回调的方法。
        RedisFuture<String> future = async.get("lettuce:async");

        try {
            String result = future.get(60L, TimeUnit.SECONDS);
            log.info("result={}", result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } finally {
            connect.close();
        }

    }
}
