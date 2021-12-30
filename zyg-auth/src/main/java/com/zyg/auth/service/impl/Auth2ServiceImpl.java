package com.zyg.auth.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zyg.auth.properties.AuthProperties;
import com.zyg.auth.service.Auth2Service;
import com.zyg.common.utils.HttpClient;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * ------------------------------
 * 功能：
 * 作者：WF
 * 微信：hbxfwf13590332912
 * 创建时间：2021/12/30-16:00
 * ------------------------------
 */
@Service
public class Auth2ServiceImpl implements Auth2Service {

    @Autowired
    private AuthProperties authProperties;
    /**
     * 功能: 根据code及其它相关信息获取访问令牌及其它信息，并保到数据库中
     * 参数:
     * 返回值: void
     * 时间: 2021/12/30 16:01
     */
    @Override
    public void getTokenAndUserInfo(String code) throws IOException, ParseException {
        //1. 定义HttpClient对象
        //1.1 定义查询参数
        Map<String, String> params = new HashMap<>();
        params.put("client_id",authProperties.getClient_id());
        params.put("client_secret",authProperties.getClient_secret());
        params.put("grant_type",authProperties.getGrant_type());
        params.put("redirect_uri",authProperties.getRedirect_uri());
        params.put("code",code);
        //1.2 构造HttpClient对象
        HttpClient httpClient = new HttpClient(authProperties.getUrl(), params);
        //1.3 发送请求
        //1.3.1 设置允许发送https类型的请求
        httpClient.setHttps(true);
        //1.3.2 开始发送http请求
        httpClient.post();
        //1.4 得到请求结果
        if(httpClient.getStatusCode() == HttpStatus.SC_OK){
            //1.4.1 得到请求结果
            String content = httpClient.getContent();
            //1.4.2 转换为JSONObejct对象
            JSONObject jsonObject = JSON.parseObject(content);
            String accessToken = jsonObject.getString("access_token");
            Integer expires_in = jsonObject.getInteger("expires_in");
            String uid =  jsonObject.getString("uid");

            //1.5 如果accessToken及uid存在，就查询用户信息
            if(StringUtils.isNotBlank(accessToken) && StringUtils.isNotBlank(uid)){
                //1.5.1 封装请求参数
                Map<String, String> data  = new HashMap<>();
                data.put("access_token",accessToken);
                data.put("uid",uid);
                //1.5.2 构造HttpClient对象
                HttpClient hc = new HttpClient(authProperties.getUserInfo_path(), data);
                //1.5.3 开始发送
                hc.setHttps(true);
                hc.get();
                //1.5.4 如果响应成功，就得到一个数据
                if(httpClient.getStatusCode() == HttpStatus.SC_OK){
                    //1.5.6 得到响应数据
                    String con = hc.getContent();
                    //1.5.7 转换为JSONObject
                    JSONObject json = JSON.parseObject(con);
                    //1.5.8 得到对于我们来说的有用信息
                    String name = json.getString("name");                   //用户名
                    String userImg = json.getString("profile_image_url");   //用户图像
                    String gender = json.getString("gender");               //用户性别

                    //todo...
                    //1.5.9 根据access_token的值查询在tb_user表中是否存在用户，如果存在就登录，不存在就放到tb_user表中
                    System.out.println("name = " + name);
                    System.out.println("userImg = " + userImg);
                    System.out.println("gender = " + gender);
                }
            }
        }
    }
}
