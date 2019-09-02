package com.glitter.working.config.spring.security;

import com.glitter.working.module.spring.security.config.WorkingSecurityUserDetailsService;
import com.glitter.working.module.spring.security.handle.CustomSecurityMetadataSource;
import com.glitter.working.module.spring.security.handle.decision.CustomAccessDecisionManager;
import com.glitter.working.properties.spring.security.WorkingSecurityProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

/**
 * @program:
 * @author: Player
 * @create: 2019-08-29
 **/
@Configuration
@ConditionalOnProperty(prefix = "working.security",name = "type",havingValue = "mvc")
@EnableConfigurationProperties({WorkingSecurityProperty.class})
public class WorkingSecurityConfigurer extends WebSecurityConfigurerAdapter {
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


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/login")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and().formLogin()
                .loginProcessingUrl("/login").permitAll()
                .and().authorizeRequests().anyRequest().authenticated()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    public <O extends FilterSecurityInterceptor> O postProcess(O fsi) {
                        fsi.setAccessDecisionManager(customAccessDecisionManager());
                        fsi.setSecurityMetadataSource(customSecurityMetadataSource());
                        return fsi;
                    }
                });
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
