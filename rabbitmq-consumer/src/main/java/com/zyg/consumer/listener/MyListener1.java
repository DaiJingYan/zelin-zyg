package com.zyg.consumer.listener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * ------------------------------
 * 功能：
 * 作者：WF
 * 微信：hbxfwf13590332912
 * 创建时间：2021/12/13-15:09
 * ------------------------------
 */
@RabbitListener(queues = "zelin")
@Component
public class MyListener1 {

    @RabbitHandler
    public void getMessage(String msg){
        System.out.println(msg);
    }
}
