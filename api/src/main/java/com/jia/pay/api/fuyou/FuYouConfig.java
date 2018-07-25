package com.jia.pay.api.fuyou;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author  ZhengXing
 * createTime: 2018/5/17
 * desc:
 */
@Data
@Component
@ConfigurationProperties(prefix = "pay.fuyou")
public class FuYouConfig {

    /**
     * 商户（自己）备案域名
     */
    private String domainName = "http://www.bucmart.net";

    /**
     * 商户代码
     */
    private String mchntCd = "0003310F1413401"; // 正式


    /**
     * B2C/B2B网关支付/代收付 交易密钥
     * mchnt_key
     */
    private String gatewayPayMchntKey = ""; //正式




    /**
     * h5快捷支付
     */
    //h5支付 接口地址
    private String h5PayUrl = "https://mpay.fuiou.com:16128/h5pay/payAction.pay"; //正式
    //h5快捷支付查询地址
    private String h5PayQueryApiUrl = "https://mpay.fuiou.com:16128/findPay/queryOrderId.pay";
    //h5支付异步通知地址
    private String h5AsyncNotifyUrl = "http://121.40.176.35/back/pay/h5/pay/async";
    // h5同步接口-自定义参数
    private String h5SyncUrl = "http://43vnzd.natappfree.cc/fuyou/h5/pay/sync";
    // h5手机支付 交易密钥
    private String h5PayMchntKey = "nsw4xuyhwer1o1sau62srylpj5nkchzr";// 正式


    /**
     * pc网银支付
     */
    //（PC网银支付） 接口地址
    private String pcWebBankPayApiUrl = "https://pay.fuiou.com/smpGate.do"; //正式
    //（PC网银支付） 结果查询（异步） 接口地址
    private String pcWebBankPayResultAsyncQueryApiUrl = "https://pay.fuiou.com/smpQueryGate.do"; //正式
    //（PC网银支付） 结果查询（同步） 接口地址
    private String pcWebBankPayResultSyncQueryApiUrl = "https://pay.fuiou.com/smpAQueryGate.do"; //正式
    //PC网银支付 同步通知地址
    private String pcSyncNotifyUrl = "http://kqc2n4.natappfree.cc/fuyou/pc/bank/page";
    //PC网银支付 异步通知地址
    private String pcAsyncNotifyUrl = "http://kqc2n4.natappfree.cc/fuyou/pc/bank/async";

    /**
     * pc快捷支付
     */
    //pc快捷支付（认证支付） 接口地址
    private String pcQuickApiUrl = "https://pay.fuiou.com/dirPayGate.do";// 正式
    //pc快捷支付 前台通知地址
    private String pcQuickPayPageUrl = "http://kqc2n4.natappfree.cc/fuyou/pc/quick/page";
    //pc快捷支付 后台异步通知地址
    private String pcQuickPayAsyncUrl = "http://kqc2n4.natappfree.cc/fuyou/pc/quick/async";
    //PC快捷支付交易私钥
    private String pcQuickPayMchntKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIXlodJ8NQvPn9c5/o7i8y6TYdfu2NNWaFQGHj8rrOwVGz+JYj3iPEktTtJdyhY0LkW7/ynp4R0Ue3/Od1vP0sev2oq1YTsgNcX8B7L38VYRmJWTtSc4zqvrdSSzltOpoImGNhnIFNNa8s8lZ7NYh9bZImLt0/NYda6q9nSQlZZxAgMBAAECgYA74vj9q5AOOE7FdKrmPZuGuWSJr8pKu9rtuc7NcjtlXAtT356RDx7nm9wlOs2oIK6RggQeOvz0hLS39SBtcAZVolPIEUSHIH4Dt/OSyl8JDQvMbgdg5hWtbXS2msXSYFwVWbdy4BqbZSmQJknQwqKnW7RFEbFny26p1pPCtIfaAQJBANcvctn/lkj8lmonUtr044FIH63hoyC60CuZs7bEVKmlNw0vrRRSSy3gMXsl6rgkcioZP3y+UsHbuvKniCuhHHcCQQCfSyRjp9vaiD2hmmmiI/amfYNd0/HPfwG7FmAVcoVftzhKhTJdkEP8Zo1RKCZr9Om6iEsTQ4SrFiIRM993TuZXAkA/TDfOpH5CrfpY84RN4CdkCiE3dt4TbKB7ktToeEwnMvBEsreI+MQglIg5n2eyDxZ+B7tDKIETgV02r81AeD31AkBmjmhlSONBdUnIy7OK0oCwfEqhlB0xLGIe468E3/CCyWGSiOL+Xi2HSTdesuzZwJrin3FGe8fkpqFUVgNfuwqNAkEAiXKyiz7xayAhISQgANkHGuOBfQgNKlcZ22h1/vpaAyKT6oNFlmkh+xDHwAY5Rq9aYj/0e0MQZe/VP89YTwZdnQ==";

    /**
     * 代付
     */
    //代付 接口地址
    private String replacePayUrl = "https://fht.fuiou.com/req.do"; //正式
    //代付查询接口地址
    private String replacePayQueryUrl = "https://fht.fuiou.com/req.do";// 正式


    /**
     * 富友通知商户验签公钥
     */
    private String notifyPublicKey = "";

    /**
     * 可直接使用默认地区码和省略支行的15家银行代码数组
     */
    private String[] defaultBankCodeArray =
            new String[]{"0102", "0103", "0104", "0105", "0301", "0308", "0309", "0305", "0306", "0307", "0310", "0304", "0403", "0303", "0302"};

}
