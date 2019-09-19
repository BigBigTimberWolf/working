package com.glitter.working.module.spring.security.config.dataFactory;

import com.glitter.working.module.spring.security.config.dataFactory.factory.AuthFactory;
import com.glitter.working.module.spring.security.handle.SecurityAccessConfigHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @program:
 * @author: Player
 * @create: 2019-09-02
 **/
public  class DefaultAuthFactory implements MetadataSourceFactory {



    @Autowired
    private AuthFactory authFactory;

    /*获取url有哪些权限可以访问*/
    public LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> getMetadataSource(HttpServletRequest request) {
        LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> requestMap=new LinkedHashMap<>();

        Map<String, List<String>> auth = authFactory.getAuth();
        if(MapUtils.isEmpty(auth)){
            return null;
        }
        auth.keySet().forEach(key->{
            AntPathRequestMatcher antPathRequestMatcher=new AntPathRequestMatcher(key);
            List<String> roleList = auth.get(key);
            if(!CollectionUtils.isEmpty(roleList)){
                String[] roleArray =  roleList.stream().map(String::toUpperCase).collect(Collectors.toList()).toArray(new String[roleList.size()]);
                SecurityAccessConfigHelper securityAccessConfigHelper=new SecurityAccessConfigHelper();
                requestMap.put(antPathRequestMatcher,SecurityConfig.createList(securityAccessConfigHelper.hasAnyRole(roleArray).access()));
            }
        });
        return requestMap;
    }


}
