package com.javasm.cloud.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author：MoDebing
 * @Version：1.0
 * @Date：2022-10-09-23:00
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {

    private String name;

    private String age;
}
