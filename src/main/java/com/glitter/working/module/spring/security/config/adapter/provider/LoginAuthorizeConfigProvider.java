package com.glitter.working.module.spring.security.config.adapter.provider;

import com.glitter.working.module.spring.security.handle.afterLogin.SecurityAuthenticationFailureHandler;
import com.glitter.working.module.spring.security.handle.afterLogin.SecurityAuthenticationSuccessHandler;
import com.glitter.working.properties.spring.security.WorkingSecurityProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

/**
 * @Description:
 * @ClassName:LoginAuthorizeConfigProvider
 * @Author:Player
 * @Date:2019-09-18 21:12
 **/
@Slf4j
public class LoginAuthorizeConfigProvider implements AuthorizeConfigProvider {
    @Autowired
    private SecurityAuthenticationFailureHandler securityAuthenticationFailureHandler;

    @Autowired
    private SecurityAuthenticationSuccessHandler securityAuthenticationSuccessHandler;
    @Override
    public void config(HttpSecurity httpSecurity) throws Exception {
        log.info("No enable custom login setting...");
        httpSecurity.formLogin()
                .successHandler(securityAuthenticationSuccessHandler)
                .failureHandler(securityAuthenticationFailureHandler)
                .and().logout();
    }
}
