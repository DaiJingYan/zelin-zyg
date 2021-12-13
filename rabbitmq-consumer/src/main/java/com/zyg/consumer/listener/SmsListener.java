package com.zyg.consumer.listener;

import com.alibaba.fastjson.JSON;
import com.zyg.consumer.util.SmsUtil;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * ------------------------------
 * 功能：
 * 作者：WF
 * 微信：hbxfwf13590332912
 * 创建时间：2021/12/13-16:05
 * ------------------------------
 */
@Component
@RabbitListener(queues = "sms")
public class SmsListener {
    @Autowired
    private SmsUtil smsUtil;
    @RabbitHandler
    public void getMessage(Map<String,String> map){
        //1. 得到传过来的手机号及验证码
        String phone = map.get("phone");
        String code = map.get("code");
        Map mapParam = new HashMap();
        mapParam.put("code",code);
        String templateParam = JSON.toJSONString(mapParam);
        //2. 使用阿里大于指定手机发送消息
        smsUtil.sendMessage(phone,templateParam);
    }
}
