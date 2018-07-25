package com.jia.pay.web.system;

import com.jia.pay.common.enums.ErrorEnum;
import com.jia.pay.common.exception.PayException;
import com.jia.pay.config.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * author:ZhengXing
 * datetime:2018-03-11 6:36
 * 异常处理类
 */
@ControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    private static final String FORWARD = "forward:";// 转发

    /**
     * 自定义异常处理
     */
    @ExceptionHandler(PayException.class)
    public String customExceptionHandle(PayException e, HttpServletRequest request) {
        log.error("[异常处理]业务异常:",e);
        setCodeAndMessage(request,e.getCode(),e.getMessage());
        return FORWARD + Constant.ERROR_PATH;
    }

    /**
     * 未受检异常处理
     */
    @ExceptionHandler(Exception.class)
    public String exceptionHandler(Exception e, HttpServletRequest request) {
        log.error("[异常处理]未知异常:",e);
        setCodeAndMessage(request, ErrorEnum.UNKNOWN_ERROR.getCode(), ErrorEnum.UNKNOWN_ERROR.getMessage());
        return FORWARD + Constant.ERROR_PATH;
    }

    /**
     * 给request设置code和message属性
     */
    private void setCodeAndMessage(HttpServletRequest request, String code, String message) {
        request.setAttribute(Constant.ERROR_CODE_FIELD,code);
        request.setAttribute(Constant.ERROR_MESSAGE_FIELD,message);
    }
}
