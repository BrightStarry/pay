package com.jia.pay.web.callback;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jia.pay.api.lianlian.LLTemplate;
import com.jia.pay.api.lianlian.api.LLReplacePayAPI;
import com.jia.pay.api.lianlian.api.LLWapQuickAPI;
import com.jia.pay.api.lianlian.api.LLWebQuickAPI;
import com.jia.pay.api.lianlian.dto.LLBaseParam;
import com.jia.pay.api.lianlian.dto.LLBaseResponse;
import com.jia.pay.api.lianlian.enums.LLReplacePayResultEnum;
import com.jia.pay.api.lianlian.enums.LLWebQuickPayResultEnum;
import com.jia.pay.api.strategy.dto.CommonPayResponse;
import com.jia.pay.api.strategy.dto.ReplacePayQueryCommonResponse;
import com.jia.pay.common.enums.ErrorEnum;
import com.jia.pay.common.exception.PayException;
import com.jia.pay.common.util.EnumUtil;
import com.jia.pay.business.CallbackService;
import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

/**
 * @author  ZhengXing
 * createTime: 2018/7/5
 * desc:  连连回调
 */
@Controller
@RequestMapping("/lianlian")
@Slf4j
public class LLCallbackController {
    private final LLTemplate llTemplate;
    private final ObjectMapper objectMapper;
    private final CallbackService callbackService;

    public LLCallbackController(LLTemplate llTemplate, ObjectMapper objectMapper, CallbackService callbackService) {
        this.llTemplate = llTemplate;
        this.objectMapper = objectMapper;
        this.callbackService = callbackService;
    }

    /**
     * wap快捷支付异步回调
     * 成功才通知，失败和异常不通知，商户通过订单结果查询来查询订单状态
     */
    @ResponseBody
    @PostMapping("/wap/pay/async")
    public LLBaseResponse wapPayAsync(HttpServletRequest request) {
        LLReplacePayAPI.AsyncResponseReturn result = new LLReplacePayAPI.AsyncResponseReturn();
        LLWapQuickAPI.AsyncResponse response = null;
        if ((response = getAndVerifyRequest(request, "[wap快捷支付]异步通知", LLWapQuickAPI.AsyncResponse.class)) == null) {
            return result.fail();
        }
        if (!EnumUtil.equals(response.getResult_pay(), LLWebQuickPayResultEnum.SUCCESS)) {
            log.error("[连连wap异步回调]收到错误响应:{}", response);
            return result.fail();
        }

        callbackService.processPayCallback(r ->
                        new CommonPayResponse(r.getNo_order(),
                                r.getResult_pay(), LLWebQuickPayResultEnum.SUCCESS.getMessage(), true),
                response);
        return result.success();
    }

    /**
     * pc快捷支付&网银支付 异步
     * 成功才会通知
     */
    @ResponseBody
    @PostMapping("/pc/pay/async")
    @SneakyThrows
    public LLBaseResponse pcQuickAsync(HttpServletRequest request) {
        LLWebQuickAPI.AsyncResponseReturn result = new LLWebQuickAPI.AsyncResponseReturn();
        LLWebQuickAPI.AsyncResponse response = null;
        if ((response = getAndVerifyRequest(request, "[连连][pc支付]异步通知", LLWebQuickAPI.AsyncResponse.class)) == null) {
            return result.fail();
        }
        if (!EnumUtil.equals(response.getResult_pay(), LLWebQuickPayResultEnum.SUCCESS)) {
            log.error("[连连][pc支付]支付失败:{}", response);
            return result.fail();
        }
        callbackService.processPayCallback(r ->
                new CommonPayResponse(r.getNo_order(),
                        r.getResult_pay(), LLWebQuickPayResultEnum.SUCCESS.getMessage(), true), response);
        return result.success();
    }

