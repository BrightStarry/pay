package com.jia.pay.api.lianlian.dto;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * @author  ZhengXing
 * createTime: 2018/6/25
 * desc:  实名风控参数
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class LLRealNameRiskControlParam extends LLBaseRiskControlParam{
    /**
     * 姓名
     */
    private String user_info_full_name;

    /**
     * 证件号码
     */
    private String user_info_id_no;

    /**
     * 实名认证方式
     * 是实名认证时，必填1：银行卡认证2：现场认证3：身份证远程认证4：其它认证
     */
    private String user_info_identify_type = "3";

}
