package com.alice.redis.spring.session.springsessionredis;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

public class FastJsonRedisSerializer<T> implements RedisSerializer<T> {
    @Override
    public byte[] serialize(T t) throws SerializationException {
        return new byte[0];
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        return null;
    }

    @Override
    public boolean canSerialize(Class<?> type) {
        return false;
    }

    @Override
    public Class<?> getTargetType() {
        return null;
    }
}
