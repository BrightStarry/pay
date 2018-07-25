package com.jia.pay.dao.entity;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * @author  ZhengXing
 * createTime: 2018/7/4
 * desc:
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(callSuper = true)
@Accessors(chain = true)
public class Test extends SuperEntity<Test> {

    private String username;

    private String pwd;

}
