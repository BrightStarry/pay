package com.jia.pay.api.lianlian.enums;

import com.jia.pay.common.enums.CodeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author  ZhengXing
 * createTime: 2018/6/21
 * desc:  web快捷支付结果 枚举
 */
@AllArgsConstructor
@Getter
public enum LLWebQuickPayResultEnum implements CodeEnum<String> {

    SUCCESS("SUCCESS","成功"),
    WAITING("WAITING","等待支付"),
    PROCESSING("PROCESSING","银行支付处理中"),
    REFUND("REFUND","退款"),
    FAILURE("FAILURE","失败"),
    ;
    private String code;
    private String message;
}
