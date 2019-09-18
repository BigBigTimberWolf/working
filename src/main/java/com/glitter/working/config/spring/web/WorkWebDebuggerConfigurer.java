package com.glitter.working.config.spring.web;

import com.glitter.working.module.spring.web.filter.DebugFilter;
import com.glitter.working.properties.spring.web.WorkingWebProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @program:
 * @author: Player
 * @create: 2019-09-02
 **/
@Configuration
@ConditionalOnProperty(prefix = "working.spring.web",name = "debug",havingValue = "true")
public class WorkWebDebuggerConfigurer {
    @Bean
    public OncePerRequestFilter debugFilter(){
        return new DebugFilter();
    }
}
