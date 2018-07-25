package com.jia.pay.api.lianlian.enums;

import com.jia.pay.common.enums.CodeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author  ZhengXing
 * createTime: 2018/6/25
 * desc:  请求应用标记
 */
@AllArgsConstructor
@Getter
public enum LLRequestAppFlagEnum implements CodeEnum<String> {


    ANDROID("1", "android"),
    IOS("2", "ios"),
    WAP("3", "wap"),

    ;

    private String code;
    private String message;
}
