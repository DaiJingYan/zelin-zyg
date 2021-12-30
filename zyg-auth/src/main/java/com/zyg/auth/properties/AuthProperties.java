package com.zyg.auth.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * ------------------------------
 * 功能：
 * 作者：WF
 * 微信：hbxfwf13590332912
 * 创建时间：2021/12/30-16:03
 * ------------------------------
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "auth")
public class AuthProperties {
    private String url;
    private String client_id;
    private String client_secret;
    private String grant_type;
    private String redirect_uri;
    private String userInfo_path;
}
