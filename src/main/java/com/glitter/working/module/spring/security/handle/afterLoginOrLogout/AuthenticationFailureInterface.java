package com.glitter.working.module.spring.security.handle.afterLoginOrLogout;

import org.springframework.security.core.AuthenticationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program:
 * @author: Player
 * @create: 2019-11-04
 **/
public interface AuthenticationFailureInterface {
    void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e);
}
