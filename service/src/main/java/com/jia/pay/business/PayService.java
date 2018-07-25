package com.jia.pay.business;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jia.pay.api.strategy.dto.PayCallDTO;
import com.jia.pay.api.strategy.dto.ReplacePayCommonResponse;
import com.jia.pay.api.strategy.pay.AbstractPayStrategy;
import com.jia.pay.api.strategy.replacepay.AbstractReplacePayStrategy;
import com.jia.pay.common.dto.ReplacePayDTO;
import com.jia.pay.common.enums.*;
import com.jia.pay.common.exception.ParamException;
import com.jia.pay.common.exception.PayException;
import com.jia.pay.common.form.BasePayForm;
import com.jia.pay.common.form.PayForm;
import com.jia.pay.common.form.ReplacePayForm;
import com.jia.pay.common.util.EnumUtil;
import com.jia.pay.common.util.MoneyConvertUtil;
import com.jia.pay.common.util.TokenUtil;
import com.jia.pay.dao.entity.Caller;
import com.jia.pay.dao.entity.Request;
import com.jia.pay.service.PayerBankService;
import com.jia.pay.service.RequestService;
import com.jia.pay.util.PayFormCompareUtil;
import com.jia.pay.util.ReplacePayResponseToDTOConvetor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author ZhengXing
 * createTime: 2018/7/4
 * desc:  支付 服务
 */
@Service
@Slf4j
public class PayService {
    private final RequestService requestService;
    private final PayerBankService payerBankService;
    private final List<AbstractPayStrategy> payStrategyList;
    private final List<AbstractReplacePayStrategy> replacePayStrategyList;
    private final ObjectMapper objectMapper;

    public PayService(RequestService requestService, PayerBankService payerBankService,
                      List<AbstractPayStrategy> payStrategyList,
                      List<AbstractReplacePayStrategy> replacePayStrategyList,
                      ObjectMapper objectMapper) {
        this.requestService = requestService;
        this.payerBankService = payerBankService;
        this.payStrategyList = payStrategyList;
        this.replacePayStrategyList = replacePayStrategyList;
        this.objectMapper = objectMapper;
    }

    // 代付-------------

    /**
     * 代付
     */
    @Transactional
    @SneakyThrows
    public ReplacePayDTO replacePay(ReplacePayForm form, Caller caller) {
        // 请求id
        String requestId = TokenUtil.generateRequestId();
        //代付策略
        AbstractReplacePayStrategy replacePayStrategy = replacePayStrategyList.get(form.getPayerEnum().getSort());
        // 增加记录
        requestService.insertRequest(requestId, caller.getId(), form.getOrderId(), form.getPayerEnum(), PayTypeEnum.REPLACE_PAY,
                objectMapper.writeValueAsString(form), PayStatusEnum.WAIT_VERIFY, objectMapper.writeValueAsString(form), new Date());
        // 调用接口
        ReplacePayCommonResponse commonResponse = callReplacePay(form, requestId, replacePayStrategy);
        // 调用失败，则抛出异常,不增加记录
        if (!commonResponse.getIsSuccess()) {
            throw new PayException("调用异常:" + commonResponse.getMessage());
        }
        return ReplacePayResponseToDTOConvetor.convert(commonResponse, requestId);
    }

    /**
     * 调用代付
     */
    @SuppressWarnings("unchecked")
    private ReplacePayCommonResponse callReplacePay(ReplacePayForm form, String requestId, AbstractReplacePayStrategy replacePayStrategy) {
        // 获取/转换必要参数
        payerBankCodeConvert(form);
        moneyConvert(form);
        // 请求
        Object response = replacePayStrategy.doRequest(form.getPayerBankCode(), form.getName(), form.getBankCard(), form.getMoney(),
                form.getPhone(), requestId);
        // 构建通用响应
        return replacePayStrategy.toCommonResponse(response);
    }


    // 支付-------------------

