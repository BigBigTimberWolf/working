package com.glitter.working.properties;

import com.glitter.working.properties.spring.WorkingSpringProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * @program:
 * @author: Player
 * @create: 2019-09-12
 **/
@ConfigurationProperties(prefix = "working")
@Data
public class WorkingProperty {

    // working-Spring-security
    @NestedConfigurationProperty
    private WorkingSpringProperties spring;



}
