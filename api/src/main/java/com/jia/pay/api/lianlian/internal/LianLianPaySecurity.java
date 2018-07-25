//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//
package com.jia.pay.api.lianlian.internal;

import java.util.Random;

public class LianLianPaySecurity {
    public LianLianPaySecurity() {
    }

    public static String encrypt(String plaintext, String public_key) throws Exception {
        String hmack_key = genLetterDigitRandom(32);
        String version = "lianpay1_0_1";
        String aes_key = genLetterDigitRandom(32);
        String nonce = genLetterDigitRandom(8);
        return LianLianPayEncrypt.lianlianpayEncrypt(plaintext, public_key, hmack_key, version, aes_key, nonce);
    }

    public static boolean isvalidate(String ciphertext, String private_key) throws Exception {
        if (isNull(ciphertext)) {
            return false;
        } else {
            String[] ciphertextArry = ciphertext.split("\\$");
            String base64_encrypted_hmac_key = ciphertextArry.length > 1 ? ciphertextArry[1] : "";
            String base64_nonce = ciphertextArry.length > 3 ? ciphertextArry[3] : "";
            String base64_ciphertext = ciphertextArry.length > 4 ? ciphertextArry[4] : "";
            String signature = ciphertextArry.length > 5 ? ciphertextArry[5] : "";
            return LianLianPayEncrypt.isvalidate(base64_nonce, base64_encrypted_hmac_key, private_key, base64_ciphertext, signature);
        }
    }

    public static String decrypt(String ciphertext, String private_key) throws Exception {
        if (isNull(ciphertext)) {
            return "";
        } else {
            String[] ciphertextArry = ciphertext.split("\\$");
            String base64_encrypted_aes_key = ciphertextArry.length > 2 ? ciphertextArry[2] : "";
            String base64_nonce = ciphertextArry.length > 3 ? ciphertextArry[3] : "";
            String base64_ciphertext = ciphertextArry.length > 4 ? ciphertextArry[4] : "";
            return LianLianPayEncrypt.lianlianpayDecrypt(base64_ciphertext, base64_encrypted_aes_key, base64_nonce, private_key);
        }
    }

    public static String genLetterDigitRandom(int size) {
        StringBuffer allLetterDigit = new StringBuffer("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        Random random = new Random();
        StringBuffer randomSb = new StringBuffer("");

        for(int i = 0; i < size; ++i) {
            randomSb.append(allLetterDigit.charAt(random.nextInt(allLetterDigit.length())));
        }

        return randomSb.toString();
    }

    public static boolean isNull(String str) {
        return str == null || str.equalsIgnoreCase("NULL") || str.equals("");
    }

    public static void main(String[] args) throws Exception {
        String private_key = "RSA私钥";
        String public_key = "RSA公钥";
        String encryptStr = encrypt("{\"id_type\":\"0\",\"id_no\":\"410382198912131344\",\"card_no\":\"6244660060887777\",\"acct_name\":\"港澳通行证\"}", public_key);
        System.out.println(encryptStr);
        boolean isvalidate = isvalidate(encryptStr, private_key);
        System.out.println(isvalidate);
        String decryptStr = decrypt(encryptStr, private_key);
        System.out.println(decryptStr);
    }
}
