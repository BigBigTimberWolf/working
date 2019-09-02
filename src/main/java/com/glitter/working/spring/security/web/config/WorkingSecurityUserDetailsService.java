package com.glitter.working.spring.security.web.config;

import com.glitter.working.spring.security.web.resourcesManager.UserInfoFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @program:
 * @author: Player
 * @create: 2019-09-02
 **/
@Component
public  class WorkingSecurityUserDetailsService implements UserDetailsService {

    @Resource
    private UserInfoFactory userInfoFactory;

    //todo 加载用户
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userInfoFactory.getUserInfo(username);
        if(!userIsNull(user)){
            return user;
        }
        throw new UsernameNotFoundException("用户不存在");

    }

    private boolean userIsNull(User user){
        return user == null || "".equals(user.getUsername()) || null == user.getUsername();
    }

    public  User userInfoFactory(String username){
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        if("admin".equals(username)){
            authorities.add(new SimpleGrantedAuthority("admin"));
            return  new User("username","123456",authorities);
        }else if("user".equals(username)){
            authorities.add(new SimpleGrantedAuthority("user"));
            return new User(username,"123456",authorities);
        }
        return null;
    };
}
