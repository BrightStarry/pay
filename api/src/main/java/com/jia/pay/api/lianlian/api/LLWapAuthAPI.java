package com.jia.pay.api.lianlian.api;

import com.jia.pay.api.lianlian.dto.LLBaseResponse;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * @author  ZhengXing
 * createTime: 2018/7/6
 * desc:  wap认证支付
 */
public interface LLWapAuthAPI {
    /**
     * 包裹请求对象
     */
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    class WrapRequest{
        private String req_data;
    }

    /**
     * 支付请求
     */
    @EqualsAndHashCode(callSuper = true)
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Accessors(chain = true)
    @ToString(callSuper = true)
    class PayRequest extends LLWapQuickAPI.PayRequest {
        /**
         * 证件类型
         * 默认为0    0:身份证
         */
        private String id_type = "0";

        /**
         * 身份证号
         */
        private String id_no;

        /**
         * 姓名
         */
        private String acct_name;

        /**
         * P：新认证支付（全渠道）
         */
        private String pay_type = "P";
    }

    /**
     * 同步通知
     */
    @EqualsAndHashCode(callSuper = true)
    @Data
    @ToString(callSuper = true)
    class SyncResponse extends LLWapQuickAPI.SyncResponse {}

    /**
     * 异步通知
     * 成功才会通知
     * 商户服务端通过读取request原始数据流方式获得通知JSON数据，非POST、GET模式。
     * request.getInputStream();
     */
    @EqualsAndHashCode(callSuper = true)
    @Data
    @ToString(callSuper = true)
    class AsyncResponse extends LLWapQuickAPI.AsyncResponse {}


    /**
     * 异步通知 返回
     * 返回给连连支付
     */
    @EqualsAndHashCode(callSuper = true)
    @Data
    @ToString(callSuper = true)
    class AsyncResponseReturn extends LLBaseResponse {
    }
}
