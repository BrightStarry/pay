package com.jia.pay.business;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jia.pay.api.strategy.replacepay.query.AbstractReplacePayQueryStrategy;
import com.jia.pay.api.strategy.dto.CommonPayResponse;
import com.jia.pay.api.strategy.dto.ReplacePayQueryCommonResponse;
import com.jia.pay.common.enums.PayStatusEnum;
import com.jia.pay.common.exception.PayException;
import com.jia.pay.common.util.EnumUtil;
import com.jia.pay.dao.entity.Request;
import com.jia.pay.service.RequestService;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Function;

/**
 * @author  ZhengXing
 * createTime: 2018/7/5
 * desc:  支付方 回调处理
 */
@Service
@Slf4j
public class
CallbackService {

    private final RequestService requestService;
    private final ObjectMapper objectMapper;

    public CallbackService(RequestService requestService, ObjectMapper objectMapper) {
        this.requestService = requestService;
        this.objectMapper = objectMapper;
    }

    /**
     * 处理支付回调
     * @param fun 原生响应->通用响应函数
     * @param r 原生响应
     */
    @Transactional
    @SneakyThrows
    public <R> void processPayCallback(Function<R, CommonPayResponse> fun, R r) {
        // 转为通用响应
        CommonPayResponse response = fun.apply(r);

        // 查询
        Request request = requestService.selectOneByRequestId(response.getRequestId(),"id,status");
        if (EnumUtil.equals(request.getStatus(), PayStatusEnum.SUCCESS)) {
            throw new PayException("该订单已经支付成功");
        }

        // 修改响应表
        requestService.writeResponse(request.getId(),PayStatusEnum.SUCCESS,
                objectMapper.writeValueAsString(r),response.getMessage());
    }

    /**
     * 处理代付回调
     * @param requestId  请求id
     * @param replacePayQueryStrategy 代付查询策略
     * @param existingResponse 存在的响应，如果该对象为空，才进行查询接口请求
     */
    @Transactional
    @SneakyThrows
    public Boolean processReplacePay(@NonNull String requestId,AbstractReplacePayQueryStrategy replacePayQueryStrategy,
                                     ReplacePayQueryCommonResponse existingResponse) {
        // 查询
        Request request = requestService.selectOneByRequestId(requestId,"id,status,request_time");
        if (!EnumUtil.equals(request.getStatus(), PayStatusEnum.WAIT_VERIFY)) {
            throw new PayException("状态异常");
        }
        if (existingResponse == null) {
            existingResponse = replacePayQueryStrategy.doQuery(requestId, request.getRequestTime());
        }

        if (existingResponse.getStatus() == null) { // 等待
            return existingResponse.getStatus();
        } else if (!existingResponse.getStatus()) { // 失败
            requestService.writeResponse(request.getId(),PayStatusEnum.FAIL,objectMapper.writeValueAsString(existingResponse.getRawData()),
                    existingResponse.getMessage());
        }else{// 成功
            requestService.writeResponse(request.getId(),PayStatusEnum.SUCCESS,objectMapper.writeValueAsString(existingResponse.getRawData()),
                    existingResponse.getMessage());
        }
        return existingResponse.getStatus();
    }
}
