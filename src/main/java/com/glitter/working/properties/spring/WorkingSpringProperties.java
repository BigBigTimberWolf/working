package com.glitter.working.properties.spring;

import com.glitter.working.properties.spring.security.WorkingSecurityProperty;
import com.glitter.working.properties.spring.web.WorkingWebProperty;
import lombok.Data;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * @program:
 * @author: Player
 * @create: 2019-09-12
 **/
@Data
public class WorkingSpringProperties {


    @NestedConfigurationProperty
    private WorkingSecurityProperty security;

    @NestedConfigurationProperty
    private WorkingWebProperty web;
}
