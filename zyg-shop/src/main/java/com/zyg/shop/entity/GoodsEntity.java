package com.zyg.shop.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

/**
 * 商品
 * 
 * @author hbxfwf
 * @email 2568783935@qq.com
 * @date 2021-11-26 09:39:42
 */
@Data
@TableName("tb_goods")
public class GoodsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;
	/**
	 * 商家ID
	 */
	private String sellerId;
	/**
	 * SPU名
	 */
	private String goodsName;
	/**
	 * 默认SKU
	 */
	private String defaultItemId;
	/**
	 * 状态
	 */
	private String auditStatus;
	/**
	 * 是否上架
	 */
	private String isMarketable;
	/**
	 * 品牌
	 */
	private String brandId;
	/**
	 * 副标题
	 */
	private String caption;
	/**
	 * 一级类目
	 */
	private String category1Id;
	/**
	 * 二级类目
	 */
	private String category2Id;
	/**
	 * 三级类目
	 */
	private String category3Id;
	/**
	 * 小图
	 */
	private String smallPic;
	/**
	 * 商城价
	 */
	private BigDecimal price;
	/**
	 * 分类模板ID
	 */
	private String typeTemplateId;
	/**
	 * 是否启用规格
	 */
	private String isEnableSpec;
	/**
	 * 是否删除
	 */
	private String isDelete;

}
