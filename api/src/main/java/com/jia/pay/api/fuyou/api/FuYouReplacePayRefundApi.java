package com.jia.pay.api.fuyou.api;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author  ZhengXing
 * createTime: 2018/5/25
 * desc:  富有代付 退票接口
 */
public interface FuYouReplacePayRefundApi {

    /**
     * 请求对象
     */
    @Data
    @Accessors(chain = true)
    class Request {
        /**
         * 请求流水号
         */
        private String orderno;

        /**
         * 原请求日期
         */
        private String merdt;

        /**
         * 富友流水号
         */
        private String fuorderno;

        /**
         * 退票日期
         */
        private String tpmerdt;

        /**
         * 退票当日唯一流水
         */
        private String futporderno;

        /**
         * 银行卡号
         */
        private String accntno;

        /**
         * 银行卡持卡人姓名
         */
        private String accntnm;

        /**
         * 总行代码
         */
        private String bankno;

        /**
         * 退票金额
         * 单位： 分
         */
        private String amt;

        /**
         * 状态
         * 参见交易状态码说明，1为退票成功
         */
        private String state;

        /**
         * 交易结果
         */
        private String result;

        /**
         * 结果原因
         */
        private String reason;

        /**
         * md5
         */
        private String mac;
    }

    /**
     * 成功响应
     */
    String successResponse = "1";
}
