package com.jia.pay.api.fuyou;


import lombok.SneakyThrows;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;

/**
 * @author  ZhengXing
 * createTime: 2018/6/1
 * desc:
 */
public class FuYouRSAUtil {

    @SneakyThrows
    public static String encrypt(String privateKey,String str) {
        byte[] bytesKey = (new BASE64Decoder()).decodeBuffer(privateKey.trim());
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(bytesKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
        Signature signature= Signature.getInstance("MD5WithRSA");signature.initSign(priKey);
        signature.update(str.getBytes("GBK"));
        return  (new BASE64Encoder()).encodeBuffer(signature.sign());
    }

}
