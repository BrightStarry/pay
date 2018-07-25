package com.jia.pay.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jia.pay.dao.entity.Test;
import com.jia.pay.dao.mapper.TestMapper;
import com.jia.pay.service.TestService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author  ZhengXing
 * createTime: 2018/7/4
 * desc:
 */
@Service
@CacheConfig(cacheNames = "test2")
public class TestServiceImpl extends ServiceImpl<TestMapper, Test> implements TestService {

    /**
     * 查询单个
     */
    @Cacheable
    public Test selectOne(Long id) {
        return this.selectById(id);
    }

}
