package com.alice.springboot.jedis.pipeline;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

/**
 * 几十倍的差距
 */
@Slf4j
public class PipelineSetMain {
    public static void main(String[] args) {
//        simple();
        pipelined();
    }

    /**
     * 19:55:00.136 [main] INFO com.alice.springboot.jedis.pipeline.PipelineSetMain - time=512
     */
    public static void pipelined() {
        Jedis jedis = new Jedis("192.168.1.153", 6379);
        Pipeline pipelined = jedis.pipelined();
        long t1 = System.currentTimeMillis();
        for (int i = 0; i < 10_0000; i++) {
            pipelined.set("batch:" + i, i + "");
        }
        pipelined.syncAndReturnAll();
        log.info("time={}", System.currentTimeMillis() - t1);
    }

    /**
     * 19:57:25.114 [main] INFO com.alice.springboot.jedis.pipeline.PipelineSetMain - time=19419
     */
    public static void simple() {
        Jedis jedis = new Jedis("192.168.1.153", 6379);
        long t1 = System.currentTimeMillis();
        for (int i = 0; i < 10_0000; i++) {
            jedis.set("batch:" + i, i + "");
        }
        log.info("time={}", System.currentTimeMillis() - t1);
    }
}
