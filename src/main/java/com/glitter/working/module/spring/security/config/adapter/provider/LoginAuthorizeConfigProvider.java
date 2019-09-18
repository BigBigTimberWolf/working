package com.glitter.working.module.spring.security.config.adapter.provider;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

/**
 * @Description:
 * @ClassName:LoginAuthorizeConfigProvider
 * @Author:Player
 * @Date:2019-09-18 21:12
 **/
public class LoginAuthorizeConfigProvider implements AuthorizeConfigProvider {
    @Override
    public void config(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.formLogin().and().logout();
    }
}
