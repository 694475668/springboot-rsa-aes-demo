package com.ratta.handler.rsa;


import com.ratta.annotation.aes.AesSecurityParameter;
import com.ratta.annotation.rsa.RsaSecurityParameter;
import com.ratta.util.RSAUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import javax.annotation.Resource;
import javax.crypto.BadPaddingException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * 在实际项目中 , 往往需要对请求参数做一些统一的操作 , 例如参数的过滤 , 字符的编码 , 第三方的解密等等 ,
 * Spring提供了RequestBodyAdvice一个全局的解决方案 , 免去了我们在Controller处理的繁琐
 * RequestBodyAdvice仅对使用了@RqestBody注解的生效 , 因为它原理上还是AOP , 所以GET方法是不会操作的
 *
 * @author bright
 */

@ControllerAdvice(basePackages = "com.ratta.controller")
@Log4j2
public class RsaDecodeRequestBodyAdvice implements RequestBodyAdvice {

    @Resource(name = "rsaKeyPair")
    private Map<String, Object> map;

    @Override
    public boolean supports(MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        //必须设置为true,不然不会执行方法
        return true;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return body;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) throws IOException {
        try {

            boolean decode = false;
            if (methodParameter.getMethod().isAnnotationPresent(RsaSecurityParameter.class)) {
                //获取注解配置的包含和去除字段
                RsaSecurityParameter serializedField = methodParameter.getMethodAnnotation(RsaSecurityParameter.class);
                //入参是否需要解密
                decode = serializedField.inDecode();
            }
            if (decode) {
                log.info("对方法method :【" + methodParameter.getMethod().getName() + "】请求数据进行解密");
                return new MyHttpInputMessage(inputMessage);
            } else {
                return inputMessage;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("对方法method :【" + methodParameter.getMethod().getName() + "】请求数据进行解密出现异常：" + e.getMessage());
            return inputMessage;
        }
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return body;
    }

    class MyHttpInputMessage implements HttpInputMessage {
        private HttpHeaders headers;

        private InputStream body;

        public MyHttpInputMessage(HttpInputMessage inputMessage) throws Exception {
            this.headers = inputMessage.getHeaders();
            String content = easpString(IOUtils.toString(inputMessage.getBody(), "utf-8"));
            try {
                this.body = IOUtils.toInputStream(RSAUtil.decryptDataOnJava(content, RSAUtil.getPrivateKey(map)));
            } catch (BadPaddingException e) {
                //这里根据你们项目需求返回状态码，提示用户重新刷新获取最新的密钥
                log.error("密钥已失效请重新刷新网页{}", e);
            }
            log.info("解密后的数据为==============", RSAUtil.decryptDataOnJava(content, RSAUtil.getPrivateKey(map)));
        }

        @Override
        public InputStream getBody() throws IOException {
            return body;
        }

        @Override
        public HttpHeaders getHeaders() {
            return headers;
        }

        /**
         * 截取请求body的加密数据
         *
         * @param requestData
         * @return
         */
        public String easpString(String requestData) {
            if (requestData != null && !requestData.equals("")) {
                String s = "{\"requestData\":";
                if (!requestData.startsWith(s)) {
                    throw new RuntimeException("参数【requestData】缺失异常！");
                } else {
                    int closeLen = requestData.length() - 1;
                    int openLen = "{\"requestData\":".length();
                    String substring = StringUtils.substring(requestData, openLen, closeLen);
                    return substring;
                }
            }
            return "";
        }
    }
}

