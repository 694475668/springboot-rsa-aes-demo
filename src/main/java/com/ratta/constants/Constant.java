package com.ratta.constants;

import java.util.Map;

/**
 * @author 刘明
 */
public class Constant {
    /** */
    /**
     * 加密算法RSA
     */
    public static final String KEY_ALGORITHM = "RSA";

    /** */
    /**
     * 签名算法
     */
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    /** */
    /**
     * 获取公钥的key
     */
    public static final String PUBLIC_KEY = "XS_PUBLIC_KEY";

    /** */
    /**
     * 获取私钥的key
     */
    public static final String PRIVATE_KEY = "XS_PRIVATE_KEY";

    /** */
    /**
     * RSA最大加密明文大小
     */
    public static final int MAX_ENCRYPT_BLOCK = 117;

    /** */
    /**
     * RSA最大解密密文大小
     */
    public static final int MAX_DECRYPT_BLOCK = 128;

    /** */
    /**
     * RSA 位数 如果采用2048 上面最大加密和最大解密则须填写:  245 256
     */
    public static final int INITIALIZE_LENGTH = 1024;

    /**
     *
     */
    public static final String ALGORITHMSTR = "AES/ECB/PKCS5Padding";
}
