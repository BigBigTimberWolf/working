package com.glitter.working.module.spring.security.defaultconfiger;

import org.junit.Test;

import java.util.UUID;

import static com.glitter.working.module.util.security.PasswordUtil.encoderPassword;


public class DefaultUserFactoryTest {


    @Test
    public void getUserInfo() {
        String password=UUID.randomUUID().toString();
        String encoderPassword = encoderPassword(password);
    }
}
