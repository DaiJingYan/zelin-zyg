package com.zyg.page.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyg.common.utils.PageUtils;
import com.zyg.page.entity.GoodsDescEntity;

import java.util.Map;

/**
 * 
 *
 * @author hbxfwf
 * @email hbxfwf@sina.com
 * @date 2021-10-22 16:59:59
 */
public interface GoodsDescService extends IService<GoodsDescEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

