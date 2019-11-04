package com.glitter.working.module.spring.security.handle.afterLogin.provider.session;

import com.glitter.working.module.util.security.response.HttpCode;
import com.glitter.working.module.util.security.response.ResponseUtil;
import com.glitter.working.properties.spring.security.WorkingSecurityLoginProperty;
import com.glitter.working.properties.spring.security.WorkingSecurityProperty;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: 有session时加载
 * @author: Player
 * @create: 2019-11-04
 **/
@Slf4j
public class SessionAuthenticationSuccess implements SessionAuthenticationSuccessInterface {
    @Autowired
    private WorkingSecurityProperty workingSecurityProperty;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication){
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        if(principal==null|| StringUtils.isEmpty(principal.getUsername())){
            ResponseUtil.response(httpServletResponse,402,"异常!未获取到用户");
        }else {
            log.info(principal.getUsername()+"登录成功！");
            try {
                WorkingSecurityLoginProperty workingSecurityLoginProperty=workingSecurityProperty.getLogin();
                httpServletResponse.sendRedirect(workingSecurityLoginProperty.getSuccessForwardUrl());
            } catch (IOException e) {
                e.printStackTrace();
            }
            //ResponseUtil.response(httpServletResponse, HttpCode.OK, "Welcome to here "+principal.getUsername());
        }
    }
}
