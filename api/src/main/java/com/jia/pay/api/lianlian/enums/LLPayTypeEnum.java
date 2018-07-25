package com.jia.pay.api.lianlian.enums;

import com.jia.pay.common.enums.CodeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author  ZhengXing
 * createTime: 2018/6/21
 * desc:  支付方式
 */
@AllArgsConstructor
@Getter
public enum LLPayTypeEnum implements CodeEnum<String> {

    借记卡("1","网银支付（借记卡）"),
    信用卡("1","网银支付（信用卡）"),
    企业("1","B2B企业网银支付"),
    ;

    private String code;
    private String message;
}
