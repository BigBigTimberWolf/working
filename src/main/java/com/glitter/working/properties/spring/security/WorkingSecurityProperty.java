package com.glitter.working.properties.spring.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @program:
 * @author: Player
 * @create: 2019-09-02
 **/
@Data
@ConfigurationProperties(prefix = "working.security")
public class WorkingSecurityProperty {
    private String type="mvc";
}
