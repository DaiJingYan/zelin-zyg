package com.zyg.user.handler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.apache.commons.lang3.StringUtils;

/**
 * ------------------------------
 * 功能：
 * 作者：WF
 * 微信：hbxfwf13590332912
 * 创建时间：2021/12/29-16:01
 * ------------------------------
 */
public class MyBlockHandler {

    /**
     * 功能: 处理BlockException异常的
     * 参数: 
     * 返回值: java.lang.String
     * 时间: 2021/12/29 15:49
     */
    public static String myBlockHandler2(BlockException ex) {
        String msg = ex.getMessage();
        if(StringUtils.isBlank(msg)){
            msg= "出现了BlockException异常!";
        }
        return "myBlockHandler[自定义类实现]-->:" + msg;
    }
}
