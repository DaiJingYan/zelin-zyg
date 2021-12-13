package com.zyg.consumer.listener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by WF on 2021/12/13 15:38
 */
@Component
@RabbitListener(queues = "sgyy")
public class MyListener5 {

    @RabbitHandler
    public void getMessage(String msg){
        System.out.println("sgyy接收消息：" + msg);
    }
}
