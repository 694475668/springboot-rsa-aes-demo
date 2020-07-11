package com.ratta.controller;

import com.ratta.annotation.rsa.RsaSecurityParameter;
import com.ratta.dto.UserDTO;
import com.ratta.service.UserService;
import com.ratta.util.RSAUtil;
import com.ratta.vo.UserVO;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

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
@RequestMapping("/rsa")
@Log4j2
public class RsaController {
    @Resource
    private UserService userService;

    @Resource(name = "rsaKeyPair")
    private Map<String, Object> map;

    @GetMapping("/index")
    public String index() {
        return "rsaLogin";
    }

    @PostMapping("/login")
    @ResponseBody
    /**
     * RsaSecurityParameter  此注解用来解密
     * 默认请求参数是加密,返回参数不加密，如果需要返回数据进行加密可以设置outEncode = true
     */
    @RsaSecurityParameter(outEncode = true)
    public UserVO login(@RequestBody UserDTO userDTO) throws Exception {
        UserVO user = userService.getUser(userDTO.getUsername(), userDTO.getPassword());
        if (null != user) {
            return user;
        }
        return null;
    }

    /**
     * 获取公钥接口
     *
     * @return
     */
    @PostMapping("/publicKey")
    @ResponseBody
    public String getPublicKey() throws Exception {
        //获取公钥
        String publicKey = RSAUtil.getPublicKey(map);
        log.info("publicKey===={}", publicKey);
        return publicKey;
    }

    /**
     * 获取私钥
     *
     * @return
     * @throws Exception
     */
    @PostMapping("/privateKey")
    @ResponseBody
    public String privateKey() throws Exception {
        //获取私钥钥
        String privateKey = RSAUtil.getPrivateKey(map);
        log.info("privateKey===={}", privateKey);
        return privateKey;
    }
}
