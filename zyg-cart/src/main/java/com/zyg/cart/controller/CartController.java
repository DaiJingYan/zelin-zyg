package com.zyg.cart.controller;

import com.zyg.cart.entity.group.Cart;
import com.zyg.cart.service.CartService;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * ------------------------------
 * 功能：
 * 作者：WF
 * 微信：hbxfwf13590332912
 * 创建时间：2021/12/20-14:33
 * ------------------------------
 */
@Controller
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private HttpServletRequest request;
    /**
     * 功能: 1. 欢迎页面
     * 参数: 
     * 返回值: java.lang.String
     * 时间: 2021/12/20 14:34
     */
    @GetMapping({"/","/cart.html"})
    public String index(Model model){
        //1.1 获取用户名（cas登录名的获取方式）
        AttributePrincipal principal = (AttributePrincipal)request.getUserPrincipal();
        String name = principal.getName();
        //1.2 根据登录名，得到购物车列表
        List<Cart> cartList = cartService.findCartList(name);
        //1.3 放到mode中
        model.addAttribute("cartList",cartList);
        return "cart";
    }

    /**
     * 功能: 2. 添加到购物车
     * 参数:
     * 返回值:
     * 时间: 2021/12/20 14:46
     */
    @GetMapping("/addCart")
    public String addCart(Long itemId,int num,Model model){
        //2.1 获取用户名（cas登录名的获取方式）
        AttributePrincipal principal = (AttributePrincipal)request.getUserPrincipal();
        String name = principal.getName();
        //2.2 添加商品到购物车列表中
        List<Cart> cartList = cartService.addCart(itemId,num,name);
        //2.3 将购物车列表放到model中
        model.addAttribute("cartList",cartList);
        return "cart";
    }
}
