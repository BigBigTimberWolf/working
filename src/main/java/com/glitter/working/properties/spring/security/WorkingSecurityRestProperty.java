package com.glitter.working.properties.spring.security;

import lombok.Data;

/**
 * @program:
 * @author: Player
 * @create: 2019-11-07
 **/
@Data
public class WorkingSecurityRestProperty {
    /*header字段*/
    private String header="authorization";
    /*前缀*/
    private String prefix="player";
    /*有效时间*/
    private long time=60000000;
}
