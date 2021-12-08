package com.zyg.page.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyg.page.entity.GoodsEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品
 * 
 * @author hbxfwf
 * @email hbxfwf@sina.com
 * @date 2021-10-22 16:59:59
 */
@Mapper
public interface GoodsDao extends BaseMapper<GoodsEntity> {
	
}
