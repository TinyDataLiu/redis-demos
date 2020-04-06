package com.alice.springboot.jedis.pipeline;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

@Slf4j
public class PipelineSetMain {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("192.168.1.153", 6379);
        Pipeline pipelined = jedis.pipelined();
        long t1 = System.currentTimeMillis();
        for (int i = 0; i < 10_0000; i++) {
            pipelined.set("batch:" + i, i + "");
        }
        pipelined.syncAndReturnAll();
        log.info("time={}", System.currentTimeMillis() - t1);
    }
}
