package com.jia.pay.web.callback;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jia.pay.api.fuyou.FuYouTemplate;
import com.jia.pay.api.fuyou.api.FuYouPCBankPayApi;
import com.jia.pay.api.fuyou.api.FuYouPCQuickPayApi;
import com.jia.pay.api.fuyou.api.FuYouReplacePayRefundApi;
import com.jia.pay.api.fuyou.api.FuYouWapPayApi;
import com.jia.pay.api.fuyou.enums.FuYouErrorCodeEnum;
import com.jia.pay.api.strategy.dto.CommonPayResponse;
import com.jia.pay.api.strategy.dto.ReplacePayQueryCommonResponse;
import com.jia.pay.common.util.EnumUtil;
import com.jia.pay.common.util.Md5Util;
import com.jia.pay.business.CallbackService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author  ZhengXing
 * createTime: 2018/7/5
 * desc:  富有回调
 */
@Controller
@RequestMapping("/fuyou")
@Slf4j
public class FuYouCallbackController {

    private final ObjectMapper objectMapper;
    private final FuYouTemplate fuYouTemplate;
    private final CallbackService callbackService;

    public FuYouCallbackController(ObjectMapper objectMapper, FuYouTemplate fuYouTemplate,
                                   CallbackService callbackService) {
        this.objectMapper = objectMapper;
        this.fuYouTemplate = fuYouTemplate;
        this.callbackService = callbackService;
    }

    /**
     * wap支付异步回调
     */
    @ResponseBody
    @PostMapping("/wap/pay/async")
    @SneakyThrows
    public void async(FuYouWapPayApi.Response response) {
        log.info("[富有][wap支付]异步通知:{}", response == null ? "" : objectMapper.writeValueAsString(response));
        if (response == null) {
            log.error("[富有][wap支付]异步通知:对象为空");
            return;
        }
        // 签名校验
        if (!fuYouTemplate.verify(response, response.getSIGN(), "SIGN", fuYouTemplate.getFuYouConfig().getH5PayMchntKey(), "RESPONSEMSG")) {
            log.error("[富有][wap支付]异步通知：签名校验失败,当前对象:{}", objectMapper.writeValueAsString(response));
            return;
        }
        // 如果是失败响应
        if (!EnumUtil.equals(response.getRESPONSECODE(), FuYouErrorCodeEnum.SUCCESS)) {
            log.error("[富有][wap支付]异步通知，支付失败.订单id:{},异常码:{},异常消息:{}", response.getMCHNTORDERID(),
                    response.getRESPONSECODE(), response.getRESPONSEMSG());
            return;
        }
        callbackService.processPayCallback(r ->
                new CommonPayResponse(r.getORDERID(),
                        r.getRESPONSECODE(), r.getRESPONSEMSG(), true), response);
    }

    /**
     * pc网银支付 异步接口
     */
    @SneakyThrows
    @ResponseBody
    @PostMapping("/pc/bank/async")
    public void pcBankAsync(FuYouPCBankPayApi.Response response) {
        log.info("[富有][pc网银]异步通知:{}", response == null ? "" : objectMapper.writeValueAsString(response));
        if (response == null) {
            log.info("[富有][pc网银]异步通知：对象为空");
            return;
        }
        // 如果是失败响应
        if (!EnumUtil.equals(response.getOrder_pay_code(), FuYouErrorCodeEnum.SUCCESS)) {
            log.info("[富有][pc网银]异步通知，支付失败.订单id:{},异常码:{},异常消息:{}", response.getOrder_id(),
                    response.getOrder_pay_code(), response.getOrder_pay_error());
            return;
        }

        callbackService.processPayCallback(r ->
                new CommonPayResponse(r.getOrder_id(),
                        r.getOrder_pay_code(),
                        FuYouErrorCodeEnum.SUCCESS.getMessage(),
                        true), response);
    }


    /**
     * pc快捷支付 异步接口
     */
    @SneakyThrows
    @ResponseBody
    @PostMapping("/pc/quick/async")
    public void pcQuickAsync(FuYouPCQuickPayApi.Response response) {
        log.info("[富有][pc快捷]异步通知:{}", response == null ? "" : objectMapper.writeValueAsString(response));
        if (response == null) {
            log.info("[富有][pc快捷]异步通知：对象为空");
            return;
        }
        // 如果是失败响应
        if (!EnumUtil.equals(response.getOrder_pay_code(), FuYouErrorCodeEnum.SUCCESS)) {
            log.info("[富有][pc快捷]异步通知，支付失败.订单id:{},异常码:{},异常消息:{}", response.getOrder_id(),
                    response.getOrder_pay_code(), response.getOrder_pay_msg());
            return;
        }

        callbackService.processPayCallback(r ->
                new CommonPayResponse(r.getOrder_id(),
                        r.getOrder_pay_code(), r.getOrder_pay_msg(), true), response);
    }


    /**
     * 代付退票推送
     */
    @SneakyThrows
    @ResponseBody
    @PostMapping("/refund")
    public String refund(FuYouReplacePayRefundApi.Request request) {
        log.info("[富有][退票]异步通知:{}", request == null ? "" : objectMapper.writeValueAsString(request));
        if (request == null) {
            log.error("[富有][退票]对象为空");
            return StringUtils.EMPTY;
        }
        log.info("[富有][退票]请求对象:{}", request);
        String md5 = Md5Util.toMd5(StringUtils.join(
                new String[]{fuYouTemplate.getFuYouConfig().getMchntCd(), fuYouTemplate.getFuYouConfig().getGatewayPayMchntKey(),
                        request.getOrderno(), request.getMerdt(), request.getAccntno(), request.getAmt()}, "|"));
        if (!StringUtils.equalsIgnoreCase(md5, request.getMac())) {
            log.error("[富有][退票]md5校验失败:{}", request);
        }

        ReplacePayQueryCommonResponse commonResponse = new ReplacePayQueryCommonResponse()
                .setCode("refund")
                .setMessage("富有退票")
                .setRawData(request)
                .setRequestId(request.getOrderno())
                .setStatus(false);
        callbackService.processReplacePay(commonResponse.getRequestId(), null, commonResponse);

        return FuYouReplacePayRefundApi.successResponse;
    }


    // 同步---------------------

    /**
     * pc网银支付 同步页面接口
     */
    @PostMapping("/pc/bank/sync")
    public String pcBankPage(FuYouPCBankPayApi.Response response, Model model) {
        model.addAttribute("success", EnumUtil.equals(response.getOrder_pay_code(), FuYouErrorCodeEnum.SUCCESS));
        model.addAttribute("message", response.getOrder_pay_error());
        return "pay/result";
    }

    /**
     * wap支付 同步页面接口
     *
     * 成功有参数，失败无参数
     */
    @RequestMapping("/wap/pay/sync")
    public String h5PaySync(FuYouWapPayApi.Response response) {
        if (response != null && EnumUtil.equals(response.getRESPONSECODE(), FuYouErrorCodeEnum.SUCCESS)) {
            return "pay/static/success";
        }
        return "pay/static/fail";
    }

    /**
     * pc快捷支付 同步页面接口
     */
    @PostMapping("/pc/quick/sync")
    public String pcQuickPage(FuYouPCQuickPayApi.Response response, Model model) {
        boolean success = EnumUtil.equals(response.getOrder_pay_code(), FuYouErrorCodeEnum.SUCCESS);
        String message = response.getOrder_pay_msg();
        model.addAttribute("success", success);
        model.addAttribute("message", message);
        return "pay/result";
    }


}
