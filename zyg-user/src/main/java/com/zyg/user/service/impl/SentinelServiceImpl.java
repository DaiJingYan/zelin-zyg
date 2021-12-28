package com.zyg.user.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.zyg.user.service.SentinelService;
import org.springframework.stereotype.Service;

/**
 * ------------------------------
 * 功能：
 * 作者：WF
 * 微信：hbxfwf13590332912
 * 创建时间：2021/12/28-14:51
 * ------------------------------
 */
@Service
public class SentinelServiceImpl implements SentinelService {
    @Override
    @SentinelResource("message")
    public String message() {
        System.out.println("执行了链路调用...");
        return "message";   
    }
}
