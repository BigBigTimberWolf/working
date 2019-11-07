package com.glitter.working.config.spring.security.filter;

import com.glitter.working.module.spring.security.filter.JsonWebTokenFilter;
import com.glitter.working.module.spring.security.util.JWTUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program:
 * @author: Player
 * @create: 2019-11-07
 **/
@Configuration
public class WorkingSpringSecurityFilter {

    @Bean
    public JWTUtil jwtUtil(){
        return new JWTUtil();
    }



}
