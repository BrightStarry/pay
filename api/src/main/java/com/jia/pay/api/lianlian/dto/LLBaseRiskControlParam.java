package com.jia.pay.api.lianlian.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author  ZhengXing
 * createTime: 2018/6/21
 * desc:  基础风控参数
 */
@Data
@Accessors(chain = true)
public class LLBaseRiskControlParam {

    /**
     * 商品类目
     */
    protected String frms_ware_category;

    /**
     * 商户用户id
     */
    protected String user_info_mercht_userno;

    /**
     * 用户绑定手机号
     * 非必需
     */
    protected String user_info_bind_phone;

    /**
     * 注册时间
     */
    protected String user_info_dt_register;

}
