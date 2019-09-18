package com.glitter.working.module.spring.security.config.adapter.provider;

import com.glitter.working.module.spring.security.handle.dataFactory.MetadataSourceFactory;
import com.glitter.working.module.spring.security.handle.metadataSource.CustomSecurityMetadataPostProcessor;
import com.glitter.working.module.spring.security.handle.metadataSource.GlobalSecurityExpressionHandlerCacheObjectPostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

/**
 * @program:
 * @author: Player
 * @create: 2019-09-02
 **/
public class PostProcessorAuthorizeConfigProvider implements AuthorizeConfigProvider  {

    @Autowired
    private MetadataSourceFactory metadataSourceFactory;



    @Override
    public void config(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests().anyRequest().authenticated()
                .withObjectPostProcessor(new CustomSecurityMetadataPostProcessor(metadataSourceFactory))
                .withObjectPostProcessor(new GlobalSecurityExpressionHandlerCacheObjectPostProcessor());
    }

}
