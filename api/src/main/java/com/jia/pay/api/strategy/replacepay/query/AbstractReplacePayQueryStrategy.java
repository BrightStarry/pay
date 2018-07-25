package com.jia.pay.api.strategy.replacepay.query;


import com.jia.pay.api.strategy.dto.ReplacePayQueryCommonResponse;
import com.jia.pay.common.enums.PayerEnum;

import java.util.Date;

/**
 * @author  ZhengXing
 * createTime: 2018/6/25
 * desc:  代付查询策略
 *
 */
public abstract class AbstractReplacePayQueryStrategy {

    /**
     * 获取通用查询响应
     * @param requestId 请求id
     * @param requestDate 请求日期
     */
    public abstract ReplacePayQueryCommonResponse doQuery(String requestId, Date requestDate);


    /**
     * 获取当前策略所属 支付方code
     */
    public abstract PayerEnum getPayer();
}
