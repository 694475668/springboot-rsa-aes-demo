package com.ratta.handler.aes;


import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ratta.annotation.aes.AesSecurityParameter;
import com.ratta.util.AESUtil;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.annotation.Resource;

/**
 * 在实际项目中 , 往往需要对返回参数做一些统一的操作 , 例如参数的过滤 , 字符的编码 , 数据加密等等 ,
 * Spring提供了ResponseBodyAdvice一个全局的解决方案 , 免去了我们在Controller处理的繁琐
 * ResponseBodyAdvice仅对使用了@ResponseBody注解的生效,或者使用@restController
 *
 * @author bright
 */

@ControllerAdvice
@Log4j2
public class AesEncodeResponseBodyAdvice implements ResponseBodyAdvice {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Resource(name = "aesKeyPair")
    private String key;

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        //必须设置为true,不然不会执行方法
        return true;
    }

    @SneakyThrows
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        log.info("数据加密前==========={}", JSON.toJSONString(body));
        boolean encode = false;
        if (methodParameter.getMethod().isAnnotationPresent(AesSecurityParameter.class)) {
            //获取注解配置的包含和去除字段
            AesSecurityParameter serializedField = methodParameter.getMethodAnnotation(AesSecurityParameter.class);
            //出参是否需要加密
            encode = serializedField.outEncode();
        }
        if (encode) {
            String content = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(body);
            log.info("对方法method :【" + methodParameter.getMethod().getName() + "】返回数据进行加密");
            //使用公钥进行数据加密
            Object data = AESUtil.aesEncrypt(content, key);
            log.info("数据加密后==========={}", data);
            return data;
        } else {
            return body;
        }
    }
}

