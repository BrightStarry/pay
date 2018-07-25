package com.jia.pay.api.fuyou.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * @author  ZhengXing
 * createTime: 2018/5/17
 * desc:  pc网银支付 接口
 *
 * 这些接口的请求对象不使用通用父类是为了确保各字段的顺序
 */
public interface FuYouPCBankPayApi {

    /**
     * 请求对象
     */
    @Data
    @Accessors(chain = true)
    class Request {
        /**
         * * 商户代码
         */
        private String mchnt_cd;

        /**
         * * 商户订单号
         * 客户支付后商户网站产生的一个唯一的定单号，该订单号应该在相当长的时间内不重复。富友通过订单号来唯一确认一笔订单的重复性
         * max(30)
         */
        private String order_id;

        /**
         * * 交易金额
         * 以分为单位。不可以为零，必需符合金额标准。
         */
        private String order_amt;

        /**
         * * 支付类型
         */
        private String order_pay_type;

        /**
         * * 页面跳转url
         */
        private String page_notify_url;

        /**
         * * 后台通知url
         */
        private String back_notify_url;

        /**
         * 超时时间
         * 1m-15 天，m：分钟、h：小时、d 天、1c 当天有效，
         * max(2)
         */
        private String order_valid_time;

        /**
         * * 银行代码
         */
        private String iss_ins_cd;

        /**
         * 商品名
         */
        private String goods_name;

        /**
         * 商品展示网址
         */
        private String goods_display_url;

        /**
         * 备注
         */
        private String rem;

        /**
         * * 版本号 目前(2018年5月17日)是1.0.1
         */
        private String ver = "1.0.1";

        /**
         * * md5
         * 之前所有字段用"|"连接，并作md5
         */
        private String md5;
    }

    /**
     * 请求对象，订单支付类型字段
     * {@link Request#order_pay_type} 枚举
     */
    @AllArgsConstructor
    @Getter
    enum OrderPayTypeEnum {
        B2C("B2C", "B2C 支付"),
        B2B("B2B", "B2B 支付"),
        FYCD("FYCD", "预付卡"),
        SXF("SXF", "随心富");
        private String code;
        private String desc;
    }



    /**
     * 响应对象
     */
    @Data
    class Response{
        /**
         * * 商户代码
         */
        private String mchnt_cd;

        /**
         * * 商户订单号
         * 客户支付后商户网站产生的一个唯一的定单号，该订单号应该在相当长的时间内不重复。富友通过订单号来唯一确认一笔订单的重复性
         * max(30)
         */
        private String order_id;

        /**
         * * 订单日期
         * YYYYMMDD
         */
        private String order_date;

        /**
         * * 交易金额
         * 以分为单位。不可以为零，必需符合金额标准。
         */
        private Double order_amt;

        /**
         * * 订单状态
         */
        private String order_st;

        /**
         * 交易代码，不参与MD5验签
         */
        private String txn_cd;

        /**
         * * 错误代码
         * 0000 表示成功 其他失败
         */
        private String order_pay_code;

        /**
         * * 错误中文描述
         */
        private String order_pay_error;

        /**
         * 保留字段
         */
        private String resv1;

        /**
         * * 富友流水号
         * 供商户查询支付交易状态及对账用
         */
        private String fy_ssn;

        /**
         * * md5
         * 最后加上mchnt_key 为 32 位的商户密钥，系统分配
         */
        private String md5;

    }

    /**
     * 响应对象，订单状态字段
     * {@link Response#order_st} 枚举
     */
    @AllArgsConstructor
    @Getter
    enum OrderStatusEnum {
       INIT ("00","订单已生成(初始状态)"),
        UNDO("01","订单已撤消"),
        MERGE("02","订单已合并"),
        EXPIRE("03","订单已过期"),
        WAIT_PAY("04","订单已确认(等待支付)"),
        PAY_FAILED("05","订单支付失败"),
        PAY_SUCCESS("11","订单已支付"),

        ;
        private String code;
        private String desc;
    }

    /**
     * 响应对象，交易代码字段
     * {@link Response#txn_cd} 枚举
     */
    @AllArgsConstructor
    @Getter
    enum TransactionCodesEnum {
        A("B01","借贷合一"),
        B("B21","借记卡"),
        C("B02","企业网银"),
        D("B03","借贷合一快捷支付"),
        E("B07","借记卡快捷支付"),

        ;
        private String code;
        private String desc;
    }




}
