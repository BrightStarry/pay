package com.jia.pay.dao.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * <p>
 * 调用表
 * </p>
 *
 * @author zx
 * @since 2018-07-05
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(callSuper = true)
@Accessors(chain = true)
public class Request extends SuperEntity<Request> {


    /**
     * 请求id,自身生成
     */
    @TableField("request_id")
    private String requestId;
    /**
     * 支付方响应的id(仅作记录)
     */
    @TableField("payer_request_id")
    private String payerRequestId;
    /**
     * 调用者id
     */
    @TableField("caller_id")
    private Long callerId;
    /**
     * 关联id,调用者传入
     */
    @TableField("relate_id")
    private String relateId;
    /**
     * 支付方code
     */
    @TableField("payer_code")
    private String payerCode;
    /**
     * 支付类型 0:wap支付; 1:pc快捷; 2:pc网银; 3:代付
     */
    @TableField("pay_type")
    private Integer payType;
    /**
     * 请求信息记录
     */
    @TableField("request_body")
    private String requestBody;
    /**
     * 状态 0:等待中; 1:待验证; 2:成功; 3:失败;
     */
    private Integer status;
    /**
     * 同步响应
     */
    @TableField("sync_response")
    private String syncResponse;
    /**
     * 异步响应
     */
    @TableField("async_response")
    private String asyncResponse;
    /**
     * 响应消息(异常消息)
     */
    @TableField("response_message")
    private String responseMessage;
    /**
     * 真正请求时间
     */
    @TableField("request_time")
    private Date requestTime;

    /**
     * 异步响应时间
     */
    @TableField("async_response_time")
    private Date asyncResponseTime;

}
