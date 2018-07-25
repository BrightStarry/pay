package com.jia.pay.common.form;

import com.jia.pay.common.enums.PayTypeEnum;
import com.jia.pay.common.enums.PayerEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

/**
 * @author  ZhengXing
 * createTime: 2018/7/5
 * desc:  基础支付参数
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper =true)
public class BasePayForm extends BaseForm{
    /**
     * 支付方code
     * see {@link com.jia.pay.common.enums.PayerEnum}
     */
    @NotBlank(message = "payerCode不能为空")
    @Length(min = 4,max = 4,message = "payerCode长度有误(4)")
    protected String payerCode;

    /**
     * 金额
     * 保留两位以内小数
     */
    @NotBlank(message = "金额不能为空")
    @Pattern(regexp = "(^[1-9](\\d+)?(\\.\\d{1,2})?$)|(^0$)|(^\\d\\.\\d{1,2}$)",message = "money格式不正确")
    protected String money;


    /**
     * 银行code
     */
    @NotBlank(message = "bankCode不能为空")
    @Length(min = 4,max = 4,message = "bankCode长度有误(4)")
    protected String bankCode;

    /**
     * 订单id 调用方订单id，选传
     */
    protected String orderId;

    //  自行转换的参数 无需传入
    protected PayTypeEnum payTypeEnum;
    protected PayerEnum payerEnum;
    // 支付方的银行code
    private String payerBankCode;
}
