package com.design.cy.cache.core.cache;

import com.design.cy.cache.core.enums.InitStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

@Slf4j
public abstract class AbstractCache<T> implements ICache<T>, ApplicationRunner, DisposableBean {

    @Resource
    private RedisTemplate redisTemplate;

    protected String mainKey() {
        return this.getClass().getName();
    }

    public boolean cacheable() {
        InitStrategy strategy = strategy();
        switch (strategy) {
            case always:
                return true;
            case never:
                return false;
            default:
                return !redisTemplate.hasKey(mainKey());
        }
    }

    protected abstract T load();

    @Override
    public void run(ApplicationArguments args) {
        this.cache();
    }

    @Override
    public void destroy() {
        remove();
    }

}
