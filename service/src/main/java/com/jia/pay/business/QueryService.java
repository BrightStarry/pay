package com.jia.pay.business;

import com.jia.pay.common.dto.RequestDTO;
import com.jia.pay.common.enums.PayTypeEnum;
import com.jia.pay.common.enums.PayerEnum;
import com.jia.pay.dao.entity.BankQuota;
import com.jia.pay.dao.entity.Caller;
import com.jia.pay.dao.entity.Request;
import com.jia.pay.service.BankQuotaService;
import com.jia.pay.service.RequestService;
import com.jia.pay.util.RequestToRequestDTOConvetor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author  ZhengXing
 * createTime: 2018/7/5
 * desc:  查询相关
 */
@Service
@Slf4j
public class QueryService {

    private final RequestService requestService;
    private final BankQuotaService bankQuotaService;

    public QueryService(RequestService requestService, BankQuotaService bankQuotaService) {
        this.requestService = requestService;
        this.bankQuotaService = bankQuotaService;
    }

    /**
     * 批量查询 requestIds / relateIds
     */
    public List<RequestDTO> batchQueryRequest(@NonNull Caller caller, List<String> requestIds, List<String> relateIds) {
        List<Request> list = requestService.selectByCallerAndInRequestOrRelateIds(caller.getId(),requestIds, relateIds,
                "request_id,relate_id,status,response_message");
        return RequestToRequestDTOConvetor.convert(list);
    }

    /**
     * 查询 银行限额 列表
     */
    public List<BankQuota> selectBankQuota(PayerEnum payerEnum, PayTypeEnum payTypeEnum) {
        return bankQuotaService.selectByPayerAndPayType(payerEnum, payTypeEnum);
    }



}

