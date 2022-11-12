package cn.itcast.dtx.seatademo.bank1.feign;

import cn.itcast.dtx.seatademo.bank1.entity.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "seata-storage-service")
public interface StorageService {
    /**
     * 根据productID对指定产品进行减库存count操作
     */
    @PostMapping("storage/decrease")
    Response decrease(@RequestParam("productId") Long productId,
                      @RequestParam("count") Integer count);
}

