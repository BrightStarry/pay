package com.jia.pay.dao.entity;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 支付方
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
public class Payer extends SuperEntity<Payer> {

    /**
     * code
     */
    private String code;

    /**
     * 名称
     */
    private String name;
    /**
     * 别名
     */
    private String alias;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 状态 0:禁用; 1:启用
     */
    private Integer status;

}
