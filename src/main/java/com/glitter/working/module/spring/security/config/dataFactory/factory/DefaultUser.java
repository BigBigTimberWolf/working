package com.glitter.working.module.spring.security.config.dataFactory.factory;

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
 * @create: 2019-09-19
 **/
public class DefaultUser implements UserFactory {
    private  static String password = UUID.randomUUID().toString();
    private  static String encoderPassword = null;

    {
        encoderPassword = encoderPassword(password);
        System.out.println("Using generated security password: "+password);
    }


    @Override
    public User getUser(String username) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        if("admin".equals(username)){
            authorities.add(new SimpleGrantedAuthority("ADMIN"));
            return  new User(username,encoderPassword(password),authorities);
        }else if("user".equals(username)){
            authorities.add(new SimpleGrantedAuthority("USER"));
            return new User(username,encoderPassword,authorities);
        }
        return null;
    }
}
