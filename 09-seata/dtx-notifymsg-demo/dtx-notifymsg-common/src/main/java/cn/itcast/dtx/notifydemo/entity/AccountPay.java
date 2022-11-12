package cn.itcast.dtx.notifydemo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 
 * </p>
 *
 * @author modebing
 * @since 2022-10-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AccountPay implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 账号
     */
    private String accountNo;

    /**
     * 充值余额
     */
    private BigDecimal payAmount;

    /**
     * 充值结果:success，fail
     */
    private String result;


}
