package com.jia.pay.util;

import com.jia.pay.api.strategy.dto.ReplacePayCommonResponse;
import com.jia.pay.common.dto.ReplacePayDTO;

/**
 * @author  ZhengXing
 * createTime: 2018/7/6
 * desc:
 * {@link ReplacePayCommonResponse} to {@link com.jia.pay.common.dto.ReplacePayDTO}
 */
public class ReplacePayResponseToDTOConvetor {

    public static ReplacePayDTO convert(ReplacePayCommonResponse commonResponse,String requestId) {
        return new ReplacePayDTO()
                .setCode(commonResponse.getCode())
                .setSuccess(commonResponse.getIsSuccess())
                .setRequestId(requestId)
                .setMessage(commonResponse.getMessage());
    }
}
