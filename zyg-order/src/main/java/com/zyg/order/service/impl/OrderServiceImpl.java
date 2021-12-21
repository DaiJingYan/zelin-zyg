package com.zyg.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zyg.common.utils.IdWorker;
import com.zyg.order.dao.TbOrderItemMapper;
import com.zyg.order.dao.TbOrderMapper;
import com.zyg.order.entity.Cart;
import com.zyg.order.entity.TbAddress;
import com.zyg.order.entity.TbOrder;
import com.zyg.order.entity.TbOrderItem;
import com.zyg.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * ------------------------------
 * 功能：
 * 作者：WF
 * 微信：hbxfwf13590332912
 * 创建时间：2021/12/21-15:03
 * ------------------------------
 */
@Service

public class OrderServiceImpl implements OrderService {

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private TbOrderMapper orderMapper;
    @Autowired
    private TbOrderItemMapper orderItemMapper;
    @Autowired
    private IdWorker idWorker;

    //1. 从redis中得到购物车
    @Override
    public List<Cart> findCartList(String name) {
        return JSON.parseArray(redisTemplate.opsForValue().get(name),Cart.class);
    }

    //2. 保存订单
    @Override
    @Transactional
    public void save(TbOrder tbOrder,String name) {
       //2.1 从redis中得到购物车列表
        List<Cart> cartList = findCartList(name);
        //2.2 遍历购物车
        for (Cart cart : cartList) {
            //第一部分：添加订单对象
            //1.1 构造要添加的订单对象
            TbOrder order = new TbOrder();
            //1.2 生成订单id
            long id = idWorker.nextId();
            //1.3 为订单设置属性
            order.setOrderId(id);
            order.setStatus("1");       //1：代表未付款 2： 代表己付款
            order.setSourceType("2");   //数据来源：2：代表来自pc端
            order.setUserId(name);
            order.setSellerId(cart.getSellerId()); //商家id
            order.setPaymentType(tbOrder.getPaymentType()); //支付类型
            order.setUpdateTime(new Date());
            order.setCreateTime(new Date());
            order.setReceiverMobile(tbOrder.getReceiverMobile());
            order.setReceiver(tbOrder.getReceiver());       //联系人
            order.setReceiverAreaName(tbOrder.getReceiverAreaName()); //联系地址

            double sum = 0;     //订单总金额

            //第二部分：添加订单项
            for (TbOrderItem orderItem : cart.getOrderItemList()) {
                //2.1 设置订单项属性
                orderItem.setOrderId(id);       //设置订单id
                orderItem.setId(idWorker.nextId());
                orderItem.setSellerId(cart.getSellerId());
                //2.2 累加所有订单项的总金额
                sum += orderItem.getTotalFee().doubleValue();
                //2.3 添加订单项
                orderItemMapper.insert(orderItem);
            }

            //第三部分：添加订单
            order.setPayment(new BigDecimal(sum));      //设置订单总金额
            orderMapper.insert(order);
        }
    }
}
