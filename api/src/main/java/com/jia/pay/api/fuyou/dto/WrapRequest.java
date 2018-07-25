package com.jia.pay.api.fuyou.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author  ZhengXing
 * createTime: 2018/5/21
 * desc: 最外层请求对象，包裹 某些api的Request
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class WrapRequest {
    private String FM;
}
