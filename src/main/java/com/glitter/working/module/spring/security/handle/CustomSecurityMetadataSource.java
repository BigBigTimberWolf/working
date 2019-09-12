package com.glitter.working.module.spring.security.handle;

import com.glitter.working.module.spring.security.defaultconfiger.CustomSecurityContext;
import com.glitter.working.properties.spring.security.WorkingSecurityProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * @program:
 * @author: Player
 * @create: 2019-09-02
 **/
public class CustomSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {


    @Autowired
    private MetadataSourceFactory metadataSourceFactory;

    @Autowired
    private WorkingSecurityProperty workingSecurityProperty;

    /*本次访问需要的权限集合，返回null则为默认白名单*/
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        FilterInvocation fi = (FilterInvocation) object;
        Map<String, Collection<ConfigAttribute>> metadataSource = metadataSourceFactory.getMetadataSource();
        for (Map.Entry<String, Collection<ConfigAttribute>> entry : metadataSource.entrySet()) {
            String uri = entry.getKey();
            RequestMatcher requestMatcher = new AntPathRequestMatcher(uri);
            if (requestMatcher.matches(fi.getHttpRequest())) {
                return entry.getValue();
            }
        }

        if(whiteListCheck(fi.getHttpRequest()))
            return null;

        if("black".equals(workingSecurityProperty.getMode())){
            Collection<ConfigAttribute> anymoreConfigAttributes = new ArrayList<ConfigAttribute>();
            anymoreConfigAttributes.add(new SecurityConfig("NoAuthorization"));
            return anymoreConfigAttributes;
        }
        return  null;
    }

    /*返回所有的资源权限,SpringSecurity在启动的时候会校验每个ConfigAttribute的权限配置是否正确，可以直接返回null*/
    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    /*返回是否支持校验，True为支持*/
    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }


    private boolean whiteListCheck(HttpServletRequest httpServletRequest){

        if(requestMatcherFactory(workingSecurityProperty.getLogin().getLoginUrl()).matches(httpServletRequest))
            return true;
        if(requestMatcherFactory(workingSecurityProperty.getLogin().getLoginPage()).matches(httpServletRequest))
            return true;
        if(requestMatcherFactory(workingSecurityProperty.getLogin().getSuccessForwardUrl()).matches(httpServletRequest))
            return true;
        if(requestMatcherFactory(workingSecurityProperty.getLogin().getLogoutUrl()).matches(httpServletRequest))
            return true;
        if(requestMatcherFactory(workingSecurityProperty.getLogin().getLogoutSuccess()).matches(httpServletRequest))
            return true;

        return false;
    }

    private RequestMatcher requestMatcherFactory(String url){
        return new AntPathRequestMatcher(url);
    }
}
