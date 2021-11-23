package com.zyg.manager.dao;

import com.zyg.manager.entity.ItemEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品表
 * 
 * @author hbxfwf
 * @email 2568783935@qq.com
 * @date 2021-11-23 10:27:07
 */
@Mapper
public interface ItemDao extends BaseMapper<ItemEntity> {
	
}
