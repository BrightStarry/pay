package com.jia.pay.api.strategy.replacepay.query;

import com.jia.pay.api.fuyou.FuYouTemplate;
import com.jia.pay.api.fuyou.api.FuYouReplacePayApi;
import com.jia.pay.api.fuyou.api.FuYouReplacePayQueryApi;
import com.jia.pay.api.strategy.dto.ReplacePayQueryCommonResponse;
import com.jia.pay.common.enums.PayerEnum;
import com.jia.pay.common.exception.PayException;
import com.jia.pay.common.util.EnumUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author  ZhengXing
 * createTime: 2018/6/25
 * desc:  富有 代付查询策略
 * {@link Order} 控制spring对list对象的自动注入的顺序； 0：富有； 1：连连
 */
@Component
@Order(0)
@Slf4j
public class FuYouReplacePayQueryStrategy extends AbstractReplacePayQueryStrategy {

    private final FuYouTemplate fuYouTemplate;

    @Autowired
    public FuYouReplacePayQueryStrategy(FuYouTemplate fuYouTemplate) {
        this.fuYouTemplate = fuYouTemplate;
    }

    @Override
    public ReplacePayQueryCommonResponse doQuery(String requestId, Date requestDate) {
        FuYouReplacePayQueryApi.WrapResponse wrapResponse = fuYouTemplate.replacePayQuery(requestId, requestDate);
        if (wrapResponse == null) {
            throw new PayException("接口调用异常");
        }
        FuYouReplacePayQueryApi.Response body = wrapResponse.getBody();
        if (body == null) {
            throw new PayException("异常:" + wrapResponse.getResponseDesc());
        }

        Boolean flag = null;
        if ((!EnumUtil.equals(body.getTransactionStatusType(), FuYouReplacePayApi.FuYouTransactionStatusTypeEnum.成功)
                && !EnumUtil.equals(body.getTransactionStatusType(), FuYouReplacePayApi.FuYouTransactionStatusTypeEnum.受理成功)
                && !EnumUtil.equals(body.getTransactionStatusType(), FuYouReplacePayApi.FuYouTransactionStatusTypeEnum.银行受理成功)
                && !EnumUtil.equals(body.getTransactionStatusType(), FuYouReplacePayApi.FuYouTransactionStatusTypeEnum.交易结果未知))
                ||
                (EnumUtil.equals(body.getRefund(), FuYouReplacePayQueryApi.FuYouIsRefundEnum.是))
                ||
                (EnumUtil.equals(body.getState(), FuYouReplacePayQueryApi.FuYouTransactionStatusEnum.交易已发送且失败)
                        || EnumUtil.equals(body.getState(), FuYouReplacePayQueryApi.FuYouTransactionStatusEnum.交易已发送且超时))) {
            flag = false;
        }else if (EnumUtil.equals(body.getState(), FuYouReplacePayQueryApi.FuYouTransactionStatusEnum.交易已发送且成功)
                && EnumUtil.equals(body.getRefund(), FuYouReplacePayQueryApi.FuYouIsRefundEnum.否)) {
            flag = true;
        }

        // 构建通用响应
        return new ReplacePayQueryCommonResponse()
                .setCode(body.getState())
                .setMessage(body.getReason())
                .setRawData(wrapResponse)
                .setRequestId(body.getTodayOrderId())
                .setStatus(flag);
    }

    @Override
    public PayerEnum getPayer() {
        return PayerEnum.FUYOU;
    }

}
