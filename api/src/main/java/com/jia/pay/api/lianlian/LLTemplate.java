package com.jia.pay.api.lianlian;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.jia.pay.api.lianlian.api.*;
import com.jia.pay.api.lianlian.dto.LLBaseParam;
import com.jia.pay.api.lianlian.dto.LLBaseRiskControlParam;
import com.jia.pay.api.lianlian.dto.LLRealNameRiskControlParam;
import com.jia.pay.api.lianlian.dto.LLWrapRequestDTO;
import com.jia.pay.api.lianlian.enums.LLRequestAppFlagEnum;
import com.jia.pay.api.lianlian.util.LLRSAUtil;
import com.jia.pay.common.util.BeanUtil;
import com.jia.pay.common.util.DateUtil;
import com.jia.pay.common.util.HttpClientUtil;
import com.sun.istack.internal.NotNull;
import lombok.Getter;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author  ZhengXing
 * createTime: 2018/6/21
 * desc:  连连支付
 */
@Component
@Slf4j
public class LLTemplate {
    // 商品名
    private static final String GOODS_NAME = "九禾木";
    // 商品类目code
    private static final String GOODS_CATEGORY = "2009";
    // 支付描述
    private static final String PAY_MEMO = "退保证金";

    private HttpClientUtil httpClientUtil;

    @Getter
    private final LLConfig llConfig;

    private final ObjectMapper objectMapper;

    public LLTemplate(ObjectMapper objectMapper, LLConfig llConfig) {
        this.objectMapper = objectMapper;
        this.llConfig = llConfig;
    }

    @PostConstruct
    public void init() {
        HttpClientUtil.DefaultHttpClientConfig defaultHttpClientConfig = new HttpClientUtil.DefaultHttpClientConfig();
        httpClientUtil = new HttpClientUtil(defaultHttpClientConfig);
    }




    /**
     * 代付
     * @param request 请求对象
     */
    @SneakyThrows
    public LLReplacePayAPI.PayResponse replacePay(LLReplacePayAPI.PayRequest request) {
        LLWrapRequestDTO wrapRequest = new LLWrapRequestDTO();
        wrapRequest.setOid_partner(request.getOid_partner()).setPay_load(encrypt(request));
        String result;
        try {
            result = httpClientUtil.doPost(llConfig.getReplacePayUrl(),
                    objectMapper.writeValueAsString(wrapRequest), null, null);

        } catch (Exception e) {
            log.error("[代付接口]请求对象:{}，HTTP请求失败:", wrapRequest, e);
            return null;
        }
        return objectMapper.readValue(result, LLReplacePayAPI.PayResponse.class);
    }

    /**
     * 构建代付对象
     * @param requestId  请求id
     * @param money    金额
     * @param bankCard 银行卡号
     * @param name     姓名
     */
    public LLReplacePayAPI.PayRequest  buildReplacePayRequest(@NonNull String requestId, @NonNull String money,
                                                              @NonNull String bankCard, @NonNull String name) {
        LLReplacePayAPI.PayRequest request = new LLReplacePayAPI.PayRequest();
        request.setNo_order(requestId)
                .setDt_order(DateUtil.getYYYYMMDDHHMMSSNowTime())
                .setMoney_order(money).setCard_no(bankCard).setAcct_name(name)
                .setInfo_order(PAY_MEMO)
                .setMemo(PAY_MEMO)
                .setNotify_url(llConfig.getReplacePayAsyncNotifyUrl());
        setBaseParam(request);
        return request;
    }

