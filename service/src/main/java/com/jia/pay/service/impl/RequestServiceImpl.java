package com.jia.pay.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jia.pay.common.enums.PayStatusEnum;
import com.jia.pay.common.enums.PayTypeEnum;
import com.jia.pay.common.enums.PayerEnum;
import com.jia.pay.common.exception.PayException;
import com.jia.pay.dao.entity.Request;
import com.jia.pay.dao.mapper.RequestMapper;
import com.jia.pay.service.RequestService;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 调用表 服务实现类
 * </p>
 *
 * @author zx
 * @since 2018-07-04
 */
@Service
public class RequestServiceImpl extends ServiceImpl<RequestMapper, Request> implements RequestService {

    /**
     * 生成请求记录
     *
     * @param requestId     请求id
     * @param callerId      回调id
     * @param relateId      关联id
     * @param payerEnum     支付方
     * @param payTypeEnum   支付类型
     * @param requestBody   请求信息
     * @param payStatusEnum 支付状态
     * @param syncResponse  同步响应
     * @param requestTime   请求时间
     */
    @Override
    public void insertRequest(String requestId, Long callerId, String relateId, PayerEnum payerEnum,
                              PayTypeEnum payTypeEnum, String requestBody, PayStatusEnum payStatusEnum,
                              String syncResponse, Date requestTime) {
        Request request = new Request()
                .setRequestId(requestId)
                .setCallerId(callerId)
                .setRelateId(relateId)
                .setPayerCode(payerEnum.getCode())
                .setPayType(payTypeEnum.getCode())
                .setRequestBody(requestBody)
                .setStatus(payStatusEnum.getCode())
                .setSyncResponse(syncResponse)
                .setRequestTime(requestTime);
        this.insert(request);
    }

    /**
     * 修改记录，写入响应
     *
     * @param id              id
     * @param payStatusEnum   支付状态
     * @param asyncResponse   异步响应
     * @param responseMessage 响应消息
     */
    @Override
    public void writeResponse(Long id, PayStatusEnum payStatusEnum, String asyncResponse,
                              String responseMessage) {
        Request updateRequest = new Request();
        updateRequest.setAsyncResponseTime(new Date()).setId(id);
        if (payStatusEnum != null) {
            updateRequest.setStatus(payStatusEnum.getCode());
        }
        if (StringUtils.isNotBlank(asyncResponse)) {
            updateRequest.setAsyncResponse(asyncResponse);
        }
        if (StringUtils.isNotBlank(responseMessage)) {
            updateRequest.setResponseMessage(responseMessage);
        }
        updateById(updateRequest);
    }


    /**
     * 根据 requestId 查询记录
     */
    @Override
    public Request selectOneByRequestId(String requestId) {
        return selectOneByRequestId(requestId, null);
    }

    /**
     * 根据 requestId 查询记录,可自定义select  xxx
     *
     * @param select 要查询的字段，例如 "name,age"，为blank时，查询全部字段(具体查看源码中setSqlSelect方法判断)
     */
    @Override
    public Request selectOneByRequestId(String requestId, String select) {
        Request request = this.selectOne(
                new EntityWrapper<Request>().setSqlSelect(select)
                        .eq("request_id", requestId)
        );
        if (request == null) {
            throw new PayException("请求记录不存在");
        }
        return request;
    }

    /**
     * 根据 payType /  status 查询记录，可自定义select
     *
     * @param select 要查询的字段，例如 "name,age"，为blank时，查询全部字段(具体查看源码中setSqlSelect方法判断)
     */
    @Override
    public List<Request> selectByPayTypeAndStatus(PayTypeEnum payTypeEnum, PayStatusEnum payStatusEnum, String select) {
        return selectList(
                new EntityWrapper<Request>().setSqlSelect(select)
                        .eq(payTypeEnum != null, "pay_type", payTypeEnum.getCode())
                        .eq(payStatusEnum != null, "status", payStatusEnum.getCode())
        );
    }

    /**
     * 根据 callerId/ in requestIds / in relateId 批量查询，可自定义select
     * @param callerId 调用者id
     * @param requestIds 请求id集合, 二选一 empty时不会进行判断
     * @param relateIds 关联id集合， 二选一 empty时不会进行判断
     * @param select 要查询的字段，例如 "name,age"，为blank时，查询全部字段(具体查看源码中setSqlSelect方法判断)
     */
    @Override
    public List<Request> selectByCallerAndInRequestOrRelateIds(@NonNull Long callerId,
                                                               List<String> requestIds,
                                                               List<String> relateIds, String select) {
        if (CollectionUtils.isEmpty(requestIds) && CollectionUtils.isEmpty(relateIds)) {
            return new ArrayList<>();
        }
        return selectList(
                new EntityWrapper<Request>().setSqlSelect(select)
                        .eq("caller_id",callerId)
                        .in("request_id", requestIds)// empty时，不会进行判断
                        .in("relate_id", relateIds)
        );
    }
}
