package com.glitter.working.module.cache.working;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @program:
 * @author: Player
 * @create: 2019-09-03
 **/
public class WorkingCache  {

    private final MaintainThread maintainThread = new MaintainThread();
    public WorkingCache(){
        maintainThread.start();
    }

    /*缓存*/
    private ConcurrentMap <String, WorkingCacheNode> CACHE=new ConcurrentHashMap(2048);
    /*缓存过期列表*/
    private PriorityQueue<WorkingCacheNode> cacheExpireQueue=new PriorityQueue(2048);

    protected boolean maintainThreadState=false;

    /*添加成功后会返回value*/
    public synchronized Object set(@NotNull String key, Object value, @NotNull Long expireTime){
        //到期时间
        long effectiveTime=System.currentTimeMillis() + expireTime;
        WorkingCacheNode node=new WorkingCacheNode(key,value,effectiveTime);
        WorkingCacheNode inhabitantsNode = CACHE.get(key);
        /*如果原本缓存中就有这个key的值*/
        if(Objects.nonNull(inhabitantsNode)){
            CACHE.putIfAbsent(key,node);
            cacheExpireQueue.remove(inhabitantsNode);
            cacheExpireQueue.add(node);
        }else {
            CACHE.put(key,node);
            cacheExpireQueue.add(node);
            this.maintainThreadState=true;
        }
        return value;
    }

    public Object get(String key){
        WorkingCacheNode node = CACHE.get(key);
        if(checkNode(node)){
           return node.getValue();
        }

        return null;
    }
    private boolean checkNode(WorkingCacheNode node){
            if(System.currentTimeMillis()>node.getEffectiveTime()){
                remove(node.getKey());
                return false;
            }
            return true;
    }

    /*删除*/
    public synchronized void remove(String key){
        WorkingCacheNode node = CACHE.get(key);
        if(Objects.nonNull(CACHE.remove(key))){
            cacheExpireQueue.remove(node);
        }
    }

    private class  MaintainThread extends Thread {
        @Override
        public void run() {
            while (true){
                while (cacheExpireQueue.size()>0){
                    WorkingCacheNode node = cacheExpireQueue.peek();
                    long now =System.currentTimeMillis();
                    if (node==null||node.getEffectiveTime()>now){
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }else {
                        CACHE.remove(node.getKey());
                        cacheExpireQueue.poll();
                    }
                }
            }
        }
    }

    @Data
    @AllArgsConstructor
    private class WorkingCacheNode implements Comparable<WorkingCacheNode> {

        private String key;

        private Object value;

        private Long effectiveTime;



        @Override
        public int compareTo(WorkingCacheNode o) {
            long l=this.effectiveTime-o.effectiveTime;
            if(l==0l)
                return 0;
            else if (l<0)
                return -1;
            else
                return 1;

        }
    }

}
