package com.glitter.working.module.spring.security.config.adapter.provider;

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
@EnableConfigurationProperties({WorkingSecurityProperty.class})
@Order(Byte.MIN_VALUE)
public class LoginAuthorizeConfigProvider implements AuthorizeConfigProvider {

    @Autowired
    private WorkingSecurityProperty workingSecurityProperty;

    //todo 登录成功和注销成功的handler还没有添加
    @Override
    public void config(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginProcessingUrl(workingSecurityProperty.getLogin()).permitAll()
                .loginPage(workingSecurityProperty.getLoginPage()).permitAll()
                .successForwardUrl(workingSecurityProperty.getSuccessForwardUrl())
                .and()
                .logout()
                .logoutUrl(workingSecurityProperty.getLogout())
                .logoutSuccessUrl(workingSecurityProperty.getLogoutSuccess()).permitAll();
    }
}
