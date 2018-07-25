package com.jia.pay.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * author:ZhengXing
 * datetime:2017/11/7 0007 16:42
 * 支付方枚举
 */
@Getter
@AllArgsConstructor
public enum PayerEnum implements CodeEnum<String> {
    FUYOU("0001", "富有", 0),
    LIANLIAN("0002", "连连", 1),
    ;
    private String code;
    private String message;
    private Integer sort;

}
