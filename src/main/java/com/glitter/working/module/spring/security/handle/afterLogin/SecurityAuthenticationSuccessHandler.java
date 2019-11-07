package com.glitter.working.module.spring.security.handle.afterLogin;

import com.glitter.working.module.spring.security.handle.afterLogin.provider.rest.RestAuthenticationFailureInterface;
import com.glitter.working.module.spring.security.handle.afterLogin.provider.rest.RestAuthenticationSuccessInterface;
import com.glitter.working.module.spring.security.handle.afterLogin.provider.session.SessionAuthenticationFailureInterface;
import com.glitter.working.module.util.security.response.HttpCode;
import com.glitter.working.module.util.security.response.ResponseUtil;
import com.glitter.working.properties.spring.security.WorkingSecurityProperty;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @program:
 * @author: Player
 * @create: 2019-09-18
 **/
public  class SecurityAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private List<AuthenticationSuccessInterface> authenticationSuccessInterfaceList;
    @Autowired
    private WorkingSecurityProperty workingSecurityProperty;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        authenticationSuccessInterfaceList.forEach(authenticationSuccessInterface -> {
            if("rest".equals(workingSecurityProperty.getType())){
                if(authenticationSuccessInterface instanceof RestAuthenticationSuccessInterface){
                    authenticationSuccessInterface.onAuthenticationSuccess(httpServletRequest,httpServletResponse,authentication);
                }
            }else if ("mvc".equals(workingSecurityProperty.getType())){
                if(authenticationSuccessInterface instanceof SessionAuthenticationFailureInterface){
                    authenticationSuccessInterface.onAuthenticationSuccess(httpServletRequest,httpServletResponse,authentication);
                }
            }
        });
    }
}
