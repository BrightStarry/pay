package com.jia.pay.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author  ZhengXing
 * createTime: 2018/7/4
 * desc:  支付方 状态
 */
@AllArgsConstructor
@Getter
public enum PayerStatusEnum implements CodeEnum<Integer>{
    DISABLED(0, 0, "禁用"),
    ENABLED(1, 1, "启用"),

    ;
    private Integer value;
    private Integer code;
    private String Message;
}
