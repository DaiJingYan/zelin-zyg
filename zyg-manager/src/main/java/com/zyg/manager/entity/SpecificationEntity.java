package com.zyg.manager.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

/**
 * 
 * 
 * @author hbxfwf
 * @email 2568783935@qq.com
 * @date 2021-11-23 10:27:07
 */
@Data
@TableName("tb_specification")
public class SpecificationEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	@GeneratedValue(strategy= GenerationType.IDENTITY)			//添加此注解后会得到添加后的规格id，但前台传入id时不能给值（比如：0）
	private String id;
	/**
	 * 名称
	 */
	private String specName;

}
