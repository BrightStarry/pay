package com.jia.pay.dao.entity;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 银行
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
public class Bank extends SuperEntity<Bank> {


    /**
     * code
     */
    private String code;
    /**
     * 银行名
     */
    private String name;

}
