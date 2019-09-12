package com.glitter.working.module.cache.working;

import org.junit.Test;

public class WorkingCacheTest {

    @Test
    public void testCache(){
        WorkingCache workingCache=new WorkingCache();
        workingCache.set("测试1","测试1内容",1000000l);
        workingCache.set("测试2","测试2内容",10000l);
        while (true){
            workingCache.get("测试5");
        }
    }

}
