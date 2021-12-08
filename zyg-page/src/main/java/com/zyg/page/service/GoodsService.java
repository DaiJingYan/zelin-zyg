package com.zyg.page.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyg.common.utils.PageUtils;
import com.zyg.page.entity.GoodsEntity;

import java.util.Map;

/**
 * 商品
 *
 * @author hbxfwf
 * @email hbxfwf@sina.com
 * @date 2021-10-22 16:59:59
 */
public interface GoodsService extends IService<GoodsEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void updateStatus(Long id, String status);
}

