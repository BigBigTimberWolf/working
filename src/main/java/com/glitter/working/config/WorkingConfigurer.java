package com.glitter.working.config;

import com.glitter.working.module.cache.working.WorkingCache;
import com.glitter.working.properties.WorkingProperty;
import com.glitter.working.properties.spring.security.WorkingSecurityProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program:
 * @author: Player
 * @create: 2019-09-12
 **/
@Configuration
@EnableConfigurationProperties({WorkingProperty.class})
public class WorkingConfigurer {

    @Autowired
    private WorkingProperty workingProperty;

    public WorkingConfigurer(WorkingProperty workingProperty) {
        this.workingProperty=workingProperty;
    }

    /*securityPropertyBean*/
    @Bean
    public WorkingSecurityProperty workingSecurityProperty(){
        return this.workingProperty.getSpring().getSecurity();
    }

    // working 定时缓存
   @Bean
   public WorkingCache workingCache(){
       return  new WorkingCache();
   }

}
