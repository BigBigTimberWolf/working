package com.glitter.working.module.spring.security.modal;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @program:
 * @author: Player
 * @create: 2019-11-07
 **/
@Data
public class JsonWebToken {
    private String name;
    private long expirationTime;

    @Override
    public String toString() {
        return "JsonWebToken{" +
                "name='" + name + '\'' +
                ", expirationTime=" + expirationTime +
                '}';
    }
}
