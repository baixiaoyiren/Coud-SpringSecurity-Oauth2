package cn.itcast.dtx.notifymsg.pay.dao;

import cn.itcast.dtx.notifydemo.entity.AccountPay;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface AccountPayDao extends BaseMapper<AccountPay> {

}
