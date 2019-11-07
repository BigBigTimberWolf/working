package com.glitter.working.config.security;

import com.glitter.working.module.security.encryption.RSACoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.NoSuchAlgorithmException;

/**
 * @program:
 * @author: Player
 * @create: 2019-11-07
 **/
@Configuration
@Slf4j
public class WorkingSecurityConfigurer {
    @Bean
    public RSACoder rsaCoder(){
        try {
            return new RSACoder();
        } catch (NoSuchAlgorithmException e) {
            log.error("初始化RSA加密失败:{}",e);
        }
        return null;
    }

}
