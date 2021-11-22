package com.zyg.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * ------------------------------
 * 功能：班级表
 * 作者：WF
 * 微信：hbxfwf13590332912
 * 创建时间：2021/11/22-9:57
 * ------------------------------
 */
@Data
public class Classes {
    @TableId(type = IdType.AUTO)
    private Integer cid;
    private String cname;
}
