package com.glitter.working.module.spring.security.config.dataFactory.factory;

import org.springframework.security.core.userdetails.User;

/**
 * @program:
 * @author: Player
 * @create: 2019-09-19
 **/
public interface UserFactory {
    User getUser(String username);
}
