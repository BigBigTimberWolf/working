package com.glitter.working.config.spring.security;

import com.glitter.working.module.spring.security.config.adapter.CoreAuthorizeConfigManager;
import com.glitter.working.module.spring.security.config.adapter.provider.*;
import com.glitter.working.module.spring.security.config.dataFactory.factory.AuthFactory;
import com.glitter.working.module.spring.security.config.dataFactory.factory.DefaultAuth;
import com.glitter.working.module.spring.security.config.dataFactory.factory.DefaultUser;
import com.glitter.working.module.spring.security.config.dataFactory.factory.UserFactory;
import com.glitter.working.module.spring.security.config.details.WorkingSecurityUserDetailsService;
import com.glitter.working.module.spring.security.config.dataFactory.DefaultAuthFactory;
import com.glitter.working.module.spring.security.config.dataFactory.DefaultUserFactory;
import com.glitter.working.module.spring.security.config.dataFactory.MetadataSourceFactory;
import com.glitter.working.module.spring.security.config.dataFactory.UserInfoFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
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
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

/**
 * @program:
 * @author: Player
 * @create: 2019-08-29
 **/
@Configuration
@ConditionalOnProperty(prefix = "working.spring.security",name = "enable",havingValue = "true")
@ConditionalOnClass(DefaultAuthenticationEventPublisher.class)
public class WorkingSpringSecurityConfigurer extends WebSecurityConfigurerAdapter {



    @Bean
    public UserInfoFactory userInfoFactory(){
        return new DefaultUserFactory();
    }
    /*在没有提供用户信息的时候，默认提供一组用户信息*/
    @Bean
    @ConditionalOnMissingBean(UserFactory.class)
    public UserFactory userFactory(){
        return new DefaultUser();
    }




    @Bean
    public MetadataSourceFactory metadataSourceFactory(){
        return new DefaultAuthFactory();
    }
    /*在没有提供权限的时候，默认提供一组权限*/
    @Bean
    @ConditionalOnMissingBean(AuthFactory.class)
    public AuthFactory authFactory(){
        return new DefaultAuth();
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
    public AuthorizeConfigProvider enableLoginAuthorizeConfigProvider(){
        return new EnableLoginAuthorizeConfigProvider();
    }
    /*登录配置*/
    @Bean
    @ConditionalOnProperty(prefix = "working.spring.security.login",name = "enable",havingValue = "false")
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
    @ConditionalOnProperty(prefix = "working.spring.security",name = "type",havingValue = "mvc")
    @Order(Byte.MIN_VALUE+2)
    public AuthorizeConfigProvider mvcAuthorizeConfigProvider(){
        return new MvcAuthorizeConfigProvider(sessionRegistry());
    }

    @Bean
    public List<AuthorizeConfigProvider> authorizeConfigManagers(List<AuthorizeConfigProvider> authorizeConfigManagers){
    return authorizeConfigManagers;
    }

    @Bean
    public CoreAuthorizeConfigManager coreAuthorizeConfigManager(List<AuthorizeConfigProvider> authorizeConfigManagers){
        return new CoreAuthorizeConfigManager(authorizeConfigManagers);
    }

    @Bean
    @Order(Byte.MIN_VALUE)
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
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
