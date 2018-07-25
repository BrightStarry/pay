package com.jia.pay.api.lianlian.api;

import com.jia.pay.api.lianlian.dto.LLBaseParam;
import com.jia.pay.api.lianlian.dto.LLBaseResponse;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * @author  ZhengXing
 * createTime: 2018/6/21
 * desc:  代付相关
 */
public interface LLReplacePayAPI {


    /**
     * 付款申请 请求
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
        private String api_version = "1.0";

        /**
         * 商户系统唯一标识该付款的流水号
         */
        private String no_order;

        /**
         * 付款时间
         * YYYYMMDDH24MISS
         */
        private String dt_order;

        /**
         * 付款金额
         */
        private String money_order;

        /**
         * 银行卡号
         */
        private String card_no;

        /**
         * 姓名
         */
        private String acct_name;

        /**
         * 订单描述
         * 付款用途
         */
        private String info_order;

        /**
         * 对公 对私标识
         * 0-对私1-对公
         */
        private String flag_card = "0";

        /**
         * 异步通知
         */
        private String notify_url;

        /**
         * 收款备注
         * 传递至银行
         */
        private String memo;

        /**
         * 银行编码
         * 对公才需要传
         */
        private String bank_code;
    }

    /**
     * 付款申请 响应
     */
    @EqualsAndHashCode(callSuper = true)
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Accessors(chain = true)
    @ToString(callSuper = true)
    class PayResponse extends LLBaseParam{
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
         * 商户系统唯一标识该付款的流水号
         */
        private String no_order;

        /**
         * 连连支付订单号
         */
        private String oid_paybill;

        /**
         * 验证码
         * 当校验疑似重复或卡号姓名不一致时返回，用于确认付款接口
         */
        private String confirm_code;
    }

    /**
     * 确认付款 请求
     */
    @EqualsAndHashCode(callSuper = true)
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Accessors(chain = true)
    @ToString(callSuper = true)
    class ConfirmPayRequest extends LLBaseParam{
        /**
         * 版本号
         */
        private String api_version = "1.0";

        /**
         * 商户付款流水号
         */
        private String no_order;

        /**
         * 确认付款验证码
         * 来自付款申请接口返回
         */
        private String confirm_code;

        /**
         * 异步通知地址
         */
        private String notify_url;

    }

    /**
     * 确认付款 响应
     */
    @EqualsAndHashCode(callSuper = true)
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString(callSuper = true)
    class ConfirmPayResponse extends LLBaseParam{
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
         * 商户付款流水号
         */
        private String no_order;

        /**
         * 连连支付订单号
         * 创建成功返回
         */
        private String oid_paybill;
    }





    /**
     * 付款结果查询 请求
     */
    @EqualsAndHashCode(callSuper = true)
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Accessors(chain = true)
    @ToString(callSuper = true)
    class QueryRequest extends LLBaseParam{
        /**
         * 版本号
         */
        private String api_version = "1.0";


        /**
         * 商户付款流水号
         */
        private String no_order;

        /**
         * 连连支付订单号
         * 非必传
         */
        private String oid_paybill;
    }

    /**
     * 付款结果查询  响应
     * 该响应不会有异常描述
     */
    @EqualsAndHashCode(callSuper = true)
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString(callSuper = true)
    class QueryResponse extends LLBaseParam {
        /**
         * 结果代码
         * 0000 表示成功
         * 该参数和ret_msg都表示接口调用状态
         */
        protected String ret_code;

        /**
         * 结果描述
         * 交易成功
         */
        protected String ret_msg;

        /**
         * 商户系统唯一标识该付款的流水号
         */
        private String no_order;

        /**
         * 商户下单时间
         * YYYYMMDDH24MISS
         */
        private String dt_order;

        /**
         * 付款金额
         */
        private String money_order;

        /**
         * 连连支付订单号
         * 非必传
         */
        private String oid_paybill;

        /**
         * 付款结果
         * see {@link com.jia.pay.api.lianlian.enums.LLReplacePayResultEnum}
         */
        private String result_pay;

        /**
         * 财务日期
         * YYYYMMDD
         */
        private String settle_date;

        /**
         * 订单描述
         * 查询时此处不会有异常描述，异步返回时才有
         */
        private String info_order;

    }

    /**
     * 异步通知
     */
    @EqualsAndHashCode(callSuper = true)
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString(callSuper = true)
    class AsyncResponse extends LLBaseParam {
        /**
         * 商户订单id
         */
        private String no_order;

        /**
         * 商户订单时间
         * YYYYMMDDH24MISS
         */
        private String dt_order;

        /**
         * 连连支付订单号
         */
        private String oid_paybill;


        /**
         * 交易金额
         * 保留两位小数
         */
        private String money_order;

        /**
         * 订单描述
         */
        private String info_order;

        /**
         * 付款状态
         * see {@link com.jia.pay.api.lianlian.enums.LLReplacePayResultEnum}
         * 只有SUCCESS 付款成功FAILURE 付款失败CANCEL   付款退款（付款成功后，有可能会发生退款）这三种
         */
        private String result_pay;

        /**
         * 清算日期
         * YYYYMMDD
         */
        private String settle_date;
    }

    /**
     * 异步通知 返回
     */
    @EqualsAndHashCode(callSuper = true)
    @Data
    @ToString(callSuper = true)
    class AsyncResponseReturn extends LLBaseResponse {
    }

}
