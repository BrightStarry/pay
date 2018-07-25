package com.jia.pay.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author  ZhengXing
 * createTime: 2018/7/6
 * desc:  代付响应
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ReplacePayDTO {
    /**
     * 是否成功
     */
    private Boolean success;

    /**
     * 响应码
     */
    private String code;

    /**
     * 成功或失败消息
     */
    private String message;

    /**
     * 请求id
     */
    private String requestId;
}
