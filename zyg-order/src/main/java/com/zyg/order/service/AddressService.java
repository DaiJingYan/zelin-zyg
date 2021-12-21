package com.zyg.order.service;

import com.zyg.order.entity.TbAddress;

import java.util.List;

/**
 * Created by WF on 2021/12/21 15:06
 */
public interface AddressService {
    List<TbAddress> findAddressByUserId(String name);
}
