package cn.itcast.dtx.notifydemo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * @author admin
 */
@Data
public class AccountInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Long id;

	/**
	 * 户主姓名
	 */
	private String accountName;

	/**
	 * 银行卡号
	 */
	private String accountNo;

	/**
	 * 帐户密码
	 */
	private String accountPassword;

	/**
	 * 帐户余额
	 */
	private Double accountBalance;
}
