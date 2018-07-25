package com.jia.pay.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author  ZhengXing
 * createTime: 2018/7/4
 * desc:  删除标记
 */
@AllArgsConstructor
@Getter
public enum DeleteFlagEnum implements CodeEnum<Integer>{
    NOT_DELETE(0, 0, "未删除"),
    DELETED(1,1,"删除")
    ;
    private Integer value;
    private Integer code;
    private String Message;
}
