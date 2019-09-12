package com.glitter.working.module.spring.security.config.adapter.provider;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * @program:
 * @author: Player
 * @create: 2019-09-02
 **/

public class RestAuthorizeConfigProvider implements AuthorizeConfigProvider{
    @Override
    public void config(HttpSecurity httpSecurity) throws Exception {
        /*不需要session*/
        httpSecurity
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
