package com.glitter.working.config.spring.security;

import com.glitter.working.module.spring.security.config.adapter.CoreAuthorizeConfigManager;
import com.glitter.working.module.spring.security.config.adapter.provider.AuthorizeConfigProvider;
import com.glitter.working.module.spring.security.config.adapter.provider.LoginAuthorizeConfigProvider;
import com.glitter.working.module.spring.security.config.adapter.provider.RestAuthorizeConfigProvider;
import com.glitter.working.module.spring.security.config.userInfo.WorkingSecurityUserDetailsService;
import com.glitter.working.module.spring.security.defaultconfiger.CustomUserInfoFactory;
import com.glitter.working.module.spring.security.handle.CustomSecurityMetadataSource;
import com.glitter.working.module.spring.security.handle.UserInfoFactory;
import com.glitter.working.module.spring.security.handle.decision.CustomAccessDecisionManager;
import com.glitter.working.properties.spring.security.WorkingSecurityProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import java.util.List;

/**
 * @program:
 * @author: Player
 * @create: 2019-08-29
 **/
@Configuration
@ConditionalOnProperty(prefix = "working.security",name = "enable",havingValue = "true")
@EnableConfigurationProperties({WorkingSecurityProperty.class})
public class WorkingSecurityConfigurer extends WebSecurityConfigurerAdapter {


    @Bean
    @ConditionalOnMissingBean(UserInfoFactory.class)
    public UserInfoFactory userInfoFactory(){
        return new CustomUserInfoFactory();
    }

    @Bean
    public WorkingSecurityUserDetailsService securityUserDetailsService(){
        return  new WorkingSecurityUserDetailsService();
    }

    @Bean
    public AccessDecisionManager customAccessDecisionManager(){
        return new CustomAccessDecisionManager();
    }

    @Bean
    public FilterInvocationSecurityMetadataSource customSecurityMetadataSource(){
        return new CustomSecurityMetadataSource();
    }

    @Bean
    @Order(Byte.MIN_VALUE)
    public AuthorizeConfigProvider LoginAuthorizeConfigProvider(){
        return new LoginAuthorizeConfigProvider();
    }
    @Bean
    @ConditionalOnProperty(prefix = "working.security",name = "type",havingValue = "rest")
    @Order(Byte.MIN_VALUE+1)
    public AuthorizeConfigProvider RestAuthorizeConfigProvider(){
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
    private WorkingSecurityProperty workingSecurityProperty;

    @Autowired
    private CoreAuthorizeConfigManager coreAuthorizeConfigManager;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
      /*  System.out.println("登录连接:"+workingSecurityProperty.getLogin());
        http
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and().formLogin()
                .loginProcessingUrl(workingSecurityProperty.getLogin()).permitAll()
                .loginPage(workingSecurityProperty.getLoginPage()).permitAll()
                .successForwardUrl(workingSecurityProperty.getSuccessForwardUrl())
                .and()
                .logout()
                .logoutUrl(workingSecurityProperty.getLogout())
                .logoutSuccessUrl(workingSecurityProperty.getLogoutSuccess()).permitAll()
                .and().authorizeRequests().anyRequest().authenticated()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    public <O extends FilterSecurityInterceptor> O postProcess(O fsi) {
                        fsi.setAccessDecisionManager(customAccessDecisionManager());
                        fsi.setSecurityMetadataSource(customSecurityMetadataSource());
                        return fsi;
                    }
                });
        http.csrf().disable();*/
        coreAuthorizeConfigManager.config(http);
        http.authorizeRequests().anyRequest().authenticated()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    public <O extends FilterSecurityInterceptor> O postProcess(O fsi) {
                        fsi.setAccessDecisionManager(customAccessDecisionManager());
                        fsi.setSecurityMetadataSource(customSecurityMetadataSource());
                        return fsi;
                    }
                });
        http.csrf().disable();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(securityUserDetailsService()).passwordEncoder(
                new PasswordEncoder() {
                    @Override
                    public String encode(CharSequence charSequence) {
                        return charSequence.toString();
                    }

                    @Override
                    public boolean matches(CharSequence charSequence, String s) {
                        return s.equals(charSequence.toString());
                    }
                });
    }
}
