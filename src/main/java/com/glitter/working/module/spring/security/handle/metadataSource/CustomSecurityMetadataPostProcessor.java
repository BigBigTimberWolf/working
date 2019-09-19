package com.glitter.working.module.spring.security.handle.metadataSource;

import com.glitter.working.module.spring.security.config.dataFactory.MetadataSourceFactory;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

/**
 * @program:
 * @author: Player
 * @create: 2019-09-18
 **/
public class CustomSecurityMetadataPostProcessor implements ObjectPostProcessor<FilterSecurityInterceptor> {


    private MetadataSourceFactory metadataSourceFactory;

    public CustomSecurityMetadataPostProcessor(MetadataSourceFactory metadataSourceFactory) {
        this.metadataSourceFactory = metadataSourceFactory;
    }

    @Override
    public <O extends FilterSecurityInterceptor> O postProcess(O object) {
        FilterSecurityInterceptor interceptor = object;
        interceptor.setSecurityMetadataSource(new CustomSecurityMetadataSource(interceptor.obtainSecurityMetadataSource(),this.metadataSourceFactory));
        return (O)interceptor;
    }
}
