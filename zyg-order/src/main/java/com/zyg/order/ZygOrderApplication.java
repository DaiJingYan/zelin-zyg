package com.zyg.order;

import com.zyg.common.utils.AlipayTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

/**
 * ------------------------------
 * 功能：
 * 作者：WF
 * 微信：hbxfwf13590332912
 * 创建时间：2021/12/21-14:44
 * ------------------------------
 */
@SpringBootApplication
@EnableFeignClients
public class ZygOrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZygOrderApplication.class);
    }
    
    @Bean
    public AlipayTemplate alipayTemplate(){
        return new AlipayTemplate();
    }
}
