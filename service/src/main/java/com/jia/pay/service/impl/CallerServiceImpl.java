package com.jia.pay.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jia.pay.common.exception.PayException;
import com.jia.pay.common.util.TokenUtil;
import com.jia.pay.dao.entity.Caller;
import com.jia.pay.dao.mapper.CallerMapper;
import com.jia.pay.service.CallerService;
import lombok.NonNull;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author  ZhengXing
 * createTime: 2018/7/4
 * desc:
 */
@Service
@CacheConfig(cacheNames = "common")
public class CallerServiceImpl  extends ServiceImpl<CallerMapper, Caller> implements CallerService {

    /**
     * 根据token获取Caller
     */
    @Cacheable
    @Override
    public Caller selectOneByToken(@NonNull String token) {
        Caller caller = selectOne(
                new EntityWrapper<Caller>()
                        .eq("token", token)
        );
        if (caller == null) {
            throw new PayException("token有误");
        }
        return caller;
    }

    /**
     * 根据名字，统计记录数
     */
    @Override
    public int countByName(@NonNull String name) {
        return selectCount(
                new EntityWrapper<Caller>()
                        .eq("name", name)
        );
    }

    /**
     * 新增caller
     */
    @Override
    public Caller insertCaller(@NonNull String name) {
        if (countByName(name) > 0) {
            throw new PayException("名字重复");
        }
        Caller caller = new Caller().setName(name)
                .setToken(TokenUtil.generateToken())
                .setPre(TokenUtil.generateRandom(4));
        insert(caller);
        return caller;
    }
}
