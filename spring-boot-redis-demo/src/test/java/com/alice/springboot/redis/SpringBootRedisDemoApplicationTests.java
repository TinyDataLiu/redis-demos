package com.alice.springboot.redis;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.DateUtil;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import static javafx.scene.input.KeyCode.L;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootRedisDemoApplicationTests {


    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    @Test
    @Ignore
    public void strings() {
        redisTemplate.boundValueOps("KEY_3").set("1");
//        不带参数，默认自增1 且只有整数类型可以操作
        Long increment_0 = redisTemplate.boundValueOps("KEY_3").increment();
        out("INCREMENT_0", increment_0.toString());
        out("KEY_3", redisTemplate.boundValueOps("KEY_3").getType().name());
        redisTemplate.boundValueOps("KEY_4").set("1.25");
        Double increment1 = redisTemplate.boundValueOps("KEY_4").increment(1.20);
        out("INCREMENT_1", increment1.toString());
        out("KEY_4", redisTemplate.boundValueOps("KEY_4").getType().name());
        Long key_3 = redisTemplate.boundValueOps("KEY_3").increment(5);
        out("KEY_3", key_3.toString());
        redisTemplate.boundValueOps("KEY_5").set("KEY_5", 10);
        String KEY_5 = redisTemplate.boundValueOps("KEY_5").get();
        out("KEY_5", KEY_5);
        Calendar instance = Calendar.getInstance();
        instance.set(Calendar.MINUTE, instance.get(Calendar.MINUTE) + 10);
        redisTemplate.boundValueOps("KEY_6").set("KEY_6");
        redisTemplate.boundValueOps("KEY_6").expireAt(instance.getTime());
        Long expire = redisTemplate.boundValueOps("KEY_6").getExpire();
        out("EXPIRE:", expire.toString());
        redisTemplate.boundValueOps("KEY_7").set("KEY_7", 20, TimeUnit.SECONDS);
        Long KEY_7 = redisTemplate.boundValueOps("KEY_7").getExpire();
        out("EXPIRE_KEY_7:", expire.toString());
        BitFieldSubCommands commands = BitFieldSubCommands.create();
        redisTemplate.opsForValue().setBit("onlineUser", 1, true);
    }

    @Test
    @Ignore
    public void bit() {

        redisTemplate.opsForValue().setBit("onlineUser", 1, true);
        Boolean onlineUser = redisTemplate.opsForValue().getBit("onlineUser", 1);
        Boolean onlineUser2 = redisTemplate.opsForValue().getBit("onlineUser", 2);
        Boolean onlineUser3 = redisTemplate.opsForValue().getBit("onlineUser", 0);
        out("onlineUser", onlineUser.toString(), onlineUser2.toString(), onlineUser3.toString());

    }


    private void stringsSetGet() {
        //        设置值
        redisTemplate.boundValueOps("KEY_1").set(randomVal());
//        取值
        String key_1 = redisTemplate.boundValueOps("KEY_1").get();
        out("set :", key_1);
    }

    private void stringsExpire() {

//
        redisTemplate.opsForValue().set("KEY_2", randomVal());
        out("get:", redisTemplate.opsForValue().get("KEY_2"));
        out("Expire:", redisTemplate.boundValueOps("KEY_2").getExpire().toString());
//       设置超时时间
        redisTemplate.boundValueOps("KEY_2").expire(10, TimeUnit.SECONDS);
        out("ExpireB:", redisTemplate.boundValueOps("KEY_2").getExpire().toString());
    }

    @Test
    @Ignore
    public void test() {
        out("alice", "cccc");
    }

    private void out(String... message) {
        String str = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()) + "  ";
        for (String st : message) {
            str += "[" + st + "]";
        }
        System.err.println(str);
    }


    private String randomVal() {
        return UUID.randomUUID().toString();
    }


    @Test
    public void add() {
//        for (int i = 0; i < 100; i++) {
//            redisTemplate.opsForValue().set("alice-" + i, UUID.randomUUID().toString());
//        }

        Set<String> keys = redisTemplate.keys("*");
        keys.forEach(key -> {
            log.info(key);
        });
    }

}
