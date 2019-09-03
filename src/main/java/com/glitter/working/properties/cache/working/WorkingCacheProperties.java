package com.glitter.working.properties.cache.working;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @program:
 * @author: Player
 * @create: 2019-09-03
 **/
@Data
@ConfigurationProperties(prefix = "working.cache")
public class WorkingCacheProperties {

    private boolean enable=false;

}
