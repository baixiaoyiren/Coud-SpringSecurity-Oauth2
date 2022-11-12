package cn.itcast.dtx.seatademo.bank1.feign;

import cn.itcast.dtx.seatademo.bank1.entity.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(value = "seata-account-service")
public interface AccountService {
    /**
     * 根据userId对指定用户进行付款money操作
     */
    @PostMapping("account/decrease")
    Response decrease(@RequestParam("userId") Long userId,
                      @RequestParam("money") BigDecimal money);
}
