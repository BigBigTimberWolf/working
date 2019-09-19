package com.glitter.working.module.spring.security.config.dataFactory.factory;

import java.util.List;
import java.util.Map;

public interface AuthFactory {
    Map<String, List<String>> getAuth();
}
