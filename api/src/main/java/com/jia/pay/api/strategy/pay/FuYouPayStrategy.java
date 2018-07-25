package com.jia.pay.api.strategy.pay;

import com.jia.pay.api.fuyou.FuYouTemplate;
import com.jia.pay.api.strategy.dto.PayCallDTO;
import com.jia.pay.common.enums.PayerEnum;
import com.jia.pay.common.form.PayForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author  ZhengXing
 * createTime: 2018/7/5
 * desc:  富有支付策略
 */
@Component
@Order(0)
@Slf4j
public class FuYouPayStrategy extends AbstractPayStrategy{

    private final FuYouTemplate fuYouTemplate;

    public FuYouPayStrategy(FuYouTemplate fuYouTemplate) {
        this.fuYouTemplate = fuYouTemplate;
    }

    @Override
    public PayerEnum getPayer() {
        return PayerEnum.FUYOU;
    }

    @Override
    public PayCallDTO wapPay(PayForm form, String requestId) {
        String html = fuYouTemplate.wapPay(requestId, form.getUserId(),form.getMoney(),
                form.getBankCard(), form.getName(), form.getIdCard(), null);
        return new PayCallDTO(requestId,html);
    }

    @Override
    public PayCallDTO pcQuickPay(PayForm form, String requestId) {
        String html = fuYouTemplate.pcQuickPayRequest(requestId,form.getUserId(), form.getMoney(),
                form.getBankCard(), form.getName(), form.getIdCard(), null);
        return new PayCallDTO(requestId,html);
    }

    @Override
    public PayCallDTO pcBankPay(PayForm form, String requestId) {
        String html = fuYouTemplate.pcWebBankPay(requestId, form.getMoney(), form.getPayerBankCode());
        return  new PayCallDTO(requestId,html);
    }
}
