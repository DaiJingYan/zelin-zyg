package com.zyg.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zyg.order.dao.TbAddressMapper;
import com.zyg.order.entity.TbAddress;
import com.zyg.order.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ------------------------------
 * 功能：
 * 作者：WF
 * 微信：hbxfwf13590332912
 * 创建时间：2021/12/21-15:06
 * ------------------------------
 */
@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private TbAddressMapper addressMapper;
    //1. 根据登录名查询地址列表
    @Override
    public List<TbAddress> findAddressByUserId(String name) {
        return addressMapper.selectList(new QueryWrapper<TbAddress>().eq("user_id",name));
    }
}

