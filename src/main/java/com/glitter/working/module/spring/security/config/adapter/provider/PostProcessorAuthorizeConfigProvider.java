package com.glitter.working.module.spring.security.config.adapter.provider;

import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

/**
 * @program:
 * @author: Player
 * @create: 2019-09-02
 **/
@Order(Byte.MIN_VALUE+3)
public class PostProcessorAuthorizeConfigProvider implements AuthorizeConfigProvider {
    @Override
    public void config(HttpSecurity httpSecurity) throws Exception {

    }
}
