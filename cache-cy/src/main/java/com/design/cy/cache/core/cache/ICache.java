package com.design.cy.cache.core.cache;


import com.design.cy.cache.core.enums.InitStrategy;

public interface ICache<T> {

    /**
     * 缓存初始化策略
     *
     * @return
     */
    default InitStrategy strategy() {
        return InitStrategy.not_exists;
    }

    /**
     * 缓存数据
     *
     * @return
     */
    T cache();

    /**
     * 获取缓存数据
     *
     * @return
     */
    T get();

    /**
     * 删除缓存
     */
    void remove();

    /**
     * 是否存在
     *
     * @return
     */
    boolean exists();

}
