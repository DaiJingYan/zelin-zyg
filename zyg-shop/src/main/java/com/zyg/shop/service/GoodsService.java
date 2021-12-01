package com.zyg.shop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyg.common.utils.PageUtils;
import com.zyg.shop.entity.GoodsEntity;
import com.zyg.shop.entity.group.Goods;

import java.util.Map;

/**
 * 商品
 *
 * @author hbxfwf
 * @email 2568783935@qq.com
 * @date 2021-11-26 09:39:42
 */
public interface GoodsService extends IService<GoodsEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void save(Goods goods);

    Goods findById(String id);

    void update(Goods goods);
}

