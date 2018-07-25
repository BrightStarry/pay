package com.jia.pay.api.kjt.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author: ZhengXing
 * @createTime： 2018/7/20
 * @desc：快捷通基础请求参数
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class KJTBaseRequestDTO {

    /**
     * 请求id
     */
    @JsonProperty("request_no")
    protected String requestId;


    /**
     * 接口名
     */
    protected String service;

    /**
     * 版本
     * 1.0
     */
    protected String version = "1.0";

    /**
     * 商户id/平台id
     */
    @JsonProperty("partner_id")
    protected String partnerId;

    /**
     * 编码
     */
    protected String charset = "UTF-8";

    /**
     * 签名
     */
    protected String sign;

    /**
     * 签名加密方式
     */
    @JsonProperty("sign_type")
    protected String signType = "RSA";

    /**
     * 请求时间
     * yyyy-MM-dd HH:mm:ss
     */

}
