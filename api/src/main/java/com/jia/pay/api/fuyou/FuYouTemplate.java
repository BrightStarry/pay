package com.jia.pay.api.fuyou;

import com.jia.pay.api.fuyou.api.*;
import com.jia.pay.api.fuyou.dto.ReplacePayWrapRequest;
import com.jia.pay.api.fuyou.enums.FuYouRequestTypeEnum;
import com.jia.pay.common.exception.PayException;
import com.jia.pay.common.util.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * @author  ZhengXing
 * createTime: 2018/5/17
 * desc:  富友接口调用模板
 * <p>
 * 此处http请求的请求头都需携带指定地址的Referer
 */
@Component
public class FuYouTemplate {

    private static Logger log = LoggerFactory.getLogger(FuYouTemplate.class);

    private static String HTTP_CLIENT_DEFAULT_HEADER_KEY = "pay";


    private HttpClientUtil httpClientUtil;

    @Getter
    private final FuYouConfig fuYouConfig;

    public FuYouTemplate(FuYouConfig fuYouConfig) {
        this.fuYouConfig = fuYouConfig;
    }

    @PostConstruct
    public void init() {
        /**
         * 配置httpClient线程池 此处请求头都需携带指定地址的Referer
         */
        ArrayList<Header> defaultHeaders = new ArrayList<>(HttpClientUtil.CHROME_HEADERS);
        defaultHeaders.add(new BasicHeader("Referer", fuYouConfig.getDomainName()));

        HttpClientUtil.DefaultHttpClientConfig defaultHttpClientConfig = new HttpClientUtil.DefaultHttpClientConfig();
        HashMap<String, List<Header>> headerMap = new HashMap<>();
        headerMap.put(HTTP_CLIENT_DEFAULT_HEADER_KEY, defaultHeaders);
        defaultHttpClientConfig.setCustomHeaders(headerMap);
        httpClientUtil = new HttpClientUtil(defaultHttpClientConfig);
    }


