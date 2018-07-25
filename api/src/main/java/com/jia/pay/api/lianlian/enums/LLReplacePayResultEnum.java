package com.jia.pay.api.lianlian.enums;

import com.jia.pay.common.enums.CodeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author  ZhengXing
 * createTime: 2018/6/21
 * desc:  代付结果 枚举
 */
@AllArgsConstructor
@Getter
public enum LLReplacePayResultEnum implements CodeEnum<String> {

    APPLY("APPLY", "付款申请"),
    CHECK("CHECK", "复核申请"),
    SUCCESS("SUCCESS", "付款成功"),
    PROCESSING("PROCESSING", "付款处理中"),
    CANCEL("CANCEL", "付款退款（付款成功后，有可能发生退款）"),
    FAILURE("FAILURE", "付款失败"),
    CLOSED("CLOSED", "付款关闭"),;
    private String code;
    private String message;
}
