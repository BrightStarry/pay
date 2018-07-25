package com.jia.pay.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jia.pay.dao.entity.Bank;
import com.jia.pay.dao.mapper.BankMapper;
import com.jia.pay.service.BankService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 银行 服务实现类
 * </p>
 *
 * @author zx
 * @since 2018-07-04
 */
@CacheConfig(cacheNames = "common")
@Service
public class BankServiceImpl extends ServiceImpl<BankMapper, Bank> implements BankService {

}
