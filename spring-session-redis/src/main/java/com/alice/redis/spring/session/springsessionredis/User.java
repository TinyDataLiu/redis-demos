package com.alice.redis.spring.session.springsessionredis;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class User implements Serializable {
    private String name;
    private Integer age;
}
