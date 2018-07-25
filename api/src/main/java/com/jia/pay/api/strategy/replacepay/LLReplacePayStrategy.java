package com.jia.pay.api.strategy.replacepay;

import com.jia.pay.api.lianlian.LLTemplate;
import com.jia.pay.api.lianlian.api.LLReplacePayAPI;
import com.jia.pay.api.lianlian.enums.LLCodeEnum;
import com.jia.pay.api.strategy.dto.ReplacePayCommonResponse;
import com.jia.pay.common.enums.PayerEnum;
import com.jia.pay.common.exception.HttpException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author  ZhengXing
 * createTime: 2018/6/25
 * desc:  连连代付策略
 */
@Component
@Order(1)
public class LLReplacePayStrategy extends AbstractReplacePayStrategy<LLReplacePayAPI.PayResponse> {

    private final LLTemplate llTemplate;

    public LLReplacePayStrategy(LLTemplate llTemplate) {
        this.llTemplate = llTemplate;
    }

    @Override
    public LLReplacePayAPI.PayResponse doRequest(String bankCode,String name,String bankCard,String money,
                                                 String mobile,String requestId) {
        LLReplacePayAPI.PayRequest request = llTemplate.buildReplacePayRequest(requestId, money, bankCard, name);
        LLReplacePayAPI.PayResponse payResponse = llTemplate.replacePay(request);
        if (payResponse == null) {
            throw new HttpException("连连代付接口请求异常");
        }
        // 验证码为空则直接返回
        if (StringUtils.isBlank(payResponse.getConfirm_code())) {
            return payResponse;
        }
        // 处理 确认订单情况
        LLReplacePayAPI.ConfirmPayResponse confirmPayResponse =
                llTemplate.confirmReplacePay(request.getNo_order(), payResponse.getConfirm_code());
        if (confirmPayResponse == null){
            throw new HttpException("连连代付确认接口请求异常");
        }
        payResponse.setOid_paybill(confirmPayResponse.getOid_paybill())
                .setRet_code(confirmPayResponse.getRet_code())
                .setRet_msg(confirmPayResponse.getRet_msg());
        return payResponse;
    }

    @Override
    public ReplacePayCommonResponse toCommonResponse(LLReplacePayAPI.PayResponse response) {
        ReplacePayCommonResponse commonResponse = new ReplacePayCommonResponse(PayerEnum.LIANLIAN);
        commonResponse.setRawData(response);
        if (!LLCodeEnum.SUCCESS.getCode().equals(response.getRet_code())){
            commonResponse.setIsSuccess(false);
        }else{
            commonResponse.setIsSuccess(true);
        }
        commonResponse.setCode(response.getRet_code());
        commonResponse.setMessage(response.getRet_msg());
        return commonResponse;
    }

    @Override
    public PayerEnum getPayer() {
        return PayerEnum.LIANLIAN;
    }
}
