package com.jia.pay.util;

import com.jia.pay.common.dto.RequestDTO;
import com.jia.pay.dao.entity.Request;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author  ZhengXing
 * createTime: 2018/7/6
 * desc:  转换工具类
 * {@link Request} to {@link RequestDTO}
 */
public class RequestToRequestDTOConvetor {

    public static RequestDTO convert(Request request) {
        return new RequestDTO()
                .setStatus(request.getStatus())
                .setMessage(request.getResponseMessage())
                .setRelateId(request.getRelateId())
                .setRequestId(request.getRequestId());
    }

    public static List<RequestDTO> convert(List<Request> requests) {
        if (CollectionUtils.isEmpty(requests)) {
            return new ArrayList<>();
        }
        return requests.stream().map(RequestToRequestDTOConvetor::convert).collect(Collectors.toList());
    }

}
