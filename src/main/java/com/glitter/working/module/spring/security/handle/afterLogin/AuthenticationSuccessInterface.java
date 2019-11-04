package com.glitter.working.module.spring.security.handle.afterLogin;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: 有session模式提供的登录成功控制方法
 * @author: Player
 * @create: 2019-11-04
 **/
public interface AuthenticationSuccessInterface{
    void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication);
}
