package com.jia.pay.api.strategy.pay;

import com.jia.pay.api.lianlian.LLTemplate;
import com.jia.pay.api.lianlian.dto.LLBaseRiskControlParam;
import com.jia.pay.api.lianlian.dto.LLRealNameRiskControlParam;
import com.jia.pay.api.lianlian.enums.LLRequestAppFlagEnum;
import com.jia.pay.api.strategy.dto.PayCallDTO;
import com.jia.pay.common.enums.PayerEnum;
import com.jia.pay.common.form.PayForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author  ZhengXing
 * createTime: 2018/7/5
 * desc:  连连支付策略
 */
@Component
@Order(1)
@Slf4j
public class LLPayStrategy extends AbstractPayStrategy{

    private final LLTemplate llTemplate;

    public LLPayStrategy(LLTemplate llTemplate) {
        this.llTemplate = llTemplate;
    }


    @Override
    public PayerEnum getPayer() {
        return PayerEnum.LIANLIAN;
    }

    @Override
    public PayCallDTO wapPay(PayForm form, String requestId) {
        LLRealNameRiskControlParam realNameRiskControlParam = llTemplate.buildRiskControlParam(form.getUserId(), form.getPhone(),
                form.getName(), form.getIdCard());
        String html = llTemplate.wapAuthPay(form.getUserId(), requestId, form.getOrderTime(),
                form.getMoney(), LLRequestAppFlagEnum.WAP, realNameRiskControlParam);
        return new PayCallDTO(requestId, html);
    }

    @Override
    public PayCallDTO pcQuickPay(PayForm form, String requestId) {
        return pcBankAndQuickPay(form,requestId,null);
    }

    @Override
    public PayCallDTO pcBankPay(PayForm form, String requestId) {
        return pcBankAndQuickPay(form,requestId,form.getPayerBankCode());
    }

    /**
     * 连连支付两种支付方法是一个接口
     * @param form
     * @param bankCode 网银支付需要传入
     * @return
     */
    private PayCallDTO pcBankAndQuickPay(PayForm form, String requestId, String bankCode) {
        LLBaseRiskControlParam llBaseRiskControlParam = llTemplate.buildRiskControlParam(form.getUserId(), form.getPhone(),
                form.getName(), form.getIdCard());
        String html = llTemplate.webPay(form.getUserId(), requestId, form.getOrderTime(), form.getMoney(), llBaseRiskControlParam, bankCode);
        return new PayCallDTO(requestId, html);
    }
}
