package cn.itcast.dtx.seatademo.bank1.service.impl;


import cn.itcast.dtx.seatademo.bank1.dao.OrderDao;
import cn.itcast.dtx.seatademo.bank1.entity.Order;
import cn.itcast.dtx.seatademo.bank1.feign.AccountService;
import cn.itcast.dtx.seatademo.bank1.feign.StorageService;
import cn.itcast.dtx.seatademo.bank1.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderDao orderDao;
    @Resource
    private StorageService storageService;
    @Resource
    private AccountService accountService;

    /**
     * 测试一次订单交易流程，涉及多个数据库
     * 下订单 --> 减库存 --> 减余额 --> 改状态
     * @param order
     */
    @Override
    public void create(Order order) {
        log.info("开始新建订单....");
        orderDao.create(order);

        log.info("订单微服务 调用 库存微服务，做扣减......");
        storageService.decrease(order.getProductId(),order.getCount());
        log.info("执行扣减完毕......");

        log.info("订单微服务 调用 账户微服务，做付款......");
        accountService.decrease(order.getUserId(),order.getMoney());
        log.info("执行付款完毕......");

        log.info("修改订单状态.....");
        orderDao.update(order.getUserId(),0);
        log.info("订单流程结束.....");
    }
}
