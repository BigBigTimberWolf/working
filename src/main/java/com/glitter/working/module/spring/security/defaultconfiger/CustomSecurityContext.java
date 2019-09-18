package com.glitter.working.module.spring.security.defaultconfiger;

import com.glitter.working.module.spring.security.handle.dataFactory.MetadataSourceFactory;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @program:
 * @author: Player
 * @create: 2019-09-02
 **/
public class CustomSecurityContext implements MetadataSourceFactory {

    /*获取url有哪些权限可以访问*/
    public  Map<RequestMatcher, Collection<ConfigAttribute>> getMetadataSource() {
        Collection<ConfigAttribute> admin_ConfigAttributes = new ArrayList<ConfigAttribute>();
        Collection<ConfigAttribute> user_ConfigAttributes = new ArrayList<ConfigAttribute>();
        ConfigAttribute admin = new SecurityConfig("admin");
        ConfigAttribute user = new SecurityConfig("user");
        admin_ConfigAttributes.add(admin);
        user_ConfigAttributes.add(user);
        Map<RequestMatcher, Collection<ConfigAttribute>> urlAuthorization=new HashMap<>();
        urlAuthorization.put(new AntPathRequestMatcher("/admin"),admin_ConfigAttributes);
        urlAuthorization.put(new AntPathRequestMatcher("/user"),user_ConfigAttributes);
        return urlAuthorization;
    }

}
