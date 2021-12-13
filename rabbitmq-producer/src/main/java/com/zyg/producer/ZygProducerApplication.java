package com.zyg.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * ------------------------------
 * 功能：
 * 作者：WF
 * 微信：hbxfwf13590332912
 * 创建时间：2021/12/13-15:02
 * ------------------------------
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class ZygProducerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZygProducerApplication.class);
    }
}
