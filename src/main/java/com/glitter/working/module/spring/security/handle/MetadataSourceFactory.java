package com.glitter.working.module.spring.security.handle;

import org.springframework.security.access.ConfigAttribute;

import java.util.Collection;
import java.util.Map;

public interface MetadataSourceFactory {
     Map<String, Collection<ConfigAttribute>> getMetadataSource();
}
