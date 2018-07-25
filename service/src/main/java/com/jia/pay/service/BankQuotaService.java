package com.jia.pay.service;

import com.jia.pay.common.enums.PayTypeEnum;
import com.jia.pay.common.enums.PayerEnum;
import com.jia.pay.dao.entity.BankQuota;
import lombok.NonNull;

import java.util.List;

/**
 * <p>
 * 银行限额表 服务类
 * </p>
 *
 * @author zx
 * @since 2018-07-09
 */
public interface BankQuotaService extends SuperService<BankQuota> {


    BankQuota selectOneByPayerAndPayTypeAndCode(PayerEnum payerEnum, PayTypeEnum payTypeEnum, @NonNull String bankCode);

    List<BankQuota> selectByPayerAndPayType(PayerEnum payerEnum, PayTypeEnum payTypeEnum);
}
