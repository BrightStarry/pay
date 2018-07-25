package com.jia.pay.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author  ZhengXing
 * createTime: 2018/7/10
 * desc:  自定义属性
 */
@Component
@Data
@ConfigurationProperties(prefix = "custom")
public class CustomProperties {

    /**
     * ip白名单
     */
    private List<String> blankIps;
}
