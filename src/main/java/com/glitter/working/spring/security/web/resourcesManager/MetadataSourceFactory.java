package com.glitter.working.spring.security.web.resourcesManager;

import org.springframework.security.access.ConfigAttribute;

import java.util.Collection;
import java.util.Map;

public interface MetadataSourceFactory {
     Map<String, Collection<ConfigAttribute>> getMetadataSource();
}
