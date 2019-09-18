package com.glitter.working.module.spring.security.handle.dataFactory;

import org.springframework.security.core.userdetails.User;

/**
 * @program:
 * @author: Player
 * @create: 2019-09-02
 **/
public interface UserInfoFactory {
    User getUserInfo(String username);
}
