package com.zyg.shop.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 商品类目
 * 
 * @author hbxfwf
 * @email 2568783935@qq.com
 * @date 2021-11-26 09:39:42
 */
@Data
@TableName("tb_item_cat")
public class ItemCatEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 类目ID
	 */
	@TableId
	private String id;
	/**
	 * 父类目ID=0时，代表的是一级的类目
	 */
	private String parentId;
	/**
	 * 类目名称
	 */
	private String name;
	/**
	 * 类型id
	 */
	private String typeId;

}
