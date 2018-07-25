package com.jia.pay.service;

import com.jia.pay.common.enums.PayStatusEnum;
import com.jia.pay.common.enums.PayTypeEnum;
import com.jia.pay.common.enums.PayerEnum;
import com.jia.pay.dao.entity.Request;
import lombok.NonNull;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 调用表 服务类
 * </p>
 *
 * @author zx
 * @since 2018-07-04
 */
public interface RequestService extends SuperService<Request>{

    void insertRequest(String requestId, Long callerId, String relateId, PayerEnum payerEnum,
                       PayTypeEnum payTypeEnum, String requestBody, PayStatusEnum payStatusEnum,
                       String syncResponse, Date requestTime);

    void writeResponse(Long id, PayStatusEnum payStatusEnum, String asyncResponse,
                       String responseMessage);

    Request selectOneByRequestId(String requestId);

    Request selectOneByRequestId(String requestId, String select);

    List<Request> selectByPayTypeAndStatus(PayTypeEnum payTypeEnum, PayStatusEnum payStatusEnum, String select);


    List<Request> selectByCallerAndInRequestOrRelateIds(@NonNull Long callerId, List<String> requestIds, List<String> relateIds, String select);
}
