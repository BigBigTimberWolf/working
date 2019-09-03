package com.glitter.working.module.spring.security.config.adapter.provider;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

/**
 * @program:
 * @author: Player
 * @create: 2019-09-02
 **/
public class PostProcessorAuthorizeConfigProvider implements AuthorizeConfigProvider {
    private AccessDecisionManager customAccessDecisionManager;
    private FilterInvocationSecurityMetadataSource customSecurityMetadataSource;

    public PostProcessorAuthorizeConfigProvider(AccessDecisionManager customAccessDecisionManager, FilterInvocationSecurityMetadataSource customSecurityMetadataSource) {
        this.customAccessDecisionManager = customAccessDecisionManager;
        this.customSecurityMetadataSource = customSecurityMetadataSource;
    }

    @Override
    public void config(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests().anyRequest().authenticated()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    public <O extends FilterSecurityInterceptor> O postProcess(O fsi) {
                        fsi.setAccessDecisionManager(customAccessDecisionManager);
                        fsi.setSecurityMetadataSource(customSecurityMetadataSource);
                        return fsi;
                    }
                });
    }
}
