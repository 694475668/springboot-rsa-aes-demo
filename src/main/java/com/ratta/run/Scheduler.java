package com.ratta.run;

import com.ratta.util.AESUtil;
import com.ratta.util.RSAUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 自定义刷新规则
 *
 * @author JH
 * @ClassName: Scheduler
 * @Description 刷新秘钥策略
 * @date 2019年5月23日 上午9:05:45
 */
@Component
@Log4j2
public class Scheduler {
    /**
     * 每天换一次公私钥，可以结合你的token失效时间进行调整
     * token多久失效，这个就多久进行更新一次
     *
     * @throws Exception
     */
    @Scheduled(cron = "1-2 0 0-23 * * ? ")
    public void testTasks() throws Exception {
        // 重新生成rsa秘钥对
        RSAUtil.genKeyPair();
        log.info("RSA Secret key pair===={}", RSAUtil.getPrivateKey(RSAUtil.genKeyPair()));
        // 重新生成aes秘钥对
        AESUtil.genEncodeRule();
        log.info("AES Secret key pair===={}", AESUtil.genEncodeRule());
    }
}
