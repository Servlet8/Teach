package com.atguigu.mybatis.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        metaObject.setValue("gmtCreate", new Date());
        metaObject.setValue("gmtModified", new Date());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        metaObject.setValue("gmtModified", new Date());
    }
}
