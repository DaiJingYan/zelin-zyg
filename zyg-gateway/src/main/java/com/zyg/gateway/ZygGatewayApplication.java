package com.zyg.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * ------------------------------
 * 功能：
 * 作者：WF
 * 微信：hbxfwf13590332912
 * 创建时间：2021/12/15-15:27
 * ------------------------------
 */
@SpringBootApplication
public class ZygGatewayApplication {
    public static void main(String[] args) {
        //添加此项内容，不然在sentinel网关界面不显示"API管理"与"请求链路"两项：
        //System.setProperty("csp.sentinel.app.type", "1");
        SpringApplication.run(ZygGatewayApplication.class);
    }
}
