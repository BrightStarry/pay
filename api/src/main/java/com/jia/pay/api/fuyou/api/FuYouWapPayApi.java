package com.jia.pay.api.fuyou.api;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author  ZhengXing
 * createTime: 2018/5/21
 * desc:  h5 支付接口
 */
public interface FuYouWapPayApi {

    /**
     * 外层请求对象
     */
    @Data
    @Accessors(chain = true)
    class WrapRequest {

        /**
         * 加密标志， 是否对订单信息FM域的内容进行加密
         * 1 加密； 0不加密
         */
        private String ENCTP = "0";

        /**
         * 版本号
         */
        private String VERSION = "2.0";

        /**
         * 商户代码
         */
        private String MCHNTCD;

        /**
         * 订单信息 xml
         */
        private String FM;

    }

    /**
     * 请求对象
     */
    @Data
    @Accessors(chain = true)
    @XmlRootElement(name = "ORDER")
    // 该注解表示所有字段默认不进行转换，全部通过@XmlElement注解（这样可以直接在字段上注解@XmlElement来改变xml中的标签名）
    @XmlAccessorType(XmlAccessType.NONE)
    class Request {


        /**
         * 交易类型， 10
         */
        @XmlElement(name = "TYPE")
        private String type = "10";


        /**
         * 版本号
         */
        @XmlElement(name = "VERSION")
        private String version = "2.0";

        /**
         * 商户代码
         */
        @XmlElement(name = "MCHNTCD")
        private String mchntCd;

        /**
         * 商户订单号
         */
        @XmlElement(name = "MCHNTORDERID")
        private String orderId;


        /**
         * 商户中的 用户id
         */
        @XmlElement(name = "USERID")
        private String userId;

        /**
         * 交易金额
         */
        @XmlElement(name = "AMT")
        private String amt;

        /**
         * 支付的银行卡号
         */
        @XmlElement(name = "BANKCARD")
        private String bankCard;


        /**
         * 后台通知url
         */
        @XmlElement(name = "BACKURL")
        private String backUrl;


        /**
         * 姓名
         */
        @XmlElement(name = "NAME")
        private String name;

        /**
         * 身份证号码
         */
        @XmlElement(name = "IDNO")
        private String cardNumber;

        /**
         * 证件类型
         * 0：身份证
         */
        @XmlElement(name = "IDTYPE")
        private String idType = "0";


        /**
         * 是否隐藏支付页面富友的logo，1隐藏，0显示
         */
        @XmlElement(name = "LOGOTP")
        private String logoTp = "0";


        /**
         * 支付成功url
         */
        @XmlElement(name = "HOMEURL")
        private String successUrl;

        /**
         * 支付失败url
         */
        @XmlElement(name = "REURL")
        private String failedUrl;

        /**
         * 签名方式
         * md5或rsa
         */
        @XmlElement(name = "SIGNTP")
        private String signType = "md5";

        /**
         * 签名
         */
        @XmlElement(name = "SIGN")
        private String sign;

    }

    /**
     * 响应对象
     */
    @Data
    class Response {

        /**
         * 类型 请求报文中的参数值
         */
        private String TYPE;
        /**
         * 版本号
         */
        private String VERSION;

        /**
         * 响应代码
         */
        private String RESPONSECODE;

        /**
         * 商户号 请求报文中的参数值
         */
        private String MCHNTCD;

        /**
         * 商户订单id 请求报文中的参数值
         */
        private String MCHNTORDERID;
        /**
         * 富友订单号 富友生成的订单号，该订单号在相当长的时间内不重复。富友通过订单号来唯一确认一笔订单的重复性
         * 需要和商户订单id一一对应
         */
        private String ORDERID;
        /**
         * 交易金额
         */
        private String AMT;
        /**
         * 银行卡号 实际支付的银行卡号
         */
        private String BANKCARD;

        /**
         * 响应中文描述, 不参与md5校验
         */
        private String RESPONSEMSG;


        /**
         * 签名
         */
        private String SIGN;


    }

}
