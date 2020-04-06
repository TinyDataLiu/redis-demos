package com.alice.springboot.jedis.pipeline;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 19:53:10.755 [pipelined] INFO com.alice.springboot.jedis.pipeline.PipelineGetMain - time=315
 * 19:53:29.383 [simple] INFO com.alice.springboot.jedis.pipeline.PipelineGetMain - time=18946
 */
@Slf4j
public class PipelineGetMain {
    public static void main(String[] args) throws IOException {
        new Thread(() -> {
            Jedis jedis = new Jedis("192.168.1.153", 6379);
            Set<String> keys = jedis.keys("batch*");
            List<String> result = new ArrayList();
            Long t1 = System.currentTimeMillis();
            keys.forEach(key -> result.add(jedis.get(key)));
            log.info("time={}", System.currentTimeMillis() - t1);
        }, "simple").start();

        new Thread(() -> {
            Jedis jedis = new Jedis("192.168.1.153", 6379);
            Set<String> keys = jedis.keys("batch*");
            List<Object> result = new ArrayList();
            Long t1 = System.currentTimeMillis();
            Pipeline pipelined = jedis.pipelined();
            keys.forEach(key -> pipelined.get(key));
            result = pipelined.syncAndReturnAll();
            log.info("time={}", System.currentTimeMillis() - t1);
        }, "pipelined").start();
        System.in.read();
    }
}
