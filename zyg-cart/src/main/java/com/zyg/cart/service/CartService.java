package com.zyg.cart.service;

import com.zyg.cart.entity.group.Cart;

import java.util.List;

/**
 * Created by WF on 2021/12/20 14:48
 */
public interface CartService {
    List<Cart> addCart(Long itemId, int num,String name);

    List<Cart> findCartList(String name);
}
