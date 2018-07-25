package com.jia.pay.api.lianlian.api;

import com.jia.pay.api.lianlian.dto.LLBaseParam;
import com.jia.pay.api.lianlian.dto.LLBaseResponse;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * @author  ZhengXing
 * createTime: 2018/6/21
 * desc:  连连 web快捷支付
 */
public interface LLWebQuickAPI {


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
        protected String version = "1.0";

        /**
         * 商户 用户id
         */
        protected String user_id;

        /**
         * 时间戳
         * YYYYMMDDH24MISS
         */
        protected String timestamp;


        /**
         * 商户业务类型
         * 虚拟商品销售：101001实物商品销售：109001外部账户充值：108001
         */
        protected String busi_partner = "101001";

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
         * 支付方式
         * 2：快捷支付（借记卡）3：快捷支付（信用卡）
         * 1：网银支付（借记卡）8：网银支付（信用卡） 网银支付必传
         */
        private String pay_type;

        /**
         * 银行编号
         */
        private String bank_code;



    }

    /**
     * 同步通知
     */
    @EqualsAndHashCode(callSuper = true)
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString(callSuper = true)
    class SyncResponse extends LLWapQuickAPI.SyncResponse{


        /**
         * 订单描述
         */
        protected String info_order;

        /**
         * 支付方式
         * 0：余额支付2：快捷支付（借记卡）3：快捷支付（信用卡）
         */
        protected String pay_type;

        /**
         * 银行编号
         * 列表见附录，余额支付没有此项
         */
        protected String bank_code;


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
    class AsyncResponse extends SyncResponse{
        /**
         * 签约协议号
         */
        private String no_agree;

        /**
         * 证件类型
         * 默认为0   0:身份证
         */
        private String id_type;

        /**
         * 证件号码
         */
        private String id_no;

        /**
         * 银行账号姓名
         */
        private String acct_name;
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


    /**
     * 查询结果 请求
     */
    @EqualsAndHashCode(callSuper = true)
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Accessors(chain = true)
    @ToString(callSuper = true)
    class QueryRequest extends LLBaseParam {
        /**
         * 商户订单id
         */
        protected String no_order;

        /**
         * 商户订单时间
         * YYYYMMDDH24MISS
         * 此选择项可以不送，商户必须保证商户唯一订单号为在商户系统中是唯一标识该订单的，同时只能查询最近半年的订单信息，否则可能查询不到
         * 非必需
         */
        protected String dt_order;

        /**
         * 连连支付订单号
         * 非必需
         */
        protected String oid_paybill;

        /**
         * 查询版本号
         * 查询版本号不传就是老版本默认1.01.1版本查询新增memo 字段、银行名称bank_name字段
         */
        protected String query_version = "1.1";
    }

    /**
     * 查询结果 响应
     */
    @EqualsAndHashCode(callSuper = true)
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString(callSuper = true)
    class QueryResponse extends SyncResponse{
        /**
         * 结果代码
         * 0000 表示成功
         */
        protected String ret_code;

        /**
         * 结果描述
         * 交易成功
         */
        protected String ret_msg;

        /**
         * 银行名称
         * query_version大于1.0版本时返回
         */
        private String bank_name;

        /**
         * 支付备注
         * query_version大于1.0版本时返回支付失败的原因，如果多次支付的话返回最后一次的失败原因
         */
        private String memo;
    }




}
