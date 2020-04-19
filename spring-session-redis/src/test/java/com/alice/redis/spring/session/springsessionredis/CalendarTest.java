package com.alice.redis.spring.session.springsessionredis;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
public class CalendarTest {

    @Test
    void test() {
        Calendar instance = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        log.info("now={}", DateFormatUtils.format(instance.getTime(), "yyyy-MM-dd HH:mm:ss"));
        int DAY_OF_YEAR = instance.get(Calendar.DAY_OF_YEAR);
        int DAY_OF_MONTH = instance.get(Calendar.DAY_OF_MONTH);
        int WEEK_OF_YEAR = instance.get(Calendar.WEEK_OF_YEAR);
        int WEEK_OF_MONTH = instance.get(Calendar.WEEK_OF_MONTH);
        log.info("DAY_OF_YEAR={},DAY_OF_MONTH={},WEEK_OF_YEAR={},WEEK_OF_MONTH={}", DAY_OF_YEAR, DAY_OF_MONTH, WEEK_OF_YEAR, WEEK_OF_MONTH);
    }

    @Test
    void random() {
//
//        Set<Integer> set = new HashSet<>();
//        for (int i = 0; i < 20_000; i++) {
//            set.add(ThreadLocalRandom.current().nextInt(8));
////            log.info("i={}", ThreadLocalRandom.current().nextInt(8));
//        }
//
//        Integer integer = set.stream().max((o1, o2) -> o1 - o2).get();
//        Optional<Integer> min = set.stream().min(new Comparator<Integer>() {
//            @Override
//            public int compare(Integer o1, Integer o2) {
//                return o1 - o2;
//            }
//        });
//
//        log.info("min={},max={},size={}", min.get(), integer, set.size());

        for (int i = 0; i < 200; i++) {
            String phone = randomPhoneNum();
            if (phone.startsWith("158")) {
                log.info(">>>>>>>>>>>>>{}", phone);
            }

            if (phone.startsWith("137")) {
                log.info("{}<<<<<<<<<<<<<", phone);
            }
        }
    }


    private String randomPhoneNum() {
        String[] prefixs = {"137", "138", "139", "150", "151", "136", "158"};
        return prefixs[ThreadLocalRandom.current().nextInt(prefixs.length)] + RandomStringUtils.random(8, false, true);
    }
}
