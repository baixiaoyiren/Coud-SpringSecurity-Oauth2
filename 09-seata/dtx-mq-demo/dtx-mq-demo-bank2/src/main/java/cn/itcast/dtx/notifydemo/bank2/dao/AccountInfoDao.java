package cn.itcast.dtx.notifydemo.bank2.dao;

import cn.itcast.dtx.notifydemo.entity.AccountInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Mapper
@Component
public interface AccountInfoDao extends BaseMapper<AccountInfo> {
    @Update("update account_info set account_balance=account_balance+#{amount} where account_no=#{accountNo}")
    int updateAccountBalance(@Param("accountNo") String accountNo, @Param("amount") BigDecimal amount);

    @Select("select count(1) from de_duplication where tx_no = #{txNo} and state=#{state}")
    int isExistTx(@Param("txNo")String txNo,@Param("state")String state);

    @Insert("insert into de_duplication values(#{txNo},now());")
    int addTx(@Param("txNo")String txNo);

}
