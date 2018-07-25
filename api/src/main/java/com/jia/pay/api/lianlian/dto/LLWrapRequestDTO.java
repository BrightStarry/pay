package com.jia.pay.api.lianlian.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author  ZhengXing
 * createTime: 2018/6/21
 * desc:  包裹一些接口请求
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class LLWrapRequestDTO {

    /**
     * 对PayRequest加密后的字符串
     */
    private String pay_load;

    /**
     * 商户编号
     */
    protected String oid_partner;
}
