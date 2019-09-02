package com.glitter.working.properties.spring.web;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @program:
 * @author: Player
 * @create: 2019-09-02
 **/
@Data
@ConfigurationProperties(prefix = "working.web")
public class WorkingWebProperty {
    private boolean debug=false;
}
