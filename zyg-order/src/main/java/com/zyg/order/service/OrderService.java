package com.zyg.order.service;

import com.zyg.common.entity.PayVo;
import com.zyg.common.entity.TbPayLog;
import com.zyg.order.entity.Cart;
import com.zyg.order.entity.TbAddress;
import com.zyg.order.entity.TbOrder;

import java.util.List;

/**
 * Created by WF on 2021/12/21 15:01
 */
public interface OrderService {

    List<Cart> findCartList(String name);

    void save(TbOrder order,String name);

    TbPayLog getPayLogFromRedis(String name);
    
    List<TbOrder> getOrderList(String loginName);
}
