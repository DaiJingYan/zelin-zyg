package com.zyg.canal;

import com.xpand.starter.canal.annotation.EnableCanalClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ------------------------------
 * 功能：
 * 作者：WF
 * 微信：hbxfwf13590332912
 * 创建时间：2021/12/14-15:30
 * ------------------------------
 */
@SpringBootApplication
@EnableCanalClient
public class ZygCanalApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZygCanalApplication.class);
    }
}
