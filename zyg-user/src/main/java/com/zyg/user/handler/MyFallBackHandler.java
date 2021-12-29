package com.zyg.user.handler;

import org.apache.commons.lang3.StringUtils;

/**
 * ------------------------------
 * 功能：
 * 作者：WF
 * 微信：hbxfwf13590332912
 * 创建时间：2021/12/29-16:04
 * ------------------------------
 */
public class MyFallBackHandler {
    /**
     * 功能: 处理ThrowableException异常的
     * 参数:
     * 返回值: java.lang.String
     * 时间: 2021/12/29 15:49
     */
    public static String myFallBackHandler2(Throwable t) {
        String msg = t.getMessage();
        if(StringUtils.isBlank(msg)){
            msg= "出现了Throwable异常!";
        }
        return "myFallBackHandler[自定义类实现]--> :" + msg;
    }
}
