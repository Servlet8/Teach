package com.atguigu.mybatis.entity;

import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

@Data
public class Product {
    private String id;
    private String name;
    private Integer price;
    //表示当前属性为了乐观锁字段值，以后更新mp会自动检查查询数据的version的值是否一致，如果版本不一致，就会版本不一致
    @Version
    private Integer version;
}
