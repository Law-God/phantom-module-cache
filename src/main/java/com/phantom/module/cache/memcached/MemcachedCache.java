package com.phantom.module.cache.memcached;

import com.phantom.module.cache.BaseCache;
import net.spy.memcached.MemcachedClient;
import org.apache.log4j.Logger;
import org.springframework.cache.support.SimpleValueWrapper;
import java.util.concurrent.Callable;

/**
 * Created by Administrator on 2017-03-03.
 */
public class MemcachedCache implements BaseCache {
    private final Logger log = Logger.getLogger(MemcachedCache.class);
    private String name;
    private int timeout;
    private MemcachedClient memcachedClient;

    /**
     * 将数据放置缓存中，指定缓存时间
     * @param key
     * @param value
     * @param timeout
     */
    public void put(Object key, Object value, int timeout) {
        memcachedClient.set((String)key,timeout,value);
        log.debug("memcache缓存key："+(String)key+"；值："+value+"；time："+timeout);
    }

    /**
     * 将数据放置缓存中，默认配置缓存时间
     * @param o
     * @param o1
     */
    public void put(Object o, Object o1) {
        memcachedClient.set((String)o,timeout,o1);
        log.debug("memcache缓存key："+(String)o+"；值："+o1+"；time："+timeout);

    }
    public String getName() {
        return name;
    }

    public Object getNativeCache() {
        return null;
    }

    public ValueWrapper get(Object o) {
        Object value = memcachedClient.get((String)o);
        ValueWrapper valueWrapper = new SimpleValueWrapper(value);
        return valueWrapper;
    }

    public <T> T get(Object o, Class<T> aClass) {
        ValueWrapper valueWrapper = get(o);
        Object obj = valueWrapper.get();
        return aClass.cast(obj);
    }

    public <T> T get(Object o, Callable<T> callable) {
        return null;
    }



    public ValueWrapper putIfAbsent(Object o, Object o1) {
        return null;
    }

    public void evict(Object o) {
        memcachedClient.delete((String)o);
    }

    public void clear() {
        memcachedClient.flush();
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public MemcachedClient getMemcachedClient() {
        return memcachedClient;
    }

    public void setMemcachedClient(MemcachedClient memcachedClient) {
        this.memcachedClient = memcachedClient;
    }
}
