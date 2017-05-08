package com.phantom.module.cache;

import org.springframework.cache.Cache;

/**
 * 缓存接口
 * extends spring-cache
 * Created by zzk on 2017-03-02.
 */
public interface BaseCache extends Cache {
    /**
     * 缓存数据
     * @param key
     * @param value
     * @param timeout
     */
    void put(Object key, Object value, int timeout);
}
