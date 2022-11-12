package cn.itcast.dtx.seatademo.bank1.service;

public interface StorageService {
    /**
     * 扣减库存
     */
    void decrease(Long productId, Integer count);
}
