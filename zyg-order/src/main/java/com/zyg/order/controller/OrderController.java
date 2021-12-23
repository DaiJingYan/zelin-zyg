package com.zyg.order.controller;

import com.alipay.api.AlipayApiException;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.zyg.common.entity.PayVo;
import com.zyg.common.entity.TbPayLog;
import com.zyg.common.utils.AlipayTemplate;
import com.zyg.common.utils.R;
import com.zyg.order.client.UserClient;
import com.zyg.order.entity.Cart;
import com.zyg.order.entity.TbAddress;
import com.zyg.order.entity.TbOrder;
import com.zyg.order.service.AddressService;
import com.zyg.order.service.OrderService;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * ------------------------------
 * 功能：
 * 作者：WF
 * 微信：hbxfwf13590332912
 * 创建时间：2021/12/21-14:54
 * ------------------------------
 */
@Controller
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private UserClient userClient;
    @Autowired
    private AlipayTemplate alipayTemplate;
    /**
     * 功能: 1. 默认页面
     * 参数: 
     * 返回值: java.lang.String
     * 时间: 2021/12/21 14:56
     */
    @GetMapping({"/","getOrderInfo"})
    public String getOrderInfo(Model model){
        //第一部分：显示登录用户的地址列表
        //1.1 获取用户名（cas登录名的获取方式）
        AttributePrincipal principal = (AttributePrincipal)request.getUserPrincipal();
        String name = principal.getName();
        //1.2 根据用户名查询地址列表
        List<TbAddress> addressList = addressService.findAddressByUserId(name);
        //1.3 将地址列表放到mode中
        model.addAttribute("addressList",addressList);
        
        //第二部分：显示登录用户的购物车信息
        List<Cart> cartList = orderService.findCartList(name);
        model.addAttribute("cartList",cartList);
        
        return "getOrderInfo";
    }
    
    /**
     * 功能: 2. 添加订单
     * 参数: 
     * 返回值: 
     * 时间: 2021/12/21 16:22
     */
    @PostMapping("/order/add")
    public String add(TbOrder order,Model model){
        //2.1 获取用户名（cas登录名的获取方式）
        AttributePrincipal principal = (AttributePrincipal)request.getUserPrincipal();
        String name = principal.getName();
        //2.2 保存订单
        orderService.save(order,name);
        //2.3 从redis中得到支付日志对象
        TbPayLog payLog = orderService.getPayLogFromRedis(name);
        //2.4 将支付日志放到model中
        model.addAttribute("payLog",payLog);
        return "pay";
    }
    
    /**
     * 功能: 3. 显示支付宝的支付页面的表单
     * 参数:
     * 返回值: 
     * 时间: 2021/12/23 16:03
     */
    @GetMapping("order/pay/{type}/{outTradeNo}")
    @ResponseBody
    public String orderPay(@PathVariable int type,@PathVariable String outTradeNo) throws AlipayApiException {
        //3.1 从redis中根据登录名，得到支付日志并构造出Payvo对象
        //3.1.1 获取用户名（cas登录名的获取方式）
        AttributePrincipal principal = (AttributePrincipal)request.getUserPrincipal();
        String name = principal.getName();
        //3.1.2 根据登录名，从redis中得到支付日志并转换为PayVo
        TbPayLog payLog = orderService.getPayLogFromRedis(name);
        PayVo vo = new PayVo();
        vo.setOut_trade_no(payLog.getOutTradeNo());
        vo.setTotal_amount(payLog.getTotalFee() + "");
        vo.setSubject("泽易购商城支付订单");
        vo.setBody("一个良好的网上交易平台...");
        //3.1.3 得到返回的支付表单页面
        String result = alipayTemplate.pay(vo, type);
        return result;
    }

}
