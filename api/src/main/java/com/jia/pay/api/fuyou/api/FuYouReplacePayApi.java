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
 * createTime: 2018/5/23
 * desc:  代付（单笔） 接口
 */
public interface FuYouReplacePayApi {

    /**
     * 请求对象
     */
    @Data
    @Accessors(chain = true)
    @XmlRootElement(name = "payforreq")
    // 该注解表示所有字段默认不进行转换，全部通过@XmlElement注解（这样可以直接在字段上注解@XmlElement来改变xml中的标签名）
    @XmlAccessorType(XmlAccessType.NONE)
    class Request {

        /**
         * 版本号
         */
        @XmlElement(name = "ver")
        private String version = "1.0";

        /**
         * 请求日期
         * yyyyMMdd
         */
        @XmlElement(name = "merdt")
        private String date;

        /**
         * 请求流水
         * 数字串，当天必须唯一
         * 长度 10 - 30
         */
        @XmlElement(name = "orderno")
        private String todayOrderId;

        /**
         * 总行代码
         */
        @XmlElement(name = "bankno")
        private String bankCode;

        /**
         * 城市代码
         */
        @XmlElement(name = "cityno")
        private String areaCode;

        /**
         * 支行名称
         * 可不填，如果填写，一定要填对，否则影响交易；但对公户、城商行、农商行、信用社必须填支行，且需正确的支行信息
         */
        @XmlElement(name = "branchnm")
        private String branchName;

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
         * 金额
         * 单位：分（单笔最少3元）
         */
        @XmlElement(name = "amt")
        private String amt;

        /**
         * 企业流水号(订单Id)
         * 填写后，系统体现在交易查询中
         * 1-32
         */
        @XmlElement(name = "entseq")
        private String orderId;

        /**
         * 备注
         * 填写后，系统体现在交易查询中
         * 1-64
         */
        @XmlElement(name = "memo")
        private String memo;

        /**
         * 手机号
         */
        @XmlElement(name = "mobile")
        private String mobile;

        /**
         * 是否返回交易状态类别
         * 值为1，其他值或不填则认为不需要返回交易状态类别且响应参数无状态类别节点
         */
        @XmlElement(name = "addDesc")
        private String addDesc = "1";
    }

    /**
     * 同步响应对象
     */
    @Data
    @XmlRootElement(name = "payforrsp")
    @XmlAccessorType(XmlAccessType.NONE)
    class SyncResponse{
        /**
         * 响应代码
         */
        @XmlElement(name = "ret")
        private String responseCode;

        /**
         * 响应描述
         */
        @XmlElement(name = "memo")
        private String responseDesc;

        /**
         * 交易状态类别
         * see {@link FuYouTransactionStatusTypeEnum}
         */
        @XmlElement(name = "transStatusDesc")
        private String transactionStatus;

        /**
         * 交易状态类别枚举
         * 自定义的一个参数
         */
        private FuYouTransactionStatusTypeEnum transactionStatusTypeEnum;
    }

    /**
     * 富友交易状态类别 枚举
     */
    @AllArgsConstructor
    @Getter
    enum FuYouTransactionStatusTypeEnum implements CodeEnum<String> {

        成功("success","成功"),
        受理成功("acceptSuccess","受理成功"),
        富友失败("internalFail","富友失败"),
        通道失败("channelFail","通道失败"),
        卡信息错误("cardInfoError","卡信息错误"),
        交易结果未知("unknowReasons","交易结果未知"),
        银行受理成功("chanAcceptSuccess","银行受理成功"),

        ;

        private String code;
        private String message;
    }
}
