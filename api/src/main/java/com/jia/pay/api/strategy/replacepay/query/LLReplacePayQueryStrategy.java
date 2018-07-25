package com.jia.pay.api.strategy.replacepay.query;

import com.jia.pay.api.lianlian.LLTemplate;
import com.jia.pay.api.lianlian.api.LLReplacePayAPI;
import com.jia.pay.api.lianlian.enums.LLReplacePayResultEnum;
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
 * desc:  连连 代付查询策略
 */
@Component
@Order(1)
@Slf4j
public class LLReplacePayQueryStrategy extends AbstractReplacePayQueryStrategy {

    private final LLTemplate llTemplate;

    @Autowired
    public LLReplacePayQueryStrategy(LLTemplate llTemplate) {
        this.llTemplate = llTemplate;
    }

    @Override
    public ReplacePayQueryCommonResponse doQuery(String requestId,  Date requestDate) {
        LLReplacePayAPI.QueryResponse response = llTemplate.replacePayQuery(requestId);
        if (response == null) {
            throw new PayException("接口调用异常");
        }
        LLReplacePayResultEnum resultEnum = EnumUtil.getByCodeIgnoreCase(response.getResult_pay(), LLReplacePayResultEnum.class)
                .orElseThrow(() -> new PayException("响应未知结果"));
        Boolean flag = null;
        if (LLReplacePayResultEnum.SUCCESS.equals(resultEnum)) {
            flag = true;
        } else if (LLReplacePayResultEnum.CANCEL.equals(resultEnum) ||
                LLReplacePayResultEnum.FAILURE.equals(resultEnum) ||
                LLReplacePayResultEnum.CLOSED.equals(resultEnum)) {
            flag = false;
        }

        // 构建通用响应
        return new ReplacePayQueryCommonResponse()
                .setCode(resultEnum.getCode())
                .setMessage(resultEnum.getMessage())
                .setRawData(response)
                .setRequestId(response.getNo_order())
                .setStatus(flag);
    }

    @Override
    public PayerEnum getPayer() {
        return PayerEnum.LIANLIAN;
    }
}
