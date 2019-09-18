package com.glitter.working.config.spring.security;

import com.glitter.working.module.spring.security.config.adapter.CoreAuthorizeConfigManager;
import com.glitter.working.module.spring.security.config.adapter.provider.AuthorizeConfigProvider;
import com.glitter.working.module.spring.security.config.adapter.provider.LoginAuthorizeConfigProvider;
import com.glitter.working.module.spring.security.config.adapter.provider.PostProcessorAuthorizeConfigProvider;
import com.glitter.working.module.spring.security.config.adapter.provider.RestAuthorizeConfigProvider;
import com.glitter.working.module.spring.security.config.userInfo.WorkingSecurityUserDetailsService;
import com.glitter.working.module.spring.security.defaultconfiger.CustomSecurityContext;
import com.glitter.working.module.spring.security.defaultconfiger.CustomUserInfoFactory;
import com.glitter.working.module.spring.security.handle.dataFactory.MetadataSourceFactory;
import com.glitter.working.module.spring.security.handle.dataFactory.UserInfoFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

/**
 * @program:
 * @author: Player
 * @create: 2019-08-29
 **/
@Configuration
@ConditionalOnProperty(prefix = "working.spring.security",name = "enable",havingValue = "true")
@ConditionalOnClass(DefaultAuthenticationEventPublisher.class)
public class WorkingSecurityConfigurer extends WebSecurityConfigurerAdapter {


    /*在没有提供用户信息的时候，默认提供一组用户信息*/
    @Bean
    @ConditionalOnMissingBean(UserInfoFactory.class)
    public UserInfoFactory userInfoFactory(){
        return new CustomUserInfoFactory();
    }

    /*在没有提供权限的时候，默认提供一组权限*/
    @Bean
    @ConditionalOnMissingBean(MetadataSourceFactory.class)
    public MetadataSourceFactory metadataSourceFactory(){
        return new CustomSecurityContext();
    }

    /*加载用户信息的bean*/
    @Bean
    public WorkingSecurityUserDetailsService securityUserDetailsService(){
        return  new WorkingSecurityUserDetailsService();
    }
    /*后置加载器、加载自定义投票器*/
    @Bean
    @Order(Byte.MIN_VALUE+1)
    public PostProcessorAuthorizeConfigProvider postProcessorAuthorizeConfigProvider(){
        return new PostProcessorAuthorizeConfigProvider();
    }

    /*登录配置*/
    @Bean
    @ConditionalOnProperty(prefix = "working.spring.security.login",name = "enable",havingValue = "true")
    @Order(Byte.MIN_VALUE)
    public AuthorizeConfigProvider loginAuthorizeConfigProvider(){
        return new LoginAuthorizeConfigProvider();
    }
    /*是否有session，rest是没有，别的就是有*/
    @Bean
    @ConditionalOnProperty(prefix = "working.spring.security",name = "type",havingValue = "rest")
    @Order(Byte.MIN_VALUE+2)
    public AuthorizeConfigProvider restAuthorizeConfigProvider(){
        return new RestAuthorizeConfigProvider();
    }



    @Bean
    public List<AuthorizeConfigProvider> authorizeConfigManagers(List<AuthorizeConfigProvider> authorizeConfigManagers){
    return authorizeConfigManagers;
    }

    @Bean
    public CoreAuthorizeConfigManager coreAuthorizeConfigManager(List<AuthorizeConfigProvider> authorizeConfigManagers){
        return new CoreAuthorizeConfigManager(authorizeConfigManagers);
    }


    @Autowired
    private CoreAuthorizeConfigManager coreAuthorizeConfigManager;

    @Autowired
    private WorkingSecurityUserDetailsService workingSecurityUserDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        coreAuthorizeConfigManager.config(http);
        http.csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(workingSecurityUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }
}
