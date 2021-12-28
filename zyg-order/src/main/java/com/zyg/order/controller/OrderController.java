package com.zyg.order.controller;

import com.alipay.api.AlipayApiException;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.zyg.common.entity.PayAsyncVo;
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
import com.zyg.order.service.PayService;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private PayService payService;
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
        String name = getLoginName();
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
        String name = getLoginName();
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
        String name = getLoginName();
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

    /**
     * 功能: 4. 获取登录名
     * 参数: 
     * 返回值: java.lang.String
     * 时间: 2021/12/24 14:39
     */
    private String getLoginName() {
        //4.1 获取用户名（cas登录名的获取方式）
        AttributePrincipal principal = (AttributePrincipal) request.getUserPrincipal();
        return principal.getName();
    }

    /**
     * 【支付宝支付】---》同步回调方法：
     * 功能: 5. 根据当前登录用户，查询此用户的所有订单列表
     * 参数: 
     * 返回值: 
     * 时间: 2021/12/24 14:37
     */
    @GetMapping("order/getOrderList")
    public String getOrderList(Model model){
        // 5.1 获取登录名
        String loginName = getLoginName();
        // 5.2 获取此登录名对应的订单列表
        List<TbOrder> orderList = orderService.getOrderList(loginName);
        // 5.3 将订单列表放到model中
        model.addAttribute("orderlist",orderList);

        return "orderlist";
    }

    /**
     * 功能: 【支付宝异步回调】
     * 参数:
     * 返回值:
     * 时间: 2021/12/24 14:56
     */
    @PostMapping("/order/pay")
    @ResponseBody
    public String syncCallback(PayAsyncVo vo, HttpServletRequest request){
        System.out.println("vo = " + vo);
        //1. 得支支付宝后台传入的数据
        Map<String,String> params = new HashMap<>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        //2. 得到登录名
        //String loginName = getLoginName();
        
        //3. 进行验签处理
        String result = payService.validSignature(vo,params);
        System.out.println("result = " + result);
        //4. 返回
        return result;
    }

    @GetMapping("/hello")
    @ResponseBody
    public R hello(){
        return R.ok();
    }
}
