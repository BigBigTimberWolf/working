package com.glitter.working.module.spring.security.handle.afterLogin;

import com.glitter.working.module.util.security.response.HttpCode;
import com.glitter.working.module.util.security.response.ResponseUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program:
 * @author: Player
 * @create: 2019-09-18
 **/
public  class SecurityAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private AuthenticationSuccessInterface authenticationSuccess;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        authenticationSuccess.onAuthenticationSuccess(httpServletRequest,httpServletResponse,authentication);

    }
}
