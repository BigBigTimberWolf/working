package com.glitter.working.module.spring.security.handle.afterLogin.provider.rest;

import com.glitter.working.module.util.security.response.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program:
 * @author: Player
 * @create: 2019-11-07
 **/
@Slf4j
public class RestAuthenticationFailure implements RestAuthenticationFailureInterface {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) {
        log.warn("登录失败！账号密码错误");
        ResponseUtil.response(httpServletResponse,400,"登录失败！账号密码错误");
    }
}
