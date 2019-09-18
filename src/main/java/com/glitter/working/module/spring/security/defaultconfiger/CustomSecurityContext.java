package com.glitter.working.module.spring.security.defaultconfiger;

import com.glitter.working.module.spring.security.handle.SecurityAccessConfigHelper;
import com.glitter.working.module.spring.security.handle.dataFactory.MetadataSourceFactory;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @program:
 * @author: Player
 * @create: 2019-09-02
 **/
public class CustomSecurityContext implements MetadataSourceFactory {

    /*获取url有哪些权限可以访问*/
    public LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> getMetadataSource(HttpServletRequest request) {
/*        Collection<ConfigAttribute> admin_ConfigAttributes = new ArrayList<ConfigAttribute>();
        Collection<ConfigAttribute> user_ConfigAttributes = new ArrayList<ConfigAttribute>();
        ConfigAttribute admin = new SecurityConfig("admin");
        ConfigAttribute user = new SecurityConfig("user");
        admin_ConfigAttributes.add(admin);
        user_ConfigAttributes.add(user);
        LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> urlAuthorization=new LinkedHashMap<>();
        urlAuthorization.put(new AntPathRequestMatcher("/admin"),admin_ConfigAttributes);
        urlAuthorization.put(new AntPathRequestMatcher("/user"),user_ConfigAttributes);*/

        LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> requestMap=new LinkedHashMap<>();



        AntPathRequestMatcher antPathRequestMatcher=new AntPathRequestMatcher("/admin");
        SecurityAccessConfigHelper securityAccessConfigHelper=new SecurityAccessConfigHelper();
        List<ConfigAttribute> attributes= SecurityConfig.createList(securityAccessConfigHelper.hasAnyRole("ADMIN").access());

        requestMap.put(antPathRequestMatcher,attributes);


        AntPathRequestMatcher antPathRequestMatcher2=new AntPathRequestMatcher("/user");
        SecurityAccessConfigHelper securityAccessConfigHelper2=new SecurityAccessConfigHelper();
        List<ConfigAttribute> attributes2= SecurityConfig.createList(securityAccessConfigHelper.hasAnyRole("USER").access());

        requestMap.put(antPathRequestMatcher2,attributes2);



        return requestMap;
    }

}
