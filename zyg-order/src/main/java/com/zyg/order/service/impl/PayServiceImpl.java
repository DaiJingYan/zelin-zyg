package com.zyg.order.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.zyg.common.entity.PayAsyncVo;
import com.zyg.common.entity.TbPayLog;
import com.zyg.common.utils.AlipayTemplate;
import com.zyg.order.client.UserClient;
import com.zyg.order.dao.TbOrderMapper;
import com.zyg.order.entity.TbOrder;
import com.zyg.order.service.OrderService;
import com.zyg.order.service.PayService;
import io.seata.spring.annotation.GlobalTransactional;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

/**
 * ------------------------------
 * 功能：
 * 作者：WF
 * 微信：hbxfwf13590332912
 * 创建时间：2021/12/24-15:15
 * ------------------------------
 */
@Service
public class PayServiceImpl implements PayService {
    @Autowired
    private AlipayTemplate alipayTemplate;
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserClient userClient;
    // @Autowired
    // private TbOrderMapper orderMapper;
    /**
     * 功能: 1. 进行验签处理
     * 参数: 
     * 返回值: java.lang.String
     * 时间: 2021/12/24 15:15
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class,timeoutMills = 60000)
    @Transactional
    public String validSignature(PayAsyncVo vo, Map<String, String> params) {
        try {
            //1.1 进行验签处理
            boolean b = AlipaySignature.rsaCheckV1(
                                params,
                                alipayTemplate.getAlipay_public_key(),
                                alipayTemplate.getCharset(),
                                alipayTemplate.getSign_type());
            //1.2 如果成功，就进行正常的业务逻辑
            if(b){
                //第一部分：修改支付日志
                //1.2.1 根据登录名得到支付日志
                TbPayLog payLog = userClient.findById(vo.getOut_trade_no());
                //1.2.2 修改支付日志中的数据
                payLog.setPayTime(new Date());          // 添加支付时间
                payLog.setTradeState("1");              // 修改订单状态为1，代表已支付
                payLog.setTransactionId(vo.getTrade_no()); // 修改流水号
                userClient.update(payLog);

                //第二部分：修改订单状态
                if(vo.getTrade_status().equals("TRADE_SUCCESS") || vo.getTrade_status().equals("TRADE_FINISHED")){
                    //2.1 得到订单id
                    String orderList = payLog.getOrderList();
                    //2.2 拆分得到每个id
                    for (String id : orderList.split(",")) {
                        //2.2.1 得到订单
                        TbOrder order = orderService.findById(id);
                        //2.2.2 修改订单状态为己支付
                        order.setStatus("2");
                        //2.2.3 保存订单
                        orderService.update(order);
                    }
                }
                return "success";
            }

        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        return "fail";
    }
}
