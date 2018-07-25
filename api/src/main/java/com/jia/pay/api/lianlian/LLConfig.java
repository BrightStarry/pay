package com.jia.pay.api.lianlian;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author  ZhengXing
 * createTime: 2018/6/21
 * desc:  连连配置类
 */
@Data
@Component
@ConfigurationProperties(prefix = "pay.lianlian")
public class LLConfig {

    // 商户编号
    private String no = "201806200001928656";

    /**
     * wap认证支付
     * 同步和异步通知，使用wap快捷支付的
     */
    // 支付接口地址
    private String wapAuthPayUrl = "https://wap.lianlianpay.com/authpay.htm";

    /**
     * wap快捷支付
     */
    // 支付接口地址
    private String wapQuickPayUrl = "https://wap.lianlianpay.com/payment.htm";
    // 支付结果查询接口地址
    private String wapQuickQueryUrl = "https://queryapi.lianlianpay.com/orderquery.htm";
    // 同步通知地址
    private String wapQuickPaySyncNotifyUrl = "http://3pnsqs.natappfree.cc/lianlian/wap/pay/sync";
    // 异步通知地址
    private String wapQuickPayAsyncNotifyUrl = "http://3pnsqs.natappfree.cc/lianlian/wap/pay/async";

    // 同步成功静态页面
    @Deprecated
    private String wapQuickPaySyncSuccessUrl = "http://manager.bucmart.net/success.html";
    // 同步失败静态页面
    @Deprecated
    private String wapQuickPaySyncFailedUrl = "http://manager.bucmart.net/failed.html";


    /**
     * web快捷支付 & web网银支付
     */
    // 支付接口地址
    private String webQuickPayUrl = "https://cashier.lianlianpay.com/payment/bankgateway.htm";
    // 支付结果查询接口地址
    private String webQuickQueryUrl = "https://queryapi.lianlianpay.com/orderquery.htm";
    // 同步通知地址
    private String webQuickPaySyncNotifyUrl = "http://3pnsqs.natappfree.cc/lianlian/pc/pay/page";
    // 异步通知地址
    private String webQuickPayAsyncNotifyUrl = "http://3pnsqs.natappfree.cc/lianlian/pc/pay/async";



    /**
     * 代付
     */
    // 代付接口
    private String replacePayUrl = "https://instantpay.lianlianpay.com/paymentapi/payment.htm";
    // 确认代付接口
    private String confirmReplacePayUrl = "https://instantpay.lianlianpay.com/paymentapi/confirmPayment.htm";
    // 代付结果查询接口
    private String replacePayQueryUrl = "https://instantpay.lianlianpay.com/paymentapi/queryPayment.htm";
    // 代付异步通知url
    private String replacePayAsyncNotifyUrl = "http://jb43d3.natappfree.cc/lianlian/replacePay/async";

    /**
     * RSA
     */
    // 商户私钥
    private String privateKey = "";
    // 连连公钥
    private String publicKey = "";


}
