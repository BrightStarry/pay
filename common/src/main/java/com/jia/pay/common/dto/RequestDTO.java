package com.jia.pay.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author  ZhengXing
 * createTime: 2018/7/6
 * desc:  请求结果
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class RequestDTO {
    /**
     * 请求id
     */
    private String requestId;

    /**
     * 关联id
     */
    private String relateId;

    /**
     * 状态
     * 0:等待中; 1:待验证; 2:成功; 3:失败;
     */
    private Integer status;

    /**
     * 异常消息
     */
    private String message;
}
