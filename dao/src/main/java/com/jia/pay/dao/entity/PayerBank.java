package com.jia.pay.dao.entity;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 支付方银行关系表
 * </p>
 *
 * @author zx
 * @since 2018-07-04
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(callSuper = true)
@Accessors(chain = true)
public class PayerBank extends SuperEntity<PayerBank> {

    /**
     * 支付方code
     */
    private String payerCode;
    /**
     * 银行表的code
     */
    private String bankCode;
    /**
     * 支付方类型(用于同一支付方不同接口需要不同code)
     */
    private Integer payerType;
    /**
     * 该支付方中银行code
     */
    private String payerBankCode;

    /**
     * 银行名(冗余字段)
     */
    private String name;

}
