package com.zyg.order.client;

import com.zyg.common.entity.TbPayLog;
import com.zyg.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created by WF on 2021/12/22 15:58
 */
@FeignClient("zyg-user")
public interface UserClient {
    
    //1. 添加支付日志
    @PostMapping("/user/paylog/add")
    R add(@RequestBody TbPayLog payLog);
    
    //2 修改支付日志
    @PostMapping("/user/paylog/update")
    R update(@RequestBody TbPayLog payLog);

    //3. 通过支付日志id得到支付日志对象
    @GetMapping("/user/paylog/findById/{outTradeNo}")
    TbPayLog findById(@PathVariable("outTradeNo") String outTradeNo);
}
