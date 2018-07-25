package com.jia.pay.api.lianlian.util;

import com.jia.pay.api.lianlian.internal.LianLianPaySecurity;
import lombok.SneakyThrows;
import org.springframework.util.Base64Utils;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * RSA签名公共类
 *
 * @author shmily
 */
public class LLRSAUtil {

    /**
     * 签名处理
     *
     * @param prikeyvalue：私钥
     * @param sign_str：签名源内容
     * @return
     */
    @SneakyThrows
    public static String sign(String prikeyvalue, String sign_str) {
        PrivateKey myprikey = getPrivateKey(prikeyvalue);
        // 用私钥对信息生成数字签名
        Signature signet = Signature
                .getInstance("MD5withRSA");
        signet.initSign(myprikey);
        signet.update(sign_str.getBytes("UTF-8"));
        byte[] signed = signet.sign(); // 对信息的数字签名
        return new String(org.apache.commons.codec.binary.Base64.encodeBase64(signed));
    }

    /**
     * 签名验证
     *
     * @param publicKey：公钥
     * @param data：加密源内容
     * @param sign：签名结果串
     * @return
     */
    @SneakyThrows
    public static boolean checksign(String publicKey, String data, String sign) {
        Signature signature = Signature.getInstance("MD5withRSA");
        PublicKey publicK = getPublicKey(publicKey);
        signature.initVerify(publicK);
        signature.update(data.getBytes("UTF-8"));
        return signature.verify(Base64Utils.decodeFromString(sign));
    }

    /**
     * 获取公钥
     */
    @SneakyThrows
    private static PublicKey getPublicKey(String publicKey) {
        byte[] keyBytes = Base64Utils.decodeFromString(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

    /**
     * 获取私钥
     */
    @SneakyThrows
    private static PrivateKey getPrivateKey(String privateKey) {
        PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(
                Base64Utils.decodeFromString(privateKey));
        KeyFactory keyf = KeyFactory.getInstance("RSA");
        return  keyf.generatePrivate(priPKCS8);
    }

    /**
     * 公钥加密
     * Illegal key size
     * https://www.cnblogs.com/lilinzhiyu/p/8024100.html
     */
    @SneakyThrows
    public static String encrypt( String publicKey,String data) {
        return LianLianPaySecurity.encrypt(data, publicKey);
    }


}
