package com.javasm.cloud.entity;

import lombok.*;

import java.io.Serializable;

/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-10-24-13:41
 * Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class RedisLockMessage implements Serializable {
    private static final long serialVersionUID = -1097961340710804027L;

    // 当前线程名称
    private long threadId;

    // 当前锁的唯一id;
    private String uuId;

    // 锁的名称
    private String lockName;

    // 上锁的次数
    private Integer count;


}
