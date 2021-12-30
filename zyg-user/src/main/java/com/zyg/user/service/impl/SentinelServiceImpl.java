package com.zyg.user.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.aliyuncs.http.HttpUtil;
import com.zyg.user.exception.MyException;
import com.zyg.user.handler.MyBlockHandler;
import com.zyg.user.handler.MyFallBackHandler;
import com.zyg.user.service.SentinelService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.netflix.ribbon.apache.HttpClientUtils;
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

    /**
     * 功能:
     * @SentinelResource 注解的说明:
     * 1. 此注解配置可以定制对两组异常的处理规则.
     * 2. 第一组是: blockHandler 和 blockHandlerClass: 专门处理BlockException的异常!
     *    2.1 二者的区别是前者是定义在当前类中的函数名(具体参见官网)
     *    2.2 后者是放在另一个类中,此类中定义的方法名必须是静态的!还要使用blockHandler参数属性指定方法名!
     * 3. 第二组是: fallbac 和 fallbackClass: 专门处理Throwable异常的!
     *    区别同上.
     * 官网地址: https://github.com/alibaba/Sentinel/wiki/%E6%B3%A8%E8%A7%A3%E6%94%AF%E6%8C%81
     */
    // @Override
    // @SentinelResource(value="myMsg",blockHandler = "myBlockHandler",fallback = "myFallBackHandler")
    // public String getMessage() {
    //     int i = 10 / 0;
    //     return "getMessage";
    // }

    //exceptionsToIgnore: 代表排除指定的异常,此异常不在处理范围,此时会直接抛出此异常!
    @Override
    @SentinelResource(value="myMsg",blockHandlerClass = MyBlockHandler.class,blockHandler = "myBlockHandler2", 
                     fallback = "myFallBackHandler2",fallbackClass = MyFallBackHandler.class,
                     exceptionsToIgnore = MyException.class)
    public String getMessage() {
        int i = 10 / 0;

        return "getMessage";
    }
    /**
     * 功能: 处理BlockException异常的
     * 参数: 
     * 返回值: java.lang.String
     * 时间: 2021/12/29 15:49
     */
    public String myBlockHandler(BlockException ex) {
        String msg = ex.getMessage();
        if(StringUtils.isBlank(msg)){
            msg= "出现了BlockException异常!";
        }
        return "myBlockHandler:" + msg;
    }

    /**
     * 功能: 处理ThrowableException异常的
     * 参数: 
     * 返回值: java.lang.String
     * 时间: 2021/12/29 15:49
     */
    public String myFallBackHandler(Throwable t) {
        String msg = t.getMessage();
        if(StringUtils.isBlank(msg)){
            msg= "出现了Throwable异常!";
        }
        return "myFallBackHandler:" + msg;
    }
}
