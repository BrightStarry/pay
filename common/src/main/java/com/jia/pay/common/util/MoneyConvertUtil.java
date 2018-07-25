package com.jia.pay.common.util;

import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @author  ZhengXing
 * createTime: 2018/6/1
 * desc:  金额转换工具类
 */
public class MoneyConvertUtil {



    /**
     * 金额 转 以分为单位
     * 例如 "0.01" -> "1"
     */
    public static String convertToDeciUnit(@NonNull String nativeMoney) {
        // 转为两位小数
        nativeMoney = convertToTowDecimal(nativeMoney);
        // 此时如果金额小于1，则会是 001 这样
        String temp = StringUtils.remove(nativeMoney, ".");
        // 执行两次, 如果以 "0" 开头，就删除
        temp = StringUtils.removeStart(temp, "0");
        temp = StringUtils.removeStart(temp, "0");
        return temp;
    }

    /**
     * 金额转为 保留两位小数
     */
    public static String convertToTowDecimal(@NonNull String nativeMoney) {
        // 将其转为保留两位小数的数字
        // DecimalFormat 该类线程不安全
        DecimalFormat format=new DecimalFormat("0.00");
        nativeMoney = format.format(new BigDecimal(nativeMoney));
        return nativeMoney;
    }


}