    /**
     * 确认代付
     *
     * @param orderId 订单id
     * @param code    验证码
     */
    @SneakyThrows
    public LLReplacePayAPI.ConfirmPayResponse confirmReplacePay(@NonNull String orderId, @NonNull String code) {
        LLReplacePayAPI.ConfirmPayRequest request = new LLReplacePayAPI.ConfirmPayRequest()
                .setNo_order(orderId).setConfirm_code(code).setNotify_url(llConfig.getReplacePayAsyncNotifyUrl());
        setBaseParam(request);
        LLWrapRequestDTO wrapRequest = new LLWrapRequestDTO();
        wrapRequest.setOid_partner(request.getOid_partner()).setPay_load(encrypt(request));
        log.info("[连连确认代付]请求对象:{}", request);
        String result;
        try {
            result = httpClientUtil.doPost(llConfig.getConfirmReplacePayUrl(),
                    objectMapper.writeValueAsString(wrapRequest), null, null);

        } catch (Exception e) {
            log.error("[代付接口]请求对象:{}，HTTP请求失败:", request, e);
            return null;
        }
        return objectMapper.readValue(result, LLReplacePayAPI.ConfirmPayResponse.class);
    }


    /**
     * 代付查询
     *
     * @param orderId 订单id
     */
    @SneakyThrows
    public LLReplacePayAPI.QueryResponse replacePayQuery(@NonNull String orderId) {
        LLReplacePayAPI.QueryRequest request = new LLReplacePayAPI.QueryRequest();
        request.setNo_order(orderId);
        setBaseParam(request);
        String result;
        try {
            result = httpClientUtil.doPost(llConfig.getReplacePayQueryUrl(),
                    objectMapper.writeValueAsString(request), null, null);

        } catch (Exception e) {
            log.error("[代付接口]请求对象:{}，HTTP请求失败:", request, e);
            return null;
        }
        return objectMapper.readValue(result, LLReplacePayAPI.QueryResponse.class);
    }

    /**
     * wap认证支付
     * @param userId    用户id
     * @param requestId   请求id
     * @param orderTime 订单时间戳 YYYYMMDDHHMMSS
     * @param money     金额
     * @param appFlagEnum app标识
     * @param riskControlParam  风控参数
     */
    @SneakyThrows
    public String wapAuthPay(@NonNull String userId, @NonNull String requestId, @NonNull Date orderTime,
                             @NonNull String money,@NonNull LLRequestAppFlagEnum appFlagEnum,
                             @NonNull LLRealNameRiskControlParam riskControlParam) {
        LLWapAuthAPI.PayRequest request = new LLWapAuthAPI.PayRequest();
        request .setUser_id(userId).setApp_request(appFlagEnum.getCode())
                .setNo_order(requestId).setDt_order(DateUtil.format(orderTime, DateUtil.FMT_YMDHMS))
                .setName_goods(GOODS_NAME).setMoney_order(money)
                .setNotify_url(llConfig.getWapQuickPayAsyncNotifyUrl())
                .setUrl_return(llConfig.getWapQuickPaySyncNotifyUrl());
        request.setId_no(riskControlParam.getUser_info_id_no()).setAcct_name(riskControlParam.getUser_info_full_name())
                .setRisk_item(objectMapper.writeValueAsString(riskControlParam));

        setBaseParam(request);
        LLWapAuthAPI.WrapRequest wrapRequest = new LLWapAuthAPI.WrapRequest();
        wrapRequest.setReq_data(objectMapper.writeValueAsString(request));
        log.info("[连连wap认证支付]请求对象:{}", request);
        return "<html>" + "<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"  />" +
                "<title>loading</title>" + "</head>" + "<body OnLoad=\"OnLoadEvent();\" >" +
                "Loading..." + "<form name=\"forwardForm\" action=\"" +
                llConfig.getWapAuthPayUrl() + "\" method=\"POST\">" +
                "  <input type=\"hidden\" name=\"req_data\"" + "value='" + wrapRequest.getReq_data() + "'/>" +
                "</form>" + "<SCRIPT LANGUAGE=\"Javascript\">" + "  function OnLoadEvent(){" +
                "    document.forwardForm.submit();" + "  }" +
                "</SCRIPT>" + "</body>" + "</html>";
    }

