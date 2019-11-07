package com.glitter.working.module.spring.security.handle.afterLoginOrLogout;

import com.glitter.working.module.spring.security.handle.afterLoginOrLogout.provider.rest.RestAuthenticationSuccessInterface;
import com.glitter.working.module.spring.security.handle.afterLoginOrLogout.provider.session.SessionAuthenticationFailureInterface;
import com.glitter.working.module.spring.security.handle.afterLoginOrLogout.provider.session.SessionAuthenticationSuccessInterface;
import com.glitter.working.properties.spring.security.WorkingSecurityProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
                if(authenticationSuccessInterface instanceof SessionAuthenticationSuccessInterface){
                    authenticationSuccessInterface.onAuthenticationSuccess(httpServletRequest,httpServletResponse,authentication);
                }
            }
        });
    }
}
