package com.zyg.user.exception;

import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.zyg.common.utils.R;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * ------------------------------
 * 功能：全局异常处理
 * 作者：WF
 * 微信：hbxfwf13590332912
 * 创建时间：2021/12/28-15:54
 * ------------------------------
 */
@ControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(FlowException.class)
    public String error(FlowException ex, Model model){
        String message = "未知异常!";
        if(ex != null){
            if(ex instanceof FlowException){
                message = ex.getMessage();
            }
            if(StringUtils.isBlank(message) ){
                message = "出现了限流异常!";
            }
        }
        System.out.println("message = " + message);
        model.addAttribute("message",message);
        return "error";
    }
    @ExceptionHandler(MyException.class)
    public String error(MyException ex, Model model){
        String message = "未知异常!";
        if(ex instanceof MyException){
            message = ex.getMessage();
        }
        model.addAttribute("message",message);
        return "error";
    }

}
