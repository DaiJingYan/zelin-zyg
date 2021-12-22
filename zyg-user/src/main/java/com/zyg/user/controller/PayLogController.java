package com.zyg.user.controller;

import com.zyg.common.entity.TbPayLog;
import com.zyg.common.utils.R;
import com.zyg.user.service.PayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    
    @PostMapping("/user/paylog/add")
    public R add(@RequestBody TbPayLog payLog){
        payLogService.add(payLog);
        return R.ok();
    }
}
