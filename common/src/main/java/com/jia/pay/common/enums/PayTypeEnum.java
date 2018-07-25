package com.jia.pay.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author  ZhengXing
 * createTime: 2018/7/4
 * desc:  支付类型
 */
@AllArgsConstructor
@Getter
public enum PayTypeEnum implements CodeEnum<Integer> {
    WAP_PAY(0, 0, "WAP支付"),
    PC_QUICK_PAY(1, 1, "PC快捷支付"),
    PC_BANK_PAY(2, 2, "PC网银支付"),

    REPLACE_PAY(3, 3, "代付"),

    ;
    private Integer value;
    private Integer code;
    private String Message;
}
