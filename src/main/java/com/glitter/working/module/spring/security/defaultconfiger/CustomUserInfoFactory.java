package com.glitter.working.module.spring.security.defaultconfiger;

import com.glitter.working.module.spring.security.handle.dataFactory.UserInfoFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import static com.glitter.working.module.util.security.PasswordUtil.encoderPassword;

/**
 * @program:
 * @author: Player
 * @create: 2019-09-02
 **/

public class CustomUserInfoFactory implements UserInfoFactory {

    private  static String password = "123456";
    private  static String encoderPassword = null;

    {
         password=UUID.randomUUID().toString();
         encoderPassword = encoderPassword(password);
        System.out.println(password);
    }

    @Override
    public User getUserInfo(String username) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        if("admin".equals(username)){
            authorities.add(new SimpleGrantedAuthority("admin"));
            return  new User(username,encoderPassword(password),authorities);
        }else if("user".equals(username)){
            authorities.add(new SimpleGrantedAuthority("user"));
            return new User(username,encoderPassword,authorities);
        }
        return null;
    }


}
