package com.jia.pay.api.fuyou.enums;

import com.jia.pay.common.enums.CodeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author  ZhengXing
 * createTime: 2018/5/23
 * desc:  富有代付接口 请求类型参数 枚举
 */
@Getter
@AllArgsConstructor
public enum FuYouRequestTypeEnum implements CodeEnum<String> {

    REPLACE_PAY("payforreq", "代付（单笔）接口"),
    REPLACE_PAY_QUERY("qrytransreq", "代付-查询交易结果"),
    ;


    private String code;
    private String message;
}
