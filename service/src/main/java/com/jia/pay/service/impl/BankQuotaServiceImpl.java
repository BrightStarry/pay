package com.jia.pay.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jia.pay.common.enums.ErrorEnum;
import com.jia.pay.common.enums.PayTypeEnum;
import com.jia.pay.common.enums.PayerEnum;
import com.jia.pay.common.exception.PayException;
import com.jia.pay.dao.entity.BankQuota;
import com.jia.pay.dao.mapper.BankQuotaMapper;
import com.jia.pay.service.BankQuotaService;
import lombok.NonNull;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 银行限额表 服务实现类
 * </p>
 *
 * @author zx
 * @since 2018-07-09
 */
@CacheConfig(cacheNames = "common")
@Service
public class BankQuotaServiceImpl extends ServiceImpl<BankQuotaMapper, BankQuota> implements BankQuotaService {

    /**
     * 根据 payer / payType / bankCode 查询记录
     */
    @Override
    public BankQuota selectOneByPayerAndPayTypeAndCode(PayerEnum payerEnum, PayTypeEnum payTypeEnum,@NonNull String bankCode) {
        // 根据支付方 和 支付类型查询
        List<BankQuota> list = selectByPayerAndPayType(payerEnum,payTypeEnum);
        if (CollectionUtils.isEmpty(list)) {
            throw new PayException(ErrorEnum.INTERNAL_ERROR);
        }
        // 查询出对应 code
        for (BankQuota item : list) {
            if(StringUtils.equals(item.getBankCode(),bankCode)){
                return item;
            }
        }
       return null;
    }

    /**
     * 根据 payer / payType  查询记录
     */
    @Cacheable
    @Override
    public List<BankQuota> selectByPayerAndPayType(PayerEnum payerEnum, PayTypeEnum payTypeEnum) {
        // 根据 支付方 查询
        List<BankQuota> list = selectList(
                new EntityWrapper<BankQuota>()
                        .eq("payer_code", payerEnum.getCode())
        );
        if (CollectionUtils.isEmpty(list)) {
            throw new PayException(ErrorEnum.INTERNAL_ERROR);
        }
        // 过滤出对应的 支付类型
        String payType = String.valueOf(payTypeEnum.getCode());
        return list.stream().filter(item -> item.getPayType().contains(payType)).collect(Collectors.toList());
    }
}
