package com.glitter.working.module.spring.security.config.adapter.provider;

import com.glitter.working.module.spring.security.handle.afterLogin.SecurityAuthenticationFailureHandler;
import com.glitter.working.module.spring.security.handle.afterLogin.SecurityAuthenticationSuccessHandler;
import com.glitter.working.properties.spring.security.WorkingSecurityProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

/**
 * 登录注销配置管理
 * @program:
 * @author: Player
 * @create: 2019-09-02
 **/
public class EnableLoginAuthorizeConfigProvider implements AuthorizeConfigProvider {

    @Autowired
    private WorkingSecurityProperty workingSecurityProperty;

    @Override
    public void config(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginProcessingUrl(workingSecurityProperty.getLogin().getLoginUrl()).permitAll()
                .loginPage(workingSecurityProperty.getLogin().getLoginPage()).permitAll()
                .successForwardUrl(workingSecurityProperty.getLogin().getSuccessForwardUrl())
                .successHandler(new SecurityAuthenticationSuccessHandler())
                .failureHandler(new SecurityAuthenticationFailureHandler())
                .and()
                .logout()
                .logoutUrl(workingSecurityProperty.getLogin().getLogoutUrl())
                .logoutSuccessUrl(workingSecurityProperty.getLogin().getLogoutSuccess()).permitAll();
    }
}
