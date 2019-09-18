package com.glitter.working.module.spring.security.handle.metadataSource;

import com.glitter.working.module.spring.security.handle.dataFactory.MetadataSourceFactory;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.joor.Reflect;
import org.springframework.expression.ExpressionParser;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.ExpressionBasedFilterInvocationSecurityMetadataSource;
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


   private static final Reflect REFLECT = Reflect.on(ExpressionBasedFilterInvocationSecurityMetadataSource.class);

    private MetadataSourceFactory metadataSourceFactory;

    private final SecurityMetadataSource securityMetadataSource;

    private ExpressionParser expressionParser;

    public CustomSecurityMetadataSource(SecurityMetadataSource securityMetadataSource,MetadataSourceFactory metadataSourceFactory) {
        super(new LinkedHashMap<>());
        this.securityMetadataSource=securityMetadataSource;
        this.metadataSourceFactory=metadataSourceFactory;
        copyDelegateRequestMap();
    }

    private void copyDelegateRequestMap() {
        Reflect reflect = Reflect.on(this);
        reflect.set("requestMap" , getDelegateRequestMap());
    }

    private LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> getDelegateRequestMap() {
        Reflect reflect = Reflect.on(this.securityMetadataSource);
        return reflect.field("requestMap").get();
    }

    /*返回访问接口所需要的权限*/
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {



        HttpServletRequest request = ((FilterInvocation) object).getRequest();
        Collection<ConfigAttribute> configAttributes = new ArrayList<>();
        LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> metadataSource = metadataSourceFactory.getMetadataSource(request);


        if (Objects.isNull(this.expressionParser)) {
            SecurityExpressionHandler securityExpressionHandler = GlobalSecurityExpressionHandlerCacheObjectPostProcessor.getSecurityExpressionHandler();
            if (Objects.isNull(securityExpressionHandler)) {
                throw new NullPointerException(SecurityExpressionHandler.class.getName() + " is null");
            }
            this.expressionParser = securityExpressionHandler.getExpressionParser();
        }

        LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> webExpressionRequestMap =
                REFLECT.call("processMap" , metadataSource , this.expressionParser).get();
        if(MapUtils.isEmpty(metadataSource)){
            configAttributes.addAll(this.securityMetadataSource.getAttributes(object));
            return configAttributes;
        }

        for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry:webExpressionRequestMap.entrySet()) {
            if (entry.getKey().matches(request)) {
                configAttributes.addAll(entry.getValue());
                break;
            }
        }

        if (CollectionUtils.isEmpty(configAttributes)) {
            configAttributes.addAll(this.securityMetadataSource.getAttributes(object));
        }

        return  configAttributes;
    }


}
