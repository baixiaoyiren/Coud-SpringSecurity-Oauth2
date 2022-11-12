package cn.itcast.dtx.notifydemo.bank1.dao;


import cn.itcast.dtx.notifydemo.entity.AccountChangeEvent;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-10-28-20:13
 * Description:
 */
@Mapper
public interface AccountChangeEventMapper extends BaseMapper<AccountChangeEvent> {
}
