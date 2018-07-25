package com.jia.pay.business;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

/**
 * @author  ZhengXing
 * createTime: 2018/7/6
 * desc:  系统
 */
@Service
@Slf4j
public class SystemService {

    private final CacheManager cacheManager;

    public SystemService(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }


    /**
     * 删除整个缓存 ，例如 common
     */
    public void deleteCache(@NonNull String cacheName) {
        cacheManager.getCache(cacheName).clear();
        log.info("清空整个缓存成功:{}",cacheName);
    }

    /**
     * 删除指定key
     *
     * see {@link org.springframework.data.redis.cache.RedisCache#getRedisCacheKey(Object)} 调用了usePrefix
     * 注意，该key不增加cacheName为前缀
     * 例如key  "common:PayerBankServiceImpl-selectBankCode-FUYOU0001DEFAULT" 需要变为
     * PayerBankServiceImpl-selectBankCode-FUYOU0001DEFAULT
     */
    public void deleteCacheKey(@NonNull String cacheName,@NonNull String key) {
        cacheManager.getCache(cacheName).evict(key);
        log.info("清空单个key成功:{}:{}",cacheName,key);
    }


}
