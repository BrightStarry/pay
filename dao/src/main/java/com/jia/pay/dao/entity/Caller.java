package com.jia.pay.dao.entity;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 调用者
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
public class Caller extends SuperEntity<Caller> {

    /**
     * 姓名
     */
    private String name;
    /**
     * 令牌
     */
    private String token;

    /**
     * 前缀，用于生成每个caller唯一的userId
     */
    private String pre;

}
