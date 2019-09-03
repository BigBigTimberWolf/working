package com.glitter.working.config.cache.working;

import com.glitter.working.module.cache.working.WorkingCache;
import com.glitter.working.properties.cache.working.WorkingCacheProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program:
 * @author: Player
 * @create: 2019-09-03
 **/
@Configuration
@ConditionalOnProperty(prefix = "working.cache",name = "enable",havingValue = "true")
@EnableConfigurationProperties({WorkingCacheProperties.class})
public class WorkingCacheConfigurer {
    @Bean
    public WorkingCache workingCache(){
        return  new WorkingCache();
    }
}
