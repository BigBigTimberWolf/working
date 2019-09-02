package com.glitter.working.spring.security.web;

import com.glitter.working.spring.security.web.config.WorkingSecurityUserDetailsService;
import com.glitter.working.spring.security.web.resourcesManager.UserInfoFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @program:
 * @author: Player
 * @create: 2019-09-02
 **/
@Component
public class CustomUserInfoFactory implements UserInfoFactory {

    @Override
    public User getUserInfo(String username) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        if("admin".equals(username)){
            authorities.add(new SimpleGrantedAuthority("admin"));
            return  new User("username","123456",authorities);
        }else if("user".equals(username)){
            authorities.add(new SimpleGrantedAuthority("user"));
            return new User(username,"123456",authorities);
        }
        return null;
    }


}
