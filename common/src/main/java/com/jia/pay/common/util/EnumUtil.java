package com.jia.pay.common.util;


import com.jia.pay.common.enums.CodeEnum;

import java.util.Objects;
import java.util.Optional;

/**
 * @author  ZhengXing
 * createTime: 2018/5/22
 * desc:  枚举相关工具类
 * 配合 {@link CodeEnum 使用}
 */
public class EnumUtil {

    /**
     * 根据Code返回枚举
     */
    public static <T extends CodeEnum<X>,X> Optional<T> getByCode(X code, Class<T> enumClass) {
        for (T each : enumClass.getEnumConstants()) {
            if (each.getCode().equals(code)) {
                return Optional.of(each);
            }
        }
        return Optional.empty();
    }

    /**
     * 根据Code返回枚举
     * 使用equalsIgnoreCase
     */
    public static <T extends CodeEnum<String>> Optional<T> getByCodeIgnoreCase(String code, Class<T> enumClass) {
        for (T each : enumClass.getEnumConstants()) {
            if (each.getCode().equalsIgnoreCase(code)) {
                return Optional.of(each);
            }
        }
        return Optional.empty();
    }

    /**
     * 比较两个枚举
     */
    public static  <T extends CodeEnum> boolean equals(T enumA,T enumB) {
        return Objects.equals(enumA, enumB);
    }


    /**
     * 某个 code 和某个枚举的的code是否一致
     *
     */
    public static <T extends CodeEnum<X>,X> boolean equals(X code,T enumObj) {
        return enumObj.getCode().equals(code);
    }
}
