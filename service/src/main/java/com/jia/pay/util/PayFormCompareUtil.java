package com.jia.pay.util;

import com.jia.pay.common.form.PayForm;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.apache.commons.beanutils.BeanUtils;

import java.util.Map;
import java.util.Objects;

/**
 * @author  ZhengXing
 * createTime: 2018/7/12
 * desc:  {@link PayForm} 比较工具类
 */
public class PayFormCompareUtil {

    /**
     * 比较两个 form,如果有不一致的字段，返回
     * @return 不一致的字段名，为null表示一致
     */
    @SneakyThrows
    public static String compare(@NonNull PayForm lastPayForm,@NonNull PayForm payForm) {
        Map<String, String> lastFormMap = BeanUtils.describe(lastPayForm);
        Map<String, String> formMap = BeanUtils.describe(payForm);
        for (Map.Entry<String, String> item : lastFormMap.entrySet()) {
            if (!Objects.equals(item.getValue(), formMap.get(item.getKey()))) {
                return item.getKey();
            }
        }
        return null;
    }
}
