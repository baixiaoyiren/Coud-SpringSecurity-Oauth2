package cn.itcast.dtx.notifydemo.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "de_duplication")
@ToString
public class AccountChangeEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 账号
     */
    private String accountNo;
    /**
     * 变动金额
     */
    @TableField(value = "pay_amount")
    private BigDecimal amount;

    /**
     * 事务号
     */
    @TableId(value = "tx_no",type = IdType.INPUT)
    private String txNo;


    private String state;


    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    public AccountChangeEvent(String accountNo, BigDecimal amount, String txNo, String state) {
        this.accountNo = accountNo;
        this.amount = amount;
        this.txNo = txNo;
        this.state = state;
    }
}
