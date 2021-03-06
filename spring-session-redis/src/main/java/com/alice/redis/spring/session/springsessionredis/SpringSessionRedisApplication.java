package com.alice.redis.spring.session.springsessionredis;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 3600)
@SpringBootApplication
public class SpringSessionRedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSessionRedisApplication.class, args);
    }


    @Bean
    RedisTemplate<Object, Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());

        CustomFastJsonRedisSerializer serializer = new CustomFastJsonRedisSerializer(Object.class);
        redisTemplate.setValueSerializer(serializer);
        redisTemplate.setHashValueSerializer(serializer);
        return redisTemplate;
    }


    @Bean(name = "userRedisTemplate")
    RedisTemplate<String, User> userRedisTemplate(LettuceConnectionFactory factory) {
        RedisTemplate<String, User> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);

        FastJsonRedisSerializer<User> fastJsonRedisSerializer = new FastJsonRedisSerializer<>(User.class);
        redisTemplate.setValueSerializer(fastJsonRedisSerializer);
        redisTemplate.setHashKeySerializer(fastJsonRedisSerializer);
        return redisTemplate;
    }
}