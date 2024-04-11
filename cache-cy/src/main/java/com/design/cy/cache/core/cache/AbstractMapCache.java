package com.design.cy.cache.core.cache;


import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;

public abstract class AbstractMapCache<T> extends AbstractCache<Map<String, T>> {

    @Resource
    private RedisTemplate redisTemplate;

    protected String mainKey() {
        return this.getClass().getName();
    }

    @Override
    public Map<String, T> cache() {
        Map<String, T> data = null;
        if (cacheable()) {
            data = load();
            if (!data.isEmpty()) {
                redisTemplate.opsForHash().putAll(mainKey(), data);
            }
        }
        return data;
    }

    public T cache(String key) {
        T data = null;
        if (cacheable()) {
            if (exists()) {
                data = load(key);
                if (Objects.nonNull(data)) {
                    redisTemplate.opsForHash().put(mainKey(), key, data);
                }
            } else {
                Map<String, T> dataMap = cache();
                return dataMap.get(key);
            }
        }
        return data;
    }

    /**
     * load
     *
     * @param key
     * @return
     */
    protected abstract T load(String key);

    /**
     * load
     *
     * @return
     */
    protected abstract Map<String, T> load();

    @Override
    public Map<String, T> get() {
        if (this.exists()) {
            return redisTemplate.opsForHash().entries(mainKey());
        }
        return cache();
    }

    public T get(String key) {
        if (this.exists(key)) {
            HashOperations<String, String, T> opsForHash = redisTemplate.opsForHash();
            return opsForHash.get(mainKey(), key);
        } else {
            return cache(key);
        }
    }

    @Override
    public boolean exists() {
        return redisTemplate.hasKey(mainKey());
    }

    public boolean exists(String key) {
        return redisTemplate.opsForHash().hasKey(mainKey(), key);
    }

    @Override
    public void remove() {
        redisTemplate.delete(mainKey());
    }

    public void remove(String key) {
        redisTemplate.opsForHash().delete(mainKey(), key);
    }

}
