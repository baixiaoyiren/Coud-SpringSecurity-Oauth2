package com.javasm.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author：MoDebing
 * @Version：1.0
 * @Date：2022-10-10-14:09
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private Integer id;

    private String name;

    private String pwd;

    private Integer age;


}
