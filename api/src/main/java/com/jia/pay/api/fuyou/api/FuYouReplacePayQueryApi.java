package com.jia.pay.api.fuyou.api;

import com.jia.pay.common.enums.CodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author  ZhengXing
 * createTime: 2018/5/24
 * desc:  代付查询接口 见其官网查询1.1接口文档
 */
public interface FuYouReplacePayQueryApi {


    /**
     * 请求对象
     */
    @Data
    @Accessors(chain = true)
    @XmlRootElement(name = "qrytransreq")
    // 该注解表示所有字段默认不进行转换，全部通过@XmlElement注解（这样可以直接在字段上注解@XmlElement来改变xml中的标签名）
    @XmlAccessorType(XmlAccessType.NONE)
    class Request {
        /**
         * 版本号
         */
        @XmlElement(name = "ver")
        private String version = "1.1";

        /**
         * 业务代码
         */
        @XmlElement(name = "busicd")
        private String businessCode = "AP01";

        /**
         * 原请求流水
         * see{@link FuYouReplacePayApi.Request#todayOrderId}
         */
        @XmlElement(name = "orderno")
        private String todayOrderId;

        /**
         * 开始日期
         * yyyyMMdd
         */
        @XmlElement(name = "startdt")
        private String startDate;

        /**
         * 结束日期
         * yyyyMMdd开始和结束时间间隔不能超过15天
         */
        @XmlElement(name = "enddt")
        private String endDate;

        /**
         * 交易状态
         * see {@link FuYouTransactionStatusEnum}
         */
        @XmlElement(name = "transst")
        private String transactionStatus;


    }

    /**
     * 最外层响应参数
     */
    @Data
    @Accessors(chain = true)
    @XmlRootElement(name = "qrytransrsp")
    @XmlAccessorType(XmlAccessType.NONE)
    class WrapResponse {

        /**
         * 响应代码
         * 成功示例： 000000
         */
        @XmlElement(name = "ret")
        private String responseCode;

        /**
         * 响应描述
         */
        @XmlElement(name = "memo")
        private String responseDesc;

        /**
         * 响应子对象
         */
        @XmlElement(name = "trans")
        private Response body;

    }

    /**
     * 响应子对象
     */
    @Data
    @Accessors(chain = true)
    @XmlRootElement(name = "trans")
    @XmlAccessorType(XmlAccessType.NONE)
   class Response {
        /**
         * 原请求日期
         */
        @XmlElement(name = "merdt")
        private String date;

        /**
         * 原请求流水
         * see{@link FuYouReplacePayApi.Request#todayOrderId}
         */
        @XmlElement(name = "orderno")
        private String todayOrderId;

        /**
         * 用户银行卡号
         */
        @XmlElement(name = "accntno")
        private String account;

        /**
         * 用户姓名
         */
        @XmlElement(name = "accntnm")
        private String accountName;

        /**
         * 交易金额
         */
        @XmlElement(name = "amt")
        private String amt;

        /**
         * 企业流水号
         */
        @XmlElement(name = "entseq")
        private String orderId;

        /**
         * 备注
         */
        @XmlElement(name = "memo")
        private String memo;

        /**
         * 交易状态
         * 成功示例: 1
         * see {@link FuYouTransactionStatusEnum}
         */
        @XmlElement(name = "state")
        private String state;

        /**
         * 交易结果
         * 成功示例： 渠道资金到账已复核,交易已发送
         */
        @XmlElement(name = "result")
        private String result;

        /**
         * 结果原因
         * 成功示例： 交易成功
         */
        @XmlElement(name = "reason")
        private String reason;

        /**
         * 是否退票
         * 1.是0.否（仅AP01有）
         * 成功示例: 0
         */
        @XmlElement(name = "tpst")
        private String refund;

        /**
         * 银行响应码
         * 成功示例： 000000
         */
        @XmlElement(name = "rspcd")
        private String bankResponseCode;

        /**
         * 交易状态类别
         * see {@link FuYouReplacePayApi.FuYouTransactionStatusTypeEnum}
         * 成功示例： success
         */
        @XmlElement(name = "transStatusDesc")
        private String transactionStatusType;
    }

    /**
     * 是否退票
     */
    @AllArgsConstructor
    @Getter
    enum FuYouIsRefundEnum implements CodeEnum<String> {

        是("1", "退票"),
        否("0", "未退票"),
        ;
        private String code;
        private String message;
    }


    /**
     * 交易状态 枚举
     */
    @AllArgsConstructor
    @Getter
     enum FuYouTransactionStatusEnum implements CodeEnum<String> {

        交易未发送("0", "交易未发送"),
        交易已发送且成功("1", "交易已发送且成功"),
        交易已发送且失败("2", "交易已发送且失败"),
        交易发送中("3", "交易发送中"),
        交易已发送且超时("7", "交易已发送且超时"),;

        private String code;
        private String message;
    }
}