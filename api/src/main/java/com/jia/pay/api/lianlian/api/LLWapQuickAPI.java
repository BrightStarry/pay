package com.jia.pay.api.lianlian.api;

import com.jia.pay.api.lianlian.dto.LLBaseParam;
import com.jia.pay.api.lianlian.dto.LLBaseResponse;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * @author  ZhengXing
 * createTime: 2018/6/25
 * desc:  wap快捷支付相关
 */
public interface LLWapQuickAPI {

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
    class PayRequest extends LLBaseParam {
        /**
         * 版本号
         */
        private String version = "1.1";


        /**
         * 用户id
         */
        private String user_id;

        /**
         * 请求应用标识
         * see {@link com.jia.pay.api.lianlian.enums.LLRequestAppFlagEnum}
         * 1-Android    2-ios    3-WAP
         */
        private String app_request;

        /**
         * 是否主动同步通知
         * 0-点击通知
         * 1-主动通知
         * 默认为0
         */
        private String syschnotify_flag = "1";

        /**
         * 商户业务类型
         */
        private String busi_partner = "101001";

        /**
         * 商户订单id
         */
        protected String no_order;

        /**
         * 商户订单时间
         * YYYYMMDDH24MISS
         */
        protected String dt_order;

        /**
         * 商品名称
         */
        protected String name_goods;

        /**
         * 交易金额
         * 保留两位小数
         */
        protected String money_order;

        /**
         * 服务器异步通知地址
         */
        protected String notify_url;

        /**
         * 支付结束回显url
         * 非必需
         */
        protected String url_return;

        /**
         * 风控参数 *
         * 如下对象的json string
         * see {@link com.jia.pay.api.lianlian.dto.LLBaseRiskControlParam}
         */
        private String risk_item;
    }

    /**
     * 同步通知
     */
    @EqualsAndHashCode(callSuper = true)
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString(callSuper = true)
    class SyncResponse extends LLBaseParam{
        /**
         * 商户订单id
         */
        protected String no_order;

        /**
         * 商户订单时间
         * YYYYMMDDH24MISS
         */
        protected String dt_order;

        /**
         * 连连支付订单号
         */
        protected String oid_paybill;

        /**
         * 交易金额
         * 保留两位小数
         */
        protected String money_order;

        /**
         * 支付结果
         * SUCCESS 成功支付结果以此为准，商户按此进行后续是否发货操作
         * see {@link com.jia.pay.api.lianlian.enums.LLWebQuickPayResultEnum}
         */
        protected String result_pay;

        /**
         * 清算日期
         * YYYYMMDD   支付成功后会有
         */
        protected String settle_date;
    }

    /**
     * 异步通知
     * 成功才会通知
     * 商户服务端通过读取request原始数据流方式获得通知JSON数据，非POST、GET模式。
     * request.getInputStream();
     */
    @EqualsAndHashCode(callSuper = true)
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString(callSuper = true)
    class AsyncResponse extends SyncResponse {
        /**
         * 订单描述
         */
        protected String info_order;

        /**
         * 支付方式
         * 0：余额支付2：快捷支付（借记卡）3：快捷支付（信用卡）
         *  D：认证支付（借记卡）
         */
        protected String pay_type;

        /**
         * 银行编号
         * 列表见附录，余额支付没有此项
         */
        protected String bank_code;
    }

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
