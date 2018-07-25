package com.jia.pay.api.lianlian.api;

import com.jia.pay.api.lianlian.dto.LLBaseResponse;
import com.jia.pay.api.lianlian.dto.LLBaseRiskControlParam;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * @author  ZhengXing
 * createTime: 2018/6/21
 * desc: web网银支付
 */
public interface LLWebBankAPI {
    /**
     * 支付请求
     */
    @EqualsAndHashCode(callSuper = true)
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Accessors(chain = true)
    @ToString(callSuper = true)
     class PayRequest extends LLWebQuickAPI.PayRequest {
        /**
         * 风控参数 *
         * 如下对象的json string
         * see {@link LLBaseRiskControlParam}
         */
        private String risk_item;

        public PayRequest(String user_id, String no_order, String name_goods, String money_order) {
            this.user_id = user_id;
            this.no_order = no_order;
            this.name_goods = name_goods;
            this.money_order = money_order;
        }
    }

    /**
     * 同步通知
     */
    @EqualsAndHashCode(callSuper = true)
    @Data
    @ToString(callSuper = true)
     class SyncResponse extends LLWebQuickAPI.SyncResponse{ }

    /**
     * 异步通知
     * 成功才会通知
     * 商户服务端通过读取request原始数据流方式获得通知JSON数据，非POST、GET模式。
     * request.getInputStream();
     */
    @EqualsAndHashCode(callSuper = true)
    @Data
    @ToString(callSuper = true)
     class AsyncResponse extends LLWebQuickAPI.SyncResponse {}

    /**
     * 异步通知 返回
     * 返回给连连支付
     */
    @EqualsAndHashCode(callSuper = true)
    @Data
    @ToString(callSuper = true)
     class AsyncResponseReturn extends LLBaseResponse { }

    /**
     * 查询结果 请求
     */
    @EqualsAndHashCode(callSuper = true)
    @Data
    @ToString(callSuper = true)
     class QueryRequest extends LLWebQuickAPI.QueryRequest {}

    /**
     * 查询结果 响应
     */
    @EqualsAndHashCode(callSuper = true)
    @Data
    @ToString(callSuper = true)
     class QueryResponse extends LLWebQuickAPI.QueryResponse {}
}
