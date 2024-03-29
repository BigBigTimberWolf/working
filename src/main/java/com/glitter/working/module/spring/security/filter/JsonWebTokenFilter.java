package com.glitter.working.module.spring.security.filter;

import com.glitter.working.module.cache.working.WorkingCache;
import com.glitter.working.module.spring.security.modal.JsonWebToken;
import com.glitter.working.module.spring.security.util.JWTUtil;
import com.glitter.working.module.util.security.response.ResponseUtil;
import com.glitter.working.properties.spring.security.WorkingSecurityProperty;
import com.glitter.working.properties.spring.security.WorkingSecurityRestProperty;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program:
 * @author: Player
 * @create: 2019-11-06
 **/
@Slf4j
public class JsonWebTokenFilter extends OncePerRequestFilter {
    @Autowired
    private WorkingSecurityProperty workingSecurityProperty;
    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private WorkingCache<String,String> workingCache;


    private UserDetailsService userDetailsService;

    public JsonWebTokenFilter(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }



    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        WorkingSecurityRestProperty rest = workingSecurityProperty.getRest();
        /*获取Token*/
        String token = httpServletRequest.getHeader(rest.getHeader());
        if(StringUtils.isEmpty(token)){
            // token为空
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }

        if(!token.startsWith(rest.getPrefix())){
            // token格式错误
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }
        //去除token头
        String originalToken=token;
        token=token.substring(rest.getPrefix().length());

        JsonWebToken jsonWebToken = jwtUtil.getJsonWebToken(token);

        if(jsonWebToken==null){
            log.warn("无效Token的请求:{}", httpServletRequest.getRequestURI());
            ResponseUtil.response(httpServletResponse,400,"无效Token的请求");
            return;
        }
        String tokenCache = workingCache.get(jwtUtil.getTokenName(jsonWebToken.getName()));
        if(StringUtils.isEmpty(tokenCache)||!StringUtils.equals(token,tokenCache)){
            log.warn("非法Token:{},{},{}",token,jsonWebToken.getName(),httpServletRequest.getRequestURI());
            ResponseUtil.response(httpServletResponse,403,"非法Token");
            return;
        }else {
            workingCache.set(jwtUtil.getTokenName(jsonWebToken.getName()),originalToken,rest.getTime());
        }

        /*从security中加载用户相关信息*/
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(jsonWebToken.getName());
        /*加载到上下文中*/
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(
                    httpServletRequest));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
