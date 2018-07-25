package com.jia.pay.api.lianlian.dto;

import com.jia.pay.api.lianlian.enums.LLCodeEnum;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author  ZhengXing
 * createTime: 2018/6/21
 * desc:  连连 响应
 */
@Data
@Accessors(chain = true)
public class LLBaseResponse {
    /**
     * 结果代码
     * 0000 表示成功
     */
    protected String ret_code;

    /**
     * 结果描述
     * 交易成功
     */
    protected String ret_msg;

    /**
     * 成功
     */
    public LLBaseResponse success() {
        return this.setRet_code(LLCodeEnum.SUCCESS.getCode()).setRet_msg(LLCodeEnum.SUCCESS.getMessage());
    }

    /**
     * 失败
     */
    public LLBaseResponse fail() {
        return this.setRet_code(LLCodeEnum.CUSTOM_ERROR.getCode()).setRet_msg(LLCodeEnum.CUSTOM_ERROR.getMessage());
    }
}
