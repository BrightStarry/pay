package com.jia.pay.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jia.pay.common.enums.PayerEnum;
import com.jia.pay.common.enums.PayerStatusEnum;
import com.jia.pay.common.util.EnumUtil;
import com.jia.pay.dao.entity.Payer;
import com.jia.pay.dao.mapper.PayerMapper;
import com.jia.pay.service.PayerService;
import lombok.NonNull;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 支付方 服务实现类
 * </p>
 *
 * @author zx
 * @since 2018-07-04
 */
@CacheConfig(cacheNames = "common")
@Service
public class PayerServiceImpl extends ServiceImpl<PayerMapper, Payer> implements PayerService {
    /**
     * 校验支付方是否可用 根据 PayerEnum
     */
    @Cacheable
    @Override
    public boolean verifyByPayerCode(@NonNull PayerEnum payerEnum) {
        Payer payer = selectOne(
                new EntityWrapper<Payer>()
                        .eq("code", payerEnum.getCode())
        );
        return EnumUtil.equals(payer.getStatus(), PayerStatusEnum.ENABLED);
    }
}
