package com.glitter.working.module.spring.security.handle.afterLoginOrLogout;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AfterLogoutInterface {
     void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication);
}