    /**
     * wap快捷支付
     * @param requestId   请求id
     * @param userId    用户id
     * @param orderTime 订单时间戳 YYYYMMDDHHMMSS
     * @param money     金额
     * @param appFlagEnum app标识
     * @param riskControlParam  风控参数
     */
    @Deprecated
    @SneakyThrows
    public String wapQuickPay(@NonNull String requestId, @NonNull String userId, @NonNull Date orderTime,
                              @NonNull String money,@NonNull LLRequestAppFlagEnum appFlagEnum,
                              LLBaseRiskControlParam riskControlParam) {
        LLWapQuickAPI.PayRequest request = new LLWapQuickAPI.PayRequest()
                .setUser_id(userId).setApp_request(appFlagEnum.getCode())
                .setNo_order(requestId).setDt_order(DateUtil.format(orderTime, DateUtil.FMT_YMDHMS))
                .setName_goods(GOODS_NAME).setMoney_order(money)
                .setNotify_url(llConfig.getWapQuickPayAsyncNotifyUrl())
                .setUrl_return(llConfig.getWapQuickPaySyncNotifyUrl());
        if (riskControlParam != null) {
            request.setRisk_item(objectMapper.writeValueAsString(riskControlParam));
        }
        setBaseParam(request);
        LLWapQuickAPI.WrapRequest wrapRequest = new LLWapQuickAPI.WrapRequest();
        wrapRequest.setReq_data(objectMapper.writeValueAsString(request));

        log.info("[连连wap快捷支付]请求对象:{}", request);
        return "<html>" + "<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"  />" +
                "<title>loading</title>" + "</head>" + "<body OnLoad=\"OnLoadEvent();\" >" +
                "Loading..." + "<form name=\"forwardForm\" action=\"" +
                llConfig.getWapQuickPayUrl() + "\" method=\"POST\">" +
                "  <input type=\"hidden\" name=\"req_data\"" + "value='" + wrapRequest.getReq_data() + "'/>" +
                "</form>" + "<SCRIPT LANGUAGE=\"Javascript\">" + "  function OnLoadEvent(){" +
                "    document.forwardForm.submit();" + "  }" +
                "</SCRIPT>" + "</body>" + "</html>";
    }



