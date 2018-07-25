package com.jia.pay.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author  ZhengXing
 * createTime: 2018/7/4
 * desc:  支付方 类型 用于 payerBank表
 */
@AllArgsConstructor
@Getter
public enum PayerBankPayerTypeEnum implements CodeEnum<Integer> {
    DEFAULT(0, 0, "默认"),
    FUYOU_PC_BANK_PAY(1, 1, "富有网银支付(当支付方为富有时)"),
    ;
    private Integer value;
    private Integer code;
    private String Message;
}
