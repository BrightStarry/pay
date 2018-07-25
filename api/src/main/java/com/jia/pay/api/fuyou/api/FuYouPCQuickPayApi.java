package com.jia.pay.api.fuyou.api;

import com.jia.pay.common.enums.CodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * @author  ZhengXing
 * createTime: 2018/5/31
 * desc:  pc快捷支付（认证支付） 接口
 */
public interface FuYouPCQuickPayApi {

    /**
     * 请求对象
     */
    @Data
    @Accessors(chain = true)
    class Request {
        /**
         * 商户代码
         */
        private String mchnt_cd;

        /**
         * 商户中 用户id
         */
        private String user_id;

        /**
         * 商户 订单id
         */
        private String order_id;

        /**
         * 金额
         */
        private String order_amt;

        /**
         * 银行卡号
         */
        private String card_no;

        /**
         * 姓名
         */
        private String cardholder_name;


        /**
         * 证件类型
         */
        private String cert_type = "0";

        /**
         * 身份证号
         */
        private String cert_no;

        /**
         * 前台通知url
         */
        private String page_notify_url;

        /**
         * 后台通知url
         */
        private String back_notify_url;


        /**
         * 支付类型
         * 0 理财版1 普通版
         * 不进行rsa
         */
        private String user_type = "0";

        /**
         * RSA加密串
         */
        private String RSA;
    }



    /**
     * 响应对象
     */
    @Data
    @Accessors(chain = true)
    class Response {
        /**
         * 商户代码
         */
        private String mchnt_cd;

        /**
         * 商户 订单id
         */
        private String order_id;

        /**
         * 订单日期
         * YYYYMMDD
         */
        private String order_date;

        /**
         * 金额
         */
        private String order_amt;

        /**
         * 订单状态
         */
        private String order_st;

        /**
         * 错误代码
         */
        private String order_pay_code;

        /**
         * 错误中文描述
         */
        private String order_pay_msg;

        /**
         * 用户id
         */
        private String user_id;

        /**
         * 富友流水号
         */
        private String fy_ssn;

        /**
         * 扣款卡号
         */
        private String card_no;

        /**
         * 支付类型
         * DIRPAY  PC快捷支付
         * WECHAT  微信支付
         * ALIPAY   支付宝支付
         */
        private String pay_type;

        /**
         * rsa
         */
        private String RSA;
    }

    /**
     * 响应对象 订单状态枚举
     * see {@link Response#order_st}
     */
    @AllArgsConstructor
    @Getter
    enum ResponseOrderStatusEnum implements CodeEnum<String> {

        SUCCESS("11", "成功"),
        ;
        private String code;
        private String message;
    }
}
