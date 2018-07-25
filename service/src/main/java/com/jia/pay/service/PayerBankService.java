package com.jia.pay.service;

import com.baomidou.mybatisplus.service.IService;
import com.jia.pay.common.enums.PayerBankPayerTypeEnum;
import com.jia.pay.common.enums.PayerEnum;
import com.jia.pay.dao.entity.PayerBank;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * <p>
 * 支付方银行关系表 服务类
 * </p>
 *
 * @author zx
 * @since 2018-07-04
 */
public interface PayerBankService extends IService<PayerBank> {

    /**
     * 根据 支付方code/ 银行code/ 支付方类型 查询 对应的银行code
     */
    String selectBankCode(PayerEnum payerEnum, String bankCode, PayerBankPayerTypeEnum payerBankPayerTypeEnum);

    @Cacheable
    List<PayerBank> selectByCodeAndType(PayerEnum payerEnum, PayerBankPayerTypeEnum payerTypeEnum);
}
