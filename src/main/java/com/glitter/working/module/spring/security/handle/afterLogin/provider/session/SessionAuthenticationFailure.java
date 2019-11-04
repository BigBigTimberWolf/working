package com.glitter.working.module.spring.security.handle.afterLogin.provider.session;

import com.glitter.working.module.spring.security.handle.afterLogin.AuthenticationFailureInterface;
import com.glitter.working.module.util.security.response.ResponseUtil;
import com.glitter.working.properties.spring.security.WorkingSecurityLoginProperty;
import com.glitter.working.properties.spring.security.WorkingSecurityProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program:
 * @author: Player
 * @create: 2019-11-04
 **/
@Slf4j
public class SessionAuthenticationFailure implements SessionAuthenticationFailureInterface {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) {
        log.warn("登录失败！账号密码错误");
        ResponseUtil.response(httpServletResponse,400,"登录失败！账号密码错误");
    }
}
