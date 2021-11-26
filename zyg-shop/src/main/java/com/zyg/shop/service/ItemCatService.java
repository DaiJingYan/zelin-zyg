package com.zyg.shop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyg.common.utils.PageUtils;
import com.zyg.shop.entity.ItemCatEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品类目
 *
 * @author hbxfwf
 * @email 2568783935@qq.com
 * @date 2021-11-26 09:39:42
 */
public interface ItemCatService extends IService<ItemCatEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<ItemCatEntity> findByParentId(String itemCatId);
}

