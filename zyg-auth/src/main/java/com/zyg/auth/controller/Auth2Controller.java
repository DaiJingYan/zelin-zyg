package com.zyg.auth.controller;

import com.zyg.auth.properties.AuthProperties;
import com.zyg.auth.service.Auth2Service;
import com.zyg.common.utils.HttpClient;
import com.zyg.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;

/**
 * ------------------------------
 * 功能：
 * 作者：WF
 * 微信：hbxfwf13590332912
 * 创建时间：2021/12/30-15:21
 * ------------------------------
 */
@RestController
public class Auth2Controller {
    @Autowired
    private Auth2Service auth2Service;
    /**
     * 功能: 第一次在前端点击”登录“按钮跳转到微博，微博又跳转到此回调页面
     * 类似于：
     * http://auth.zeyigou.com/oauth2/weibo/success?code=a19ab80912e1498f7c61c7e260d58157
     * 参数:
     * 返回值: com.zyg.common.utils.R
     * 时间: 2021/12/30 15:22
     */
    @GetMapping("/oauth2/weibo/success")
    public R weiboSuccess(String code) throws IOException, ParseException {
        //1. 根据code及其它相关信息，获取access_token/uid/expires_in并保存到数据库中
        auth2Service.getTokenAndUserInfo(code);
        System.out.println("code = " + code);
        return R.ok();
    }

    @GetMapping("/toBaidu")
    public R toBaidu() throws IOException, ParseException {
        HttpClient httpClient = new HttpClient("https://baidu.com", null);
        httpClient.post();
        String content = httpClient.getContent();
        return R.ok().put("content",content);
    }
}
