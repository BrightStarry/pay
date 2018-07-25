package com.jia.pay.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author  ZhengXing
 * createTime: 2018/7/6
 * desc:  支付方 通用配置
 */
@Component
@ConfigurationProperties(prefix = "pay.common")
@Data
public class PayerCommonConfig {

    /**
     * 域名
     * 所有回调url增加该前缀
     */
    private String domain = "";

}
