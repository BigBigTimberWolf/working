package com.glitter.working.config.spring.security.handle;

import com.glitter.working.config.spring.security.WorkingSpringSecurityConfigurer;
import com.glitter.working.module.spring.security.handle.afterLoginOrLogout.*;
import com.glitter.working.module.spring.security.handle.afterLoginOrLogout.provider.rest.RestAuthenticationFailure;
import com.glitter.working.module.spring.security.handle.afterLoginOrLogout.provider.rest.RestAuthenticationFailureInterface;
import com.glitter.working.module.spring.security.handle.afterLoginOrLogout.provider.rest.RestAuthenticationSuccess;
import com.glitter.working.module.spring.security.handle.afterLoginOrLogout.provider.rest.RestAuthenticationSuccessInterface;
import com.glitter.working.module.spring.security.handle.afterLoginOrLogout.provider.session.SessionAuthenticationFailure;
import com.glitter.working.module.spring.security.handle.afterLoginOrLogout.provider.session.SessionAuthenticationFailureInterface;
import com.glitter.working.module.spring.security.handle.afterLoginOrLogout.provider.session.SessionAuthenticationSuccess;
import com.glitter.working.module.spring.security.handle.afterLoginOrLogout.provider.session.SessionAuthenticationSuccessInterface;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program:
 * @author: Player
 * @create: 2019-11-04
 **/
@Configuration
@ConditionalOnClass(WorkingSpringSecurityConfigurer.class)
public class AfterLoginConfigurer {

    /*有session登录成功控制类*/
    @Bean
    @ConditionalOnMissingBean({SessionAuthenticationSuccessInterface.class})
    public AuthenticationSuccessInterface sessionAuthenticationSuccess(){
        return new SessionAuthenticationSuccess();
    }

    /*有session登录失败控制类*/
    @Bean
    @ConditionalOnMissingBean({SessionAuthenticationFailureInterface.class})
    public AuthenticationFailureInterface  SessionAuthenticationFailure(){
        return new SessionAuthenticationFailure();
    }

    @Bean
    @ConditionalOnMissingBean({RestAuthenticationFailureInterface.class})
    public AuthenticationSuccessInterface RestAuthenticationSuccess(){
        return new RestAuthenticationSuccess();
    }
    @Bean
    @ConditionalOnMissingBean({RestAuthenticationSuccessInterface.class})
    public AuthenticationFailureInterface RestAuthenticationFailure(){
        return new RestAuthenticationFailure();
    }

    @Bean
    public SecurityAuthenticationFailureHandler securityAuthenticationFailureHandler(){
        return new SecurityAuthenticationFailureHandler();
    }

    @Bean
    public SecurityAuthenticationSuccessHandler securityAuthenticationSuccessHandler(){
        return new SecurityAuthenticationSuccessHandler();
    }

    @Bean
    public SecurityLogoutHandler securityLogoutHandler(){
        return new SecurityLogoutHandler();
    }

}
