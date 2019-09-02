package com.glitter.working.spring.security.web.resourcesManager;

import org.springframework.security.core.userdetails.User;

/**
 * @program:
 * @author: Player
 * @create: 2019-09-02
 **/
public interface UserInfoFactory {
    User getUserInfo(String username);
}
