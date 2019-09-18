package com.glitter.working.module.spring.security.handle.metadataSource;

import com.glitter.working.module.spring.security.handle.dataFactory.MetadataSourceFactory;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.DefaultFilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @program:
 * @author: Player
 * @create: 2019-09-02
 **/
public class CustomSecurityMetadataSource extends DefaultFilterInvocationSecurityMetadataSource {


    private MetadataSourceFactory metadataSourceFactory;

    private final SecurityMetadataSource securityMetadataSource;

    public CustomSecurityMetadataSource(SecurityMetadataSource securityMetadataSource,MetadataSourceFactory metadataSourceFactory) {
        super(new LinkedHashMap<>());
        this.securityMetadataSource=securityMetadataSource;
        this.metadataSourceFactory=metadataSourceFactory;
    }

    /*返回访问接口所需要的权限*/
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        HttpServletRequest request = ((FilterInvocation) object).getRequest();
        Collection<ConfigAttribute> configAttributes = new ArrayList<>();
        Map<RequestMatcher, Collection<ConfigAttribute>> metadataSource = metadataSourceFactory.getMetadataSource();

        if(MapUtils.isEmpty(metadataSource)){
            configAttributes.addAll(this.securityMetadataSource.getAttributes(object));
            return configAttributes;
        }

        if(Objects.nonNull(metadataSource)){
            for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry:metadataSource.entrySet()) {
                if (entry.getKey().matches(request)) {
                    configAttributes.addAll(entry.getValue());
                    break;
                }
            }
        }

        if (CollectionUtils.isEmpty(configAttributes)) {
            configAttributes.addAll(this.securityMetadataSource.getAttributes(object));
        }

        return  configAttributes;
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

}
