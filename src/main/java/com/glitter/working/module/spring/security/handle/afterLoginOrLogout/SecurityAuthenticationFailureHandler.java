package com.glitter.working.module.spring.security.handle.afterLoginOrLogout;

import com.glitter.working.module.spring.security.handle.afterLoginOrLogout.provider.rest.RestAuthenticationFailure;
import com.glitter.working.module.spring.security.handle.afterLoginOrLogout.provider.rest.RestAuthenticationFailureInterface;
import com.glitter.working.module.spring.security.handle.afterLoginOrLogout.provider.session.SessionAuthenticationFailure;
import com.glitter.working.module.spring.security.handle.afterLoginOrLogout.provider.session.SessionAuthenticationFailureInterface;
import com.glitter.working.properties.spring.security.WorkingSecurityProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

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
public class SecurityAuthenticationFailureHandler implements AuthenticationFailureHandler {
   @Autowired
   private List<AuthenticationFailureInterface> authenticationFailureInterfaceList;
   @Autowired
   private WorkingSecurityProperty workingSecurityProperty;

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        authenticationFailureInterfaceList.forEach(authenticationFailureInterface -> {
            if("rest".equals(workingSecurityProperty.getType())){
                if(authenticationFailureInterface instanceof RestAuthenticationFailureInterface){
                        authenticationFailureInterface.onAuthenticationFailure(httpServletRequest,httpServletResponse,e);
                }
            }else if ("mvc".equals(workingSecurityProperty.getType())){
                if(authenticationFailureInterface instanceof SessionAuthenticationFailureInterface){
                    authenticationFailureInterface.onAuthenticationFailure(httpServletRequest,httpServletResponse,e);
                }
            }
        });
    }
}
