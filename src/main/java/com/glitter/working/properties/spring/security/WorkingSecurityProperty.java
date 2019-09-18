package com.glitter.working.properties.spring.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * @program:
 * @author: Player
 * @create: 2019-09-02
 **/
@Data
public class WorkingSecurityProperty {

    //是否开启working security
    private boolean enable=false;

    /*security模式*/
    private String type="mvc";

    @NestedConfigurationProperty
    private WorkingSecurityLoginProperty login;

}
