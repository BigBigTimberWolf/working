package com.glitter.working.module.spring.security.handle.dataFactory;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.Collection;
import java.util.Map;

public interface MetadataSourceFactory {
     Map<RequestMatcher, Collection<ConfigAttribute>> getMetadataSource();
}
