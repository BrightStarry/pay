package com.jia.pay.api.strategy.pay;

import com.jia.pay.api.strategy.dto.PayCallDTO;
import com.jia.pay.common.enums.PayerEnum;
import com.jia.pay.common.form.PayForm;

/**
 * @author  ZhengXing
 * createTime: 2018/7/5
 * desc:  支付策略
 */
public abstract class AbstractPayStrategy {
    /**
     * 获取当前策略所属 支付方
     */
    public abstract PayerEnum getPayer();

    /**
     * wap支付
     */
    public abstract PayCallDTO wapPay(PayForm form, String requestId);

    /**
     * pc快捷支付
     */
    public abstract PayCallDTO pcQuickPay(PayForm form, String requestId);

    /**
     * pc网银支付
     */
    public abstract PayCallDTO pcBankPay(PayForm form, String requestId);

}
