package com.glitter.working.module.spring.security.config.adapter.provider;

import com.glitter.working.properties.spring.security.WorkingSecurityProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.annotation.Order;
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
