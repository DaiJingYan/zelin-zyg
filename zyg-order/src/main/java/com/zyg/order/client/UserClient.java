package com.zyg.order.client;

import com.zyg.common.entity.TbPayLog;
import com.zyg.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created by WF on 2021/12/22 15:58
 */
@FeignClient("zyg-user")
public interface UserClient {
    
    @PostMapping("/user/paylog/add")
    R add(@RequestBody TbPayLog payLog);
}
