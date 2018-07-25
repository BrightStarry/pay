package com.jia.pay.api.lianlian.enums;

import com.jia.pay.common.enums.CodeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author  ZhengXing
 * createTime: 2018/6/21
 * desc:  响应 枚举
 */
@AllArgsConstructor
@Getter
public enum LLCodeEnum implements CodeEnum<String> {

    SUCCESS("0000","成功"),
    CUSTOM_ERROR("1111", "自定义异常"),
    ;
    private String code;
    private String message;
}
