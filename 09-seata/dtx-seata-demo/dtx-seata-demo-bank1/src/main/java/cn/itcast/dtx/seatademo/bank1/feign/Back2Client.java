package cn.itcast.dtx.seatademo.bank1.feign;

import cn.itcast.dtx.seatademo.bank1.feign.fallback.Back2ClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-10-26-16:45
 * Description:
 */
@FeignClient(value = "seata-demo-bank2",fallback = Back2ClientFallback.class)
public interface Back2Client {

    // 远程调用李四的微服务
    @GetMapping("bank2/transfer")
    public String transfer(@RequestParam("amount")Double amount);
}
