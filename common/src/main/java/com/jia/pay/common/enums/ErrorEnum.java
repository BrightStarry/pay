package com.jia.pay.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * author:ZhengXing
 * datetime:2017/11/7 0007 16:42
 * 异常状态枚举
 */
@Getter
@AllArgsConstructor
public enum ErrorEnum implements CodeEnum<String> {
    SUCCESS("0000","成功"),
    UNKNOWN_ERROR("0001","未知异常"),
    COMMON_ERROR("0002","通用异常(即自定义异常消息)"),
    NOT_FOUND_ERROR("0003","路径不存在"),

    FORM_ERROR("1001", "参数校验异常"),
    HTTP_ERROR("1002", "http请求异常"),

    INTERNAL_ERROR("9999","内部异常"),//该异常是主动抛出的，用于按正常逻辑绝不可能发生的异常，表示系统内部出现问题
    ;
    private String code;
    private String message;

}
