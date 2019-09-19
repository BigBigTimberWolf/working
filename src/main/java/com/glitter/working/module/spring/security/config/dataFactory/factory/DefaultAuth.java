package com.glitter.working.module.spring.security.config.dataFactory.factory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program:
 * @author: Player
 * @create: 2019-09-19
 **/
public class DefaultAuth implements AuthFactory{
    private final Map<String,List<String>> auth=new HashMap<>();
    @Override
    public Map<String, List<String>> getAuth() {
        auth.put("/admin", Arrays.asList("ADMIN"));
        auth.put("/user",Arrays.asList("USER"));
        auth.put("/super",Arrays.asList("ADMIN","SUPER_ADMIN"));
        return auth;
    }
}
