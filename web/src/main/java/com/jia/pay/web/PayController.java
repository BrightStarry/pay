package com.jia.pay.web;

import com.jia.pay.api.strategy.dto.PayCallDTO;
import com.jia.pay.business.PayService;
import com.jia.pay.common.dto.ReplacePayDTO;
import com.jia.pay.common.dto.ResultDTO;
import com.jia.pay.common.enums.PayTypeEnum;
import com.jia.pay.common.enums.PayerEnum;
import com.jia.pay.common.exception.ParamException;
import com.jia.pay.common.exception.PayException;
import com.jia.pay.common.form.BasePayForm;
import com.jia.pay.common.form.PayForm;
import com.jia.pay.common.form.ReplacePayForm;
import com.jia.pay.common.util.EnumUtil;
import com.jia.pay.dao.entity.Caller;
import com.jia.pay.service.CallerService;
import com.jia.pay.service.PayerService;
import com.jia.pay.web.system.ControllerPlus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author  ZhengXing
 * createTime: 2018/7/4
 * desc:  支付
 */
@RestController
@RequestMapping("/pay")
@Slf4j
public class PayController implements ControllerPlus {

    private final PayService payService;
    private final CallerService callerService;
    private final PayerService payerService;

    public PayController(PayService payService, CallerService callerService, PayerService payerService) {
        this.payService = payService;
        this.callerService = callerService;
        this.payerService = payerService;
    }

    /**
     * 支付方法
     */
    @PostMapping
    public ResultDTO<PayCallDTO> pay(@Valid @RequestBody PayForm form, BindingResult bindingResult) {
        Caller caller = pre(form, bindingResult);
        form.setUserId(caller.getPre().concat(form.getUserId()));// userId增加前缀
        form.setPayTypeEnum(EnumUtil.getByCode(form.getPayType(), PayTypeEnum.class).orElseThrow(() -> new ParamException("payType有误")));
        return ResultDTO.success(payService.pay(form, caller));
    }

    /**
     * 代付
     */
    @PostMapping("/replace")
    public ResultDTO<ReplacePayDTO> replacePay(@Valid @RequestBody ReplacePayForm form, BindingResult bindingResult) {
        Caller caller = pre(form, bindingResult);
        return ResultDTO.success(payService.replacePay(form, caller));
    }

    /**
     * 前奏
     */
    private Caller pre(BasePayForm form, BindingResult bindingResult) {
        log.info("请求参数:{}", form);
        verify(bindingResult);
        form.setPayerEnum(EnumUtil.getByCode(form.getPayerCode(), PayerEnum.class).orElseThrow(() -> new ParamException("payerCode有误")));
        // 验证payer是否可用
        if (!payerService.verifyByPayerCode(form.getPayerEnum())) {
            throw new PayException("payer不可用");
        }
        // 获取并验证 caller
        return  callerService.selectOneByToken(form.getToken());
    }
}
