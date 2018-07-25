package com.jia.pay.api.fuyou.enums;

import com.jia.pay.common.enums.CodeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author  ZhengXing
 * createTime: 2018/5/22
 * desc:  富友状态码
 */
@AllArgsConstructor
@Getter
public enum FuYouErrorCodeEnum implements CodeEnum<String> {
    SUCCESS("0000", "成功"),
    REPLACE_QUERY_SUCCESS("000000", "代付查询接口成功"),
    PHONE_ERROR("100042", "手机号错误 | 手机号不符"),
    CAPTCHA_ERROR("8143", "验证码失效或错误"),

    ;

    private String code;
    private String message;
    }