    /**
     * 代付异步通知
     */
    @ResponseBody
    @PostMapping("/replacePay/async")
    public LLBaseResponse replacePayAsync(HttpServletRequest request) {
        LLReplacePayAPI.AsyncResponseReturn r = new LLReplacePayAPI.AsyncResponseReturn();
        LLReplacePayAPI.AsyncResponse response = null;
        if ((response = getAndVerifyRequest(request, "[连连][代付]异步通知", LLReplacePayAPI.AsyncResponse.class)) == null) {
            return r.fail();
        }
        /**
         * 响应 转 通用响应
         */
        LLReplacePayResultEnum resultEnum = EnumUtil.getByCode(response.getResult_pay(), LLReplacePayResultEnum.class)
                .orElseThrow(() -> new PayException("响应异常"));


        // 该异步对象中该属性是异常消息(例如  退保证金_户名错误  ，其中退保证金是自己传入的订单描述)，同步时没有
        // 所以，以下用于将成功响应修改为 "付款成功",而不是 "退保证金"
        Boolean flag = null;
        String message = response.getInfo_order();
        if (LLReplacePayResultEnum.SUCCESS.equals(resultEnum)) {
            flag = true;
            message = LLReplacePayResultEnum.SUCCESS.getMessage();
        } else if (LLReplacePayResultEnum.CANCEL.equals(resultEnum) ||
                LLReplacePayResultEnum.FAILURE.equals(resultEnum) ||
                LLReplacePayResultEnum.CLOSED.equals(resultEnum)) {
            flag = false;
            message = message.split("_")[1];
        }
        // 构建通用响应
        ReplacePayQueryCommonResponse commonResponse = new ReplacePayQueryCommonResponse()
                .setCode(response.getResult_pay())
                .setMessage(message)
                .setRawData(response)
                .setRequestId(response.getNo_order())
                .setStatus(flag);



        // 处理
        callbackService.processReplacePay(commonResponse.getRequestId(), null, commonResponse);

        return r.success();
    }


    // 同步-----------

    /**
     * wap快捷支付同步
     * 成功以POST方式访问商户指定地址（url_return）
     * （失败和异常只以GET方式返回指定地址，不返回参数，商户可通过订单结果查询来查询订单状态），
     * 以res_data参数返回json串，json返回串中包含所有参数信息。
     */
    @RequestMapping("/wap/pay/sync")
    public String wapPaySync(HttpServletRequest request, @RequestParam(value = "res_data", required = false) String jsonData) {
        // 失败
        if (HttpMethod.GET.matches(request.getMethod())) {
            return "pay/static/fail";
        }
        // 成功
        return "pay/static/success";
    }

    /**
     * pc快捷支付同步
     */
    @PostMapping("/pc/pay/sync")
    public String webQuickPaySync(LLWebQuickAPI.SyncResponse response, Model model) {
        LLWebQuickPayResultEnum resultEnum = EnumUtil.getByCode(response.getResult_pay(), LLWebQuickPayResultEnum.class)
                .orElseThrow(() -> new PayException(ErrorEnum.INTERNAL_ERROR));
        model.addAttribute("success", EnumUtil.equals(response.getResult_pay(), LLWebQuickPayResultEnum.SUCCESS));
        model.addAttribute("message", resultEnum.getMessage());
        return "pay/result";
    }

    /**
     * 读取参数，并校验签名
     */
    @SneakyThrows
    private <R extends LLBaseParam> R getAndVerifyRequest(HttpServletRequest request, String logPre, Class<R> rClass) {
        @Cleanup ServletInputStream in = request.getInputStream();
        String jsonStr = IOUtils.toString(in, "UTF-8");
        if (StringUtils.isBlank(jsonStr)) {
            log.error("[连连]{}：对象为空", logPre);
            return null;
        }
        R response = objectMapper.readValue(jsonStr, rClass);
        if (response == null) {
            log.error("[连连]异步通知：对象为空");
            return null;
        }
        if (!llTemplate.verifySign(response)) {
            log.error("[连连]异步通知：签名校验失败:{}", response);
            return null;
        }
        log.info("[连连]异步通知:{}", response);
        return response;
    }
}
