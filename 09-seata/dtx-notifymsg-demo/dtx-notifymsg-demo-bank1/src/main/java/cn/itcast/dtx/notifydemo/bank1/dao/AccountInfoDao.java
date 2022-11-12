package cn.itcast.dtx.notifydemo.bank1.dao;

import cn.itcast.dtx.notifydemo.entity.AccountInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Mapper
@Component
public interface AccountInfoDao extends BaseMapper<AccountInfo> {
    //修改账户金额
    @Update("update account_info set account_balance=account_balance+#{amount} where account_no=#{accountNo}")
    int updateAccountBalance(@Param("accountNo") String accountNo, @Param("amount") BigDecimal amount);


   //查询幂等记录，用于幂等控制
    @Select("select count(1) from de_duplication where tx_no = #{txNo}")
    int isExistTx(String txNo);

    //添加事务记录，用于幂等控制
    @Insert("insert into de_duplication values(#{txNo},now());")
    int addTx(String txNo);

}
