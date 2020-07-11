package com.ratta.util;


import com.ratta.constants.Constant;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

@Log4j2
public class AESUtil {

    public static void main(String[] args) throws Exception {

        String name = "qinqinwobaaiwoba";
        String encodeRule = genEncodeRule();
        System.out.println(encodeRule);
        String enResult = aesEncrypt(name, encodeRule);
        System.out.println("加密后：=======" + enResult);
        String deResult = aesDecrypt(enResult, encodeRule);
        System.out.println("解密后：=======" + deResult);
        System.out.println("解密后：======="+aesDecrypt("aCjHUJESpDb6fND8+eb2e6fnwqs/xzqSi+LDQNqFYakvRyCKP0Si6CqfpYM6ov/Ttq8s5oJtaFkMUmn2AxM7r3h9HqklIhnbO0qIhR+5yBY=","Ggpp79CN80IlynMo"));
    }

    /**
     * 获取AES编码规则
     *
     * @return
     */
    public static String genEncodeRule() {
        StringBuilder chars = new StringBuilder("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789");
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            int num = Integer.parseInt(String.valueOf(Math.round(Math.floor(Math.random() * chars.length()))));
            result.append(chars.charAt(num));
        }
        return result.toString();
    }

    public static String base64Encode(byte[] bytes) {
        return org.apache.commons.codec.binary.Base64.encodeBase64String(bytes);
    }

    public static byte[] base64Decode(String base64Code) throws Exception {
        return Base64.decodeBase64(base64Code);
    }

    public static byte[] aesEncryptToBytes(String content, String encryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);
        Cipher cipher = Cipher.getInstance(Constant.ALGORITHMSTR);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), "AES"));
        return cipher.doFinal(content.getBytes("utf-8"));
    }

    public static String aesEncrypt(String content, String encryptKey) throws Exception {
        return base64Encode(aesEncryptToBytes(content, encryptKey));
    }

    public static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);
        Cipher cipher = Cipher.getInstance(Constant.ALGORITHMSTR);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), "AES"));
        byte[] decryptBytes = cipher.doFinal(encryptBytes);
        return new String(decryptBytes);
    }

    public static String aesDecrypt(String encryptStr, String decryptKey) throws Exception {
        return aesDecryptByBytes(base64Decode(encryptStr), decryptKey);
    }
}
