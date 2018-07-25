package com.jia.pay.api.strategy.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jia.pay.common.enums.PayerEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author  ZhengXing
 * createTime: 2018/6/25
 * desc: 代付通用响应
 */
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Data
public class ReplacePayCommonResponse {

    /**
     * 是否成功
     */
    private Boolean isSuccess;

    /**
     * 响应码
     */
    private String code;

    /**
     * 成功或失败消息
     */
    private String message;

    /**
     * 原响应对象
     */
    @JsonIgnore
    private Object rawData;

    /**
     * 支付调用方
     */
    private PayerEnum payer;


    public ReplacePayCommonResponse(PayerEnum payer) {
        this.payer = payer;
    }
}
