package com.zyg.user.controller;

import com.zyg.common.entity.TbPayLog;
import com.zyg.common.utils.R;
import com.zyg.user.service.PayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * ------------------------------
 * 功能：
 * 作者：WF
 * 微信：hbxfwf13590332912
 * 创建时间：2021/12/22-15:59
 * ------------------------------
 */
@RestController
public class PayLogController {
    @Autowired
    private PayLogService payLogService;
    
    /**
     * 功能: 1. 添加支付日志
     * 参数: 
     * 返回值: com.zyg.common.utils.R
     * 时间: 2021/12/24 15:26
     */
    @PostMapping("/user/paylog/add")
    public R add(@RequestBody TbPayLog payLog){
        payLogService.add(payLog);
        return R.ok();
    }
    
    /**
     * 功能: 2. 修改支付日志
     * 参数: 
     * 返回值: com.zyg.common.utils.R
     * 时间: 2021/12/24 15:27
     */
    @PostMapping("/user/paylog/update")
    public R update(@RequestBody TbPayLog payLog){
        payLogService.update(payLog);
        return R.ok();
    }
    
    @GetMapping("/user/paylog/findById/{outTradeNo}")
    public TbPayLog findById(@PathVariable String outTradeNo){
        return payLogService.findById(outTradeNo);
    }
}
