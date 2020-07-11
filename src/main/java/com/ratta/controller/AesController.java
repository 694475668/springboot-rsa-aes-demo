package com.ratta.controller;

import com.ratta.annotation.aes.AesSecurityParameter;
import com.ratta.dto.UserDTO;
import com.ratta.service.UserService;
import com.ratta.vo.UserVO;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * ******Title:<p> 程序的奥秘 </p> ******
 * ******Description:<p> </p>******
 * ******Company: <p> 深圳汇星数字技术有限公司 </p> ******
 *
 * @version :
 * @author: bright
 * @date:Created in 2020/7/9 13:36
 */
@Controller
@RequestMapping("/aes")
@Log4j2
public class AesController {
    @Resource
    private UserService userService;

    @Resource(name = "aesKeyPair")
    private String key;

    @GetMapping("/index")
    public String index() {
        return "aesLogin";
    }

    @PostMapping("/login")
    @ResponseBody
    /**
     * @AesSecurityParameter 此注解用来解密
     * 默认请求参数是加密,返回参数不加密，如果需要返回数据进行加密可以设置outEncode = true
     */
    @AesSecurityParameter(outEncode = true)
    public UserVO login(@RequestBody UserDTO userDTO) throws Exception {
        UserVO user = userService.getUser(userDTO.getUsername(), userDTO.getPassword());
        if (null != user) {
            return user;
        }
        return null;
    }

    /**
     * 获取公钥接口 (AES公钥和私钥是一个)
     *
     * @return
     */
    @PostMapping("/key")
    @ResponseBody
    public String getKey() throws Exception {
        log.info("key===={}", key);
        //获取公钥
        return key;
    }
}
