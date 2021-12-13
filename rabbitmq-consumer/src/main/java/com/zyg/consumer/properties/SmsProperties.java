package com.zyg.consumer.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/**
 * ------------------------------
 * 功能：
 * 作者：WF
 * 微信：hbxfwf13590332912
 * 创建时间：2021/12/13-16:12
 * ------------------------------
 */
@Data
@ConfigurationProperties(prefix = "aliyun")
public class SmsProperties {
    private String signname;
    private String templatecode;
    private String accesskeyid;
    private String accesskeysecret;

}
