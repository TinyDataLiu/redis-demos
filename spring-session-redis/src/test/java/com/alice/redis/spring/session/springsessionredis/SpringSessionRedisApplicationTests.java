package com.alice.redis.spring.session.springsessionredis;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@SpringBootTest
class SpringSessionRedisApplicationTests {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;
    @Autowired
    private RedisTemplate<String, User> userRedisTemplate;

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


//    @Test
//    void addUser() {
//        for (int i = 0; i < 20; i++) {
//            redisTemplate.opsForValue().set("user:" + i, User.builder().age(i).name(UUID.randomUUID().toString()).build());
//        }
//
//        redisTemplate.keys("user*").forEach(key -> log.info("key = {} , value = {}", key, redisTemplate.opsForValue().get(key)));
//    }


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


//        Double score = zSet.score("invest:all", "15824587384");
//        log.info("score={}", score);


//        Long range = zSet.removeRange("invest:all", 1, 20);
//        log.info("range={}", range);


        String invAll = "invest:all";

        Long range = zSet.removeRangeByScore("invest:all", 0, 5000);
        log.info("range={}", range);
        Calendar instance = Calendar.getInstance(TimeZone.getTimeZone("GTM+8"));
//        for (int i = 0; i < 100; i++) {
//            zSet.add("invest:" + instance.get(Calendar.YEAR) + ":day:" + instance.get(Calendar.DAY_OF_YEAR), randomPhoneNum(), ThreadLocalRandom.current().nextInt(50000));
//            zSet.add("invest:" + instance.get(Calendar.YEAR) + ":month:" + (instance.get(Calendar.MONTH) + 1), randomPhoneNum(), ThreadLocalRandom.current().nextInt(50000));
//        }


        String personKey = "person:" + instance.get(Calendar.YEAR) + ":day:" + instance.get(Calendar.DAY_OF_YEAR);
//
//        for (int i = 0; i < 200; i++) {
//
//            User user = User.builder()
//                    .name(RandomStringUtils.random(5, true, false))
//                    .age(ThreadLocalRandom.current().nextInt(18, 55))
//                    .phone(randomPhoneNum())
//                    .build();
//
//            zSet.add(personKey, user, ThreadLocalRandom.current().nextInt(50000));
//        }


        zSet.reverseRangeWithScores(personKey, 0, 10).forEach(member -> {
            User user = JSON.parseObject(member.toString(), User.class);
            log.info("user={},score={}", user, member.getScore());
        });

//        zSet.reverseRangeWithScores(invAll, 0, 20).forEach(member -> {
//            instance.set(2018, 2, 28);
//            int yearDay = instance.get(Calendar.DAY_OF_YEAR);
//            int i = instance.get(Calendar.YEAR);
//            int month = instance.get(Calendar.MONTH);
//            String format = DateFormatUtils.format(instance.getTime(), "yyyy-MM-dd");
//            log.info("i={},yearDay={},month={},format={}", i, yearDay, month, format);
//            log.info("{}-----{}", member.getValue(), member.getScore());
//        });
    }


    private String randomPhoneNum() {
        String[] prefixs = {"137", "138", "139", "150", "151", "136", "158"};
        return prefixs[ThreadLocalRandom.current().nextInt(prefixs.length)] + RandomStringUtils.random(8, false, true);
    }


    @Test
    void getPhone() {
        for (int i = 0; i < 100; i++) {
            log.info("phone={}", randomPhoneNum());
        }
    }


    @Test
    void addUserTo() {
        ValueOperations<String, User> value = userRedisTemplate.opsForValue();
        String key = "user-test-01";
        value.set(key, new User().setAge(18).setName("alice").setPhone(randomPhoneNum()).setDate(new Date()));
        User user = value.get(key);
        log.info("user={}", user);
    }

    @Test
    void userZSet() {
        String key = "user";
        ZSetOperations<String, User> set = userRedisTemplate.opsForZSet();
        User user = new User();
        user.setDate(new Date());
        for (int i = 0; i < 10_000; i++) {
            user.setName(RandomStringUtils.random(5, true, false).toLowerCase())
                    .setAge(ThreadLocalRandom.current().nextInt(20, 55))
                    .setPhone(randomPhoneNum())
            ;
            set.add(key, user, ThreadLocalRandom.current().nextInt(50001));
        }
        Set<ZSetOperations.TypedTuple<User>> tuples = set.reverseRangeWithScores(key, 0, 10);
        tuples.forEach(member -> {
            log.info("user={},score={}", member.getValue(), member.getScore());
        });


//        Boolean expire = userRedisTemplate.expire(key, 30, TimeUnit.SECONDS);
//        log.info("expire={}", expire);
    }

}
