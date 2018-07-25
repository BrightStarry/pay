package com.jia.pay.api.strategy.replacepay;

import com.jia.pay.api.strategy.dto.ReplacePayCommonResponse;
import com.jia.pay.common.enums.PayerEnum;

/**
 * @author  ZhengXing
 * createTime: 2018/6/25
 * desc:  代付策略类
 */
public abstract class AbstractReplacePayStrategy<R> {

    /**
     * 进行请求，获取响应对象
     * @param bankCode 银行代码
     * @param name 名字
     * @param bankCard 银行卡号
     * @param money 金额
     * @param mobile 手机号
     * @param requestId 请求流水号
     */
    public abstract R doRequest(String bankCode,String name,String bankCard,String money,
                                  String mobile,String requestId);

    /**
     * 将响应 转换为 {@link ReplacePayCommonResponse}
     */
    public abstract ReplacePayCommonResponse toCommonResponse(R response);


    /**
     * 获取当前策略所属 支付方
     */
    public abstract PayerEnum getPayer();
}