    /**
     * web快捷支付/网银支付
     *
     * @param userId    用户id
     * @param orderId   订单id
     * @param orderTime 订单时间戳 YYYYMMDDHHMMSS
     * @param money     金额
     * @param bankCode  银行代码，如果传，则为网银支付
     * @param riskControlParam  风控参数
     *
     */
    @SneakyThrows
    public String webPay(@NonNull String userId, @NonNull String orderId, @NonNull Date orderTime,
                         @NonNull String money,
                         @NotNull LLBaseRiskControlParam riskControlParam, String bankCode) {
        // 构建快捷支付参数
        LLWebBankAPI.PayRequest request = new LLWebBankAPI
                .PayRequest(userId, orderId, GOODS_NAME, money);
        request.setTimestamp(DateUtil.getYYYYMMDDHHMMSSNowTime())// 当前时间戳
                .setDt_order(DateUtil.format(orderTime, DateUtil.FMT_YMDHMS))
                .setNotify_url(llConfig.getWebQuickPayAsyncNotifyUrl())
                .setUrl_return(llConfig.getWebQuickPaySyncNotifyUrl());
        // 如果是网银
        if (StringUtils.isNotBlank(bankCode)) {
            request.setBank_code(bankCode).setPay_type("1");
            request.setRisk_item(objectMapper.writeValueAsString(riskControlParam));
        }
        setBaseParam(request);
        log.info("[连连web快捷支付]请求对象:{}", request);
        StringBuilder formHtml = new StringBuilder();
        formHtml.append("<html>").append("<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"  />")
                .append("<title>loading</title>").append("</head>").append("<body OnLoad=\"OnLoadEvent();\" >")
                .append("Loading...").append("<form name=\"forwardForm\" action=\"")
                .append(llConfig.getWebQuickPayUrl()).append("\" method=\"POST\">");
        Map<String, String> paramMap = BeanUtil.beanToMap(request);
        for (Map.Entry<String, String> entry : paramMap.entrySet()) {
            formHtml.append("<input type=\"hidden\" name=\"").append(entry.getKey()).append("\" value='").append(entry.getValue()).append("'/>");
        }
        formHtml.append("</form>").append("<SCRIPT LANGUAGE=\"Javascript\">").append("  function OnLoadEvent(){")
                .append("    document.forwardForm.submit();").append("  }")
                .append("</SCRIPT>").append("</body>").append("</html>");
        return formHtml.toString();
    }

    /**
     * web快捷支付 & web网银支付 & wap快捷支付 查询
     *
     * @param orderId 订单id
     * @param orderTime 订单创建时间
     */
    public LLWebQuickAPI.QueryResponse payQuery(@NonNull Long orderId, @NonNull Date orderTime) {
        LLWebQuickAPI.QueryRequest request = new LLWebQuickAPI.QueryRequest()
                .setNo_order(String.valueOf(orderId))
                .setDt_order(DateUtil.format(orderTime, DateUtil.FMT_YMDHMS));
        setBaseParam(request);
        log.info("[web快捷支付查询接口]请求对象:{}", request);
        try {
            String result = httpClientUtil.doPost(llConfig.getWebQuickQueryUrl(),
                    objectMapper.writeValueAsString(request), null, null);
            return objectMapper.readValue(result, LLWebQuickAPI.QueryResponse.class);
        } catch (Exception e) {
            log.error("[h5快捷支付查询接口]请求对象:{}，HTTP请求失败:", request, e);
        }
        return null;
    }

    /**
     * 构建风控参数
     */
    public LLRealNameRiskControlParam buildRiskControlParam(@NonNull String userId, @NonNull String phone,
//                                                        @NonNull Date signUpTime,
                                                            @NonNull String name, @NonNull String idCard) {
        LLRealNameRiskControlParam param = new LLRealNameRiskControlParam();
        param.setFrms_ware_category(GOODS_CATEGORY).setUser_info_mercht_userno(userId)
                .setUser_info_bind_phone(phone);
//                .setUser_info_dt_register(DateUtil.format(signUpTime, DateUtil.FMT_YMDHMS));
        param.setUser_info_full_name(name).setUser_info_id_no(idCard);
        return param;
    }


    /**
     * 签名验证
     */
    public <R extends LLBaseParam> boolean verifySign(R response) {
        String signData = generateSignStr(response);
        return LLRSAUtil.checksign(llConfig.getPublicKey(), signData, response.getSign());
    }


    /**
     * 设置 {@link LLBaseParam} 的几个属性
     * 需要在其他字段填充完成后调用
     */
    private <B extends LLBaseParam> void setBaseParam(B request) {
        request.setOid_partner(llConfig.getNo());
        request.setSign(LLRSAUtil.sign(llConfig.getPrivateKey(), generateSignStr(request)));
    }

    /**
     * 生成待签名字符串
     */
    @SneakyThrows
    private <R> String generateSignStr(R request) {
        LinkedHashMap<String, String> beanMap = BeanUtil.beanToOrderlyMapOfLetter(request);
        StringBuilder content = new StringBuilder();
        for (Map.Entry<String, String> i : beanMap.entrySet()) {
            // 此处有个非常奇怪的bug。 当参数中带有risk_item时，即使不符合以下的条件判断，仍会执行continue;
            // 在加入 && !i.getKey().equals("risk_item")后，才会不执行
            if ((i.getKey().equalsIgnoreCase("sign") || StringUtils.isBlank(i.getValue())) &&
                    !i.getKey().equals("risk_item"))
                continue;
            content.append("&").append(i.getKey()).append("=").append(i.getValue());
        }

        return content.substring(1);
    }

    /**
     * 公钥加密
     */
    @SneakyThrows
    private <R> String encrypt(R data) {
        return LLRSAUtil.encrypt(llConfig.getPublicKey(), objectMapper.writeValueAsString(data));
    }
}
