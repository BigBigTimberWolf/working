package com.glitter.working.module.spring.security.config.adapter;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

/**
 * @program:
 * @author: Player
 * @create: 2019-09-02
 **/
public interface AuthorizeConfigManager {
    void config(HttpSecurity httpSecurity) throws Exception;
}
