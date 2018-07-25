package com.jia.pay.web.system;

import com.jia.pay.common.exception.ParamException;
import org.springframework.validation.BindingResult;

/**
 * @author  ZhengXing
 * createTime: 2018/7/4
 * desc:  controller 扩展
 */
public interface ControllerPlus {

    /**
     * 参数校验
     */
    default void verify(BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new ParamException(bindingResult.getFieldError().getDefaultMessage());
        }
    }

}
