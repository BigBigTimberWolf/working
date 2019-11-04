package com.glitter.working.module.spring.security.config.adapter.provider;

import com.glitter.working.properties.spring.security.WorkingSecurityProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;

/**
 * @program:
 * @author: Player
 * @create: 2019-09-02
 **/

public class MvcAuthorizeConfigProvider implements AuthorizeConfigProvider{
    private SessionRegistry sessionRegistry;

    public MvcAuthorizeConfigProvider(SessionRegistry sessionRegistry) {
        this.sessionRegistry = sessionRegistry;
    }

    @Autowired
    private WorkingSecurityProperty workingSecurityProperty;
    @Override
    public void config(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.sessionManagement()
                //session无效时的跳转
                .invalidSessionUrl(workingSecurityProperty.getLogin().getLoginPage())
                .maximumSessions(1)
                // session过期跳转
                .expiredUrl(workingSecurityProperty.getLogin().getLoginPage())
                .sessionRegistry(sessionRegistry);
    }
}
