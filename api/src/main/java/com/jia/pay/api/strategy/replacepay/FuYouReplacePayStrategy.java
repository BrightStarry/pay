package com.jia.pay.api.strategy.replacepay;

import com.jia.pay.api.fuyou.FuYouTemplate;
import com.jia.pay.api.fuyou.api.FuYouReplacePayApi;
import com.jia.pay.api.fuyou.dto.ReplacePayWrapRequest;
import com.jia.pay.api.strategy.dto.ReplacePayCommonResponse;
import com.jia.pay.common.enums.PayerEnum;
import com.jia.pay.common.exception.HttpException;
import com.jia.pay.common.util.DateUtil;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author  ZhengXing
 * createTime: 2018/6/25
 * desc:  富有代付策略
 */
@Component
@Order(0)
public class FuYouReplacePayStrategy extends AbstractReplacePayStrategy<FuYouReplacePayApi.SyncResponse> {

    private final FuYouTemplate fuYouTemplate;

    public FuYouReplacePayStrategy(FuYouTemplate fuYouTemplate) {
        this.fuYouTemplate = fuYouTemplate;
    }

    @Override
    public FuYouReplacePayApi.SyncResponse doRequest(String bankCode, String name, String bankCard, String money,
                                                     String mobile, String requestId) {
        ReplacePayWrapRequest request = fuYouTemplate.buildReplacePayRequest(bankCode, name, bankCard,
                money, requestId, mobile, requestId, DateUtil.getYYYYMMDDTodayDate());
        FuYouReplacePayApi.SyncResponse syncResponse = fuYouTemplate.replacePay(request);
        if (syncResponse == null) {
            throw new HttpException("富有代付接口请求异常");
        }
        return syncResponse;
    }

    @Override
    public ReplacePayCommonResponse toCommonResponse(FuYouReplacePayApi.SyncResponse response) {
        ReplacePayCommonResponse commonResponse = new ReplacePayCommonResponse(PayerEnum.FUYOU);
        commonResponse.setRawData(response);
        if (!FuYouReplacePayApi.FuYouTransactionStatusTypeEnum.成功.equals(response.getTransactionStatusTypeEnum()) &&
                !FuYouReplacePayApi.FuYouTransactionStatusTypeEnum.受理成功.equals(response.getTransactionStatusTypeEnum()) &&
                !FuYouReplacePayApi.FuYouTransactionStatusTypeEnum.交易结果未知.equals(response.getTransactionStatusTypeEnum())){
            commonResponse.setIsSuccess(false);
        }else{
            commonResponse.setIsSuccess(true);
        }
        commonResponse.setCode(response.getTransactionStatus());
        commonResponse.setMessage(response.getTransactionStatusTypeEnum().getMessage());
        return commonResponse;
    }

    @Override
    public PayerEnum getPayer() {
        return PayerEnum.FUYOU;
    }
}
