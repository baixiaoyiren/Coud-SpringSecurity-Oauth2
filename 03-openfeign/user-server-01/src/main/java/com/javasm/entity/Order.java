package com.javasm.entity;

import lombok.*;

import java.util.Date;

/**
 * @Author：MoDebing
 * @Version：1.0
 * @Date：2022-10-10-04:38
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Order {
    private Integer id;
    private String name;
    private Double price;
    private Date time;
}
