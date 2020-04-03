package com.alice.springboot.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;

@Configuration
public class RedisConfig {


//    @Bean
//    @Primary
//    public LettuceConnectionFactory factory(RedisStandaloneConfiguration redisConf) {
//        LettuceClientConfiguration clientConfiguration = LettucePoolingClientConfiguration.builder()
//                .poolConfig(config).commandTimeout(Duration.ofMillis(config.getMaxWaitMillis())).build();
//        return new LettuceConnectionFactory(redisConf, clientConfiguration);
//    }

//
//    @Bean
//    RedisTemplate<String, String> redisTemplate154() {
//        RedisTemplate redisTemplate = new RedisTemplate();
//        LettuceConnectionFactory factory = new LettuceConnectionFactory();
//        factory.setDatabase(0);
//        factory.setHostName("192.168.1.154");
//        factory.setPort(6379);
//        redisTemplate.setConnectionFactory(factory);
//        return redisTemplate;
//    }
//
//    @Bean
//    @Primary
//    RedisTemplate<String, String> redisTemplate153() {
//        RedisTemplate redisTemplate = new RedisTemplate();
//        LettuceConnectionFactory factory = new LettuceConnectionFactory();
//        factory.setDatabase(0);
//        factory.setHostName("192.168.1.153");
//        factory.setPort(6379);
//        redisTemplate.setConnectionFactory(factory);
//        return redisTemplate;
//    }
}
