package com.zyg.page.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * ------------------------------
 * 功能：学生表
 * 作者：WF
 * 微信：hbxfwf13590332912
 * 创建时间：2021/11/22-9:48
 * ------------------------------
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("student")
public class Student {

    @TableId(type=IdType.AUTO)      //定义主键是一个自动增长的主键
    private Integer sid;
    private String sname;
    private String sex;
    private Integer age;
    private String addr;
    private Integer cid;

    // 自动填充
    @TableField(fill = FieldFill.INSERT)        //代表插入数据时自动填充
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)   //代表插入、数据修改时自动填充
    private LocalDateTime updateTime;

    // 逻辑删除
    @TableLogic      //默认：1：代表删除 0：代表不删除（正常数据）
    private Integer deleted;

    @TableField(exist = false)      //代表数据库表中不存在此字段
    private String cname;



    public Student(String sname, String sex, Integer age, String addr, Integer cid) {
        this.sname = sname;
        this.sex = sex;
        this.age = age;
        this.addr = addr;
        this.cid = cid;
    }
}
