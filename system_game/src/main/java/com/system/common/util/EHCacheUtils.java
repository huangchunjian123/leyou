package com.system.common.util;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EHCacheUtils {

    @Autowired
    private CacheManager cacheManager;
    /**
     * 设置缓存对象
     * @param key
     * @param object
     */
    public void setCache(String key, Object object){
        this.getCacheName();
        Cache cache = cacheManager.getCache("maxLog");
        Element element = new Element(key,object);
        cache.put(element);
    }
    /**
     * 从缓存中取出对象
     * @param key
     * @return
     */
    public Object getCache(String key){
        this.getCacheName();
        Object object = null;
        Cache cache = cacheManager.getCache("maxLog");
        if(cache.get(key)!=null && !cache.get(key).equals("")){
            object = cache.get(key).getObjectValue();
        }
        return object;
    }

    public void getCacheName(){
        String [] names = cacheManager.getCacheNames();
        for(String name:names){
            System.out.println("cashName:"+name);
        }
    }
}
