package com.ratta.annotation.aes;

import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;

/**
 * @author bright
 */

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Mapping
@Documented
public @interface AesSecurityParameter {

    /**
     * 入参是否解密，默认解密
     */
    boolean inDecode() default true;

    /**
     * 出参是否加密，默认不加密
     */
    boolean outEncode() default false;
}

