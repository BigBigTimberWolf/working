package com.glitter.working.module.spring.security.config.adapter.provider;

import com.glitter.working.module.spring.security.handle.afterLogin.SecurityAuthenticationFailureHandler;
import com.glitter.working.module.spring.security.handle.afterLogin.SecurityAuthenticationSuccessHandler;
import com.glitter.working.properties.spring.security.WorkingSecurityProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

/**
 * 登录注销配置管理
 * @program:
 * @author: Player
 * @create: 2019-09-02
 **/
@Slf4j
public class EnableLoginAuthorizeConfigProvider implements AuthorizeConfigProvider {

    @Autowired
    private WorkingSecurityProperty workingSecurityProperty;

    @Autowired
    private SecurityAuthenticationFailureHandler securityAuthenticationFailureHandler;

    @Autowired
    private SecurityAuthenticationSuccessHandler securityAuthenticationSuccessHandler;

    @Override
    public void config(HttpSecurity httpSecurity) throws Exception {

        if(workingSecurityProperty.getLogin().isEnable()){
            log.info("Enable custom login setting...");
            log.info(workingSecurityProperty.getLogin().toString());
        }

        httpSecurity.authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginProcessingUrl(workingSecurityProperty.getLogin().getLoginUrl()).permitAll()
                .loginPage(workingSecurityProperty.getLogin().getLoginPage()).permitAll()
                .successForwardUrl(workingSecurityProperty.getLogin().getSuccessForwardUrl())
                .successHandler(securityAuthenticationSuccessHandler)
                .failureHandler(securityAuthenticationFailureHandler)
                .and()
                .logout()
                .logoutUrl(workingSecurityProperty.getLogin().getLogoutUrl())
                .logoutSuccessUrl(workingSecurityProperty.getLogin().getLogoutSuccess()).permitAll();
    }
}
