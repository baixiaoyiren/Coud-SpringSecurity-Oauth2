package cn.itcast.dtx.notifydemo.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-10-28-21:00
 * Description:
 */
@Component
@Slf4j
public class MybatisConfig implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("<<<<<<<<<<<MetaObjectHandler   insertFill<<<<<<<<<<<<<<<<");
        this.setFieldValByName("createTime",new Date(),metaObject);
        this.setFieldValByName("updateTime",new Date(),metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("<<<<<<<<<<<MetaObjectHandler   updateFill<<<<<<<<<<<<<<<<");
        this.setFieldValByName("updateTime",new Date(),metaObject);
    }
}
