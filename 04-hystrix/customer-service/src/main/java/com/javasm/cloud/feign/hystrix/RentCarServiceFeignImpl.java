package com.javasm.cloud.feign.hystrix;

import com.javasm.cloud.feign.RentCarServiceFeign;
import org.springframework.stereotype.Component;

/**
 * @Author：MoDebing
 * @Version：1.0
 * @Date：2022-10-10-06:08
 * @Description:
 */
@Component
public class RentCarServiceFeignImpl implements RentCarServiceFeign {

    /**
     * 这个方法就是备选方案，失败回调
     *
     * @return
     */
    @Override
    public String rent() {
        return "我是断路器备选方案";
    }
}
