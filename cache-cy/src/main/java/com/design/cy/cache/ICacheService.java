package com.design.cy.cache;

import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author chenyun
 * @date 2022/6/7 14:45
 */
@Component
 public interface ICacheService {



    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key   缓存的键值
     * @param value 缓存的值
     */
     <T> void setCacheObject(final String key, final T value);

    /**
     * increment
     *
     * @param key   Redis键
     * @param value 值
     */
     Long increment(final String key, final long value);

    /**
     * increment
     *
     * @param key   Redis键
     * @param value 值
     */
     Double increment(final String key, final Double value);

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key      缓存的键值
     * @param value    缓存的值
     * @param timeout  时间
     * @param timeUnit 时间颗粒度
     */
     <T> void setCacheObject(final String key, final T value, final Integer timeout, final TimeUnit timeUnit);

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key   缓存的键值
     * @param value 缓存的值
     */
     <T> void setCacheObjectByDynamic(boolean redisCache, final String key, final T value, final Integer timeout, final TimeUnit timeUnit) ;

    /**
     * 设置有效时间
     *
     * @param key     Redis键
     * @param timeout 超时时间
     * @return true=设置成功；false=设置失败
     */
     boolean expire(final String key, final long timeout);

    /**
     * 设置有效时间
     *
     * @param key     Redis键
     * @param timeout 超时时间
     * @param unit    时间单位
     * @return true=设置成功；false=设置失败
     */
     boolean expire(final String key, final long timeout, final TimeUnit unit) ;
    /**
     * 获得缓存的基本对象。
     *
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */
     <T> T getCacheObject(final String key) ;

    /**
     * 删除单个对象
     *
     * @param key
     */
     boolean deleteObject(final String key);

    /**
     * 删除集合对象
     *
     * @param collection 多个对象
     * @return
     */
     long deleteObject(final Collection collection) ;


     long deleteObject(final String key, final String hKey);


    /**
     * 缓存List数据
     *
     * @param key      缓存的键值
     * @param dataList 待缓存的List数据
     * @return 缓存的对象
     */
     <T> long setCacheList(final String key, final List<T> dataList) ;
    /**
     * 获得缓存的list对象
     *
     * @param key 缓存的键值
     * @return 缓存键值对应的数据
     */
     <T> List<T> getCacheList(final String key) ;

    /**
     * 缓存Set
     *
     * @param key     缓存键值
     * @param dataSet 缓存的数据
     * @return 缓存数据的对象
     */
     <T> BoundSetOperations<String, T> setCacheSet(final String key, final Set<T> dataSet);

    /**
     * 获得缓存的set
     *
     * @param key
     * @return
     */
     <T> Set<T> getCacheSet(final String key);

    /**
     * 缓存Map
     *
     * @param key
     * @param dataMap
     */
     <T> void setCacheMap(final String key, final Map<String, T> dataMap);

    /**
     * 获得缓存的Map
     *
     * @param key
     * @return
     */
     <T> Map<String, T> getCacheMap(final String key) ;
    /**
     * 往Hash中存入数据
     *
     * @param key   Redis键
     * @param hKey  Hash键
     * @param value 值
     */
     <T> void setCacheMapValue(final String key, final String hKey, final T value) ;

    /**
     * 获取Hash中的数据
     *
     * @param key  Redis键
     * @param hKey Hash键
     * @return Hash中的对象
     */
     <T> T getCacheMapValue(final String key, final String hKey);

    /**
     * 获取多个Hash中的数据
     *
     * @param key   Redis键
     * @param hKeys Hash键集合
     * @return Hash对象集合
     */
     <T> List<T> getMultiCacheMapValue(final String key, final Collection<Object> hKeys);

    /**
     * 获得缓存的基本对象列表
     *
     * @param pattern 字符串前缀
     * @return 对象列表
     */
     Collection<String> keys(final String pattern) ;



    /**
     * 增加有序集合
     *
     * @param key
     * @param value
     * @param seqNo
     * @return
     */
     Boolean addZSet(String key, Object value, double seqNo);

    /**
     * 增加有序集合 设置1小时过期时间
     *
     * @param key
     * @param value
     * @param seqNo
     * @return
     */
     Boolean addZSetTimeOut(String key, Object value, double seqNo, final long timeout, final TimeUnit unit);

    /**
     * 获取zset指定范围内的集合
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
     Set<Object> rangeZset(String key, long start, long end) ;


}
