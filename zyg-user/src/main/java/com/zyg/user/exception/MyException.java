package com.zyg.user.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * ------------------------------
 * 功能：
 * 作者：WF
 * 微信：hbxfwf13590332912
 * 创建时间：2021/12/28-15:53
 * ------------------------------
 */
@Data
@NoArgsConstructor
public class MyException extends RuntimeException{
    private String message;
    public MyException(String message) {
        this.message = message;
    }
}
