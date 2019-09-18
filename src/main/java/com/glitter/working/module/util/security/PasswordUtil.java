package com.glitter.working.module.util.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @program:
 * @author: Player
 * @create: 2019-09-18
 **/
public class PasswordUtil {
    /*加密密码*/
    public static String encoderPassword(String password){
        return  new BCryptPasswordEncoder().encode(password);
    }
}
