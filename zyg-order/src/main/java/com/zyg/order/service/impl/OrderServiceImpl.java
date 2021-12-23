package com.zyg.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.zyg.common.entity.PayVo;
import com.zyg.common.entity.TbPayLog;
import com.zyg.common.utils.IdWorker;
import com.zyg.order.client.UserClient;
import com.zyg.order.dao.TbOrderItemMapper;
import com.zyg.order.dao.TbOrderMapper;
import com.zyg.order.entity.Cart;
import com.zyg.order.entity.TbOrder;
import com.zyg.order.entity.TbOrderItem;
import com.zyg.order.service.OrderService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
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
    @Autowired
    private UserClient userClient;
    //1. 从redis中得到购物车
    @Override
    public List<Cart> findCartList(String name) {
        return JSON.parseArray(redisTemplate.opsForValue().get(name),Cart.class);
    }

    //2. 保存订单
    @Override
    @GlobalTransactional(rollbackFor = Exception.class,timeoutMills = 60000)
    @Transactional
    public void save(TbOrder tbOrder,String name) {
       //2.1 从redis中得到购物车列表
        List<Cart> cartList = findCartList(name);
        //2.1.1 定义存放订单id的集合
        List<String> ids = new ArrayList<>();
        //2.2 遍历购物车
        double total = 0;
        for (Cart cart : cartList) {
            //第一部分：添加订单对象
            //1.1 构造要添加的订单对象
            TbOrder order = new TbOrder();
            //1.2 生成订单id
            long id = idWorker.nextId();
            ids.add(id + "");

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
            //累加所有的订单金额之和
            total += sum;
            //第三部分：添加订单
            order.setPayment(new BigDecimal(sum));      //设置订单总金额
            orderMapper.insert(order);

        }
        //第四部分：添加订单日志
        if(tbOrder.getPaymentType().equals("1")){
            //4.1 定义一个TbPaylog对象
            TbPayLog payLog = new TbPayLog();
            payLog.setOutTradeNo(idWorker.nextId() + "");
            //4.2 得到订单id
            String orderIds = ids.toString().replace("[", "").replace("]", "");
            payLog.setOrderList(orderIds);

            payLog.setCreateTime(new Date());
            payLog.setPayType("2");
            payLog.setTotalFee((long)(total * 100));
            payLog.setTradeState("0");      //0: 未支付 1：己支付
            payLog.setUserId(name);
            // payLogMapper.insert(payLog);
            userClient.add(payLog);
            //4.3 添加redis中
            redisTemplate.opsForValue().set("paylog:" + name,JSON.toJSONString(payLog));
        }

    }

    /**
     * 功能: 从redis得到支付日志
     * 参数: 
     * 返回值: com.zyg.common.entity.TbPayLog
     * 时间: 2021/12/23 14:18
     */
    @Override
    public TbPayLog getPayLogFromRedis(String name) {
        return JSON.parseObject(redisTemplate.opsForValue().get("paylog:" + name),TbPayLog.class);
    }

  

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("4");
        String replace = list.toString().replace("[", "").replace("]", "");
        System.out.println(replace);
    }
}
