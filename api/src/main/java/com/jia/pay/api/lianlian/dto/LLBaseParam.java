package com.jia.pay.api.lianlian.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author  ZhengXing
 * createTime: 2018/6/21
 * desc:  基础参数
 */
@Data
@Accessors(chain = true)
public abstract class LLBaseParam {

    /**
     * 商户编号
     */
    protected String oid_partner;


    /**
     * 签名方式
     */
    protected String sign_type = "RSA";

    /**
     * 签名
     */
    protected String sign;
}
