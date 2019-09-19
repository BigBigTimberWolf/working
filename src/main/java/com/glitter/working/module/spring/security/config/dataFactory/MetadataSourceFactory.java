package com.glitter.working.module.spring.security.config.dataFactory;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.LinkedHashMap;

public interface MetadataSourceFactory {
     LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> getMetadataSource(HttpServletRequest request);
}
