package com.jia.pay.service;

import com.jia.pay.common.enums.PayerEnum;
import com.jia.pay.dao.entity.Payer;

/**
 * <p>
 * 支付方 服务类
 * </p>
 *
 * @author zx
 * @since 2018-07-04
 */
public interface PayerService extends SuperService<Payer> {

    boolean verifyByPayerCode(PayerEnum payerEnum);
}
