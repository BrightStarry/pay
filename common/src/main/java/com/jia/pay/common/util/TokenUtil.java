package com.jia.pay.common.util;

import org.apache.commons.text.RandomStringGenerator;

/**
 * @author  ZhengXing
 * createTime: 2018/7/4
 * desc: 令牌工具类
 */
public class TokenUtil {
    // 线程安全的随机数生成器
    private static RandomStringGenerator randomStringGenerator;

    // 令牌默认长度
    private static final Integer TOKEN_LENGTH = 32;

    static{
        randomStringGenerator = new RandomStringGenerator.Builder()
                .withinRange('A', 'Z').build();
    }

    /**
     * 指定长度 随机数(0-z)
     * @param len
     * @return
     */
    public static String generateRandom(int len) {
        return randomStringGenerator.generate(len);
    }

    /**
     * 默认{@link #TOKEN_LENGTH}长度的随机数
     * @return
     */
    public static String generateToken() {
        return randomStringGenerator.generate(TOKEN_LENGTH);
    }

    /**
     * 生成请求id
     * 时间戳（13） + 随机字符(7) = 20
     */
    public static String generateRequestId() {
        return System.currentTimeMillis() + generateRandom(7);
    }




}
