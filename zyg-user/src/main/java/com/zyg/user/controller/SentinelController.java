package com.zyg.user.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.zyg.common.utils.R;
import com.zyg.user.exception.MyException;
import com.zyg.user.service.SentinelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * ------------------------------
 * 功能：
 * 作者：WF
 * 微信：hbxfwf13590332912
 * 创建时间：2021/12/27-15:06
 * ------------------------------
 */
@Controller
@RequestMapping(produces = "application/json;charset=utf-8")
public class SentinelController {

    @Autowired
    private SentinelService sentinelService;
    @GetMapping("hello")
    @ResponseBody
    public R hello(){
        // 1.5.0 版本开始可以直接利用 try-with-resources 特性，自动 exit entry
        try (Entry entry = SphU.entry("HelloWorld")) {
            // 被保护的逻辑
            System.out.println("hello world");
        } catch (BlockException ex) {
            // 处理被流控的逻辑
            System.out.println("blocked!");
        }
        return R.ok();
    }
    
    @GetMapping("/world")
    @ResponseBody
    public R world(){
        return R.ok().put("message","关联资源！");
    }
    
    //处理方法一:返回的是模板视图(使用thymeleaf)
    // @GetMapping("/flow/blockPage")
    // public String blockPage(){
    //     return "blockPage";
    // }
    
    //处理方法二:直接返回内容
    @GetMapping({"/flow/blockPage"})
    @ResponseBody
    public R blockPage(){
        return R.ok().put("message","限流出错页面！");
    }
    // @GetMapping("/error")
    // public String error(){
    //     return "error";
    // }
    //限流--->链路
    @GetMapping("/sentinel/message1")
    @ResponseBody
    public String message1(){
       sentinelService.message();
       return "message1";
    }

    @GetMapping("/sentinel/message2")
    @ResponseBody
    public String message2(){
        sentinelService.message();
        return "message2";
    }

    int i = 0;
    //降级--->异常比较
    @GetMapping("/sentinel/message3")
    @ResponseBody
    public String message3(){
        i++;
        if(i % 3 == 0){
            throw new RuntimeException();
        }
        return "message3";
    }

}