    /**
     * 支付
     */
    @Transactional
    @SneakyThrows
    public PayCallDTO pay(PayForm form, Caller caller) {
        // 处理重复支付请求
        if (StringUtils.isNotBlank(form.getRequestId())) {
            return processAgainPay(form);
        }

        // 请求id为空，新请求
        String requestId = TokenUtil.generateRequestId();
        // 增加记录
        requestService.insertRequest(
                requestId,
                caller.getId(),
                form.getOrderId(),
                form.getPayerEnum(),
                form.getPayTypeEnum(),
                objectMapper.writeValueAsString(form),
                PayStatusEnum.WAIT_VERIFY,
                null, new Date());
        // 调用支付
        return callPay(form, requestId);
    }

    /**
     * 处理再次的支付请求
     * 携带requestId
     */
    @SneakyThrows
    private PayCallDTO processAgainPay(PayForm form) {
        // 查询之前是否有该请求,
        Request request = requestService.selectOneByRequestId(form.getRequestId());
        if (request == null) {
            throw new PayException("重复请求-该请求Id有误");
        }
        if (!EnumUtil.equals(request.getStatus(), PayStatusEnum.WAIT_VERIFY)) {
            throw new PayException("重复请求-该请求已经成功");
        }
        // 上次请求form，并设置上本次的请求id
        PayForm lastForm = objectMapper.readValue(request.getRequestBody(), PayForm.class).setRequestId(form.getRequestId());
        String diffField;
        if ((diffField = PayFormCompareUtil.compare(lastForm, form)) != null) {
            throw new ParamException("重复请求-参数有误:" + diffField + "不一致");
        }
        return callPay(form, form.getRequestId());
    }


    /**
     * 调用支付方法
     */

    private PayCallDTO callPay(PayForm form, String requestId) {
        // 获取/转换必要参数
        payerBankCodeConvert(form);
        moneyConvert(form);
        AbstractPayStrategy payStrategy = payStrategyList.get(form.getPayerEnum().getSort());
        PayCallDTO payCallDTO = null;
        switch (form.getPayTypeEnum()) {
            case WAP_PAY:
                payCallDTO = payStrategy.wapPay(form, requestId);
                break;
            case PC_BANK_PAY:
                payCallDTO = payStrategy.pcBankPay(form, requestId);
                break;
            case PC_QUICK_PAY:
                payCallDTO = payStrategy.pcQuickPay(form, requestId);
                break;
            default:
                throw new PayException(ErrorEnum.INTERNAL_ERROR);
        }
        return payCallDTO;
    }


    // 通用-------

    /**
     * 银行代码转换
     * 如果 bankCode不为空，将其转换为 对应支付方处的 payerBankCode
     */
    private void payerBankCodeConvert(BasePayForm form) {
        if (StringUtils.isBlank(form.getBankCode())) {
            return;
        }
        // 支付方中的类型，防止同一支付方多个bankCode
        PayerBankPayerTypeEnum payerBankPayerTypeEnum = PayerBankPayerTypeEnum.DEFAULT;
        // 富有，网银支付类型
        if (EnumUtil.equals(form.getPayTypeEnum(), PayTypeEnum.PC_BANK_PAY)
                && EnumUtil.equals(form.getPayerEnum(), PayerEnum.FUYOU)) {
            payerBankPayerTypeEnum = PayerBankPayerTypeEnum.FUYOU_PC_BANK_PAY;
        }
        //查询
        String bankCode = payerBankService.selectBankCode(form.getPayerEnum(), form.getBankCode(), payerBankPayerTypeEnum);
        if (StringUtils.isBlank(bankCode)) {
            throw new PayException("bankCode不存在");
        }
        form.setPayerBankCode(bankCode);
    }

    /**
     * 金额转换
     */
    private void moneyConvert(BasePayForm form) {
        switch (form.getPayerEnum()) {
            case FUYOU:
                form.setMoney(MoneyConvertUtil.convertToDeciUnit(form.getMoney()));
                break;
            case LIANLIAN:
                form.setMoney(MoneyConvertUtil.convertToTowDecimal(form.getMoney()));
                break;
        }
    }


}
