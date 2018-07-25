package com.jia.pay.common.exception;

import com.jia.pay.common.enums.CodeEnum;
import com.jia.pay.common.enums.ErrorEnum;

/**
 * @author  ZhengXing
 * createTime: 2018/7/5
 * desc:  参数异常
 */
public class ParamException extends PayException {

    public ParamException(String message) {
        super(message);
        super.code = ErrorEnum.FORM_ERROR.getCode();
    }
}
