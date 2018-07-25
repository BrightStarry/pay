package com.jia.pay.common.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author  ZhengXing
 * createTime: 2018/7/4
 * desc:
 */
public class Md5Util {

    public static String toMd5(String data) {
        return DigestUtils.md5Hex(data);
    }
}
