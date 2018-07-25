package com.jia.pay.api.strategy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author  ZhengXing
 * createTime: 2018/6/25
 * desc:  代付查询通用响应对象
 */
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Data
public class ReplacePayQueryCommonResponse {

    /**
     * 原响应对象
     */
    private Object rawData;

    /**
     * 响应码
     */
    private String code;

    /**
     * 响应描述
     */
    private String message;

    /**
     * 请求id
     */
    private String requestId;

    /**
     * 成功/失败/等待中状态
     * true: 成功
     * false: 失败
     * null: 继续等待
     */
    private Boolean status;

}
