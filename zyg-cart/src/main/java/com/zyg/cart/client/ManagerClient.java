package com.zyg.cart.client;

import com.zyg.cart.entity.ItemEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by WF on 2021/12/20 15:04
 */
@FeignClient("zyg-manager")
public interface ManagerClient {
    
    //1. 根据itemId查询商品信息
    @GetMapping("/manager/item/findById/{id}")
    ItemEntity findById(@PathVariable("id") String id);
}
