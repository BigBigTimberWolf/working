package com.glitter.working.module.spring.security.handle.afterLogin.provider.rest;

import com.glitter.working.module.cache.working.WorkingCache;
import com.glitter.working.module.spring.security.modal.JsonWebToken;
import com.glitter.working.module.spring.security.util.JWTUtil;
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
import java.util.Date;

/**
 * @program:
 * @author: Player
 * @create: 2019-11-07
 **/
@Slf4j
public class RestAuthenticationSuccess implements RestAuthenticationSuccessInterface {
    @Autowired
    private WorkingCache<String,String> workingCache;
    @Autowired
    private WorkingSecurityProperty workingSecurityProperty;
    @Autowired
    private JWTUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        if(principal==null|| StringUtils.isEmpty(principal.getUsername())){
            ResponseUtil.response(httpServletResponse,402,"异常!未获取到用户");
        }else {
            JsonWebToken jsonWebToken = new JsonWebToken();
            jsonWebToken.setExpirationTime(new Date().getTime()+workingSecurityProperty.getRest().getTime());
            jsonWebToken.setName(principal.getUsername());
            String token = jwtUtil.getToken(jsonWebToken);
            workingCache.set(jwtUtil.getTokenName(principal.getUsername()),token,workingSecurityProperty.getRest().getTime());
            log.info(principal.getUsername()+"登录成功！");
            httpServletResponse.addHeader(workingSecurityProperty.getRest().getHeader(),workingSecurityProperty.getRest().getPrefix()+token);
            ResponseUtil.response(httpServletResponse, HttpCode.OK, "Welcome to here "+principal.getUsername());
        }
    }
}
