package com.alice.redis.spring.session.springsessionredis;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class User implements Serializable {
    private static final long serialVersionUID = 5264795471493271533L;
    private String name;
    private Integer age;
    private String phone;
    private Date date;
}
