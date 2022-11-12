package cn.itcast.dtx.seatademo.bank1.feign.fallback;

import cn.itcast.dtx.seatademo.bank1.feign.Back2Client;
import org.springframework.stereotype.Component;

/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-10-26-16:49
 * Description:
 */
@Component
public class Back2ClientFallback implements Back2Client {
    @Override
    public String transfer(Double amount) {
        return "请求失败";
    }
}
