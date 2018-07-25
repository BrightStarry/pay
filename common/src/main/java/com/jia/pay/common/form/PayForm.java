package com.jia.pay.common.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * @author  ZhengXing
 * createTime: 2018/7/4
 * desc:  支付接口
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@Accessors(chain = true)
public class PayForm extends BasePayForm {


    /**
     * 请求id （上一次支付请求返回的请求id）
     * 用于重复返回支付页面响应
     */
    protected String requestId;

    /**
     * 支付类型
     * see {@link com.jia.pay.common.enums.PayTypeEnum}
     */
    @NotNull(message = "payType不能为空")
    @Range(min = 0,max = 3,message = "payType范围有误(0-3)")
    protected Integer payType;

    /**
     * 用户id
     */
    @NotBlank(message = "userId不能为空")
    @Length(min = 1,max = 28,message = "userId长度有误(1-28)")
    protected String userId;

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
     * 身份证
     */
    @NotBlank(message = "身份证不能为空")
    @Pattern(regexp = "^(\\d{6})(\\d{4})(\\d{2})(\\d{2})(\\d{3})([0-9]|X)$",message = "身份证格式有误")
    protected String idCard;

    /**
     * 银行卡号
     */
    @NotBlank(message = "银行卡号不能为空")
    protected String bankCard;

    /**
     * 订单生成时间
     * 可为空
     * 默认当前时间
     * yyyy-MM-dd HH:mm:ss
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    protected Date orderTime = new Date();

}
