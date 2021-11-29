package com.zyg.shop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyg.common.utils.PageUtils;
import com.zyg.shop.entity.SpecificationEntity;
import com.zyg.shop.entity.group.Specification;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author hbxfwf
 * @email 2568783935@qq.com
 * @date 2021-11-26 09:39:42
 */
public interface SpecificationService extends IService<SpecificationEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<Map> findSpecByTypeId(String typeId);
}

