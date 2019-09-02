package com.glitter.working.module.spring.security.config.adapter.provider;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

public interface AuthorizeConfigProvider {
    void config(HttpSecurity httpSecurity) throws Exception;
}