    /**
     * pc快捷支付（认证支付）接口  {@link FuYouPCQuickPayApi.Request}
     *
     * @param requestId 订单id
     * @param amt       金额
     * @param bankCard  银行卡号
     * @param idCard    身份证号
     * @param name      姓名
     * @param userId    用户id
     * @param pageUrl   同步url
     */
    @SneakyThrows
    public String pcQuickPayRequest(String requestId, String userId, String amt, String bankCard,
                                    String name, String idCard,String pageUrl) {
        FuYouPCQuickPayApi.Request request = buildPCQuickPayRequest(requestId, amt, bankCard, idCard, name, userId,pageUrl);
        log.info("[pc快捷支付接口]请求对象:{}", request);

        StringBuilder formHtml = new StringBuilder();
        formHtml.append("<html>").append("<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"  />")
                .append("<title>loading</title>").append("</head>").append("<body OnLoad=\"OnLoadEvent();\" >")
                .append("Loading...").append("<form name=\"forwardForm\" action=\"")
                .append(fuYouConfig.getPcQuickApiUrl()).append("\" method=\"POST\">");
        Map<String, String> paramMap = BeanUtil.beanToMap(request);
        for (Map.Entry<String, String> entry : paramMap.entrySet()) {
            formHtml.append("  <input type=\"hidden\" name=\"").append(entry.getKey()).append("\" value=\"").append(entry.getValue()).append("\"/>");
        }
        formHtml.append("</form>").append("<SCRIPT LANGUAGE=\"Javascript\">").append("  function OnLoadEvent(){")
                .append("    document.forwardForm.submit();").append("  }")
                .append("</SCRIPT>").append("</body>").append("</html>");
        return formHtml.toString();
    }


    /**
     * 代付接口 {@link ReplacePayWrapRequest}
     * see {@link #buildReplacePayRequest(String, String, String, String, String, String, String, String)}
     *
     * @return 请求对象
     */
    public FuYouReplacePayApi.SyncResponse replacePay(@NonNull ReplacePayWrapRequest request) {
        // // 构造请求对象
//         ReplacePayWrapRequest request = buildReplacePayRequest(bankCode, areaCode, branchName, accountName, account,
        // amt, orderId, phone);
        String result;
        try {
            log.info("[富友代付接口]请求对象:{}", request);
            result = httpClientUtil.doPost(fuYouConfig.getReplacePayUrl(), request, null,
                    HTTP_CLIENT_DEFAULT_HEADER_KEY);
        } catch (Exception e) {
            log.error("[富友代付接口]请求对象:{}，HTTP请求失败:", request, e);
            return null;
        }
        FuYouReplacePayApi.SyncResponse syncResponse = XmlUtil.xmlToBean(result, FuYouReplacePayApi.SyncResponse.class);
        // 根据交易状态类别，从枚举中查询对应状态的说明
        Optional<FuYouReplacePayApi.FuYouTransactionStatusTypeEnum> transactionStatusEnumOptional = EnumUtil.getByCode(syncResponse.getTransactionStatus(),
                FuYouReplacePayApi.FuYouTransactionStatusTypeEnum.class);
        syncResponse.setTransactionStatusTypeEnum(transactionStatusEnumOptional.orElse(FuYouReplacePayApi.FuYouTransactionStatusTypeEnum.交易结果未知));
        return syncResponse;
    }

    /**
     * 代付查询接口
     *
     * @param requestId 请求流水
     * @param date      请求日期 yyyyMMdd
     */
    public FuYouReplacePayQueryApi.WrapResponse replacePayQuery(String requestId, Date date) {
        ReplacePayWrapRequest request = buildReplacePayQueryRequest(requestId, date);
        String result = null;
        try {
            log.info("[富友代付查询接口]请求对象:{}", request);
            result = httpClientUtil.doPost(fuYouConfig.getReplacePayQueryUrl(), request, null,
                    HTTP_CLIENT_DEFAULT_HEADER_KEY);
        } catch (Exception e) {
            log.error("[富友代付查询接口]请求对象:{}，HTTP请求失败:", request, e);
            return null;
        }
        return XmlUtil.xmlToBean(result,
                FuYouReplacePayQueryApi.WrapResponse.class);
    }

    /**
     * H5支付接口 {@link com.jia.pay.api.fuyou.api.FuYouWapPayApi}
     *
     * @param requestId  请求id
     * @param userId     用户id
     * @param amt        金额
     * @param bankCard   银行卡号
     * @param name       姓名
     * @param idCard 身份证号
     * @param pageUrl 同步url
     * @return 跳转页面
     */
    @SneakyThrows
    public String wapPay(@NonNull String requestId, @NonNull String userId, @NonNull String amt, @NonNull String bankCard,
                        @NonNull String name, @NonNull String idCard,String pageUrl) {
        FuYouWapPayApi.WrapRequest wrapRequest = buildWapPayRequest(requestId, userId, amt, bankCard, name, idCard,pageUrl);
        log.info("[富友H5支付接口]请求对象:{}", wrapRequest);
        StringBuilder formHtml = new StringBuilder();
        formHtml.append("<html>").append("<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"  />")
                .append("<title>loading</title>").append("</head>").append("<body OnLoad=\"OnLoadEvent();\" >")
                .append("Loading...").append("<form name=\"forwardForm\" action=\"")
                .append(fuYouConfig.getH5PayUrl()).append("\" method=\"POST\">");
        Map<String, String> paramMap = BeanUtil.beanToMap(wrapRequest);
        for (Map.Entry<String, String> entry : paramMap.entrySet()) {
            formHtml.append("  <input type=\"hidden\" name=\"").append(entry.getKey()).append("\" value=\"").append(entry.getValue()).append("\"/>");
        }
        formHtml.append("</form>").append("<SCRIPT LANGUAGE=\"Javascript\">").append("  function OnLoadEvent(){")
                .append("    document.forwardForm.submit();").append("  }")
                .append("</SCRIPT>").append("</body>").append("</html>");
        return formHtml.toString();
    }




    /**
     * PC网银支付接口调用 {@link com.jia.pay.api.fuyou.api.FuYouPCBankPayApi}
     *
     * @param requestId 请求id
     * @param orderAmt  订单金额，以分为单位，整数，例如 10.38元，则传入 "1038"
     * @param bankCode   银行代码
     * @return 富友返回的html页面代码，可能为空，表示调用失败
     */
    @SneakyThrows
    public String pcWebBankPay(@NonNull String requestId, @NonNull String orderAmt,
                               @NonNull String bankCode) {
        // 构造请求对象
        FuYouPCBankPayApi.Request request = buildPCWebBankPayRequest(requestId, orderAmt,
                FuYouPCBankPayApi.OrderPayTypeEnum.B2C, bankCode);
        StringBuilder formHtml = new StringBuilder();
        formHtml.append("<html>").append("<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"  />")
                .append("<title>loading</title>").append("</head>").append("<body OnLoad=\"OnLoadEvent();\" >")
                .append("Loading...").append("<form name=\"forwardForm\" action=\"")
                .append(fuYouConfig.getPcWebBankPayApiUrl()).append("\" method=\"POST\">");
        Map<String, String> paramMap = BeanUtil.beanToMap(request);
        for (Map.Entry<String, String> entry : paramMap.entrySet()) {
            formHtml.append("<input type=\"hidden\" name=\"").append(entry.getKey()).append("\" value=\"").append(entry.getValue()).append("\"/>");
        }
        formHtml.append("</form>").append("<SCRIPT LANGUAGE=\"Javascript\">").append("  function OnLoadEvent(){")
                .append("    document.forwardForm.submit();").append("  }")
                .append("</SCRIPT>").append("</body>").append("</html>");
        return formHtml.toString();
    }





    /**
     * pc快捷支付 根据参数构造请求对象  {@link FuYouPCQuickPayApi.Request}
     *
     * @param requestId 请求id
     * @param amt       金额
     * @param bankCard  银行卡号
     * @param idNumber  身份证号
     * @param name      姓名
     * @param userId    用户id
     * @param pageUrl    同步url
     * @return 请求对象
     */
    @SneakyThrows
    private FuYouPCQuickPayApi.Request buildPCQuickPayRequest(String requestId, String amt, String bankCard,
                                                              String idNumber, String name, String userId,String pageUrl) {
        FuYouPCQuickPayApi.Request request = new FuYouPCQuickPayApi.Request()
                // 传入参数
                .setOrder_id(requestId).setOrder_amt(amt)
                .setCard_no(bankCard).setCert_no(idNumber)
                .setCardholder_name(name).setUser_id(userId)
                // 固定参数
                .setMchnt_cd(fuYouConfig.getMchntCd())
                .setPage_notify_url(StringUtils.isBlank(pageUrl) ? fuYouConfig.getPcQuickPayPageUrl() : pageUrl)
                .setBack_notify_url(fuYouConfig.getPcQuickPayAsyncUrl());

        String rsa = generateEncrypt(request, "RSA", null, false, "user_type")
                .replace("\r\n","");// 删除换行符
        return request.setRSA(rsa);
    }

    /**
     * pc网银支付接口 根据参数构造请求对象 {@link FuYouPCBankPayApi.Request}
     *
     * @param orderId      订单id
     * @param orderAmt     订单金额，以分为单位，整数，例如 10.38元，则传入 "1038"
     * @param orderPayType 支付类型
     * @param bankCode     银行代码
     * @return 请求对象
     */
    @SneakyThrows
    private FuYouPCBankPayApi.Request buildPCWebBankPayRequest(String orderId, String orderAmt,
                                                               FuYouPCBankPayApi.OrderPayTypeEnum orderPayType,
                                                                  String bankCode) {
        FuYouPCBankPayApi.Request request = new FuYouPCBankPayApi.Request()
                .setMchnt_cd(fuYouConfig.getMchntCd()).setOrder_id(orderId)
                .setOrder_amt(orderAmt).setOrder_pay_type(orderPayType.getCode())
                .setPage_notify_url(fuYouConfig.getPcSyncNotifyUrl())
                .setBack_notify_url(fuYouConfig.getPcAsyncNotifyUrl())
                .setIss_ins_cd(bankCode);
        // 设置md5
        return request.setMd5(generateMd5OfMd5(request, fuYouConfig.getGatewayPayMchntKey()));
    }


    /**
     * h5手机支付 根据参数构造请求对象 {@link FuYouWapPayApi.WrapRequest}
     *
     * @param requestId  请求id
     * @param userId     用户id
     * @param amt        金额
     * @param bankCard   银行卡号
     * @param name       姓名
     * @param cardNumber 身份证号
     * @param pageUrl 同步url
     * @return 请求对象
     */
    @SneakyThrows
    private FuYouWapPayApi.WrapRequest buildWapPayRequest(String requestId, String userId, String amt, String bankCard,
                                                        String name, String cardNumber,String pageUrl) {
        FuYouWapPayApi.Request request = new FuYouWapPayApi.Request();
        request.setOrderId(requestId).setUserId(userId).setAmt(amt).setBankCard(bankCard)
                .setName(name).setCardNumber(cardNumber).setBackUrl(fuYouConfig.getH5AsyncNotifyUrl())
                .setFailedUrl(StringUtils.isBlank(pageUrl) ? fuYouConfig.getH5SyncUrl() :pageUrl)
                .setSuccessUrl(StringUtils.isBlank(pageUrl) ?fuYouConfig.getH5SyncUrl() : pageUrl)
                .setMchntCd(fuYouConfig.getMchntCd())
                .setSign(generateMd5OfSign(request, fuYouConfig.getH5PayMchntKey(), "signType"));
        String xml = XmlUtil.beanToXmlString(request).substring(55);
        return new FuYouWapPayApi.WrapRequest().setMCHNTCD(fuYouConfig.getMchntCd()).setFM(xml);
    }



    /**
     * 代付接口 根据参数构造请求对象 {@link ReplacePayWrapRequest}
     *
     * @param bankCode    银行代码 // * @param areaCode 地区代码 // * @param branchName 支行名
     * @param accountName 用户姓名
     * @param bankCard    用户银行卡号
     * @param amt         金额，单位：分
     * @param orderId     订单id
     * @param phone       手机号
     * @param requestId   请求流水号
     * @param date        请求日期 yyyyMMdd
     * @return 请求对象
     */
    public ReplacePayWrapRequest buildReplacePayRequest(String bankCode, String accountName, String bankCard,
                                                        String amt, String orderId, String phone, String requestId,
                                                        String date) {
        // 如果是默认的15家银行
        String areaCode;
        if (ArrayUtils.contains(fuYouConfig.getDefaultBankCodeArray(), bankCode)) {
            areaCode = "1000";
        } else {
            throw new PayException("暂不支持该银行");
        }

        FuYouReplacePayApi.Request request = new FuYouReplacePayApi.Request().setAccount(bankCard).setAccountName(accountName).setAmt(amt)
                .setAreaCode(areaCode).setBankCode(bankCode).setOrderId(orderId).setMemo(orderId).setMobile(phone).setDate(date).setTodayOrderId(requestId);
        String xml = XmlUtil.beanToXmlString(request);
        ReplacePayWrapRequest wrapRequest = new ReplacePayWrapRequest()
                .setMerid(fuYouConfig.getMchntCd()).setReqtype(FuYouRequestTypeEnum.REPLACE_PAY.getCode()).setXml(xml);

        String md5 = Md5Util.toMd5(StringUtils.join(new String[]{wrapRequest.getMerid(),
                fuYouConfig.getGatewayPayMchntKey(), wrapRequest.getReqtype(), xml}, "|"));
        return wrapRequest.setMac(md5);
    }

    /**
     * 代付查询接口 根据参数构造请求对象 {@link ReplacePayWrapRequest}
     *
     * @param todayOrderId 请求流水
     * @param startDate         请求日期 yyyyMMdd
     * @return 请求对象
     */
    private ReplacePayWrapRequest buildReplacePayQueryRequest(String todayOrderId, Date startDate) {
        FuYouReplacePayQueryApi.Request request = new FuYouReplacePayQueryApi.Request();
        // 结束日期为 起始日期后10天，或 当前时间（如果结束日期大于当前日期了）
        Date endDate = DateUtil.addDayFroDateAfterNow(startDate, 10);
        request.setTodayOrderId(todayOrderId)
                .setStartDate(DateUtil.format(startDate,DateUtil.FMT_YMD))
                .setEndDate(DateUtil.format(endDate,DateUtil.FMT_YMD));
        String xml = XmlUtil.beanToXmlString(request);
        ReplacePayWrapRequest wrapRequest = new ReplacePayWrapRequest()
                .setMerid(fuYouConfig.getMchntCd())
                .setReqtype(FuYouRequestTypeEnum.REPLACE_PAY_QUERY.getCode()).setXml(xml);
        String md5 = Md5Util.toMd5(StringUtils.join(new String[]{wrapRequest.getMerid(),
                fuYouConfig.getGatewayPayMchntKey(), wrapRequest.getReqtype(), xml}, "|"));
        return wrapRequest.setMac(md5);
    }

    /**
     * 根据请求对象生成md5或rsa加密
     *
     * @param encryptFieldName 对象中加密字段的名字
     */
    @SneakyThrows
    private <T> String generateEncrypt(T request, String encryptFieldName, String key, boolean isMd5, String... skipFields) {

        // 是否跳过某些字段
        boolean isSkip = !ArrayUtils.isEmpty(skipFields);
        Map<String, String> map = BeanUtil.beanToOrderlyMap(request);
        StringBuilder md5StringBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (isSkip && ArrayUtils.contains(skipFields, entry.getKey())) {
                continue;
            }

            if (StringUtils.isNotEmpty(entry.getValue())) {
                md5StringBuilder.append("|").append(entry.getValue());
            } else if (!entry.getKey().equals(encryptFieldName)) {
                md5StringBuilder.append("|");
            }
        }
        // RSA无需追加盐
        if (isMd5) {
            md5StringBuilder.append("|").append(key);
        }
        String result = md5StringBuilder.toString().substring(1);
        // 返回
        if (isMd5) {
            return Md5Util.toMd5(result);
        } else {
            return FuYouRSAUtil.encrypt(fuYouConfig.getPcQuickPayMchntKey(), result);
        }
    }

    /**
     * 根据请求对象生成md5,字段名为md5
     */
    @SneakyThrows
    private <T> String generateMd5OfMd5(T request, String key, String... skipFields) {
        return generateEncrypt(request, "md5", key, true, skipFields);
    }

    /**
     * 根据请求对象生成md5,字段名为sign
     */
    @SneakyThrows
    private <T> String generateMd5OfSign(T request, String key, String... skipFields) {
        return generateEncrypt(request, "sign", key, true, skipFields);
    }

    /**
     * 校验返回对象的md5
     *
     * @param response     响应对象
     * @param currentMd5   响应对象的md5
     * @param md5FieldName 响应对象的md5字段名,默认为 "md5"
     * @param skipFields   相应对象中需要跳过校验的字段
     * @param key          密钥，pc网银支付 和 h5支付密钥不同
     * @param <T>          响应对象的类类型
     * @return true:校验通过； false:校验失败
     */
    @SneakyThrows
    public <T> boolean verify(T response, String currentMd5, String md5FieldName, String key, String... skipFields) {
        if (response == null) {
            return false;
        }
        if (StringUtils.isEmpty(md5FieldName)) {
            md5FieldName = "md5";
        }

        // 是否跳过某些字段
        boolean isSkip = !ArrayUtils.isEmpty(skipFields);

        // 设置md5
        Map<String, String> map = BeanUtil.beanToOrderlyMap(response);
        StringBuilder md5StringBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getKey().equals(md5FieldName) || isSkip && ArrayUtils.contains(skipFields, entry.getKey())) {
                continue;
            }
            if (StringUtils.isNotEmpty(entry.getValue())) {
                md5StringBuilder.append("|").append(entry.getValue());
            } else {
                md5StringBuilder.append("|");
            }
        }
        md5StringBuilder.append("|").append(key);
        String md5 = DigestUtils.md5Hex(md5StringBuilder.toString().substring(1));

        return StringUtils.equalsIgnoreCase(md5, currentMd5);
    }

}
