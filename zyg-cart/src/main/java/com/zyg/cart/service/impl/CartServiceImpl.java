package com.zyg.cart.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.zyg.cart.client.ManagerClient;
import com.zyg.cart.entity.ItemEntity;
import com.zyg.cart.entity.TbOrderItem;
import com.zyg.cart.entity.group.Cart;
import com.zyg.cart.service.CartService;
import com.zyg.common.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * ------------------------------
 * 功能：
 * 作者：WF
 * 微信：hbxfwf13590332912
 * 创建时间：2021/12/20-14:53
 * ------------------------------
 */
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private ManagerClient managerClient;
    @Autowired
    private IdWorker idWorker;
    //1. 添加商品到购物车(放到redis中)
    @Override
    public List<Cart> addCart(Long itemId, int num,String name) {
        List<Cart> cartList = null;
        //1.1 根据登录名name得到redis中的购物车列表信息
        String cartListStr = redisTemplate.opsForValue().get(name);

        //1.2 判断是否有内容
        if(StrUtil.isNotBlank(cartListStr)){  //如果不为空，就转换为List<Cart>并将商品添加进去即可
            cartList = JSON.parseArray(cartListStr, Cart.class);
        }else{                                //第一次时，就构造一个空集合
            cartList = new ArrayList<>();
        }

        //1.3 根据商品id，找到商品对象
        ItemEntity item = managerClient.findById(itemId + "");

        System.out.println("item = " + item);

        //1.4 得到商家id
        String sellerId = item.getSellerId();

        //1.5 根据商家id，查询出购物车对象
        Cart cart = findCartBySellerId(cartList,sellerId);

        //1.6 判断是否存在购物车
        //1.6.1 如果存在，此时分两种情况：
        // ① 判断购买的商品是否在当前购物车的购物项中，如果在，就修改商品数量及金额，如果不存，就创建sku商品并添加到购物项列表中
        if(cart != null){       //如果存在，就修改商品数量及金额
            //从购物车列表中找到商品，分情况：① 如果在购物车的就购物项列表中有此商品就修改数量及金额，如果没有就直接添加到购物项列表中
            updateOrderItem(cart,item,num);
        }else{      //1.6.2 如果不存在，就创建订单项商品,添加到新的购物车中
            TbOrderItem orderItem = createOrderItem(item,num);
            //创建购物车，并将订单项放到新的购物车中
            cart = createCart(orderItem,item);
            //添加购物车到购物车列表
            cartList.add(cart);
        }

        //1.7 如果一个购物车中的所有订单项的个数为0，就从购物车列表中删除此购物车
        if(cart.getOrderItemList().size() == 0){
            cartList.remove(cart);
        }
        //1.8 将购物车添加到redis中
        redisTemplate.opsForValue().set(name,JSON.toJSONString(cartList));

        return cartList;
    }

    //2. 根据商家id来查找购物车
    private Cart findCartBySellerId(List<Cart> cartList,String sellerId) {
        if(cartList.size() > 0){
            for (Cart cart : cartList) {
                if(cart.getSellerId().equals(sellerId)){
                    return cart;
                }
            }
        }
        return null;
    }
    //3. 创建订单项商品
    private TbOrderItem createOrderItem(ItemEntity item, int num) {
        //3.1 定义订单项id
        long id = idWorker.nextId();
        TbOrderItem orderItem = new TbOrderItem();
        //3.2 设置订单项的其它属性
        orderItem.setId(id);
        orderItem.setNum(num);
        orderItem.setTitle(item.getTitle());
        orderItem.setPrice(item.getPrice());
        orderItem.setItemId(item.getId());
        orderItem.setGoodsId(item.getGoodsId());
        orderItem.setSellerId(item.getSellerId());
        orderItem.setPicPath(item.getImage());
        orderItem.setTotalFee(new BigDecimal(item.getNum() * item.getPrice().doubleValue()));
        //3.3 返回订单项
        return orderItem;

    }

    //4. 创建购物车
    private Cart createCart(TbOrderItem orderItem,ItemEntity item) {
        //4.1 新建一个购物车
        Cart cart = new Cart();
        //4.2 新建一个订单项集合
        List<TbOrderItem> orderItems = new ArrayList<>();
        //4.3 将这个订单项对象放到上面的集合中
        orderItems.add(orderItem);
        //4.4 将这个订单项集合绑定到购物车中
        cart.setOrderItemList(orderItems);
        //4.5 设置购物车的其它项
        cart.setSellerId(orderItem.getSellerId());
        //4.6 设置商家名字
        cart.setSellerName(item.getSeller());
        //4.7 返回购物车
        return cart;
    }

    //5. 修改购物车中的商品及数量
    private void updateOrderItem(Cart cart, ItemEntity item, int num) {
        //5.1 查找是否在购物车的商品列表中有此商品
        TbOrderItem tbOrderItem = null;
        for (TbOrderItem orderItem : cart.getOrderItemList()) {
            if(orderItem.getItemId().longValue() == item.getId()){
                tbOrderItem = orderItem;
                break;
            }
        }
        //5.2 根据是否找到购物项商品，从而决定是修改商品还是添加商品到购物项列表中
        if(tbOrderItem != null){
            //5.2.1 修改商品数量
            tbOrderItem.setNum(tbOrderItem.getNum() + num);
            //5.2.2 修改商品金额
            tbOrderItem.setTotalFee(new BigDecimal(tbOrderItem.getNum() * tbOrderItem.getPrice().doubleValue()));
        }else{  //5.3 没有此商品就创建它
            TbOrderItem orderItem = createOrderItem(item, num);
                //5.4 再将此商品放到购物车列表中
            cart.getOrderItemList().add(orderItem);
        }

        //5.5 如果商品数量为0，就从购物车中的商品列表中删除它
        if(tbOrderItem.getNum() == 0){
            cart.getOrderItemList().remove(tbOrderItem);
        }
    }
    //6. 从redis中得到购物车列表
    @Override
    public List<Cart> findCartList(String name) {
        return JSON.parseArray(redisTemplate.opsForValue().get(name),Cart.class);
    }
}
