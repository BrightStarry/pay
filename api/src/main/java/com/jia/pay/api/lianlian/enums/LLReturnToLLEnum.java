package com.jia.pay.api.lianlian.enums;

import com.jia.pay.common.enums.CodeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author  ZhengXing
 * createTime: 2018/6/21
 * desc:  响应给连连支付 枚举
 */
@AllArgsConstructor
@Getter
public enum LLReturnToLLEnum implements CodeEnum<String> {
    SUCCESS("0000","交易成功"),
    ;

    private String code;
    private String message;
}
