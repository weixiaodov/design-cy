package com.design.cy.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author chenyun
 * @date 2022/6/7 14:45
 */
@Slf4j
@Component
public class CacheService implements ICacheService {

    @Resource
    public RedisTemplate redisTemplate;

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key   缓存的键值
     * @param value 缓存的值
     */
    @Override
    public <T> void setCacheObject(final String key, final T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * increment
     *
     * @param key   Redis键
     * @param value 值
     */
    @Override
    public Long increment(final String key, final long value) {
        return redisTemplate.opsForValue().increment(key, value);
    }

    /**
     * increment
     *
     * @param key   Redis键
     * @param value 值
     */
    @Override
    public Double increment(final String key, final Double value) {
        return redisTemplate.opsForValue().increment(key, value);
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key      缓存的键值
     * @param value    缓存的值
     * @param timeout  时间
     * @param timeUnit 时间颗粒度
     */
    @Override
    public <T> void setCacheObject(final String key, final T value, final Integer timeout, final TimeUnit timeUnit) {
        try {
            redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
        } catch (Exception e) {
            log.error("setCacheObject case an error,", e);
        }
    }

    @Override
    public <T> void setCacheObjectByDynamic(boolean redisCache, String key, T value, Integer timeout, TimeUnit timeUnit) {

    }

    /**
     * 设置有效时间
     *
     * @param key     Redis键
     * @param timeout 超时时间
     * @return true=设置成功；false=设置失败
     */
    @Override
    public boolean expire(final String key, final long timeout) {
        return expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置有效时间
     *
     * @param key     Redis键
     * @param timeout 超时时间
     * @param unit    时间单位
     * @return true=设置成功；false=设置失败
     */
    @Override
    public boolean expire(final String key, final long timeout, final TimeUnit unit) {
        return redisTemplate.expire(key, timeout, unit);
    }

    /**
     * 获得缓存的基本对象。
     *
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */
    @Override
    public <T> T getCacheObject(final String key) {
        try {
            ValueOperations<String, T> operation = redisTemplate.opsForValue();
            return operation.get(key);
        } catch (Exception e) {
            log.error("getCacheObject case an error,", e);
        }
        return null;
    }

    /**
     * 删除单个对象
     *
     * @param key
     */
    @Override
    public boolean deleteObject(final String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 删除集合对象
     *
     * @param collection 多个对象
     * @return
     */
    @Override
    public long deleteObject(final Collection collection) {
        return redisTemplate.delete(collection);
    }


    @Override
    public long deleteObject(final String key, final String hKey) {
        return redisTemplate.opsForHash().delete(key, hKey);
    }


    /**
     * 缓存List数据
     *
     * @param key      缓存的键值
     * @param dataList 待缓存的List数据
     * @return 缓存的对象
     */
    @Override
    public <T> long setCacheList(final String key, final List<T> dataList) {
        Long count = redisTemplate.opsForList().rightPushAll(key, dataList);
        return count == null ? 0 : count;
    }

    /**
     * 获得缓存的list对象
     *
     * @param key 缓存的键值
     * @return 缓存键值对应的数据
     */
    @Override
    public <T> List<T> getCacheList(final String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    /**
     * 缓存Set
     *
     * @param key     缓存键值
     * @param dataSet 缓存的数据
     * @return 缓存数据的对象
     */
    @Override
    public <T> BoundSetOperations<String, T> setCacheSet(final String key, final Set<T> dataSet) {
        BoundSetOperations<String, T> setOperation = redisTemplate.boundSetOps(key);
        Iterator<T> it = dataSet.iterator();
        while (it.hasNext()) {
            setOperation.add(it.next());
        }
        return setOperation;
    }

    /**
     * 获得缓存的set
     *
     * @param key
     * @return
     */
    @Override
    public <T> Set<T> getCacheSet(final String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 缓存Map
     *
     * @param key
     * @param dataMap
     */
    @Override
    public <T> void setCacheMap(final String key, final Map<String, T> dataMap) {
        if (dataMap != null) {
            redisTemplate.opsForHash().putAll(key, dataMap);
        }
    }

    /**
     * 获得缓存的Map
     *
     * @param key
     * @return
     */
    @Override
    public <T> Map<String, T> getCacheMap(final String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 往Hash中存入数据
     *
     * @param key   Redis键
     * @param hKey  Hash键
     * @param value 值
     */
    @Override
    public <T> void setCacheMapValue(final String key, final String hKey, final T value) {
        redisTemplate.opsForHash().put(key, hKey, value);
    }

    /**
     * 获取Hash中的数据
     *
     * @param key  Redis键
     * @param hKey Hash键
     * @return Hash中的对象
     */
    @Override
    public <T> T getCacheMapValue(final String key, final String hKey) {
        HashOperations<String, String, T> opsForHash = redisTemplate.opsForHash();
        return opsForHash.get(key, hKey);
    }

    /**
     * 获取多个Hash中的数据
     *
     * @param key   Redis键
     * @param hKeys Hash键集合
     * @return Hash对象集合
     */
    @Override
    public <T> List<T> getMultiCacheMapValue(final String key, final Collection<Object> hKeys) {
        return redisTemplate.opsForHash().multiGet(key, hKeys);
    }

    /**
     * 获得缓存的基本对象列表
     *
     * @param pattern 字符串前缀
     * @return 对象列表
     */
    @Override
    public Collection<String> keys(final String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * 增加有序集合
     *
     * @param key
     * @param value
     * @param seqNo
     * @return
     */
    @Override
    public Boolean addZSet(String key, Object value, double seqNo) {
        try {
            return redisTemplate.opsForZSet().add(key, value, seqNo);
        } catch (Exception e) {
            log.error("[RedisUtils.addZset] [error]", e);
            return false;
        }
    }


    /**
     * 增加有序集合 设置1小时过期时间
     *
     * @param key
     * @param value
     * @param seqNo
     * @return
     */
    @Override
    public Boolean addZSetTimeOut(String key, Object value, double seqNo, final long timeout, final TimeUnit unit) {
        try {
            ZSetOperations zSetOperations = redisTemplate.opsForZSet();
            Boolean add = zSetOperations.add(key, value, seqNo);
            Long expire = redisTemplate.getExpire(key);
            if (null == expire || expire.equals(-1L)) {
                redisTemplate.expire(key, timeout, unit);
            }
            return add;
        } catch (Exception e) {
            log.error("[RedisUtils.addZset] [error]", e);
            return false;
        }
    }

    /**
     * 获取zset指定范围内的集合
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    @Override
    public Set<Object> rangeZset(String key, long start, long end) {
        try {
            return redisTemplate.opsForZSet().range(key, start, end);
        } catch (Exception e) {
            log.error("[RedisUtils.rangeZset] [error] [key is {},start is {},end is {}]", key, start, end, e);
            return null;
        }
    }

}
