package com.phantom.module.cache.memcached;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author 张志凯 https://github.com/Law-God/phantom-util
 * phantom-module-cache
 * com.phantom.module.cache.memcached.MemcachedCacheTest
 * 2017-04-06 下午 05:29
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-common.xml","classpath:applicationContext-cache.xml"})
public class MemcachedCacheTest {
    @Autowired
    MemcachedCache memcachedCache;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    public void test(){
        memcachedCache.put("str","string1",30);
//        Cache.ValueWrapper wrapper = memcachedCache.get("str1");
//        System.out.println(wrapper.get());
//        System.out.println(memcachedCache.get("str1",String.class));
//        List list = new ArrayList();
//        list.add("a");
//        list.add("b");
//        memcachedCache.put("list",list,10000);
//        List<String> list2 = memcachedCache.get("list",List.class);
//        for(String str : list2){
//            System.out.println(str);
//        }
    }

    @Test
    public void testDataSource() throws SQLException {
        memcachedCache.clear();
        for(int i=0;i<1000;i++){
            memcachedCache.put(i+"",i);
        }
        int count = 0;
        for(int i=0;i<1000;i++){
            Map map = memcachedCache.get("map",Map.class);
            if(map == null){
                count++;
                String sql = "select * from tuser where nid=1";
                map = jdbcTemplate.queryForMap(sql);
                memcachedCache.put("map",map);
            }
        }
        System.out.println(count);
        System.out.println(count/1000.00);
        System.out.println(1.00-count/1000.00);
        double d = (1.00-count/1000.00)*100;
        DecimalFormat format = new DecimalFormat("#,##0.00");
        System.out.println("缓存命中率是" + format.format(d));
    }
}
