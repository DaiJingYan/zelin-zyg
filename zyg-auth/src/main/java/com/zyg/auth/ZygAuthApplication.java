package com.zyg.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * ------------------------------
 * 功能：
 * 作者：WF
 * 微信：hbxfwf13590332912
 * 创建时间：2021/12/30-15:18
 * ------------------------------
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class ZygAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZygAuthApplication.class);
    }

    //将restTemplate对象添加到spring容器中
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
