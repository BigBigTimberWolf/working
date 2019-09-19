package com.glitter.working.module.spring.security.handle;

import org.junit.Test;
import org.springframework.security.access.SecurityConfig;

import java.util.Arrays;

public class SecurityAccessConfigHelperTest {

    @Test
    public void hasAnyRole() {
        String [] roleArray={"ADMIN","USER"};
        SecurityAccessConfigHelper securityAccessConfigHelper=new SecurityAccessConfigHelper();
        String role=securityAccessConfigHelper.hasAnyRole(roleArray).access();
        System.out.println(role);

        System.out.println(SecurityConfig.createList(role));


    }
}
