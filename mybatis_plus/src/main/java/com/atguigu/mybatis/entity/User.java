package com.atguigu.mybatis.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")
public class User {
    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private String id;
    @TableField("name")
    private String name1;
    private Integer age;
    private String email;
    //新增数据到数据库是，设置gmtCreate的值 insert 新增自动填充
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;
    @TableField(fill = FieldFill.INSERT_UPDATE) // 新增和更新时都填充
    private Date gmtModified;

    //ssh默认开头的单词有特殊含义 如果是0没删除，如果是1 代表删除成功
    @TableField(value = "is_deleted")
    @TableLogic//表示当前字段为逻辑删除字段，查询时会添加逻辑字段的条件
    private Boolean deleted;
    public User(String id, String name1, Integer age, String email) {
        this.id = id;
        this.name1 = name1;
        this.age = age;
        this.email = email;
    }
}
