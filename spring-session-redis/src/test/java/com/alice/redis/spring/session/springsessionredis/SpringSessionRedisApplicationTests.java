package com.alice.redis.spring.session.springsessionredis;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Slf4j
@SpringBootTest
class SpringSessionRedisApplicationTests {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Test
    void contextLoads() {
        redisTemplate.executePipelined((RedisCallback<Object>) connection -> {
            connection.openPipeline();
            for (int i = 0; i < 100; i++) {
                try {
                    connection.set(("pipeline:" + i).getBytes("UTF-8"), UUID.randomUUID().toString().getBytes("Utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            connection.close();
            return null;
        });
        Set<Object> keys = redisTemplate.keys("pipeline:*");
        keys.forEach(key -> log.info("key={},value={}", key, redisTemplate.opsForValue().get(key)));
    }


    @Test
    void addUser() {
        for (int i = 0; i < 20; i++) {
            redisTemplate.opsForValue().set("user:" + i, User.builder().age(i).name(UUID.randomUUID().toString()).build());
        }

        redisTemplate.keys("user*").forEach(key -> log.info("key = {} , value = {}", key, redisTemplate.opsForValue().get(key)));
    }


    @Test
    void set() {
        List<Integer> integers = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            integers.add(i);
        }
//        redisTemplate.opsForSet().add("set", integers.toArray());
        for (int i = 0; i < 15; i++) {
            Object set = redisTemplate.opsForSet().pop("set");
            Object randomMember = redisTemplate.opsForSet().randomMember("set");
            log.info("set={},randomMember={},size={}", set, randomMember, redisTemplate.opsForSet().members("set").size());
        }
    }

    @Test
    void zset() {
//        for (int i = 0; i < 100; i++) {
//            redisTemplate.opsForZSet().add("zset", "alice:" + i, i);
//        }
        ZSetOperations<Object, Object> zSet = redisTemplate.opsForZSet();
        Set<Object> objects = zSet.reverseRange("zset", 0, 10);
//        objects.forEach(o -> {
//            Double score = zSet.incrementScore("zset", o, ThreadLocalRandom.current().nextDouble(200));
//            log.info("o={},score={}", o, score);
//        });

//
//        for (int i = 0; i < 1000; i++) {
//            zSet.add("invest:0", "150" + RandomStringUtils.random(8, false, true), ThreadLocalRandom.current().nextInt(50000));
//            zSet.add("invest:1", "158" + RandomStringUtils.random(8, false, true), ThreadLocalRandom.current().nextInt(50000));
//        }

//        Set<Object> invest = zSet.range("invest", 0, 5);
//        invest.forEach(member -> log.info("member={}", member));
//
//
//        Set<Object> byScore = zSet.reverseRangeByScore("invest", 2000L, 5000L);
//        byScore.forEach(member -> log.info("member={}", member));
//
//        Set<ZSetOperations.TypedTuple<Object>> typedTuples = zSet.reverseRangeByScoreWithScores("invest", 0L, 168);
//        log.info("typedTuples.size={}", typedTuples.size());
//        typedTuples.forEach(typedTuple -> log.info("score={},value={}", typedTuple.getScore(), typedTuple.getValue()));


//        Set<ZSetOperations.TypedTuple<Object>> invest = zSet.reverseRangeWithScores("invest", 0, 5);
//        invest.forEach(member -> {
//            log.info("phone={},score={}", member.getValue(), member.getScore());
//        });
//          合并并保存
//        Long store = zSet.unionAndStore("invest:0", "invest:1", "invest:all");

//        log.info("store={}", store);


        Double score = zSet.score("invest:all", "15824587384");
//        log.info("score={}", score);


//        Long range = zSet.removeRange("invest:all", 1, 20);
//        log.info("range={}", range);


        String invAll = "invest:all";

        Long range = zSet.removeRangeByScore("invest:all", 0, 5000);
        log.info("range={}", range);


        zSet.reverseRangeWithScores(invAll, 0, 20).forEach(member -> {
            log.info("{}-----{}", member.getValue(), member.getScore());
        });
    }

}
