package com.zyg.page.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 商品类目
 * 
 * @author hbxfwf
 * @email hbxfwf@sina.com
 * @date 2021-10-22 16:59:59
 */
@Data
@TableName("tb_item_cat")
public class ItemCatEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 类目ID
	 */
	@TableId
	private Long id;
	/**
	 * 父类目ID=0时，代表的是一级的类目
	 */
	private Long parentId;
	/**
	 * 类目名称
	 */
	private String name;
	/**
	 * 类型id
	 */
	private Long typeId;

}
