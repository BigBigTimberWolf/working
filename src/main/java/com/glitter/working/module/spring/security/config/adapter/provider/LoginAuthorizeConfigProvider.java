package com.glitter.working.module.spring.security.config.adapter.provider;

import com.glitter.working.module.spring.security.handle.afterLoginOrLogout.SecurityAuthenticationFailureHandler;
import com.glitter.working.module.spring.security.handle.afterLoginOrLogout.SecurityAuthenticationSuccessHandler;
import com.glitter.working.module.spring.security.handle.afterLoginOrLogout.SecurityLogoutHandler;
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
    @Autowired
    private SecurityLogoutHandler securityLogoutHandler;
    @Override
    public void config(HttpSecurity httpSecurity) throws Exception {
        log.info("No enable custom login setting...");
        httpSecurity.formLogin()
                .successHandler(securityAuthenticationSuccessHandler)
                .failureHandler(securityAuthenticationFailureHandler)
                .and()
                .logout()
                .logoutSuccessHandler(securityLogoutHandler)
        ;
    }
}
