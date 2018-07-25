package com.jia.pay.common.enums;

/**
 * @author  ZhengXing
 * createTime: 2018/7/4
 * desc:  包含code的枚举
 */
public interface CodeEnum<T> {
    T getCode();

    String getMessage();
}
