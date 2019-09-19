package com.glitter.working.module.spring.security.config.userInfo;

import com.glitter.working.module.spring.security.config.dataFactory.UserInfoFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @program:
 * @author: Player
 * @create: 2019-09-02
 **/
public  class WorkingSecurityUserDetailsService implements UserDetailsService {


    @Autowired
    private UserInfoFactory userInfoFactory;




    //todo 加载用户
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userInfoFactory.getUser(username);
        if(!userIsNull(user)){
            return user;
        }
        throw new UsernameNotFoundException("用户不存在");

    }

    private boolean userIsNull(User user){
        return user == null || "".equals(user.getUsername()) || null == user.getUsername();
    }
}
