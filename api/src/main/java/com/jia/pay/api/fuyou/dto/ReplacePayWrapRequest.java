package com.jia.pay.api.fuyou.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author  ZhengXing
 * createTime: 2018/5/21
 * desc: 代付相关接口， 最外层请求对象，包裹 某些api的Request
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class ReplacePayWrapRequest {
    /**
     * 商户id
     */
    private String merid;

    /**
     * 请求类型
     */
    private String reqtype;

    /**
     * 请求参数
     */
    private String xml;

    /**
     * 校验值
     */
    private String mac;
}
