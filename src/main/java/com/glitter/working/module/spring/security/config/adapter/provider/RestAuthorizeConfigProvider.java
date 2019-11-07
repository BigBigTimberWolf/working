package com.glitter.working.module.spring.security.config.adapter.provider;

import com.glitter.working.module.spring.security.filter.JsonWebTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @program:
 * @author: Player
 * @create: 2019-09-02
 **/

public class RestAuthorizeConfigProvider implements AuthorizeConfigProvider{
    private JsonWebTokenFilter jsonWebTokenFilter;

    public RestAuthorizeConfigProvider(JsonWebTokenFilter jsonWebTokenFilter) {
        this.jsonWebTokenFilter = jsonWebTokenFilter;
    }

    @Override
    public void config(HttpSecurity httpSecurity) throws Exception {
        /*不需要session*/
        httpSecurity
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
        .addFilterBefore(jsonWebTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
