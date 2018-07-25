package com.jia.pay.common.exception;

import com.jia.pay.common.enums.ErrorEnum;

/**
 * @author  ZhengXing
 * createTime: 2018/7/5
 * desc:  http请求异常
 */
public class HttpException extends PayException {

    public HttpException(String message) {
        super(message);
        super.code = ErrorEnum.HTTP_ERROR.getCode();
    }
}
