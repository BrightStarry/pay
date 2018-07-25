package com.jia.pay.api.strategy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author  ZhengXing
 * createTime: 2018/7/5
 * desc:  支付调用结果
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class PayCallDTO {

    /**
     * 请求id
     * 用于查账
     */
    private String requestId;

    /**
     * html string
     */
    private String html;


}
