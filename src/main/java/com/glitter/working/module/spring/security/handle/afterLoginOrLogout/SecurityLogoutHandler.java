package com.glitter.working.module.spring.security.handle.afterLoginOrLogout;

import com.glitter.working.module.cache.working.WorkingCache;
import com.glitter.working.module.spring.security.modal.JsonWebToken;
import com.glitter.working.module.spring.security.util.JWTUtil;
import com.glitter.working.module.util.security.response.HttpCode;
import com.glitter.working.module.util.security.response.ResponseUtil;
import com.glitter.working.properties.spring.security.WorkingSecurityProperty;
import com.glitter.working.properties.spring.security.WorkingSecurityRestProperty;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @program:
 * @author: Player
 * @create: 2019-11-07
 **/
@Slf4j
public class SecurityLogoutHandler implements LogoutSuccessHandler {
    @Autowired
    private WorkingSecurityProperty workingSecurityProperty;
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private WorkingCache<String,String> workingCache;
    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        WorkingSecurityRestProperty rest = workingSecurityProperty.getRest();
        /*获取Token*/
        String token = httpServletRequest.getHeader(rest.getHeader());
        if(StringUtils.isEmpty(token)){
            // token为空
            httpServletResponse.sendRedirect(workingSecurityProperty.getLogin().getLoginPage());
            return;
        }
        if(!token.startsWith(rest.getPrefix())){
            // token格式错误
            ResponseUtil.response(httpServletResponse,200,"注销成功");
            return;
        }
        //去除token头
        token=token.substring(rest.getPrefix().length());
        JsonWebToken jsonWebToken = jwtUtil.getJsonWebToken(token);
        if(jsonWebToken==null){
            log.warn("无效Token的请求希望注销:{}", httpServletRequest.getRequestURI());
            ResponseUtil.response(httpServletResponse,200,"注销成功");
            return;
        }
        String tokenCache = workingCache.get(jwtUtil.getTokenName(jsonWebToken.getName()));

        if(StringUtils.isEmpty(tokenCache)||!StringUtils.equals(token,tokenCache)){
            log.warn("非法Token希望注销:{},{},{}",token,jsonWebToken.getName(),httpServletRequest.getRequestURI());
            ResponseUtil.response(httpServletResponse,200,"注销成功");
            return;
        }
        workingCache.remove(jwtUtil.getTokenName(jsonWebToken.getName()));
        log.info("{}注销成功",jsonWebToken.getName());
        ResponseUtil.response(httpServletResponse,200,"注销成功");
    }
}
