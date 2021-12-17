package com.zyg.user.config;

import net.unicon.cas.client.configuration.EnableCasClient;
import org.jasig.cas.client.authentication.AuthenticationFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashMap;
import java.util.Map;

 @Configuration
 @EnableCasClient
public class CasUrlPatternConfig {

    @Value("${cas.server-login-url}")
    private String casServerLoginUrl;

    @Value("${cas.client-host-url}")
    private String casClientHostUrl;

    @Value("${cas-ignore-pattern}")
    private String casIgnorePattern;

    /**
     * description:授权过滤器
     * ignoreUrlPatternType 使用CAS现成的正则表达式过滤策略
     */
    @Bean
    public FilterRegistrationBean filterAuthenticationRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new AuthenticationFilter());
        registration.addUrlPatterns("/*");

        Map<String,String> initParameters = new HashMap<String, String>();
        initParameters.put("casServerLoginUrl", casServerLoginUrl);    //服务器登录地址
        initParameters.put("serverName", casClientHostUrl);            //客户端地址
        //配置文件中设置要过滤拦截的路径
        initParameters.put("ignorePattern", casIgnorePattern);         //忽略的请求地址，只相当于本项目来说，外界访问我们哪些地址不需要提供Ticket票据
        initParameters.put("ignoreUrlPatternType", "org.jasig.cas.client.authentication.RegexUrlPatternMatcherStrategy"); //让忽略的地址支持正则表达式
        registration.setInitParameters(initParameters);

        registration.setOrder(1);
        return registration;
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encode = encoder.encode("123");
        System.out.println("encode = " + encode);
    }
}