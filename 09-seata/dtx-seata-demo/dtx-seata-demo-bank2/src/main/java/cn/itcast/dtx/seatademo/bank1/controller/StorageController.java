package cn.itcast.dtx.seatademo.bank1.controller;


import cn.itcast.dtx.seatademo.bank1.entity.Response;
import cn.itcast.dtx.seatademo.bank1.service.StorageService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class StorageController {

    @Resource
    private StorageService storageService;

    /**
     * 扣减库存
     */
    @RequestMapping("/storage/decrease")
    public Response decrease(Long productId, Integer count) {
        storageService.decrease(productId, count);
        return new Response(200,"扣减库存成功！");
    }
}
