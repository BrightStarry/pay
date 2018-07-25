package com.jia.pay.common.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author  ZhengXing
 * createTime: 2018/7/6
 * desc:  基础参数
 */
@Data
public class BaseForm {

    /**
     * 令牌
     */
    @NotBlank(message = "token不能为空")
    @Length(min = 32,max = 32,message = "token长度有误(32)")
    protected String token;
}
