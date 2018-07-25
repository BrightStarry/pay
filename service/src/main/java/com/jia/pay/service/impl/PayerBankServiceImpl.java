package com.jia.pay.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jia.pay.common.enums.PayerBankPayerTypeEnum;
import com.jia.pay.common.enums.PayerEnum;
import com.jia.pay.dao.entity.PayerBank;
import com.jia.pay.dao.mapper.PayerBankMapper;
import com.jia.pay.service.PayerBankService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 支付方银行关系表 服务实现类
 * </p>
 *
 * @author zx
 * @since 2018-07-04
 */
@CacheConfig(cacheNames = "common")
@Service
public class PayerBankServiceImpl extends ServiceImpl<PayerBankMapper, PayerBank> implements PayerBankService {

    /**
     * 根据 支付方code/ 银行code/ 支付方类型 查询 对应的银行code
     */
    @Override
    public String selectBankCode(PayerEnum payerEnum, String bankCode, PayerBankPayerTypeEnum payerTypeEnum) {
        // 查询该支付方，指定类型的所有 银行列表
        List<PayerBank> list = selectByCodeAndType(payerEnum, payerTypeEnum);
        for (PayerBank item : list) {
            if (StringUtils.equals(bankCode, item.getBankCode())) {
                return item.getPayerBankCode();
            }
        }
        return null;
    }


    /**
     * 根据支付方code/支付方类型 查询对应的  payerBank列表
     */
    @Cacheable
    @Override
    public List<PayerBank> selectByCodeAndType(PayerEnum payerEnum, PayerBankPayerTypeEnum payerTypeEnum) {
        return selectList(
                new EntityWrapper<PayerBank>()
                        .eq("payer_code", payerEnum.getCode())
                        .eq("payer_type", payerTypeEnum.getCode())
        );
    }



}
