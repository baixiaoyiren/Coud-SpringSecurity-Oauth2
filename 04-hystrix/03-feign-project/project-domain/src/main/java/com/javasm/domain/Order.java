package com.javasm.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author：MoDebing
 * @Version：1.0
 * @Date：2022-10-10-11:17
 * @Description:
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {

    private Integer orderId;

    private String name;

    private Double price;
}
