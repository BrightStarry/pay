package com.jia.pay.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author  ZhengXing
 * createTime: 2018/7/4
 * desc:  支付状态
 */
@AllArgsConstructor
@Getter
public enum PayStatusEnum implements CodeEnum<Integer> {
    WAIT(0, 0, "等待中"),
    WAIT_VERIFY(1, 1, "待验证"),
    SUCCESS(2, 2, "成功"),
    FAIL(3, 3, "失败"),

    ;
    private Integer value;
    private Integer code;
    private String Message;
}
