package com.ratta;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * /启动定时函数
 *
 * @author bright
 */
@EnableScheduling
@SpringBootApplication
@MapperScan({"com.ratta.mapper"})
public class SpringbootRsaAesDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringbootRsaAesDemoApplication.class, args);
    }

}
