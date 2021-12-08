package com.zyg.page.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyg.page.entity.ItemEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品表
 * 
 * @author hbxfwf
 * @email hbxfwf@sina.com
 * @date 2021-10-22 16:59:59
 */
@Mapper
public interface ItemDao extends BaseMapper<ItemEntity> {
	
}
