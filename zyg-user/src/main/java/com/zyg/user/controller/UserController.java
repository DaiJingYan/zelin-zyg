package com.zyg.user.controller;

import com.zyg.user.entity.TbUser;
import com.zyg.user.service.UserService;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * ------------------------------
 * 功能：
 * 作者：WF
 * 微信：hbxfwf13590332912
 * 创建时间：2021/12/13-14:33
 * ------------------------------
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping({"/","register.html"})
    public String index(){
        return "register";
    }
    //2. 根据手机号获取验证码
    @GetMapping("/user/getCode")
    public String register(String phone){

        //1. 根据手机号生成验证码
        userService.createCode(phone);
        return "register";
    }

    //3. 添加用户
    @PostMapping("/user/add")
    public String add(TbUser user,String validcode){
        System.out.println("user = " + user);
        //3.1 验证验证码是否存在
        boolean b = userService.isExistsCode(validcode,user.getPhone());
        System.out.println("validcode = " + validcode);
        if(b){
            userService.add(user);
        }
        //应该到登录页面
        return "register";
    }
}