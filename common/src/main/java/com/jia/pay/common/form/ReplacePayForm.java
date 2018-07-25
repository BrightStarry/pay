package com.jia.pay.common.form;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author  ZhengXing
 * createTime: 2018/7/4
 * desc:  代付接口
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class ReplacePayForm extends BasePayForm {


    /**
     * 用户手机号
     */
    @NotBlank(message = "手机号不能为空")
    protected String phone;


    /**
     * 姓名
     */
    @NotBlank(message = "姓名不能为空")
    @Length(min = 2,message = "姓名长度过小(min:2)")
    protected String name;


    /**
     * 银行卡号
     */
    @NotBlank(message = "银行卡号不能为空")
    protected String bankCard;

}
