package com.jia.pay.web;

import com.fasterxml.jackson.annotation.JsonView;
import com.jia.pay.business.QueryService;
import com.jia.pay.common.dto.RequestDTO;
import com.jia.pay.common.dto.ResultDTO;
import com.jia.pay.common.enums.PayTypeEnum;
import com.jia.pay.common.enums.PayerEnum;
import com.jia.pay.common.exception.ParamException;
import com.jia.pay.common.form.BankListForm;
import com.jia.pay.common.form.RequestListForm;
import com.jia.pay.common.util.EnumUtil;
import com.jia.pay.dao.entity.BankQuota;
import com.jia.pay.dao.entity.Caller;
import com.jia.pay.service.CallerService;
import com.jia.pay.web.system.ControllerPlus;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author  ZhengXing
 * createTime: 2018/7/5
 * desc:  查询
 */
@RestController
@RequestMapping("/query")
@Slf4j
public class QueryController implements ControllerPlus{

    private final QueryService queryService;
    private final CallerService callerService;

    public QueryController(QueryService queryService, CallerService callerService) {
        this.queryService = queryService;
        this.callerService = callerService;
    }

    /**
     * 批量查询请求记录结果，根据 requestId or relateId
     */
    @PostMapping("/request/list")
    public ResultDTO<List<RequestDTO>> requestList(@Valid @RequestBody RequestListForm form, BindingResult bindingResult) {
        verify(bindingResult);
        Caller caller = callerService.selectOneByToken(form.getToken());
        if (CollectionUtils.isEmpty(form.getRelateIds()) && CollectionUtils.isEmpty(form.getRequestIds())) {
            throw new ParamException("requestIds和relateIds不能都为空");
        }
        if (CollectionUtils.isNotEmpty(form.getRelateIds()) && CollectionUtils.isNotEmpty(form.getRequestIds())) {
            throw new ParamException("requestIds和relateIds不能都不为空");
        }
        List<RequestDTO> list = queryService.batchQueryRequest(caller, form.getRequestIds(), form.getRelateIds());
        return ResultDTO.success(list);
    }

    /**
     * 查询 银行限额， 根据 payerCode / payerType（默认0）
     * 限额,0表示未知,-1表示无限
     */
    @JsonView(BankQuota.BankListView.class)
    @PostMapping("/bank/list")
    public ResultDTO<List<BankQuota>> bankList(@Valid @RequestBody BankListForm form, BindingResult bindingResult) {
        verify(bindingResult);
        callerService.selectOneByToken(form.getToken());
        form.setPayerEnum(EnumUtil.getByCode(form.getPayerCode(), PayerEnum.class).orElseThrow(() -> new ParamException("payerCode有误")));
        form.setPayTypeEnum(EnumUtil.getByCode(form.getPayType(), PayTypeEnum.class).orElseThrow(() -> new ParamException("payType有误")));
        if (EnumUtil.equals(form.getPayTypeEnum(), PayTypeEnum.PC_BANK_PAY)) {
            throw new ParamException("网银支付无法查询限额");
        }
        List<BankQuota> list = queryService.selectBankQuota(form.getPayerEnum(),form.getPayTypeEnum());
        return ResultDTO.success(list);
    }


}
