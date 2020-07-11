package com.ratta.run;

import com.ratta.util.AESUtil;
import com.ratta.util.RSAUtil;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.util.Map;


/**
 * 容器加载就触发
 *
 * @author bright
 */
@Configuration
public class MyCommandLineRunner {

    @Bean(name = "rsaKeyPair")
    public Map<String, Object> getMap() throws NoSuchAlgorithmException {
        //生成rsa公私钥对
        return RSAUtil.genKeyPair();
    }


    @Bean(name = "aesKeyPair")
    public String getAesKey() throws NoSuchAlgorithmException {
        //生成aes公私钥对
        return AESUtil.genEncodeRule();
    }
}