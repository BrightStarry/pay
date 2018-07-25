package com.jia.pay.common.form;

import com.jia.pay.common.enums.PayTypeEnum;
import com.jia.pay.common.enums.PayerEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * @author  ZhengXing
 * createTime: 2018/7/9
 * desc:  银行列表
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class BankListForm extends BaseForm{

    /**
     * 支付方code
     * see {@link com.jia.pay.common.enums.PayerEnum}
     */
    @NotBlank(message = "payerCode不能为空")
    @Length(min = 4,max = 4,message = "payerCode长度有误(4)")
    protected String payerCode;

    /**
     * 支付类型 不包含 网银支付
     * see {@link com.jia.pay.common.enums.PayTypeEnum}
     */
    @NotNull(message = "payType不能为空")
    @Range(min = 0,max = 3,message = "payType范围有误(0-3)")
    protected Integer payType;


    // 自定义参数---
    private PayerEnum payerEnum;
    private PayTypeEnum payTypeEnum;
}
